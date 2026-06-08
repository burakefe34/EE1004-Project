# Library Management System

This is our Object-Oriented Programming project for EE1004 / CSE2062 (Group 6). It is a Java
application that manages a small library of books, magazines and DVDs. Each type of item has a
different maximum loan period: 21 days for a book, 7 days for a magazine and 3 days for a DVD.

There are two ways to run it, and both use the same underlying classes:

- `Main` is the console version with a numbered menu.
- `LibraryGUI` is an optional Swing window that shows the items in a table with a search box and
  check-out / return buttons.

## Requirements

We used OpenJDK 21 (on VS Code, Windows 11). Any JDK 8 or later should work. No external
libraries are required, and the program does not read any input files. All data is entered while
the program is running.

## Compiling and running

From the folder that contains the `.java` files, run:

```
javac *.java
java Main
```

or, as a single command:

```
javac *.java && java Main
```

To start the graphical version instead of the console:

```
java LibraryGUI
```

The code compiles without errors and runs from the command line without an IDE.

## Source files

- `LibraryItem.java` - abstract base class shared by every item
- `Book.java`, `Magazine.java`, `DVD.java` - the three item types
- `Library.java` - keeps the list of items and handles add, check out, return, search and list
- `Main.java` - console menu (entry point)
- `LibraryGUI.java` - Swing interface (entry point)

All of the classes are in the default package, so the commands above work without any extra setup.

## Console menu

```
1. Add a new item
2. List all items
3. Check out an item
4. Return an item
5. Search by title
6. Exit
```
