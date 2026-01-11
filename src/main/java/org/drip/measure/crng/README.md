# DROP Measure CRNG Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Measure CRNG implements the Continuous Random Number Stream Generator.


## Class Components

 * [***CorrelatedFactorsPathVertexRealization***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/CorrelatedFactorsPathVertexRealization.java)
 <i>CorrelatedFactorsPathVertexRealization</i> generates Correlated R<sup>d</sup> Random Numbers at the specified Vertexes, over the Specified Paths.

 * [***LinearCongruentialGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/LinearCongruentialGenerator.java)
 <i>LinearCongruentialGenerator</i> implements a RNG based on Recurrence Based on Modular Integer Arithmetic.

 * [***LogNormalRandomNumberGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/LogNormalRandomNumberGenerator.java)
 <i>LogNormalRandomNumberGenerator</i> provides the Functionality to generate Log-normal Random Numbers.

 * [***MultipleRecursiveGeneratorLEcuyer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/MultipleRecursiveGeneratorLEcuyer.java)
 <i>MultipleRecursiveGeneratorLEcuyer</i> - L'Ecuyer's Multiple Recursive Generator - combines Multiple
 Recursive Sequences to produce a Large State Space with good Randomness Properties. MRG32k3a is a special
 Type of MultipleRecursiveGeneratorLEcuyer.

 * [***MultiStreamGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/MultiStreamGenerator.java)
 <i>MultiStreamGenerator</i> helps generate Multiple Independent (i.e., Non-Overlapping) Streams of Random
 Numbers.

 * [***QuadraticResampler***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/QuadraticResampler.java)
 <i>QuadraticResampler</i> Quadratically Re-samples the Input Points to Convert it to a Standard Normal.

 * [***RandomMatrixGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/RandomMatrixGenerator.java)
 <i>RandomMatrixGenerator</i> provides Functionality for generating different Kinds of Random Matrices.

 * [***RandomNumberGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/RandomNumberGenerator.java)
 <i>RandomNumberGenerator</i> provides the Functionality to generate Random Numbers. Default simply forwards to the Native JRE/JDK Implementation.

 * [***RandomSequenceGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/RandomSequenceGenerator.java)
 <i>RandomSequenceGenerator</i> generates the specified Univariate Sequence of the Given Distribution Type.

 * [***RdRandomSequenceGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/RdRandomSequenceGenerator.java)
 <i>RdRandomSequenceGenerator</i> generates 1D and 2D random arrays.

 * [***RecursiveGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/RecursiveGenerator.java)
 <i>RecursiveGenerator</i> exposes Sequence Generation using Recursive Schemes. The Recursion Schemes can be
 Single or Multiple. The Sequence Numbers can then be used for generating Random Numbers.

 * [***ShiftRegisterGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/ShiftRegisterGenerator.java)
 <i>ShiftRegisterGenerator</i> implements a RNG based on the Shift Register Generation Scheme.


## References

 * Axler, S. J. (1997): <i>Linear Algebra Done Right 2<sup>nd</sup> Edition</i> <b>Springer</b> New York NY

 * Backstrom, T., and J. Fischer (2018): Fast Randomization for Distributed Low Bit-rate Coding of Speech and Audio <i>IEEE/ACM Transactions on Audio, Speech, and Language Processing</i> <b>26 (1)</b> 19-30

 * Bernstein, D. S. (2009): <i>Matrix Mathematics: Theory, Facts, and Formulas 2<sup>nd</sup> Edition</i> <b>Princeton University Press</b> Princeton NJ

 * Chi-Squared Distribution (2019): Chi-Squared Function https://en.wikipedia.org/wiki/Chi-squared_distribution

 * Herstein, I. N. (1975): <i>Topics in Algebra 2<sup>nd</sup> Edition</i> <b>Wiley</b> New York NY

 * Johnson, N. L., S. Klotz, and N. Balakrishnan (1994): <i>Continuous Univariate Distributions <b>1</b> 2<sup>nd</sup> Edition</i> <b>John Wiley and Sons</b>

 * Lancaster, H, O. (1969): <i>The Chi-Squared Distribution</i> <b>Wiley</b> New York NY

 * Pillai, N. S. (1026): An Unexpected Encounter with Cauchy and Levy <i>Annals of Statistics</i> <b>44 (5)</b> 2089-2097

 * Prasolov, V. V. (1994): <i>Topics in Algebra</i> <b>American Mathematical Society</b> Providence RI

 * Wikipedia (2024): Triangular Matrix https://en.wikipedia.org/wiki/Triangular_matrix


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
