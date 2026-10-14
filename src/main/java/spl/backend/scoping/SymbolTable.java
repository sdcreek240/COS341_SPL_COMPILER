package spl.backend.scoping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Static symbol table for SPL. Owns the scope stack, the unique-name counters
 * (v1, v2, ... / f1, f2, ...) and the node-id -> symbol bindings used to
 * annotate tree.xml and, later, by the code generator.
 */
public final class SymbolTable {
    private final List<Scope> allScopes = new ArrayList<>();
    private final Map<Integer, VarSymbol> varBindings = new HashMap<>();   // tree node id -> variable
    private final Map<Integer, FuncSymbol> funcBindings = new HashMap<>(); // tree node id -> function
    private final Map<String, String> types = new HashMap<>();            // unique name -> type
    private Scope current = null;
    private int varCounter = 0;
    private int funcCounter = 0;

    // ---------- scope handling ----------

    /** Opens a new scope: level 0 the first time, then one deeper than the current one. */
    public Scope enterScope() {
        Scope s = new Scope(current == null ? 0 : current.level() + 1, current);
        allScopes.add(s);
        current = s;
        return s;
    }

    public void exitScope() {
        if (current == null) throw new IllegalStateException("no scope to exit");
        current = current.parent();
    }

    public Scope currentScope() { return current; }
    public int currentLevel()   { return current.level(); }

    // ---------- declarations ----------

    /** Declares a variable (or parameter) in the CURRENT scope. */
    public VarSymbol declareVariable(String name, int nodeId, boolean isParameter) {
        VarSymbol existing = current.variables().get(name);
        if (existing != null) {
            if (existing.isParameter() && !isParameter) {
                throw new SemanticException("local variable " + name
                        + " masks a parameter of the same function", nodeId);
            }
            throw new SemanticException("variable " + name
                    + " is already declared at scope level " + current.level(), nodeId);
        }
        VarSymbol s = new VarSymbol(name, "v" + (++varCounter), current.level(), nodeId, isParameter);
        current.variables().add(s);
        varBindings.put(nodeId, s);
        return s;
    }

    /** Declares a function in the CURRENT scope's function table. */
    public FuncSymbol declareFunction(String name, int nodeId, String returnType, int paramCount) {
        if (current.functions().contains(name)) {
            throw new SemanticException("function " + name
                    + " is already declared at scope level " + current.level(), nodeId);
        }
        FuncSymbol s = new FuncSymbol(name, "f" + (++funcCounter), current.level(),
                nodeId, returnType, paramCount);
        current.functions().add(s);
        funcBindings.put(nodeId, s);
        return s;
    }

    // ---------- lookups (for uses inside an ALGO) ----------

    /** Variable use: nearest declaration at this level or any enclosing level. */
    public VarSymbol resolveVariable(String name, int useNodeId) {
        for (Scope s = current; s != null; s = s.parent()) {
            VarSymbol v = s.variables().get(name);
            if (v != null) {
                varBindings.put(useNodeId, v);
                return v;
            }
        }
        throw new SemanticException("variable " + name + " is used but never declared", useNodeId);
    }

    /** Function call: ONLY the current level's function table (strictly hierarchic, no recursion). */
    public FuncSymbol resolveFunction(String name, int useNodeId) {
        FuncSymbol f = current.functions().get(name);
        if (f == null) {
            for (Scope s = current.parent(); s != null; s = s.parent()) {
                if (s.functions().contains(name)) {
                    throw new SemanticException("function " + name + " is not listed at scope level "
                            + current.level() + " (it is declared at level " + s.level()
                            + "; recursion and calling outer functions are not allowed)", useNodeId);
                }
            }
            throw new SemanticException("function " + name
                    + " is called but not declared at scope level " + current.level(), useNodeId);
        }
        funcBindings.put(useNodeId, f);
        return f;
    }

    // ---------- type information (phase 2b) ----------

    /** Records the type of a symbol ("numeric", "procedure", ...), keyed by its unique name (v1, f2, ...). */
    public void setType(String uniqueName, String type) { types.put(uniqueName, type); }

    /** Type of a symbol; "unknown" until the type analyser has visited its declaration. */
    public String typeOf(String uniqueName)             { return types.getOrDefault(uniqueName, "unknown"); }

    // ---------- results ----------

    /** Symbol bound to a USER-DEFINED-NAME leaf (declaration or use), or null. */
    public VarSymbol variableAt(int nodeId)  { return varBindings.get(nodeId); }
    public FuncSymbol functionAt(int nodeId) { return funcBindings.get(nodeId); }
    public List<Scope> scopes()              { return Collections.unmodifiableList(allScopes); }

    /** Human-readable dump for debugging. */
    public String dump() {
        StringBuilder sb = new StringBuilder();
        for (Scope s : allScopes) {
            sb.append("Scope level ").append(s.level()).append('\n');
            for (VarSymbol v : s.variables().all())
                sb.append("  var  ").append(v.name()).append(" -> ").append(v.uniqueName())
                  .append(v.isParameter() ? " (param)" : "")
                  .append(" [").append(typeOf(v.uniqueName())).append("]").append('\n');
            for (FuncSymbol f : s.functions().all())
                sb.append("  func ").append(f.name()).append(" -> ").append(f.uniqueName())
                  .append(" : ").append(f.returnType())
                  .append(" [").append(typeOf(f.uniqueName())).append("]").append('\n');
        }
        return sb.toString();
    }
}