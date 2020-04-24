# DROP Sample Cross Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Sample Cross illustrates Single/Dual Stream XCCY Component Analytics.


## Class Components

 * [***CrossFixedPlainFloat***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/cross/CrossFixedPlainFloat.java)
 <i>CrossFixedPlainFloat</i> demonstrates the construction, usage, and eventual valuation of a fix-float swap
 with a EUR Fixed leg that pays in USD, and a USD Floating Leg. Comparison is done across MTM and non-MTM
 fixed Leg Counterparts.

 * [***CrossFixedPlainFloatAnalysis***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/cross/CrossFixedPlainFloatAnalysis.java)
 <i>CrossFixedPlainFloatAnalysis</i> demonstrates the impact of Funding Volatility, Forward Volatility, and
 Funding/Forward Correlation on the Valuation of a fix-float swap with a EUR Fixed leg that pays in USD, and
 a USD Floating Leg. Comparison is done across MTM and non-MTM fixed Leg Counterparts.

 * [***CrossFloatCrossFloat***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/cross/CrossFloatCrossFloat.java)
 <i>CrossFloatCrossFloat</i> demonstrates the construction, usage, and eventual valuation of the
 Mark-to-market float-float swap with a 3M EUR Floater leg that pays in USD, and a 6M EUR Floater leg that
 pays in USD. Comparison is done across MTM and non-MTM fixed Leg Counterparts.

 * [***CrossFloatCrossFloatAnalysis***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/cross/CrossFloatCrossFloatAnalysis.java)
 <i>CrossFloatCrossFloatAnalysis</i> demonstrates the impact of Funding Volatility, Forward Volatility, and
 Funding/Forward, Funding/FX, and Forward/FX Correlation for each of the FRI's on the Valuation of a
 float-float swap with a 3M EUR Floater leg that pays in USD, and a 6M EUR Floater leg that pays in USD.
 Comparison is done across MTM and non-MTM fixed Leg Counterparts.

 * [***FixFloatFixFloat***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/cross/FixFloatFixFloat.java)
 <i>FixFloatFixFloat</i> demonstrates the construction, the usage, and the eventual valuation of the Cross
 Currency Basis Swap built out of a pair of fix-float swaps.

 * [***FixFloatFixFloatAnalysis***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/cross/FixFloatFixFloatAnalysis.java)
 <i>FixFloatFixFloatAnalysis</i> demonstrates the Funding Volatility, Forward Volatility, FX Volatility,
 Funding/Forward Correlation, Funding/FX Correlation, and Forward/FX Correlation across the 2 currencies (USD
 and EUR) on the Valuation of the Cross Currency Basis Swap built out of a pair of fix-float swaps.

 * [***FloatFloatFloatFloat***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/cross/FloatFloatFloatFloat.java)
 <i>FloatFloatFloatFloat</i> demonstrates the construction, the usage, and the eventual valuation of the
 Cross Currency Basis Swap built out of a pair of float-float swaps.

 * [***FloatFloatFloatFloatAnalysis***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/cross/FloatFloatFloatFloatAnalysis.java)
 <i>FloatFloatFloatFloatAnalysis</i> demonstrates the Funding Volatility, Forward Volatility, FX Volatility,
 Funding/Forward Correlation, Funding/FX Correlation, and Forward/FX Correlation of the Cross Currency Basis
 Swap built out of a pair of float-float swaps.


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
