# DROP State Non linear Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP State Non linear Package implements the Nonlinear (i.e., Boot) Latent State Construction.


## Class Components

 * [***FlatForwardDiscountCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear/FlatForwardDiscountCurve.java)
 <i>FlatForwardDiscountCurve</i> manages the Discounting Latent State, using the Forward Rate as the State
 Response Representation. It exports the following functionality:
 	* Boot Methods; Set/Bump Specific Node Quantification Metric, or Set Flat Value
 	* Boot Calibration; Initialize Run, Compute Calibration Metric
 	* Compute the discount factor, forward rate, or the zero rate from the Forward Rate Latent State
 	* Create a ForwardRateEstimator instance for the given Index
 	* Retrieve Array of the Calibration Components
 	* Retrieve the Curve Construction Input Set
 	* Compute the Jacobian of the Discount Factor Latent State to the input Quote
 	* Synthesize scenario Latent State by parallel shifting/custom tweaking the quantification metric
 	* Synthesize scenario Latent State by parallel/custom shifting/custom tweaking the manifest measure

 * [***FlatForwardForwardCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear/FlatForwardForwardCurve.java)
 <i>FlatForwardForwardCurve</i> contains an implementation of the flat forward rate forward curve.

 * [***FlatForwardFXCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear/FlatForwardFXCurve.java)
 <i>FlatForwardFXCurve</i> manages the Volatility Latent State, using the Forward FX as the State Response
 Representation.

 * [***FlatForwardGovvieCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear/FlatForwardGovvieCurve.java)
 <i>FlatForwardGovvieCurve</i> manages the Govvie Latent State, using the Flat Forward Rate as the State
 Response Representation.

 * [***FlatForwardRepoCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear/FlatForwardRepoCurve.java)
 <i>FlatForwardRepoCurve</i> manages the Repo Latent State, using the Forward Repo Rate as the State Response
 Representation.

 * [***FlatForwardVolatilityCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear/FlatForwardVolatilityCurve.java)
 <i>FlatForwardVolatilityCurve</i> manages the Volatility Latent State, using the Forward Volatility as the

 * [***FlatYieldGovvieCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear/FlatYieldGovvieCurve.java)
 <i>FlatYieldGovvieCurve</i> manages the Govvie Latent State, using the Flat Yield as the State Response
 Representation.

 * [***ForwardHazardCreditCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear/ForwardHazardCreditCurve.java)
 <i>ForwardHazardCreditCurve</i> manages the Survival Latent State, using the Hazard Rate as the State
 Response Representation. It exports the following functionality:
 	* Boot Methods; Set/Bump Specific Node Quantification Metric, or Set Flat Value
 	* Boot Calibration; Initialize Run, Compute Calibration Metric
 	* Compute the survival probability, recovery rate, or the hazard rate from the Hazard Rate Latent State
 	* Retrieve Array of the Calibration Components
 	* Retrieve the Curve Construction Input Set
 	* Synthesize scenario Latent State by parallel shifting/custom tweaking the quantification metric
 	* Synthesize scenario Latent State by parallel/custom shifting/custom tweaking the manifest measure

 * [***NonlinearCurveBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear/NonlinearCurveBuilder.java)
 <i>NonlinearCurveBuilder</i> calibrates the discount and credit/hazard curves from the components and their
 quotes. NonlinearCurveCalibrator employs a set of techniques for achieving this calibration.
 	* It bootstraps the nodes in sequence to calibrate the curve.
 	* In conjunction with splining estimation techniques, it may also be used to perform dual sweep
 		calibration. The inner sweep achieves the calibration of the segment spline parameters, while the
 		outer sweep calibrates iteratively for the targeted boundary conditions.
 	* It may also be used to custom calibrate a single Interest Rate/Hazard Rate Node from the corresponding
 		Component.

 CurveCalibrator bootstraps/cooks both discount curves and credit curves.


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
