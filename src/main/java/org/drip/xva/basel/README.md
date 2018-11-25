# DROP XVA Basel Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP XVA Basel Package implements the XVA Based Basel Accounting Measures.


## Class Components

 * [***BalanceSheetEdge***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/basel/BalanceSheetEdge.java)
 <i>BalanceSheetEdge</i> implements the Balance Sheet Edge Component of the Streamlined Accounting Framework
 for OTC Derivatives, as described in Albanese and Andersen (2014).

 * [***BalanceSheetVertex***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/basel/BalanceSheetVertex.java)
 <i>BalanceSheetVertex</i> implements the Balance Sheet Vertex Component of the Streamlined Accounting
 Framework for OTC Derivatives, as described in Albanese and Andersen (2014).

 * [***OTCAccountingModus***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/basel/OTCAccountingModus.java)
 <i>OTCAccountingModus</i> implements the Generic Basel Accounting Scheme using the Streamlined Accounting
 Framework for OTC Derivatives, as described in Albanese and Andersen (2014).

 * [***OTCAccountingModusFCAFBA***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/basel/OTCAccountingModusFCAFBA.java)
 <i>OTCAccountingModusFCAFBA</i> implements the Basel Accounting Scheme using the FCA/FBA Specification of
 the Streamlined Accounting Framework for OTC Derivatives, as described in Albanese and Andersen (2014).

 * [***OTCAccountingModusFVAFDA***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/basel/OTCAccountingModusFVAFDA.java)
 <i>OTCAccountingModusFVAFDA</i> implements the Basel Accounting Scheme using the FVA/FDA Specification of
 the Streamlined Accounting Framework for OTC Derivatives, as described in Albanese and Andersen (2014).

 * [***OTCAccountingPolicy***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/basel/OTCAccountingPolicy.java)
 <i>OTCAccountingPolicy</i> implements the Generic Basel Accounting Policy using the Streamlined Accounting
 Framework for OTC Derivatives, as described in Albanese and Andersen (2014).

 * [***ValueAdjustment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/basel/ValueAdjustment.java)
 <i>ValueAdjustment</i> holds the Value and the Attribution Category at the Level of a Portfolio.

 * [***ValueCategory***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/basel/ValueCategory.java)
 <i>ValueCategory</i> holds the Fields relevant to Classifying Value Attribution from an Accounting View
 Point.


## References

 * Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 	Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955 <b>eSSRN</b>

 * BCBS (2012): <i>Consultative Document: Application of Own Credit Risk Adjustments to Derivatives</i>
 	<b>Basel Committee on Banking Supervision</b>

 * Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk and
 	Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19

 * Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75

 * Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 	<i>Risk</i> <b>21 (2)</b> 97-102


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
