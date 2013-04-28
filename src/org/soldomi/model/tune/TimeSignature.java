package org.soldomi.model.tune;

import org.soldomi.commons.Property;
import org.soldomi.commons.SingleRelationship;

public class TimeSignature {
    public final Property<Long> id = new Property<Long>();
    public final SingleRelationship<TimeSignature, Symbol> symbol = new SingleRelationship<TimeSignature, Symbol>(this);
    public final Property<Integer> beatCount = new Property<Integer>();
    public final Property<NoteValue> beatValue = new Property<NoteValue>();
}
