# DROP Learning Kernel Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Learning Kernel Package implements the Statistical Learning Banach Mercer Kernels.

## Class Components

 * [***DiagonalScalingOperator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/kernel/DiagonalScalingOperator.java)
 <i>DiagonalScalingOperator</i> implements the Scaling Operator that is used to determine the Bounds of the
 R<sup>x</sup> L<sub>2</sub> To R<sup>x</sup> L<sub>2</sub> Kernel Linear Integral Operator defined by:
 
 		T_k [f(.)] := Integral Over Input Space {k (., y) * f(y) * d[Prob(y)]}

 * [***EigenFunctionRdToR1***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/kernel/EigenFunctionRdToR1.java)
 <i>EigenFunctionRdToR1</i> holds the Eigen-vector Function and its corresponding Space of the R<sup>d</sup>
 To R<sup>1</sup> Kernel Linear Integral Operator defined by:

 		T_k [f(.)] := Integral Over Input Space {k (., y) * f(y) * d[Prob(y)]}

 * [***HilbertSupremumKernelSpace***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/kernel/HilbertSupremumKernelSpace.java)
 <i>HilbertSupremumKernelSpace</i> contains the Space of Kernels S that are a Transform from the
 R<sup>d</sup> L<sub>2</sub> Hilbert To R<sup>m</sup> L<sub>Infinity</sub> Supremum Banach Spaces.

 * [***IntegralOperator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/kernel/IntegralOperator.java)
 <i>IntegralOperator</i> implements the R<sup>x</sup> L<sub>2</sub> To R<sup>x</sup> L<sub>2</sub> Mercer
 Kernel Integral Operator defined by:
 
 		T_k [f(.)] := Integral Over Input Space {k (., y) * f(y) * d[Prob(y)]}

 * [***IntegralOperatorEigenComponent***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/kernel/IntegralOperatorEigenComponent.java)
 <i>IntegralOperatorEigenComponent</i> holds the Eigen-Function Space and the Eigenvalue Functions/Spaces of
 the R<sup>x</sup> L<sub>2</sub> To R<sup>x</sup> L<sub>2</sub> Kernel Linear Integral Operator defined by:

 		T_k [f(.)] := Integral Over Input Space {k (., y) * f(y) * d[Prob(y)]}

 * [***IntegralOperatorEigenContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/kernel/IntegralOperatorEigenContainer.java)
 <i>IntegralOperatorEigenContainer</i> holds the Group of Eigen-Components that result from the Eigenization
 of the R<sup>x</sup> L<sub>2</sub> To R<sup>x</sup> L<sub>2</sub> Kernel Linear Integral Operator defined by
 
 		T_k [f(.)] := Integral Over Input Space {k (., y) * f(y) * d[Prob(y)]}

 * [***MercerKernel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/kernel/MercerKernel.java)
 <i>MercerKernel</i> exposes the Functionality behind the Eigenized Kernel that is Normed R<sup>x</sup> X
 Normed R<sup>x</sup> To Supremum R<sup>1</sup>

 * [***SymmetricRdToNormedR1Kernel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/kernel/SymmetricRdToNormedR1Kernel.java)
 <i>SymmetricRdToNormedR1Kernel</i> exposes the Functionality behind the Kernel that is Normed R<sup>d</sup>
 X Normed R<sup>d</sup> To Supremum R<sup>1</sup>, that is, a Kernel that symmetric in the Input Metric
 Vector Space in terms of both the Metric and the Dimensionality.

 * [***SymmetricRdToNormedRdKernel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/kernel/SymmetricRdToNormedR1Kernel.java)
 <i>SymmetricRdToNormedR1Kernel</i> exposes the Functionality behind the Kernel that is Normed R<sup>d</sup>
 X Normed R<sup>d</sup> To Supremum R<sup>d</sup>, that is, a Kernel that symmetric in the Input Metric
 Vector Space in terms of both the Metric and the Dimensionality.


## References

 * Ash, R. (1965): <i>Information Theory</i> Inter-science</b> New York

 * Carl, B. (1985): Inequalities of the Bernstein-Jackson type and the Degree of Compactness of Operator in
 Banach Spaces <i>Annals of the Fourier Institute</i> <b>35 (3)</b> 79-118

 * Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and Approximation of Operators</i> <b>Cambridge
 University Press</b> Cambridge UK

 * Gordon, Y., H. Konig, and C. Schutt (1987): Geometric and Probabilistic Estimates of Entropy and
 Approximation Numbers of Operators <i>Journal of Approximation Theory</i> <b>49</b> 219-237

 * Konig, H. (1986): <i>Eigenvalue Distribution of Compact Operators</i> <b>Birkhauser</b> Basel, Switzerland

 * Smola, A. J., A. Elisseff, B. Scholkopf, and R. C. Williamson (2000): Entropy Numbers for Convex
 Combinations and mlps, in: <i>Advances in Large Margin Classifiers, A. Smola, P. Bartlett, B. Scholkopf, and
 D. Schuurmans - editors</i> <b>MIT Press</b> Cambridge, MA

 * Williamson, R. C., A. J. Smola, and B. Scholkopf (2000): Entropy Numbers of Linear Function Classes, in:
 <i>Proceedings of the 13th Annual Conference on Computational Learning Theory</i> <b>ACM</b> New York


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
