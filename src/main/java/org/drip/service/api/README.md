# DROP Service API Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Service API Package implements the Horizon Roll Attribution Service API.


## Class Components

 * [***CDXCOB***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/api/CDXCOB.java)
 <i>CDXCOB</i> contains the Name and the COB Price for a given CDX.

 * [***DateDiscountCurvePair***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/api/DateDiscountCurvePair.java)
 <i>DateDiscountCurvePair</i> contains the COB/Discount Curve Pair, and the corresponding computed outputs.

 * [***DiscountCurveInputInstrument***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/api/DiscountCurveInputInstrument.java)
 <i>DiscountCurveInputInstrument</i> contains the input instruments and their quotes.

 * [***FixFloatFundingInstrument***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/api/FixFloatFundingInstrument.java)
 <i>FixFloatFundingInstrument</i> contains the Fix Float Instrument Inputs for the Funding Curve Construction
 Purposes.

 * [***ForwardRates***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/api/ForwardRates.java)
 <i>ForwardRates</i> contains the array of the forward rates.

 * [***InstrMetric***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/api/InstrMetric.java)
 <i>InstrMetric</i> contains the fields that hold the result of the PnL metric calculations.

 * [***ProductDailyPnL***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/api/ProductDailyPnL.java)
 <i>ProductDailyPnL</i> contains the following daily measures computed:
 	* 1D Carry, Roll Down, Curve Shift, and Full Return PnL
 	* 3D Carry and Roll Down PnL
 	* 3M Carry and Roll Down PnL
 	* Current DV01


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
