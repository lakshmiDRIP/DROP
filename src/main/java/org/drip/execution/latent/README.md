# DROP Execution Latent Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Execution Latent Package generates the Correlated Latent Market State Sequence.


## Class Components

 * [***Market State***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/latent/MarketState.java)
 <i>MarketState</i> holds the Random Market State(s) that control(s) the Cost Evolution and the Eventual
 Optimal Trajectory Generation.

 * [***Market State Correlated***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/latent/MarketStateCorrelated.java)
 <i>MarketStateCorrelated</i> holds the Correlated Market State that drives the Liquidity and the Volatility
 Market States separately.

 * [***Market State Systemic***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/latent/MarketStateSystemic.java)
 <i>MarketStateSystemic</i> holds the Single Systemic Market State that drives both the Liquidity and the
 Volatility Market States.

 * [***Ornstein-Uhlenbeck Sequence***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/latent/OrnsteinUhlenbeckSequence.java)
 <i>OrnsteinUhlenbeckSequence</i> holds the Sequence of the Market State that drives the Liquidity and the
 Volatility Market States driven using an Ornstein-Uhlenbeck Process.


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
