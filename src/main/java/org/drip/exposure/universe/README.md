# DROP Exposure Universe Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Exposure Universe Package implements the Market States Simulation for Exposure Generation.

## Class Components

 * [***LatentStateWeiner***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/universe/LatentStateWeiner.java)
 <i>LatentStateWeiner</i> generates the Edge Latent State Weiner Increments across Trajectory Vertexes needed
 for computing the Valuation Adjustment.

 * [***MarketCorrelation***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/universe/MarketCorrelation.java)
 <i>MarketCorrelation</i> holds the Cross Latent State Correlations needed for computing the Valuation
 Adjustment.

 * [***MarketEdge***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/universe/MarketEdge.java)
 <i>MarketEdge</i> holds the Vertex Realizations of the Market States of the Reference Universe along an
 Evolution Edge.

 * [***MarketPath***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/universe/MarketPath.java)
 <i>MarketPath</i> holds the Vertex Market Realizations at the Trajectory Vertexes along the Path of a
 Simulation.

 * [***MarketVertex***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/universe/MarketVertex.java)
 <i>MarketVertex</i> holds the Market Realizations at a Market Trajectory Vertex needed for computing the
 Valuation Adjustment.

 * [***MarketVertexEntity***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/universe/MarketVertexEntity.java)
 <i>MarketVertexEntity</i> holds the Realizations at a Market Trajectory Vertex of the given XVA Entity
 (i.e., Dealer/Client).

 * [***MarketVertexGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/universe/MarketVertexGenerator.java)
 <i>MarketVertexGenerator</i> generates the Market Realizations at a Trajectory Vertex needed for computing
 the Valuation Adjustment.


# References

 * Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies <i>Risk</i> <b>23 (12)</b> 82-87

 * Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75

 * Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk and
 Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19

 * Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>

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
