# DROP LVaR Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP LVaR illustrates Estimating of the Liquidity VaR Based Optimal Trajectory.


## Class Components

 * [***OptimalTrajectoryNoDrift***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/lvar/OptimalTrajectoryNoDrift.java)
 <i>OptimalTrajectoryNoDrift</i> generates the Trade/Holdings List of Optimal Execution Schedule based on the
 Evolution Walk Parameters specified according to the Liquidity VaR Optimal Objective Function, exclusive of
 Drift. The Generation follows a Numerical Optimizer Scheme.

 * [***OptimalTrajectoryWithDrift***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/lvar/OptimalTrajectoryWithDrift.java)
 <i>OptimalTrajectoryWithDrift</i> generates the Trade/Holdings List of Optimal Execution Schedule based on
 the Evolution Walk Parameters specified according to the Liquidity VaR Optimal Objective Function, inclusive
 of Drift. The Generation follows a Numerical Optimizer Scheme.


## References

 * Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>

 * Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i> <b>3
 	(2)</b> 5-39

 * Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk <i>Applied
 	Mathematical Finance</i> <b>10 (1)</b> 1-18

 * Artzner, P., F. Delbaen, J. M. Eber, and D. Heath (1999): Coherent Measures of Risk <i>Mathematical
 	Finance</i> <b>9</b> 203-228

 * Basak, S., and A. Shapiro (2001): Value-at-Risk Based Risk Management: Optimal Policies and Asset Prices
 	<i>Review of Financial Studies</i> <b>14</b> 371-405


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
