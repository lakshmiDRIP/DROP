# DROP Execution HJB Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Execution HJB Package implements the Optimal Hamilton-Jacobi-Bellman Execution Functionality.

## Class Components

 * [***NonDimensionalCost***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/hjb/NonDimensionalCost.java)
 <i>NonDimensionalCost</i> exposes the Level, the Gradient, and the Jacobian of the Realized Non Dimensional
 Cost Value Function to the Market State.

 * [***NonDimensionalCostCorrelated***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/hjb/NonDimensionalCostCorrelated.java)
 <i>NonDimensionalCostCorrelated</i> contains the Level, the Gradient, and the Jacobian of the HJB Non
 dimensional Cost Value Function to the Individual Correlated Market States.

 * [***NonDimensionalCostEvolver***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/hjb/NonDimensionalCostEvolver.java)
 <i>NonDimensionalCostEvolver</i> exposes the HJB-based Single Step Optimal Trajectory Cost Step Evolver
 using the Variants of the Coordinated Variation Version of the Stochastic Volatility and the Transaction
 Function arising from the Realization of the Market State Variable as described in the "Trading Time" Model.

 * [***NonDimensionalCostEvolverCorrelated***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/hjb/NonDimensionalCostEvolverCorrelated.java)
 <i>NonDimensionalCostEvolverCorrelated</i> implements the Correlated HJB-based Single Step Optimal
 Trajectory Cost Step Evolver using the Correlated Coordinated Variation Version of the Stochastic Volatility
 and the Transaction Function arising from the Realization of the Market State Variable as described in the
 "Trading Time" Model.

 * [***NonDimensionalCostEvolverSystemic***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/hjb/NonDimensionalCostEvolverSystemic.java)
 <i>NonDimensionalCostEvolverSystemic</i> implements the 1D HJB-based Single Step Optimal Trajectory Cost
 Step Evolver using the Systemic Coordinated Variation Version of the Stochastic Volatility and the
 Transaction Function arising from the Realization of the Market State Variable as described in the "Trading
 Time" Model.

 * [***NonDimensionalCostSystemic***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/hjb/NonDimensionalCostSystemic.java)
 <i>NonDimensionalCostSystemic</i> contains the Level, the Gradient, and the Jacobian of the HJB Non
 Dimensional Cost Value Function to the Systemic Market State.


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
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
