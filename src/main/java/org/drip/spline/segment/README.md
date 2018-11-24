# DROP Spline Segment Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Spline Segment Package holds the Flexure Penalizing Best Fit Segment.


## Class Components

 * [***BasisEvaluator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/segment/BasisEvaluator.java)
 <i>BasisEvaluator</i> implements the Segment's Basis Evaluator Functions. It exports the following
 	functions:
 	* Retrieve the number of Segment's Basis Functions
 	* Set the Inelastics that provides the enveloping Context the Basis Evaluation
 	* Clone/Replicate the current Basis Evaluator Instance
 	* Compute the Response Value of the indexed Basis Function at the specified Predictor Ordinate
 	* Compute the Basis Function Value at the specified Predictor Ordinate
 	* Compute the Response Value at the specified Predictor Ordinate
 	* Compute the Ordered Derivative of the Response Value off of the indexed Basis Function at the specified
 		Predictor Ordinate
 	* Compute the Ordered Derivative of the Response Value off of the Basis Function Set at the specified
 		Predictor Ordinate
 	* Compute the Response Value Derivative at the specified Predictor Ordinate

 * [***BestFitFlexurePenalizer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/segment/BestFitFlexurePenalizer.java)
 <i>BestFitFlexurePenalizer</i> implements the Segment's Best Fit, Curvature, and Length Penalizers. It
 provides the following functionality:
 	* Compute the Cross-Curvature Penalty for the given Basis Pair
 	* Compute the Cross-Length Penalty for the given Basis Pair
 	* Compute the Best Fit Cross-Product Penalty for the given Basis Pair
 	* Compute the Basis Pair Penalty Coefficient for the Best Fit and the Curvature Penalties
 	* Compute the Penalty Constraint for the Basis Pair

 * [***LatentStateInelastic***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/segment/LatentStateInelastic.java)
 <i>LatentStateInelastic</i> contains the spline segment in-elastic fields - in this case the start/end
 ranges. It exports the following functions:
 	* Retrieve the Segment Left/Right Predictor Ordinate
 	* Find out if the Predictor Ordinate is inside the segment - inclusive of left/right
 	* Get the Width of the Predictor Ordinate in this Segment
 	* Transform the Predictor Ordinate to the Local Segment Predictor Ordinate
 	* Transform the Local Predictor Ordinate to the Segment Ordinate

 * [***LatentStateManifestSensitivity***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/segment/LatentStateManifestSensitivity.java)
 <i>LatentStateManifestSensitivity</i> contains the Manifest Sensitivity generation control parameters and
 the Manifest Sensitivity outputs related to the given Segment.

 * [***LatentStateResponseModel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/segment/LatentStateResponseModel.java)
 <i>LatentStateResponseModel</i> implements the single segment basis calibration and inference functionality.
 It exports the following functionality:
 	* Build the LatentStateResponseModel instance from the Basis Function/Shape Controller Set
 	* Build the LatentStateResponseModel instance from the Basis Evaluator Set
 	* Retrieve the Number of Parameters, Basis Evaluator, Array of the Response Basis Coefficients, and
 		Segment Design Inelastic Control
 	* Calibrate the Segment State from the Calibration Parameter Set
 	* Sensitivity Calibrator: Calibrate the Segment Manifest Jacobian from the Calibration Parameter Set
 	* Calibrate the coefficients from the prior Predictor/Response Segment, the Constraint, and fitness
 		Weights
 	* Calibrate the coefficients from the prior Segment and the Response Value at the Right Predictor
 		Ordinate
 	* Calibrate the Coefficients from the Edge Response Values and the Left Edge Response Slope
 	* Calibrate the coefficients from the Left Edge Response Value Constraint, the Left Edge Response Value
 		Slope, and the Right Edge Response Value Constraint
 	* Retrieve the Segment Curvature, Length, and the Best Fit DPE
 	* Calculate the Response Value and its Derivative at the given Predictor Ordinate
 	* Calculate the Ordered Derivative of the Coefficient to the Manifest
 	* Calculate the Jacobian of the Segment's Response Basis Function Coefficients to the Edge Inputs
 	* Calculate the Jacobian of the Response to the Edge Inputs at the given Predictor Ordinate
 	* Calculate the Jacobian of the Response to the Basis Coefficients at the given Predictor Ordinate
 	* Calibrate the segment and calculate the Jacobian of the Segment's Response Basis Function Coefficients
 		to the Edge Parameters
 	* Calibrate the Coefficients from the Edge Response Values and the Left Edge Response Value Slope and
 		calculate the Jacobian of the Segment's Response Basis Function Coefficients to the Edge Parameters
 	* Calibrate the coefficients from the prior Segment and the Response Value at the Right Predictor
 		Ordinate and calculate the Jacobian of the Segment's Response Basis Function Coefficients to the Edge
 		Parameters
 	* Indicate whether the given segment is monotone. If monotone, may optionally indicate the nature of the
 		extrema contained inside (maxima/minima/infection)
 	* Clip the part of the Segment to the Right of the specified Predictor Ordinate. Retain all other
 		constraints the same
 	* Clip the part of the Segment to the Left of the specified Predictor Ordinate. Retain all other
 		constraints the same
 	* Display the string representation for diagnostic purposes

 * [***Monotonicity***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/segment/Monotonicity.java)
 <i>Monotonicity</i> contains the monotonicity details related to the given segment. Indicates whether the
 	segment is monotonic, and if not, whether it contains a maximum, a minimum, or an inflection.

 * [***SegmentBasisEvaluator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/segment/SegmentBasisEvaluator.java)
 <i>SegmentBasisEvaluator</i> implements the BasisEvaluator interface for the given set of the Segment Basis
 	Evaluator Functions.


## References

 * Akima (1970): A New Method of Interpolation and Smooth Curve Fitting based on Local Procedures <i>Journal
 of the Association for the Computing Machinery</i> <b>17 (4)</b> 589-602


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
