# DROP Execution Principal Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Execution Principal Package implements Information Ratio Based Principal Trading.


## Class Components

 * [***Almgren 2003 Estimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/principal/Almgren2003Estimator.java)
 <i>Almgren2003Estimator</i> generates the Gross Profit Distribution and the Information Ratio for a given
 Level of Principal Discount for an Optimal Trajectory that is generated using the Almgren (2003) Scheme.

 * [***Gross Profit Estimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/principal/GrossProfitEstimator.java)
 <i>GrossProfitEstimator</i> generates the Gross Profit Distribution and the Information Ratio for a given
 Level of Principal Discount.

 * [***Gross Profit Expectation***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/principal/GrossProfitExpectation.java)
 <i>GrossProfitExpectation</i> implements the R<sup>1</sup> To R<sup>1</sup> Univariate that computes the
 Explicit Profit of a Principal Execution given the Optimal Trajectory.

 * [***Horizon Information Ratio Dependence***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/principal/HorizonInformationRatioDependence.java)
 <i>HorizonInformationRatioDependence</i> holds the Dependence Constants/Exponents for the Optimal
 Information Ratio and the corresponding Horizon.

 * [***Optimal Measure Dependence***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/principal/OptimalMeasureDependence.java)
 <i>OptimalMeasureDependence</i> contains the Dependence Exponents on Liquidity, Trade Size, and Permanent
 Impact Adjusted Principal Discount for the Optimal Principal Horizon and the Optional Information Ratio. It
 also holds the Constant.


# References

 * Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>

 * Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i>
 	<b>3 (2)</b> 5-39

 * Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk <i>Applied
 	Mathematical Finance</i> <b>10 (1)</b> 1-18

 * Almgren, R., and N. Chriss (2003): Bidding Principles <i>Risk</i> 97-102

 * Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact <i>Risk</i> <b>18 (7)</b> 57-62


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
