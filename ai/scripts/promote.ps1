<#
.SYNOPSIS
    Move a file between ai/ tiers (scratch, local, saved).

.DESCRIPTION
    Moves a file from its current tier to a target tier.
    Source path is relative to the ai/ directory.
    If -SubDir is provided, the file lands in ai/<tier>/<subdir>/.
    When promoting to saved/, the script offers to run `git add` on the file.

.PARAMETER Source
    Source path relative to the ai/ directory.
    Examples: scratch\draft.md   local\java\note.md

.PARAMETER Tier
    Destination tier: local | saved

.PARAMETER SubDir
    Optional subdirectory within the destination tier.
    Example: java  ->  ai/<tier>/java/<filename>

.EXAMPLE
    .\ai\scripts\promote.ps1 scratch\draft.md local
    Moves ai/scratch/draft.md to ai/local/draft.md

.EXAMPLE
    .\ai\scripts\promote.ps1 scratch\draft.md local java
    Moves ai/scratch/draft.md to ai/local/java/draft.md

.EXAMPLE
    .\ai\scripts\promote.ps1 local\note.md saved
    Moves ai/local/note.md to ai/saved/note.md and offers git add.
#>
[CmdletBinding()]
param(
    [Parameter(Mandatory)]
    [string]$Source,

    [Parameter(Mandatory)]
    [ValidateSet("local", "saved")]
    [string]$Tier,

    [string]$SubDir = ""
)

$repoRoot = Split-Path -Parent (Split-Path -Parent $PSScriptRoot)
$aiRoot   = Join-Path $repoRoot "ai"

# Resolve source -- allow bare filename (searched across tiers), relative, or absolute
$sourcePath = $Source
if (-not [System.IO.Path]::IsPathRooted($sourcePath)) {
    $sourcePath = Join-Path $aiRoot $sourcePath
}

if (-not (Test-Path $sourcePath)) {
    Write-Error "Source not found: $sourcePath"
    exit 1
}

$fileName   = Split-Path $sourcePath -Leaf
$destDir    = if ($SubDir) { Join-Path $aiRoot "$Tier\$SubDir" } else { Join-Path $aiRoot $Tier }
$destPath   = Join-Path $destDir $fileName

if (-not (Test-Path $destDir)) {
    New-Item -ItemType Directory -Path $destDir | Out-Null
}

if (Test-Path $destPath) {
    $answer = Read-Host "Destination already exists: $destPath`nOverwrite? [y/N]"
    if ($answer -notmatch '^[yY]') {
        Write-Host "Cancelled." -ForegroundColor Yellow
        exit 0
    }
}

Move-Item -Path $sourcePath -Destination $destPath -Force
Write-Host "Moved: $($sourcePath.Substring($aiRoot.Length + 1)) -> $($destPath.Substring($aiRoot.Length + 1))" -ForegroundColor Green

if ($Tier -eq "saved") {
    Write-Host ""
    $add = Read-Host "Run 'git add' on the file? [Y/n]"
    if ($add -notmatch '^[nN]') {
        $relativePath = "ai/saved/" + ($destPath.Substring((Join-Path $aiRoot "saved").Length + 1) -replace '\\', '/')
        Push-Location $repoRoot
        git add $relativePath
        Pop-Location
        Write-Host "Staged: $relativePath" -ForegroundColor Cyan
        Write-Host "Next: git commit -m `"Save: <topic>`""
    }
}
