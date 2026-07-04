# Custom-Lightweight-Stream-Processing-Language
A domain specific language focussed on lightweight, functional stream-processing. This is an engine that performs lexical analysis, parsing and execution, transforming source code into an abstract syntax tree for evaluation and to support functional composition via a pipe operator |> and user defined functions

---

## Features

- Custom-built Lexer, Parser, and Interpreter written in Java
- Functional pipe operator (`|>`) for readable data flow
- Stateful execution environment
- User-defined functions with `defun`
- Modular architecture for extending built-in operations
- Interactive interpreter for executing pipeline scripts

---

## Getting Started

This project is built entirely from scratch in Java and uses React for the fronted ide, implementing a complete **Lexer → Parser → Abstract Syntax Tree (AST) → Interpreter** pipeline to execute custom programs.

### Prerequisites

- Java Development Kit (JDK) **17** or later
- Node.js and npm is installed

---

## Installation

Clone the repository:

```bash
git clone https://github.com/DeveloperHeat/Custom-Lightweight-Stream-Processing-Language.git
cd Custom-Lightweight-Stream-Processing-Language
```

Compile the source code:

```bash
javac -d bin language/Main/main.java
java -cp bin language.Main.main
```

Run the React frontend:

```bash
cd my-language-ide
npm install
npm run dev
```

You can then:

- Write scripts directly into the frontend ide
- Enter pipeline scripts directly into the terminal, or
- Modify the interpreter to load scripts from files in the terminal.

---

## Example

### Define reusable functions

```
defun transform {
    double |> double |> double
}

defun triple {
    plus 6
}
```

### Execute a pipeline

```
// 10 -> (10×2×2×2) = 80 -> (80+6) = 86

10 |> transform |> triple |> add |> log
```

### Output

```text
Added: 86
LOG: 86
```

---

## Tech Stack

| Component | Technology |
|----------|------------|
| Languages | Java |
| Library | React |
| Architecture | Custom Interpreter |
| Compiler Stages | Lexer, Parser, AST, Evaluator |
| Programming Style | Functional Stream Processing |

---

## Project Architecture

The interpreter follows the classic language implementation pipeline:

```
Input
  │
  ▼
Lexer
  │
  ▼
Parser
  │
  ▼
Abstract Syntax Tree (AST)
  │
  ▼
Interpreter / Evaluator
  │
  ▼
Output
```

State is managed through an `Environment` object, allowing functions and runtime values to persist throughout execution.

---

## Extending The Project

Adding new built-in operations is straightforward.

The runtime stores all built-in functions inside `Environment.java` using a centralized `HashMap`.

Simply register a new function:

```java
functions.put("myFunction", ...);
```

Once registered, it becomes immediately available inside its pipelines.

---

## Troubleshooting

### `Expect '{'`

If you receive an error similar to:

```text
Expect '{'
```

check that every `defun` is properly enclosed with matching curly braces:

```
defun example {
    ...
}
```

Missing braces prevent the parser from correctly determining function boundaries.

---

## Example Pipeline

```
defun transform {
    double |> double |> double
}

defun triple {
    plus 6
}

10 |> transform |> triple |> add |> log
```

Execution flow:

```
10
 │
 ▼
double
 │
 ▼
20
 │
 ▼
double
 │
 ▼
40
 │
 ▼
double
 │
 ▼
80
 │
 ▼
plus 6
 │
 ▼
86
 │
 ▼
add
 │
 ▼
log
```

Output:

```text
Added: 86
LOG: 86
```

---

## Future Improvements

- File execution support
- Variables and assignment
- Conditional execution
- Loops and iteration
- Standard library of built-in stream operations
- Improved error reporting with source locations
- REPL enhancements and command history
