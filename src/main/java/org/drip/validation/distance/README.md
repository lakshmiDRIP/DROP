# DROP Validation Distance Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP XVA Validation Distance implements the Hypothesis Target Distance Test Builders.


## Class Components

 * [***GapLossFunction***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/distance/GapLossFunction.java)
 <i>GapLossFunction</i> holds the Function that Penalizes the Gap between the Empirical and the Hypothesis p-values.

 * [***GapLossWeightFunction***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/distance/GapLossWeightFunction.java)
 <i>GapLossWeightFunction</i> weighs the outcome of each Empirical Hypothesis Gap Loss.

 * [***GapTestOutcome***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/distance/GapTestOutcome.java)
 <i>GapTestOutcome</i> holds the Outcomes of a Distance Test of a Sample from the Hypothesis.

 * [***GapTestOutcome***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/distance/GapTestOutcome.java)
 <i>GapTestSetting</i> holds the Settings required to Control a Gap Test Run.

 * [***HypothesisOutcome***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/distance/HypothesisOutcome.java)
 <i>HypothesisOutcome</i> holds the Hypothesis ID and the its corresponding Gap Test Outcome.

 * [***HypothesisOutcomeSuite***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/distance/HypothesisOutcomeSuite.java)
 <i>HypothesisOutcomeSuite</i> holds the Map of Hypotheses Outcomes to be subjected to Discriminatory Power Analysis.

 * [***HypothesisSuite***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/distance/HypothesisSuite.java)
 <i>HypothesisSuite</i> holds the Map of Hypotheses to be subjected to Discriminatory Power Analysis.

 * [***HypothesisSuite***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/distance/HypothesisSuite.java)
 <i>HypothesisSuite</i> holds the Map of Hypotheses to be subjected to Discriminatory Power Analysis.

 * [***ImportanceWeight***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/distance/ImportanceWeight.java)
 <i>ImportanceWeight</i> weighs the Importance of each Empirical Hypothesis Outcome.


## References

 * Anfuso, F., D. Karyampas, and A. Nawroth (2017): A Sound Basel III Compliant Framework for Back-testing Credit Exposure Models https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2264620 <b>eSSRN</b>

 * Diebold, F. X., T. A. Gunther, and A. S. Tay (1998): Evaluating Density Forecasts with Applications to Financial Risk Management, International Economic Review 39 (4) 863-883

 * Kenyon, C., and R. Stamm (2012): <i>Discounting, LIBOR, CVA, and Funding: Interest Rate and Credit Pricing</i> <b>Palgrave Macmillan</b>

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
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
