# DROP Spline Stretch Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Spline Stretch Package contains the Multi-Segment Sequence Spline Stretch.


## Class Components

 * [***BoundarySettings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/BoundarySettings.java)
 <i>BoundarySettings</i> implements the Boundary Settings that determine the full extent of description of
 the regime's State. It exports functions that:
 	* Specify the type of the boundary condition (NATURAL/FLOATING/IS-A-KNOT)
 	* Boundary Condition specific additional parameters (e.g., Derivative Orders and Matches)
 	* Static methods that help construct standard boundary settings

 * [***CalibratableMultiSegmentSequence***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/CalibratableMultiSegmentSequence.java)
 <i>CalibratableMultiSegmentSequence</i> implements the MultiSegmentSequence span that spans multiple
 segments. It holds the ordered segment sequence, segment sequence builder, the segment control parameters,
 and, if available, the spanning Jacobian. It provides a variety of customization for the segment
 construction and state representation control.

 * [***CkSegmentSequenceBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/CkSegmentSequenceBuilder.java)
 <i>CkSegmentSequenceBuilder</i> implements the SegmentSequenceBuilder interface to customize segment
 sequence construction. Customization is applied at several levels:
 	* Segment Calibration Boundary Setting/Segment Best Fit Response Settings
 	* Segment Response Value Constraints for the starting and the subsequent Segments

 * [***MultiSegmentSequence***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/MultiSegmentSequence.java)
 <i>MultiSegmentSequence</i> is the interface that exposes functionality that spans multiple segments. Its
 derived instances hold the ordered segment sequence, the segment control parameters, and, if available, the
 spanning Jacobian. MultiSegmentSequence exports the following group of functionality:
 	* Retrieve the Segments and their Builder Parameters
 	* Compute the monotonicity details - segment/Stretch level monotonicity, co-monotonicity, local
 		monotonicity
 	* Check if the Predictor Ordinate is in the Stretch Range, and return the segment index in that case
 	* Set up (i.e., calibrate) the individual Segments in the Stretch by specifying one/or more of the node
 		parameters and Target Constraints
 	* Set up (i.e., calibrate) the individual Segment in the Stretch to the Target Segment Edge Values and
 		Constraints. This is also called the Hermite setup - where the segment boundaries are entirely
 		locally set
 	* Generate a new Stretch by clipping all the Segments to the Left/Right of the specified Predictor
 		Ordinate. Smoothness Constraints will be maintained
 	* Retrieve the Span Curvature/Length, and the Best Fit DPE's
 	* Retrieve the Merge Stretch Manager
 	* Display the Segments

 * [***MultiSegmentSequenceBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/MultiSegmentSequenceBuilder.java)
 <i>MultiSegmentSequenceBuilder</i> exports Stretch creation/calibration methods to generate customized basis
 splines, with customized segment behavior using the segment control. It exports the following method of
 Stretch Creation:
 	* Create an uncalibrated Stretch instance over the specified Predictor Ordinate Array using the specified
 		Basis Spline Parameters for the Segment
 	* Create a calibrated Stretch Instance over the specified array of Predictor Ordinates and Response
 		Values using the specified Basis Splines
 	* Create a calibrated Stretch Instance over the specified Predictor Ordinates, Response Values, and their
 		Constraints, using the specified Segment Builder Parameters
 	* Create a Calibrated Stretch Instance from the Array of Predictor Ordinates and a flat Response Value
 	* Create a Regression Spline Instance over the specified array of Predictor Ordinate Knot Points and the
 		Set of the Points to be Best Fit

 * [***MultiSegmentSequenceModifier***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/MultiSegmentSequenceModifier.java)
 <i>MultiSegmentSequenceModifier</i> exports Stretch modification/alteration methods to generate customized
 basis splines, with customized segment behavior using the segment control. It exposes the following stretch
 modification methods:
 	* Insert the specified Predictor Ordinate Knot into the specified Stretch, using the specified Response
 		Value
 	* Append a Segment to the Right of the Specified Stretch using the Supplied Constraint
 	* Insert the Predictor Ordinate Knot into the specified Stretch
 	* Insert a Cardinal Knot into the specified Stretch at the specified Predictor Ordinate Location
 	* Insert a Catmull-Rom Knot into the specified Stretch at the specified Predictor Ordinate Location

 * [***SegmentSequenceBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/SegmentSequenceBuilder.java)
 <i>SegmentSequenceBuilder</i> is the interface that contains the stubs required for the construction of the
 segment stretch. It exposes the following functions:
 	* Set the Stretch whose Segments are to be calibrated
 	* Retrieve the Calibration Boundary Condition
 	* Calibrate the Starting Segment using the LeftSlope
 	* Calibrate the Segment Sequence in the Stretch

 * [***SingleSegmentLagrangePolynomial***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/SingleSegmentLagrangePolynomial.java)
 <i>SingleSegmentLagrangePolynomial</i> implements the SingleSegmentSequence Stretch interface using the
 Lagrange Polynomial Estimator. As such it provides a perfect fit that travels through all the
 predictor/response pairs causing Runge's instability.

 * [***SingleSegmentSequence***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/SingleSegmentSequence.java)
 <i>SingleSegmentSequence</i> is the interface that exposes functionality that spans multiple segments. Its
 derived instances hold the ordered segment sequence, the segment control parameters, and, if available, the
 spanning Jacobian. SingleSegmentSequence exports the following group of functionality:
 	* Construct adjoining segment sequences in accordance with the segment control parameters
 	* Calibrate according to a varied set of (i.e., NATURAL/FINANCIAL) boundary conditions
 	* Estimate both the value, the ordered derivatives, and the Jacobian (quote/coefficient) at the given
 		ordinate
 	* Compute the monotonicity details - segment/Stretch level monotonicity, co-monotonicity, local
 		monotonicity
 	* Predictor Ordinate Details - identify the left/right predictor ordinate edges, and whether the given
 		predictor ordinate is a knot


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
