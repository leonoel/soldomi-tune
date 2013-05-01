package org.soldomi.model.tune;

import org.soldomi.commons.Property;
import org.soldomi.commons.MultipleRelationship;

public class Tuplet {
    public final Property<Long> id = new Property<Long>();
    public final MultipleRelationship<Tuplet, Segment> segments = new MultipleRelationship<Tuplet, Segment>(this);
    public final Property<Long> duration = new Property<Long>();
}
