package spl.backend.generator;

import java.util.List;

import org.w3c.dom.Element;

import spl.backend.tree.SyntaxTree;

final class BooleanGenerator {

    private final CodeGenerator generator;
    private final SyntaxTree tree;

    /**
     * Creates the Boolean translator.
     */
    BooleanGenerator(CodeGenerator generator) {
        this.generator = generator;
        this.tree = generator.tree();
    }//END_BooleanGenerator

    /**
     * Translates a Boolean expression into jumps to its true or false label.
     */
    void translateBool(
            Element bool, String trueLabel, String falseLabel) {

        List<Element> kids = tree.kids(bool);

        if (kids.isEmpty()) {
            throw new IllegalStateException(
                    "Empty Boolean node id=" + SyntaxTree.id(bool));
        }

        String label = SyntaxTree.contents(kids.get(0));

        switch (label) {
            case "eq" ->
                    translateComparison(kids, trueLabel, falseLabel, "==");
            case "larger" ->
                    translateComparison(kids, trueLabel, falseLabel, ">");
            case "lesser" ->
                    translateComparison(kids, trueLabel, falseLabel, "<");
            case "not" ->
                    translateNot(kids, trueLabel, falseLabel);
            case "and" ->
                    translateAnd(kids, trueLabel, falseLabel);
            case "or" ->
                    translateOr(kids, trueLabel, falseLabel);

            default -> throw new UnsupportedOperationException(
                    "Unsupported Boolean operator '" + label
                            + "' at node id=" + SyntaxTree.id(bool));
        }
    }//END_translateBool

    /**
     * Translates a comparison using explicit true and false jumps.
     */
    private void translateComparison(
            List<Element> kids,
            String trueLabel,
            String falseLabel,
            String operator) {

        if (kids.size() < 5) {
            throw new IllegalStateException(
                    "Malformed comparison at node id="
                            + SyntaxTree.id(kids.get(0)));
        }

        String leftTemp = declareTemp();
        generator.translateTerm(kids.get(2), leftTemp);

        String rightTemp = declareTemp();
        generator.translateTerm(kids.get(3), rightTemp);

        generator.emit("if ( " + leftTemp + " " + operator + " "
                + rightTemp + " ) goto " + trueLabel + ";\n");
        generator.emit("goto " + falseLabel + ";\n");
    }//END_translateComparison

    /**
     * Translates not by swapping the destination labels.
     */
    private void translateNot(
            List<Element> kids, String trueLabel, String falseLabel) {

        if (kids.size() < 4) {
            throw new IllegalStateException(
                    "Malformed not expression at node id="
                            + SyntaxTree.id(kids.get(0)));
        }

        generator.translateBool(kids.get(2), falseLabel, trueLabel);
    }//END_translateNot

    /**
     * Translates or by evaluating the second operand if the first is false.
     */
    private void translateOr(
            List<Element> kids, String trueLabel, String falseLabel) {

        if (kids.size() < 5) {
            throw new IllegalStateException(
                    "Malformed or expression at node id="
                            + SyntaxTree.id(kids.get(0)));
        }

        String rightStart = generator.freshLabel();

        generator.translateBool(kids.get(2), trueLabel, rightStart);
        generator.emit(rightStart + ": ;\n");
        generator.translateBool(kids.get(3), trueLabel, falseLabel);
    }//END_translateOr

    /**
     * Translates and by evaluating the second operand only if the first is true.
     */
    private void translateAnd(
            List<Element> kids, String trueLabel, String falseLabel) {

        if (kids.size() < 5) {
            throw new IllegalStateException(
                    "Malformed and expression at node id="
                            + SyntaxTree.id(kids.get(0)));
        }

        String rightStart = generator.freshLabel();

        generator.translateBool(kids.get(2), rightStart, falseLabel);
        generator.emit(rightStart + ": ;\n");
        generator.translateBool(kids.get(3), trueLabel, falseLabel);
    }//END_translateAnd

    /**
     * Declares and returns a fresh numeric temporary.
     */
    private String declareTemp() {
        String temp = generator.freshTemp();
        generator.emit(generator.numericType() + " " + temp + ";\n");
        return temp;
    }//END_declareTemp
}//END_BooleanGenerator