# DROP Execution Impact Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Execution Impact Package contains the Market Impact Transaction Function Implementation.


## Class Components

 * [***Participation Rate Linear***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/impact/ParticipationRateLinear.java)
 <i>ParticipationRateLinear</i> implements a Linear Temporary/Permanent Market Impact Function where the
 Price Change scales linearly with the Trade Rate, along with an Offset.

 * [***Participation Rate Power***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/impact/ParticipationRatePower.java)
 <i>ParticipationRatePower</i> implements a Power-Law Based Temporary/Permanent Market Impact Function where
 the Price Change scales as a Power of the Trade Rate.

 * [***Transaction Function***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/impact/TransactionFunction.java)
 <i>TransactionFunction</i> exports the Temporary/Permanent Market Impact Displacement/Volatility Functional
 Dependence on the Trade Rate.

 * [***Transaction Function Linear***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/impact/TransactionFunctionLinear.java)
 <i>TransactionFunctionLinear</i> exposes the Linear Impact Function Stubs as defined in Almgren and Chriss
 (2000) and Almgren (2003).

 * [***Transaction Function Power***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/impact/TransactionFunctionPower.java)
 <i>TransactionFunctionPower</i> exposes the Power Law Impact Function Stubs as defined in Almgren and Chriss
 (2000) and Almgren (2003).


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
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
