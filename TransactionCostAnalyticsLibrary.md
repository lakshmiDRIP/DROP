
# Transaction Cost Analytics Library


<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

Transaction Cost Analytics Library contains the Functionality to estimate single Trade/Portfolio Execution Cost, and corresponding Optimal Trajectories.


## Documentation

 |        Document         | Link |
 |-------------------------|------|
 | Technical Specification | [*Latest*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/TransactionCostAnalytics/TransactionCostAnalytics_v6.05.pdf) [*Previous*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/TransactionCostAnalytics) |
 | User Guide              |  |
 | API                     | [*Javadoc*](https://lakshmidrip.github.io/DROP/Javadoc/index.html)|


## Component Projects

 * *Execution* => Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aexecution) }


## Coverage

 * Volume-weighted Average Price
	* Overview
	* Formula
	* Using VWAP
	* References
 * Order
	* Overview
	* Market Order
	* Limit Order
	* Time in Force
	* Conditional Orders
		* Stop Orders
			* Sell-stop Orders
			* Buy-stop Orders
			* Stop-limit Orders
			* Trailing Stop Order
			* Trailing Stop-limit Order
		* Peg Order
			* Peg Best
			* Mid-price Peg
		* Market-if-touched Order
		* One Cancels Other Orders
		* One Sends Other Orders
		* Tick-sensitive Orders
		* At the Opening
	* Discretionary Order
	* Bracket
	* Quantity and Display Instructions
	* Electronic Markets
	* References
 * Time-in-Force
	* Abstract
	* What is Time-in-Force?
	* Basics of Time-in-Force
	* Types of TIF Orders
	* Example of Time-in-Force
	* Reference
 * Smart Order Routing
	* Overview
	* Benefits and Disadvantages of Smart Order Routing
	* Brief Concept
	* Algorithmic Trading and SOR
	* Cross-Border Routing
	* References
 * Central Limit Order Book
	* Overview
	* References
 * Retail SOR Strategy Builder
	* Retail SOR Wave Instructions
	* Phase-based Conditional Action
	* Additional Considerations
	* Continuous Trading Scenario Overview
 * Execution Cost and Transaction Trajectories
	* Motivation and Practice Overview
	* Post Trade Reporting
	* Optimal Trading
	* Pre Trade Cost Estimation
	* References
 * Execution of Portfolio Transactions - Optimal Trajectory
	* Overview, Scope, and Key Results
	* Motivation, Background, and Synopsys
	* Definition of a Trading STrategy
	* Price Dynamics
	* Temporary Market Impact
	* Capture and Cost of Trading Trajectories
	* Linear Impact Functions
	* The Efficient Frontier of Optimal Execution
	* The Definition of the Frontier
	* Explicit Construction of Optimal Strategies
	* The Half-Life of the Trade
	* The Structure of the Frontier
	* The Utility Function
	* Value-at-Risk
	* The Role of Utility in Execution
	* Choice of Parameters
	* The Value of Information
	* Drift
	* Gain Due to Drift
	* Serial Correlation
	* Parameter Shifts
	* Conclusions and Further Extensions
	* Numerical Optimal Trajectory Generation
	* References
 * Non Linear Impact and Trading Enhanced Risk
	* Abstract
	* Introduction
	* The Model
	* Non Linear Cost Functions
	* Objective Functions
	* Almgren (2003) Example
	* Trading Enhanced Risk
	* Constant Enhanced Risk
	* Linear Enhanced Risk
	* Almgren (2003) Non Linear Example Sample
	* Conclusions: Summary and Extensions
	* References
 * Market Impact Function/Parameters Estimation
	* Introduction, Overview, and Background
	* Data Description and Filtering Rules
	* Data Model Variables
	* Trajectory Cost Model
	* Permanent Impact
	* Temporary Impact
	* Choice of the Functional Form
	* Cross Sectional Description
	* Model Determination
	* Determination of the Coefficients
	* Residual Analysis
	* References
 * Optimal Execution of Program Trades
	* Introduction
	* Efficient Frontier Pricing of Program Trades
	* The Efficient Frontier Including Discount
	* Performance Measures
	* Annualization
	* Definition of the Information Ratio
	* Application of the Information Ratio
	* References
 * Order Placement in Limit Order Markets
	* Overview
	* Introduction
	* The Order Placement Problem
		* Assumptions
		* Proposition 1
		* Proposition 2
	* Choice of Order Type: Limit Orders vs Market Orders
		* Proposition 3 – Single Exchange: Optimal Split between Limit and Market Orders
	* Optimal Routing of Limit Orders across Multiple Exchanges
		* Proposition 4
		* Proposition 4 Corollary
		* Example
	* Numerical Solution to the Optimization Problem
	* Conclusion
	* References
 * Bayesian Trading with a Daily Trend
	* Overview, Motivation, and Synopsys
	* Introduction and the Associated Literature
	* Price Model Using Bayesian Update
	* Bayesian Inference
	* Trading and Price Impact
	* Optimal Trading Strategies
	* Trajectory by the Calculus of Variations
	* Optimality of the Bayesian Adaptive Strategy
	* Stochastic Optimal Control Treatment
	* References
 * Cost Adaptive Arrival Price Trading
	* Synopsys and Key Results
	* Introduction, Background, and Motivation
	* Adaptive Strategies - A Simple Illustration
	* Trading in Practice
	* Other Adaptive Strategies
	* The Market Model
	* Static Trajectories
	* Non Dimensionalization
	* Small Portfolio Limit
	* Portfolio Size Comparison
	* Single Update
	* Single Update Mean and Variance
	* Almgren and Lorenz (2007) Results
	* Continuous Response
	* Continuous Response Numerical Results
	* Discussion and Conclusions
	* References
 * Mean Variance Optimal Adaptive Execution
	* Background, Synposys, and Key Results
	* References
 * Optimal Trading in a Dynamic Market
	* Introduction, Overview, and Motivation
	* Limitations of Arrival Price Frameworks
	* The Liquidation Problem
	* Cost of Trading
	* Constant Coefficients
	* Coordinated Variation
	* Rolling Time Horizon Approximate Strategy
	* Small Impact Approximation
	* Dynamic Programming - Fully Coordinated Version	
	* Log Normal and Non Dimensionalization
	* Constant Market
	* Long Time
	* Dynamic Programming - Custom \epsilon (t) and \sigma (t)
	* Log Normal Model
	* Coordinated Variation Model
	* Asymptotic Behavior
	* Numerical Solution
	* Time Discretization
	* Space Discretization
	* Almgren (2009, 2012) Sample Solutions
	* References
 * Systemic Market Making SKU
	* Symbology
	* Glossary
	* Width/Skew/Size Estimation Models
	* Market Making System SKU
	* Market Making Parameter Types
	* Intra-day Pricing Curve Generation Schemes
	* Mid-Price Models
	* Width Models
	* Skew Models
	* Size Models
	* Heuristics Control
	* Published Market Quote Picture
	* Flow Analysis
 * Corporate Bond Auto-Responder (CBAR)
	* Summary
	* Reference Data
	* Flow Diagram - Client RFQ Data
	* Streaming Flow
	* Algorithm Operational Logic Description
	* Algorithm Processing Logic Operating Detail – RFQ Quoting
	* Algorithm Processing Logic Operating Detail - Streaming
	* Auto-Execution of Streamed Levels
	* Inputs
	* Outputs
	* Benchmarks
	* Market Phases
	* Algorithm Operating Constraints
	* Model Use Constraints
	* Operational Risk
	* Level Validation Checks for Algo Levels on Bonds Marked in Spread or Yield
	* Level Validation Checks on Algo Levels for Bonds Marked in Price
	* Level Validation Checks for Outgoing Streamed Prices
	* Reputational Risk
	* Market Risk
	* Sample Risk/Notional Controls for an IG Desk
	* Typical Risk/Notional Controls for HY Desk
	* Risk/Notional Controls
	* Volatility Controls
 * Corporate Bond Skewer
	* Major Components
	* Maximum Auto-responder Sizes
	* Waterfalls
	* Skewing Flow


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
