package org.soldomi.model.tune;

import org.soldomi.commons.Property;
import org.soldomi.commons.MultipleRelationship;

import java.util.Date;

public class Tune {

    public final Property<Long> id = new Property<Long>();
    public final Property<String> name = new Property<String>();
    public final Property<Date> lastModified = new Property<Date>();
    public final MultipleRelationship<Tune, Syst> systs = new MultipleRelationship<Tune, Syst>(this);
    public final MultipleRelationship<Tune, Sect> sects = new MultipleRelationship<Tune, Sect>(this);

    public Tune() {
	lastModified.set(new Date());
    }

    @Override
    public String toString() {
	StringBuilder b = new StringBuilder();
	b.append("Tune (");
	b.append(id.get() == null ? "undefined id" : "id : " + id.get());
	b.append(", ");
	b.append("name : ");
	b.append(name.get());
	b.append(", ");
	b.append("lastModified : ");
	b.append(lastModified.get().toString());
	b.append(", ");
	b.append(systs.group().size());
	b.append(" systs");
	b.append(", ");
	b.append(sects.group().size());
	b.append(" sects");
	b.append(")");
	return b.toString();
    }

}
