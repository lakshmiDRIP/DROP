# DROP Analytics Definition Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Analytics Definition Package defines the Latent State Curves, Surfaces, and Turns.

## Class Components

 * [***Curve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/definition/Curve.java)
 <i>Curve</i> extends the Latent State to abstract the functionality required among all financial curve. It
 exposes the following functionality:
 	* Set the Epoch and the Identifiers
 	* Set up/retrieve the Calibration Inputs
 	* Retrieve the Latent State Metric Measures

 * [***ExplicitBootCurve***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/definition/ExplicitBootCurve.java)
 In <i>ExplicitBootCurve</i>, the segment boundaries explicitly line up with the instrument maturity
 boundaries. This feature is exploited in building a boot-strappable curve. Functionality is provides set the
 Latent State at the Explicit Node, adjust the Latent State at the given Node, or set a common Flat Value
 across all Nodes.

 * [***LatentStateStatic***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/definition/LatentStateStatic.java)
 <i>LatentStateStatic</i> contains the Analytics Latent State Static/Textual Identifiers.

 * [***MarketSurface***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/definition/MarketSurface.java)
 <i>MarketSurface</i> exposes the stub that implements the market surface that holds the latent state's
 Evolution parameters.

 * [***NodeStructure***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/definition/NodeStructure.java)
 <i>NodeStructure</i> exposes the stub that implements the latent state's Node Structure (e.g., a
 Deterministic Term Structure) - by Construction, this is expected to be non-local.

 * [***Turn***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/definition/Turn.java)
 <i>Turn</i> implements rate spread at discrete time spans. It contains the turn amount and the start/end
 turn spans.

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
