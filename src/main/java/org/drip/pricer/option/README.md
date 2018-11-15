# DROP Pricer Option Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Pricer Option Package implements the Deterministic/Stochastic Volatility Algorithms/Greeks.


## Class Components

 * [***BlackNormalAlgorithm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pricer/option/BlackNormalAlgorithm.java)
 <i>BlackNormalAlgorithm</i> implements the Black Normal European Call and Put Options Pricer.

 * [***BlackScholesAlgorithm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pricer/option/BlackScholesAlgorithm.java)
 <i>BlackScholesAlgorithm</i> implements the Black Scholes based European Call and Put Options Pricer.

 * [***FokkerPlanckGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pricer/option/FokkerPlanckGenerator.java)
 <i>FokkerPlanckGenerator</i> holds the base functionality that the performs the PDF evolution oriented
 Option Pricing.

 * [***Greeks***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pricer/option/Greeks.java)
 <i>Greeks</i> contains the Sensitivities/Pricing Measures common across both Call and Put Option Pricing
 Runs.

 * [***HestonStochasticVolatilityAlgorithm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pricer/option/HestonStochasticVolatilityAlgorithm.java)
 <i>HestonStochasticVolatilityAlgorithm</i> implements the Heston 1993 Stochastic Volatility European Call
 and Put Options Pricer.

 * [***PutGreeks***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pricer/option/PutGreeks.java)
 <i>PutGreeks</i> contains the Sensitivities generated during the Put Option Pricing Run.


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
