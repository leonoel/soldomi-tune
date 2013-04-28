package org.soldomi.model.tune;

import org.soldomi.commons.SingleRelationship;
import org.soldomi.commons.MultipleRelationship;
import org.soldomi.commons.Property;

public class Sect {
    public final Property<Long> id = new Property<Long>();
    public final SingleRelationship<Sect, Tune> tune = new SingleRelationship<Sect, Tune>(this);
    public final MultipleRelationship<Sect, Block> blocks = new MultipleRelationship<Sect, Block>(this);
    public final Property<Long> startTime = new Property<Long>();

}
