# DROP Measure Bayesian Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Measure Bayesian contains the Functionality to generate the Prior, Conditional, Posterior Theil Bayesian
	Distributions.


## Class Components

 * [***ConjugateParameterPrior***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian/ConjugateParameterPrior.java)
 <i>ConjugateParameterPrior</i> implements the Determinants of the Parameter of the Conjugate Prior.

 * [***ProjectionDistributionLoading***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian/ProjectionDistributionLoading.java)
 <i>ProjectionDistributionLoading</i> contains the Projection Distribution and its Loadings to the Scoping
 Distribution.

 * [***R1MultivariateConvolutionEngine***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian/R1MultivariateConvolutionEngine.java)
 <i>R1MultivariateConvolutionEngine</i> implements the Engine that generates the Joint/Posterior Distributions from the Prior and the Conditional Joint Multivariate R<sup>1</sup> Distributions.

 * [***R1MultivariateConvolutionMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian/R1MultivariateConvolutionMetrics.java)
 <i>R1MultivariateConvolutionMetrics</i> holds the Inputs and the Results of a Bayesian Multivariate Convolution Execution.

 * [***R1MultivariateNormalConvolutionEngine***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian/R1MultivariateNormalConvolutionEngine.java)
 <i>R1MultivariateNormalConvolutionEngine</i> implements the Engine that generates the Joint/Posterior Distribution from the Prior and the Conditional Joint R<sup>1</sup> Multivariate Normal Distributions.

 * [***ScopingProjectionVariateDistribution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian/ScopingProjectionVariateDistribution.java)
 <i>ScopingProjectionVariateDistribution</i> holds the Scoping Variate Distribution, the Projection Variate Distributions, and the Projection Variate Loadings based off of the Scoping Variates.

 * [***TheilMixedEstimationModel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian/TheilMixedEstimationModel.java)
 <i>TheilMixedEstimationModel</i> implements the Theil's Mixed Model for the Estimation of the Distribution Parameters.


## References

 * Devroye, L. (1986): <i>Non-Uniform Random Variate Generation</i> <b>Springer-Verlag</b> New York

 * Gamma Distribution (2019): Gamma Distribution https://en.wikipedia.org/wiki/Chi-squared_distribution

 * Louzada, F., P. L. Ramos, and E. Ramos (2019): A Note on Bias of Closed-Form Estimators for the Gamma Distribution Derived From Likelihood Equations <i>The American Statistician</i> <b>73 (2)</b> 195-199

 * Minka, T. (2002): Estimating a Gamma distribution https://tminka.github.io/papers/minka-gamma.pdf

 * Theil, H. (1971): <i>Principles of Econometrics</i> <b>Wiley</b>

 * Ye, Z. S., and N. Chen (2017): Closed-Form Estimators for the Gamma Distribution Derived from Likelihood Equations <i>The American Statistician</i> <b>71 (2)</b> 177-181


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
