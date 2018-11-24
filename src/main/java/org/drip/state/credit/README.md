# DROP State Credit Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP State Credit Package contains the Credit Latent State Curve Representation.


## Class Components

 * [***CreditCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/credit/CreditCurve.java)
 <i>CreditCurve</i> is the stub for the survival curve functionality. It extends the Curve object by exposing
 the following functions:
 	* Set of curve and market identifiers
 	* Recovery to a specific date/tenor, and effective recovery between a date interval
 	* Hazard Rate to a specific date/tenor, and effective hazard rate between a date interval
 	* Survival to a specific date/tenor, and effective survival between a date interval
 	* Set/unset date of specific default
 	* Generate scenario curves from the base credit curve (flat/parallel/custom)
 	* Set/unset the Curve Construction Inputs, Latent State, and the Manifest Metrics
 	* Serialization/De-serialization to and from Byte Arrays

 * [***ExplicitBootCreditCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/credit/ExplicitBootCreditCurve.java)
 <i>ExplicitBootCreditCurve</i> exposes the functionality associated with the bootstrapped Credit Curve.


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
