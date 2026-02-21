#!/usr/bin/env bash
# promote.sh -- Move a file between ai/ tiers (scratch, local, saved)
#
# Usage:
#   ./ai/scripts/promote.sh <source> <tier> [subdir]
#
# Arguments:
#   source   Path relative to ai/   (e.g. scratch/draft.md  or  local/java/note.md)
#   tier     Destination: local | saved
#   subdir   Optional subdirectory within tier  (e.g. java -> ai/<tier>/java/<file>)
#
# Examples:
#   ./ai/scripts/promote.sh scratch/draft.md local
#   ./ai/scripts/promote.sh scratch/draft.md local java
#   ./ai/scripts/promote.sh local/note.md saved

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
AI_ROOT="$REPO_ROOT/ai"

SOURCE="${1:-}"
TIER="${2:-}"
SUBDIR="${3:-}"

if [[ -z "$SOURCE" || -z "$TIER" ]]; then
    echo "Usage: $0 <source> <tier> [subdir]" >&2
    echo "  tier: local | saved" >&2
    exit 1
fi

if [[ "$TIER" != "local" && "$TIER" != "saved" ]]; then
    echo "Error: tier must be 'local' or 'saved', got: $TIER" >&2
    exit 1
fi

# Resolve source
if [[ "$SOURCE" = /* ]]; then
    SOURCE_PATH="$SOURCE"
else
    SOURCE_PATH="$AI_ROOT/$SOURCE"
fi

if [[ ! -f "$SOURCE_PATH" ]]; then
    echo "Error: source not found: $SOURCE_PATH" >&2
    exit 1
fi

FILENAME="$(basename "$SOURCE_PATH")"

if [[ -n "$SUBDIR" ]]; then
    DEST_DIR="$AI_ROOT/$TIER/$SUBDIR"
else
    DEST_DIR="$AI_ROOT/$TIER"
fi

DEST_PATH="$DEST_DIR/$FILENAME"
mkdir -p "$DEST_DIR"

if [[ -f "$DEST_PATH" ]]; then
    read -r -p "Destination already exists: ${DEST_PATH#"$AI_ROOT/"} -- Overwrite? [y/N] " answer
    if [[ ! "$answer" =~ ^[yY] ]]; then
        echo "Cancelled."
        exit 0
    fi
fi

mv "$SOURCE_PATH" "$DEST_PATH"

SOURCE_REL="${SOURCE_PATH#"$AI_ROOT/"}"
DEST_REL="${DEST_PATH#"$AI_ROOT/"}"
echo "Moved: $SOURCE_REL -> $DEST_REL"

if [[ "$TIER" == "saved" ]]; then
    echo ""
    read -r -p "Run 'git add' on the file? [Y/n] " add
    if [[ ! "$add" =~ ^[nN] ]]; then
        GIT_PATH="ai/saved/${DEST_PATH#"$AI_ROOT/saved/"}"
        git -C "$REPO_ROOT" add "$GIT_PATH"
        echo "Staged: $GIT_PATH"
        echo "Next: git commit -m \"Save: <topic>\""
    fi
fi
