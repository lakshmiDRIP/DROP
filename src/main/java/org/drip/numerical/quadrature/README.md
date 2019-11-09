# DROP Numerical Quadrature Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Numerical Quadrature Package implements the R<sup>1</sup> Gaussian Integration Quadrature Schemes.


## Class Components

 * [***GolubWelsch***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/quadrature/GolubWelsch.java)
 <i>GolubWelsch</i> implements the Golub-Welsch Algorithm that extracts the Quadrature Nodes and Weights.

 * [***IntegrandGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/quadrature/IntegrandGenerator.java)
 <i>IntegrandGenerator</i> contains the Settings that enable the Generation of Integrand Quadrature and
 Weights for the Specified Orthogonal Polynomial Scheme.

 * [***OrthogonalPolynomial***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/quadrature/OrthogonalPolynomial.java)
 <i>OrthogonalPolynomial</i> implements a Single Basis Orthogonal Polynomial used in the Construction of the
 Quadrature.

 * [***OrthogonalPolynomialSuite***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/quadrature/OrthogonalPolynomialSuite.java)
 <i>OrthogonalPolynomialSuite</i> holds the Suite of Basis Orthogonal Polynomials used in the Construction of
 the Quadrature.

 * [***WeightFunctionBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/quadrature/WeightFunctionBuilder.java)
 <i>WeightFunctionBuilder</i> builds the Weight Function associated with Different Kinds of Orthogonal Basis
 Polynomials.


## References

 * Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book on
 Mathematics</b>

 * Gil, A., J. Segura, and N. M. Temme (2007): <i>Numerical Methods for Special Functions</i> <b>Society for
 Industrial and Applied Mathematics</b> Philadelphia

 * Press, W. H., S. A. Teukolsky, W. T. Vetterling, and B. P. Flannery (2007): <i>Numerical Recipes: The Art
 of Scientific Computing 3rd Edition</i> <b>Cambridge University Press</b> New York

 * Stoer, J., and R. Bulirsch (2002): <i>Introduction to Numerical Analysis 3rd Edition</i> <b>Springer</b>

 * Wikipedia (2019): Gaussian Quadrature https://en.wikipedia.org/wiki/Gaussian_quadrature


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
