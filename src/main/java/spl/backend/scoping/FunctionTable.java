package spl.backend.scoping;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/** Function table of ONE scope level. */
public final class FunctionTable {
    private final Map<String, FuncSymbol> symbols = new LinkedHashMap<>();

    public boolean contains(String name)  { return symbols.containsKey(name); }
    public FuncSymbol get(String name)    { return symbols.get(name); }
    public void add(FuncSymbol s)         { symbols.put(s.name(), s); }
    public Collection<FuncSymbol> all()   { return symbols.values(); }
}