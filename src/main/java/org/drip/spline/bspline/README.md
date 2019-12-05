# DROP Spline B Spline Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Spline B Spline Package implements the de Boor Rational/Exponential/Tension B-Splines.


## Class Components

 * [***BasisHatPairGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/BasisHatPairGenerator.java)
 <i>BasisHatPairGenerator</i> implements the generation functionality behind the hat basis function pair. It
 provides the following functionality:
 	* Generate the array of the Hyperbolic Phy and Psy Hat Function Pair.
 	* Generate the array of the Hyperbolic Phy and Psy Hat Function Pair From their Raw Counterparts.
 	* Generate the array of the Cubic Rational Phy and Psy Hat Function Pair From their Raw Counterparts.
 	* Generate the array of the Custom Phy and Psy Hat Function Pair From their Raw Counterparts.

 * [***BasisHatShapeControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/BasisHatShapeControl.java)
 <i>BasisHatShapeControl</i> implements the shape control function for the hat basis set as laid out in the
 framework outlined in Koch and Lyche (1989), Koch and Lyche (1993), and Kvasov (2000) Papers. Currently
 BasisHatShapeControl implements the following shape control customizers:
 	* Cubic Polynomial with Rational Linear Shape Controller.
 	* Cubic Polynomial with Rational Quadratic Shape Controller.
 	* Cubic Polynomial with Rational Exponential Shape Controller.

 * [***CubicRationalLeftRaw***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/CubicRationalLeftRaw.java)
 <i>CubicRationalLeftRaw</i> implements the TensionBasisHat interface in accordance with the raw left cubic
 rational hat basis function laid out in the basic framework outlined in Koch and Lyche (1989), Koch and
 Lyche (1993), and Kvasov (2000) Papers.

 * [***CubicRationalRightRaw***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/CubicRationalRightRaw.java)
 <i>CubicRationalRightRaw</i> implements the TensionBasisHat interface in accordance with the raw right cubic
 rational hat basis function laid out in the basic framework outlined in Koch and Lyche (1989), Koch and
 Lyche (1993), and Kvasov (2000) Papers.

 * [***ExponentialTensionLeftHat***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/ExponentialTensionLeftHat.java)
 <i>ExponentialTensionLeftHat</i> implements the TensionBasisHat interface in accordance with the left
 exponential hat basis function laid out in the basic framework outlined in Koch and Lyche (1989), Koch and
 Lyche (1993), and Kvasov (2000) Papers.

 * [***ExponentialTensionLeftRaw***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/ExponentialTensionLeftRaw.java)
 <i>ExponentialTensionLeftRaw</i> implements the TensionBasisHat interface in accordance with the raw left
 exponential hat basis function laid out in the basic framework outlined in Koch and Lyche (1989), Koch and
 Lyche (1993), and Kvasov (2000) Papers.

 * [***ExponentialTensionRightHat***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/ExponentialTensionRightHat.java)
 <i>ExponentialTensionRightHat</i> implements the TensionBasisHat interface in accordance with the right
 exponential hat basis function laid out in the basic framework outlined in Koch and Lyche (1989), Koch and
 Lyche (1993), and Kvasov (2000) Papers.

 * [***ExponentialTensionRightRaw***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/ExponentialTensionRightRaw.java)
 <i>ExponentialTensionRightRaw</i> implements the TensionBasisHat interface in accordance with the raw right
 exponential hat basis function laid out in the basic framework outlined in Koch and Lyche (1989), Koch and
 Lyche (1993), and Kvasov (2000) Papers.

 * [***LeftHatShapeControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/LeftHatShapeControl.java)
 <i>LeftHatShapeControl</i> implements the BasisHatShapeControl interface for the left hat basis set as laid
 out in the basic framework outlined in Koch and Lyche (1989), Koch and Lyche (1993), and Kvasov (2000)
 Papers.

 * [***RightHatShapeControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/RightHatShapeControl.java)
 <i>RightHatShapeControl</i> implements the BasisHatShapeControl interface for the right hat basis set as
 laid out in the basic framework outlined in Koch and Lyche (1989), Koch and Lyche (1993), and Kvasov (2000)
 Papers.

 * [***SegmentBasisFunction***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/SegmentBasisFunction.java)
 <i>SegmentBasisFunction</i> is the abstract class over which the local ordered envelope functions for the B
 Splines are implemented. It exposes the following stubs:
 	* Retrieve the Order of the B Spline.
 	* Retrieve the Leading Predictor Ordinate.
 	* Retrieve the Following Predictor Ordinate.
 	* Retrieve the Trailing Predictor Ordinate.
 	* Compute the complete Envelope Integrand - this will serve as the Envelope Normalizer.
 	* Evaluate the Cumulative Normalized Integrand up to the given ordinate.

 * [***SegmentBasisFunctionGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/SegmentBasisFunctionGenerator.java)
 <i>SegmentBasisFunctionGenerator</i> generates B Spline Functions of different order. It provides the
 following functionality:
 	* Create a Tension Monic B Spline Basis Function.
 	* Construct a Sequence of Monic Basis Functions.
 	* Create a sequence of B Splines of the specified order from the given inputs.

 * [***SegmentBasisFunctionSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/SegmentBasisFunctionSet.java)
 <i>SegmentBasisFunctionSet</i> class implements per-segment function set for B Splines and tension splines.
 Derived implementations expose explicit targeted basis functions.

 * [***SegmentMonicBasisFunction***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/SegmentMonicBasisFunction.java)
 <i>SegmentMonicBasisFunction</i> implements the local monic B Spline that envelopes the predictor ordinates,
 and the corresponding set of ordinates/basis functions. SegmentMonicBasisFunction uses the left/right
 TensionBasisHat instances to achieve its implementation goals.

 * [***SegmentMulticBasisFunction***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/SegmentMulticBasisFunction.java)
 <i>SegmentMulticBasisFunction</i> implements the local quadratic B Spline that envelopes the predictor
 ordinates, and the corresponding set of ordinates/basis functions. SegmentMulticBasisFunction uses the
 left/right SegmentBasisFunction instances to achieve its implementation goals.

 * [***TensionBasisHat***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/TensionBasisHat.java)
 <i>TensionBasisHat</i> implements the common basis hat function that form the basis for all B Splines. It
 contains the left/right ordinates, the tension, and the normalizer.

 * [***TensionProcessedBasisHat***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/TensionProcessedBasisHat.java)
 <i>TensionProcessedBasisHat</i> implements the processed hat basis function of the form laid out in the
 basic framework outlined in Koch and Lyche (1989), Koch and Lyche (1993), and Kvasov (2000) Papers.


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
