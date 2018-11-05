# DROP Execution Parameters Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Execution Parameters Package contains the Empirical Market Impact Coefficients Calibration.

## Class Components

 * [***Arithmetic Price Dynamics Settings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/parameters/ArithmeticPriceDynamicsSettings.java)
 <i>ArithmeticPriceDynamicsSettings</i> contains the Arithmetic Price Evolution Dynamics Parameters used in
 the Almgren and Chriss (2000) Optimal Trajectory Generation Scheme.

 * [***Asset Flow Settings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/parameters/AssetFlowSettings.java)
 <i>AssetFlowSettings</i> contains the Asset's Market Flow Parameters that are determined empirically from
 Almgren, Thum, Hauptmann, and Li (2005), using the Parameterization of Almgren (2003).

 * [***Asset Transaction Settings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/parameters/AssetTransactionSettings.java)
 <i>AssetTransactionSettings</i> contains the Asset Transaction Settings Inputs used in the Construction of
 the Impact Parameters for the Almgren and Chriss (2000) Optimal Trajectory Generation Scheme.

 * [***Price Market Impact***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/parameters/PriceMarketImpact.java)
 <i>PriceMarketImpact</i> contains the Price Market Impact Inputs used in the Construction of the Impact
 Parameters for the Almgren and Chriss (2000) Optimal Trajectory Generation Scheme.

 * [***Price Market Impact Linear***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/parameters/PriceMarketImpactLinear.java)
 <i>PriceMarketImpactLinear</i> contains the Linear Price Market Impact Inputs used in the Construction of
 the Impact Parameters for the Almgren and Chriss (2000) Optimal Trajectory Generation Scheme.

 * [***Price Market Impact Power***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/parameters/PriceMarketImpactPower.java)
 <i>PriceMarketImpactPower</i> contains the Power Law based Price Market Impact Inputs used in the
 Construction of the Impact Parameters for the Almgren and Chriss (2000) Optimal Trajectory Generation
 Scheme.


# References

 * Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>

 * Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i>
 	<b>3 (2)</b> 5-39

 * Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk <i>Applied
 	Mathematical Finance</i> <b>10 (1)</b> 1-18

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
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
