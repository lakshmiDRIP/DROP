# DROP XVA Topology Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP XVA Topology Package holds the Collateral, Credit/Debt, Funding Topologies.


## Class Components

 * [***Adiabat***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/topology/Adiabat.java)
 <i>Adiabat</i> represents the Directed Graph of all the Encompassing Funding Groups inside of a Closed
 System (i.e., Adiabat).

 * [***AdiabatMarketParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/topology/AdiabatMarketParams.java)
 <i>AdiabatMarketParams</i> contains the Market Parameters that correspond to a given Adiabat.

 * [***CollateralGroup***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/topology/CollateralGroup.java)
 <i>CollateralGroup</i> represents an Aggregation of Position Groups over a common Collateral Specification.

 * [***CreditDebtGroup***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/topology/CreditDebtGroup.java)
 <i>CreditDebtGroup</i> represents an Aggregation of Collateral Groups with a common Credit Debt
 Specification.

 * [***FundingGroup***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/topology/FundingGroup.java)
 <i>FundingGroup</i> represents an Aggregation of Credit Debt Groups with a common Funding Grou
 Specification.

 * [***PositionGroup***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/topology/PositionGroup.java)
 <i>PositionGroup</i> contains the Named Position Group Instance and Specification.


## References

 * Albanese, C., L. Andersen, and, S. Iabichino (2015): The FVA Puzzle: Accounting, Risk Management, and
 	Collateral Trading https://papers.ssrn.com/sol3/paper.cfm?abstract_id=2517301 <b>eSSRN</b>

 * Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk and
 	Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19

 * Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75

 * Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b> 86-90

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
