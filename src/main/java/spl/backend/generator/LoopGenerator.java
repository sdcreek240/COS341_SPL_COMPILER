package spl.backend.generator;

import java.util.List;

import org.w3c.dom.Element;

import spl.backend.tree.SyntaxTree;

final class LoopGenerator {

    private final CodeGenerator generator;
    private final SyntaxTree tree;

    /**
     * Creates the loop translator.
     */
    LoopGenerator(CodeGenerator generator) {
        this.generator = generator;
        this.tree = generator.tree();
    }//END_LoopGenerator

    /**
     * Translates a loop in either condition-first or body-first form.
     */
    void translateLoop(Element loop) {
        List<Element> kids = tree.kids(loop);

        if (kids.isEmpty()) {
            throw new IllegalStateException(
                    "Empty loop node id=" + SyntaxTree.id(loop));
        }

        String first = SyntaxTree.contents(kids.get(0));

        if ("cond".equals(first)) {
            translateConditionFirst(kids, loop);
        } else if ("do".equals(first)) {
            translateBodyFirst(kids, loop);
        } else {
            throw new IllegalStateException(
                    "Unexpected loop child '" + first
                            + "' at node id=" + SyntaxTree.id(loop));
        }
    }//END_translateLoop

    /**
     * Translates while and until loops with the condition first.
     */
    private void translateConditionFirst(List<Element> kids, Element loop) {
        if (kids.size() < 6) {
            throw new IllegalStateException(
                    "Malformed condition-first loop at node id="
                            + SyntaxTree.id(loop));
        }

        String kind = loopKind(kids.get(0));
        Element condition = kids.get(1);
        Element body = kids.get(4);

        String startLabel = generator.freshLabel();
        String bodyLabel = generator.freshLabel();
        String endLabel = generator.freshLabel();

        generator.emit(startLabel + ": /* loop start */\n");

        if ("while".equals(kind)) {
            generator.translateBool(condition, bodyLabel, endLabel);
        } else if ("until".equals(kind)) {
            generator.translateBool(condition, endLabel, bodyLabel);
        } else {
            throw new IllegalStateException("Unknown loop condition: " + kind);
        }

        generator.emit(bodyLabel + ": /* loop body */\n");
        generator.translateAlgo(body);
        generator.emit("goto " + startLabel + ";\n");
        generator.emit(endLabel + ": /* loop end */\n");
    }//END_translateConditionFirst

    /**
     * Translates while and until loops with the body first.
     */
    private void translateBodyFirst(List<Element> kids, Element loop) {
        if (kids.size() < 6) {
            throw new IllegalStateException(
                    "Malformed body-first loop at node id="
                            + SyntaxTree.id(loop));
        }

        Element body = kids.get(2);
        String kind = loopKind(kids.get(4));
        Element condition = kids.get(5);

        String bodyLabel = generator.freshLabel();
        String endLabel = generator.freshLabel();

        generator.emit(bodyLabel + ": /* loop body */\n");
        generator.translateAlgo(body);

        if ("while".equals(kind)) {
            generator.translateBool(condition, bodyLabel, endLabel);
        } else if ("until".equals(kind)) {
            generator.translateBool(condition, endLabel, bodyLabel);
        } else {
            throw new IllegalStateException("Unknown loop condition: " + kind);
        }

        generator.emit(endLabel + ": /* loop end */\n");
    }//END_translateBodyFirst

    /**
     * Reads the loop condition keyword.
     */
    private String loopKind(Element cond) {
        List<Element> kids = tree.kids(cond);

        if (kids.isEmpty()) {
            throw new IllegalStateException(
                    "Empty loop condition node id=" + SyntaxTree.id(cond));
        }

        return SyntaxTree.contents(kids.get(0));
    }//END_loopKind
}//END_LoopGenerator