package spl.backend.scoping;

/** One declared function. */
public record FuncSymbol(String name, String uniqueName, int scopeLevel, int declNodeId, String returnType, int paramCount) {}