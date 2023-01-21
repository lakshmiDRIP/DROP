# DROP SIMM Credit Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP SIMM Credit Package contains the Credit Qualifying/Non-Qualifying Risk Factor Settings.


## Class Components

 * [***CRBucket***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/CRBucket.java)
 <i>CRBucket</i> holds the ISDA SIMM Credit Quality, Sector List, and Risk Weights for a given Credit
 Qualifying/Non-Qualifying Issuer Exposure Bucket.

 * [***CRNQBucketCorrelation20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/CRNQBucketCorrelation20.java)
 <i>CRNQBucketCorrelation20</i> contains the SIMM 2.0 between the Same/Different Issuer/Seniority and
 Different Vertex/Currency for the Same Credit Non-Qualifying Buckets.

 * [***CRNQBucketCorrelation21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/CRNQBucketCorrelation21.java)
 <i>CRNQBucketCorrelation21</i> contains the SIMM 2.1 between the Same/Different Issuer/Seniority and
 Different Vertex/Currency for the Same Credit Non-Qualifying Buckets.

 * [***CRNQSettingsContainer20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/CRNQSettingsContainer20.java)
 <i>CRNQSettingsContainer20</i> holds the ISDA SIMM 2.0 Credit Non-Qualifying Buckets.

 * [***CRNQSettingsContainer21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/CRNQSettingsContainer21.java)
 <i>CRNQSettingsContainer21</i> holds the ISDA SIMM 2.1 Credit Non-Qualifying Buckets.

 * [***CRNQSystemics20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/CRNQSystemics20.java)
 <i>CRNQSystemics20</i> contains the SIMM 2.0 Systemic Settings of the Credit Non-Qualifying Risk Factors.

 * [***CRNQSystemics21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/CRNQSystemics21.java)
 <i>CRNQSystemics21</i> contains the SIMM 2.1 Systemic Settings of the Credit Non-Qualifying Risk Factors.

 * [***CRQBucketCorrelation20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/CRQBucketCorrelation20.java)
 <i>CRQBucketCorrelation20</i> contains the SIMM 2.0 between the Same/Different Issuer/Seniority and
 Different Vertex/Currency for the Same Credit Qualifying Buckets.

 * [***CRQBucketCorrelation21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/CRQBucketCorrelation21.java)
 <i>CRQBucketCorrelation21</i> contains the SIMM 2.1 between the Same/Different Issuer/Seniority and
 Different Vertex/Currency for the Same Credit Qualifying Buckets.

 * [***CRQSettingsContainer20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/CRQSettingsContainer20.java)
 <i>CRQSettingsContainer20</i> holds the ISDA SIMM 2.0 Credit Qualifying Buckets.

 * [***CRQSettingsContainer21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/CRQSettingsContainer21.java)
 <i>CRQSettingsContainer21</i> holds the ISDA SIMM 2.1 Credit Qualifying Buckets.

 * [***CRQSystemics20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/CRQSystemics20.java)
 <i>CRQSystemics20</i> contains the SIMM 2.0 Systemic Settings of the Credit Qualifying Risk Factors.

 * [***CRQSystemics21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/CRQSystemics21.java)
 <i>CRQSystemics21</i> contains the SIMM 2.1 Systemic Settings of the Credit Qualifying Risk Factors.

 * [***CRSystemics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/CRSystemics.java)
 <i>CRSystemics</i> contains the Systemic Settings Common to both Qualifying and Non-Qualifying Credit Risk
 Factors.

 * [***CRThresholdContainer20***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/CRThresholdContainer20.java)
 <i>CRThresholdContainer20</i> holds the ISDA SIMM 2.0 Credit Risk Thresholds - the Credit Risk Buckets and
 the Delta/Vega Limits defined for the Concentration Thresholds.

 * [***CRThresholdContainer21***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/CRThresholdContainer21.java)
 <i>CRThresholdContainer21</i> holds the ISDA SIMM 2.1 Credit Risk Thresholds - the Credit Risk Buckets and
 the Delta/Vega Limits defined for the Concentration Thresholds.

 * [***SectorSystemics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/SectorSystemics.java)
 <i>SectorSystemics</i> contains the Systemic Settings that hold Sector-related Information.


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
