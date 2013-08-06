package org.soldomi.model.tune;

import org.soldomi.commons.Function;
import org.soldomi.commons.Edge;
import org.soldomi.commons.Property;

public class Segment {
    public static final Function<Void, Segment> constructor = new Function<Void, Segment>() {
	@Override public Segment apply(Void value) { return new Segment(); }
    };

    public static final Function<Segment, Edge<Segment, Symbol>> metaSymbol = new Function<Segment, Edge<Segment, Symbol>>() {
	@Override public Edge<Segment, Symbol> apply(Segment segment) { return segment.symbol; }
    };

    public static final Function<Segment, Edge<Segment, Note>> metaNote = new Function<Segment, Edge<Segment, Note>>() {
	@Override public Edge<Segment, Note> apply(Segment segment) { return segment.note; }
    };

    public final Edge<Segment, Symbol> symbol = Edge.makeOne(this,
							     Symbol.constructor,
							     Symbol.metaSegment);

    public final Edge<Segment, Note> note = Edge.makeOne(this,
							 Note.constructor,
							 Note.metaSegment);
    public final Property<Long> id = Property.makeLong();
}

