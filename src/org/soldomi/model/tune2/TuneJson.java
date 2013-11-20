package org.soldomi.model.tune2;

import org.soldomi.commons2.JsonWriter;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;

public class TuneJson {
    public static final JsonWriter<Tune> tuneWithSystsAndSects = new JsonWriter<Tune>() {
	@Override public JsonNode write(Tune tune) {
	    return object(property("id", tune.id),
		       property("name", tune.name),
		       property("systs", array(tune.systs, new JsonWriter<Syst>() {
				   @Override public JsonNode write(Syst syst) {
				       return object(property("id", syst.id),
						     property("name", syst.name),
						     property("staves", array(syst.staves, new JsonWriter<Staff>() {
								 @Override public JsonNode write(Staff staff) {
								     return object(property("id", staff.id),
										   property("name", staff.name)
										   );
								 }
							     }))
						     );
				   }
			       })),
		       property("sects", array(tune.sects, new JsonWriter<Sect>() {
				   public JsonNode write(Sect sect) {
				       return object(property("id", sect.id),
						     property("startTime", sect.startTime),
						     property("blocks", array(sect.blocks, new JsonWriter<Block>() {
								 public JsonNode write(Block block) {
								     return object(property("id", block.id),
										   property("name", block.startTime)
										   );
								 }
							     }))
						     );
				   }
			       }))
		       );
	}
    };

    public static final JsonWriter<List<Symbol>> symbols = new JsonWriter<List<Symbol>>() {
	public JsonNode write(List<Symbol> symbols) {
	    return array(symbols, new JsonWriter<Symbol>() {
		    public JsonNode write(final Symbol symbol) {
			return object(new ArrayList<JsonWriter.Property>() {{
				add(property("id", symbol.id));
				add(property("staffId", symbol.staffId));
				add(property("blockId", symbol.blockId));
				add(property("startTime", object(property("n", symbol.startTime.getNumerator()),
								 property("d", symbol.startTime.getDenominator()))));
				add(property("type", symbol.symbolType.name()));
				switch(symbol.symbolType.role) {
				case KEY_SIGNATURE:
				    add(property("keySignature", object(property("id", symbol.keySignature.id),
									property("a", symbol.keySignature.a.name()),
									property("b", symbol.keySignature.b.name()),
									property("c", symbol.keySignature.c.name()),
									property("d", symbol.keySignature.d.name()),
									property("e", symbol.keySignature.e.name()),
									property("f", symbol.keySignature.f.name()),
									property("g", symbol.keySignature.g.name())
									)
						 )
					);
				    break;
				case TIME_SIGNATURE:
				    add(property("timeSignature", object(property("id", symbol.timeSignature.id),
									 property("beatCount", symbol.timeSignature.beatCount),
									 property("beatValue", symbol.timeSignature.beatValue.name())
									 )
						 )
					);
				    break;
				case REST:
				    add(property("rest", object(property("segmentId", symbol.segment.id),
								   property("duration", object(property("n", symbol.segment.duration.getNumerator()),
											       property("d", symbol.segment.duration.getDenominator())
											       )),
								   property("dotCount", symbol.segment.dotCount)
								   )
						 )
					);
				    break;
				case NOTE:
				    add(property("note", object(property("segmentId", symbol.segment.id),
								property("noteId", symbol.segment.note.id),
								property("duration", object(property("n", symbol.segment.duration.getNumerator()),
											    property("d", symbol.segment.duration.getDenominator())
											    )),
								property("dotCount", symbol.segment.dotCount),
								property("pitch", object(property("name", symbol.segment.note.pitch.noteName.name()),
											 property("octave", symbol.segment.note.pitch.octave)
											 )
									 ),
								property("accidental", symbol.segment.note.accidental.name())
								)
						 )
					);
				    break;
				default:
				    break;
				}
				}}
			    );
		    }
		});
	}
    };
}
