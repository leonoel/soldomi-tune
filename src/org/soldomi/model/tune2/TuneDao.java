package org.soldomi.model.tune2;

import org.soldomi.commons2.DaoAction;
import org.soldomi.commons2.Result;
import org.soldomi.commons2.SqlInsert;
import org.soldomi.commons2.SqlSelect;
import org.soldomi.commons2.ParameterSet;
import org.soldomi.commons2.FieldSet;
import org.soldomi.commons2.Function2;
import org.soldomi.commons2.Function1;
import org.soldomi.commons2.RowParser;
import org.soldomi.commons2.CollectionUtils;
import org.soldomi.commons2.Reducer;

import java.sql.Connection;
import java.util.List;

public class TuneDao {
    public static final DaoAction<Tune, Tune> insertTune = new SqlInsert<Tune>("INSERT INTO tune (name) VALUES (?)") {
	@Override public ParameterSet withParameterSet(Tune tune) {
	    return new ParameterSet().add(tune.name);
	}
	@Override public Tune withKey(Tune tune, Long id) {
	    return tune.withId(id);
	}
    };

    public static final DaoAction<Syst, Syst> insertSyst = new SqlInsert<Syst>("INSERT INTO syst (tune_id, name) VALUES (?, ?)") {
	@Override public ParameterSet withParameterSet(Syst syst) {
	    return new ParameterSet()
	    .add(syst.tuneId)
	    .add(syst.name);
	}
	@Override public Syst withKey(Syst syst, Long id) {
	    return syst.withId(id);
	}
    };

    public static final DaoAction<Staff, Staff> insertStaff = new SqlInsert<Staff>("INSERT INTO staff (syst_id, name) VALUES (?, ?)") {
	@Override public ParameterSet withParameterSet(Staff staff) {
	    return new ParameterSet()
	    .add(staff.systId)
	    .add(staff.name);
	}
	@Override public Staff withKey(Staff staff, Long id) {
	    return staff.withId(id);
	}
    };

    public static final DaoAction<Sect, Sect> insertSect = new SqlInsert<Sect>("INSERT INTO sect (tune_id, start_time) VALUES (?, ?)") {
	@Override public ParameterSet withParameterSet(Sect sect) {
	    return new ParameterSet()
	    .add(sect.tuneId)
	    .add(sect.startTime);
	}
	@Override public Sect withKey(Sect sect, Long id) {
	    return sect.withId(id);
	}
    };

    public static final DaoAction<Block, Block> insertBlock = new SqlInsert<Block>("INSERT INTO block (sect_id, start_time) VALUES (?, ?)") {
	@Override public ParameterSet withParameterSet(Block block) {
	    return new ParameterSet()
	    .add(block.sectId)
	    .add(block.startTime);
	}
	@Override public Block withKey(Block block, Long id) {
	    return block.withId(id);
	}
    };

    public static final DaoAction<Sect, Sect> insertSectWithBlocks = new DaoAction<Sect, Sect>() {
	@Override public Result<Sect> run(Connection connection, final Sect sect) {
	    return insertSect
	    .chain(new DaoAction<Sect, Sect>() {
		    @Override public Result<Sect> run(Connection connection, final Sect sect) {
			return DaoAction.<Block, Block> runAll(connection, sect.blocks, insertBlock)
			.map(new Function1<List<Block>, Sect>() {
				@Override public Sect apply(List<Block> blocks) {
				    return sect.withBlocks(blocks);
				}
			    });
		    }
		})
	    .run(connection, sect);
	}
    };

    public static final DaoAction<Syst, Syst> insertSystWithStaves = new DaoAction<Syst, Syst>() {
	@Override public Result<Syst> run(Connection connection, final Syst syst) {
	    return insertSyst
	    .chain(new DaoAction<Syst, Syst>() {
		    @Override public Result<Syst> run(Connection connection, final Syst syst) {
			return DaoAction.<Staff, Staff> runAll(connection, syst.staves, insertStaff)
			.map(new Function1<List<Staff>, Syst>() {
				@Override public Syst apply(List<Staff> staves) {
				    return syst.withStaves(staves);
				}
			    });
		    }
		})
	    .run(connection, syst);
	}
    };

    public static final DaoAction<Tune, Tune> insertTuneWithSystsAndSects = new DaoAction<Tune, Tune>() {
    	@Override public Result<Tune> run(Connection connection, final Tune tune) {
    	    return insertTune
	    .chain(new DaoAction<Tune, Tune>() {
		    @Override public Result<Tune> run(Connection connection, final Tune tune) {
			return DaoAction.<Syst, Syst> runAll(connection, tune.systs, insertSystWithStaves)
			.map(new Function1<List<Syst>, Tune>() {
				@Override public Tune apply(List<Syst> systs) {
				    return tune.withSysts(systs);
				}
			    });
		    }
		})
	    .chain(new DaoAction<Tune, Tune>() {
		    @Override public Result<Tune> run(Connection connection, final Tune tune) {
			return DaoAction.<Sect, Sect> runAll(connection, tune.sects, insertSectWithBlocks)
			.map(new Function1<List<Sect>, Tune>() {
				@Override public Tune apply(List<Sect> sects) {
				    return tune.withSects(sects);
				}
			    });
		    }
		})
	    .run(connection, tune);
    	}
    };

    public static final DaoAction<Long, Tune> getTune = new SqlSelect<Long, Tune>("SELECT id, name FROM tune WHERE id = ?") {
	@Override public ParameterSet withParameterSet(Long id) {
	    return new ParameterSet().add(id);
	}

	@Override public RowParser<Tune> withRowParser(Long id) {
	    return new FieldSet()
	    .addLong()
	    .addString()
	    .toRowParser(new Function2<Long, String, Tune>() {
		    @Override public Tune apply(Long id, String name) {
			return new Tune(id, name);
		    }
		});
	}
    }.single();

    public static final DaoAction<Long, List<Staff>> getSystStaves = new SqlSelect<Long, Staff>("SELECT id, name FROM staff WHERE syst_id = ?") {
	@Override public ParameterSet withParameterSet(Long systId) {
	    return new ParameterSet().add(systId);
	}

	@Override public RowParser<Staff> withRowParser(final Long systId) {
	    return new FieldSet()
	    .addLong()
	    .addString()
	    .toRowParser(new Function2<Long, String, Staff>() {
		    @Override public Staff apply(Long id, String name) {
			return new Staff(id, systId, name);
		    }
		});
	}
    };

    public static final DaoAction<Long, List<Syst>> getTuneSysts = new SqlSelect<Long, Syst>("SELECT id, name FROM syst WHERE tune_id = ?") {
	@Override public ParameterSet withParameterSet(Long tuneId) {
	    return new ParameterSet().add(tuneId);
	}

	@Override public RowParser<Syst> withRowParser(final Long tuneId) {
	    return new FieldSet()
	    .addLong()
	    .addString()
	    .toRowParser(new Function2<Long, String, Syst>() {
		    @Override public Syst apply(Long id, String name) {
			return new Syst(id, tuneId, name);
		    }
		});
	}
    };

    public static final DaoAction<Long, List<Block>> getSectBlocks = new SqlSelect<Long, Block>("SELECT id, start_time FROM block WHERE sect_id = ?") {
	@Override public ParameterSet withParameterSet(Long sectId) {
	    return new ParameterSet().add(sectId);
	}
	@Override public RowParser<Block> withRowParser(final Long sectId) {
	    return new FieldSet()
	    .addLong()
	    .addLong()
	    .toRowParser(new Function2<Long, Long, Block>() {
		    @Override public Block apply(Long id, Long startTime) {
			return new Block(id, sectId, startTime);
		    }
		});
	}
    };

    public static final DaoAction<Long, List<Sect>> getTuneSects = new SqlSelect<Long, Sect>("SELECT id, start_time FROM sect WHERE tune_id = ?") {
	@Override public ParameterSet withParameterSet(Long tuneId) {
	    return new ParameterSet().add(tuneId);
	}

	@Override public RowParser<Sect> withRowParser(final Long tuneId) {
	    return new FieldSet()
	    .addLong()
	    .addLong()
	    .toRowParser(new Function2<Long, Long, Sect>() {
		    @Override public Sect apply(Long id, Long startTime) {
			return new Sect(id, tuneId, startTime);
		    }
		});
	}
    };

    public static final DaoAction<Long, Tune> getTuneWithSystsAndSects = new DaoAction<Long, Tune>() {
	@Override public Result<Tune> run(Connection connection, Long id) {
	    return getTune
	    .chain(new DaoAction<Tune, Tune>() {
		    @Override public Result<Tune> run(Connection connection, final Tune tune) {
			return getTuneSysts
			.chain(new DaoAction<List<Syst>, List<Syst>>() {
				@Override public Result<List<Syst>> run(Connection connection, List<Syst> systs) {
				    return DaoAction.<Syst, Syst> runAll(connection, systs, new DaoAction<Syst, Syst>() {
					    @Override public Result<Syst> run(Connection connection, final Syst syst) {
						return getSystStaves
						.map(new Function1<List<Staff>, Syst>() {
							@Override public Syst apply(List<Staff> staves) {
							    return syst.withStaves(staves);
							}
						    })
						.run(connection, syst.id);
					    }
					});
				}
			    })
			.map(new Function1<List<Syst>, Tune>() {
				@Override public Tune apply(List<Syst> systs) {
				    return tune.withSysts(systs);
				}
			    })
			.run(connection, tune.id);
		    }
		})
	    .chain(new DaoAction<Tune, Tune>() {
		    @Override public Result<Tune> run(Connection connection, final Tune tune) {
			return getTuneSects
			.chain(new DaoAction<List<Sect>, List<Sect>>() {
				@Override public Result<List<Sect>> run(Connection connection, List<Sect> sects) {
				    return DaoAction.<Sect, Sect> runAll(connection, sects, new DaoAction<Sect, Sect>() {
					    @Override public Result<Sect> run(Connection connection, final Sect sect) {
						return getSectBlocks
						.map(new Function1<List<Block>, Sect>() {
							@Override public Sect apply(List<Block> blocks) {
							    return sect.withBlocks(blocks);
							}
						    })
						.run(connection, sect.id);
					    }
					});
				}
			    })
			.map(new Function1<List<Sect>, Tune>() {
				@Override public Tune apply(List<Sect> sects) {
				    return tune.withSects(sects);
				}
			    })
			.run(connection, tune.id);
		    }
		}).run(connection, id);
	}
    };

    public static final DaoAction<Void, List<Tune>> getAllTunes = new SqlSelect<Void, Tune>("SELECT id, name FROM tune") {
	@Override public ParameterSet withParameterSet(Void v) {
	    return new ParameterSet();
	}

	@Override public RowParser<Tune> withRowParser(Void v) {
	    return new FieldSet()
	    .addLong()
	    .addString()
	    .toRowParser(new Function2<Long, String, Tune>() {
		    @Override public Tune apply(Long id, String name) {
			return new Tune(id, name);
		    }
		});
	}
    };
}
