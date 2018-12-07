# DROP Regression Fixed Point Finder Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Regression Fixed Point Finder implements the Fixed Point Finder Regression Engine.


## Class Components

 * [***BracketingRegressorSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/fixedpointfinder/BracketingRegressorSet.java)
 <i>BracketingRegressorSet</i> implements regression run for the Primitive Bracketing Fixed Point Search
 Method. It implements the following 4 primitive bracketing schemes: Bisection, False Position, Quadratic,
 and Inverse Quadratic.

 * [***CompoundBracketingRegressorSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/fixedpointfinder/CompoundBracketingRegressorSet.java)
 <i>CompoundBracketingRegressorSet</i> implements regression run for the Compound Bracketing Fixed Point
 Search Method. It implements the following 2 compound bracketing schemes: Brent and Zheng.

 * [***FixedPointFinderRegressionEngine***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/fixedpointfinder/FixedPointFinderRegressionEngine.java)
 <i>FixedPointFinderRegressionEngine</i> implements the RegressionEngine for the Fixed Point Finder
 regression. It adds the OpenRegressorSet, the BracketingRegressorSet, and the
 CompoundBracketingRegressorSet, and launches the regression engine.

 * [***OpenRegressorSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/fixedpointfinder/OpenRegressorSet.java)
 <i>OpenRegressorSet</i> implements the regression run for the Open (i.e., Newton) Fixed Point Search Method.


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
