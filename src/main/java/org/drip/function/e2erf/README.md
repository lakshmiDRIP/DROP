# DROP Function E2ERF Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Function E2ERF Package contains the E<sub>2</sub> erf and erf<sup>-1</sup> Implementations.


## Class Components

 * [***AbramowitzStegun***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/e2erf/AbramowitzStegun.java)
 <i>AbramowitzStegun</i> implements the E<sub>2</sub> (erf) Estimator using Abramowitz-Stegun Scheme.

 * [***AbramowitzStegunSeriesGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/e2erf/AbramowitzStegunSeriesGenerator.java)
 <i>AbramowitzStegunSeriesGenerator</i> implements the E<sub>2</sub> erf Abramowitz-Stegun Variant of Series Term Generator.

 * [***BuiltInEntry***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/e2erf/BuiltInEntry.java)
 <i>BuiltInEntry</i> implements E<sub>2</sub> Entries of the Built-in Table of erf and erfc Values.

 * [***ErrorFunction***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/e2erf/ErrorFunction.java)
 <i>ErrorFunction</i> implements the E<sub>2</sub> Error Function (erf).

 * [***ErrorFunctionAnalytical***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/e2erf/ErrorFunctionAnalytical.java)
 <i>ErrorFunctionAnalytical</i> implements Analytical Versions of the E<sub>2</sub> Error Function (erf) Estimate.

 * [***ErrorFunctionInverse***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/e2erf/ErrorFunctionInverse.java)
 <i>ErrorFunctionInverse</i> implements the E<sub>2</sub> erf Inverse erf<sup>-1</sup>.

 * [***HansHeinrichBurmannSeries***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/e2erf/HansHeinrichBurmannSeries.java)
 <i>HansHeinrichBurmannSeries</i> generates the Terms in the E<sub>2</sub> erf Hans-Heinrich-Burmann Series Variants.

 * [***HansHeinrichBurmannTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/e2erf/HansHeinrichBurmannTerm.java)
 <i>HansHeinrichBurmannTerm</i> implements the Term in the E<sub>2</sub> erf Hans-Heinrich-Burmann Series Variants.

 * [***MacLaurinSeries***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/e2erf/MacLaurinSeries.java)
 <i>MacLaurinSeries</i> implements the E<sub>2</sub> MacLaurin Series Term.

 * [***MacLaurinSeriesTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/e2erf/MacLaurinSeriesTerm.java)
 <i>MacLaurinSeriesTerm</i> implements the E<sub>2</sub> MacLaurin Series Term. This is used for both erf and erf<sup>-1</sup>.


## References

 * Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book on Mathematics</b>

 * Chang, S. H., P. C. Cosman, L. B. Milstein (2011): Chernoff-Type Bounds for Gaussian Error Function <i>IEEE Transactions on Communications</i> <b>59 (11)</b> 2939-2944

 * Cody, W. J. (1991): Algorithm 715: SPECFUN; A Portable FORTRAN Package of Special Function Routines and Test Drivers <i>ACM Transactions on Mathematical Software</i> <b>19 (1)</b> 22-32

 * Schopf, H. M., and P. H. Supancic (2014): On Burmann Theorem and its Application to Problems of Linear and Non-linear Heat Transfer and Diffusion https://www.mathematica-journal.com/2014/11/on-burmanns-theorem-and-its-application-to-problems-of-linear-and-nonlinear-heat-transfer-and-diffusion/#more-39602/

 * Wikipedia (2019): Error Function https://en.wikipedia.org/wiki/Error_function


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
