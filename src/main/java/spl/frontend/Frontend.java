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
     * @throws IOException
     */
    public ParseTree processFile(Path filePath) {

        // lex
        CommonTokenStream tokens = lexFile(filePath);

        //parse
        ParseTree tree =  parseTokens(tokens);

        return tree;
    }//END_processFile

    //🎅's little helpers

    /**
     * Lex the input file and return a CommonTokenStream
     * @param filePath - path to the input file
     * @return CommonTokenStream containing all tokens
     * @throws IOException - do we want to define our own exceptions?
     */
    private CommonTokenStream lexFile(Path filePath) {

        try {
            String source = Files.readString(filePath);
            CharStream input = CharStreams.fromString(source);

            SPLLexer lexer = new SPLLexer(input);
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

            handleError(new FrontendException("Lexing", "I/O error while reading file", -1, -1));
            return null;
        } catch (RuntimeException e) {

            handleError(new FrontendException("Lexing", "Unexpected lexical error", -1, -1));
            return null;
        }
    }//END_lexFile

    /**
     * Parse the token stream and return the parse tree
     * @param tokens - token stream produced by the lexer
     * @return the ParseTree
     */
    private ParseTree parseTokens(CommonTokenStream tokens) {

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

            handleError(new FrontendException(
                "Parsing",
                "Grammar error near token '" + offending.getText() + "'", line, col
            ));
            return null;
        }
    }//END_parseTokens

    private void handleError(FrontendException e) {
        System.out.printf(
            "%s error at line %d, column %d: %s%n",
            e.getStage(), e.getLine(), e.getColumn(), e.getMessage()
        );
    }
}//END_Frontend