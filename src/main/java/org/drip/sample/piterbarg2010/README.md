# DROP Piterbarg (2010) Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Piterbarg (2010) demonstrates the Piterbarg (2010) CSA Measure Extraction.


## Class Components

 * [***CSAFundingAbsoluteForward***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/piterbarg2010/CSAFundingAbsoluteForward.java)
 <i>CSAFundingAbsoluteForward</i> compares the Absolute Differences between the CSA and the non-CSA Forward LIBOR under a Stochastic Funding Model.

 * [***CSAFundingRelativeForward***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/piterbarg2010/CSAFundingRelativeForward.java)
 <i>CSAFundingRelativeForward</i> compares the Relative Differences between the CSA and the non-CSA Forward Prices under a Stochastic Funding Model.

 * [***CSAImpliedMeasureDifference***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/piterbarg2010/CSAImpliedMeasureDifference.java)
 <i>CSAImpliedMeasureDifference</i> compares the Differences between the CSA and the non-CSA Implied Distribution, expressed in Implied Volatilities across Strikes, and across Correlations.

 * [***ForwardContract***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/piterbarg2010/ForwardContract.java)
 <i>ForwardContract</i> examines the Valuation of Forward Contract under CSA and non-CSA Settle Agreements. CSA is proxied using the OIS Curve, and non-CSA using the Issuer Hedge Funding Curve. The corresponding Convexity Adjustments using Spread/CSA Co-variance are also calculated.

 * [***ZeroStrikeCallOption***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/piterbarg2010/ZeroStrikeCallOption.java)
 <i>ZeroStrikeCallOption</i> examines the Impact of Funding and Collateralization on a "Zero Strike Call", i.e., the Futures Contract on an Asset with Non-Zero Value.


## Class Components

 * Barden, P. (2009): Equity Forward Prices in the Presence of Funding Spreads <i>ICBI Conference</i> <b>Rome</b>

 * Burgard, C., and M. Kjaer (2009): Modeling and successful Management of Credit Counter-party Risk of Derivative Portfolios <i>ICBI Conference</i> <b>Rome</b>

 * Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b> 86-90

 * Johannes, M., and S. Sundaresan (2007): Pricing Collateralized Swaps <i>Journal of Finance</i> <b>62</b> 383-410

 * Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing <i>Risk</i> <b>21 (2)</b> 97-102


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
