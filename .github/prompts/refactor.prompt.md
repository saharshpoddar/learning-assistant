```prompt
---
name: refactor
description: 'Suggest and apply refactorings to improve design, readability, and maintainability'
agent: designer
tools: ['codebase', 'usages', 'search', 'editFiles']
---

Analyze this file for refactoring opportunities:
${file}

## Instructions
For each refactoring:
1. **Name the refactoring** — use standard refactoring catalog names (Extract Method, Introduce Parameter Object, Replace Conditional with Polymorphism, etc.)
2. **What smells does it address?** — name the code smell or design smell
3. **Why does it matter?** — what principle does the current code violate?
4. **Before & After** — show the exact code transformation
5. **Risk** — what could go wrong? What needs retesting?

Prioritize refactorings by impact: highest value, lowest risk first.
```
