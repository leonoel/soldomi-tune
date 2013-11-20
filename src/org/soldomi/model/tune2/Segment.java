package org.soldomi.model.tune2;

import org.apache.commons.math3.fraction.Fraction;

public final class Segment {
    public final Long id;
    public final Long symbolId;
    public final Fraction duration;
    public final Integer dotCount;
    public final Long tupletId;
    public final Note note;

    public Segment(Fraction duration, Integer dotCount) {
	this(null, null, duration, dotCount, null, null);
    }

    public Segment(Fraction duration, Integer dotCount, Note note) {
	this(null, null, duration, dotCount, null, note);
    }

    public Segment(Long id, Long symbolId, Fraction duration, Integer dotCount, Long tupletId, Note note) {
	this.id = id;
	this.symbolId = symbolId;
	this.duration = duration;
	this.dotCount = dotCount;
	this.tupletId = tupletId;
	this.note = note;
    }

    public Segment withId(Long id) {
	Note note = this.note;
	if (note != null) {
	    note = note.withSegmentId(id);
	}
	return new Segment(id, symbolId, duration, dotCount, tupletId, note);
    }

    public Segment withSymbolId(Long symbolId) {
	return new Segment(id, symbolId, duration, dotCount, tupletId, note);
    }

    public Segment withNote(Note note) {
	return new Segment(id, symbolId, duration, dotCount, tupletId, note);
    }
}
