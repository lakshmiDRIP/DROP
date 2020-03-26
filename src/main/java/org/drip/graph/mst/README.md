# DROP Graph MST Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Graph MST computes the Agnostic Minimum Spanning Tree Properties.


## Class Components

 * [***CompleteRandomGraph***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/mst/CompleteRandomGraph.java)
 <i>CompleteRandomGraph</i> implements the Expected Size Metrics for a Complete Graph with Randomly Distributed Weights and non-zero Count of Vertexes.

 * [***CompleteRandomGraphEnsemble***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/mst/CompleteRandomGraphEnsemble.java)
 <i>CompleteRandomGraphEnsemble</i> implements the Ensemble of Complete Random Graphs.

 * [***SteeleCompleteUniformRandomEntry***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/mst/SteeleCompleteUniformRandomEntry.java)
 <i>SteeleCompleteUniformRandomEntry</i> holds a single Entry from the Expected MST Length Computation for Fully Connected Graphs with a small Number of Vertexes and Edge Weights that are i.i.d from U [0, 1].

 * [***SteeleCompleteUniformRandomMST***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/mst/SteeleCompleteUniformRandomMST.java)
 <i>SteeleCompleteUniformRandomMST</i> holds the Expected Length of the MST computed by Steele (2002) for Graphs with small Number of Vertexes.


# References

 * Bader, D. A., and G. Cong (2006): Fast Shared Memory Algorithms for computing the Minimum Spanning Forests of Sparse Graphs <i>Journal of Parallel and Distributed Computing</i> <b>66 (11)</b> 1366-1378

 * Chazelle, B. (2000): A Minimum Spanning Tree Algorithm with Inverse-Ackerman Type Complexity <i> Journal of the Association for Computing Machinery</i> <b>47 (6)</b> 1028-1047

 * Karger, D. R., P. N. Klein, and R. E. Tarjan (1995): A Randomized Linear-Time Algorithm to find Minimum Spanning Trees <i> Journal of the Association for Computing Machinery</i> <b>42 (2)</b> 321-328

 * Pettie, S., and V. Ramachandran (2002): An Optimal Minimum Spanning Tree <i>Algorithm Journal of the ACM</i> <b>49 (1)</b> 16-34

 * Wikipedia (2020): Minimum Spanning Tree https://en.wikipedia.org/wiki/Minimum_spanning_tree


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
