# DROP Validation Risk Factor Single Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Validation Risk Factor Single implements the Single Risk Factor Aggregate Tests.


## Class Components

 * [***DiscriminatoryPowerAnalyzer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/riskfactorsingle/DiscriminatoryPowerAnalyzer.java)
 <i>DiscriminatoryPowerAnalyzer</i> implements the Discriminatory Power Analyzer for the given Sample across the One/More Hypothesis at a Single Event.

 * [***DiscriminatoryPowerAnalyzerAggregate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/riskfactorsingle/DiscriminatoryPowerAnalyzerAggregate.java)
 <i>DiscriminatoryPowerAnalyzerAggregate</i> implements the Discriminatory Power Analyzer for the given Sample across the One/More Hypothesis and Multiple Events.

 * [***EventAggregationWeightFunction***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/riskfactorsingle/EventAggregationWeightFunction.java)
 <i>EventAggregationWeightFunction</i> exposes the Aggregation Weight for the given Event.

 * [***GapTestOutcomeAggregate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/riskfactorsingle/GapTestOutcomeAggregate.java)
 <i>GapTestOutcomeAggregate</i> holds the Map of Event Gap Test Outcomes and the Aggregate DPA Distance Metric for a <b>Single <i>Hypothesis</i></b>.

 * [***HypothesisOutcomeAggregate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/riskfactorsingle/HypothesisOutcomeAggregate.java)
 <i>HypothesisOutcomeAggregate</i> holds the Hypothesis and its corresponding Gap Test Outcome Aggregate.

 * [***HypothesisOutcomeSuiteAggregate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/riskfactorsingle/HypothesisOutcomeSuiteAggregate.java)
 <i>HypothesisOutcomeSuiteAggregate</i> holds the Map of Hypothesis and its corresponding Gap Test Outcome Aggregate.

 * [***HypothesisSuiteAggregate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/riskfactorsingle/HypothesisSuiteAggregate.java)
 <i>HypothesisSuiteAggregate</i> holds Indexed Hypothesis Ensembles across One/More Event Points.


## References

 * Anfuso, F., D. Karyampas, and A. Nawroth (2017): A Sound Basel III Compliant Framework for Back-testing Credit Exposure Models https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2264620 <b>eSSRN</b>

 * Diebold, F. X., T. A. Gunther, and A. S. Tay (1998): Evaluating Density Forecasts with Applications to Financial Risk Management <i>International Economic Review</i> <b>39 (4)</b> 863-883

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
