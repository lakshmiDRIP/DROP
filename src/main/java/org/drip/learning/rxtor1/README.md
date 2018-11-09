# DROP Learning R<sup>x</sup> -> R<sup>1</sup> Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Learning Kernel Regularization R<sup>x</sup> -> R<sup>1</sup> the Suite of Statistical Learning Empirical Loss Penalizers.

## Class Components

 * [***ApproximateLipschitzLossLearner***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/rxtor1/ApproximateLipschitzLossLearner.java)
 <i>ApproximateLipschitzLossLearner</i> implements the Learner Class that holds the Space of Normed
 R<sup>d</sup> To Normed R<sup>1</sup> Learning Functions for the Family of Loss Functions that are
 "approximately" Lipschitz, i.e.,
 
 				loss (ep) - loss (ep') Less Than max (C * |ep-ep'|, C')

 * [***EmpiricalLearningMetricEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/rxtor1/EmpiricalLearningMetricEstimator.java)
 <i>EmpiricalLearningMetricEstimator</i> is the Estimator of the Empirical Loss and Risk, as well as the
 corresponding Covering Numbers.

 * [***EmpiricalPenaltySupremum***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/rxtor1/EmpiricalPenaltySupremum.java)
 <i>EmpiricalPenaltySupremum<i> holds the Learning Function that corresponds to the Empirical Supremum, as
 well as the corresponding Supremum Value.

 * [***EmpiricalPenaltySupremumEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/rxtor1/EmpiricalPenaltySupremumEstimator.java)
 <i>EmpiricalPenaltySupremumEstimator</i> contains the Implementation of the Empirical Penalty Supremum
 Estimator dependent on Multivariate Random Variables where the Multivariate Function is a Linear Combination
 of Bounded Univariate Functions acting on each Random Variate.

 * [***EmpiricalPenaltySupremumMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/rxtor1/EmpiricalPenaltySupremumMetrics.java)
 <i>EmpiricalPenaltySupremumMetrics</i> computes Efron-Stein Metrics for the Penalty Supremum R<sup>x</sup>
 To R<sup>1</sup> Functions.

 * [***GeneralizedLearner***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/rxtor1/GeneralizedLearner.java)
 <i>GeneralizedLearner</i> implements the Learner Class that holds the Space of Normed R<sup>x</sup> To
 Normed R<sup>1</sup> Learning Functions along with their Custom Empirical Loss. Class-Specific Asymptotic
 Sample, Covering Number based Upper Probability Bounds and other Parameters are also maintained.

 * [***L1LossLearner***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/rxtor1/L1LossLearner.java)
 <i>L1LossLearner</i> implements the Learner Class that holds the Space of Normed R<sup>x</sup> To Normed
 R<sup>1</sup> Learning Functions that employs L<sub>1</sub> Empirical Loss Routine. Class-Specific
 Asymptotic Sample, Covering Number based Upper Probability Bounds and other Parameters are also maintained.

 * [***LipschitzLossLearner***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/rxtor1/LipschitzLossLearner.java)
 <i>LipschitzLossLearner</i> implements the Learner Class that holds the Space of Normed R<sup>1</sup> To
 Normed R<sup>1</sup> Learning Functions for the Family of Loss Functions that are Lipschitz, i.e.,

 				loss (ep) - loss (ep') Less Than C * |ep-ep'|

 * [***LpLossLearner***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/rxtor1/LpLossLearner.java)
 <i>LpLossLearner</i> implements the Learner Class that holds the Space of Normed R<sup>x</sup> To Normed
 R<sup>1</sup> Learning Functions for the Family of Loss Functions that are Polynomial, i.e.,

 				loss (eta) = (eta ^ p) / p,  for p greater than 1.

 This is Lipschitz, with a Lipschitz Slope of

 				C = (b - a) ^ (p - 1).


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
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
