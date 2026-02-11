# 🎯 Prompt Files — Detailed Guide

> **What:** Reusable task templates you invoke as **slash commands** in chat (e.g., `/explain`).  
> **Where:** `.github/prompts/*.prompt.md`  
> **How to use:** Type `/prompt-name` in VS Code Chat.

---

## 📌 What Are Prompt Files?

Prompt files are **recipes for common tasks.** Instead of typing a long, detailed prompt every time you want to explain code or generate a class, you save it as a file and invoke it with a slash command.

### Real-World Analogy

| Without Prompts | With Prompts |
|---|---|
| Type 10 lines of instructions every time you want a code review | Type `/review` and answer one question |
| Forget a step every third time | Steps are baked into the prompt |
| Each team member has different prompts | Everyone uses the same file from Git |

### How It Looks in Practice

```
You type:    /explain
Copilot asks: (uses current file automatically)
Copilot:     *gives a structured explanation of Main.java*
```

---

## 📄 File Format

```markdown
---
name: explain
description: 'Explain the current file in plain language'
agent: ask
tools: ['search', 'codebase']
---

Your task instructions go here.
Can use variables like ${file} or ${input:question:What do you want to know?}
```

### Frontmatter Fields

| Field | Required? | Description | Example |
|---|---|---|---|
| `name` | No | Slash command name. Defaults to filename. | `explain` |
| `description` | No | Short description shown in the prompt picker | `'Explain the current file'` |
| `agent` | No | Agent to run this: `ask`, `agent`, `plan`, or a custom agent | `agent` |
| `model` | No | AI model to use | `Claude Sonnet 4.5 (copilot)` |
| `tools` | No | Tools available during this prompt | `['editFiles', 'search']` |
| `argument-hint` | No | Hint text shown in chat input | `'What would you like explained?'` |

### Agent Modes

| Agent Value | Can Edit Files? | Can Run Terminal? | Best For |
|---|---|---|---|
| `ask` | ❌ No | ❌ No | Reading, explaining, reviewing |
| `agent` | ✅ Yes | ✅ Yes | Creating, editing, building |
| `plan` | ❌ No | ❌ No | Planning changes without applying |
| `your-agent-name` | Depends on agent's tools | Depends | Using a custom agent |

---

## 🔤 Variables Reference

Variables let your prompts accept dynamic input:

### User Input Variables

| Variable | What It Does | Example |
|---|---|---|
| `${input:name}` | Ask user for free-text input | `${input:className}` → user types "Calculator" |
| `${input:name:placeholder}` | Same, with hint text | `${input:module:e.g., service, controller}` |

### File Context Variables

| Variable | What It Provides | Example Value |
|---|---|---|
| `${file}` | Full path of current file | `E:\learning\src\Main.java` |
| `${fileBasename}` | Just the filename | `Main.java` |
| `${fileDirname}` | Directory of current file | `E:\learning\src\` |
| `${fileBasenameNoExtension}` | Filename without extension | `Main` |
| `${selection}` or `${selectedText}` | Currently selected text in editor | `(whatever you highlighted)` |
| `${workspaceFolder}` | Project root path | `E:\learning\learning-assistant` |

### Referencing Other Files

You can link to instruction files or code examples with Markdown links:

```markdown
Follow the conventions in [Java instructions](../instructions/java.instructions.md).
Base it on this example: [Main.java](../../src/Main.java)
```

### Referencing Tools

```markdown
Use #tool:search to find existing implementations.
Use #tool:codebase to understand the class hierarchy.
```

---

## ✍️ Complete Examples

### Example 1: Explain Current File

```markdown
---
name: explain
description: 'Explain the current file in plain language'
agent: ask
tools: ['codebase']
---

Explain this file in plain, beginner-friendly language.

## File
${file}

## Instructions
1. **Purpose:** What does this file do? (1-2 sentences)
2. **Structure:** Walk through the code section by section
3. **Key concepts:** Explain any Java concepts used (loops, methods, classes, etc.)
4. **How to run:** How would someone execute this code?
5. **What could be improved:** Suggest 2-3 improvements with code examples
```

> **Usage:** Open any `.java` file → type `/explain` → get a structured walkthrough

---

### Example 2: Create a Java Class

```markdown
---
name: create-class
description: 'Scaffold a new Java class following project conventions'
agent: agent
tools: ['editFiles', 'search', 'codebase']
---

Create a new Java class in this project.

## Conventions
Follow the rules from [Java instructions](../instructions/java.instructions.md):
- Classes: `UpperCamelCase`
- Methods: `lowerCamelCase`
- Use `final` where possible
- Add Javadoc on public methods
- Include a `toString()` method
- Include proper `equals()` and `hashCode()` if it's a data class

## Input
Class name: ${input:className:e.g., Calculator, StudentService, OrderManager}
Purpose: ${input:purpose:What should this class do?}
Package: ${input:package:e.g., com.example.service (or leave blank for default)}

## Requirements
1. Create the class file in the `src/` directory
2. Add a constructor
3. Add Javadoc class comment
4. Include at least one public method based on the purpose
5. Add a `main` method that demonstrates usage
```

> **Usage:** Type `/create-class` → enter name, purpose, package → Copilot creates the file

---

### Example 3: Code Review Checklist

```markdown
---
name: review
description: 'Review the current file against a coding checklist'
agent: ask
tools: ['search', 'codebase']
---

Review this file for code quality issues.

## File to Review
${file}

## Checklist
1. **Naming:** Do class/method/variable names clearly describe their purpose?
2. **Length:** Are methods under 30 lines? Is the class under 200 lines?
3. **SRP:** Does each method do exactly one thing?
4. **Error handling:** Are exceptions caught specifically (not generic `catch(Exception)`)?
5. **Resources:** Are streams/connections closed properly (try-with-resources)?
6. **Magic numbers:** Are literal values extracted to named constants?
7. **Comments:** Are comments explaining WHY, not WHAT?
8. **Edge cases:** Are null checks and boundary conditions handled?
9. **Formatting:** Consistent indentation and brace style?
10. **Tests:** Is this code easily testable? What would you test?

## Output
For each issue found:
- **Rule #:** which checklist item
- **Line:** approximate location
- **Issue:** what's wrong
- **Fix:** concrete code change
```

---

### Example 4: Refactor Selection

```markdown
---
name: refactor
description: 'Refactor the selected code with explanations'
agent: agent
tools: ['editFiles', 'codebase']
---

Refactor the selected code to improve quality.

## Code to Refactor
${selection}

## File Context
This code is from: ${file}

## Refactoring Goals (apply whichever are relevant)
1. **Extract Method:** Break long methods into smaller ones
2. **Rename:** Improve variable/method names to be more descriptive
3. **Simplify:** Reduce complexity (fewer nested ifs, shorter methods)
4. **DRY:** Remove duplication
5. **Modernize:** Use modern Java features (streams, var, records, etc.)

## Rules
- Explain each change you make and WHY
- Show before and after for significant changes
- Don't change behavior — only improve structure
- Keep changes minimal and focused
```

> **Usage:** Select some code → type `/refactor` → Copilot improves it and explains why

---

### Example 5: Generate Tests

```markdown
---
name: test
description: 'Generate JUnit 5 tests for the current file or selected method'
agent: agent
tools: ['editFiles', 'search', 'codebase']
---

Generate unit tests for the current file.

## Source File
${file}

## Test Conventions
- Use JUnit 5 (`@Test`, `@DisplayName`)
- Method name: `should_<expected>_when_<condition>`
- Follow Arrange-Act-Assert pattern
- Test happy path, edge cases, and error cases
- Use descriptive `@DisplayName` annotations
- One assertion concept per test

## Output
1. Create a test file in the appropriate location
2. Include tests for ALL public methods
3. For each method, include:
   - Happy path test
   - Edge case test (null, empty, boundary values)
   - Error case test (if method can fail)
4. Add a comment above each test explaining what it verifies
```

---

## 📂 How to Create a Prompt File

### Step-by-Step

```
1. Create the folder (if missing):  .github/prompts/
2. Create a new file:               .github/prompts/<task-name>.prompt.md
3. Add YAML frontmatter with name, description, agent, tools
4. Write task instructions in the body
5. Use ${input:name:hint} for dynamic inputs
6. Save — type /task-name in chat to use it
```

### Using VS Code Command

1. Press `Ctrl+Shift+P`
2. Type: `Chat: New Prompt File`
3. Choose **"Workspace"**
4. Enter filename
5. Edit the generated template

### Testing a Prompt

- **Quick test:** Open the `.prompt.md` file in the editor, click the **▶️ play button** in the title bar
- **Normal test:** In Chat, type `/` and select your prompt from the list

---

## 💡 Tips

- **Use `agent: ask`** for read-only prompts (explain, review) and **`agent: agent`** for editing prompts (create, refactor)
- **Iterate:** Prompts rarely work perfectly first time. Run → review output → tweak → repeat
- **Reference instructions:** Link to your instruction files instead of duplicating rules
- **Keep prompts focused:** One task per prompt. "Create a class" and "Generate tests" should be separate
- **Use `argument-hint`** to tell users what to type when the prompt needs context
- **Test with different files:** Make sure your prompt works on various kinds of source files, not just one

---

## 💡 Prompt Ideas

| Prompt | Description |
|---|---|
| `/explain` | Explain current file in plain language |
| `/create-class` | Scaffold a new Java class |
| `/review` | Code review checklist |
| `/refactor` | Refactor selected code with explanations |
| `/test` | Generate JUnit 5 tests |
| `/document` | Add Javadoc to all public methods |
| `/simplify` | Reduce complexity of selected code |
| `/pattern` | Identify and explain design patterns in current file |
| `/debug` | Analyze a bug and suggest fixes |
| `/convert` | Convert between Java versions (e.g., add streams, records) |

---

## 🧪 Experiments to Try

1. **Create `/explain`** and run it on `Main.java` — does the output make sense?
2. **Create `/create-class`** and scaffold a `Calculator` class — does it follow your rules?
3. **Create two prompts** with different `agent` values (`ask` vs `agent`) — notice how one can edit and the other can't
4. **Chain a prompt with an agent** — set `agent: Java-Tutor` in a prompt to use your custom agent's persona
5. **Compare models** — duplicate a prompt with different `model` fields — compare quality

---

## 🔗 Links

- [VS Code: Prompt Files](https://code.visualstudio.com/docs/copilot/customization/prompt-files)
- [Community Examples](https://github.com/github/awesome-copilot/tree/main/prompts)
- ← Back to [main guide](../README.md)
