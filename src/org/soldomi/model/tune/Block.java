package org.soldomi.model.tune;

import org.soldomi.commons.SingleRelationship;
import org.soldomi.commons.MultipleRelationship;
import org.soldomi.commons.Property;

public class Block {
    public final Property<Long> id = new Property<Long>();
    public final SingleRelationship<Block, Sect> sect = new SingleRelationship<Block, Sect>(this);
    public final MultipleRelationship<Block, Symbol> symbols = new MultipleRelationship<Block, Symbol>(this);
    public final Property<Long> startTime = new Property<Long>();
}
