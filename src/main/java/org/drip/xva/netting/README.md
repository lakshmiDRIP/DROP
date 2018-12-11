# DROP XVA Netting Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP XVA Netting Package holds the Credit/Debt/Funding Netting Groups.


## Class Components

 * [***CollateralGroupPath***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/netting/CollateralGroupPath.java)
 <i>CollateralGroupPath</i> accumulates the Vertex Realizations of the Sequence in a Single Path Projection
 Run along the Granularity of a Regular Collateral Hypothecation Group.

 * [***CreditDebtGroupPath***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/netting/CreditDebtGroupPath.java)
 <i>CreditDebtGroupPath</i> rolls up the Path Realizations of the Sequence in a Single Path Projection Run
 over Multiple Collateral Hypothecation Groups onto a Single Credit/Debt Netting Group - the Purpose being to
 calculate Credit Valuation Adjustments.

 * [***FundingGroupPath***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/netting/FundingGroupPath.java)
 <i>FundingGroupPath</i> holds up the Strategy Abstract Realizations of the Sequence in a Single Path
 Projection Run over Multiple Collateral Groups onto a Single Funding Group - the Purpose being to calculate
 Funding Valuation Adjustments.


## References

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
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
