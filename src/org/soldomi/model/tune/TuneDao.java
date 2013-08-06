package org.soldomi.model.tune;

import org.soldomi.commons.DaoAction;
import org.soldomi.commons.DaoLink;
import org.soldomi.commons.Edge;
import org.soldomi.commons.SqlRequest;

import java.util.Arrays;
import java.util.List;

public interface TuneDao {
    public static final DaoAction<TuneSet> getAllTunes = 
	SqlRequest.select("select id, name, last_modified " +
			  "from tune ",
			  TuneSet.metaTunes)
	.addOutput(Tune.metaId)
	.addOutput(Tune.metaName)
	.addOutput(Tune.metaLastModified);

    public static final DaoAction<Tune> getTune = 
	SqlRequest.select("select name, last_modified " +
			  "from tune " +
			  "where id = ? ",
			  Edge.<Tune> loop())
	.addInput(Tune.metaId)
	.addOutput(Tune.metaName)
	.addOutput(Tune.metaLastModified);

    public static final DaoAction<Tune> getTuneSysts =
	SqlRequest.select("select id, name " +
			  "from syst " +
			  "where tune_id = ? ",
			  Tune.metaSysts)
	.addInput(Tune.metaId)
	.addOutput(Syst.metaId)
	.addOutput(Syst.metaName);

    public static final DaoAction<Syst> getSystStaves =
	SqlRequest.select("select id, name " +
			  "from staff " +
			  "where syst_id = ? ",
			  Syst.metaStaves)
	.addInput(Syst.metaId)
	.addOutput(Staff.metaId)
	.addOutput(Staff.metaName);

    public static final DaoAction<Tune> getTuneSects = 
	SqlRequest.select("select id, start_time " +
			  "from sect " +
			  "where tune_id = ? ",
			  Tune.metaSects)
	.addInput(Tune.metaId)
	.addOutput(Sect.metaId)
	.addOutput(Sect.metaStartTime);

    public static final DaoAction<Sect> getSectBlocks =
	SqlRequest.select("select id, start_time " +
			  "from block " +
			  "where sect_id = ? ",
			  Sect.metaBlocks)
	.addInput(Sect.metaId)
	.addOutput(Block.metaId)
	.addOutput(Block.metaStartTime);

    public static final DaoAction<Tune> insertTune =
	SqlRequest.insert("insert into tune (name, last_modified) values (?, ?)",
			  Edge.<Tune> loop())
	.addInput(Tune.metaName)
	.addInput(Tune.metaLastModified)
	.addOutput(Tune.metaId);

    public static final DaoAction<Syst> insertSyst =
	SqlRequest.insert("insert into syst (tune_id, name) values (?, ?)",
			  Edge.<Syst> loop())
	.addInput(Edge.alias(Syst.metaTune, Tune.metaId))
	.addInput(Syst.metaName)
	.addOutput(Syst.metaId);

    public static final DaoAction<Staff> insertStaff =
	SqlRequest.insert("insert into staff (syst_id, name) values (?, ?)",
			  Edge.<Staff> loop())
	.addInput(Edge.alias(Staff.metaSyst, Syst.metaId))
	.addInput(Staff.metaName)
	.addOutput(Staff.metaId);

    public static final DaoAction<Sect> insertSect =
	SqlRequest.insert("insert into sect  (tune_id, start_time) values (?, ?)",
			  Edge.<Sect> loop())
	.addInput(Edge.alias(Sect.metaTune, Tune.metaId))
	.addInput(Sect.metaStartTime)
	.addOutput(Sect.metaId);

    public static final DaoAction<Block> insertBlock =
	SqlRequest.insert("insert into block (sect_id, start_time) values (?, ?)",
			  Edge.<Block> loop())
	.addInput(Edge.alias(Block.metaSect, Sect.metaId))
	.addInput(Block.metaStartTime)
	.addOutput(Block.metaId);

    public static final DaoAction<Tune> getTuneSystsWithStaves =
	getTuneSysts.chain(new DaoLink<Tune, Syst>(getSystStaves, Tune.metaSysts));

    public static final DaoAction<Tune> getTuneSectsWithBlocks =
	getTuneSects.chain(new DaoLink<Tune, Sect>(getSectBlocks, Tune.metaSects));

    public static final DaoAction<Tune> getTuneWithStaffsAndBlocks = 
	getTune
	.chain(new DaoLink<Tune, Tune>(getTuneSystsWithStaves, Edge.<Tune> loop()))
	.chain(new DaoLink<Tune, Tune>(getTuneSectsWithBlocks, Edge.<Tune> loop()));
		      
    public static final DaoAction<Sect> insertSectWithBlocks =
	insertSect.chain(new DaoLink<Sect, Block>(insertBlock, Sect.metaBlocks));

    public static final DaoAction<Syst> insertSystWithStaves =
	insertSyst.chain(new DaoLink<Syst, Staff>(insertStaff, Syst.metaStaves));

    public static final DaoAction<Tune> insertTuneWithStavesAndBlocks =
	insertTune
	.chain(new DaoLink<Tune, Sect>(insertSectWithBlocks, Tune.metaSects))
	.chain(new DaoLink<Tune, Syst>(insertSystWithStaves, Tune.metaSysts));
}
