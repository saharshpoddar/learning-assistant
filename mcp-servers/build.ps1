<#
.SYNOPSIS
    Compile all MCP server Java sources.

.DESCRIPTION
    Resolves javac automatically in this order:
      1. build.env.local file (machine-specific overrides, gitignored)
      2. JAVA_HOME environment variable
      3. VS Code redhat.java extension bundled JDK
      4. javac on PATH

.PARAMETER OutDir
    Output directory for compiled .class files. Default: out

.PARAMETER Clean
    Delete the output directory before compiling.

.EXAMPLE
    .\build.ps1
    .\build.ps1 -Clean
    .\build.ps1 -OutDir build/classes
#>
[CmdletBinding()]
param(
    [string] $OutDir = "out",
    [switch] $Clean
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $ScriptDir

# Load build.env.local if present (machine-specific paths, gitignored).
# Values here OVERRIDE existing environment variables -- this file is the
# local-authoritative config for paths like JAVA_HOME.
$EnvLocal = Join-Path $ScriptDir 'build.env.local'
if (Test-Path $EnvLocal) {
    Get-Content $EnvLocal | ForEach-Object {
        if ($_ -match '^\s*([^#=]+?)\s*=\s*(.+?)\s*$') {
            Set-Item "env:$($Matches[1])" $Matches[2]
        }
    }
}

function Find-Javac {
    if ($env:JAVA_HOME) {
        $candidate = Join-Path $env:JAVA_HOME 'bin\javac.exe'
        if (Test-Path $candidate) { return $candidate }
        Write-Host "  JAVA_HOME='$env:JAVA_HOME' has no javac (JRE only). Trying fallbacks..." -ForegroundColor Yellow
    }

    $vsCodeExt = Join-Path $env:USERPROFILE '.vscode\extensions'
    if (Test-Path $vsCodeExt) {
        $jdkDirs = Get-ChildItem $vsCodeExt -Filter 'redhat.java-*' -Directory | Sort-Object Name -Descending
        foreach ($dir in $jdkDirs) {
            $jreRoot = Join-Path $dir.FullName 'jre'
            if (Test-Path $jreRoot) {
                $hit = Get-ChildItem $jreRoot -Recurse -Filter 'javac.exe' -ErrorAction SilentlyContinue | Select-Object -First 1
                if ($hit) { return $hit.FullName }
            }
        }
    }

    $onPath = Get-Command javac -ErrorAction SilentlyContinue
    if ($onPath) { return $onPath.Source }
    return $null
}

Write-Host ""
Write-Host "Locating javac..." -ForegroundColor Cyan
$Javac = Find-Javac

if (-not $Javac) {
    Write-Host "ERROR: javac not found." -ForegroundColor Red
    Write-Host "Install a JDK 17+ and set JAVA_HOME, or install the VS Code Java Extension Pack:" -ForegroundColor Red
    Write-Host "  https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack" -ForegroundColor Red
    exit 1
}

Write-Host "  javac  : $Javac" -ForegroundColor Cyan
$VersionLine = (& $Javac -version 2>&1) -join ' '
Write-Host "  version: $VersionLine" -ForegroundColor Cyan

$JavaExe = Join-Path (Split-Path $Javac -Parent) 'java.exe'
if (Test-Path $JavaExe) {
    Write-Host "  java   : $JavaExe" -ForegroundColor DarkGray
}
Write-Host ""

if ($Clean -and (Test-Path $OutDir)) {
    Write-Host "Cleaning $OutDir/ ..." -ForegroundColor Yellow
    Remove-Item $OutDir -Recurse -Force
}
New-Item -ItemType Directory -Path $OutDir -Force | Out-Null

$Sources = Get-ChildItem -Path 'src' -Recurse -Filter '*.java' | Select-Object -ExpandProperty FullName
if (-not $Sources) {
    Write-Host "ERROR: No .java files found under src/" -ForegroundColor Red
    exit 1
}

Write-Host "Compiling $($Sources.Count) source files  ->  $OutDir/" -ForegroundColor Cyan

$SourceList = [System.IO.Path]::GetTempFileName()
# WriteAllLines uses UTF-8 without BOM -- required so javac reads the first filename cleanly
[System.IO.File]::WriteAllLines($SourceList, $Sources)

$ExitCode = 0
try {
    & $Javac -d $OutDir --release 21 "@$SourceList"
    $ExitCode = $LASTEXITCODE
} finally {
    Remove-Item $SourceList -ErrorAction SilentlyContinue
}

Write-Host ""
if ($ExitCode -eq 0) {
    Write-Host "BUILD SUCCESS -- compiled $($Sources.Count) files" -ForegroundColor Green
} else {
    Write-Host "BUILD FAILED  (exit code $ExitCode)" -ForegroundColor Red
    exit $ExitCode
}
