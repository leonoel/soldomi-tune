package org.soldomi.model.tune2;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Syst {
    public final Long id;
    public final Long tuneId;
    public final String name;
    public final List<Staff> staves;

    public Syst(String name) {
	this(null, null, name);
    }

    public Syst(Long id, Long tuneId, String name) {
	this(id, tuneId, name, new ArrayList<Staff>());
    }

    public Syst(Long id, Long tuneId, String name, List<Staff> staves) {
	this.id = id;
	this.tuneId = tuneId;
	this.name = name;
	this.staves = Collections.unmodifiableList(new ArrayList<Staff>(staves));
    }

    public Syst withId(Long id) {
	List<Staff> staves = new ArrayList<Staff>();
	for (Staff staff : this.staves) {
	    staves.add(staff.withSystId(id));
	}
	return new Syst(id, tuneId, name, staves);
    }

    public Syst withTuneId(Long tuneId) {
	return new Syst(id, tuneId, name, staves);
    }

    public Syst withStaves(List<Staff> staves) {
	return new Syst(id, tuneId, name, staves);
    }

    public Syst addStaff(Staff staff) {
	List<Staff> staves = new ArrayList<Staff>(this.staves);
	staves.add(staff);
	return withStaves(staves);
    }

    public Syst replaceStaff(Staff oldStaff, Staff newStaff) {
	List<Staff> staves = new ArrayList<Staff>(this.staves);
	staves.set(staves.indexOf(oldStaff), newStaff);
	return new Syst(id, tuneId, name, staves);
    }
}
