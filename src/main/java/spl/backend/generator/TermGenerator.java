package spl.backend.generator;

import java.util.List;

import org.w3c.dom.Element;

import spl.backend.scoping.VarSymbol;
import spl.backend.tree.SyntaxTree;

/** Translates SPL numeric terms and arithmetic operations. */
final class TermGenerator {

    private final CodeGenerator generator;
    private final SyntaxTree tree;

    /** Creates the term translator. */
    TermGenerator(CodeGenerator generator) {
        
        this.generator = generator;
        this.tree = generator.tree();
    }//END_TermGenerator

    /** Translates a term and stores its result in the supplied variable. */
    void translateTerm(Element term, String resultVar) {

        List<Element> kids = tree.kids(term);

        if (kids.isEmpty()) {
            throw new IllegalStateException(
                    "Empty term node id=" + SyntaxTree.id(term));
        }

        Element head = kids.get(0);
        String label = SyntaxTree.contents(head);

        switch (label) {
            case "NUM_LIT" ->
                    generator.emit(resultVar + " = "
                            + SyntaxTree.value(head) + ";\n");

            case "USER-DEFINED-NAME" -> {
                // A non-empty termTail beginning with "(" represents a call.
                Element tail = kids.size() > 1 ? kids.get(1) : null;
                List<Element> tailKids = tail == null
                        ? List.of()
                        : tree.kids(tail);

                if (!tailKids.isEmpty()
                        && "(".equals(SyntaxTree.contents(tailKids.get(0)))
                        && tailKids.size() == 3) {
                    Element input = tailKids.get(1);
                    generator.callInliner().inlineCall(
                            head, input, resultVar, true);
                    return;
                }

                VarSymbol symbol = generator.symbols()
                        .variableAt(SyntaxTree.id(head));

                if (symbol == null) {
                    throw new IllegalStateException(
                            "No symbol bound to NAME node id="
                                    + SyntaxTree.id(head));
                }

                generator.emit(resultVar + " = "
                        + symbol.uniqueName() + ";\n");
            }

            case "add" -> translateBinary(kids, resultVar, "+");
            case "sub" -> translateBinary(kids, resultVar, "-");
            case "mul" -> translateBinary(kids, resultVar, "*");
            case "div" -> translateBinary(kids, resultVar, "/");
            case "mod" -> translateBinary(kids, resultVar, "%");
            case "neg" -> translateNeg(kids, resultVar);
            default -> throw new UnsupportedOperationException(
                    "Unsupported term '" + label + "' at node id="
                            + SyntaxTree.id(term));
        }
    }//END_translateTerm

    /** Translates a binary arithmetic operation. */
    private void translateBinary(
            List<Element> kids, String resultVar, String operator) {

        if (kids.size() < 5) {
            throw new IllegalStateException(
                    "Malformed binary term at node id="
                            + SyntaxTree.id(kids.get(0)));
        }

        Element left = kids.get(2);
        Element right = kids.get(3);

        String leftTemp = declareTemp();
        translateTerm(left, leftTemp);

        String rightTemp = declareTemp();
        translateTerm(right, rightTemp);

        generator.emit(resultVar + " = " + leftTemp + " "
                + operator + " " + rightTemp + ";\n");
    }//END_translateBinary

    /** Translates unary numeric negation. */
    private void translateNeg(List<Element> kids, String resultVar) {

        if (kids.size() < 4) {
            throw new IllegalStateException(
                    "Malformed neg term at node id="
                            + SyntaxTree.id(kids.get(0)));
        }

        String operandTemp = declareTemp();
        translateTerm(kids.get(2), operandTemp);

        generator.emit(resultVar + " = -" + operandTemp + ";\n");
    }//END_translateNeg

    /** Declares and returns a fresh numeric temporary. */
    private String declareTemp() {

        String temp = generator.freshTemp();
        generator.emit(generator.numericType() + " " + temp + ";\n");
        return temp;
    }//END_declareTemp
}//END_TermGenerator