
# Asset Allocation Analytics Library


<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

Asset Allocation Analytics Library implements Optimal Portfolio Construction and Asset Allocation Functionality.


## Documentation

 |        Document         | Link |
 |-------------------------|------|
 | Technical Specification | [*Latest*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/AssetAllocationAnalytics/AssetAllocationAnalytics_v6.75.pdf) [*Previous*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/AssetAllocationAnalytics) |
 | User Guide              |  |
 | API                     | [*Javadoc*](https://lakshmidrip.github.io/DROP/Javadoc/index.html)|


## Component Projects

 * *Portfolio Construction* => Optimal and Constrained Portfolio Construction Functionality.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aportfolioconstruction) }


## Coverage

 * Modern Portfolio Theory
	* Introduction, Background, Focus, and Motivation
	* Mathematical Model
	* Asset Pricing
	* Criticisms
	* Other Applications
	* References
 * Factor Investing
	* Introduction
	* Value Factor
	* Low-volatility Factor
	* Momentum Factor
	* References
 * Fama-French Three-Factor Model
	* Introduction
	* Background and Development
	* Discussion
	* Fama-French Five-Factor Model
	* References
 * Carhart Four-Factor Model
	* Introduction
	* Development
	* References
 * The Black Litterman Model
	* Overview
	* Introduction
	* Historical Taxonomy amd Literature Survey
	* Canonical Black Litterman Reference Model
	* Computing the Equilibrium Returns
	* Specifying the Views
	* View Distribution in the Asset Space - Derivation
	* Specifying \Omega
	* \Omega Proportional to the Variance of the Prior
	* Using Confidence Interval for \Omega
	* \Omega as a Residual of Variances from a Factor Model
	* Using Idzorek Method for \Omega
	* The Estimation Model
	* Theil's Mixed Estimation Model
	* A Quick Introduction to Bayes' Theorem
	* The PDF Based Approach
	* Using Bayes' Theorem for the Estimation Model
	* The Alternate Reference Model
	* The Impact of \tau
	* Calibration of \tau
	* Black Litterman Model Implementation Steps
	* Extensions to the Black Litterman Model
	* References
 * The Intuition behind Black Litterman Model Portfolios
	* Purpose, Drivers, and Primary Motivation
	* Analyzing the Unconstrained Optimal Portfolio
	* Impact of an Incremental Projection
	* Projection Distribution Dependence on Parameters
	* Black Litterman Intuition Numerical Examples
	* References
 * Incorporating User Specified Confidence Levels
	* Overview
	* Introduction
	* Estimating the Excess Returns Distribution
	* Reverse Optimization of Expected Returns
	* The Black Litterman Model
	* Building the Inputs
	* Fine Tuning the Model
	* Method for Incorporating User-Specified Confidence Levels
	* Implied Confidence Levels
	* The Tilt Based Intuitive Approach
	* References
 * Simplified Black Litterman Surplus Optimizer
	* Background
	* Black Litterman Surplus Optimizer Inputs
	* Cash Flow Projections and Liability Returns
	* References
 * Multiple Security Portfolios Optimal Trajectory
	* Trading Model
	* Optimal Trajectories
	* Explicit Solution for a Diagonal Model
	* Almgren and Chriss (2000) Example
	* References
 * Asset Allocation Workspace Objects
	* Allocation Workspace
	* Allocation Workspace -> Account
	* Allocation Workspace -> Risk Model
	* Allocation Workspace -> Alpha Group
	* Allocation Workspace -> Alpha Uncertainty Group
	* Allocation Workspace -> Transaction Cost Model
	* Allocation Workspace -> Transaction Cost Model Group
	* Allocation Workspace -> Benchmark
	* Allocation Workspace -> Attribute
	* Allocation Workspace -> Classification Model
	* Allocation Workspace -> Tax Accounting Scheme
	* Allocation Workspace -> Strategy
	* Allocation Workspace -> Strategy -> Options
	* Allocation Workspace -> Strategy -> Objective Group
	* Allocation Workspace -> Strategy -> Constraint
	* Allocation Workspace -> Rebalancing
 * Asset Allocation Objective Terms
	* Symbology
	* Net Tilt Objective Term
	* Tilt Long Objective Term
	* Tilt Short Objective Term
	* Net Tax Gains Objective Term
	* Custom Net Tax Gains Objective Term
	* Tax Liability Objective Term
	* Risk (Standard Deviation) Objective Term
	* Variance Objective Term
	* MVO (Expected Returns) Objective Term
	* Robust Objective Term
	* Fixed Charge Buy Cost Objective Term
	* Fixed Charge Sell Cost Objective Term
	* Fixed Charge Transaction Cost Objective Term
	* Goldman Sachs Shortfall Objective Term
	* Linear Buy Objective Term
	* Linear Sell Objective Term
	* Linear Transaction Cost Objective Term
	* Market Impact Objective Term
	* Short Sell Cost Objective Term
	* Transaction Cost Objective Term
 * Asset Allocation Constraint Terms
	* Limit Absolute Exposure Constraint Term
	* Limit Net Exposure Constraint Term
	* Limit Net Issuer Exposure Constraint Term
	* Limit Issuer Long Exposure Constraint Term
	* Limit Issuer Short Exposure Constraint Term
	* Budget Constraint Term
	* After Transaction Constraint Term
	* Limit Absolute Holdings Constraint Term
	* Limit Net Holdings Constraint Term
	* Limit Issuer Net Holdings Constraint Term
	* Limit Issuer Long Holdings Constraint Term
	* Limit Issuer Short Holdings Constraint Term
	* Limit Long/Short Holdings Constraint Term
	* Limit Weighted Average Holdings Constraint Term
	* Limit Model Deviation Constraint Term
	* Limit Minimum Holding Period Constraint Term
	* Limit Threshold Net Holding Constraint Term
	* Limit Threshold Long Holding Constraint Term
	* Limit Threshold Short holding Constraint Term
	* Limit Total Names Constraint Term
	* Limit Long Names Constraint Term
	* Limit Short Names Constraint Term
	* Limit Number of Trades Constraint Term
	* Limit Number of Buy Trades Constraint Term
	* Limit Number of Sell Trades Constraint Term
	* Limit Risk Constraint Term
	* Limit Relative Marginal Contribution To Risk Constraint Term
	* Limit Almost Long-Term Gains Constraint Term
	* Limit Gross Tax Gains Constraint Term
	* Limit Gross Tax Loss Constraint Term
	* Limit Net Tax Loss Constraint Term
	* Limit Net Gain Constraint Term
	* Limit Tax Liability Constraint Term
	* Limit Buy Constraint Term
	* Limit Sell Constraint Term
	* Limit Short Sell Constraint Term
	* Limit Trade Constraint Term
	* Limit Issuer Trade Constraint Term
	* Limit Turnover Constraint Term
	* Limit Transaction Cost Constraint Term
	* Limit Goldman Sachs Shortfall Constraint Term
	* Limit Number of Threshold Buy Trades Constraint Term
	* Limit Number of Threshold Sell Trades Constraint Term
	* Limit Number of Threshold Trades Constraint Term
 * Portfolio Optimization Service
	* Introduction
	* Sleeve
	* Sell Only Options
	* Universe
	* Groups
	* Simple Groups
	* Multiple Unknown Simple Groups
	* Multiple Simple Groups from Model Entity
	* Complex Groups
	* Adjustments
	* Constraints
	* Rounding
	* Minimum Trade Percent
	* Targets
	* Trade Filter
 * Muni Cash Raise Enhancement
	* Rebalancer Target - Maximize Book Yield
	* Rebalancer Constraints
	* Priority Sells
	* Workflow


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
