# DROP Measure Continuous Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Measure Continuous implements the R<sup>1</sup> R<sup>d</sup> Continuous Random Measure.


## Class Components

 * [***MetaRd***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/MetaRd.java)
 <i>MetaRd</i> holds a Group of Variable Names - each of which separately is a Valid Single R<sup>1</sup>/R<sup>d</sup> Variable.

 * [***MetaRdDistribution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/MetaRdDistribution.java)
 <i>MetaRdDistribution</i> contains the Generalized R<sup>1</sup> Multivariate Distributions.

 * [***R1Distribution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/R1Distribution.java)
 <i>R1Distribution</i> exposes the Base Abstract Class behind continuous R<sup>1</sup> Distributions. It exports the Methods for incremental, cumulative, and inverse cumulative distribution densities.

 * [***R1ParetoDistribution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/R1ParetoDistribution.java)
 <i>R1ParetoDistribution</i> implements the R<sup>1</sup> Pareto Distribution.

 * [***R1PowerLawDistribution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/R1PowerLawDistribution.java)
 <i>R1PowerLawDistribution</i> implements the R<sup>1</sup> Power Law Distribution.

 * [***R1R1Distribution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/R1R1Distribution.java)
 <i>R1R1Distribution</i> implements the Base Abstract Class behind Bivariate R<sup>1</sup> Distributions. It exports Methods for Incremental, Cumulative, and Inverse Cumulative Distribution Densities.

 * [***R1UniformDistribution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/R1UniformDistribution.java)
 <i>R1UniformDistribution</i> implements the Univariate R<sup>1</sup> Uniform Distribution. It implements the Incremental, the Cumulative, and the Inverse Cumulative Distribution Densities.

 * [***RdDistribution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/RdDistribution.java)
 <i>RdDistribution</i> implements the Base Abstract Class behind R<sup>d</sup> Distributions. It exports Methods for incremental, cumulative, and inverse cumulative Distribution Densities.

 * [***RdR1Distribution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/RdR1Distribution.java)
 <i>RdR1Distribution</i> implements the Base Abstract Class behind R<sup>d</sup> X R<sup>1</sup> Distributions. It exports Methods for incremental, cumulative, and inverse cumulative Distribution Densities.


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
