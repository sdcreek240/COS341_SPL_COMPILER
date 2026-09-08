package spl.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Lexer {
    private static final Map<String, TokenType> KEYWORDS = Map.ofEntries(
            Map.entry("num", TokenType.NUM_KEYWORD),
            Map.entry("void", TokenType.VOID),
            Map.entry("return", TokenType.RETURN),

            Map.entry("print", TokenType.PRINT),
            Map.entry("nop", TokenType.NOP),
            Map.entry("comment", TokenType.COMMENT),

            Map.entry("if", TokenType.IF),
            Map.entry("then", TokenType.THEN),
            Map.entry("else", TokenType.ELSE),
            Map.entry("do", TokenType.DO),
            Map.entry("while", TokenType.WHILE),
            Map.entry("until", TokenType.UNTIL),

            Map.entry("mod", TokenType.MOD),
            Map.entry("add", TokenType.ADD),
            Map.entry("sub", TokenType.SUB),
            Map.entry("mul", TokenType.MUL),
            Map.entry("div", TokenType.DIV),
            Map.entry("neg", TokenType.NEG),

            Map.entry("not", TokenType.NOT),
            Map.entry("and", TokenType.AND),
            Map.entry("or", TokenType.OR),
            Map.entry("eq", TokenType.EQ),
            Map.entry("larger", TokenType.LARGER),
            Map.entry("lesser", TokenType.LESSER));

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

            // Recognise reserved keywords.
            if (isLowercaseLetter(current)) {
                int startPosition = position;
                int startColumn = column;

                while (position < source.length()
                        && isLowercaseLetter(source.charAt(position))) {
                    position++;
                    column++;
                }

                String lexeme = source.substring(
                        startPosition,
                        position);

                TokenType keywordType = KEYWORDS.get(lexeme);

                if (keywordType == null) {
                    throw new LexicalException(
                            "Unknown keyword '" + lexeme + "'.",
                            line,
                            startColumn);
                }

                tokens.add(
                        new Token(
                                keywordType,
                                lexeme,
                                line,
                                startColumn));

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

    private boolean isLowercaseLetter(char character) {
        return character >= 'a' && character <= 'z';
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