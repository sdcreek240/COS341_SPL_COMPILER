package spl.lexer;

public class LexicalException extends RuntimeException {
    private final int line;
    private final int column;

    public LexicalException(
            String message,
            int line,
            int column) {
        super(
                "Lexical error at line "
                        + line
                        + ", column "
                        + column
                        + ": "
                        + message);

        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}