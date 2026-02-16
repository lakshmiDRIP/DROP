# DROP Measure Distribution Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Measure Distribution implements the R<sup>1</sup> R<sup>d</sup> Continuous Random Measure.


## Class Components

 * [***I1ContinuousUniform***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/distribution/I1ContinuousUniform.java)
 <i>I1ContinuousUniform</i> implements the Univariate Bounded Uniform Integer Distribution, with the Integer being generated between a (n inclusive) lower and an upper Bound.

 * [***R1Continuous***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/distribution/R1Continuous.java)
 <i>R1Continuous</i> exposes the Base Abstract Class behind continuous R<sup>1</sup> Distributions. It exports the Methods for incremental, cumulative, and inverse cumulative distribution densities.

 * [***R1ContinuousPareto***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/distribution/R1ContinuousPareto.java)
 <i>R1ContinuousPareto</i> implements the R<sup>1</sup> Continuous Pareto Distribution.

 * [***R1ContinuousPowerLaw***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/distribution/R1ContinuousPowerLaw.java)
 <i>R1ContinuousPowerLaw</i> implements the R<sup>1</sup> Continuous Power Law Distribution.

 * [***R1ContinuousUniform***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/distribution/R1ContinuousUniform.java)
 <i>R1ContinuousUniform</i> implements the Univariate R<sup>1</sup> Uniform Distribution. It implements the Incremental, the Cumulative, and the Inverse Cumulative Distribution Densities.

 * [***R1ContinuousUniformPiecewiseDisplaced***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/distribution/R1ContinuousUniformPiecewiseDisplaced.java)
 <i>R1ContinuousUniformPiecewiseDisplaced</i> implements the Continuous Uniform Displaced Piecewise Linear R<sup>1</sup> Distributions. It exports the Methods corresponding to the R<sup>1</sup> Lebesgue Base Class.

 * [***R1ContinuousUniformPiecewiseLinear***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/distribution/R1ContinuousUniformPiecewiseLinear.java)
 <i>R1ContinuousUniformPiecewiseLinear</i> implements the Piecewise Linear R<sup>1</sup> Distributions. It exports the Methods corresponding to the R<sup>1</sup> Lebesgue Base Class.

 * [***R1Discrete***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/distribution/R1Discrete.java)
 <i>R1Discrete</i> implements the Discrete Distribution over the Combinatorial R<sup>1</sup> Outcomes.

 * [***R1R1Continuous***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/distribution/R1R1Continuous.java)
 <i>R1R1Continuous</i> implements the Base Abstract Class behind Bivariate R<sup>1</sup> Distributions. It exports Methods for Incremental, Cumulative, and Inverse Cumulative Distribution Densities.

 * [***RdContinuous***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/distribution/RdContinuous.java)
 <i>RdContinuous</i> implements the Base Abstract Class behind R<sup>d</sup> Distributions. It exports Methods for incremental, cumulative, and inverse cumulative Distribution Densities.

 * [***RdContinuousUniform***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/distribution/RdContinuousUniform.java)
 <i>RdContinuousUniform</i> contains the R<sup>d</sup> Continuous Uniform Distribution inside a Fixed Support.

 * [***RdR1Continuous***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/distribution/RdR1Continuous.java)
 <i>RdR1Continuous</i> implements the Base Abstract Class behind R<sup>d</sup> X R<sup>1</sup> Distributions. It exports Methods for incremental, cumulative, and inverse cumulative Distribution Densities.


## References

 * Devroye, L. (1986): <i>Non-Uniform Random Variate Generation</i> <b>Springer-Verlag</b> New York

 * Exponential Distribution (2019): Exponential Distribution https://en.wikipedia.org/wiki/Exponential_distribution

 * Norton, M., V. Khokhlov, and S. Uryasev (2019): Calculating CVaR and bPOE for Common Probability Distributions with Application to Portfolio Optimization and Density Estimation <i>Annals of Operations Research</i> <b>299 (1-2)</b> 1281-1315

 * Ross, S. M. (2009): <i>Introduction to Probability and Statistics for Engineers and Scientists 4<sup>th</sup> Edition</i> <b>Associated Press</b> New York, NY

 * Schmidt, D. F., and D. Makalic (2009): Universal Models for the Exponential Distribution <i>IEEE Transactions on Information Theory</i> <b>55 (7)</b> 3087-3090


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
