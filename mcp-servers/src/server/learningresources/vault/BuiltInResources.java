package server.learningresources.vault;

import server.learningresources.model.LearningResource;
import server.learningresources.model.ResourceCategory;
import server.learningresources.model.ResourceType;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

/**
 * Pre-loaded library of famous, useful, worldwide learning resources.
 *
 * <p>This is the "vault" — a curated collection of high-quality resources
 * organized by topic and type. These are well-known, authoritative sources
 * that every developer should know about.
 *
 * <p>Categories include official documentation, community tutorials, blogs,
 * open-source project guides, video courses, and reference materials.
 */
public final class BuiltInResources {

    private BuiltInResources() {
        // Utility class — no instantiation
    }

    /**
     * Returns all built-in learning resources.
     *
     * @return an unmodifiable list of curated resources
     */
    public static List<LearningResource> all() {
        final var now = Instant.now();
        return Collections.unmodifiableList(List.of(

                // ─── JAVA — OFFICIAL DOCUMENTATION ──────────────────────────

                new LearningResource(
                        "oracle-java-tutorials",
                        "The Java Tutorials (dev.java)",
                        "https://dev.java/learn/",
                        "Oracle's official Java tutorials covering language basics, OOP, generics, "
                                + "lambdas, streams, collections, modules, records, sealed classes, "
                                + "pattern matching, and more. The authoritative starting point for Java.",
                        ResourceType.TUTORIAL,
                        List.of(ResourceCategory.JAVA),
                        List.of("official", "language-basics", "oop", "generics", "streams", "modules"),
                        "Oracle / dev.java",
                        "beginner",
                        true, now
                ),

                new LearningResource(
                        "jdk-javadoc",
                        "JDK API Documentation (Javadoc)",
                        "https://docs.oracle.com/en/java/javase/21/docs/api/",
                        "Official JDK 21 API documentation — the complete reference for every "
                                + "class, method, and package in the standard library.",
                        ResourceType.API_REFERENCE,
                        List.of(ResourceCategory.JAVA),
                        List.of("official", "api", "javadoc", "reference"),
                        "Oracle",
                        "intermediate",
                        true, now
                ),

                new LearningResource(
                        "java-language-spec",
                        "The Java Language Specification (JLS)",
                        "https://docs.oracle.com/javase/specs/jls/se21/html/index.html",
                        "The formal specification of the Java programming language. "
                                + "Defines the syntax, type system, and semantics. Essential for "
                                + "understanding edge cases and language guarantees.",
                        ResourceType.DOCUMENTATION,
                        List.of(ResourceCategory.JAVA),
                        List.of("official", "specification", "jls", "formal"),
                        "Oracle",
                        "advanced",
                        true, now
                ),

                new LearningResource(
                        "inside-java",
                        "Inside.java — Official Java Blog",
                        "https://inside.java/",
                        "The official blog from the Java team at Oracle. Features JEP previews, "
                                + "deep dives into new features, performance analysis, and interviews "
                                + "with JDK engineers.",
                        ResourceType.BLOG,
                        List.of(ResourceCategory.JAVA),
                        List.of("official", "blog", "jep", "new-features", "performance"),
                        "Oracle Java Team",
                        "intermediate",
                        true, now
                ),

                // ─── JAVA — COMMUNITY RESOURCES ─────────────────────────────

                new LearningResource(
                        "baeldung-java",
                        "Baeldung — Java Tutorials & Guides",
                        "https://www.baeldung.com/",
                        "Comprehensive tutorial site covering Spring, Java core, testing, "
                                + "persistence, security, and modern Java features. Practical, "
                                + "example-driven articles with working code.",
                        ResourceType.TUTORIAL,
                        List.of(ResourceCategory.JAVA, ResourceCategory.WEB, ResourceCategory.TESTING),
                        List.of("spring", "tutorials", "practical", "spring-boot", "testing"),
                        "Baeldung",
                        "intermediate",
                        true, now
                ),

                new LearningResource(
                        "jenkov-tutorials",
                        "Jenkov.com — Java & Web Tutorials",
                        "https://jenkov.com/",
                        "In-depth tutorials on Java concurrency, networking, NIO, JDBC, "
                                + "design patterns, and web technologies. Known for exceptionally "
                                + "clear explanations of complex topics.",
                        ResourceType.TUTORIAL,
                        List.of(ResourceCategory.JAVA, ResourceCategory.WEB),
                        List.of("concurrency", "nio", "design-patterns", "networking"),
                        "Jakob Jenkov",
                        "intermediate",
                        true, now
                ),

                new LearningResource(
                        "effective-java",
                        "Effective Java (3rd Edition) — Joshua Bloch",
                        "https://www.oreilly.com/library/view/effective-java/9780134686097/",
                        "The definitive best-practices guide for Java. 90 items covering "
                                + "object creation, equality, generics, enums, lambdas, streams, "
                                + "concurrency, and serialization.",
                        ResourceType.BOOK,
                        List.of(ResourceCategory.JAVA, ResourceCategory.SOFTWARE_ENGINEERING),
                        List.of("best-practices", "design", "patterns", "joshua-bloch"),
                        "Joshua Bloch",
                        "intermediate",
                        false, now
                ),

                new LearningResource(
                        "java-concurrency-in-practice",
                        "Java Concurrency in Practice",
                        "https://jcip.net/",
                        "The gold standard reference for multithreaded Java programming. "
                                + "Covers thread safety, synchronization, the Java Memory Model, "
                                + "concurrent collections, and task execution frameworks.",
                        ResourceType.BOOK,
                        List.of(ResourceCategory.JAVA),
                        List.of("concurrency", "threads", "memory-model", "synchronization"),
                        "Brian Goetz",
                        "advanced",
                        false, now
                ),

                // ─── JAVA — OPEN SOURCE PROJECTS TO STUDY ──────────────────

                new LearningResource(
                        "spring-boot-guides",
                        "Spring Boot — Getting Started Guides",
                        "https://spring.io/guides",
                        "Official Spring Boot guides covering REST APIs, data access, security, "
                                + "messaging, testing, and deployment. Each guide is a complete "
                                + "working project.",
                        ResourceType.TUTORIAL,
                        List.of(ResourceCategory.JAVA, ResourceCategory.WEB),
                        List.of("spring-boot", "rest-api", "microservices", "guides"),
                        "VMware / Spring Team",
                        "intermediate",
                        true, now
                ),

                new LearningResource(
                        "guava-wiki",
                        "Google Guava — Wiki & User Guide",
                        "https://github.com/google/guava/wiki",
                        "User guide for Google Guava — a widely-used utility library. "
                                + "Learn immutable collections, caching, hashing, string utilities, "
                                + "and functional idioms from world-class engineers.",
                        ResourceType.DOCUMENTATION,
                        List.of(ResourceCategory.JAVA),
                        List.of("guava", "collections", "caching", "utilities", "google"),
                        "Google",
                        "intermediate",
                        true, now
                ),

                new LearningResource(
                        "junit5-user-guide",
                        "JUnit 5 User Guide",
                        "https://junit.org/junit5/docs/current/user-guide/",
                        "Official guide for JUnit 5 — the standard Java testing framework. "
                                + "Covers assertions, parameterized tests, extensions, nested tests, "
                                + "and migration from JUnit 4.",
                        ResourceType.DOCUMENTATION,
                        List.of(ResourceCategory.JAVA, ResourceCategory.TESTING),
                        List.of("junit", "testing", "unit-tests", "tdd"),
                        "JUnit Team",
                        "intermediate",
                        true, now
                ),

                // ─── WEB & JAVASCRIPT ───────────────────────────────────────

                new LearningResource(
                        "mdn-web-docs",
                        "MDN Web Docs",
                        "https://developer.mozilla.org/en-US/",
                        "Mozilla's comprehensive web documentation — the de facto reference "
                                + "for HTML, CSS, JavaScript, Web APIs, HTTP, and accessibility. "
                                + "Includes tutorials, references, and browser compatibility data.",
                        ResourceType.DOCUMENTATION,
                        List.of(ResourceCategory.WEB, ResourceCategory.JAVASCRIPT),
                        List.of("html", "css", "javascript", "web-apis", "http", "reference"),
                        "Mozilla",
                        "beginner",
                        true, now
                ),

                new LearningResource(
                        "javascript-info",
                        "The Modern JavaScript Tutorial (javascript.info)",
                        "https://javascript.info/",
                        "Comprehensive JavaScript tutorial from basics to advanced topics. "
                                + "Covers the language, browser APIs, and Node.js. Known for "
                                + "clear explanations with interactive examples.",
                        ResourceType.TUTORIAL,
                        List.of(ResourceCategory.JAVASCRIPT, ResourceCategory.WEB),
                        List.of("javascript", "dom", "async", "promises", "fetch"),
                        "Ilya Kantor",
                        "beginner",
                        true, now
                ),

                new LearningResource(
                        "typescript-handbook",
                        "TypeScript Handbook",
                        "https://www.typescriptlang.org/docs/handbook/intro.html",
                        "Official TypeScript handbook — type system, generics, utility types, "
                                + "modules, declaration files, and migration guides.",
                        ResourceType.DOCUMENTATION,
                        List.of(ResourceCategory.JAVASCRIPT, ResourceCategory.WEB),
                        List.of("typescript", "types", "generics", "handbook"),
                        "Microsoft",
                        "intermediate",
                        true, now
                ),

                // ─── PYTHON ─────────────────────────────────────────────────

                new LearningResource(
                        "python-docs-tutorial",
                        "The Python Tutorial (Official)",
                        "https://docs.python.org/3/tutorial/",
                        "Official Python tutorial from python.org. Covers data structures, "
                                + "control flow, functions, modules, I/O, classes, and the standard library.",
                        ResourceType.TUTORIAL,
                        List.of(ResourceCategory.PYTHON),
                        List.of("official", "python3", "language-basics", "stdlib"),
                        "Python Software Foundation",
                        "beginner",
                        true, now
                ),

                new LearningResource(
                        "real-python",
                        "Real Python — Tutorials & Articles",
                        "https://realpython.com/",
                        "High-quality Python tutorials covering web development, data science, "
                                + "automation, testing, and best practices. Mix of free and premium content.",
                        ResourceType.TUTORIAL,
                        List.of(ResourceCategory.PYTHON, ResourceCategory.WEB),
                        List.of("python", "django", "flask", "data-science", "automation"),
                        "Real Python",
                        "intermediate",
                        true, now
                ),

                // ─── ALGORITHMS & DATA STRUCTURES ───────────────────────────

                new LearningResource(
                        "algorithms-visualgo",
                        "VisuAlgo — Visualizing Algorithms",
                        "https://visualgo.net/en",
                        "Interactive visualizations of sorting, searching, graph, and tree "
                                + "algorithms. See each step animate in real-time. Excellent for "
                                + "building intuition about algorithmic behavior.",
                        ResourceType.INTERACTIVE,
                        List.of(ResourceCategory.ALGORITHMS),
                        List.of("visualization", "sorting", "graphs", "trees", "interactive"),
                        "Steven Halim",
                        "beginner",
                        true, now
                ),

                new LearningResource(
                        "big-o-cheatsheet",
                        "Big-O Cheat Sheet",
                        "https://www.bigocheatsheet.com/",
                        "Quick reference for time and space complexity of common "
                                + "data structures and algorithms. Covers arrays, linked lists, "
                                + "trees, graphs, sorting, and searching.",
                        ResourceType.CHEAT_SHEET,
                        List.of(ResourceCategory.ALGORITHMS),
                        List.of("big-o", "complexity", "cheatsheet", "reference"),
                        "Eric Rowell",
                        "beginner",
                        true, now
                ),

                // ─── SOFTWARE ENGINEERING & DESIGN ──────────────────────────

                new LearningResource(
                        "refactoring-guru",
                        "Refactoring.Guru — Design Patterns & Refactoring",
                        "https://refactoring.guru/",
                        "Visual catalog of 22 GoF design patterns and refactoring techniques. "
                                + "Each pattern includes UML diagrams, real-world analogies, "
                                + "code examples in multiple languages, and when to use/avoid.",
                        ResourceType.TUTORIAL,
                        List.of(ResourceCategory.SOFTWARE_ENGINEERING, ResourceCategory.JAVA),
                        List.of("design-patterns", "refactoring", "solid", "oop", "gof"),
                        "Refactoring.Guru",
                        "intermediate",
                        true, now
                ),

                new LearningResource(
                        "clean-code-summary",
                        "Clean Code (Robert C. Martin) — Summary & Key Takeaways",
                        "https://gist.github.com/wojteklu/73c6914cc446146b8b533c0988cf8d29",
                        "Community-curated summary of Clean Code principles: meaningful names, "
                                + "small functions, single responsibility, DRY, error handling, "
                                + "and writing code that reads like prose.",
                        ResourceType.ARTICLE,
                        List.of(ResourceCategory.SOFTWARE_ENGINEERING),
                        List.of("clean-code", "best-practices", "naming", "solid", "dry"),
                        "Robert C. Martin (summary by community)",
                        "beginner",
                        true, now
                ),

                new LearningResource(
                        "twelve-factor-app",
                        "The Twelve-Factor App",
                        "https://12factor.net/",
                        "Methodology for building modern, scalable, maintainable SaaS applications. "
                                + "Covers config, dependencies, backing services, build/release/run, "
                                + "concurrency, and disposability.",
                        ResourceType.ARTICLE,
                        List.of(ResourceCategory.SOFTWARE_ENGINEERING, ResourceCategory.DEVOPS, ResourceCategory.CLOUD),
                        List.of("12-factor", "saas", "cloud-native", "methodology"),
                        "Adam Wiggins / Heroku",
                        "intermediate",
                        true, now
                ),

                new LearningResource(
                        "system-design-primer",
                        "The System Design Primer (GitHub)",
                        "https://github.com/donnemartin/system-design-primer",
                        "Comprehensive guide to system design interview preparation. Covers "
                                + "scalability, load balancing, caching, databases, microservices, "
                                + "message queues, and real-world architecture examples.",
                        ResourceType.REPOSITORY,
                        List.of(ResourceCategory.SOFTWARE_ENGINEERING, ResourceCategory.SYSTEMS),
                        List.of("system-design", "scalability", "architecture", "interview"),
                        "Donne Martin",
                        "advanced",
                        true, now
                ),

                // ─── DEVOPS & CLOUD ─────────────────────────────────────────

                new LearningResource(
                        "docker-docs-get-started",
                        "Docker — Getting Started Guide",
                        "https://docs.docker.com/get-started/",
                        "Official Docker tutorial: containers, images, Dockerfiles, "
                                + "volumes, networks, and Docker Compose. Start here for "
                                + "containerization fundamentals.",
                        ResourceType.TUTORIAL,
                        List.of(ResourceCategory.DEVOPS, ResourceCategory.CLOUD),
                        List.of("docker", "containers", "dockerfile", "compose"),
                        "Docker Inc.",
                        "beginner",
                        true, now
                ),

                new LearningResource(
                        "kubernetes-docs",
                        "Kubernetes Documentation",
                        "https://kubernetes.io/docs/home/",
                        "Official Kubernetes documentation — concepts, tutorials, tasks, "
                                + "and reference. Covers pods, services, deployments, config maps, "
                                + "networking, and cluster administration.",
                        ResourceType.DOCUMENTATION,
                        List.of(ResourceCategory.DEVOPS, ResourceCategory.CLOUD),
                        List.of("kubernetes", "k8s", "orchestration", "pods", "services"),
                        "CNCF / Kubernetes",
                        "intermediate",
                        true, now
                ),

                // ─── GIT & TOOLS ────────────────────────────────────────────

                new LearningResource(
                        "pro-git-book",
                        "Pro Git (2nd Edition) — Free Online Book",
                        "https://git-scm.com/book/en/v2",
                        "The complete Git book, freely available online. Covers basics, "
                                + "branching, merging, rebasing, distributed workflows, internals, "
                                + "and advanced topics like reflog and bisect.",
                        ResourceType.BOOK,
                        List.of(ResourceCategory.TOOLS),
                        List.of("git", "version-control", "branching", "merging"),
                        "Scott Chacon & Ben Straub",
                        "beginner",
                        true, now
                ),

                new LearningResource(
                        "github-skills",
                        "GitHub Skills — Interactive Courses",
                        "https://skills.github.com/",
                        "Hands-on GitHub courses: intro to GitHub, GitHub Pages, "
                                + "GitHub Actions, pull requests, code review, and security. "
                                + "Learn by doing in real repositories.",
                        ResourceType.INTERACTIVE,
                        List.of(ResourceCategory.TOOLS, ResourceCategory.DEVOPS),
                        List.of("github", "github-actions", "ci-cd", "pull-requests"),
                        "GitHub",
                        "beginner",
                        true, now
                ),

                // ─── DATABASES ──────────────────────────────────────────────

                new LearningResource(
                        "use-the-index-luke",
                        "Use The Index, Luke — SQL Indexing & Tuning",
                        "https://use-the-index-luke.com/",
                        "Free web book explaining SQL indexing in depth. Covers B-tree indexes, "
                                + "query execution plans, join optimization, sorting, and partial indexes "
                                + "across PostgreSQL, MySQL, Oracle, and SQL Server.",
                        ResourceType.BOOK,
                        List.of(ResourceCategory.DATABASE),
                        List.of("sql", "indexing", "performance", "postgresql", "mysql"),
                        "Markus Winand",
                        "intermediate",
                        true, now
                ),

                // ─── SECURITY ───────────────────────────────────────────────

                new LearningResource(
                        "owasp-top-ten",
                        "OWASP Top 10 — Web Application Security Risks",
                        "https://owasp.org/www-project-top-ten/",
                        "The definitive list of the most critical web application security risks. "
                                + "Covers injection, broken auth, XSS, insecure deserialization, "
                                + "and security misconfiguration with prevention guides.",
                        ResourceType.DOCUMENTATION,
                        List.of(ResourceCategory.SECURITY, ResourceCategory.WEB),
                        List.of("owasp", "security", "xss", "injection", "authentication"),
                        "OWASP Foundation",
                        "intermediate",
                        true, now
                ),

                // ─── AI / ML ────────────────────────────────────────────────

                new LearningResource(
                        "fast-ai",
                        "fast.ai — Practical Deep Learning",
                        "https://course.fast.ai/",
                        "Free course: Practical Deep Learning for Coders. Top-down approach — "
                                + "start building models immediately, then understand the theory. "
                                + "Uses PyTorch and the fastai library.",
                        ResourceType.TUTORIAL,
                        List.of(ResourceCategory.AI_ML, ResourceCategory.PYTHON),
                        List.of("deep-learning", "pytorch", "neural-networks", "course"),
                        "Jeremy Howard / fast.ai",
                        "intermediate",
                        true, now
                ),

                new LearningResource(
                        "prompt-engineering-guide",
                        "Prompt Engineering Guide",
                        "https://www.promptingguide.ai/",
                        "Comprehensive guide to prompt engineering for LLMs. Covers techniques "
                                + "like chain-of-thought, few-shot, zero-shot, retrieval-augmented "
                                + "generation, and tool use.",
                        ResourceType.TUTORIAL,
                        List.of(ResourceCategory.AI_ML),
                        List.of("prompt-engineering", "llm", "gpt", "rag", "techniques"),
                        "DAIR.AI",
                        "intermediate",
                        true, now
                ),

                // ─── TESTING ────────────────────────────────────────────────

                new LearningResource(
                        "testing-trophy",
                        "The Testing Trophy — Kent C. Dodds",
                        "https://kentcdodds.com/blog/the-testing-trophy-and-testing-classifications",
                        "Influential article on test strategy: static analysis, unit tests, "
                                + "integration tests, and end-to-end tests. The 'testing trophy' "
                                + "emphasizes integration tests over unit tests for confidence.",
                        ResourceType.ARTICLE,
                        List.of(ResourceCategory.TESTING, ResourceCategory.JAVASCRIPT),
                        List.of("testing-strategy", "integration-tests", "unit-tests", "e2e"),
                        "Kent C. Dodds",
                        "intermediate",
                        true, now
                ),

                // ─── GENERAL / CAREER ───────────────────────────────────────

                new LearningResource(
                        "roadmap-sh",
                        "roadmap.sh — Developer Roadmaps",
                        "https://roadmap.sh/",
                        "Interactive roadmaps for various tech roles: frontend, backend, "
                                + "DevOps, full-stack, Android, DBA, QA, and more. Each roadmap "
                                + "shows skills to learn with links to resources.",
                        ResourceType.INTERACTIVE,
                        List.of(ResourceCategory.GENERAL),
                        List.of("roadmap", "career", "learning-path", "skills"),
                        "Kamran Ahmed",
                        "beginner",
                        true, now
                ),

                new LearningResource(
                        "free-programming-books",
                        "Free Programming Books (GitHub)",
                        "https://github.com/EbookFoundation/free-programming-books",
                        "Massive curated list of free programming books, courses, podcasts, "
                                + "and interactive tutorials. Covers every language and topic "
                                + "imaginable. Community-maintained with 300k+ stars.",
                        ResourceType.REPOSITORY,
                        List.of(ResourceCategory.GENERAL),
                        List.of("free", "books", "curated-list", "courses"),
                        "Ebook Foundation",
                        "beginner",
                        true, now
                )
        ));
    }
}
