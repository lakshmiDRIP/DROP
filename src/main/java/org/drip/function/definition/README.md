# DROP Function Definition Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Function Definition Package contains the Function Execution Ancillary Support Objects.

## Class Components

 * [***R1ToR1***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/R1ToR1.java)
 <i>R1ToR1</i> provides the evaluation of the objective function and its derivatives for a specified
 variate. Default implementation of the derivatives are for non-analytical black box objective functions.

 * [***R1ToRd***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/R1ToRd.java)
 <i>R1ToRd</i> provides the evaluation of the R<sup>1</sup> To R<sup>d</sup> Objective Function and its
 derivatives for a specified variate. Default implementation of the derivatives are for non-analytical black
 box objective functions.

 * [***RdToR1***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/RdToR1.java)
 <i>RdToR1</i> provides the evaluation of the R<sup>d</sup> To R<sup>1</sup> objective function and its
 derivatives for a specified set of R<sup>d</sup> variates. Default implementation of the derivatives are for
 non-analytical lack box objective functions.

 * [***RdToRd***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/RdToRd.java)
 <i>RdToRd</i> provides the evaluation of the R<sup>d</sup> To R<sup>d</sup> objective function and its
 derivatives for a specified set of R<sup>d</sup> variates. Default implementation of the derivatives are for
 non-analytical black box objective functions.

 * [***SizedVector***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/SizedVector.java)
 <i>SizedVector</i> holds the R<sup>d</sup> Unit Direction Vector along with its Magnitude.

 * [***UnitVector***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/UnitVector.java)
 <i>UnitVector</i> implements the Normalized R<sup>d</sup> Unit Vector.

 * [***VariateOutputPair***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/VariateOutputPair.java)
 <i>VariateOutputPair</i> records the Multidimensional Variate and its corresponding Objective Function
 Value.


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
