---
name: java-build
description: >
  Compile and run Java source files from the command line.
  Use when asked to build, compile, run, execute, or troubleshoot Java compilation errors.
---

# Java Build Skill

## Project Info
- **Language:** Java 21+
- **Source directory:** `src/`
- **Entry point:** `Main.java`
- **Build tool:** None (manual compilation)

## Compile a Single File

```sh
javac src/Main.java
```

## Compile All Java Files

```sh
javac src/*.java
```

## Run

```sh
java -cp src Main
```

## Compile and Run in One Step

```sh
javac src/Main.java && java -cp src Main
```

## Windows (PowerShell)

```powershell
javac src\Main.java; java -cp src Main
```

## Common Errors

| Error | Cause | Fix |
|---|---|---|
| `'javac' is not recognized` | Java not on PATH | Set `JAVA_HOME` and add to PATH |
| `cannot find symbol` | Typo in class/method name, or missing import | Check spelling, add `import` statement |
| `class not found` | Wrong classpath or class name | Use `-cp src` and match the class name exactly |
| `unreported exception` | Checked exception not handled | Add `try/catch` or `throws` declaration |
| `incompatible types` | Type mismatch in assignment/return | Check that variable types match |
| `reached end of file while parsing` | Missing closing brace `}` | Count opening and closing braces |
| `illegal start of expression` | Syntax error (missing semicolon, wrong keyword) | Check the line above the error |

## Check Java Version

```sh
java --version
javac --version
```

Both should show 21 or higher.
