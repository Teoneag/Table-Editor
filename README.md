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

**<font color="red">ToDo add description</font>**

By [Teodor Neagoe](https://github.com/Teoneag)

</div>
<img src="gifs/Table-Editor Preview.gif" alt="Table-Editor"/>
</div>

## Getting Started

### Prerequisites

- Os: Windows

## Download

### 1. Clone the repository

```bash
git clone https://github.com/Teoneag/Table-Editor
```

### 2. Build & run

To build it run

```bash
./gradlew build
```

And then to run it run

```bash
java -cp build/libs/Table-Editor-1.0-SNAPSHOT.jar com.teoneag.Main
```

Or you can run it directly from gradle (but IO is a bit slower)

```bash
./gradlew run -q --console=plain
```

Or you can use IntelliJ IDEA to run it. (open the project and run the Main class)

## Usage

**<font color="red">ToDo add usage code</font>**

```java

```

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

## Plan -> Actual: 

Chronological order. Planned time -> actual time
- read + make initial plan: 30m -> 34m
- swing example from GeeksForGeeks: 20m -> 5m
- read similar projects: 20m

## ToDo

- GitHub description

### Refactor

- 

### Fix

- 

### Features

- 