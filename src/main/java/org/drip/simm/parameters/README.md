# DROP SIMM Parameters Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP SIMM Parameters Package contains the ISDA SIMM Risk Factor Parameters.


## Class Components

 * [***BucketCurvatureSettings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/BucketCurvatureSettings.java)
 <i>BucketCurvatureSettings</i> holds the ISDA SIMM Curvature Settings for Interest Rates, Qualifying and
 Non-qualifying Credit, Equity, Commodity, and Foreign Exchange.

 * [***BucketCurvatureSettingsCR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/BucketCurvatureSettingsCR.java)
 <i>BucketCurvatureSettingsCR</i> holds the Curvature Risk Weights, Concentration Thresholds, and Cross-Tenor
 Correlations for each Currency Curve and its Tenor.

 * [***BucketCurvatureSettingsIR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/BucketCurvatureSettingsIR.java)
 <i>BucketCurvatureSettingsIR</i> holds the Curvature Risk Weights, Concentration Thresholds, and
 Cross-Tenor/Cross-Curve Correlations for each Currency Curve and its Tenor.

 * [***BucketSensitivitySettings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/BucketSensitivitySettings.java)
 <i>BucketSensitivitySettings</i> holds the Settings that govern the Generation of the ISDA SIMM Single
 Bucket Sensitivities.

 * [***BucketSensitivitySettingsCR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/BucketSensitivitySettingsCR.java)
 <i>BucketSensitivitySettingsCR</i> holds the Delta Risk Weights, Concentration Thresholds, and Cross-Tenor
 Correlations for each Credit Curve and its Tenor.

 * [***BucketSensitivitySettingsIR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/BucketSensitivitySettingsIR.java)
 <i>BucketSensitivitySettingsIR</i> holds the Delta Risk Weights, Concentration Thresholds, and
 Cross-Tenor/Cross-Curve Correlations for each Currency Curve and its Tenor.

 * [***BucketVegaSettings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/BucketVegaSettings.java)
 <i>BucketVegaSettings</i> holds the Settings that govern the Generation of the ISDA SIMM Single Bucket Vega
 Sensitivities.

 * [***BucketVegaSettingsCR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/BucketVegaSettingsCR.java)
 <i>BucketVegaSettingsCR</i> holds the Vega Risk Weights, Concentration Thresholds, and Cross-Tenor
 Correlations for each Credit Curve and its Tenor.

 * [***BucketVegaSettingsIR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/BucketVegaSettingsIR.java)
 <i>BucketVegaSettingsIR</i> holds the Vega Risk Weights, Concentration Thresholds, and
 Cross-Tenor/Cross-Curve Correlations for each Currency Curve and its Tenor.

 * [***LiquiditySettings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/LiquiditySettings.java)
 <i>LiquiditySettings</i> exposes the Concentration Thresholds for each Risk Factor.

 * [***RiskClassSensitivitySettings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/RiskClassSensitivitySettings.java)
 <i>RiskClassSensitivitySettings</i> holds the Settings that govern the Generation of the ISDA SIMM Bucket
 Sensitivities across Individual Risk Class Buckets.

 * [***RiskClassSensitivitySettingsCR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/RiskClassSensitivitySettingsCR.java)
 <i>RiskClassSensitivitySettingsCR</i> holds the Settings that govern the Generation of the ISDA SIMM Bucket
 Sensitivities across Individual CR Risk Class Buckets.

 * [***RiskClassSensitivitySettingsIR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/RiskClassSensitivitySettingsIR.java)
 <i>RiskClassSensitivitySettingsIR</i> holds the Settings that govern the Generation of the ISDA SIMM Bucket
 Sensitivities across Individual IR Risk Class Buckets.

 * [***RiskMeasureSensitivitySettings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/RiskMeasureSensitivitySettings.java)
 <i>RiskMeasureSensitivitySettings</i> holds the Settings that govern the Generation of the ISDA SIMM Bucket
 Sensitivities across Individual Risk Measure Buckets.

 * [***RiskMeasureSensitivitySettingsCR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/RiskMeasureSensitivitySettingsCR.java)
 <i>RiskMeasureSensitivitySettingsCR</i> holds the Settings that govern the Generation of the ISDA SIMM
 Bucket Sensitivities across Individual CR Class Risk Measure Buckets.

 * [***RiskMeasureSensitivitySettingsIR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/RiskMeasureSensitivitySettingsIR.java)
 <i>RiskMeasureSensitivitySettingsIR</i> holds the Settings that govern the Generation of the ISDA SIMM
 Bucket Sensitivities across Individual IR Class Risk Measure Buckets.


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
