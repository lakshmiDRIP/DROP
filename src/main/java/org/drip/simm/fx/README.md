# DROP SIMM FX Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP SIMM FX Package contains the FX Risk Factor Calibration Settings.


## Class Components

 * [***FXRiskGroup***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/fx/FXRiskGroup.java)
 <i>FXRiskGroup</i> holds the ISDA SIMM FX Risk Group Concentration Categories and their Delta Limits.

 * [***FXRiskThresholdContainer20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/fx/FXRiskThresholdContainer20.java)
 <i>FXRiskThresholdContainer20</i> holds the ISDA SIMM 2.0 FX Risk Thresholds - the FX Categories and the
 Delta/Vega Limits defined for the Concentration Thresholds.

 * [***FXRiskThresholdContainer21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/fx/FXRiskThresholdContainer21.java)
 <i>FXRiskThresholdContainer21</i> holds the ISDA SIMM 2.1 FX Risk Thresholds - the FX Categories and the
 Delta/Vega Limits defined for the Concentration Thresholds.

 * [***FXSystemics20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/fx/FXSystemics20.java)
 <i>FXSystemics20</i> contains the SIMM 2.0 Systemic Settings Common to FX Risk Factors.

 * [***FXSystemics21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/fx/FXSystemics21.java)
 <i>FXSystemics21</i> contains the SIMM 2.1 Systemic Settings Common to FX Risk Factors.


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
