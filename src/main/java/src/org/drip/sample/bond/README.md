# DROP Sample Bond Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Sample Bond illustrates the Bullet, EOS Bond Metrics + Curve Analytics.


## Class Components

 * [***BasketAggregateMeasuresGeneration***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bond/BasketAggregateMeasuresGeneration.java)
 <i>BasketAggregateMeasuresGeneration</i> contains a demo of the bond basket Measure generation Sample. It
 shows the following:
 	* Build the IR Curve from the Rates' instruments.
 	* Build the Component Credit Curve from the CDS instruments.
 	* Create the basket market parameters and add the named discount curve and the credit curves to it.
 	* Create the bond basket from the component bonds and their weights.
 	* Construct the Valuation and the Pricing Parameters.
 	* Generate the bond basket measures from the valuation, the pricer, and the market parameters.

 * [***CoreCashFlowMeasures***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bond/CoreCashFlowMeasures.java)
 <i>CoreCashFlowMeasures</i> contains a demo of the Bond Core Measures and the Cash Flow Sample. It
 generates the Core and the RV measures for essentially the same bond (with identical cash flows) constructed
 in three different ways:
  	* As a fixed rate bond.
  	* As a floater.
  	* As a bond constructed from a set of custom coupon and principal flows.

 * [***CorporateIssueMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bond/CorporateIssueMetrics.java)
 <i>CorporateIssueMetrics</i> demonstrates the Corporate Bond Pricing and Relative Value Measure Generation
 Functionality.

 * [***MultiCallExerciseMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bond/MultiCallExerciseMetrics.java)
 <i>MultiCallExerciseMetrics</i> demonstrates the Simulations of the Per-Path Callable Bond OAS Based
 Exercise Metrics.

 * [***MultiCallMonteCarlo***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bond/MultiCallMonteCarlo.java)
 <i>MultiCallMonteCarlo</i> demonstrates the Simulations of the Path/Vertex EOS Bond Metrics.

 * [***RegressionSplineCashCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bond/RegressionSplineCashCurve.java)
 <i>RegressionSplineCashCurve</i> demonstrates the Functionality behind the Regression Spline based OLS
 best-fit Construction of a Cash Bond Discount Curve Based on Input Price/Yield.

 * [***RelativeValueMeasuresGeneration***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bond/RelativeValueMeasuresGeneration.java)
 <i>RelativeValueMeasuresGeneration</i> is a Bond RV Measures Generation Sample demonstrating the invocation
 and usage of Bond RV Measures functionality. It shows the following:
   	* Create the discount/treasury curve from rates/treasury instruments.
   	* Compute the work-out date given the price.
   	* Compute and display the base RV measures to the work-out date.
  	* Compute and display the bumped RV measures to the work-out date.


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
