# DROP Numerical Differentiation Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Numerical Differentiation Package implements the R<sup>1</sup> To R<sup>1</sup> Numerical
	Differentiation.


## Class Components

 * [***DerivativeControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/differentiation/DerivativeControl.java)
 <i>DerivativeControl</i> provides bumps needed for numerically approximating derivatives. Bumps can be
 absolute or relative, and they default to a floor.

 * [***Differential***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/differentiation/Differential.java)
 <i>Differential</i> holds the incremental differentials for the variate and the objective function.

 * [***WengertJacobian***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/differentiation/WengertJacobian.java)
 <i>WengertJacobian</i> contains the Jacobian of the given set of Wengert variables to the set of parameters.
 It exposes the following functionality:
 	* Set/Retrieve the Wengert variables
 	* Accumulate the Partials
 	* Scale the partial entries
 	* Merge the Jacobian with another
 	* Retrieve the Wengert Jacobian elements
 	* Display the contents of the Wengert Jacobian


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
