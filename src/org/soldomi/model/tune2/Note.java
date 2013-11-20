package org.soldomi.model.tune2;

public final class Note {
    public final Long id;
    public final Long segmentId;
    public final Pitch pitch;
    public final Accidental accidental;

    public Note(Pitch pitch, Accidental accidental) {
	this(null, null, pitch, accidental);
    }
    
    public Note(Long id, Long segmentId, Pitch pitch, Accidental accidental) {
	this.id = id;
	this.segmentId = segmentId;
	this.pitch = pitch;
	this.accidental = accidental;
    }

    public Note withId(Long id) {
	return new Note(id, segmentId, pitch, accidental);
    }

    public Note withSegmentId(Long segmentId) {
	return new Note(id, segmentId, pitch, accidental);
    }
}
