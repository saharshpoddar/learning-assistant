package server.atlassian.client;

import server.atlassian.model.ConnectionConfig;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * REST API client for Jira Cloud (API v3).
 *
 * <p>Provides methods for searching, reading, creating, updating,
 * and transitioning Jira issues. All methods return raw JSON strings
 * that are parsed by the handler layer.
 *
 * <p><strong>API Reference:</strong>
 * <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/">Jira REST API v3</a>
 *
 * @see AtlassianRestClient
 */
public class JiraClient {

    private static final Logger LOGGER = Logger.getLogger(JiraClient.class.getName());
    private static final String API_BASE = "/rest/api/3";

    private final AtlassianRestClient restClient;

    /**
     * Creates a Jira client with the given connection configuration.
     *
     * @param config the connection settings
     */
    public JiraClient(final ConnectionConfig config) {
        Objects.requireNonNull(config, "ConnectionConfig must not be null");
        this.restClient = new AtlassianRestClient(config);
    }

    /**
     * Searches for issues using JQL (Jira Query Language).
     *
     * @param jql       the JQL query string
     * @param maxResults maximum number of results to return
     * @return the raw JSON response
     * @throws IOException          if the API call fails
     * @throws InterruptedException if the call is interrupted
     */
    public String searchIssues(final String jql, final int maxResults)
            throws IOException, InterruptedException {
        Objects.requireNonNull(jql, "JQL query must not be null");
        final var encodedJql = java.net.URLEncoder.encode(jql, java.nio.charset.StandardCharsets.UTF_8);
        final var path = API_BASE + "/search?jql=" + encodedJql + "&maxResults=" + maxResults;
        LOGGER.info("Searching Jira issues: " + jql);
        return restClient.get(path);
    }

    /**
     * Gets a single issue by its key.
     *
     * @param issueKey the issue key (e.g., "PROJ-123")
     * @return the raw JSON response
     * @throws IOException          if the API call fails
     * @throws InterruptedException if the call is interrupted
     */
    public String getIssue(final String issueKey) throws IOException, InterruptedException {
        Objects.requireNonNull(issueKey, "Issue key must not be null");
        final var path = API_BASE + "/issue/" + issueKey;
        LOGGER.info("Getting Jira issue: " + issueKey);
        return restClient.get(path);
    }

    /**
     * Creates a new issue.
     *
     * @param requestBody the JSON request body containing issue fields
     * @return the raw JSON response with the created issue
     * @throws IOException          if the API call fails
     * @throws InterruptedException if the call is interrupted
     */
    public String createIssue(final String requestBody)
            throws IOException, InterruptedException {
        Objects.requireNonNull(requestBody, "Request body must not be null");
        final var path = API_BASE + "/issue";
        LOGGER.info("Creating Jira issue");
        return restClient.post(path, requestBody);
    }

    /**
     * Updates an existing issue's fields.
     *
     * @param issueKey    the issue key
     * @param requestBody the JSON body with fields to update
     * @return the raw JSON response
     * @throws IOException          if the API call fails
     * @throws InterruptedException if the call is interrupted
     */
    public String updateIssue(final String issueKey, final String requestBody)
            throws IOException, InterruptedException {
        Objects.requireNonNull(issueKey, "Issue key must not be null");
        Objects.requireNonNull(requestBody, "Request body must not be null");
        final var path = API_BASE + "/issue/" + issueKey;
        LOGGER.info("Updating Jira issue: " + issueKey);
        return restClient.put(path, requestBody);
    }

    /**
     * Transitions an issue to a new status.
     *
     * @param issueKey     the issue key
     * @param transitionId the target transition ID
     * @return the raw JSON response
     * @throws IOException          if the API call fails
     * @throws InterruptedException if the call is interrupted
     */
    public String transitionIssue(final String issueKey, final String transitionId)
            throws IOException, InterruptedException {
        Objects.requireNonNull(issueKey, "Issue key must not be null");
        Objects.requireNonNull(transitionId, "Transition ID must not be null");
        final var path = API_BASE + "/issue/" + issueKey + "/transitions";
        final var body = "{\"transition\":{\"id\":\"" + transitionId + "\"}}";
        LOGGER.info("Transitioning issue " + issueKey + " with transition " + transitionId);
        return restClient.post(path, body);
    }

    /**
     * Lists available transitions for an issue.
     *
     * @param issueKey the issue key
     * @return the raw JSON response with available transitions
     * @throws IOException          if the API call fails
     * @throws InterruptedException if the call is interrupted
     */
    public String getTransitions(final String issueKey)
            throws IOException, InterruptedException {
        Objects.requireNonNull(issueKey, "Issue key must not be null");
        final var path = API_BASE + "/issue/" + issueKey + "/transitions";
        return restClient.get(path);
    }

    /**
     * Lists all accessible projects.
     *
     * @return the raw JSON response with project list
     * @throws IOException          if the API call fails
     * @throws InterruptedException if the call is interrupted
     */
    public String listProjects() throws IOException, InterruptedException {
        final var path = API_BASE + "/project";
        LOGGER.info("Listing Jira projects");
        return restClient.get(path);
    }

    /**
     * Gets the active sprint for a board.
     *
     * @param boardId the agile board ID
     * @return the raw JSON response with sprint details
     * @throws IOException          if the API call fails
     * @throws InterruptedException if the call is interrupted
     */
    public String getActiveSprint(final int boardId)
            throws IOException, InterruptedException {
        // Agile API uses a different base path
        final var path = "/rest/agile/1.0/board/" + boardId + "/sprint?state=active";
        LOGGER.info("Getting active sprint for board " + boardId);
        return restClient.get(path);
    }
}
