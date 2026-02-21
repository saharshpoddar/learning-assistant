package server.atlassian.handler;

import server.atlassian.client.ConfluenceClient;
import server.atlassian.model.AtlassianProduct;
import server.atlassian.model.ToolResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles Confluence-related MCP tool calls.
 *
 * <p>Routes Confluence tool invocations to the appropriate
 * {@link ConfluenceClient} method and formats the results.
 *
 * <p><strong>Registered Tools:</strong>
 * <ul>
 *   <li>{@code confluence_search} — search pages using CQL or free text</li>
 *   <li>{@code confluence_get_page} — get full content of a page</li>
 *   <li>{@code confluence_create_page} — create a new page in a space</li>
 *   <li>{@code confluence_update_page} — update an existing page</li>
 *   <li>{@code confluence_list_spaces} — list accessible spaces</li>
 * </ul>
 */
public class ConfluenceHandler {

    private static final Logger LOGGER = Logger.getLogger(ConfluenceHandler.class.getName());
    private static final int DEFAULT_MAX_RESULTS = 25;

    private final ConfluenceClient confluenceClient;

    /**
     * Creates a Confluence handler with the given client.
     *
     * @param confluenceClient the Confluence REST API client
     */
    public ConfluenceHandler(final ConfluenceClient confluenceClient) {
        this.confluenceClient = Objects.requireNonNull(
                confluenceClient, "ConfluenceClient must not be null");
    }

    /**
     * Searches for Confluence pages using CQL or free text.
     *
     * @param arguments the tool arguments ({@code query}, optional {@code maxResults})
     * @return the tool response with search results
     */
    public ToolResponse searchPages(final Map<String, String> arguments) {
        final var query = arguments.get("query");
        if (query == null || query.isBlank()) {
            return ToolResponse.error(AtlassianProduct.CONFLUENCE, "confluence_search",
                    "Missing required argument: 'query'");
        }

        final int maxResults = parseMaxResults(arguments.get("maxResults"));

        // Auto-detect: if it looks like CQL, use as-is; otherwise wrap in text search
        final var cql = looksLikeCql(query)
                ? query
                : "type = page AND text ~ \"" + escapeCql(query) + "\" ORDER BY lastModified DESC";

        try {
            final var response = confluenceClient.searchPages(cql, maxResults);
            return ToolResponse.success(AtlassianProduct.CONFLUENCE, "confluence_search", response);
        } catch (IOException | InterruptedException exception) {
            LOGGER.log(Level.WARNING, "Confluence search failed", exception);
            return ToolResponse.error(AtlassianProduct.CONFLUENCE, "confluence_search",
                    "Search failed: " + exception.getMessage());
        }
    }

    /**
     * Gets full content of a Confluence page.
     *
     * @param arguments the tool arguments ({@code pageId})
     * @return the tool response with page content
     */
    public ToolResponse getPage(final Map<String, String> arguments) {
        final var pageId = arguments.get("pageId");
        if (pageId == null || pageId.isBlank()) {
            return ToolResponse.error(AtlassianProduct.CONFLUENCE, "confluence_get_page",
                    "Missing required argument: 'pageId'");
        }

        try {
            final var response = confluenceClient.getPage(pageId);
            return ToolResponse.success(AtlassianProduct.CONFLUENCE, "confluence_get_page", response);
        } catch (IOException | InterruptedException exception) {
            LOGGER.log(Level.WARNING, "Failed to get page: " + pageId, exception);
            return ToolResponse.error(AtlassianProduct.CONFLUENCE, "confluence_get_page",
                    "Failed to get page " + pageId + ": " + exception.getMessage());
        }
    }

    /**
     * Creates a new Confluence page in a space.
     *
     * @param arguments the tool arguments ({@code spaceKey}, {@code title}, {@code body})
     * @return the tool response with the created page
     */
    public ToolResponse createPage(final Map<String, String> arguments) {
        final var spaceKey = arguments.get("spaceKey");
        final var title = arguments.get("title");
        final var body = arguments.get("body");

        if (spaceKey == null || spaceKey.isBlank()) {
            return ToolResponse.error(AtlassianProduct.CONFLUENCE, "confluence_create_page",
                    "Missing required argument: 'spaceKey'");
        }
        if (title == null || title.isBlank()) {
            return ToolResponse.error(AtlassianProduct.CONFLUENCE, "confluence_create_page",
                    "Missing required argument: 'title'");
        }
        if (body == null || body.isBlank()) {
            return ToolResponse.error(AtlassianProduct.CONFLUENCE, "confluence_create_page",
                    "Missing required argument: 'body'");
        }

        final var requestBody = buildCreatePageJson(spaceKey, title, body);

        try {
            final var response = confluenceClient.createPage(requestBody);
            return ToolResponse.success(AtlassianProduct.CONFLUENCE,
                    "confluence_create_page", response);
        } catch (IOException | InterruptedException exception) {
            LOGGER.log(Level.WARNING, "Failed to create page", exception);
            return ToolResponse.error(AtlassianProduct.CONFLUENCE, "confluence_create_page",
                    "Failed to create page: " + exception.getMessage());
        }
    }

    /**
     * Updates an existing Confluence page.
     *
     * @param arguments the tool arguments ({@code pageId}, {@code title}, {@code body}, {@code version})
     * @return the tool response
     */
    public ToolResponse updatePage(final Map<String, String> arguments) {
        final var pageId = arguments.get("pageId");
        final var title = arguments.get("title");
        final var body = arguments.get("body");
        final var versionStr = arguments.get("version");

        if (pageId == null || pageId.isBlank()) {
            return ToolResponse.error(AtlassianProduct.CONFLUENCE, "confluence_update_page",
                    "Missing required argument: 'pageId'");
        }
        if (title == null || title.isBlank()) {
            return ToolResponse.error(AtlassianProduct.CONFLUENCE, "confluence_update_page",
                    "Missing required argument: 'title'");
        }
        if (body == null || body.isBlank()) {
            return ToolResponse.error(AtlassianProduct.CONFLUENCE, "confluence_update_page",
                    "Missing required argument: 'body'");
        }

        final int version = parseVersion(versionStr);
        final var requestBody = buildUpdatePageJson(pageId, title, body, version);

        try {
            final var response = confluenceClient.updatePage(pageId, requestBody);
            return ToolResponse.success(AtlassianProduct.CONFLUENCE,
                    "confluence_update_page", response);
        } catch (IOException | InterruptedException exception) {
            LOGGER.log(Level.WARNING, "Failed to update page: " + pageId, exception);
            return ToolResponse.error(AtlassianProduct.CONFLUENCE, "confluence_update_page",
                    "Failed to update page " + pageId + ": " + exception.getMessage());
        }
    }

    /**
     * Lists all accessible Confluence spaces.
     *
     * @return the tool response with space list
     */
    public ToolResponse listSpaces() {
        try {
            final var response = confluenceClient.listSpaces();
            return ToolResponse.success(AtlassianProduct.CONFLUENCE,
                    "confluence_list_spaces", response);
        } catch (IOException | InterruptedException exception) {
            LOGGER.log(Level.WARNING, "Failed to list spaces", exception);
            return ToolResponse.error(AtlassianProduct.CONFLUENCE, "confluence_list_spaces",
                    "Failed to list spaces: " + exception.getMessage());
        }
    }

    /**
     * Heuristic: does the query look like raw CQL?
     */
    private boolean looksLikeCql(final String query) {
        final var upper = query.toUpperCase();
        return upper.contains("=") || upper.contains("~")
                || upper.contains(" AND ") || upper.contains(" OR ")
                || upper.contains("ORDER BY") || upper.contains("TYPE ")
                || upper.contains("SPACE ");
    }

    /**
     * Escapes special characters for CQL text search.
     */
    private String escapeCql(final String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
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
     * Builds the JSON body for creating a Confluence page (v2 API).
     */
    private String buildCreatePageJson(final String spaceKey,
                                       final String title,
                                       final String body) {
        return """
                {
                  "spaceId": "%s",
                  "status": "current",
                  "title": "%s",
                  "body": {
                    "representation": "storage",
                    "value": "%s"
                  }
                }
                """.formatted(escapeJson(spaceKey), escapeJson(title), escapeJson(body));
    }

    /**
     * Builds the JSON body for updating a Confluence page (v2 API).
     */
    private String buildUpdatePageJson(final String pageId,
                                       final String title,
                                       final String body,
                                       final int version) {
        return """
                {
                  "id": "%s",
                  "status": "current",
                  "title": "%s",
                  "body": {
                    "representation": "storage",
                    "value": "%s"
                  },
                  "version": {
                    "number": %d,
                    "message": "Updated via MCP"
                  }
                }
                """.formatted(escapeJson(pageId), escapeJson(title),
                escapeJson(body), version);
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

    /**
     * Parses version number with a safe default of 1.
     */
    private int parseVersion(final String value) {
        if (value == null || value.isBlank()) {
            return 1;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
            return 1;
        }
    }
}
