# DROP XVA Definition Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP XVA Definition Package holds the XVA Definition - Close Out, Universe.


## Class Components

 * [***CloseOut***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/definition/CloseOut.java)
 <i>CloseOut</i> exposes the General Close Out Amounts to be applied to the MTM Exposure at the
 Dealer/Client Default.

 * [***CloseOutBilateral***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/definition/CloseOutBilateral.java)
 <i>CloseOutBilateral</i> implements the (2002) ISDA Master Agreement Bilateral Close Out Scheme to be
 applied to the MTM at the Dealer/Client Default.

 * [***PDEEvolutionControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/definition/PDEEvolutionControl.java)
 <i>PDEEvolutionControl</i> is used to Customize the XVA Estimation using PDE Evolution, e.g., determine the
 MTM Mechanism that determines the actual Termination Close Out, as laid out in Burgard and Kjaer (2014).

 * [***SimpleBalanceSheet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/definition/SimpleBalanceSheet.java)
 <i>SimpleBalanceSheet</i> implements a Simple Dealer Balance Sheet Model as specified in Burgard and Kjaer
 (2012).


## References

 * Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs <i>Risk</i> <b>24 (12)</b> 82-87

 * Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk and
 	Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19

 * Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): <i>Modeling, Pricing,
 	and Hedging Counter-party Credit Exposure - A Technical Guide</i> <b>Springer Finance</b> New York

 * Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b> 86-90

 * Li, B., and Y. Tang (2007): <i>Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 	Presence of Counter-party Credit Risk for the Fixed Income Market</i> <b>World Scientific Publishing</b>
 	Singapore

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
