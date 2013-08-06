package org.soldomi.model.tune;

import org.soldomi.commons.Function;
import org.soldomi.commons.Edge;
import org.soldomi.commons.Property;

public class KeySignature {
    public static final Function<Void, KeySignature> constructor = new Function<Void, KeySignature>() {
	@Override public KeySignature apply(Void value) { return new KeySignature(); }
    };

    public static final Function<KeySignature, Edge<KeySignature, Symbol>> metaSymbol = new Function<KeySignature, Edge<KeySignature, Symbol>>() {
	@Override public Edge<KeySignature, Symbol> apply(KeySignature keySignature) { return keySignature.symbol; }
    };
    
    public final Edge<KeySignature, Symbol> symbol = Edge.makeOne(this,
								  Symbol.constructor,
								  Symbol.metaKeySignature);
    public final Property<Long> id = Property.makeLong();
}
