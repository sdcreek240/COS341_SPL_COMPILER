package spl.tree;
import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    protected String content;
    protected Node parent;
    protected List<Node> children;

    public Node(String content) {
        this.content = content;
        this.children = new ArrayList<>();
    }

    public String getContent() { 
        return content;
    }
    
    public void setContent(String content) { 
        this.content = content; 
    }
    
    public Node getParent() { 
        return parent; 
    }
    
    public void setParent(Node parent) { 
        this.parent = parent; 
    }
    
    public List<Node> getChildren() { 
        return children; 
    }

    public void addChild(Node child) {
        children.add(child);
        child.setParent(this);
    }
}