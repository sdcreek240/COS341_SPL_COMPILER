package spl.backend.generator;

import java.util.List;

import org.w3c.dom.Element;

import spl.backend.scoping.VarSymbol;
import spl.backend.tree.SyntaxTree;

final class InstructionGenerator {

    private final CodeGenerator generator;
    private final SyntaxTree tree;

    /**
     * Creates the instruction translator.
     */
    InstructionGenerator(CodeGenerator generator) {
        this.generator = generator;
        this.tree = generator.tree();
    }//END_InstructionGenerator

    /** Top-level entry: emits the trailing*/
    void translateProgram(Element topAlgo) {
        translateAlgo(topAlgo, true);
    }//END_translateProgram

    /**
     * Translates a sequence of instructions in source order.
     */
    void translateAlgo(Element algo) {
        translateAlgo(algo, false);
    }

    private void translateAlgo(Element algo, boolean topLevel) {
        java.util.List<Element> kids = tree.kids(algo);
        if (kids.isEmpty()) {
            if (topLevel) generator.emit("/* end */\n");
            return;
        }
        Element instr = kids.get(0);
        translateInstr(instr, tree.kids(instr));
        if (kids.size() > 2) {
            translateAlgo(kids.get(2), topLevel);
        }
    }

    /**
     * Dispatches an instruction to its translator.
     */
    void translateInstr(Element instr, List<Element> kids) {
        if (kids.isEmpty()) {
            throw new IllegalStateException(
                    "Empty instruction node id=" + SyntaxTree.id(instr));
        }

        String label = SyntaxTree.contents(kids.get(0));

        switch (label) {
            case "USER-DEFINED-NAME" ->
                    translatePossibleAssign(instr, kids);
            case "print" ->
                    translatePrint(kids);
            case "nop" ->
                    generator.emit("/* nop */\n");
            case "comment" ->
                    translateComment(kids);
            case "branch" ->
                    translateBranch(kids.get(0));
            case "loop" ->
                    generator.translateLoop(kids.get(0));

            default -> throw new UnsupportedOperationException(
                    "Unsupported instruction '" + label
                            + "' at node id=" + SyntaxTree.id(instr));
        }
    }//END_translateInstr

    /**
     * Translates a print instruction.
     */
    private void translatePrint(List<Element> kids) {
        if (kids.size() != 2) {
            throw new IllegalStateException("Malformed print instruction");
        }

        Element outp = kids.get(1);
        List<Element> outpKids = tree.kids(outp);

        if (outpKids.size() == 1
                && "STRING".equals(SyntaxTree.contents(outpKids.get(0)))) {
            generator.emit("std::cout << "
                    + SyntaxTree.value(outpKids.get(0)) + ";\n");
            return;
        }

        if (outpKids.size() >= 2
                && "(".equals(SyntaxTree.contents(outpKids.get(0)))
                && "term".equals(SyntaxTree.contents(outpKids.get(1)))) {

            String temp = declareTemp();
            generator.translateTerm(outpKids.get(1), temp);
            generator.emit("std::cout << " + temp + ";\n");
            return;
        }

        throw new UnsupportedOperationException(
                "Unsupported print expression at node id="
                        + SyntaxTree.id(outp));
    }//END_translatePrint

    /**
     * Translates a comment instruction.
     */
    private void translateComment(List<Element> kids) {
        if (kids.size() != 2
                || !"STRING".equals(SyntaxTree.contents(kids.get(1)))) {
            throw new IllegalStateException("Malformed comment instruction");
        }

        String raw = SyntaxTree.value(kids.get(1));
        String value = raw;

        if (raw.length() >= 2
                && raw.startsWith("\"")
                && raw.endsWith("\"")) {
            value = raw.substring(1, raw.length() - 1);
        }

        generator.emit("/* " + value + " */\n");
    }//END_translateComment

    /**
     * Translates an assignment or rejects a deferred function call.
     */
    private void translatePossibleAssign(
            Element instr, List<Element> kids) {

        if (kids.size() != 2
                || !"instrTail".equals(SyntaxTree.contents(kids.get(1)))) {
            throw new IllegalStateException(
                    "Malformed name instruction at node id="
                            + SyntaxTree.id(instr));
        }

        Element lhs = kids.get(0);
        Element tail = kids.get(1);
        List<Element> tailKids = tree.kids(tail);

        if (tailKids.isEmpty()) {
            return;
        }

        String first = SyntaxTree.contents(tailKids.get(0));

        if ("=".equals(first) && tailKids.size() == 2) {
            translateAssign(lhs, tailKids.get(1));
            return;
        }

        if ("(".equals(first)) {
            throw new UnsupportedOperationException(
                    "Function-call code generation is deferred to Phase 3b");
        }

        throw new UnsupportedOperationException(
                "Unsupported name instruction at node id="
                        + SyntaxTree.id(instr));
    }//END_translatePossibleAssign

    /**
     * Translates an assignment using a temporary for its right-hand side.
     */
    private void translateAssign(Element lhs, Element rhs) {
        VarSymbol symbol = generator.symbols()
                .variableAt(SyntaxTree.id(lhs));

        if (symbol == null) {
            throw new IllegalStateException(
                    "No symbol bound to assignment node id="
                            + SyntaxTree.id(lhs));
        }

        String temp = declareTemp();
        generator.translateTerm(rhs, temp);
        generator.emit(symbol.uniqueName() + " = " + temp + ";\n");
    }//END_translateAssign

    /**
     * Translates an if-then-else branch using labels and jumps.
     */
    void translateBranch(Element branch) {
        List<Element> kids = tree.kids(branch);

        if (kids.size() < 10) {
            throw new IllegalStateException(
                    "Malformed branch at node id=" + SyntaxTree.id(branch));
        }

        Element condition = kids.get(1);
        Element thenBody = kids.get(4);
        Element elseBody = kids.get(8);

        String thenLabel = generator.freshLabel();
        String elseLabel = generator.freshLabel();
        String endLabel = generator.freshLabel();

        generator.emit("/* if */\n");
        generator.translateBool(condition, thenLabel, elseLabel);

        generator.emit(elseLabel + ": /* else */\n");
        translateAlgo(elseBody);
        generator.emit("goto " + endLabel + ";\n");

        generator.emit(thenLabel + ": /* then */\n");
        translateAlgo(thenBody);

        generator.emit(endLabel + ": /* continue */\n");
    }//END_translateBranch

    /**
     * Declares and returns a fresh numeric temporary.
     */
    private String declareTemp() {
        String temp = generator.freshTemp();
        generator.emit(generator.numericType() + " " + temp + ";\n");
        return temp;
    }//END_declareTemp
}//END_InstructionGenerator