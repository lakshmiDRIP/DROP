# DROP Param Quoting Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Param Quoting Package contains the Quoting Convention Valuation Customization Parameters.


## Class Components

 * [***MeasureInterpreter***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/quoting/MeasureInterpreter.java)
 <i>MeasureInterpreter</i> is the abstract shell stub class from which all product measure quoting parameters
 are derived. It contains fields needed to interpret a measure quote.

 * [***QuotedSpreadInterpreter***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/quoting/QuotedSpreadInterpreter.java)
 <i>QuotedSpreadInterpreter</i> holds the fields needed to interpret a Quoted Spread Quote. It contains the
 contract type and the coupon.

 * [***YieldInterpreter***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/quoting/YieldInterpreter.java)
 <i>YieldInterpreter</i> holds the fields needed to interpret a Yield Quote. It contains the quote day count,
 quote frequency, quote EOM Adjustment, quote Act/Act parameters, and quote Calendar.


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
