# 🤖 Custom Agents — Detailed Guide

> **What:** Custom agents are AI **personas** — specialist roles that Copilot can assume.  
> **Where:** `.github/agents/*.agent.md`  
> **How to use:** Select from the agents dropdown in VS Code Chat.

---

## 📌 What Are Custom Agents?

Think of agents as **hats** Copilot can wear. When you select an agent:

- Copilot adopts that persona's **instructions** (what it knows and how it behaves)
- It's restricted to specific **tools** (what it can do — edit files? search only? run terminal?)
- It can **hand off** to another agent when done (workflow chaining)

When you switch away from the agent, Copilot reverts to its default behavior.

### Real-World Analogy

| Without Agents | With Agents |
|---|---|
| *"Act as a security reviewer. Only search code, don't edit anything. Focus on input validation, thread safety..."* | Select `Security-Reviewer` from dropdown. Done. |

---

## 📄 File Format

Agent files are Markdown with a YAML frontmatter header:

```markdown
---
name: Agent-Display-Name
description: 'Short description shown as placeholder in chat input'
tools: ['search', 'codebase', 'editFiles']
model: Claude Sonnet 4.5 (copilot)
---

# Agent Instructions

Your detailed persona instructions go here.
Written in Markdown. Can be as detailed as needed.
```

### Frontmatter Fields Reference

| Field | Required? | Description | Example |
|---|---|---|---|
| `name` | No | Display name in dropdown. Defaults to filename. | `Planner` |
| `description` | No | Placeholder text in chat input when agent is active | `'Research-only agent that creates implementation plans'` |
| `tools` | No | Array of tools the agent can use | `['search', 'codebase', 'editFiles', 'terminal', 'fetch']` |
| `agents` | No | Sub-agents this agent can invoke. `*` = all | `['implementation']` or `*` |
| `model` | No | AI model to use | `Claude Sonnet 4.5 (copilot)` |
| `handoffs` | No | Workflow transitions to other agents | See [Handoffs](#-handoffs) below |
| `user-invokable` | No | Set `false` to hide from dropdown | `false` (for subagent-only agents) |
| `disable-model-invocation` | No | Prevent use as subagent | `true` |

### Available Tools

| Tool Name | What It Does |
|---|---|
| `search` | Search through workspace files |
| `codebase` | Understand code structure and relationships |
| `editFiles` | Create/edit/delete files |
| `terminal` | Run commands in terminal |
| `fetch` | Fetch web pages |
| `githubRepo` | Search GitHub repositories |
| `usages` | Find references/usages of symbols |

> **Tip:** Omitting `tools` gives the agent access to **all** tools. Listing specific tools **restricts** it.

---

## ✍️ Complete Examples

### Example 1: Code Reviewer (Read-Only)

This agent can only search and read — it cannot edit files or run terminal commands.

```markdown
---
name: Code-Reviewer
description: 'Reviews Java code for best practices, bugs, and style issues'
tools: ['search', 'codebase', 'usages']
---

# Code Review Agent

You are a senior Java code reviewer. You review code for:

## Focus Areas
- **Bug detection:** Null pointer risks, off-by-one errors, resource leaks
- **Style:** Naming conventions, code organization, method length
- **Best practices:** Proper exception handling, immutability, encapsulation
- **Performance:** Unnecessary allocations, N+1 patterns, string concatenation in loops

## Rules
- Do NOT edit files — you are read-only
- Always cite specific line numbers
- Suggest concrete fixes with code snippets
- Rate each issue: Critical / Warning / Suggestion

## Output Format
For each finding:
1. **Severity:** Critical / Warning / Suggestion
2. **Line:** approximate location
3. **Issue:** what's wrong
4. **Fix:** what to change (show code)
```

> **Try it:** Select `Code-Reviewer` from the agent dropdown → ask *"Review Main.java"*

---

### Example 2: Planner (Research + Handoff)

This agent researches and plans, then hands off to the default agent for implementation.

```markdown
---
name: Planner
description: 'Analyzes code and creates implementation plans without editing files'
tools: ['search', 'codebase', 'fetch', 'usages']
handoffs:
  - label: Start Implementation
    agent: agent
    prompt: Implement the plan outlined above.
    send: false
---

# Planner Agent

You are a planning specialist. Your job is to research the codebase and create
a detailed implementation plan — but NEVER edit files yourself.

## Your Workflow
1. Analyze the user's request
2. Search the codebase for relevant files and patterns
3. Create a step-by-step plan

## Output Format
For each change needed:
1. **File:** full path
2. **What:** describe the change
3. **Before:** current code snippet
4. **After:** proposed code snippet
5. **Risk:** any concerns

## Rules
- Do NOT create or edit files
- Always list ALL files that need to change
- Show concrete code snippets, not vague descriptions
- Note dependencies between changes
```

> **Try it:** Select `Planner` → ask your question → review the plan → click **"Start Implementation"** button to hand off to the editing agent.

---

### Example 3: Java Tutor (Educational)

Perfect for a learning project — explains concepts instead of just writing code.

```markdown
---
name: Java-Tutor
description: 'Teaches Java concepts with explanations, examples, and exercises'
tools: ['search', 'codebase', 'editFiles']
---

# Java Tutor Agent

You are a patient, encouraging Java programming tutor.

## Teaching Style
- Explain WHY, not just WHAT
- Use simple analogies for complex concepts
- Show both the wrong way and the right way
- Include a small exercise after each explanation
- Build on concepts the student already knows

## When Writing Code
- Add comments explaining each line
- Start with a simple version, then improve it
- Show common mistakes and how to avoid them

## When Answering Questions
- First check what the student already knows
- Break complex answers into numbered steps
- End with "Try this:" followed by a small challenge

## Topics You Excel At
- Object-Oriented Programming (classes, interfaces, inheritance, polymorphism)
- Collections (List, Set, Map, and when to use which)
- Exception handling patterns
- Java streams and lambdas
- Design patterns (explain when they're useful, not just how)
```

> **Try it:** Select `Java-Tutor` → ask *"Explain interfaces to me using a real-world analogy"*

---

## 🔀 Handoffs

Handoffs let you chain agents into **workflows**. After the first agent responds, a button appears to transition to the next agent:

```yaml
handoffs:
  - label: Start Implementation       # Button text shown after response
    agent: agent                       # Target agent to switch to
    prompt: Implement the plan above.  # Pre-filled prompt
    send: false                        # false = user reviews before sending
```

### Workflow Example

```
┌──────────┐     handoff      ┌────────────────┐     handoff      ┌──────────────┐
│ Planner  │ ──────────────→  │ Implementation │ ──────────────→  │ Code Review  │
│ (search  │  "Start          │ (editFiles,    │  "Review         │ (search,     │
│  only)   │   Implementation" │  terminal)     │   Changes"      │  codebase)   │
└──────────┘                  └────────────────┘                  └──────────────┘
```

### How `send` Works

| `send` value | Behavior |
|---|---|
| `true` | Prompt is sent automatically — no user review |
| `false` | Prompt is pre-filled, user reviews and presses Enter |

---

## 📂 How to Create an Agent

### Option A — VS Code Command (Recommended for Beginners)

1. Press `Ctrl+Shift+P`
2. Type: `Chat: New Custom Agent`
3. Choose **"Workspace"** (saves to `.github/agents/`)
4. Enter a filename (e.g., `my-agent`)
5. VS Code generates a template — edit it

### Option B — Manual

1. Create file: `.github/agents/<name>.agent.md`
2. Add frontmatter between `---` markers
3. Write instructions below
4. Save — agent appears in the dropdown immediately

### Where Agents Are Stored

| Location | Scope | Use Case |
|---|---|---|
| `.github/agents/` | Workspace | Shared with team via Git |
| User profile folder | Personal | Available across ALL your workspaces |

> **Tip:** For learning, use `.github/agents/` (workspace scope) so you can see the files easily.

---

## ❓ FAQ

**Q: How do I switch to an agent?**  
A: In the Chat view, click the agent dropdown (top of chat input) and select your agent.

**Q: Can I use agents AND instructions at the same time?**  
A: Yes! When you select an agent, `copilot-instructions.md` and matching `*.instructions.md` files are still loaded alongside the agent's instructions.

**Q: An agent doesn't appear in the dropdown?**  
A: Check: (1) file is in `.github/agents/`, (2) extension is `.agent.md`, (3) YAML frontmatter is valid. Right-click in Chat → Diagnostics to see errors.

**Q: What's the difference between agents and prompts?**  
A: Agents are **persistent personas** (active throughout the conversation). Prompts are **one-shot tasks** (run once and done).

**Q: How detailed should agent instructions be?**  
A: As detailed as needed — 20 lines is fine for simple roles, 200+ lines for complex specialists. But remember: longer = more tokens consumed per request.

---

## 🧪 Experiments to Try

1. **Create a "No-Code" agent** — set `tools: []` and see what happens (it can only chat, no tools)
2. **Create a "Terminal-Only" agent** — `tools: ['terminal']` — it can only run commands
3. **Create two agents that hand off** — Planner → Implementer workflow
4. **Compare models** — duplicate an agent with a different `model` field and compare outputs

---

## 🔗 Links

- [VS Code: Custom Agents](https://code.visualstudio.com/docs/copilot/customization/custom-agents)
- [Community Examples](https://github.com/github/awesome-copilot/tree/main/agents)
- ← Back to [main guide](../README.md)
