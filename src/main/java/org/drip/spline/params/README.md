# DROP Spline Params Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Spline Params Package holds Spline Segment Construction Control Parameters.


## Class Components

 * [***PreceedingManifestSensitivityControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/PreceedingManifestSensitivityControl.java)
 <i>PreceedingManifestSensitivityControl</i> provides the control parameters that determine the behavior of
 non local manifest sensitivity.

 * [***ResponseScalingShapeControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/ResponseScalingShapeControl.java)
 <i>ResponseScalingShapeControl</i> implements the segment level basis functions proportional adjustment to
 achieve the desired shape behavior of the response. In addition to the actual shape controller function, it
 interprets whether the control is applied on a local or global predicate ordinate basis.

 * [***SegmentResponseValueConstraint***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/SegmentResponseValueConstraint.java)
 <i>SegmentResponseValueConstraint</i> holds the SegmentBasisFlexureConstraint instances for the Base
 Calibration and one for each Manifest Measure Sensitivity.

 * [***SegmentBasisFlexureConstraint***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/SegmentBasisFlexureConstraint.java)
 <i>SegmentBasisFlexureConstraint</i> holds the set of fields needed to characterize a single local linear
 Constraint, expressed linearly as a combination of the local Predictor Ordinates and their corresponding
 Response Basis Function Realizations. Constraints are expressed as

 			C := Sigma_(i,j) [W_i * B_i(x_j)] = V where

 	x_j - The Predictor Ordinate at Node j
 	B_i - The Coefficient for the Response Basis Function i
 	W_i - Weight applied for the Response Basis Function i
 	V - Value of the Constraint

 SegmentBasisFlexureConstraint can be viewed as the localized basis function transpose of
 SegmentResponseValueConstraint.

 * [***SegmentBestFitResponse***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/SegmentBestFitResponse.java)
 <i>SegmentBestFitResponse </i>implements basis per-segment Fitness Penalty Parameter Set. Currently it
 contains the Best Fit Penalty Weight Grid Matrix and the corresponding Segment Local Predictor
 Ordinate/Response Match Pair.

 * [***SegmentCustomBuilderControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/SegmentCustomBuilderControl.java)
 <i>SegmentCustomBuilderControl</i> holds the parameters the guide the creation/behavior of the segment. It
 holds the segment elastic/inelastic parameters and the named basis function set.

 * [***SegmentFlexurePenaltyControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/SegmentFlexurePenaltyControl.java)
 <i>SegmentFlexurePenaltyControl</i> implements basis per-segment Flexure Penalty Parameter Set. Currently it
 contains the Flexure Penalty Derivative Order and the Roughness Coefficient Amplitude. Flexure Penalty
 Control may be used to implement Segment Curvature Control and/or Segment Length Control.

 * [***SegmentInelasticDesignControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/SegmentInelasticDesignControl.java)
 <i>SegmentInelasticDesignControl</i> implements basis per-segment inelastic parameter set. It exports the
 following functionality:
 	* Retrieve the Continuity Order.
 	* Retrieve the Length Penalty and the Curvature Penalty Parameters.
  	* Create the C2 Inelastic Design Parameters.
 	* Create the Inelastic Design Parameters for the desired Ck Criterion and the Roughness Penalty

 * [***SegmentPredictorResponseDerivative***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/SegmentPredictorResponseDerivative.java)
 <i>SegmentPredictorResponseDerivative</i> contains the segment local parameters used for the segment
 calibration. It holds the edge Y value and the derivatives. It exposes the following functions:
 	* Retrieve the Response Value as well as the DResponseDPredictorOrdinate Array.
 	* Aggregate the 2 Predictor Ordinate Response Derivatives by applying the Cardinal Tension Weight.

 * [***SegmentResponseConstraintSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/SegmentResponseConstraintSet.java)
 <i>SegmentResponseConstraintSet</i> holds the set of SegmentResponseValueConstraint (Base + One/more
 Sensitivities) for the given Segment. It exposes functions to add/retrieve the base RVC as well as
 additional RVC sensitivities.

 * [***SegmentResponseConstraintSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/SegmentResponseConstraintSet.java)
 <i>SegmentResponseValueConstraint</i> holds the following set of fields that characterize a single global
 linear constraint between the predictor and the response variables within a single segment, expressed
 linearly across the constituent nodes. Constraints are expressed as

 			C = Sigma_j [W_j * y_j] = V where

 	x_j - Predictor j
 	y_j - Response j
 	W_j - Weight at ordinate j
 	V - Value of the Constraint

 SegmentResponseValueConstraint exports the following functionality:
 	* Retrieve the Array of Predictor Ordinates.
 	* Retrieve the Array of Response Weights at each Predictor Ordinate.
 	* Retrieve the Constraint Value.
 	* Convert the Segment Constraint onto Local Predictor Ordinates, the corresponding Response Basis
 		Function, and the Shape Controller Realizations.
 	* Get the Position of the Predictor Knot relative to the Constraints.
 	* Generate a SegmentResponseValueConstraint instance from the given predictor/response pair.

 SegmentResponseValueConstraint can be viewed as the global response point value transform of
 SegmentBasisFlexureConstraint.

 * [***SegmentStateCalibrationInputs***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/SegmentStateCalibrationInputs.java)
 <i>SegmentStateCalibrationInputs</i> implements basis per-segment Calibration Parameter Input Set. It
 exposes the following functionality:
 	* Retrieve the Array of the Calibration Predictor Ordinates.
 	* Retrieve the Array of the Calibration Response Values.
 	* Retrieve the Array of the Left/Right Edge Derivatives.
 	* Retrieve the Segment Best Fit Response.
 	* Retrieve the Array of Segment Basis Flexure Constraints.

 * [***StretchBestFitResponse***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/StretchBestFitResponse.java)
 <i>StretchBestFitResponse</i> implements basis per-Stretch Fitness Penalty Parameter Set. Currently it
 contains the Best Fit Penalty Weight Grid Matrix and the corresponding Local Predictor Ordinate/Response
 Match Pair. StretchBestFitResponse exports the following methods:
 	* Retrieve the Array of the Fitness Weights.
 	* Retrieve the Indexed Fitness Weight Element.
 	* Retrieve the Array of Predictor Ordinates.
 	* Retrieve the Indexed Predictor Ordinate Element.
 	* Retrieve the Array of Responses.
 	* Retrieve the Indexed Response Element.
 	* Retrieve the Number of Fitness Points.
 	* Generate the Segment Local Best Fit Weighted Response contained within the specified Segment.
 	* Construct the StretchBestFitResponse Instance from the given Inputs.
 	* Construct the StretchBestFitResponse Instance from the given Predictor Ordinate/Response Pairs, using
 		Uniform Weightings.


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
