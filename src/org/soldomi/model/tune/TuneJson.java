package org.soldomi.model.tune;

import org.soldomi.commons.JsonNodeReader;
import org.soldomi.commons.JsonNodeWriter;

public class TuneJson {
    public static final JsonNodeReader<Tune> readTune = new JsonNodeReader<Tune>()
	.map("id", Tune.metaId)
	.map("name", Tune.metaName)
	.map("last_modified", Tune.metaLastModified)
	.chain("systs", Tune.metaSysts,
	       new JsonNodeReader<Syst>()
	       .map("id", Syst.metaId)
	       .map("name", Syst.metaName)
	       .chain("staves", Syst.metaStaves,
		      new JsonNodeReader<Staff>()
		      .map("id", Staff.metaId)
		      .map("name", Staff.metaName)
		      )
	       )
	.chain("sects", Tune.metaSects,
	       new JsonNodeReader<Sect>()
	       .map("id", Sect.metaId)
	       .map("start_time", Sect.metaStartTime)
	       .chain("blocks", Sect.metaBlocks,
		      new JsonNodeReader<Block>()
		      .map("id", Block.metaId)
		      .map("start_time", Block.metaStartTime)
		      )
	       );

    public static final JsonNodeWriter<Tune> writeTune = new JsonNodeWriter<Tune>()
	.map("id", Tune.metaId)
	.map("name", Tune.metaName)
	.map("last_modified", Tune.metaLastModified)
	.chain("systs", Tune.metaSysts,
	       new JsonNodeWriter<Syst>()
	       .map("id", Syst.metaId)
	       .map("name", Syst.metaName)
	       .chain("staves", Syst.metaStaves,
		      new JsonNodeWriter<Staff>()
		      .map("id", Staff.metaId)
		      .map("name", Staff.metaName)
		      )
	       )
	.chain("sects", Tune.metaSects,
	       new JsonNodeWriter<Sect>()
	       .map("id", Sect.metaId)
	       .map("start_time", Sect.metaStartTime)
	       .chain("blocks", Sect.metaBlocks,
		      new JsonNodeWriter<Block>()
		      .map("id", Block.metaId)
		      .map("start_time", Block.metaStartTime)
		      )
	       );
}
