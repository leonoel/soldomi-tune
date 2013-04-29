import org.soldomi.model.tune.Tune;
import org.soldomi.model.tune.Syst;
import org.soldomi.model.tune.Staff;
import org.soldomi.model.tune.Sect;
import org.soldomi.model.tune.Block;
import org.soldomi.model.tune.Symbol;


public class Test {
    public static void main(String[] args) {
	Tune tune = new Tune();
	Syst syst = new Syst();
	Staff staff = new Staff();
	Sect sect = new Sect();
	Block block = new Block();

	tune.systs.linkTo(syst.tune);
	syst.staves.linkTo(staff.syst);
	tune.sects.linkTo(sect.tune);
	sect.blocks.linkTo(block.sect);

	tune.name.set("Barnum Circus");

	
	Symbol symbol = new Symbol();

	System.out.println(tune.toString());

    }

}
