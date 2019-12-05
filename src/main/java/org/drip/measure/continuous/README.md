# DROP Measure Continuous Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Measure Continuous implements the R<sup>1</sup> R<sup>d</sup> Continuous Random Measure.


## Class Components

 * [***MultivariateMeta***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/MultivariateMeta.java)
 <i>MultivariateMeta</i> holds a Group of Variable Names - each of which separately is a Valid Single
 R<sup>1</sup>/R<sup>d</sup> Variable.

 * [***R1Multivariate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/R1Multivariate.java)
 <i>R1Multivariate</i> contains the Generalized Joint Multivariate R<sup>1</sup> Distributions.

 * [***R1R1***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/R1R1.java)
 <i>R1R1</i> implements the Base Abstract Class behind Bivariate R<sup>1</sup> Distributions. It exports
 Methods for Incremental, Cumulative, and Inverse Cumulative Distribution Densities.

 * [***R1Univariate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/R1Univariate.java)
 <i>R1Univariate</i> implements the Base Abstract Class behind R<sup>1</sup> Univariate Distributions. It exports the Methods for incremental, cumulative, and inverse cumulative distribution densities.

 * [***R1UnivariateExponential***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/R1UnivariateExponential.java)
 <i>R1UnivariateExponential</i> implements the Univariate R<sup>1</sup> Exponential Distribution. It implements the Incremental, the Cumulative, and the Inverse Cumulative Distribution Densities.

 * [***R1UnivariateScaledExponential***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/R1UnivariateScaledExponential.java)
 <i>R1UnivariateScaledExponential</i> implements the Probability Density Function for the Scaled Exponential Function.

 * [***R1UnivariateUniform***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/R1UnivariateUniform.java)
 <i>R1UnivariateUniform</i> implements the Univariate R<sup>1</sup> Uniform Distribution. It implements the Incremental, the Cumulative, and the Inverse Cumulative Distribution Densities.

 * [***Rd***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/Rd.java)
 <i>Rd</i> implements the Base Abstract Class behind R<sup>d</sup> Distributions. It exports Methods for
 incremental, cumulative, and inverse cumulative Distribution Densities.

 * [***RdR1***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/RdR1.java)
 <i>RdR1</i> implements the Base Abstract Class behind R<sup>d</sup> X R<sup>1</sup> Distributions. It
 exports Methods for incremental, cumulative, and inverse cumulative Distribution Densities.


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
