package org.soldomi.model.tune;

public enum NoteValue {
    WHOLE("whole"),
    HALF("half"),
    QUARTER("quarter"),
    EIGHTH("eighth"),
    SIXTEENTH("sixteenth"),
    THIRTY_SECOND("thirty_second");

    public final String baseValue;
	
    private NoteValue(String baseValue) {
	this.baseValue = baseValue;
    }

    public static NoteValue fromBaseValue(String baseValue) {
	for (NoteValue noteValue : NoteValue.values()) {
	    if (noteValue.baseValue.equals(baseValue)) {
		return noteValue;
	    }
	}
	return null;
    }

}

