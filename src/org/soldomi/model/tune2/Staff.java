package org.soldomi.model.tune2;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Staff {
    public final Long id;
    public final Long systId;
    public final String name;
    public final List<Symbol> symbols;

    public Staff(String name) {
	this(null, null, name, new ArrayList<Symbol>());
    }

    public Staff(Long id, Long systId, String name, List<Symbol> symbols) {
	this.id = id;
	this.systId = systId;
	this.name = name;
	this.symbols = Collections.unmodifiableList(new ArrayList<Symbol>(symbols));
    }

    public Staff withId(Long id) {
	return new Staff(id, systId, name, symbols);
    }

    public Staff withSystId(Long systId) {
	return new Staff(id, systId, name, symbols);
    }

    public Staff withSymbols(List<Symbol> symbols) {
	return new Staff(id, systId, name, symbols);
    }

    public Staff addSymbol(Symbol symbol) {
	List<Symbol> symbols = new ArrayList<Symbol>(this.symbols);
	symbols.add(symbol);
	return withSymbols(symbols);
    }
}
