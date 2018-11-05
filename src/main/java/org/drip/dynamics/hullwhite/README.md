# DROP Dynamics Hull White Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Dynamics Hull White Package implements the Hull White Latent State Evolution.

## Class Components

 * [***Short Rate Update***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/hullwhite/ShortRateUpdate.java)
 <i>ShortRateUpdate</i> records the Metrics associated with the Evolution of the Instantaneous Short Rate
 from a Starting to the Terminal Date.

 * [***Single Factor State Evolver***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/hullwhite/SingleFactorStateEvolver.java)
 <i>SingleFactorStateEvolver</i> provides the Hull-White One-Factor Gaussian HJM Short Rate Dynamics
 Implementation.

 * [***Trinomial Tree Node Metrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/hullwhite/TrinomialTreeNodeMetrics.java)
 <i>TrinomialTreeNodeMetrics</i> records the Metrics associated with each Node in the Trinomial Tree
 Evolution of the Instantaneous Short Rate using the Hull-White Model.

 * [***Trinomial Tree Sequence Metrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/hullwhite/TrinomialTreeSequenceMetrics.java)
 <i>TrinomialTreeSequenceMetrics</i> records the Evolution Metrics of the Hull-White Model Trinomial Tree
 Sequence.

 * [***Trinomial Tree Transition Metrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/hullwhite/TrinomialTreeTransitionMetrics.java)
 <i>TrinomialTreeTransitionMetrics</i> records the Transition Metrics associated with Node-to-Node Evolution
 of the Instantaneous Short Rate using the Hull-White Model Trinomial Tree.


## DROP Specifications

 * Main                     => https://lakshmidrip.github.io/DROP/
 * Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * GitHub                   => https://github.com/lakshmiDRIP/DROP
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
