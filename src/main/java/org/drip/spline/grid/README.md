# DROP Spline Grid Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Spline Grid Package holds the Aggregated/Overlapping Stretch/Span Grids.


## Class Components

 * [***AggregatedSpan***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/grid/AggregatedSpan.java)
 <i>AggregatedSpan</i> implements the Span interface. Here response from an array of spans whose responses
 are aggregated by their weights.

 * [***OverlappingStretchSpan***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/grid/OverlappingStretchSpan.java)
 <i>OverlappingStretchSpan</i> implements the Span interface, and the collection functionality of overlapping
 Stretches. In addition to providing a custom implementation of all the Span interface stubs, it also
 converts the Overlapping Stretch Span to a non-overlapping Stretch Span. Overlapping Stretches are clipped
 from the Left.

 * [***Span***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/grid/Span.java)
 <i>Span</i> is the interface that exposes the functionality behind the collection of Stretches that may be
 overlapping or non-overlapping. It exposes the following stubs:
 	* Retrieve the Left/Right Span Edge.
 	* Indicate if the specified Label is part of the Merge State at the specified Predictor Ordinate.
 	* Compute the Response from the containing Stretches.
 	* Add a Stretch to the Span.
 	* Retrieve the first Stretch that contains the Predictor Ordinate.
 	* Retrieve the Stretch by Name.
 	* Calculate the Response Derivative to the Quote at the specified Ordinate.
 	* Display the Span Edge Coordinates.


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
