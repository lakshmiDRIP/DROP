# DROP Product Rates Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Product Rates Package implements the Fixed Income Multi-Stream Components.


## Class Components

 * [***DualStreamComponent***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/rates/DualStreamComponent.java)
 <i>DualStreamComponent</i> is the abstract class that extends the CalibratableFixedIncomeComponent on top of
 which all the dual stream rates components (fix-float, float-float, IRS etc.) are implemented.

 * [***FixFloatComponent***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/rates/FixFloatComponent.java)
 <i>FixFloatComponent</i> contains the implementation of the Fix-Float Index Basis Swap product
 contract/valuation details. It is made off one Reference Fixed stream and one Derived floating stream. It
 exports the following functionality:
 	* Standard/Custom Constructor for the FixFloatComponent
 	* Dates: Effective, Maturity, Coupon dates and Product settlement Parameters
 	* Coupon/Notional Outstanding as well as schedules
 	* Retrieve the constituent floating streams
 	* Market Parameters: Discount, Forward, Credit, Treasury Curves
 	* Cash Flow Periods: Coupon flows and (Optionally) Loss Flows
 	* Valuation: Named Measure Generation
 	* Calibration: The codes and constraints generation
 	* Jacobians: Quote/DF and PV/DF micro-Jacobian generation
 	* Serialization into and de-serialization out of byte arrays

 * [***FloatFloatComponent***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/rates/FloatFloatComponent.java)
 <i>FloatFloatComponent</i> contains the implementation of the Float-Float Index Basis Swap product
 contract/valuation details. It is made off one Reference Floating stream and one Derived floating stream. It
 exports the following functionality:
 	* Standard/Custom Constructor for the FloatFloatComponent
 	* Dates: Effective, Maturity, Coupon dates and Product settlement Parameters
 	* Coupon/Notional Outstanding as well as schedules
 	* Retrieve the constituent floating streams
 	* Market Parameters: Discount, Forward, Credit, Treasury Curves
 	* Cash Flow Periods: Coupon flows and (Optionally) Loss Flows
 	* Valuation: Named Measure Generation
 	* Calibration: The codes and constraints generation
 	* Jacobians: Quote/DF and PV/DF micro-Jacobian generation
 	* Serialization into and de-serialization out of byte arrays

 * [***RatesBasket***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/rates/RatesBasket.java)
 <i>RatesBasket</i> contains the implementation of the Basket of Rates Component legs. RatesBasket is made
 from zero/more fixed and floating streams. It exports the following functionality:
 	* Standard/Custom Constructor for the RatesBasket
 	* Dates: Effective, Maturity, Coupon dates and Product settlement Parameters
 	* Coupon/Notional Outstanding as well as schedules
 	* Retrieve the constituent fixed and floating streams
 	* Market Parameters: Discount, Forward, Credit, Treasury Curves
 	* Cash Flow Periods: Coupon flows and (Optionally) Loss Flows
 	* Valuation: Named Measure Generation
 	* Calibration: The codes and constraints generation
 	* Jacobians: Quote/DF and PV/DF micro-Jacobian generation
 	* Serialization into and de-serialization out of byte arrays

 * [***SingleStreamComponent***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/rates/SingleStreamComponent.java)
 <i>SingleStreamComponent</i> implements fixed income component that is based off of a single stream.

 * [***Stream***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/rates/Stream.java)
 <i>Stream</i> implements the fixed and the floating streams.


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
