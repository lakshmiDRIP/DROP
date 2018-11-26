# DROP Exposure MPoR Time-line Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Exposure MPoR Package computes the Margin Period Collateral Amount Estimates.

## Class Components

 * [***CollateralAmountEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/mpor/CollateralAmountEstimator.java)
 <i>CollateralAmountEstimator</i> estimates the Amount of Collateral Hypothecation that is to be Posted
 during a Single Run of a Collateral Hypothecation Group Valuation.

 * [***CollateralAmountEstimatorOutput***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/mpor/CollateralAmountEstimatorOutput.java)
 <i>CollateralAmountEstimatorOutput</i> contains the Estimation Output of the Hypothecation Collateral that
 is to be Posted during a Single Run of a Collateral Hypothecation Group Valuation.

 * [***MarginPeriodOfRisk***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/mpor/MarginPeriodOfRisk.java)
 <i>MarginPeriodOfRisk</i> contains the Margining Information associated with the Client Exposure.

 * [***PathVariationMarginTrajectoryEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/mpor/PathVariationMarginTrajectoryEstimator.java)
 <i>PathVariationMarginTrajectoryEstimator</i> computes the Variation Margin Estimate/Posting from the
 specified Dense Uncollateralized Exposures and Trade Payments along the specified Path Trajectory.

 * [***TradePayment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/mpor/TradePayment.java)
 <i>TradePayment</i> holds the Dealer (Negative) and Client (Positive) Trade Payments at an Exposure Date.

 * [***VariationMarginTradePaymentVertex***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/mpor/VariationMarginTradePaymentVertex.java)
 <i>VariationMarginTradePaymentVertex</i> exposes the Generation of the Estimated Variation Margin and the
 Trade Payment at a Vertex off of the Realized Market Path.

 * [***VariationMarginTradeVertexExposure***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/mpor/VariationMarginTradeVertexExposure.java)
 <i>VariationMarginTradeVertexExposure</i> holds the Variation Margin, Trade Payments, and Exposures for a
 specific Forward Vertex Date.

 * [***VariationMarginTrajectoryBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/mpor/VariationMarginTrajectoryBuilder.java)
 <i>VariationMarginTrajectoryBuilder</i> builds the Variation Margin Trajectory using several Techniques.


# References

 * Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies <i>Risk</i> <b>23 (12)</b> 82-87

 * Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75

 * Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk and
 Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19

 * Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>

 * Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b> 86-90

 * Li, B., and Y. Tang (2007): Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 Presence of Counter-party Credit Risk for the Fixed Income Market <i>World Scientific Publishing </i>
 <b>Singapore</b>

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
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
