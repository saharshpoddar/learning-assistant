package server.atlassian;

import server.atlassian.config.AtlassianConfigLoader;
import server.atlassian.config.AtlassianServerConfig;
import server.atlassian.handler.ToolHandler;
import server.atlassian.model.ToolResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entry point for the Atlassian MCP Server.
 *
 * <p>This server provides a unified gateway to Atlassian's core developer
 * tools — Jira, Confluence, and Bitbucket — through the Model Context
 * Protocol, enabling AI assistants to manage issues, documentation, and
 * code collaboration.
 *
 * <p><strong>Capabilities (17 tools):</strong>
 * <ul>
 *   <li><strong>Jira (7):</strong> search issues (JQL/text), get issue, create,
 *       update, transition, list projects, get active sprint</li>
 *   <li><strong>Confluence (5):</strong> search pages (CQL/text), get page,
 *       create page, update page, list spaces</li>
 *   <li><strong>Bitbucket (5):</strong> list repos, get repo, list PRs,
 *       get PR details, search code</li>
 * </ul>
 *
 * <p><strong>Transport:</strong> STDIO (reads JSON-RPC from stdin, writes to stdout).
 *
 * <p><strong>Usage:</strong>
 * <pre>
 *   java -cp out server.atlassian.AtlassianServer
 *   java -cp out server.atlassian.AtlassianServer --list-tools
 *   java -cp out server.atlassian.AtlassianServer --demo
 * </pre>
 */
public class AtlassianServer {

    private static final Logger LOGGER = Logger.getLogger(AtlassianServer.class.getName());
    private static final String SERVER_NAME = "atlassian";
    private static final String SERVER_VERSION = "1.0.0";

    private final ToolHandler toolHandler;
    private volatile boolean isRunning;

    /**
     * Creates the server from a fully loaded {@link AtlassianServerConfig}.
     *
     * <p>Config is loaded by {@link AtlassianConfigLoader} from layered properties
     * files under {@code user-config/servers/atlassian/}. Environment variables
     * ({@code ATLASSIAN_*}) override file values.
     *
     * @param serverConfig the loaded Atlassian server configuration
     */
    public AtlassianServer(final AtlassianServerConfig serverConfig) {
        this.toolHandler = ToolHandler.fromServerConfig(serverConfig);
        this.isRunning = false;
    }

    /**
     * Starts the MCP server, listening on stdin for JSON-RPC messages.
     *
     * <p>This is a simplified STDIO transport implementation. In production,
     * this would use a full MCP SDK library for proper JSON-RPC framing,
     * message parsing, and protocol compliance.
     */
    public void start() {
        LOGGER.info("Starting " + SERVER_NAME + " v" + SERVER_VERSION
                + " — Jira + Confluence + Bitbucket MCP Server");
        isRunning = true;

        try (var reader = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            while (isRunning) {
                final var line = reader.readLine();
                if (line == null) {
                    LOGGER.info("EOF on stdin — shutting down.");
                    break;
                }

                if (line.isBlank()) {
                    continue;
                }

                processMessage(line.trim());
            }
        } catch (IOException ioException) {
            LOGGER.log(Level.SEVERE, "I/O error reading from stdin", ioException);
        }

        LOGGER.info(SERVER_NAME + " stopped.");
    }

    /**
     * Stops the server gracefully.
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * Returns the server's tool definitions for MCP capability negotiation.
     *
     * @return a map of tool name to description
     */
    public Map<String, String> getToolDefinitions() {
        return Map.ofEntries(
                // Jira tools
                Map.entry("jira_search_issues",
                        "Search Jira issues using JQL or free text"),
                Map.entry("jira_get_issue",
                        "Get full details of a specific Jira issue by key"),
                Map.entry("jira_create_issue",
                        "Create a new Jira issue (requires projectKey, summary, issueType)"),
                Map.entry("jira_update_issue",
                        "Update fields on an existing Jira issue"),
                Map.entry("jira_transition_issue",
                        "Move a Jira issue to a new status/transition"),
                Map.entry("jira_list_projects",
                        "List all accessible Jira projects"),
                Map.entry("jira_get_sprint",
                        "Get the active sprint for a Jira board"),

                // Confluence tools
                Map.entry("confluence_search",
                        "Search Confluence pages using CQL or free text"),
                Map.entry("confluence_get_page",
                        "Get full content of a Confluence page by ID"),
                Map.entry("confluence_create_page",
                        "Create a new Confluence page in a space"),
                Map.entry("confluence_update_page",
                        "Update an existing Confluence page"),
                Map.entry("confluence_list_spaces",
                        "List all accessible Confluence spaces"),

                // Bitbucket tools
                Map.entry("bitbucket_list_repos",
                        "List repositories in a Bitbucket workspace"),
                Map.entry("bitbucket_get_repo",
                        "Get details of a specific Bitbucket repository"),
                Map.entry("bitbucket_list_pull_requests",
                        "List pull requests for a Bitbucket repository"),
                Map.entry("bitbucket_get_pull_request",
                        "Get full details of a Bitbucket pull request"),
                Map.entry("bitbucket_search_code",
                        "Search code across Bitbucket repositories")
        );
    }

    /**
     * Processes a single incoming message line.
     *
     * <p>Simplified message handler — a production implementation would
     * parse JSON-RPC messages with full protocol compliance.
     *
     * @param message the raw message line
     */
    private void processMessage(final String message) {
        LOGGER.fine("Received: " + message);

        // Simplified tool invocation: "tool_name arg1=val1 arg2=val2"
        final var parts = message.split("\\s+", 2);
        final var toolName = parts[0];
        final var arguments = parseSimpleArguments(parts.length > 1 ? parts[1] : "");

        final var result = toolHandler.handleToolCall(toolName, arguments);
        System.out.println(result.text());
        System.out.flush();
    }

    /**
     * Parses simple key=value arguments from a string.
     *
     * @param argumentString space-separated key=value pairs
     * @return parsed argument map
     */
    private Map<String, String> parseSimpleArguments(final String argumentString) {
        final var arguments = new HashMap<String, String>();

        if (argumentString.isBlank()) {
            return arguments;
        }

        for (final var pair : argumentString.split("\\s+")) {
            final var equalsIndex = pair.indexOf('=');
            if (equalsIndex > 0) {
                final var key = pair.substring(0, equalsIndex);
                final var value = pair.substring(equalsIndex + 1);
                arguments.put(key, value);
            }
        }

        return arguments;
    }

    /**
     * CLI entry point for the Atlassian MCP Server.
     *
     * @param args command-line arguments: {@code --list-tools} to print tools,
     *             {@code --demo} to run a demo, or no args for STDIO server
     */
    public static void main(final String[] args) {
        final AtlassianServerConfig serverConfig = loadConfig();
        final var server = new AtlassianServer(serverConfig);

        if (args.length > 0 && "--list-tools".equals(args[0])) {
            printToolList(server);
            return;
        }

        if (args.length > 0 && "--demo".equals(args[0])) {
            runDemo(server);
            return;
        }

        server.start();
    }

    /**
     * Loads the Atlassian server configuration using the layered config loader.
     *
     * <p>Config files are read from {@code user-config/servers/atlassian/} relative
     * to the working directory. Environment variables ({@code ATLASSIAN_*}) override
     * file values. If loading fails, a safe placeholder config is used so the server
     * can start and print useful error messages via the demo/list-tools modes.
     *
     * @return the loaded server configuration
     */
    private static AtlassianServerConfig loadConfig() {
        try {
            // Allow override of the config directory for multi-instance setups.
            // Set ATLASSIAN_CONFIG_DIR to point to a different atlassian instance dir:
            //   user-config/servers/atlassian-dc/
            //   user-config/servers/atlassian-colleague/
            final var configDirEnv = System.getenv("ATLASSIAN_CONFIG_DIR");
            final var loader = (configDirEnv != null && !configDirEnv.isBlank())
                    ? AtlassianConfigLoader.withConfigDir(resolveConfigDir(configDirEnv))
                    : AtlassianConfigLoader.withDefaults();

            return loader.load();
        } catch (final IllegalStateException ex) {
            LOGGER.warning("Atlassian config incomplete: " + ex.getMessage()
                    + "\nStarting in unconfigured mode — tool calls will fail until credentials are set."
                    + "\nSee user-config/servers/atlassian/atlassian-config.local.example.properties");
            // Return a minimal placeholder so --list-tools and --demo can still run
            return AtlassianServerConfig.cloudDefaults(
                    "unconfigured",
                    "https://placeholder.atlassian.net",
                    new server.atlassian.model.AtlassianCredentials(
                            "", "placeholder-token",
                            server.atlassian.model.AuthType.PERSONAL_ACCESS_TOKEN));
        }
    }

    /**
     * Resolves a config directory path — absolute paths are used as-is;
     * relative paths are resolved against the process working directory.
     *
     * @param rawPath the path string from the env var (may be absolute or relative)
     * @return the resolved {@link java.nio.file.Path}
     */
    private static java.nio.file.Path resolveConfigDir(final String rawPath) {
        final var path = java.nio.file.Path.of(rawPath.strip());
        return path.isAbsolute()
                ? path
                : java.nio.file.Path.of(System.getProperty("user.dir")).resolve(path);
    }

    /**
     * Prints the list of available tools to stdout.
     *
     * @param server the server instance
     */
    private static void printToolList(final AtlassianServer server) {
        System.out.println("Atlassian MCP Server v" + SERVER_VERSION
                + " — Available Tools (" + server.getToolDefinitions().size() + ")\n");

        System.out.println("=== Jira ===");
        server.getToolDefinitions().entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("jira_"))
                .forEach(entry -> System.out.println(
                        "  " + entry.getKey() + "\n    " + entry.getValue() + "\n"));

        System.out.println("=== Confluence ===");
        server.getToolDefinitions().entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("confluence_"))
                .forEach(entry -> System.out.println(
                        "  " + entry.getKey() + "\n    " + entry.getValue() + "\n"));

        System.out.println("=== Bitbucket ===");
        server.getToolDefinitions().entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("bitbucket_"))
                .forEach(entry -> System.out.println(
                        "  " + entry.getKey() + "\n    " + entry.getValue() + "\n"));
    }

    /**
     * Runs a demo showing the server's capabilities.
     *
     * @param server the server instance
     */
    private static void runDemo(final AtlassianServer server) {
        System.out.println("=== Atlassian MCP Server Demo ===\n");
        System.out.println("Server: " + SERVER_NAME + " v" + SERVER_VERSION);
        System.out.println("Tools:  " + server.getToolDefinitions().size() + " registered\n");

        System.out.println("--- Listing Jira Projects (requires valid credentials) ---");
        final var result = server.toolHandler.handleToolCall(
                "jira_list_projects", Map.of());
        System.out.println(result.text());

        System.out.println("\n=== Demo complete ===");
        System.out.println("Configure credentials in "
                + "user-config/servers/atlassian/atlassian-config.local.properties "
                + "(copy from atlassian-config.local.example.properties) "
                + "or via ATLASSIAN_* environment variables.");
    }
}
