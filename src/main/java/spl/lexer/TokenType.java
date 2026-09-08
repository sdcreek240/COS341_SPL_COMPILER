package spl.lexer;

public enum TokenType {
    // Lexical categories
    NUMBER,
    USER_DEFINED_NAME,
    STRING,

    // Declaration keywords
    NUM_KEYWORD,
    VOID,
    RETURN,

    // Instruction and control-flow keywords
    PRINT,
    NOP,
    COMMENT,
    IF,
    THEN,
    ELSE,
    DO,
    WHILE,
    UNTIL,

    // Arithmetic keywords
    MOD,
    ADD,
    SUB,
    MUL,
    DIV,
    NEG,

    // Boolean keywords
    NOT,
    AND,
    OR,
    EQ,
    LARGER,
    LESSER,

    // Symbols
    LEFT_PAREN,
    RIGHT_PAREN,
    LEFT_BRACE,
    RIGHT_BRACE,
    COLON,
    SEMICOLON,
    ASSIGN,
    DOLLAR,

    // Internal end of input
    EOF
}