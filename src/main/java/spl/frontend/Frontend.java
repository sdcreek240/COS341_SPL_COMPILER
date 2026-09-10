package spl.frontend;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import spl.antlr.SPLLexer;
import spl.antlr.SPLParser;

public class Frontend {

    private final boolean debug;

    public Frontend(boolean debug) {

        this.debug = debug;
    }

    /**
     * Full pipeline
     * @param filePath - path to the input file
     * @return ParseTree representing the program
     */
    public ParseTree processFile(Path filePath) {

        try {

            // lex
            CommonTokenStream tokens = lexFile(filePath);

            //lexing failed
            if (tokens==null)
                return null;

            //parse
            ParseTree tree =  parseTokens(tokens);

            return tree;
        } catch (FrontendException e) {
            handleError(e);
            return null;
        }

    }//END_processFile

    //🎅's little helpers

    /**
     * Lex the input file and return a CommonTokenStream
     * @param filePath - path to the input file
     * @return CommonTokenStream containing all tokens
     * @throws FrontendException
     */
    private CommonTokenStream lexFile(Path filePath) throws FrontendException {

        try {

            String source = Files.readString(filePath);
            CharStream input = CharStreams.fromString(source);

            SPLLexer lexer = new SPLLexer(input);
            
            lexer.removeErrorListeners();
            lexer.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer,
                                        Object offendingSymbol,
                                        int line, int charPositionInLine,
                                        String msg,
                                        RecognitionException e) {
                    throw new FrontendException("Lexing", msg, line, charPositionInLine + 1);
                }
            });

            CommonTokenStream tokens =  new CommonTokenStream(lexer);

            //Debug
            if (debug) {

                tokens.fill();

                for (Token t: tokens.getTokens()){

                    System.out.printf("Token %-20s Lexeme %-10s Line %d Column %d%n",
                        SPLLexer.VOCABULARY.getSymbolicName(t.getType()),
                        t.getText(),
                        t.getLine(),
                        t.getCharPositionInLine() + 1
                    );
                }//END_t
            }//END_debug

            return tokens;
        } catch (IOException e) {

            throw new FrontendException("Lexing", "I/O error while reading file", -1, -1);
        }
    }//END_lexFile

    /**
     * Parse the token stream and return the parse tree
     * @param tokens - token stream produced by the lexer
     * @return the ParseTree
     * @throws FrontendException
     */
    private ParseTree parseTokens(CommonTokenStream tokens) throws FrontendException{

        SPLParser parser = new SPLParser(tokens);

        // Error handling for parser
        parser.removeErrorListeners();
        parser.addErrorListener(new DiagnosticErrorListener());
        parser.setErrorHandler(new BailErrorStrategy());

        try {

            // Construct tree
            ParseTree tree = parser.spl_prog();

            //Debug
            if (debug) {
                System.out.println("Parse tree: " + tree.toStringTree(parser));
            }

            return tree;
        } catch (ParseCancellationException e) {

            // Wie se skuld was dit
            Token offending = parser.getCurrentToken();

            int line = offending.getLine();
            int col = offending.getCharPositionInLine()+1;

            throw new FrontendException(
                "Parsing",
                "Grammar error near token '" + offending.getText() + "'", line, col
            );
        }
    }//END_parseTokens

    /**
     * Handles FrontendExceptions
     * @param e
     */
    private void handleError(FrontendException e) {
        System.out.printf(
            "%s error at line %d, column %d: %s%n",
            e.getStage(), e.getLine(), e.getColumn(), e.getMessage()
        );
    }
}//END_Frontend