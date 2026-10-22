package spl.backend.generator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.w3c.dom.Element;

import spl.backend.scoping.SymbolTable;
import spl.backend.scoping.VarSymbol;
import spl.backend.tree.SyntaxTree;

/** Coordinates SPL-to-C++ code generation and shared translation state. */
public final class CodeGenerator {

    public static final Path DEFAULT_OUTPUT = Paths.get("target.cpp");

    private final SymbolTable symbols;
    private final SyntaxTree tree;

    private final TermGenerator terms;
    private final BooleanGenerator booleans;
    private final InstructionGenerator instructions;
    private final LoopGenerator loops;

    private final FunctionMineGenerator functions;
    private final CallInliner inliner;

    private StringBuilder out;
    private int tempCounter;
    private int labelCounter;
    private boolean programUsesInt;
    private String fallThroughLabel = null;

    /** Creates a generator for a semantically validated syntax tree. */
    public CodeGenerator(SymbolTable symbols, SyntaxTree tree) {

        if (symbols == null) {
            throw new IllegalArgumentException("symbols must not be null");
        }
        if (tree == null) {
            throw new IllegalArgumentException("tree must not be null");
        }

        this.symbols = symbols;
        this.tree = tree;

        this.terms = new TermGenerator(this);
        this.booleans = new BooleanGenerator(this);
        this.instructions = new InstructionGenerator(this);
        this.loops = new LoopGenerator(this);

        this.functions = new FunctionMineGenerator(this);
        this.inliner = new CallInliner(this);
    }//END_CodeGenerator

    /** Declares and returns a fresh numeric temporary in the current type. */
    String declareTemp() {

        String temp = freshTemp();
        emit(numericType() + " " + temp + ";\n");
        return temp;
    }//END_declareTemp

    /** Returns the function-call inliner used by this generator. */
    CallInliner callInliner() {
        return inliner;
    }//END_callInliner

    /** Generates target code and writes it to the default output file. */
    public void generateToFile() {
        generateToFile(DEFAULT_OUTPUT);
    }//END_generateToFile

    /** Generates target code and writes it to the specified file. */
    public void generateToFile(Path outputPath) {

        if (outputPath == null) {
            throw new IllegalArgumentException("outputPath must not be null");
        }

        String code = generate();

        try {
            Path parent = outputPath.toAbsolutePath().getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (BufferedWriter writer = Files.newBufferedWriter(
                    outputPath, StandardCharsets.UTF_8)) {
                writer.write(code);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Failed to write target code to " + outputPath, e);
        }
    }//END_generateToFile

    /** Generates target code and returns it as a string. */
    public String generate() {

        out = new StringBuilder();
        tempCounter = 0;
        labelCounter = 0;
        fallThroughLabel = null;
        programUsesInt = decideNumericType(tree.root());

        Element p = tree.child(tree.root(), "p");
        if (p == null) {
            throw new IllegalStateException("No <p> node found under root");
        }

        emit("/* begin of the non-executable code-mine for later inlining\n");

        Element fDecl = tree.child(p, "f_decl");
        if (fDecl == null) {
            throw new IllegalStateException("No <f_decl> under <p>");
        }

        String mine = functions.generateMines(fDecl);
        emit(mine);
        if (!mine.endsWith("\n")) {
            emit("\n");
        }

        emit("end of the non-executable code-mine */\n");

        // Start the executable C++ section.
        emit("/* begin of executable C code */\n");
        emit("#include <iostream>\n");

        // Emit the main wrapper.
        emit("int main()\n");
        emit("{\n");

        // Emit global variable declarations.
        Element vDecl = tree.child(p, "v_decl");
        if (vDecl == null) {
            throw new IllegalStateException("No <v_decl> under <p>");
        }
        emitVariableDeclarations(vDecl);

        // Emit the top-level algorithm.
        Element algo = tree.child(p, "algo");
        if (algo == null) {
            throw new IllegalStateException("No <algo> under <p>");
        }
        instructions.translateProgram(algo);

        emit("return 0;\n");
        emit("}\n");
        emit("/* end of executable C */\n");

        return out.toString();
    }//END_generate

    /** Emits declarations for a V_DECL chain. */
    private void emitVariableDeclarations(Element vDecl) {

        List<Element> kids = tree.kids(vDecl);
        if (kids.isEmpty()) {
            emit("\n");
            return;
        }

        Element nameLeaf = kids.get(0);
        Element rest = kids.get(1);

        VarSymbol v = symbols.variableAt(SyntaxTree.id(nameLeaf));
        if (v == null) {
            throw new IllegalStateException(
                    "No symbol bound to declared variable node id="
                            + SyntaxTree.id(nameLeaf));
        }

        emit(numericType() + " " + v.uniqueName() + " ;\n");
        emitVariableDeclarations(rest);
    }//END_emitVariableDeclarations

    /** Emits generated target code into the current output buffer. */
    void emit(String code) {
        out.append(code);
    }//END_emit

    /** Allocates a unique temporary variable name. */
    String freshTemp() {
        return "vN" + tempCounter++;
    }//END_freshTemp

    /** Allocates a unique control-flow label. */
    String freshLabel() {
        return "L" + labelCounter++;
    }//END_freshLabel

    /** Returns the target numeric type required by the whole program. */
    String numericType() {
        return programUsesInt ? "int" : "float";
    }//END_numericType

    /** Returns the syntax tree used by the generator. */
    SyntaxTree tree() {
        return tree;
    }//END_tree

    /** Returns the symbol table produced by semantic analysis. */
    SymbolTable symbols() {
        return symbols;
    }//END_symbols

    /** Translates a numeric or arithmetic term. */
    void translateTerm(Element term, String resultVar) {
        terms.translateTerm(term, resultVar);
    }//END_translateTerm

    /** Translates a Boolean expression into conditional jumps. */
    void translateBool(Element bool, String trueLabel, String falseLabel) {
        booleans.translateBool(bool, trueLabel, falseLabel);
    }//END_translateBool

    /** Translates an instruction and its syntax-tree children. */
    void translateInstr(Element instr, List<Element> kids) {
        instructions.translateInstr(instr, kids);
    }//END_translateInstr

    /** Translates a sequence of instructions. */
    void translateAlgo(Element algo) {
        instructions.translateAlgo(algo);
    }//END_translateAlgo

    /** Translates a branch. */
    void translateBranch(Element branch) {
        instructions.translateBranch(branch);
    }//END_translateBranch

    /** Translates a loop. */
    void translateLoop(Element loop) {
        loops.translateLoop(loop);
    }//END_translateLoop

    /** Chooses the program-wide numeric type from the syntax tree. */
    private boolean decideNumericType(Element element) {

        if (containsLeaf(element, "mod")) return true;
        if (containsLeaf(element, "div")) return false;
        if (containsDecimalLiteral(element)) return false;
        return true;
    }//END_decideNumericType

    /** Checks whether a syntax-tree subtree contains the given leaf. */
    private boolean containsLeaf(Element element, String contents) {

        if (contents.equals(SyntaxTree.contents(element))) return true;
        for (Element child : tree.kids(element)) {
            if (containsLeaf(child, contents)) return true;
        }
        return false;
    }//END_containsLeaf

    /** Checks whether a syntax-tree subtree contains a decimal numeric literal. */
    private boolean containsDecimalLiteral(Element element) {

        if ("NUM_LIT".equals(SyntaxTree.contents(element))
                && SyntaxTree.value(element) != null
                && SyntaxTree.value(element).contains(".")) {
            return true;
        }
        for (Element child : tree.kids(element)) {
            if (containsDecimalLiteral(child)) return true;
        }
        return false;
    }//END_containsDecimalLiteral

    /** Sets the label that can be reached by falling through. */
    void setFallThroughLabel(String label) {
        this.fallThroughLabel = label;
    }//END_setFallThroughLabel

    /** Returns the current fall-through label, if one is set. */
    String getFallThroughLabel() {
        return fallThroughLabel;
    }//END_getFallThroughLabel

    /** Redirects generated output to a different buffer and returns the old buffer. */
    StringBuilder swapEmitTarget(StringBuilder newOut) {
        
        StringBuilder saved = this.out;
        this.out = newOut;
        return saved;
    }//END_swapEmitTarget
}//END_CodeGenerator