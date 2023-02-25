# DROP Analytics Date Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Analytics Date Package implements Date/Time Creation/Manipulation/Usage Functionality.

## Class Components

 * [***Date Time***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/date/DateTime.java)
 <i>DateTime</i> provides the representation of the instantiation-time date and time objects. It provides the
 following functionality:
 	* Instantiation-time and Explicit Date/Time Construction
 	* Retrieval of Date/Time Fields
 	* Serialization/De-serialization to and from Byte Arrays

 * [***Date Util***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/date/DateUtil.java)
 <i>DateUtil</i> contains Various Utilities for manipulating Date. The Julian Date - Gregorian Date Inter
 Conversion follows the References below.

 * [***Julian Date***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/date/JulianDate.java)
 <i>JulianDate</i> provides a comprehensive representation of Julian date and date manipulation
 functionality. It exports the following functionality:
 	* Explicit date construction, as well as date construction from several input string formats/today
 	* Date Addition/Adjustment/Elapsed/Difference, add/subtract days/weeks/months/years and tenor codes
 	* Leap Year Functionality (number of leap days in the given interval, is the given year a leap year etc.)
 	* Generate the subsequent IMM date (CME IMM date, CDS/Credit ISDA IMM date etc)
 	* Year/Month/Day in numbers/characters
 	* Days Elapsed/Remaining, is EOM
 	* Comparison with the Other, equals/hash-code/comparator
 	* Export the date to a variety of date formats (Oracle, Julian, Bloomberg)
 	* Serialization/De-serialization to and from Byte Arrays


## References

 * Fliegel, H. F., and T. C. van Flandern (1968): A Machine Algorithm for Processing Calendar Dates
 	<i>Communications of the ACM</i> <b>11</b> 657

 * Fenton, D. (2001): Julian to Calendar Date Conversion http://mathforum.org/library/drmath/view/51907.html


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
