package spl.tree;

/**
 *
 * @author Keegan
 * A leaf node. Holds content and a parent pointer, but
 * cannot have children assigned to it.
 */
public class TerminalNode extends Node {
    public TerminalNode(String content) {
        super(content);
    }
 
    @Override
    public void addChild(Node child) {
        throw new UnsupportedOperationException("TerminalNode cannot have children.");
    }
}
