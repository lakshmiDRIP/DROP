# DROP Function Definition Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Function Definition Package contains the Function Execution Ancillary Support Objects.


## Class Components

 * [***CartesianComplexNumber***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/CartesianComplexNumber.java)
 <i>CartesianComplexNumber</i> implements the functionality for dealing with the Cartesian Form of Complex Numbers.

 * [***PoleResidue***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/PoleResidue.java)
 <i>PoleResidue</i> holds the Residue for  given variate, if it is a Pole.

 * [***R1PropertyVerification***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/R1PropertyVerification.java)
 <i>R1PropertyVerification</i> evaluates the Specified Pair of R<sup>x</sup> To R<sup>1</sup> Functions, and holds the Verification Status.

 * [***R1ToR1***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/R1ToR1.java)
 <i>R1ToR1</i> provides the evaluation of the objective function and its derivatives for a specified
 variate. Default implementation of the derivatives are for non-analytical black box objective functions.

 * [***R1ToR1Property***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/R1ToR1Property.java)
 <i>R1ToR1Property</i> evaluates the Specified Pair of R<sup>1</sup> To R<sup>1</sup> Functions, and verifies the Properties.

 * [***R1ToRd***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/R1ToRd.java)
 <i>R1ToRd</i> provides the evaluation of the R<sup>1</sup> To R<sup>d</sup> Objective Function and its
 derivatives for a specified variate. Default implementation of the derivatives are for non-analytical black
 box objective functions.

 * [***R2ToR1***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/R2ToR1.java)
 <i>R2ToR1</i> provides the Evaluation of the Objective Function and its derivatives for a specified variate Pair. Default Implementation of the Derivatives are for Non-analytical Black Box Objective Functions.

 * [***R2ToR1Property***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/R2ToR1Property.java)
 <i>R2ToR1Property</i> evaluates the Specified Pair of R<sup>2</sup> To R<sup>1</sup> Functions, and verifies the Properties.

 * [***R2ToZ1***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/R2ToZ1.java)
 <i>R2ToZ1</i> provides the Evaluation of the Complex Objective Function and its Derivatives for a specified Variate Pair.

 * [***R3ToR1***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/R3ToR1.java)
 <i>R3ToR1</i> provides the Evaluation of the Objective Function and its derivatives for a specified variate Pair. Default Implementation of the Derivatives are for Non-analytical Black Box Objective Functions.

 * [***R3ToR1Property***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/R3ToR1Property.java)
 <i>R3ToR1Property</i> evaluates the Specified Pair of R<sup>3</sup> To R<sup>1</sup> Functions, and verifies the Properties.

 * [***RdToR1***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/RdToR1.java)
 <i>RdToR1</i> provides the evaluation of the R<sup>d</sup> To R<sup>1</sup> objective function and its
 derivatives for a specified set of R<sup>d</sup> variates. Default implementation of the derivatives are for
 non-analytical lack box objective functions.

 * [***RdToRd***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/RdToRd.java)
 <i>RdToRd</i> provides the evaluation of the R<sup>d</sup> To R<sup>d</sup> objective function and its
 derivatives for a specified set of R<sup>d</sup> variates. Default implementation of the derivatives are for
 non-analytical black box objective functions.

 * [***RxToR1Property***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/RxToR1Property.java)
 <i>RxToR1Property</i> evaluates the Specified Pair of R<sup>x</sup> To R<sup>1</sup> Functions, and verifies the Properties.

 * [***SizedVector***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/SizedVector.java)
 <i>SizedVector</i> holds the R<sup>d</sup> Unit Direction Vector along with its Magnitude.

 * [***UnitVector***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/UnitVector.java)
 <i>UnitVector</i> implements the Normalized R<sup>d</sup> Unit Vector.

 * [***VariateOutputPair***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/VariateOutputPair.java)
 <i>VariateOutputPair</i> records the Multidimensional Variate and its corresponding Objective Function
 Value.


## References

 * Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book on Mathematics</b>

 * Arfken, G. B., and H. J. Weber (2005): <i>Mathematical Methods for Physicists 6<sup>th</sup> Edition</i> <b>Harcourt</b> San Diego

 * Davis, P. J. (1959): Leonhard Euler's Integral: A Historical Profile of the Gamma Function <i>American Mathematical Monthly</i> <b>66 (10)</b> 849-869

 * Temme N. M. (1996): <i>Special Functions: An Introduction to the Classical Functions of Mathematical Physics 2<sup>nd</sup> Edition</i> <b>Wiley</b> New York

 * Watson, G. N. (1995): <i>A Treatise on the Theory of Bessel Functions</i> <b>Cambridge University Press</b>

 * Whitaker, E. T., and G. N. Watson (1996): <i>A Course on Modern Analysis</i> <b>Cambridge University Press</b> New York

 * Wikipedia (2019): Bessel Function https://en.wikipedia.org/wiki/Bessel_function

 * Wikipedia (2019): Beta Function https://en.wikipedia.org/wiki/Beta_function

 * Wikipedia (2019): Gamma Function https://en.wikipedia.org/wiki/Gamma_function


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
