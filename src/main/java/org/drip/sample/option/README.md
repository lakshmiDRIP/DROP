# DROP Sample Option Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Sample Option demonstrates Deterministic (Black) / Stochastic (Heston) Option Valuation/Risk.


## Class Components

 * [***ATMTermStructureSpline***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/option/ATMTermStructureSpline.java)
 <i>ATMTermStructureSpline</i> contains an illustration of the Calibration and Extraction of the Deterministic ATM Price and Volatility Term Structures using Custom Splines. This does not deal with Local Volatility Surfaces.

 * [***BrokenDateVolSurface***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/option/BrokenDateVolSurface.java)
 <i>BlackHestonForwardOption</i> illustrates pricing a forward using the Black '76 variant and the Heston's stochastic Volatility Models.

 * [***BrokenDateVolSurface***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/option/BrokenDateVolSurface.java)
 <i>BrokenDateVolSurface</i> contains an illustration of the Construction and Usage of the Option Volatility Surface, and the Evaluation at the supplied Broken Dates.

 * [***CustomVolSurfaceBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/option/CustomVolSurfaceBuilder.java)
 <i>CustomVolSurfaceBuilder</i> contains an Comparison of the Construction of the Volatility Surface using different Splining Techniques.

 * [***DeterministicVolBlackScholes***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/option/DeterministicVolBlackScholes.java)
 <i>DeterministicVolBlackScholes</i> contains an illustration of the Black Scholes based European Call and Put Options Pricer that uses deterministic Volatility Function.

 * [***DeterministicVolatilityTermStructure***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/option/DeterministicVolatilityTermStructure.java)
 <i>DeterministicVolatilityTermStructure</i> contains an illustration of the Calibration and Extraction of the Implied and the Deterministic Volatility Term Structures. This does not deal with Local Volatility Surfaces.

 * [***LocalVolatilityTermStructure***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/option/LocalVolatilityTermStructure.java)
 <i>LocalVolatilityTermStructure</i> contains an illustration of the Calibration and Extraction of the Implied and the Local Volatility Surfaces and their eventual Strike and Maturity Anchor Term Structures.

 * [***MarketSurfaceTermStructure***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/option/MarketSurfaceTermStructure.java)
 <i>MarketSurfaceTermStructure</i> contains an illustration of the Creation and Usage of the Strike Anchored and Maturity Anchored Term Structures extracted from the given Market Surface.

 * [***VanillaBlackNormalPricing***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/option/VanillaBlackNormalPricing.java)
 <i>VanillaBlackNormalPricing</i> contains an illustration of the Vanilla Black Normal European Call and Put Options Pricer.

 * [***VanillaBlackScholesPricing***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/option/VanillaBlackScholesPricing.java)
 <i>VanillaBlackScholesPricing</i> contains an illustration of the Vanilla Black Scholes based European Call and Put Options Pricer.


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
