# ai/ -- AI-Generated Content

A local workspace for content produced by AI agents, Copilot, custom prompts,
and MCP servers during development and learning sessions.

---

## Three Tiers

```
ai/
  scratch/   DISCARD   session scratchpad -- clear when done       [gitignored]
  local/     KEEP      survives sessions, stays on this machine    [gitignored]
  saved/     PUSH      committed to the repo, permanent reference  [tracked]
  scripts/   TOOLS     utilities for managing content              [tracked]
```

The three words map to what you would actually say out loud:
- **"This is scratch work"** -> drop it in `scratch/`
- **"I want to keep this"** -> move it to `local/`
- **"This should go to the repo"** -> promote it to `saved/` and commit

### When to use each tier

| Tier | Use when... | Gitignored? |
|------|------------|-------------|
| `scratch/` | Working through a problem, rough drafts, mid-session context | Yes |
| `local/` | It was useful, you may come back to it, but not repo-worthy yet | Yes |
| `saved/` | Worth having on another machine, or a permanent decision record | No |

---

## No Enforced Subfolders

Files go directly into `scratch/`, `local/`, or `saved/`. There are no required
topic subfolders -- previously prescribed folders like `decisions/`, `changelogs/`
were arbitrary and did not match how you actually think about content.

**Organise by your own mental model -- add subdirs only when you feel the need:**
- By project:  `local/mcp-servers/`, `local/learning-assistant/`
- By subject:  `local/java/`, `local/spring/`, `local/docker/`
- By time:     flat files with date prefix, sort naturally
- Mix freely -- there are no wrong choices here

---

## File Template

Start every file with this frontmatter so content is searchable and filterable
by future tooling (scripts, Obsidian vault import, search engine, etc.):

```markdown
---
date: YYYY-MM-DD
type: concept | q-and-a | decision | changelog | review | resource | other
tags: [tag1, tag2, tag3]
project: mcp-servers | general | learning-assistant | <your-project>
---

# Title

Content here.
```

The four frontmatter fields are the baseline. Add any additional fields that
help you (e.g. `source: copilot`, `status: draft`, `related: other-file.md`).

---

## Moving Files Between Tiers

Use the scripts in `ai/scripts/` or move files manually:

### Using scripts (recommended)

```powershell
# Windows PowerShell -- run from repo root
.\ai\scripts\promote.ps1 scratch\draft.md local            # scratch -> local
.\ai\scripts\promote.ps1 local\note.md saved               # local -> saved (also git-adds)
.\ai\scripts\promote.ps1 scratch\draft.md local java       # put in local/java/ subdir
.\ai\scripts\clear-scratch.ps1                             # preview scratch contents
.\ai\scripts\clear-scratch.ps1 -Confirm                    # clear with confirmation prompt
.\ai\scripts\clear-scratch.ps1 -Force                      # clear immediately, no prompt
```

```bash
# Bash -- run from repo root
./ai/scripts/promote.sh scratch/draft.md local             # scratch -> local
./ai/scripts/promote.sh local/note.md saved                # local -> saved (also git-adds)
./ai/scripts/promote.sh scratch/draft.md local java        # put in local/java/ subdir
./ai/scripts/clear-scratch.sh                              # preview scratch contents
./ai/scripts/clear-scratch.sh --confirm                    # clear with confirmation
./ai/scripts/clear-scratch.sh --force                      # clear immediately
```

### Manually

```powershell
# scratch -> local
Move-Item ai\scratch\draft.md ai\local\

# local -> saved, then commit
Move-Item ai\local\note.md ai\saved\
git add ai/saved/note.md
git commit -m "Save: <topic>"
```

---

## Naming Convention

```
YYYY-MM-DD_topic-slug.md           <- date-prefixed (most files, sorts cleanly)
topic-slug.md                      <- timeless reference docs
YYYY-MM-DD_project_topic-slug.md   <- when project context matters in the name
```

---

## Quick Reference

```
New session starts  ->  use scratch/ freely, no organisation needed
Session ends        ->  worth keeping? yes -> local/   no -> clear scratch
Later, needs repo   ->  promote to saved/ + git commit
```