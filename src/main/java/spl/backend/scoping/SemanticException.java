package spl.backend.scoping;

/** Thrown when an SPL program violates a scope/name rule. */
public class SemanticException extends RuntimeException {
    private final int nodeId;

    public SemanticException(String message, int nodeId) {
        super(message + " (tree node " + nodeId + ")");
        this.nodeId = nodeId;
    }

    /** For errors whose wording is fixed by the specification (no node id appended).
     * @param message */
    public SemanticException(String message) {
        super(message);
        this.nodeId = -1;
    }

    public int nodeId() { return nodeId; }
}