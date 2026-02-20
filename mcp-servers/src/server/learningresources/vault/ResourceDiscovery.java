package server.learningresources.vault;

import server.learningresources.model.ConceptArea;
import server.learningresources.model.ContentFreshness;
import server.learningresources.model.DifficultyLevel;
import server.learningresources.model.LearningResource;
import server.learningresources.model.ResourceCategory;
import server.learningresources.model.ResourceQuery;
import server.learningresources.model.ResourceType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Smart discovery engine that interprets user intent and finds relevant resources.
 *
 * <p>Handles three query modes:
 * <ul>
 *   <li><strong>Specific</strong> — user knows exactly what they want ("JUnit 5 docs")</li>
 *   <li><strong>Vague</strong> — user knows the topic area ("something about testing")</li>
 *   <li><strong>Exploratory</strong> — user wants to learn but isn't sure what ("beginner stuff")</li>
 * </ul>
 *
 * <p>Uses relevance scoring across multiple dimensions: text match, concept alignment,
 * category match, freshness, official-source boost, and difficulty fit.
 */
public final class ResourceDiscovery {

    private static final Logger LOGGER = Logger.getLogger(ResourceDiscovery.class.getName());

    /** Maximum results to return from a discovery query. */
    private static final int MAX_RESULTS = 15;

    /** Scoring weights for relevance ranking. */
    private static final int EXACT_TITLE_MATCH_SCORE = 100;
    private static final int PARTIAL_TITLE_MATCH_SCORE = 40;
    private static final int DESCRIPTION_MATCH_SCORE = 20;
    private static final int TAG_MATCH_SCORE = 15;
    private static final int CONCEPT_MATCH_SCORE = 25;
    private static final int CATEGORY_MATCH_SCORE = 20;
    private static final int OFFICIAL_BOOST = 15;
    private static final int FRESHNESS_BOOST = 10;
    private static final int DIFFICULTY_FIT_SCORE = 10;

    /** Keyword-to-concept mapping for vague query inference. */
    private static final Map<String, ConceptArea> KEYWORD_CONCEPT_MAP = buildKeywordConceptMap();

    /** Keyword-to-category mapping for vague query inference. */
    private static final Map<String, ResourceCategory> KEYWORD_CATEGORY_MAP = buildKeywordCategoryMap();

    /** Keyword-to-difficulty mapping for exploratory queries. */
    private static final Map<String, DifficultyLevel> KEYWORD_DIFFICULTY_MAP = buildKeywordDifficultyMap();

    private final ResourceVault vault;

    /**
     * Creates a discovery engine backed by the given vault.
     *
     * @param vault the resource vault to search
     */
    public ResourceDiscovery(final ResourceVault vault) {
        this.vault = Objects.requireNonNull(vault, "ResourceVault must not be null");
    }

    // ─── Public API ─────────────────────────────────────────────────────

    /**
     * Discovers resources matching a free-form user query.
     *
     * <p>Automatically classifies the query as specific, vague, or exploratory
     * and applies the appropriate search strategy with relevance scoring.
     *
     * @param userInput the raw user query string
     * @return a {@link DiscoveryResult} with scored, sorted results and suggestions
     */
    public DiscoveryResult discover(final String userInput) {
        if (userInput == null || userInput.isBlank()) {
            return exploreDefault();
        }

        final var normalizedInput = userInput.strip().toLowerCase();
        final var queryType = classifyQuery(normalizedInput);

        LOGGER.fine("Query classified as: " + queryType + " for input: '" + normalizedInput + "'");

        return switch (queryType) {
            case SPECIFIC -> handleSpecificQuery(normalizedInput);
            case VAGUE -> handleVagueQuery(normalizedInput);
            case EXPLORATORY -> handleExploratoryQuery(normalizedInput);
        };
    }

    /**
     * Discovers resources by concept area, optionally filtered by difficulty range.
     *
     * @param concept       the concept area to search
     * @param minDifficulty minimum difficulty (may be null for no lower bound)
     * @param maxDifficulty maximum difficulty (may be null for no upper bound)
     * @return a {@link DiscoveryResult} with scored concept matches
     */
    public DiscoveryResult discoverByConcept(final ConceptArea concept,
                                              final DifficultyLevel minDifficulty,
                                              final DifficultyLevel maxDifficulty) {
        Objects.requireNonNull(concept, "Concept must not be null");

        final var query = ResourceQuery.byConcept(concept);
        final var candidates = vault.search(query);

        final var scored = candidates.stream()
                .map(resource -> scoreForConcept(resource, concept, minDifficulty, maxDifficulty))
                .sorted(Comparator.comparingInt(ScoredResource::score).reversed())
                .limit(MAX_RESULTS)
                .toList();

        return new DiscoveryResult(
                QueryType.SPECIFIC,
                scored,
                List.of(),
                "Resources for concept: " + concept.name()
        );
    }

    /**
     * Returns a curated "getting started" set for a given category.
     *
     * @param category the category to explore
     * @return a {@link DiscoveryResult} with beginner-friendly, official-first resources
     */
    public DiscoveryResult exploreCategory(final ResourceCategory category) {
        Objects.requireNonNull(category, "Category must not be null");

        final var query = ResourceQuery.byCategory(category);
        final var candidates = vault.search(query);

        final var scored = candidates.stream()
                .map(resource -> scoreForExploration(resource, DifficultyLevel.BEGINNER))
                .sorted(Comparator.comparingInt(ScoredResource::score).reversed())
                .limit(MAX_RESULTS)
                .toList();

        final var suggestions = suggestRelatedCategories(category);
        return new DiscoveryResult(
                QueryType.EXPLORATORY,
                scored,
                suggestions,
                "Explore " + category.getDisplayName() + " — starting with beginner-friendly resources"
        );
    }

    // ─── Query Classification ───────────────────────────────────────────

    /**
     * Classifies a normalized query string into one of three query types.
     *
     * <p>Rules:
     * <ul>
     *   <li>Query includes specific identifiers (quoted text, URLs, exact titles) → SPECIFIC</li>
     *   <li>Query is short and vague with exploratory keywords → EXPLORATORY</li>
     *   <li>Otherwise → VAGUE (topic-level search)</li>
     * </ul>
     */
    private QueryType classifyQuery(final String input) {
        // Specific: contains quoted text, URLs, or very targeted phrases
        if (input.contains("\"") || input.contains("http") || input.contains("docs for")
                || input.contains("reference for") || input.contains("official")) {
            return QueryType.SPECIFIC;
        }

        // Exploratory: short + uses exploratory language
        final var exploratoryKeywords = List.of(
                "learn", "start", "beginner", "getting started", "new to",
                "don't know", "where to begin", "recommend", "suggest",
                "what should", "help me", "explore", "overview", "introduction"
        );
        final var isShort = input.split("\\s+").length <= 5;
        final var hasExploratoryKeyword = exploratoryKeywords.stream()
                .anyMatch(input::contains);

        if (isShort && hasExploratoryKeyword) {
            return QueryType.EXPLORATORY;
        }

        // Exploratory: very short with no clear topic
        if (input.split("\\s+").length <= 2 && KEYWORD_CONCEPT_MAP.get(input) == null
                && KEYWORD_CATEGORY_MAP.get(input) == null) {
            return QueryType.EXPLORATORY;
        }

        return QueryType.VAGUE;
    }

    // ─── Query Handlers ─────────────────────────────────────────────────

    /**
     * Handles specific queries — user knows what they want.
     * Focuses on exact text match and relevance scoring.
     */
    private DiscoveryResult handleSpecificQuery(final String input) {
        final var allResources = vault.search(ResourceQuery.ALL);

        final var scored = allResources.stream()
                .map(resource -> scoreForSpecific(resource, input))
                .filter(sr -> sr.score() > 0)
                .sorted(Comparator.comparingInt(ScoredResource::score).reversed())
                .limit(MAX_RESULTS)
                .toList();

        final var suggestions = scored.isEmpty()
                ? generateDidYouMean(input)
                : List.<String>of();

        return new DiscoveryResult(
                QueryType.SPECIFIC,
                scored,
                suggestions,
                scored.isEmpty()
                        ? "No exact matches found for: '" + input + "'"
                        : "Found " + scored.size() + " matching resources"
        );
    }

    /**
     * Handles vague queries — user has a topic in mind but not a specific resource.
     * Infers concepts and categories from keywords, then scores broadly.
     */
    private DiscoveryResult handleVagueQuery(final String input) {
        final var words = input.split("\\s+");
        final var inferredConcepts = new ArrayList<ConceptArea>();
        final var inferredCategories = new ArrayList<ResourceCategory>();

        for (final var word : words) {
            final var concept = KEYWORD_CONCEPT_MAP.get(word);
            if (concept != null) {
                inferredConcepts.add(concept);
            }
            final var category = KEYWORD_CATEGORY_MAP.get(word);
            if (category != null) {
                inferredCategories.add(category);
            }
        }

        // Also try multi-word phrases
        for (final var entry : KEYWORD_CONCEPT_MAP.entrySet()) {
            if (input.contains(entry.getKey()) && !inferredConcepts.contains(entry.getValue())) {
                inferredConcepts.add(entry.getValue());
            }
        }
        for (final var entry : KEYWORD_CATEGORY_MAP.entrySet()) {
            if (input.contains(entry.getKey()) && !inferredCategories.contains(entry.getValue())) {
                inferredCategories.add(entry.getValue());
            }
        }

        final var allResources = vault.search(ResourceQuery.ALL);
        final var scored = allResources.stream()
                .map(resource -> scoreForVague(resource, input, inferredConcepts, inferredCategories))
                .filter(sr -> sr.score() > 0)
                .sorted(Comparator.comparingInt(ScoredResource::score).reversed())
                .limit(MAX_RESULTS)
                .toList();

        final var suggestions = scored.isEmpty()
                ? generateDidYouMean(input)
                : List.<String>of();

        final var summary = buildVagueSummary(input, inferredConcepts, inferredCategories, scored.size());
        return new DiscoveryResult(QueryType.VAGUE, scored, suggestions, summary);
    }

    /**
     * Handles exploratory queries — user wants to learn but isn't sure what.
     * Prioritizes beginner-friendly, official, actively-maintained resources.
     */
    private DiscoveryResult handleExploratoryQuery(final String input) {
        // Try to infer preferred difficulty from the query
        var targetDifficulty = DifficultyLevel.BEGINNER;
        for (final var entry : KEYWORD_DIFFICULTY_MAP.entrySet()) {
            if (input.contains(entry.getKey())) {
                targetDifficulty = entry.getValue();
                break;
            }
        }

        // Try to infer a category, otherwise show a broad mix
        ResourceCategory targetCategory = null;
        for (final var entry : KEYWORD_CATEGORY_MAP.entrySet()) {
            if (input.contains(entry.getKey())) {
                targetCategory = entry.getValue();
                break;
            }
        }

        final var allResources = vault.search(ResourceQuery.ALL);
        final var effectiveDifficulty = targetDifficulty;
        final var effectiveCategory = targetCategory;

        final var scored = allResources.stream()
                .map(resource -> scoreForExploration(resource, effectiveDifficulty))
                .filter(sr -> effectiveCategory == null
                        || sr.resource().categories().contains(effectiveCategory))
                .sorted(Comparator.comparingInt(ScoredResource::score).reversed())
                .limit(MAX_RESULTS)
                .toList();

        final var suggestions = new ArrayList<String>();
        suggestions.add("Try 'browse java' to explore Java resources");
        suggestions.add("Try 'official docs' to see official documentation");
        suggestions.add("Try 'system design' for architecture resources");
        suggestions.add("Try 'testing' for testing resources");

        return new DiscoveryResult(
                QueryType.EXPLORATORY,
                scored,
                suggestions,
                "Here are recommended resources for " + targetDifficulty + " level learners"
        );
    }

    /**
     * Default exploration when no query is provided — shows a curated overview.
     */
    private DiscoveryResult exploreDefault() {
        final var allResources = vault.search(ResourceQuery.ALL);

        final var scored = allResources.stream()
                .map(resource -> scoreForExploration(resource, DifficultyLevel.BEGINNER))
                .sorted(Comparator.comparingInt(ScoredResource::score).reversed())
                .limit(MAX_RESULTS)
                .toList();

        return new DiscoveryResult(
                QueryType.EXPLORATORY,
                scored,
                List.of("Try searching for a specific topic, category, or concept"),
                "Showing top recommended resources across all categories"
        );
    }

    // ─── Scoring Functions ──────────────────────────────────────────────

    /**
     * Scores a resource for a specific, targeted query.
     */
    private ScoredResource scoreForSpecific(final LearningResource resource,
                                            final String input) {
        var score = 0;
        final var searchable = resource.searchableText().toLowerCase();
        final var titleLower = resource.title().toLowerCase();

        // Title match
        if (titleLower.equals(input)) {
            score += EXACT_TITLE_MATCH_SCORE;
        } else if (titleLower.contains(input)) {
            score += PARTIAL_TITLE_MATCH_SCORE;
        }

        // ID match
        if (resource.id().toLowerCase().contains(input)) {
            score += PARTIAL_TITLE_MATCH_SCORE;
        }

        // Description / full-text match
        if (searchable.contains(input)) {
            score += DESCRIPTION_MATCH_SCORE;
        }

        // Individual word matching for multi-word queries
        for (final var word : input.split("\\s+")) {
            if (word.length() < 3) {
                continue;
            }
            if (titleLower.contains(word)) {
                score += TAG_MATCH_SCORE;
            }
            if (resource.tags().stream().anyMatch(tag -> tag.contains(word))) {
                score += TAG_MATCH_SCORE;
            }
        }

        // Boost official sources
        if (resource.isOfficial()) {
            score += OFFICIAL_BOOST;
        }

        return new ScoredResource(resource, score);
    }

    /**
     * Scores a resource for a vague, topic-level query.
     */
    private ScoredResource scoreForVague(final LearningResource resource,
                                         final String input,
                                         final List<ConceptArea> inferredConcepts,
                                         final List<ResourceCategory> inferredCategories) {
        var score = 0;

        // Text matching (lower weight than specific queries)
        final var searchable = resource.searchableText().toLowerCase();
        for (final var word : input.split("\\s+")) {
            if (word.length() < 3) {
                continue;
            }
            if (searchable.contains(word)) {
                score += TAG_MATCH_SCORE;
            }
        }

        // Concept alignment
        for (final var concept : inferredConcepts) {
            if (resource.hasConcept(concept)) {
                score += CONCEPT_MATCH_SCORE;
            }
        }

        // Category alignment
        for (final var category : inferredCategories) {
            if (resource.categories().contains(category)) {
                score += CATEGORY_MATCH_SCORE;
            }
        }

        // Boost official and actively maintained
        if (resource.isOfficial()) {
            score += OFFICIAL_BOOST;
        }
        if (resource.freshness() == ContentFreshness.ACTIVELY_MAINTAINED) {
            score += FRESHNESS_BOOST;
        }

        return new ScoredResource(resource, score);
    }

    /**
     * Scores a resource for a concept-targeted query with optional difficulty range.
     */
    private ScoredResource scoreForConcept(final LearningResource resource,
                                           final ConceptArea concept,
                                           final DifficultyLevel minDifficulty,
                                           final DifficultyLevel maxDifficulty) {
        var score = CONCEPT_MATCH_SCORE;

        if (resource.isOfficial()) {
            score += OFFICIAL_BOOST;
        }
        if (resource.freshness() == ContentFreshness.ACTIVELY_MAINTAINED) {
            score += FRESHNESS_BOOST;
        }

        // Difficulty fit
        final var effectiveMin = minDifficulty != null ? minDifficulty : DifficultyLevel.BEGINNER;
        final var effectiveMax = maxDifficulty != null ? maxDifficulty : DifficultyLevel.EXPERT;
        if (resource.isDifficultyInRange(effectiveMin, effectiveMax)) {
            score += DIFFICULTY_FIT_SCORE;
        }

        return new ScoredResource(resource, score);
    }

    /**
     * Scores a resource for exploration — favours beginner-friendly, official,
     * getting-started resources.
     */
    private ScoredResource scoreForExploration(final LearningResource resource,
                                               final DifficultyLevel targetDifficulty) {
        var score = 0;

        // Difficulty match (prefer target difficulty)
        if (resource.difficulty() == targetDifficulty) {
            score += DIFFICULTY_FIT_SCORE * 2;
        } else if (resource.difficulty().getLevel()
                <= targetDifficulty.getLevel() + 1) {
            score += DIFFICULTY_FIT_SCORE;
        }

        // Boost getting-started concepts
        if (resource.hasConcept(ConceptArea.GETTING_STARTED)) {
            score += CONCEPT_MATCH_SCORE;
        }

        // Boost official sources
        if (resource.isOfficial()) {
            score += OFFICIAL_BOOST * 2;
        }

        // Boost actively maintained
        if (resource.freshness() == ContentFreshness.ACTIVELY_MAINTAINED) {
            score += FRESHNESS_BOOST;
        }

        // Boost free resources for exploratory learners
        if (resource.isFree()) {
            score += 5;
        }

        return new ScoredResource(resource, score);
    }

    // ─── Suggestion Engine ──────────────────────────────────────────────

    /**
     * Generates "did you mean?" suggestions when no results are found.
     */
    private List<String> generateDidYouMean(final String input) {
        final var suggestions = new ArrayList<String>();

        // Suggest matching categories
        for (final var category : ResourceCategory.values()) {
            if (category.getDisplayName().toLowerCase().contains(input)
                    || input.contains(category.getDisplayName().toLowerCase())) {
                suggestions.add("Browse category: " + category.getDisplayName());
            }
        }

        // Suggest matching concepts
        for (final var concept : ConceptArea.values()) {
            final var conceptName = concept.name().toLowerCase().replace('_', ' ');
            if (conceptName.contains(input) || input.contains(conceptName)) {
                suggestions.add("Search concept: " + concept.name());
            }
        }

        // Generic suggestions if nothing found
        if (suggestions.isEmpty()) {
            suggestions.add("Try broader terms (e.g., 'java', 'testing', 'design patterns')");
            suggestions.add("Use 'list_categories' to see all available categories");
        }

        return suggestions;
    }

    /**
     * Suggests related categories for exploration.
     */
    private List<String> suggestRelatedCategories(final ResourceCategory category) {
        final var related = new ArrayList<String>();
        switch (category) {
            case JAVA -> {
                related.add("Also explore: software-engineering, testing");
                related.add("Concept: try 'concurrency' or 'design-patterns'");
            }
            case WEB, JAVASCRIPT -> {
                related.add("Also explore: security, devops");
                related.add("Concept: try 'web-security' or 'api-design'");
            }
            case PYTHON -> {
                related.add("Also explore: ai-ml, data");
                related.add("Concept: try 'machine-learning' or 'testing'");
            }
            case DEVOPS -> {
                related.add("Also explore: software-engineering");
                related.add("Concept: try 'containers' or 'ci-cd'");
            }
            default -> related.add("Try searching for a specific concept area");
        }
        return related;
    }

    /**
     * Builds a human-readable summary for vague query results.
     */
    private String buildVagueSummary(final String input,
                                     final List<ConceptArea> concepts,
                                     final List<ResourceCategory> categories,
                                     final int resultCount) {
        final var builder = new StringBuilder();
        builder.append("Found ").append(resultCount).append(" resources for '").append(input).append("'");

        if (!concepts.isEmpty()) {
            builder.append("\nInferred concepts: ").append(concepts.stream()
                    .map(ConceptArea::name)
                    .collect(Collectors.joining(", ")));
        }
        if (!categories.isEmpty()) {
            builder.append("\nInferred categories: ").append(categories.stream()
                    .map(ResourceCategory::getDisplayName)
                    .collect(Collectors.joining(", ")));
        }
        return builder.toString();
    }

    // ─── Keyword Mappings ───────────────────────────────────────────────

    /**
     * Builds keyword-to-concept mapping for vague query inference.
     */
    private static Map<String, ConceptArea> buildKeywordConceptMap() {
        final var map = new HashMap<String, ConceptArea>();

        // Programming fundamentals
        map.put("oop", ConceptArea.OOP);
        map.put("object-oriented", ConceptArea.OOP);
        map.put("classes", ConceptArea.OOP);
        map.put("inheritance", ConceptArea.OOP);
        map.put("polymorphism", ConceptArea.OOP);
        map.put("functional", ConceptArea.FUNCTIONAL_PROGRAMMING);
        map.put("lambda", ConceptArea.FUNCTIONAL_PROGRAMMING);
        map.put("lambdas", ConceptArea.FUNCTIONAL_PROGRAMMING);
        map.put("streams", ConceptArea.FUNCTIONAL_PROGRAMMING);
        map.put("generics", ConceptArea.GENERICS);
        map.put("type-system", ConceptArea.GENERICS);

        // Core CS
        map.put("concurrency", ConceptArea.CONCURRENCY);
        map.put("threads", ConceptArea.CONCURRENCY);
        map.put("async", ConceptArea.CONCURRENCY);
        map.put("parallel", ConceptArea.CONCURRENCY);
        map.put("data structures", ConceptArea.DATA_STRUCTURES);
        map.put("collections", ConceptArea.DATA_STRUCTURES);
        map.put("algorithms", ConceptArea.ALGORITHMS);
        map.put("sorting", ConceptArea.ALGORITHMS);
        map.put("searching", ConceptArea.ALGORITHMS);
        map.put("big-o", ConceptArea.COMPLEXITY_ANALYSIS);
        map.put("complexity", ConceptArea.COMPLEXITY_ANALYSIS);
        map.put("memory", ConceptArea.MEMORY_MANAGEMENT);
        map.put("garbage collection", ConceptArea.MEMORY_MANAGEMENT);
        map.put("jvm", ConceptArea.MEMORY_MANAGEMENT);

        // Software engineering
        map.put("design patterns", ConceptArea.DESIGN_PATTERNS);
        map.put("patterns", ConceptArea.DESIGN_PATTERNS);
        map.put("singleton", ConceptArea.DESIGN_PATTERNS);
        map.put("factory", ConceptArea.DESIGN_PATTERNS);
        map.put("clean code", ConceptArea.CLEAN_CODE);
        map.put("refactoring", ConceptArea.CLEAN_CODE);
        map.put("best practices", ConceptArea.CLEAN_CODE);
        map.put("testing", ConceptArea.TESTING);
        map.put("unit test", ConceptArea.TESTING);
        map.put("tdd", ConceptArea.TESTING);
        map.put("junit", ConceptArea.TESTING);
        map.put("api", ConceptArea.API_DESIGN);
        map.put("rest", ConceptArea.API_DESIGN);
        map.put("architecture", ConceptArea.ARCHITECTURE);
        map.put("microservices", ConceptArea.ARCHITECTURE);

        // System design & infra
        map.put("system design", ConceptArea.SYSTEM_DESIGN);
        map.put("scalability", ConceptArea.SYSTEM_DESIGN);
        map.put("database", ConceptArea.DATABASES);
        map.put("sql", ConceptArea.DATABASES);
        map.put("postgresql", ConceptArea.DATABASES);
        map.put("distributed", ConceptArea.DISTRIBUTED_SYSTEMS);
        map.put("networking", ConceptArea.NETWORKING);
        map.put("http", ConceptArea.NETWORKING);
        map.put("tcp", ConceptArea.NETWORKING);
        map.put("operating systems", ConceptArea.OPERATING_SYSTEMS);

        // DevOps & tooling
        map.put("ci/cd", ConceptArea.CI_CD);
        map.put("ci-cd", ConceptArea.CI_CD);
        map.put("pipeline", ConceptArea.CI_CD);
        map.put("github actions", ConceptArea.CI_CD);
        map.put("docker", ConceptArea.CONTAINERS);
        map.put("kubernetes", ConceptArea.CONTAINERS);
        map.put("containers", ConceptArea.CONTAINERS);
        map.put("git", ConceptArea.VERSION_CONTROL);
        map.put("version control", ConceptArea.VERSION_CONTROL);
        map.put("gradle", ConceptArea.BUILD_TOOLS);
        map.put("maven", ConceptArea.BUILD_TOOLS);
        map.put("build", ConceptArea.BUILD_TOOLS);

        // Security
        map.put("security", ConceptArea.WEB_SECURITY);
        map.put("owasp", ConceptArea.WEB_SECURITY);
        map.put("xss", ConceptArea.WEB_SECURITY);
        map.put("injection", ConceptArea.WEB_SECURITY);
        map.put("cryptography", ConceptArea.CRYPTOGRAPHY);
        map.put("encryption", ConceptArea.CRYPTOGRAPHY);

        // AI & data
        map.put("machine learning", ConceptArea.MACHINE_LEARNING);
        map.put("deep learning", ConceptArea.MACHINE_LEARNING);
        map.put("neural", ConceptArea.MACHINE_LEARNING);
        map.put("ai", ConceptArea.MACHINE_LEARNING);
        map.put("llm", ConceptArea.LLM_AND_PROMPTING);
        map.put("prompt", ConceptArea.LLM_AND_PROMPTING);
        map.put("gpt", ConceptArea.LLM_AND_PROMPTING);

        // Career & meta
        map.put("interview", ConceptArea.INTERVIEW_PREP);
        map.put("career", ConceptArea.CAREER_DEVELOPMENT);
        map.put("roadmap", ConceptArea.CAREER_DEVELOPMENT);
        map.put("getting started", ConceptArea.GETTING_STARTED);
        map.put("beginner", ConceptArea.GETTING_STARTED);

        return Map.copyOf(map);
    }

    /**
     * Builds keyword-to-category mapping for vague query inference.
     */
    private static Map<String, ResourceCategory> buildKeywordCategoryMap() {
        final var map = new HashMap<String, ResourceCategory>();
        map.put("java", ResourceCategory.JAVA);
        map.put("spring", ResourceCategory.JAVA);
        map.put("jdk", ResourceCategory.JAVA);
        map.put("python", ResourceCategory.PYTHON);
        map.put("javascript", ResourceCategory.JAVASCRIPT);
        map.put("typescript", ResourceCategory.JAVASCRIPT);
        map.put("web", ResourceCategory.WEB);
        map.put("html", ResourceCategory.WEB);
        map.put("css", ResourceCategory.WEB);
        map.put("frontend", ResourceCategory.WEB);
        map.put("devops", ResourceCategory.DEVOPS);
        map.put("docker", ResourceCategory.DEVOPS);
        map.put("kubernetes", ResourceCategory.DEVOPS);
        map.put("security", ResourceCategory.SECURITY);
        map.put("data", ResourceCategory.DATA);
        map.put("database", ResourceCategory.DATA);
        map.put("ai", ResourceCategory.AI_ML);
        map.put("ml", ResourceCategory.AI_ML);
        map.put("machine learning", ResourceCategory.AI_ML);
        map.put("algorithm", ResourceCategory.ALGORITHMS);
        map.put("algorithms", ResourceCategory.ALGORITHMS);
        map.put("testing", ResourceCategory.TESTING);
        map.put("engineering", ResourceCategory.SOFTWARE_ENGINEERING);
        return Map.copyOf(map);
    }

    /**
     * Builds keyword-to-difficulty mapping for exploratory queries.
     */
    private static Map<String, DifficultyLevel> buildKeywordDifficultyMap() {
        final var map = new HashMap<String, DifficultyLevel>();
        map.put("beginner", DifficultyLevel.BEGINNER);
        map.put("new", DifficultyLevel.BEGINNER);
        map.put("start", DifficultyLevel.BEGINNER);
        map.put("easy", DifficultyLevel.BEGINNER);
        map.put("intro", DifficultyLevel.BEGINNER);
        map.put("intermediate", DifficultyLevel.INTERMEDIATE);
        map.put("moderate", DifficultyLevel.INTERMEDIATE);
        map.put("advanced", DifficultyLevel.ADVANCED);
        map.put("deep", DifficultyLevel.ADVANCED);
        map.put("expert", DifficultyLevel.EXPERT);
        map.put("master", DifficultyLevel.EXPERT);
        return Map.copyOf(map);
    }

    // ─── Result Types ───────────────────────────────────────────────────

    /**
     * Classifies the type of user query.
     */
    public enum QueryType {
        /** User knows exactly what they want. */
        SPECIFIC,
        /** User knows the topic area but not the specific resource. */
        VAGUE,
        /** User wants to learn but isn't sure what. */
        EXPLORATORY
    }

    /**
     * A learning resource paired with a relevance score.
     *
     * @param resource the matched resource
     * @param score    the relevance score (higher is more relevant)
     */
    public record ScoredResource(LearningResource resource, int score) {

        /**
         * Creates a scored resource.
         *
         * @param resource the matched resource
         * @param score    the relevance score
         */
        public ScoredResource {
            Objects.requireNonNull(resource, "Resource must not be null");
        }
    }

    /**
     * The complete result of a discovery query.
     *
     * @param queryType   how the query was classified
     * @param results     scored resources sorted by relevance (descending)
     * @param suggestions "did you mean?" or follow-up suggestions
     * @param summary     human-readable summary of the search
     */
    public record DiscoveryResult(
            QueryType queryType,
            List<ScoredResource> results,
            List<String> suggestions,
            String summary
    ) {

        /**
         * Creates a discovery result.
         *
         * @param queryType   the classified query type
         * @param results     scored and sorted results
         * @param suggestions follow-up suggestions
         * @param summary     human-readable summary
         */
        public DiscoveryResult {
            Objects.requireNonNull(queryType, "QueryType must not be null");
            results = List.copyOf(results);
            suggestions = List.copyOf(suggestions);
            Objects.requireNonNull(summary, "Summary must not be null");
        }

        /**
         * Returns {@code true} if this result has no matching resources.
         *
         * @return true if empty
         */
        public boolean isEmpty() {
            return results.isEmpty();
        }

        /**
         * Returns the number of matched resources.
         *
         * @return result count
         */
        public int count() {
            return results.size();
        }
    }
}
