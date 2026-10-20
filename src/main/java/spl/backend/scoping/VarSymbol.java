package spl.backend.scoping;

/** One declared variable (or function parameter). */
public record VarSymbol(String name, String uniqueName, int scopeLevel, int declNodeId, boolean isParameter) {}