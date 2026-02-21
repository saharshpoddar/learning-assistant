#!/usr/bin/env bash
# clear-scratch.sh -- Clear files from ai/scratch/ (preserves README.md)
#
# Usage:
#   ./ai/scripts/clear-scratch.sh              Preview only -- lists files, no delete
#   ./ai/scripts/clear-scratch.sh --confirm    List then prompt before deleting
#   ./ai/scripts/clear-scratch.sh --force      Delete without prompting
#
# Run from any location -- resolves path relative to this script's directory.

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
SCRATCH_DIR="$REPO_ROOT/ai/scratch"

MODE="preview"
for arg in "$@"; do
    case "$arg" in
        --confirm) MODE="confirm" ;;
        --force)   MODE="force" ;;
        -h|--help)
            sed -n '2,10p' "$0" | sed 's/^# \?//'
            exit 0
            ;;
        *)
            echo "Unknown option: $arg" >&2
            exit 1
            ;;
    esac
done

if [[ ! -d "$SCRATCH_DIR" ]]; then
    echo "scratch/ does not exist: $SCRATCH_DIR"
    exit 0
fi

mapfile -t FILES < <(find "$SCRATCH_DIR" -type f ! -name "README.md")

if [[ ${#FILES[@]} -eq 0 ]]; then
    echo "scratch/ is already empty."
    exit 0
fi

echo ""
echo "Files in ai/scratch/:"
for f in "${FILES[@]}"; do
    echo "  ${f#"$SCRATCH_DIR/"}"
done
echo ""

if [[ "$MODE" == "force" ]]; then
    for f in "${FILES[@]}"; do rm -f "$f"; done
    echo "Cleared ${#FILES[@]} file(s) from scratch/."
    exit 0
fi

if [[ "$MODE" == "confirm" ]]; then
    read -r -p "Delete these ${#FILES[@]} file(s)? [y/N] " answer
    if [[ "$answer" =~ ^[yY] ]]; then
        for f in "${FILES[@]}"; do rm -f "$f"; done
        echo "Cleared ${#FILES[@]} file(s) from scratch/."
    else
        echo "Cancelled."
    fi
    exit 0
fi

# preview only
echo "Preview only (no files deleted)."
echo "Run with --confirm to delete interactively, or --force to delete immediately."
