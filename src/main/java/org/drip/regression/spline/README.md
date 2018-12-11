# DROP Regression Spline Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Regression Spline implements the Custom Basis Spline Regression Engine.


## Class Components

 * [***BasisSplineRegressionEngine***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/spline/BasisSplineRegressionEngine.java)
 <i>BasisSplineRegressionEngine</i> implements the RegressionEngine class for the basis spline functionality.

 * [***BasisSplineRegressor***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/spline/BasisSplineRegressor.java)
 <i>BasisSplineRegressor</i> implements the custom basis spline regressor for the given basis spline. As part
 of the regression run, it executes the following:
 	* Calibrate and compute the left and the right Jacobian.
 	* Reset right node and re-run calibration.
 	* Compute an intermediate value Jacobian.

 * [***BasisSplineRegressorSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/spline/BasisSplineRegressorSet.java)
 <i>BasisSplineRegressorSet</i> carries out regression testing for the following series of basis splines:
 	* #1: Polynomial Basis Spline, n = 2 basis functions, and Ck = 0.
 	* #2: Polynomial Basis Spline, n = 3 basis functions, and Ck = 1.
 	* #3: Polynomial Basis Spline, n = 4 basis functions, and Ck = 1.
 	* #4: Polynomial Basis Spline, n = 4 basis functions, and Ck = 2.
 	* #5: Polynomial Basis Spline, n = 5 basis functions, and Ck = 1.
 	* #6: Polynomial Basis Spline, n = 5 basis functions, and Ck = 2.
 	* #7: Polynomial Basis Spline, n = 5 basis functions, and Ck = 3.
 	* #8: Polynomial Basis Spline, n = 6 basis functions, and Ck = 1.
 	* #9: Polynomial Basis Spline, n = 6 basis functions, and Ck = 2.
 	* #10: Polynomial Basis Spline, n = 6 basis functions, and Ck = 3.
 	* #11: Polynomial Basis Spline, n = 6 basis functions, and Ck = 4.
 	* #12: Polynomial Basis Spline, n = 7 basis functions, and Ck = 1.
 	* #13: Polynomial Basis Spline, n = 7 basis functions, and Ck = 2.
 	* #14: Polynomial Basis Spline, n = 7 basis functions, and Ck = 3.
 	* #15: Polynomial Basis Spline, n = 7 basis functions, and Ck = 4.
 	* #16: Polynomial Basis Spline, n = 7 basis functions, and Ck = 5.
 	* #17: Bernstein Polynomial Basis Spline, n = 4 basis functions, and Ck = 2.
 	* #18: Exponential Tension Spline, n = 4 basis functions, Tension = 1., and Ck = 2.
 	* #19: Hyperbolic Tension Spline, n = 4 basis functions, Tension = 1., and Ck = 2.
 	* #20: Kaklis-Pandelis Tension Spline, n = 4 basis functions, KP = 2, and Ck = 2.
 	* #21: C1 Hermite Local Spline, n = 4 basis functions, and Ck = 1.
  	* #22: Hermite Local Spline with Local, Catmull-Rom, and Cardinal Knots, n = 4 basis functions, and
  		Ck = 1.

 * [***HermiteBasisSplineRegressor***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/spline/HermiteBasisSplineRegressor.java)
 <i>HermiteBasisSplineRegressor</i> implements the Hermite basis spline regressor for the given basis spline.
 As part of the regression run, it executes the following:
 	* Calibrate and compute the left and the right Jacobian.
 	* Reset right node and re-run calibration.
 	* Compute an intermediate value Jacobian.

 * [***LagrangePolynomialStretchRegressor***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/spline/LagrangePolynomialStretchRegressor.java)
 <i>LagrangePolynomialStretchRegressor</i> implements the local control basis spline regressor for the given
 basis spline. As part of the regression run, it executes the following:
 	* Calibrate and compute the left and the right Jacobian.
 	* Insert the Local Control Hermite, Cardinal, and Catmull-Rom knots.
 	* Compute an intermediate value Jacobian.

 * [***LocalControlBasisSplineRegressor***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/spline/LocalControlBasisSplineRegressor.java)
 <i>LocalControlBasisSplineRegressor</i> implements the local control basis spline regressor for the given
 basis spline. As part of the regression run, it executes the following:
 	* Calibrate and compute the left and the right Jacobian.
 	* Insert the Local Control Hermite, Cardinal, and Catmull-Rom knots.
 	* Run Regressor for the C1 Local Control C1 Slope Insertion Bessel/Hermite Spline.
 	* Compute an intermediate value Jacobian.


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
