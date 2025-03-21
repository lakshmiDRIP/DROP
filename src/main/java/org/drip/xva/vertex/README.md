# DROP XVA Vertex Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP XVA Vertex Package implements the XVA Hypothecation Group Vertex Generators.


## Class Components

 * [***AlbaneseAndersen***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/vertex/AlbaneseAndersen.java)
 <i>AlbaneseAndersen</i> holds the Albanese and Andersen (2014) Vertex Exposures of a Projected Path of a
 Simulation Run of a Collateral Hypothecation Group.

 * [***BurgardKjaer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/vertex/BurgardKjaer.java)
 <i>BurgardKjaer</i> holds the Close Out Based Vertex Exposures of a Projected Path of a Simulation Run of a
 Collateral Hypothecation Group using the Generalized Burgard Kjaer (2013) Scheme.

 * [***BurgardKjaerBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/vertex/BurgardKjaerBuilder.java)
 <i>BurgardKjaerBuilder</i> contains the Builders that construct the Burgard Kjaer Vertex using a Variant of
 the Generalized Burgard Kjaer (2013) Scheme.

 * [***BurgardKjaerExposure***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/vertex/BurgardKjaerExposure.java)
 <i>BurgardKjaerExposure</i> holds the Credit, the Debt, and the Funding Exposures, as well as the Collateral
 Balances at each Rehypothecation Collateral Group using the Burgard Kjaer (2014) Scheme.


## References

 * Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 	Rehypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955 <b>eSSRN</b>

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
