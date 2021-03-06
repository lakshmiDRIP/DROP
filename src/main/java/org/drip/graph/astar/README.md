# DROP A<sup>*</sup> Search Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP A<sup>+</sup> Search Package implements A<sup>*</sup> Heuristic Shortest Path Family.


## Class Components

 * [***DynamicWeightFHeuristic***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/astar/DynamicWeightFHeuristic.java)
 <i>DynamicWeightFHeuristic</i> implements the Dynamically Weighted A<sup>*</sup> F-Heuristic Value at a Vertex.

 * [***FHeuristic***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/astar/FHeuristic.java)
 <i>FHeuristic</i> implements the A<sup>*</sup> F-Heuristic Value at a Vertex.

 * [***MalikAllardCompositeHeuristic***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/astar/MalikAllardCompositeHeuristic.java)
 <i>MalikAllardCompositeHeuristic</i> implements the Composite Malik and Allard (1983) A<sup>*</sup> F-Heuristic Value at a Vertex.

 * [***MalikAllardFHeuristic***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/astar/MalikAllardFHeuristic.java)
 <i>MalikAllardFHeuristic</i> implements the Statically Weighted Primary/Backtracking A<sup>*</sup> F-Heuristic Value at a Vertex.

 * [***StaticWeightFHeuristic***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/astar/StaticWeightFHeuristic.java)
 <i>StaticWeightFHeuristic</i> implements the Statically Weighted A<sup>*</sup> F-Heuristic Value at a Vertex.

 * [***VertexContext***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/astar/VertexContext.java)
 <i>VertexContext</i> holds the Current Vertex, its Parent, and the most recently expanded Vertexes for use in the Alpha A<sup>*</sup> Heuristic Function.

 * [***VertexContextEpsilonAdmissibleHeuristic***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/astar/VertexContextEpsilonAdmissibleHeuristic.java)
 <i>VertexContextEpsilonAdmissibleHeuristic</i> computes the Reese (1999) Epsilon-Admissible Heuristic in the Alpha A<sup>*</sup> Heuristic Function.

 * [***VertexContextWeightHeuristic***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/astar/VertexContextWeightHeuristic.java)
 <i>VertexContextWeightHeuristic</i> computes the Reese (1999) Epsilon-Admissible Weight Heuristic for use in the Alpha A<sup>*</sup> Heuristic Function.

 * [***VertexFunction***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/astar/VertexFunction.java)
 <i>VertexFunction</i> exposes the Value at a Vertex.


# References

 * Dechter, R., and J. Pearl (1985): Generalized Best-first Search Strategies and the Optimality of A<sup>*</sup> <i>Journal of the ACM</i> <b>32 (3)</b> 505-536

 * Hart, P. E., N. J. Nilsson, and B. Raphael (1968): A Formal Basis for the Heuristic Determination of the Minimum Cost Paths <i>IEEE Transactions on Systems Sciences and Cybernetics</i> <b>4 (2)</b> 100-107

 * Kagan, E., and I. Ben-Gal (2014): A Group Testing Algorithm with Online Informational Learning <i>IIE Transactions</i> <b>46 (2)</b> 164-184

 * Russell, S. J. and P. Norvig (2018): <i>Artificial Intelligence: A Modern Approach 4<sup>th</sup> Edition</i> <b>Pearson</b>

 * Wikipedia (2020): A<sup>+</sup> Search Algorithm https://en.wikipedia.org/wiki/A*_search_algorithm


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
