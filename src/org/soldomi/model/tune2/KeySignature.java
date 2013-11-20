package org.soldomi.model.tune2;

public final class KeySignature {
    public enum Modifier {
	NATURAL,
	SHARP,
	FLAT
    }

    public final Long id;
    public final Long symbolId;
    public final Modifier a;
    public final Modifier b;
    public final Modifier c;
    public final Modifier d;
    public final Modifier e;
    public final Modifier f;
    public final Modifier g;

    public KeySignature(Modifier a,
			Modifier b,
			Modifier c,
			Modifier d,
			Modifier e,
			Modifier f,
			Modifier g) {
	this(null, null, a, b, c, d, e, f, g);
    }

    public KeySignature(Long id,
			Long symbolId,
			Modifier a,
			Modifier b,
			Modifier c,
			Modifier d,
			Modifier e,
			Modifier f,
			Modifier g) {
	this.id = id;
	this.symbolId = symbolId;
	this.a = a;
	this.b = b;
	this.c = c;
	this.d = d;
	this.e = e;
	this.f = f;
	this.g = g;
    }

    public KeySignature withId(Long id) {
	return new KeySignature(id, symbolId, a, b, c, d, e, f, g);
    }

    public KeySignature withSymbolId(Long symbolId) {
	return new KeySignature(id, symbolId, a, b, c, d, e, f, g);
    }
}
