# DROP Analytics Input Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Analytics Input Package contains the Curve Surface Construction Customization Inputs.


## Class Components

 * [***BootCurveConstructionInput***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/input/BootCurveConstructionInput.java)
 <i>BootCurveConstructionInput</i> contains the Parameters needed for the Curve Calibration/Estimation. It
 contains the following:
 	* Calibration Valuation Parameters
 	* Calibration Quoting Parameters
 	* Array of Calibration Instruments
 	* Map of Calibration Quotes
 	* Map of Calibration Measures
 	* Latent State Fixings Container

 * [***CurveConstructionInputSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/input/CurveConstructionInputSet.java)
 <i>CurveConstructionInputSet</i> interface contains the Parameters needed for the Curve
 Calibration/Estimation. It's methods expose access to the following:
 	* Calibration Valuation Parameters
 	* Calibration Quoting Parameters
 	* Array of Calibration Instruments
 	* Map of Calibration Quotes
 	* Map of Calibration Measures
 	* Double Map of the Date/Index Fixings

 * [***LatentStateShapePreservingCCIS***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/input/LatentStateShapePreservingCCIS.java)
 <i>LatentStateShapePreservingCCIS</i> contains the Parameters needed for the Curve Calibration/Estimation.
 It contains the following:
 	* Calibration Valuation Parameters
 	* Calibration Quoting Parameters
 	* Calibration Market Parameters
 	* Calibration Pricing Parameters
 	* Array of Calibration Span Representation
 	* Map of Calibration Quotes
 	* Map of Calibration Measures
 	* Double Map of the Date/Index Fixings
 	* The Shape Preserving Linear Latent State Calibrator

 Additional functions provide for retrieval of the above and specific instrument quotes.


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
