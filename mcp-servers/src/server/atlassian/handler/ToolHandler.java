package server.atlassian.handler;

import server.atlassian.client.BitbucketClient;
import server.atlassian.client.ConfluenceClient;
import server.atlassian.client.JiraClient;
import server.atlassian.config.AtlassianServerConfig;
import server.atlassian.model.AtlassianProduct;
import server.atlassian.model.ConnectionConfig;
import server.atlassian.model.ToolResponse;

import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Central dispatcher that routes incoming MCP tool calls to the
 * appropriate product-specific handler (Jira, Confluence, or Bitbucket).
 *
 * <p>Acts as the single entry point for all MCP protocol tool invocations.
 * Tool names use a product prefix convention:
 * <ul>
 *   <li>{@code jira_*} → {@link JiraHandler}</li>
 *   <li>{@code confluence_*} → {@link ConfluenceHandler}</li>
 *   <li>{@code bitbucket_*} → {@link BitbucketHandler}</li>
 * </ul>
 *
 * <p><strong>Registered Tools (27 total):</strong>
 * <ul>
 *   <li>Jira (11): search_issues, get_issue, create_issue, update_issue, transition_issue,
 *       list_projects, get_sprint, add_comment, get_comments, assign_issue, get_sprint_issues</li>
 *   <li>Confluence (7): search, get_page, create_page, update_page, list_spaces,
 *       get_page_children, delete_page</li>
 *   <li>Bitbucket (8): list_repos, get_repo, list_pull_requests, get_pull_request, search_code,
 *       create_pull_request, list_branches, get_commits</li>
 *   <li>Cross-product (1): atlassian_unified_search</li>
 * </ul>
 */
public class ToolHandler {

    private static final Logger LOGGER = Logger.getLogger(ToolHandler.class.getName());

    private final JiraHandler jiraHandler;
    private final ConfluenceHandler confluenceHandler;
    private final BitbucketHandler bitbucketHandler;
    private final UnifiedSearchHandler unifiedSearchHandler;

    /**
     * Creates a tool handler wired to all three product handlers.
     *
     * @param jiraConfig       connection config for Jira
     * @param confluenceConfig connection config for Confluence
     * @param bitbucketConfig  connection config for Bitbucket
     */
    public ToolHandler(final ConnectionConfig jiraConfig,
                       final ConnectionConfig confluenceConfig,
                       final ConnectionConfig bitbucketConfig) {
        Objects.requireNonNull(jiraConfig, "Jira config must not be null");
        Objects.requireNonNull(confluenceConfig, "Confluence config must not be null");
        Objects.requireNonNull(bitbucketConfig, "Bitbucket config must not be null");

        final var jiraClient       = new JiraClient(jiraConfig);
        final var confluenceClient = new ConfluenceClient(confluenceConfig);
        final var bitbucketClient  = new BitbucketClient(bitbucketConfig);

        this.jiraHandler          = new JiraHandler(jiraClient);
        this.confluenceHandler    = new ConfluenceHandler(confluenceClient);
        this.bitbucketHandler     = new BitbucketHandler(bitbucketClient);
        this.unifiedSearchHandler = new UnifiedSearchHandler(jiraClient, confluenceClient, bitbucketClient);
    }

    /**
     * Creates a tool handler from a fully loaded {@link AtlassianServerConfig}.
     *
     * <p>Each enabled product gets its own {@link ConnectionConfig} built from
     * the per-product base URL, shared credentials, and the configured timeouts.
     * Disabled products receive a placeholder config — their tools will return
     * an appropriate error response if accidentally invoked.
     *
     * @param serverConfig the loaded Atlassian server configuration
     * @return a fully wired tool handler
     */
    public static ToolHandler fromServerConfig(final AtlassianServerConfig serverConfig) {
        Objects.requireNonNull(serverConfig, "Server config must not be null");

        final var credentials = serverConfig.credentials();
        final int readTimeoutMs = serverConfig.readTimeoutMs();

        final var jiraConfig = serverConfig.jiraEnabled()
                ? ConnectionConfig.withTimeoutMs(serverConfig.jiraBaseUrl(), credentials, readTimeoutMs)
                : ConnectionConfig.withDefaults("https://placeholder.atlassian.net", credentials);

        final var confluenceConfig = serverConfig.confluenceEnabled()
                ? ConnectionConfig.withTimeoutMs(serverConfig.confluenceBaseUrl(), credentials, readTimeoutMs)
                : ConnectionConfig.withDefaults("https://placeholder.atlassian.net/wiki", credentials);

        final var bitbucketConfig = serverConfig.bitbucketEnabled()
                ? ConnectionConfig.withTimeoutMs(serverConfig.bitbucketBaseUrl(), credentials, readTimeoutMs)
                : ConnectionConfig.withDefaults("https://api.bitbucket.org", credentials);

        return new ToolHandler(jiraConfig, confluenceConfig, bitbucketConfig);
    }

    /**
     * Dispatches an MCP tool call to the appropriate product handler.
     *
     * @param toolName  the name of the tool being invoked
     * @param arguments the tool arguments as a key-value map
     * @return the tool response
     */
    public ToolResponse handleToolCall(final String toolName,
                                       final Map<String, String> arguments) {
        Objects.requireNonNull(toolName, "Tool name must not be null");
        Objects.requireNonNull(arguments, "Arguments must not be null");

        LOGGER.info("Handling tool call: " + toolName);

        return switch (toolName) {
            // Jira tools
            case "jira_search_issues"   -> jiraHandler.searchIssues(arguments);
            case "jira_get_issue"       -> jiraHandler.getIssue(arguments);
            case "jira_create_issue"    -> jiraHandler.createIssue(arguments);
            case "jira_update_issue"    -> jiraHandler.updateIssue(arguments);
            case "jira_transition_issue" -> jiraHandler.transitionIssue(arguments);
            case "jira_list_projects"   -> jiraHandler.listProjects();
            case "jira_get_sprint"      -> jiraHandler.getActiveSprint(arguments);
            case "jira_add_comment"     -> jiraHandler.addComment(arguments);
            case "jira_get_comments"    -> jiraHandler.getComments(arguments);
            case "jira_assign_issue"    -> jiraHandler.assignIssue(arguments);
            case "jira_get_sprint_issues" -> jiraHandler.getSprintIssues(arguments);

            // Confluence tools
            case "confluence_search"      -> confluenceHandler.searchPages(arguments);
            case "confluence_get_page"    -> confluenceHandler.getPage(arguments);
            case "confluence_create_page" -> confluenceHandler.createPage(arguments);
            case "confluence_update_page" -> confluenceHandler.updatePage(arguments);
            case "confluence_list_spaces" -> confluenceHandler.listSpaces();
            case "confluence_get_page_children" -> confluenceHandler.getPageChildren(arguments);
            case "confluence_delete_page"       -> confluenceHandler.deletePage(arguments);

            // Bitbucket tools
            case "bitbucket_list_repos"         -> bitbucketHandler.listRepositories(arguments);
            case "bitbucket_get_repo"            -> bitbucketHandler.getRepository(arguments);
            case "bitbucket_list_pull_requests"  -> bitbucketHandler.listPullRequests(arguments);
            case "bitbucket_get_pull_request"    -> bitbucketHandler.getPullRequest(arguments);
            case "bitbucket_search_code"         -> bitbucketHandler.searchCode(arguments);
            case "bitbucket_create_pull_request" -> bitbucketHandler.createPullRequest(arguments);
            case "bitbucket_list_branches"       -> bitbucketHandler.listBranches(arguments);
            case "bitbucket_get_commits"         -> bitbucketHandler.getCommits(arguments);

            // Cross-product tools
            case "atlassian_unified_search" -> unifiedSearchHandler.search(arguments);

            default -> {
                LOGGER.warning("Unknown tool: " + toolName);
                yield ToolResponse.error(AtlassianProduct.JIRA, toolName,
                        "Unknown tool: '" + toolName + "'. Use --list-tools to see available tools.");
            }
        };
    }

    /**
     * Returns the list of all registered tool names.
     *
     * @return array of tool name strings
     */
    public String[] listToolNames() {
        return new String[]{
                // Jira (11)
                "jira_search_issues", "jira_get_issue", "jira_create_issue",
                "jira_update_issue", "jira_transition_issue", "jira_list_projects",
                "jira_get_sprint", "jira_add_comment", "jira_get_comments",
                "jira_assign_issue", "jira_get_sprint_issues",
                // Confluence (7)
                "confluence_search", "confluence_get_page", "confluence_create_page",
                "confluence_update_page", "confluence_list_spaces",
                "confluence_get_page_children", "confluence_delete_page",
                // Bitbucket (8)
                "bitbucket_list_repos", "bitbucket_get_repo",
                "bitbucket_list_pull_requests", "bitbucket_get_pull_request",
                "bitbucket_search_code", "bitbucket_create_pull_request",
                "bitbucket_list_branches", "bitbucket_get_commits",
                // Cross-product (1)
                "atlassian_unified_search"
        };
    }
}
