# DROP Param Market Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Param Market Package implements the Curves Surfaces Quotes Fixings Container.


## Class Components

 * [***CreditCurveScenarioContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/market/CreditCurveScenarioContainer.java)
 <i>CreditCurveScenarioContainer</i> contains the place holder for the bump parameters and the curves for the
 different credit curve scenarios. Contains the spread and the recovery bumps, and the credit curve scenario
 generator object that wraps the calibration instruments. It also contains the base credit curve, spread
 bumped up-down credit curves, recovery bumped up-down credit curves, and the tenor mapped up-down credit
 curves.

 * [***CurveSurfaceScenarioContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/market/CurveSurfaceScenarioContainer.java)
 <i>CurveSurfaceScenarioContainer</i> extends MarketParams abstract class, and is the place holder for the
 comprehensive suite of the market set of curves for the given date. It exports the following functionality.
 	* Add/remove/retrieve scenario discount curve
 	* Add/remove/retrieve scenario Forward curve
 	* Add/remove/retrieve scenario zero curve
 	* Add/remove/retrieve scenario credit curve
 	* Add/remove/retrieve scenario recovery curve
 	* Add/remove/retrieve scenario FXForward curve
 	* Add/remove/retrieve scenario FXBasis curve
 	* Add/remove/retrieve scenario fixings
 	* Add/remove/retrieve Treasury/component quotes
 	* Retrieve scenario Market Parameters
 	* Retrieve map of flat rates/credit/recovery Market Parameters
 	* Retrieve double map of tenor rates/credit/recovery Market Parameters
 	* Retrieve rates/credit scenario generator


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
