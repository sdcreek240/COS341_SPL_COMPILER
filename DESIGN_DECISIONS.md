# Design Decisions: SPL Code Generator (Phases 3a/3b)

## 1. Overview
The generator takes the type-checked syntax tree and the Phase 2a symbol table and writes one C++ file (`target.cpp` by default): a non-executable code-mine comment, then an executable `main()`. Calls are inlined, so no runtime stack is needed. `CodeGenerator` drives output; one class per concern handles terms, Booleans, instructions, loops, the code-mine and call inlining.

## 2. Numeric type
`CodeGenerator.decideNumericType` runs once over the whole tree before output: `mod` gives `int`; else `div` gives `float`; else any decimal literal gives `float`; else `int`. That type is used for every variable, temporary, parameter and return variable. The mod/div conflict is rejected in Phase 2b.

## 3. Boolean translation
The spec's page-7 and rule, taken literally, produces the same translation as or and therefore fails for three of the four boolean input combinations: it jumps to the true label as soon as the first operand is true, and it jumps to true when the first operand is false and the second is true. The implementation instead uses the textbook's rightStart pattern (jump to B when A is true, jump to false when A is false), which is correct for all four combinations.

## 4. Loop translation
Labels follow the spec: `ls, le, d` for condition-first loops and `ld, le` for body-first loops. `until` and `do-while` are translated directly. `while` is `until` with T and F swapped (the output of `until not(BOOL)`, as the spec's transformation prescribes), and `do-until` is `do-while` with T and F swapped. Comments follow the translated form: `until`/`do`/`exit` for condition-first loops, `do`/`while`/`exit` for body-first loops.

## 5. Branch translation
Labels are allocated `La`, `Lb`, `Lc`. Output: condition (T=`La`, F=`Lb`), `Lb:` else-code, `goto Lc;`, `La:` then-code, `Lc:`. The fall-through label is set to `Lb` for the condition only and then restored, so nested constructs do not see each other's value.

## 6. Function inlining (Phase 3b)
The code-mine is documentation only: one outer comment, with inner `/*` and `*/` rewritten to `(*` and `*)` once, after all mine text is assembled. Each call regenerates the callee from the tree instead of copying from the mine, giving fresh temporaries and labels per call (C++ labels have function scope, so duplicates would not compile).

At a call site: evaluate all arguments in the caller's scope, left to right, into fresh temporaries; open `{ /* inlined call of fN */`; declare all parameters, then assign all; emit locals and body. For `num`, declare `r = fN ++ vNk`, translate the return term into it and copy `r` to the caller's result variable. `void` skips the copy-back.

## 7. Errors and safety
Before any callee code is emitted, the inliner throws for a wrong argument count, a `void` procedure used as a term, and a `num` function used as a statement. Output is written only after generation succeeds, so a failed run leaves no partial file; the program entry point also guards against a stale `target.cpp` passing as fresh output.

## 8. Testing
Golden fixtures (`functions/*.txt` against `*.expected.c`) check exact output for `void`/`num`, zero to two parameters, a repeated call and a loop in a callee. Runtime fixtures compile the generated C++ and check what it prints. Negative fixtures check rejection messages.

## 9. Deviations from the spec text
- **Two-label contract.** Spec: a comparison emits only `if ( x op y ) goto label`. We also emit `goto F` (unless fall-through) so compound Booleans and `while` reach the right target.
- **`and`.** Spec: both operands as `not(...)` with swapped labels. We use `rightStart`; the spec version is wrong (section 3).
- **`or`.** Spec: two sequential translations. We add `rightStart` so B1's false jump lands on B2 and B2 receives the real F.
- **Loop comments.** Spec: `while` is the `until` translation. We print `/* until */` for `while` and `/* while */` for `do-until`, matching the translated form.