# DROP Measure Bayesian Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Measure Bayesian contains the Functionality to generate the Prior, Conditional, Posterior Theil Bayesian
	Distributions.


## Class Components

 * [***JointPosteriorMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian/JointPosteriorMetrics.java)
 <i>JointPosteriorMetrics</i> holds the Inputs and the Results of a Bayesian Computation Execution.

 * [***JointR1CombinationEngine***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian/JointR1CombinationEngine.java)
 <i>JointR1CombinationEngine</i> implements the Engine that generates the Combined/Posterior Distributions
 from the Prior and the Conditional Joint Multivariate R<sup>1</sup> Distributions.

 * [***JointR1NormalCombinationEngine***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian/JointR1NormalCombinationEngine.java)
 <i>JointR1NormalCombinationEngine</i> implements the Engine that generates the Combined/Posterior
 Distribution from the Prior and the Conditional Joint R<sup>1</sup> Multivariate Normal Distributions.

 * [***ProjectionDistributionLoading***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian/ProjectionDistributionLoading.java)
 <i>ProjectionDistributionLoading</i> contains the Projection Distribution and its Loadings to the Scoping
 Distribution.

 * [***ScopingProjectionVariateDistribution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian/ScopingProjectionVariateDistribution.java)
 <i>ScopingProjectionVariateDistribution</i> holds the Scoping Variate Distribution, the Projection Variate Distributions, and the Projection Variate Loadings based off of the Scoping Variates.

 * [***TheilMixedEstimationModel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian/TheilMixedEstimationModel.java)
 <i>TheilMixedEstimationModel</i> implements the Theil's Mixed Model for the Estimation of the Distribution Parameters.


## References

 * Theil, H. (1971): <i>Principles of Econometrics</i> <b>Wiley</b>


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
