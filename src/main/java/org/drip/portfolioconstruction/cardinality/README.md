# DROP Portfolio Construction Cardinality Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Portfolio Construction Cardinality Package implements Portfolio Construction under Cardinality Bounds.


## Class Components

 * [***TadonkiVialHoldingsAllocation***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/cardinality/TadonkiVialHoldingsAllocation.java)
 <i>TadonkiVialHoldingsAllocation</i> holds the Results of the Allocation performed using the Tadonki and Vial (2004) Heuristic Scheme.

 * [***TadonkiVialMeanVarianceOptimizer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/cardinality/TadonkiVialMeanVarianceOptimizer.java)
 <i>TadonkiVialMeanVarianceOptimizer</i> builds an Optimal Portfolio Based on MPT Using the Asset Pool Statistical Properties with the Specified Lower/Upper Bounds on the Component Assets, along with an Upper Bound on Portfolio Cardinality, using the Tadonki and Vial (2004) Heuristic Scheme.

 * [***UpperBoundHoldingsAllocationControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/cardinality/UpperBoundHoldingsAllocationControl.java)
 <i>UpperBoundHoldingsAllocationControl</i> holds the Parameters needed to build the Portfolio with Bounds on the Underlying Assets as well as Portfolio Level Holdings Cardinality Constraint.


## References

 * Chang, T., J., N. Meade, J. E. Beasley, and Y. M. Sharaiha (2000): Heuristics for Cardinality Constrained Portfolio Optimization <i>Computers and Operations Research</i> <b>27 (13)</b> 1271-1302

 * Chvatal, V. (1973): Edmonds Polytopes in a Hierarchy of Combinatorial Problems <i>Discrete Mathematics</i> <b>4 (4)</b> 305-337

 * Jobst, N. J., M. D. Horniman, C. A. Lucas, and G. Mitra (2001): Computational Aspects of Alternative Portfolio Selection Models in the Presence of Discrete Asset Choice Constraints <i>Quantitative Finance</i> <b>1 (5)</b> 1-13

 * Letchford, A. N. and A. Lodi (2002): Strengthening Chvatal-Gomory Cuts and Gomory Fractional Cuts <i>Operations Research Letters</i> <b>30 (2)</b> 74-82

 * Tadonki, C., and J. P. Vial (2004): Portfolio Selection with Cardinality and Bound Constraints https://www.cri.ensmp.fr/~tadonki/PaperForWeb/Tadonki_PF.pdf


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
