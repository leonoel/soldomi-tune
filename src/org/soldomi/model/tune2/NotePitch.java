package org.soldomi.model.tune2;

public enum NotePitch {
    NATURAL("natural"),
    SHARP("sharp"),
    FLAT("flat");

    public final String baseValue;
	
    private NotePitch(String baseValue) {
	this.baseValue = baseValue;
    }

    public static NotePitch fromBaseValue(String baseValue) {
	for (NotePitch notePitch : NotePitch.values()) {
	    if (notePitch.baseValue.equals(baseValue)) {
		return notePitch;
	    }
	}
	return null;
    }

}
