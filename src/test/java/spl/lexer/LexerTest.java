package spl.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}