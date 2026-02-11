# 📋 Path-Specific Instructions — Detailed Guide

> **What:** Markdown files with coding rules that automatically activate when you edit files matching a glob pattern.  
> **Where:** `.github/instructions/*.instructions.md`  
> **How to use:** Just save them — they activate automatically. No manual action needed.

---

## 📌 What Are Path-Specific Instructions?

These are **conditional rules**. Instead of burdening every Copilot request with every rule, you say:

> *"Only apply these test rules when I'm editing files in `test/`"*

This keeps each request focused and avoids overwhelming Copilot's context window.

### Real-World Analogy

| Type | Analogy |
|---|---|
| `copilot-instructions.md` | Company-wide employee handbook (applies to everyone) |
| `*.instructions.md` | Department-specific rules (only when you're in that department) |

---

## 📄 File Format

```markdown
---
applyTo: "**/*.java"
---

# Rules for Java Files

Your rules go here.
Written in Markdown.
```

### The Frontmatter

The top section between `---` markers is YAML **frontmatter**. It controls when the file activates:

| Field | Required? | Description | Example |
|---|---|---|---|
| `applyTo` | **Recommended** | Glob pattern — instructions activate when current file matches | `"**/*.java"` |
| `name` | No | Display name in UI | `'Java Standards'` |
| `description` | No | Shown on hover in Chat view | `'Coding conventions for Java files'` |
| `excludeAgent` | No | Hide from specific Copilot features | `"code-review"` or `"coding-agent"` |

> **If `applyTo` is omitted:** The file is NOT applied automatically. You'd have to manually attach it to a chat prompt.

---

## 🎯 Glob Pattern Reference

Globs define which files trigger the instructions. Here's the complete syntax:

| Pattern | What It Matches | Example Matches |
|---|---|---|
| `*` | All files in current directory only | `Foo.java`, `Bar.java` |
| `**` or `**/*` | All files recursively everywhere | Every file in the project |
| `*.java` | `.java` files in current directory | `Foo.java` (not `src/Foo.java`) |
| `**/*.java` | `.java` files recursively everywhere | `src/Foo.java`, `src/a/b/Foo.java` |
| `src/*.java` | `.java` files directly in `src/` | `src/Foo.java` (not `src/sub/Foo.java`) |
| `src/**/*.java` | `.java` files recursively under `src/` | `src/Foo.java`, `src/sub/Foo.java` |
| `**/*.xml` | All XML files anywhere | `pom.xml`, `config/app.xml` |
| `**/*.{java,kt}` | Java AND Kotlin files everywhere | All `.java` and `.kt` files |
| `**/test/**/*.java` | Java files under any `test/` folder | `src/test/FooTest.java` |
| `**/*.ts,**/*.tsx` | Multiple patterns (comma-separated) | All TypeScript files |

### Pattern Gotchas

| ❌ Common Mistake | ✅ Correct Pattern | Why |
|---|---|---|
| `*.java` | `**/*.java` | Without `**`, only matches root-level files |
| `src/test/` | `src/test/**` | Need `/**` to match files inside the folder |
| `*.java *.xml` | `**/*.java,**/*.xml` | Use comma to separate patterns, not space |

---

## ✍️ Complete Examples

### Example 1: Java Source Files

```markdown
---
applyTo: "**/*.java"
---

# Java Coding Conventions

## Naming
- Classes: `UpperCamelCase` (e.g., `CustomerService`)
- Methods/variables: `lowerCamelCase` (e.g., `getCustomerName`)
- Constants: `UPPER_SNAKE_CASE` (e.g., `MAX_RETRY_COUNT`)
- Packages: all lowercase (e.g., `com.example.service`)

## Structure
- One public class per file
- Class name must match filename
- Order: fields → constructors → public methods → private methods

## Best Practices
- Use `final` for variables that don't change
- Prefer `var` for local variables when type is obvious (Java 10+)
- Always close resources with try-with-resources
- Never catch `Exception` or `Throwable` — catch specific exceptions
- Use `Objects.requireNonNull()` for null-checking parameters
```

---

### Example 2: Test Files

```markdown
---
applyTo: "**/test/**/*.java,**/*Test.java,**/*Tests.java"
name: 'Test Conventions'
---

# Test File Standards

## Framework
- Use JUnit 5 (`@Test`, `@BeforeEach`, `@AfterEach`)
- Use AssertJ for fluent assertions: `assertThat(result).isEqualTo(expected)`

## Naming
- Test class: `<ClassName>Test.java` (e.g., `CalculatorTest.java`)
- Test method: `should_<expected>_when_<condition>`
  - Example: `should_returnZero_when_dividingZeroByAny`
- Use `@DisplayName` for readable names in reports

## Structure (Arrange-Act-Assert)
```java
@Test
@DisplayName("Adding two positive numbers returns their sum")
void should_returnSum_when_addingPositiveNumbers() {
    // Arrange
    Calculator calc = new Calculator();
    
    // Act
    int result = calc.add(2, 3);
    
    // Assert
    assertThat(result).isEqualTo(5);
}
```

## Rules
- One assertion concept per test
- No logic in tests (no if/else, no loops)
- Use `@ParameterizedTest` for multiple inputs
- Mock external dependencies, don't mock the class under test
```

---

### Example 3: Configuration / XML Files

```markdown
---
applyTo: "**/*.xml,**/*.properties,**/*.yml,**/*.yaml"
name: 'Configuration Files'
---

# Configuration File Rules

- Never hardcode secrets, passwords, or API keys
- Use environment variables or property placeholders: `${DB_PASSWORD}`
- Always include a comment explaining non-obvious settings
- Keep config files sorted alphabetically when order doesn't matter
- Use descriptive property names: `database.connection.timeout` not `db.conn.to`
```

---

### Example 4: Markdown / Documentation Files

```markdown
---
applyTo: "**/*.md"
name: 'Documentation Standards'
---

# Documentation Conventions

- Use ATX-style headings (`#` prefix, not underline style)
- One sentence per line (better for version control diffs)
- Use fenced code blocks with language identifiers: ```java, ```sh, ```xml
- Include a brief description at the top of each document
- Use relative links for internal references, not absolute paths
- Tables: use consistent column alignment
```

---

## 📂 How to Create a New Instruction File

### Step-by-Step

```
1. Navigate to:              .github/instructions/
2. Create a new file:        <descriptive-name>.instructions.md
3. Add the frontmatter:      ---
                              applyTo: "your/glob/pattern/**/*.java"
                              ---
4. Write your rules below the frontmatter in Markdown
5. Save — it takes effect immediately
```

### Using VS Code Command

1. Press `Ctrl+Shift+P`
2. Type: `Chat: New Instructions File`
3. Choose **"Workspace"**
4. Enter filename
5. Edit the generated template

---

## ✅ How to Verify Instructions Are Loading

1. Open a file that should match your glob pattern
2. Open Copilot Chat (`Ctrl+Shift+I`)
3. Ask any question
4. After Copilot responds, expand **"References"** at the top of the response
5. Your instruction file should appear in the list

Alternative: Right-click in Chat → **Diagnostics** → see all loaded instructions.

---

## 💡 Tips

- **One concern per file** — don't mix database rules with UI rules
- **Keep each file focused** — 50-200 lines is ideal
- **Be specific** — "Use `lowerCamelCase` for methods" beats "follow naming conventions"
- **Include code examples** — 5 lines of code teaches more than a paragraph of text
- **Test your globs** — open a file that should match, ask Copilot something, expand References to verify
- **Multiple files can match** — if a file matches multiple patterns, ALL matching instruction files are loaded together
- **Use `name` field** — makes it easier to identify in References/Diagnostics

---

## 🧪 Experiments to Try

1. **Create a `*.java` instruction** with a naming rule → open a Java file → ask Copilot to write a method → does it follow your rule?
2. **Create two instruction files** with overlapping patterns → verify both load (check References)
3. **Try different glob patterns** → create a `.md` instruction, a `test/` instruction, and see when each activates
4. **Remove `applyTo`** from an instruction file → notice it no longer loads automatically

---

## 🔗 Links

- [VS Code: Custom Instructions](https://code.visualstudio.com/docs/copilot/customization/custom-instructions)
- [GitHub: Path-Specific Instructions](https://docs.github.com/en/copilot/customizing-copilot/adding-repository-custom-instructions-for-github-copilot)
- ← Back to [main guide](../README.md)
