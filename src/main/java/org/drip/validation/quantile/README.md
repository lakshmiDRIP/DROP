# DROP Validation Quantile Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Validation Quantile implements the Quantile Based Graphical Numerical Validators.


## Class Components

 * [***PlottingPosition***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/quantile/PlottingPosition.java)
 <i>PlottingPosition</i> holds the Order Statistic Ordinal and the Quantile corresponding to a Plotting Position.

 * [***PlottingPositionGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/quantile/PlottingPositionGenerator.java)
 <i>PlottingPositionGenerator</i> exposes all Plotting Position Generation Schemes - both Expectation Based and Median Based.

 * [***PlottingPositionGeneratorFilliben***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/quantile/PlottingPositionGeneratorFilliben.java)
 <i>PlottingPositionGeneratorFilliben</i> holds the Order Statistic Median Based Heuristic Plotting Position Generation Schemes.

 * [***PlottingPositionGeneratorHeuristic***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/quantile/PlottingPositionGeneratorHeuristic.java)
 <i>PlottingPositionGeneratorHeuristic</i> holds the Expected Order Statistic Based Heuristic Plotting Position Generation Schemes.

 * [***QQTestOutcome***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/quantile/QQTestOutcome.java)
 <i>QQTestOutcome</i> holds the Elements of the QQ Vertexes that come from a QQ Plot Run.

 * [***QQVertex***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/quantile/QQVertex.java)
 <i>QQVertex</i> holds the Elements in a single QQ Vertex - the Plotting Position and the Expected Order Statistics.


## References

 * Filliben, J. J. (1975): The Probability Plot Correlation Coefficient Test for Normality <i>Technometrics, American Society for Quality</i> <b>17 (1)</b> 111-117

 * Gibbons, J. D., and S. Chakraborti (2003): <i>Non-parametric Statistical Inference 4<sup>th</sup> Edition</i> <b>CRC Press</b>

 * Gnanadesikan, R. (1977): <i>Methods for Statistical Analysis of Multivariate Observations</i> <b>Wiley</b>

 * Thode, H. C. (2002): <i>Testing for Normality</i> <b>Marcel Dekker</b> New York

 * Wikipedia (2018): Q-Q Plot https://en.wikipedia.org/wiki/Q%E2%80%93Q_plot


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
