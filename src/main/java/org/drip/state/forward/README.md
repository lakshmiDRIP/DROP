# DROP State Forward Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP State Forward Package implements the Forward Latent State Curve Estimator.


## Class Components

 * [***ForwardCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/forward/ForwardCurve.java)
 <i>ForwardCurve</i> is the stub for the forward curve functionality. It extends the Curve object by exposing
 the following functions:
 	* The name/epoch of the forward rate instance
 	* The index/currency/tenor associated with the forward rate instance
 	* Forward Rate to a specific date/tenor
 	* Generate scenario tweaked Latent State from the base forward curve corresponding to mode adjusted
 		(flat/parallel/custom) manifest measure/quantification metric
 	* Retrieve array of latent state manifest measure, instrument quantification metric, and the array of
 		calibration components
 	* Set/retrieve curve construction input instrument sets

 * [***ForwardRateEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/forward/ForwardRateEstimator.java)
 <i>ForwardRateEstimator</i> is the interface that exposes the calculation of the Forward Rate for a specific
 Index. It exposes methods to compute forward rates to a given date/tenor, extract the forward rate index and
 the Tenor.


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
