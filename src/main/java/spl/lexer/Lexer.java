package spl.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Lexer {

    public List<Token> tokenize(String source) {
        Objects.requireNonNull(source, "Source text cannot be null.");

        List<Token> tokens = new ArrayList<>();

        int position = 0;
        int line = 1;
        int column = 1;

        while (position < source.length()) {
            char current = source.charAt(position);

            // handle spaces
            if (current == ' ') {
                position++;
                column++;
                continue;
            }

            // handle Unix/macOS line endings
            if (current == '\n') {
                position++;
                line++;
                column = 1;
                continue;
            }

            // handle carriage returns and Windows CRLF line endings
            if (current == '\r') {
                position++;

                if (position < source.length()
                        && source.charAt(position) == '\n') {
                    position++;
                }

                line++;
                column = 1;
                continue;
            }

            TokenType type = symbolType(current);

            if (type != null) {
                tokens.add(
                        new Token(
                                type,
                                Character.toString(current),
                                line,
                                column));

                position++;
                column++;
                continue;
            }

            throw new LexicalException(
                    "Unrecognised character '" + current + "'.",
                    line,
                    column);
        }

        tokens.add(
                new Token(
                        TokenType.EOF,
                        "",
                        line,
                        column));

        return List.copyOf(tokens);
    }

    private TokenType symbolType(char character) {
        return switch (character) {
            case '(' -> TokenType.LEFT_PAREN;
            case ')' -> TokenType.RIGHT_PAREN;
            case '{' -> TokenType.LEFT_BRACE;
            case '}' -> TokenType.RIGHT_BRACE;
            case ':' -> TokenType.COLON;
            case ';' -> TokenType.SEMICOLON;
            case '=' -> TokenType.ASSIGN;
            case '$' -> TokenType.DOLLAR;
            default -> null;
        };
    }
}