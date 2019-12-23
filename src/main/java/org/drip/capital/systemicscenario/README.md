# DROP Capital Systemic Scenario Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Capital Systemic Scenario Package implements the Systemic Stress Scenario Design/Construction.


## Class Components

 * [***CapitalBaselineDefinition***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/systemicscenario/CapitalBaselineDefinition.java)
 <i>CapitalBaselineDefinition</i> holds the Capital Baseline Estimates for the Historical Scenarios.

 * [***CreditSpreadEvent***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/systemicscenario/CreditSpreadEvent.java)
 <i>CreditSpreadEvent</i> contains the Specifications of Criteria corresponding to a Credit Spread Event.

 * [***Criterion***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/systemicscenario/Criterion.java)
 <i>Criterion</i> contains the Specification Details of a Credit Spread Event Criterion.

 * [***CriterionUnit***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/systemicscenario/CriterionUnit.java)
 <i>CriterionUnit</i> maintains a List of the Possible Criterion Units.

 * [***HistoricalScenarioDefinition***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/systemicscenario/HistoricalScenarioDefinition.java)
 <i>HistoricalScenarioDefinition</i> holds the Realizations of the Historical Stress Scenarios.

 * [***HypotheticalScenarioDefinition***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/systemicscenario/HypotheticalScenarioDefinition.java)
 <i>HypotheticalScenarioDefinition</i> holds the Realizations of the Hypothetical Stress Scenarios.

 * [***MarketSegment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/systemicscenario/MarketSegment.java)
 <i>MarketSegment</i> maintains a List of the Applicable Market Segments.

 * [***PredictorScenarioSpecification***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/systemicscenario/PredictorScenarioSpecification.java)
 <i>PredictorScenarioSpecification</i> specifies the Full Stress Scenario Specification for the given Predictor across Market Segments.

 * [***StressScenarioQuantification***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/systemicscenario/StressScenarioQuantification.java)
 <i>StressScenarioQuantification</i> specifies the Unit and the Type of Change for the given Market Factor/Applicability Combination.

 * [***StressScenarioSpecification***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/systemicscenario/StressScenarioSpecification.java)
 <i>StressScenarioSpecification</i> specifies the Full Stress Scenario Specification for the given Market Factor/Applicability Combination.

 * [***SystemicStressShockIndicator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/systemicscenario/SystemicStressShockIndicator.java)
 <i>SystemicStressShockIndicator</i> holds the Directional Indicator Settings for a given Systemic Stress Shock Event.

 * [***TypeOfChange***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/systemicscenario/TypeOfChange.java)
 <i>TypeOfChange</i> maintains a List of the Possible Types of Change.


## References

 * Bank for International Supervision (2005): Stress Testing at Major Financial Institutions: Survey Results and Practice https://www.bis.org/publ/cgfs24.htm

 * Glasserman, P. (2004): <i>Monte Carlo Methods in Financial Engineering</i> <b>Springer</b>

 * Kupiec, P. H. (2000): Stress Tests and Risk Capital <i>Risk</i> <b>2 (4)</b> 27-39


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
