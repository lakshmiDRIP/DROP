
# Numerical Optimizer Library


<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

Numerical Optimizer Library contains the supporting Functionality for Numerical Methods - including R<sup>x</sup> Solvers, Linear Algebra, and Constrained Optimizers.


## Documentation

 |        Document         | Link |
 |-------------------------|------|
 | Technical Specification | [*Latest*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/NumericalOptimizer/NumericalOptimization_v3.92.pdf) [*Previous*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/NumericalOptimizer) |
 | User Guide              |  |
 | API                     | [*Javadoc*](https://lakshmidrip.github.io/DROP/Javadoc/index.html)|


## Component Projects

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


## Coverage

 * Introduction
	* Framework Glossary
	* Document Layout
 * Framework
 * Search Initialization
	* Bracketing
	* Objective Function Failure
	* Bracketing Start Initialization
	* Open Search Initialization
	* Search/Bracketing Initializer Heuristic Customization
 * Numerical Challenges in Search
 * Variate Iteration
 * Open Search Method: Newton Method
 * Closed Search Methods
	* Secant
	* Bracketing Iterative Search
	* Univariate Iterator Primitive: Bisection
	* Univariate Iterator Primitive: False Position
	* Univariate Iterator Primitive: Inverse Quadratic
	* Univariate Iterator Primitive: Ridder
	* Univariate compound iterator: Brent and Zheng
 * Polynomial Root Search
 * Meta-heuristics
	* Introduction
	* Properties and Classification
	* Meta-heuristics Techniques
	* Meta-heuristics Techniques in Combinatorial Problems
	* Key Meta-heuristics Historical Milestones
	* References
 * Introduction and Overview
	* Motivation, Background, and Setup
	* Convex Sets and Convex Hull
	* Properties of Convex Sets/Functions
	* Convex Optimization Problems
	* References
 * Newton’s Method in Optimization
	* Method
	* Higher Dimensions
	* Wolfe Condition
	* Armijo Rule and Curvature Condition
	* Rationale for the Wolfe Conditions
	* References
 * Constrained Optimization
	* Constrained Optimization – Definition and Description
	* General Form
	* Solution Methods
	* Constraint Optimization: Branch and Bound
	* Branch-and-Bound: First-Choice Bounding Conditions
	* Branch-and-Bound: Russian Doll Search
	* Branch-and-Bound: Bucket Elimination
	* References
 * Lagrange Multipliers
	* Motivation, Definition, and Problem Formulation
	* Introduction, Background, and Overview
	* Handling Multiple Constraints
	* Modern Formulation via Differentiable Manifolds
	* Interpretation of the Lagrange Multipliers
	* Lagrange Application: Maximal Information Entropy
	* Lagrange Application: Numerical Optimization Techniques
	* Lagrange Multipliers – Common Practice Applications
	* References
 * Karush-Kuhn-Tucker Conditions
	* Introduction, Overview, Purpose, and Motivation
	* Necessary Conditions for Optimization Problems
	* Regularity Conditions or Constraint Qualifications
	* Sufficiency Conditions
	* KKT Conditions Application - Economics
	* KKT Conditions Application - Value Function
	* Generalizations
	* References
 * Interior Point Method
	* Motivation, Background, and Literature Survey
	* Interior Point Methodology and Algorithm
	* References
 * Optimizer
	* Constrained Optimization using Lagrangian
	* Least Squares Optimizer
 * Multivariate Distribution
	* Parallels between Vector Calculus and Statistical Distribution Analysis
 * Linear Systems Analysis and Transformation
	* Matrix Transforms
	* Systems of Linear Equations
	* Orthogonalization
	* Gaussian Elimination
 * Rayleigh Quotient Iteration
	* Introduction
	* The Algorithm
	* References
 * Power Iteration
	* Introduction
	* The Method
	* Analysis
	* Applications
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
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
