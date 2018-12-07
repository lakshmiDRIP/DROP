
# Statistical Learning Library


<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

Statistical Learning Library implements the Statistical Learning Analyzers and Machine Learning Schemes.


## Documentation

 |        Document         | Link |
 |-------------------------|------|
 | Technical Specification | [*Latest*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/StatisticalLearning/StatisticalLearning_v3.93.pdf) [*Previous*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/StatisticalLearning) |
 | User Guide              |  |
 | API                     | [*Javadoc*](https://lakshmidrip.github.io/DROP/Javadoc/index.html)|


## Component Projects

 * *Learning* => Agnostic Learning Bounds under Empirical Loss Minimization Schemes.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Alearning) }
 * *Sequence* => Bounds Metrics for Random, Custom, and Functional Sequences.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Asequence) }
 * *Spaces* => R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes off of them.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aspaces) }


## Coverage

 * Probabilistic Bounds
	* Motivation
	* Tail Probability Bounds Estimation - Survey
	* Basic Probability Inequalities
	* Cauchy-Schwartz Inequality
	* Association Inequalities
	* Moment, Gaussian, and Exponential Bounds
	* Bounding Sum of Independent Random Variables
	* Non-Moment Based Bounding - Hoeffding Bound
	* Moment Based Bounds
	* Binomial Tails
	* Custom Bounds for Special i.i.d. Sequences
	* References
 * Efron-Stein Bounds
	* Introduction
	* Martingale Differences Sum Inequality
	* Efron-Stein Inequality
	* Bounded Differences Inequality
	* Bounded Differences Inequality - Applications
	* Self-Bounding Functions
	* Configuration Functions
	* References
 * Entropy Methods
	* Introduction
	* Information Theory - Basics
	* Tensorization of the Entropy
	* Logarithmic Sobolev Inequalities
	* Logarithmic Sobolev Inequalities - Applications
	* Exponential Inequalities for Self-Bounding Functions
	* Combinatorial Entropy
	* Variations on the Theme of Self-Bounding Functions
	* References
 * Concentration of Measure
	* Introduction
	* Equivalent Bounded Differences Inequality
	* Convex Distance Inequality
	* Convex Distance Inequality - Proof
	* Application of the Convex Distance Inequality – Bin Packing
	* References
 * Standard SLT Framework
	* Statistical Learning Theory Monographs
	* Computational Learning Theory
	* Probably Approximately Correct (PAC) Learning
	* PAC Definitions and Terminology
	* SLT Introduction
	* The Setup
	* Algorithms for Reducing Over-fitting
	* Bayesian Normalized Regularizer Setup
	* References
 * Generalization and Consistency
	* Concept Motivation
	* Types of Consistency
	* Bias-Variance or Estimation-Approximation Trade-off
	* Bias Variance Decomposition
	* Bias Variance Optimization
	* Generalization and Consistency for kNN
	* References
 * Empirical Risk Minimization
	* ERM Literature and Introduction
	* Overview
	* The Loss Function and the Empirical Risk Minimization Principles
	* Application of the Central Limit Theorem (CLT) and Law of Large Numbers (LLN)
	* Inconsistency of Empirical Risk Minimizers
	* Uniform Convergence
	* ERM Complexity
	* References
 * Symmetrization
	* Introduction
	* Proof of the Symmetrization Lemma
	* References
 * Generalization Bounds
	* Union Bound
	* Shattering Coefficient
	* Empirical Risk Generalization Bound
	* Large Margin Bounds
	* References
 * Rademacher Complexity
	* Setup and Definition
	* Rademacher-based Uniform Convergence
	* VC Entropy
	* Chaining Technique
	* Literature
	* References
 * Local Rademacher Averages
	* Introduction
	* Star-Hull and Sub-root Functions
	* Local Rademacher Averages and Fixed Point
	* Local Rademacher Average – Consequences
 * Normalized ERM
	* Background
	* Computing the Normalized Empirical Risk Bounds
	* Denormalized Bounds
	* References
 * Noise Conditions
	* SLT Analysis Metrics
	* Types of Noise Conditions
	* Relative Loss Class
 * VC Theory and VC Dimension
	* Introduction
	* Empirical Process
	* Bounding the Empirical Loss Function
	* VC Dimension - Formal Definition
	* VC Dimension - Introduction
	* VC Dimension Examples
	* VC Dimension vs. Popper Dimension
	* References
 * Sauer Lemma and VC Classifier Framework
	* Motivation
	* Derivation of Sauer Lemma Bounds
	* Sauer Lemma ERM Bounds
	* VC Classifier Framework
	* References
 * Covering and Entropy Numbers
	* Motivation
	* Nomenclature - Normed Spaces
	* Covering, Entropy, and Dyadic Numbers
	* Background and Overview of Basic Results
	* References
 * Covering Numbers for Real-Valued Function Classes
	* Introduction
	* Functions of Bounded Variation
	* Functions of Bounded Variation – Upper Bound Proof
	* Functions of Bounded Variation – Lower Bound Proof
	* General Function Classes
	* General Function Class Bounds – Proof Sketch
	* General Function Class Bounds – Lemmas
	* General Function Class - Upper Bounds
	* General Function Class - Lower Bounds
	* References
 * Operator Theory Methods for Entropy Numbers
	* Introduction and Setup
	* Literature, Approaches, and Result
	* References
 * Generalization Bounds via Uniform Convergence
	* Basic Uniform Convergence Bounds
	* Loss Function Induced Classes
	* Standard Form of Uniform Convergence
	* References
 * Kernel Machines
	* Introduction
	* SVM – Capacity Control
	* Non-linear Kernels
	* Generalization Performance of Regularization Networks
	* Covering Number Determination Steps
	* Challenges Presenting Master Generalization Error
	* References
 * Entropy Numbers for Kernel Machines
	* Mercer Kernels
	* Equivalent Kernels
	* Mapping ${/phi}$ Into l<sub>2</sub>
	* Corrigenda to the Mercer Conditions
	* l<sub>2</sub> Unit Ball → /epsilon Mapping Scaling Operator A<sup>/hat</sup>
	* Unit Bounding Operator Entropy Numbers
	* The SVM Operator
	* Maurey’s Theorem
	* Bounds for SV Classes
	* Asymptotic Rates of Decay for the Entropy Numbers
	* References
 * Discrete Spectra of Convolution Operators
	* Kernels with Compact/Non-compact Support
	* Eigenvalues of the Operator
	* Choosing /nu
	* Extension to d-dimension
	* References
 * Covering Numbers for Given Decay Rates
	* Asymptotic/Non-asymptotic Decay of Covering Numbers
	* Polynomial Eigenvalue Decay
	* Summation and Integration of Non-decreasing Functions
	* Exponential Polynomial Decay
	* References
 * Kernels for High Dimensional Data
	* Introduction
	* Kernel Fourier Transforms
	* Degenerate Kernel Bounds
	* Covering Numbers for Degenerate Systems
	* Bounds for Kernels in R <sup>d</sup>
	* Impact of the Fourier Transform Decay on the Entropy Numbers
	* References
 * Regularization Networks Entropy Numbers Determination - Practice
	* Introduction
	* Custom Application of the Kernel Machines Entropy Numbers
	* Extensions to the Operator-Theoretic Viewpoint for Covering Numbers
	* References
 * Minimum Description Length Approach
	* Motivation
	* Coding Approaches
	* MDL Analyses
	* References
 * Bayesian Methods
	* Bayesian and Frequentist Approaches
	* Bayesian Approaches
	* References
 * Knowledge Based Bounds
	* Places to incorporate Bounds
	* Prior Knowledge into the Function Space
	* References
 * Approximation Error and Bayes’ Consistency
	* Motivation
	* Nested Function Spaces
	* Regularization
	* Achieving Zero Approximation Error
	* Rate of Convergence
	* References
 * No Free Lunch Theorem
	* Introduction
	* Algorithmic Consistency
	* NFT Formal Statements
	* References
 * Generative and Discriminative Models
	* Generative Models
	* Discriminant Models
	* Examples of Discriminant Approaches
	* Differences Between Generative and Discriminant Models
	* References
 * Supervised Learning
	* Introduction
	* Supervised Learning Practice Steps
	* Challenges with the Supervised Learning Practice
	* References
 * Unsupervised Learning
	* References
 * Machine Learning
	* Calibration vs. Training
 * Pattern Recognition
	* Introduction
	* Supervised vs. Unsupervised Pattern Recognition
	* Probabilistic Pattern Recognition
 	* Formulation of Pattern Recognition
	* Pattern Recognition Practice SKU
	* Pattern Recognition Applications
	* References
 * Statistical Classification
	* References
 * Linear Discriminant Analysis
	* Introduction
	* Setup and Formulation
	* Fisher’s Linear Discriminant
	* Quadratic Discriminant Analysis
	* References
 * Logistic Regression
	* Introduction
	* Formulation
	* Goodness of Fit
	* Mathematical Setup
	* Bayesian Logistic Regression
	* Logistic Regression Extensions
	* Model Suitability Tests with Cross Validation
	* References
 * Multinomial Logistic Regression
	* Introduction
	* Setup and Formulation
	* References
 * Decision Trees and Decision Lists
	* References
 * Variable Bandwidth Kernel Density Estimation
	* References
 * k-Nearest Neighbors Algorithm
	* References
 * Perceptron
	* References
 * Support Vector Machines (SVM)
	* References
 * Gene Expression Programming (GEP)
	* References
 * Cluster Analysis
	* Introduction
	* Cluster Models
	* Connectivity Based Clustering
	* Centroid Based Clustering
	* Distribution Based Clustering
	* Density Based Clustering
	* Recent Clustering Enhancements
	* Internal Cluster Evaluation
	* External Cluster Evaluation
	* Clustering Axiom
	* References
 * Mixture Model
	* Introduction
	* Generic Mixture Model Details
	* Specific Mixture Models
	* Mixture Model Samples
	* Identifiability
	* Expectation Maximization
	* Alternatives to EM
	* Mixture Model Extensions
	* References
 * Deep Learning
	* Introduction
	* Unsupervised Representation Learner
	* Deep Learning Using ANN
	* Deep Learning Architectures
	* Challenges with the DNN Approach
	* Deep Belief Networks (DBN)
	* Convolutional Neural Networks (CNN)
	* Deep Learning Evaluation Data Sets
	* Neurological Basis of Deep Learning
	* Criticism of Deep Learning
	* References
 * Hierarchical Clustering
	* References
 * k-Means Clustering
	* Introduction
	* Mathematical Formulation
	* The Standard Algorithm
	* k-Means Initialization Schemes
	* k-Means Complexity
	* k-Means Variations
	* k-Means Applications
	* Alternate k-Means Formulations
	* Alternate k-Means Formulations
 * Correlation Clustering
	* References
 * Kernel Principal Component Analysis (Kernel PCA)
	* References
 * Ensemble Learning
	* Introduction
	* Overview
	* Theoretical Underpinnings
	* Ensemble Aggregator Types
	* Bayes’ Optimal Classifier
	* Bagging and Boosting
	* Bayesian Model Averaging (BMA)
	* Bayesian Model Combination (BMC)
	* Bucket of Models (BOM)
	* Stacking
	* Ensemble Averaging vs. Basis Spline Representation
	* References
 * ANN Ensemble Averaging
	* Overview
	* Techniques and Results
	* References
 * Boosting
	* Overview
	* Philosophy behind Boosting Algorithms
	* Popular Boosting Algorithms and Drawbacks
	* References
 * Bootstrap Aggregating
	* Overview and Sample Generation
	* Bagging with 1NN – Theoretical Treatment
	* References
 * Tensors and Multi-linear Subspace Learning
	* Tensors
	* Multi-linear Subspace Learning
	* Multi-linear PCA
	* References
 * Kalman Filtering
	* Continuous Time Kalman Filtering
	* Non-linear Kalman Filtering
	* Kalman Smoothing
	* References
 * Particle Filtering
	* References
 * Regression Analysis
	* Linear Regression
	* Assumptions underlying Basic Linear Regression
	* Multivariate Regression Analysis
	* Multivariate Predictor/Response Regression
	* OLS on Basis Spline Representation
	* Extensions to Linear Regression Methodology
	* Linear Regression Estimator Extensions
	* Bayesian Approach to Regression Analysis
 * Component Analysis
	* Independent Component Analysis (ICA) - Specification
	* Independent Component Analysis (ICA) - Formulation
	* Principal Component Analysis
	* Principal Component Analysis – Constrained Formulation
	* 2D Principal Component Analysis – Constrained Formulation
	* 2D Principal Component Analysis – Lagrange Multiplier Based Constrained Optimization
	* nD Principal Component Analysis – Lagrange Multiplier Based Constrained Optimization
	* Information Theoretic Analysis of PCA
	* Empirical PCA Estimation from the Data Set
 * Kriging
 * Hidden Markov Models
	* HMM State Transition/Emission Parameter Estimation
	* HMM Based Inference - Applications
	* Non-Bayesian HMM Model Setup
	* Bayesian Extension to the HMM Model Setup
	* HMM in Practical World
	* References
 * Markov Chain Models
	* Markov Property
	* Markov Chains
	* Classification of the Markov Models
	* Monte Carlo Markov Chains (MCMC)
	* MCMC for Multi-dimensional Integrals
	* References
 * Markov Random and Condition Random Fields
	* Introduction and Background
	* MRF/CRF Axiomatic Definition/Properties
	* Clique Factorization
	* Inference in MRF/CRF
	* References
 * Maximum Entropy Markov Models (MEMM)
	* References
 * Probabilistic Grammar and Parsing
	* Parsing
	* Parser
	* Context-Free Grammar (CFG)
	* References
 * Bayesian Analysis: Concepts, Formulation, Usage, and Application
	* Framework Symbology
	* Applicability
	* Analysis of Bayesian Systems
	* Advantage of Bayesian over other systems
	* Bayesian Networks
	* Hypothesis Testing
	* Bayesian Updating
	* Maximum Entropy Techniques
	* Priors
	* Predictive Posteriors and Priors
	* Approximate Bayesian Computation
	* Measurement and Parametric Calibration
	* Regression Analysis
	* Bayesian Regression Analysis
	* Extensions to Regression Analysis
	* Spline Analysis of Bayesian Systems


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
