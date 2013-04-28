package org.soldomi.model.tune;

import org.soldomi.commons.Property;
import org.soldomi.commons.SingleRelationship;

public class KeySignature {
    public final Property<Long> id = new Property<Long>();
    public final SingleRelationship<KeySignature, Symbol> symbol = new SingleRelationship<KeySignature, Symbol>(this);
    public final Property<NotePitch> a = new Property<NotePitch>();
    public final Property<NotePitch> b = new Property<NotePitch>();
    public final Property<NotePitch> c = new Property<NotePitch>();
    public final Property<NotePitch> d = new Property<NotePitch>();
    public final Property<NotePitch> e = new Property<NotePitch>();
    public final Property<NotePitch> f = new Property<NotePitch>();
    public final Property<NotePitch> g = new Property<NotePitch>();
}
