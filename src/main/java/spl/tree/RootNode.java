
package spl.tree;

/**
 *
 * @author Keegan
 *
 * The root of the tree. Has no parent by definition,
 * but can still have children assigned to it.
 */
public class RootNode extends Node {
    public RootNode(String content) {
        super(content);
    }

    @Override
    public void setParent(Node parent) {
        throw new UnsupportedOperationException("RootNode cannot have a parent.");
    }
}
