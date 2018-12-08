# DROP Spline Basis Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Spline Basis Package contains the Basis Spline Construction/Customization Parameters.


## Class Components

 * [***BSplineSequenceParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/basis/BSplineSequenceParams.java)
 <i>BSplineSequenceParams</i> implements the parameter set for constructing the B Spline Sequence. It
 provides functionality to:
 	* Retrieve the B Spline Order
 	* Retrieve the Number of Basis Functions
 	* Retrieve the Processed Basis Derivative Order
 	* Retrieve the Basis Hat Type
 	* Retrieve the Shape Control Type
 	* Retrieve the Tension
 	* Retrieve the Array of Predictor Ordinates

 * [***ExponentialMixtureSetParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/basis/ExponentialMixtureSetParams.java)
 <i>ExponentialMixtureSetParams</i> implements per-segment parameters for the exponential mixture basis set,
 i.e., the array of the exponential tension parameters, one per each entity in the mixture.

 * [***ExponentialRationalSetParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/basis/ExponentialRationalSetParams.java)
 <i>ExponentialRationalSetParams</i> implements per-segment parameters for the exponential rational basis
 set, i.e., the exponential tension and the rational tension parameters.

 * [***FunctionSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/basis/FunctionSet.java)
 <i>FunctionSet</i> implements the basis spline function set.

 * [***FunctionSetBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/basis/FunctionSetBuilder.java)
 <i>FunctionSetBuilder</i> implements the basis set and spline builder for the following types of splines:
 	* Exponential basis tension splines
 	* Hyperbolic basis tension splines
 	* Polynomial basis splines
 	* Bernstein Polynomial basis splines
 	* Kaklis Pandelis basis tension splines

 This elastic coefficients for the segment using Ck basis splines inside [0,...,1) - Globally [x_0,...,x_1)
 	are extracted for:

 			y = Estimator (C_k, x) * ShapeControl (x)

		where x is the normalized ordinate mapped as

 			x becomes (x - x_i-1) / (x_i - x_i-1)

 The inverse quadratic/rational spline is a typical shape controller spline used.

 * [***FunctionSetBuilderParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/basis/FunctionSetBuilderParams.java)
 <i>FunctionSetBuilderParams</i> is an empty stub class whose derived implementations hold the per segment
 basis set parameters.

 * [***KaklisPandelisSetParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/basis/KaklisPandelisSetParams.java)
 <i>KaklisPandelisSetParams</i> implements per segment parameters for the Kaklis Pandelis basis set.
 Currently it only holds the polynomial tension degree.

 * [***PolynomialFunctionSetParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/basis/PolynomialFunctionSetParams.java)
 <i>PolynomialFunctionSetParams</i> implements per-segment basis set parameters for the polynomial basis
 spline. Currently it holds the number of basis functions.


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
