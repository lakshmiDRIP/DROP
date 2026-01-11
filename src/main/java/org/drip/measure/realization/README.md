# DROP Measure Realization Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Measure Realization implements the Stochastic Jump Diffusion Vertex Edge.


## Class Components

 * [***DiffusionEvolver***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/realization/DiffusionEvolver.java)
 <i>DiffusionEvolver</i> implements the Functionality that guides the Single Factor R<sup>1</sup> Diffusion Random Process Variable Evolution.

 * [***JumpDiffusionEdge***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/realization/JumpDiffusionEdge.java)
 <i>JumpDiffusionEdge</i> implements the Deterministic and the Stochastic Components of a R<sup>d</sup>
 Marginal Random Increment Edge as well the Original Marginal Random Variate.

 * [***JumpDiffusionEdgeUnit***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/realization/JumpDiffusionEdgeUnit.java)
 <i>JumpDiffusionEdgeUnit</i> holds the Jump Diffusion R<sup>d</sup> Unit Edge Realizations.

 * [***JumpDiffusionEvolver***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/realization/JumpDiffusionEvolver.java)
 <i>JumpDiffusionEvolver</i> implements the Functionality that guides the Single Factor R<sup>1</sup> Jump Diffusion Random Process Variable Evolution.

 * [***JumpDiffusionVertex***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/realization/JumpDiffusionVertex.java)
 <i>JumpDiffusionVertex</i> holds the Snapshot Values of the Realized R<sup>d</sup> Variable - its Value,
 whether it has terminated, and the Cumulative Hazard Integral - and Time.

 * [***StochasticEdgeDiffusion***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/realization/StochasticEdgeDiffusion.java)
 <i>StochasticEdgeDiffusion</i> holds the Edge of the Diffusion Stochastic Evaluator Outcome.

 * [***StochasticEdgeJump***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/realization/StochasticEdgeJump.java)
 <i>StochasticEdgeJump</i> holds the Edge of the Jump Stochastic Evaluator Outcome.

 * [***VertexRdSequence***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/realization/VertexRdSequence.java)
 <i>VertexRdSequence</i> holds the R<sup>d</sup> Realizations at the Individual Vertexes.


## References

 * Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i>
 <b>3 (2)</b> 5-39

 * Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf

 * Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility <i>SIAM Journal of
 Financial Mathematics</i> <b>3 (1)</b> 163-181

 * Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes <i>Mathematical Finance</i>
 <b>11 (1)</b> 79-96

 * Jones, C. M., G. Kaul, and M. L. Lipson (1994): Transactions, Volume, and Volatility <i>Review of
 Financial Studies</i> <b>7 (4)</b> 631-651


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
