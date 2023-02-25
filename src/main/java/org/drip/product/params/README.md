# DROP Product Params Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Product Params Package contains the Fixed Income Product Customization Parameters.


## Class Components

 * [***BondStream***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/BondStream.java)
 <i>BondStream</i> is the place-holder for the bond period generation parameters. Contains the bond date
 adjustment parameters for period start/end, period accrual start/end, effective, maturity, pay and reset,
 first coupon date, and interest accrual start date. It exports serialization into and de-serialization out
 of byte arrays.

 * [***CDXIdentifier***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/CDXIdentifier.java)
 <i>CDXIdentifier</i> implements the creation and the static details of the all the NA, EU, SovX, EMEA, and
 ASIA standardized CDS indexes. It contains the index, the tenor, the series, and the version of a given CDX.
 It exports serialization into and de-serialization out of byte arrays.

 * [***CDXRefDataParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/CDXRefDataParams.java)
 <i>CDXRefDataParams</i> contains the complete set of reference data that corresponds to the contract of a
 standard CDX. It consists of the following category and fields:
 	* Descriptive - Index Label, Index Name, Curve Name, Index Class, Index Group Name, Index Short Group
 		Name, Index Short Name, Short Name
 	* Issuer ID - Curve ID, Red ID, Series, Version, Curvy Curve ID, Location, Bloomberg Ticker
 	* Quote Details - Quote As CDS
 	* Date - Issue Date, Maturity Date
 	* Coupon Parameters - Coupon Rate, Currency, Day Count, Full First Stub, Frequency
 	* Component Details - Original Count, Defaulted Count
 	* Payoff Details - Knock-out on Default, Pay Accrued Amount, Recovery on Default
 	* Other - Index Life Span, Index Factor

 * [***CouponSetting***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/CouponSetting.java)
 <i>CouponSetting</i> contains the coupon type, schedule, and the coupon amount for the component. If
 available floor and/or ceiling may also be applied to the coupon, in a pre-determined order of precedence.
 It exports serialization into and de-serialization out of byte arrays.

 * [***CreditSetting***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/CreditSetting.java)
 <i>CreditSetting</i> contains the credit related valuation parameters - use default pay lag, use curve or
 the component recovery, component recovery, credit curve name, and whether there is accrual on default. It
 exports serialization into and de-serialization out of byte arrays.

 * [***CTDEntry***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/CTDEntry.java)
 <i>CTDEntry</i> implements the Bond Futures CTD Entry Details.

 * [***CurrencyPair***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/CurrencyPair.java)
 <i>CurrencyPair</i> class contains the numerator currency, the denominator currency, the quote currency, and
 the PIP Factor. It exports serialization into and de-serialization out of byte arrays.

 * [***EmbeddedOptionSchedule***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/EmbeddedOptionSchedule.java)
 <i>EmbeddedOptionSchedule</i> is a place holder for the embedded option schedule for the component. It
 contains the schedule of exercise dates and factors, the exercise notice period, and the option is to call
 or put. Further, if the option is of the type fix-to-float on exercise, contains the post-exercise floater
 index and floating spread. If the exercise is not discrete (American option), the exercise dates/factors are
 discretized according to a pre-specified discretization grid. It exports serialization into and de-
 serialization out of byte arrays.

 * [***FloaterSetting***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/FloaterSetting.java)
 <i>FloaterSetting</i> contains the component floating rate parameters. It holds the rate index, floater day
count, and one of either the coupon spread or the full current coupon. It also provides for serialization
into and de-serialization out of byte arrays.

 * [***IdentifierSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/IdentifierSet.java)
 <i>IdentifierSet</i> contains the component identifier parameters - ISIN, CUSIP, ID, and ticker. It exports
 serialization into and de-serialization out of byte arrays.

 * [***LastTradingDateSetting***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/LastTradingDateSetting.java)
 <i>LastTradingDateSetting</i> contains the Last Trading Date Generation Scheme for the given Option.

 * [***NotionalSetting***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/NotionalSetting.java)
 <i>NotionalSetting</i> contains the product notional schedule and the amount. It also incorporates hints on
 how the notional factors are to be interpreted - off of the original or the current notional. Further flags
 tell whether the notional factor is to be applied at the start/end/average of the coupon period. It exports
 serialization into and de-serialization out of byte arrays.

 * [***QuoteConvention***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/QuoteConvention.java)
 <i>QuoteConvention</i> contains the Component Market Convention Parameters - the quote convention, the
 calculation type, the first settle date, and the redemption amount. It exports serialization into and
 de-serialization out of byte arrays.

 * [***StandardCDXParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/StandardCDXParams.java)
 <i>StandardCDXParams</i> implements the parameters used to create the standard CDX - the coupon, the number
 of components, and the currency.

 * [***TerminationSetting***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/TerminationSetting.java)
 <i>TerminationSetting</i> class contains the current "liveness" state of the component, and, if inactive,
 how it entered that state. It exports serialization into and de-serialization out of byte arrays.

 * [***TreasuryBenchmarks***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/TreasuryBenchmarks.java)
 <i>TreasuryBenchmarks</i> contains the treasury benchmark set - the primary treasury benchmark, and an array
 of secondary treasury benchmarks. It exports serialization into and de-serialization out of byte arrays.

 * [***Validatable***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/Validatable.java)
 <i>Validatable</i> interface defines the validate function, which validates the current object state.


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
