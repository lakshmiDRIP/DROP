# DROP Regression Curve Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Regression Curve implements the Curve Construction/Reconciliation Regression Engine.


## Class Components

 * [***CreditAnalyticsRegressionEngine***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/curve/CreditAnalyticsRegressionEngine.java)
 <i>CreditAnalyticsRegressionEngine</i> implements the RegressionEngine for the curve regression. It adds the
 CreditCurveRegressor, DiscountCurveRegressor, and ZeroCurveRegressor, and launches the regression engine.

 * [***CreditCurveRegressor***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/curve/CreditCurveRegressor.java)
 <i>CreditCurveRegressor</i> implements the regression set analysis for the Credit Curve.
 CreditCurveRegressor regresses 12 scenarios:
 	* #1: Create an SNAC CDS.
 	* #2: Create the credit curve from a set of CDS instruments.
 	* #3: Create the credit curve from a flat hazard rate.
 	* #4: Create the credit curve from a set of survival probabilities.
 	* #5: Create the credit curve from an array of hazard rates.
 	* #6: Extract the credit curve instruments and quotes.
 	* #7: Create a parallel hazard shifted credit curve.
 	* #8: Create a parallel quote shifted credit curve.
 	* #9: Create a node tweaked credit curve.
 	* #10: Set a specific default date on the credit curve.
 	* #11: Compute the effective survival probability between 2 dates.
 	* #12: Compute the effective hazard rate between 2 dates.

 * [***DiscountCurveRegressor***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/curve/DiscountCurveRegressor.java)
 <i>DiscountCurveRegressor</i> implements the regression set analysis for the Discount Curve.
 DiscountCurveRegressor regresses 11 scenarios:
 	* #1: Create the discount curve from a set 30 instruments (cash/future/swap).
 	* #2: Create the discount curve from a flat discount rate.
 	* #3: Create the discount curve from a set of discount factors.
 	* #4: Create the discount curve from the implied discount rates.
 	* #5: Extract the discount curve instruments and quotes.
 	* #6: Create a parallel shifted discount curve.
 	* #7: Create a rate shifted discount curve.
 	* #8: Create a basis rate shifted discount curve.
 	* #9: Create a node tweaked discount curve.
 	* #10: Compute the effective discount factor between 2 dates.
 	* #11: Compute the effective implied rate between 2 dates.

 * [***ZeroCurveRegressor***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/curve/ZeroCurveRegressor.java)
 <i>ZeroCurveRegressor</i> implements the regression analysis set for the Zero Curve. The  regression tests
 do the consists of the following:
 	* Build a discount curve, followed by the zero curve
 	* Regressor #1: Compute zero curve discount factors
 	* Regressor #2: Compute zero curve zero rates


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
