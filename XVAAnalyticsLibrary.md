
# XVA Analytics Library

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

XVA Analytics Library contains the Utilities to generate various Valuation Adjustments (Collateral VA/CVA/DVA/FBA/FCA/FVA/MVA/XVA).


## Documentation

 |        Document         | Link |
 |-------------------------|------|
 | Technical Specification | [*Latest*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/XVAAnalytics/XVAAnalytics_v3.87.pdf) [*Previous*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/XVAAnalytics) |
 | User Guide              |  |
 | API                     | [*Javadoc*](https://lakshmidrip.github.io/DROP/Javadoc/index.html)|


## Component Projects

 * *XVA* => Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Axva) }


## Coverage

 * Collateral Agreements and Derivative Valuation
	* Background
	* Introduction and Motivation
	* Two Collateralized Assets
	* Setup of the Collateral Curve Dynamics
	* Collateralized Black-Scholes Formulation
	* Collateralization and Funding Derivative Valuation
	* Collateral PDE Formulation
	* Forward Contract Valuation
	* European Style Options
	* Cross-Currency Model
	* Collateral Choice Model	
	* References
 * Cross Asset Random Number Generator
	* Introduction
	* Centralized Random Number Generator
	* Data Structures
	* Factor Model for Correlation Handling
	* Variance Reduction
	* Implementation - The Scope of the Risk Factors
	* Implementation - The Correlation Matrix
	* Testing
	* Random Number Generators
	* Multi-Stream RNG's
 * Core CVA/DVA Model
	* Abstract and Synopsys
	* Introduction
	* General Framework for CVA/DVA Pricing
	* Recovery Rate Map
	* CVA/DVA Model for Interest Rate Products
	* CVA/DVA Valuation of a Single Swap
	* Approximate Computation of the Convexity Based Adjustment for Contracts based on Averaging Index
	* Hull White Model Calibration
	* CVA/DVA of Portfolio of Swaps, Caps, Floors, and Swaptions
	* Risk Participation Swap (RPS) Valuation
	* CVA for Swaps with Prepay Risk
	* Negative Rates Distribution
	* Portfolio Specific Calibration of CVA/DVA for Interest Rates Products
	* Portfolio Construction for the Calibration
	* Portfolio NPV and NPV Option Valuation
	* Bootstrapping the Volatility Term Structure
	* Margin Period of Risk for CVA/DVA
	* Exposure Interpolation
	* CVA/DVA of MUNI FPA Product
	* CVA/DVA Model for Portfolio of Cross-Currency Swaps
	* Cross-Currency Swaps with MTM Legs for Notional Rates
	* FX Delta and Gamma Calculation and PnL
	* Negative Rate Distribution in XCCY swap CVA Model
	* CVA/DVA of Credit Products
	* An Approximate Method to combine Credit Exposure Profiles with a given Correlation
	* References
 * Cross Asset CVA Modeling, Testing, and Validation
	* Executive Summary of the Framework
	* Features of the LOB CVA Models
	* Cross Asset CVA Model Features
	* Overview
	* Overview of the Mathematical Definitions – CVA
	* Overview of the Mathematical Definitions – DVA
	* Collateral
	* Summary of Model Inputs
	* Summary of Model Outputs
	* Centralized Random Number Generator
	* Factor Model for Correlation Handling
	* Market Generation and Monte Carlo Simulation
	* General Scope of the Simulation
	* Correlations in Monte Carlo Simulations
	* Credit Risk Factors Simulation
	* FX Risk Factors Simulation
	* Equity Risk Factors Simulation
	* Commodity Risk Factors Simulation
	* IR Risk Factors Simulation
	* Simplified Stochastic Volatility Model
	* Interpolation in Monte Carlo Simulation
	* Trade Valuation
	* Sparse Grid or Sub-Sampling
	* Replication Model with Risks or Scenario Values
	* Proxy Pricer
	* Basel Notional Conversion Factor Based Proxy Pricer
	* NPV Based Proxy Pricer
	* Testing, Monitoring, Conservative Measures, and Materiality Analysis
	* Exposure Valuation and Aggregation
	* Exposure Interpolation
	* Conservative Measures
	* Portfolio Level Conservative Measure for Cross-Asset or Cross-System Portfolios
	* Monitoring and Materiality Analysis
	* CVA/DVA Valuation
	* Overview of Wrong-Way and Right-Way Risks
	* Specific Wrong-Way and Right-Way Risks
	* General Wrong-Way and Right-Way Risks
	* Extreme Wrong-Way Risks
	* Methodology Survey of WWR and RWR Models
	* Identification and Monitoring of WWR and RWR CPs
	* Mathematical Modeling of Wrong-Way and Right-Way Risks
	* A Simple WWR/RWR Model Based on the Convexity Adjustment to the CP Portfolio Value
	* A Simple WWR/RWR Model with Approximated CP Probability Distribution
	* A Simple CVA Model with Approximated CP Collateral under Stress Scenarios
	* A Simple Specific WWR and RWR Model with Approximated Exposures upon CP Default
	* Default and Market Risk Metrics
	* Martingale Resampling and Testing
	* At the Underlying Trade Level
	* Known Limitations of the CVA Models
	* Materiality Criteria
	* CVA-PV-Underlying-PV Test
	* One Cash Flow Test
	* Swap-Forward-Swap Test
	* Swap Swaption Test
	* Swap Forward Swaption Test
	* Cash Flow Martingale Target
	* Vega Based Error Analysis
	* At the State Variable Level
	* Portfolio Based Testing
	* Materiality Analysis of Unmodeled or Failed Trades
	* Materiality Analysis Based on P&L
	* CVA/DVA Testing
	* Stress/CCAR Testing
	* Back Testing
	* Quantile Based Exposure Testing
	* Simplified Back Testing
	* References
 * Exposure Aggregation and XVA Calculation in Cross-Asset Model
	* Introduction
	* Netting and Aggregation
	* Aggregation for Trades Priced in Proxy Models
	* Three Points Brownian Bridge Interpolation
	* XVA Calculation
 * Prudent Adjustments
	* Abstract
	* Fair Valuations
	* Wealth Transfers and Prudent Valuation
	* Loss in Translation
	* References
 * CVA And Funding Adjustments PDE
	* Counterparty Risk and Funding Costs
	* Motivation, Literature Scan, and Approach
	* Notation, Symbology, and Key PDEs
	* Model Setup and the Derivation of the Bilateral Risky PDE
	* Using $\hat{V}$ (T, s) As Mark-to-Market at Default
	* Using V (T, S) As Mark-to-Market at Default
	* Funding and Default Payoff Examples
	* Counterparty Funding and PDE Extensions
	* Balance Sheet and Funding Cost Management
	* Unified Framework for Bilateral Counterparty Risk and Funding Adjustments
	* Simple Model for the Impact of Derivative Asset on Balance Sheet and Funding
	* Balance Sheet Management to Mitigate Funding Costs
	* Funding Strategies and Costs Impact
	* Generalized Semi-Replication and Pricing PDE
	* Semi-Replication
	* Examples of Different Bond Portfolios
	* Perfect Replication – The FCA Vanishes
	* Semi-Replication with No Shortfall at Own-Default
	* Set-offs
	* Semi-Replication with a Single Bond
	* Burgard and Kjaer (2013) Case Study
	* References
 * Accounting for OTC Derivatives: Funding Adjustments and Rehypothecation Option
	* Status of Current FCA/FBA Accounting
	* Comparison Between FCA/FBA and FDA/FVA
	* References
 * Funding and Re-Hypothecation Adjustment - Motivation
	* OTC vs. Repo Markets
	* Modus Operandi of Funding Desks
	* MMT And Asset-Liability Symmetry
	* Rigorous Framework For Funding Costs
	* Funding Set VM RHO Computation
	* Shortcomings of Traditional CVA Systems
	* Addressing the Shortcomings of FCA/FBA Accounting
	* References
 * Albanese and Andersen (2014) Results Summary
	* Valuation Adjustment Estimation Framework Setup	* OTC Books Funding Set Decomposition
	* Inconsistent Booking Under the FCA/FBA
	* Improvements Offered by the FVA/FDA Accounting
	* References
 * CET1 Capital Deductions in Basel III and Capital Structure Considerations
	* CET1 Deductions
	* “Going Concern” or Defaultable Banks?
	* Categorization of Cash-flow Streams
	* References
 * Accounting Principles, Units of Accounts, and Valuation Adjustment Metrics
	* Accounting Rules
	* Contra-Asset and Contra-Liability Accounting for Credit Risk
	* Contra-Asset and Contra-Liability Accounting for Funding
	* References
 * Accounting Cash Flows
	* Accounting Cash Flow Setup Framework
	* Cash Flows Related to VM Funding
	* Cash Flows at Counterparty Default
	* Cash Flows at Bank Default
	* References
 * Credit and Funding Valuation Adjustments
	* Introduction
	* CVA and DVA
	* FVA and FDA
	* FCA and FBA
	* CA and CL Adjustments
	* Own Credit Sensitivities
	* References
 * Triggers and Close-out Adjustments
	* Introduction
	* Collateral Triggers and Close-outs
	* Incorporating ISDA 1992 Close-outs
	* VM Rehypothecability Across Funding Sets
	* References
 * Entry Prices, Exit Prices, and Trade FTP
	* Trade and Portfolio FTP Estimation
	* FTP For FCA/FBA Accounting
	* FTP For FVA/FDA Accounting
	* Exit Prices and Fair Valuation
	* FVA/FDA Accounting
	* FCA/FBA Accounting
	* References
 * Liquidity Spreads, Asset Liability Symmetry, and Alternative Allocations for Excess Collateral	* Motivation
	* Working Capital Management and Operations
	* Equity Gain and Debt Gain
	* Liquidity Based Analysis and Treatment
	* Problems with the Gain Accounting
	* References
 * Albanese and Andersen (2014) Case Study
	* Case Study Setting and Purpose
	* Scenario Estimation of the XVA Metrics
	* Product and Scenario Threshold Type Scenarios
	* XVA Metric Errors and Incrementals
	* Estimation of the FCA/FBA – FVA/FDA Mismatch
	* References
 * Conclusions with Funding Adjustments with RHO
	* Traditional Challenges with Derivative Accounting
	* Problems with FCA/FBA Accounting
	* FVA/FDA as FCA/FBA Enhancement
	* Trading Staff Point of View
	* Challenges with the XVA Metric Estimation
	* Shortfalls of the FVA/FDA Scheme
	* Alternate Specialized Value Adjustment Metrics
	* References
 * The FVA Puzzle: Accounting, Risk Management, and Collateral Trading
	* Abstract
	* Introduction
	* CVA/DVA Accounting
	* The FBA/FCA Method
	* FVA/FDA Accounting
	* Funds Transfer Pricing
	* FCA/FBA Accounting
	* FVA/FDA Accounting
	* Notes on Exit Pricing and Asset-Liability Symmetry
	* Extensions
	* Balance Sheet Simulations and Reverse-Stress Testing
	* Strategies for Exploiting Funding Arbitrage
	* References
 * Derivatives Funding, Netting, and Accounting
	* Introduction, Motivation, Scope, and Synopsis
	* Model Setup and Asset Dynamics
	* Balance Sheet Dynamics under Semi-Replication
	* Economic Values
	* Derivation of the Coupled Solutions
	* Fair Values
	* Funding Strategies
	* Derivation of $\hat{V}$<sub>IV</sub>
	* Proof of the Statement FCA<sub>IV</sub> < FCA<sub>III</sub> < FCA<sub>I</sub>
	* Discussion
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
