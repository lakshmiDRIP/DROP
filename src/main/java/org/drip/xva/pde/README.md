# DROP XVA PDE Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP XVA PDE Package implements the Burgard Kjaer PDE Evolution Scheme.


## Class Components

 * [***BurgardKjaerEdge***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/pde/BurgardKjaerEdge.java)
 <i>BurgardKjaerEdge</i> holds the Underlier Stochastic and the Credit Risk Free Components of the XVA
 Derivative Value Growth, as laid out in Burgard and Kjaer (2014).

 * [***BurgardKjaerEdgeAttribution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/pde/BurgardKjaerEdgeAttribution.java)
 <i>BurgardKjaerEdgeAttribution</i> collects the Attribution Components of the Burgard Kjaer PDE based on the
 Risk-Neutral Ito Evolution of the Derivative, as laid out in Burgard and Kjaer (2014).

 * [***BurgardKjaerEdgeRun***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/pde/BurgardKjaerEdgeRun.java)
 <i>BurgardKjaerEdgeRun</i> collects the Results of the Burgard Kjaer PDE based on the Risk-Neutral Ito
 Evolution of the Derivative, as laid out in Burgard and Kjaer (2014).

 * [***BurgardKjaerOperator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/pde/BurgardKjaerOperator.java)
 <i>BurgardKjaerOperator</i> sets up the Parabolic Differential Equation PDE based on the Ito Evolution
 Differential for the Reference Underlier Asset, as laid out in Burgard and Kjaer (2014).

 * [***ParabolicDifferentialOperator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/pde/ParabolicDifferentialOperator.java)
 <i>ParabolicDifferentialOperator</i> sets up the Parabolic Differential Equation based on the Ito Evolution
 Differential for the Reference Underlier Asset, as laid out in Burgard and Kjaer (2014).

 * [***TrajectoryEvolutionScheme***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/pde/TrajectoryEvolutionScheme.java)
 <i>TrajectoryEvolutionScheme</i> holds the Evolution Edges of a Trajectory evolved in a Dynamically Adaptive
 Manner, as laid out in Burgard and Kjaer (2014).


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
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
