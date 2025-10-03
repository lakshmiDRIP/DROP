
# Numerical Optimizer Library


<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

Numerical Optimizer Library contains the supporting Functionality for Numerical Optimization - including Constrained and Mixed Integer Non-Linear Optimizers.


## Documentation

 |        Document         | Link |
 |-------------------------|------|
 | Technical Specification | [*Latest*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/NumericalOptimizer/NumericalOptimizer_v7.11.pdf) [*Previous*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/NumericalOptimizer) |
 | User Guide              |  |
 | API                     | [*Javadoc*](https://lakshmidrip.github.io/DROP/Javadoc/index.html)|


## Component Projects

 * *Optimization* => Necessary, Sufficient, and Regularity Checks for Gradient Descent in a Constrained Optimization Setup.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aoptimization) }


## Coverage

 * Convex Optimization - Introduction and Overview
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
 * Spline Optimizer
	* Constrained Optimization using Lagrangian
	* Least Squares Optimizer
 * Karush-Kuhn-Tucker Conditions
	* Introduction, Overview, Purpose, and Motivation
	* Necessary Conditions for Optimization Problems	* Regularity Conditions or Constraint Qualifications
	* Sufficiency Conditions
	* KKT Conditions Application - Economics
	* KKT Conditions Application - Value Function
	* Generalizations
	* References
 * Interior Point Method
	* Motivation, Background, and Literature Survey
	* Interior Point Methodology and Algorithm
	* References
 * Portfolio Selection with Cardinality and Bound Constraints
	* Synposys
	* Introduction
	* Problem Formulation
	* Analysis of the Problem
	* Bender’s Decomposition
	* A Greedy Heuristic
	* Cutting Planes Algorithm and PROXACCPM – Concept and Tool
	* PROXACCPM Performance on the Generic Problem
	* Chvatal-Gomory Cuts and Variants
	* Chvatal-Gomory Cuts
	* Deriving the Cuts for the Setup
	* Branching Rule and Node Selection
	* Computational Results
	* Conclusion
	* References
 * Simplex Algorithm
	* Introduction
	* Overview
	* Standard Form
	* Simplex Tableau
	* Pivot Operations
	* The Algorithm
	* Entering Variable Selection
	* Leaving Variable Selection
	* Example #1
	* Finding an Initial Canonical Tableau
	* Example #2
	* Advanced Topics – Implementation
	* Degeneracy and Stalling
	* Efficiency
	* Other Algorithms
	* Linear-Fractional Programming
	* References
 * Optimal Control
	* Introduction
	* General Method
	* Linear Quadratic Control
	* Numerical Methods for Optimal Control
	* Discrete-time Optimal Control
	* Examples
		* Finite Time
	* References
 * Hamilton–Jacobi–Bellman Equation
	* Introduction
	* Optimal Control Problems
	* The Partial Differential Equation
	* Deriving the Equation
	* Solving the Equation
	* Extension to Stochastic Problems
		* Application to LQG-Control
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
