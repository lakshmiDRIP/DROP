# DROP Measure Dynamics Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Measure Dynamics contains Jump Diffusion Evolution Evaluator Variants.


## Class Components

 * [***DiffusionEvaluator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/dynamics/DiffusionEvaluator.java)
 <i>DiffusionEvaluator</i> implements the Drift/Volatility Evaluators for R<sup>1</sup> Random Diffusion
 Process.

 * [***DiffusionEvaluatorLinear***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/dynamics/DiffusionEvaluatorLinear.java)
 <i>DiffusionEvaluatorLinear</i> implements the Linear Drift and Volatility Evaluators for R<sup>1</sup>
 Random Diffusion Process.

 * [***DiffusionEvaluatorLogarithmic***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/dynamics/DiffusionEvaluatorLogarithmic.java)
 <i>DiffusionEvaluatorLogarithmic</i> evaluates the Drift/Volatility of the Diffusion Random Variable
 Evolution according to R<sup>1</sup> Logarithmic Process.

 * [***DiffusionEvaluatorMeanReversion***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/dynamics/DiffusionEvaluatorMeanReversion.java)
 <i>DiffusionEvaluatorMeanReversion</i> evaluates the Drift/Volatility of the Diffusion Random Variable
 Evolution according to R<sup>1</sup> Mean Reversion Process.

 * [***DiffusionEvaluatorOrnsteinUhlenbeck***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/dynamics/DiffusionEvaluatorOrnsteinUhlenbeck.java)
 <i>DiffusionEvaluatorOrnsteinUhlenbeck</i> evaluates the Drift/Volatility of the Diffusion Random Variable
 Evolution according to R<sup>1</sup> Ornstein Uhlenbeck Process.

 * [***HazardJumpEvaluator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/dynamics/HazardJumpEvaluator.java)
 <i>HazardJumpEvaluator</i> implements the Hazard Jump Process Point Event Indication Evaluator that guides
 the Single Factor Jump-Termination Random Process Variable Evolution.

 * [***LocalEvaluator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/dynamics/LocalEvaluator.java)
 <i>LocalEvaluator</i> exposes the Random Evolution's Local/Deterministic Evaluators.

 * [***OrnsteinUhlenbeck***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/dynamics/OrnsteinUhlenbeck.java)
 <i>OrnsteinUhlenbeck</i> Interface exposes the Reference Parameter Scales the guide the Random Variable Evolution according to Ornstein-Uhlenbeck Mean Reverting Process.

 * [***OrnsteinUhlenbeckPair***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/dynamics/OrnsteinUhlenbeckPair.java)
 <i>OrnsteinUhlenbeckPair</i> guides the Random Variable Evolution according to 2D Ornstein-Uhlenbeck Mean Reverting Process.

 * [***SingleJumpEvaluator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/dynamics/SingleJumpEvaluator.java)
 <i>SingleJumpEvaluator</i> implements the Single Point Jump Event Indication Evaluator that guides the One
 Factor Jump Random Process Variable Evolution.


## References

 * Almgren, R. F. (2009): Optimal Trading in a Dynamic Market https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf

 * Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility <i>SIAM Journal of Financial Mathematics</i> <b>3 (1)</b> 163-181

 * Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes <i>Mathematical Finance</i> <b>11 (1)</b> 79-96

 * Jones, C. M., G. Kaul, and M. L. Lipson (1994): Transactions, Volume, and Volatility <i>Review of Financial Studies</i> <b>7 (4)</b> 631-651

 * Walia, N. (2006): <i>Optimal Trading - Dynamic Stock Liquidation Strategies</i> <b>Princeton University</b>


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
