# Code Verifier

A lexical and syntax analyzer for a small imperative teaching language,
written in Java with no parser generators or external libraries. Given a
source file, it reports whether the program is syntactically correct, or
the position of the first error and what was expected there.

Built for COSC 455 (Programming Languages) at Towson University, Fall 2024.

## What it does

- **Lexical analysis:** a hand-written scanner reads the file one character
  at a time, applies maximal munch, skips `//` comments, tracks line and
  column, and rejects characters and symbols outside the language.
- **Syntax analysis:** a recursive-descent parser with one procedure per
  grammar rule (`program`, `body`, `declarations`, `statement`,
  `expression`, `term`, `factor`, and so on).
- **Error reporting:** analysis stops at the first error and prints its
  `line:column`, the offending symbol, and the symbols that were expected.

## The language

| Element | Forms |
|---|---|
| Program | `program Name: ... .` |
| Declarations | `int x;` `bool flag;` |
| Statements | assignment `x := e`, `if ... then ... else ... end`, `while ... do ... end`, `print e` |
| Operators | `+ - * / mod`, `< =< = != >= >`, `and or not` |
| Literals | integers, `true`, `false` |
| Comments | `//` to end of line |

Statements are separated by `;`.

## Running it

Requires a JDK (developed on Java 21).

```
javac EvanDiglesProject2.java
java EvanDiglesProject2
```

The program asks for a file name, analyzes that file, and asks again until
you type `quit` or `exit`.

## Example

`examples/valid.txt`:

```
// Sums the integers from 1 to 10.
program Sum:
   int total;  int i;
   total := 0;
   i := 1;
   while i =< 10 do
      total := total + i;
      i := i + 1
   end;
   print total
.
```

```
No issues found. File is Syntactically Correct.
```

`examples/invalid.txt` uses `=` where `:=` is required on line 4:

```
Error at position 4:10 with symbol "=", Expected ":="
```

## Notes

- The whole analyzer is one source file, `EvanDiglesProject2.java`, as the
  course required.
- The commit history is the original course history, from the first
  scanner work through the finished parser.
- The two files in `examples/` were written for this README. The course's
  own test files are not included.

## About this README

This README was rewritten with Claude for the portfolio copy of the
project. The code and its comments are as submitted for the course.
