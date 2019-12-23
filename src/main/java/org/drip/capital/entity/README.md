# DROP Capital Entity Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Capital Entity Package implements the Economic Risk Capital Estimation Nodes.


## Class Components

 * [***CapitalSegment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/entity/CapitalSegment.java)
 <i>CapitalSegment</i> exposes the VaR and the Stress Functionality for a Capital Segment.

 * [***CapitalSimulator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/entity/CapitalSimulator.java)
 <i>CapitalSimulator</i> exposes the Simulator for the VaR and the Stress Functionality for a given Capital Entity - Segment or Unit.

 * [***CapitalUnit***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/entity/CapitalUnit.java)
 <i>CapitalUnit</i> implements the VaR and the Stress Functionality for the specified Capital Unit.

 * [***CapitalUnitEventContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/entity/CapitalUnitEventContainer.java)
 <i>CapitalUnitEventContainer</i> contains all the Stress Event Specifications across all of the Event Types that belong inside of the a Capital Unit.

 * [***ManagedSegmentL1***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/entity/ManagedSegmentL1.java)
 <i>ManagedSegmentL1</i> implements the VaR and the Stress Functionality inside of the L1 Managed Segment.

 * [***ManagedSegmentLn***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/entity/ManagedSegmentLn.java)
 <i>ManagedSegmentLn</i> implements the VaR and the Stress Functionality inside of the Ln Managed Segment.


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
