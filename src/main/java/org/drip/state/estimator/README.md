# DROP State Estimator Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP State Estimator Package implements the Multi-Pass Customized Stretch Curve.


## Class Components

 * [***CurveStretch***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/estimator/CurveStretch.java)
 <i>CurveStretch</i> expands the regular Multi-Segment Stretch to aid the calibration of Boot-strapped
 Instruments. In particular, CurveStretch implements the following functions that are used at different
 stages of curve construction sequence:
 	* Mark the Range of the "built" Segments
 	* Clear the built range mark to signal the start of a fresh calibration run
 	* Indicate if the specified Predictor Ordinate is inside the "Built" Range
 	* Retrieve the MergeSubStretchManager

 * [***GlobalControlCurveParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/estimator/GlobalControlCurveParams.java)
 <i>GlobalControlCurveParams</i> enhances the SmoothingCurveStretchParams to produce globally customized
 curve smoothing. Currently, GlobalControlCurveParams uses custom boundary setting and spline details to
 implement the global smoothing pass.

 * [***LatentStateStretchBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/estimator/LatentStateStretchBuilder.java)
 <i>LatentStateStretchBuilder</i> contains the Functionality to construct the Curve Latent State Stretch for
 the different Latent States.

 * [***LocalControlCurveParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/estimator/LocalControlCurveParams.java)
 <i>LocalControlCurveParams</i> enhances the SmoothingCurveStretchParams to produce locally customized curve
 smoothing. Flags implemented by LocalControlCurveParams control the following:
 	* The C1 generator scheme to be used
 	* Whether to eliminate spurious extrema
 	* Whether or not to apply monotone filtering

 * [***PredictorResponseRelationSetup***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/estimator/PredictorResponseRelationSetup.java)
 <i>PredictorResponseRelationSetup</i> holds the Linearized Constraints (and, optionally, their quote
 sensitivities) necessary needed for the Linear Calibration. Linearized Constraints are expressed as

 			Sum_i[Predictor Weight_i * Function (Response_i)] = Constraint Value

 where Function can either be univariate function, or weighted spline basis set. To this end, it implements
 the following functionality:
 	* Update/Retrieve Predictor/Response Weights and their Quote Sensitivities
 	* Update/Retrieve Predictor/Response Constraint Values and their Quote Sensitivities
 	* Display the contents of PredictorResponseRelationSetup

 * [***PredictorResponseWeightConstraint***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/estimator/PredictorResponseWeightConstraint.java)
 <i>PredictorResponseWeightConstraint</i> holds the Linearized Constraints (and, optionally, their quote
 sensitivities) necessary needed for the Linear Calibration. Linearized Constraints are expressed as

 			Sum_i[Predictor Weight_i * Function (Response_i)] = Constraint Value

 where Function can either be univariate function, or weighted spline basis set. To this end, it implements
 the following functionality:
 	* Update/Retrieve Predictor/Response Weights and their Quote Sensitivities
 	* Update/Retrieve Predictor/Response Constraint Values and their Quote Sensitivities
 	* Display the contents of PredictorResponseWeightConstraint

 * [***SmoothingCurveStretchParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/estimator/SmoothingCurveStretchParams.java)
 <i>SmoothingCurveStretchParams</i> contains the Parameters needed to hold the Stretch. It provides
 functionality to:
 	* The Stretch Best fit Response and the corresponding Quote Sensitivity
 	* The Calibration Detail and the Curve Smoothening Quantification Metric
 	* The Segment Builder Parameters


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
