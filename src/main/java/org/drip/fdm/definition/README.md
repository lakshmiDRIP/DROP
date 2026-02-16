# DROP FDM Definition Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP FDM Definition holds the Finite Difference PDE Evolver Schemes.


## Class Components

 * [***Diffusion1DNumericalEvolver***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/fdm/definition/Diffusion1DNumericalEvolver.java)
 <i>Diffusion1DNumericalEvolver</i> implements key Finite Difference Diffusion Schemes for R<sup>1</sup> State Factor Space Evolution.

 * [***Diffusion1DPDE***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/fdm/definition/Diffusion1DPDE.java)
 <i>Diffusion1DPDE</i> implements the Evolution of R<sup>1</sup> State Factor Space Response using the Diffusion PDE.

 * [***EvolutionGrid1D***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/fdm/definition/EvolutionGrid1D.java)
 <i>EvolutionGrid1D</i> maintains the Time and Factor Predictor Grids R<sup>1</sup> State Response Evolution.

 * [***R1EvolutionSnapshot***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/fdm/definition/R1EvolutionSnapshot.java)
 <i>R1EvolutionSnapshot</i> maintains the time Snapshots for R<sup>1</sup> State Factor Space Evolution.

 * [***R1StateResponseSnapshot***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/fdm/definition/R1StateResponseSnapshot.java)
 <i>R1StateResponseSnapshot</i> maintains the R<sup>1</sup> State Factor Space Snapshot.

 * [***R1StateResponseSnapshotDiagnostics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/fdm/definition/R1StateResponseSnapshotDiagnostics.java)
 <i>R1StateResponseSnapshotDiagnostics</i> augments <i>R1StateResponseSnapshot</i> by collecting additional Snapshot Diagnostics, i.e., State Response Time-shift Jacobian, the State Response array, and the von-Newmann stability metric array.

 * [***SecondOrder1DNumericalEvolver***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/fdm/definition/SecondOrder1DNumericalEvolver.java)
 <i>SecondOrder1DNumericalEvolver</i> implements key Second Order Finite Difference Schemes for R<sup>1</sup> State Factor Space Evolution.

 * [***SecondOrder1DPDE***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/fdm/definition/SecondOrder1DPDE.java)
 <i>SecondOrder1DPDE</i> implements the Evolution of R<sup>1</sup> State Factor Space Response using a Second Order 1D PDE.


## References

 * Datta, B. N. (2010): <i>Numerical Linear Algebra and Applications 2<sup>nd</sup> Edition</i> <b>SIAM</b> Philadelphia PA

 * Cebeci, T. (2002): <i>Convective Heat Transfer</i> <b>Horizon Publishing</b> Hammond IN

 * Crank, J., and P. Nicolson (1947): A Practical Method for Numerical Evaluation of Solutions of Partial Differential Equations of the Heat Conduction Type <i>Proceedings of the Cambridge Philosophical Society</i> <b>43 (1)</b> 50-67

 * Thomas, J. W. (1995): <i>Numerical Partial Differential Equations: Finite Difference Methods</i> <b>Springer-Verlag</b> Berlin, Germany

 * Wikipedia (2023): Alternating-direction implicit method https://en.wikipedia.org/wiki/Alternating-direction_implicit_method

 * Wikipedia (2024): Crank–Nicolson method https://en.wikipedia.org/wiki/Crank%E2%80%93Nicolson_method


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
