# DROP Exposure Generator Time-line Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Exposure Generator Package generates the Rates Stream Margin Period Exposure.

## Class Components

 * [***FixedStreamMPoR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/generator/FixedStreamMPoR.java)
 <i>FixedStreamMPoR</i> estimates the MPoR Variation Margin and the Trade Payments for the given Fixed Coupon
 Stream off of the Realized Market Path.

 * [***FixFloatMPoR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/generator/FixFloatMPoR.java)
 <i>FixFloatMPoR</i> estimates the MPoR Variation Margin and the Trade Payments for the given Fix Float
 Component off of the Realized Market Path.

 * [***FloatStreamMPoR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/generator/FloatStreamMPoR.java)
 <i>FloatStreamMPoR</i> estimates the MPoR Variation Margin and the Trade Payments for the given Float Stream
 off of the Realized Market Path.

 * [***NumeraireMPoR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/generator/NumeraireMPoR.java)
 <i>NumeraireMPoR</i> estimates the MPoR Variation Margin and the Trade Payments for the generic Numeraire
 off of the Realized Market Path.

 * [***PortfolioMPoR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/generator/PortfolioMPoR.java)
 <i>PortfolioMPoR</i> estimates the MPoR Variation Margin and the Trade Payments for the Component MPoRs of a
 given Portfolio off of the Realized Market Path.

 * [***StreamMPoR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/generator/StreamMPoR.java)
 <i>StreamMPoR</i> estimates the MPoR Variation Margin and the Trade Payments for the generic Stream off of
 the Realized Market Path.


# References

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>

 * Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the Re-
 Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955 <b>eSSRN</b>

 * Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>

 * Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 <i>Risk</i> <b>21 (2)</b> 97-102

 * Pykhtin, M. (2009): Modeling Counter-party Credit Exposure in the Presence of Margin Agreements
 http://www.risk-europe.com/protected/michael-pykhtin.pdf


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
