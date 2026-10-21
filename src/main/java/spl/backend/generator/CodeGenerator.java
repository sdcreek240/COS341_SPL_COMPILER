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

public final class CodeGenerator {

    public static final Path DEFAULT_OUTPUT = Paths.get("target.cpp");

    private final SymbolTable symbols;
    private final SyntaxTree tree;

    private final TermGenerator terms;
    private final BooleanGenerator booleans;
    private final InstructionGenerator instructions;
    private final LoopGenerator loops;

    private StringBuilder out;
    private int tempCounter;
    private int labelCounter;
    private boolean programUsesInt;
    private String fallThroughLabel = null;

    /**
     * Creates a generator for a semantically validated syntax tree.
     */
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
    }//END_CodeGenerator

    /**
     * Generates target code and writes it to the default output file.
     */
    public void generateToFile() {
        generateToFile(DEFAULT_OUTPUT);
    }//END_generateToFile

    /**
     * Generates target code and writes it to the specified file.
     */
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

    /**
     * Generates target code and returns it as a string.
     */
    public String generate() {
        out = new StringBuilder();
        tempCounter = 0;
        labelCounter = 0;
        programUsesInt = decideNumericType(tree.root());

        Element p = tree.child(tree.root(), "p");
        if (p == null) {
            throw new IllegalStateException("No <p> node found under root");
        }

        // 1. code-mine comment block (empty until Step E fills it)
        emit("/* begin of the non-executable code-mine for later inlining */\n");
        // Step E will emit function mines here.
        emit("/* end of the non-executable code-mine */\n");

        // 2. start of executable C
        emit("/* begin of executable C code */\n");
        emit("#include <iostream>\n");

        // 3. main wrapper
        emit("int main()\n");
        emit("{\n");

        // 4. global variable declarations
        Element vDecl = tree.child(p, "v_decl");
        if (vDecl == null) {
            throw new IllegalStateException("No <v_decl> under <p>");
        }
        emitVariableDeclarations(vDecl);

        // 5. top-level algorithm
        Element algo = tree.child(p, "algo");
        if (algo == null) {
            throw new IllegalStateException("No <algo> under <p>");
        }
        instructions.translateProgram(algo);

        // 6. closing
        emit("return 0;\n");
        emit("}\n");
        emit("/* end of executable C */\n");

        return out.toString();
    }//END_generate

    /**
     * V_DECL -> eps
     * V_DECL -> NAME V_DECL
     */
    private void emitVariableDeclarations(Element vDecl) {
        List<Element> kids = tree.kids(vDecl);
        if (kids.isEmpty()) {
            emit("\n");
            return;
        }
        Element nameLeaf = kids.get(0);
        Element rest     = kids.get(1);

        VarSymbol v = symbols.variableAt(SyntaxTree.id(nameLeaf));
        if (v == null) {
            throw new IllegalStateException(
                    "No symbol bound to declared variable node id="
                            + SyntaxTree.id(nameLeaf));
        }

        emit(numericType() + " " + v.uniqueName() + " ;\n");
        emitVariableDeclarations(rest);
    }//END_emitVariableDeclarations

    /**
     * Finds the algorithm belonging to the top-level program.
     */
    private Element findTopLevelAlgo() {
        Element p = tree.child(tree.root(), "p");

        if (p == null) {
            throw new IllegalStateException("No <p> node found under root");
        }

        Element algo = tree.child(p, "algo");
        if (algo == null) {
            throw new IllegalStateException("No <algo> node found under <p>");
        }

        return algo;
    }//END_findTopLevelAlgo

    /**
     * Emits generated target code.
     */
    void emit(String code) {
        out.append(code);
    }//END_emit

    /**
     * Allocates a unique temporary variable name.
     */
    String freshTemp() {
        return "vN" + tempCounter++;
    }//END_freshTemp

    /**
     * Allocates a unique control-flow label.
     */
    String freshLabel() {
        return "L" + labelCounter++;
    }//END_freshLabel

    /**
     * Returns the target type required by the program-wide numeric rule.
     */
    String numericType() {
        return programUsesInt ? "int" : "float";
    }//END_numericType

    /**
     * Returns the syntax tree used by the generator.
     */
    SyntaxTree tree() {
        return tree;
    }//END_tree

    /**
     * Returns the symbol table produced by semantic analysis.
     */
    SymbolTable symbols() {
        return symbols;
    }//END_symbols

    /**
     * Translates a numeric or arithmetic term.
     */
    void translateTerm(Element term, String resultVar) {
        terms.translateTerm(term, resultVar);
    }//END_translateTerm

    /**
     * Translates a Boolean expression into conditional jumps.
     */
    void translateBool(Element bool, String trueLabel, String falseLabel) {
        booleans.translateBool(bool, trueLabel, falseLabel);
    }//END_translateBool

    /**
     * Translates an instruction.
     */
    void translateInstr(Element instr, java.util.List<Element> kids) {
        instructions.translateInstr(instr, kids);
    }//END_translateInstr

    /**
     * Translates a sequence of instructions.
     */
    void translateAlgo(Element algo) {
        instructions.translateAlgo(algo);
    }//END_translateAlgo

    /**
     * Translates a branch.
     */
    void translateBranch(Element branch) {
        instructions.translateBranch(branch);
    }//END_translateBranch

    /**
     * Translates a loop.
     */
    void translateLoop(Element loop) {
        loops.translateLoop(loop);
    }//END_translateLoop


    private boolean decideNumericType(Element element) {
        if (containsLeaf(element, "mod")) return true;
        if (containsLeaf(element, "div")) return false;
        if (containsDecimalLiteral(element)) return false;
        return true;
    }//END_decideNumericType

    private boolean containsLeaf(Element element, String contents) {
        if (contents.equals(SyntaxTree.contents(element))) return true;
        for (Element child : tree.kids(element)) {
            if (containsLeaf(child, contents)) return true;
        }
        return false;
    }//END_containsLeaf

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

    
    void setFallThroughLabel(String label) {
        this.fallThroughLabel = label;
    }//END_setFallThroughLabel

    String getFallThroughLabel() {
        return fallThroughLabel;
    }//END_getFallThroughLabel
}//END_CodeGenerator