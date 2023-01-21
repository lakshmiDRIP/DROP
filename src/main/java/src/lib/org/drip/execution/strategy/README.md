# DROP Execution Strategy Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Execution Strategy Package implements the Discrete/Continuous Trading Trajectory Schedule.


## Class Components

 * [***Continuous Trading Trajectory***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/strategy/ContinuousTradingTrajectory.java)
 <i>ContinuousTradingTrajectory</i> holds the Continuous Trajectory of a Trading Block that is to be executed
 over the Specified Horizon.

 * [***Discrete Trading Trajectory***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/strategy/DiscreteTradingTrajectory.java)
 <i>DiscreteTradingTrajectory</i> holds the Trajectory of a Trading Block that is to be executed over a
 Discrete Time Set.

 * [***Discrete Trading Trajectory Control***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/strategy/DiscreteTradingTrajectoryControl.java)
 <i>DiscreteTradingTrajectoryControl</i> holds the Time Trajectory Control Settings of a Trading Block that
 is to be executed over a Discrete Time Sequence.

 * [***Minimum Impact Trading Trajectory***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/strategy/MinimumImpactTradingTrajectory.java)
 <i>MinimumImpactTradingTrajectory</i> holds the Trajectory of a Trading Block that is to be executed
 uniformly over Equal Intervals, the Idea being to minimize the Trading Impact.

 * [***Minimum Variance Trading Trajectory***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/strategy/MinimumVarianceTradingTrajectory.java)
 <i>MinimumVarianceTradingTrajectory</i> holds the Trajectory of a Trading Block that is to be executed in
 a Single Block, the Idea being to minimize the Trading Variance.

 * [***Order Specification***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/strategy/OrderSpecification.java)
 <i>OrderSpecification</i> contains the Parameters that constitute an Order, namely the Size and the
 Execution Time.

 * [***Trading Trajectory***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/strategy/TradingTrajectory.java)
 <i>TradingTrajectory</i> holds the Continuous/Discrete Trajectory of a Trading Block that is to be executed
 over a Discrete Time Set.


# References

 * Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>

 * Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i>
 	<b>3 (2)</b> 5-39

 * Almgren, R., and N. Chriss (2003): Optimal Execution with Nonlinear Impact Functions and Trading-
 	Enhanced Risk <i>Applied Mathematical Finance</i> <b>10 (1)</b> 1-18

 * Almgren, R., and N. Chriss (2003): Bidding Principles <i>Risk</i> 97-102

 * Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact <i>Risk</i> <b>18 (7)</b> 57-62

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
