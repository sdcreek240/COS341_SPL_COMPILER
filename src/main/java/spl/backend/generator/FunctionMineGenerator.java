package spl.backend.generator;

import java.util.List;

import org.w3c.dom.Element;

import spl.backend.scoping.FuncSymbol;
import spl.backend.scoping.VarSymbol;
import spl.backend.tree.SyntaxTree;

/** Emits the non-executable code-mine used to describe functions and procedures. */
final class FunctionMineGenerator {

    private final CodeGenerator generator;
    private final SyntaxTree tree;

    /** Accumulates code-mine text before it is sanitised and wrapped. */
    private final StringBuilder mine = new StringBuilder();

    /** Creates the function code-mine generator. */
    FunctionMineGenerator(CodeGenerator generator) {
        
        this.generator = generator;
        this.tree = generator.tree();
    }//END_FunctionMineGenerator

    /** Generates the code-mine text for an F_DECL chain. */
    String generateMines(Element fDecl) {

        mine.setLength(0);
        emitFunctionDecls(fDecl);
        return sanitize(mine.toString());
    }//END_generateMines

    /** Emits each function declaration in an F_DECL chain. */
    private void emitFunctionDecls(Element fDecl) {

        List<Element> kids = tree.kids(fDecl);
        if (kids.isEmpty()) return;

        emitFunctionMine(kids.get(0));
        emitFunctionDecls(kids.get(1));
    }//END_emitFunctionDecls

    /** Emits the code-mine description for a function or procedure. */
    private void emitFunctionMine(Element fType) {

        List<Element> kids = tree.kids(fType);
        String kind = SyntaxTree.contents(kids.get(0));
        Element nameLeaf = kids.get(1);
        Element vDecl = kids.get(3);
        Element bodyP = kids.get(6);

        FuncSymbol f = generator.symbols()
                .functionAt(SyntaxTree.id(nameLeaf));

        if (f == null) {
            throw new IllegalStateException(
                    "No function symbol bound to declaration node id="
                            + SyntaxTree.id(nameLeaf));
        }

        String header = "void".equals(kind)
                ? "begin of code-mine for void-procedure " + f.uniqueName()
                : "begin of code-mine for Function " + f.uniqueName();
        mine.append(header).append('\n');

        mine.append("Parameters for inlining:\n");
        emitParameterDeclarations(vDecl);

        mine.append("Body of the procedure:\n");
        emitBody(bodyP);

        if ("num".equals(kind)) {
            Element returnTerm = kids.get(9);
            String r = f.uniqueName() + generator.freshTemp();

            StringBuilder saved = generator.swapEmitTarget(mine);
            try {
                generator.translateTerm(returnTerm, r);
            } finally {
                generator.swapEmitTarget(saved);
            }

            mine.append("end of code-mine for Function ")
                    .append(f.uniqueName()).append('\n');
            mine.append("The result is found in the return value ")
                    .append(r).append('\n');
        } else {
            mine.append("end of code-mine for void-procedure ")
                    .append(f.uniqueName()).append('\n');
        }
    }//END_emitFunctionMine

    /** Emits parameter declarations for a V_DECL chain. */
    private void emitParameterDeclarations(Element vDecl) {

        List<Element> kids = tree.kids(vDecl);
        if (kids.isEmpty()) {
            mine.append(" \n");
            return;
        }

        Element nameLeaf = kids.get(0);
        Element rest = kids.get(1);

        VarSymbol v = generator.symbols()
                .variableAt(SyntaxTree.id(nameLeaf));

        if (v == null) {
            throw new IllegalStateException(
                    "No symbol bound to parameter node id="
                            + SyntaxTree.id(nameLeaf));
        }

        mine.append(generator.numericType()).append(' ')
                .append(v.uniqueName()).append(" ;\n");
        emitParameterDeclarations(rest);
    }//END_emitParameterDeclarations

    /** Emits a function body and any nested function code-mine descriptions. */
    private void emitBody(Element p) {
        Element bodyVDecl = tree.child(p, "v_decl");
        if (bodyVDecl != null) {
            emitParameterDeclarations(bodyVDecl);
        }

        Element nestedFDecl = tree.child(p, "f_decl");
        if (nestedFDecl != null) {
            emitFunctionDecls(nestedFDecl);
        }

        Element bodyAlgo = tree.child(p, "algo");
        if (bodyAlgo == null) {
            throw new IllegalStateException("No <algo> under function <p>");
        }

        StringBuilder savedOut = generator.swapEmitTarget(mine);
        try {
            generator.translateAlgo(bodyAlgo);
        } finally {
            generator.swapEmitTarget(savedOut);
        }
    }//END_emitBody

    /** Replaces inner comment delimiters so the code-mine stays a single comment. */
    private static String sanitize(String s) {
        return s.replace("/*", "(*").replace("*/", "*)");
    }//END_sanitize
}//END_FunctionMineGenerator