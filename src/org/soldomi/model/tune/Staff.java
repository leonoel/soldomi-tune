package org.soldomi.model.tune;

import org.soldomi.commons.Parent;
import org.soldomi.commons.HasSome;

public class Staff {
    public final Parent<Staff, Syst> syst = new Parent<Staff, Syst>(this);
    public final HasSome<Staff, Symbol> symbols = new HasSome<Staff, Symbol>(this);
}
