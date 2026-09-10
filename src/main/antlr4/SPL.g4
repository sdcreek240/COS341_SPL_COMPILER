grammar SPL;

@header {
    package spl.antlr;
}

// Lexer rules (Tokens)
VOID        : 'void';
NUM         : 'num';
RETURN      : 'return';
PRINT       : 'print';
NOP         : 'nop';
COMMENT     : 'comment';
IF          : 'if';
THEN        : 'then';
ELSE        : 'else';
DO          : 'do';
WHILE       : 'while';
UNTIL       : 'until';

// Arithmetic operators
MOD         : 'mod';
ADD         : 'add';
SUB         : 'sub';
MUL         : 'mul';
DIV         : 'div';
NEG         : 'neg';

// Boolean operators
NOT         : 'not';
AND         : 'and';
OR          : 'or';
EQ          : 'eq';
LARGER      : 'larger';
LESSER      : 'lesser';

// Identifiers and literals
USER_ID     : '#' [0-9a-z]+;   // matches spec for user-defined names
NUM_LIT     : [0-9]+;          // numeric literal (simplified form)
STRING      : '"' [a-z0-9 ,.:!?-]* '"';

// Symbols
LEFT_PAREN  : '(';
RIGHT_PAREN : ')';
LEFT_BRACE  : '{';
RIGHT_BRACE : '}';
COLON       : ':';
SEMICOLON   : ';';
ASSIGN      : '=';
DOLLAR      : '$';

// Whitespace
WS          : [ \t\r\n]+ -> skip;

// END_Tokens -----------------------------------------------------------

// --- Parser rules ---
spl_prog    : p EOF ;

p           : v_decl ':' f_decl ':' algo ;

v_decl      : USER_ID v_decl
            | /* epsilon */ ;

f_decl      : f_type f_decl
            | /* epsilon */ ;

f_type      : VOID USER_ID '(' v_decl ')' '{' p RETURN '}'
            | NUM USER_ID '(' v_decl ')' '{' p RETURN '}' ;

algo        : instr*
            | /* epsilon */ ;

instr       : PRINT outp ';'
            | NOP ';'
            | COMMENT ';'
            | USER_ID ';'
            | branch
            | loop ;

outp        : term
            | STRING ;

term        : USER_ID
            | NUM_LIT
            | 'mod' term
            | 'add' term
            | 'sub' term
            | 'mul' term
            | 'div' term
            | 'neg' term ;

branch      : IF bool 'then' algo ('else' algo)? ;

loop        : WHILE bool 'do' algo
            | UNTIL bool 'do' algo
            | DO algo WHILE bool ;

bool        : 'not' bool
            | 'and' bool
            | 'or' bool
            | 'eq' term term
            | 'larger' term term
            | 'lesser' term term ;
