import org.soldomi.model.tune.Tune;
import org.soldomi.model.tune.TuneSet;
import org.soldomi.model.tune.Syst;
import org.soldomi.model.tune.Staff;
import org.soldomi.model.tune.Sect;
import org.soldomi.model.tune.Block;
import org.soldomi.model.tune.TuneDao;
import org.soldomi.model.tune.TuneJson;

import org.soldomi.commons.Function;

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
	for (final TuneFactory tuneFactory : new TuneFactory[] {TuneFactory.brassBand}) {
	    new WithConnection() {
		@Override protected void action(Connection connection) throws Exception {
		    TuneDao.insertTuneWithStavesAndBlocks.run(connection, tuneFactory.make());
		}
	    }.run();
	}
    }

    private static WithConnection getAllTunes = new WithConnection() {
        @Override protected void action(final Connection connection) throws Exception {
	    TuneSet allTunes = new TuneSet();
	    TuneDao.getAllTunes.run(connection, allTunes);
	    allTunes.tunes.forEach(new Function<Tune, Void>() {
		    @Override public Void apply(Tune tune) {
			TuneDao.getTuneWithStaffsAndBlocks.run(connection, tune);
			System.out.println(TuneJson.writeTune.toJsonNode(tune).toString());
			return null;
		    }
		});
	}
    };

    public static String printTuneInfo(Tune tune) {
	String newLine = System.getProperty("line.separator");
	StringBuilder builder = new StringBuilder();
	builder.append(tune.name.get());
	builder.append(newLine);
	for (Syst syst : tune.systs) {
	    builder.append(syst.name.get());
	    builder.append(newLine);
	    for (Staff staff : syst.staves) {
		builder.append(staff.name.get());
		builder.append(newLine);
	    }
	}
	for (Sect sect : tune.sects) {
	    builder.append(sect.startTime.get());
	    builder.append(newLine);
	    for (Block block : sect.blocks) {
		builder.append(block.startTime.get());
		builder.append(newLine);
	    }
	}
	return builder.toString();
    }
}
