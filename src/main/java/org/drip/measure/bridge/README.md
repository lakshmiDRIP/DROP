# DROP Measure Bridge Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Measure Bridge implements the Broken Date Brownian Bridge Interpolator.


## Class Components

 * [***BrokenDateInterpolator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bridge/BrokenDateInterpolator.java)
 <i>BrokenDateInterpolator</i> exposes the Ability to Interpolate the Realized Path Value between two Broken
 Dates.

 * [***BrokenDateInterpolatorBrownian3P***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bridge/BrokenDateInterpolatorBrownian3P.java)
 <i>BrokenDateInterpolatorBrownian3P</i> Interpolates the Broken Dates using Three Stochastic Value Nodes
 using the Three Point Brownian Bridge Scheme.

 * [***BrokenDateInterpolatorLinearT***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bridge/BrokenDateInterpolatorLinearT.java)
 <i>BrokenDateInterpolatorLinearT</i> Interpolates using Two Stochastic Value Nodes with Linear Scheme. The
 Scheme is Linear in Time.

 * [***BrokenDateInterpolatorSqrtT***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bridge/BrokenDateInterpolatorSqrtT.java)
 <i>BrokenDateInterpolatorSqrtT</i> Interpolates using Two Stochastic Value Nodes with Linear Scheme. The Scheme is Linear in Square Root of Time.


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
