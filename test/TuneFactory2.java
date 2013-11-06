import org.soldomi.model.tune2.Tune;
import org.soldomi.model.tune2.Syst;
import org.soldomi.model.tune2.Staff;
import org.soldomi.model.tune2.Sect;
import org.soldomi.model.tune2.Block;

public interface TuneFactory2 {
    public Tune make();

    public static final TuneFactory2 brassBand = new TuneFactory2() {

	    @Override public Tune make() {
		System.out.println("TuneFactory2 brassBand - make");

		Tune tune = new Tune(null, "New brass band tune");
		for (String staffName : new String[] {"Trumpet",
						      "Trombone",
						      "Saxhorn",
						      "Sousaphone"}) {

		    tune = tune.addSyst(new Syst(staffName)
					.addStaff(new Staff(staffName))
					);
		}


		Sect sect = new Sect(0L);

		for (Long time : new Long[] {0L, 64L, 128L}) {
		    sect = sect.addBlock(new Block(time));
		}

		tune = tune.addSect(sect);

		System.out.println(Test.printTuneInfo(tune));
		
		return tune;
	    }

	};

}
