# DROP Param Definition Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Param Definition Package implements the Functionality for Latent State Quantification Metrics Tweak.


## Class Components

 * [***CalibrationParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/definition/CalibrationParams.java)
 <i>CalibrationParams</i> the calibration parameters - the measure to be calibrated, the type/nature of the
 calibration to be performed, and the work-out date to which the calibration is done.

 * [***CreditManifestMeasureTweak***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/definition/CreditManifestMeasureTweak.java)
 <i>CreditManifestMeasureTweak</i> contains the place holder for the credit curve scenario tweak parameters:
 in addition to the ResponseValueTweakParams fields, this exposes the calibration manifest measure, the curve
 node, and the nodal calibration type (entire curve/flat or a given tenor point).

 * [***ManifestMeasureTweak***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/definition/ManifestMeasureTweak.java)
 <i>ManifestMeasureTweak</i> contains the place holder for the scenario tweak parameters, for either a
 specific curve node, or the entire curve (flat). Parameter bumps can be parallel or proportional.

 * [***ProductQuote***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/definition/ProductQuote.java)
 <i>ProductQuote</i> abstract class holds the different types of quotes for a given product. It contains a
 single market field/quote pair, but multiple alternate named quotes (to accommodate quotes on different
 measures for the component).

 * [***Quote***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/definition/Quote.java)
 <i>Quote</i> interface contains the stubs corresponding to a product quote. It contains the quote value,
 quote instant for the different quote sides (bid/ask/mid).

 * [***ScenarioMarketParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/definition/ScenarioMarketParams.java)
 <i>ScenarioMarketParams</i> is the place holder for the comprehensive suite of the market set of curves for
 the given date. It exports the following functionality:
 	* Add/remove/retrieve scenario discount curve
  	* Add/remove/retrieve scenario forward curve
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
 * Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
