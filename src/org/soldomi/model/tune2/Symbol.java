package org.soldomi.model.tune2;

import org.apache.commons.math3.fraction.Fraction;

public class Symbol {
    public final Long id;
    public final Long staffId;
    public final Long blockId;

    public final Fraction startTime;
    public final SymbolType symbolType;

    public final Segment segment;
    public final TimeSignature timeSignature;
    public final KeySignature keySignature;

    public Symbol(Long id,
		  Long staffId,
		  Long blockId,
		  Fraction startTime,
		  SymbolType symbolType,
		  Segment segment,
		  TimeSignature timeSignature,
		  KeySignature keySignature) {
	this.id = id;
	this.staffId = staffId;
	this.blockId = blockId;
	this.startTime = startTime;
	this.symbolType = symbolType;
	this.segment = segment;
	this.timeSignature = timeSignature;
	this.keySignature = keySignature;

    }

    public static Symbol newClef(Fraction startTime, Clef clef) {
	return new Symbol(null, null, null, startTime, clef.symbolType, null, null, null);
    }

    public static Symbol newSegment(Fraction startTime, DurationSymbol durationSymbol, Boolean isRest, Fraction duration) {
	return new Symbol(null, null, null, startTime, isRest ? durationSymbol.restSymbolType : durationSymbol.noteSymbolType, new Segment(duration), null, null);
    }
}
