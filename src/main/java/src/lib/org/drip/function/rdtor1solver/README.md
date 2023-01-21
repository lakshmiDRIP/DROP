# DROP Function R<sup>d</sup> To R<sup>1</sup> Solver Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Function R<sup>d</sup> To R<sup>1</sup> Solver Package implements the Suite of Built-in R<sup>d</sup> To R<sup>1</sup> Solvers.


## Class Components

 * [***BarrierFixedPointFinder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1solver/BarrierFixedPointFinder.java)
 <i>BarrierFixedPointFinder</i> invokes the Iterative Finders for locating the Fixed Point of R<sup>d</sup>
 To R<sup>1</sup> Convex/Non-Convex Functions Under Inequality Constraints using Barrier Sequences of
 decaying Strengths.

 * [***ConstraintFunctionPointMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1solver/ConstraintFunctionPointMetrics.java)
 <i>ConstraintFunctionPointMetrics</i> holds the R<sup>d</sup> Point Base and Sensitivity Metrics of the
 Constraint Function.

 * [***ConvergenceControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1solver/ConvergenceControl.java)
 <i>ConvergenceControl</i> contains the R<sup>d</sup> To R<sup>1</sup> Convergence Control/Tuning Parameters.

 * [***FixedRdFinder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1solver/FixedRdFinder.java)
 <i>FixedRdFinder</i> exports the Methods needed for the locating a Fixed R<sup>d</sup> Point.

 * [***InteriorFixedPointFinder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1solver/InteriorFixedPointFinder.java)
 <i>InteriorFixedPointFinder</i> generates the Iterators for solving R<sup>d</sup> To R<sup>1</sup>
 Convex/Non-Convex Functions Under Inequality Constraints loaded using a Barrier Coefficient.

 * [***InteriorPointBarrierControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1solver/InteriorPointBarrierControl.java)
 <i>InteriorPointBarrierControl</i> contains the Barrier Iteration Control Parameters.

 * [***NewtonFixedPointFinder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1solver/NewtonFixedPointFinder.java)
 <i>NewtonFixedPointFinder</i> generates the Iterators for solving R<sup>d</sup> To R<sup>1</sup>
 Convex/Non-Convex Functions Using the Multivariate Newton Method.

 * [***ObjectiveFunctionPointMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1solver/ObjectiveFunctionPointMetrics.java)
 <i>ObjectiveFunctionPointMetrics</i> holds the R<sup>d</sup> Point Base and Sensitivity Metrics of the
 Objective Function.

 * [***VariateInequalityConstraintMultiplier***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1solver/VariateInequalityConstraintMultiplier.java)
 <i>VariateInequalityConstraintMultiplier</i> holds the Variates and their Inequality Constraint Multipliers
 in either the Absolute or the Incremental Forms.


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
