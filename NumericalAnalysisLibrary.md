
# Numerical Analysis Library


<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

Numerical Analysis Library contains the supporting Functionality for Numerical Methods - including R<sup>x</sup> Solvers, Linear Algebra, and Statistical Measure Distributions.


## Documentation

 |        Document         | Link |
 |-------------------------|------|
 | Technical Specification | [*Latest*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/NumericalAnalysis/NumericalAnalysis_v4.67.pdf) [*Previous*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/NumericalAnalysis) |
 | User Guide              |  |
 | API                     | [*Javadoc*](https://lakshmidrip.github.io/DROP/Javadoc/index.html)|


## Component Projects

 * *Function* => Implementation and Solvers for a Suite of R<sup>x</sup> To R<sup>1</sup> Functions.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Afunction) }
 * *Measure* => Continuous and Discrete Measure Distributions and Variate Evolutions.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Ameasure) }
 * *Numerical* => Suite of DROP Numerical Analysis Utilities.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Anumerical) }


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
 * Sylvester's Formula
	* Overview
	* Conditions
	* Generalization
	* References
 * Numerical Integration
	* Introduction and Overview
	* Reasons for Numerical Integration
	* Methods for One-Dimensional Integrals
	* Quadrature Rules Based on Interpolating Functions
	* Generalized Mid-Point Rule Formulation
	* Adaptive Algorithms
	* Extrapolation Methods
	* A Priori Conservative Error Estimation
	* Integrals Over Infinite Intervals
	* Multi-dimensional Integrals
	* Monte Carlo
	* Sparse Grids
	* Bayesian Quadrature
	* Connections to Differential Equations
	* References
 * Gaussian Quadrature
	* Introduction and Overview
	* Gauss-Legendre Quadrature
	* Change of Interval
	* Other Forms
	* Fundamental Theorem
	* General Formula for the Weights
	* Proof that the Weights are Positive
	* Computation of Gaussian Quadrature Rules
	* Recurrence Relation
	* The Golub-Welsch Algorithm
	* Error Estimates
	* Gauss-Kronrod Rules
	* Gauss-Lobatto Rules
	* References
 * Gauss-Kronrod Quadrature
	* Introduction and Overview
	* Description
	* Example
	* Implementation
	* References
 * Gamma Distribution
	* Overview
	* Gamma Measures - Central Distribution Table
	* Definitions
	* Characterization Using Shape 𝜶 and Rate 𝜷
	* Characterization using Shape 𝒌 and Scale 𝜽
	* Properties – Skewness
	* Properties – Median Calculation
	* Properties – Summation
	* Properties – Scaling
	* Properties – Exponential Family
	* Properties – Logarithmic Expectation and Variance
	* Properties – Information Entropy
	* Properties – Kullback-Liebler Divergence
	* Properties – Laplace Transform
	* Related Distributions – General
	* Properties – Compound Gamma
	* Statistical Inference – Maximum Likelihood Parameter Estimation
	* Closed-Form Estimators
	* Bayesian Minimum Mean-Squared Error
	* Bayesian Inference Conjugate Prior
	* Occurrence and Applications
	* Computational Methods – Generating Gamma Distributed Random Variables
	* References
 * Chi Square Distribution
	* Overview
	* Definition
	* Introduction
	* Probability Density Function
	* Cumulative Distribution Function
	* Additivity
	* Sample Mean
	* Entropy
	* Non-central Moments
	* Asymptotic Properties
	* Cumulants
	* Relation to Other Distributions
	* Generalizations
	* Linear Combinations
	* Non-Central Chi-Squared Distribution
	* Generalized Chi-Squared Distribution
	* Gamma, Exponential, and Related Distribution
	* Occurrence and Applications
	* Table of χ^2 Values vs. p-Values
	* Summary Expressions
	* References
 * Non-central Chi-Square Distribution
	* Overview
	* Background
	* Non-central Chi-Square Distribution Table
	* Definition
	* Properties – Moment Generating Function
	* Properties – Moments
	* Cumulative Distribution Function
	* Approximation – including for Quantiles
	* Derivation of the PDF
	* Related Distributions
	* Transformations
	* Use in Tolerance Intervals
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
