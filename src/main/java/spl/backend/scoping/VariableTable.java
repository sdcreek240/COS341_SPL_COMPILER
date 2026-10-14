package spl.backend.scoping;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/** Variable table of ONE scope level. */
public final class VariableTable {
    private final Map<String, VarSymbol> symbols = new LinkedHashMap<>();

    public boolean contains(String name)  { return symbols.containsKey(name); }
    public VarSymbol get(String name)     { return symbols.get(name); }
    public void add(VarSymbol s)          { symbols.put(s.name(), s); }
    public Collection<VarSymbol> all()    { return symbols.values(); }
}