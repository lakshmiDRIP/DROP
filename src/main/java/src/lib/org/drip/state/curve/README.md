# DROP State Curve Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP State Curve Package implements Basis Spline Based Latent States.


## Class Components

 * [***BasisSplineBasisCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve/BasisSplineBasisCurve.java)
 <i>BasisSplineBasisCurve</i> manages the Basis Latent State, using the Basis as the State Response
 Representation. It exports the following functionality:
 	* Calculate implied forward rate / implied forward rate Jacobian

 * [***BasisSplineDeterministicVolatility***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve/BasisSplineDeterministicVolatility.java)
 <i>BasisSplineDeterministicVolatility</i> extends the BasisSplineTermStructure for the specific case of the
 Implementation of the Deterministic Volatility Term Structure.

 * [***BasisSplineForwardRate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve/BasisSplineForwardRate.java)
 <i>BasisSplineForwardRate</i> manages the Forward Latent State, using the Forward Rate as the State Response
 Representation. It exports the following functionality:
 	* Calculate implied forward rate / implied forward rate Jacobian
 	* Serialize into and de-serialize out of byte arrays

 * [***BasisSplineFXForward***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve/BasisSplineFXForward.java)
 <i>BasisSplineFXForward</i> manages the Basis Latent State, using the Basis as the State Response
 Representation.

 * [***BasisSplineGovvieYield***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve/BasisSplineGovvieYield.java)
 <i>BasisSplineGovvieYield</i> manages the Basis Spline Latent State, using the Basis as the State Response
 Representation, for the Govvie Curve with Yield Quantification Metric.

 * [***BasisSplineMarketSurface***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve/BasisSplineMarketSurface.java)
 <i>BasisSplineMarketSurface</i> implements the Market surface that holds the latent state Dynamics
 parameters.

 * [***BasisSplineRepoCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve/BasisSplineRepoCurve.java)
 <i>BasisSplineRepoCurve</i> manages the Basis Latent State, using the Repo as the State Response
 Representation.

 * [***BasisSplineTermStructure***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve/BasisSplineTermStructure.java)
 <i>BasisSplineTermStructure</i> implements the TermStructure Interface - if holds the latent states Term
 Structure Parameters.

 * [***DerivedZeroRate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve/DerivedZeroRate.java)
 <i>DerivedZeroRate</i> implements the delegated ZeroCurve functionality. Beyond discount factor/zero rate
 computation at specific cash pay nodes, all other functions are delegated to the embedded discount curve.

 * [***DeterministicCollateralChoiceDiscountCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve/DeterministicCollateralChoiceDiscountCurve.java)
 <i>DeterministicCollateralChoiceDiscountCurve</i> implements the Dynamically Switchable Collateral Choice
 Discount Curve among the choice of provided "deterministic" collateral curves.

 * [***DiscountFactorDiscountCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve/DiscountFactorDiscountCurve.java)
 <i>DiscountFactorDiscountCurve</i> manages the Discounting Latent State, using the Discount Factor as the
 State Response Representation. It exports the following functionality:
 	* Compute the discount factor, forward rate, or the zero rate from the Discount Factor Latent State
 	* Create a ForwardRateEstimator instance for the given Index
 	* Retrieve Array of the Calibration Components
 	* Retrieve the Curve Construction Input Set
 	* Compute the Jacobian of the Discount Factor Latent State to the input Quote
 	* Synthesize scenario Latent State by parallel shifting/custom tweaking the quantification metric
 	* Synthesize scenario Latent State by parallel/custom shifting/custom tweaking the manifest measure
 	* Serialize into and de serialize out of byte array

 * [***ForeignCollateralizedDiscountCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve/ForeignCollateralizedDiscountCurve.java)
 <i>ForeignCollateralizedDiscountCurve</i> computes the discount factor corresponding to one unit of domestic
 currency collateralized by a foreign collateral.

 * [***ZeroRateDiscountCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve/ZeroRateDiscountCurve.java)
 <i>ZeroRateDiscountCurve</i> manages the Discounting Latent State, using the Zero Rate as the State Response
 Representation. It exports the following functionality:
 	* Compute the discount factor, forward rate, or the zero rate from the Zero Rate Latent State
 	* Create a ForwardRateEstimator instance for the given Index
 	* Retrieve Array of the Calibration Components
 	* Retrieve the Curve Construction Input Set
 	* Compute the Jacobian of the Discount Factor Latent State to the input Quote
 	* Synthesize scenario Latent State by parallel shifting/custom tweaking the quantification metric
 	* Synthesize scenario Latent State by parallel/custom shifting/custom tweaking the manifest measure
 	* Serialize into and de-serialize out of byte array


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
