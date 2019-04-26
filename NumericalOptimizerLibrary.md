
# Numerical Optimizer Library


<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

Numerical Optimizer Library contains the supporting Functionality for Numerical Methods - including R<sup>x</sup> Solvers, Linear Algebra, and Constrained Optimizers.


## Documentation

 |        Document         | Link |
 |-------------------------|------|
 | Technical Specification | [*Latest*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/NumericalOptimizer/NumericalOptimization_v4.23.pdf) [*Previous*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/NumericalOptimizer) |
 | User Guide              |  |
 | API                     | [*Javadoc*](https://lakshmidrip.github.io/DROP/Javadoc/index.html)|


## Component Projects

 * *Function* => Implementation and Solvers for a Suite of R<sup>x</sup> To R<sup>1</sup> Functions.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Afunction) }
 * *Measure* => Continuous and Discrete Measure Distributions and Variate Evolutions.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Ameasure) }
 * *Optimization* => Necessary, Sufficient, and Regularity Checks for Gradient Descent in a Constrained Optimization Setup.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aoptimization) }
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
 * Gamma Function
	* Introduction and Background
	* Motivation
	* Main Definition
	* Alternate Definitions: Euler’s Definition as an Infinite Product
	* Weierstrass Definition
	* In Terms of Generalized Laguerre Polynomials
	* General Properties
	* Inequalities
	* Stirling’s Formula
	* Residues
	* Minima
	* Integral Representations
	* Fourier Series Expansion
	* Raabe’s Formula
	* Pi Function
	* Relation to Other Functions
	* Particular Values
	* The Log-Gamma Function
	* The Log-Gamma Function Properties
	* Integration Over Log-Gamma
	* Approximations
	* Applications – Integration Problems
	* Calculating Products
	* Analytic Number Theory
	* References
 * Stirling's Approximation
	* Introduction and Overview
	* Derivation
	* An Alternative Derivation
	* Speed of Convergence and Error Estimates
	* Stirling’s Formula for the Gamma Function
	* Error Bounds
	* A Convergent Version of the Sterling’s Formula
	* Versions Suitable for Calculators
	* References
 * Lanczos Approximation
	* Introduction
	* Coefficients
	* References
 * Incomplete Gamma Function
	* Introduction and Overview
	* Definition
	* Properties
	* Continuation to Complex Values
	* Lower Incomplete Gamma FUnction - Holomorphic Extensions
	* Multi-Valuedness
	* Sectors
	* Branches
	* Relationshiop between Branches
	* Behavior near the Branch Point
	* Algebraic Relations
	* Integral Representation
	* Limit for z→±∞ - Real Values
	* Limit for z→±∞ - Complex Values
	* Sector-wise Convergence
	* Lower Incomplete Gamma Function – Overview
	* Upper Incomplete Gamma Function
	* Special Values
	* Asymptotic Behavior
	* Evaluation Formulas
	* Connection with Kummer’s Confluent Hyper-geometric Function
	* Multiplication Theorem
	* Software Implementation
	* Regularized Gamma Functions and Poisson Random Variables
	* Derivatives
	* Indefinite and Definite Integrals
	* References
 * Digamma Function
	* Introduction and Overview
	* Relation to the Harmonic Series
	* Integral Representations
	* Infinite Product Representation
	* Series Formula
	* Evaluation of Sums of Rational Functions
	* Taylor Series
	* Newton Series
	* Series with Gregory’s Coefficients, Cauchy Numbers, and Bernoulli Polynomials of the Second Kind
	* Reflection Formula
	* Recurrence Formula and Characterization
	* Some Finite Sums involving the Digamma Function
	* Gauss Digamma Theorem
	* Asymptotic Expansion
	* Inequalities
	* Computation and Approximation
	* Special Values
	* Roots of the Digamma Function
	* Regularization
	* References
 * Beta Function
	* Introduction and Overview
	* Properties
	* Relationship between Gamma Function and Beta Function
	* Derivatives
	* Integrals
	* Approximation
	* Incomplete Beta Function
	* Incomplete Beta Function - Properties
	* Multi-variate Beta Function
	* Software Implementation
	* References
 * Error Function
	* Introduction and Overview
	* Name
	* Applications
	* Properties
	* Taylor Series
	* Derivative and Integral
	* Burmann Series
	* Inverse Functions
	* Asymptotic Expansion
	* Continued Fraction Expansion
	* Integral of Error Function with Gaussian Density Function
	* Factorial Series
	* Numerical Approximations – Approximation with Elementary Functions
	* Polynomial
	* Table of Values
	* Related Functions – Complementary Error Function
	* Imaginary Error Function
	* Cumulative Distribution Function
	* Generalized Error Functions
	* Iterated Integrals of the Complementary Error Function
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
