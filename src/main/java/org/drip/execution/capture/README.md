# DROP Execution Capture Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Execution Capture Package contains the Execution Trajectory Transaction Cost Capture.

## Class Components

 * [***Linear Impact Block Trajectory Estimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/capture/LinearImpactBlockTrajectoryEstimator.java)
 <i>LinearImpactBlockTrajectoryEstimator</i> estimates the Price/Cost Distribution associated with the Single
 Block Trading Trajectory generated using the Linear Market Impact Evolution Parameters.

 * [***Linear Impact Trajectory Estimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/capture/LinearImpactTrajectoryEstimator.java)
 <i>LinearImpactTrajectoryEstimator</i> estimates the Price/Cost Distribution associated with the Trading
 Trajectory generated using the Linear Market Impact Evolution Parameters.

 * [***Linear Impact Uniform Trajectory Estimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capture/bayesian/LinearImpactUniformTrajectoryEstimator.java)
 <i>LinearImpactUniformTrajectoryEstimator</i> estimates the Price/Cost Distribution associated with the
 Uniform Trading Trajectory generated using the Linear Market Impact Evolution Parameters.

 * [***Trajectory Short-fall Aggregate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/capture/TrajectoryShortfallAggregate.java)
 <i>TrajectoryShortfallAggregate</i> aggregates the  Execution Short-fall Distribution across each Interval
 in the Trade.

 * [***Trajectory Short-fall Estimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/capture/TrajectoryShortfallEstimator.java)
 <i>TrajectoryShortfallEstimator</i> estimates the Price/Short Fall Distribution associated with the Trading
 Trajectory generated using the specified Evolution Parameters.

 * [***Trajectory Short-fall Realization***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/capture/TrajectoryShortfallRealization.java)
 <i>TrajectoryShortfallRealization</i> holds Execution Cost Realization across each Interval in the Trade
 during a Single Simulation Run.


# References

 * Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>

 * Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i> <b>3
 	(2)</b> 5-39

 * Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial Markets</i>
 	<b>1</b> 1-50

 * Chan, L. K. C., and J. Lakonishak (1995): The Behavior of Stock Prices around Institutional Trades
 	<i>Journal of Finance</i> <b>50</b> 1147-1174

 * Keim, D. B., and A. Madhavan (1997): Transaction Costs and Investment Style: An Inter-exchange Analysis of
 	Institutional Equity Trades <i>Journal of Financial Economics</i> <b>46</b> 265-292


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
