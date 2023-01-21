# DROP Capital Shell Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Capital Shell Package holds the Economic Risk Capital Parameter Contexts.


## Class Components

 * [***AccountBusinessContext***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/shell/AccountBusinessContext.java)
 <i>AccountBusinessContext</i> maintains the Account To Business Mappings.

 * [***BusinessGroupingContext***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/shell/BusinessGroupingContext.java)
 <i>BusinessGroupingContext</i> maintains the Loaded Business Groupings.

 * [***CapitalEstimationContextContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/shell/CapitalEstimationContextContainer.java)
 <i>CapitalEstimationContextContainer</i> maintains all the Context Entities needed for a Full Economic Capital Estimation Run.

 * [***CapitalUnitStressEventContext***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/shell/CapitalUnitStressEventContext.java)
 <i>CapitalUnitStressEventContext</i> maintains the Systemic, Idiosyncratic, and Correlated Scenarios at the Capital Unit Coordinate Level.

 * [***CreditSpreadEventContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/shell/CreditSpreadEventContainer.java)
 <i>CreditSpreadEventContainer</i> maintains all the Credit Spread Events needed for a Full GSST Scenario Design Run.

 * [***PredictorScenarioSpecificationContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/shell/PredictorScenarioSpecificationContainer.java)
 <i>PredictorScenarioSpecificationContainer</i> maintains the Map of Predictors and their Scenario Stress Specification as well the Map of Predictors and their Categories.

 * [***RegionDigramContext***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/shell/RegionDigramContext.java)
 <i>RegionDigramContext</i> maintains the Loaded Region Digram Mapping.

 * [***RiskTypeContext***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/shell/RiskTypeContext.java)
 <i>RiskTypeContext</i> maintains the Loaded Mapping between Risk Code and Risk Type. Unmapped RBC's will be excluded.

 * [***SystemicScenarioPnLSeries***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/shell/SystemicScenarioPnLSeries.java)
 <i>SystemicScenarioPnLSeries</i> contains the PnL Series of a Systemic Stress Scenario.

 * [***SystemicScenarioPnLSeriesPAA***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/shell/SystemicScenarioPnLSeriesPAA.java)
 <i>SystemicScenarioPnLSeriesPAA</i> contains the PAA Category Decomposition of the PnL Series of a Systemic Stress Scenario.

 * [***VolatilityScaleContext***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/shell/VolatilityScaleContext.java)
 <i>VolatilityScaleContext</i> maintains the Loaded Risk-Factor Volatility Scale Mappings.


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
