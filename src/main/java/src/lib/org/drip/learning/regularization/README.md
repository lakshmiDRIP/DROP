# DROP Learning Regularization Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Learning Kernel Regularization implements the Statistical Learning Empirical Loss Regularizer.


## Class Components

 * [***RegularizationFunction***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/regularization/RegularizationFunction.java)
 <i>RegularizationFunction</i> the R<sup>1</sup> To R<sup>1</sup> and the R<sup>d</sup> To R<sup>1</sup>
 Regularization Functions.

 * [***RegularizerBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/regularization/RegularizerBuilder.java)
 <i>RegularizerBuilder</i> constructs Custom Regularizers for the different Normed Learner Function Types.

 * [***RegularizerR1CombinatorialToR1Continuous***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/regularization/RegularizerR1CombinatorialToR1Continuous.java)
 <i>RegularizerR1CombinatorialToR1Continuous</i> computes the Structural Loss and Risk for the specified
 Normed R<sup>1</sup> Combinatorial To Normed R<sup>1</sup> Continuous Learning Function.

 * [***RegularizerR1ContinuousToR1Continuous***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/regularization/RegularizerR1ContinuousToR1Continuous.java)
 <i>RegularizerR1ContinuousToR1Continuous</i> computes the Structural Loss and Risk for the specified
 Normed R<sup>1</sup> Continuous To Normed R<sup>1</sup> Continuous Learning Function.

 * [***RegularizerR1ToR1***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/regularization/RegularizerR1ToR1.java)
 <i>RegularizerR1ToR1</i> computes the Structural Loss and Risk for the specified Normed R<sup>1</sup> To
 Normed R<sup>1</sup> Learning Function.

 * [***RegularizerRdCombinatorialToR1Continuous***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/regularization/RegularizerRdCombinatorialToR1Continuous.java)
 <i>RegularizerRdCombinatorialToR1Continuous</i> computes the Structural Loss and Risk for the specified
 Normed R<sup>d</sup> Combinatorial To Normed R<sup>1</sup> Continuous Learning Function.

 * [***RegularizerRdContinuousToR1Continuous***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/regularization/RegularizerRdContinuousToR1Continuous.java)
 <i>RegularizerRdContinuousToR1Continuous</i> computes the Structural Loss and Risk for the specified
 Normed R<sup>d</sup> Continuous To Normed R<sup>1</sup> Continuous Learning Function.

 * [***RegularizerRdToR1***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/regularization/RegularizerRdToR1.java)
 <i>RegularizerRdToR1</i> computes the Structural Loss and Risk for the specified Normed R<sup>d</sup> To
 Normed R<sup>1</sup> Learning Function.


## References

 * Alon, N., S. Ben-David, N. Cesa Bianchi, and D. Haussler (1997): Scale-sensitive Dimensions, Uniform
 Convergence, and Learnability <i>Journal of Association of Computational Machinery</i> <b>44 (4)</b> 615-631

 * Anthony, M., and P. L. Bartlett (1999): <i>Artificial Neural Network Learning - Theoretical
 Foundations</i> <b>Cambridge University Press</b> Cambridge, UK

 * Kearns, M. J., R. E. Schapire, and L. M. Sellie (1994): <i>Towards Efficient Agnostic Learning</i> Machine
 Learning <b>17 (2)</b> 115-141

 * Lee, W. S., P. L. Bartlett, and R. C. Williamson (1998): The Importance of Convexity in Learning with
 Squared Loss <i>IEEE Transactions on Information Theory</i> <b>44</b> 1974-1980

 * Vapnik, V. N. (1998): <i>Statistical learning Theory</i> <b>Wiley</b> New York


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
