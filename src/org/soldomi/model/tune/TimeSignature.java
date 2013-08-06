package org.soldomi.model.tune;

import org.soldomi.commons.Function;
import org.soldomi.commons.Edge;
import org.soldomi.commons.Property;

public class TimeSignature {
    public static final Function<Void, TimeSignature> constructor = new Function<Void, TimeSignature>() {
	@Override public TimeSignature apply(Void value) { return new TimeSignature(); }
    };

    public static final Function<TimeSignature, Edge<TimeSignature, Symbol>> metaSymbol = new Function<TimeSignature, Edge<TimeSignature, Symbol>>() {
	@Override public Edge<TimeSignature, Symbol> apply(TimeSignature timeSignature) { return timeSignature.symbol; }
    };
    
    public final Edge<TimeSignature, Symbol> symbol = Edge.makeOne(this,
								  Symbol.constructor,
								  Symbol.metaTimeSignature);
    public final Property<Long> id = Property.makeLong();
}
