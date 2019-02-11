
# Analytics Core Module

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

Analytics Core Module contains libraries the implement Fixed Income Analytics, Asset Back Analytics, Exposure and Margin Analytics, and XVA Analytics.


## Component Libraries

 * [*Fixed Income Analytics*](https://github.com/lakshmiDRIP/DROP/blob/master/FixedIncomeAnalyticsLibrary.md) => Valuation and Risk Functionality of the Principal Asset Classes, i.e., Equity, Rates, Credit, FX, Commodity, and their Hybrids.

 * [*Asset Backed Analytics*](https://github.com/lakshmiDRIP/DROP/blob/master/AssetBackedAnalyticsLibrary.md) => Valuation and Risk Functionality for Asset Backed and Mortgage Backed Securities.

 * [*XVA Analytics*](https://github.com/lakshmiDRIP/DROP/blob/master/XVAAnalyticsLibrary.md) => Valuation Adjustments (Collateral VA/CVA/DVA/FBA/FCA/FVA/MVA/XVA).

 * [*Exposure Analytics*](https://github.com/lakshmiDRIP/DROP/blob/master/ExposureAnalyticsLibrary.md) => Exposure, Margin, and Capital at the specified Trade Group Granularity.


## Fixed Income Analytics Coverage

 * Association and Exchanges
 * Date Conventions
 * Overnight and IBOR Like Indexes
 * Over-the-Counter Instruments
 * Exchange Traded Instruments
 * Treasury Futures Trading and Hedging
 * Identification of the CTF in the Basket
 * Valuation of the Treasury Futures Contract
 * Curve Builder Features
 * Curve Construction Methodology
 * Curve Construction Formulation
 * Stream Based Calibration
 * Spanning Splines
 * Monotone Decreasing Splines
 * Hagan West (2006) Smoothness Preserving Spanning Spline
 * Extrapolation in Curve Construction
 * Multi-Pass Curve Construction
 * Transition Spline (or Stitching Spline)
 * Penalizing Exact/Closeness of Fit and Curvature Penalty
 * Index/Tenor Basis Swaps
 * Multi-Stretch Merged Curve Construction
 * Latent State Manifest Measure Sensitivity
 * OIS Valuation and Curve Construction
 * Spline Based Credit Curve Calibration
 * Correlated Multi-Curve Buildout
 * Cross Currency Basis Swap
 * Convexity Correction Associated with Margining
 * Hedging Considerations
 * Product Curve Effect Attribution
 * Inference Based Curve Construction
 * Credit Analytics Bond RV Calculation Methodology
 * Stochastic Calculus
 * Black Scholes Methodology
 * Log Normal Black Scholes Greeks
 * Black Scholes Extensions
 * Options on Forward
 * Stochastic Volatility Models: The Heston Model
 * Dynamical Latent State Calibration
 * HJM Model
 * Hull White Model
 * Market Model of Interest Rate Dynamics
 * The BGM Model
 * Application of BGM to Derivatives Pricing
 * The SABR Model
 * LMM Calibration and Greeks Overview
 * LMM Extensions Overview and Literature
 * Algorithmic Differentiation
 * Algorithmic Differentiation - Basics
 * Sensitivity Generation During Curve Construction
 * Stochastic Entity Evolution
 * Formulation of Sensitivities for Pay-off Functions
 * Bermudan Swap Option Sensitivities
 * Basket Sensitivities
 * Leibnitz Integral Rule


## Asset Backed Analytics Coverage

 * Market Place Lending Credit Model Methodology


## XVA Analytics Coverage

 * Collateral Agreements and Derivative Valuation
 * Cross Asset Random Number Generator
 * Core CVA/DVA Model
 * Cross Asset CVA Modeling, Testing, and Validation
 * Exposure Aggregation and XVA Calculation in Cross-Asset Model
 * Prudent Adjustments
 * CVA And Funding Adjustments PDE
 * Accounting for OTC Derivatives: Funding Adjustments and Rehypothecation Option
 * Funding and Re-Hypothecation Adjustment - Motivation
 * Albanese and Andersen (2014) Results Summary
 * CET1 Capital Deductions in Basel III and Capital Structure Considerations
 * Accounting Principles, Units of Accounts, and Valuation Adjustment Metrics
 * Accounting Cash Flows
 * Credit and Funding Valuation Adjustments
 * Triggers and Close-out Adjustments
 * Entry Prices, Exit Prices, and Trade FTP
 * Liquidity Spreads, Asset Liability Symmetry, and Alternative Allocations for Excess Collateral * Albanese and Andersen (2014) Case Study
 * Conclusions with Funding Adjustments with RHO
 * The FVA Puzzle: Accounting, Risk Management, and Collateral Trading
 * Derivatives Funding, Netting, and Accounting


## Exposure and Margin Analytics Coverage

 * Modeling Counterparty Credit Exposure in the Presence of Margin Agreements
 * Estimation of Margin Period of Risk
 * Regression Sensitivities in Margin Calculations
 * Principles Behind ISDA SIMM Specification
 * ISDA SIMM Methodology
 * Dynamic Initial Margin Impact on Exposure
 * CCP and SIMM Initial Margin
 * Basel II
 * Basel III


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
