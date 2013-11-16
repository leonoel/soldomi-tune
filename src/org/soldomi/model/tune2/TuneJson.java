package org.soldomi.model.tune2;

import org.soldomi.commons2.JsonWriter;
import com.fasterxml.jackson.databind.JsonNode;


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
				   @Override public JsonNode write(Sect sect) {
				       return object(property("id", sect.id),
						     property("startTime", sect.startTime),
						     property("blocks", array(sect.blocks, new JsonWriter<Block>() {
								 @Override public JsonNode write(Block block) {
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
}
