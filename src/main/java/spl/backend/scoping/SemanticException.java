package spl.backend.scoping;
 
/** Thrown when an SPL program violates a scope/name rule. */
public class SemanticException extends RuntimeException {
    private final int nodeId;
 
    public SemanticException(String message, int nodeId) {
        super(message + " (tree node " + nodeId + ")");
        this.nodeId = nodeId;
    }
 
    public int nodeId() { return nodeId; }
}