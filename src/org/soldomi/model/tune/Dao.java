package org.soldomi.model.tune;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.soldomi.commons.EnumBaseMapper;
import org.soldomi.commons.DaoAction;
import org.soldomi.commons.DaoAction.DaoException;

import org.apache.commons.math3.fraction.Fraction;


public interface Dao {
    public static class BlockStaff {
	public final Block block;
	public final Staff staff;
	public BlockStaff(Block b, Staff s) {
	    block = b;
	    staff = s;
	}
    }

    public static final EnumBaseMapper<NoteName> NoteNameMapper = new EnumBaseMapper<NoteName>(NoteName.class);
    public static final EnumBaseMapper<NoteValue> NoteValueMapper = new EnumBaseMapper<NoteValue>(NoteValue.class);
    public static final EnumBaseMapper<NotePitch> NotePitchMapper = new EnumBaseMapper<NotePitch>(NotePitch.class);
    public static final EnumBaseMapper<Accidental> AccidentalMapper = new EnumBaseMapper<Accidental>(Accidental.class);
    public static final EnumBaseMapper<SymbolType> SymbolTypeMapper = new EnumBaseMapper<SymbolType>(SymbolType.class);

    public static final DaoAction<List<Tune>> getAll = new DaoAction<List<Tune>>() {
	private final String sql = "select id, name, last_modified from tune";

	@Override public void query(Connection connection, List<Tune> tuneList) throws SQLException {
	    ResultSet resultSet = connection.createStatement().executeQuery(sql);
	    while(resultSet.next()) {
		Tune tune = new Tune();
		tune.id.set(resultSet.getLong("id"));
		tune.name.set(resultSet.getString("name"));
		tune.lastModified.set(resultSet.getDate("last_modified"));
		tuneList.add(tune);
	    }
	}
    };


    public static final DaoAction<Tune> insertTune = new DaoAction<Tune>() {
	private final String sql = "insert into tune values (null, ?, ?)";

	@Override public void query(Connection connection, Tune tune) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setString(1, tune.name.get());
	    stat.setDate(2, new java.sql.Date(tune.lastModified.get().getTime()));
	    stat.executeUpdate();
	    ResultSet resultSet = stat.getGeneratedKeys();
	    if(!resultSet.next()) {
		throw new SQLException("Could not retrieve new tune id.");
	    }
	    tune.id.set(resultSet.getLong(1));
	    for (Syst syst : tune.systs.group()) {
		insertSyst.query(connection, syst);
	    }
	    for (Sect sect : tune.sects.group()) {
		insertSect.query(connection, sect);
	    }
	}
    };

    public static final DaoAction<Tune> getTune = new DaoAction<Tune>() {
	private final String sql = 
	"select t.name as tune_name, " +
	"t.last_modified as tune_last_modified " +
	"from tune as t " +
	"where t.id = ? ";

	@Override public void query(Connection connection, Tune tune) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, tune.id.get());
	    ResultSet resultSet = stat.executeQuery();
	    if(!resultSet.next()) {
		throw new SQLException("Tune id not found");
	    }

	    tune.name.set(resultSet.getString("tune_name"));
	    tune.lastModified.set(resultSet.getDate("tune_last_modified"));
	    getAllSystsInTune.query(connection, tune);
	    getAllSectsInTune.query(connection, tune);
	}
    };

    public static final DaoAction<Tune> deleteTune = new DaoAction<Tune>() {
	private final String sql =
	"delete from tune " +
	"where id = ?";

	@Override public void query(Connection connection, Tune tune) throws SQLException {
	    deleteAllSectsInTune.query(connection, tune);
	    deleteAllSystsInTune.query(connection, tune);
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, tune.id.get());
	    stat.executeUpdate();
	}
    };

    public static final DaoAction<Syst> insertSyst = new DaoAction<Syst>() {
	private final String sql = "insert into syst values (null, ?, ?)";

	@Override public void query(Connection connection,
				    Syst syst) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, syst.tune.target().id.get());
	    stat.setString(2, syst.name.get());
	    stat.executeUpdate();
	    ResultSet resultSet = stat.getGeneratedKeys();
	    if (!resultSet.next()) {
		throw new SQLException("Could not retrieve new syst id");
	    }
	    syst.id.set(resultSet.getLong(1));
	    for (Staff staff : syst.staves.group()) {
		insertStaff.query(connection, staff);
	    }
	}
    };

    public static final DaoAction<Tune> getAllSystsInTune = new DaoAction<Tune>() {
	private final String sql = 
	"select id, " +
	"name " +
	"from syst " +
	"where tune_id = ? ";

	@Override public void query(Connection connection,
				    Tune tune) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, tune.id.get());
	    ResultSet resultSet = stat.executeQuery();
		    
	    while (resultSet.next()) {
		Syst syst = new Syst();
		syst.id.set(resultSet.getLong("id"));
		syst.name.set(resultSet.getString("name"));
		syst.tune.linkTo(tune.systs);
		getAllStavesInSyst.query(connection, syst);
	    }
	}
    };

    public static final DaoAction<Tune> deleteAllSystsInTune = new DaoAction<Tune>() {
	private final String sql = 
	"delete from syst " +
	"where tune_id = ?";

	@Override public void query(Connection connection,
				    Tune tune) throws SQLException {
	    deleteAllStavesInTune.query(connection, tune);
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, tune.id.get());
	    stat.executeUpdate();
	}
    };


    public static final DaoAction<Staff> insertStaff = new DaoAction<Staff>() {
	private final String sql = "insert into staff values (null, ?, ?)";

	@Override public void query(Connection connection,
				    Staff staff) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, staff.syst.target().id.get());
	    stat.setString(2, staff.name.get());
	    stat.executeUpdate();
	    ResultSet resultSet = stat.getGeneratedKeys();
	    if (!resultSet.next()) {
		throw new SQLException("Could not retrieve new staff id");
	    }
	    staff.id.set(resultSet.getLong(1));
	    for (Symbol symbol : staff.symbols.group()) {
		if (symbol.block.target().id.get() != null) {
		    insertSymbol.query(connection, symbol);
		}
	    }
	}
    };

    public static final DaoAction<Syst> getAllStavesInSyst = new DaoAction<Syst>() {
	private final String sql =
	"select id, " +
	"name " +
	"from staff " +
	"where syst_id = ? ";

	@Override public void query(Connection connection,
				    Syst syst) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, syst.id.get());
	    ResultSet resultSet = stat.executeQuery();

	    while (resultSet.next()) {
		Staff staff = new Staff();
		staff.id.set(resultSet.getLong("id"));
		staff.name.set(resultSet.getString("name"));
		staff.syst.linkTo(syst.staves);
	    }
	}
    };

    public static final DaoAction<Tune> deleteAllStavesInTune = new DaoAction<Tune>() {
	private final String sql =
	"delete from staff " +
	"where exists ( " +
	"select * from syst " +
	"where staff.syst_id = syst.id " +
	"and syst.tune_id = ?)";

	@Override public void query(Connection connection,
				    Tune tune) throws SQLException {
	    deleteAllSymbolsInTune.query(connection, tune);
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, tune.id.get());
	    stat.executeUpdate();
	}
    };

    public static final DaoAction<Sect> insertSect = new DaoAction<Sect>() {
	private final String sql = "insert into sect values (null, ?, ?)";

	@Override public void query(Connection connection,
				    Sect sect) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, sect.tune.target().id.get());
	    stat.setLong(2, sect.startTime.get());
	    stat.executeUpdate();
	    ResultSet resultSet = stat.getGeneratedKeys();
	    if (!resultSet.next()) {
		throw new SQLException("Could not retrieve new sect id");
	    }
	    sect.id.set(resultSet.getLong(1));
	    for (Block block : sect.blocks.group()) {
		insertBlock.query(connection, block);
	    }
	}
    };
    
    public static final DaoAction<Tune> getAllSectsInTune = new DaoAction<Tune>() {
	private final String sql =
	"select id, " +
	"start_time " +
	"from sect " +
	"where tune_id = ? ";

	@Override public void query(Connection connection,
				    Tune tune) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, tune.id.get());
	    ResultSet resultSet = stat.executeQuery();
		    
	    while (resultSet.next()) {
		Sect sect = new Sect();
		sect.id.set(resultSet.getLong("id"));
		sect.tune.linkTo(tune.sects);
		sect.startTime.set(resultSet.getLong("start_time"));
		getAllBlocksInSect.query(connection, sect);
	    }
	}
    };
    
    public static final DaoAction<Tune> deleteAllSectsInTune = new DaoAction<Tune>() {
	private final String sql =
	"delete from sect " +
	"where tune_id = ?";
	
	@Override public void query(Connection connection,
				    Tune tune) throws SQLException {
	    deleteAllBlocksInTune.query(connection, tune);
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, tune.id.get());
	    stat.executeUpdate();
	}
    };

    public static final DaoAction<Block> insertBlock = new DaoAction<Block>() {
	private final String sql = "insert into block values (null, ?, ?)";

	@Override public void query(Connection connection,
				    Block block) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, block.sect.target().id.get());
	    stat.setLong(2, block.startTime.get());
	    stat.executeUpdate();
	    ResultSet resultSet = stat.getGeneratedKeys();
	    if (!resultSet.next()) {
		throw new SQLException("Could not retrieve new block id");
	    }
	    block.id.set(resultSet.getLong(1));
	    for (Symbol symbol : block.symbols.group()) {
		if (symbol.staff.target().id.get() != null) {
		    insertSymbol.query(connection, symbol);
		}
	    }
	}
    };

    public static final DaoAction<Sect> getAllBlocksInSect = new DaoAction<Sect>() {
	private final String sql = 
	"select id, " +
	"start_time " +
	"from block " +
	"where sect_id = ? ";

	@Override public void query(Connection connection,
				    Sect sect) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, sect.id.get());
	    ResultSet resultSet = stat.executeQuery();
		    
	    while (resultSet.next()) {
		Block block = new Block();
		block.id.set(resultSet.getLong("id"));
		block.startTime.set(resultSet.getLong("start_time"));
		block.sect.linkTo(sect.blocks);
	    }
	}
    };

    public static final DaoAction<Tune> deleteAllBlocksInTune = new DaoAction<Tune>() {
	private final String sql = 
	"delete from block " +
	"where exists ( " +
	"select * from sect " +
	"where block.sect_id = sect.id " +
	"and sect.tune_id = ?)";

	@Override public void query(Connection connection,
				    Tune tune) throws SQLException {
	    deleteAllSymbolsInTune.query(connection, tune);
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, tune.id.get());
	    stat.executeUpdate();
	}
    };

    public static final DaoAction<BlockStaff> getAllSymbolsInBlockStaff = new DaoAction<BlockStaff>() {
	private final String sql =
	"select id, start_time_n, start_time_d, symbol_type " +
	"from symbol " +
	"where staff_id = ? " +
	"and block_id = ? ";

	@Override public void query(Connection connection, BlockStaff blockStaff) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, blockStaff.staff.id.get());
	    stat.setLong(2, blockStaff.block.id.get());
	    ResultSet resultSet = stat.executeQuery();
	    while(resultSet.next()) {
		Symbol symbol = new Symbol();
		symbol.id.set(resultSet.getLong("id"));
		symbol.block.linkTo(blockStaff.block.symbols);
		symbol.staff.linkTo(blockStaff.staff.symbols);
		symbol.startTime.set(new Fraction(resultSet.getInt("start_time_n"), resultSet.getInt("start_time_d")));
		symbol.type.set(SymbolTypeMapper.fromBase(resultSet.getString("symbol_type")));

		if (symbol.type.get().role.isSegment) {
		    getSegmentFromSymbol.query(connection, symbol);
		} else if (SymbolType.STANDARD_TIME_SIGNATURE == symbol.type.get()) {
		    getTimeSignatureFromSymbol.query(connection, symbol);
		} else if (SymbolType.KEY_SIGNATURE == symbol.type.get()) {
		    getKeySignatureFromSymbol.query(connection, symbol);
		}
	    }
	}
    };

    public static final DaoAction<Symbol> insertSymbol = new DaoAction<Symbol>() {
	private final String sql =
	"insert into symbol (block_id, staff_id, start_time_n, start_time_d, symbol_type) " +
	"values (?, ?, ?, ?, ?)";

	@Override public void query(Connection connection, Symbol symbol) throws SQLException {
	    
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, symbol.block.target().id.get());
	    stat.setLong(2, symbol.staff.target().id.get());
	    stat.setLong(3, symbol.startTime.get().getNumerator());
	    stat.setLong(4, symbol.startTime.get().getDenominator());
	    stat.setString(5, SymbolTypeMapper.toBase(symbol.type.get()));
	    stat.executeUpdate();
	    ResultSet resultSet = stat.getGeneratedKeys();
	    if(!resultSet.next()) {
		throw new SQLException("Could not retrieve new symbol id.");
	    }
	    symbol.id.set(resultSet.getLong(1));
	    if (symbol.segment.target() != null) {
		insertSegment.query(connection, symbol.segment.target());
	    }
	    if (symbol.timeSignature.target() != null) {
		insertTimeSignature.query(connection, symbol.timeSignature.target());
	    }
	    if (symbol.keySignature.target() != null) {
		insertKeySignature.query(connection, symbol.keySignature.target());
	    }
	}
    };

    public static final DaoAction<Tune> deleteAllSymbolsInTune = new DaoAction<Tune>() {
	private final String sql = "delete from symbol " +
	"where exists ( " +
	"select * from staff " +
	"join syst on syst.id = staff.syst_id " +
	"where syst.tune_id = ?)" +
	"or exists ( " +
	"select * from block " +
	"join sect on sect.id = block.sect_id " +
	"where sect.tune_id = ?)";

	@Override public void query(Connection connection, Tune tune) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, tune.id.get());
	    stat.setLong(2, tune.id.get());
	    stat.executeUpdate();
	}
    };

    public static final DaoAction<TimeSignature> insertTimeSignature = new DaoAction<TimeSignature>() {
	private final String sql =
	"insert into time_signature (symbol_id, " +
	"beat_count, " +
	"beat_value ) " +
	"values (?, " +
	"?, " +
	"?) ";

	@Override public void query(Connection connection, TimeSignature timeSignature) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, timeSignature.symbol.target().id.get());
	    stat.setInt(2, timeSignature.beatCount.get());
	    stat.setString(3, NoteValueMapper.toBase(timeSignature.beatValue.get()));
	    stat.executeUpdate();
	    ResultSet resultSet = stat.getGeneratedKeys();
	    if(!resultSet.next()) {
		throw new SQLException("Could not retrieve new timeSignature id.");
	    }
	    timeSignature.id.set(resultSet.getLong(1));
	}
    };

    public static final DaoAction<Symbol> getTimeSignatureFromSymbol = new DaoAction<Symbol>() {
	private final String sql = 
	"select id, beat_count, beat_value " +
	"from time_signature " +
	"where symbol_id = ? ";

	@Override public void query(Connection connection, Symbol symbol) throws SQLException {
	    TimeSignature timeSignature = new TimeSignature();
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, symbol.id.get());
	    ResultSet resultSet = stat.executeQuery();
	    if(!resultSet.next()) {
		throw new SQLException("Could not retrieve timeSignature");
	    }
	    timeSignature.id.set(resultSet.getLong("id"));
	    timeSignature.symbol.linkTo(symbol.timeSignature);
	    timeSignature.beatCount.set(resultSet.getInt("beat_count"));
	    timeSignature.beatValue.set(NoteValueMapper.fromBase(resultSet.getString("beat_value")));
	}
    };

    public static final DaoAction<KeySignature> insertKeySignature = new DaoAction<KeySignature>() {
	private final String sql =
	"insert into key_signature (symbol_id, " +
	"a, " +
	"b, " +
	"c, " +
	"d, " +
	"e, " +
	"f, " +
	"g ) " +
	"values (?, " +
	"?, " +
	"?, " +
	"?, " +
	"?, " +
	"?, " +
	"?, " +
	"?) ";

	@Override public void query(Connection connection, KeySignature keySignature) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, keySignature.symbol.target().id.get());
	    stat.setString(2, NotePitchMapper.toBase(keySignature.a.get()));
	    stat.setString(3, NotePitchMapper.toBase(keySignature.b.get()));
	    stat.setString(4, NotePitchMapper.toBase(keySignature.c.get()));
	    stat.setString(5, NotePitchMapper.toBase(keySignature.d.get()));
	    stat.setString(6, NotePitchMapper.toBase(keySignature.e.get()));
	    stat.setString(7, NotePitchMapper.toBase(keySignature.f.get()));
	    stat.setString(8, NotePitchMapper.toBase(keySignature.g.get()));
	    stat.executeUpdate();
	    ResultSet resultSet = stat.getGeneratedKeys();
	    if(!resultSet.next()) {
		throw new SQLException("Could not retrieve new keySignature id.");
	    }
	    keySignature.id.set(resultSet.getLong(1));
	}
    };

    public static final DaoAction<Symbol> getKeySignatureFromSymbol = new DaoAction<Symbol>() {
	private final String sql =
	"select id, " +
	"a, " +
	"b, " +
	"c, " +
	"d, " +
	"e, " +
	"f, " +
	"g " +
	"from key_signature " +
	"where symbol_id = ? ";

	@Override public void query(Connection connection, Symbol symbol) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, symbol.id.get());
	    ResultSet resultSet = stat.executeQuery();
	    if(!resultSet.next()) {
		throw new SQLException("Could not retrieve keySignature");
	    }
	    KeySignature keySignature = new KeySignature();
	    keySignature.id.set(resultSet.getLong("id"));
	    keySignature.symbol.linkTo(symbol.keySignature);
	    keySignature.a.set(NotePitchMapper.fromBase(resultSet.getString("a")));
	    keySignature.b.set(NotePitchMapper.fromBase(resultSet.getString("b")));
	    keySignature.c.set(NotePitchMapper.fromBase(resultSet.getString("c")));
	    keySignature.d.set(NotePitchMapper.fromBase(resultSet.getString("d")));
	    keySignature.e.set(NotePitchMapper.fromBase(resultSet.getString("e")));
	    keySignature.f.set(NotePitchMapper.fromBase(resultSet.getString("f")));
	    keySignature.g.set(NotePitchMapper.fromBase(resultSet.getString("g")));
	}
    };

    public static final DaoAction<Segment> insertSegment = new DaoAction<Segment>() {
	private final String sql = 
	"insert into segment (symbol_id, " +
	"duration_n, " +
	"duration_d, " +
	"dot_count, " +
	"tuplet_id ) " +
	"values (?, " +
	"?, " +
	"?, " +
	"?, " +
	"?) ";

	@Override public void query(Connection connection, Segment segment) throws SQLException {
	    if (segment.tuplet.target() != null && segment.tuplet.target().id.get() == null) {
		insertTuplet.query(connection, segment.tuplet.target());
	    }
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, segment.symbol.target().id.get());
	    stat.setLong(2, segment.duration.get().getNumerator());
	    stat.setLong(3, segment.duration.get().getDenominator());
	    stat.setInt(4, segment.dotCount.get());
	    stat.setLong(5, segment.tuplet.target() == null ? 0L : segment.tuplet.target().id.get());
	    stat.executeUpdate();
	    ResultSet resultSet = stat.getGeneratedKeys();
	    if(!resultSet.next()) {
		throw new SQLException("Could not retrieve new segment id.");
	    }
	    segment.id.set(resultSet.getLong(1));
	    if (segment.note.target() != null) {
		insertNote.query(connection, segment.note.target());
	    }
	}
    };

    public static final DaoAction<Symbol> getSegmentFromSymbol = new DaoAction<Symbol>() {
	private final String sql = 
	"select id, duration_n, duration_d, dot_count " +
	"from segment " +
	"where symbol_id = ? ";

	@Override public void query(Connection connection, Symbol symbol) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, symbol.id.get());
	    ResultSet resultSet = stat.executeQuery();
	    if(!resultSet.next()) {
		throw new SQLException("Could not retrieve segment info");
	    }
	    Segment segment = new Segment();
	    segment.id.set(resultSet.getLong("id"));
	    segment.symbol.linkTo(symbol.segment);
	    segment.duration.set(new Fraction(resultSet.getInt("duration_n"), resultSet.getInt("duration_d")));
	    segment.dotCount.set(resultSet.getInt("dot_count"));

	    if (symbol.type.get().role == SymbolRole.NOTE) {
		getNoteFromSegment.query(connection, segment);
	    }
	}
    };


    public static final DaoAction<Note> insertNote = new DaoAction<Note>() {
	private final String sql = 
	"insert into note (segment_id, " +
	"note_name, " +
	"octave, " +
	"accidental ) " +
	"values (?, " +
	"?, " +
	"?, " +
	"?) ";

	@Override public void query(Connection connection, Note note) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, note.segment.target().id.get());
	    stat.setString(2, NoteNameMapper.toBase(note.pitch.get().noteName));
	    stat.setInt(3, note.pitch.get().octave);
	    stat.setString(4, AccidentalMapper.toBase(note.accidental.get()));
	    stat.executeUpdate();
	    ResultSet resultSet = stat.getGeneratedKeys();
	    if(!resultSet.next()) {
		throw new SQLException("Could not retrieve new note id.");
	    }
	    note.id.set(resultSet.getLong(1));
	}
    };

    public static final DaoAction<Segment> getNoteFromSegment = new DaoAction<Segment>() {
	private final String sql =
	"select id, note_name, octave, accidental " +
	"from note " +
	"where segment_id = ? ";
	
	@Override public void query(Connection connection, final Segment segment) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, segment.id.get());
	    ResultSet resultSet = stat.executeQuery();
	    if(!resultSet.next()) {
		throw new SQLException("Could not retrieve note info");
	    }
	    Note note = new Note();
	    note.id.set(resultSet.getLong("id"));
	    note.segment.linkTo(segment.note);
	    note.pitch.set(new Pitch(NoteNameMapper.fromBase(resultSet.getString("note_name")),
				     resultSet.getInt("octave")));
	    note.accidental.set(AccidentalMapper.fromBase(resultSet.getString("accidental")));
	}
    };

    public static final DaoAction<Tuplet> insertTuplet = new DaoAction<Tuplet>() {
	private final String sql =
	"insert into tuplet (duration) " +
	"values (?) ";

	@Override public void query(Connection connection, Tuplet tuplet) throws SQLException {
	    PreparedStatement stat = connection.prepareStatement(sql);
	    stat.setLong(1, tuplet.duration.get());
	    stat.executeUpdate();
	    ResultSet resultSet = stat.getGeneratedKeys();
	    if(!resultSet.next()) {
		throw new SQLException("Could not retrieve new tuplet id.");
	    }
	    tuplet.id.set(resultSet.getLong(1));
	}
    };

}
