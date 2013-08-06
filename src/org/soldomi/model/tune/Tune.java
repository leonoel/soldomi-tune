package org.soldomi.model.tune;

import org.soldomi.commons.Function;
import org.soldomi.commons.Edge;
import org.soldomi.commons.Property;

import java.util.Date;

public class Tune {
    public static final Function<Void, Tune> constructor = new Function<Void, Tune>() {
	@Override public Tune apply(Void value) { return new Tune(); }
    };

    public static final Function<Tune, Edge<Tune, TuneSet>> metaTuneSet = new Function<Tune, Edge<Tune, TuneSet>>() {
	@Override public Edge<Tune, TuneSet> apply(Tune tune) { return tune.tuneSet; }
    };

    public static final Function<Tune, Edge<Tune, Syst>> metaSysts = new Function<Tune, Edge<Tune, Syst>>() {
	@Override public Edge<Tune, Syst> apply(Tune tune) { return tune.systs; }
    };

    public static final Function<Tune, Edge<Tune, Sect>> metaSects = new Function<Tune, Edge<Tune, Sect>>() {
	@Override public Edge<Tune, Sect> apply(Tune tune) { return tune.sects; }
    };

    public static final Function<Tune, Property<Long>> metaId = new Function<Tune, Property<Long>>() {
	@Override public Property<Long> apply(Tune tune) { return tune.id; }
    };

    public static final Function<Tune, Property<String>> metaName = new Function<Tune, Property<String>>() {
	@Override public Property<String> apply(Tune tune) { return tune.name; }
    };

    public static final Function<Tune, Property<Date>> metaLastModified = new Function<Tune, Property<Date>>() {
	@Override public Property<Date> apply(Tune tune) { return tune.lastModified; }
    };

    public final Edge<Tune, TuneSet> tuneSet = Edge.makeOne(this,
							    TuneSet.constructor,
							    TuneSet.metaTunes);
    public final Edge<Tune, Syst> systs = Edge.makeMany(this,
							Syst.constructor,
							Syst.metaTune);
    public final Edge<Tune, Sect> sects = Edge.makeMany(this,
							Sect.constructor,
							Sect.metaTune);

    public final Property<Long> id = Property.makeLong();
    public final Property<String> name = Property.makeString();
    public final Property<Date> lastModified = Property.makeDate();
}
