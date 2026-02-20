# Project Instructions — Learning Assistant

## Overview
- **Project:** Learning Assistant — a simple Java project for learning Copilot customization
- **Language:** Java 21+
- **Build:** Manual compilation (no build tool yet)
- **Purpose:** Hands-on experimentation with GitHub Copilot's customization features

## Project Structure
```
learning-assistant/
├── .github/              ← Copilot customization files (you're learning this!)
├── src/
│   └── Main.java         ← Entry point
└── .gitignore
```

## Coding Conventions

### Naming
- **Classes:** `UpperCamelCase` (e.g., `StudentManager`, `OrderService`)
- **Methods:** `lowerCamelCase` (e.g., `calculateTotal`, `getStudentName`)
- **Variables:** `lowerCamelCase`, descriptive (e.g., `totalPrice`, not `tp`)
- **Constants:** `UPPER_SNAKE_CASE` (e.g., `MAX_RETRY_COUNT`)
- **Packages:** all lowercase (e.g., `com.learning.service`)

### Code Style
- Use `final` for variables that don't change
- Prefer `var` for local variables when the type is obvious (Java 10+)
- One public class per file, class name matches filename
- Use `Logger` instead of `System.out.println` (except in learning examples)

### Methods
- Keep methods under 30 lines
- Each method should do exactly one thing
- Add Javadoc to all public methods:
  ```java
  /**
   * Calculates the total price including tax.
   *
   * @param price    the base price
   * @param taxRate  the tax rate as a decimal (e.g., 0.10 for 10%)
   * @return the total price with tax
   */
  public double calculateTotal(double price, double taxRate) { ... }
  ```

### Error Handling
- Catch specific exceptions, never generic `Exception`
- Always include a helpful error message
- Use try-with-resources for closeable resources

### File Organization (order within a class)
1. Static fields
2. Instance fields
3. Constructors
4. Public methods
5. Private/helper methods
6. `toString()`, `equals()`, `hashCode()`

## Do's and Don'ts

### Do:
- ✅ Use descriptive variable names (minimum 3 characters, except loop counters)
- ✅ Add comments explaining WHY, not WHAT
- ✅ Use `Objects.requireNonNull()` for null-checking constructor parameters
- ✅ Include `@Override` on all overridden methods
- ✅ Close resources with try-with-resources

### Don't:
- ❌ Don't use single-letter variables (except `i`, `j`, `k` in loops)
- ❌ Don't leave empty catch blocks
- ❌ Don't use `==` to compare Strings (use `.equals()`)
- ❌ Don't hardcode values — extract to constants
- ❌ Don't write methods longer than 30 lines

## Commit Guidelines

### Commit Messages
- Write **meaningful, descriptive** commit messages that summarize WHAT changed and WHY
- Use imperative mood: "Add smart discovery engine" not "Added smart discovery engine"
- First line: concise summary (≤ 72 characters)
- Optional body: explain context, motivation, or notable changes (wrap at 72 chars)
- Always **append author attribution** at the end of the commit message:
  - For AI-generated code: `— created by gpt`
  - For AI-assisted code with human edits: `— assisted by gpt`
  - For human-only code: no suffix needed

### Commit Scope
- Commit related changes together — one logical unit per commit
- Don't mix unrelated changes (e.g., a bug fix + a new feature) in one commit
- Stage and review before committing: `git diff --staged`

### Examples
```
feat: Add ResourceDiscovery smart search with relevance scoring

Implements three-mode discovery engine (specific, vague, exploratory)
with keyword-to-concept inference, relevance scoring across 7 dimensions,
and "did you mean?" suggestions for empty results.

— created by gpt
```
