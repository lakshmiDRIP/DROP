# DROP Sample Almgren Chriss Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Sample Almgren Chriss illustrates Almgren Chriss Efficient Frontier Trajectories.


## Class Components

 * [***EfficientFrontierNoDrift***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgrenchriss/EfficientFrontierNoDrift.java)
 <i>EfficientFrontierNoDrift</i> constructs the Efficient Frontier over a Sequence of Risk Aversion
 Parameters for Optimal Trading Trajectories computed in accordance with the Specification of Almgren and
 Chriss (2000), and calculates the corresponding Execution Half Life and the Trajectory Penalty without
 regard to the Drift.

 * [***EfficientFrontierWithDrift***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgrenchriss/EfficientFrontierNoDrift.java)
 <i>EfficientFrontierWithDrift</i> constructs the Efficient Frontier over a Sequence of Risk Aversion
 Parameters for Optimal Trading Trajectories computed in accordance with the Specification of Almgren and
 Chriss (2000), and calculates the corresponding Execution Half Life and the Trajectory Penalty incorporating
 the Impact of Drift.

 * [***OptimalSerialCorrelationImpact***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgrenchriss/OptimalSerialCorrelationImpact.java)
 <i>OptimalSerialCorrelationImpact</i> estimates the Optimal Adjustment to the Optimal Trading Trajectory
 attributable to Serial Correlation in accordance with the Specification of Almgren and Chriss (2000) for the
 given Risk Aversion Parameter without the Asset Drift.

 * [***OptimalTrajectoryNoDrift***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgrenchriss/OptimalTrajectoryNoDrift.java)
 <i>OptimalTrajectoryNoDrift</i> demonstrates the Generation of the Optimal Trading Trajectory in accordance
 with the Specification of Almgren and Chriss (2000) for the given Risk Aversion Parameter without the Asset
 Drift.

 * [***OptimalTrajectoryWithDrift***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgrenchriss/OptimalTrajectoryWithDrift.java)
 <i>OptimalTrajectoryWithDrift</i> demonstrates the Generation of the Optimal Trading Trajectory in
 accordance with the Specification of Almgren and Chriss (2000) for the given Risk Aversion Parameter
 inclusive of the Asset Drift.

 * [***TrajectoryComparisonNoDrift***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgrenchriss/TrajectoryComparisonNoDrift.java)
 <i>TrajectoryComparisonNoDrift</i> compares different Optimal Trading Trajectories computed in accordance
 with the Specification of Almgren and Chriss (2000) for a Set of Risk Aversion Parameters, excluding the
 Asset Drift.

 * [***TrajectoryComparisonWithDrift***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgrenchriss/TrajectoryComparisonWithDrift.java)
 <i>TrajectoryComparisonWithDrift</i> compares different Optimal Trading Trajectories computed in accordance
 with the Specification of Almgren and Chriss (2000) for a Set of Risk Aversion Parameters, inclusive of the
 Asset Drift.


## References

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
 * Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
