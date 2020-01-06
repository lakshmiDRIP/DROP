
# DROP Project | Library | Module Layout

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>


## [Product Core Module](https://github.com/lakshmiDRIP/DROP/blob/master/ProductCore.md)

 * [***Fixed Income Analytics Library***](https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md) => Valuation and Risk Functionality of the Principal Asset Classes, i.e., Equity, Rates, Credit, FX, Commodity, and their Hybrids.
	* *Analytics* => Date, Cash Flow, and Cash Flow Period Measure Generation Utilities.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aanalytics) }  
	* *Dynamics* => HJM, Hull-White, LMM, and SABR Dynamic Evolution Models.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Adynamics) }
	* *Market* => Static Market Fields - the Definitions, the OTC/Exchange Traded Products, and the Treasury Settings.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Amarket) }
	* *Param* => Core Suite of Parameters - Product Cash Flow, Valuation, Market, Pricing, and Quoting Parameters.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aparam) }
	* *Pricer* => Custom Pricing Algorithms and the Derivative Fokker Planck Trajectory Generators.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pricer/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Apricer) }
	* *Product* => Product Components/Baskets for Credit, FRA, FX, Govvie, Rates, and Option Asset Classes.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aproduct) }
	* *State* => Latent State Inference and Creation Utilities.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Astate) }
	* *Template* => Pricing/Risk Templates for Fixed Income Products.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Atemplate) }
 * [***Loan Analytics***](https://github.com/lakshmiDRIP/DROP/blob/master/LoanAnalyticsLibrary.md) => Valuation and Risk Functionality for Asset Backed and Mortgage Backed Securities.
 	* *Loan* => Asset Backed Borrower and Loan Level Characteristics.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/loan/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aloan) }
 * [***Transaction Cost Analytics***](https://github.com/lakshmiDRIP/DROP/blob/master/TransactionCostAnalyticsLibrary.md) => Functionality to estimate single Trade/Portfolio Execution Cost, and corresponding Optimal Trajectories.
	* *Execution* => Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aexecution) }


## [Portfolio Core Module](https://github.com/lakshmiDRIP/DROP/blob/master/PortfolioCore.md)

 * [***Asset Allocation Analytics***](https://github.com/lakshmiDRIP/DROP/blob/master/AssetAllocationAnalyticsLibrary.md) => Optimal Portfolio Construction and Asset Allocation Functionality.
	* *Portfolio Construction* => Optimal and Constrained Portfolio Construction Functionality.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aportfolioconstruction) }
 * [***Asset Liability Analytics***](https://github.com/lakshmiDRIP/DROP/blob/master/AssetLiabilityAnalyticsLibrary.md) => Asset Liability Analytics Functionality.
	* *ALM* => Asset Liability Analytics Functionality.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/alm/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aalm) }
 * [***Capital Analytics***](https://github.com/lakshmiDRIP/DROP/blob/master/CapitalAnalyticsLibrary.md) => Economic Risk Capital and Basel Operational Capital Analytics.
	* *Capital* => Basel Market Risk and Operational Capital Analytics.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Acapital) }
 * [***Exposure Analytics***](https://github.com/lakshmiDRIP/DROP/blob/master/ExposureAnalyticsLibrary.md) => Scenario Exposures at the specified Trade Group Granularity.
	* *Exposure* => Exposure Group Level Collateralized/Uncollateralized Exposure.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aexposure) }
 * [***Margin Analytics***](https://github.com/lakshmiDRIP/DROP/blob/master/MarginAnalyticsLibrary.md) => Initial and Variation Margin Analytics.
	* *SIMM* => Initial Margin Analytics based on ISDA SIMM and its Variants.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Asimm) }
 * [***XVA Analytics***](https://github.com/lakshmiDRIP/DROP/blob/master/XVAAnalyticsLibrary.md) => Valuation Adjustments (Collateral VA/CVA/DVA/FBA/FCA/FVA/MVA/XVA).
	* *XVA* => Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Axva) }


## [Computational Core Module](https://github.com/lakshmiDRIP/DROP/blob/master/ComputationalCore.md)

 * [***Function Analysis***](https://github.com/lakshmiDRIP/DROP/blob/master/FunctionAnalysisLibrary.md) => Special Function and their Analysis.
 	* *Special Function* => Special Function and their Analysis.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aspecialfunction) }
 * [***Model Validation Library***](https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md) => Functionality for Statistical Hypotheses Validation and Testing.
	* *Validation* => Statistical Hypotheses Evidence Processing and Testing.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Avalidation) }
 * [***Numerical Analysis***](https://github.com/lakshmiDRIP/DROP/blob/master/NumericalAnalysisLibrary.md) => Functionality for Numerical Methods - including R<sup>x</sup> Solvers, Linear Algebra, and Statistical Measure Distributions.
 	* *Function* => Implementation and Solvers for a Suite of R<sup>x</sup> To R<sup>1</sup> Functions.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Afunction) }
 	* *Measure* => Continuous and Discrete Measure Distributions and Variate Evolutions.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Ameasure) }
 	* *Numerical* => Suite of DROP Numerical Analysis Utilities.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Anumerical) }
 * [***Numerical Optimizer Library***](https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md) => Functionality for Numerical Optimization - including Constrained and Mixed Integer Non-Linear Optimizers.
	* *Optimization* => Necessary, Sufficient, and Regularity Checks for Gradient Descent in a Constrained Optimization Setup.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aoptimization) }
 * [***Spline Builder Library***](https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md) => Functionality for constructing Spline Based Curves and Surfaces.
	* *Spline* => Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aspline) }
 * [***Statistical Learning Library***](https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md) => Statistical Learning Analyzers and Machine Learning Schemes.
	* *Learning* => Agnostic Learning Bounds under Empirical Loss Minimization Schemes.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Alearning) }
	* *Sequence* => Bounds Metrics for Random, Custom, and Functional Sequences.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Asequence) }
	* *Spaces* => R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes off of them.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aspaces) }
 * ***Computation Support Library***
	* *Feed* => Functionality to load, transform, and compute target metrics across feeds.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Afeed) }
	* *Historical* => Historical State Processing Utilities.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Ahistorical) }
	* *JSON* => Implementation of the RFC-4627 Compliant JSON Encoder/Decoder (Parser).
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Ajson) }
	* *Regression* => Regression Test Runs for Fixed Income, Numerical Analysis, and Spline Libraries.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aregression) }
	* *Service* => Environment, Product/Definition Containers, and hosts the Scenario/State Manipulation APIs.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aservice) }
