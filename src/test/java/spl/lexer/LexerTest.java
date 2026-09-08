package spl.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

class LexerTest {

    @Test
    void recognisesSymbolsAndEndOfInput() {
        Lexer lexer = new Lexer();

        List<Token> tokens = lexer.tokenize(
                "( ) { } : ; = $");

        List<TokenType> actualTypes = tokens.stream()
                .map(Token::type)
                .toList();

        List<TokenType> expectedTypes = List.of(
                TokenType.LEFT_PAREN,
                TokenType.RIGHT_PAREN,
                TokenType.LEFT_BRACE,
                TokenType.RIGHT_BRACE,
                TokenType.COLON,
                TokenType.SEMICOLON,
                TokenType.ASSIGN,
                TokenType.DOLLAR,
                TokenType.EOF);

        assertEquals(expectedTypes, actualTypes);
    }

    @Test
    void tracksTokenLineAndColumn() {
        Lexer lexer = new Lexer();

        List<Token> tokens = lexer.tokenize(
                "(\n  )");

        Token leftParenthesis = tokens.get(0);
        Token rightParenthesis = tokens.get(1);
        Token endOfInput = tokens.get(2);

        assertEquals(1, leftParenthesis.line());
        assertEquals(1, leftParenthesis.column());

        assertEquals(2, rightParenthesis.line());
        assertEquals(3, rightParenthesis.column());

        assertEquals(2, endOfInput.line());
        assertEquals(4, endOfInput.column());
    }

    @Test
    void recognisesAllReservedKeywords() {
        Lexer lexer = new Lexer();

        List<Token> tokens = lexer.tokenize(
                "num void return "
                        + "print nop comment "
                        + "if then else do while until "
                        + "mod add sub mul div neg "
                        + "not and or eq larger lesser ");

        List<TokenType> actualTypes = tokens.stream()
                .map(Token::type)
                .toList();

        List<TokenType> expectedTypes = List.of(
                TokenType.NUM_KEYWORD,
                TokenType.VOID,
                TokenType.RETURN,

                TokenType.PRINT,
                TokenType.NOP,
                TokenType.COMMENT,

                TokenType.IF,
                TokenType.THEN,
                TokenType.ELSE,
                TokenType.DO,
                TokenType.WHILE,
                TokenType.UNTIL,

                TokenType.MOD,
                TokenType.ADD,
                TokenType.SUB,
                TokenType.MUL,
                TokenType.DIV,
                TokenType.NEG,

                TokenType.NOT,
                TokenType.AND,
                TokenType.OR,
                TokenType.EQ,
                TokenType.LARGER,
                TokenType.LESSER,

                TokenType.EOF);

        assertEquals(expectedTypes, actualTypes);
    }

    @Test
    void rejectsUnknownKeyword() {
        Lexer lexer = new Lexer();

        LexicalException exception = assertThrows(
                LexicalException.class,
                () -> lexer.tokenize("hello "));

        assertEquals(1, exception.getLine());
        assertEquals(1, exception.getColumn());
        assertEquals(
                "Lexical error at line 1, column 1: "
                        + "Unknown keyword 'hello'.",
                exception.getMessage());
    }
}