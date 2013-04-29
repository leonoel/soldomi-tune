package org.soldomi.model.tune;

public enum Clef {
  UNDEFINED(null, null),
  TREBLE(new Pitch(NoteName.B, 4),
	 SymbolType.TREBLE_CLEF),
  BASS(new Pitch(NoteName.D, 3),
       SymbolType.BASS_CLEF),
  ALTO(new Pitch(NoteName.C, 4),
       SymbolType.ALTO_CLEF),
  TENOR(new Pitch(NoteName.A, 3),
	SymbolType.TENOR_CLEF);

  /*
   * Pitch of the middle line
   */
  public final Pitch pitch;
  public final SymbolType symbolType;

  private Clef(Pitch _pitch, SymbolType _symbolType) {
    pitch = _pitch;
    symbolType = _symbolType;
  }
  
}
