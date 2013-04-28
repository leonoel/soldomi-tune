package org.soldomi.model.tune;

import org.soldomi.commons.Property;
import org.soldomi.commons.SingleRelationship;

public class Note {
    public final Property<Long> id = new Property<Long>();
    public final SingleRelationship<Note, Segment> segment = new SingleRelationship<Note, Segment>(this);
    public final Property<Accidental> accidental = new Property<Accidental>();
    public final Property<Pitch> pitch = new Property<Pitch>();
}
