# DROP State Boot Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP State Boot Package implements the Bootable Discount, Credit, and Volatility States.


## Class Components

 * [***CreditCurveScenario***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/boot/CreditCurveScenario.java)
 <i>CreditCurveScenario</i> uses the hazard rate calibration instruments along with the component calibrator
 to produce scenario hazard rate curves. CreditCurveScenario typically first constructs the actual curve
 calibrator instance to localize the intelligence around curve construction. It then uses this curve
 calibrator instance to build individual curves or the sequence of node bumped scenario curves. The curves in
 the set may be an array, or tenor-keyed.

 * [***DiscountCurveScenario***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/boot/DiscountCurveScenario.java)
 <i>DiscountCurveScenario</i> uses the interest rate calibration instruments along with the component
 calibrator to produce scenario interest rate curves. DiscountCurveScenario typically first constructs the
 actual curve calibrator instance to localize the intelligence around curve construction. It then uses this
 curve calibrator instance to build individual curves or the sequence of node bumped scenario curves. The
 curves in the set may be an array, or tenor-keyed.

 * [***VolatilityCurveScenario***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/boot/VolatilityCurveScenario.java)
 <i>VolatilityCurveScenario</i> uses the Volatility calibration instruments along with the component
 calibrator to produce scenario Volatility curves.


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
