package org.soldomi.model.tune2;

import org.apache.commons.math3.fraction.Fraction;

public final class Segment {
    public final Long id;
    public final Long symbolId;
    
    public final Fraction duration;

    public final Note note;

    public Segment(Fraction duration) {
	this(null, null, duration, null);
    }

    public Segment(Long id, Long symbolId, Fraction duration, Note note) {
	this.id = id;
	this.symbolId = symbolId;
	this.duration = duration;
	this.note = note;
    }
}
