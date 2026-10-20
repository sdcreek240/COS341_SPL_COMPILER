package spl.backend.tree;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SyntaxTree {

    private final Document doc;
    private final Map<Integer, Element> nodes = new HashMap<>();

    private SyntaxTree(Document doc) {

        this.doc = doc;
        for (Node n = doc.getDocumentElement().getFirstChild(); n != null; n = n.getNextSibling()) {
            if (n instanceof Element e) {
                nodes.put(id(e), e);
            }
        }
    }

    /** Loads a tree.xml file.*/
    public static SyntaxTree load(Path xml) {

        try {

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xml.toFile());
            stripWhitespace(doc.getDocumentElement());
            return new SyntaxTree(doc);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new IllegalStateException("could not load syntax tree " + xml + ": " + e.getMessage(), e);
        }
    }//END_load

    public Document document() { return doc; }
    public Element byId(int id) { return nodes.get(id); }
    public Element root()      { return nodes.get(0); }

    //accessors

    public static Element field(Element e, String tag) {

        for (Node n = e.getFirstChild(); n != null; n = n.getNextSibling()) {
            if (n instanceof Element c && c.getTagName().equals(tag)) return c;
        }
        return null;
    }

    public static String text(Element e, String tag) {

        Element f = field(e, tag);
        return f == null ? null : f.getTextContent().trim();
    }

    public static int id(Element e)          { return Integer.parseInt(text(e, "id")); }
    public static String contents(Element e) { return text(e, "contents"); }
    public static String value(Element e)    { return text(e, "value"); }
    public static boolean isLeaf(Element e)  { return e.getTagName().equals("leaf"); }

    public List<Element> kids(Element e) {

        List<Element> out = new ArrayList<>();
        String s = text(e, "children");
        if (s != null && !s.isEmpty()) {
            for (String t : s.split("\\s+")) out.add(nodes.get(Integer.parseInt(t)));
        }
        return out;
    }

    /** First child whose `contents` equals {@code label}, or null. */
    public Element child(Element e, String label) {

        for (Element k : kids(e)) {
            if (label.equals(contents(k))) return k;
        }
        return null;
    }

    /** All children whose `contents` equals {@code label}, in order. */
    public List<Element> children(Element e, String label) {

        List<Element> out = new ArrayList<>();
        for (Element k : kids(e)) {
            if (label.equals(contents(k))) out.add(k);
        }
        return out;
    }

    //internals

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