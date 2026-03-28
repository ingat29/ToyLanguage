# Toy Language Interpreter

A multi-threaded Abstract Syntax Tree (AST) interpreter for a custom programming language similar to Java, developed from the ground up using Java. This project serves as a comprehensive demonstration of systems programming, memory management, and software architecture.

## Technical Highlights

* **Architecture and Design Patterns**: Built using a strict **Model-View-Controller (MVC)** pattern to ensure high maintainability and separation of concerns. The system relies on **Interface-Driven Design** and **Object-Oriented Programming (OOP)** principles.
* **Concurrency and Multithreading**: Implemented parallel execution of program states using a fixed thread pool (**ExecutorService**). It features a `fork` mechanism that allows for dynamic thread creation, where child threads share the **Heap** and **File Table** but maintain independent execution stacks.
* **Memory Management**: Developed a custom **Tracing Garbage Collector** using the Java Stream API. The collector identifies reachable memory addresses across all active program threads and automatically deallocates unused heap entries.
* **Static Type Analysis**: Includes a **Type Checker** that performs a semantic pass over the AST before execution. This ensures type safety by validating expressions and statements against a type environment, catching errors before the program runs.
* **Custom Data Structures (ADTs)**: Implemented essential generic collections from scratch, including a **Stack** (for execution flow), **Dictionary** (for symbol and file tables), and **Heap** (for dynamic memory allocation).
* **Graphical User Interface (GUI)**: Created an interactive dashboard using **JavaFX**. The UI provides a real-time visualization of the interpreter's internal state, including the execution stack, heap, and symbol tables.

## Tech Stack

* **Language**: Java 11+
* **GUI Framework**: JavaFX

## Core Language Features

* **Data Types**: Supports Integers, Booleans, Strings, and Reference types.
* **Memory Operations**: Explicit heap allocation (`new`), reading (`readHeap`), and writing (`writeHeap`).
* **Flow Control**: Support for conditional `if-else` branching and `while` loops.
* **File I/O**: Robust file handling including `open`, `read`, and `close` operations.
* **Concurrency**: Parallel execution via the `fork` statement.

## Project Structure

* **model/**: Defines the language grammar (Expressions, Statements, Types) and the Program State.
* **controller/**: Manages the execution flow, the thread pool, and the garbage collection logic.
* **repository/**: Handles state persistence and logging of execution steps to text files.
* **view/gui/**: Contains the JavaFX controllers and FXML layouts for the visual interface.

## How to Run

The project includes two interfaces:
1. **GUI Mode**: Launch `MainGUI.java` to use the interactive JavaFX interface.
2. **CLI Mode**: Launch `Interpreter.java` for a text-based menu-driven experience.
