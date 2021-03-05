# DROP Exposure Holdings Time-line Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Exposure Holdings Package generates the Holdings Exposure - Position and Dependencies.

## Class Components

 * [***FixFloatBaselPositionEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/holdings/FixFloatBaselPositionEstimator.java)
 <i>FixFloatBaselPositionEstimator</i> evaluates the Value of a Fix Float Position Group given the Realized
 Market Path using the Basel Scheme.

 * [***PositionGroup***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/holdings/PositionGroup.java)
 <i>PositionGroup</i> holds the Settings that correspond to a Position/Collateral Group.

 * [***PositionGroupContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/holdings/PositionGroupContainer.java)
 <i>PositionGroupContainer</i> contains a Set of Position/Collateral Groups.

 * [***PositionGroupEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/holdings/PositionGroupEstimator.java)
 <i>PositionGroupEstimator</i> evaluates the Value of the Position Group given the Realized Market Path.

 * [***PositionGroupSegment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/holdings/PositionGroupSegment.java)
 <i>PositionGroupSegment</i> contains one Segment of a Position/Collateral Group.


# References

 * Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies <i>Risk</i> <b>23 (12)</b> 82-87

 * Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75

 * Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk and
 Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19

 * Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>

 * Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 <i>Risk</i> <b>21 (2)</b> 97-102


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
