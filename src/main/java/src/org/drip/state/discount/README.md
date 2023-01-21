# DROP State Discount Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP State Discount Package holds the Discount Curve Spline Latent State.


## Class Components

 * [***DiscountCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/discount/DiscountCurve.java)
 <i>DiscountCurve</i> Interface combines the Interfaces of Latent State Curve Representation and Discount
 Factor Estimator.

 * [***DiscountFactorEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/discount/DiscountFactorEstimator.java)
 <i>DiscountFactorEstimator</i> is the interface that exposes the calculation of the Discount Factor for a specific Sovereign/Jurisdiction Span. It exposes the following functionality:
 	* Curve Epoch Date
 	* Discount Factor Target/Effective Variants - to Specified Julian Dates and/or Tenors
 	* Forward Rate Target/Effective Variants - to Specified Julian Dates and/or Tenors
 	* Zero Rate Target/Effective Variants - to Specified Julian Dates and/or Tenors
 	* LIBOR Rate and LIBOR01 Target/Effective Variants - to Specified Julian Dates and/or Tenors
 	* Curve Implied Arbitrary Measure Estimates

 * [***ExplicitBootDiscountCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/discount/ExplicitBootDiscountCurve.java)
<i>ExplicitBootDiscountCurve</i> exposes the functionality associated with the bootstrapped Discount Curve.
 	* Generate a curve shifted using targeted basis at specific nodes
 	* Generate scenario tweaked Latent State from the base forward curve corresponding to mode adjusted (flat/parallel/custom) manifest measure/quantification metric
 	* Retrieve array of latent state manifest measure, instrument quantification metric, and the array of calibration components
 	* Set/retrieve curve construction input instrument sets

 * [***MergedDiscountForwardCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/discount/MergedDiscountForwardCurve.java)
<i>MergedDiscountForwardCurve</i> is the Stub for the Merged Discount and Forward Curve Functionality. It extends the both the Curve and the DiscountFactorEstimator instances by implementing their functions, and exposing the following:
  	* Forward Rate to a specific date/tenor, and effective rate between a date interval
  	* Discount Factor to a specific date/tenor, and effective discount factor between a date interval
 	* Zero Rate to a specific date/tenor
 	* Value Jacobian for Forward rate, discount factor, and zero rate
 	* Cross Jacobian between each of Forward rate, discount factor, and zero rate
 	* Quote Jacobian to Forward rate, discount factor, and zero rate
 	* QM (DF/Zero/Forward) to Quote Jacobian
 	* Latent State Quantification Metric, and the canonical truthness transformations
 	* Implied/embedded ForwardRateEstimator
 	* Turns set/unset/adjust

 * [***TurnListDiscountFactor***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/discount/TurnListDiscountFactor.java)
 <i>TurnListDiscountFactor</i> implements the discounting based off of the turns list. Its functions add a turn instance to the current set, and concurrently apply the discount factor inside the range to each relevant turn.

 * [***ZeroCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/discount/ZeroCurve.java)
 <i>ZeroCurve</i> exposes the node set containing the zero curve node points. In addition to the discount curve functionality that it automatically provides by extension, it provides the functionality to calculate the zero rate.


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
