package spl.backend.generator;

import java.util.List;
import org.w3c.dom.Element;
import spl.backend.tree.SyntaxTree;

final class LoopGenerator {
    private final CodeGenerator generator;
    private final SyntaxTree tree;

    LoopGenerator(CodeGenerator generator) {
        this.generator = generator;
        this.tree = generator.tree();
    }//END_LoopGenerator

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

    private void translateConditionFirst(List<Element> kids, Element loop) {
        String kind = loopKind(kids.get(0));
        Element condition = kids.get(1);
        Element body = kids.get(4);

        // Spec order: ls, le, d
        String ls = generator.freshLabel();
        String le = generator.freshLabel();
        String d  = generator.freshLabel();

        generator.emit(ls + ": /* until */\n");

        generator.setFallThroughLabel(d);
        if ("while".equals(kind)) {

            generator.translateBool(condition, d, le);
        } else {
            // until BOOL
            generator.translateBool(condition, le, d);
        }
        generator.setFallThroughLabel(null);

        generator.emit(d + ": /* do */\n");
        generator.translateAlgo(body);
        generator.emit("goto " + ls + ";\n");
        
        generator.emit(le + ":\n");
        generator.emit("/* exit */\n");
    }//END_translateConditionFirst

    private void translateBodyFirst(List<Element> kids, Element loop) {
        Element body = kids.get(2);
        String kind = loopKind(kids.get(4));
        Element condition = kids.get(5);

        // Spec order: ld, le
        String ld = generator.freshLabel();
        String le = generator.freshLabel();

        generator.emit(ld + ": /* do */\n");
        generator.translateAlgo(body);
        
        generator.emit("/* while */\n");
        
        generator.setFallThroughLabel(le);
        if ("while".equals(kind)) {
            // do-while: repeat if true, exit if false
            generator.translateBool(condition, ld, le);
        } else {
            // do-until = do-while not(BOOL). Swaps targets.
            generator.translateBool(condition, le, ld);
        }
        generator.setFallThroughLabel(null);

        generator.emit(le + ": /* exit */\n");
    }//END_translateBodyFirst

    private String loopKind(Element cond) {
        return SyntaxTree.contents(tree.kids(cond).get(0));
    }//END_loopKind
}