# DROP Product Definition Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Product Definition Package implements the Fixed Income Components/Baskets Definitions.


## Class Components

 * [***BasketMarketParamRef***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/definition/BasketMarketParamRef.java)
 <i>BasketMarketParamRef</i> interface provides stubs for basket name, IR curve, forward curve, credit curve,
 TSY curve, and needed to value the component.

 * [***BasketProduct***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/definition/BasketProduct.java)
 <i>BasketProduct</i> abstract class extends MarketParamRef. It provides methods for getting the basket
 components, notional, coupon, effective date, maturity date, coupon amount, and list of coupon periods.

 * [***Bond***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/definition/Bond.java)
 <i>Bond</i> abstract class implements the pricing, the valuation, and the RV analytics functionality for the
 bond product.


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
