# DROP Sensitivity Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Sensitivity demonstrates the Forward Funding OIS Curve Sensitivity Functionality.


## Class Components

 * [***ForwardDerivedBasisSensitivity***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/sensitivity/ForwardDerivedBasisSensitivity.java)
 <i>ForwardDerivedBasisSensitivity</i> contains the sample demonstrating the full functionality behind creating highly customized spline based forward curves. The first sample illustrates the creation and usage of the xM-6M Tenor Basis Swap:
 	* Construct the 6M-xM float-float basis swap.
  	* Calculate the corresponding starting forward rate off of the discount curve.
  	* Construct the shape preserving forward curve off of Cubic Polynomial Basis Spline.
  	* Construct the shape preserving forward curve off of Quartic Polynomial Basis Spline.
  	* Construct the shape preserving forward curve off of Hyperbolic Tension Based Basis Spline.
  	* Set the discount curve based component market parameters.
  	* Set the discount curve + cubic polynomial forward curve based component market parameters.
  	* Set the discount curve + quartic polynomial forward curve based component market parameters.
  	* Set the discount curve + hyperbolic tension forward curve based component market parameters.
  	* Compute the following forward curve metrics for each of cubic polynomial forward, quartic polynomial forward, and KLK Hyperbolic tension forward curves:
 		* Reference Basis Par Spread
 		* Derived Basis Par Spread
  	* Compare these with a) the forward rate off of the discount curve, b) The LIBOR rate, and c) The Input Basis Swap Quote.

 * The second sample illustrates how to build and test the forward curves across various tenor basis. It shows the following steps:
  	* Construct the Discount Curve using its instruments and quotes.
  	* Build and run the sampling for the 1M-6M Tenor Basis Swap from its instruments and quotes.
  	* Build and run the sampling for the 3M-6M Tenor Basis Swap from its instruments and quotes.
  	* Build and run the sampling for the 6M-6M Tenor Basis Swap from its instruments and quotes.
  	* Build and run the sampling for the 12M-6M Tenor Basis Swap from its instruments and quotes.

 * [***ForwardReferenceBasisSensitivity***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/sensitivity/ForwardReferenceBasisSensitivity.java)
<i>ForwardReferenceBasisSensitivity</i> contains the sample demonstrating the full functionality behind creating highly customized spline based forward curves. The first sample illustrates the creation and usage of the xM-6M Tenor Basis Swap:
  	* Construct the 6M-xM float-float basis swap.
  	* Calculate the corresponding starting forward rate off of the discount curve.
  	* Construct the shape preserving forward curve off of Cubic Polynomial Basis Spline.
  	* Construct the shape preserving forward curve off of Quartic Polynomial Basis Spline.
  	* Construct the shape preserving forward curve off of Hyperbolic Tension Based Basis Spline.
  	* Set the discount curve based component market parameters.
  	* Set the discount curve + cubic polynomial forward curve based component market parameters.
  	* Set the discount curve + quartic polynomial forward curve based component market parameters.
  	* Set the discount curve + hyperbolic tension forward curve based component market parameters.
  	* Compute the following forward curve metrics for each of cubic polynomial forward, quartic polynomial forward, and KLK Hyperbolic tension forward curves:
  		* Reference Basis Par Spread
  		* Derived Basis Par Spread
  	* Compare these with a) the forward rate off of the discount curve, b) The LIBOR rate, and c) The Input Basis Swap Quote.

 * The second sample illustrates how to build and test the forward curves across various tenor basis. It shows the following steps:
  	* Construct the Discount Curve using its instruments and quotes.
  	* Build and run the sampling for the 1M-6M Tenor Basis Swap from its instruments and quotes.
  	* Build and run the sampling for the 3M-6M Tenor Basis Swap from its instruments and quotes.
  	* Build and run the sampling for the 6M-6M Tenor Basis Swap from its instruments and quotes.
  	* Build and run the sampling for the 12M-6M Tenor Basis Swap from its instruments and quotes.

 * [***ForwardReferenceBasisSensitivity***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/sensitivity/ForwardReferenceBasisSensitivity.java)
<i>FundingCurveQuoteSensitivity</i> demonstrates the calculation of the Funding curve sensitivity to the calibration instrument quotes. It does the following:
  	* Construct the Array of Cash/Swap Instruments and their Quotes from the given set of parameters.
  	* Construct the Cash/Swap Instrument Set Stretch Builder.
  	* Set up the Linear Curve Calibrator using the following parameters:
  		* Cubic Exponential Mixture Basis Spline Set
  		* Ck = 2, Segment Curvature Penalty = 2
  		* Quadratic Rational Shape Controller
  		* Natural Boundary Setting
  	* Construct the Shape Preserving Discount Curve by applying the linear curve calibrator to the array of Cash and Swap Stretches.
  	* Cross-Comparison of the Cash/Swap Calibration Instrument "Rate" metric across the different curve construction methodologies.
  	* Display of the Cash Instrument Discount Factor Quote Jacobian Sensitivities.
  	* Display of the Swap Instrument Discount Factor Quote Jacobian Sensitivities.

 * [***OISCurveQuoteSensitivity***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/sensitivity/OISCurveQuoteSensitivity.java)
<i>OISCurveQuoteSensitivity</i> demonstrates the calculation of the OIS discount curve sensitivity to the calibration instrument quotes. It does the following:
  	* Construct the Array of Cash/OIS Instruments and their Quotes from the given set of parameters.
  	* Construct the Cash/OIS Instrument Set Stretch Builder.
  	* Set up the Linear Curve Calibrator using the following parameters:
  		* Cubic Exponential Mixture Basis Spline Set
  		* Ck = 2, Segment Curvature Penalty = 2
  		* Quadratic Rational Shape Controller
  		* Natural Boundary Setting
  	* Construct the Shape Preserving OIS Discount Curve by applying the linear curve calibrator to the array of Cash and OIS Stretches.
  	* Cross-Comparison of the Cash/OIS Calibration Instrument "Rate" metric across the different curve construction methodologies.
  	* Display of the Cash Instrument Discount Factor Quote Jacobian Sensitivities.
  	* Display of the OIS Instrument Discount Factor Quote Jacobian Sensitivities.

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
