# ai/saved/ -- Committed Reference

**Tracked by git. Pushed to the repo. Permanent.**

Content here is a real record -- something you would want available from
another machine, or something you want in your portfolio / history.

Think carefully before committing here; prefer `local/` if you are unsure.

---

## Typical content

- Architecture decisions and their rationale
- Concept explanations you worked through and want to keep
- Session changelogs / retrospectives you want to remember
- Polished Q&A notes, "things I learned today" summaries
- Resources and references you want searchable across machines
- Code review observations worth keeping long-term

---

## Organise how you think

No required subdirectory structure. Add folders only when you feel the need:

```
saved/
  2025-06-10_mcp-sse-architecture.md   <- flat, date-prefixed
  java/
    generics-cheatsheet.md             <- by subject
  mcp-servers/
    2025-05-20_auth-design.md          <- by project
```

---

## File template

```markdown
---
date: YYYY-MM-DD
type: concept | q-and-a | decision | changelog | review | resource | other
tags: [tag1, tag2]
project: mcp-servers | general | learning-assistant | <your-project>
---

# Title

Content here.
```

---

## Commit message template

```
Save: <topic>

Brief description of what this captures and why it is worth keeping.

-- created by gpt
```
