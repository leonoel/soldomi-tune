package org.soldomi.model.tune2;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Sect {
    public final Long id;
    public final Long tuneId;
    public final Long startTime;
    public final List<Block> blocks;

    public Sect(Long startTime) {
	this(null, null, startTime);
    }

    public Sect(Long id, Long tuneId, Long startTime) {
	this(id, tuneId, startTime, new ArrayList<Block>());
    }

    public Sect(Long id, Long tuneId, Long startTime, List<Block> blocks) {
	this.id = id;
	this.tuneId = tuneId;
	this.startTime = startTime;
	this.blocks = Collections.unmodifiableList(new ArrayList<Block>(blocks));
    }

    public Sect withId(Long id) {
	List<Block> blocks = new ArrayList<Block>();
	for (Block block : this.blocks) {
	    blocks.add(block.withSectId(id));
	}
	return new Sect(id, tuneId, startTime, blocks);
    }

    public Sect withTuneId(Long tuneId) {
	return new Sect(id, tuneId, startTime, blocks);
    }

    public Sect withBlocks(List<Block> blocks) {
	return new Sect(id, tuneId, startTime, blocks);
    }

    public Sect addBlock(Block block) {
	List<Block> blocks = new ArrayList<Block>(this.blocks);
	blocks.add(block);
	return withBlocks(blocks);
    }
}
