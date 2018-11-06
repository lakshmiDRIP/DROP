# DROP Analytics Event Day Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Analytics Event Day Package holds the Fixed/Variable Custom Holiday Creation.

## Class Components

 * [***Base***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/eventday/Base.java)
 <i>Base</i> is an abstraction around holiday and description. Abstract function generates an optional
 adjustment for weekends in a given year.

 * [***DateInMonth***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/eventday/DateInMonth.java)
 <i>DateInMonth</i> exports Functionality that generates the specific Event Date inside of the specified
 Month/Year.

 * [***Locale***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/eventday/Locale.java)
 <i>Locale</i> contains the set of regular holidays and the weekend holidays for a location. It also provides
 the functionality to add custom holidays and weekends.

 * [***Static***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/eventday/Static.java)
 <i>Static</i> implements a complete date as a specific holiday.

 * [***Variable***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/eventday/Variable.java)
 <i>Variable</i> class contains the rule characterizing the variable holiday month, day in week, week in
 month, and the weekend days. Specific holidays in the given year are generated using these rules.

 * [***Weekend***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/eventday/Weekend.java)
 <i>Weekend</i> holds the left and the right weekend days. It provides functionality to retrieve them, check
 if the given day is a weekend, and serialize/de-serialize weekend days.


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
