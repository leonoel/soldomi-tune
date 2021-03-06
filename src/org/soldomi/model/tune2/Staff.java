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
	this(null, null, name);
    }

    public Staff(Long id, Long systId, String name) {
	this(id, systId, name, new ArrayList<Symbol>());
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

    public Staff addSymbols(List<Symbol> s) {
	List<Symbol> symbols = new ArrayList<Symbol>(this.symbols);
	symbols.addAll(s);
	return withSymbols(symbols);
    }

    public Staff replaceSymbol(Symbol oldSymbol, Symbol newSymbol) {
	List<Symbol> symbols = new ArrayList<Symbol>(this.symbols);
	symbols.set(symbols.indexOf(oldSymbol), newSymbol);
	return new Staff(id, systId, name, symbols);
    }
}
