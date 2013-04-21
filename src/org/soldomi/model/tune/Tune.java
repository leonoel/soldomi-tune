package org.soldomi.model.tune;

import org.soldomi.commons.Property;
import org.soldomi.commons.HasSome;

public class Tune {
    public final Property<Tune, String> name = new Property<Tune, String>(this);
    public final HasSome<Tune, Syst> systs = new HasSome<Tune, Syst>(this);

    public static void main(String[] args) {
	Tune tune = new Tune();
	tune.name.set("Barnum Circus");
    
	Syst syst = new Syst();
	tune.systs.add(syst.tune);
    }
}
