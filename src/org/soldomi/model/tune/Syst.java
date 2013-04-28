package org.soldomi.model.tune;

import org.soldomi.commons.Property;
import org.soldomi.commons.SingleRelationship;
import org.soldomi.commons.MultipleRelationship;

public class Syst {
    public final Property<Long> id = new Property<Long>();
    public final SingleRelationship<Syst, Tune> tune = new SingleRelationship<Syst, Tune>(this);
    public final MultipleRelationship<Syst, Staff> staves = new MultipleRelationship<Syst, Staff>(this);
    public final Property<String> name = new Property<String>();
}
