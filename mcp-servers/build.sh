#!/usr/bin/env bash
# =============================================================================
# build.sh — Compile all MCP server Java sources
# =============================================================================
# Resolves javac automatically in this order:
#   1. build.env.local file (machine-specific overrides, gitignored)
#   2. JAVA_HOME environment variable
#   3. VS Code redhat.java extension bundled JDK  (~/.vscode/extensions)
#   4. javac on PATH
#
# Usage:
#   ./build.sh              # incremental compile
#   ./build.sh --clean      # delete out/ first, then compile
#   ./build.sh --out=build  # use a different output directory
# =============================================================================

set -euo pipefail

# ─── Defaults ─────────────────────────────────────────────────────────────────
OUT_DIR="out"
CLEAN=false

for arg in "$@"; do
    case "$arg" in
        --clean)    CLEAN=true ;;
        --out=*)    OUT_DIR="${arg#*=}" ;;
        -h|--help)
            sed -n '2,/^# ====*$/p' "$0" | grep '^#' | sed 's/^# \?//'
            exit 0 ;;
        *)
            echo "Unknown argument: $arg  (use --clean, --out=DIR, or --help)"
            exit 1 ;;
    esac
done

# --- Resolve script directory -------------------------------------------------
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

# Load build.env.local if present (machine-specific paths, gitignored).
# Values here OVERRIDE existing environment variables -- this file is the
# local-authoritative config for paths like JAVA_HOME.
if [[ -f "$SCRIPT_DIR/build.env.local" ]]; then
    while IFS='=' read -r key val; do
        [[ "$key" =~ ^[[:space:]]*# ]] && continue
        [[ -z "${key// }" ]] && continue
        key="${key// /}"
        val="${val#"${val%%[![:space:]]*}"}"
        val="${val%"${val##*[![:space:]]}"}"        
        export "$key"="$val"
fi

# --- Find javac ---------------------------------------------------------------
find_javac() {
    # 1. JAVA_HOME (must have bin/javac — JREs don't)
    if [[ -n "${JAVA_HOME:-}" ]]; then
        if [[ -x "$JAVA_HOME/bin/javac" ]]; then
            echo "$JAVA_HOME/bin/javac"
            return 0
        else
            echo "  WARNING: JAVA_HOME='$JAVA_HOME' has no javac (JRE only?). Trying fallbacks..." >&2
        fi
    fi

    # 2. VS Code redhat.java extension bundled JDK (Linux/macOS: ~/.vscode/extensions)
    local vscode_ext="$HOME/.vscode/extensions"
    if [[ -d "$vscode_ext" ]]; then
        local hit
        hit=$(find "$vscode_ext" \
                -path "*/redhat.java-*/jre/*/bin/javac" \
                -type f 2>/dev/null \
              | sort -rV | head -1)
        if [[ -n "$hit" ]]; then
            echo "$hit"
            return 0
        fi
    fi

    # 3. javac on PATH
    if command -v javac &>/dev/null; then
        command -v javac
        return 0
    fi

    return 1
}

echo ""
echo "Locating javac..."

JAVAC=$(find_javac 2>&1) || {
    echo ""
    echo "ERROR: javac not found."
    echo "Install a JDK 17 or later and set the JAVA_HOME environment variable,"
    echo "or install the VS Code Java Extension Pack (it bundles a JDK automatically):"
    echo "  https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack"
    exit 1
}

echo "  javac  : $JAVAC"
"$JAVAC" -version
echo ""

# Also report java (same bin directory) for reference
JAVA_BIN="$(dirname "$JAVAC")/java"
if [[ -x "$JAVA_BIN" ]]; then
    echo "  java   : $JAVA_BIN"
    echo "  (Use this path in run scripts when no system java is on PATH)"
    echo ""
fi

# ─── Output directory ─────────────────────────────────────────────────────────
if $CLEAN && [[ -d "$OUT_DIR" ]]; then
    echo "Cleaning $OUT_DIR/ ..."
    rm -rf "$OUT_DIR"
fi
mkdir -p "$OUT_DIR"

# ─── Collect .java source files ───────────────────────────────────────────────
SOURCE_LIST=$(mktemp)
trap 'rm -f "$SOURCE_LIST"' EXIT

find src -name "*.java" -type f | sort > "$SOURCE_LIST"
COUNT=$(wc -l < "$SOURCE_LIST" | tr -d ' ')

if [[ "$COUNT" -eq 0 ]]; then
    echo "ERROR: No .java files found under src/  (run from mcp-servers/)"
    exit 1
fi

echo "Compiling $COUNT source files  →  $OUT_DIR/"

# ─── Compile ──────────────────────────────────────────────────────────────────
"$JAVAC" -d "$OUT_DIR" --release 21 "@$SOURCE_LIST"

echo ""
echo "BUILD SUCCESS — compiled $COUNT files"
