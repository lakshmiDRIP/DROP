# DROP Dynamics LIBOR Market Model Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Dynamics LMM Package implements the LMM Based Latent State Evolution.


## Class Components

 * [***BGMCurveUpdate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/lmm/BGMCurveUpdate.java)
 <i>BGMCurveUpdate</i> contains the Instantaneous Snapshot of the Evolving Discount Curve Latent State
 Quantification Metrics Updated using the BGM LIBOR Update Dynamics.

 * [***BGMForwardTenorSnap***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/lmm/BGMForwardTenorSnap.java)
 <i>BGMForwardTenorSnap</i> contains the Absolute and the Incremental Latent State Quantifier Snapshot
 traced from the Evolution of the LIBOR Forward Rate as formulated in the References below.

 * [***BGMPointUpdate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/lmm/BGMPointUpdate.java)
 <i>BGMPointUpdate</i> contains the Instantaneous Snapshot of the Evolving Discount Point Latent State
 Quantification Metrics Updated using the BGM LIBOR Update Dynamics.

 * [***BGMTenorNodeSequence***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/lmm/BGMTenorNodeSequence.java)
 <i>BGMTenorNodeSequence</i> contains the Point Nodes of the Latent State Quantifiers and their Increments
 present in the specified BGMForwardTenorSnap Instance.

 * [***ContinuousForwardRateEvolver***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/lmm/ContinuousForwardRateEvolver.java)
 <i>ContinuousForwardRateEvolver</i> sets up and implements the Multi-Factor No-arbitrage Dynamics of the
 Rates State Quantifiers traced from the Evolution of the Continuously Compounded Forward Rate.

 * [***ContinuousForwardRateUpdate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/lmm/ContinuousForwardRateUpdate.java)
 <i>ContinuousForwardRateUpdate</i> contains the Instantaneous Snapshot of the Evolving Discount Latent State
 Quantification Metrics Updated using the Continuously Compounded Forward Rate Dynamics.

 * [***ContinuouslyCompoundedForwardProcess***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/lmm/ContinuouslyCompoundedForwardProcess.java)
 <i>ContinuouslyCompoundedForwardProcess</i> implements the Continuously Compounded Forward Rate Process
 defined in the LIBOR Market Model.

 * [***LognormalLIBORCurveEvolver***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/lmm/LognormalLIBORCurveEvolver.java)
 <i>LognormalLIBORCurveEvolver</i> sets up and implements the Multi-Factor No-arbitrage Dynamics of the full
 Curve Rates State Quantifiers traced from the Evolution of the LIBOR Forward Rate.

 * [***LognormalLIBORPointEvolver***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/lmm/LognormalLIBORPointEvolver.java)
 <i>LognormalLIBORPointEvolver</i> sets up and implements the Multi-Factor No-arbitrage Dynamics of the Point
 Rates State Quantifiers traced from the Evolution of the LIBOR Forward Rate.

 * [***LognormalLIBORVolatility***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/lmm/LognormalLIBORVolatility.java)
 <i>LognormalLIBORVolatility</i> implements the Multi-Factor Log-normal LIBOR Volatility.

 * [***PathwiseQMRealization***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/lmm/PathwiseQMRealization.java)
 <i>PathwiseQMRealization</i> contains the Sequence of the Simulated Target Point State QM Realizations and
 their corresponding Date Nodes. The formulations for the case of the Forward Rates are in the References
 below.

 * [***ShortRateProcess***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/lmm/ShortRateProcess.java)
 <i>ShortRateProcess</i> implements the Short Rate Process defined in the LIBOR Market Model.


## References

 * Brace, A., D. Gatarek, and M. Musiela (1997): The Market Model of Interest Rate Dynamics <i>Mathematical
 	Finance</i> <b>7 (2)</b> 127-155

 * Goldys, B., M. Musiela, and D. Sondermann (1994): <i>Log-normality of Rates and Term Structure Models</i>
 	<b>The University of New South Wales</b>

 * Musiela, M. (1994): <i>Nominal Annual Rates and Log-normal Volatility Structure</i> <b>The University of
 	New South Wales</b>


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
