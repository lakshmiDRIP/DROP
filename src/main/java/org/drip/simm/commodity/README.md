# DROP Service Commodity Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Service Commodity Package implements the Commodity Risk Factor Calibration Settings.


## Class Components

 * [***CTBucket***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/commodity/CTBucket.java)
 <i>CTBucket</i> holds the ISDA SIMM Commodity, Risk Weight, and Member Correlation for each Commodity
 Bucket.

 * [***CTRiskThresholdContainer20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/commodity/CTRiskThresholdContainer20.java)
 <i>CTRiskThresholdContainer20</i> holds the ISDA SIMM 2.0 Commodity Risk Thresholds - the Commodity Buckets
 and the Delta/Vega Limits defined for the Concentration Thresholds.

 * [***CTRiskThresholdContainer21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/commodity/CTRiskThresholdContainer21.java)
 <i>CTRiskThresholdContainer21</i> holds the ISDA SIMM 2.1 Commodity Risk Thresholds - the Commodity Buckets
 and the Delta/Vega Limits defined for the Concentration Thresholds.

 * [***CTSettingsContainer20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/commodity/CTSettingsContainer20.java)
 <i>CTSettingsContainer20</i> holds the ISDA SIMM 2.0 Commodity Buckets and their Correlations.

 * [***CTSettingsContainer21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/commodity/CTSettingsContainer21.java)
 <i>CTSettingsContainer21</i> holds the ISDA SIMM 2.1 Commodity Buckets and their Correlations.

 * [***CTSystemics20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/commodity/CTSystemics20.java)
 <i>CTSystemics20</i> contains the SIMM 2.0 Systemic Settings Common to Commodity Risk Factors.

 * [***CTSystemics21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/commodity/CTSystemics21.java)
 <i>CTSystemics21</i> contains the SIMM 2.1 Systemic Settings Common to Commodity Risk Factors.


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
