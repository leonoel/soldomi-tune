package org.soldomi.model.tune;

import org.soldomi.commons.Function;
import org.soldomi.commons.Edge;
import org.soldomi.commons.Property;

public class Syst {
    public static final Function<Void, Syst> constructor = new Function<Void, Syst>() {
	@Override public Syst apply(Void value) { return new Syst(); }
    };

    public static final Function<Syst, Edge<Syst, Tune>> metaTune = new Function<Syst, Edge<Syst, Tune>>() {
	@Override public Edge<Syst, Tune> apply(Syst syst) { return syst.tune; }
    };
    
    public static final Function<Syst, Edge<Syst, Staff>> metaStaves = new Function<Syst, Edge<Syst, Staff>>() {
	@Override public Edge<Syst, Staff> apply(Syst syst) { return syst.staves; }
    };

    public static final Function<Syst, Property<Long>> metaId = new Function<Syst, Property<Long>>() {
	@Override public Property<Long> apply(Syst syst) { return syst.id; }
    };

    public static final Function<Syst, Property<String>> metaName = new Function<Syst, Property<String>>() {
	@Override public Property<String> apply(Syst syst) { return syst.name; }
    };

    public final Edge<Syst, Tune> tune = Edge.makeOne(this,
						      Tune.constructor,
						      Tune.metaSysts);
    public final Edge<Syst, Staff> staves = Edge.makeMany(this,
							  Staff.constructor,
							  Staff.metaSyst);

    public final Property<Long> id = Property.makeLong();
    public final Property<String> name = Property.makeString();
}
