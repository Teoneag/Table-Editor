<div align="center">
<pre>
████████╗ █████╗ ██████╗ ██╗     ███████╗    ███████╗██████╗ ██╗████████╗ ██████╗ ██████╗ 
╚══██╔══╝██╔══██╗██╔══██╗██║     ██╔════╝    ██╔════╝██╔══██╗██║╚══██╔══╝██╔═══██╗██╔══██╗
   ██║   ███████║██████╔╝██║     █████╗      █████╗  ██║  ██║██║   ██║   ██║   ██║██████╔╝
   ██║   ██╔══██║██╔══██╗██║     ██╔══╝      ██╔══╝  ██║  ██║██║   ██║   ██║   ██║██╔══██╗
   ██║   ██║  ██║██████╔╝███████╗███████╗    ███████╗██████╔╝██║   ██║   ╚██████╔╝██║  ██║
   ╚═╝   ╚═╝  ╚═╝╚═════╝ ╚══════╝╚══════╝    ╚══════╝╚═════╝ ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝
</pre>
<div align="right">

A simple table editor with formula support build in Java & Swing

By [Teodor Neagoe](https://github.com/Teoneag)

</div>
<img src="gifs/Table-Editor Preview.gif" alt="Table-Editor"/>
</div>

## Getting Started

### 0. Prerequisites

- Java 21 or higher

### 1. Clone the repository

```bash
git clone https://github.com/Teoneag/Table-Editor
```

### 2. Build & run

You can run it directly from gradle
```bash
./gradlew run
```

Or alternatively, first build it

```bash
./gradlew build
```

And then run it

```bash
java -cp build/libs/TableEditor-1.0-SNAPSHOT.jar com.teoneag.Main
```

Or you can use IntelliJ IDEA to run it. (open the project and run the TableEditor class)

### 3. Enjoy!

**⚠️ Don't forget to add <code>=</code> before the formula in the cell, just like in Excel! ⚠️**

## Features

### File management

- create new table with custom size
- save table as CSV
- open table from CSV

### Table

- cells can hold
  - normal text
  - formulas
    - the result of the formula is displayed
    - just double click it to edit it and you can see the formula
- move, resize columns

### Formulas

- Parentheses: ()
- Binary operators: +, -, *, /, %, ^
- Unary operators: - (negation)
- Named functions with variable nr of args: pow, sqrt, abs, log, log10, exp, sin, cos, tan, min, max, sum, avg
- References to table cells: A1, ZZ3
  - live formula update (when you change a cell, all cells that depend on it are updated)
- the design is very modular, so it's easy to add new functions or operators

## Design choices

- Save data: CSV
- UI
  - AppBar
    - File
      - New
        - Select rows and columns
      - Open
      - Save
      - Save As
      - Exit
    - Edit
      - Undo
      - Redo
      - Cut
      - Copy
      - Paste
    - Table
      - Insert Row
      - Insert Column
      - Delete Row
      - Delete Column
    - Help
      - About
  - Table
    - Show all saved ros and columns
    - Before any row, column: "-" button to delete
    - Between any row, column: "+" button to insert
    - See the result of any cell, when selected show the formula
    - If it's between "" it's text, otherwise it's a formula
    - If the formula is invalid, show an error message as a pop-up
- For decimal numbers use comma: 1,5
- calculating equation
  - tokenizer
  - parser AST
  - evaluator

## Task

Write a table editor with formula support.

- Language: Java
- UI framework: Swing
- Requirements
    - edit a cell directly in the cell itself (not like Excel where you have a separate input field)
    - optional: select/edit multiple cells at once
- Formulas (write parser myself)
  - parentheses
  - binary operators
  - unary operators
  - named functions
- references to table cells

Example:
```
pow(-2, A1 - 3) * (42 + B2)
```
- My choice: set of supported operators, functions names

## Grading

- the completeness of the implementation (the application should behave correctly with
  any user actions and any user input in the table cells)
- code quality (assume we will have to develop and maintain this project for a long time)
- simple, tidy interface (not expected a particularly beautiful UI)

## Plan -> Actual: 6:24h

Chronological order. Planned time -> actual time
- read + make initial plan: 30m -> 34m
- swing example from GeeksForGeeks: 20m -> 5m
- read similar projects: 20m -> 21m
  - https://github.com/japplis/Joeffice
- Sketch UI: 10m -> 11m
- UI in Swing: 30m -> 2:30h
- Formula parser & evaluator: 3h -> 5h
  - https://en.wikipedia.org/wiki/Shunting_yard_algorithm
  - fix comma in cell: 10m -> 20m
  - https://www.geeksforgeeks.org/expression-evaluation/
- test, fix & write tests: 3h -> 2h:38m
  - allow strings in cells, fix empty cell: 13m
  - make formulas auto update: 20m
  - not happened: fix infinite recursion if happens (a=b, b=a)
  - make table full screen: 1m
  - show row numbers: 20m
    - https://stackoverflow.com/questions/64867515/how-to-add-row-header-in-table
  - make open file work: 1:10h
  - refactor: 34m
  - fix create a new table pop up: if you change a value, and the other one is wrong, when u are asked again for the values, the good one is lost
  - refactor table: 1h
- documentation & push: 30m -> 30m

## ToDo

- bonus: multiple cells: 1h
- style: 1h
- final testing + fix todos: 1h
- when editing a cell, if you click on another cell, add that string to the current cell
- if trying to open invalide file, show its contents and suggest fix
- resize (add, remove cols)
- icon
- shortcuts (pressing esc on a cell should cancel the edit)
- implement the following
  - Comparison (==, !=, <, >, <=, >=)
  - Logical AND (&&)
  - Logical OR (||) 
  - Logical NOT (!)
  - if(condition, value_if_true, value_if_false) - Returns one of two values depending on the condition.
  - concat(string1, string2, ...)
- select column when clicking on the name
- theme changing
- import, export to excel
