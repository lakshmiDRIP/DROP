# DROP Feed Loader Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Feed Loader Package contains the Reference/Market Data Feed Loader.


## Class Components

 * [***BondRefData***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/loader/BondRefData.java)
 <i>BondRefData</i> contains functionality to load a variety of Bond Product reference data and closing
 marks. It exposes the following functionality:
 	* Load the bond valuation-based reference data, amortization schedule and EOS
 	* Build the bond instance entities from the valuation-based reference data
 	* Load the bond non-valuation-based reference data

 * [***CDXRefData***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/loader/CDXRefData.java)
 <i>CDXRefData</i> contains the functionality to load the standard CDX reference data and definitions, and
 create compile time static classes for these definitions.

 * [***CreditStaticAndMarks***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/loader/CreditStaticAndMarks.java)
 <i>CreditStaticAndMarks</i> contains functionality to load a variety of Credit and Rates Product reference
 data and closing marks. It exposes the following functionality:
 	* Load the bond reference data, static data, amortization schedule and EOS
 	* Build the bond instance entities from the reference data
 	* Load the bond, CDS, and Rates product Closing Marks
 	* Load and build the Holiday Calendars

 * [***CSVGrid***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/loader/CSVGrid.java)
 <i>CSVGrid</i> Holds the Outputs of a CSV Parsing Exercise.

 * [***CSVParser***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/loader/CSVParser.java)
 <i>CSVParser</i> Parses the Lines of a Comma Separated File into appropriate Data Types.

 * [***InstrumentSetTenorQuote***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/loader/InstrumentSetTenorQuote.java)
 <i>InstrumentSetTenorQuote</i> holds the Instrument Set Tenor and Closing Quote Group.

 * [***PropertiesParser***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/loader/PropertiesParser.java)
 <i>PropertiesParser</i> contains the functionality to load the Field/Value Sets from the Field=Value Format.

 * [***TenorQuote***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/loader/TenorQuote.java)
 <i>TenorQuote</i> holds the Instrument Tenor and Closing Quote.


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
