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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Type analysis (Part 2b). Runs on the tree.xml that the scope analysis (2a) has
 * already annotated with <symbol> elements.
 *
 * Every node gets a <type> element, initially "unknown", which is replaced while the
 * tree is crawled. The types of names are also stored in the SymbolTable.
 * Throws SemanticException at the first violation; the file is written only if the
 * whole tree is type correct.
 */
public final class TypeAnalyser {

    private static final String UNKNOWN = "unknown";
    private static final String OK = "ok";
    private static final String NUMERIC = "numeric";
    private static final String BOOLEAN = "boolean";
    private static final String PROCEDURE = "procedure";

    private final SymbolTable symbols;
    private final Document doc;
    private final Map<Integer, Element> nodes = new TreeMap<>();

    private TypeAnalyser(Document doc, SymbolTable symbols) {
        this.doc = doc;
        this.symbols = symbols;
        for (Node n = doc.getDocumentElement().getFirstChild(); n != null; n = n.getNextSibling()) {
            if (n instanceof Element e) {
                nodes.put(id(e), e);
            }
        }
    }

    // ------------------------------------------------------------ entry point

    /** Reads {@code in}, type-checks it, writes the typed tree to {@code out} (may be the same file). */
    public static void analyse(Path in, Path out, SymbolTable symbols) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in.toFile());
            stripWhitespace(doc.getDocumentElement());

            TypeAnalyser a = new TypeAnalyser(doc, symbols);
            a.run();

            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            tf.transform(new DOMSource(doc), new StreamResult(out.toFile()));
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            throw new IllegalStateException("could not type-check " + in + ": " + e.getMessage(), e);
        }
    }

    private void run() {
        for (Element e : nodes.values()) {
            setType(e, UNKNOWN);                       // every node starts as "unknown"
        }
        checkFloatIntegerRule();                       // crude whole-tree rejection comes first
        check(nodes.get(0));                           // SPL_PROG
    }

    /** The crude mod/div rule, applied before any finer analysis. */
    private void checkFloatIntegerRule() {
        List<Element> mods = new ArrayList<>();
        List<Element> divs = new ArrayList<>();
        for (Element e : nodes.values()) {
            if (isLeaf(e)) {
                String c = contents(e);
                if ("mod".equals(c)) mods.add(e);
                if ("div".equals(c)) divs.add(e);
            }
        }
        if (!mods.isEmpty() && !divs.isEmpty()) {
            throw new SemanticException("A float-integer-conflict might perhaps be possible\n"
                    + "  'mod' only works on integers, used at: " + where(mods) + "\n"
                    + "  'div' only works on floats, used at: " + where(divs) + "\n"
                    + "  A program may use either mod or div, not both.");
        }
        if (!mods.isEmpty()) {
            for (Element e : nodes.values()) {
                if (isLeaf(e) && "NUM_LIT".equals(contents(e)) && value(e).contains(".")) {
                    throw new SemanticException("A float-integer-conflict might perhaps be possible\n"
                            + "  the number " + value(e) + " (" + where(List.of(e)) + ") has a decimal dot,\n"
                            + "  but the program uses mod at: " + where(mods) + "\n"
                            + "  With mod, every number in the program must be an integer.");
                }
            }
        }
    }

    /** "line 12 (node 40), line 30 (node 91)" - the line comes from the <line> element, if present. */
    private String where(List<Element> leaves) {
        StringBuilder sb = new StringBuilder();
        int shown = 0;
        for (Element e : leaves) {
            if (shown == 8) {
                sb.append(", ... (").append(leaves.size() - shown).append(" more)");
                break;
            }
            if (shown > 0) sb.append(", ");
            String line = text(e, "line");
            sb.append(line == null ? "" : "line " + line + " ").append("(node ").append(id(e)).append(")");
            shown++;
        }
        return sb.toString();
    }

    // ------------------------------------------------------------ the crawl

    /** Checks one inner node, records its type on the node, and returns it. */
    private String check(Element e) {
        String t;
        switch (contents(e)) {
            case "spl_prog": t = checkProg(e);   break;
            case "p":        t = checkP(e);      break;
            case "v_decl":   t = checkVDecl(e);  break;
            case "f_decl":   t = checkFDecl(e);  break;
            case "f_type":   t = checkFType(e);  break;
            case "algo":     t = checkAlgo(e);   break;
            case "instr":    t = checkInstr(e);  break;
            case "outp":     t = checkOutp(e);   break;
            case "input":    t = checkInput(e);  break;
            case "term":     t = checkTerm(e);   break;
            case "bool":     t = checkBool(e);   break;
            case "branch":   t = checkBranch(e); break;
            case "loop":     t = checkLoop(e);   break;
            case "cond":     t = OK;             break;   // while / until
            default: throw new IllegalStateException("unexpected node '" + contents(e) + "'");
        }
        setType(e, t);
        return t;
    }

    // SPL_PROG -> P
    private String checkProg(Element e) {
        require(check(child(e, "p")), OK, "the program", e);
        return OK;
    }

    // P -> V_DECL : F_DECL : ALGO
    private String checkP(Element e) {
        require(check(child(e, "v_decl")), OK, "the variable declarations", e);
        require(check(child(e, "f_decl")), OK, "the function declarations", e);
        require(check(child(e, "algo")), OK, "the algorithm", e);
        return OK;
    }

    // V_DECL -> epsilon | NAME V_DECL
    private String checkVDecl(Element e) {
        List<Element> k = kids(e);
        if (k.isEmpty()) {
            return OK;
        }
        require(check(k.get(1)), OK, "the rest of the declarations", e);
        setNameType(k.get(0), NUMERIC);                // declared variables and parameters are numeric
        return OK;
    }

    // F_DECL -> epsilon | F_TYPE F_DECL
    private String checkFDecl(Element e) {
        List<Element> k = kids(e);
        if (k.isEmpty()) {
            return OK;
        }
        require(check(k.get(0)), OK, "the function declaration", e);
        require(check(k.get(1)), OK, "the rest of the function declarations", e);
        return OK;
    }

    // F_TYPE -> void NAME ( V_DECL ) { P return }
    // F_TYPE -> num  NAME ( V_DECL ) { P return ( TERM ) }
    private String checkFType(Element e) {
        List<Element> k = kids(e);
        boolean returnsValue = "num".equals(contents(k.get(0)));
        Element name = k.get(1);

        require(check(child(e, "v_decl")), OK, "the parameters of " + value(name), e);   // parameters first
        require(check(child(e, "p")), OK, "the body of " + value(name), e);

        if (returnsValue) {
            require(check(child(e, "term")), NUMERIC, "the return value of " + value(name), e);
            setNameType(name, NUMERIC);
        } else {
            setNameType(name, PROCEDURE);
        }
        return OK;
    }

    // ALGO -> epsilon | INSTR ; ALGO
    private String checkAlgo(Element e) {
        List<Element> k = kids(e);
        if (k.isEmpty()) {
            return OK;
        }
        require(check(k.get(0)), OK, "the instruction", e);
        require(check(k.get(2)), OK, "the rest of the algorithm", e);
        return OK;
    }

    // INSTR -> print OUTP | nop | comment STRING | BRANCH | LOOP | ASSIGN | CALL
    private String checkInstr(Element e) {
        List<Element> k = kids(e);
        Element first = k.get(0);
        switch (contents(first)) {
            case "print":
                require(check(k.get(1)), OK, "the output", e);
                return OK;
            case "nop":
                return OK;
            case "comment":
                setType(k.get(1), OK);
                return OK;
            case "branch":
            case "loop":
                require(check(first), OK, "the " + contents(first), e);
                return OK;
            default:
                break;                                  // NAME instrTail: assignment or call
        }

        Element tail = k.get(1);
        List<Element> tk = kids(tail);
        if ("=".equals(contents(tk.get(0)))) {          // ASSIGN -> NAME = TERM
            require(check(tk.get(1)), NUMERIC, "the right-hand side of the assignment to " + value(first), e);
            require(nameType(first), NUMERIC, "the assigned name " + value(first), e);
            setType(tail, OK);
            return OK;
        }
        // CALL used as an instruction: only a procedure is allowed here
        String callType = checkCall(first, tail);
        require(callType, PROCEDURE, "the call of " + value(first) + " used as an instruction", e);
        return OK;
    }

    // CALL -> NAME ( INPUT ) : type_of(CALL) = type_of(NAME); the type is stored on the tail node
    private String checkCall(Element name, Element tail) {
        require(check(child(tail, "input")), OK, "the arguments of " + value(name), tail);
        String nameType = nameType(name);
        if (UNKNOWN.equals(nameType)) {
            throw error("the type of " + value(name) + " is unknown", name);
        }
        setType(tail, nameType);
        return nameType;
    }

    // OUTP -> ( TERM ) | STRING
    private String checkOutp(Element e) {
        List<Element> k = kids(e);
        if ("STRING".equals(contents(k.get(0)))) {
            setType(k.get(0), OK);
            return OK;
        }
        require(check(k.get(1)), NUMERIC, "the printed term", e);
        return OK;
    }

    // INPUT -> epsilon | TERM INPUT
    private String checkInput(Element e) {
        List<Element> k = kids(e);
        if (k.isEmpty()) {
            return OK;
        }
        require(check(k.get(0)), NUMERIC, "an argument", e);
        require(check(k.get(1)), OK, "the remaining arguments", e);
        return OK;
    }

    // TERM -> NAME | NUM | CALL | mod/div/add/sub/mul ( TERM TERM ) | neg ( TERM )
    private String checkTerm(Element e) {
        List<Element> k = kids(e);
        Element first = k.get(0);
        switch (contents(first)) {
            case "NUM_LIT":
                setType(first, NUMERIC);
                return NUMERIC;
            case "USER-DEFINED-NAME": {
                Element tail = k.get(1);
                if (kids(tail).isEmpty()) {             // TERM -> NAME
                    setType(tail, OK);
                    require(nameType(first), NUMERIC, "the name " + value(first), e);
                } else {                                // TERM -> CALL
                    require(checkCall(first, tail), NUMERIC,
                            "the call of " + value(first) + " used inside a term", e);
                }
                return NUMERIC;
            }
            case "neg":
                require(check(k.get(2)), NUMERIC, "the operand of neg", e);
                return NUMERIC;
            case "add":
            case "sub":
            case "mul":
            case "div":
            case "mod":
                require(check(k.get(2)), NUMERIC, "the left operand of " + contents(first), e);
                require(check(k.get(3)), NUMERIC, "the right operand of " + contents(first), e);
                return NUMERIC;
            default:
                throw new IllegalStateException("unexpected term '" + contents(first) + "'");
        }
    }

    // BOOL -> not ( BOOL ) | and/or ( BOOL BOOL ) | eq/larger/lesser ( TERM TERM )
    private String checkBool(Element e) {
        List<Element> k = kids(e);
        String op = contents(k.get(0));
        switch (op) {
            case "not" -> {
                require(check(k.get(2)), BOOLEAN, "the operand of not", e);
                return BOOLEAN;
            }
            case "and", "or" -> {
                require(check(k.get(2)), BOOLEAN, "the left operand of " + op, e);
                require(check(k.get(3)), BOOLEAN, "the right operand of " + op, e);
                return BOOLEAN;
            }
            case "eq", "larger", "lesser" -> {
                require(check(k.get(2)), NUMERIC, "the left operand of " + op, e);
                require(check(k.get(3)), NUMERIC, "the right operand of " + op, e);
                return BOOLEAN;
            }
            default -> throw new IllegalStateException("unexpected bool '" + op + "'");
        }
    }

    // BRANCH -> if BOOL then { ALGO } else { ALGO }
    private String checkBranch(Element e) {
        require(check(child(e, "bool")), BOOLEAN, "the condition of the if", e);
        for (Element k : kids(e)) {
            if ("algo".equals(contents(k))) {
                require(check(k), OK, "a branch of the if", e);
            }
        }
        return OK;
    }

    // LOOP -> COND BOOL do { ALGO } | do { ALGO } COND BOOL
    private String checkLoop(Element e) {
        require(check(child(e, "cond")), OK, "the loop keyword", e);
        require(check(child(e, "bool")), BOOLEAN, "the loop condition", e);
        require(check(child(e, "algo")), OK, "the loop body", e);
        return OK;
    }

    // ------------------------------------------------------------ type helpers

    private void require(String actual, String expected, String what, Element at) {
        if (!expected.equals(actual)) {
            throw error(what + " must be " + expected + " but is " + actual, at);
        }
    }

    private SemanticException error(String message, Element at) {
        return new SemanticException("type error: " + message, id(at));
    }

    /** Gives a declared name its type, on the leaf and in the symbol table. */
    private void setNameType(Element nameLeaf, String type) {
        symbols.setType(symbolOf(nameLeaf), type);
        setType(nameLeaf, type);
    }

    /** Reads a name's type from the symbol table and records it on the leaf. */
    private String nameType(Element nameLeaf) {
        String t = symbols.typeOf(symbolOf(nameLeaf));
        setType(nameLeaf, t);
        return t;
    }

    private String symbolOf(Element nameLeaf) {
        String s = text(nameLeaf, "symbol");
        if (s == null) {
            throw new IllegalStateException("name " + value(nameLeaf)
                    + " has no <symbol>: run the scope analysis first");
        }
        return s;
    }

    private void setType(Element e, String type) {
        Element t = field(e, "type");
        if (t == null) {
            t = doc.createElement("type");
            e.appendChild(t);
        }
        t.setTextContent(type);
    }

    // ------------------------------------------------------------ XML helpers

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
        throw new IllegalStateException("node " + id(e) + " has no child '" + label + "'");
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