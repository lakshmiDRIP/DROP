# DROP Exposure Regression Trade Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Exposure Regression Trade Package computes the Exposure Regression under Margin and Trade Payments.

## Class Components

 * [***AdjustedVariationMarginDynamics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regressiontrade/AdjustedVariationMarginDynamics.java)
 <i>AdjustedVariationMarginDynamics</i> builds the Dynamics of the Sparse Path Adjusted Variation Margin.

 * [***AdjustedVariationMarginEstimate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regressiontrade/AdjustedVariationMarginEstimate.java)
 <i>AdjustedVariationMarginEstimate</i> holds the Sparse Path Adjusted Variation Margin and the Daily Trade
 Flows.

 * [***AdjustedVariationMarginEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regressiontrade/AdjustedVariationMarginEstimator.java)
 <i>AdjustedVariationMarginEstimator</i> coordinates the Generation of the Path-specific Trade Payment
 Adjusted Variation Margin Flows.

 * [***AndersenPykhtinSokolEnsemble***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regressiontrade/AndersenPykhtinSokolEnsemble.java)
 <i>AndersenPykhtinSokolEnsemble</i> adjusts the Variation Margin, computes Path-wise Local Volatility, and
 eventually estimates the Path-wise Unadjusted Variation Margin across the Suite of Simulated Paths.

 * [***AndersenPykhtinSokolPath***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regressiontrade/AndersenPykhtinSokolPath.java)
 <i>AndersenPykhtinSokolPath</i> holds the holds the Sparse Path Adjusted/Unadjusted Exposures along with
 Dense Trade Payments. Adjustments are applied in accordance with the Andersen, Pykhtin, and Sokol (2017a)
 Regression Scheme.

 * [***AndersenPykhtinSokolTrajectory***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regressiontrade/AndersenPykhtinSokolTrajectory.java)
 <i>AndersenPykhtinSokolTrajectory</i> holds the per-Path Variation Margin Trajectory and theTrade Flow
 Array.

 * [***VariationMarginEstimateVertex***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regressiontrade/VariationMarginEstimateVertex.java)
 <i>VariationMarginEstimateVertex</i> holds the Sparse Date Unadjusted and Adjusted Variation Margin
 Estimates. Adjustments are applied in accordance with the Andersen, Pykhtin, and Sokol (2017a) Regression
 Scheme.


# References

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017a): Re-thinking Margin Period of Risk
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017b): Credit Exposure in the Presence of Initial Margin
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>

 * Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the Re-
 Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955 <b>eSSRN</b>

 * Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>

 * Pykhtin, M. (2009): Modeling Counter-party Credit Exposure in the Presence of Margin Agreements
 http://www.risk-europe.com/protected/michael-pykhtin.pdf


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
