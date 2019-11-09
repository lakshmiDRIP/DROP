# DROP Numerical Integration Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Numerical Integration Package implements the R<sup>1</sup> R<sup>d</sup> Numerical Integration Schemes.


## Class Components

 * [***AbscissaTransform***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/integrand/AbscissaTransform.java)
 <i>AbscissaTransform</i> transforms the Abscissa over into Corresponding Integrand Variable.

 * [***GaussKronrodQuadratureGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/integrand/GaussKronrodQuadratureGenerator.java)
 <i>GaussKronrodQuadratureGenerator</i> generates the Array of Gaussian Quadrature Based Abscissa and their
 corresponding Weights, with the Kronrod Extensions applied.

 * [***GaussLegendreQuadratureGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/integrand/GaussLegendreQuadratureGenerator.java)
 <i>GaussLegendreQuadratureGenerator</i> generates the Array of Orthogonal Legendre Polynomial Gaussian
 Quadrature Based Abscissa and their corresponding Weights.

 * [***GaussLobattoQuadratureGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/integrand/GaussLobattoQuadratureGenerator.java)
 <i>GaussLobattoQuadratureGenerator</i> generates the Array of Orthogonal Lobatto Polynomial Gaussian
 Quadrature Based Abscissa and their corresponding Weights.

 * [***GeneralizedMidPointQuadrature***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/integrand/GeneralizedMidPointQuadrature.java)
 <i>GeneralizedMidPointQuadrature</i> computes the R<sup>1</sup> Numerical Estimate of a Function Quadrature
 using the Generalized Mid-Point Scheme.

 * [***NestedQuadratureEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/integrand/NestedQuadratureEstimator.java)
 <i>NestedQuadratureEstimator</i> extends the R<sup>1</sup> Quadrature Estimator by providing the Estimation
 Error.

 * [***NewtonCotesQuadratureGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/integrand/NewtonCotesQuadratureGenerator.java)
 <i>NewtonCotesQuadratureGenerator</i> generates the Array of Newton-Cotes Based Quadrature Abscissa and
 their corresponding Weights.

 * [***QuadratureEstimate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/integrand/QuadratureEstimate.java)
 <i>QuadratureEstimate</i> contains the Estimate of the Integrand Quadrature and its corresponding Error.

 * [***QuadratureEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/integrand/QuadratureEstimator.java)
 <i>QuadratureEstimator</i> estimates an Integrand Quadrature using the Array of Transformed Quadrature
 Abscissa and their corresponding Weights.

 * [***R1ToR1Integrator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/integrand/R1ToR1Integrator.java)
 <i>R1ToR1Integrator</i> implements the following routines for integrating the R<sup>1</sup> To R<sup>1</sup>
 Objective Function.
 	* Linear Quadrature
 	* Mid-Point Scheme
 	* Trapezoidal Scheme
 	* Simpson/Simpson38 schemes
 	* Boole Scheme


## References

 * Briol, F. X., C. J. Oates, M. Girolami, and M. A. Osborne (2015): <i>Frank-Wolfe Bayesian Quadrature:
 Probabilistic Integration with Theoretical Guarantees</i> <b>arXiv</b>

 * Forsythe, G. E., M. A. Malcolm, and C. B. Moler (1977): <i>Computer Methods for Mathematical
 Computation</i> <b>Prentice Hall</b> Englewood Cliffs NJ

 * Leader, J. J. (2004): <i>Numerical Analysis and Scientific Computation</i> <b>Addison Wesley</b>

 * Stoer, J., and R. Bulirsch (1980): <i>Introduction to Numerical Analysis</i> <b>Springer-Verlag</b> New
 York

 * Wikipedia (2019): Numerical Integration https://en.wikipedia.org/wiki/Numerical_integration


## DROP Specifications

 * Main                     => https://lakshmidrip.github.io/DROP/
 * Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * GitHub                   => https://github.com/lakshmiDRIP/DROP
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
