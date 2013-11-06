import org.soldomi.model.tune2.Tune;
import org.soldomi.model.tune2.Syst;
import org.soldomi.model.tune2.Staff;
import org.soldomi.model.tune2.Sect;
import org.soldomi.model.tune2.Block;
import org.soldomi.model.tune2.TuneDao;
import org.soldomi.model.tune2.TuneJson;

import org.soldomi.commons2.Result;

import org.h2.tools.Server;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.InputStream;

public class Test {

    public static void main(String[] args) {
	Server server = null;
	try {
	    server = Server.createTcpServer().start();
	    Class.forName("org.h2.Driver");

	    createTables.run();
	    makeDataSet();

	    getAllTunes.run();

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (server != null)
		server.stop();
	}
    }

    private static abstract class WithConnection {
	protected abstract void action(Connection connection) throws Exception;
	public void run() throws Exception {
	    action(DriverManager.getConnection("jdbc:h2:mem:test", "sa", ""));
	}
    }

    private static WithConnection createTables = new WithConnection() {
	@Override protected void action(Connection connection) throws Exception {
	    InputStream in = Tune.class.getResourceAsStream("tables.sql");
	    Scanner s = new Scanner(in).useDelimiter("\\A");
	    String sql = s.hasNext() ? s.next() : null;
	    Statement statement = connection.createStatement();
	    statement.execute(sql);
	}
    };

    private static void makeDataSet() throws Exception {
	for (final TuneFactory2 tuneFactory : new TuneFactory2[] {TuneFactory2.brassBand}) {
	    new WithConnection() {
		@Override protected void action(Connection connection) throws Exception {
		    Result<Tune> result = TuneDao.insertTuneWithSystsAndSects.run(connection, tuneFactory.make());
		    if (result.success) {
			System.out.println(TuneJson.tuneWithSystsAndSects.write(result.value()).toString());
		    } else {
			System.out.println("makeDataSet : Error : " + result.error());
		    }
		}
	    }.run();
	}
    }

    private static WithConnection getAllTunes = new WithConnection() {
        @Override protected void action(final Connection connection) throws Exception {
	}
    };

    public static String printTuneInfo(Tune tune) {
	String newLine = System.getProperty("line.separator");
	StringBuilder builder = new StringBuilder();
	builder.append(tune.name);
	builder.append(newLine);
	for (Syst syst : tune.systs) {
	    builder.append(syst.name);
	    builder.append(newLine);
	    for (Staff staff : syst.staves) {
		builder.append(staff.name);
		builder.append(newLine);
	    }
	}
	for (Sect sect : tune.sects) {
	    builder.append(sect.startTime);
	    builder.append(newLine);
	    for (Block block : sect.blocks) {
		builder.append(block.startTime);
		builder.append(newLine);
	    }
	}
	return builder.toString();
    }
}
