# Code Generator – Design Decisions (Phases 3a & 3b)

Short record of the decisions we made where the specification is ambiguous,
incomplete, or needs a concrete choice. Each entry: **Decision** and **Why**.

---

## 1. Target language and build

**Decision:** The generator writes `target.cpp` (C++). Preferred compiler: **g++**.

```
g++ target.cpp -o target
gcc -x c++ target.cpp -lstdc++ -o target     # alternative via gcc
```

**Why:** The spec requires `std::cout` and `#include <iostream>`, which are C++.
A plain C compiler cannot compile them. Announcement #34 allows C++ output.
Plain `gcc target.cpp` fails at link time (no libstdc++), hence the extra flags.

## 2. Numeric type (`typ` = `int` or `float`)

**Decision:** One type for the whole program, chosen by scanning the whole tree
(including function bodies), first match wins:

1. tree contains `mod` -> `int`
2. tree contains `div` -> `float`
3. any NUM literal contains `.` -> `float`
4. otherwise -> `int`

**Why:** The spec says `typ` follows what the type analyser found: whole numbers ->
`int`, real numbers -> `float`. Phase 2b guarantees `mod` and `div` never coexist and
that `mod` programs have no decimal literals. A whole-number program compiled as
`float` prints wrongly (`1234567` -> `1.23457e+06`).

## 3. Boolean translation (deviation from the literal formulas)

**Decision:** Every Boolean is translated into jumps to an explicit *true* label and
*false* label. A comparison emits `if ( x op y ) goto True;` and only omits the
`goto False;` when the false label is the very next label (fall-through).
`not` swaps the labels; `and`/`or` use a fresh middle label.
`while` and `do…until` are handled by choosing the label pair, not by wrapping in `not`.

**Why:** The spec's formulas (`not` = swap target/dummy, `and` via two `not`s,
`while` = `until not(...)`) fall through into the wrong code when a nested `not`,
`and`, or a false `while` condition is hit. Explicit labels give the same shape for
simple conditions and are correct for all nestings. No `else`, no Boolean operators
and no boolean variables appear in the output, as required.

## 4. Labels

**Decision:** Every label is emitted as `Lx: ;` followed by its comment.

**Why:** A label must be followed by a statement. A label at the end of a block is
rejected by older compilers and by `-pedantic-errors`. The empty statement makes the
output valid everywhere.

## 5. Fall-through label handling

**Decision:** The "next label" used to skip redundant gotos is saved before and
restored after each branch/loop/Boolean translation.

**Why:** Inlined function bodies may contain their own branches and loops inside a
condition; restoring prevents them from clobbering the outer value.

## 6. Strings and literals

**Decision:** `?` inside a print string is emitted as `\?`. 

**Why:** `??x` sequences can be read as trigraphs under strict `-std` modes; a
non-ASCII dash is not valid in C++ source.

## 7. Empty constructs and declarations

**Decision:** Empty `V_DECL`, `F_DECL` and `INPUT` are emitted as one blank space.
Variables are declared exactly as `typ name ;` (no initial value).

**Why:** Literal compliance with the Part 3b hints.

## 8. Program layout

**Decision:** Output order: code-mine comment block -> `#include <iostream>` ->
`int main()` -> `{` -> variable declarations -> translated ALGO -> `return 0;` -> `}`.

**Why:** Part 3b, rules for `SPL_PROG` and `P`.

## 9. Functions: inlining and the code-mine

**Decision:**
- Every function is written once, as readable text, inside a single block comment
  (the **code-mine**). Comments inside the mine are rewritten (`/*`->`(*`, `*/`->`*)`)
  because C comments cannot nest.
- Calls are inlined by **regenerating the function body from the syntax tree** at
  each call site, not by copying text.
- Arguments are evaluated in the caller's scope first, then assigned to the
  parameters, then the body runs, then `result = r ;`.
- Each inlined body is wrapped in `{ … }`.
- A nested `P` (inside a function) emits only locals + ALGO. Sub-functions are
  listed in their parent's mine text and inlined where called; they never appear
  as executable code of their own.

**Why:** Copying text would duplicate temporaries and labels (illegal in C++).
Regenerating gives fresh temporaries and labels per copy; the block scope allows the
same parameter and local names to be declared once per copy. Nested inlining works
because the scope rules only let a function call functions declared in its own `P`.

## 10. Calls: lookup and checks

**Decision:** A call is resolved through the symbol table by the **node ID** of the
called name, never by spelling. A call with the wrong number of arguments is reported
as an error: `Function <name> expects N argument(s) but was called with M`.

**Why:** A variable and a function may share a user name; node IDs are unambiguous.
Phase 2b does not check argument counts.

## 11. Output formatting

**Decision:** `print` emits exactly `std::cout << …;` with no separators or newlines.

**Why:** Literal compliance with the Part 3a rules. Consecutive prints are
concatenated.
