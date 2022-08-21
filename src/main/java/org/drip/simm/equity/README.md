# DROP SIMM Equity Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP SIMM Equity Package contains the Equity Risk Factor Calibration Settings.


## Class Components

 * [***EQBucket***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/equity/EQBucket.java)
 <i>EQBucket</i> holds the ISDA SIMM Region, Sector, Member Correlation, and Risk Weights for a given Equity
 Issuer Exposure Bucket.

 * [***EQRiskThresholdContainer20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/equity/EQRiskThresholdContainer20.java)
 <i>EQRiskThresholdContainer20</i> holds the ISDA SIMM 2.0 Equity Risk Thresholds - the Equity Buckets and
 the Delta/Vega Limits defined for the Concentration Thresholds.

 * [***EQRiskThresholdContainer21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/equity/EQRiskThresholdContainer21.java)
 <i>EQRiskThresholdContainer21</i> holds the ISDA SIMM 2.1 Equity Risk Thresholds - the Equity Buckets and
 the Delta/Vega Limits defined for the Concentration Thresholds.

 * [***EQRiskThresholdContainer24***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/equity/EQRiskThresholdContainer24.java)
 <i>EQRiskThresholdContainer24</i> holds the ISDA SIMM 2.4 Equity Risk Thresholds - the Equity Buckets and
 the Delta/Vega Limits defined for the Concentration Thresholds.

 * [***EQSettingsContainer20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/equity/EQSettingsContainer20.java)
 <i>EQSettingsContainer20</i> holds the ISDA SIMM 2.0 Equity Buckets and their Correlations.

 * [***EQSettingsContainer21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/equity/EQSettingsContainer21.java)
 <i>EQSettingsContainer21</i> holds the ISDA SIMM 2.1 Equity Buckets and their Correlations.

 * [***EQSettingsContainer24***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/equity/EQSettingsContainer24.java)
 <i>EQSettingsContainer24</i> holds the ISDA SIMM 2.4 Equity Buckets and their Correlations.

 * [***EQSystemics20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/equity/EQSystemics20.java)
 <i>EQSystemics20</i> contains the SIMM 2.0 Systemic Settings common to all Equity Risk Factors.

 * [***EQSystemics21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/equity/EQSystemics21.java)
 <i>EQSystemics21</i> contains the SIMM 2.1 Systemic Settings common to all Equity Risk Factors.

 * [***EQSystemics24***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/equity/EQSystemics24.java)
 <i>EQSystemics24</i> contains the SIMM 2.4 Systemic Settings common to all Equity Risk Factors.

 * [***MarketCapitalizationSystemics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/equity/MarketCapitalizationSystemics.java)
 <i>MarketCapitalizationSystemics</i> contains the Systemic Settings that contain the Market Capitalization
 Classification.

 * [***RegionSystemics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/equity/RegionSystemics.java)
 <i>RegionSystemics</i> contains the Systemic Settings that contain the Region Details.


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
