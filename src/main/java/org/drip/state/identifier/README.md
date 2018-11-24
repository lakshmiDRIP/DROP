# DROP State Identifier Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP State Identifier Package contains the Latent State Identifier Labels.


## Class Components

 * [***CollateralLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/CollateralLabel.java)
 <i>CollateralLabel</i> contains the Identifier Parameters referencing the Latent State of the named
 Collateral Discount Curve. Currently it only contains the collateral currency.

 * [***CSALabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/CSALabel.java)
 <i>CSALabel</i> specifies the Label of of a Credit Support Annex (CSA) Specification.

 * [***CustomLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/CustomLabel.java)
 <i>CustomLabel</i> contains the Identifier Parameters referencing the Latent State of the named Custom
 Metric. Currently it only contains the Arbitrarily Assigned Label.

 * [***EntityCDSLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/EntityCDSLabel.java)
 <i>EntityCDSLabel</i> contains the Identifier Parameters referencing the Latent State of the named Entity
 CDS Curve.

 * [***EntityCreditLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/EntityCreditLabel.java)
 <i>EntityCreditLabel</i> contains the Identifier Parameters referencing the Latent State of the named Entity
 Credit Curve.

 * [***EntityDesignateLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/EntityDesignateLabel.java)
 <i>EntityDesignateLabel</i> contains the Identifier Parameters referencing the Latent State of an Entity
 Designate.

 * [***EntityEquityLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/EntityEquityLabel.java)
 <i>EntityEquityLabel</i> contains the Identifier Parameters referencing the Latent State of the Entity
 Equity Curve.

 * [***EntityFundingLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/EntityFundingLabel.java)
 <i>EntityFundingLabel</i> contains the Identifier Parameters referencing the Latent State of the Entity
 Funding Curve.

 * [***EntityHazardLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/EntityHazardLabel.java)
 <i>EntityHazardLabel</i> contains the Identifier Parameters referencing the Latent State of the Entity
 Hazard Curve.

 * [***EntityRecoveryLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/EntityRecoveryLabel.java)
 <i>EntityRecoveryLabel</i> contains the Identifier Parameters referencing the Latent State of the Entity
 Recovery Curve.

 * [***FloaterLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/FloaterLabel.java)
 <i>FloaterLabel</i> is an Abstract Class that underpins the Latent State Labels that use a Single Floater
 Index.

 * [***ForwardLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/ForwardLabel.java)
 <i>ForwardLabel</i> contains the Index Parameters referencing a payment on a Forward Index. It provides the
 following functionality:
 	* Indicate if the Index is an Overnight Index
 	* Retrieve Index, Tenor, Currency, and Fully Qualified Name

 * [***FundingLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/FundingLabel.java)
 <i>FundingLabel</i> contains the Identifier Parameters referencing the Latent State of the named Funding
 Discount Curve. Currently it only contains the funding currency.

 * [***FXLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/FXLabel.java)
 <i>FXLabel</i> contains the Identifier Parameters referencing the Latent State of the named FX Curve.
 Currently it only contains the FX Code.

 * [***GovvieLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/GovvieLabel.java)
 <i>GovvieLabel</i> contains the Identifier Parameters referencing the Latent State of the named Sovereign
 Curve. Currently it only contains the Sovereign Name.

 * [***LatentStateLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/LatentStateLabel.java)
 <i>LatentStateLabel</i> is the interface that contains the labels inside the sub-stretch of the alternate
 state. The functionality its derivations implement provide fully qualified label names and their matches.

 * [***OTCFixFloatLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/OTCFixFloatLabel.java)
 <i>OTCFixFloatLabel</i> contains the Index Parameters referencing a Payment on an OTC Fix/Float IRS Par Rate
 Index.

 * [***OvernightLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/OvernightLabel.java)
 <i>OvernightLabel</i> contains the Index Parameters referencing an Overnight Index. It provides the
 functionality to Retrieve Index, Tenor, Currency, and Fully Qualified Name.

 * [***PaydownLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/PaydownLabel.java)
 <i>PaydownLabel</i> contains the Identifier Parameters referencing the Latent State of the named Paydown
 Curve. Currently it only contains the Reference Entity Name.

 * [***RatingLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/RatingLabel.java)
 <i>RatingLabel</i> contains the Identifier Parameters referencing the Label corresponding to the Credit
 Rating Latent State. Currently it holds the Ratings Agency Name and the Rated Code.

 * [***RepoLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/RepoLabel.java)
 <i>RepoLabel</i> contains the Identifier Parameters referencing the Latent State of the named Repo Curve.
 It holds the Name of the Repoable Product.

 * [***VolatilityLabel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/VolatilityLabel.java)
 <i>VolatilityLabel</i> contains the Identifier Parameters referencing the Latent State of the named
 Volatility Curve. Currently it only contains the label of the underlying Latent State.


## References

 *  Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter Party Risk
 	and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19

 * Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): Modeling, Pricing, and
 	Hedging Counter-party Credit Exposure - A Technical Guide <i>Springer Finance</i> <b>New York</b>

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
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
