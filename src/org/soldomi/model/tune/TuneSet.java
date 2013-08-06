package org.soldomi.model.tune;

import org.soldomi.commons.Function;
import org.soldomi.commons.Edge;
import org.soldomi.commons.Property;

public class TuneSet {

    public static final Function<Void, TuneSet> constructor = new Function<Void, TuneSet>() {
	@Override public TuneSet apply(Void value) { return new TuneSet(); }
    };

    public static final Function<TuneSet, Edge<TuneSet, Tune>> metaTunes = new Function<TuneSet, Edge<TuneSet, Tune>>() {
	@Override public Edge<TuneSet, Tune> apply(TuneSet tuneSet) { return tuneSet.tunes; }
    };

    public final Edge<TuneSet, Tune> tunes = Edge.makeMany(this,
							   Tune.constructor,
							   Tune.metaTuneSet);

}
