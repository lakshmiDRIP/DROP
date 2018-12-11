# DROP Learning Bound Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Learning Bound Package implements Covering Numbers, Concentration, Lipschitz Bounds.


## Class Components

 * [***CoveringNumberBoundBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/bound/CoveringNumberBoundBuilder.java)
 <i>CoveringNumberBoundBuilder</i> constructs the CoveringNumberProbabilityBound Instances for specific
 Learning Situations.

 * [***CoveringNumberLossBound***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/bound/CoveringNumberLossBound.java)
 <i>CoveringNumberLossBound provides</i> the Upper Probability Bound that the Loss/Deviation of the Empirical
 from the Actual Mean of the given Learner Class exceeds 'epsilon', using the Covering Number Generalization
 Bounds. This is expressed as

  						C1 (n) * N (epsilon, n) * exp (-n.epsilon^b/C2)

 where:
 	* n is the Size of the Sample
 	* 'epsilon' is the Deviation Empirical Mean from the Population Mean
 	* C1 (n) is the sample coefficient function
 	* C2 is an exponent scaling constant
 	* 'b' an exponent ((i.e., the Epsilon Exponent) that depends on the setting (i.e.,
 		agnostic/classification/regression/convex etc)

 * [***DiagonalOperatorCoveringBound***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/bound/DiagonalOperatorCoveringBound.java)
 <i>DiagonalOperatorCoveringBound</i> implements the Behavior of the Bound on the Covering Number of the
 Diagonal Scaling Operator. The Asymptote is set as either

 				log [e_n(A)] ~ O {(1/log n)^alpha}

 		- OR -

 					  e_n(A) ~ O {(1/log n)^alpha}

 * [***EmpiricalLearnerLoss***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/bound/EmpiricalLearnerLoss.java)
 <i>EmpiricalLearnerLoss</i> Function computes the Empirical Loss of a Learning Operation resulting from the
 Use of a Learning Function in Conjunction with the corresponding Empirical Realization.

 * [***LipschitzCoveringNumberBound***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/bound/LipschitzCoveringNumberBound.java)
 <i>LipschitzCoveringNumberBound</i> contains the Upper Bounds of the Covering Numbers induced by Lipschitz
 and approximate Lipschitz Loss Function Class.

 * [***EmpiricalLearnerLoss***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/bound/EmpiricalLearnerLoss.java)
 <i>MeasureConcentrationExpectationBound</i> provides the Upper Bound of the Expected Loss between Empirical
 Outcome and the Prediction of the given Learner Class using the Concentration of Measure Inequalities. This
 is expressed as C n<sup>a</sup>, where n is the Size of the Sample, and 'C' and 'a' are Constants specific
 to the Learning Class.


## References

 * Alon, N., S. Ben-David, N. Cesa Bianchi, and D. Haussler (1997): Scale-sensitive Dimensions, Uniform
 Convergence, and Learnability <i>Journal of Association of Computational Machinery</i> <b>44 (4)</b> 615-631

 * Anthony, M., and P. L. Bartlett (1999): <i>Artificial Neural Network Learning - Theoretical
 Foundations</i> <b>Cambridge University Press</b> Cambridge, UK

 * Ash, R. (1965): <i>Information Theory</i> Inter-science</b> New York

 * Bartlett, P. L., P. Long, and R. C. Williamson (1996): Fat-shattering and the Learnability of Real Valued
 Functions <i>Journal of Computational System Science</i> <b>52 (3)</b> 434-452

 * Boucheron, S., G. Lugosi, and P. Massart (2003): Concentration Inequalities Using the Entropy Method
 <i>Annals of Probability</i> <b>31</b> 1583-1614

 * Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and Approximation of Operators</i>
 <b>Cambridge University Press</b> Cambridge UK

 * Gordon, Y., H. Konig, and C. Schutt (1987): Geometric and Probabilistic Estimates of Entropy and
 Approximation Numbers of Operators <i>Journal of Approximation Theory</i> <b>49</b> 219-237

 * Kearns, M. J., R. E. Schapire, and L. M. Sellie (1994): <i>Towards Efficient Agnostic Learning</i> Machine
 Learning <b>17 (2)</b> 115-141

 * Konig, H. (1986): <i>Eigenvalue Distribution of Compact Operators</i> <b>Birkhauser</b> Basel, Switzerland

 * Lee, W. S., P. L. Bartlett, and R. C. Williamson (1998): The Importance of Convexity in Learning with
 Squared Loss <i>IEEE Transactions on Information Theory</i> <b>44</b> 1974-1980

 * Lugosi, G. (2002): Pattern Classification and Learning Theory, in: <i>L. Gyor, editor, Principles of
 Non-parametric Learning</i> <b>Springer</b> Wien 5-62

 * Smola, A. J., A. Elisseff, B. Scholkopf, and R. C. Williamson (2000): Entropy Numbers for Convex
 Combinations and mlps, in: <i>Advances in Large Margin Classifiers, A. Smola, P. Bartlett, B. Scholkopf, and
 D. Schuurmans - editors</i> <b>MIT Press</b> Cambridge, MA

 * Vapnik, V. N. (1998): <i>Statistical learning Theory</i> <b>Wiley</b> New York

 * Williamson, R. C., A. J. Smola, and B. Scholkopf (2001): Generalization Performance of Regularization
 Networks and Support Vector Machines via Entropy Numbers of Compact Operators <i>IEEE Transactions on
 Information Theory</i> <b>47 (6)</b> 2516-2532


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
