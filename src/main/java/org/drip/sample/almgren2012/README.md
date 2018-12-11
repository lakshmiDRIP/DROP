# DROP Sample Almgren (2012) Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Sample Almgren (2012) illustrates the Almgren (2012) Dynamic Optimal Adaptive Trajectories.


## Class Components

 * [***AdaptiveStaticInitialHoldings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgren2012/AdaptiveStaticInitialHoldings.java)
 <i>AdaptiveStaticInitialHoldings</i> simulates the Outstanding Holdings from the Sample Realization of the
 Adaptive Cost Strategy using the Market State Trajectory the follows the Zero Mean Ornstein-Uhlenbeck
 Evolution Dynamics. The Initial Dynamics is derived from the "Mean Market State" Initial Static Trajectory.

 * [***AdaptiveStaticInitialTradeRate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgren2012/AdaptiveStaticInitialTradeRate.java)
 <i>AdaptiveStaticInitialTradeRate</i> simulates the Trade Rate from the Sample Realization of the Adaptive
 Cost Strategy using the Market State Trajectory the follows the Zero Mean Ornstein-Uhlenbeck Evolution
 Dynamics. The Initial Dynamics is derived from the "Mean Market State" Initial Static Trajectory.

 * [***AdaptiveZeroInitialHoldings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgren2012/AdaptiveZeroInitialHoldings.java)
 <i>AdaptiveZeroInitialHoldings</i> simulates the Outstanding Holdings from the Sample Realization of the
 Adaptive Cost Strategy using the Market State Trajectory the follows the Zero Mean Ornstein-Uhlenbeck
 Evolution Dynamics. The Initial Dynamics is derived from the "Mean Market State" Initial Static Trajectory.
 The Initial Dynamics corresponds to the Zero Cost, Zero Cost Sensitivities, and Zero Trade Rate.

 * [***AdaptiveZeroInitialTradeRate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgren2012/AdaptiveZeroInitialTradeRate.java)
 <i>AdaptiveZeroInitialTradeRate</i> simulates the Trade Rate from the Sample Realization of the Adaptive
 Cost Strategy using the Market State Trajectory the follows the Zero Mean Ornstein-Uhlenbeck Evolution
 Dynamics. The Initial Dynamics corresponds to the Zero Cost, Zero Cost Sensitivities, and Zero Trade Rate.

 * [***RollingHorizonOptimalHoldings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgren2012/RollingHorizonOptimalHoldings.java)
 <i>RollingHorizonOptimalHoldings</i> simulates the Holdings from the Sample Realization of the Adaptive Cost
 Strategy using the Market State Trajectory the follows the Zero Mean Ornstein-Uhlenbeck Evolution Dynamics.
 Instead of a HJB Based Truly Adaptive Strategy, a Rolling Horizon Approximation is used.

 * [***RollingHorizonOptimalTradeRate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgren2012/RollingHorizonOptimalTradeRate.java)
 <i>RollingHorizonOptimalTradeRate</i> simulates the Trade Rate from the Sample Realization of the Adaptive
 Cost Strategy using the Market State Trajectory the follows the Zero Mean Ornstein-Uhlenbeck Evolution
 Dynamics. Instead of a HJB Based Truly Adaptive Strategy, a Rolling Horizon Approximation is used.

 * [***StaticOptimalTrajectoryHoldings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgren2012/StaticOptimalTrajectoryHoldings.java)
 <i>StaticOptimalTrajectoryHoldings</i> simulates the Outstanding Holdings from the Sample Realization of the
 Static Cost Strategy extracted using the Mean Market State that follows the Zero Mean Ornstein-Uhlenbeck
 Evolution Dynamics.

 * [***StaticOptimalTrajectoryTradeRate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgren2012/StaticOptimalTrajectoryTradeRate.java)
 <i>StaticOptimalTrajectoryTradeRate</i> simulates the Trade Rate from the Sample Realization of the Static
 Cost Strategy extracted using the Mean Market State that follows the Zero Mean Ornstein-Uhlenbeck Evolution
 Dynamics.


## References

 * Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i>
 <b>3 (2)</b> 5-39

 * Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf

 * Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility <i>SIAM Journal of
 Financial Mathematics</i> <b>3 (1)</b> 163-181

 * Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes <i>Mathematical Finance</i>
 <b>11 (1)</b> 79-96

 * Walia, N. (2006): Optimal Trading: Dynamic Stock Liquidation Strategies <b>Princeton University</b>


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
