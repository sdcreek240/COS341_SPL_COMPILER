package spl.frontend;

import org.antlr.v4.runtime.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import spl.antlr.SPLLexer;

public class Frontend {

    /**
     * Lex the input text file
     * @param filePath - path to the input file
     * @throws IOException
     */
    public static void lexFile(Path filePath) throws IOException {

        String source = Files.readString(filePath);

        CharStream input = CharStreams.fromString(source);

        SPLLexer lexer = new SPLLexer(input);

        Token token;

        while ((token = lexer.nextToken()).getType() != Token.EOF) {

            System.out.printf(
                "Token %-20s Text %-10s Line %d Column %d%n",
                SPLLexer.VOCABULARY.getSymbolicName(token.getType()),
                token.getText(),
                token.getLine(),
                token.getCharPositionInLine() + 1
            );
        }
    }
}