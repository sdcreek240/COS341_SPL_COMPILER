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
// USER-DEFINED-NAME -> #(0|1|...|9|a|...|z)*_
USER_ID     : '#' [0-9a-z]* ;

// NUM -> 0_ | (-|eps)0.(digit)*(nonzero)_ | (-|eps)(nonzero)(digit)*.(digit)*(nonzero)_ | (-|eps)(nonzero)(digit)*_
NUM_LIT     : '0'
            | '-'? '0' '.' DIGIT* NONZERO
            | '-'? NONZERO DIGIT* '.' DIGIT* NONZERO
            | '-'? NONZERO DIGIT* ;

fragment DIGIT    : [0-9];
fragment NONZERO  : [1-9];

// STRING -> "(,|.|:|-|?|!|0..9|a..z)*"_
STRING      : '"' (',' | '.' | ':' | '-' | '?' | '!' | [0-9a-z])* '"' ;

// Symbols
LEFT_PAREN  : '(';
RIGHT_PAREN : ')';
LEFT_BRACE  : '{';
RIGHT_BRACE : '}';
COLON       : ':';
SEMICOLON   : ';';
ASSIGN_OP   : '=';

// Whitespace
WS          : [ \t\r\n]+ -> skip;

//-----------------------------------------------------------------------------------------------

// Parser rules

// SPL_PROG -> P $ - for now we saying its psuedo and wont appear in a valid program
spl_prog    : p EOF ;

p           : v_decl ':' f_decl ':' algo ;

v_decl      : USER_ID v_decl
            | /* epsilon */ ;

f_decl      : f_type f_decl
            | /* epsilon */ ;

f_type      : VOID USER_ID '(' v_decl ')' '{' p RETURN '}'
            | NUM  USER_ID '(' v_decl ')' '{' p RETURN '(' term ')' '}' ;

algo        : (instr ';')* ;

instr       : PRINT outp
            | NOP
            | COMMENT STRING
            | assign
            | call
            | branch
            | loop ;

outp        : '(' term ')'
            | STRING ;

call        : USER_ID '(' input ')' ;

input       : term* ;

assign      : USER_ID '=' term ;

term        : USER_ID
            | NUM_LIT
            | call
            | MOD '(' term term ')'
            | ADD '(' term term ')'
            | SUB '(' term term ')'
            | MUL '(' term term ')'
            | DIV '(' term term ')'
            | NEG '(' term ')' ;

branch      : IF bool THEN '{' algo '}' ELSE '{' algo '}' ;

bool        : NOT    '(' bool ')'
            | AND    '(' bool bool ')'
            | OR     '(' bool bool ')'
            | EQ     '(' term term ')'
            | LARGER '(' term term ')'
            | LESSER '(' term term ')' ;

loop        : cond bool DO '{' algo '}'
            | DO '{' algo '}' cond bool ;

cond        : WHILE
            | UNTIL ;

//END_PArserRules