# DROP Portfolio Construction Bayesian Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Portfolio Construction Bayesian Package implements Black Litterman Bayesian Portfolio Construction.


## Class Components

 * [***BlackLittermanCombinationEngine***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/bayesian/BlackLittermanCombinationEngine.java)
 <i>BlackLittermanCombinationEngine</i> implements the Engine that generates the Combined/Posterior
 Distributions from the Prior and the Conditional Joint R<sup>1</sup> Multivariate Normal Distributions.

 * [***BlackLittermanCustomConfidenceOutput***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/bayesian/BlackLittermanCustomConfidenceOutput.java)
 <i>BlackLittermanCustomConfidenceOutput</i> holds the Outputs generated from a Custom Confidence Black
 Litterman Bayesian COmbination Run.

 * [***BlackLittermanOutput***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/bayesian/BlackLittermanOutput.java)
 <i>BlackLittermanOutput</i> holds the essential Outputs generated from either a Full or a Custom Confidence
 of the Projection Black Litterman Bayesian Combination Run.

 * [***MeucciViewUncertaintyParameterization***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/bayesian/MeucciViewUncertaintyParameterization.java)
 <i>MeucciViewUncertaintyParameterization</i> demonstrates the Meucci Parameterization for the View
 Projection Uncertainty Matrix.

 * [***PriorControlSpecification***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/bayesian/PriorControlSpecification.java)
 <i>PriorControlSpecification</i> contains the Black Litterman Prior Specification Settings.

 * [***ProjectionExposure***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/bayesian/ProjectionExposure.java)
 <i>ProjectionExposure</i> holds the Projection Exposure Loadings that Weight the Exposure to the Projection
 Pick Portfolio.

 * [***ProjectionImpliedConfidenceOutput***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/bayesian/ProjectionImpliedConfidenceOutput.java)
 <i>ProjectionImpliedConfidenceOutput</i> holds the Results of the Idzorek 2005 Black Litterman Intuitive
 Projection Confidence Level Estimation Run.

 * [***ProjectionSpecification***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/bayesian/ProjectionSpecification.java)
 <i>ProjectionSpecification</i> contains the Black Litterman Projection Specification Settings.


## References

 * He. G., and R. Litterman (1999): <i>The Intuition behind the Black-Litterman Model Portfolios</i>
 <b>Goldman Sachs Asset Management</b>

 * Idzorek, T. (2005): <i>A Step-by-Step Guide to the Black-Litterman Model: Incorporating User-Specified
 Confidence Levels</i> <b>Ibbotson Associates</b> Chicago, IL

 * Meucci, A. (2005): <i>Risk and Asset Allocation</i> <b>Springer Finance</b>


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
