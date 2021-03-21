# DROP XVA Derivative Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP XVA Derivative Package implements the Burgard Kjaer Dynamic Portfolio Replication.


## Class Components

 * [***CashAccountEdge***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/derivative/CashAccountEdge.java)
 <i>CashAccountEdge</i> holds the Increments of the Cash Account Components resulting from the Dynamic
 Replication Process.

 * [***CashAccountRebalancer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/derivative/CashAccountRebalancer.java)
 <i>CashAccountRebalancer</i> holds the Edge Cash Account Increment and the Edge Derivative Value Update for
 a Trajectory that has just undergone Cash Account Re-balancing, as laid out in Burgard and Kjaer (2014).

 * [***EvolutionTrajectoryEdge***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/derivative/EvolutionTrajectoryEdge.java)
 <i>EvolutionTrajectoryEdge</i> holds the Evolution Edges of the Trajectory, the Cash Account, and the
 Derivative Values evolved in a Dynamically Adaptive Manner, as laid out in Burgard and Kjaer (2014).

 * [***EvolutionTrajectoryVertex***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/derivative/EvolutionTrajectoryVertex.java)
 <i>EvolutionTrajectoryVertex</i> holds the Evolution Snapshot of the Trade-able Prices, the Cash Account,
 the Replication Portfolio, and the corresponding Derivative Value, as laid out in Burgard and Kjaer (2014).

 * [***PositionGreekVertex***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/derivative/PositionGreekVertex.java)
 <i>PositionGreekVertex</i> holds the Derivative XVA Value, its Delta, and its Gamma to the Position Value.

 * [***ReplicationPortfolioVertex***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/derivative/ReplicationPortfolioVertex.java)
 <i>ReplicationPortfolioVertex</i> contains the Dynamic Replicating Portfolio of the Pay-out using the Assets
 in the Economy, from the Dealer View Point.

 * [***ReplicationPortfolioVertexDealer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/derivative/ReplicationPortfolioVertexDealer.java)
 <i>ReplicationPortfolioVertexDealer</i> holds the Dealer Senor/Subordinate Replication Portfolio.

 * [***TerminalPayout***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/derivative/TerminalPayout.java)
 <i>TerminalPayout</i> implements the Pay-out Function on the given Asset, using its Marginal Evolution
 Process, at the specified Terminal Time Instance.


## References

 * Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk and
 	Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19

 * Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): <i>Modeling, Pricing,
 	and Hedging Counter-party Credit Exposure - A Technical Guide</i> <b>Springer Finance</b> New York

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
