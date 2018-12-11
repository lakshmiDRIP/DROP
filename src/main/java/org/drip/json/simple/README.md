# DROP JSON Simple Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP JSON Simple Package implements the RFC4627 Compliant JSON Message Object.


## Class Components

 * [***ItemList***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json/simple/ItemList.java)
 <i>ItemList</i> is an Adaptation of the ItemList Interface from the RFC4627 compliant JSON Simple (https://code.google.com/p/json-simple/).

 		|a:b:c| = |a|,|b|,|c|
 		|:| = ||,||
 		|a:| = |a|,||

 * [***JSONArray***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json/simple/JSONArray.java)
 <i>JSONArray</i> is an Adaptation of the JSONArray class from the RFC4627 compliant JSON Simple (https://code.google.com/p/json-simple/). A JSON array. JSONObject supports java.util.List interface.

 * [***JSONAware***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json/simple/JSONAware.java)
 <i>JSONAware</i> is an Adaptation of the JSONAware class from the RFC4627 compliant JSON Simple 
 (https://code.google.com/p/json-simple/). Beans that support customized output of JSON text shall implement
 this interface.

 * [***JSONObject***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json/simple/JSONObject.java)
 <i>JSONObject</i> is an Adaptation of the JSONObject Class from the RFC4627 compliant JSON Simple (https://code.google.com/p/json-simple/).

 * [***JSONStreamAware***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json/simple/JSONStreamAware.java)
 <i>JSONStreamAware</i> is an Adaptation of the JSONStreamAware class from the RFC4627 compliant JSON Simple
 (https://code.google.com/p/json-simple/). Beans that support customized output of JSON text to a writer
 shall implement this interface.

 * [***JSONValue***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json/simple/JSONValue.java)
 <i>JSONValue</i> is an Adaptation of the JSONValue Class from the RFC4627 compliant JSON Simple
 (https://code.google.com/p/json-simple/).


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
