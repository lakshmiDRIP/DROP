# DROP Exposure CSA Time-line Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Exposure CSA Time-line Package implements the Time-line of IMA/CSA Event Dates.

## Class Components

 * [***Andersen Pykhtin Sokol Lag***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/csatimeline/AndersenPykhtinSokolLag.java)
 <i>AndersenPykhtinSokolLag</i> holds the Client/Dealer Margin Flow and Trade Flow Lags using the
 Parameterization laid out in Andersen, Pykhtin, and Sokol (2017).

 * [***Event Date***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/csatimeline/EventDate.java)
 <i>EventDate</i> holds a specific Date composing BCBS/IOSCO prescribed Events Time-line occurring Margin
 Period.

 * [***Event Date Builder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/csatimeline/EventDateBuilder.java)
 <i>EventDateBuilder</i> builds the CSA BCBS/IOSCO Dates prescribed Events Time-line occurring Margin Period.

 * [***Event Sequence***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/csatimeline/EventSequence.java)
 <i>EventSequence</i> holds the BCBS/IOSCO prescribed Events Time-line occurring Margin Period.

 * [***Last Flow Dates***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/csatimeline/LastFlowDates.java)
 <i>LastFlowDates</i> holds the Last Client/Dealer Margin Flow and Trade Flow Dates using the
 Parameterization laid out in Andersen, Pykhtin, and Sokol (2017).


# References

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>

 * Anfuso, F., D. Aziz, P. Giltinan, and K Loukopoulus (2017): A Sound Modeling and Back-testing Framework
 for Forecasting Initial Margin Requirements
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>

 * BCBS (2015): Margin Requirements for Non-centrally Cleared Derivatives
 https://www.bis.org/bcbs/publ/d317.pdf

 * Pykhtin, M. (2009): Modeling Credit Exposure for Collateralized Counter-parties <i>Journal of Credit
 Risk</i> <b>5 (4)</b> 3-27


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
