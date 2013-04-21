package org.soldomi.model.tune;

import org.soldomi.commons.Property;
import org.soldomi.commons.Option;

public class Symbol {
    public final Property<Symbol, Staff> staff = new Property<Symbol, Staff>(this);
    public final Option<Symbol, Segment> segment = new Option<Symbol, Segment>(this);
    public final Option<Symbol, TimeSignature> timeSignature = new Option<Symbol, TimeSignature>(this);

    
}
