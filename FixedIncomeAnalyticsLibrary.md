
# Fixed Income Analytics Library


<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

Fixed Income Analytics Library contains the Valuation and Risk Functionality of the Principal Asset Classes, i.e., Equity, Rates, Credit, FX, Commodity, and their Hybrids.


## Documentation

 |        Document         | Link |
 |-------------------------|------|
 | Technical Specification | [*Latest*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/FixedIncome/FixedIncomeAnalytics_v3.11.pdf) [*Previous*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/FixedIncome) |
 | User Guide              |  |
 | API                     | [*Javadoc*](https://lakshmidrip.github.io/DROP/Javadoc/index.html)|


## Component Projects

 * *Analytics* => Date, Cash Flow, and Cash Flow Period Measure Generation Utilities.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aanalytics) }  
 * *Dynamics* => HJM, Hull-White, LMM, and SABR Dynamic Evolution Models.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Adynamics) }
 * *Market* => Static Market Fields - the Definitions, the OTC/Exchange Traded Products, and the Treasury Settings.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Amarket) }
 * *Param* => Core Suite of Parameters - Product Cash Flow, Valuation, Market, Pricing, and Quoting Parameters.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aparam) }
 * *Pricer* => Custom Pricing Algorithms and the Derivative Fokker Planck Trajectory Generators.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pricer) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Apricer) }
 * *Product* => Product Components/Baskets for Credit, FRA, FX, Govvie, Rates, and Option Asset Classes.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aproduct) }
 * *State* => Latent State Inference and Creation Utilities.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Astate) }
 * *Template* => Pricing/Risk Templates for Fixed Income Products.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Atemplate) }


## Coverage

 * Associations and Exchanges
	* Associations
	* Exchanges
 * Date Conventions
	* Day Count Conventions
	* Business Day Conventions
	* References
 * Overnight and IBOR Like Indexes
	* IBOR Indexes - Introduction
	* Main IBOR Indexes
	* Other IBOR Indexes
	* Overnight Index Definitions
	* Overnight Index Committees and Meeting Dates
 * Over the Counter Instruments
	* Forward Rate Agreements
	* Interest Rate Swaps
	* Vanilla IRS
	* Interest Rate Swaps (Basis Swaps: IBOR for IBOR)
	* Cross Currency Swaps: IBOR for IBOR
	* Constant Maturity Swaps
	* Swap Indexes
	* Overnight Index Swaps
	* Swaptions
	* Forex and Forward Swaps
 * Exchange Traded Instruments
	* Introduction
	* Overnight Futures
	* Short Term Interest Rate (STIR) Futures
	* Currency Specific Futures
	* Interest Rates Futures Option - Premium
	* Interest Rates Futures Option - Margin
	* Bank Bill Futures - AUD Style
	* Deliverable Swap (IRS) Futures - PV Quoted
	* Bond Futures (non AUD/NZD)
	* Country-specific Bond Futures - USD
	* Country-specific Bond Futures - Germany
	* Country-specific Bond Futures - Spain
	* Country-specific Bond Futures - GBP
	* Country-specific Bond Futures - JPY
	* Options on Bond Futures (non AUD/NZD) - Premium
	* Options on Bond Futures (non AUD/NZD) - Margin
	* AUD-NZD Bond Futures
 * Treasury Futures Hedging and Trading
	* Introduction and Contract Detail Specifications
	* References
 * Identification of the CTD in the Basket
	* Motivation for the Conversion Factor
	* Illustration - Old vs. Active Treasury
	* Market Parameters Influencing the CTD Calculation
	* Impact of the Yield Curve Changes
	* References
 * Valuation of Treasury Futures Contract
	* Futures Contract and Mark to Market
	* Role of the Clearing Corporation
	* Delivery Options for the Underlying
	* Implied Repo Rate for Futures
	* Net Basis for Treasury Futures
	* References
 * Curve Builder Features
	* Overview
	* Discount Curves
	* References
 * Curve Construction Metyhodology
	* Approach
	* State Span Design Components
	* Curve Calibration from Instruments/Quotes
	* Calibration Considerations
	* References
 * Curve Construction Formulation
	* Introduction
	* Segment Linear Discount Curve Calibration
	* Curve Jacobian
	* References
 * Stream-based Calibration
	* Latent State Formulation Metric (LSFM)
	* Stream Inference Setup
	* Coupon Period Based Calibration Specification
	* Stream Based Calibration Specification
	* Calibration of Multi-Stream Components
 * Spanning Spline
	* Formulation and Setup
	* Challenges with the Spanning Spline Approach
	* References
 * Monotone Decreasing Splines
	* Motivation
	* Exponential Rational Basis Spline
	* Exponential Mixture Basis Set
	* References
 * Hagan West (2006) Smoothness Preserving Spanning Spline
	* Monotone and Convexity Preserving Estimators
	* Positivity Preserving Estimators
	* Ameliorating Estimator
	* Harmonic Spline Extension to the Framework Above
	* Minimal Quadratic Estimator
	* References
 * Extrapolation in Curve Construction
 * Multi-Pass Curve Construction
	* Motivation
	* Bear-Sterns Multi-Pass Curve Building Techniques
	* References
 * Transition Spline (or Stitching Spline)
	* Motivation
	* Stretch Modeling Using Transition Splines
	* Stretch Partition/Isolation in Transition Splines
	* Knot Insertion vs. Transition Splines
	* Overlapping Stretches
 * Penalizing Exact/Closeness of Fit and Curvature Penalty
	* Motivation
	* References
 * Index/Tenor Basis Swaps
	* Component Layout and Motivation
	* Formulation
	* References
 * Multi-Stretch Merged Curve Construction
	* Motivation
	* Merge Stretch Calibration
 * Latent State Manifest Measure Sensitivity
	* Introduction
	* Float-Float Manifest Measure Sensitivities
	* Multi-Reset Floating Period
 * OIS Valuation and Curve Construction
	* Base Framework and Environment Setup
	* OIS Valuation Extensions and Approximations
	* OIS-FX Basis Swap Valuation and Approximation
	* Arithmetic Accrual Convexity Correction
	* Composed Period Latent State Loadings
	* References
 * Spline Based Credit Curve Calibration
	* References
 * Correlated Multi-Curve Build-Out
	* Introduction
	* Standard FRA Setup
	* Standard FRA Options
	* No Arbitrage and Counter-party Risk Based FRA Formulation
	* Market FRA Setup
	* Futures
	* Multi-Curve Swap Valuation
	* References
 * Cross Currency Basis Swap
	* Product Details and Valuation
	* Building the CCS Discount Curve
	* Custom CCBS Based Discount Curve Construction SKU
	* Mark To Market Cross Currency Swap Valuation
	* Mark To Market Cross Currency Swap Valuation Formulation
	* Absolute/Relative MTM Application
	* Per-trade Risk Isolation Components
	* References
 * Convexity Correction Associated with Margining
 * Hedging Considerations
 * Product Curve Effect Attribution
	* Market Value Change Explain Componentsd
	* Coupon Accrual Intrinsic
	* Market Parameters Intrinsic
	* Market Parameters Extrinsic
	* Market Value Change Effects Formulation
 * Inference Based Curve Construction
	* Curve Smoothing in Finance
	* Bayesian Curve Calibration
	* Sequential Curve Estimation
	* References
 * Credit Analytics Bond RV Calculation Methodology
	* Introduction
	* The Bond RV Measure Set
	* Asset Swap Spread
	* Bond Basis
	* Convexity
	* Credit Basis
	* Discount Margin
	* Duration
	* DV01
	* G Spread
	* I Spread
	* Macaulay Duration
	* Modified Duration
	* Option Adjusted Spread
	* Par Asset Swap Spread
	* Par Spread
	* Par Equivalent CDS Spread (PECS)
	* Price
	* Spread Over Treasury Benchmark
	* Yield
	* Yield Basis
	* Yield Spread
	* Yield01
	* Zero Discount Margin (ZDM)
	* Zero (Z) Spread
	* Realtive Value Cross Metric Grid
	* Basic Measures
	* Some Trivial Closed Form Analytical Bond Math Results
 * Stochastic Calculus
	* Single Factor Stochastic Calculus
	* Multi-Factor Stochastic Calculus
	* Risk Neutral Pricing Framework
	* References
 * Black Scholes Methodology
	* Overview and Base Derivation
	* The Replication Technology
	* Capital Asset Pricing Model
	* Multi Numeraire Formulation
	* References
 * Log Normal Black Scholes Greeks
	* First Order Greeks
	* Second Order Greeks
	* Third Order Greeks
 * Black Scholes Extensions
	* Time Dependent Black Scholes
	* Local Volatility Models
	* Black Normal Model Specification and Dynamics
 * Options on Forward
	* Theoretical Framework and Background
	* Valuation
 * Stochastic Volatility Models: The Heston Model
	* Model Specification and Dynamics
	* Price Estimation Through Characteristic Functions
	* Fourier Inversion in Characteristic Function
	* References
 * Dynamical Latent State Calibration
	* Fokker Planck Equations
	* Volatility Observations vs. Calibration
	* References
 * HJM Model
	* Introduction
	* Formulation
	* Hull White from HJM
	* G2++ - A 2F HJM Model
	* HJM to LMM
	* HJM PCA
	* References
 * Hull White Model
	* Short Rate Formulation
	* Hull White Trinomial Tree
	* Construction of the Symmetric Trinomial Tree
	* Displacing the Nodes of the Trinomial Tree
	* References
 * Market Model of Interest Rate Dynamics
	* Problems with Conventional Market Practice
	* Nomenclature and Notation
	* References
 * The BGM Model
	* LIBOR Rate Dynamics
	* Relation to the HJM Dynamics
	* Existence, Uniqueness, and Regularity of the LIBOR Dynamics Solution
	* Upper/Lower Bounds for the LIBOR Rate
	* Invariant Measure for the LIBOR Rate
	* References
 * Application of BGM to Derivatives Pricing
	* Cap/Floor Pricing
	* Payer Swap Option Pricing
	* Payer Swap Option Pricing Simplification
	* Mismatched Period Cap/Swaption Pricing
	* Approximate vs. Full Simulation Comparisons
	* Typical Model Calibration Results
	* References
 * The SABR Model
	* Introduction
	* Parameter Estimation
	* Reference
 * LMM Calibration and Greeks Overview
	* Motivation for Robust LMM Calibration
	* Robust LMM Calibration Approaches Overview
	* Cross Currency LIBOR Market Model
	* LMM Based Greeks Calculation Approaches
	* References
 * LMM Extensions Overview and Literature
	* LMM Approach Advantages and Drawbacks
	* Major Extensions to the LMM
	* Derivatives Fair Value Pricing Challenge
	* Hedging the Derivatives Cash Flow
	* Basic LMM and its Calibration
	* LMM Skew and its Calibration
	* LMM Smile and its Calibration
	* Cross Currency Extensions to LMM
	* LMM Monte Carlo Methods and Greeks
	* Numerical Methods for LMM Calibration
	* LMM Calibration and Greeks - Results
	* First Generation LMM Treatment Literature
	* Smile Extensions to the LMM
	* Numerical Methods in Calibration/Greeks
	* Object Based Financial Valuation Models
	* References
 * Algorithmic Differentiation
	* Glossary
	* Overview
	* Algorithmic Differentiation in Finance
	* References
 * Algorithmic Differentiation - Basics
	* Motivation and Advantages
	* Program Sequence Construction Modes
	* Canonicalization - Program Statements Simplification by Decomposition
	* Challenges of Automating the Differentiation
	* Wengert Representation and Optimal Program Structure Synthesis
	* Optimization using Pre-accumulation and Check-pointing
	* Algorithmic Differentiation Financial Application Space Customization
	* References
 * Sensitivity Generation During Curve Construction
	* Introduction
	* Curve Jacobian
 * Stochastic Entity Evolution
	* Stochastic Entity Evolution - Sensitivity Formulation
	* Sensitivities to Stochastic State Variates and Dynamical Parameters
	* Stochastic Variate Evolution Constrained by Splines
	* Formulation of the Evolution of Stochastic Variate Self-Jacobian
	* Correlated Stochastic Variables Evolution
	* LMM Forward Rate Evolution
	* References
 * Formulation of Sensitivities for Pay-off Functions
	* Formulation of Pay-off Function Stochastic Evolution
	* Path Greek
	* Path Sensitivity to the Correlation Matrix
	* Algorithmic Differentiation in Payoff Sensitivities Calculation
	* References
 * Bermudan Swap Option Sensitivities
	* Base Formulation
	* Greek Estimation
	* LSM Methodology
	* References
 * Basket Sensitivities
	* NTD Product Formulation
	* Basket Options
	* References
 * Leibnitz Integral Rule


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
