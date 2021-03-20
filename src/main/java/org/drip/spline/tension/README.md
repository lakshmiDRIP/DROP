# DROP Spline Tension Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Spline Tension Package implements the Koch Lyche Kvasov Tension Splines.


## Class Components

 * [***KLKHyperbolicTensionPhy***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/tension/KLKHyperbolicTensionPhy.java)
 <i>KLKHyperbolicTensionPhy</i> implements the basic framework and the family of C2 Tension Splines outlined
 in Koch and Lyche (1989), Koch and Lyche (1993), and Kvasov (2000) Papers. KLKHyperbolicTensionPsy
 implements the custom evaluator, differentiator, and integrator for the KLK Tension Phy Functions outlined
 in the publications above.

 * [***KLKHyperbolicTensionPsy***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/tension/KLKHyperbolicTensionPsy.java)
 <i>KLKHyperbolicTensionPsy</i> implements the basic framework and the family of C2 Tension Splines outlined
 in Koch and Lyche (1989), Koch and Lyche (1993), and Kvasov (2000) Papers. KLKHyperbolicTensionPsy
 implements the custom evaluator, differentiator, and integrator for the KLK Tension Psy Functions outlined
 in the publications above.

 * [***KochLycheKvasovBasis***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/tension/KochLycheKvasovBasis.java)
 <i>KochLycheKvasovBasis</i> implements the basic framework and the family of C2 Tension Splines outlined in
 Koch and Lyche (1989), Koch and Lyche (1993), and Kvasov (2000) Papers. Currently, this class exposes
 functions to create monic and quadratic tension B Spline Basis Function Set.

 * [***KochLycheKvasovFamily***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/tension/KochLycheKvasovFamily.java)
 <i>KochLycheKvasovFamily</i> implements the basic framework and the family of C2 Tension Splines outlined in Koch
 and Lyche (1989), Koch and Lyche (1993), and Kvasov (2000) Papers. Functions exposed here implement the
 Basis Function Set from:
 	* Hyperbolic Hat Primitive Set
 	* Cubic Polynomial Numerator and Linear Rational Denominator
 	* Cubic Polynomial Numerator and Quadratic Rational Denominator
 	* Cubic Polynomial Numerator and Exponential Denominator


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
