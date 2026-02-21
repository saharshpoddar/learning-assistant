/**
 * Atlassian MCP Server — unified gateway to Jira, Confluence, and Bitbucket
 * through the Model Context Protocol.
 *
 * <p>Package structure:
 * <pre>
 *   server.atlassian
 *   ├── {@link server.atlassian.AtlassianServer}                          — STDIO server entry point
 *   ├── model/                                                             — Shared and product-specific models
 *   │   ├── {@link server.atlassian.model.AtlassianProduct}               — Product enum (JIRA, CONFLUENCE, BITBUCKET)
 *   │   ├── {@link server.atlassian.model.AtlassianCredentials}           — Auth credentials record
 *   │   ├── {@link server.atlassian.model.ConnectionConfig}               — Base URL, auth, timeout settings
 *   │   ├── {@link server.atlassian.model.ToolResponse}                   — Standardized tool response wrapper
 *   │   ├── jira/                                                          — Jira-specific models
 *   │   │   ├── {@link server.atlassian.model.jira.JiraIssue}             — Jira issue record
 *   │   │   ├── {@link server.atlassian.model.jira.JiraProject}           — Jira project record
 *   │   │   ├── {@link server.atlassian.model.jira.IssueType}             — Issue type enum
 *   │   │   ├── {@link server.atlassian.model.jira.IssuePriority}         — Priority enum
 *   │   │   └── {@link server.atlassian.model.jira.IssueStatusCategory}   — Status category enum
 *   │   ├── confluence/                                                    — Confluence-specific models
 *   │   │   ├── {@link server.atlassian.model.confluence.ConfluencePage}   — Page record
 *   │   │   └── {@link server.atlassian.model.confluence.ConfluenceSpace}  — Space record
 *   │   └── bitbucket/                                                     — Bitbucket-specific models
 *   │       ├── {@link server.atlassian.model.bitbucket.BitbucketRepository}    — Repository record
 *   │       ├── {@link server.atlassian.model.bitbucket.BitbucketPullRequest}   — Pull request record
 *   │       └── {@link server.atlassian.model.bitbucket.PullRequestState}       — PR state enum
 *   ├── client/                                                            — REST API clients
 *   │   ├── {@link server.atlassian.client.AtlassianRestClient}           — Shared HTTP client
 *   │   ├── {@link server.atlassian.client.JiraClient}                    — Jira REST API v3
 *   │   ├── {@link server.atlassian.client.ConfluenceClient}              — Confluence REST API v2
 *   │   └── {@link server.atlassian.client.BitbucketClient}               — Bitbucket REST API 2.0
 *   ├── handler/                                                           — MCP tool dispatch
 *   │   ├── {@link server.atlassian.handler.ToolHandler}                  — Central tool router
 *   │   ├── {@link server.atlassian.handler.JiraHandler}                  — Jira tool implementations
 *   │   ├── {@link server.atlassian.handler.ConfluenceHandler}            — Confluence tool implementations
 *   │   └── {@link server.atlassian.handler.BitbucketHandler}             — Bitbucket tool implementations
 *   └── formatter/                                                         — Response formatting
 *       ├── {@link server.atlassian.formatter.IssueFormatter}             — Jira issue → readable text
 *       ├── {@link server.atlassian.formatter.PageFormatter}              — Confluence page → text
 *       └── {@link server.atlassian.formatter.PullRequestFormatter}       — Bitbucket PR → text
 * </pre>
 *
 * <p>This server exposes 17 MCP tools across three Atlassian products,
 * connected via a shared REST client with Basic or Bearer auth.
 *
 * @see server.atlassian.AtlassianServer
 */
package server.atlassian;
