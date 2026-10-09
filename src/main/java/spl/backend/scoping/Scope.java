package spl.backend.scoping;

/** One scope level: its own variable table and function table, plus a link to the enclosing scope. */
public final class Scope {
    private final int level;
    private final Scope parent;
    private final VariableTable variables = new VariableTable();
    private final FunctionTable functions = new FunctionTable();

    Scope(int level, Scope parent) { this.level = level; this.parent = parent; }

    public int level()                  { return level; }
    public Scope parent()               { return parent; }
    public VariableTable variables()    { return variables; }
    public FunctionTable functions()    { return functions; }
}