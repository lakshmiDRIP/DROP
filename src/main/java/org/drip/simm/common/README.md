# DROP Service Common Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Service Common Package contains the Common Cross Risk Factor Utilities.


## Class Components

 * [***Chargram***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/common/Chargram.java)
 <i>Chargram</i> contains the 2-4 Character Code that identifies a specific Risk Class.

 * [***CrossRiskClassCorrelation20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/common/CrossRiskClassCorrelation20.java)
 <i>CrossRiskClassCorrelation20</i> contains the SIMM 2.0 Correlation between the Different Risk Classes.

 * [***CrossRiskClassCorrelation21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/common/CrossRiskClassCorrelation20.java)
 <i>CrossRiskClassCorrelation21</i> contains the SIMM 2.1 Correlation between the Different Risk Classes.

 * [***DeltaVegaThreshold***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/common/DeltaVegaThreshold.java)
 <i>DeltaVegaThreshold</i> holds the ISDA SIMM Delta/Vega Limits defined for the Concentration Thresholds.

 * [***ISDASettingsContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/common/ISDASettingsContainer.java)
 <i>ISDASettingsContainer</i> holds the ISDA SIMM Risk Weights/Correlations for Interest Rates, Qualifying
 and Non-qualifying Credit, Equity, Commodity, and Foreign Exchange. The corresponding Concentration
 Thresholds are also contained.

 * [***ProductClassMultiplicativeScale***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/common/ProductClassMultiplicativeScale.java)
 <i>ProductClassMultiplicativeScale</i> holds the Multiplicative Scales Minimum/Default Values for the Four
 Product Classes - RatesFX, Credit, Equity, and Commodity.

 * [***RiskFactorThresholdContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/common/RiskFactorThresholdContainer.java)
 <i>RiskFactorThresholdContainer</i> holds the ISDA SIMM 2.0 Risk Factor Thresholds - the Concentration
 Limits for Interest Rate, Credit Spread, Equity, Commodity, and FX Risk Factors.


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
