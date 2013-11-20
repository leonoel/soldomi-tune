package org.soldomi.model.tune2;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Tune {
    public final Long id;
    public final String name;
    public final List<Syst> systs;
    public final List<Sect> sects;

    public Tune(String name) {
	this(null, name);
    }

    public Tune(Long id, String name) {
	this(id, name, new ArrayList<Syst>(), new ArrayList<Sect>());
    }

    public Tune(Long id, String name, List<Syst> systs, List<Sect> sects) {
	this.id = id;
	this.name = name;
	this.systs = Collections.unmodifiableList(new ArrayList<Syst>(systs));
	this.sects = Collections.unmodifiableList(new ArrayList<Sect>(sects));
    }

    public Tune withId(Long id) {
	List<Syst> systs = new ArrayList<Syst>();
	for (Syst syst : this.systs) {
	    systs.add(syst.withTuneId(id));
	}
	List<Sect> sects = new ArrayList<Sect>();
	for (Sect sect : this.sects) {
	    sects.add(sect.withTuneId(id));
	}
	return new Tune(id, name, systs, sects);
    }

    public Tune withSysts(List<Syst> systs) {
	return new Tune(id, name, systs, sects);
    }

    public Tune withSects(List<Sect> sects) {
	return new Tune(id, name, systs, sects);
    }

    public Tune addSyst(Syst syst) {
	List<Syst> systs = new ArrayList<Syst>(this.systs);
	systs.add(syst);
	return withSysts(systs);
    }

    public Tune addSect(Sect sect) {
	List<Sect> sects = new ArrayList<Sect>(this.sects);
	sects.add(sect);
	return withSects(sects);
    }

    public Tune replaceSyst(Syst oldSyst, Syst newSyst) {
	List<Syst> systs = new ArrayList<Syst>(this.systs);
	systs.set(systs.indexOf(oldSyst), newSyst);
	return new Tune(id, name, systs, sects);
    }

    public Tune replaceSect(Sect oldSect, Sect newSect) {
	List<Sect> sects = new ArrayList<Sect>(this.sects);
	sects.set(sects.indexOf(oldSect), newSect);
	return new Tune(id, name, systs, sects);
    }
}
