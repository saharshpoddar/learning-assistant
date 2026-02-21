<#
.SYNOPSIS
    Clear all files from ai/scratch/ (preserves README.md).

.DESCRIPTION
    Lists or deletes files in the ai/scratch/ directory.
    Run with no flags to preview what would be removed.
    Use -Confirm to delete with an interactive prompt.
    Use -Force to delete immediately without prompting.

.PARAMETER Confirm
    Delete files after showing a confirmation prompt.

.PARAMETER Force
    Delete files immediately with no confirmation.

.EXAMPLE
    .\ai\scripts\clear-scratch.ps1
    Preview only -- lists files but does not delete.

.EXAMPLE
    .\ai\scripts\clear-scratch.ps1 -Confirm
    List files and ask before deleting.

.EXAMPLE
    .\ai\scripts\clear-scratch.ps1 -Force
    Delete without prompting.
#>
[CmdletBinding()]
param(
    [switch]$Confirm,
    [switch]$Force
)

$repoRoot  = Split-Path -Parent (Split-Path -Parent $PSScriptRoot)
$scratchDir = Join-Path $repoRoot "ai\scratch"

if (-not (Test-Path $scratchDir)) {
    Write-Host "scratch/ does not exist: $scratchDir" -ForegroundColor Yellow
    exit 0
}

$files = Get-ChildItem -Path $scratchDir -Recurse -File |
         Where-Object { $_.Name -ne "README.md" }

if ($files.Count -eq 0) {
    Write-Host "scratch/ is already empty." -ForegroundColor Green
    exit 0
}

Write-Host ""
Write-Host "Files in ai/scratch/:" -ForegroundColor Cyan
$files | ForEach-Object {
    $relative = $_.FullName.Substring($scratchDir.Length + 1)
    Write-Host "  $relative" -ForegroundColor White
}
Write-Host ""

if ($Force) {
    $files | Remove-Item -Force
    Write-Host "Cleared $($files.Count) file(s) from scratch/." -ForegroundColor Green
    exit 0
}

if ($Confirm) {
    $answer = Read-Host "Delete these $($files.Count) file(s)? [y/N]"
    if ($answer -match '^[yY]') {
        $files | Remove-Item -Force
        Write-Host "Cleared $($files.Count) file(s) from scratch/." -ForegroundColor Green
    } else {
        Write-Host "Cancelled." -ForegroundColor Yellow
    }
    exit 0
}

# No flags -- preview only
Write-Host "Preview only (no files deleted)." -ForegroundColor Yellow
Write-Host "Run with -Confirm to delete interactively, or -Force to delete immediately."
