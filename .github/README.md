# GitHub Copilot Customization — Learning Guide

> **Purpose:** Learn how to customize GitHub Copilot using all 5 official primitives.  
> **Project:** `learning-assistant` — a simple Java project for hands-on experimentation.  
> **Audience:** Developers new to Copilot customization who want to learn by doing.

---

## Why Customize Copilot?

Out of the box, Copilot knows nothing about **your** project. It guesses based on generic training data. Customization fixes that:

| Without Customization | With Customization |
|---|---|
| Copilot suggests `System.out.println` | Copilot uses your preferred `Logger` |
| Generic variable names like `list1` | Follows your naming conventions |
| Doesn't know your project structure | Knows which files do what |
| You repeat instructions every chat session | Instructions persist in files |

---

## The 5 Official Primitives

GitHub Copilot in VS Code supports exactly **5 customization primitives**. Everything you can do falls into one of these:

```
┌─────────────────────────────────────────────────────────────────────┐
│                    COPILOT CUSTOMIZATION                            │
│                                                                     │
│  1. copilot-instructions.md    Always-on project rules              │
│  2. *.instructions.md          Conditional rules per file type      │
│  3. *.agent.md                 Custom AI personas                   │
│  4. *.prompt.md                Reusable slash-command tasks          │
│  5. SKILL.md                   Tool folders with scripts/resources  │
└─────────────────────────────────────────────────────────────────────┘
```

### Quick Decision Guide

```
Need to...                                        → Use this primitive
─────────────────────────────────────────────────────────────────────
Set project-wide rules Copilot always follows      → copilot-instructions.md
Add rules only when editing certain file types     → *.instructions.md
Create a specialist persona (reviewer, planner)    → *.agent.md
Save a reusable task as a /slash-command           → *.prompt.md
Bundle instructions + scripts + templates          → SKILL.md folder
```

---

## Official Folder Structure

```
.github/
├── copilot-instructions.md           ← 1. Always-on (auto-loaded every request)
│
├── instructions/                     ← 2. Path-specific (auto-loaded by glob)
│   ├── java.instructions.md               applyTo: "**/*.java"
│   ├── clean-code.instructions.md          applyTo: "**/*.java"
│   └── README.md                           ← Guide for this folder
│
├── agents/                           ← 3. Custom agents (manual — dropdown)
│   ├── designer.agent.md                   Architecture & design review
│   ├── debugger.agent.md                   Systematic debugging
│   ├── impact-analyzer.agent.md            Change impact analysis
│   ├── learning-mentor.agent.md            Teaching & learning
│   ├── code-reviewer.agent.md              Read-only code review
│   └── README.md                           ← Guide for this folder
│
├── prompts/                          ← 4. Prompt files (manual — /command)
│   ├── design-review.prompt.md             /design-review
│   ├── debug.prompt.md                     /debug
│   ├── impact.prompt.md                    /impact
│   ├── teach.prompt.md                     /teach
│   ├── refactor.prompt.md                  /refactor
│   ├── explain.prompt.md                   /explain
│   └── README.md                           ← Guide for this folder
│
├── skills/                           ← 5. Agent skills (auto by task match)
│   ├── java-build/
│   │   └── SKILL.md                        Compile & run Java
│   ├── design-patterns/
│   │   └── SKILL.md                        OOP patterns & SOLID reference
│   ├── java-debugging/
│   │   └── SKILL.md                        Exception patterns & debug techniques
│   └── README.md                           ← Guide for this folder
│
└── docs/
    └── getting-started.md                  ← Step-by-step tutorial
```

### What's NOT Official

You may see tutorials or colleagues using these folders — they are **not** part of the official spec:

| Folder | Status | What to Use Instead |
|---|---|---|
| `.github/roles/` | **Not official** | Use `.github/agents/` — agents serve the "role" purpose |
| `.github/copilot/` | **Not official** | Use `.github/copilot-instructions.md` (file at root of `.github/`) |

---

## This Project's Current Setup

This learning project includes working samples of each primitive, organized into **four specialist modes** that work as a senior developer's toolkit:

### Specialist Agents (Modes)

| Agent | File | Purpose |
|---|---|---|
| **Designer** | `agents/designer.agent.md` | Architecture review, SOLID/GRASP, design patterns, clean code |
| **Debugger** | `agents/debugger.agent.md` | Systematic root cause analysis, hypothesis-driven debugging |
| **Impact-Analyzer** | `agents/impact-analyzer.agent.md` | Ripple effect analysis, dependency mapping, risk assessment |
| **Learning-Mentor** | `agents/learning-mentor.agent.md` | Concept teaching with theory, analogies, and hands-on code |
| **Code-Reviewer** | `agents/code-reviewer.agent.md` | Bug detection, style checks, best practices (read-only) |

### Prompt Files (Slash Commands)

| Command | File | What It Does |
|---|---|---|
| `/design-review` | `prompts/design-review.prompt.md` | Full SOLID/GRASP design review of current file |
| `/debug` | `prompts/debug.prompt.md` | Systematic bug investigation workflow |
| `/impact` | `prompts/impact.prompt.md` | Change impact & ripple effect analysis |
| `/teach` | `prompts/teach.prompt.md` | Learn concepts from current file's code |
| `/refactor` | `prompts/refactor.prompt.md` | Identify and apply refactoring opportunities |
| `/explain` | `prompts/explain.prompt.md` | Beginner-friendly file explanation |

### Instruction Files (Auto-Applied)

| File | Applies To | Content |
|---|---|---|
| `copilot-instructions.md` | All requests | Project-wide rules and conventions |
| `instructions/java.instructions.md` | `**/*.java` | Java naming, style, Java 21+ features |
| `instructions/clean-code.instructions.md` | `**/*.java` | Clean code practices, code smell detection |

### Skills (Auto-Loaded by Task Match)

| Skill | Folder | Triggers On |
|---|---|---|
| `java-build` | `skills/java-build/` | Compile, run, build questions |
| `design-patterns` | `skills/design-patterns/` | Design patterns, SOLID, architecture questions |
| `java-debugging` | `skills/java-debugging/` | Exception analysis, debugging techniques |

### Agent Workflow (Handoffs)

The agents support **handoff buttons** for seamless multi-step workflows:

```
Designer ──→ Impact-Analyzer ──→ Agent (implement)
    │              │
    └──→ Agent     └──→ Code-Reviewer
    
Debugger ──→ Impact-Analyzer (assess fix impact)
    │
    └──→ Code-Reviewer (review fix quality)
    
Learning-Mentor ──→ Agent (practice exercise)
    │
    └──→ Code-Reviewer (review my code)
```

---

## How These Work Together

```
You open Main.java and ask a question
        │
        ├── copilot-instructions.md         ← ALWAYS loaded
        ├── instructions/java.instructions.md  ← Loaded because *.java matches
        │
        ├── If you selected an agent:
        │   └── agents/code-reviewer.agent.md  ← Agent instructions added
        │
        ├── If you typed /explain:
        │   └── prompts/explain.prompt.md      ← Prompt template used
        │
        └── If your question matches a skill description:
            └── skills/java-build/SKILL.md     ← Skill loaded automatically
```

---

## Priority Order

When multiple files are loaded, Copilot applies them in this order (later = higher priority):

```
1. copilot-instructions.md         (lowest — general rules)
2. *.instructions.md               (overrides general with specifics)
3. Agent instructions              (persona-specific rules)
4. Prompt file                     (task-specific instructions)
5. Your message                    (highest — what you type)
```

---

## Environment Support

| Feature | VS Code Chat | Copilot CLI | GitHub Web | GitHub PR |
|---|---|---|---|---|
| `copilot-instructions.md` | ✅ | ✅ | ✅ | ✅ |
| `*.instructions.md` | ✅ | ❌ | ❌ | ❌ |
| `*.agent.md` | ✅ | ❌ | ❌ | ❌ |
| `*.prompt.md` | ✅ | ❌ | ❌ | ❌ |
| `SKILL.md` | ✅ | ✅ | ❌ | ❌ |

---

## Sub-Guides

Each folder has its own detailed README with complete examples:

| Guide | What You'll Learn |
|---|---|
| [Getting Started Tutorial](docs/getting-started.md) | Step-by-step hands-on walkthrough |
| [Instructions Guide](instructions/README.md) | Glob patterns, conditional rules, examples |
| [Agents Guide](agents/README.md) | Custom personas, tools, handoffs |
| [Prompts Guide](prompts/README.md) | Slash commands, variables, task templates |
| [Skills Guide](skills/README.md) | Skill folders, scripts, progressive loading |

---

## Learning Path (Recommended Order)

```
Step 1: Read this README                              ← You are here
Step 2: Follow the Getting Started tutorial
Step 3: Experiment with the sample files
Step 4: Read each sub-guide as you need it
Step 5: Create your own customizations
Step 6: Apply what you learned to your production project
```

---

## Reference Links

- [VS Code: Customizing Copilot](https://code.visualstudio.com/docs/copilot/customization)
- [VS Code: Custom Instructions](https://code.visualstudio.com/docs/copilot/customization/custom-instructions)
- [VS Code: Custom Agents](https://code.visualstudio.com/docs/copilot/customization/custom-agents)
- [VS Code: Prompt Files](https://code.visualstudio.com/docs/copilot/customization/prompt-files)
- [VS Code: Agent Skills](https://code.visualstudio.com/docs/copilot/customization/agent-skills)
- [GitHub: Repository Instructions](https://docs.github.com/en/copilot/customizing-copilot/adding-repository-custom-instructions-for-github-copilot)
- [Community Examples](https://github.com/github/awesome-copilot)
- [Agent Skills Open Standard](https://agentskills.io/)
