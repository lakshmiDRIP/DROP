# DROP Overnight Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Overnight demonstrates Shape Preserving Stretch Overnight Curve Construction.


## Class Components

 * [***CustomOvernightCurveReconciler***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/overnight/CustomOvernightCurveReconciler.java)
 <i>CustomOvernightCurveReconciler</i> demonstrates the multi-stretch transition custom Overnight curve construction, turns application, discount factor extraction, and calibration quote recovery.

 * [***MultiStretchCurveBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/overnight/MultiStretchCurveBuilder.java)
 <i>MultiStretchCurveBuilder</i> contains a sample of the construction and usage of the Overnight Curve built using the Overnight Indexed Swap Product Instruments in their distinct stretches.

 * [***ShapeOvernightZeroLocalSmooth***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/overnight/ShapeOvernightZeroLocalSmooth.java)
 <i>ShapeOvernightZeroLocalSmooth</i> demonstrates the usage of different local smoothing techniques involved in the Overnight curve creation. It shows the following:
 	* Construct the Array of Deposit/OIS Instruments and their Quotes from the given set of parameters.
 	* Construct the Deposit/OIS Instrument Set Stretch Builder.
 	* Set up the Linear Curve Calibrator using the following parameters:
 		* Cubic Exponential Mixture Basis Spline Set
 		* Ck = 2, Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Set up the Akima Local Curve Control parameters as follows:
 		* C1 Akima Monotone Smoothener with spurious extrema elimination and monotone filtering applied
 		* Zero Rate Quantification Metric
 		* Cubic Polynomial Basis Spline Set
 		* Ck = 2, Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Set up the Harmonic Local Curve Control parameters as follows:
 		* C1 Harmonic Monotone Smoothener with spurious extrema elimination and monotone filtering applied
 		* Zero Rate Quantification Metric
 		* Cubic Polynomial Basis Spline Set
 		* Ck = 2, Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Set up the Hyman 1983 Local Curve Control parameters as follows:
 		* C1 Hyman 1983 Monotone Smoothener with spurious extrema elimination and monotone filtering applied
 		* Zero Rate Quantification Metric
 		* Cubic Polynomial Basis Spline Set
 		* Ck = 2, Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Set up the Hyman 1989 Local Curve Control parameters as follows:
 		* C1 Akima Monotone Smoothener with spurious extrema elimination and monotone filtering applied
 		* Zero Rate Quantification Metric
 		* Cubic Polynomial Basis Spline Set
 		* Ck = 2, Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Set up the Huynh-Le Floch Delimited Local Curve Control parameters as follows:
 		* C1 Huynh-Le Floch Delimited Monotone Smoothener with spurious extrema elimination and monotone filtering applied
 		* Zero Rate Quantification Metric
 		* Cubic Polynomial Basis Spline Set
 		* Ck = 2, Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Set up the Kruger Local Curve Control parameters as follows:
 		* C1 Kruger Monotone Smoothener with spurious extrema elimination and monotone filtering applied
 		* Zero Rate Quantification Metric
 		* Cubic Polynomial Basis Spline Set
 		* Ck = 2, Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Construct the Shape Preserving OIS Discount Curve by applying the linear curve calibrator to the array of Deposit and OIS Stretches.
 	* Construct the Akima Locally Smoothened OIS Discount Curve by applying the linear curve calibrator and the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape preserving discount curve.
 	* Construct the Harmonic Locally Smoothened OIS Discount Curve by applying the linear curve calibrator and the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape preserving discount curve.
 	* Construct the Hyman 1983 Locally Smoothened OIS Discount Curve by applying the linear curve calibrator and the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape preserving discount curve.
 	* Construct the Hyman 1989 Locally Smoothened OIS Discount Curve by applying the linear curve calibrator and the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape preserving discount curve.
 	* Construct the Huynh-Le Floch Delimiter Locally Smoothened OIS Discount Curve by applying the linear curve calibrator and the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape preserving discount curve.
 	* Construct the Kruger Locally Smoothened OIS Discount Curve by applying the linear curve calibrator and the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape preserving discount curve.
 	* Cross-Comparison of the Deposit/OIS Calibration Instrument "Rate" metric across the different curve construction methodologies.
 	* Cross-Comparison of the OIS Calibration Instrument "Rate" metric across the different curve construction methodologies for a sequence of bespoke swap instruments.

 * [***ShapePreservingOvernightZeroSmooth***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/overnight/ShapePreservingOvernightZeroSmooth.java)
 <i>ShapePreservingOvernightZeroSmooth</i> demonstrates the usage of different shape preserving and smoothing techniques involved in the Overnight curve creation. It shows the following:
 	* Construct the Array of Cash/OIS Instruments and their Quotes from the given set of parameters.
 	* Construct the Cash/OIS Instrument Set Stretch Builder.
 	* Set up the Linear Curve Calibrator using the following parameters:
 		* Cubic Exponential Mixture Basis Spline Set
 		* Ck = 2, Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Set up the Global Curve Control parameters as follows:
 		* Zero Rate Quantification Metric
 		* Cubic Polynomial Basis Spline Set
 		* Ck = 2, Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
  	* Set up the Local Curve Control parameters as follows:
 		* C1 Bessel Monotone Smoothener with no spurious extrema elimination and no monotone filter
 		* Zero Rate Quantification Metric
 		* Cubic Polynomial Basis Spline Set
 		* Ck = 2, Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Construct the Shape Preserving OIS Discount Curve by applying the linear curve calibrator to the array of Cash and OIS Stretches.
 	* Construct the Globally Smoothened OIS Discount Curve by applying the linear curve calibrator and the Global Curve Control parameters to the array of Cash and OIS Stretches and the shape preserving discount curve.
 	* Construct the Locally Smoothened OIS Discount Curve by applying the linear curve calibrator and the Local Curve Control parameters to the array of Cash and OIS Stretches and the shape preserving discount curve.
 	* Cross-Comparison of the Cash/OIS Calibration Instrument "Rate" metric across the different curve construction methodologies.
 	* Cross-Comparison of the OIS Calibration Instrument "Rate" metric across the different curve construction methodologies for a sequence of bespoke OIS instruments.

 * [***SingleStretchCurveBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/overnight/SingleStretchCurveBuilder.java)
 <i>SingleStretchCurveBuilder</i> contains a sample of the construction and usage of the Overnight Curve built using the Overnight Indexed Swap Product Instruments inside a single stretch.


## Class Components

 * Ametrano, F., and M. Bianchetti (2013): Everything You Always Wanted to Know About Multiple Interest Rate Curve Bootstrapping but Were Afraid to Ask http://papers.ssrn.com/sol3/papers.cfm?abstract_id=2219548


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
