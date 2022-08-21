# DROP SIMM Foundation Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP SIMM Foundation Package contains the Foundation Utilities for ISDA SIMM.


## Class Components

 * [***CurvatureEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/foundation/CurvatureEstimator.java)
 <i>CurvatureEstimator</i> exposes the Curvature Margin Estimation using the Curvature Sensitivities.

 * [***CurvatureEstimatorFRTB***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/foundation/CurvatureEstimatorFRTB.java)
 <i>CurvatureEstimatorFRTB</i> estimates the Curvature Margin from the Curvature Sensitivities using the FRTB
 Curvature Margin Estimate.

 * [***CurvatureEstimatorISDADelta***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/foundation/CurvatureEstimatorISDADelta.java)
 <i>CurvatureEstimatorISDADelta</i> estimates the Curvature Margin from the Curvature Sensitivities using the
 ISDA Delta Curvature Margin Estimate.

 * [***CurvatureEstimatorResponseFunction***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/foundation/CurvatureEstimatorResponseFunction.java)
 <i>CurvatureEstimatorResponseFunction</i> estimates the Curvature Margin from the Curvature Sensitivities
 using the Curvature Response Function.

 * [***CurvatureResponse***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/foundation/CurvatureResponse.java)
 <i>CurvatureResponse</i> exposes the Calculation of the Curvature Co-variance Scaling Factor (lambda) using
 the Cumulative Curvature Sensitivities.

 * [***CurvatureResponseCornishFischer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/foundation/CurvatureResponseCornishFischer.java)
 <i>CurvatureResponseCornishFischer</i> computes the Curvature Co-variance Scaling Factor using the
 Cumulative Curvature Sensitivities.

 * [***MarginEstimationSettings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/foundation/MarginEstimationSettings.java)
 <i>MarginEstimationSettings</i> exposes the Customization Settings used in the Margin Estimation.

 * [***RiskGroupPrincipalCovariance***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/foundation/RiskGroupPrincipalCovariance.java)
 <i>RiskGroupPrincipalCovariance</i> contains the Cross Risk-Group Principal Component Based Co-variance.


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

 * International Swaps and Derivatives Association (2021): SIMM v2.4 Methodology
		https://www.isda.org/a/CeggE/ISDA-SIMM-v2.4-PUBLIC.pdf


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
