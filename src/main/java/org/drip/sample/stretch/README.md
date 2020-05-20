# DROP Stretch Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Stretch demonstrates Knot Insertion Curvature Roughness Penalty.


## Class Components

 * [***ATMTTESurface2D***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/stretch/ATMTTESurface2D.java)
 <i>ATMTTESurface2D</i> demonstrates the Surface 2D ATM/TTE (X/Y) Stretch Construction and usage API.

 * [***CurvatureLengthRoughnessFit***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/stretch/CurvatureLengthRoughnessFit.java)
 <i>CurvatureLengthRoughnessPenalty</i> demonstrates the setting up and the usage of the curvature, the length, and the closeness of fit penalizing spline. This sample shows the following:
 	* Set up the X Predictor Ordinate and the Y Response Value Set.
 	* Construct a set of Predictor Ordinates, their Responses, and corresponding Weights to serve as weighted closeness of fit.
 	* Construct a rational shape controller with the desired shape controller tension parameters and Global Scaling.
 	* Construct the Segment Inelastic Parameter that is C2 (iK = 2 sets it to C2), with First Order Segment Length Penalty Derivative, Second Order Segment Curvature Penalty Derivative, their Amplitudes, and without Constraint.
 	* Construct the base, the base + 1 degree segment builder control.
 	* Construct the base, the elevated, and the best fit basis spline stretches.
 	* Compute the segment-by-segment monotonicity for all the three stretches.
 	* Compute the Stretch Jacobian for all the three stretches.
 	* Compute the Base Stretch Curvature, Length, and the Best Fit DPE.
 	* Compute the Elevated Stretch Curvature, Length, and the Best Fit DPE.
 	* Compute the Best Fit Stretch Curvature, Length, and the Best Fit DPE.

 * [***CurvatureRoughnessPenaltyFit***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/stretch/CurvatureRoughnessPenaltyFit.java)
 <i>CurvatureRoughnessPenaltyFit</i> demonstrates the setting up and the usage of the curvature and closeness of fit penalizing spline. It illustrates in detail the following steps:
 	* Set up the X Predictor Ordinate and the Y Response Value Set.
 	* Construct a set of Predictor Ordinates, their Responses, and corresponding Weights to serve as weighted closeness of fit.
 	* Construct a rational shape controller with the desired shape controller tension parameters and Global Scaling.
 	* Construct the segment inelastic parameter that is C2 (iK = 2 sets it to C2), with 2nd order roughness penalty derivative, and without constraint.
 	* Construct the base, the base + 1 degree segment builder control.
 	* Construct the base, the elevated, and the best fit basis spline stretches.
 	* Compute the segment-by-segment monotonicity for all the three stretches.
 	* Compute the Stretch Jacobian for all the three stretches.
 	* Compute the Base Stretch Curvature Penalty Estimate.
 	* Compute the Elevated Stretch Curvature Penalty Estimate.
 	* Compute the Best Fit Stretch Curvature Penalty Estimate.

 * [***CustomDiscountCurveBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/stretch/CustomDiscountCurveBuilder.java)
 <i>CustomDiscountCurveBuilder</i> contains samples that demo how to build a discount curve from purely the cash flows. It provides for elaborate curve builder control, both at the segment level and at the Stretch level. In particular, it shows the following:
 	* Construct a discount curve from the discount factors available purely from the cash and the euro-dollar instruments.
 	* Construct a discount curve from the cash flows available from the swap instruments.

 In addition, the sample demonstrates the following ways of controlling curve construction:
 	* Control over the type of segment basis spline
 	* Control over the polynomial basis spline order, Ck, and tension parameters
 	* Provision of custom shape controllers (in this case rational shape controller)
 	* Calculation of segment monotonicity and convexity

 * [***KnotInsertionPolynomialEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/stretch/KnotInsertionPolynomialEstimator.java)
 <i>KnotInsertionPolynomialEstimator</i> demonstrates the Stretch builder and usage API. It shows the following:
 	* Construction of segment control parameters - polynomial (regular/Bernstein) segment control, exponential/hyperbolic tension segment control, Kaklis-Pandelis tension segment control.
 	* Perform the following sequence of tests for a given segment control for a predictor/response range
 		* Assign the array of Segment Builder Parameters - one per segment
 		* Construct the Stretch Instance
 		* Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian
 		* Construct a new Stretch instance by inserting a pair of of predictor/response knots
 		* Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian
 	* Demonstrate the construction, the calibration, and the usage of Local Control Segment Spline.
 	* Demonstrate the construction, the calibration, and the usage of Lagrange Polynomial Stretch.
 	* Demonstrate the construction, the calibration, and the usage of C1 Stretch with the desired customization.

 * [***KnotInsertionSequenceAdjuster***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/stretch/KnotInsertionSequenceAdjuster.java)
 <i>KnotInsertionSequenceAdjuster</i> demonstrates the Stretch Manipulation and Adjustment API. It shows the following:
 	* Construct a simple Base Stretch.
 	* Clip a left Portion of the Stretch to construct a left-clipped Stretch.
 	* Clip a right Portion of the Stretch to construct a tight-clipped Stretch.
 	* Compare the values across all the stretches to establish that
 		* Continuity in the base smoothness is, preserved, and:
 		* Continuity across the predictor ordinate for the implied response value is also preserved.

 * [***KnotInsertionTensionEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/stretch/KnotInsertionTensionEstimator.java)
 <i>KnotInsertionTensionEstimator</i> demonstrates the Stretch builder and usage API. It shows the following:
 	* Construction of segment control parameters - polynomial (regular/Bernstein) segment control, exponential/hyperbolic tension segment control, Kaklis-Pandelis tension segment control.
 	* Tension Basis Spline Test using the specified predictor/response set and the array of segment custom builder control parameters.
 	* Complete the full tension stretch estimation sample test.

 * [***KnottedRegressionSplineEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/stretch/KnottedRegressionSplineEstimator.java)
 <i>KnottedRegressionSplineEstimator</i> shows the sample construction and usage of Knot-based Regression Splines. It demonstrates construction of the segment's predictor ordinate/response value combination, and eventual calibration.

 * [***MultiSpanAggregationEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/stretch/MultiSpanAggregationEstimator.java)
 <i>MultiSpanAggregationEstimator</i> demonstrates the Construction and Usage of the Multiple Span Aggregation Functionality.


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
