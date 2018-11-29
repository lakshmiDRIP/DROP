# DROP Optimization Constrained Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Optimization Constrained implements the KKT Fritz-John Constrained Optimizer Framework.


## Class Components

 * [***FritzJohnMultipliers***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/constrained/FritzJohnMultipliers.java)
 <i>FritzJohnMultipliers</i> holds the Array of the Fritz John/KKT Multipliers for the Array of the Equality
 and the Inequality Constraints, one per each Constraint.

 * [***NecessarySufficientConditions***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/constrained/NecessarySufficientConditions.java)
 <i>NecessarySufficientConditions</i> holds the Results of the Verification of the Necessary and the
 Sufficient Conditions at the specified (possibly) Optimal Variate and the corresponding Fritz John
 Multiplier Suite.

 * [***OptimizationFramework***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/constrained/OptimizationFramework.java)
 <i>OptimizationFramework</i> holds the Non Linear Objective Function and the Collection of Equality and the
 Inequality Constraints that correspond to the Optimization Setup.

 * [***RegularityConditions***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/constrained/RegularityConditions.java)
 <i>RegularityConditions</i> holds the Results of the Verification of the Regularity Conditions/Constraint
 Qualifications at the specified (possibly) Optimal Variate and the corresponding Fritz John Multipliers.


## References

 * Boyd, S., and L. van den Berghe (2009): <i>Convex Optimization</i> <b>Cambridge University Press</b>
 Cambridge UK

 * Eustaquio, R., E. Karas, and A. Ribeiro (2008): <i>Constraint Qualification for Nonlinear Programming</i>
 <b>Federal University of Parana</b>

 * Karush, A. (1939): <i>Minima of Functions of Several Variables with Inequalities as Side Constraints</i>
 <b>University of Chicago</b> Chicago IL

 * Kuhn, H. W., and A. W. Tucker (1951): Nonlinear Programming <i>Proceedings of the Second Berkeley
 Symposium</i> <b>University of California</b> Berkeley CA 481-492

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
