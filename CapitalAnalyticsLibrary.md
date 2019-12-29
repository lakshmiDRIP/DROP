
# Capital Analytics Library


<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

Capital Analytics Library computes the Economic Risk Capital and Basel Operational Capital Analytics.


## Documentation

 |        Document         | Link |
 |-------------------------|------|
 | Technical Specification | [*Latest*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/CapitalAnalytics/CapitalAnalytics_v4.63.pdf) [*Previous*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/CapitalAnalytics) |
 | User Guide              |  |
 | API                     | [*Javadoc*](https://lakshmidrip.github.io/DROP/Javadoc/index.html)|


## Component Projects

 * *Capital* => Basel Market Risk and Operational Capital Analytics.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Acapital) }


## Coverage

 * Basel II
	* Overview
	* Objective
	* The Accord in Operation: Three Pillars
	* The First Pillar: Minimum Capital Requirements
	* The Second Pillar: The Supervisory Review
	* The Third Pillar: Market Discipline
	* Chronological Updates
	* References
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
 * VaR and Stress Methodology – Integration and Testing
	* Objectives of Risk Capital Estimation
	* BHC Risk Capital – Principles
	* Market Risk Capital – Coverage and History
	* Enhanced Risk Capital Framework
	* Step #1 – VaR at 99.97% at 1Y Horizon
	* Step #2 – Global Systemic Stress Testing (GSST)
	* Credit Risk – The Anchor Set for GSST
	* Historical Credit Spread Studies
	* Scenario Design – Analyzing Patterns
	* Top 10 Credit Spread Events
	* Formulating GSST Scenarios
	* Step #3 – Business Specific Stress Tests
	* VaR-Stress integrates VaR and Stress Results
	* Illustration of VaR-Stress Process
	* Allocation of Total Risk Capital to each Business
	* Conclusion
	* GSST Stress Scenario Definitions
 * Integrated VaR and Stress Testing Risk Capital Methodology
	* Executive Summary
	* Model Scope, Purpose, and Functional Soundness
	* Functional Soundness Governance
	* Basis behind the Modeling Approach
	* Technical Soundness Considerations
	* Model Performance Testing and Outcomes Analysis
 * Integrated VaR and Stress Testing Risk Capital Methodology Validation
	* Brief Description of the Purpose of this Model
	* General Modeling Approach
	* Approximations or Algorithms
	* Brief Description of the Main Assumptions Underlying the Model
	* General Review
	* Alternative Approaches
	* Limitations of the General Modeling Framework
	* Limitations of any Particular Algorithms or Approximations
	* Cases Used in Testing
	* Error Analysis/Convergence Testing
	* Stress Testing
	* Benchmarking
	* Sensitivity Analysis
	* Conclusions
	* Model Description
	* Error Analysis/Convergence Testing
	* Conclusion
	* Benchmarking
	* References
 * Trading Risk Capital Beta Allocation
	* Allocation Methodology
	* Allocation Approach and Available Inputs
	* Allocation Algorithm for each Component
 * Two-Beta Allocation of Trading Capital
	* Executive Summary
	* Summary of Capital Allocation Approaches
	* Enhanced Approach for the Allocation
	* Incorporating Systemic Stresses
	* Old vs New Capital Allocations
	* Alternate Approaches Considered in the Past
	* Allocation Technical Details
	* Allocation Approach and Available Inputs
	* Allocation Algorithm for each Component
 * Reporting Flow
	* Overview
	* P&L Data Repository
	* Reporting
 * Enhanced Business Hierarchy for VaR Stress Estimation
	* Business Hierarchy Decisions
	* Considerations when Picking Business Reporting Options
 * Comparison of Hierarchies
	* Problem Statement
	* Principles and Goals
	* Need for Defining a Structural Truth
	* Current Definitions – Capital Segment
	* Current Definitions – Volcker
	* Example – Capital Markets Origination Business

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
