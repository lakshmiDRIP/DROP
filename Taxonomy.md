
# DROP Taxonomy


<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>



## Analytics Core Module
 * [***Fixed Income Analytics Library***](https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md) => Valuation and Risk Functionality of the Principal Asset Classes, i.e., Equity, Rates, Credit, FX, Commodity, and their Hybrids.
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
 * [***Asset Backed Analytics Library***](https://github.com/lakshmiDRIP/DROP/tree/master/AssetBackedAnalyticsLibrary.md) => Valuation and Risk Functionality for Asset Backed and Mortgage Backed Securities.
	* *Asset Backed* => Asset Backed Borrower and Loan Level Characteristics.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/assetbacked) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aassetbacked) }
 * [***XVA Analytics Library***](https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md) => Utilities to generate various Valuation Adjustments (Collateral VA/CVA/DVA/FBA/FCA/FVA/MVA/XVA).
	* *XVA* => Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Axva) }
 * [***Exposure and Margin Analytics Library***](https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md) => Computes the Scenario Exposures at the specified Trade Group Granularity.
	* *Exposure* => Exposure Group Level Collateralized/Uncollateralized Exposure.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aexposure) }
	* *SIMM* => Initial Margin Analytics based on ISDA SIMM and its Variants.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Asimm) }

## Portfolio Core Module
 * [***Asset Allocation Analytics Library***](https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md) => Optimal Portfolio Construction and Asset Allocation Functionality.
	* *Portfolio Construction* => Optimal and Constrained Portfolio Construction Functionality.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aportfolioconstruction) }
 * [***Transaction Cost Analytics Library***](https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md) => Estimating Single Trade/Portfolio Execution Cost, and corresponding Optimal Trajectories.
	* *Execution* => Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aexecution) }

## Numerical Core Module
 * [***Spline Builder Library***](https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md) => Functionality for constructing Spline Based Curves and Surfaces.
	* *Spline* => Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aspline) }
 * [***Statistical Learning Library***](https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md) => Statistical Learning Analyzers and Machine Learning Schemes.
	* *Learning* => Agnostic Learning Bounds under Empirical Loss Minimization Schemes.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Alearning) }
	* *Sequence* => Bounds Metrics for Random, Custom, and Functional Sequences.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Asequence) }
	* *Spaces* => R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes off of them.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aspaces) }
 * [***Numerical Optimizer Library***](https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md) => Functionality for Numerical Methods - including R<sup>x</sup> Solvers, Linear Algebra, and Constrained Optimizers.
	* *Function* => Implementation and Solvers for a Suite of R<sup>x</sup> To R<sup>1</sup> Functions.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Afunction) }
	* *Measure* => Continuous and Discrete Measure Distributions and Variate Evolutions.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Ameasure) }
	* *Optimization* => Necessary, Sufficient, and Regularity Checks for Gradient Descent in a Constrained Optimization Setup.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aoptimization) }
	* *Quant* => Suite of DROP Linear Algebra Utilities.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/quant) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aquant) }
 * ***Algorithm Support Library***
	* *Feed* => Functionality to load, transform, and compute target metrics across feeds.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Afeed) }
	* *Historical* => Historical State Processing Utilities.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Ahistorical) }
	* *JSON* => Implementation of the RFC-4627 Compliant JSON Encoder/Decoder (Parser).
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Ajson) }
	* *Regression* => Regression Test Runs for Fixed Income, Numerical Analysis, and Spline Libraries.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aregression) }
	* *Service* => Environment, Product/Definition Containers, and hosts the Scenario/State Manipulation APIs.
		* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service) | 
		[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aservice) }
