# DROP Exposure Evolver Time-line Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Exposure Evolver Package holds the Securities and Exposure States Evolvers.

## Class Components

 * [***DynamicsContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/evolver/DynamicsContainer.java)
 <i>DynamicsContainer</i> holds the Dynamics of the Economy with the following Traded Assets - the Numeraire
 Evolver Dynamics, the Terminal Latent State Evolver Dynamics, and the Primary Security Evolver Dynamics.

 * [***EntityDynamicsContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/evolver/EntityDynamicsContainer.java)
 <i>EntityDynamicsContainer</i> contains the Dealer and the Client Hazard and Recovery Latent State Evolvers.

 * [***Equity***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/evolver/Equity.java)
 <i>Equity</i> describes a Tradeable Equity.

 * [***LatentStateDynamicsContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/evolver/LatentStateDynamicsContainer.java)
 <i>LatentStateDynamicsContainer</i> holds the Latent State Labels for a variety of Latent States and their
 Evolvers.

 * [***LatentStateVertexContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/evolver/LatentStateVertexContainer.java)
 <i>LatentStateVertexContainer</i> holds the Latent State Labels and their corresponding Vertex Realizations.

 * [***PrimarySecurity***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/evolver/PrimarySecurity.java)
 <i>PrimarySecurity</i> holds Definitions and Parameters that specify a Primary Security in XVA Terms.

 * [***PrimarySecurityDynamicsContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/evolver/PrimarySecurityDynamicsContainer.java)
 <i>PrimarySecurityDynamicsContainer</i> holds the Economy with the following Traded Assets - the Overnight
 Index Numeraire, the Collateral Scheme Numeraire, the Default-able Dealer Bond Numeraire, the Array of
 Default-able Client Numeraires, and an Asset that follows Brownian Motion.

 * [***ScalingNumeraire***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/evolver/ScalingNumeraire.java)
 <i>ScalingNumeraire</i> holds Parameters that guide the Diffusion of a Scaling Numeraire.

 * [***TerminalLatentState***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/evolver/TerminalLatentState.java)
 <i>TerminalLatentState</i> contains the Latent State Label and the corresponding Terminal Diffusion Evolver.


# References

 * Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the Re-
 Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955 <b>eSSRN</b>

 * Albanese, C., L. Andersen, and, S. Iabichino (2015): The FVA Puzzle: Accounting, Risk Management, and
 Collateral Trading https://papers.ssrn.com/sol3/paper.cfm?abstract_id_2517301 <b>eSSRN</b>

 * Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies <i>Risk</i> <b>23 (12)</b> 82-87

 * Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75

 * Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk and
 	Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19

 * Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>

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
