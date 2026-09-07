# COS341_SPL_COMPILER
A compiler for the SPL programming language, built as part of COS341. Implements lexical analysis and recursive descent parsing to produce structured XML syntax trees from SPL source code.

## Branching Strategy

- Feature branches: `feat/lexer`, `feat/parser`, `feat/tokenstream`, etc.
- **Pull requests required** for all merges into `main`
- At least **1 approving review** needed before merging

## Build

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="spl.Main"
```

## Project Structure
```
src/main/java/spl/
├── lexer/      # Tokenization
├── parser/     # Recursive descent parsing
├── tree/       # Node tree + NodeFactory
├── io/         # XMLWriter
└── Main.java   # Entry point
```


