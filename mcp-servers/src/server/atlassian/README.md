# Atlassian MCP Server

> **Unified MCP server for Jira, Confluence, and Bitbucket — issue tracking, documentation, and code collaboration through a single AI-accessible interface.**

---

## Overview

This MCP server provides a **unified gateway** to Atlassian's core developer tools:

- **Jira** — create, search, update, and transition issues; manage sprints and boards
- **Confluence** — search, read, create, and update pages and spaces
- **Bitbucket** — browse repos, pull requests, commits, and code search

**What it does:**

- **Issue Management** — search, create, update, and transition Jira issues with JQL support
- **Sprint Tracking** — view active sprints, board summaries, and backlog items
- **Documentation** — search and read Confluence pages with CQL; create and update content
- **Code Collaboration** — browse Bitbucket repositories, review pull requests, view diffs
- **Cross-Product Linking** — follow links between Jira issues, Confluence pages, and Bitbucket PRs
- **Formatting** — clean, readable output suitable for AI assistant consumption

---

## Quick Start

```bash
# From the mcp-servers directory:

# Compile
javac -d out src/server/atlassian/**/*.java src/server/atlassian/*.java

# Run demo (no stdin needed)
java -cp out server.atlassian.AtlassianServer --demo

# List available tools
java -cp out server.atlassian.AtlassianServer --list-tools

# Start as MCP server (STDIO transport)
java -cp out server.atlassian.AtlassianServer
```

---

## Available Tools

### Jira Tools

| Tool | Description | Required Args |
| ------ | ------------- | --------------- |
| `jira_search_issues` | Search issues using JQL or free text | `query` |
| `jira_get_issue` | Get full details of a specific issue | `issueKey` |
| `jira_create_issue` | Create a new issue | `projectKey`, `summary`, `issueType` |
| `jira_update_issue` | Update fields on an existing issue | `issueKey` |
| `jira_transition_issue` | Move issue to a new status | `issueKey`, `transition` |
| `jira_list_projects` | List accessible Jira projects | _(none)_ |
| `jira_get_sprint` | Get active sprint details for a board | `boardId` |

### Confluence Tools

| Tool | Description | Required Args |
| ------ | ------------- | --------------- |
| `confluence_search` | Search pages using CQL or free text | `query` |
| `confluence_get_page` | Get full content of a page | `pageId` |
| `confluence_create_page` | Create a new page in a space | `spaceKey`, `title`, `body` |
| `confluence_update_page` | Update an existing page | `pageId`, `title`, `body` |
| `confluence_list_spaces` | List accessible Confluence spaces | _(none)_ |

### Bitbucket Tools

| Tool | Description | Required Args |
| ------ | ------------- | --------------- |
| `bitbucket_list_repos` | List repositories in a workspace | `workspace` |
| `bitbucket_get_repo` | Get repository details | `workspace`, `repoSlug` |
| `bitbucket_list_pull_requests` | List PRs for a repository | `workspace`, `repoSlug` |
| `bitbucket_get_pull_request` | Get full PR details with diff | `workspace`, `repoSlug`, `prId` |
| `bitbucket_search_code` | Search code across repositories | `workspace`, `query` |

---

## Architecture

```text
server.atlassian
├── AtlassianServer.java              — STDIO server entry point
├── model/                             — Shared enums & records
│   ├── AtlassianProduct.java          — Product enum (JIRA, CONFLUENCE, BITBUCKET)
│   ├── AtlassianCredentials.java      — Auth credentials record
│   ├── ConnectionConfig.java          — Base URL, auth, timeout settings
│   └── ToolResponse.java             — Standardized tool response wrapper
├── client/                            — REST API clients
│   ├── AtlassianRestClient.java       — Shared HTTP client with auth
│   ├── JiraClient.java                — Jira REST API v3 client
│   ├── ConfluenceClient.java          — Confluence REST API v2 client
│   └── BitbucketClient.java           — Bitbucket REST API 2.0 client
├── handler/                           — MCP tool dispatch
│   ├── ToolHandler.java               — Central tool router
│   ├── JiraHandler.java               — Jira tool implementations
│   ├── ConfluenceHandler.java         — Confluence tool implementations
│   └── BitbucketHandler.java          — Bitbucket tool implementations
└── formatter/                         — Response formatting
    ├── IssueFormatter.java            — Jira issue → readable text
    ├── PageFormatter.java             — Confluence page → readable text
    └── PullRequestFormatter.java      — Bitbucket PR → readable text
```

---

## Authentication

The server supports two authentication methods:

1. **API Token** (Atlassian Cloud) — email + API token from [id.atlassian.com](https://id.atlassian.com/manage-profile/security/api-tokens)
2. **Personal Access Token** (Bitbucket / Data Center) — PAT from your Atlassian product settings

Configure credentials via (highest precedence wins):

1. **Environment variables:** `ATLASSIAN_TOKEN`, `ATLASSIAN_EMAIL`, `ATLASSIAN_JIRA_URL`, etc.
2. **Local config:** `user-config/servers/atlassian/atlassian-config.local.properties` (gitignored — copy from `.example.properties`)
3. **Base config:** `user-config/servers/atlassian/atlassian-config.properties` (committed defaults, no secrets)

---

## Configuration

Copy the example file and fill in your credentials:

```bash
cd mcp-servers/user-config/servers/atlassian
cp atlassian-config.local.example.properties atlassian-config.local.properties
# Edit atlassian-config.local.properties with your URLs and API token
```

Key settings in `atlassian-config.local.properties`:

```properties
# Instance identity
atlassian.instance.name=work-cloud
atlassian.variant=cloud               # cloud | data_center | server | custom

# Per-product base URLs
atlassian.jira.baseUrl=https://your-domain.atlassian.net
atlassian.confluence.baseUrl=https://your-domain.atlassian.net/wiki
atlassian.bitbucket.baseUrl=https://api.bitbucket.org

# Authentication (Cloud: api_token; Data Center: pat)
atlassian.auth.type=api_token
atlassian.auth.email=your.email@example.com
atlassian.auth.token=YOUR_API_TOKEN_HERE

# Enable/disable products
atlassian.product.jira.enabled=true
atlassian.product.confluence.enabled=true
atlassian.product.bitbucket.enabled=false
```

Then enable the server in `user-config/mcp-config.properties`:

```properties
server.atlassian.enabled=true
```

---

## Iterative Build Plan

This server is being built iteratively:

- [x] **Phase 1** — Project scaffolding, model enums, package structure
- [ ] **Phase 2** — Jira client + handler (search, get, create, update, transition)
- [ ] **Phase 3** — Confluence client + handler (search, get, create, update)
- [ ] **Phase 4** — Bitbucket client + handler (repos, PRs, code search)
- [ ] **Phase 5** — Cross-product linking and unified search
- [ ] **Phase 6** — Response formatting and export
- [ ] **Phase 7** — Integration tests and error handling hardening

---

## API References

- [Jira REST API v3](https://developer.atlassian.com/cloud/jira/platform/rest/v3/)
- [Confluence REST API v2](https://developer.atlassian.com/cloud/confluence/rest/v2/)
- [Bitbucket REST API 2.0](https://developer.atlassian.com/cloud/bitbucket/rest/intro/)
