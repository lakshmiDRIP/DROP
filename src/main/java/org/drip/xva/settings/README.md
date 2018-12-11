# DROP XVA Settings Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP XVA Settings Package holds the XVA Group and Path Settings.


## Class Components

 * [***AdjustmentDigestScheme***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/settings/AdjustmentDigestScheme.java)
 <i>AdjustmentDigestScheme</i> contains Settings to the Schemes that generate Aggregated Valuation Adjustment
 Metrics.

 * [***BrokenDateScheme***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/settings/BrokenDateScheme.java)
 <i>BrokenDateScheme</i> holds the Broken Date Interpolation Scheme to generate Intermediate Values for the
 Path Exposures and Collateral Balances.

 * [***CloseOutScheme***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/settings/CloseOutScheme.java)
 <i>CloseOutScheme</i> carries the Close Out Specification Schemes for the Simulation.

 * [***PositionReplicationScheme***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/settings/PositionReplicationScheme.java)
 <i>PositionReplicationScheme</i> holds the various Position Group Replication Schemes and their
 corresponding Vertex Generation Mechanisms.

 * [***StandardizedExposureGeneratorScheme***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/settings/StandardizedExposureGeneratorScheme.java)
 <i>StandardizedExposureGeneratorScheme</i> holds the Fields for the Generation of the Conservative Exposure
 Measures generated using the Standardized Basel Scheme.


## References

 * Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 	Rehypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955 <b>eSSRN</b>

 * Albanese, C., L. Andersen, and, S. Iabichino (2015): The FVA Puzzle: Accounting, Risk Management, and
 	Collateral Trading https://papers.ssrn.com/sol3/paper.cfm?abstract_id_2517301 <b>eSSRN</b>

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk
 	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin
 	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>

 * Anfuso, F., D. Aziz, P. Giltinan, and K Loukopoulus (2017): A Sound Modeling and Back Testing Framework
 	for Forecasting Initial Margin Requirements
 	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>

 * BCBS (2015): Margin Requirements for Non-centrally Cleared Derivatives
 	https://www.bis.org/bcbs/publ/d317.pdf

 * Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk and
 	Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19

 * Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75

 * Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>

 * Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b> 86-90

 * Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 	<i>Risk</i> <b>21 (2)</b> 97-102

 * Pykhtin, M. (2009): Modeling Credit Exposure for Collateralized Counter-parties <i>Journal of Credit
 	Risk</i> <b>5 (4)</b> 3-27


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
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
