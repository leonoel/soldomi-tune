package org.soldomi.model.tune2;

public final class Note {
    public final Long id;
    public final Segment segment;
    
    public Note(Long id, Segment segment) {
	this.id = id;
	this.segment = segment;
    }
}
