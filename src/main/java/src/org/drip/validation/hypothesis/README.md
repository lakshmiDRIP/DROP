# DROP Validation Hypothesis Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Validation Hypothesis implements the Statistical Hypothesis Validation Test Suite.


## Class Components

 * [***HistogramTestOutcome***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/hypothesis/HistogramTestOutcome.java)
 <i>HistogramTestOutcome</i> contains the p-value Cumulative and Incremental Histograms across the Test Statistic.

 * [***HistogramTestSetting***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/hypothesis/HistogramTestSetting.java)
 <i>HistogramTestSetting</i> holds the Settings required to conduct a Histogram Test.

 * [***ProbabilityIntegralTransform***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/hypothesis/ProbabilityIntegralTransform.java)
 <i>ProbabilityIntegralTransform</i> holds the PIT Distribution CDF of the Test-Statistic Response over the Outcome Instances.

 * [***ProbabilityIntegralTransformTest***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/hypothesis/ProbabilityIntegralTransformTest.java)
 <i>ProbabilityIntegralTransformTest</i> implements Comparison Tests post a PIT Transform on the Hypothesis and/or Test Sample.

 * [***SignificanceTestOutcome***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/hypothesis/SignificanceTestOutcome.java)
 <i>SignificanceTestOutcome</i> contains the Results of the Significant Test of the Statistical Hypothesis.

 * [***SignificanceTestSetting***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/hypothesis/SignificanceTestSetting.java)
 <i>SignificanceTestSetting</i> contains the Control Settings that determine the Success/Failure of the specified Statistical Hypothesis p-Test.

 * [***StatisticalTestOutcome***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/hypothesis/StatisticalTestOutcome.java)
 <i>StatisticalTestOutcome</i> contains the Results of the Significant Test and t-Test of the given Statistical Hypothesis.

 * [***TTestOutcome***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/hypothesis/TTestOutcome.java)
 <i>TTestOutcome</i> holds the Results of a Statistic Hypothesis t-Test.


## References

 * Anfuso, F., D. Karyampas, and A. Nawroth (2017): A Sound Basel III Compliant Framework for Back-testing Credit Exposure Models https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2264620 <b>eSSRN</b>

 * Bhattacharya, B., and D. Habtzghi (2002): Median of the p-value under the Alternate Hypothesis <i>American Statistician</i> <b>56 (3)</b> 202-206

 * Diebold, F. X., T. A. Gunther, and A. S. Tay (1998): Evaluating Density Forecasts with Applications to Financial Risk Management <i>International Economic Review</i> <b>39 (4)</b> 863-883

 * Head, M. L., L. Holman, R, Lanfear, A. T. Kahn, and M. D. Jennions (2015): The Extent and Consequences of p-Hacking in Science <i>PLoS Biology</i> <b>13 (3)</b> e1002106

 * Kenyon, C., and R. Stamm (2012): <i>Discounting, LIBOR, CVA, and Funding: Interest Rate and Credit Pricing</i> <b>Palgrave Macmillan</b>

 * Wasserstein, R. L., and N. A. Lazar (2016): The ASA Statement on p-values: Context, Process, and Purpose <i>American Statistician</i> <b>70 (2)</b> 129-133

 * Wetzels, R., D. Matzke, M. D. Lee, J. N. Rouder, G, J, Iverson, and E. J. Wagenmakers (2011): Statistical Evidence in Experimental Psychology: An Empirical Comparison using 855 t-Tests <i>Perspectives in Psychological Science</i> <b>6 (3)</b> 291-298

 * Wikipedia (2018): Probability Integral Transform https://en.wikipedia.org/wiki/Probability_integral_transform

 * Wikipedia (2019): p-value https://en.wikipedia.org/wiki/P-value


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
