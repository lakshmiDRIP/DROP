
<p align="center"><img src="https://github.com/lakshmiDRIP/DRIP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

**v2.63**  1 March 2017

DRIP Asset Allocation is a collection of model libraries for MPT framework, Black Litterman Strategy Incorporator, Holdings Constraint, and Transaction Costs.

DRIP Asset Allocation is composed of the following main model libraries:

* MPT Framework Model Library
* Black Litterman Model Library
* Holdings Constraint Model Library
* Transaction Cost Model Library

For Installation, Documentation and Samples, and the associated supporting Numerical Libraries please check out [DRIP](https://github.com/lakshmiDRIP/DRIP).


<H3>DRIP Core Technical Specifications</H3>

 * [Asset Allocation Library](https://github.com/lakshmiDRIP/DRIP/tree/master/Docs/DRIPSpecification/AssetAllocation/AssetAllocation_v2.56.pdf)
 * [Fixed Income Analytics](https://github.com/lakshmiDRIP/DRIP/tree/master/Docs/DRIPSpecification/FixedIncome/FixedIncomeAnalytics_v2.58.pdf)
 * [Transaction Cost Analytics](https://github.com/lakshmiDRIP/DRIP/tree/master/Docs/DRIPSpecification/TransactionCost/TransactionCostAnalytics_v2.57.pdf)
 * [XVA Analytics](https://github.com/lakshmiDRIP/DRIP/tree/master/Docs/DRIPSpecification/XVA/XVAAnalytics_v2.62.pdf)


<H3>DRIP Supporting Technical Specifications</H3>

 * [Spline Builder Library](https://github.com/lakshmiDRIP/DRIP/tree/master/Docs/DRIPSpecification/SplineBuilder/SplineBuilder_v0.82.pdf)
 * [Numerical Optimization Library](https://github.com/lakshmiDRIP/DRIP/tree/master/Docs/DRIPSpecification/NumericalOptimizer/NumericalOptimization_v2.05.pdf)
 * [Statistical Learning Library](https://github.com/lakshmiDRIP/DRIP/tree/master/Docs/DRIPSpecification/StatisticalLearning/StatisticalLearningLibrary_v0.80.pdf)
 * [Machine Learning Library](https://github.com/lakshmiDRIP/DRIP/tree/master/Docs/DRIPSpecification/MachineLearning/MachineLearningLibrary_v0.92.pdf)


<H3>Additional Documentation</H3>

 * [DRIP GitHub Source](https://github.com/lakshmiDRIP/DRIP)
 * [DRIP API Javadoc](https://lakshmidrip.github.io/DRIP/Javadoc/index.html)
 * [DRIP Release Notes](https://github.com/lakshmiDRIP/DRIP/tree/master/ReleaseNotes)
 * [DRIP Technical Specifications](https://github.com/lakshmiDRIP/DRIP/tree/master/Docs/DRIPSpecification)
 * [DRIP External Specifications](https://github.com/lakshmiDRIP/DRIP/tree/master/Docs/External)
 * User guide is a work in progress!


<H3>Samples (Asset Allocation)</H3>

 * [Asset Liability Management](https://github.com/lakshmiDRIP/DRIP/tree/master/org/drip/sample/alm)
 * [Asset Allocation Core](https://github.com/lakshmiDRIP/DRIP/tree/master/org/drip/sample/assetallocation)
 * [Asset Allocation Excel](https://github.com/lakshmiDRIP/DRIP/tree/master/org/drip/sample/assetallocationexcel)
 * [Black Litterman Core](https://github.com/lakshmiDRIP/DRIP/tree/master/org/drip/sample/blacklitterman)
 * [Efficient Frontier MPT](https://github.com/lakshmiDRIP/DRIP/tree/master/org/drip/sample/efficientfrontier)
 * [He Litterman Intuition](https://github.com/lakshmiDRIP/DRIP/tree/master/org/drip/sample/helitterman)
 * [Idzorek User Confidence](https://github.com/lakshmiDRIP/DRIP/tree/master/org/drip/sample/idzorek)
 * [Asset Allocation Core](https://github.com/lakshmiDRIP/DRIP/tree/master/org/drip/sample/assetallocation)


<H3>Samples (Transaction Cost)</H3>

 * [Mean-Variance Execution](https://github.com/lakshmiDRIP/DRIP/tree/master/org/drip/sample/almgrenchriss)
 * [Nonlinear Impact Function](https://github.com/lakshmiDRIP/DRIP/tree/master/org/drip/sample/almgren2003)
 * [Market Impact Estimator](https://github.com/lakshmiDRIP/DRIP/tree/master/org/drip/sample/athl)
 * [Optimal Numerical Execution](https://github.com/lakshmiDRIP/DRIP/tree/master/org/drip/sample/execution)
 * [Liquidity VaR Objective](https://github.com/lakshmiDRIP/DRIP/tree/master/org/drip/sample/lvar)
 * [Principal Agency Execution](https://github.com/lakshmiDRIP/DRIP/tree/master/org/drip/sample/principal)
 * [Bayesian Trend Drift Analysis](https://github.com/lakshmiDRIP/DRIP/tree/master/org/drip/sample/trend)
 * [HJB Based Adaptive Trajectories](https://github.com/lakshmiDRIP/DRIP/tree/master/org/drip/sample/almgren2009)
 * [Adaptive, Static, and Rolling Horizon Trajectories](https://github.com/lakshmiDRIP/DRIP/tree/master/org/drip/sample/almgren2012)


<H3>Features</H3>

<H4>MPT Framework Model Library</H4>

 * MPT Core Mathematical Model
 * CAPM Asset Pricing

<H4>Black Litterman Model Library</H4>

<H4>The Black Litterman Model</H4>

 * Canonical Black-Litterman Reference Model
 * Computing the Equilibrium Returns
 * Specifying the Views
 * View Distribution in the Asset Space
 * Specifying Omega
 * Omega Proportional to the Variance of the Prior
 * Using Confidence Inteval for Omega
 * Omega as the Variance of Residuals from a Factor Model
 * Using Idzorek's Method for Omega
 * The Estimation Model
 * Theil's Mixed Estimation Model
 * The PDF Based Approach
 * Using Bayes' Theorem for the Estimation Model
 * The Alternate Reference Model
 * The Impact of Tau
 * Calibration of Tau
 * Black Litterman Model Implementation Steps
 * Extensions to the Black Litterman Model

<H4>Black Litterman Model Attributions</H4>

 * Analysis of the Unconstrained Optimal Portfolio
 * Impact of an Incremental Projection
 * Projection Distribution Dependence on Parameters
 * Black Litterman Intuition Numerical Examples

<H4>Incorporating User Specified Confidence Levels</H4>

 * Estimating the Excess Returns Distribution
 * Reverse Optimization of Expected Returns
 * The Black Litterman Model
 * Building the Inputs
 * Fine Tuning the Model
 * Method for Incorporating User-Specified Confidence Levels
 * Implied Confidence Levels
 * The Tilt-Based Intuitive Approach

<H4>Simplified Black Litterman Surplus Optimizer</H4>

 * Black Litterman Surplus Optimizer Inputs
 * Cash Flow Projections and Liability Returns

<H3>Transaction Costs Model Library</H3>

<H4>Execution of Portfolio Transactions - Optimal Trajectory</H4>

 * Defining a Trading Strategy
 * Price Dynamics
 * Temporary Market Impact
 * Capture and Cost of Trading Trajectories
 * Linear Impact Functions
 * The Efficient Frontier of Optimal Execution
 * The Definition of the Frontier
 * Explicit Construction of Optimal Strategies
 * The Half-Life of a Trade
 * Structure of the Frontier
 * The Utility Function
 * Value-at-Risk
 * The Role of Utility in Execution
 * Choice of Parameters
 * The Value of Information
 * Drift
 * Gain due to Drift
 * Serial Correlation
 * Parameter Shifts
 * Numerical Optimal Trajectory Generation

<H4>Non-linear Impact and Trading-Enhanced Risk</H4>

 * The Model
 * Nonlinear Cost Functions
 * Objective Functions
 * Almgren (2003) Example
 * Trading-Enhanced Risk
 * Constant-Enhanced Risk
 * Linear-Enhanced Risk
 * Almgren (2003) Nonlinear Example Sample

<H4>Market Impact Function/Parameters Estimation</H4>

 * Data Description and Filtering Rules
 * Data Model - Variables
 * Trajectory Cost Model
 * Permanent Impact
 * Temporary Impact
 * Choice of the Functional Form
 * Cross-Sectional Description
 * Model Determination
 * Determination of the Coefficients
 * Residual Analysis

<H4>Optimal Execution of Program Trades</H4>

 * Efficient Frontier Pricing of Program Trades
 * The Efficient Frontier Including Discount
 * Performance Measures
 * Annualization
 * Definition of the Information Ratio
 * Applications of the Information Ratio

<H4>Bayesian Trading with a Daily Trend</H4>

 * Price Motion Using Bayesian Update
 * Bayesian Inference
 * Trading and Price Impact
 * Optimal Trading Strategies
 * Trajectory by Calculus of Variations
 * Optimality of the Bayesian Adaptive Strategy
 * Stochastic Optimal Control Treatment

<H4>Cost Adaptive Arrival Price Trading</H4>

 * Adaptive Strategies - Trading in Practice
 * AIM/PIM, and Prospect Theory
 * Market Model and Static Trajectories
 * Non-dimensionalization
 * Small Portfolio Limit
 * Portfolio Comparison
 * Single Update - Mean and Variance
 * Almgren and Lorenz (2007) Results Replication
 * Continuous Response - Numerical Results Comparison

<H4>Optimal Trading in a Dynamic Market</H4>

 * Limitations of Arrival Price Frameworks
 * The Liquidation Problem
 * The Cost of Trading
 * Constant Coefficients
 * Coordinated Variation
 * Rolling Time Horizon Approximation Strategy
 * Small Impact Approximation
 * Dynamic Programming - Fully Co-ordinated Version
 * Log-Normal Model and Non-dimensionalization
 * Constant Market
 * Long Time Asymptote
 * Dynamic Programming - Custom Volatility and Liquidity
 * Log-normal Volatility/Liquidity
 * Coordinated Variation of Volatility and Liquidity
 * Long/Short Time Asymptotic Behavior
 * HJB Based Numerical Solution
 * HJB Grid Time Discretization
 * HJB Grid Space Discretization
 * Almgren (2009, 2012) Solutions Replication

<H4>Market Making Models</H4>

 * Width/Skew/Size Estimation Models
 * Market Making System SKU
 * Market Making Parameter Types
 * Intra-day Pricing Curve Generation Schemes
 * Mid-Price Models
 * Width Models
 * Skew Models
 * Size Model
 * Heuristic Controls
 * Flow Analysis


<H2>Licence</H2>

Apache 2.0


<H2>Contact</H2>

lakshmi@synergicdesign.com
