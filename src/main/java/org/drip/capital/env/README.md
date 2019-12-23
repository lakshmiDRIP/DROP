# DROP Capital Env Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Capital Env Package implements the Economic Risk Capital Parameter Factories.


## Class Components

 * [***AccountBusinessFactory***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/env/AccountBusinessFactory.java)
 <i>AccountBusinessFactory</i> instantiates the Built-in Account To Business Mappings.

 * [***BusinessGroupingFactory***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/env/BusinessGroupingFactory.java)
 <i>BusinessGroupingFactory</i> instantiates the Built-in Business Groupings.

 * [***CapitalEstimationContextManager***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/env/CapitalEstimationContextManager.java)
 <i>CapitalEstimationContextManager</i> initializes the Capital Estimation Context Settings.

 * [***CapitalUnitStressEventFactory***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/env/CapitalUnitStressEventFactory.java)
 <i>CapitalUnitStressEventFactory</i> instantiates the Built-in Systemic, Idiosyncratic, and Correlated Events at the Capital Unit Coordinate Level.

 * [***RegionDigramFactory***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/env/RegionDigramFactory.java)
 <i>RegionDigramFactory</i> instantiates the Built-in Region Digram Mapping.

 * [***RiskTypeFactory***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/env/RiskTypeFactory.java)
 <i>RiskTypeFactory</i> instantiates the Built-in Mapping between Risk Code and Risk Type. Unmapped Risk Codes will be excluded.

 * [***SystemicScenarioDefinitionContextManager***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/env/SystemicScenarioDefinitionContextManager.java)
 <i>SystemicScenarioDefinitionContextManager</i> sets up the Predictor Scenario Specification Container.

 * [***SystemicScenarioDesignContextManager***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/env/SystemicScenarioDesignContextManager.java)
 <i>SystemicScenarioDesignContextManager</i> sets up the Credit Spread Event Container.

 * [***VolatilityScaleFactory***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/env/VolatilityScaleFactory.java)
 <i>VolatilityScaleFactory</i> instantiates the Built-in Risk-Factor Volatility Scale Mappings.


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
