# DROP SIMM Variance Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP SIMM Variance demonstrates Position Bucket Co-variance, i.e., ISDA SIMM vs. FRTB SBA-C vs. Actual.


## Class Components

 |     Sample     | Source | Output |
 |----------------|--------|--------|
 | CRNQ Margin Comparison | [Java](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simmvariance/CRNQMarginComparison.java) | [DROP](https://github.com/lakshmiDRIP/DROP/blob/master/drop/org/drip/sample/simmvariance/CRNQMarginComparison.drop) |
 | Cross Group Principal Covariance | [Java](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simmvariance/CrossGroupPrincipalCovariance.java) | [DROP](https://github.com/lakshmiDRIP/DROP/blob/master/drop/org/drip/sample/simmvariance/CrossGroupPrincipalCovariance.drop) |
 | CRQ Margin Comparison | [Java](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simmvariance/CRQMarginComparison.java) | [DROP](https://github.com/lakshmiDRIP/DROP/blob/master/drop/org/drip/sample/simmvariance/CRQMarginComparison.drop) |
 | CT Cross Bucket Principal | [Java](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simmvariance/CTCrossBucketPrincipal.java) | [DROP](https://github.com/lakshmiDRIP/DROP/blob/master/drop/org/drip/sample/simmvariance/CTCrossBucketPrincipal.drop) |
 | CT Margin Comparison | [Java](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simmvariance/CTMarginComparison.java) | [DROP](https://github.com/lakshmiDRIP/DROP/blob/master/drop/org/drip/sample/simmvariance/CTMarginComparison.drop) |
 | EQ Cross Bucket Principal | [Java](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simmvariance/EQCrossBucketPrincipal.java) | [DROP](https://github.com/lakshmiDRIP/DROP/blob/master/drop/org/drip/sample/simmvariance/EQCrossBucketPrincipal.drop) |
 | EQ Margin Comparison | [Java](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simmvariance/EQMarginComparison.java) | [DROP](https://github.com/lakshmiDRIP/DROP/blob/master/drop/org/drip/sample/simmvariance/EQMarginComparison.drop) |
 | FX Cross Group Principal | [Java](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simmvariance/FXCrossGroupPrincipal.java) | [DROP](https://github.com/lakshmiDRIP/DROP/blob/master/drop/org/drip/sample/simmvariance/FXCrossGroupPrincipal.drop) |
 | FX Margin Comparison | [Java](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simmvariance/FXMarginComparison.java) | [DROP](https://github.com/lakshmiDRIP/DROP/blob/master/drop/org/drip/sample/simmvariance/FXMarginComparison.drop) |
 | IR Cross Curve Principal | [Java](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simmvariance/IRCrossCurvePrincipal.java) | [DROP](https://github.com/lakshmiDRIP/DROP/blob/master/drop/org/drip/sample/simmvariance/IRCrossCurvePrincipal.drop) |
 | IR Margin Comparison | [Java](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simmvariance/IRMarginComparison.java) | [DROP](https://github.com/lakshmiDRIP/DROP/blob/master/drop/org/drip/sample/simmvariance/IRMarginComparison.drop) |


## References

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>

 * Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin Calculations https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>

 * Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing Framework for Forecasting Initial Margin Requirements https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>

 * Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin Requirements; A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167 <b>eSSRN</b>

 * International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology https://www.isda.org/a/oFiDE/isda-simm-v2.pdf


## Hierarchy

 <ul>
	<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
	<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></li>
	<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
	<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simmvariance/README.md">Position Bucket Co-variance - ISDA SIMM vs. FRTB SBA-C vs. Actual</a></li>
 </ul>


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
