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

 * [***BondProduct***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/definition/BondProduct.java)
 <i>BondProduct</i> interface implements the product static data behind bonds of all kinds. Bond static data
 is captured in a set of 11 container classes; BondTSYParams, BondCouponParams, BondNotionalParams,
 BondFloaterParams, BondCurrencyParams, BondIdentifierParams, ComponentValuationParams,
 ComponentRatesValuationParams, ComponentCreditValuationParams, ComponentTerminationEvent,
 BondFixedPeriodParams, and one EmbeddedOptionSchedule object instance each for the call and the put objects.
 Each of these parameter sets can be set separately.

 * [***CalibratableComponent***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/definition/CalibratableComponent.java)
 <i>CalibratableComponent</i> abstract class provides implementation of Components calibration interface. It
 exposes stubs for getting/setting the component calibration code, generate calibrated measure values from
 the market inputs, and compute micro Jacobians (QuoteDF and PVDF micro Jacks).

 * [***Component***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/definition/Component.java)
 <i>Component</i> abstract class extends the ComponentMarketParamRef and provides the following methods.
 	* Get the products initial notional, notional, and coupon.
 	* Get the Effective date, Maturity date, First Coupon Date.
 	* List the coupon periods.
 	* Set the market curves; discount, TSY, forward, and Credit curves.
 	* Retrieve the product settlement parameters.
 	* Value the product using standard/custom market parameters.
 	* Retrieve the product named measures and named measure values.

 * [***ComponentMarketParamRef***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/definition/ComponentMarketParamRef.java)
 <i>ComponentMarketParamRef</i> interface provides stubs for name, IR curve, forward curve, credit curve, TSY
 curve, and needed to value the component.

 * [***CreditComponent***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/definition/CreditComponent.java)
 <i>CreditComponent</i> is the base abstract class on top of which all credit components are implemented. Its
 methods expose Credit Valuation Parameters, product specific recovery, and coupon/loss cash flows.

 * [***CreditDefaultSwap***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/definition/CreditDefaultSwap.java)
 <i>CreditDefaultSwap</i> is the base abstract class implements the pricing, the valuation, and the RV
 analytics functionality for the CDS product. Targeted functions calibrate the flat spread and reset coupon
 (for calibration purposes).


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
