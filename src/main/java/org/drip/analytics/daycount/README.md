# DROP Analytics Day Count Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Analytics Day Count Package implements Date/Time Creation/Manipulation/Usage Functionality.


## Class Components

 * [***Act/Act DC Params***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/ActActDCParams.java)
 <i>ActActDCParams</i> contains parameters to represent Act/Act day count. It exports the following
 functionality:
 	* Frequency/Start/End Date Fields
 	* Serialization/De-serialization to and from Byte Arrays

 * [***Convention***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/Convention.java)
 <i>Convention</i> contains flags that indicate where the holidays are loaded from, as well as the holiday
 types and load rules. It exports the following date related functionality:
 	* Add business days according to the specified calendar</li>
 	* The Year Fraction between any 2 days given the day count type and the holiday calendar</li>
 	* Adjust/roll to the next working day according to the adjustment rule</li>
 	* Holiday Functions - is the given day a holiday/business day, the number and the set of
 		holidays/business days between 2 days.
 	* Calendars and Day counts - Available set of day count conventions and calendars, and the weekend days
 		corresponding to a given calendar.

 * [***Date Adjust Params***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DateAdjustParams.java)
 <i>DateAdjustParams</i> class contains the parameters needed for adjusting dates. It exports the following
 functionality:
 	* Accessor for holiday calendar and adjustment type
 	* Serialization/De-serialization to and from Byte Arrays

 * [***Date EOM Adjustment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DateEOMAdjustment.java)
 <i>DateEOMAdjustment</i> holds the applicable adjustments for a given date pair. It exposes the following
 functionality:
 	* Static Methods for creating 30/360, 30/365, and EOMA Date Adjustments
 	* Export Anterior and Posterior EOM Adjustments

 * [***DC1_1***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DC1_1.java)
 <i>DC1_1</i> implements the 1/1 day count convention.

 * [***DC28_360***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DC28_360.java)
 <i>DC28_360</i> implements the 28/360 day count convention.

 * [***DC30_360***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DC30_360.java)
 <i>DC30_360</i> implements the 30/360 day count convention.

 * [***DC30_365***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DC30_365.java)
 <i>DC30_365</i> implements the 30/365 day count convention.

 * [***DC30_Act***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DC30_Act.java)
 <i>DC30_Act</i> implements the 30/365 day count convention.

 * [***DC30E_360_ISDA***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DC30E_360_ISDA.java)
 <i>DC30E_360_ISDA</i> implements the 30E/360 ISDA day count convention.

 * [***DC30E_360***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DC30E_360.java)
 <i>DC30E_360</i> implements the 30E/360 day count convention.

 * [***DC30EPLUS_360_ISDA***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DC30EPLUS_360_ISDA.java)
 <i>DC30EPLUS_360_ISDA</i> implements the 30E+/360 ISDA day count convention.

 * [***DCAct_360***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DCAct_360.java)
 <i>DCAct_360</i> implements the Act/360 day count convention.

 * [***DCAct_364***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DCAct_364.java)
 <i>DCAct_364</i> implements the Act/364 day count convention.

 * [***DCAct_365***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DCAct_365.java)
 <i>DCAct_365</i> implements the Act/360 day count convention.

 * [***DCAct_365L***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DCAct_365L.java)
 <i>DCAct_365L</i> implements the Act/365L day count convention.

 * [***DCAct_Act_ISDA***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DCAct_Act_ISDA.java)
 <i>DCAct_Act_ISDA</i> implements the ISDA Act/Act day count convention.

 * [***DCAct_Act_UST***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DCAct_Act_UST.java)
 <i>DCAct_Act_UST</i> implements the US Treasury Bond Act/Act UST Day Count Convention.

 * [***DCAct_Act***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DCAct_Act.java)
 <i>DCAct_Act</i> implements the Act/Act day count convention.

 * [***DCFCalculator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DCFCalculator.java)
 <i>DCFCalculator</i> is the stub for all the day count convention functionality. It exposes the
 base/alternate day count convention names, the year-fraction and the days accrued.

 * [***DCNL_360***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DCNL_360.java)
 <i>DCNL_360</i> implements the NL/360 day count convention.

 * [***DCNL_365***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DCNL_365.java)
 <i>DCNL_365</i> implements the NL/365 day count convention.

 * [***DCNL_Act***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/DCNL_Act.java)
 <i>DCNL_Act</i> implements the NL/Act day count convention.


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
