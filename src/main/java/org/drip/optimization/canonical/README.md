# DROP Linear Program Canonical Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Linear Program Canonical Package implements Polyhedral Cutting Plane Generation Schemes.


## Class Components

 * [***ILP***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/linearprogram/canonical/ILP.java)
 <i>ILP</i> holds the Objective and the Constraint Terms of an Integer Linear Program.

 * [***ILPConstraint***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/linearprogram/canonical/ILPConstraint.java)
 <i>ILPConstraint</i> holds the Constraint Matrix LHS and Constraint Array RHS for an Integer Linear Program Ax lte B, where A is Z<sup>m x n</sup>, B is Z<sup>m</sup>, and x is Z<sub>+</sub><sup>n</sup>.

 * [***ILPObjective***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/linearprogram/canonical/ILPObjective.java)
 <i>ILPObjective</i> holds the Coefficients of the Objective Term of an Integer Linear Program c<sup>T</sup>x where c is R<sup>n</sup> and x is Z<sub>+</sub><sup>n</sup>.


## References

 * Boyd, S., and L. van den Berghe (2009): <i>Convex Optimization</i> <b>Cambridge University Press</b>
 Cambridge UK

 * Burdet, C. A., and E. L. Johnson (1977): A Sub-additive Approach to Solve Linear Integer Programs <i>Annals of Discrete Mathematics</i> <b>1</b> 117-143

 * Chvatal, V. (1973): Edmonds Polytopes in a Hierarchy of Combinatorial Problems <i>Discrete Mathematics</i> <b>4 (4)</b> 305-337

 * Eustaquio, R., E. Karas, and A. Ribeiro (2008): <i>Constraint Qualification for Nonlinear Programming</i>
 <b>Federal University of Parana</b>

 * Gomory, R. E. (1958): Outline of an Algorithm for Integer Solutions to Linear Programs <i>Bulletin of the American Mathematical Society</i> <b>64 (5)</b> 275-278

 * Karush, A. (1939): <i>Minima of Functions of Several Variables with Inequalities as Side Constraints</i>
 <b>University of Chicago</b> Chicago IL

 * Kelley, J. E. (1960): The Cutting Plane Method for Solving Convex Problems <i>Journal for the Society of the Industrial and Applied Mathematics</i> <b>8 (4)</b> 703-712

 * Kuhn, H. W., and A. W. Tucker (1951): Nonlinear Programming <i>Proceedings of the Second Berkeley
 Symposium</i> <b>University of California</b> Berkeley CA 481-492

 * Letchford, A. N. and A. Lodi (2002): Strengthening Chvatal-Gomory Cuts and Gomory Fractional Cuts <i>Operations Research Letters</i> <b>30 (2)</b> 74-82

 * Ruszczynski, A. (2006): <i>Nonlinear Optimization</i> <b>Princeton University Press</b> Princeton NJ


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
