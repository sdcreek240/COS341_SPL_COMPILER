package spl.frontend.exceptionHandling;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import spl.antlr.SPLLexer;

public final class DebugPrinter {

    private DebugPrinter() {}

    public static void printTokens(CommonTokenStream tokens) {

        for (Token t : tokens.getTokens()) {

            System.out.printf("Token %-20s Lexeme %-10s Line %d Column %d%n",
                SPLLexer.VOCABULARY.getSymbolicName(t.getType()),
                t.getText(), t.getLine(), t.getCharPositionInLine() + 1);
        }//END_t
    }//END_printTokens

    public static void printTree(ParseTree tree, Parser parser) {

        System.out.println("Parse tree: " + tree.toStringTree(parser));
    }//END_printTree
}//END_DebugPrinter