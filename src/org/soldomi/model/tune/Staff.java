package org.soldomi.model.tune;

import org.soldomi.commons.Property;
import org.soldomi.commons.SingleRelationship;
import org.soldomi.commons.MultipleRelationship;

public class Staff {
    public final Property<Long> id = new Property<Long>();
    public final SingleRelationship<Staff, Syst> syst = new SingleRelationship<Staff, Syst>(this);
    public final MultipleRelationship<Staff, Symbol> symbols = new MultipleRelationship<Staff, Symbol>(this);
    public final Property<String> name = new Property<String>();
}
