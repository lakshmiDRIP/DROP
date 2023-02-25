# DROP Sample Multi-Curve Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Sample Multi-Curve illustrates the Multi-Curve Construction and Valuation.


## Class Components

 * [***CustomBasisCurveBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/multicurve/CustomBasisCurveBuilder.java)
 <i>CustomBasisCurveBuilder</i> contains the sample demonstrating the full functionality behind creating highly customized spline based Basis curves.

 * [***FixFloatForwardCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/multicurve/FixFloatForwardCurve.java)
 <i>FixFloatForwardCurve</i> contains the sample demonstrating the full functionality behind creating highly customized spline based forward curves from fix-float swaps and the discount curves.

 * [***FixFloatSwap***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/multicurve/FixFloatSwap.java)
 <i>FixFloatSwap</i> contains a full valuation run on the Multi-Curve Fix-Float IRS Product.

 * [***FixFloatSwapAnalysis***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/multicurve/FixFloatSwapAnalysis.java)
 <i>FixFloatSwapAnalysis</i> contains an analysis if the correlation and volatility impact on the fix-float Swap.

 * [***FixFloatSwapIMM***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/multicurve/FixFloatSwapIMM.java)
 <i>FixFloatSwapIMM</i> contains a full valuation run on the IMM Fix-Float Swap Product.

 * [***FloatFloatForwardCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/multicurve/FloatFloatForwardCurve.java)
 <i>FloatFloatForwardCurve</i> contains the sample demonstrating the full functionality behind creating highly customized spline based forward curves.
 	* The first sample illustrates the creation and usage of the xM-6M Tenor Basis Swap:
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

 * [***FundingNativeForwardReconciler***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/multicurve/FundingNativeForwardReconciler.java)
 <i>FundingNativeForwardReconciler</i> demonstrates the Construction of the Forward Curve Native to the Discount Curve across different Tenors, and display their Reconciliation.

 * [***OTCSwapOptionSettlements***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/multicurve/OTCSwapOptionSettlements.java)
 <i>OTCSwapOptionSettlements</i> contains all the pre-fixed Definitions of the OTC Swap Option Settlements.


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
