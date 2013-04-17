package org.soldomi.model.tune;

import org.soldomi.commons.Property;

public class Tune {
    public final Property<String> name = new Property<String>();
    public final HasSome<Tune, Syst> systs = new HasSome<Tune, Syst>();
}
