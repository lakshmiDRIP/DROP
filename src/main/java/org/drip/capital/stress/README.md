# DROP Capital Stress Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Capital Stress Package holds the Economic Risk Capital Stress Event Settings.


## Class Components

 * [***Event***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/stress/Event.java)
 <i>Event</i> holds the Coordinate-Level Parameterization of a Stress Event.

 * [***EventProbabilityContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/stress/EventProbabilityContainer.java)
 <i>EventProbabilityContainer</i> contains the Map of the Named Stress Event Probabilities.

 * [***EventProbabilityLadder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/stress/EventProbabilityLadder.java)
 <i>EventProbabilityLadder</i> contains the Probabilities and their corresponding Event Steps in a Ladder Progression.

 * [***EventSpecification***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/stress/EventSpecification.java)
 <i>EventSpecification</i> contains the Name of a Stress Event and its Probability.

 * [***IdiosyncraticEventContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/stress/IdiosyncraticEventContainer.java)
 <i>IdiosyncraticEventContainer</i> contains the Scenario Stress Events' Specifications of the Idiosyncratic Stress Scenario Event Type that belong inside of a single Coordinate.

 * [***PnLSeries***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/stress/PnLSeries.java)
 <i>PnLSeries</i> contains the PnL Series of a Single Event.

 * [***SystemicEventContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/stress/SystemicEventContainer.java)
 <i>SystemicEventContainer</i> contains the Scenario Stress Events' Specifications of the Systemic Stress Scenario Event Type that belong inside of a single Coordinate.


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
