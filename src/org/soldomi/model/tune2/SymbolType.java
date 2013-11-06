package org.soldomi.model.tune2;

public enum SymbolType {

    WHOLE(SymbolRole.NOTE),
    WHOLE_R(SymbolRole.REST),
    HALF(SymbolRole.NOTE),
    HALF_R(SymbolRole.REST),
    QUARTER(SymbolRole.NOTE),
    QUARTER_R(SymbolRole.REST),
    EIGHTH(SymbolRole.NOTE),
    EIGHTH_R(SymbolRole.REST),
    SIXTEENTH(SymbolRole.NOTE),
    SIXTEENTH_R(SymbolRole.REST),
    THIRTY_SECOND(SymbolRole.NOTE),
    THIRTY_SECOND_R(SymbolRole.REST),
    SIXTY_FOURTH(SymbolRole.NOTE),
    SIXTY_FOURTH_R(SymbolRole.REST),
    TREBLE_CLEF(SymbolRole.CLEF),
    BASS_CLEF(SymbolRole.CLEF),
    ALTO_CLEF(SymbolRole.CLEF),
    TENOR_CLEF(SymbolRole.CLEF),
    KEY_SIGNATURE(SymbolRole.KEY_SIGNATURE),
    STANDARD_TIME_SIGNATURE(SymbolRole.TIME_SIGNATURE),
    ALLA_BREVE(SymbolRole.TIME_SIGNATURE),
    COMMON_TIME(SymbolRole.TIME_SIGNATURE);

    public final SymbolRole role;

    private SymbolType(SymbolRole role) {
	this.role = role;
    }

}
