# DROP Execution Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Execution illustrates Nonlinear Trading Enhanced Market Impact.


## Class Components

 * [***AlmgrenConstantTradingEnhanced***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/execution/AlmgrenConstantTradingEnhanced.java)
 <i>AlmgrenConstantTradingEnhanced</i> demonstrates the Generation of the Optimal Trading Trajectory under
 the Condition of Constant Trading Enhanced Volatility using a Numerical Optimization Technique.

 * [***AlmgrenLinearTradingEnhanced***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/execution/AlmgrenLinearTradingEnhanced.java)
 <i>AlmgrenLinearTradingEnhanced</i> demonstrates the Generation of the Optimal Trading Trajectory under the
 Condition of Linear Trading Enhanced Volatility using a Numerical Optimization Technique.

 * [***ConcaveImpactNoDrift***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/execution/ConcaveImpactNoDrift.java)
 <i>ConcaveImpactNoDrift</i> generates the Trade/Holdings List of Optimal Execution Schedule based on the
 Concave Power Law Evolution Walk Parameters specified. The Generation follows a Numerical Optimizer Scheme,
 as opposed to the Closed Form; it also excludes the Impact of Drift.

 * [***LinearImpactNoDrift***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/execution/LinearImpactNoDrift.java)
 <i>LinearImpactNoDrift</i> generates the Trade/Holdings List of Optimal Execution Schedule based on the
 Evolution Walk Parameters specified. The Generation follows a Numerical Optimizer Scheme, as opposed to the
 Almgren-Chriss Closed Form; it also excludes the Impact of Drift.

 * [***LinearImpactWithDrift***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/execution/LinearImpactWithDrift.java)
 <i>LinearImpactWithDrift</i> generates the Trade/Holdings List of Optimal Execution Schedule based on the
 Evolution Walk Parameters specified. The Generation follows a Numerical Optimizer Scheme, as opposed to the
 Almgren-Chriss Closed Form; it includes the Impact of Drift.


## References

 * Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>

 * Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i> <b>3
 	(2)</b> 5-39

 * Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk <i>Applied
 	Mathematical Finance</i> <b>10 (1)</b> 1-18

 * Almgren, R., and N. Chriss (2003): Bidding Principles <i>Risk</i> 97-102

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
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
