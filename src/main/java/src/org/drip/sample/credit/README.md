# DROP Sample Credit Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Sample Credit illustrates Single Name Portfolio CDS Analytics.


## Class Components

 * [***BuiltInCDSPortfolioDefinitions***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/credit/BuiltInCDSPortfolioDefinitions.java)
 <i>BuiltInCDSPortfolioDefinitions</i> displays the Built-in CDS Portfolios. It shows the following:
 	* Construct the CDX.NA.IG 5Y Series 17 index by name and series.
 	* Construct the on-the-run CDX.NA.IG 5Y Series index.
 	* List all the built-in CDX's - their names and descriptions.
 	* Construct the on-the run CDX.EM 5Y corresponding to T - 1Y.
 	* Construct the on-the run ITRAXX.ENERGY 5Y corresponding to T - 7Y.
 	* Retrieve the full set of date/index series set for ITRAXX.ENERGY.

 * [***CDSBasketMeasures***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/credit/CDSBasketMeasures.java)
 <i>CDSBasketMeasures</i> contains a demo of the CDS Basket Measures Generation Sample. It shows the
 following:
 	* Build the IR Curve from the Rates' instruments.
 	* Build the Component Credit Curve from the CDS instruments.
 	* Create the basket market parameters and add the named discount curve and the credit curves to it.
 	* Create the CDS basket from the component CDS and their weights.
 	* Construct the Valuation and the Pricing Parameters.
 	* Generate the CDS basket measures from the valuation, the pricer, and the market parameters.

 * [***CDSCashFlowMeasures***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/credit/CDSCashFlowMeasures.java)
 <i>CDSCashFlowMeasures</i> contains a demo of the CDS Measures and Cash flow Generation Sample. It
 illustrates the following:
 	* Credit Curve Creation: From flat Hazard Rate, and from an array of dates and their corresponding
 		survival probabilities.
 	* Create Credit Curve from CDS instruments, and recover the input measure quotes.
 	* Create an SNAC CDS, price it, and display the coupon/loss cash flow.

 * [***CDSValuationMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/credit/CDSValuationMetrics.java)
 <i>CDSValuationMetrics</i> contains the Demonstration of Valuing a Payer/Receiver CDS European Option
 Sample.

 * [***CreditIndexDefinitions***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/credit/CreditIndexDefinitions.java)
 <i>CreditIndexDefinitions</i> displays the Definitions of the CDX NA IG OTC Index CDS Contracts.
 

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
