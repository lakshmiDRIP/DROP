
# Exposure Analytics Library


<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

Exposure Analytics Library computes the Scenario Exposures at the specified Trade Group Granularity.


## Documentation

 |        Document         | Link |
 |-------------------------|------|
 | Technical Specification | [*Latest*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/Exposure/ExposureAnalytics_v3.99.pdf) [*Previous*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/Exposure) |
 | User Guide              |  |
 | API                     | [*Javadoc*](https://lakshmidrip.github.io/DROP/Javadoc/index.html)|


## Component Projects

 * *Exposure* => Exposure Group Level Collateralized/Uncollateralized Exposure.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aexposure) }
 * *SIMM* => Initial Margin Analytics based on ISDA SIMM and its Variants.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Asimm) }


## Coverage

 * Modeling Counterparty Credit Exposure in the Presence of Margin Agreements
	* Abstract
	* Margin Agreements as a Means of Reducing Counterparty Credit Exposure
	* Collateralized Exposure and Margin Period of Risk
	* Semi-Analytical Method for Collateralized EE
	* Analysis of Basel “Shortcut” Method for Collateralized Effective EPE
	* Conclusion
	* References
 * Estimation of Margin Period of Risk
	* Abstract
	* Introduction
	* The Fundamentals of Variation Margin: Basic Definitions
	* Margin Calls and Cash Flows
	* Revised Exposure Definition
	* Classical Model for Collateralized Exposure – Assumptions about Margin Flows
	* Assumptions about Trade Flows
	* Full Timeline of IMA/CSA Events
	* Events Prior to Default
	* Some Behavioral and Legal Aspects
	* Simplified Timeline of IMA/CSA Events
	* Identification of Key Time Periods
	* Establishing the Sequence of Events
	* Evaluation of the Client Survival Probability
	* Timeline Calibration
	* Aggressive Calibration
	* Conservative Calibration
	* Summary and Comparison of Timelines
	* Unpaid Margin Flows and Margin Flow Gap
	* Unpaid Trade Flows and Trade Flow Gap
	* Numerical Examples
	* Portfolio Results
	* CVA Results
	* Improvement of the Computation Times
	* The Coarse Grid Lookback Method and its Shortcomings
	* Brownian Bridge Method
	* Initial Margin
	* Conclusion
	* References
 * Regression Sensitivities in Margin Calculations
	* Abstract
	* Methodology
	* References
 * Principles Behind ISDA SIMM Specification
	* Introduction
	* Background
	* Objective
	* Criteria
	* Modeling Constraints
	* Selecting the Model Specification
	* Scanning the Existing Industry Solutions
	* SIMM Specification
	* Non Procyclicality
	* Data Needs, Costs, and Maintenance
	* Transparency and Implementation Costs
	* Evolution of SIMM Through the Regulatory Process
	* SIMM and the Nested Variance/Covariance Formulas
	* Rationale Behind the Nested Sequence Approach
	* Explicit Expression for S<sub>a</sub>
	* FRTB Approximation
	* SIMM Approximation
	* Testing the Approximations
	* Explicit Large Correlation Matrix
	* Proof that the Elements of the Eigenvectors are smaller than One in Magnitude
	* Numerical Example – Global Interest Rate Risk (GIRR)
	* SIMM Curvature Formulas – Introduction
	* ISDA SIMM Curvature Formula
	* Numerical Tests
	* References
 * ISDA SIMM Methodology
	* Contextual Considerations
	* General Provisions
	* Definition of the Interest Rate Risk
	* Definition of Sensitivity for Delta Margin Calculation
	* Interest Rate Risk Weight
	* Credit Qualifying: Risk Weights
	* Credit Qualifying: Correlations
	* Credit Non-Qualifying Risk
	* Credit Non-Qualifying Correlations
	* Equity Risk Weights
	* Equity Correlations
	* Commodity Risk Weights
	* Commodity Correlations
	* Foreign Exchange Risk
	* Concentration Thresholds
	* Additional Initial Margin Expressions
	* Structure of the Methodology
	* Interest Rate Risk Delta Margin
	* Non Interest Rate Risk Classes
	* References
 * Dynamic Initial Margin Impact on Exposure
	* Abstract
	* Introduction
	* Exposure in the Presence of IM and VM
	* Modeling VM
	* Modeling U
	* Modeling IM
	* Summary and Calibration
	* The Impact of IM: No Trade Flows within the MPoR
	* Local Gaussian Approximation
	* Numerical Tests
	* The Impact of IM: Trade Flows within the MPoR
	* Expected Exposure – Numerical Example #1
	* Expected Exposure – Numerical Example #2
	* The Impact of IM on CVA
	* Numerical Techniques – Daily Time Grid
	* Calculation of the Path-wise IM
	* Calculation of the Path-wise Exposure
	* Numerical Example
	* Conclusion
	* References
 * CCP and SIMM Initial Margin
	* Initial Margin
	* CCP IM
	* Interest Rate Swap Methodology
	* Interest Rate Swap Calculation
	* Credit Default Swap Methodology
	* SIMM
	* MVA
	* Summary
 * Basel III
	* Overview
	* Key Principles - Capital Requirements
	* Key Principles - Leverage Ratio
	* Liquidity Requirements
	* US Version of the Basel Liquidity Coverage Ratio Requirements
	* Summary of Originally Proposed Changes (2010) in the Basel Committee Language
	* US Implementation
	* Europe Implementation
	* Key Milestones
	* References
 * Basel III Framework for Backtesting Exposure Models
	* Abstract
	* Introduction
	* Basic Concepts and the Need for Backtesting
	* Regulatory Guidances
	* RF Backtesting: The Backtesting Construction for Collateralized and Uncollateralized Models
	* Discriminatory Power RF Backtesting
	* The Aggregation of Backtesting Results
	* Correlations Backtesting
	* Portfolio Backtesting
	* Capital Buffer Calculation
	* Conclusion
	* References
 * Initial Margin Backtesting Framework
	* Abstract
	* Introduction
	* How to Construct a DIM Model
	* How to Back Test a DIM Model
	* Backtesting DIM Mapping Functions (for Capital Exposure and CVA)
	* Backtesting the IMRD for MVA and LCR/NSFR
	* Conclusion
	* References


## DROP Specifications

 * Main                     => https://lakshmidrip.github.io/DROP/
 * Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * GitHub                   => https://github.com/lakshmiDRIP/DROP
 * Repo Layout Taxonomy     => https://lakshmidrip.github.io/DROP/Taxonomy.md
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
