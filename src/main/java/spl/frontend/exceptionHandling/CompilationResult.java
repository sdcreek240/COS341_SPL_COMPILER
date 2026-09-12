package spl.frontend.exceptionHandling;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import spl.antlr.SPLParser;

public final class CompilationResult {

    private final ParseTree tree;
    private final Diagnostic diagnostic;
    private final CommonTokenStream tokens;
    private final SPLParser parser;

    private CompilationResult(ParseTree tree, Diagnostic diagnostic, CommonTokenStream tokens, SPLParser parser) {

        this.tree = tree;
        this.diagnostic = diagnostic;
        this.tokens = tokens;
        this.parser = parser;
    }//END_CompilationResult

    public static CompilationResult success(ParseTree tree, CommonTokenStream tokens, SPLParser parser) {

        return new CompilationResult(tree, null, tokens, parser);
    }//END_success

    public static CompilationResult failure(Diagnostic diagnostic, CommonTokenStream tokens) {

        return new CompilationResult(null, diagnostic, tokens, null);
    }//END_failure

    public boolean isSuccess() { return diagnostic == null; }

    public CommonTokenStream getTokens() { return tokens; }

    public ParseTree getTree() {

        if (tree == null) throw new IllegalStateException("no parse tree - compilation failed");
        return tree;
    }//END_getTree

    public SPLParser getParser() {
        if (parser == null) throw new IllegalStateException("no parser - compilation failed");
        return parser;
    }//END_getParser

    public Diagnostic getDiagnostic() {

        if (diagnostic == null) throw new IllegalStateException("no diagnostic - compilation succeeded");
        return diagnostic;
    }//END_getDiagnostic
}//END_CompilationResult