
package spl.tree;

/**
 *
 * @author Keegan
 * An internal node. Sits between the root and terminal
 * nodes, and can have both a parent and children.
 */
public class InnerNode extends Node {
    public InnerNode(String content) {
        super(content);
    }
}