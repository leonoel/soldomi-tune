package org.soldomi.model.tune;

import org.soldomi.commons.Function;
import org.soldomi.commons.Edge;
import org.soldomi.commons.Property;

public class Note {
    public static final Function<Void, Note> constructor = new Function<Void, Note>() {
	@Override public Note apply(Void value) { return new Note(); }
    };

    public static final Function<Note, Edge<Note, Segment>> metaSegment = new Function<Note, Edge<Note, Segment>>() {
	@Override public Edge<Note, Segment> apply(Note note) { return note.segment; }
    };

    public final Edge<Note, Segment> segment = Edge.makeOne(this,
							    Segment.constructor,
							    Segment.metaNote);
    public final Property<Long> id = Property.makeLong();
}

