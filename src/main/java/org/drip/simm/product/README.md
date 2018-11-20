# DROP SIMM Product Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP SIMM Product Package contains the ISDA SIMM Risk Factor Sensitivities.


## Class Components

 * [***BucketSensitivity***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product/BucketSensitivity.java)
 <i>BucketSensitivity</i> holds the Risk Factor Sensitivities inside a single Bucket.

 * [***BucketSensitivityCR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product/BucketSensitivityCR.java)
 <i>BucketSensitivityCR</i> holds the ISDA SIMM Risk Factor Tenor Bucket Sensitivities across CR Tenor
 Factors.

 * [***BucketSensitivityIR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product/BucketSensitivityIR.java)
 <i>BucketSensitivityIR</i> holds the ISDA SIMM Risk Factor Tenor Bucket Sensitivities across IR Factor Sub
 Curves. USD Exposures enhanced with the USD specific Sub-Curve Factors - PRIME and MUNICIPAL.

 * [***CreditEntity***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product/CreditEntity.java)
 <i>CreditEntity</i> holds the SIMM specific Details of a Credit Entity.

 * [***RiskClassSensitivity***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product/RiskClassSensitivity.java)
 <i>RiskClassSensitivity</i> holds the Risk Class Bucket Sensitivities for a single Risk Class.

 * [***RiskClassSensitivityCR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product/RiskClassSensitivityCR.java)
 <i>RiskClassSensitivityCR</i> holds the Risk Class Bucket Sensitivities for a single CR Class.

 * [***RiskClassSensitivityIR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product/RiskClassSensitivityIR.java)
 <i>RiskClassSensitivityIR</i> holds the Risk Class Bucket Sensitivities for a single IR Class.

 * [***RiskFactorTenorSensitivity***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product/RiskFactorTenorSensitivity.java)
 <i>RiskFactorTenorSensitivity</i> holds the ISDA SIMM 2.0 Risk Factor Tenor Bucket Sensitivities.

 * [***RiskMeasureSensitivity***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product/RiskMeasureSensitivity.java)
 <i>RiskMeasureSensitivity</i> holds the Risk Class Bucket Sensitivities for a single Risk Measure.

 * [***RiskMeasureSensitivityCR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product/RiskMeasureSensitivityCR.java)
 <i>RiskMeasureSensitivityCR</i> holds the Risk Class Bucket Sensitivities for the CR Risk Measure.

 * [***RiskMeasureSensitivityIR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product/RiskMeasureSensitivityIR.java)
 <i>RiskMeasureSensitivityIR</i> holds the Risk Class Bucket Sensitivities for the IR Risk Measure.


## References

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin
 	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>

 * Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin Calculations
 	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>

 * Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing Framework
 	for Forecasting Initial Margin Requirements https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279
 		<b>eSSRN</b>

 * Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin Requirements - A
 	Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167 <b>eSSRN</b>

 * International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
		https://www.isda.org/a/oFiDE/isda-simm-v2.pdf


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
