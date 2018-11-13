# DROP Param Valuation Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Param Valuation Package contains the Valuation Settlement and Valuation Customization Parameters.


## Class Components

 * [***CashSettleParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/valuation/CashSettleParams.java)
 <i>CashSettleParams</i> is the place-holder for the cash settlement parameters for a given product. It
 contains the cash settle lag, the calendar, and the date adjustment mode.

 * [***ValuationCustomizationParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/valuation/ValuationCustomizationParams.java)
 <i>ValuationCustomizationParams</i> holds the parameters needed to interpret the input quotes. It contains
 the quote day count, the quote frequency, the quote EOM Adjustment, the quote Act/Act parameters, the quote
 Calendar, the Core Collateralization Parameters, and the Switchable Alternate Collateralization Parameters.
 It also indicates if the native quote is spread based.

 * [***ValuationParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/valuation/ValuationParams.java)
 <i>ValuationParams</i> is the place-holder for the valuation parameters for a given product. It contains the
 valuation and the cash pay/settle dates, as well as the calendar. It also exposes a number of methods to
 construct standard valuation parameters.

 * [***WorkoutInfo***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/valuation/WorkoutInfo.java)
 <i>WorkoutInfo</i> is the place-holder for the work-out parameters. It contains the date, the factor, the
 type, and the yield of the work-out.


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
