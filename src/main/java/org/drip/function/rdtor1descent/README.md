# DROP Function R<sup>d</sup> To R<sup>1</sup> Descent Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Function R<sup>d</sup> To R<sup>1</sup> Descent Package implements the Suite of R<sup>d</sup> To
	R<sup>1</sup> Gradient Descent Techniques.

## Class Components

 * [***ArmijoEvolutionVerifier***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1descent/ArmijoEvolutionVerifier.java)
 <i>ArmijoEvolutionVerifier</i> implements the Armijo Criterion used for the Inexact Line Search Increment
 Generation to ascertain that the Function has reduced sufficiently.

 * [***ArmijoEvolutionVerifierMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1descent/ArmijoEvolutionVerifierMetrics.java)
 <i>ArmijoEvolutionVerifierMetrics</i> implements the Armijo Criterion used for the Inexact Line Search
 Increment Generation to ascertain that the Function has reduced sufficiently.

 * [***CurvatuveEvolutionVerifier***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1descent/CurvatuveEvolutionVerifier.java)
 <i>CurvatuveEvolutionVerifier</i> implements the Armijo Criterion used for the Inexact Line Search Increment
 Generation to ascertain that the Gradient of the Function has reduced sufficiently.

 * [***CurvatureEvolutionVerifierMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1descent/CurvatureEvolutionVerifierMetrics.java)
 <i>CurvatureEvolutionVerifierMetrics</i> implements the Armijo Criterion used for the Inexact Line Search
 Increment Generation to ascertain that the Gradient of the Function has reduced sufficiently.

 * [***LineEvolutionVerifier***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1descent/LineEvolutionVerifier.java)
 <i>LineEvolutionVerifier</i> implements the Step Length Verification Criterion used for the Inexact Line
 Search Increment Generation.

 * [***LineEvolutionVerifierMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1descent/LineEvolutionVerifierMetrics.java)
 <i>LineEvolutionVerifierMetrics</i> implements the Step Length Verification Criterion used for the Inexact
 Line Search Increment Generation.

 * [***LineStepEvolutionControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1descent/LineStepEvolutionControl.java)
 <i>LineStepEvolutionControl</i> contains the Parameters required to compute the Valid a Line Step.

 * [***WolfeEvolutionVerifier***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1descent/WolfeEvolutionVerifier.java)
 <i>WolfeEvolutionVerifier</i> implements the Wolfe Criterion used for the Inexact Line Search Increment
 Generation.

 * [***WolfeEvolutionVerifierMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1descent/WolfeEvolutionVerifierMetrics.java)
 <i>WolfeEvolutionVerifierMetrics</i> implements the Wolfe Criterion used for the Inexact Line Search
 Increment Generation.
 
 
## References

 * Armijo, L. (1966): Minimization of Functions having Lipschitz-Continuous First Partial Derivatives
 	<i>Pacific Journal of Mathematics</i> <b>16 (1)</b> 1-3

 * Nocedal, J., and S. Wright (1999): <i>Numerical Optimization</i> <b>Wiley</b>

 * Wolfe, P. (1969): Convergence Conditions for Ascent Methods <i>SIAM Review</i> <b>11 (2)</b> 226-235

 * Wolfe, P. (1971): Convergence Conditions for Ascent Methods; II: Some Corrections <i>SIAM Review</i> <b>13
 	 (2)</b> 185-188


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
