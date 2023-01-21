# DROP Execution Trading Time Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Execution Trading Time Package implements the Coordinated Variation Trading Time Models.


## Class Components

 * [***Coordinated Market State***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/tradingtime/CoordinatedMarketState.java)
 <i>CoordinatedMarketState</i> implements the Coordinated Variation Version of the Volatility and the Linear
 Transaction Function arising from the Realization of the Market State Variable as described in the "Trading
 Time" Model.

 * [***Coordinated Participation Rate Linear***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/tradingtime/CoordinatedParticipationRateLinear.java)
 <i>CoordinatedParticipationRateLinear</i> implements the Coordinated Variation Version of the Linear
 Participation Rate Transaction Function as described in the "Trading Time" Model.

 * [***Coordinated Variation***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/tradingtime/CoordinatedVariation.java)
 <i>CoordinatedVariation</i> implements the Coordinated Variation of the Volatility and Liquidity as
 described in the "Trading Time" Model.

 * [***Volume Time Frame***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/tradingtime/VolumeTimeFrame.java)
 <i>VolumeTimeFrame</i> implements the Pre- and Post-transformed Increment in the Volume Time Space as used
 in the "Trading Time" Model.


# References

 * Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i>
 	<b>3 (2)</b> 5-39

 * Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 	https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf

 * Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility <i>SIAM Journal of
 	Financial Mathematics</i> <b>3 (1)</b> 163-181

 * Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes <i>Mathematical Finance</i>
 	<b>11 (1)</b> 79-96

 * Jones, C. M., G. Kaul, and M. L. Lipson (1994): Transactions, Volume, and Volatility <i>Review of
 	Financial Studies</i> <b>7 (4)</b> 631-651


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
