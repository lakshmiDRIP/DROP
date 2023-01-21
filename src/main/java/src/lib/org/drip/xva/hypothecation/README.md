# DROP XVA Hypothecation Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP XVA Hypothecation Package implements the XVA Hypothecation Group Amount Estimation.


## Class Components

 * [***CollateralGroupVertex***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/hypothecation/CollateralGroupVertex.java)
 <i>CollateralGroupVertex</i> holds the Vertex Exposures of a Projected Path of a Simulation Run of a
 Collateral Hypothecation Group.

 * [***CollateralGroupVertexCloseOut***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/hypothecation/CollateralGroupVertexCloseOut.java)
 <i>CollateralGroupVertexCloseOut</i> holds the Dealer and the Client Close Outs at each Re-hypothecation
 Collateral Group.

 * [***CollateralGroupVertexExposure***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/hypothecation/CollateralGroupVertexExposure.java)
 <i>CollateralGroupVertexExposure</i> holds the Uncollateralized Exposure and the Collateral Balances at each
 Re-hypothecation Collateral Group.

 * [***CollateralGroupVertexExposureComponent***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/hypothecation/CollateralGroupVertexExposureComponent.java)
 <i>CollateralGroupVertexExposureComponent</i> holds the Credit, the Debt, and the Funding Exposures, as well
 as the Collateral Balances at each Re-hypothecation Collateral Group.


## References

 * Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 	Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955 <b>eSSRN</b>

 * Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk and
 	Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19

 * Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75

 * Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b> 86-90

 * Li, B., and Y. Tang (2007): <i>Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 	Presence of Counter-party Credit Risk for the Fixed Income Market</i> <b>World Scientific Publishing</b>
 	Singapore

 * Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 	<i>Risk</i> <b>21 (2)</b> 97-102


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
