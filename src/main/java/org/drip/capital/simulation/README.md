# DROP Capital Simulation Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Capital Simulation Package holds the Economic Risk Capital Simulation Ensemble.


## Class Components

 * [***CapitalSegmentPathEnsemble***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/simulation/CapitalSegmentPathEnsemble.java)
 <i>CapitalSegmentPathEnsemble</i> generates the Ensemble of Capital Paths from the Simulation PnL Realizations for the Capital Units under the specified Capital Segments.

 * [***CapitalUnitPathEnsemble***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/simulation/CapitalUnitPathEnsemble.java)
 <i>CapitalUnitPathEnsemble</i> generates the Ensemble of Capital Paths from the Simulation PnL Realizations for the specified Capital Unit.

 * [***EnsemblePnLDistribution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/simulation/EnsemblePnLDistribution.java)
 <i>EnsemblePnLDistribution</i> contains the PnL Distribution from Realized Path Ensemble.

 * [***EnsemblePnLDistributionGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/simulation/EnsemblePnLDistributionGenerator.java)
 <i>EnsemblePnLDistributionGenerator</i> exposes the Functionality to generate the PnL Distribution from the Realized Path Ensemble.

 * [***FSPnLDecomposition***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/simulation/FSPnLDecomposition.java)
 <i>FSPnLDecomposition</i> holds the Per FS PnL Decomposition.

 * [***FSPnLDecompositionContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/simulation/FSPnLDecompositionContainer.java)
 <i>FSPnLDecompositionContainer</i> holds the Series of Decomposed FS PnLs.

 * [***PathEnsemble***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/simulation/PathEnsemble.java)
 <i>PathEnsemble</i> exposes the Ensemble of Capital Paths from the Simulation PnL Realizations.

 * [***PathPnLRealization***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/simulation/PathPnLRealization.java)
 <i>PathPnLRealization</i> holds the Realized PnL and its Components along a Simulated Path.

 * [***StressEventIncidence***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/simulation/StressEventIncidence.java)
 <i>StressEventIncidence</i> holds the Name, the Type, and the PnL induced by a Stress Event Occurrence.

 * [***StressEventIncidenceEnsemble***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/simulation/StressEventIncidenceEnsemble.java)
 <i>StressEventIncidenceEnsemble</i> holds the Ensemble of Stress Event Occurrences.

 * [***StressEventIndicator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/simulation/StressEventIndicator.java)
 <i>StressEventIndicator</i> holds the Systemic and the Idiosyncratic Stress Event Indicators corresponding to the specified Entity.


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
