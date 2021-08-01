# DROP Sample Funding Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Sample Funding illustrates Shape Preserving Local Funding Curve Construction.


## Class Components

 * [***CustomFundingCurveBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/funding/CustomFundingCurveBuilder.java)
 <i>CustomFundingCurveBuilder</i> funding curve calibration and input instrument calibration quote recovery.
 It shows the following:
 	* Construct the Array of Deposit/Swap Instruments and their Quotes from the given set of parameters.
 	* Construct the Deposit/Swap Instrument Set Stretch Builder.
 		* Set up the Linear Curve Calibrator using the following parameters:
 		* Cubic Exponential Mixture Basis Spline Set
 		* C<sub>k</sub> = 2
			Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Construct the Shape Preserving Discount Curve by applying the linear curve calibrator to the array of
 	Cash and Swap Stretches.
 	* Cross-Comparison of the Cash/Swap Calibration Instrument "Rate" metric across the different curve
 	construction methodologies.

 * [***CustomFundingCurveReconciler***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/funding/CustomFundingCurveReconciler.java)
 <i>CustomFundingCurveReconciler</i> demonstrates the multi-stretch transition custom Funding curve
 construction, turns application, discount factor extraction, and calibration quote recovery. It shows the
 following steps:
 	* Setup the linear curve calibrator.
 	* Setup the cash instruments and their quotes for calibration.
 	* Setup the cash instruments stretch latent state representation - this uses the discount factor
 	quantification metric and the "rate" manifest measure.
 	* Setup the swap instruments and their quotes for calibration.
 	* Setup the swap instruments stretch latent state representation - this uses the discount factor
 	quantification metric and the "rate" manifest measure.
 	* Calibrate over the instrument set to generate a new overlapping latent state span instance.
 	* Retrieve the "cash" stretch from the span.
 	* Retrieve the "swap" stretch from the span.
 	* Create a discount curve instance by converting the overlapping stretch to an exclusive non-overlapping
 	stretch.
 	* Compare the discount factors and their monotonicity emitted from the discount curve, the
 	non-overlapping span, and the "swap" stretch across the range of tenor predictor ordinates.
 	* Cross-Recovery of the Cash Calibration Instrument "Rate" metric across the different curve construction
 	methodologies.
 	* Cross-Recovery of the Swap Calibration Instrument "Rate" metric across the different curve construction
 	methodologies.
 	* Create a turn list instance and add new turn instances.
 	* Update the discount curve with the turn list.
 	* Compare the discount factor implied the discount curve with and without applying the turns adjustment.

 * [***HaganWestForwardInterpolator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/funding/HaganWestForwardInterpolator.java)
 <i>HaganWestForwardInterpolator</i> illustrates using the Hagan and West (2006) Estimator. It provides the
 following functionality:
 	* Set up the Predictor ordinates and the response values.
 	* Construct the rational linear shape control with the specified tension.
 	* Create the Segment Inelastic design using the Ck and Curvature Penalty Derivatives.
 	* Build the Array of Segment Custom Builder Control Parameters of the KLK Hyperbolic Tension Basis Type,
 	the tension, the segment inelastic design control, and the shape controller.
 	* Setup the monotone convex stretch using the above settings, and with no linear inference, no spurious
 	extrema, or no monotone filtering applied.
 	* Setup the monotone convex stretch using the above settings, and with linear inference, no spurious
 	extrema, or no monotone filtering applied.
 	* Compute and display the monotone convex output with the linear forward state.
 	* Compute and display the monotone convex output with the harmonic forward state.

 * [***MultiStreamSwapMeasures***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/funding/MultiStreamSwapMeasures.java)
 <i>MultiStreamSwapMeasures</i> illustrates the creation, invocation, and usage of the MultiStreamSwap. It
 shows how to:
 	* Create the Discount Curve from the rates instruments.
 	* Set up the valuation and the market parameters.
 	* Create the Rates Basket from the fixed/float streams.
 	* Value the Rates Basket.

 * [***NonlinearCurveMeasures***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/funding/NonlinearCurveMeasures.java)
 <i>NonlinearCurveMeasures</i> contains a demo of the Non-linear Rates Analytics API Usage. It shows the
 following:
 	* Build a discount curve using: cash instruments only, EDF instruments only, IRS instruments only, or all
 	of them strung together.
 	* Re-calculate the component input measure quotes from the calibrated discount curve object.
 	* Compute the PVDF Wengert Jacobian across all the instruments used in the curve construction.

 * [***ShapePreservingZeroSmooth***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/funding/ShapePreservingZeroSmooth.java)
 <i>ShapePreservingZeroSmooth</i> demonstrates the usage of different shape preserving and smoothing
 techniques involved in the funding curve creation. It shows the following:
 	* Construct the Array of Cash/Swap Instruments and their Quotes from the given set of parameters.
 	* Construct the Cash/Swap Instrument Set Stretch Builder.
 	* Set up the Linear Curve Calibrator using the following parameters:
 		* Cubic Exponential Mixture Basis Spline Set
 		* C<sub>k</sub> = 2
			Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Set up the Global Curve Control parameters as follows:
 		* Zero Rate Quantification Metric
 		* Cubic Polynomial Basis Spline Set
 		* C<sub>k</sub> = 2
 			Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Set up the Local Curve Control parameters as follows:
 		* C1 Bessel Monotone Smoothener with no spurious extrema elimination and no monotone filter
 		* Zero Rate Quantification Metric
 		* Cubic Polynomial Basis Spline Set
 		* C<sub>k</sub> = 2
 			Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Construct the Shape Preserving Discount Curve by applying the linear curve calibrator to the array of
 		Cash and Swap Stretches.
 	* Construct the Globally Smoothened Discount Curve by applying the linear curve calibrator and the Global
 		Curve Control parameters to the array of Cash and Swap Stretches and the shape preserving discount
 		curve.
 	* Construct the Locally Smoothened Discount Curve by applying the linear curve calibrator and the Local
 		Curve Control parameters to the array of Cash and Swap Stretches and the shape preserving discount
 		curve.
 	* Cross-Comparison of the Cash/Swap Calibration Instrument "Rate" metric across the different curve
 		construction methodologies.
 	* Cross-Comparison of the Swap Calibration Instrument "Rate" metric across the different curve
 		construction methodologies for a sequence of bespoke swap instruments.

 * [***ShapeZeroLocalSmooth***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/funding/ShapeZeroLocalSmooth.java)
 <i>ShapeZeroLocalSmooth</i> demonstrates the usage of different local smoothing techniques involved in the
 funding curve creation. It shows the following:
 	* Construct the Array of Cash/Swap Instruments and their Quotes from the given set of parameters.
 	* Construct the Cash/Swap Instrument Set Stretch Builder.
 	* Set up the Linear Curve Calibrator using the following parameters:
 		* Cubic Exponential Mixture Basis Spline Set
 		* Zero Rate Quantification Metric
 		* C<sub>k</sub> = 2
 			Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Set up the Akima Local Curve Control parameters as follows:
 		* C1 Akima Monotone Smoothener with spurious extrema elimination and monotone filtering applied
 		* Zero Rate Quantification Metric
 		* Cubic Polynomial Basis Spline Set
 		* C<sub>k</sub> = 2
 			Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Set up the Harmonic Local Curve Control parameters as follows:
 		* C1 Harmonic Monotone Smoothener with spurious extrema elimination and monotone filtering applied
 		* Zero Rate Quantification Metric
 		* Cubic Polynomial Basis Spline Set
 		* C<sub>k</sub> = 2
 			Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Set up the Hyman 1983 Local Curve Control parameters as follows:
 		* C1 Hyman 1983 Monotone Smoothener with spurious extrema elimination and monotone filtering applied
 		* Zero Rate Quantification Metric
 		* Cubic Polynomial Basis Spline Set
 		* C<sub>k</sub> = 2
 			Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Set up the Hyman 1989 Local Curve Control parameters as follows:
 		* C1 Akima Monotone Smoothener with spurious extrema elimination and monotone filtering applied
 		* Zero Rate Quantification Metric
 		* Cubic Polynomial Basis Spline Set
 		* C<sub>k</sub> = 2
 			Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Set up the Huynh-Le Floch Delimited Local Curve Control parameters as follows:
 		* C1 Huynh-Le Floch Delimited Monotone Smoothener with spurious extrema elimination and monotone
 			filtering applied
 		* Zero Rate Quantification Metric
 		* Cubic Polynomial Basis Spline Set
 		* C<sub>k</sub> = 2
 			Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Set up the Kruger Local Curve Control parameters as follows:
 		* C1 Kruger Monotone Smoothener with spurious extrema elimination and monotone filtering applied
 		* Zero Rate Quantification Metric
 		* Cubic Polynomial Basis Spline Set
 		* C<sub>k</sub> = 2
 			Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Construct the Shape Preserving Discount Curve by applying the linear curve calibrator to the array of
 		Cash and Swap Stretches.
 	* Construct the Akima Locally Smoothened Discount Curve by applying the linear curve calibrator and the
 		Local Curve Control parameters to the array of Cash and Swap Stretches and the shape preserving
 		discount curve.
 	* Construct the Harmonic Locally Smoothened Discount Curve by applying the linear curve calibrator and
 		the Local Curve Control parameters to the array of Cash and Swap Stretches and the shape preserving
 		discount curve.
 	* Construct the Hyman 1983 Locally Smoothened Discount Curve by applying the linear curve calibrator and
 		the Local Curve Control parameters to the array of Cash and Swap Stretches and the shape preserving
 		discount curve.
 	* Construct the Hyman 1989 Locally Smoothened Discount Curve by applying the linear curve calibrator and
 		the Local Curve Control parameters to the array of Cash and Swap Stretches and the shape preserving
 		discount curve.
 	* Construct the Huynh-Le Floch Delimiter Locally Smoothened Discount Curve by applying the linear curve
 		calibrator and the Local Curve Control parameters to the array of Cash and Swap Stretches and the
 		shape preserving discount curve.
 	* Construct the Kruger Locally Smoothened Discount Curve by applying the linear curve calibrator and the
 		Local Curve Control parameters to the array of Cash and Swap Stretches and the shape preserving
 		discount curve.
 	* Cross-Comparison of the Cash/Swap Calibration Instrument "Rate" metric across the different curve
 		construction methodologies.
 	* Cross-Comparison of the Swap Calibration Instrument "Rate" metric across the different curve
 		construction methodologies for a sequence of bespoke swap instruments.

 * [***TemplatedFundingCurveBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/funding/TemplatedFundingCurveBuilder.java)
 <i>TemplatedFundingCurveBuilder</i> sample demonstrates the usage of the different pre-built Funding Curve
 Builders. It shows the following:
 	* Construct the Array of Cash Instruments and their Quotes from the given set of parameters.
 	* Construct the Array of Swap Instruments and their Quotes from the given set of parameters.
 	* Construct the Cubic Tension KLK Hyperbolic Discount Factor Shape Preserver.
 	* Construct the Cubic Tension KLK Hyperbolic Discount Factor Shape Preserver with Zero Rate Smoothening
 		applied.
 	* Construct the Cubic Polynomial Discount Factor Shape Preserver.
 	* Construct the Cubic Polynomial Discount Factor Shape Preserver with Zero Rate Smoothening applied.
 	* Construct the Discount Curve using the Bear Sterns' DENSE Methodology.
 	* Construct the Discount Curve using the Bear Sterns' DUALDENSE Methodology.
 	* Cross-Comparison of the Cash Calibration Instrument "Rate" metric across the different curve
 		construction methodologies.
 	* Cross-Comparison of the Swap Calibration Instrument "Rate" metric across the different curve
 		construction methodologies.
 	* Cross-Comparison of the generated Discount Factor across the different curve construction Methodologies
 		for different node points.


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
