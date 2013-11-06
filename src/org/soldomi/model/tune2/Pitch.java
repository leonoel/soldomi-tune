package org.soldomi.model.tune2;

public final class Pitch {
    public NoteName noteName;
    public Integer octave;

    public Pitch(NoteName _noteName,
		 Integer _octave) {
	noteName = _noteName;
	octave = _octave;
    }

    public Pitch addInterval(int toAdd) {
	int ordinal = (noteName.ordinal() + toAdd) % NoteName.values().length;
	int octave = this.octave + (noteName.ordinal() + toAdd - ordinal) / NoteName.values().length;
	if (ordinal < 0) {
	    ordinal += NoteName.values().length;
	    octave--;
	}
	return new Pitch(NoteName.values()[ordinal], octave);
    }

    public static Integer interval(Pitch from, Pitch to) {
	return (to.noteName.ordinal() - from.noteName.ordinal()) +
	    NoteName.values().length * (to.octave - from.octave);
    }
}

