package org.soldomi.model.tune2;

public final class TimeSignature {
    public final Long id;
    public final Long symbolId;
    public final Integer beatCount;
    public final NoteValue beatValue;

    public TimeSignature(Integer beatCount, NoteValue beatValue) {
	this(null, null, beatCount, beatValue);
    }
    
    public TimeSignature(Long id, Long symbolId, Integer beatCount, NoteValue beatValue) {
	this.id = id;
	this.symbolId = symbolId;
	this.beatCount = beatCount;
	this.beatValue = beatValue;
    }

    public TimeSignature withId(Long id) {
	return new TimeSignature(id, symbolId, beatCount, beatValue);
    }

    public TimeSignature withSymbolId(Long symbolId) {
	return new TimeSignature(id, symbolId, beatCount, beatValue);
    }
}
