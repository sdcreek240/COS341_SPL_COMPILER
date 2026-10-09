package spl.backend.scoping;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tree crawler for the scope rules of SPL (Part 2a).
 * Reads tree.xml, checks the rules with the help of SymbolTable, and adds a
 * <symbol> element (v1, v2, ... / f1, f2, ...) to every USER-DEFINED-NAME leaf.
 * Throws SemanticException at the first violation; the output file is only
 * written if the whole program is semantically correct.
 */
public final class SemanticAnalyser {

    private final SymbolTable symbols = new SymbolTable();
    private final Map<Integer, Element> nodes = new HashMap<>();
    private final Document doc;

    private SemanticAnalyser(Document doc) {
        this.doc = doc;
        for (Node n = doc.getDocumentElement().getFirstChild(); n != null; n = n.getNextSibling()) {
            if (n instanceof Element e) {
                nodes.put(id(e), e);
            }
        }
    }

    // ------------------------------------------------------------ entry point

    /** Reads {@code in}, analyses it, writes the annotated tree to {@code out} (may be the same file). */
    public static SymbolTable analyse(Path in, Path out) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in.toFile());
            stripWhitespace(doc.getDocumentElement());

            SemanticAnalyser a = new SemanticAnalyser(doc);
            Element root = a.nodes.get(0);
            a.symbols.enterScope();                 // Scope Level 0
            a.visitP(a.child(root, "p"));
            a.symbols.exitScope();

            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            tf.transform(new DOMSource(doc), new StreamResult(out.toFile()));
            return a.symbols;
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            throw new IllegalStateException("could not analyse " + in + ": " + e.getMessage(), e);
        }
    }

    // ------------------------------------------------------------ the crawl

    /** A P node: V_DECL, F_DECL, ALGO. Runs in the scope that is currently open. */
    private void visitP(Element p) {
        declareVariables(child(p, "v_decl"), false);
        List<Element> functions = declareFunctions(child(p, "f_decl"));   // all names first
        for (Element fType : functions) {
            visitFunction(fType);
        }
        visitUses(child(p, "algo"));
    }

    /** void/num NAME ( V_DECL ) { P  return... }  -> opens Scope Level l+1. */
    private void visitFunction(Element fType) {
        symbols.enterScope();
        declareVariables(child(fType, "v_decl"), true);     // parameters first, so locals can't mask them

        Element body = child(fType, "p");
        visitP(body);                                        // locals share the parameters' scope

        boolean afterBody = false;                           // the return expression sits behind the body
        for (Element k : kids(fType)) {
            if (afterBody) {
                visitUses(k);
            }
            if (k == body) {
                afterBody = true;
            }
        }
        symbols.exitScope();
    }

    /** Walks a right-recursive V_DECL chain (name leaf + next V_DECL) and declares every name. */
    private void declareVariables(Element vDecl, boolean isParameter) {
        Element v = vDecl;
        while (!kids(v).isEmpty()) {
            List<Element> k = kids(v);
            Element nameLeaf = k.get(0);
            VarSymbol s = symbols.declareVariable(value(nameLeaf), id(nameLeaf), isParameter);
            annotate(nameLeaf, s.uniqueName());
            v = k.get(1);
        }
    }

    /** Walks the F_DECL chain (F_TYPE + next F_DECL), declares each function, returns the F_TYPE nodes. */
    private List<Element> declareFunctions(Element fDecl) {
        List<Element> fTypes = new ArrayList<>();
        Element f = fDecl;
        while (!kids(f).isEmpty()) {
            List<Element> k = kids(f);
            Element fType = k.get(0);
            List<Element> fk = kids(fType);
            String returnType = contents(fk.get(0));         // "void" or "num"
            Element nameLeaf = fk.get(1);
            int paramCount = countNames(child(fType, "v_decl"));

            FuncSymbol s = symbols.declareFunction(value(nameLeaf), id(nameLeaf), returnType, paramCount);
            annotate(nameLeaf, s.uniqueName());
            fTypes.add(fType);
            f = k.get(1);
        }
        return fTypes;
    }

    /** Resolves every name USED below {@code e}: call names as functions, all others as variables. */
    private void visitUses(Element e) {
        if (isLeaf(e)) {
            if ("USER-DEFINED-NAME".equals(contents(e))) {
                if (isCallName(e)) {
                    annotate(e, symbols.resolveFunction(value(e), id(e)).uniqueName());
                } else {
                    annotate(e, symbols.resolveVariable(value(e), id(e)).uniqueName());
                }
            }
            return;
        }
        for (Element k : kids(e)) {
            visitUses(k);
        }
    }

    /**
     * A name is a function call when it is directly followed by a "(" part:
     * instr -> NAME instrTail, term -> NAME termTail (tail starting with "("),
     * or the older call -> NAME ( input ) shape.
     */
    private boolean isCallName(Element leaf) {
        Element parent = nodes.get(Integer.parseInt(text(leaf, "parent")));
        List<Element> siblings = kids(parent);
        int i = siblings.indexOf(leaf);
        if ("call".equals(contents(parent)) && i == 0) {
            return true;
        }
        if (i >= 0 && i + 1 < siblings.size()) {
            List<Element> next = kids(siblings.get(i + 1));
            return !next.isEmpty() && "(".equals(contents(next.get(0)));
        }
        return false;
    }

    // ------------------------------------------------------------ XML helpers

    private int countNames(Element vDecl) {
        int n = 0;
        for (Element v = vDecl; !kids(v).isEmpty(); v = kids(v).get(1)) {
            n++;
        }
        return n;
    }

    private void annotate(Element leaf, String uniqueName) {
        Element s = field(leaf, "symbol");
        if (s == null) {
            s = doc.createElement("symbol");
            leaf.appendChild(s);
        }
        s.setTextContent(uniqueName);
    }

    private static Element field(Element e, String tag) {
        for (Node n = e.getFirstChild(); n != null; n = n.getNextSibling()) {
            if (n instanceof Element c && c.getTagName().equals(tag)) {
                return c;
            }
        }
        return null;
    }

    private static String text(Element e, String tag) {
        Element f = field(e, tag);
        return f == null ? null : f.getTextContent().trim();
    }

    private static int id(Element e)            { return Integer.parseInt(text(e, "id")); }
    private static String contents(Element e)   { return text(e, "contents"); }
    private static String value(Element e)      { return text(e, "value"); }
    private static boolean isLeaf(Element e)    { return e.getTagName().equals("leaf"); }

    private List<Element> kids(Element e) {
        List<Element> out = new ArrayList<>();
        String s = text(e, "children");
        if (s != null && !s.isEmpty()) {
            for (String t : s.split("\\s+")) {
                out.add(nodes.get(Integer.parseInt(t)));
            }
        }
        return out;
    }

    private Element child(Element e, String label) {
        for (Element k : kids(e)) {
            if (label.equals(contents(k))) {
                return k;
            }
        }
        return null;
    }

    private static void stripWhitespace(Node n) {
        Node c = n.getFirstChild();
        while (c != null) {
            Node next = c.getNextSibling();
            if (c.getNodeType() == Node.TEXT_NODE && c.getTextContent().isBlank()) {
                n.removeChild(c);
            } else {
                stripWhitespace(c);
            }
            c = next;
        }
    }
}