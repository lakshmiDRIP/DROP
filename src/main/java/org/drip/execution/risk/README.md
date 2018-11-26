# DROP Execution Risk Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Execution Risk Package contains the Optimal Execution MVO Efficient Frontier.


## Class Components

 * [***Mean Variance Objective Utility***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/risk/MeanVarianceObjectiveUtility.java)
 <i>MeanVarianceObjectiveUtility</i> implements the Mean-Variance Objective Utility Function that needs to be
 optimized to extract the Optimal Execution Trajectory.

 * [***Objective Utility***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/risk/ObjectiveUtility.java)
 <i>ObjectiveUtility</i> exposes the Objective Utility Function that needs to be optimized to extract the
 Optimal Execution Trajectory.

 * [***Power Variance Objective Utility***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/risk/PowerVarianceObjectiveUtility.java)
 <i>PowerVarianceObjectiveUtility</i> implements the Mean-Power-Variance Objective Utility Function that
 needs to be optimized to extract the Optimal Execution Trajectory. The Exact Objective Function is of the
 Form:

  			U[x] = E[x] + lambda * (V[x] ^p)

  where p is greater than 0.
  
  p = 1
  
  is the Regular Mean-Variance, and
  
  p = 0.5
  
  is VaR Minimization (L-VaR).


# References

 * Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>

 * Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i>
 	<b>3 (2)</b> 5-39

 * Almgren, R., and N. Chriss (2003): Optimal Execution with Nonlinear Impact Functions and Trading-
 	Enhanced Risk <i>Applied Mathematical Finance</i> <b>10 (1)</b> 1-18

 * Almgren, R. (2003): Optimal Execution with Non-linear Impact Functions and Trading Enhanced Risk
 	<i>Applied Mathematical Finance</i> <b>10 (1)</b> 1-18

 * Almgren, R., and N. Chriss (2003): Bidding Principles <i>Risk</i> 97-102

 * Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact <i>Risk</i> <b>18 (7)</b> 57-62

 * Artzner, P., F. Delbaen, J. M. Eber, and D. Heath (1999): Coherent Measures of Risk <i>Mathematical
 	Finance</i> <b>9</b> 203-228

 * Basak, S., and A. Shapiro (2001): Value-at-Risk Based Risk Management: Optimal Policies and Asset Prices
 	<i>Review of Financial Studies</i> <b>14</b> 371-405

 * Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial Markets</i>
 	<b>1</b> 1-50

 * Chan, L. K. C., and J. Lakonishak (1995): The Behavior of Stock Prices around Institutional Trades
 	<i>Journal of Finance</i> <b>50</b> 1147-1174

 * Keim, D. B., and A. Madhavan (1997): Transaction Costs and Investment Style: An Inter-exchange Analysis of
 	Institutional Equity Trades <i>Journal of Financial Economics</i> <b>46</b> 265-292


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
