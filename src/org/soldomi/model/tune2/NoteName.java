package org.soldomi.model.tune2;

public enum NoteName {
    C("c"),
    D("d"),
    E("e"),
    F("f"),
    G("g"),
    A("a"),
    B("b");
    public String baseValue;
    private NoteName(String _baseValue) {
	baseValue = _baseValue;
    }
    public static NoteName fromBaseValue(String baseValue) {
	for (NoteName noteName : NoteName.values()) {
	    if (noteName.baseValue.equals(baseValue)) {
		return noteName;
	    }
	}
	return null;
    }
}

