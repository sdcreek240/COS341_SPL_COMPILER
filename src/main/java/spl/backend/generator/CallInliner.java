package spl.backend.generator;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import spl.backend.scoping.FuncSymbol;
import spl.backend.scoping.VarSymbol;
import spl.backend.tree.SyntaxTree;

/** Inlines validated SPL function and procedure calls at their call sites. */
final class CallInliner {

    private final CodeGenerator generator;
    private final SyntaxTree tree;

    /** Creates the function-call inliner. */
    CallInliner(CodeGenerator generator) {
        
        this.generator = generator;
        this.tree = generator.tree();
    }//END_CallInliner

    /**
     * Inlines a call and, for a numeric function, assigns its return value.
     */
    void inlineCall(
            Element callNameLeaf,
            Element inputNode,
            String resultVar,
            boolean expectValue) {

        FuncSymbol f = generator.symbols()
                .functionAt(SyntaxTree.id(callNameLeaf));

        if (f == null) {
            throw new IllegalStateException(
                    "No function symbol bound to call node id="
                            + SyntaxTree.id(callNameLeaf));
        }

        if (expectValue && "void".equals(f.returnType())) {
            throw new IllegalStateException(
                    "Procedure " + f.name()
                            + " returns no value and cannot be used in an expression");
        }

        if (!expectValue && !"void".equals(f.returnType())) {
            throw new IllegalStateException(
                    "Function " + f.name()
                            + " returns a value and cannot be used as a statement");
        }

        List<Element> argTerms = collectArgumentTerms(inputNode);
        if (argTerms.size() != f.paramCount()) {
            throw new CodeGenException(
                    "Function " + f.name() + " expects " + f.paramCount()
                            + " argument(s) but was called with " + argTerms.size());
        }

        // Evaluate arguments in caller scope and source order.
        List<String> argTemps = new ArrayList<>();
        for (Element argTerm : argTerms) {
            String tmp = generator.declareTemp();
            generator.translateTerm(argTerm, tmp);
            argTemps.add(tmp);
        }

        Element fType = findDeclaration(f);

        List<Element> fKids = tree.kids(fType);
        Element params = fKids.get(3);
        Element bodyP = fKids.get(6);
        Element returnTerm = "num".equals(f.returnType()) ? fKids.get(9) : null;

        List<VarSymbol> paramSyms = collectParameterSymbols(params);

        generator.emit("{ /* inlined call of " + f.uniqueName() + " */\n");

        // Declare parameters before assigning their argument values.
        for (VarSymbol pv : paramSyms) {
            generator.emit(
                    generator.numericType() + " " + pv.uniqueName() + " ;\n");
        }

        for (int i = 0; i < paramSyms.size(); i++) {
            generator.emit(
                    paramSyms.get(i).uniqueName() + " = "
                            + argTemps.get(i) + " ;\n");
        }

        emitBodyForInline(bodyP);

        if (expectValue) {
            String r = f.uniqueName() + generator.freshTemp();
            generator.emit(generator.numericType() + " " + r + " ;\n");
            generator.translateTerm(returnTerm, r);
            generator.emit(resultVar + " = " + r + " ;\n");
        }

        generator.emit("}\n");
    }//END_inlineCall

    /** Collects the TERM children of an INPUT chain in source order. */
    private List<Element> collectArgumentTerms(Element input) {

        List<Element> terms = new ArrayList<>();
        Element cur = input;

        while (cur != null) {
            List<Element> kids = tree.kids(cur);
            if (kids.isEmpty()) break;

            Element termNode = kids.get(0);
            if (!"term".equals(SyntaxTree.contents(termNode))) {
                throw new IllegalStateException(
                        "Malformed <input> at node id=" + SyntaxTree.id(cur)
                                + " — expected <term> as first child, got <"
                                + SyntaxTree.contents(termNode) + ">");
            }

            terms.add(termNode);

            if (kids.size() < 2) break;
            cur = kids.get(1);
        }

        return terms;
    }//END_collectArgumentTerms

    /** Collects the parameter symbols of a V_DECL chain in source order. */
    private List<VarSymbol> collectParameterSymbols(Element vDecl) {

        List<VarSymbol> syms = new ArrayList<>();
        Element cur = vDecl;

        while (cur != null) {
            List<Element> kids = tree.kids(cur);
            if (kids.isEmpty()) break;

            Element nameLeaf = kids.get(0);
            VarSymbol v = generator.symbols()
                    .variableAt(SyntaxTree.id(nameLeaf));

            if (v == null) {
                throw new IllegalStateException(
                        "No symbol bound to parameter node id="
                                + SyntaxTree.id(nameLeaf));
            }

            syms.add(v);
            cur = kids.get(1);
        }

        return syms;
    }//END_collectParameterSymbols

    /** Locates a function's f_type declaration in the syntax tree. */
    private Element findDeclaration(FuncSymbol f) {

        Element nameLeaf = tree.byId(f.declNodeId());
        if (nameLeaf != null) {
            Element parent = tree.byId(parentId(nameLeaf));
            if (parent != null
                    && "f_type".equals(SyntaxTree.contents(parent))) {
                return parent;
            }
        }

        Element found = scanForFType(tree.root(), f.uniqueName());
        if (found == null) {
            throw new IllegalStateException(
                    "Cannot locate the f_type declaration for "
                            + f.name() + " (" + f.uniqueName()
                            + "); tree may be corrupt");
        }

        return found;
    }//END_findDeclaration

    /** Searches a subtree for the f_type matching a unique function name. */
    private Element scanForFType(Element e, String uniqueName) {

        if ("f_type".equals(SyntaxTree.contents(e))) {
            List<Element> kids = tree.kids(e);
            if (kids.size() >= 2) {
                Element nameLeaf = kids.get(1);
                if (uniqueName.equals(SyntaxTree.text(nameLeaf, "symbol"))) {
                    return e;
                }
            }
        }

        for (Element k : tree.kids(e)) {
            Element found = scanForFType(k, uniqueName);
            if (found != null) return found;
        }

        return null;
    }//END_scanForFType

    /** Returns the parent node ID stored on an element. */
    private int parentId(Element e) {

        String p = SyntaxTree.text(e, "parent");
        return p == null ? -1 : Integer.parseInt(p);
    }//END_parentId

    /** Emits the executable local declarations and algorithm of an inlined body. */
    private void emitBodyForInline(Element bodyP) {

        Element bodyVDecl = tree.child(bodyP, "v_decl");
        if (bodyVDecl != null) {
            emitLocalDeclarations(bodyVDecl);
        }

        Element bodyAlgo = tree.child(bodyP, "algo");
        if (bodyAlgo == null) {
            throw new IllegalStateException(
                    "No <algo> under inlined function body");
        }

        generator.translateAlgo(bodyAlgo);
    }//END_emitBodyForInline

    /** Emits local variable declarations for a V_DECL chain. */
    private void emitLocalDeclarations(Element vDecl) {

        List<Element> kids = tree.kids(vDecl);
        if (kids.isEmpty()) return;

        Element nameLeaf = kids.get(0);
        Element rest = kids.get(1);

        VarSymbol v = generator.symbols()
                .variableAt(SyntaxTree.id(nameLeaf));

        if (v == null) {
            throw new IllegalStateException(
                    "No symbol bound to local variable node id="
                            + SyntaxTree.id(nameLeaf));
        }

        generator.emit(
                generator.numericType() + " " + v.uniqueName() + " ;\n");
        emitLocalDeclarations(rest);
    }//END_emitLocalDeclarations
}//END_CallInliner