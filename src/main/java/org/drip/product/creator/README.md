# DROP Product Creator Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Product Creator Package implements the Streams and Products Construction Utilities.


## Class Components

 * [***BondBasketBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/creator/BondBasketBuilder.java)
 <i>BondBasketBuilder</i> contains the suite of helper functions for creating the bond Basket Product from
 different kinds of inputs and byte streams.

 * [***BondBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/creator/BondBuilder.java)
 <i>BondBuilder</i> contains the suite of helper functions for creating simple fixed/floater bonds, user
 defined bonds, optionally with custom cash flows and embedded option schedules (European or American). It
 also constructs bonds by de-serializing the byte stream.

 * [***BondProductBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/creator/BondProductBuilder.java)
 <i>BondProductBuilder</i> holds the static parameters of the bond product needed for the full bond
 valuation. It contains the bond identifier parameters (ISIN, CUSIP), the issuer level parameters (Ticker,
 SPN or the credit curve string), coupon parameters (coupon rate, coupon frequency, coupon type, day count),
 maturity parameters (maturity date, maturity type, final maturity, redemption value), date parameters
 (announce, first settle, first coupon, interest accrual start, and issue dates), embedded option parameters
 (callable, putable, has been exercised), currency parameters (trade, coupon, and redemption currencies),
 floater parameters (floater flag, floating coupon convention, current coupon, rate index, spread), and
 whether the bond is perpetual or has defaulted.

 * [***BondRefDataBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/creator/BondRefDataBuilder.java)
 <i>BondRefDataBuilder</i> holds the entire set of static parameters for the bond product. In particular, it
 contains the bond identifier parameters (ISIN, CUSIP, BBG ID, name short name), the issuer level parameters
 (Ticker, category, industry, issue type, issuer country, issuer country code, collateral type, description,
 security type, unique Bloomberg ID, long company name, issuer name, SPN or the credit curve string), issue
 parameters (issue amount, issue price, outstanding amount, minimum piece, minimum increment, par amount,
 lead manager, exchange code, country of incorporation, country of guarantor, country of domicile, industry
 sector, industry group, industry sub-group, senior/sub), coupon parameters (coupon rate, coupon frequency,
 coupon type, day count), maturity parameters (maturity date, maturity type, final maturity, redemption
 value), date parameters (announce, first settle, first coupon, interest accrual start, next coupon, previous
 coupon, penultimate coupon, and issue dates), embedded option parameters (callable, putable, has been
 exercised), currency parameters (trade, coupon, and redemption currencies), floater parameters (floater
 flag, floating coupon convention, current coupon, rate index, spread), trade status, ratings (SnP, Moody,
 and Fitch), and whether the bond is private placement, is registered, is a bearer bond, is reverse
 convertible, is a structured note, can be unit traded, is perpetual or has defaulted.

 * [***CDSBasketBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/creator/CDSBasketBuilder.java)
 <i>CDSBasketBuilder</i> contains the suite of helper functions for creating the CDS Basket Product from
 different kinds of inputs and byte streams.

 * [***CDSBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/creator/CDSBuilder.java)
 <i>CDSBuilder</i> contains the suite of helper functions for creating the CreditDefaultSwap product from the
 parameters/byte array streams. It also creates the standard EU, NA, ASIA contracts, CDS with amortization
 schedules, and custom CDS from product codes/tenors.

 * [***ConstantPaymentBondBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/creator/ConstantPaymentBondBuilder.java)
 <i>ConstantPaymentBondBuilder</i> contains the Suite of Helper Functions for creating Constant Payments
 Based Bonds.

 * [***DualStreamComponentBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/creator/DualStreamComponentBuilder.java)
 <i>DualStreamComponentBuilder</i> contains the suite of helper functions for creating the Stream-based Dual
 Streams from different kinds of inputs. In particular, it exposes the following functionality:
 	* Construction of the fix-float swap component.
 	* Construction of the float-float swap component.
 	* Construction of the generic dual stream component.

 * [***SingleStreamComponentBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/creator/SingleStreamComponentBuilder.java)
 <i>SingleStreamComponentBuilder</i> contains the suite of helper functions for creating the Futures product
 and product pack from the parameters/codes/byte array streams. It also contains function to construct EDF
 codes and the EDF product from code.

 * [***SingleStreamOptionBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/creator/SingleStreamOptionBuilder.java)
 <i>SingleStreamOptionBuilder</i> contains the suite of helper functions for creating the Options Product
 Instance off of a single stream underlying.

 * [***StreamBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/creator/StreamBuilder.java)
 <i>StreamBuilder</i> contains Utility Functions to construct Fixed, Floating, and Mixed Streams.


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
