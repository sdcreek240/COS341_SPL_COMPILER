package spl.frontend.exceptionHandling;

import java.util.List;
import java.util.Optional;

public final class Diagnostic {

    private final String stage;
    private final String message;
    private final int line;
    private final int column;
    private final List<String> expected;
    private final String sourceLine;

    public Diagnostic(String stage, String message, int line, int column,
                       List<String> expected, String sourceLine) {

        this.stage = stage;
        this.message = message;
        this.line = line;
        this.column = column;
        this.expected = expected;
        this.sourceLine = sourceLine;
    }//END_Diagnostic

    public String getStage()          { return stage; }
    public String getMessage()        { return message; }
    public int getLine()              { return line; }
    public int getColumn()            { return column; }
    public List<String> getExpected() { return expected; }
    public Optional<String> getSourceLine() { return Optional.ofNullable(sourceLine); }

    public String render() {

        StringBuilder sb = new StringBuilder();
        sb.append(stage).append(" error at line ").append(line)
          .append(", column ").append(column).append(": ").append(message);

        if (!expected.isEmpty()) {
            sb.append("\n  hint: expected ").append(String.join(" or ", expected));
        }

        if (sourceLine != null && column >= 1) {
            sb.append("\n  ").append(sourceLine);
            sb.append("\n  ").append(" ".repeat(column - 1)).append("^");
        }

        return sb.toString();
    }//END_render
}//END_Diagnostic