package org.soldomi.model.tune;

import org.soldomi.commons.Property;
import org.soldomi.commons.SingleRelationship;
import org.apache.commons.math3.fraction.Fraction;

public class Segment {
    public final Property<Long> id = new Property<Long>();
    public final SingleRelationship<Segment, Symbol> symbol = new SingleRelationship<Segment, Symbol>(this);
    public final SingleRelationship<Segment, Tuplet> tuplet = new SingleRelationship<Segment, Tuplet>(this);
    public final SingleRelationship<Segment, Note> note = new SingleRelationship<Segment, Note>(this);
    public final Property<Fraction> duration = new Property<Fraction>();
    public final Property<Integer> dotCount = new Property<Integer>();
}
