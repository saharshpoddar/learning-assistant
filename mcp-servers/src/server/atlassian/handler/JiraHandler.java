package server.atlassian.handler;

import server.atlassian.client.JiraClient;
import server.atlassian.model.AtlassianProduct;
import server.atlassian.model.ToolResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles Jira-related MCP tool calls.
 *
 * <p>Routes Jira tool invocations to the appropriate {@link JiraClient}
 * method and formats the results for MCP response.
 *
 * <p><strong>Registered Tools:</strong>
 * <ul>
 *   <li>{@code jira_search_issues} — search issues by JQL or free text</li>
 *   <li>{@code jira_get_issue} — get full details of a specific issue</li>
 *   <li>{@code jira_create_issue} — create a new issue</li>
 *   <li>{@code jira_update_issue} — update fields on an existing issue</li>
 *   <li>{@code jira_transition_issue} — move an issue to a new status</li>
 *   <li>{@code jira_list_projects} — list accessible projects</li>
 *   <li>{@code jira_get_sprint} — get active sprint for a board</li>
 * </ul>
 */
public class JiraHandler {

    private static final Logger LOGGER = Logger.getLogger(JiraHandler.class.getName());
    private static final int DEFAULT_MAX_RESULTS = 25;

    private final JiraClient jiraClient;

    /**
     * Creates a Jira handler with the given client.
     *
     * @param jiraClient the Jira REST API client
     */
    public JiraHandler(final JiraClient jiraClient) {
        this.jiraClient = Objects.requireNonNull(jiraClient, "JiraClient must not be null");
    }

    /**
     * Searches for Jira issues using JQL or free text.
     *
     * @param arguments the tool arguments ({@code query}, optional {@code maxResults})
     * @return the tool response with search results
     */
    public ToolResponse searchIssues(final Map<String, String> arguments) {
        final var query = arguments.get("query");
        if (query == null || query.isBlank()) {
            return ToolResponse.error(AtlassianProduct.JIRA, "jira_search_issues",
                    "Missing required argument: 'query'");
        }

        final int maxResults = parseMaxResults(arguments.get("maxResults"));

        // Auto-detect: if it looks like raw JQL, use as-is; otherwise wrap in text search
        final var jql = looksLikeJql(query)
                ? query
                : "text ~ \"" + escapeJql(query) + "\" ORDER BY updated DESC";

        try {
            final var response = jiraClient.searchIssues(jql, maxResults);
            return ToolResponse.success(AtlassianProduct.JIRA, "jira_search_issues", response);
        } catch (IOException | InterruptedException exception) {
            LOGGER.log(Level.WARNING, "Jira search failed", exception);
            return ToolResponse.error(AtlassianProduct.JIRA, "jira_search_issues",
                    "Search failed: " + exception.getMessage());
        }
    }

    /**
     * Gets full details of a specific Jira issue.
     *
     * @param arguments the tool arguments ({@code issueKey})
     * @return the tool response with issue details
     */
    public ToolResponse getIssue(final Map<String, String> arguments) {
        final var issueKey = arguments.get("issueKey");
        if (issueKey == null || issueKey.isBlank()) {
            return ToolResponse.error(AtlassianProduct.JIRA, "jira_get_issue",
                    "Missing required argument: 'issueKey'");
        }

        try {
            final var response = jiraClient.getIssue(issueKey);
            return ToolResponse.success(AtlassianProduct.JIRA, "jira_get_issue", response);
        } catch (IOException | InterruptedException exception) {
            LOGGER.log(Level.WARNING, "Failed to get issue: " + issueKey, exception);
            return ToolResponse.error(AtlassianProduct.JIRA, "jira_get_issue",
                    "Failed to get issue " + issueKey + ": " + exception.getMessage());
        }
    }

    /**
     * Creates a new Jira issue.
     *
     * @param arguments the tool arguments ({@code projectKey}, {@code summary}, {@code issueType}, optional fields)
     * @return the tool response with the created issue
     */
    public ToolResponse createIssue(final Map<String, String> arguments) {
        final var projectKey = arguments.get("projectKey");
        final var summary = arguments.get("summary");
        final var issueType = arguments.getOrDefault("issueType", "Task");

        if (projectKey == null || projectKey.isBlank()) {
            return ToolResponse.error(AtlassianProduct.JIRA, "jira_create_issue",
                    "Missing required argument: 'projectKey'");
        }
        if (summary == null || summary.isBlank()) {
            return ToolResponse.error(AtlassianProduct.JIRA, "jira_create_issue",
                    "Missing required argument: 'summary'");
        }

        final var description = arguments.getOrDefault("description", "");
        final var priority = arguments.getOrDefault("priority", "Medium");

        final var requestBody = buildCreateIssueJson(projectKey, summary, issueType,
                description, priority);

        try {
            final var response = jiraClient.createIssue(requestBody);
            return ToolResponse.success(AtlassianProduct.JIRA, "jira_create_issue", response);
        } catch (IOException | InterruptedException exception) {
            LOGGER.log(Level.WARNING, "Failed to create issue", exception);
            return ToolResponse.error(AtlassianProduct.JIRA, "jira_create_issue",
                    "Failed to create issue: " + exception.getMessage());
        }
    }

    /**
     * Updates fields on an existing Jira issue.
     *
     * @param arguments the tool arguments ({@code issueKey}, plus fields to update)
     * @return the tool response
     */
    public ToolResponse updateIssue(final Map<String, String> arguments) {
        final var issueKey = arguments.get("issueKey");
        if (issueKey == null || issueKey.isBlank()) {
            return ToolResponse.error(AtlassianProduct.JIRA, "jira_update_issue",
                    "Missing required argument: 'issueKey'");
        }

        final var requestBody = buildUpdateIssueJson(arguments);

        try {
            final var response = jiraClient.updateIssue(issueKey, requestBody);
            return ToolResponse.success(AtlassianProduct.JIRA, "jira_update_issue",
                    "Issue " + issueKey + " updated successfully. " + response);
        } catch (IOException | InterruptedException exception) {
            LOGGER.log(Level.WARNING, "Failed to update issue: " + issueKey, exception);
            return ToolResponse.error(AtlassianProduct.JIRA, "jira_update_issue",
                    "Failed to update " + issueKey + ": " + exception.getMessage());
        }
    }

    /**
     * Transitions a Jira issue to a new status.
     *
     * @param arguments the tool arguments ({@code issueKey}, {@code transition})
     * @return the tool response
     */
    public ToolResponse transitionIssue(final Map<String, String> arguments) {
        final var issueKey = arguments.get("issueKey");
        final var transition = arguments.get("transition");

        if (issueKey == null || issueKey.isBlank()) {
            return ToolResponse.error(AtlassianProduct.JIRA, "jira_transition_issue",
                    "Missing required argument: 'issueKey'");
        }
        if (transition == null || transition.isBlank()) {
            return ToolResponse.error(AtlassianProduct.JIRA, "jira_transition_issue",
                    "Missing required argument: 'transition'");
        }

        try {
            final var response = jiraClient.transitionIssue(issueKey, transition);
            return ToolResponse.success(AtlassianProduct.JIRA, "jira_transition_issue",
                    "Issue " + issueKey + " transitioned successfully. " + response);
        } catch (IOException | InterruptedException exception) {
            LOGGER.log(Level.WARNING, "Failed to transition issue: " + issueKey, exception);
            return ToolResponse.error(AtlassianProduct.JIRA, "jira_transition_issue",
                    "Failed to transition " + issueKey + ": " + exception.getMessage());
        }
    }

    /**
     * Lists all accessible Jira projects.
     *
     * @return the tool response with project list
     */
    public ToolResponse listProjects() {
        try {
            final var response = jiraClient.listProjects();
            return ToolResponse.success(AtlassianProduct.JIRA, "jira_list_projects", response);
        } catch (IOException | InterruptedException exception) {
            LOGGER.log(Level.WARNING, "Failed to list projects", exception);
            return ToolResponse.error(AtlassianProduct.JIRA, "jira_list_projects",
                    "Failed to list projects: " + exception.getMessage());
        }
    }

    /**
     * Gets the active sprint for a board.
     *
     * @param arguments the tool arguments ({@code boardId})
     * @return the tool response with sprint details
     */
    public ToolResponse getActiveSprint(final Map<String, String> arguments) {
        final var boardIdStr = arguments.get("boardId");
        if (boardIdStr == null || boardIdStr.isBlank()) {
            return ToolResponse.error(AtlassianProduct.JIRA, "jira_get_sprint",
                    "Missing required argument: 'boardId'");
        }

        try {
            final int boardId = Integer.parseInt(boardIdStr);
            final var response = jiraClient.getActiveSprint(boardId);
            return ToolResponse.success(AtlassianProduct.JIRA, "jira_get_sprint", response);
        } catch (NumberFormatException numberException) {
            return ToolResponse.error(AtlassianProduct.JIRA, "jira_get_sprint",
                    "Invalid boardId — must be a number: " + boardIdStr);
        } catch (IOException | InterruptedException exception) {
            LOGGER.log(Level.WARNING, "Failed to get sprint for board " + boardIdStr, exception);
            return ToolResponse.error(AtlassianProduct.JIRA, "jira_get_sprint",
                    "Failed to get sprint: " + exception.getMessage());
        }
    }

    /**
     * Heuristic: does the query look like raw JQL?
     *
     * @param query the user's query string
     * @return {@code true} if it appears to be JQL
     */
    private boolean looksLikeJql(final String query) {
        final var upper = query.toUpperCase();
        return upper.contains("=") || upper.contains("~")
                || upper.contains(" AND ") || upper.contains(" OR ")
                || upper.contains("ORDER BY") || upper.contains("PROJECT ")
                || upper.contains("STATUS ");
    }

    /**
     * Escapes special characters for JQL text search.
     *
     * @param text the raw text
     * @return the escaped text
     */
    private String escapeJql(final String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    /**
     * Builds the JSON body for creating a Jira issue.
     */
    private String buildCreateIssueJson(final String projectKey,
                                        final String summary,
                                        final String issueType,
                                        final String description,
                                        final String priority) {
        return """
                {
                  "fields": {
                    "project": { "key": "%s" },
                    "summary": "%s",
                    "issuetype": { "name": "%s" },
                    "description": {
                      "type": "doc",
                      "version": 1,
                      "content": [{ "type": "paragraph", "content": [{ "type": "text", "text": "%s" }] }]
                    },
                    "priority": { "name": "%s" }
                  }
                }
                """.formatted(projectKey, escapeJson(summary), issueType,
                escapeJson(description), priority);
    }

    /**
     * Builds the JSON body for updating a Jira issue.
     */
    private String buildUpdateIssueJson(final Map<String, String> arguments) {
        final var builder = new StringBuilder("{\"fields\":{");
        var first = true;

        if (arguments.containsKey("summary")) {
            builder.append("\"summary\":\"").append(escapeJson(arguments.get("summary"))).append("\"");
            first = false;
        }
        if (arguments.containsKey("priority")) {
            if (!first) builder.append(",");
            builder.append("\"priority\":{\"name\":\"")
                    .append(escapeJson(arguments.get("priority"))).append("\"}");
            first = false;
        }
        if (arguments.containsKey("assignee")) {
            if (!first) builder.append(",");
            builder.append("\"assignee\":{\"accountId\":\"")
                    .append(escapeJson(arguments.get("assignee"))).append("\"}");
        }

        builder.append("}}");
        return builder.toString();
    }

    /**
     * Escapes special characters for JSON string values.
     */
    private String escapeJson(final String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    /**
     * Parses maxResults from arguments with a safe default.
     */
    private int parseMaxResults(final String value) {
        if (value == null || value.isBlank()) {
            return DEFAULT_MAX_RESULTS;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
            return DEFAULT_MAX_RESULTS;
        }
    }
}
