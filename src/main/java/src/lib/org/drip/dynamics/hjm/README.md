# DROP Dynamics HJM Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Dynamics HJM Package implements the HJM Based Latent State Evolution.


## Class Components

 * [***G2++***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/hjm/G2PlusPlus.java)
 <i>G2PlusPlus</i> provides the Hull-White-type, but 2F Gaussian HJM Short Rate Dynamics Implementation.

 * [***Multi-Factor State Evolver***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/hjm/MultiFactorStateEvolver.java)
 <i>MultiFactorStateEvolver</i> sets up and implements the Base Multi-Factor No-arbitrage Dynamics of the
 Rates State Quantifiers as formulated in Heath, Jarrow, and Morton (1992).

 * [***Multi-Factor Volatility***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/hjm/MultiFactorVolatility.java)
 <i>MultiFactorVolatility</i> implements the Volatility of the Multi-factor Stochastic Evolution Process. The
 Factors may come from the Underlying Stochastic Variables, or from Principal Components.

 * [***Short/Forward Rate Update***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/hjm/ShortForwardRateUpdate.java)
 <i>ShortForwardRateUpdate</i> contains the Instantaneous Snapshot of the Evolving Discount Latent State
 Quantification Metrics.


## References

 * Heath, D., R. Jarrow, and A. Morton (1992): Bond Pricing and Term Structure of Interest Rates: A New
 Methodology for Contingent Claims Valuation <i>Econometrica</i> <b>60 (1)</b> 77-105


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
