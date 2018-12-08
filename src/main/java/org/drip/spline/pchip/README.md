# DROP Spline PCHIP Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Spline PCHIP Package implements Monotone Convex Themed PCHIP Splines.


## Class Components

 * [***AkimaLocalC1Generator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/pchip/AkimaLocalC1Generator.java)
 <i>AkimaLocalC1Generator</i> generates the local control C1 Slope using the Akima Cubic Algorithm.

 * [***LocalControlStretchBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/pchip/LocalControlStretchBuilder.java)
 <i>LocalControlStretchBuilder</i> exports Stretch creation/calibration methods to generate customized basis
 splines, with customized segment behavior using the segment control. It provides the following local control
 functionality:
 	* Create a Stretch off of Hermite Splines from the specified the Predictor Ordinates, the Response
 		Values, the Custom Slopes, and the Segment Builder Parameters
 	* Create Hermite/Bessel C1 Cubic Spline Stretch
 	* Create Hyman (1983) Monotone Preserving Stretch
 	* Create Hyman (1989) enhancement to the Hyman (1983) Monotone Preserving Stretch
 	* Create the Harmonic Monotone Preserving Stretch
 	* Create the Van Leer Limiter Stretch
 	* Create the Huynh Le Floch Limiter Stretch
 	* Generate the local control C1 Slope using the Akima Cubic Algorithm
 	* Generate the local control C1 Slope using the Hagan-West Monotone Convex Algorithm

 * [***LocalMonotoneCkGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/pchip/LocalMonotoneCkGenerator.java)
 <i>LocalMonotoneCkGenerator</i> generates customized Local Stretch by trading off Ck for local control. It
 implements the following variants: Akima, Bessel, Harmonic, Hyman83, Hyman89, Kruger, Monotone Convex, as
 well as the Van Leer and the Huynh/LeFloch limiters. It also provides the following custom control on the
 resulting C1:
 	* Eliminate the Spurious Extrema in the Input C1 Entry
 	* Apply the Monotone Filter in the Input C1 Entry
 	* Generate a Vanilla C1 Array from the specified Array of Predictor Ordinates and the Response Values
 	* Verify if the given Quintic Polynomial is Monotone using the Hyman89 Algorithm, and generate it if
 		necessary

 * [***MinimalQuadraticHaganWest***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/pchip/MinimalQuadraticHaganWest.java)
 <i>MinimalQuadraticHaganWest</i> implements the regime using the Hagan and West (2006) Minimal Quadratic
 Estimator.

 * [***MinimalQuadraticHaganWest***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/pchip/MinimalQuadraticHaganWest.java)
 <i>MonotoneConvexHaganWest</i> implements the regime using the Hagan and West (2006) Estimator. It provides
 the following functionality:
 	* Static Method to Create an instance of MonotoneConvexHaganWest
 	* Ensure that the estimated regime is monotone an convex
 	* If need be, enforce positivity and/or apply amelioration
 	* Apply segment-by-segment range bounds as needed
 	* Retrieve predictor ordinates/response values


## References

 * Akima (1970): A New Method of Interpolation and Smooth Curve Fitting based on Local Procedures <i>Journal
 of the Association for the Computing Machinery</i> <b>17 (4)</b> 589-602


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
