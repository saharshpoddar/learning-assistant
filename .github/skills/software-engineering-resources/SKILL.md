---
name: software-engineering-resources
description: >
  Comprehensive curated index of software engineering learning resources — not restricted to any language.
  Covers OOP, OS, networking, protocols (TCP/UDP/HTTP/gRPC/REST/RPC), DSA (data structures, algorithms, patterns, interview prep),
  system design (HLD/LLD), DBMS, multithreading/concurrency, testing (TDD/BDD/ATDD, unit/integration/e2e),
  SDLC methodologies (Agile/Scrum/Kanban/XP), design patterns, SOLID, clean code, refactoring, architecture,
  and general computer science fundamentals.
  Use when asked about learning resources, book recommendations, tutorials, documentation, interview preparation,
  CS fundamentals, engineering concepts, development methodologies, or when building a study plan for any SE topic.
---

# Software Engineering Learning Resources — Comprehensive Index

## Foundational Books (The Canon)

### Clean Code & Design
| Book | Author | Core Topics |
|---|---|---|
| **Clean Code** | Robert C. Martin | Naming, functions, comments, formatting, error handling, unit tests |
| **The Clean Coder** | Robert C. Martin | Professionalism, time management, estimation, TDD discipline |
| **Clean Architecture** | Robert C. Martin | Component principles, architecture patterns, boundaries, frameworks |
| **Refactoring (2nd Ed.)** | Martin Fowler | Systematic code improvement, catalog of refactorings, code smells |
| **Design Patterns (GoF)** | Gamma, Helm, Johnson, Vlissides | 23 classic OOP patterns: creational, structural, behavioral |
| **Head First Design Patterns** | Freeman & Robson | Visual, beginner-friendly design patterns with analogies |
| **Patterns of Enterprise Application Architecture** | Martin Fowler | Domain model, data mapper, unit of work, repository, service layer |
| **Working Effectively with Legacy Code** | Michael Feathers | Seams, characterization tests, breaking dependencies safely |

### Software Engineering Practices
| Book | Author | Core Topics |
|---|---|---|
| **The Pragmatic Programmer** | Hunt & Thomas | Tips, DRY, orthogonality, tracer bullets, pragmatic thinking |
| **Code Complete (2nd Ed.)** | Steve McConnell | Construction practices, variables, control flow, code quality |
| **A Philosophy of Software Design** | John Ousterhout | Complexity, deep vs shallow modules, information hiding |
| **Domain-Driven Design** | Eric Evans | Ubiquitous language, bounded contexts, aggregates, value objects |
| **Implementing Domain-Driven Design** | Vaughn Vernon | Practical DDD with real code examples |
| **Release It! (2nd Ed.)** | Michael Nygard | Stability patterns, circuit breakers, bulkheads, production readiness |

### Testing
| Book | Author | Core Topics |
|---|---|---|
| **Test Driven Development: By Example** | Kent Beck | Red-green-refactor, TDD rhythms, money example |
| **Growing Object-Oriented Software, Guided by Tests** | Freeman & Pryce | Outside-in TDD, mocking, test doubles |
| **xUnit Test Patterns** | Gerard Meszaros | Test smells, fixtures, result verification, test organization |
| **The Art of Unit Testing (3rd Ed.)** | Roy Osherove | Isolation frameworks, trustworthy tests, maintainability |
| **BDD in Action** | John Ferguson Smart | Behavior-driven development, Gherkin, living documentation |

### Algorithms & Data Structures
| Book | Author | Core Topics |
|---|---|---|
| **Introduction to Algorithms (CLRS)** | Cormen, Leiserson, Rivest, Stein | Comprehensive: sorting, graphs, DP, greedy, NP |
| **Algorithm Design Manual** | Steven Skiena | Practical algorithm design, war stories, catalog |
| **Grokking Algorithms** | Aditya Bhargava | Visual, beginner-friendly: BFS, DP, greedy, hash tables |
| **Cracking the Coding Interview** | Gayle Laakmann McDowell | Interview patterns, 189 problems, data structures review |
| **Elements of Programming Interviews** | Aziz, Lee, Prakash | Advanced interview problems with detailed solutions |

### System Design
| Book | Author | Core Topics |
|---|---|---|
| **Designing Data-Intensive Applications (DDIA)** | Martin Kleppmann | Storage, replication, partitioning, consistency, stream processing |
| **System Design Interview (Vol 1 & 2)** | Alex Xu | Step-by-step system designs, scale estimation, trade-offs |
| **Building Microservices (2nd Ed.)** | Sam Newman | Service decomposition, communication, deployment, testing |
| **Software Architecture: The Hard Parts** | Ford, Richards, Sadalage, Dehghani | Architecture decisions, trade-off analysis, modularity |

### Operating Systems & Networking
| Book | Author | Core Topics |
|---|---|---|
| **Operating System Concepts (Silberschatz)** | Silberschatz, Galvin, Gagne | Processes, threads, scheduling, memory, file systems |
| **Modern Operating Systems** | Andrew Tanenbaum | Process management, deadlocks, I/O, virtualization |
| **Computer Networking: A Top-Down Approach** | Kurose & Ross | Application → transport → network → link layers |
| **TCP/IP Illustrated (Vol 1)** | W. Richard Stevens | TCP, UDP, IP, ICMP, routing — packet-level detail |
| **High Performance Browser Networking** | Ilya Grigorik | HTTP/2, WebSocket, TLS, TCP optimization (free online) |

### Databases
| Book | Author | Core Topics |
|---|---|---|
| **Database Internals** | Alex Petrov | Storage engines, B-trees, LSM, distributed databases |
| **SQL Performance Explained** | Markus Winand | Indexes, execution plans, joins, query optimization |
| **Designing Data-Intensive Applications** | Martin Kleppmann | (also covers distributed data, consistency, transactions) |

### Concurrency & Multithreading
| Book | Author | Core Topics |
|---|---|---|
| **Java Concurrency in Practice** | Brian Goetz | Thread safety, synchronization, concurrent collections, executors |
| **The Art of Multiprocessor Programming** | Herlihy & Shavit | Lock-free algorithms, concurrent data structures, linearizability |
| **Seven Concurrency Models in Seven Weeks** | Paul Butcher | Threads, STM, actors, CSP, data parallelism, GPU, lambda architecture |

---

## Online Resources by Domain

### Object-Oriented Programming (OOP)
| Resource | URL | Best For |
|---|---|---|
| **Refactoring.Guru — OOP Basics** | `https://refactoring.guru/oop-basics` | Visual OOP concepts |
| **Refactoring.Guru — Design Patterns** | `https://refactoring.guru/design-patterns` | Interactive pattern catalog |
| **SOLID Principles (Baeldung)** | `https://www.baeldung.com/solid-principles` | Practical SOLID with Java |
| **SourceMaking** | `https://sourcemaking.com/` | Patterns, anti-patterns, refactoring |
| **Wikipedia — OOP** | `https://en.wikipedia.org/wiki/Object-oriented_programming` | Conceptual overview, history |

### Data Structures & Algorithms (DSA)

#### Online Resources
| Resource | URL | Best For |
|---|---|---|
| **VisuAlgo** | `https://visualgo.net/` | Animated algorithm visualizations |
| **Big-O Cheat Sheet** | `https://www.bigocheatsheet.com/` | Time/space complexity reference |
| **NeetCode** | `https://neetcode.io/` | Curated LeetCode roadmap, pattern-based |
| **LeetCode** | `https://leetcode.com/` | Coding problems, company tags |
| **HackerRank** | `https://www.hackerrank.com/` | Skill-based challenges |
| **GeeksforGeeks DSA** | `https://www.geeksforgeeks.org/data-structures/` | In-depth articles per structure |
| **Blind 75** | `https://neetcode.io/practice` | Core 75 problems covering all patterns |
| **AlgoExpert** | `https://www.algoexpert.io/` | Video explanations + coding |
| **CP-Algorithms** | `https://cp-algorithms.com/` | Competitive programming algorithms |
| **MIT OpenCourseWare 6.006** | `https://ocw.mit.edu/courses/6-006-introduction-to-algorithms-spring-2020/` | Academic algorithms course (free videos + notes) |
| **Stanford Algorithms (Coursera)** | `https://www.coursera.org/specializations/algorithms` | Tim Roughgarden's algorithms specialization |

#### Data Structures — Time & Space Complexity

##### Core Data Structures at a Glance
| Data Structure | Access | Search | Insert | Delete | Space | Notes |
|---|---|---|---|---|---|---|
| **Array** | O(1) | O(n) | O(n) | O(n) | O(n) | Contiguous memory; O(1) append if not full |
| **Dynamic Array** (ArrayList) | O(1) | O(n) | O(1)* | O(n) | O(n) | *Amortized O(1) append; O(n) on resize |
| **Singly Linked List** | O(n) | O(n) | O(1)† | O(1)† | O(n) | †If at known position; O(n) to find |
| **Doubly Linked List** | O(n) | O(n) | O(1)† | O(1)† | O(n) | Bidirectional traversal |
| **Stack** | O(n) | O(n) | O(1) | O(1) | O(n) | LIFO — push/pop at top |
| **Queue** | O(n) | O(n) | O(1) | O(1) | O(n) | FIFO — enqueue/dequeue |
| **Deque** | O(n) | O(n) | O(1) | O(1) | O(n) | Insert/remove both ends |
| **HashMap / HashSet** | — | O(1)* | O(1)* | O(1)* | O(n) | *Average; O(n) worst case (collisions) |
| **TreeMap / TreeSet (BST)** | O(log n) | O(log n) | O(log n) | O(log n) | O(n) | Sorted order guaranteed |
| **AVL Tree** | O(log n) | O(log n) | O(log n) | O(log n) | O(n) | Strictly balanced (height diff ≤ 1) |
| **Red-Black Tree** | O(log n) | O(log n) | O(log n) | O(log n) | O(n) | Relaxed balance; used in Java TreeMap |
| **B-Tree / B+ Tree** | O(log n) | O(log n) | O(log n) | O(log n) | O(n) | Disk-optimized; used in databases |
| **Min/Max Heap** | O(1)‡ | O(n) | O(log n) | O(log n) | O(n) | ‡O(1) for min/max; O(log n) extract |
| **Trie** | O(m) | O(m) | O(m) | O(m) | O(Σ·m) | m = key length; Σ = alphabet size |
| **Segment Tree** | O(log n) | O(log n) | O(log n) | — | O(n) | Range queries + point updates |
| **Fenwick Tree (BIT)** | O(log n) | O(log n) | O(log n) | — | O(n) | Prefix sums; less memory than seg tree |
| **Union-Find (DSU)** | — | O(α(n))* | O(α(n))* | — | O(n) | *Near O(1) with path compression + rank |
| **Bloom Filter** | — | O(k) | O(k) | — | O(m) | Probabilistic; false positives possible |
| **Skip List** | O(log n) | O(log n) | O(log n) | O(log n) | O(n log n) | Probabilistic balanced list; used in Redis |

##### Hash Collision Strategies
| Strategy | How It Works | Avg Lookup | Worst Lookup | Used In |
|---|---|---|---|---|
| **Separate Chaining** | Each bucket is a linked list (or tree at threshold) | O(1) | O(n) | Java HashMap (list → tree at 8) |
| **Open Addressing — Linear Probing** | Try next slot linearly | O(1) | O(n) | Python dict (variant), Go map |
| **Open Addressing — Quadratic Probing** | Try i², skip quadratically | O(1) | O(n) | Some C++ implementations |
| **Open Addressing — Double Hashing** | Use second hash for step size | O(1) | O(n) | Academic, some production systems |
| **Robin Hood Hashing** | Steal from rich (low displacement) | O(1) | O(log n) | Rust HashMap |
| **Cuckoo Hashing** | Two hash functions, displacing on collision | O(1) | O(1) amortized | Network hardware, MemC3 |

**Industry note:** Java's `HashMap` switches from linked list to red-black tree per bucket when a bucket reaches 8 entries (treeify threshold), giving O(log n) worst-case per-bucket lookup instead of O(n).

##### Trees — Deep Dive with Industry Use
| Tree Type | Balance | Typical Use | Industry Examples |
|---|---|---|---|
| **BST** | None (can degrade to O(n)) | Teaching, simple ordered data | Rarely used raw in production |
| **AVL Tree** | Strict (height ≤ 1 diff) | Read-heavy, in-memory lookups | C++ std::map (some impls) |
| **Red-Black Tree** | Relaxed (2x height guarantee) | General-purpose balanced map | Java TreeMap/TreeSet, Linux CFS scheduler, C++ std::map |
| **B-Tree** | M-way, minimizes disk I/O | Database indexes, file systems | PostgreSQL, MySQL InnoDB, NTFS, ext4 |
| **B+ Tree** | Leaf-linked B-Tree variant | Range scans on disk | Almost all RDBMS indexes, SQLite |
| **Trie** | N/A (prefix structure) | Autocomplete, spell check, IP routing | Google search suggestions, DNS lookups |
| **Segment Tree** | Perfect binary tree | Range queries (min/max/sum) | Competitive programming, interval scheduling |
| **Merkle Tree** | Hash tree | Data integrity verification | Git, blockchain, Cassandra anti-entropy |
| **LSM Tree** | Log-Structured Merge | Write-heavy workloads | Cassandra, RocksDB, LevelDB, HBase |
| **R-Tree** | Spatial indexing | Geospatial queries | PostGIS, MongoDB geospatial, Google Maps |

##### Heaps — Applications & Patterns
| Application | Data Structure | Complexity | Industry Use |
|---|---|---|---|
| **Top-K elements** | Min-heap of size K | O(n log K) | Twitter trending topics, search ranking |
| **K-th largest/smallest** | Min/max heap or quickselect | O(n) avg with quickselect | Analytics dashboards |
| **Running median** | Two heaps (max + min) | O(log n) per element | Real-time sensor data, streaming analytics |
| **Priority scheduling** | Min-heap (by priority/deadline) | O(log n) insert/extract | OS process scheduler, K8s pod scheduling |
| **Merge K sorted lists** | Min-heap of size K | O(N log K) | Database merge sort, multi-way external sort |
| **Dijkstra's algorithm** | Min-heap (priority queue) | O((V+E) log V) | Google Maps, network routing protocols |
| **Event-driven simulation** | Min-heap (by time) | O(log n) | Discrete event simulation, game loops |
| **Huffman coding** | Min-heap (by frequency) | O(n log n) | ZIP compression, JPEG, HTTP/2 HPACK |

#### Algorithms — Time & Space Complexity

##### Sorting Algorithms
| Algorithm | Best | Average | Worst | Space | Stable? | In-Place? | Industry Notes |
|---|---|---|---|---|---|---|---|
| **Bubble Sort** | O(n) | O(n²) | O(n²) | O(1) | ✅ | ✅ | Teaching only; never in production |
| **Selection Sort** | O(n²) | O(n²) | O(n²) | O(1) | ❌ | ✅ | Minimizes swaps; embedded systems with limited writes |
| **Insertion Sort** | O(n) | O(n²) | O(n²) | O(1) | ✅ | ✅ | Fast for small/nearly-sorted; used as base in Timsort |
| **Merge Sort** | O(n log n) | O(n log n) | O(n log n) | O(n) | ✅ | ❌ | Java `Arrays.sort()` for objects; external sort for large files |
| **Quick Sort** | O(n log n) | O(n log n) | O(n²) | O(log n) | ❌ | ✅ | C `qsort`, most general-purpose sorts; median-of-3 avoids worst case |
| **Heap Sort** | O(n log n) | O(n log n) | O(n log n) | O(1) | ❌ | ✅ | Linux kernel sort; guaranteed O(n log n) in-place |
| **Tim Sort** | O(n) | O(n log n) | O(n log n) | O(n) | ✅ | ❌ | Python `sorted()`, Java `Arrays.sort()`, Rust `sort()` — hybrid merge/insertion |
| **Counting Sort** | O(n+k) | O(n+k) | O(n+k) | O(k) | ✅ | ❌ | k = range; useful for small range integers (e.g., sorting exam scores) |
| **Radix Sort** | O(d·(n+k)) | O(d·(n+k)) | O(d·(n+k)) | O(n+k) | ✅ | ❌ | d = digits; sorting strings, IP addresses, fixed-length keys |
| **Bucket Sort** | O(n+k) | O(n+k) | O(n²) | O(n) | ✅ | ❌ | Uniformly distributed floats; MapReduce partitioning |
| **Intro Sort** | O(n log n) | O(n log n) | O(n log n) | O(log n) | ❌ | ✅ | C++ `std::sort` — hybrid quick/heap/insertion |

**Interview tip:** Know that `Arrays.sort()` in Java uses dual-pivot Quicksort for primitives and TimSort for objects. Python uses TimSort. C++ `std::sort` uses IntroSort. Knowing **why** each language chose its default matters in interviews.

##### Searching Algorithms
| Algorithm | Best | Average | Worst | Space | Prerequisite | Industry Use |
|---|---|---|---|---|---|---|
| **Linear Search** | O(1) | O(n) | O(n) | O(1) | None | Small unsorted data, fallback |
| **Binary Search** | O(1) | O(log n) | O(log n) | O(1) | Sorted data | Database index lookup, `Arrays.binarySearch()` |
| **Interpolation Search** | O(1) | O(log log n) | O(n) | O(1) | Sorted, uniform distribution | Phone book lookup, in-memory sorted arrays |
| **Exponential Search** | O(1) | O(log n) | O(log n) | O(1) | Sorted, unbounded | Infinite/streaming sorted data |
| **Hash Lookup** | O(1) | O(1) | O(n) | O(n) | Hash table | `HashMap.get()`, DNS cache, symbol tables |
| **Ternary Search** | O(1) | O(log n) | O(log n) | O(1) | Unimodal function | Function optimization, ML hyperparameter tuning |

**Binary search variants used in industry:**
- **Lower bound / Upper bound:** Database range queries, Java `Collections.binarySearch()`
- **Search on answer space:** Rate limiter tuning, capacity planning, "minimum maximum" problems
- **Rotated sorted array:** Time-series data wrap, circular buffer search

##### Graph Algorithms
| Algorithm | Time | Space | Graph Type | Industry Use |
|---|---|---|---|---|
| **BFS** | O(V+E) | O(V) | Unweighted | Social network "degrees of separation", web crawlers, shortest path |
| **DFS** | O(V+E) | O(V) | Any | Cycle detection, topological sort, maze solving, garbage collection (mark phase) |
| **Dijkstra** | O((V+E) log V) | O(V) | Non-negative weights | Google Maps, network routing (OSPF), GPS navigation |
| **Bellman-Ford** | O(V·E) | O(V) | Any (neg. weights OK) | Arbitrage detection (negative cycles), distance-vector routing (RIP) |
| **Floyd-Warshall** | O(V³) | O(V²) | All-pairs | Small networks, transitive closure, network distance matrices |
| **A*** | O(E) | O(V) | Weighted + heuristic | Game pathfinding, robotics, GPS with traffic |
| **Kruskal** | O(E log E) | O(V) | Undirected, weighted | Network design (min-cost wiring), cluster analysis |
| **Prim** | O((V+E) log V) | O(V) | Undirected, weighted | Dense network MST, image segmentation |
| **Topological Sort** | O(V+E) | O(V) | DAG only | Build systems (Make, Gradle), course prereqs, task scheduling |
| **Tarjan (SCC)** | O(V+E) | O(V) | Directed | Compiler optimization, deadlock detection, social network analysis |
| **Kahn's Algorithm** | O(V+E) | O(V) | DAG (BFS topo sort) | Package managers (npm), CI/CD pipeline ordering |
| **Johnson's** | O(V²·log V + V·E) | O(V²) | Sparse, neg. weights | Sparse graph all-pairs shortest path |

**Interview tip:** Always state the graph representation you're using. Adjacency list gives O(V+E) for BFS/DFS; adjacency matrix gives O(V²). Real systems almost always use adjacency lists (or edge lists for sparse graphs).

##### Dynamic Programming Patterns
| Pattern | Example Problems | Key Insight | Time | Space (optimized) |
|---|---|---|---|---|
| **1D DP** | Climbing Stairs, House Robber, Coin Change | Current state depends on previous 1-2 states | O(n) | O(1) with rolling variables |
| **2D DP** | Longest Common Subsequence, Edit Distance, Grid Paths | Two dimensions of subproblems | O(m·n) | O(min(m,n)) with rolling array |
| **0/1 Knapsack** | Knapsack, Target Sum, Partition Equal Subset | Include or exclude each item | O(n·W) | O(W) with 1D array |
| **Unbounded Knapsack** | Coin Change, Rod Cutting | Items can be reused | O(n·W) | O(W) |
| **Interval DP** | Matrix Chain, Burst Balloons, Palindrome Partitioning | Merge intervals, try all split points | O(n³) | O(n²) |
| **DP on Trees** | Diameter, Max Path Sum, House Robber III | Post-order DFS, return state from children | O(n) | O(h) stack depth |
| **Bitmask DP** | TSP, Assign Tasks, Subset covering | State = bitmask of visited/used elements | O(2ⁿ·n²) | O(2ⁿ·n) |
| **DP on Strings** | Regex Matching, Wildcard, Distinct Subsequences | Character-by-character comparison | O(m·n) | O(n) with rolling |
| **State Machine DP** | Buy/Sell Stock (with cooldown/fee/k txns) | States = holding, not holding, cooldown | O(n·k) | O(k) |
| **Digit DP** | Count numbers with property in range [L, R] | Digit by digit with tight/loose bound | O(d·states) | O(d·states) |

**Optimization techniques:**
- **Memoization → Tabulation:** Memoization = top-down recursion + cache; tabulation = bottom-up iteration. Tabulation avoids stack overflow for large inputs.
- **Rolling array:** If DP[i] depends only on DP[i-1], use 2 rows (or variables) instead of full table. Reduces O(m·n) space to O(n).
- **State reduction:** Identify which states are actually needed. Example: Stock Buy/Sell — only 2-3 states instead of 2D table.

#### Real-World Industry Applications

##### Where Each Data Structure Lives in Production
| Data Structure | Industry System | How It's Used |
|---|---|---|
| **Array / Buffer** | Kafka, network stack | Ring buffers for message partitions, TCP receive buffers |
| **HashMap** | Redis, Memcached, every web server | Cache, session store, request routing table |
| **B+ Tree** | PostgreSQL, MySQL, SQLite | All index lookups, range scans |
| **LSM Tree** | Cassandra, RocksDB, LevelDB | Write-optimized storage (append → merge) |
| **Skip List** | Redis sorted set, LevelDB memtable | Concurrent ordered data without complex tree rotations |
| **Trie** | DNS resolution, autocomplete | IP routing tables, T9 keyboard, search suggest |
| **Bloom Filter** | Cassandra, Chrome safe browsing, Spark | Quickly check "definitely not in set" before expensive lookup |
| **Priority Queue (Heap)** | OS scheduler, Kubernetes, Netty | Thread scheduling, pod priority, event loop |
| **Graph (adjacency list)** | Social networks, Google Maps, package managers | Friend recommendations, routing, dependency resolution |
| **Merkle Tree** | Git, blockchain, Cassandra | Data integrity verification, efficient sync |
| **HyperLogLog** | Redis, Google Analytics | Approximate distinct count (cardinality estimation) |
| **Count-Min Sketch** | Network monitoring, ad-tech | Approximate frequency counting for streaming data |
| **Segment Tree / BIT** | Analytics engines, game engines | Range aggregation queries, real-time leaderboards |
| **Union-Find** | Network connectivity, image processing | Connected components, Kruskal's MST, percolation |
| **Consistent Hashing** | DynamoDB, Cassandra, CDN routing | Distributed shard assignment with minimal remapping |

##### Where Each Algorithm Lives in Production
| Algorithm | Industry System | How It's Used |
|---|---|---|
| **Binary Search** | Database query optimizer, git bisect | Index lookup, finding bug-introducing commit |
| **BFS** | LinkedIn, Facebook | "People you may know", shortest connection path |
| **DFS** | Garbage collectors, web crawlers | Mark-and-sweep GC, site indexing |
| **Dijkstra / A*** | Google Maps, Uber, game engines | Shortest path with real-time traffic, NPC pathfinding |
| **Topological Sort** | Webpack, Gradle, Make, Airflow | Build dependency order, DAG task scheduling |
| **Dynamic Programming** | Spell checkers, bioinformatics | Edit distance for "did you mean?", DNA sequence alignment |
| **Merge Sort** | External sort in databases | Sorting data larger than memory (sort-merge join) |
| **Quick Select** | Database Top-K queries | Finding k-th smallest without full sort |
| **Consistent Hashing** | Load balancers, distributed caches | Sharding with minimal redistribution on node changes |
| **PageRank (graph)** | Google Search, citation analysis | Web page importance ranking |
| **MapReduce (graph + sort)** | Hadoop, Spark | Distributed data processing at scale |
| **Huffman Coding (greedy)** | ZIP, GZIP, JPEG, HTTP/2 HPACK | Data compression |
| **Rabin-Karp (rolling hash)** | Plagiarism detection, git diff | Efficient pattern matching in text |
| **Aho-Corasick** | Intrusion detection, content filtering | Multi-pattern string matching |

#### Optimization Techniques & Patterns

##### Time Complexity Optimization Strategies
| Strategy | Before | After | Example |
|---|---|---|---|
| **Sorting first** | O(n²) brute force pair search | O(n log n) sort + O(n) two pointers | Two Sum (sorted variant) |
| **Hash table** | O(n²) nested loop | O(n) hash lookup | Two Sum, anagram grouping |
| **Binary search** | O(n) linear scan | O(log n) on sorted/monotonic data | Search in sorted array, capacity planning |
| **Prefix sum** | O(n) per query, O(q·n) total | O(1) per query after O(n) preprocess | Subarray sum queries |
| **Sliding window** | O(n·k) recompute window | O(n) maintain running window | Max sum subarray of size k |
| **Two pointers** | O(n²) all pairs | O(n) converging pointers | Container with most water, 3Sum |
| **Monotonic stack** | O(n²) check all previous | O(n) amortized | Next greater element, largest rectangle |
| **Bit manipulation** | O(n) extra space | O(1) space | Find single number (XOR), power of 2 |
| **Divide & conquer** | O(n²) brute force | O(n log n) | Merge sort, closest pair of points |
| **Memoization/DP** | O(2ⁿ) recursive | O(n·k) with memo table | Fibonacci, knapsack, edit distance |

##### Space Complexity Optimization Strategies
| Strategy | Before | After | Example |
|---|---|---|---|
| **In-place operations** | O(n) extra array | O(1) | Reverse array, Dutch national flag, rotate |
| **Rolling array** | O(m·n) full DP table | O(n) two rows | LCS, edit distance, grid DP |
| **Bit array / bitmask** | O(n) boolean array | O(n/8) or O(1) | Sieve of Eratosthenes, visited set |
| **Two variables** | O(n) array for DP | O(1) two vars | Fibonacci, max subarray (Kadane) |
| **Morris traversal** | O(h) stack for tree traversal | O(1) with thread pointers | Inorder tree traversal without recursion or stack |
| **Floyd's cycle detection** | O(n) visited set | O(1) two pointers | Linked list cycle detection |
| **Iterator / generator** | O(n) materialized list | O(1) per yield | Java Streams, Python generators for large datasets |

##### Common Optimization Anti-Patterns (What NOT to Do)
| Anti-Pattern | Problem | Fix |
|---|---|---|
| **Premature optimization** | Optimizing before profiling | Profile first, optimize bottlenecks |
| **String concatenation in loop** | O(n²) due to immutable strings | Use StringBuilder (Java), join (Python) |
| **Nested loops when hash works** | O(n²) when O(n) is possible | Use HashMap for lookups |
| **Sorting when not needed** | O(n log n) when O(n) suffices | Use counting/bucket for limited range |
| **Full sort for top-K** | O(n log n) to get K items | Use heap O(n log K) or quickselect O(n) avg |
| **Computing same value repeatedly** | Exponential tree of calls | Memoize / DP |
| **Using LinkedList for random access** | O(n) per access vs O(1) for ArrayList | Use ArrayList unless frequent insert/delete at middle |
| **Autoboxing in hot loop** | Integer vs int — object creation overhead | Use primitives in performance-critical code |

#### Interview Perspective — What Interviewers Look For

##### How to Approach a DSA Problem (UMPIRE Framework)
```
U — Understand the problem
    → Restate in your own words
    → Clarify edge cases (empty, single element, duplicates, negative)
    → Ask about constraints (input size, value range, sorted?)

M — Match to known patterns
    → What data structure fits? (hash, heap, tree, graph?)
    → What pattern applies? (two pointers, sliding window, DP?)
    → Have you seen a similar problem?

P — Plan
    → Describe your approach in plain English
    → Walk through a small example
    → State the time and space complexity BEFORE coding

I — Implement
    → Write clean, readable code (variable names matter!)
    → Use helper functions to keep it modular
    → Don't try to be clever — correct and readable first

R — Review
    → Trace through your code with the example
    → Check edge cases
    → Verify complexity matches your plan

E — Evaluate
    → Can you optimize? (time? space?)
    → What are the trade-offs?
    → Is there a different approach entirely?
```

##### What Each Level of DSA Mastery Looks Like
| Level | What You Know | What You Can Solve | Interview Readiness |
|---|---|---|---|
| **Beginner** | Arrays, strings, basic sorting, linear/binary search | Easy problems with brute force | Not ready — build foundations first |
| **Intermediate** | HashMap, stack/queue, BFS/DFS, basic DP (1D), two pointers, sliding window | Most Easy + some Medium problems | Ready for mid-level SDE roles |
| **Advanced** | Segment tree, trie, advanced DP (interval, bitmask), union-find, graph algorithms | Most Medium + some Hard problems | Ready for FAANG/top-tier roles |
| **Expert** | Persistent data structures, suffix arrays, advanced graph theory, math/number theory | Hard + competitive programming | Ready for L5+ / Principal / Research roles |

##### Complexity Analysis — How to Think About It
```
O(1)       → Hash lookup, array access, stack push/pop
O(log n)   → Binary search, balanced BST ops, heap insert/extract
O(n)       → Single array pass, hash table build, BFS/DFS
O(n log n) → Efficient sorting, building a balanced BST
O(n²)      → Nested loops (brute force pair comparisons)
O(n³)      → Triple nested loops, Floyd-Warshall, naive matrix multiply
O(2ⁿ)      → All subsets, recursive without memoization
O(n!)      → All permutations, brute force TSP

Rule of thumb for interview constraint → expected complexity:
  n ≤ 10       → O(n!) or O(2ⁿ) — brute force/backtracking OK
  n ≤ 20       → O(2ⁿ) — bitmask DP
  n ≤ 500      → O(n³) — DP, Floyd-Warshall
  n ≤ 10,000   → O(n²) — DP, basic nested loops
  n ≤ 1,000,000 → O(n log n) — sorting-based, binary search
  n ≤ 10⁸      → O(n) — linear scan, two pointers, sliding window
  n > 10⁸      → O(log n) or O(1) — binary search, math, hashing
```

##### DSA Patterns for Interviews (Expanded)
| # | Pattern | Signal Words in Problem | Example Problems | Optimal Complexity |
|---|---|---|---|---|
| 1 | **Two Pointers** | "sorted array", "pair", "remove duplicates" | Two Sum II, 3Sum, Container With Most Water | O(n) |
| 2 | **Sliding Window** | "contiguous subarray/substring", "window size k" | Max Sum Subarray, Longest Substring Without Repeating | O(n) |
| 3 | **Fast & Slow Pointers** | "cycle", "middle of linked list" | Linked List Cycle, Happy Number, Palindrome LL | O(n) |
| 4 | **Binary Search** | "sorted", "minimum/maximum that satisfies", "monotonic" | Search Rotated Array, Koko Eating Bananas | O(log n) |
| 5 | **BFS** | "shortest path", "level by level", "minimum steps" | Word Ladder, Rotten Oranges, 01 Matrix | O(V+E) |
| 6 | **DFS** | "all paths", "permutations", "islands", "tree traversal" | Number of Islands, Path Sum, Clone Graph | O(V+E) |
| 7 | **Backtracking** | "all combinations", "generate all", "valid arrangements" | N-Queens, Sudoku Solver, Subsets, Permutations | Varies |
| 8 | **DP** | "minimum/maximum", "count ways", "can you reach", "longest/shortest" | Longest Increasing Subsequence, Coin Change | Varies |
| 9 | **Greedy** | "most/fewest intervals", "maximum profit", "huffman" | Activity Selection, Jump Game, Meeting Rooms | O(n log n) |
| 10 | **Monotonic Stack** | "next greater/smaller", "largest rectangle", "stock span" | Daily Temperatures, Largest Rectangle in Histogram | O(n) |
| 11 | **Heap / Top-K** | "k-th largest/smallest", "top K", "merge K sorted" | Kth Largest Element, Merge K Sorted Lists | O(n log K) |
| 12 | **Topological Sort** | "ordering", "prerequisites", "dependencies", DAG | Course Schedule, Alien Dictionary | O(V+E) |
| 13 | **Union-Find** | "connected", "groups", "redundant connection" | Number of Connected Components, Redundant Connection | O(V·α(V)) |
| 14 | **Trie** | "prefix", "word dictionary", "autocomplete" | Implement Trie, Word Search II | O(m) per op |
| 15 | **Interval** | "merge intervals", "overlapping", "insert interval" | Merge Intervals, Non-Overlapping Intervals | O(n log n) |
| 16 | **Matrix** | "spiral", "rotate", "search in 2D" | Spiral Order, Rotate Image, Search 2D Matrix | Varies |
| 17 | **Bit Manipulation** | "single number", "power of 2", "count bits" | Single Number, Counting Bits | O(n) or O(1) |

#### When to Use Which Data Structure (Expanded)
| Need | Use | Why | Alternative | Trade-off |
|---|---|---|---|---|
| Fast lookup by key | HashMap / HashSet | O(1) average | TreeMap O(log n) | Hash loses ordering |
| Sorted order iteration | TreeMap / TreeSet | O(log n) ops, sorted | SortedList, Skip List | Slower than hash for lookup |
| FIFO processing | Queue / ArrayDeque | O(1) enqueue/dequeue | LinkedList | ArrayDeque is cache-friendly |
| LIFO / undo / parsing | Stack / ArrayDeque | O(1) push/pop | — | Consider monotonic stack for range problems |
| Priority-based access | PriorityQueue (heap) | O(log n) insert/extract | Balanced BST O(log n) | BST supports arbitrary delete; heap doesn't |
| Range queries | Segment Tree / BIT | O(log n) per query/update | Sqrt decomposition O(√n) | Segment tree is more flexible |
| Prefix matching | Trie | O(m) per operation | HashMap with key iteration | Trie uses more memory but is faster for prefix ops |
| Connected components | Union-Find (DSU) | Near O(1) with optimizations | BFS/DFS O(V+E) | Union-Find better for incremental connectivity |
| Approximate counting | HyperLogLog | O(1) per add, fixed memory | Exact HashSet O(n) | Trades accuracy for massive memory savings |
| Approximate membership | Bloom Filter | O(k) per query, fixed memory | HashSet O(n) | False positives possible, no false negatives |
| Weighted shortest path | Dijkstra + min-heap | O((V+E) log V) | Bellman-Ford O(V·E) | Dijkstra requires non-negative weights |
| All-pairs shortest | Floyd-Warshall | O(V³) | V × Dijkstra O(V(V+E) log V) | Floyd-Warshall simpler for dense graphs |

### System Design

#### Online Resources
| Resource | URL | Best For |
|---|---|---|
| **System Design Primer** | `https://github.com/donnemartin/system-design-primer` | Comprehensive free guide |
| **ByteByteGo** | `https://bytebytego.com/` | Visual system design explanations |
| **Grokking System Design** | `https://www.designgurus.io/course/grokking-the-system-design-interview` | Interview-focused designs |
| **High Scalability** | `http://highscalability.com/` | Real-world architecture case studies |
| **Martin Fowler's Blog** | `https://martinfowler.com/` | Architectural patterns, enterprise design |
| **InfoQ Architecture** | `https://www.infoq.com/architecture-design/` | Conference talks, architecture trends |
| **Awesome System Design** | `https://github.com/madd86/awesome-system-design` | Curated links, papers, talks |
| **System Design 101 (ByteByteGo)** | `https://github.com/ByteByteGoHq/system-design-101` | Visual cheat sheets for system design topics |
| **Educative.io System Design** | `https://www.educative.io/courses/grokking-modern-system-design-interview-for-engineers-managers` | Modern interactive system design course |

#### System Design Topics Map
```
HLD (High-Level Design):
  Load Balancing → Caching → CDN → Database Sharding →
  Message Queues → Microservices → API Gateway →
  Rate Limiting → Consistent Hashing → CAP Theorem →
  Replication → Partitioning → Consensus (Raft/Paxos)

LLD (Low-Level Design):
  Class Design → SOLID → Design Patterns → OOP Modeling →
  API Design (REST/gRPC) → Schema Design → State Machines →
  Concurrency Control → Error Handling Strategy → Logging
```

#### HLD — High-Level Design Components (Deep Dive)

##### Load Balancing
| Concept | Description | Examples |
|---|---|---|
| **L4 (Transport)** | Routes by IP/port, no payload inspection | AWS NLB, HAProxy (TCP mode) |
| **L7 (Application)** | Routes by HTTP headers, URL, cookies | Nginx, AWS ALB, Envoy |
| **Algorithms** | Round Robin, Least Connections, IP Hash, Weighted, Consistent Hashing | — |
| **Health Checks** | Active (ping), Passive (track errors) | Heartbeat, TCP check, HTTP 200 |
| **Global (DNS-based)** | Geo-routing via DNS | Route 53, Cloudflare LB |
| **Service Mesh LB** | Sidecar proxy handles LB per-service | Istio, Linkerd |

##### Caching Strategies
```
Caching Layers:
  Client Cache (browser, app)
    → CDN Cache (edge, static assets)
      → API Gateway Cache (response cache)
        → Application Cache (in-process, e.g., Guava, Caffeine)
          → Distributed Cache (Redis, Memcached)
            → Database Cache (query cache, materialized views)
```

| Strategy | Description | When to Use |
|---|---|---|
| **Cache-Aside (Lazy)** | App checks cache → miss → reads DB → writes cache | General purpose, read-heavy |
| **Write-Through** | App writes cache + DB simultaneously | Consistency-critical, slower writes |
| **Write-Behind (Back)** | App writes cache → async flush to DB | Write-heavy, eventual consistency OK |
| **Read-Through** | Cache itself fetches from DB on miss | Transparent to application |
| **Refresh-Ahead** | Pre-fetch before expiry based on access patterns | Predictable access, low latency |

| Eviction Policy | How It Works | Use Case |
|---|---|---|
| **LRU** | Evict least recently used | General purpose |
| **LFU** | Evict least frequently used | Hot-spot heavy workloads |
| **TTL** | Expire after time-to-live | Time-sensitive data |
| **FIFO** | Evict oldest entry | Simple, predictable |

##### Content Delivery Network (CDN)
| Concept | Description |
|---|---|
| **Push CDN** | Origin proactively pushes content to edge | 
| **Pull CDN** | Edge fetches from origin on first request, then caches |
| **Edge Computing** | Run logic at CDN edge (Cloudflare Workers, Lambda@Edge) |
| **Cache Invalidation** | Purge by URL, tag, or prefix; versioned filenames (`app.v2.js`) |
| **Providers** | Cloudflare, AWS CloudFront, Akamai, Fastly |

##### Database Sharding & Partitioning
| Strategy | How It Works | Pros | Cons |
|---|---|---|---|
| **Horizontal (Sharding)** | Split rows across DB instances by shard key | Scales writes, each shard smaller | Cross-shard queries hard |
| **Vertical** | Split columns into separate tables/services | Isolate hot columns | Joins across partitions |
| **Hash-Based** | Hash(key) % N → shard | Even distribution | Rebalancing on shard add/remove |
| **Range-Based** | Key ranges → shards (A-M, N-Z) | Range queries easy | Hot spots if uneven |
| **Consistent Hashing** | Hash ring with virtual nodes | Minimal redistribution on changes | Complexity |
| **Directory-Based** | Lookup table maps key → shard | Flexible | Directory is SPOF |

##### Message Queues & Event-Driven Architecture
```
Point-to-Point (Queue):
  Producer → [Queue] → Consumer (one consumer gets each message)
  Used: Task distribution, order processing
  Examples: SQS, RabbitMQ (default)

Pub/Sub (Fan-out):
  Publisher → [Topic] → Subscriber A
                      → Subscriber B
                      → Subscriber N
  Used: Notifications, event broadcasting
  Examples: SNS, Kafka topics, Redis Pub/Sub

Event Streaming:
  Producer → [Log/Partition] → Consumer Group A (parallel consumers)
                              → Consumer Group B
  Used: Real-time analytics, audit trail, replay
  Examples: Kafka, Pulsar, Kinesis
```

| Pattern | Description | Use Case |
|---|---|---|
| **CQRS** | Separate read/write models | Read-heavy with different query needs |
| **Event Sourcing** | Store events, not state; replay to reconstruct | Audit trail, temporal queries |
| **Outbox Pattern** | Write to DB + outbox table → relay to queue | Reliable event publishing |
| **Saga Pattern** | Distributed transaction via compensating events | Cross-service workflows |
| **Dead Letter Queue** | Failed messages go to DLQ for inspection | Error handling, debugging |

##### Microservices Architecture
| Concept | Description |
|---|---|
| **Service Decomposition** | Split by bounded context (DDD), not by technical layer |
| **API Gateway** | Single entry point: routing, auth, rate limiting, aggregation |
| **Service Mesh** | Sidecar proxies handle networking (mTLS, retries, LB) — Istio, Linkerd |
| **Service Discovery** | Dynamic lookup: Consul, Eureka, K8s DNS |
| **Saga vs 2PC** | Saga (eventual) for most distributed txns; 2PC (strong) for critical atomicity |
| **Sidecar Pattern** | Co-deploy helper process alongside main service (logging, proxy) |
| **Strangler Fig** | Incrementally replace monolith by routing to new services |
| **Backend for Frontend (BFF)** | Separate API layers per client type (mobile, web, IoT) |

##### Rate Limiting
| Algorithm | How It Works | Pros | Cons |
|---|---|---|---|
| **Token Bucket** | Tokens added at fixed rate, consumed per request | Allows bursts, simple | Burst size tuning |
| **Leaky Bucket** | Requests processed at fixed rate, overflow dropped | Smooth output | No burst support |
| **Fixed Window** | Count requests per time window | Simple | Burst at window edges |
| **Sliding Window Log** | Track timestamp of each request, count in window | Accurate | Memory-heavy |
| **Sliding Window Counter** | Weighted count across current + previous window | Good balance | Approximation |

##### Reliability & Resilience Patterns
| Pattern | Description | Example |
|---|---|---|
| **Circuit Breaker** | Stop calling failing service → fallback → retry after timeout | Netflix Hystrix, Resilience4j |
| **Bulkhead** | Isolate resources (thread pools, connections) per service | Separate pools per dependency |
| **Retry with Backoff** | Exponential backoff + jitter on transient failures | `retry(3, backoff=2^n + jitter)` |
| **Timeout** | Bound waiting time for external calls | HTTP client timeout, gRPC deadline |
| **Idempotency** | Same request = same result, safe to retry | Idempotency keys (Stripe API) |
| **Failover** | Active-passive or active-active redundancy | Database failover, DNS failover |
| **Graceful Degradation** | Reduce features instead of total failure | Show cached data when DB is down |
| **Health Checks** | Liveness (alive?) + Readiness (ready to serve?) | K8s probes, ELB health checks |

#### LLD — Low-Level Design Patterns (Deep Dive)

##### SOLID Principles Quick Reference
| Principle | Full Name | One-Liner | Violation Sign |
|---|---|---|---|
| **S** | Single Responsibility | One class = one reason to change | "and" in class description |
| **O** | Open/Closed | Open for extension, closed for modification | Modifying existing code to add features |
| **L** | Liskov Substitution | Subtypes must be substitutable for base types | Overriding methods that break contracts |
| **I** | Interface Segregation | Many specific interfaces > one fat interface | Implementing methods that throw `UnsupportedOperationException` |
| **D** | Dependency Inversion | Depend on abstractions, not concretions | `new ConcreteClass()` inside business logic |

##### Design Pattern Categories for LLD
| Category | Patterns | When to Use |
|---|---|---|
| **Creational** | Singleton, Factory Method, Abstract Factory, Builder, Prototype | Object creation complexity, hide instantiation details |
| **Structural** | Adapter, Bridge, Composite, Decorator, Facade, Flyweight, Proxy | Compose objects, interface adaptation |
| **Behavioral** | Strategy, Observer, Command, State, Template Method, Iterator, Chain of Responsibility, Mediator, Visitor | Object interaction, algorithms, state changes |

##### API Design Checklist
| Aspect | REST Best Practice | gRPC Best Practice |
|---|---|---|
| **Naming** | Nouns for resources (`/users`), verbs via HTTP methods | Service + Method (`UserService.GetUser`) |
| **Versioning** | URL (`/v2/users`) or header (`Accept-Version: v2`) | Package version (`user.v2.UserService`) |
| **Errors** | Standard HTTP status codes + error body | gRPC status codes + `google.rpc.Status` |
| **Pagination** | `?page=2&size=20` or cursor-based (`?cursor=abc`) | `page_token` + `page_size` in request message |
| **Filtering** | Query params (`?status=active&sort=name`) | Repeated fields or `FieldMask` |
| **Auth** | Bearer token (JWT), OAuth2, API key | Metadata headers, mutual TLS |
| **Rate Limits** | `X-RateLimit-*` headers, `429 Too Many Requests` | Deadline propagation, backpressure |

##### State Machine Design
```
Example: Order State Machine

  [CREATED] ──place──▶ [PLACED] ──pay──▶ [PAID] ──ship──▶ [SHIPPED] ──deliver──▶ [DELIVERED]
       │                   │                │                   │
       └──cancel──▶ [CANCELLED]  ◀──cancel──┘      ◀──return──┘──▶ [RETURNED]

Rules:
  - Define all STATES (enum)
  - Define all EVENTS/TRANSITIONS
  - Guard conditions (can cancel only if not shipped)
  - Side effects per transition (send email, update inventory)
  - Use State Pattern or state machine library (Spring StateMachine, XState)
```

##### Schema Design Principles
| Concept | Relational (SQL) | Document (NoSQL) |
|---|---|---|
| **Normalize** | 3NF+ — eliminate redundancy, use JOINs | Denormalize — embed related data |
| **When to denormalize** | Read-heavy + joins are bottleneck | Always start denormalized |
| **Relationships** | Foreign keys + junction tables | Embed (1:few) or reference (1:many, many:many) |
| **Indexing** | B-Tree (default), Hash, GIN (full-text), partial indexes | Compound indexes, TTL indexes |
| **Migration** | Schema migrations (Flyway, Liquibase, Alembic) | Schema-less, validation at app layer |

#### Classic System Design Case Studies

##### HLD Case Studies — Approach Template
```
For each system:
1. Requirements     → Functional + Non-functional
2. Estimation       → QPS, Storage, Bandwidth, Memory
3. API Design       → Core endpoints / RPCs
4. High-Level Arch  → ASCII diagram with all components
5. Data Model       → Schema + storage choice (SQL/NoSQL/both)
6. Deep Dive        → 2-3 most interesting components
7. Scaling          → How to handle 10x → 100x → 1000x
8. Trade-offs       → Key decisions with alternatives
9. Bottlenecks      → Failure modes + mitigations
```

| Case Study | Key Components | Key Concepts |
|---|---|---|
| **URL Shortener** | Hash generation, KGS, DB, cache, analytics | Base62 encoding, collision handling, read-heavy |
| **Rate Limiter** | Token bucket, sliding window, distributed counter | Redis atomic ops, race conditions, distributed sync |
| **Chat System** | WebSocket servers, presence, message store, push | Fan-out, delivery guarantees, offline queuing |
| **News Feed** | Fan-out service, ranking, cache, notification | Fan-out-on-write vs fan-out-on-read, celebrity problem |
| **Video Streaming** | Transcoding, CDN, chunk delivery, recommendation | Adaptive bitrate, DAG task queue, cold storage |
| **Web Crawler** | Frontier, fetcher, parser, deduplication, DNS resolver | Politeness, URL prioritization, trap detection |
| **Notification System** | Priority queue, template engine, delivery service | Multi-channel (push/SMS/email), throttling, retry |
| **Search Autocomplete** | Trie, top-K, data collection, aggregation pipeline | Trie pruning, frequency updates, shard by prefix |
| **Distributed Cache** | Consistent hashing, cache cluster, eviction | Cache stampede, hot key, replication |
| **Payment System** | Payment gateway, ledger, reconciliation, idempotency | Double-entry bookkeeping, exactly-once, PCI compliance |
| **Ride-Sharing** | Geo-index, matching, trip, ETA, surge pricing | Geohash/S2/H3, supply-demand, real-time location |
| **Distributed File Storage** | Metadata service, chunk servers, replication | GFS/HDFS model, heartbeats, chunk checksums |

##### LLD Case Studies — Approach Template
```
For each system:
1. Use Cases        → Actors + user stories
2. Class Diagram    → Entities + relationships (UML)
3. Design Patterns  → Which patterns + why
4. SOLID Check      → Verify each principle
5. Code Skeleton    → Key classes/interfaces
6. State Diagrams   → For stateful entities
7. Extensibility    → How to add features
8. Testing Plan     → What + how to test
```

| Case Study | Key Classes | Key Patterns |
|---|---|---|
| **Parking Lot** | ParkingLot, Floor, Slot, Vehicle, Ticket | Strategy (pricing), Factory (vehicle), Observer (display) |
| **LRU Cache** | LRUCache, DoublyLinkedList, HashMap | — (data structure design) |
| **Elevator System** | Elevator, Floor, Request, Scheduler | Strategy (scheduling), State (elevator), Observer (display) |
| **Library Management** | Library, Book, Member, Loan, Fine | Observer (due date), Strategy (fine calc) |
| **Online Shopping** | Cart, Order, Product, Payment, User | Strategy (payment), Observer (notifications), State (order) |
| **Tic-Tac-Toe / Chess** | Board, Player, Piece, Move, Game | Command (moves), Strategy (AI), Template Method (game flow) |
| **Hotel Booking** | Hotel, Room, Booking, Guest, Payment | Strategy (pricing by season), State (room status) |
| **Vending Machine** | VendingMachine, Inventory, Coin, Product | State (idle/selecting/dispensing), Strategy (payment) |

#### Architecture Styles Comparison
| Style | When to Use | Pros | Cons | Examples |
|---|---|---|---|---|
| **Monolith** | Small team, early stage, simple domain | Simple deploy, easy debugging | Scaling limits, tight coupling | Early-stage startups |
| **Microservices** | Large team, complex domain, independent scaling | Scale independently, tech diversity | Operational complexity, distributed debugging | Netflix, Uber, Amazon |
| **Serverless** | Event-driven, variable load, rapid prototyping | Zero ops, auto-scale, pay-per-use | Cold starts, vendor lock-in, time limits | Lambda, Cloud Functions |
| **Event-Driven** | Async workflows, loose coupling, audit trails | Decoupled, replay, temporal queries | Eventual consistency, debugging complexity | Event sourcing systems |
| **Hexagonal (Ports & Adapters)** | Domain-centric, testable, swappable infra | Domain isolation, easy testing | More boilerplate | DDD applications |
| **CQRS** | Separate read/write scaling, complex queries | Optimized read models, event replay | Eventual consistency, complexity | High-read analytics systems |
| **Service-Oriented (SOA)** | Enterprise integration, reusable services | Reuse, standards (WSDL/SOAP) | Heavyweight, ESB complexity | Enterprise IT |

#### Estimation Cheat Sheet (Back-of-Envelope)
| Metric | Quick Estimate |
|---|---|
| **Seconds in a day** | ~86,400 ≈ ~100K |
| **Seconds in a month** | ~2.5M |
| **Seconds in a year** | ~31.5M ≈ ~32M |
| **1 million requests/day** | ~12 QPS |
| **1 billion requests/day** | ~12K QPS |
| **1 char** | 1 byte (ASCII) / 2-4 bytes (UTF-8) |
| **1 image (average)** | ~300 KB |
| **1 short video (1 min)** | ~5 MB |
| **1 TB** | 1,000 GB = 1,000,000 MB |
| **Read:Write ratio (social)** | ~100:1 or higher |
| **80/20 rule (caching)** | 20% of data serves 80% of reads |

#### Common System Design Interview Framework
```
Step 1: Clarify Requirements (5 min)
  → Functional requirements (what the system does)
  → Non-functional requirements (scale, latency, availability, durability)
  → Out of scope (explicitly state what you won't cover)

Step 2: Estimate Scale (5 min)
  → DAU / MAU → QPS (read + write)
  → Storage (per record × records × retention period)
  → Bandwidth (QPS × avg response size)
  → Memory for cache (80/20 rule: 20% of daily data)

Step 3: High-Level Design (10-15 min)
  → Draw architecture diagram (clients, LB, servers, DB, cache, queues)
  → Identify core APIs (REST / gRPC)
  → Choose database (SQL vs NoSQL, justify)
  → Show data flow for main use cases

Step 4: Deep Dive (10-15 min)
  → Pick 2-3 most interesting/challenging components
  → Discuss trade-offs (consistency vs availability, latency vs throughput)
  → Show scaling strategy (sharding, caching, replication)
  → Address failure modes (what if X goes down?)

Step 5: Wrap Up (5 min)
  → Summarize key design decisions
  → Mention what you'd add with more time (monitoring, analytics, ML)
  → Discuss potential improvements and scaling path
```

### Networking & Protocols
| Resource | URL | Best For |
|---|---|---|
| **MDN Web Docs — HTTP** | `https://developer.mozilla.org/en-US/docs/Web/HTTP` | HTTP protocol deep-dive |
| **gRPC Official Docs** | `https://grpc.io/docs/` | gRPC concepts, protobuf, streaming |
| **REST API Tutorial** | `https://restfulapi.net/` | REST principles, best practices |
| **High Performance Browser Networking** | `https://hpbn.co/` | Free book: TCP, TLS, HTTP/2, WebSocket |
| **Beej's Guide to Network Programming** | `https://beej.us/guide/bgnet/` | Sockets, TCP/UDP from scratch |
| **Wikipedia — OSI model** | `https://en.wikipedia.org/wiki/OSI_model` | Network layers reference |

#### Protocol Comparison Quick Reference
| Protocol | Type | Connection | Use Case | Format |
|---|---|---|---|---|
| **HTTP/REST** | Request-Response | Stateless | CRUD APIs, web services | JSON/XML |
| **gRPC** | RPC | Multiplexed (HTTP/2) | Microservice-to-microservice, low latency | Protobuf (binary) |
| **GraphQL** | Query language | Stateless | Flexible client-driven queries | JSON |
| **WebSocket** | Bidirectional | Stateful | Real-time: chat, gaming, live feeds | Any |
| **TCP** | Stream | Stateful | Reliable ordered delivery | Bytes |
| **UDP** | Datagram | Stateless | Low-latency: video, DNS, gaming | Bytes |
| **RPC (general)** | Remote procedure call | Varies | Cross-process/machine function calls | Varies |
| **MQTT** | Pub/Sub | Stateful | IoT, lightweight messaging | Binary |
| **AMQP** | Message queue | Stateful | Enterprise messaging (RabbitMQ) | Binary |

#### Stateful vs Stateless
| Aspect | Stateless | Stateful |
|---|---|---|
| Server memory | No client state between requests | Server tracks client state |
| Scalability | Easy — any server can handle any request | Hard — client tied to specific server (sticky sessions) |
| Examples | REST, HTTP, DNS, UDP | WebSocket, TCP connections, gRPC streams, database sessions |
| Trade-off | Client sends more data per request | Server uses more memory |

### Operating Systems
| Resource | URL | Best For |
|---|---|---|
| **OSTEP (free book)** | `https://pages.cs.wisc.edu/~remzi/OSTEP/` | Free, excellent OS textbook |
| **MIT 6.S081 (xv6)** | `https://pdos.csail.mit.edu/6.S081/` | Hands-on OS course with real kernel code |
| **OSDev Wiki** | `https://wiki.osdev.org/` | OS development from scratch |
| **Linux Kernel Docs** | `https://www.kernel.org/doc/html/latest/` | Linux kernel internals |
| **Julia Evans' Zines** | `https://wizardzines.com/` | Visual, fun explanations of OS/networking |

#### OS Core Concepts
```
Processes → Threads → Scheduling (Round Robin, MLFQ, CFS) →
Memory Management (Paging, Segmentation, Virtual Memory) →
Concurrency (Locks, Semaphores, Monitors, Deadlocks) →
File Systems (Inodes, Journaling, VFS) →
I/O (Blocking, Non-blocking, Async, Polling, Epoll) →
IPC (Pipes, Shared Memory, Message Queues, Sockets)
```

### DBMS & Databases
| Resource | URL | Best For |
|---|---|---|
| **Use The Index, Luke** | `https://use-the-index-luke.com/` | SQL indexing and performance |
| **CMU Database Course** | `https://15445.courses.cs.cmu.edu/` | Academic database internals |
| **SQLZoo** | `https://sqlzoo.net/` | Interactive SQL tutorial |
| **DB-Engines** | `https://db-engines.com/` | Database comparison and ranking |
| **PostgreSQL Docs** | `https://www.postgresql.org/docs/current/` | Excellent reference for RDBMS concepts |

#### Database Concepts Map
```
ACID → Transactions → Isolation Levels (Read Uncommitted → Serializable) →
Indexing (B-Tree, Hash, GIN, GiST) → Query Optimization → Joins →
Normalization (1NF → BCNF) → Denormalization → Sharding →
Replication (Leader-Follower, Multi-Leader, Leaderless) →
CAP Theorem → Eventual Consistency → Distributed Transactions (2PC, Saga)
```

### Testing & Quality
| Resource | URL | Best For |
|---|---|---|
| **Martin Fowler — Testing** | `https://martinfowler.com/testing/` | Test pyramid, test doubles, strategies |
| **Testing Trophy (Kent C. Dodds)** | `https://kentcdodds.com/blog/the-testing-trophy-and-testing-classifications` | Modern testing philosophy |
| **Google Testing Blog** | `https://testing.googleblog.com/` | Industry testing practices |
| **Test Desiderata (Kent Beck)** | `https://kentbeck.github.io/TestDesiderata/` | Properties of good tests |
| **xUnit Patterns** | `http://xunitpatterns.com/` | Catalog of test patterns and smells |

#### Testing Types Map
```
Unit Testing → Integration Testing → Contract Testing →
Component Testing → End-to-End Testing → Performance Testing →
Load Testing → Stress Testing → Chaos Testing →
Security Testing → Mutation Testing → Property-Based Testing →
Smoke Testing → Regression Testing → Acceptance Testing
```

#### Development Methodologies
| Methodology | Full Name | Key Idea | Cycle |
|---|---|---|---|
| **TDD** | Test-Driven Development | Write test first, then code, then refactor | Red → Green → Refactor |
| **BDD** | Behavior-Driven Development | Describe behavior in business language (Given/When/Then) | Scenario → Steps → Implementation |
| **ATDD** | Acceptance Test-Driven Development | Write acceptance tests with stakeholders first | Discuss → Distill → Develop → Demo |
| **DDD** | Domain-Driven Design | Model code around the business domain | Ubiquitous Language → Bounded Contexts |

### SDLC & Methodologies
| Resource | URL | Best For |
|---|---|---|
| **Agile Manifesto** | `https://agilemanifesto.org/` | Original agile principles |
| **Scrum Guide** | `https://scrumguides.org/` | Official Scrum framework |
| **Atlassian Agile Coach** | `https://www.atlassian.com/agile` | Practical Agile/Scrum/Kanban guides |
| **Martin Fowler — Agile** | `https://martinfowler.com/agile.html` | Thoughtful agile engineering practices |
| **Shape Up (Basecamp)** | `https://basecamp.com/shapeup` | Alternative to Scrum — free book |

#### SDLC Models
| Model | Flow | Best For | Risk |
|---|---|---|---|
| **Waterfall** | Linear: Requirements → Design → Build → Test → Deploy | Stable, well-known requirements | Late feedback |
| **Agile/Scrum** | Iterative sprints with continuous feedback | Evolving requirements | Scope discipline needed |
| **Kanban** | Continuous flow, WIP limits | Operations, maintenance | Can lack long-term planning |
| **Spiral** | Iterative with risk analysis each cycle | Large, risky projects | Complex process |
| **XP** | Pair programming, CI, TDD, short iterations | High-quality, changing requirements | Discipline-intensive |

### Multithreading & Concurrency
| Resource | URL | Best For |
|---|---|---|
| **Jenkov Concurrency** | `https://jenkov.com/tutorials/java-concurrency/index.html` | Deep Java concurrency tutorial |
| **Baeldung Concurrency** | `https://www.baeldung.com/java-concurrency` | Practical concurrency guides |
| **LMAX Disruptor** | `https://lmax-exchange.github.io/disruptor/` | High-performance concurrent patterns |
| **deadlocks.dev** | `https://deadlocks.dev/` | Concurrency puzzles and challenges |

#### Concurrency Concepts Map
```
Threads → Runnable/Callable → Thread Lifecycle → Synchronization →
Locks (ReentrantLock, ReadWriteLock, StampedLock) →
Atomic Variables → Volatile → Happens-Before →
Executors → Thread Pools → CompletableFuture →
Fork/Join → Parallel Streams → Virtual Threads (Project Loom) →
Producer-Consumer → Readers-Writers → Dining Philosophers →
Lock-Free Data Structures → CAS Operations
```

---

## Distributed Systems

### Books
| Book | Author | Core Topics |
|---|---|---|
| **Designing Data-Intensive Applications** | Martin Kleppmann | Replication, partitioning, consistency, stream processing |
| **Distributed Systems (3rd Ed.)** | Tanenbaum & Van Steen | Architectures, processes, communication, naming, consistency |
| **Understanding Distributed Systems** | Roberto Vitillo | Practical guide, communication, coordination, scalability |
| **Database Internals** | Alex Petrov | Storage engines, distributed transactions, consensus |

### Online Resources
| Resource | URL | Best For |
|---|---|---|
| **Distributed Systems for Fun and Profit** | `http://book.mixu.net/distsys/` | Free, concise distributed systems intro |
| **MIT 6.824 (Distributed Systems)** | `https://pdos.csail.mit.edu/6.824/` | Academic course with labs (Raft, MapReduce) |
| **Jepsen** | `https://jepsen.io/` | Distributed systems correctness testing |
| **The Morning Paper** | `https://blog.acolyer.org/` | CS paper summaries (archived, still valuable) |
| **Martin Kleppmann's Blog** | `https://martin.kleppmann.com/` | Distributed data, CRDTs, formal verification |

### Replication Topologies
```
Single-Leader (Master-Slave / Primary-Replica)
────────────────────────────────────────────
  Write → [Leader] → replicates to → [Follower 1]
                                    → [Follower 2]
                                    → [Follower N]
  Read  ← [any node]

  ✅ Simple, strong consistency on leader
  ❌ Leader is SPOF, writes don't scale
  📌 Used by: PostgreSQL streaming replication, MySQL

Multi-Leader (Master-Master / Active-Active)
────────────────────────────────────────────
  Write → [Leader A] ←→ sync ←→ [Leader B]
  Read  ← [any leader]

  ✅ Writes scale, geo-distributed writes
  ❌ Conflict resolution is hard
  📌 Used by: CouchDB, multi-region setups

Leaderless (Peer-to-Peer / Dynamo-style)
────────────────────────────────────────
  Write → [Node A, Node B, Node C]   (W quorum)
  Read  ← [Node A, Node B, Node C]   (R quorum)
  Rule:   W + R > N  for consistency

  ✅ High availability, no SPOF
  ❌ Eventual consistency, read repair needed
  📌 Used by: Cassandra, DynamoDB, Riak
```

### Consensus Algorithms
| Algorithm | Approach | Used By |
|---|---|---|
| **Raft** | Leader-based, understandable | etcd, CockroachDB, Consul |
| **Paxos** | Quorum-based, theoretical foundation | Chubby (Google), original academia |
| **Multi-Paxos** | Paxos optimized for repeated decisions | Spanner (Google) |
| **ZAB** | Zookeeper Atomic Broadcast | Apache ZooKeeper |
| **PBFT** | Byzantine fault tolerant | Blockchain, adversarial environments |
| **Viewstamped Replication** | View-change based consensus | Research, some production systems |

### Consistency Models
```
Strong Consistency
  └── Linearizability — operations appear instantaneous, globally ordered
  └── Sequential Consistency — all processes see same order

Weak Consistency
  └── Eventual Consistency — all replicas converge eventually
  └── Causal Consistency — causally related ops in order, concurrent ops unordered
  └── Read-Your-Writes — a client always sees its own writes
  └── Monotonic Reads — a client never sees older data after seeing newer data
```

### Distributed Systems Concepts Map
```
Communication:
  RPC → REST → gRPC → Message Queues → Event Streaming →
  Pub/Sub → Request-Reply → Async Messaging

Replication:
  Single-Leader → Multi-Leader → Leaderless →
  Synchronous vs Async → Quorum (W + R > N) →
  Chain Replication → Log Replication

Consistency & Ordering:
  Linearizability → Sequential → Causal → Eventual →
  Vector Clocks → Lamport Timestamps → Happens-Before

Fault Tolerance:
  Failure Detection (heartbeats, φ accrual) →
  Leader Election → Fencing → Split-Brain Prevention →
  Circuit Breaker → Bulkhead → Retry with Backoff

Coordination:
  Consensus (Raft/Paxos) → Distributed Locks →
  Leader Election → Barrier → Two-Phase Commit (2PC) →
  Saga Pattern → Outbox Pattern

Partitioning (Sharding):
  Hash Partitioning → Range Partitioning →
  Consistent Hashing → Virtual Nodes →
  Rebalancing → Hot Spots
```

### Key Theorems & Trade-offs
| Theorem | Statement | Implication |
|---|---|---|
| **CAP** | Choose 2 of: Consistency, Availability, Partition tolerance | In network partitions, choose C or A |
| **PACELC** | If Partition → choose A or C; Else → choose Latency or Consistency | Extends CAP to normal operation trade-offs |
| **FLP Impossibility** | No deterministic consensus in async systems with even 1 failure | Consensus algorithms use timeouts/randomization |
| **Two Generals** | Cannot guarantee agreement over unreliable channel | At-least-once / idempotency needed |
| **Byzantine Generals** | Agreement possible if > 2/3 nodes are honest | Foundation for BFT algorithms |

---

## DevOps, CI/CD & Infrastructure

### CI/CD Tools
| Tool | Type | Best For | Docs |
|---|---|---|---|
| **GitHub Actions** | Cloud-native CI/CD | GitHub-hosted projects, full workflow automation | `https://docs.github.com/en/actions` |
| **Jenkins** | Self-hosted CI/CD | Enterprise pipelines, extensive plugin ecosystem | `https://www.jenkins.io/doc/` |
| **GitLab CI/CD** | Integrated CI/CD | GitLab-hosted projects, built-in registry & security | `https://docs.gitlab.com/ee/ci/` |
| **CircleCI** | Cloud CI/CD | Fast builds, Docker-first, config-as-code | `https://circleci.com/docs/` |
| **Travis CI** | Cloud CI/CD | Open-source projects, simple YAML config | `https://docs.travis-ci.com/` |
| **Azure DevOps Pipelines** | Cloud CI/CD | Microsoft ecosystem, multi-stage pipelines | `https://learn.microsoft.com/en-us/azure/devops/pipelines/` |
| **ArgoCD** | GitOps CD | Kubernetes-native continuous delivery | `https://argo-cd.readthedocs.io/` |
| **Tekton** | Cloud-native CI/CD | Kubernetes-native pipeline resources | `https://tekton.dev/docs/` |

### Containers & Orchestration
| Tool | Purpose | Docs |
|---|---|---|
| **Docker** | Container runtime — package apps with dependencies | `https://docs.docker.com/` |
| **Docker Compose** | Multi-container local environments | `https://docs.docker.com/compose/` |
| **Kubernetes (K8s)** | Container orchestration — scheduling, scaling, self-healing | `https://kubernetes.io/docs/` |
| **Helm** | K8s package manager — templated deployments | `https://helm.sh/docs/` |
| **Podman** | Rootless container runtime (Docker alternative) | `https://podman.io/docs` |
| **Containerd** | Industry-standard container runtime (powers Docker & K8s) | `https://containerd.io/docs/` |

#### Docker Concepts Map
```
Dockerfile → Image → Container → Volume → Network →
Docker Compose → Multi-stage Builds → Layer Caching →
Registry (Docker Hub, GHCR, ECR) → Security Scanning
```

#### Kubernetes Concepts Map
```
Pod → ReplicaSet → Deployment → Service → Ingress →
ConfigMap → Secret → PersistentVolume → StatefulSet →
DaemonSet → Job/CronJob → HPA → Namespace →
Helm Chart → Operator → CRD → Service Mesh (Istio/Linkerd)
```

### Infrastructure as Code (IaC)
| Tool | Type | Docs |
|---|---|---|
| **Terraform** | Declarative IaC — cloud-agnostic provisioning | `https://developer.hashicorp.com/terraform/docs` |
| **Ansible** | Configuration management — agentless, YAML playbooks | `https://docs.ansible.com/` |
| **Pulumi** | IaC using real programming languages (TypeScript, Python, Go) | `https://www.pulumi.com/docs/` |
| **CloudFormation** | AWS-native IaC — JSON/YAML templates | `https://docs.aws.amazon.com/cloudformation/` |
| **Chef / Puppet** | Configuration management — agent-based | `https://docs.chef.io/` / `https://www.puppet.com/docs` |

### Cloud Platforms
| Platform | Docs | Key Services |
|---|---|---|
| **AWS** | `https://docs.aws.amazon.com/` | EC2, S3, RDS, Lambda, ECS/EKS, SQS, SNS, DynamoDB |
| **Google Cloud (GCP)** | `https://cloud.google.com/docs` | Compute Engine, GKE, Cloud Run, BigQuery, Pub/Sub |
| **Microsoft Azure** | `https://learn.microsoft.com/en-us/azure/` | VMs, AKS, App Service, Cosmos DB, Service Bus |
| **DigitalOcean** | `https://docs.digitalocean.com/` | Droplets, K8s, App Platform — simpler, dev-friendly |
| **Vercel / Netlify** | `https://vercel.com/docs` | Frontend/JAMstack deployment, serverless functions |

### Monitoring & Observability
| Tool | Purpose | Docs |
|---|---|---|
| **Prometheus** | Metrics collection & alerting (pull-based) | `https://prometheus.io/docs/` |
| **Grafana** | Visualization dashboards (works with Prometheus, etc.) | `https://grafana.com/docs/` |
| **ELK Stack** | Elasticsearch + Logstash + Kibana — log aggregation | `https://www.elastic.co/guide/` |
| **Jaeger / Zipkin** | Distributed tracing | `https://www.jaegertracing.io/docs/` |
| **Datadog** | Full-stack monitoring (commercial) | `https://docs.datadoghq.com/` |
| **PagerDuty** | Incident management & on-call scheduling | `https://support.pagerduty.com/docs` |
| **OpenTelemetry** | Vendor-neutral observability framework (traces, metrics, logs) | `https://opentelemetry.io/docs/` |

#### Observability Pillars
```
Logs   — What happened? (text records of events)
Metrics — How much? How fast? (numerical measurements over time)
Traces  — How did a request flow through services? (distributed context)
```

### Version Control & Collaboration
| Tool | Purpose | Docs |
|---|---|---|
| **Git** | Distributed version control | `https://git-scm.com/doc` |
| **GitHub** | Hosting + collaboration + CI/CD + code review | `https://docs.github.com/` |
| **GitLab** | Hosting + full DevOps platform | `https://docs.gitlab.com/` |
| **Bitbucket** | Hosting + Jira integration | `https://support.atlassian.com/bitbucket-cloud/` |

---

## Frameworks & Tech Stacks

### Backend Frameworks
| Framework | Language | Type | Docs |
|---|---|---|---|
| **Spring Boot** | Java | Full-featured, enterprise | `https://docs.spring.io/spring-boot/docs/current/reference/html/` |
| **Quarkus** | Java | Cloud-native, fast startup | `https://quarkus.io/guides/` |
| **Micronaut** | Java | Lightweight, compile-time DI | `https://docs.micronaut.io/` |
| **Django** | Python | Batteries-included, ORM, admin | `https://docs.djangoproject.com/` |
| **FastAPI** | Python | Modern, async, type hints, OpenAPI | `https://fastapi.tiangolo.com/` |
| **Flask** | Python | Minimal, flexible | `https://flask.palletsprojects.com/` |
| **Express.js** | Node.js | Minimal, widely used | `https://expressjs.com/` |
| **NestJS** | Node.js/TS | Structured, Angular-inspired | `https://docs.nestjs.com/` |
| **Gin** | Go | Lightweight, high-perf | `https://gin-gonic.com/docs/` |
| **Actix Web** | Rust | High-performance, actor-based | `https://actix.rs/docs/` |
| **ASP.NET Core** | C# | Microsoft ecosystem, cross-platform | `https://learn.microsoft.com/en-us/aspnet/core/` |
| **Ruby on Rails** | Ruby | Convention over configuration | `https://guides.rubyonrails.org/` |

### Frontend Frameworks
| Framework | Language | Docs |
|---|---|---|
| **React** | JavaScript/TSX | `https://react.dev/` |
| **Vue.js** | JavaScript/SFC | `https://vuejs.org/guide/` |
| **Angular** | TypeScript | `https://angular.dev/` |
| **Svelte / SvelteKit** | JavaScript | `https://svelte.dev/docs/` |
| **Next.js** | React/SSR | `https://nextjs.org/docs` |
| **Nuxt** | Vue/SSR | `https://nuxt.com/docs` |

### Databases
| Database | Type | Best For | Docs |
|---|---|---|---|
| **PostgreSQL** | Relational | General purpose, advanced features | `https://www.postgresql.org/docs/` |
| **MySQL** | Relational | Web apps, widely deployed | `https://dev.mysql.com/doc/` |
| **MongoDB** | Document | Flexible schema, rapid prototyping | `https://www.mongodb.com/docs/` |
| **Redis** | Key-Value / Cache | Caching, sessions, pub/sub | `https://redis.io/docs/` |
| **Cassandra** | Wide-Column | Write-heavy, high availability | `https://cassandra.apache.org/doc/` |
| **Neo4j** | Graph | Relationship-heavy data | `https://neo4j.com/docs/` |
| **DynamoDB** | Key-Value (AWS) | Serverless, auto-scaling | `https://docs.aws.amazon.com/dynamodb/` |
| **SQLite** | Embedded Relational | Local/embedded apps, testing | `https://www.sqlite.org/docs.html` |

### Message Brokers & Queues
| System | Type | Docs |
|---|---|---|
| **Apache Kafka** | Distributed event streaming | `https://kafka.apache.org/documentation/` |
| **RabbitMQ** | Message broker (AMQP) | `https://www.rabbitmq.com/docs` |
| **Apache Pulsar** | Multi-tenant messaging | `https://pulsar.apache.org/docs/` |
| **AWS SQS / SNS** | Managed queue / pub-sub | `https://docs.aws.amazon.com/sqs/` |
| **NATS** | Lightweight messaging | `https://docs.nats.io/` |

### Build Tools
| Tool | Language/Ecosystem | Docs |
|---|---|---|
| **Maven** | Java | `https://maven.apache.org/guides/` |
| **Gradle** | Java/Kotlin/Android | `https://docs.gradle.org/` |
| **npm / pnpm / yarn** | JavaScript/Node.js | `https://docs.npmjs.com/` |
| **pip / Poetry** | Python | `https://pip.pypa.io/en/stable/` |
| **Cargo** | Rust | `https://doc.rust-lang.org/cargo/` |
| **CMake** | C/C++ | `https://cmake.org/documentation/` |
| **Go modules** | Go | `https://go.dev/ref/mod` |

### API Styles Comparison
| Style | Protocol | Data Format | Best For |
|---|---|---|---|
| **REST** | HTTP | JSON | Standard CRUD APIs |
| **GraphQL** | HTTP | JSON | Flexible client queries, reducing over-fetching |
| **gRPC** | HTTP/2 | Protobuf (binary) | Microservices, low-latency, streaming |
| **WebSocket** | TCP (upgraded HTTP) | Any | Real-time bidirectional (chat, live data) |
| **SOAP** | HTTP/SMTP | XML | Legacy enterprise integrations |

---

## Open-Source Projects to Study (Language-Agnostic Value)

| Project | Language | What You'll Learn |
|---|---|---|
| **Redis** | C | Efficient data structures, event loop, protocol design |
| **SQLite** | C | Database internals in one file, testing discipline |
| **Linux Kernel** | C | OS concepts in practice, concurrency, memory management |
| **Nginx** | C | Event-driven architecture, high-performance I/O |
| **Go standard library** | Go | Excellent API design, minimal dependencies |
| **Flask / FastAPI** | Python | Clean web framework design, middleware patterns |
| **Kubernetes** | Go | Distributed systems, reconciliation loops, CRDs |
| **Tokio** | Rust | Async runtime, futures, task scheduling |
| **Spring Framework** | Java | Enterprise patterns, DI, AOP, convention over configuration |
| **JUnit 5** | Java | Test framework design, extension model, annotations |

---

## How to Use This Skill

When a learner asks about **any** software engineering concept:
1. **Identify the domain** — is it DSA, system design, networking, OS, testing, patterns, etc.?
2. **Recommend the best resource** from the relevant section above
3. **Provide the canonical book reference** if one exists
4. **Point to hands-on practice** — exercises, projects, or code to study
5. **Use `fetch` to retrieve content** from official documentation websites when the learner wants to study specific pages

When recommending resources:
- Start with **1 official doc + 1 tutorial + 1 hands-on** — never overwhelm with 10 links
- Explain **why** each resource is recommended for the learner's current level
- Sequence resources: foundational → practical → advanced
