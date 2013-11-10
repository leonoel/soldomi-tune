package org.soldomi.model.tune2;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Block {
    public final Long id;
    public final Long sectId;
    public final Long startTime;
    public final List<Symbol> symbols;

    public Block(Long startTime) {
	this(null, null, startTime);
    }

    public Block(Long id, Long sectId, Long startTime) {
	this(id, sectId, startTime, new ArrayList<Symbol>());
    }

    public Block(Long id, Long sectId, Long startTime, List<Symbol> symbols) {
	this.id = id;
	this.sectId = sectId;
	this.startTime = startTime;
	this.symbols = Collections.unmodifiableList(new ArrayList<Symbol>(symbols));
    }

    public Block withId(Long id) {
	return new Block(id, sectId, startTime, symbols);
    }

    public Block withSectId(Long sectId) {
	return new Block(id, sectId, startTime, symbols);
    }

    public Block withSymbols(List<Symbol> symbols) {
	return new Block(id, sectId, startTime, symbols);
    }

    public Block addSymbol(Symbol symbol) {
	List<Symbol> symbols = new ArrayList<Symbol>(this.symbols);
	symbols.add(symbol);
	return withSymbols(symbols);
    }
}
