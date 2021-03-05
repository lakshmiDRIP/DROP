# DROP Execution Discrete Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Execution Discrete Package implements the Trajectory Slice Execution Cost Distribution.


## Class Components

 * [***Evolution Increment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/discrete/EvolutionIncrement.java)
 <i>EvolutionIncrement</i> contains the Realized Stochastic Evolution Increments of the Price/Short-fall
 exhibited by an Asset owing to the Volatility and the Market Impact Factors over the Slice Time Interval. It
 is composed of Stochastic and Deterministic Price Increment Components.

 * [***Optimal Serial Correlation Adjustment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/discrete/OptimalSerialCorrelationAdjustment.java)
 <i>OptimalSerialCorrelationAdjustment</i> contains an Estimate of the Optimal Adjustments attributable to
 Cross Period Serial Price Correlations over the Slice Time Interval.

 * [***Price Increment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/discrete/PriceIncrement.java)
 <i>PriceIncrement</i> contains the Realized Stochastic Evolution Increments of the Price Movements exhibited
 by an Asset owing to the Volatility and the Market Impact Factors over the Slice Time Interval. It is
 composed of Stochastic and Deterministic Price Increment Components.

 * [***Short-fall Increment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/discrete/ShortfallIncrement.java)
 <i>ShortfallIncrement</i> generates the Realized Incremental Stochastic Trading/Execution Short-fall and the
 corresponding Implementation Short-fall corresponding to the Trajectory of a Holdings Block that is to be
 executed over Time.

 * [***Short-fall Increment Distribution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/discrete/ShortfallIncrementDistribution.java)
 <i>ShortfallIncrementDistribution</i> holds the Parameters of the R<sup>1</sup> Normal Short fall Increment
 Distribution.

 * [***Slice***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/discrete/Slice.java)
 <i>Slice</i> implements the Arithmetic Dynamics of the Price/Cost Movements exhibited by an Asset owing to
 the Volatility and the Market Impact Factors on a Trajectory Slice.


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
 * Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
