
import org.soldomi.model.tune.Tune;
import org.soldomi.model.tune.Syst;
import org.soldomi.model.tune.Staff;
import org.soldomi.model.tune.Sect;
import org.soldomi.model.tune.Block;
import org.soldomi.model.tune.Symbol;

import java.util.Date;

public interface TuneFactory {
    public Tune make();

    public static final TuneFactory brassBand = new TuneFactory() {

	    @Override public Tune make() {
		System.out.println("TuneFactory brassBand - make");

		Tune tune = new Tune();
		tune.name.set("New brass band tune");
		tune.lastModified.set(new Date());
		for (String staffName : new String[] {"Trumpet",
						      "Trombone",
						      "Saxhorn",
						      "Sousaphone"}) {

		    Syst syst = new Syst();
		    syst.name.set(staffName);
		    syst.tune.bind(tune);

		    Staff staff = new Staff();
		    staff.name.set(staffName);
		    staff.syst.bind(syst);
		}


		Sect sect = new Sect();
		sect.startTime.set(0L);
		sect.tune.bind(tune);
		
		for (Long time : new Long[] {0L, 64L, 128L}) {
		    Block block = new Block();
		    block.startTime.set(time);
		    block.sect.bind(sect);
		}

		System.out.println(Test.printTuneInfo(tune));
		
		return tune;
	    }

	};

}
