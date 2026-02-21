package server.atlassian.handler;

import server.atlassian.client.BitbucketClient;
import server.atlassian.model.AtlassianProduct;
import server.atlassian.model.ToolResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles Bitbucket-related MCP tool calls.
 *
 * <p>Routes Bitbucket tool invocations to the appropriate
 * {@link BitbucketClient} method and formats the results.
 *
 * <p><strong>Registered Tools:</strong>
 * <ul>
 *   <li>{@code bitbucket_list_repos} — list repositories in a workspace</li>
 *   <li>{@code bitbucket_get_repo} — get repository details</li>
 *   <li>{@code bitbucket_list_pull_requests} — list PRs for a repository</li>
 *   <li>{@code bitbucket_get_pull_request} — get full PR details</li>
 *   <li>{@code bitbucket_search_code} — search code across repositories</li>
 * </ul>
 */
public class BitbucketHandler {

    private static final Logger LOGGER = Logger.getLogger(BitbucketHandler.class.getName());
    private static final int DEFAULT_MAX_RESULTS = 25;

    private final BitbucketClient bitbucketClient;

    /**
     * Creates a Bitbucket handler with the given client.
     *
     * @param bitbucketClient the Bitbucket REST API client
     */
    public BitbucketHandler(final BitbucketClient bitbucketClient) {
        this.bitbucketClient = Objects.requireNonNull(
                bitbucketClient, "BitbucketClient must not be null");
    }

    /**
     * Lists repositories in a Bitbucket workspace.
     *
     * @param arguments the tool arguments ({@code workspace}, optional {@code maxResults})
     * @return the tool response with repository list
     */
    public ToolResponse listRepositories(final Map<String, String> arguments) {
        final var workspace = arguments.get("workspace");
        if (workspace == null || workspace.isBlank()) {
            return ToolResponse.error(AtlassianProduct.BITBUCKET, "bitbucket_list_repos",
                    "Missing required argument: 'workspace'");
        }

        final int maxResults = parseMaxResults(arguments.get("maxResults"));

        try {
            final var response = bitbucketClient.listRepositories(workspace, maxResults);
            return ToolResponse.success(AtlassianProduct.BITBUCKET,
                    "bitbucket_list_repos", response);
        } catch (IOException | InterruptedException exception) {
            LOGGER.log(Level.WARNING, "Failed to list repos in " + workspace, exception);
            return ToolResponse.error(AtlassianProduct.BITBUCKET, "bitbucket_list_repos",
                    "Failed to list repositories: " + exception.getMessage());
        }
    }

    /**
     * Gets details of a specific repository.
     *
     * @param arguments the tool arguments ({@code workspace}, {@code repoSlug})
     * @return the tool response with repository details
     */
    public ToolResponse getRepository(final Map<String, String> arguments) {
        final var workspace = arguments.get("workspace");
        final var repoSlug = arguments.get("repoSlug");

        if (workspace == null || workspace.isBlank()) {
            return ToolResponse.error(AtlassianProduct.BITBUCKET, "bitbucket_get_repo",
                    "Missing required argument: 'workspace'");
        }
        if (repoSlug == null || repoSlug.isBlank()) {
            return ToolResponse.error(AtlassianProduct.BITBUCKET, "bitbucket_get_repo",
                    "Missing required argument: 'repoSlug'");
        }

        try {
            final var response = bitbucketClient.getRepository(workspace, repoSlug);
            return ToolResponse.success(AtlassianProduct.BITBUCKET,
                    "bitbucket_get_repo", response);
        } catch (IOException | InterruptedException exception) {
            LOGGER.log(Level.WARNING,
                    "Failed to get repo " + workspace + "/" + repoSlug, exception);
            return ToolResponse.error(AtlassianProduct.BITBUCKET, "bitbucket_get_repo",
                    "Failed to get repository: " + exception.getMessage());
        }
    }

    /**
     * Lists pull requests for a repository.
     *
     * @param arguments the tool arguments ({@code workspace}, {@code repoSlug}, optional {@code state}, {@code maxResults})
     * @return the tool response with PR list
     */
    public ToolResponse listPullRequests(final Map<String, String> arguments) {
        final var workspace = arguments.get("workspace");
        final var repoSlug = arguments.get("repoSlug");

        if (workspace == null || workspace.isBlank()) {
            return ToolResponse.error(AtlassianProduct.BITBUCKET,
                    "bitbucket_list_pull_requests",
                    "Missing required argument: 'workspace'");
        }
        if (repoSlug == null || repoSlug.isBlank()) {
            return ToolResponse.error(AtlassianProduct.BITBUCKET,
                    "bitbucket_list_pull_requests",
                    "Missing required argument: 'repoSlug'");
        }

        final var state = arguments.getOrDefault("state", "OPEN");
        final int maxResults = parseMaxResults(arguments.get("maxResults"));

        try {
            final var response = bitbucketClient.listPullRequests(
                    workspace, repoSlug, state, maxResults);
            return ToolResponse.success(AtlassianProduct.BITBUCKET,
                    "bitbucket_list_pull_requests", response);
        } catch (IOException | InterruptedException exception) {
            LOGGER.log(Level.WARNING,
                    "Failed to list PRs for " + workspace + "/" + repoSlug, exception);
            return ToolResponse.error(AtlassianProduct.BITBUCKET,
                    "bitbucket_list_pull_requests",
                    "Failed to list pull requests: " + exception.getMessage());
        }
    }

    /**
     * Gets full details of a specific pull request.
     *
     * @param arguments the tool arguments ({@code workspace}, {@code repoSlug}, {@code prId})
     * @return the tool response with PR details
     */
    public ToolResponse getPullRequest(final Map<String, String> arguments) {
        final var workspace = arguments.get("workspace");
        final var repoSlug = arguments.get("repoSlug");
        final var prIdStr = arguments.get("prId");

        if (workspace == null || workspace.isBlank()) {
            return ToolResponse.error(AtlassianProduct.BITBUCKET,
                    "bitbucket_get_pull_request",
                    "Missing required argument: 'workspace'");
        }
        if (repoSlug == null || repoSlug.isBlank()) {
            return ToolResponse.error(AtlassianProduct.BITBUCKET,
                    "bitbucket_get_pull_request",
                    "Missing required argument: 'repoSlug'");
        }
        if (prIdStr == null || prIdStr.isBlank()) {
            return ToolResponse.error(AtlassianProduct.BITBUCKET,
                    "bitbucket_get_pull_request",
                    "Missing required argument: 'prId'");
        }

        try {
            final int prId = Integer.parseInt(prIdStr);
            final var response = bitbucketClient.getPullRequest(workspace, repoSlug, prId);
            return ToolResponse.success(AtlassianProduct.BITBUCKET,
                    "bitbucket_get_pull_request", response);
        } catch (NumberFormatException numberException) {
            return ToolResponse.error(AtlassianProduct.BITBUCKET,
                    "bitbucket_get_pull_request",
                    "Invalid prId — must be a number: " + prIdStr);
        } catch (IOException | InterruptedException exception) {
            LOGGER.log(Level.WARNING,
                    "Failed to get PR #" + prIdStr + " for " + workspace + "/" + repoSlug,
                    exception);
            return ToolResponse.error(AtlassianProduct.BITBUCKET,
                    "bitbucket_get_pull_request",
                    "Failed to get pull request: " + exception.getMessage());
        }
    }

    /**
     * Searches code across repositories in a workspace.
     *
     * @param arguments the tool arguments ({@code workspace}, {@code query})
     * @return the tool response with code search results
     */
    public ToolResponse searchCode(final Map<String, String> arguments) {
        final var workspace = arguments.get("workspace");
        final var query = arguments.get("query");

        if (workspace == null || workspace.isBlank()) {
            return ToolResponse.error(AtlassianProduct.BITBUCKET,
                    "bitbucket_search_code",
                    "Missing required argument: 'workspace'");
        }
        if (query == null || query.isBlank()) {
            return ToolResponse.error(AtlassianProduct.BITBUCKET,
                    "bitbucket_search_code",
                    "Missing required argument: 'query'");
        }

        try {
            final var response = bitbucketClient.searchCode(workspace, query);
            return ToolResponse.success(AtlassianProduct.BITBUCKET,
                    "bitbucket_search_code", response);
        } catch (IOException | InterruptedException exception) {
            LOGGER.log(Level.WARNING,
                    "Code search failed in " + workspace, exception);
            return ToolResponse.error(AtlassianProduct.BITBUCKET,
                    "bitbucket_search_code",
                    "Code search failed: " + exception.getMessage());
        }
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
