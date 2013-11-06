package org.soldomi.model.tune2;

public enum SymbolRole {
    REST(true),
    NOTE(true),
    CLEF(false),
    KEY_SIGNATURE(false),
    TIME_SIGNATURE(false);
    public final boolean isSegment;
    private SymbolRole(boolean isSegment) {
	this.isSegment = isSegment;
    }
}
