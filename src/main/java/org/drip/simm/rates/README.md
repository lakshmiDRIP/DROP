# DROP SIMM Rates Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP SIMM Rates Package contains the SIMM IR Risk Factor Settings.


## Class Components

 * [***CurrencyRiskGroup***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/rates/CurrencyRiskGroup.java)
 <i>CurrencyRiskGroup</i> holds the ISDA SIMM Currency Risk Group Concentrations.

 * [***IRSettingsContainer20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/rates/IRSettingsContainer20.java)
 <i>IRSettingsContainer20</i> holds the ISDA SIMM 2.0 Tenor Vertex Risk Weights/Correlations for Single IR
 Curves, Cross Currencies, and Inflation.

 * [***IRSettingsContainer21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/rates/IRSettingsContainer21.java)
 <i>IRSettingsContainer21</i> holds the ISDA SIMM 2.1 Tenor Vertex Risk Weights/Correlations for Single IR
 Curves, Cross Currencies, and Inflation.

 * [***IRSystemics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/rates/IRSystemics.java)
 <i>IRSystemics</i> contains the Systemic Settings of the SIMM Interest Rate Risk Factors.

 * [***IRSystemics20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/rates/IRSystemics20.java)
 <i>IRSystemics20</i> contains the Systemic Settings of the SIMM 2.0 Interest Rate Risk Factors.

 * [***IRSystemics21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/rates/IRSystemics21.java)
 <i>IRSystemics21</i> contains the Systemic Settings of the SIMM 2.1 Interest Rate Risk Factors.

 * [***IRThreshold***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/rates/IRThreshold.java)
 <i>IRThreshold</i> holds the ISDA SIMM Interest Rate Delta and Vega Concentration Thresholds.

 * [***IRThresholdContainer20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/rates/IRThresholdContainer20.java)
 <i>IRThresholdContainer20</i> holds the ISDA SIMM 2.0 Interest Rate Thresholds - the Currency Risk Groups,
 and the Delta/Vega Limits defined for the Concentration Thresholds.

 * [***IRThresholdContainer21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/rates/IRThresholdContainer21.java)
 <i>IRThresholdContainer21</i> holds the ISDA SIMM 2.1 Interest Rate Thresholds - the Currency Risk Groups,
 and the Delta/Vega Limits defined for the Concentration Thresholds.

 * [***IRWeight***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/rates/IRWeight.java)
 <i>IRWeight</i> holds the ISDA SIMM Tenor Interest Rate Vertex Risk Weights for Currencies across all
 Volatility Types.


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
 * Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
