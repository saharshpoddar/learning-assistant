# journal/ -- Curated Notes (Committed)

Committed counterpart to `notes/`. Content here has been reviewed and is worth
keeping in version control -- either because it will be useful to revisit, serves
as a record of decisions made, or documents understanding that took effort to build.

---

## When to promote here

Something from `notes/` (or directly from a session) is ready for `journal/` when:
- You'd genuinely want to re-read it later
- It explains a decision that someone (including future you) might question
- It captures understanding that took a meaningful session to build
- It's tidy enough to read without the session context

If you're unsure, leave it in `notes/` first.

---

## Directory Map

| Directory | What belongs here |
|-----------|-------------------|
| `learning/concepts/` | Concept deep-dives, how-it-works explanations |
| `learning/q-and-a/` | Interview prep, topic Q&As, problem walkthroughs |
| `learning/resources/` | Curated reading plans, resource collections |
| `sessions/` | Significant session changelogs worth long-term reference |
| `decisions/` | Architecture decisions, design choices, trade-off records |
| `reviews/` | Reviewed code analysis, refactor plans, impact assessments |

---

## Promotion Workflow

1. Find the file in `notes/` (or `notes/session/` if still in the active session).
2. Copy or move it to the matching path under `journal/`.
3. Clean up if needed -- remove rough edges, add a clear heading.
4. `git add` and commit with a meaningful message.

**Path mirror example:**
```
notes/learning/concepts/2026-02-21_java-records.md
                    ->
journal/learning/concepts/2026-02-21_java-records.md
```

---

## Naming Convention

Mirrors `notes/`:
```
{YYYY-MM-DD}_{topic-slug}.md             <- date-prefixed (most entries)
{topic-slug}.md                          <- timeless reference docs
```
