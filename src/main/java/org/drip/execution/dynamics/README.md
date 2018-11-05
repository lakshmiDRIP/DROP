# DROP Execution Dynamics Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Execution Dynamics Package contains the Arithmetic Price Evolution Execution Parameters.

## Class Components

 * [***Arithmetic Price Evolution Parameters***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/dynamics/ArithmeticPriceEvolutionParameters.java)
 <i>ArithmeticPriceEvolutionParameters</i> contains the Exogenous Parameters that determine the Dynamics of
 the Arithmetic Price Movements exhibited by an Asset owing to the Volatility and the Market Impact Factors.

 * [***Arithmetic Price Evolution Parameters Builder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/dynamics/ArithmeticPriceEvolutionParametersBuilder.java)
 <i>ArithmeticPriceEvolutionParametersBuilder</i> constructs a variety of Arithmetic Price Evolution
 Parameters.

 * [***Linear Permanent Expectation Parameters***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/dynamics/LinearPermanentExpectationParameters.java)
 <i>LinearPermanentExpectationParameters</i> implements a Permanent Market Impact Function where the Price
 Change scales linearly with the Trade Rate.

 * [***Walk Suite***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/dynamics/WalkSuite.java)
 <i>WalkSuite</i> holds the Walk Random Variables (e.g., Weiner Variates) that correspond to an Instance of
 Walk attributable to different Factor Contributions inside of a Slice Increment.


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
