# 🛠️ Agent Skills — Detailed Guide

> **What:** Folders containing instructions + scripts + resources that Copilot loads automatically when the task matches.  
> **Where:** `.github/skills/<skill-name>/SKILL.md`  
> **How to use:** Just ask Copilot a matching question — skills load automatically.

---

## 📌 What Are Agent Skills?

Skills are the most powerful customization primitive. Unlike instructions (text only), skills can include:

- **Instructions** (the `SKILL.md` file)
- **Scripts** (shell scripts, batch files)
- **Templates** (code templates the AI can use)
- **Examples** (example files for reference)
- **Any other resource** the AI might need

Skills are also an **open standard** ([agentskills.io](https://agentskills.io/)) — they work across VS Code, Copilot CLI, and Copilot coding agent.

### Real-World Analogy

| Type | Analogy |
|---|---|
| Instructions | Telling someone how to cook (just words) |
| **Skills** | Giving them a recipe card + pre-measured ingredients + cooking tools |

### How Skills Differ from Other Primitives

| Aspect | Instructions | Prompts | **Skills** |
|---|---|---|---|
| Includes scripts? | ❌ | ❌ | ✅ **Yes** |
| Includes examples? | ❌ | Via links only | ✅ **In-directory** |
| Activation | Glob pattern match | Manual (`/command`) | **Auto** (task description match) |
| Portable? | VS Code + GitHub | VS Code only | **Open standard** |
| Location | Single `.md` file | Single `.md` file | **Folder** with `SKILL.md` + resources |

---

## 📁 Directory Structure

Each skill is a **folder** (not a single file):

```
.github/skills/
├── java-build/                    ← Skill folder
│   ├── SKILL.md                   ← Required: skill definition
│   ├── build-verify.sh            ← Optional: helper script
│   └── common-errors.md           ← Optional: reference material
│
├── run-tests/                     ← Another skill
│   ├── SKILL.md
│   └── examples/
│       └── SampleTest.java        ← Optional: template/example
│
└── create-class/                  ← Another skill
    ├── SKILL.md
    └── ClassTemplate.java         ← Optional: template file
```

---

## 📄 SKILL.md Format

```markdown
---
name: skill-name
description: >
  Detailed description of what this skill does and when to use it.
  Be specific so Copilot knows when to load this skill.
---

# Skill Instructions

Detailed instructions, procedures, and guidelines go here.
You can reference files in this directory using relative paths.
```

### Frontmatter Fields

| Field | Required? | Description | Constraints |
|---|---|---|---|
| `name` | **Yes** | Unique identifier for the skill | Lowercase, hyphens for spaces, max 64 chars |
| `description` | **Yes** | What the skill does and when to use it | Max 1024 chars. **Be specific!** |

> **Critical:** The `description` is how Copilot decides whether to load the skill. Vague descriptions = skill never loads. Specific descriptions = skill loads at the right time.

---

## 🧠 How Copilot Uses Skills (3-Level Loading)

Skills use **progressive disclosure** — they don't dump everything into context immediately:

```
Level 1: DISCOVERY (always loaded — very lightweight)
├── Copilot reads: name + description from frontmatter
├── Cost: ~10-20 tokens per skill
└── Happens: Every request

Level 2: INSTRUCTIONS (loaded when task matches)
├── Copilot reads: Full SKILL.md body
├── Cost: Depends on file size
└── Happens: Only when description matches your prompt

Level 3: RESOURCES (loaded on demand)
├── Copilot reads: Scripts, templates, examples in the directory
├── Cost: Only what it references
└── Happens: Only when the AI decides it needs them
```

**This means you can have many skills installed without performance impact** — only relevant ones get loaded.

---

## ✍️ Complete Examples

### Example 1: Java Build Skill

```
.github/skills/java-build/
├── SKILL.md
└── common-errors.md
```

**SKILL.md:**
```markdown
---
name: java-build
description: >
  Compile and run Java source files from the command line.
  Use when asked to build, compile, run, or troubleshoot Java compilation errors.
---

# Java Build Skill

## Compile a Single File
```sh
javac src/Main.java
```

## Compile All Files
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

## Common Errors
See [common-errors.md](./common-errors.md) for troubleshooting.
```

**common-errors.md:**
```markdown
# Common Java Build Errors

| Error | Cause | Fix |
|---|---|---|
| `cannot find symbol` | Typo in class/method name, missing import | Check spelling, add `import` |
| `class not found` | Wrong classpath or wrong class name | Use `-cp src` and correct class name |
| `unreported exception` | Checked exception not handled | Add `try/catch` or `throws` |
| `incompatible types` | Type mismatch in assignment | Check variable types match |
| `reached end of file` | Missing closing brace `}` | Count your braces |
```

---

### Example 2: Run Tests Skill

```
.github/skills/run-tests/
├── SKILL.md
└── examples/
    └── SampleTest.java
```

**SKILL.md:**
```markdown
---
name: run-tests
description: >
  Run JUnit 5 tests for the Java learning project.
  Use when asked to run tests, fix failing tests, or create new test files.
---

# Run Tests Skill

## Prerequisites
- JUnit 5 JARs on classpath
- Test files in `test/` directory

## Running Tests

### With Maven (if available)
```sh
mvn test
```

### With Gradle (if available)
```sh
./gradlew test
```

### Manual (no build tool)
```sh
javac -cp .:junit-platform-console-standalone.jar test/*.java
java -jar junit-platform-console-standalone.jar --class-path test --scan-class-path
```

## Test Conventions
- File naming: `<ClassName>Test.java`
- Method naming: `should_<expected>_when_<condition>`
- Use `@DisplayName` for readability
- Follow Arrange-Act-Assert pattern

## Template
See [SampleTest.java](./examples/SampleTest.java) for the standard test structure.
```

**examples/SampleTest.java:**
```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class SampleTest {

    @Test
    @DisplayName("Example: adding two numbers returns their sum")
    void should_returnSum_when_addingTwoNumbers() {
        // Arrange
        int a = 2, b = 3;

        // Act
        int result = a + b;

        // Assert
        assertEquals(5, result);
    }
}
```

---

### Example 3: Create Class Skill (with Template)

```
.github/skills/create-class/
├── SKILL.md
└── ClassTemplate.java
```

**SKILL.md:**
```markdown
---
name: create-class
description: >
  Create a new Java class following project conventions.
  Use when asked to create, scaffold, or generate a new Java class or interface.
---

# Create Class Skill

Use [ClassTemplate.java](./ClassTemplate.java) as the starting point.

## Conventions
- One public class per file
- Class name matches filename
- UpperCamelCase for class names
- lowerCamelCase for methods and fields
- Include Javadoc on public methods
- Include `toString()` for data classes
- Include `equals()` and `hashCode()` for data classes

## File Location
- Place in `src/` directory
- Use package directories if packages are defined

## Structure Order
1. Fields (private, then protected, then public)
2. Constructors
3. Public methods
4. Private methods
5. toString / equals / hashCode
```

**ClassTemplate.java:**
```java
/**
 * Brief description of what this class does.
 *
 * @author Your Name
 */
public class ClassName {

    private final String name;

    /**
     * Creates a new ClassName instance.
     *
     * @param name the name to assign
     */
    public ClassName(String name) {
        this.name = name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ClassName{name='" + name + "'}";
    }
}
```

---

## 📂 How to Create a Skill

### Step-by-Step

```
1. Create the skills root (if missing):  .github/skills/
2. Create a subdirectory:                .github/skills/<skill-name>/
3. Create the definition file:           .github/skills/<skill-name>/SKILL.md
4. Add YAML frontmatter with name (required) and description (required)
5. Write instructions in the body
6. (Optional) Add scripts, templates, or examples alongside SKILL.md
7. Save — skill is available immediately
```

### Choosing a Good `description`

The description is **critical** — it determines when the skill gets loaded:

| ❌ Bad (Too Vague) | ✅ Good (Specific) |
|---|---|
| `Helps with building` | `Compile and run Java source files from the command line. Use when asked to build, compile, run, or troubleshoot Java compilation errors.` |
| `Testing stuff` | `Run JUnit 5 tests for the Java project. Use when asked to run tests, fix failing tests, or create new test files.` |
| `Class creation` | `Create a new Java class following project conventions. Use when asked to create, scaffold, or generate a new Java class or interface.` |

**Tips for descriptions:**
- Include **action verbs** the user might use: "build", "compile", "run", "create", "test"
- Include **synonyms**: "build" AND "compile", "create" AND "scaffold"
- Be explicit about **when to use**: "Use when asked to..."

---

## 💡 Skill Ideas for Your Learning Project

| Skill Name | Description | Resources to Include |
|---|---|---|
| `java-build` | Compile and run Java files | Common errors doc |
| `run-tests` | Run JUnit tests | Test template |
| `create-class` | Scaffold a Java class | Class template |
| `create-interface` | Scaffold a Java interface | Interface template |
| `design-pattern` | Implement common design patterns | Pattern templates |
| `add-logging` | Add proper logging to a class | Logger setup example |
| `git-workflow` | Common Git operations | Command cheat sheet |

---

## 🧪 Experiments to Try

1. **Create `java-build` skill** → ask Copilot *"How do I compile Main.java?"* → does it use your skill?
2. **Check skill loading** → right-click Chat → Diagnostics → verify skill appears
3. **Add a resource file** to your skill → ask Copilot about it → does it read the resource?
4. **Make a vague description** → notice the skill doesn't load → make it specific → now it loads
5. **Create a skill with a script** → add a `.sh` or `.bat` file → ask Copilot to run it

---

## 🔗 Links

- [VS Code: Agent Skills](https://code.visualstudio.com/docs/copilot/customization/agent-skills)
- [Agent Skills Open Standard](https://agentskills.io/)
- [Community Skills](https://github.com/github/awesome-copilot/tree/main/skills)
- ← Back to [main guide](../README.md)
