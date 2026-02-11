# GitHub Copilot Customization — Learning Guide

> **Purpose:** Learn how to customize GitHub Copilot using all 5 official primitives.  
> **Project:** `learning-assistant` — a simple Java project for hands-on experimentation.  
> **Audience:** Developers new to Copilot customization who want to learn by doing.

---

## 📑 Table of Contents

- [Why Customize Copilot?](#why-customize-copilot)
- [The 5 Official Primitives](#the-5-official-primitives)
- [Folder Structure](#folder-structure)
- [This Project's Setup](#this-projects-current-setup)
  - [Specialist Agents](#-specialist-agents-modes)
  - [Slash Commands](#-slash-commands-prompts)
  - [Auto-Applied Instructions](#-auto-applied-instructions)
  - [Auto-Loaded Skills](#-auto-loaded-skills)
  - [Handoff Workflows](#-agent-workflow-handoffs)
- [How It All Connects](#how-these-work-together)
- [Priority Order](#priority-order)
- [Environment Support](#environment-support)
- [Documentation Map](#-documentation-map)
- [Learning Path](#-learning-path)
- [Reference Links](#-reference-links)

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

| Need to... | Use this primitive |
|---|---|
| Set project-wide rules Copilot always follows | `copilot-instructions.md` |
| Add rules only when editing certain file types | `*.instructions.md` |
| Create a specialist persona (reviewer, planner) | `*.agent.md` |
| Save a reusable task as a `/slash-command` | `*.prompt.md` |
| Bundle instructions + scripts + templates | `SKILL.md` folder |

---

## Folder Structure

```
.github/
│
├── copilot-instructions.md              ← Always-on (auto-loaded every request)
│
├── instructions/                        ← Path-specific (auto-loaded by glob)
│   ├── 📋 README.md                         Guide: how instructions work
│   ├── java.instructions.md                 applyTo: "**/*.java"
│   └── clean-code.instructions.md           applyTo: "**/*.java"
│
├── agents/                              ← Custom agents (select from dropdown)
│   ├── 🤖 README.md                         Guide: how agents work
│   ├── designer.agent.md                   Architecture & design review
│   ├── debugger.agent.md                   Systematic debugging
│   ├── impact-analyzer.agent.md            Change impact analysis
│   ├── learning-mentor.agent.md            Teaching & learning
│   └── code-reviewer.agent.md             Read-only code review
│
├── prompts/                             ← Slash commands (type /command)
│   ├── 🎯 README.md                         Guide: how prompts work
│   ├── design-review.prompt.md             /design-review
│   ├── debug.prompt.md                     /debug
│   ├── impact.prompt.md                    /impact
│   ├── teach.prompt.md                     /teach
│   ├── refactor.prompt.md                  /refactor
│   └── explain.prompt.md                   /explain
│
├── skills/                              ← Agent skills (auto by task match)
│   ├── 🛠️ README.md                         Guide: how skills work
│   ├── java-build/SKILL.md                 Compile & run Java
│   ├── design-patterns/SKILL.md            OOP patterns & SOLID reference
│   └── java-debugging/SKILL.md             Exception patterns & debug techniques
│
└── docs/                                ← Documentation & tutorials
    ├── getting-started.md                  Step-by-step tutorial
    └── customization-guide.md              Architecture deep-dive
```

### What's NOT Official

| Folder | Status | What to Use Instead |
|---|---|---|
| `.github/roles/` | **Not official** | Use `.github/agents/` — agents serve the "role" purpose |
| `.github/copilot/` | **Not official** | Use `.github/copilot-instructions.md` (file at root of `.github/`) |

---

## This Project's Current Setup

This learning project includes working samples of each primitive, organized into **four specialist modes** that work as a senior developer's toolkit.

<br>

### 🤖 Specialist Agents (Modes)

> **How to use:** Select from the agent dropdown in VS Code Chat.

| Agent | File | Purpose |
|---|---|---|
| **Designer** | [`designer.agent.md`](agents/designer.agent.md) | Architecture review, SOLID/GRASP, design patterns, clean code |
| **Debugger** | [`debugger.agent.md`](agents/debugger.agent.md) | Systematic root cause analysis, hypothesis-driven debugging |
| **Impact-Analyzer** | [`impact-analyzer.agent.md`](agents/impact-analyzer.agent.md) | Ripple effect analysis, dependency mapping, risk assessment |
| **Learning-Mentor** | [`learning-mentor.agent.md`](agents/learning-mentor.agent.md) | Concept teaching with theory, analogies, and hands-on code |
| **Code-Reviewer** | [`code-reviewer.agent.md`](agents/code-reviewer.agent.md) | Bug detection, style checks, best practices (read-only) |

> 📖 **Deep dive:** [Agents Guide →](agents/README.md)

<br>

### 🎯 Slash Commands (Prompts)

> **How to use:** Type `/command` in VS Code Chat.

| Command | File | What It Does |
|---|---|---|
| `/design-review` | [`design-review.prompt.md`](prompts/design-review.prompt.md) | Full SOLID/GRASP design review of current file |
| `/debug` | [`debug.prompt.md`](prompts/debug.prompt.md) | Systematic bug investigation workflow |
| `/impact` | [`impact.prompt.md`](prompts/impact.prompt.md) | Change impact & ripple effect analysis |
| `/teach` | [`teach.prompt.md`](prompts/teach.prompt.md) | Learn concepts from current file's code |
| `/refactor` | [`refactor.prompt.md`](prompts/refactor.prompt.md) | Identify and apply refactoring opportunities |
| `/explain` | [`explain.prompt.md`](prompts/explain.prompt.md) | Beginner-friendly file explanation |

> 📖 **Deep dive:** [Prompts Guide →](prompts/README.md)

<br>

### 📋 Auto-Applied Instructions

> **How to use:** These load automatically — no action needed.

| File | Applies To | Content |
|---|---|---|
| [`copilot-instructions.md`](copilot-instructions.md) | All requests | Project-wide rules and conventions |
| [`java.instructions.md`](instructions/java.instructions.md) | `**/*.java` | Java naming, style, Java 21+ features |
| [`clean-code.instructions.md`](instructions/clean-code.instructions.md) | `**/*.java` | Clean code practices, code smell detection |

> 📖 **Deep dive:** [Instructions Guide →](instructions/README.md)

<br>

### 🛠️ Auto-Loaded Skills

> **How to use:** Just ask a matching question — skills load automatically.

| Skill | Folder | Triggers On |
|---|---|---|
| `java-build` | [`skills/java-build/`](skills/java-build/SKILL.md) | Compile, run, build questions |
| `design-patterns` | [`skills/design-patterns/`](skills/design-patterns/SKILL.md) | Design patterns, SOLID, architecture questions |
| `java-debugging` | [`skills/java-debugging/`](skills/java-debugging/SKILL.md) | Exception analysis, debugging techniques |

> 📖 **Deep dive:** [Skills Guide →](skills/README.md)

<br>

### 🔀 Agent Workflow (Handoffs)

The agents support **handoff buttons** for seamless multi-step workflows:

```
  ┌────────────┐        ┌───────────────────┐        ┌─────────────┐
  │  Designer  │──────→ │  Impact-Analyzer  │──────→ │    Agent    │
  │  (think)   │        │  (assess risk)    │        │  (build)    │
  └──────┬─────┘        └────────┬──────────┘        └─────────────┘
         │                       │
         └──→ Agent              └──→ Code-Reviewer
              (implement)             (verify)

  ┌────────────┐        ┌───────────────────┐
  │  Debugger  │──────→ │  Impact-Analyzer  │
  │  (find)    │        │  (assess fix)     │
  └──────┬─────┘        └───────────────────┘
         │
         └──→ Code-Reviewer
              (review fix)

  ┌──────────────────┐        ┌─────────────┐
  │  Learning-Mentor │──────→ │    Agent    │
  │  (teach)         │        │  (practice) │
  └────────┬─────────┘        └─────────────┘
           │
           └──→ Code-Reviewer
                (review my code)
```

---

## How These Work Together

```
You open Main.java and ask a question
│
├── 📋 copilot-instructions.md               ← ALWAYS loaded
├── 📋 instructions/java.instructions.md     ← Loaded because *.java matches
├── 📋 instructions/clean-code...            ← Loaded because *.java matches
│
├── If you selected an agent:
│   └── 🤖 agents/designer.agent.md         ← Agent persona added
│
├── If you typed /design-review:
│   └── 🎯 prompts/design-review.prompt.md  ← Task template used
│
└── If your question matches a skill:
    └── 🛠️ skills/design-patterns/SKILL.md  ← Knowledge loaded
```

---

## Priority Order

When multiple files are loaded, Copilot applies them in this order (later = higher priority):

| Priority | Source | Example |
|---|---|---|
| 1 (lowest) | `copilot-instructions.md` | General project rules |
| 2 | `*.instructions.md` | Language/path-specific overrides |
| 3 | Agent instructions | Persona-specific behavior |
| 4 | Prompt file | Task-specific instructions |
| **5 (highest)** | **Your message** | What you type in chat |

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

## 📚 Documentation Map

```
📖 YOU ARE HERE
│
├─── Guides by Primitive
│    ├── instructions/README.md ·········· Glob patterns, conditional rules
│    ├── agents/README.md ················ Personas, tools, handoffs
│    ├── prompts/README.md ··············· Slash commands, variables
│    └── skills/README.md ················ Skill folders, progressive loading
│
└─── Tutorials & Deep Dives
     ├── docs/getting-started.md ········· Hands-on: verify setup, try each primitive
     └── docs/customization-guide.md ····· Theory: how primitives connect & extend
```

| Guide | What You'll Learn | Time |
|---|---|---|
| [Getting Started →](docs/getting-started.md) | Verify setup, try each primitive hands-on | ~30 min |
| [Customization Guide →](docs/customization-guide.md) | Architecture, how primitives connect, extending | ~20 min |
| [Instructions Guide →](instructions/README.md) | Glob patterns, conditional rules, examples | ~15 min |
| [Agents Guide →](agents/README.md) | Custom personas, tools, handoffs, examples | ~15 min |
| [Prompts Guide →](prompts/README.md) | Slash commands, variables, task templates | ~15 min |
| [Skills Guide →](skills/README.md) | Skill folders, scripts, progressive loading | ~15 min |

---

## 🧭 Learning Path

| Step | What to Do | Guide |
|---|---|---|
| **1** | Read this README | ← You are here |
| **2** | Follow the hands-on tutorial | [Getting Started →](docs/getting-started.md) |
| **3** | Experiment with the sample files | Try agents, prompts, skills |
| **4** | Understand how it all connects | [Customization Guide →](docs/customization-guide.md) |
| **5** | Deep dive into each primitive as needed | See [Documentation Map](#-documentation-map) |
| **6** | Create your own customizations | Templates in each guide |
| **7** | Apply to your production project | Port what works |

---

## 🔗 Reference Links

**Official Documentation**

- [VS Code: Customizing Copilot](https://code.visualstudio.com/docs/copilot/customization)
- [VS Code: Custom Instructions](https://code.visualstudio.com/docs/copilot/customization/custom-instructions)
- [VS Code: Custom Agents](https://code.visualstudio.com/docs/copilot/customization/custom-agents)
- [VS Code: Prompt Files](https://code.visualstudio.com/docs/copilot/customization/prompt-files)
- [VS Code: Agent Skills](https://code.visualstudio.com/docs/copilot/customization/agent-skills)

**GitHub & Community**

- [GitHub: Repository Instructions](https://docs.github.com/en/copilot/customizing-copilot/adding-repository-custom-instructions-for-github-copilot)
- [Community Examples (awesome-copilot)](https://github.com/github/awesome-copilot)
- [Agent Skills Open Standard](https://agentskills.io/)

---

<p align="center">

**Navigation:** [Getting Started →](docs/getting-started.md) · [Customization Guide →](docs/customization-guide.md) · [Instructions](instructions/README.md) · [Agents](agents/README.md) · [Prompts](prompts/README.md) · [Skills](skills/README.md)

</p>
