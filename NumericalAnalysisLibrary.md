
# Numerical Analysis Library


<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

Numerical Analysis Library contains the supporting Functionality for Numerical Methods - including R<sup>x</sup> Solvers, Linear Algebra, and Statistical Measure Distributions.


## Documentation

 |        Document         | Link |
 |-------------------------|------|
 | Technical Specification | [*Latest*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/NumericalAnalysis/NumericalAnalysis_v7.56.pdf) [*Previous*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/NumericalAnalysis) |
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
 * Monte-Carlo Integration
	* Introduction
	* Overview
		* Example
		* C/C++ Example
		* Python Example
		* Wolfram Mathematica Example
	* Recursive Stratified Sampling
		* MISER Monte Carlo
	* Importance Sampling
		* Algorithm
		* VEGAS Monte Carlo
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
 * Chi-Squared Distribution
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
 * Exponential Distribution
	* Overview
	* Definitions
		* Probability Density Function
		* Cumulative Distribution Function
		* Alternative Parameterization
	* Properties
		* Mean, Variance, Moments, and Median
		* Memorylessness Property of Exponential Random Variable
		* Quantiles
		* Conditional Value at Risk (Expected Shortfall)
		* Buffered Probability of Exceedance (bPOE)
		* Kullback-Leibler Divergence
		* Maximum Entropy Distribution
		* Distribution of the Minimum of Exponential Random Variables
		* Joint Moments of i.i.d. Exponential Order Statistics
		* Sum of Two Independent Exponential Random Variables
	* Related Distributions
	* Statistical Inference
		* Parameter Estimation
		* Fisher Information
		* Confidence Intervals
		* Bayesian Inference
	* Occurrence and Applications
		* Occurrence of Events
		* Prediction
	* Random Variate Generation
	* References
 * Hilbert Space
	* Overview
	* Definition and Illustration
		* Motivating Example: Euclidean Vector Space
		* Definition
		* Second Example: Sequence Spaces
	* Examples
		* Lebesgue Spaces
		* Sobolev Spaces
		* Spaces of Holomorphic Functions
			* Hardy Spaces
			* Bergman Spaces
	* Applications
		* Sturm-Liouville Theory
		* Partial Differential Equations
		* Ergodic Theory
		* Fourier Analysis
		* Quantum Mechanics
		* Color Perception
	* Properties
		* Pythagorean Identity
		* Parallelogram Identity and Polarization
		* Best Approximation
		* Duality
		* Weakly-convergent Sequences
		* Banach Space Properties
	* Operators on Hilbert Spaces
		* Bounded Operators
		* Unbounded Operators
	* Constructions
		* Direct Sums
		* Tensor Products
	* Orthonormal Bases
		* Sequence Spaces
		* Bessel's Inequality and Parseval's Formula
		* Hilbert Dimension
		* Separable Spaces
	* Orthogonal Complements and Projections
	* Spectral Theory
	* References
 * Positive-definite Kernel
	* Definition
		* Some General Properties
		* Examples of Positive-definite Kernels
	* Connection with Reproducing Kernel Hilbert Spaces and Feature Maps
	* Kernels and Distances
	* Some Applications
		* Kernels in Machine Learning
		* Kernels in Probabilistic Models
		* Numerical Solution of Partial Differential Equations
		* Other Applications
	* References
 * Reproducing Kernel Hilbert Space
	* Overview
	* Definition
	* Example
	* Moore-Aronszajn Theorem
	* Integral Operator's and Mercer's Theorem
	* Featur Maps
	* Properties
	* Common Examples
		* Bilinear Kernels
		* Polynomial Kernels
		* Radial Basis Function Kernels
			* Gaussian or Squared Exponential Kernel
			* Laplacian Kernel
		* Bergman Kernels
	* Extension to Vector-valued Functions
	* Connection between RKHS and ReLU Function
	* References
 * Representer Theorem
	* Overview
	* Formal Statement
	* Generalizations
	* Applications
	* References
 * Kernel Methods
	* Overview
	* Motivation and Informal Explanation
	* Mathematics: The Kernel Trick
	* Applications
	* References
 * Moment-generating Function
	* Definition
	* Examples
	* Calculation
		* Linear Transformations of Random Variables
		* Linear Combination of Independent Random Variables
		* Vector-valued Random Variables
	* Important Properties
		* Calculation of Moments
	* Other Properties
	* Relation to Other Functions
	* References
 * Fokker Planck Equation
	* One Dimension
	* Higher Dimensions
	* Generalization
	* Examples
		* Wiener Process
		* Boltzmann Distribution at the Thermodynamic Equilibrium
		* Ornstein–Uhlenbeck Process
		* Plasma Physics
	* Smoluchowski Diffusion Equation
	* Computational Considerations
		* 1-D Linear Potential Example
			* Theory
			* Simulation
	* Solution
	* Particular Cases with known Solution and Inversion
	* Fokker–Planck Equation and Path Integral
	* References
 * Householder Transformation
	* Overview
	* Definition
		* Transformation
		* Householder Matrix
		* Properties
	* Applications
		* Numerical Linear Algebra
			* QR Decomposition
			* Tridiagonalization
			* Examples
	* Computational and Theoretical Relationship to other Unitary Transformations
	* References
 * The Householder Transformation in Numerical Linear Algebra
	* Abstract
	* Linear Algebra
		* Geometric Meanings of the Determinant and Matrix Norm
		* Computation of Determinants
		* Computation of Matrix Inverses
		* Error Propagation
	* Gaussian Elimination
		* Row Reduction using Gaussian Elimination
		* Gaussian Elimination without Pivoting
		* Gaussian Elimination with Pivoting
	* Householder Transformations
		* Geometric Construction
		* Construction with Specified Source and Destination
		* Properties of Q, Algebraically Obtained
		* Properties of Q, Geometrically Obtained
		* Repeated Householders for Upper-Triangularization
		* Householders for Column-zeroing
		* Computation of Determinants
		* Computation of Matrix Inverses
		* Rotation Matrices
	* References
 * Successive Over-relaxation
	* Introduction
	* Formulation
	* Convergence
		* Convergence Rate
	* Algorithm
	* Symmetric Successive Over-relaxation
	* Other Applications of the Method
	* References
 * Symmetric Successive Over-relaxation
	* References
 * Tridiagonal Matrix Algorithm
	* Introduction
	* Derivation
	* Variants
	* References
 * Crank–Nicolson Method
	* Introduction
	* Principle
	* Example: 1D Diffusion
	* Example: 1D Diffusion with advection for steady flow, with multiple channel connections
	* Example: 2D Diffusion
	* Crank–Nicolson for nonlinear problems
	* Application in financial mathematics
	* References
 * Alternating Direction Implicit Method
	* Overview
	* ADI for Matrix Equations
		* The Method
		* When to use ADI
		* Shift-parameter Selection and the ADI Error Equation
			* Near-optimal Shift Parameters
			* Heuristic Shift-parameter Strategies
		* Factored ADI
	* ADI for Parabolic Equations
		* Example: 2D Diffusion Equation
		* Generalizations
	* Fundamental ADI FADI
		* Simplification of ADI to FADI
		* Relation to Other Implicit Methods
	* References
 * Sylvester Equation
	* Existence and uniqueness of the solutions
	* Roth's removal rule
	* Numerical solutions
	* References
 * Bartels–Stewart Algorithm
	* Introduction
	* The Algorithm
		* Computational Cost
		* Simplifications and Special Cases
	* The Hessenberg–Schur algorithm
	* References
 * Triangular Matrix
	* Description
	* Forward and Back Substitution
		* Forward Substitution
	* Properties
	* Special Forms
		* Unitriangular Matrix
		* Strictly Triangular Matrix
		* Atomic Triangular Matrix
			* Lower Block Triangular Matrix
			* Upper Block Triangular Matrix
	* Triangularizability
		* Simultaneous Triangularizability
	* Algebras of Triangular Matrices
		* Borel Subgroups and Borel Subalgebras
		* Examples
	* References
 * QR Decomposition
	* Introduction
	* Cases and Definitions
		* Square Matrix
		* Rectangular Matrix
		* QL, RQ and LQ Decompositions
	* Computing the QR Decomposition
		* Using the Gram–Schmidt Process
			* Example
			* Relation to RQ decomposition
			* Advantages and Disadvantages
		* Using Householder Reflections
			* Example
			* Advantages and Disadvantages
		* Using Givens Rotations
			* Example
			* Advantages and Disadvantages
	* Connection to a Determinant or Product of Eigenvalues
	* Column Pivoting
	* Using for Solution to Linear Inverse Problems
	* Generalizations
	* References
 * Gershgorin Circle Theorem
	* Introduction
	* Statement and Proof
	* Discussion
	* Strengthening of the Theorem
	* Application
	* Example
	* References
 * Condition Number
	* Introduction
	* General Definition in the Context of Error Analysis
	* Matrices
	* Non-linear
		* One Variable
		* Several Variables
	* References
 * Unitary Matrix
	* Introduction
	* Properties
	* Equivalent Conditions
	* Elementary Constructions
		* 2 × 2 Unitary Matrix
	* References
 * Matrix Norm
	* Introduction
	* Preliminaries
	* Matrix Norms induced by Vector Norms
		* Matrix Norms induced by Vector p-Norms
			* p = 1, ∞
			* Spectral Norm (p = 2)
		* Matrix Norms induced by Vector α- and β-norms
		* Properties
		* Square Matrices
	* Consistent and Compatible Norms
	* Entry-wise Matrix Norms
		* L (2,1) and L (p,q) Norms
		* Frobenius Norm
		* Max Norm
	* Schatten Norms
	* Monotone Norms
	* Cut Norms
	* Equivalence of Norms
		* Examples of Norm Equivalence
	* References
 * Spectral Radius
	* Introduction
	* Definition
		* Matrices
		* Bounded Linear Operators
		* Graphs
	* Upper Bounds
		* Upper Bounds on the Spectral Radius of a Matrix
		* Upper Bounds on the Spectral Radius of a Graph
	* Power Sequence
	* Gelfand's Formula
		* Theorem
		* Proof
		* Corollary
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
