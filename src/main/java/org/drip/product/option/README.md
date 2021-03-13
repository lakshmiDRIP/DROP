# DROP Product Option Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Product Option Package implements the Options on Fixed Income Components.


## Class Components

 * [***CDSEuropeanOption***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/option/CDSEuropeanOption.java)
 <i>CDSEuropeanOption</i> implements the Payer/Receiver European Option on a CDS.

 * [***EuropeanCallPut***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/option/EuropeanCallPut.java)
 <i>EuropeanCallPut</i> implements a simple European Call/Put Option, and its Black Scholes Price.

 * [***FixFloatEuropeanOption***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/option/FixFloatEuropeanOption.java)
 <i>FixFloatEuropeanOption</i> implements the Payer/Receiver European Option on the Fix-Float Swap.

 * [***OptionComponent***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/option/OptionComponent.java)
 <i>OptionComponent</i> extends ComponentMarketParamRef and provides the following methods:
 	* Get the component's initial notional, notional, and coupon.
 	* Get the Effective date, Maturity date, First Coupon Date.
 	* Set the market curves - discount, TSY, forward, and Credit curves.
 	* Retrieve the component's settlement parameters.
 	* Value the component using standard/custom market parameters.
 	* Retrieve the component's named measures and named measure values.
 	* Retrieve the Underlying Fixed Income Product, Day Count, Strike, Calendar, and Manifest Measure.


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
