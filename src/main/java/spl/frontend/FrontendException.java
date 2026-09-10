package spl.frontend;

//For exceptions thrown only by frontend - lex or parse
public class FrontendException extends Exception {

    private final String stage;

    private final int line;
    private final int column;

    public FrontendException(String stage, String message, int line, int column) {

        super(message);
        
        this.stage = stage;
        this.line = line;
        this.column = column;
    }//END_FrontendException

    public String getStage() { return stage; }
    public int getLine() { return line; }
    public int getColumn() { return column; }
}//END_FrontendException
