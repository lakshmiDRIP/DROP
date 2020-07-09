
# Graph Algorithm Library


<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

Graph Algorithm Library contains the Libraries implementing the Graph Representation and Path Traversal Algorithms.


## Documentation

 |        Document         | Link |
 |-------------------------|------|
 | Technical Specification | [*Latest*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/GraphAlgorithm/GraphAlgorithm_v4.98.pdf) [*Previous*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/GraphAlgorithm) |
 | User Guide              |  |
 | API                     | [*Javadoc*](https://lakshmidrip.github.io/DROP/Javadoc/index.html)|


## Component Projects

 * *Graph* => Graph Representation and Path Traversal.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Agraph) }


## Coverage

 * Priority Queue
	* Overview
	* Operations
	* Implementation
	* Specialized Heaps
	* Summary of Running Times
	* Using a Priority Queue to Sort
	* Using a Sorting Algorithm to Make a Priority Queue
	* Applications – Bandwidth Management
	* Applications – Discrete Event Simulation
	* Application – Dijkstra’s Algorithm
	* Application – Huffman Coding
	* Application – Best-First Search Algorithm
	* Application – ROAM Triangulation Algorithm
	* Application – Prim’s Algorithm for Minimum Spanning Tree
	* Parallel Priority Queue
	* Parallelize Individual Operations
	* Concurrent Parallel Access
	* K-Element Operations
	* References
 * Binary Heap
	* Overview
	* Heap Operations
	* Insert
	* Extract
	* Building a Heap
	* Heap Implementation
	* Derivation of Index Equations
	* Child Nodes
	* Parent Node
	* Related Structures
	* References
 * Binomial Heap
	* Overview
	* Definition
	* Structure of a Binomial Heap
	* Implementation
	* Merge
	* Insert
	* Find Minimum
	* Delete Minimum
	* Decrease Key
	* Delete
	* References
 * Soft Heap
	* Overview
	* Applications - Selection Algorithm
	* References
 * The Soft Heap: An Approximate Priority Queue with Optimal Error Rate
	* Abstract
	* Introduction
	* Applications
	* The Data Structure
	* Soft Heap Operations
	* Inserting an Item
	* Melding Two Heaps
	* DeleteMin
	* Complexity Analysis
	* The Error Rate
	* The Running Time
	* Optimality
	* References
 * A Simpler Implementation and Analysis of Chazelle’s Soft Heaps
	* Introduction
	* Soft Heaps
	* Implementation
	* The Data Structure
	* The Sift Operation
	* The Combine Operation
	* The Update-Suffix-Min Operation
	* The make-heap Operation
	* The meld Operation
	* The insert Operation
	* The extract-min Operation
	* Correctness
	* Amortized Analysis
	* Adding a Delete Operation
	* Comparison with Chazelle’s Implementation
	* Concluding Remarks
	* References
 * Spanning Tree
	* Overview
	* Applications
	* Definition
	* Fundamental Cycles
	* Fundamental Cutsets
	* Spanning Forests
	* Counting Spanning Trees
	* In Specific Graphs
	* In Arbitrary Graphs
	* Deletion-Contraction
	* Tutte Polynomial
	* Algorithms - Construction
	* Algorithms - Optimization
	* Randomization
	* Enumeration
	* In Infinite Graphs
	* In Directed Multi-graphs
	* References
 * Minimum Spanning Tree
	* Overview
	* Multiplicity Properties
	* Uniqueness Property
	* Minimum Cost Subgraph Property
	* Cycle Property
	* Cut Property
	* Minimum-Cost Edge Property
	* Contraction Properties
	* Algorithms
	* Faster Algorithms
	* Linear-Time Algorithms in Special Cases – Dense Graphs
	* Linear Time Algorithm – Integer Weights
	* Decision Trees
	* Optimal Algorithm
	* Parallel and Distributed Algorithms
	* MST on Complete Graphs
	* Applications
	* Related Problems
	* References
 * Prim’s Algorithm
	* Overview
	* Description
	* Time Complexity
	* Proof of Correctness
	* Parallel Algorithm
	* References
 * Kruskal’s Algorithm
	* Introduction
	* The Algorithm
	* Complexity
	* Proof of Correctness
	* Spanning Tree
	* Minimality
	* Parallel Algorithm
	* References
 * Boruvka's Algorithm
	* Overview
	* Special Cases
	* Complexity
	* Other Algorithms
	* References
 * Reverse-Delete Algorithm
	* Overview
	* Running Time
	* Proof of Correctness: Approach
	* Proof of Correctness: Part One
	* Proof of Correctness: Part Two
	* References
 * Breadth-first Search
	* Overview
	* Objective
	* Implementation
	* Time and Space Complexity Analysis
	* Completeness Analysis
	* BFS Ordering
	* Applications
	* References
 * Depth-first Search
	* Overview
	* Properties
	* DFS Traversal
	* Edges from a DFS Output
	* Ordering of the DFS Output
	* Vertex Orderings
	* Implementation
	* Applications
	* Complexity
	* References
 * Dijkstra's Algorithm
	* Introduction
	* Algorithm
	* Characteristics
	* Generalization of the Problem
	* Using a Priority Queue
	* Proof of Correctness
	* Running Time
	* Practical Optimizations and Infinite Graphs
	* Specialized Variants
	* Related Problems and Algorithms
	* Dynamics Programming Perspective
	* References
 * Bellman-Ford Algorithm
	* Overview
	* The Algorithm
	* Proof of Correctness
	* Finding Negative Weights
	* Applications in Routing
	* Improvements
	* References
 * Johnson's Algorithm
	* Overview
	* Algorithm Description
	* Correctness
	* Analysis
	* References
 * A^* Search Algorithm
	* Overview
	* Introduction
	* Description
	* Implementation Details
	* Special Cases
	* Properties – Termination and Completeness
	* Properties – Admissibility
	* Properties - Optimal Efficiency
	* Bounded Relaxation
	* Complexity
	* Applications
	* Relation to Other Algorithms
	* Variants
	* References
 * Floyd-Warshall Algorithm
	* Overview
	* History and Naming
	* Algorithm
	* Behavior with Negative Cycles
	* Path Reconstruction
	* Analysis
	* Applications and Generalizations
	* Comparisons with other Shortest Path Algorithms
	* References
 * Selection Algorithm
	* Overview
	* Selection by Sorting
	* Unordered Partial Sorting
	* Partial Selection Sort
	* Partition-Based Selection
	* Median Selection as Pivot Strategy
	* Incremental Sorting by Selection
	* Using Data Structures to Select in Sublinear Time
	* Lower Bounds
	* Space Complexity
	* Online Selection Algorithm
	* Related Problems
	* References
 * Quickselect
	* Overview
	* Algorithm
	* Time Complexity
	* Variants
	* References
 * Median Of Medians
	* Overview
	* Outline
	* Algorithm
	* Properties of the Pivot
	* Proof of O(n) Running Time
	* Analysis
	* References
 * Floyd-Rivest Algorithm
	* Overview
	* Algorithm
	* References
 * Introselect
	* Overview
	* Algorithm
	* References
 * Order Statistics Tree
	* Overview
	* Advanced Search Tree Implementation
	* References
 * Maximum Sub-array Problem
	* Overview
	* Background
	* Applications
	* Kadane’s Algorithm
	* Generalizations
	* References
 * Subset Sum Problem
	* Overview
	* Complexity
	* Exponential Time Algorithm
	* Pseudo-polynomial Time Dynamic Programming Solution
	* Polynomial-Time Approximate Algorithm
	* References
 * 3SUM
	* Overview
	* Quadratic Algorithm
	* Variants
	* Reduction from Conv3SUM to 3SUM
	* Reduction from 3SUM to Conv3SUM
	* 3SUM-Hardness
	* References


## DROP Specifications

 * Main                     => https://lakshmidrip.github.io/DROP/
 * Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * GitHub                   => https://github.com/lakshmiDRIP/DROP
 * Repo Layout Taxonomy     => https://lakshmidrip.github.io/DROP/Taxonomy.md
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
