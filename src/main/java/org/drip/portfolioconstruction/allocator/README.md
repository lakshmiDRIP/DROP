# DROP Portfolio Construction Allocator Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Portfolio Construction Allocator Package implements the MVO Based Portfolio Allocation Construction.


## Class Components

 * [***BoundedPortfolioConstructionParameters***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator/BoundedPortfolioConstructionParameters.java)
 <i>BoundedPortfolioConstructionParameters</i> holds the Parameters needed to build the Portfolio with Bounds
 on the Underlying Assets.

 * [***ConstrainedMeanVarianceOptimizer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator/ConstrainedMeanVarianceOptimizer.java)
 <i>ConstrainedMeanVarianceOptimizer</i> builds an Optimal Portfolio Based on MPT Using the Asset Pool
 Statistical Properties with the Specified Lower/Upper Bounds on the Component Assets.

 * [***CustomRiskUtilitySettings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator/CustomRiskUtilitySettings.java)
 <i>CustomRiskUtilitySettings</i> contains the settings used to generate the Risk Objective Utility Function.
 It accommodates both the Risk Tolerance and Risk Aversion Variants.

 * [***EqualityConstraintSettings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator/EqualityConstraintSettings.java)
 <i>EqualityConstraintSettings</i> holds the Parameters required to generate the Mandatory
 Constraints for the Portfolio.

 * [***ForwardReverseOptimizationOutput***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator/ForwardReverseOptimizationOutput.java)
 <i>ForwardReverseOptimizationOutput</i> holds the Metrics that result from a Forward/Reverse Optimization
 Run.

 * [***MeanVarianceOptimizer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator/MeanVarianceOptimizer.java)
 <i>MeanVarianceOptimizer</i> exposes Portfolio Construction using Mean Variance Optimization Techniques.

 * [***OptimizationOutput***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator/OptimizationOutput.java)
 <i>OptimizationOutput</i> holds the Output of an Optimal Portfolio Construction Run, i.e., the Optimal Asset
 Weights in the Portfolio and the related Portfolio Metrics.

 * [***PortfolioConstructionParameters***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator/PortfolioConstructionParameters.java)
 <i>PortfolioConstructionParameters</i> holds the Parameters needed to construct the Portfolio.

 * [***QuadraticMeanVarianceOptimizer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator/QuadraticMeanVarianceOptimizer.java)
 <i>QuadraticMeanVarianceOptimizer</i> builds an Optimal Portfolio Based on MPT Using the Asset Pool
 Statistical Properties using a Quadratic Optimization Function and Equality Constraints (if any).

 * [***RiskUtilitySettingsEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator/RiskUtilitySettingsEstimator.java)
 <i>RiskUtilitySettingsEstimator</i> contains Utility Functions that help estimate the
 CustomRiskUtilitySettings Inputs Parameters.


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
