# DROP Spline Variance Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Spline illustrates Basis Monic Multic Tension Splines.


## Class Components

 * [***BasisBSplineSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/spline/BasisBSplineSet.java)
 <i>BasisBSplineSet</i> implements Samples for the Construction and the usage of various basis spline functions. It demonstrates the following:
 	* Construction of segment control parameters - polynomial (regular/Bernstein) segment control, exponential/hyperbolic tension segment control, Kaklis-Pandelis tension segment control, and C1 Hermite.
 	* Control the segment using the rational shape controller, and the appropriate Ck.
 	* Estimate the node value and the node value Jacobian with the segment, as well as at the boundaries.
 	* Calculate the segment monotonicity.

 * [***BasisMonicBSpline***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/spline/BasisMonicBSpline.java)
 <i>BasisMonicBSpline</i> implements Samples for the Construction and the usage of various monic basis B Splines. It demonstrates the following:
 	* Construction of segment B Spline Hat Basis Functions.
 	* Estimation of the derivatives and the basis envelope cumulative integrands.
 	* Estimation of the normalizer and the basis envelope cumulative normalized integrands.

 * [***BasisMonicHatComparison***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/spline/BasisMonicHatComparison.java)
 <i>BasisMonicHatComparison</i> implements the comparison of the basis hat functions used in the construction of the monic basis B Splines. It demonstrates the following:
 	* Construction of the Linear Cubic Rational Raw Hat Functions
 	* Construction of the Quadratic Cubic Rational Raw Hat Functions
 	* Construction of the Corresponding Processed Tension Basis Hat Functions
 	* Construction of the Wrapping Monic Functions
 	* Estimation and Comparison of the Ordered Derivatives

 * [***BasisMulticBSpline***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/spline/BasisMulticBSpline.java)
 <i>BasisMulticBSpline</i> implements Samples for the Construction and the usage of various multic basis B Splines. It demonstrates the following:
 	* Construction of segment higher order B Spline from the corresponding Hat Basis Functions.
 	* Estimation of the derivatives and the basis envelope cumulative integrands.
 	* Estimation of the normalizer and the basis envelope cumulative normalized integrands.

 * [***BasisSplineSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/spline/BasisSplineSet.java)
 <i>BasisSplineSet</i> implements Samples for the Construction and the usage of various basis spline functions. It demonstrates the following:
 	* Construction of segment control parameters - polynomial (regular/Bernstein) segment control, exponential/hyperbolic tension segment control, Kaklis-Pandelis tension segment control, and C1 Hermite.
 	* Control the segment using the rational shape controller, and the appropriate Ck.
 	* Estimate the node value and the node value Jacobian with the segment, as well as at the boundaries.
 	* Calculate the segment monotonicity.

 * [***BasisTensionSplineSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/spline/BasisTensionSplineSet.java)
 <i>BasisTensionSplineSet</i> implements Samples for the Construction and the usage of various basis spline functions. It demonstrates the following:
 	* Construction of Kocke-Lyche-Kvasov tension spline segment control parameters - using hyperbolic, exponential, rational linear, and rational quadratic primitives.
 	* Control the segment using the rational shape controller, and the appropriate Ck.
 	* Estimate the node value and the node value Jacobian with the segment, as well as at the boundaries.
 	* Calculate the segment monotonicity.

 * [***BSplineSequence***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/spline/BSplineSequence.java)
 <i>BSplineSequence</i> implements Samples for the Construction and the usage of various monic basis B Spline Sequences. It demonstrates the following:
 	* Construction and Usage of segment Monic B Spline Sequence.
 	* Construction and Usage of segment Multic B Spline Sequence.

 * [***PolynomialBasisSpline***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/spline/PolynomialBasisSpline.java)
 <i>PolynomialBasisSpline</i> implements Samples for the Construction and the usage of polynomial (both regular and Hermite) basis spline functions. It demonstrates the following:
 	* Control the polynomial segment using the rational shape controller, the appropriate Ck, and the basis function.
 	* Demonstrate the variational shape optimization behavior.
 	* Estimate the node value and the node value Jacobian with the segment, as well as at the boundaries.
 	* Calculate the segment monotonicity and the curvature penalty.


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
