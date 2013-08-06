package org.soldomi.model.tune;

import org.soldomi.commons.Function;
import org.soldomi.commons.Edge;
import org.soldomi.commons.Property;

public class Block {

    public static final Function<Void, Block> constructor = new Function<Void, Block>() {
	@Override public Block apply(Void value) { return new Block(); }
    };

    public static final Function<Block, Edge<Block, Sect>> metaSect = new Function<Block, Edge<Block, Sect>>() {
	@Override public Edge<Block, Sect> apply(Block block) { return block.sect; }
    };

    public static final Function<Block, Edge<Block, Symbol>> metaSymbols = new Function<Block, Edge<Block, Symbol>>() {
	@Override public Edge<Block, Symbol> apply(Block block) { return block.symbols; }
    };

    public static final Function<Block, Property<Long>> metaId = new Function<Block, Property<Long>>() {
	@Override public Property<Long> apply(Block block) { return block.id; }
    };

    public static final Function<Block, Property<Long>> metaStartTime = new Function<Block, Property<Long>>() {
	@Override public Property<Long> apply(Block block) { return block.startTime; }
    };

    public final Edge<Block, Sect> sect = Edge.makeOne(this,
						       Sect.constructor,
						       Sect.metaBlocks);
    public final Edge<Block, Symbol> symbols = Edge.makeMany(this,
							     Symbol.constructor,
							     Symbol.metaBlock);
    public final Property<Long> id = Property.makeLong();
    public final Property<Long> startTime = Property.makeLong();
}
