# DROP Graph Bellman Ford Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Graph Bellman Ford Package implements the Bellman Ford Shortest Path Family of Algorithms.


## Class Components

 * [***BannisterEppsteinPathGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/bellmanford/BannisterEppsteinPathGenerator.java)
 <i>BannisterEppsteinPathGenerator</i> generates the Shortest Path for a Directed Graph using the Bellman-Ford Algorithm with the Bannister and Eppstein (2012) Edge Partitioning Scheme applied to improve the Worst-Case Behavior.

 * [***EdgePartition***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/bellmanford/EdgePartition.java)
 <i>EdgePartition</i> contains the sub-graphs of the Partitioned Vertexes and their Edges from a Master Graph.

 * [***EdgePartitionGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/bellmanford/EdgePartitionGenerator.java)
 <i>EdgePartitionGenerator</i> generates the Shortest Path for a Directed Graph using the Bellman-Ford Algorithm with the Edge Partitioning Scheme applied to improve the Worst-Case Behavior.

 * [***EdgeRelaxationPathGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/bellmanford/EdgeRelaxationPathGenerator.java)
 <i>EdgeRelaxationPathGenerator</i> generates the Shortest Path for a Directed Graph using the Bellman-Ford Algorithm.

 * [***JohnsonPathGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/bellmanford/JohnsonPathGenerator.java)
 <i>JohnsonPathGenerator</i> generates the Shortest Path for a Directed Graph using the Johnson Algorithm.

 * [***VertexRelaxationControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/bellmanford/VertexRelaxationControl.java)
 <i>VertexRelaxationControl</i> controls the Vertexes to be relaxed in the Shortest Path Generation for a Directed Graph under the Bellman-Ford Algorithm. This happens by eliminating unnecessary Vertex Relaxations.

 * [***YenEdgePartitionPathGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/bellmanford/YenEdgePartitionPathGenerator.java)
 <i>YenEdgePartitionPathGenerator</i> generates the Shortest Path for a Directed Graph using the Bellman-Ford Algorithm with the Yen (1970) Edge Partitioning Scheme applied to improve the Worst-Case Behavior.

 * [***YenReducedRelaxationPathGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/bellmanford/YenReducedRelaxationPathGenerator.java)
 <i>YenReducedRelaxationPathGenerator</i> generates the Shortest Path for a Directed Graph using the Bellman-Ford Algorithm with a Yen (1970) Vertex Relaxation Trimming Scheme applied.


# References

 * Bang-Jensen, J., and G. Gutin (2008): <i>Digraphs: Theory, Algorithms, and Applications 2<sup>nd</sup> Edition</i> <b>Springer</b>

 * Black, P. E. (2004): Johnson Algorithm https://xlinux.nist.gov/dads/HTML/johnsonsAlgorithm.html

 * Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms</i> 3<sup>rd</sup> Edition <b>MIT Press</b>

 * Johnson, D. B. (1977): Efficient Algorithms for Shortest Paths in Sparse Networks <i>Journal of the ACM</i> <b>24 (1)</b> 1-13

 * Kleinberg, J., and E. Tardos (2022): <i>Algorithm Design 2<sup>nd</sup> Edition</i> <b>Pearson</b>

 * Mehlhorn, K. W., and P. Sanders (2008): <i>Algorithms and Data Structures: The Basic Toolbox</i> <b>Springer</b>

 * Russell, S., and P. Norvig (2009): <i>Artificial Intelligence: A Modern Approach 3<sup>rd</sup> Edition</i> <b>Prentice Hall</b>

 * Sedgewick, R. and K. Wayne (2011): <i>Algorithms 4<sup>th</sup> Edition</i> <b>Addison Wesley</b>

 * Suurballe, J. W. (1974): Disjoint Paths in a Network <i>Networks</i> <b>14 (2)</b> 125-145

 * Wikipedia (2019): Johnson Algorithm https://en.wikipedia.org/wiki/Johnson%27s_algorithm

 * Wikipedia (2020): Bellman-Ford Algorithm https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm


## DROP Specifications

 * Main                     => https://lakshmidrip.github.io/DROP/
 * Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * GitHub                   => https://github.com/lakshmiDRIP/DROP
 * Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
