# DROP Execution Bayesian Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Execution Bayesian Package implements the Bayesian Price Based Optimal Execution.


## Class Components

 * [***Conditional Price Distribution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/bayesian/ConditionalPriceDistribution.java)
 <i>ConditionalPriceDistribution</i> holds the Price Distribution Conditional on a given Drift.

 * [***Prior Conditional Combiner***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/bayesian/PriorConditionalCombiner.java)
 <i>PriorConditionalCombiner</i> holds the Distributions associated with the Prior Drift and the Conditional
 Price Distributions. It uses them to generate the resulting Joint, Posterior, and MAP Implied Posterior
 Distributions.

 * [***Prior Drift Distribution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/bayesian/PriorDriftDistribution.java)
 <i>PriorDriftDistribution</i> holds the Prior Belief Distribution associated with the Directional Drift.


# References

 * Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i> <b>3
 	(2)</b> 5-39

 * Almgren, R., and J. Lorenz (2006): Bayesian Adaptive Trading with a Daily Cycle <i>Journal of Trading</i>
 	<b>1 (4)</b> 38-46

 * Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial Markets</i>
 	<b>1</b> 1-50

 * Brunnermeier, L. K., and L. H. Pedersen (2005): Predatory Trading <i>Journal of Finance</i> <b>60 (4)</b>
 	1825-1863

 * Kissell, R., and R. Malamut (2007): Algorithmic Decision Making Framework <i>Journal of Trading</i> <b>1
 	(1)</b> 12-21


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
