# DROP Spaces Graph Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Spaces Graph Package implements the Graph Representation and Traversal Algorithms.


## Class Components

 * [***BellmanFordScheme***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/graph/BellmanFordScheme.java)
 <i>BellmanFordScheme</i> implements the Bellman Ford Algorithm for finding the Shortest Path between a Pair
 of Vertexes in a Graph.

 * [***DijkstraScheme***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/graph/DijkstraScheme.java)
 <i>DijkstraScheme</i> implements the Dijkstra Algorithm for finding the Shortest Path between a Pair of
 Vertexes in a Graph.

 * [***Edge***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/graph/Edge.java)
 <i>Edge</i> represents the Connection between a Pair of Vertexes.

 * [***ShortestPathFirstWengert***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/graph/ShortestPathFirstWengert.java)
 <i>ShortestPathFirstWengert</i> maintains the Intermediate Wengert Objects generated during a Single
 Sequence of the Scheme Run.

 * [***ShortestPathTree***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/graph/ShortestPathTree.java)
 <i>ShortestPathTree</i> holds the Map of Vertex Peripheries by Weight and Vertex Name.

 * [***ShortestPathVertex***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/graph/ShortestPathVertex.java)
 <i>ShortestPathVertex</i> holds the given Vertex's Previous Traversal Vertex and the Weight from the Source.

 * [***SinglyLinkedNode***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/graph/SinglyLinkedNode.java)
 <i>SinglyLinkedNode</i> implements the Node behind a Singly Linked List.

 * [***Topography***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/graph/Topography.java)
 <i>Topography</i> holds Vertexes and the Edges between them.

 * [***TopographyEdgeMap***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/graph/TopographyEdgeMap.java)
 <i>TopographyEdgeMap</i> maintains a Map of the Topography Connection Edges.


## References

 * Knuth, D. (1973): <i>The Art of Computer Programming</i> <b>Addison-Wesley</b>

 * Oracle (2018): LinkedList (Java Platform SE 7)
 	https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html

 * Wikipedia (2018a): Linked List https://en.wikipedia.org/wiki/Linked_list

 * Wikipedia (2018b): Doubly Linked List https://en.wikipedia.org/wiki/Doubly_linked_list

 * Wikipedia (2018c): Linked Data Structure https://en.wikipedia.org/wiki/Linked_data_structure

 * Wikipedia (2018d): Graph (Abstract Data Type) https://en.wikipedia.org/wiki/Graph_(abstract_data_type)

 * Wikipedia (2018e): Graph Theory https://en.wikipedia.org/wiki/Graph_theory

 * Wikipedia (2018f): Graph (Discrete Mathematics)
 	https://en.wikipedia.org/wiki/Graph_(discrete_mathematics)

 * Wikipedia (2018g): Dijkstra's Algorithm https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm

 * Wikipedia (2018h): Bellman-Ford Algorithm https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm


## DROP Specifications

 * Main                     => https://lakshmidrip.github.io/DROP/
 * Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * GitHub                   => https://github.com/lakshmiDRIP/DROP
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
