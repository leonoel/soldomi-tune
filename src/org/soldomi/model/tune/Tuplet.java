package org.soldomi.model.tune;

import org.soldomi.commons.Property;
import org.soldomi.commons.SingleRelationship;

public class Tuplet {
    public final Property<Long> id = new Property<Long>();
    public final SingleRelationship<Tuplet, Segment> segment = new SingleRelationship<Tuplet, Segment>(this);
    public final Property<Long> duration = new Property<Long>();
}
