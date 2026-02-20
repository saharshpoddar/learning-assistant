package server.learningresources.model;

/**
 * Fine-grained concept areas for learning resources.
 *
 * <p>While {@link ResourceCategory} groups by broad technology domain (Java, Python,
 * DevOps), {@code ConceptArea} classifies by the specific CS/SE concept being taught.
 * A resource can belong to multiple concept areas — for example, a tutorial on
 * "Java virtual threads" would have concepts {@code CONCURRENCY} and {@code LANGUAGE_FEATURES}.
 *
 * <p>This enables concept-based discovery: a user asking "I want to learn about
 * concurrency" gets resources from Java, Python, and general CS — not just one language.
 */
public enum ConceptArea {

    // ─── Programming Fundamentals ───────────────────────────────────

    /** Language syntax, semantics, core features (loops, types, operators). */
    LANGUAGE_BASICS("language-basics"),

    /** Object-oriented concepts: classes, inheritance, polymorphism, encapsulation. */
    OOP("oop"),

    /** Functional programming: lambdas, higher-order functions, immutability. */
    FUNCTIONAL_PROGRAMMING("functional-programming"),

    /** New language features, version upgrades, migration guides. */
    LANGUAGE_FEATURES("language-features"),

    /** Generic types, type parameters, variance, wildcards, type erasure. */
    GENERICS("generics"),

    // ─── Core CS Concepts ───────────────────────────────────────────

    /** Threads, executors, locks, synchronization, async patterns (virtual threads, coroutines). */
    CONCURRENCY("concurrency"),

    /** Data structures: arrays, lists, trees, graphs, heaps, tries, hash tables. */
    DATA_STRUCTURES("data-structures"),

    /** Algorithms: sorting, searching, dynamic programming, greedy, backtracking. */
    ALGORITHMS("algorithms"),

    /** Big-O analysis, time/space complexity, benchmarking, optimization. */
    COMPLEXITY_ANALYSIS("complexity-analysis"),

    /** Memory management, garbage collection, allocation strategies. */
    MEMORY_MANAGEMENT("memory-management"),

    // ─── Software Engineering ───────────────────────────────────────

    /** Design patterns: creational, structural, behavioral (GoF, enterprise). */
    DESIGN_PATTERNS("design-patterns"),

    /** SOLID, GRASP, DRY, KISS, clean code, code smells, refactoring. */
    CLEAN_CODE("clean-code"),

    /** Unit testing, integration testing, TDD, BDD, mocking, test pyramid. */
    TESTING("testing"),

    /** REST, GraphQL, gRPC, WebSocket, API versioning, OpenAPI. */
    API_DESIGN("api-design"),

    /** Application architecture, layers, hexagonal, microservices, monolith. */
    ARCHITECTURE("architecture"),

    // ─── System Design & Infrastructure ─────────────────────────────

    /** High-level system design: scalability, load balancing, caching, CDN, sharding. */
    SYSTEM_DESIGN("system-design"),

    /** Databases: SQL, NoSQL, indexing, query optimization, replication, schemas. */
    DATABASES("databases"),

    /** Distributed systems: consensus, CAP, eventual consistency, leader election. */
    DISTRIBUTED_SYSTEMS("distributed-systems"),

    /** Networking: TCP/IP, HTTP, DNS, TLS, sockets, protocols. */
    NETWORKING("networking"),

    /** Operating systems: processes, scheduling, file systems, I/O, kernels. */
    OPERATING_SYSTEMS("operating-systems"),

    // ─── DevOps & Tooling ───────────────────────────────────────────

    /** Continuous integration and continuous delivery/deployment pipelines. */
    CI_CD("ci-cd"),

    /** Containers: Docker, Kubernetes, orchestration, images, registries. */
    CONTAINERS("containers"),

    /** Git, version control, branching strategies, merge procedures. */
    VERSION_CONTROL("version-control"),

    /** Build systems: Maven, Gradle, Ant, Bazel, Make, npm scripts. */
    BUILD_TOOLS("build-tools"),

    /** Infrastructure as Code: Terraform, Ansible, CloudFormation, Pulumi. */
    INFRASTRUCTURE("infrastructure"),

    /** Monitoring, observability, logging, tracing, alerting, SLI/SLO. */
    OBSERVABILITY("observability"),

    // ─── Security ───────────────────────────────────────────────────

    /** Web security: XSS, CSRF, SQL injection, OWASP, authentication, OAuth. */
    WEB_SECURITY("web-security"),

    /** Cryptography, encryption, hashing, digital signatures, TLS internals. */
    CRYPTOGRAPHY("cryptography"),

    // ─── AI & Data ──────────────────────────────────────────────────

    /** Machine learning: neural networks, training, inference, models. */
    MACHINE_LEARNING("machine-learning"),

    /** LLMs, prompt engineering, RAG, fine-tuning, AI agents. */
    LLM_AND_PROMPTING("llm-and-prompting"),

    // ─── Career & Meta ──────────────────────────────────────────────

    /** Interview preparation: coding challenges, system design interviews. */
    INTERVIEW_PREP("interview-prep"),

    /** Career growth, learning paths, roadmaps, skill mapping. */
    CAREER_DEVELOPMENT("career-development"),

    /** Getting started: environment setup, first project, "hello world". */
    GETTING_STARTED("getting-started");

    private final String displayName;

    ConceptArea(final String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the human-readable display name.
     *
     * @return the display name (e.g., "concurrency", "design-patterns")
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Resolves a {@link ConceptArea} from a string (case-insensitive, supports hyphens and spaces).
     *
     * @param value the string to parse
     * @return the matching concept area
     * @throws IllegalArgumentException if no match is found
     */
    public static ConceptArea fromString(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Concept area must not be null or blank");
        }
        final var normalized = value.strip().toLowerCase().replace(' ', '-');
        for (final ConceptArea area : values()) {
            if (area.displayName.equals(normalized) || area.name().equalsIgnoreCase(value.strip().replace('-', '_'))) {
                return area;
            }
        }
        throw new IllegalArgumentException("Unknown concept area: '" + value + "'");
    }
}
