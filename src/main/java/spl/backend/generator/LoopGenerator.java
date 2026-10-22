package spl.backend.generator;

import java.util.List;

import org.w3c.dom.Element;

import spl.backend.tree.SyntaxTree;

/** Translates condition-first and body-first SPL loops. */
final class LoopGenerator {

    private final CodeGenerator generator;
    private final SyntaxTree tree;

    /** Creates the loop translator. */
    LoopGenerator(CodeGenerator generator) {
        
        this.generator = generator;
        this.tree = generator.tree();
    }//END_LoopGenerator

    /** Translates a loop according to its syntax-tree form. */
    void translateLoop(Element loop) {

        List<Element> kids = tree.kids(loop);
        String first = SyntaxTree.contents(kids.get(0));

        if ("cond".equals(first)) {
            translateConditionFirst(kids, loop);
        } else if ("do".equals(first)) {
            translateBodyFirst(kids, loop);
        } else {
            throw new IllegalStateException("Unexpected loop child");
        }
    }//END_translateLoop

    /** Translates a loop whose condition appears before its body. */
    private void translateConditionFirst(List<Element> kids, Element loop) {

        String kind = loopKind(kids.get(0));
        Element condition = kids.get(1);
        Element body = kids.get(4);

        // Allocate labels in the order required by the translation.
        String ls = generator.freshLabel();
        String le = generator.freshLabel();
        String d = generator.freshLabel();

        generator.emit(ls + ": ; /* until */\n");

        String saved = generator.getFallThroughLabel();

        generator.setFallThroughLabel(d);
        if ("while".equals(kind)) {
            generator.translateBool(condition, d, le);
        } else {
            // until BOOL
            generator.translateBool(condition, le, d);
        }
        generator.setFallThroughLabel(saved);

        generator.emit(d + ": ; /* do */\n");
        generator.translateAlgo(body);
        generator.emit("goto " + ls + ";\n");

        generator.emit(le + ": ;\n");
        generator.emit("/* exit */\n");
    }//END_translateConditionFirst

    /** Translates a loop whose body appears before its condition. */
    private void translateBodyFirst(List<Element> kids, Element loop) {

        Element body = kids.get(2);
        String kind = loopKind(kids.get(4));
        Element condition = kids.get(5);

        // Allocate labels in the order required by the translation.
        String ld = generator.freshLabel();
        String le = generator.freshLabel();

        generator.emit(ld + ": ; /* do */\n");
        generator.translateAlgo(body);

        generator.emit("/* while */\n");

        String saved = generator.getFallThroughLabel();

        generator.setFallThroughLabel(le);
        if ("while".equals(kind)) {
            // do-while: repeat if true, exit if false
            generator.translateBool(condition, ld, le);
        } else {
            // do-until: reverse the condition's destinations
            generator.translateBool(condition, le, ld);
        }
        generator.setFallThroughLabel(saved);

        generator.emit(le + ": ; /* exit */\n");
    }//END_translateBodyFirst

    /** Returns the loop operator from its condition node. */
    private String loopKind(Element cond) {
        return SyntaxTree.contents(tree.kids(cond).get(0));
    }//END_loopKind
}//END_LoopGenerator