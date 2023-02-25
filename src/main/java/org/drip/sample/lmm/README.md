# DROP Sample LMM Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Sample LMM illustrates the LMM Multi-Factor Monte Carlo Functionality.


## Class Components

 * [***ContinuousForwardRateVolatility***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/lmm/ContinuousForwardRateVolatility.java)
 <i>ContinuousForwardRateVolatility</i> demonstrates the Implying of the Volatility of the Continuously
 Compounded Forward Rate from the Corresponding LIBOR Forward Rate Volatility.

 * [***FixFloatMonteCarloEvolver***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/lmm/FixFloatMonteCarloEvolver.java)
 <i>FixFloatMonteCarloEvolver</i> demonstrates the steps associated with a LMM-Based Monte-Carlo pricing of a
 Standard Fix-Float Swap.

 * [***MultiFactorCurveDynamics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/lmm/MultiFactorCurveDynamics.java)
 <i>MultiFactorCurveDynamics</i> demonstrates the Construction and Usage of the Curve LIBOR State Evolver,
 and the eventual Evolution of the related Discount/Forward Latent State Quantification Metrics.

 * [***MultiFactorLIBORCurveEvolver***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/lmm/MultiFactorLIBORCurveEvolver.java)
 <i>MultiFactorLIBORCurveEvolver</i> demonstrates the Evolution Sequence of the full LIBOR Forward Curve.

 * [***MultiFactorLIBORMonteCarlo***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/lmm/MultiFactorLIBORMonteCarlo.java)
 <i>MultiFactorLIBORMonteCarlo</i> demonstrates the Monte-Carlo Evolution Sequence of the LIBOR Forward
 Curve.

 * [***PointAncillaryMetricsDynamics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/lmm/PointAncillaryMetricsDynamics.java)
 <i>PointAncillaryMetricsDynamics</i> demonstrates the Construction and Usage of the Point LIBOR State
 Evolver, and the eventual Evolution of the related Ancillary bDiscount/Forward Latent State Quantification
 Metrics.

 * [***PointCoreMetricsDynamics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/lmm/PointCoreMetricsDynamics.java)
 <i>PointCoreMetricsDynamics</i> demonstrates the Construction and Usage of the Point LIBOR State Evolver,
 and the eventual Evolution of the related Core bDiscount/Forward Latent State Quantification Metrics.

 * [***TwoFactorLIBORVolatility***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/lmm/TwoFactorLIBORVolatility.java)
 <i>TwoFactorLIBORVolatility</i> demonstrates the Construction and Usage of the 2 Factor LIBOR Forward Rate
 Volatility.


## References

 * Goldys, B., M. Musiela, and D. Sondermann (1994): Log-normality of Rates and Term Structure Models, The
 	University of New South Wales.

 * Musiela, M. (1994): Nominal Annual Rates and Log-normal Volatility Structure, The University of New South
 	Wales.

 * Brace, A., D. Gatarek, and M. Musiela (1997): The Market Model of Interest Rate Dynamics, Mathematical
 	Finance 7 (2), 127-155.


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
