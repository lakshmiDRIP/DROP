# DROP State Creator Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP State Creator Package implements the Scenario State Curve/Surface Builders.


## Class Components

 * [***ScenarioBasisCurveBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator/ScenarioBasisCurveBuilder.java)
 <i>ScenarioBasisCurveBuilder</i> implements the construction of the scenario basis curve using the input
 instruments and their quotes.

 * [***ScenarioCreditCurveBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator/ScenarioCreditCurveBuilder.java)
 <i>ScenarioCreditCurveBuilder</i> implements the construction of the custom Scenario based credit curves.

 * [***ScenarioDeterministicVolatilityBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator/ScenarioDeterministicVolatilityBuilder.java)
 <i>ScenarioDeterministicVolatilityBuilder</i> implements the construction of the basis spline deterministic
 volatility term structure using the input instruments and their quotes.

 * [***ScenarioDiscountCurveBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator/ScenarioDiscountCurveBuilder.java)
 <i>ScenarioDiscountCurveBuilder</i> implements the the construction of the scenario discount curve using the
 input discount curve instruments, and a wide variety of custom builds. It implements the following
 functionality:
 	* Non-linear Custom Discount Curve
 	* Shape Preserving Discount Curve Builds - Standard Cubic Polynomial/Cubic KLK Hyperbolic Tension, and
 		other Custom Builds
 	* Smoothing Local/Control Custom Build - DC/Forward/Zero Rate LSQM's
 	* "Industry Standard Methodologies" - DENSE/DUALDENSE/CUSTOMDENSE and Hagan-West Forward Interpolator
 		Schemes

 * [***ScenarioForwardCurveBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator/ScenarioForwardCurveBuilder.java)
 <i>ScenarioForwardCurveBuilder</i> implements the the construction of the scenario Forward curve using the
 input discount curve instruments, and a wide variety of custom builds. It implements the following
 functionality:
 	* Non-linear Custom Discount Curve
 	* Shape Preserving Discount Curve Builds - Standard Cubic Polynomial/Cubic KLK Hyperbolic Tension, and
 		other Custom Builds
 	* Smoothing Local/Control Custom Build - DC/Forward/Zero Rate LSQM's
 	* "Industry Standard Methodologies" - DENSE/DUALDENSE/CUSTOMDENSE and Hagan-West Forward Interpolator
 		Schemes

 * [***ScenarioFXCurveBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator/ScenarioFXCurveBuilder.java)
 <i>ScenarioFXCurveBuilder</i> implements the construction of the scenario FX Curve using the input FX Curve
 instruments.

 * [***ScenarioGovvieCurveBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator/ScenarioGovvieCurveBuilder.java)
 <i>ScenarioGovvieCurveBuilder</i> implements the Construction of the Scenario Govvie Curve using the Input
 Govvie Curve Instruments.

 * [***ScenarioLocalVolatilityBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator/ScenarioLocalVolatilityBuilder.java)
 <i>ScenarioLocalVolatilityBuilder</i> implements the construction of the Local Volatility surface using the
 input option instruments, their Call Prices, and a wide variety of custom build schemes.

 * [***ScenarioMarketSurfaceBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator/ScenarioMarketSurfaceBuilder.java)
 <i>ScenarioMarketSurfaceBuilder</i> implements the construction of the scenario market Node surface using
 the input option instruments, their quotes, and a wide variety of custom builds.

 * [***ScenarioRepoCurveBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator/ScenarioRepoCurveBuilder.java)
 <i>ScenarioRepoCurveBuilder</i> implements the Construction of the Scenario Repo Curve using the Input
 Instruments and their Quotes.

 * [***ScenarioTermStructureBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator/ScenarioTermStructureBuilder.java)
 <i>ScenarioTermStructureBuilder</i> implements the construction of the basis spline term structure using the
 input instruments and their quotes.


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
