
# Transaction Cost Analytics Library


<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

Transaction Cost Analytics Library contains the Functionality to:
	* Centralized Limit Order Book Manager
	* Standardized Execution Scheduling Strategies
	* Estimate single Trade/Portfolio Execution Cost
	* Construct Optimal Trajectories
	* Order Management and Routing


## Documentation

 |        Document         | Link |
 |-------------------------|------|
 | Technical Specification | [*Latest*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/TransactionCostAnalytics/TransactionCostAnalytics_v7.24.pdf) [*Previous*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/TransactionCostAnalytics) |
 | User Guide              |  |
 | API                     | [*Javadoc*](https://lakshmidrip.github.io/DROP/Javadoc/index.html)|


## Component Projects

 * *Execution* => Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aexecution) }


## Coverage

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
 * Order State Change Matrices
	* Introduction
	* Scenario Order State Change Matrices
	* D1 Filled Order
	* D2 – Part-Filled Day Order, Done for Day
	* D3 – Cancel Request Issued for a Zero-filled Order
	* D4 – Cancel Request Issued for a Part-filled Order – Executions occur when Cancel Request is Active
	* D5 – Cancel Request issued for an Order that becomes Filled before Cancel Request can be Accepted
	* D6 – Zero-filled Order, Cancel/Replace Request issued to increase Order Quantity
	* D7 – Part-filled Order, followed by Cancel/Replace Request to increase Order Quantity, Execution occurs while Order is Pending Replace
	* D8 – Filled Order Followed by Cancel/Replace to increase Order Quantity
	* D9 – Cancel/Replace Request – Not for Quantity Change – is rejected as a Fill has Occurred
	* D10 – Cancel/Replace Request Sent while Execution is being Reported. The Requested Order Quantity exceeds the Cumulative Quantity. The Order is replaced then Filled
	* D11 – Cancel/Replace Request Sent while Order Execution is being Reported. The Requested Order Quantity equals the Cumulative Quantity. The Order Quantity is Amended to the Cumulative Quantity
	* D12 – Cancel/Replace Request Sent while Order Execution is being Reported. The Requested Order Quantity is below the Cumulative Quantity. The Order Quantity is Amended to the Cumulative Quantity
	* D13 – One Cancel/Replace Request is Issued which is Accepted. Another One is Issued which is also Accepted
	* D14 – One Cancel/Replace Request is issued which is Rejected before Order becomes Pending Replace. Then Another is issued which is Accepted
	* D15 – One Cancel/Replace Request is Issued which is Rejected after it is in Pending Replace. Then Another One is Issued which is Accepted
	* D16 – One Cancel/Replace Request is Issued Followed immediately by Another. Broker Processes Sequentially
	* D17 – One Cancel/Replace Request is issued followed immediately by Another. Broker rejects the Second as Order is Pending Replace
	* D18 – Telephoned Order
	* D19 – Unsolicited Cancel of a Part-timed Order
	* D20 – Unsolicited Replacement of a Part-filled Order
	* D21 – Unsolicited Reduction of Order Quantity by Sell-side. For example, the US ECNs Communication NASDAQ SelectNet Declines
	* D22 – Order Rejected due to Duplicate ClOrdID
	* D23 – Order Rejected because Order has already been Verbally Submitted
	* D24 – Order Status Request Rejected for Unknown Order
	* D25 – Transmitting a CMS-style “Nothing Done” in Response to a Status Request
	* D26 - Order sent, immediately followed by a status request. Subsequent Status requests sent during Life of Order
	* D27 – GTC Order Partially Filled, Restated/Renewed and Partially Filled the Following Day
	* D28 – GTC Order with a Partial Fill, a 2:1 Stock Split, then a Partial Fill and Fill the Following Day
	* D29 – GTC Order Partially Filled, Restated/Renewed, and Canceled the Following Day
	* D30 – GTC Order Partially Filled, Restated/Renewed Followed by Replace Request to increase Quantity
	* D31 – Possible Resend Order
	* D32 – Fill or Kill Order cannot be Filled
	* D33 – Immediate-Or-Cancel Order that cannot be immediately Hit
	* D34 – Filled Order, Followed by Correction and Cancelation of Executions
	* D35 – A Canceled Order Followed by a Busted Execution and a new Execution
	* D36 – GTC Order Partially Filled, Restated/Renewed, and Partially Filled the Next Day, with Corrections of Quantities on both Executions
	* D37 – Transmitting a Guarantee of Execution Prior to Execution
 * Central Limit Order Book
	* Overview
	* References
 * How Storing Supply and Demand affects Price Diffusion
	* Abstract
	* Main
	* References
 * Limit Order Book Simulations: A Review
	* Abstract
	* Overview
		* Motivation
		* Contributions
	* Limit Order Books
		* Dynamics
	* Stylized Facts
	* Point Process Models
		* Poisson Process and Variants
			* Zero-intelligence Models
			* Variable Order Intensity Poisson Models
			* Discussion
		* Hawkes Process
			* Mathematical Overview
			* n-dimensional Hawkes Process
			* Constrained Hawkes Process
			* Other Variants
			* Scaling Limits
			* Non-linear Hawkes Process
			* Neural Hawkes Process
			* Discussion
	* Agent Based Models
		* Recent Work
		* Combining ABMs with Other Methods
		* Discussion
	* Deep Learning Based Models
		* Mid-price Prediction from LOB
		* Recurrent Neural Networks
		* Generative Networks
		* Large Kanguage Models
		* Discussion
	* Stochastic Differential Equations Based Models
		* Continuous Limits of Point Process Models
		* Volume of Orders as a Stochastic Process
		* Probabilistic Properties under Scaling Limits
		* Connecting various Timescales
		* Discussion
	* Responsiveness to Trades: Market Impact
		* Introduction
		* Zero-intelligence Models
		* Poisson Process
		* Hawkes Process
		* Agent-based Process
		* Stochastic PDEs basec Models
		Deep Learning based Models
	* Comparative Study
		* Poisson
		* Hawkes
		* Hawkes and Deep Learning
		* Agent-based Model
		* Deep Learning
		* Agent-based Model, Hawkes, and Deep Learning
		* Stochastic Partial Differential Equation
	* Conclusion and Future Work
	* References
 * Inverted Price Venues
	* Reference
 * Auction On-Demand
	* Efficient Trading with On-demand Auctions
	* Highlights
	* Features
	* Safety Features
	* Auction Overview
	* Order Types
	* References
 * Volume-weighted Average Price
	* Overview
	* Formula
	* Using VWAP
	* References
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
 * Smart Order Routing
	* Overview
	* Benefits and Disadvantages of Smart Order Routing
	* Brief Concept
	* Algorithmic Trading and SOR
	* Cross-Border Routing
	* References
 * NAGAR - Algos and Smart Order Router
	* Introduction
	* Summary of the Algos Logic
	* Algorithms and Routing Logic
	* Ping Execution Mode
	* Sweep Execution Mode
	* Exchange Post Execution Mode
	* Dark Post Execution Mode
	* Venue Selection
	* Accessible Venues
	* Order Types Used
	* Order Handling Scenarios to Consider
	* Router Customization Options
	* Directed Orders via PATH
	* Exchange Market Data
	* Non-exchange Market Data
	* Performance Evaluation of Algos
	* Information Leakage Prevention and Anti-gaming Protection
	* Capital Commitment Features
 * Retail SOR Strategy Builder
	* Retail SOR Wave Instructions
	* Phase-based Conditional Action
	* Additional Considerations
	* Continuous Trading Scenario Overview
 * Indifference Price
	* Overview
	* Mathematics
	* Example
	* Notes
	* Reference
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
 * Seven Execution Algorithms
	* Instruction
	* Why are Algorithmic Strategies Used
	* References
 * Liquidity Seeker Trading Strategy
	* Introduction
	* The Algo Engine
	* The Trading Strategies (Algos)
	* Trading Logic Components
		* Algo
		* Scheduler
		* Worker
		* Routers
	* Life-cycle of an Order
		* Order Arrival
		* Wakeup Logic
		* Interval Logic
		* Order Amendment
	* Trading Objectives / Design Process
		* Low Level
		* High Level
		* Elegance and Simplicity
	* References
 * A Volume-Weighted Average Approach
	* Introduction
	* Defining the Prediction Problem
	* Predicting Cumulative Relative Volume with Real-time Data
	* Extension to Partial Trading Days
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
