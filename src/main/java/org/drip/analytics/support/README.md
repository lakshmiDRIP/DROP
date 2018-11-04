# DROP Analytics Support Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Analytics Support Package contains Assorted Support and Helper Utilities.


## Class Components

 * [***CaseInsensitiveHashMap***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/support/CaseInsensitiveHashMap.java)
 <i>CaseInsensitiveHashMap</i> implements a Case Insensitive Key in a Hash Map.

 * [***CaseInsensitiveTreeMap***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/support/CaseInsensitiveTreeMap.java)
 <i>CaseInsensitiveTreeMap</i> implements a Case Insensitive Key in a Tree Map.

 * [***ForwardDecompositionUtil***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/support/ForwardDecompositionUtil.java)
 <i>ForwardDecompositionUtil</i> contains the utility functions needed to carry out periodic decomposition at
 MTM sync points for the given stream.

 * [***FuturesHelper***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/support/FuturesHelper.java)
 <i>FuturesHelper</i> contains the Collection of the Futures Valuation related Utility Functions.

 * [***Helper***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/support/Helper.java)
 <i>Helper</i> contains the collection of the analytics related utility functions used by the modules.
 Following are some of the functionality that it exposes:
 	* Yield to Discount Factor, and vice versa
 	* Map Bloomberg Day Count Codes to Credit Analytics Day Count Codes
  	* Generate rule-based curve node manifest measure bumps
  	* Generate loss periods using a variety of different schemes
  	* Aggregate/disaggregate/merge coupon period lists
  	* Create fixings objects, rate index from currency/coupon/frequency
  	* String Tenor/Month Code/Work-out
  	* Standard Treasury Bench-mark off of Maturity

 * [***Logger***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/support/Logger.java)
 <i>Logger</i> implements level-set logging, backed by either the screen or a file. Logging always includes
 time-stamps, and happens according to the level requested.

 * [***LossQuadratureGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/support/LossQuadratureGenerator.java)
 <i>LossQuadratureGenerator</i> generates the decomposed Integrand Quadrature for the Loss Steps.

 * [***OptionHelper***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/support/OptionHelper.java)
 <i>OptionHelper</i> contains the collection of the option valuation related utility functions used by the
 modules.

 * [***VertexDateBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/support/VertexDateBuilder.java)
 <i>VertexDateBuilder</i> exports Static Functions that create Vertex Dates using different Schemes.


## References

 * Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs <i>Risk</i> <b>24 (12)</b> 82-87
 
 * Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk and
 	Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19

 * Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75

 * Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b> 86-90

 * Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 <i>Risk</i> <b>21 (2)</b> 97-102


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
