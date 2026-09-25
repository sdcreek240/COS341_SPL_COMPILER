package spl.frontend.io;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Path;

public final class XMLWriter {

    private final Parser parser;
    private final Document doc;
    private int nextId = 0;

    private XMLWriter(Parser parser, Document doc) {
        this.parser = parser;
        this.doc = doc;
    }
    /** Creates an XML element with the specified name and text content. */
    private Element textElement(String name, String text) {
        Element el = doc.createElement(name);
        el.setTextContent(text);
        return el;
    }

    /** Converts the parse tree to the required XML structure and writes it to {@code out}. */
    public static void write(ParseTree tree, Parser parser, Path out) {
        try {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().newDocument();
            Element top = doc.createElement("syntaxTree");
            doc.appendChild(top);
            new XMLWriter(parser, doc).addNode(tree, null, top);

            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            tf.transform(new DOMSource(doc), new StreamResult(out.toFile()));
        } catch (ParserConfigurationException | TransformerException e) {
            throw new IllegalStateException("could not write " + out + ": " + e.getMessage(), e);
        }
    }

    /** Adds this node and its subtree to the document; returns this node's id. */
    /** Adds this node and its subtree to the document; returns this node's id. */
    private int addNode(ParseTree node, Integer parentId, Element container) {
        int id = nextId++;
        boolean isRoot = (parentId == null);
        boolean isLeaf = node instanceof TerminalNode;

        Element el = doc.createElement(isRoot ? "root" : isLeaf ? "leaf" : "inner");
        el.appendChild(textElement("id", String.valueOf(id)));
        el.appendChild(textElement("contents", isLeaf
                ? node.getText()
                : parser.getRuleNames()[((RuleContext) node).getRuleIndex()]));

        Element children = null;
        if (!isLeaf) {
            children = doc.createElement("children");
            el.appendChild(children);
        }
        if (!isRoot) {
            el.appendChild(textElement("parent", String.valueOf(parentId)));
        }

        container.appendChild(el);   // before recursing, so the file is in pre-order

        java.util.List<String> childIds = new java.util.ArrayList<>();
        for (int i = 0; i < node.getChildCount(); i++) {
            ParseTree child = node.getChild(i);
            if (child instanceof TerminalNode t && t.getSymbol().getType() == Token.EOF) {
                continue;   // EOF is not a real token of the input
            }
            int childId = addNode(child, id, container);
            childIds.add(String.valueOf(childId));
        }

        if (children != null && !childIds.isEmpty()) {
            children.setTextContent(String.join(" ", childIds));
        }

        return id;
    }
}