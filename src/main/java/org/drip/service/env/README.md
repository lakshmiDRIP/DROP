# DROP Service Env Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Service Env Package holds the Library Module Loader and Environment Manager.


## Class Components

 * [***BuildManager***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/env/BuildManager.java)
 <i>BuildManager</i> maintains a Log of the Build Records.

 * [***BuildRecord***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/env/BuildRecord.java)
 <i>BuildRecord</i> records the Build Log - DROP Version, Java Version, and Build Time Stamp.

 * [***CacheManager***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/env/CacheManager.java)
 <i>CacheManager</i> implements the DRIP Cache Management Functionality, and contains the Functions to Add,
 Delete, Retrieve, and Time out a Key-Value Pair along the lines of memcached.

 * [***EnvManager***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/env/EnvManager.java)
 <i>EnvManager</i> sets the environment/connection parameters, and populates the market parameters for the
 given EOD.

 * [***InvocationManager***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/env/InvocationManager.java)
 <i>InvocationManager</i> records the manages the Build/Execution Environment of an Invocation.

 * [***InvocationRecord***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/env/InvocationRecord.java)
 <i>InvocationRecord</i> implements the Invocation Start/Finish Times of a given Invocation.

 * [***StandardCDXManager***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/env/StandardCDXManager.java)
 <i>StandardCDXManager</i> implements the creation and the static details of the all the NA, EU, SovX, EMEA,
 and ASIA standardized CDS indices. It exposes the following functionality:
 	* Retrieve the full set of pre-set/pre-loaded CDX names/descriptions.
 	* Retrieve all the CDX's given an index name.
 	* Get the index, index series, and the effective/maturity dates for a given CDX.
 	* Get all the on-the-runs for an index, date, and tenor.
 	* Retrieve the full basket product corresponding to NA/EU/ASIA IG/HY/EM and other available standard CDX.
 	* Build a custom CDX product.


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
