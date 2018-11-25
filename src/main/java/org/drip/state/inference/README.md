# DROP State Inference Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP State Inference Package implements the Latent State Stretch Sequence Inference.


## Class Components

 * [***LatentStateSegmentSpec***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/inference/LatentStateSegmentSpec.java)
 <i>LatentStateSegmentSpec</i> carries the calibration instrument and the manifest measure set used in
 calibrating the segment.

 * [***LatentStateSequenceBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/inference/LatentStateSequenceBuilder.java)
 <i>LatentStateSequenceBuilder</i> holds the logic behind building the bootstrap segments contained in the
 given Stretch.

 * [***LatentStateStretchSpec***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/inference/LatentStateStretchSpec.java)
 <i>LatentStateStretchSpec</i> carries the Latent State Segment Sequence corresponding to the calibratable
 Stretch.

 * [***LinearLatentStateCalibrator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/inference/LinearLatentStateCalibrator.java)
 <i>LinearLatentStateCalibrator</i> calibrates/constructs the Latent State Stretch/Span from the calibration
 instrument details. The span construction may be customized using specific settings provided in
 GlobalControlCurveParams.


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
