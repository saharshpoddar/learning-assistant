```instructions
---
applyTo: "**/*.java"
name: 'Clean Code'
description: 'Clean code practices, code smells to avoid, and quality heuristics for Java files'
---

# Clean Code Practices

## Method Quality
- Methods do ONE thing — if you need the word "and" to describe it, it does too much
- Max 30 lines per method — extract helpers when longer
- Max 3 parameters — use a parameter object or builder if more
- No side effects — a method named `checkPassword` should NOT initialize a session
- Prefer early returns (guard clauses) over deep nesting

## Naming Heuristics
- Names should reveal intent: `elapsedTimeInDays` not `d`
- Avoid disinformation: don't use `list` suffix for things that aren't Lists
- Make meaningful distinctions: `source` and `destination`, not `a1` and `a2`
- Use pronounceable names: `generationTimestamp` not `genymdhms`
- Boolean names: `isValid`, `hasPermission`, `canExecute`, `shouldRetry`

## Code Smells to Watch For
- **Long Method** → Extract smaller methods with descriptive names
- **Large Class** → Split by responsibility (SRP)
- **Feature Envy** → Method uses another class's data more than its own → move it
- **Data Clumps** → Same group of fields/params appear together → extract a class
- **Primitive Obsession** → Using `String email` everywhere → create an `Email` value object
- **Shotgun Surgery** → One change requires edits in many classes → consolidate
- **Divergent Change** → One class changes for multiple unrelated reasons → split it

## Error Handling
- Throw exceptions for exceptional conditions, not for flow control
- Catch specific exceptions: `FileNotFoundException`, not `Exception`
- Include context in error messages: what failed, what was expected, what was actual
- Never swallow exceptions with empty catch blocks
- Use try-with-resources for ALL AutoCloseable resources

## Comments
- Good: explain WHY a non-obvious decision was made
- Good: warn about consequences ("this is O(n²), don't use for large datasets")
- Bad: restating what the code does (`// increment i` before `i++`)
- Bad: commented-out code — delete it, version control remembers
```
