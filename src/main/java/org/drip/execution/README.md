# DROP Execution

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Execution contains the Functionality that implements Optimal Impact/Capture Based Trading Trajectories -
	Deterministic, Stochastic, Static, and Dynamic.


## Component Packages

 * [***Adaptive***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/adaptive)
DROP Execution Adaptive Package implements the Coordinate Variation Based Adaptive Execution.

 * [***ATHL***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/athl)
DROP Execution ATHL Package implements the Almgren, Thum, Hauptmann, and Li (2005) Calibration.

 * [***Bayesian***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/bayesian)
DROP Execution Bayesian Package implements the Bayesian Price Based Optimal Execution.

 * [***Capture***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/capture)
DROP Execution Capture Package contains the Execution Trajectory Transaction Cost Capture.

 * [***Cost***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/cost)
DROP Execution Cost Package contains the Linear Temporary Market Impact Cost.

 * [***Discrete***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/discrete)
DROP Execution Discrete Package implements the Trajectory Slice Execution Cost Distribution.

 * [***Dynamics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/dynamics)
DROP Execution Dynamics Package contains the Arithmetic Price Evolution Execution Parameters.

 * [***Evolution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/evolution)
DROP Execution Evolution Package holds the Execution Cost Market Impact Decomposition.

 * [***HJB***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/hjb)
DROP Execution HJB Package implements the Optimal Hamilton-Jacobi-Bellman Execution Functionality.

 * [***Impact***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/impact)
DROP Execution Impact Package contains the Market Impact Transaction Function Implementation.

 * [***Latent***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/latent)
DROP Execution Latent Package generates the Correlated Latent Market State Sequence.

 * [***Non-Adaptive***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/nonadaptive)
DROP Execution Non-Adaptive Package generates the Almgren-Chriss Static Optimal Trajectory.

 * [***Optimum***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/optimum)
DROP Execution Optimum Package generates the Almgren-Chriss Efficient Trading Trajectories.

 * [***Parameters***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/parameters)
DROP Execution Parameters Package contains the Empirical Market Impact Coefficients Calibration.

 * [***Principal***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/principal)
DROP Execution Principal Package implements Information Ratio Based Principal Trading.

 * [***Profile Time***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/profiletime)
DROP Execution Profile Time Package implements the Participation Rate Profile Time Models.

 * [***Risk***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/risk)
DROP Execution Risk Package contains the Optimal Execution MVO Efficient Frontier.

 * [***Sensitivity***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/sensitivity)
DROP Execution Sensitivity Package implements the Trajectory Control Nodes Sensitivity Greeks.

 * [***Strategy***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/strategy)
DROP Execution Strategy Package implements the Discrete/Continuous Trading Trajectory Schedule.

 * [***Trading Time***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/tradingtime)
DROP Execution Trading Time Package implements the Coordinated Variation Trading Time Models.


## References

 * Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>

 * Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i>
 	<b>3 (2)</b> 5-39

 * Almgren, R., and N. Chriss (2003): Optimal Execution with Nonlinear Impact Functions and Trading-
 	Enhanced Risk <i>Applied Mathematical Finance</i> <b>10 (1)</b> 1-18

 * Almgren, R., and N. Chriss (2003): Bidding Principles <i>Risk</i> 97-102

 * Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact <i>Risk</i> <b>18 (7)</b> 57-62

 * Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 	https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf

 * Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility <i>SIAM Journal of
 	Financial Mathematics</i> <b>3 (1)</b> 163-181

 * Artzner, P., F. Delbaen, J. M. Eber, and D. Heath (1999): Coherent Measures of Risk <i>Mathematical
 	Finance</i> <b>9</b> 203-228

 * Basak, S., and A. Shapiro (2001): Value-at-Risk Based Risk Management: Optimal Policies and Asset Prices
 	<i>Review of Financial Studies</i> <b>14</b> 371-405

 * Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial Markets</i>
 	<b>1</b> 1-50

 * Brunnermeier, L. K., and L. H. Pedersen (2005): Predatory Trading <i>Journal of Finance</i> <b>60 (4)</b>
 	1825-1863

 * Chan, L. K. C., and J. Lakonishak (1995): The Behavior of Stock Prices around Institutional Trades
 	<i>Journal of Finance</i> <b>50</b> 1147-1174

 * Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes <i>Mathematical Finance</i>
 	<b>11 (1)</b> 79-96

 * Huberman, G., and W. Stanzl (2004): Price Manipulation and Quasi-arbitrage <i>Econometrics</i> <b>72
 	(4)</b> 1247-1275

 * Jones, C. M., G. Kaul, and M. L. Lipson (1994): Transactions, Volume, and Volatility <i>Review of
 	Financial Studies</i> <b>7 (4)</b> 631-651

 * Keim, D. B., and A. Madhavan (1997): Transaction Costs and Investment Style: An Inter-exchange Analysis of
 	Institutional Equity Trades <i>Journal of Financial Economics</i> <b>46</b> 265-292

 * Kissell, R., and R. Malamut (2007): Algorithmic Decision Making Framework <i>Journal of Trading</i> <b>1
 	(1)</b> 12-21

 * Walia, N. (2006): <i>Optimal Trading: Dynamic Stock Liquidation Strategies</i> Princeton University


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
