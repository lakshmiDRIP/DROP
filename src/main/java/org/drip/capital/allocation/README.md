# DROP Capital Allocation Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Capital Allocation Package contains Economic Risk Capital Entity Allocation.


## Class Components

 * [***CorrelationCategoryBeta***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/allocation/CorrelationCategoryBeta.java)
 <i>CorrelationCategoryBeta</i> exposes the Correlation Category Beta Loading and its Elasticity (FIXED/FLOAT).

 * [***CorrelationCategoryBetaManager***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/allocation/CorrelationCategoryBetaManager.java)
 <i>CorrelationCategoryBetaManager</i> holds the Beta Loading Map Scheme for the different Correlation Categories.

 * [***EntityCapital***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/allocation/EntityCapital.java)
 <i>EntityCapital</i> holds the Capital for each Entity.

 * [***EntityCapitalAssignmentSetting***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/allocation/EntityCapitalAssignmentSetting.java)
 <i>EntityCapitalAssignmentSetting</i> holds the Correlation Elasticities for the different Capital Components as well as the Entity's Correlation Category.

 * [***EntityComponentAssignmentScheme***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/allocation/EntityComponentAssignmentScheme.java)
 <i>EntityComponentAssignmentScheme</i> holds the Indicators for the BETA and the PRO RATA Capital Allocation Schemes.

 * [***EntityComponentCapital***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/allocation/EntityComponentCapital.java)
 <i>EntityComponentCapital</i> holds the Component Capital for each Entity.

 * [***EntityComponentCapitalAssignment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/allocation/EntityComponentCapitalAssignment.java)
 <i>EntityComponentCapitalAssignment</i> contains the Capital Assignment for each Entity and its Component.

 * [***EntityComponentCorrelationCategory***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/allocation/EntityComponentCorrelationCategory.java)
 <i>EntityComponentCorrelationCategory</i> holds the Indicators of different Correlation Categories used under the BETA Capital Allocation Scheme.

 * [***EntityComponentElasticityAttribution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/allocation/EntityComponentElasticityAttribution.java)
 <i>EntityComponentElasticityAttribution</i> holds the Attributions of a single Individual Entity Component into Fixed, Float, and Pro-rata Elasticities.

 * [***EntityComponentProRataCategory***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/allocation/EntityComponentProRataCategory.java)
 <i>EntityComponentProRataCategory</i> holds the Indicators of different Pro-Rata Categories used under the PRO-RATA Capital Allocation Scheme.

 * [***EntityElasticityAttribution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/allocation/EntityElasticityAttribution.java)
 <i>EntityElasticityAttribution</i> holds the Attributions across all Entity Components into Fixed, Float, and Pro-rata Elasticities.


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
