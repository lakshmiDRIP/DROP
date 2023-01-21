# DROP Execution Adaptive Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Execution Adaptive Package implements the Coordinate Variation Based Adaptive Execution.


## Class Components

 * [***CoordinatedVariationDynamic***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/adaptive/CoordinatedVariationDynamic.java)
 <i>CoordinatedVariationDynamic</i> implements the HJB-based Single Step Optimal Cost Dynamic Trajectory
 using the Coordinated Variation Version of the Stochastic Volatility and the Transaction Function arising
 from the Realization of the Market State Variable as described in the "Trading Time" Model.

 * [***CoordinatedVariationRollingHorizon***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/adaptive/CoordinatedVariationRollingHorizon.java)
 <i>CoordinatedVariationRollingHorizon</i> implements the "Rolling Horizon" Approximation of the Optimal Cost
  Dynamic Trajectory arising from the Coordinated Variation Version of the Stochastic Volatility and the
  Transaction Function arising from the Realization of the Market State Variable as described in the "Trading
  Time" Model.

 * [***CoordinatedVariationStatic***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/adaptive/CoordinatedVariationStatic.java)
 <i>CoordinatedVariationStatic</i> implements the Static Trajectory based on the "Mean Equilibrium Market
 State" of the Coordinated Variation Version of the Stochastic Volatility and the Transaction Function
 arising from the Realization of the Market State Variable as described in the "Trading Time" Model.

 * [***CoordinatedVariationTrajectory***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/adaptive/CoordinatedVariationTrajectory.java)
 <i>CoordinatedVariationTrajectory</i> holds the "Common" Measures generated from the HJB-based MultiStep
 Optimal Cost Dynamic Trajectory Generation using the Coordinated Variation Version of the Stochastic
 Volatility and the Transaction Function arising from the Realization of the Market State Variable as
 described in the "Trading Time" Model.

 * [***CoordinatedVariationTrajectoryDeterminant***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/adaptive/CoordinatedVariationTrajectoryDeterminant.java)
 <i>CoordinatedVariationTrajectoryDeterminant</i> contains the HJB-based MultiStep Optimal Cost Dynamic
 Trajectory Generation Metrics using the Coordinated Variation Version of the Stochastic Volatility and the
 Transaction Function arising from the Realization of the Market State Variable as described in the "Trading
 Time" Model.

 * [***CoordinatedVariationTrajectoryGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/adaptive/CoordinatedVariationTrajectoryGenerator.java)
 <i>CoordinatedVariationTrajectoryGenerator</i> implements the Continuous HJB-based Single Step Optimal Cost
 Trajectory using the Coordinated Variation Version of the Stochastic Volatility and the Transaction Function
 arising from the Realization of the Market State Variable as described in the "Trading Time" Model.

 * [***CoordinatedVariationTrajectoryGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/adaptive/CoordinatedVariationTrajectoryGenerator.java)
 <i>CoordinatedVariationTrajectoryState</i> holds the HJB-based Multi Step Optimal Trajectory State at each
 Step of the Evolution using the Coordinated Variation Version of the Stochastic Volatility and the
 Transaction Function arising from the Realization of the Market State Variable as described in the "Trading
 Time" Model.


# References

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
