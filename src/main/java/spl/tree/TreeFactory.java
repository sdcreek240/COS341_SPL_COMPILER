package spl.tree;

/**
 *
 * @author Keegan
 * Factory responsible for creating tree nodes, including
 * a single shared root for the tree.
 */
public class TreeFactory {
    private final RootNode root;
 
    public TreeFactory() {
        this.root = new RootNode("SPL_PROG");
    }
 
    public TreeFactory(String rootContent) {
        this.root = new RootNode(rootContent);
    }
 
    /**
     * Returns the root node of the tree.
     */
    public RootNode getTree() {
        return root;
    }
 
    /**
     * Creates a new terminal node with the given content.
     */
    public TerminalNode getTerminal(String content) {
        return new TerminalNode(content);
    }
 
    /**
     * Creates a new inner node with the given content.
     */
    public InnerNode getInner(String content) {
        return new InnerNode(content);
    }
}

/*
 * 
 * An EXAMPLE of how to use the tree
 * 
 * TreeFactory factory = new TreeFactory();
 * RootNode root = factory.getTree();
 * InnerNode branch = factory.getInner("branch-1");
 * TerminalNode leaf1 = factory.getTerminal("leaf-1");
 * TerminalNode leaf2 = factory.getTerminal("leaf-2");
 * root.addChild(branch);
 * branch.addChild(leaf1);
 * branch.addChild(leaf2);
 * 
 */
