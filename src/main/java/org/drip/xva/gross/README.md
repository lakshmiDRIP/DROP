# DROP XVA Gross Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP XVA Gross Package implements the XVA Gross Adiabat Exposure Aggregation.


## Class Components

 * [***BaselExposureDigest***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/gross/BaselExposureDigest.java)
 <i>BaselExposureDigest</i> holds the Conservative Exposure Measures generated using the Standardized Basel
 Approach.

 * [***ExposureAdjustmentAggregator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/gross/ExposureAdjustmentAggregator.java)
 <i>ExposureAdjustmentAggregator</i> aggregates across Multiple Exposure/Adjustment Paths belonging to the
 Counter Party.

 * [***ExposureAdjustmentDigest***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/gross/ExposureAdjustmentDigest.java)
 <i>ExposureAdjustmentDigest</i> holds the "thin" Statistics of the Aggregations across Multiple Path
 Projection Runs along the Granularity of a Counter Party Group (i.e., across multiple Funding and
 Credit/Debt Netting groups).

 * [***GroupPathExposureAdjustment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/gross/GroupPathExposureAdjustment.java)
 <i>GroupPathExposureAdjustment</i> cumulates the Exposures and the Adjustments across Multiple
 Netting/Funding Groups on a Single Path Projection Run across multiple Counter Party Groups the constitute a
 Book.

 * [***MonoPathExposureAdjustment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/gross/MonoPathExposureAdjustment.java)
 <i>MonoPathExposureAdjustment</i> aggregates the Exposures and the Adjustments across Multiple
 Netting/Funding Groups on a Single Path Projection Run along the Granularity of a Counter Party Group.

 * [***PathExposureAdjustment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/gross/PathExposureAdjustment.java)
 <i>PathExposureAdjustment</i> aggregates the Exposures and the Adjustments across Multiple Netting/Funding
 Groups on a Single Path Projection Run along the Granularity of a Counter Party Group.


## References

 * Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 	Re-Hypothecation Option https://papers.ssrn.com/sol3/paper.cfm?abstract_id=2482955 <b>eSSRN</b>

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk
 	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin
 	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>

 * Anfuso, F., D. Aziz, P. Giltinan, and K Loukopoulus (2017): A Sound Modeling and Back testing Framework
 	for Forecasting Initial Margin Requirements https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279
 	<b>eSSRN</b>

 * BCBS (2015): <i>Margin Requirements for Non-centrally Cleared Derivatives</i>
 	https://www.bis.org/bcbs/publ/d317.pdf

 * Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs <i>Risk</i> <b>24 (12)</b> 82-87

 * Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk and
 	Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19

 * Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75

 * Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>

 * Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b> 86-90

 * Li, B., and Y. Tang (2007): <i>Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 	Presence of Counter-party Credit Risk for the Fixed Income Market</i> <b>World Scientific Publishing</b>
 	Singapore

 * Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 	<i>Risk</i> <b>21 (2)</b> 97-102

 * Pykhtin, M. (2009): Modeling Credit Exposure for Collateralized Counter-parties <i>Journal of Credit
 	Risk</i> <b>5 (4)</b> 3-27


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
