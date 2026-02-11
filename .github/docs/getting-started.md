# 📖 Getting Started — Step-by-Step Tutorial

> **Time needed:** ~30 minutes  
> **Prerequisites:** VS Code with GitHub Copilot extension installed  
> **Goal:** Verify the sample setup works, understand each primitive, and start experimenting

---

## Overview

```
 Step 1 ─ Open the project in VS Code              (2 min)
 Step 2 ─ Understand the folder structure           (5 min, reading)
 Step 3 ─ Verify instructions are loading           (5 min, hands-on)
 Step 4 ─ Try an agent                              (5 min, hands-on)
 Step 5 ─ Try a prompt                              (5 min, hands-on)
 Step 6 ─ Try a skill                               (3 min, hands-on)
 Step 7 ─ Create your own customization             (5 min, hands-on)
```

---

## Step 1 · Open the Project in VS Code *(2 min)*

1. Open VS Code
2. File → Open Folder → navigate to `E:\mgcnoscan\learning\learning-assistant`
3. Wait for VS Code to finish loading

> **Important:** You must open the **project folder** (the one containing `.github/`), not a parent folder. Copilot looks for `.github/` in the workspace root.

---

## Step 2 · Understand the Folder Structure *(5 min)*

Open the Explorer sidebar and navigate to `.github/`. You should see:

```
.github/
├── README.md                         ← Main learning guide (you read this already)
├── copilot-instructions.md           ← ALWAYS loaded — project-wide rules
│
├── instructions/                     ← Auto-loaded when editing matching files
│   ├── README.md                     ← Guide: how instructions work
│   └── java.instructions.md          ← Sample: rules for *.java files
│
├── agents/                           ← Manual — select from dropdown
│   ├── README.md                     ← Guide: how agents work
│   └── code-reviewer.agent.md        ← Sample: read-only review agent
│
├── prompts/                          ← Manual — type /command
│   ├── README.md                     ← Guide: how prompts work
│   └── explain.prompt.md             ← Sample: explain current file
│
├── skills/                           ← Auto — matches task description
│   ├── README.md                     ← Guide: how skills work
│   └── java-build/
│       └── SKILL.md                  ← Sample: compile & run Java
│
└── docs/
    └── getting-started.md            ← THIS FILE
```

### Quick Read

1. **Open `copilot-instructions.md`** — skim the project-wide rules. Every time you chat with Copilot, this file is included automatically.
2. **Open `instructions/java.instructions.md`** — notice the `applyTo: "**/*.java"` line. These rules only load when you're editing a `.java` file.
3. **Open `agents/code-reviewer.agent.md`** — notice how it restricts `tools` to search-only.

---

## Step 3 · Verify Instructions Are Loading *(5 min)*

### Test 1: Check References

1. Open `src/Main.java`
2. Open Copilot Chat: **`Ctrl+Shift+I`**
3. Type: *"What coding conventions should I follow?"*
4. After Copilot responds, look at the **top** of the response
5. Click **"References"** to expand it
6. You should see:
   - `copilot-instructions.md` ← always-on
   - `java.instructions.md` ← because you have a `.java` file open

> ✅ Both files appear → instructions are working!  
> ❌ Missing → see [Troubleshooting](#troubleshooting) below

### Test 2: Check Diagnostics

1. In the Chat view, **right-click** anywhere
2. Select **"Diagnostics"**
3. This shows ALL loaded agents, prompt files, instruction files, and skills
4. Verify your sample files appear here without errors

### Test 3: Confirm Path-Specific Loading

1. Close all files
2. Open `src/Main.java` → chat → check References → `java.instructions.md` should appear
3. Now open `.gitignore` → chat → check References → `java.instructions.md` should NOT appear (it only matches `*.java`)

> This confirms **path-specific instructions only load for matching files**.

---

## Step 4 · Try an Agent *(5 min)*

1. In the Chat view, look at the **top of the chat input** — there's an agent selector/dropdown
2. Click it — you should see:
   - **Ask** (default — chat only, no edits)
   - **Agent** (default — can edit files, run terminal)
   - **Code-Reviewer** (your sample agent)
3. Select **Code-Reviewer**
4. Notice the description text changes
5. Ask: *"Review Main.java for issues"*
6. The response should focus on code quality but should NOT try to edit files (because the agent has read-only tools)

### Experiment

7. Switch to the default **Agent** mode
8. Ask the same question: *"Review Main.java for issues"*
9. Compare: the default agent might try to edit the file, while Code-Reviewer only reports

> This shows how **tool restrictions** change agent behavior.

---

## Step 5 · Try a Prompt *(5 min)*

1. Open `src/Main.java`
2. In Copilot Chat, type: **`/explain`**
3. You should see `explain` in the prompt suggestions
4. Select it and press Enter
5. Copilot should give a structured explanation of Main.java

> ✅ Prompt appears and runs → success!  
> ❌ Doesn't appear → check file extension (`.prompt.md`), location (`.github/prompts/`), and try `Ctrl+Shift+P` → `Chat: Configure Prompt Files`

### How It Works

The `/explain` prompt:
- Uses `agent: ask` → it can only read, not edit
- Uses `${file}` variable → it automatically knows which file you have open
- Has structured instructions → Copilot follows the numbered output format

---

## Step 6 · Try a Skill *(3 min)*

1. In Copilot Chat (any agent mode), ask: *"How do I compile and run Main.java?"*
2. Copilot should give accurate instructions because the `java-build` skill matches your question
3. Check: right-click Chat → Diagnostics → verify the skill loaded

### How It Works

Unlike prompts (manual `/command`), skills are **automatic**:
- Copilot reads every skill's `description` field
- Your question contains "compile" and "run" → matches the java-build skill's description
- The skill's SKILL.md body is loaded into context
- Copilot uses that knowledge to answer

---

## Step 7 · Create Your Own Customization *(5 min)*

Now that you've seen all 5 primitives working, create something yourself. Pick one:

### Option A: New Instruction File

Create `.github/instructions/naming.instructions.md`:

```markdown
---
applyTo: "**/*.java"
---

# My Custom Naming Rule

- All variables must have descriptive names (minimum 3 characters)
- No single-letter variables except: `i`, `j`, `k` in for-loops
- Boolean variables should start with `is`, `has`, `can`, or `should`
```

**Test it:** Open a `.java` file → ask Copilot to write a method → does it follow your naming rule?

### Option B: New Prompt

Create `.github/prompts/improve.prompt.md`:

```markdown
---
name: improve
description: 'Suggest 3 improvements for the current file'
agent: ask
tools: ['codebase']
---

Look at this file and suggest exactly 3 improvements:
${file}

For each improvement:
1. **What:** one-sentence description
2. **Why:** why it's better
3. **Code:** show the improved version
```

**Test it:** Open `Main.java` → type `/improve` → see suggestions

### Option C: New Agent

Create `.github/agents/mentor.agent.md`:

```markdown
---
name: Mentor
description: 'A senior developer who reviews code and explains concepts'
tools: ['search', 'codebase']
---

You are a friendly senior Java developer mentoring a junior developer.
- Always explain the "why" behind recommendations
- Use analogies to explain complex concepts
- When reviewing code, be constructive — praise good parts too
- Suggest one thing to learn next after each interaction
```

**Test it:** Select `Mentor` → ask *"What does the for-loop in Main.java do, and how can I improve it?"*

---

## Troubleshooting

### Instructions not loading?

1. **Check the setting:** `Ctrl+,` → search for `instructionFiles` → ensure `chat.includeApplyingInstructions` is enabled
2. **Check workspace root:** `.github/` must be at the root of your opened folder
3. **Check Diagnostics:** Right-click Chat → Diagnostics

### Agent not in dropdown?

1. File must be in `.github/agents/` with `.agent.md` extension
2. YAML frontmatter must be valid (check for stray characters, missing colons)
3. Try restarting VS Code

### Prompt not showing with `/`?

1. File must be in `.github/prompts/` with `.prompt.md` extension
2. Check YAML frontmatter syntax (valid YAML between `---` markers)
3. Try: `Ctrl+Shift+P` → `Chat: Configure Prompt Files` to see if it's detected

### Skill not loading?

1. Must be in `.github/skills/<name>/SKILL.md` (folder, not flat file)
2. Both `name` and `description` fields are **required** in frontmatter
3. Description must be specific enough to match your question — use action verbs

### Nothing is working?

1. Make sure you opened the correct folder: `E:\mgcnoscan\learning\learning-assistant`
2. Make sure GitHub Copilot extension is installed and signed in
3. Restart VS Code: `Ctrl+Shift+P` → `Developer: Reload Window`

---

## What's Next?

Now that all 5 primitives are working:

| To Learn More About... | Read This Guide |
|---|---|
| Path-specific instructions | [instructions/README.md](../instructions/README.md) |
| Custom agents | [agents/README.md](../agents/README.md) |
| Prompt files | [prompts/README.md](../prompts/README.md) |
| Agent skills | [skills/README.md](../skills/README.md) |

### Practice Progression

```
Level 1: Modify the sample files — change rules, see what happens
Level 2: Create new files for each primitive — build up your collection
Level 3: Chain agents with handoffs — create workflows
Level 4: Build skills with scripts and templates — add resource files
Level 5: Apply to your production project — port what works
```

### Ongoing Maintenance Habits

| When... | Do This... |
|---|---|
| Copilot gives wrong advice | Add a correcting rule to an instruction file |
| You repeat the same prompt often | Save it as a `.prompt.md` file |
| You need a specialist role | Create an agent for it |
| You need Copilot to know a procedure | Create a skill with step-by-step instructions |

---

## 🔗 Links

- ← Back to [main guide](../README.md)
- 📋 [Instructions guide](../instructions/README.md)
- 🤖 [Agents guide](../agents/README.md)
- 🎯 [Prompts guide](../prompts/README.md)
- 🛠️ [Skills guide](../skills/README.md)
