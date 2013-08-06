package org.soldomi.model.tune;

import org.soldomi.commons.Function;
import org.soldomi.commons.Edge;
import org.soldomi.commons.Property;

public class Symbol {
    public static final Function<Void, Symbol> constructor = new Function<Void, Symbol>() {
	@Override public Symbol apply(Void value) { return new Symbol(); }
    };

    public static final Function<Symbol, Edge<Symbol, Staff>> metaStaff = new Function<Symbol, Edge<Symbol, Staff>>() {
	@Override public Edge<Symbol, Staff> apply(Symbol symbol) { return symbol.staff; }
    };

    public static final Function<Symbol, Edge<Symbol, Block>> metaBlock = new Function<Symbol, Edge<Symbol, Block>>() {
	@Override public Edge<Symbol, Block> apply(Symbol symbol) { return symbol.block; }
    };
    
    public static final Function<Symbol, Edge<Symbol, Segment>> metaSegment = new Function<Symbol, Edge<Symbol, Segment>>() {
	@Override public Edge<Symbol, Segment> apply(Symbol symbol) { return symbol.segment; }
    };

    public static final Function<Symbol, Edge<Symbol, TimeSignature>> metaTimeSignature = new Function<Symbol, Edge<Symbol, TimeSignature>>() {
	@Override public Edge<Symbol, TimeSignature> apply(Symbol symbol) { return symbol.timeSignature; }
    };

    public static final Function<Symbol, Edge<Symbol, KeySignature>> metaKeySignature = new Function<Symbol, Edge<Symbol, KeySignature>>() {
	@Override public Edge<Symbol, KeySignature> apply(Symbol symbol) { return symbol.keySignature; } 
    };

    public final Edge<Symbol, Staff> staff = Edge.makeOne(this,
							  Staff.constructor,
							  Staff.metaSymbols);
    public final Edge<Symbol, Block> block = Edge.makeOne(this,
							  Block.constructor,
							  Block.metaSymbols);

    public final Edge<Symbol, Segment> segment = Edge.makeOne(this,
							      Segment.constructor,
							      Segment.metaSymbol);

    public final Edge<Symbol, TimeSignature> timeSignature = Edge.makeOne(this,
									  TimeSignature.constructor,
									  TimeSignature.metaSymbol);

    public final Edge<Symbol, KeySignature> keySignature = Edge.makeOne(this,
									KeySignature.constructor,
									KeySignature.metaSymbol);

    public final Property<Long> id = Property.makeLong();
    public final Property<Long> startTimeNumerator = Property.makeLong();
    public final Property<Long> startTimeDenominator = Property.makeLong();
}
