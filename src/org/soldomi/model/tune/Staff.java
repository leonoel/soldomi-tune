package org.soldomi.model.tune;

import org.soldomi.commons.Function;
import org.soldomi.commons.Edge;
import org.soldomi.commons.Property;

public class Staff {
    public static final Function<Void, Staff> constructor = new Function<Void, Staff>() {
	@Override public Staff apply(Void value) { return new Staff(); }
    };

    public static final Function<Staff, Edge<Staff, Syst>> metaSyst = new Function<Staff, Edge<Staff, Syst>>() {
	@Override public Edge<Staff, Syst> apply(Staff staff) { return staff.syst; }
    };

    public static final Function<Staff, Edge<Staff, Symbol>> metaSymbols = new Function<Staff, Edge<Staff, Symbol>>() {
	@Override public Edge<Staff, Symbol> apply(Staff staff) { return staff.symbols; }
    };

    public static final Function<Staff, Property<Long>> metaId = new Function<Staff, Property<Long>>() {
	@Override public Property<Long> apply(Staff staff) { return staff.id; }
    };

    public static final Function<Staff, Property<String>> metaName = new Function<Staff, Property<String>>() {
	@Override public Property<String> apply(Staff staff) { return staff.name; }
    };

    public final Edge<Staff, Syst> syst = Edge.makeOne(this,
						       Syst.constructor,
						       Syst.metaStaves);
    public final Edge<Staff, Symbol> symbols = Edge.makeMany(this,
							     Symbol.constructor,
							     Symbol.metaStaff);
    public final Property<Long> id = Property.makeLong();
    public final Property<String> name = Property.makeString();
}

