# DROP Measure CRNG Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Measure CRNG implements the Continuous Random Number Stream Generator.


## Class Components

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

 * [***RandomNumberGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/RandomNumberGenerator.java)
 <i>RandomNumberGenerator</i> provides the Functionality to generate Random Numbers. Default simply forwards to the Native JRE/JDK Implementation.

 * [***RecursiveGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/RecursiveGenerator.java)
 <i>RecursiveGenerator</i> exposes Sequence Generation using Recursive Schemes. The Recursion Schemes can be
 Single or Multiple. The Sequence Numbers can then be used for generating Random Numbers.

 * [***ShiftRegisterGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/ShiftRegisterGenerator.java)
 <i>ShiftRegisterGenerator</i> implements a RNG based on the Shift Register Generation Scheme.


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
