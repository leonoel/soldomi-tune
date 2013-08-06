package org.soldomi.model.tune;

import org.soldomi.commons.Function;
import org.soldomi.commons.Edge;
import org.soldomi.commons.Property;

public class Sect {

    public static final Function<Void, Sect> constructor = new Function<Void, Sect>() {
	@Override public Sect apply(Void value) { return new Sect(); }
    };

    public static final Function<Sect, Edge<Sect, Tune>> metaTune = new Function<Sect, Edge<Sect, Tune>>() {
	@Override public Edge<Sect, Tune> apply(Sect sect) { return sect.tune; }
    };

    public static final Function<Sect, Edge<Sect, Block>> metaBlocks = new Function<Sect, Edge<Sect, Block>>() {
	@Override public Edge<Sect, Block> apply(Sect sect) { return sect.blocks; }
    };

    public static final Function<Sect, Property<Long>> metaId = new Function<Sect, Property<Long>>() {
	@Override public Property<Long> apply(Sect sect) { return sect.id; }
    };

    public static final Function<Sect, Property<Long>> metaStartTime = new Function<Sect, Property<Long>>() {
	@Override public Property<Long> apply(Sect sect) { return sect.startTime; }
    };

    public final Edge<Sect, Tune> tune = Edge.makeOne(this,
						      Tune.constructor,
						      Tune.metaSects);
    public final Edge<Sect, Block> blocks = Edge.makeMany(this,
							  Block.constructor,
							  Block.metaSect);
    public final Property<Long> id = Property.makeLong();
    public final Property<Long> startTime = Property.makeLong();
}

