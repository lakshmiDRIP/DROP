# DROP Validation Evidence Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Validation Evidence implements the Sample and Ensemble Evidence Processing.


## Class Components

 * [***Ensemble***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/evidence/Ensemble.java)
 <i>Ensemble</i> contains the Ensemble Collection of Statistical Samples and their Test Statistic Evaluators.

 * [***NativePITGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/evidence/NativePITGenerator.java)
 <i>NativePITGenerator</i> exposes Functionality to Generate Native Probability Integral Transforms on their Realizations.

 * [***Sample***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/evidence/Sample.java)
 <i>Sample</i> holds the Sample of Realizations.

 * [***TestStatisticAccumulator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/evidence/TestStatisticAccumulator.java)
 <i>TestStatisticAccumulator</i> contains the Instance Counts of the Sorted Test Statistic Values.

 * [***TestStatisticEvaluator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/evidence/TestStatisticEvaluator.java)
 <i>TestStatisticEvaluator</i> exposes the Function that must be applied on a Set to evaluate the Test Statistic.


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
