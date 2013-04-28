package org.soldomi.model.tune;

import org.soldomi.commons.SingleRelationship;
import org.soldomi.commons.Property;
import org.apache.commons.math3.fraction.Fraction;

public class Symbol {
    public final Property<Long> id = new Property<Long>();

    public final SingleRelationship<Symbol, Staff> staff = new SingleRelationship<Symbol, Staff>(this);
    public final SingleRelationship<Symbol, Block> block = new SingleRelationship<Symbol, Block>(this);

    public final Property<SymbolType> type = new Property<SymbolType>();
    public final Property<Fraction> startTime = new Property<Fraction>();

    public final SingleRelationship<Symbol, Segment> segment = new SingleRelationship<Symbol, Segment>(this);
    public final SingleRelationship<Symbol, TimeSignature> timeSignature = new SingleRelationship<Symbol, TimeSignature>(this);
    public final SingleRelationship<Symbol, KeySignature> keySignature = new SingleRelationship<Symbol, KeySignature>(this);
    
}
