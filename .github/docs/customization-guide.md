````markdown
# 🧰 Copilot Customization Guide — Understanding the System

> **Audience:** Developer learning how to customize GitHub Copilot  
> **Prerequisites:** Read `.github/README.md` and follow `docs/getting-started.md` first  
> **Goal:** Understand the architecture behind the modular agent/skill/prompt system

---

## The Big Picture — Why Modular Customization?

Think of Copilot customization like building a toolbox, not a single tool:

```
Without customization:
┌──────────────────────────┐
│   Generic Copilot        │  ← Same response regardless of task
│   "One size fits all"    │
└──────────────────────────┘

With modular customization:
┌──────────────────────────────────────────────────────────────┐
│                     Your Copilot Toolkit                     │
│                                                              │
│  🏗️ Designer        → Thinks in architecture & patterns      │
│  🔍 Debugger        → Thinks in evidence & root causes       │
│  💥 Impact-Analyzer → Thinks in dependencies & risk          │
│  📘 Learning-Mentor → Thinks in analogies & understanding    │
│  📋 Code-Reviewer   → Thinks in quality & best practices     │
│                                                              │
│  Each agent brings different:                                │
│    • Mindset & expertise                                     │
│    • Available tools                                         │
│    • Output format                                           │
│    • Next-step recommendations (handoffs)                    │
└──────────────────────────────────────────────────────────────┘
```

## How the Primitives Work Together

### 1. Instructions = The Foundation (Always Running)

Instructions define your baseline rules. They load **automatically** — you never need to invoke them.

```
copilot-instructions.md          ← Loads on EVERY request
  "Use Java 21+, follow our naming rules..."

instructions/java.instructions.md   ← Loads when editing *.java files
  "Use var, records, pattern matching..."

instructions/clean-code.instructions.md  ← Loads when editing *.java files
  "Methods do ONE thing, avoid code smells..."
```

**Key concept:** Instructions are **additive**. When you edit a `.java` file, BOTH `copilot-instructions.md` AND the java-specific instructions load together. Copilot sees all of them combined.

**When to create a new instruction file:**
- You find yourself repeating the same rule in every chat → add it to instructions
- Different file types need different rules → use `applyTo` glob patterns
- You want consistent behavior without remembering to ask for it

### 2. Agents = Specialized Personas (You Choose)

Agents are like switching hats. Each one changes:
- **Mindset:** How Copilot thinks about your question
- **Tools:** What Copilot can access (read-only vs. full editing)
- **Format:** How the response is structured

```yaml
# Key fields in an agent file
name: Designer           # Shows in the agent dropdown
description: '...'       # Shown when you hover
tools: ['search', ...]   # Controls capabilities
handoffs:                # Suggested next steps
  - label: Start Implementation
    agent: agent
```

**How to choose an agent:**

| I want to... | Select this agent |
|---|---|
| Review architecture or class design | **Designer** |
| Investigate a bug or error | **Debugger** |
| Understand what a change will break | **Impact-Analyzer** |
| Learn or understand a concept | **Learning-Mentor** |
| Get a code quality review | **Code-Reviewer** |
| Write/edit code (default) | **Agent** (built-in) |

**The handoff pattern:** After the Designer reviews your code and suggests changes, a "Start Implementation" button appears. Click it to switch to the default Agent mode with your design context carried over. This creates a natural workflow:

```
Think (Designer) → Assess Risk (Impact-Analyzer) → Build (Agent) → Review (Code-Reviewer)
```

### 3. Prompts = Reusable Tasks (You Invoke with /)

Prompts are saved tasks you trigger with `/command`. Unlike agents (which change the persona), prompts define a **specific task** with a predefined workflow.

```
/design-review  → Run a SOLID/GRASP review of the current file
/debug          → Start a systematic debugging investigation
/impact         → Map the ripple effects of changing this file
/teach          → Learn the concepts used in this file
/refactor       → Find and apply refactoring opportunities
/explain        → Get a beginner-friendly explanation
```

**Prompts + Agents work together:** Each prompt specifies which agent to use. `/design-review` uses the Designer agent, `/debug` uses the Debugger. This means the prompt gets the agent's full persona PLUS the specific task instructions.

**When to create a prompt vs. just chatting:**
- If you do the same task more than twice with similar instructions → make it a prompt
- If the task has a specific structure you want consistently → make it a prompt
- If it's a one-off question → just chat directly

### 4. Skills = Auto-Loading Knowledge Packs

Skills are the most automatic primitive. You don't invoke them — Copilot loads them when your question matches the skill's description.

```
Ask: "How do I compile this?"
  → Copilot reads skill descriptions
  → Matches java-build skill ("compile, run, build...")
  → Loads SKILL.md into context
  → Gives accurate answer

Ask: "Should I use Strategy or Template Method here?"
  → Matches design-patterns skill
  → Loads the pattern decision guide
  → Gives contextual recommendation
```

**Three-level progressive loading:**
1. **Discovery:** Copilot always knows skill names and descriptions (lightweight)
2. **Instructions:** When matched, loads the SKILL.md body (detailed guidance)
3. **Resources:** Can access additional files in the skill folder (scripts, templates, etc.)

**When to create a skill vs. an instruction:**

| Feature | Skill | Instruction |
|---|---|---|
| Loading | On-demand, task-matched | Always-on or glob-matched |
| Content | Instructions + scripts + examples | Instructions only |
| Portability | Works in VS Code, CLI, coding agent | VS Code only (mostly) |
| Best for | Specialized workflows | Coding standards |

## Architecture of This Setup

```
                    ┌─ copilot-instructions.md ─── Always loaded
                    │
    INSTRUCTIONS ───┤─ java.instructions.md ────── Loaded for *.java
                    │
                    └─ clean-code.instructions.md ─ Loaded for *.java


                    ┌─ Designer ──────── design review, architecture
                    │     └─ uses: design-patterns skill
                    │
                    ├─ Debugger ──────── bug investigation
    AGENTS ─────────┤     └─ uses: java-debugging skill
                    │
                    ├─ Impact-Analyzer ─ change assessment
                    │
                    ├─ Learning-Mentor ─ concept teaching
                    │
                    └─ Code-Reviewer ─── quality check (read-only)


                    ┌─ /design-review → uses Designer agent
                    ├─ /debug ────────→ uses Debugger agent
    PROMPTS ────────┤─ /impact ───────→ uses Impact-Analyzer agent
                    ├─ /teach ────────→ uses Learning-Mentor agent
                    ├─ /refactor ─────→ uses Designer agent
                    └─ /explain ──────→ uses Ask agent (read-only)


                    ┌─ java-build ──── compile/run procedures
    SKILLS ─────────┤─ design-patterns ─ SOLID, GoF, clean architecture
                    └─ java-debugging ── exception patterns, debug techniques
```

## How to Extend This System

### Adding a New Agent

1. Create `.github/agents/your-agent.agent.md`
2. Define: name, description, tools, handoffs
3. Write the persona and instructions in the body
4. Test: select it from the dropdown, ask a question

**Template:**
```markdown
---
name: Your-Agent-Name
description: 'What this agent does — shown in dropdown'
tools: ['search', 'codebase']
handoffs:
  - label: Next Step
    agent: agent
    prompt: Follow up on the above.
---

# Persona Description

You are a [role] who [approach].

## How You Work
[Step-by-step process]

## Output Format
[Consistent structure for responses]

## Rules
[Constraints and guardrails]
```

### Adding a New Skill

1. Create folder: `.github/skills/your-skill/`
2. Create `SKILL.md` with name + description in frontmatter
3. Add instructions, code examples, and reference material in the body
4. Optionally add scripts/templates as additional files in the folder

**Key:** The `description` field is critical — it's how Copilot decides whether to load your skill. Use specific action verbs:
```yaml
# GOOD — specific, matchable
description: >
  Analyze database query performance, identify slow queries, suggest index optimizations.
  Use when asked about SQL performance, query plans, or database optimization.

# BAD — too vague
description: > 
  Database stuff.
```

### Adding a New Prompt

1. Create `.github/prompts/your-prompt.prompt.md`
2. Define: name, description, agent, tools
3. Write the task instructions in the body, using `${file}`, `${selection}`, etc.
4. Test: type `/your-prompt` in chat

### Adding a New Instruction File

1. Create `.github/instructions/your-rules.instructions.md`
2. Set `applyTo` glob pattern to control when it loads
3. Write concise, specific rules
4. Test: open a matching file, chat, check References

## Tips From Real-World Usage

1. **Start small.** Use the agents for a week before creating new ones. Learn what's missing.
2. **Iterate on prompts.** Use the play button in the editor to test and refine prompt files.
3. **Check diagnostics.** Right-click Chat → Diagnostics shows what's loaded and any errors.
4. **Don't over-instruct.** Shorter, clearer instructions work better than long essays.
5. **Use handoffs.** They create natural workflows — Design → Impact → Implement → Review.
6. **Keep skills focused.** One skill = one capability. Don't create a mega-skill.
7. **Reference, don't duplicate.** Use Markdown links in prompts/agents to reference instruction files instead of copying rules.

## Further Reading

- [VS Code: Custom Instructions](https://code.visualstudio.com/docs/copilot/customization/custom-instructions)
- [VS Code: Custom Agents](https://code.visualstudio.com/docs/copilot/customization/custom-agents)
- [VS Code: Prompt Files](https://code.visualstudio.com/docs/copilot/customization/prompt-files)
- [VS Code: Agent Skills](https://code.visualstudio.com/docs/copilot/customization/agent-skills)
- [Agent Skills Open Standard](https://agentskills.io/)
- [Community Examples](https://github.com/github/awesome-copilot)
- [Clean Code by Robert C. Martin](https://www.oreilly.com/library/view/clean-code/9780136083238/)
- [Design Patterns: Elements of Reusable OO Software (GoF)](https://www.oreilly.com/library/view/design-patterns-elements/0201633612/)
- [Refactoring by Martin Fowler](https://refactoring.com/)

````
