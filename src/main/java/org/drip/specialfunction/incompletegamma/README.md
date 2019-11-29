# DROP Special Function Incomplete Gamma Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Special Function Incomplete Gamma Package implements the Upper/Lower Incomplete Gamma Functions.


## Class Components

 * [***GaussContinuedFraction***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/incompletegamma/GaussContinuedFraction.java)
 <i>GaussContinuedFraction</i> implements the Gauss Continued Fraction Based Estimates for the Lower/Upper Incomplete Gamma Function.

 * [***LimitAsymptote***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/incompletegamma/LimitAsymptote.java)
 <i>LimitAsymptote</i> implements the Asymptotes for the Lower/Upper Incomplete Gamma Function.

 * [***LowerEulerIntegral***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/incompletegamma/LowerEulerIntegral.java)
 <i>LowerEulerIntegral</i> implements the Euler's Second Kind Integral Version of the Lower Incomplete Gamma Function.

 * [***LowerLimitPowerIntegrand***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/incompletegamma/LowerLimitPowerIntegrand.java)
 <i>LowerLimitPowerIntegrand</i> contains the Integrand that is the Product of the Limit raised to a Power Exponent and the corresponding Lower Incomplete Gamma, for a given s.

 * [***LowerRegularized***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/incompletegamma/LowerRegularized.java)
 <i>LowerRegularized</i> implements the Regularized Version of the Lower Incomplete Gamma.

 * [***LowerSFixed***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/incompletegamma/LowerSFixed.java)
 <i>LowerSFixed</i> implements the Lower Incomplete Gamma Function using Power Series for a Fixed s.

 * [***LowerSFixedSeries***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/incompletegamma/LowerSFixedSeries.java)
 <i>LowerSFixedSeries</i> implements Lower Incomplete Gamma Expansion Series.

 * [***LowerSFixedSeriesTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/incompletegamma/LowerSFixedSeriesTerm.java)
 <i>LowerSFixedSeriesTerm</i> implements a Single Term in the Lower Incomplete Gamma Expansion Series for a Fixed s.

 * [***UpperEulerIntegral***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/incompletegamma/UpperEulerIntegral.java)
 <i>UpperEulerIntegral</i> implements the Euler's Second Kind Integral Version of the Upper Incomplete Gamma Function.

 * [***UpperLimitPowerIntegrand***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/incompletegamma/UpperLimitPowerIntegrand.java)
 <i>UpperLimitPowerIntegrand</i> contains the Integrand that is the Product of the Limit raised to a Power Exponent and the corresponding Upper Incomplete Gamma, for a given s.

 * [***UpperRegularized***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/incompletegamma/UpperRegularized.java)
 <i>UpperRegularized</i> implements the Regularized Version of the Upper Incomplete Gamma.

 * [***UpperSFixed***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/incompletegamma/UpperSFixed.java)
 <i>UpperSFixed</i> implements the Upper Incomplete Gamma Function using the Power Expansion Series, starting with s = 0 if Recurrence is employed.

 * [***UpperSFixedSeries***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/incompletegamma/UpperSFixedSeries.java)
 <i>UpperSFixedSeries</i> implements Upper Incomplete Gamma Expansion Series, starting with s = 0 if Recurrence is employed.

 * [***UpperSFixedSeriesTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/incompletegamma/UpperSFixedSeriesTerm.java)
 <i>UpperSFixedSeriesTerm</i> implements a Single Term in the Upper Incomplete Gamma Expansion Series for a Fixed s, starting from s = 0 if Recurrence is used.


## References

 * Geddes, K. O., M. L. Glasser, R. A. Moore, and T. C. Scott (1990): Evaluation of Classes of Definite Integrals involving Elementary Functions via Differentiation of Special Functions <i>Applicable Algebra in Engineering, Communications, and </i> <b>1 (2)</b> 149-165

 * Gradshteyn, I. S., I. M. Ryzhik, Y. V. Geronimus, M. Y. Tseytlin, and A. Jeffrey (2015): <i>Tables of Integrals, Series, and Products</i> <b>Academic Press</b>

 * Mathar, R. J. (2010): Numerical Evaluation of the Oscillatory Integral over e<sup>iπx</sup> x<sup>(1/x)</sup> between 1 and ∞ https://arxiv.org/pdf/0912.3844.pdf <b>arXiV</b>

 * National Institute of Standards and Technology (2019): Incomplete Gamma and Related Functions https://dlmf.nist.gov/8

 * Wikipedia (2019): Incomplete Gamma Function https://en.wikipedia.org/wiki/Incomplete_gamma_function


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
