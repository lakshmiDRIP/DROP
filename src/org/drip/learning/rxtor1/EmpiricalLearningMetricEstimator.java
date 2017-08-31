
package org.drip.learning.rxtor1;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * EmpiricalLearningMetricEstimator is the Estimator of the Empirical Loss and Risk, as well as the
 * 	corresponding Covering Numbers.
 *  
 * The References are:
 *  
 *  1) Alon, N., S. Ben-David, N. Cesa Bianchi, and D. Haussler (1997): Scale-sensitive Dimensions, Uniform
 *  	Convergence, and Learnability, Journal of Association of Computational Machinery, 44 (4) 615-631.
 * 
 *  2) Anthony, M., and P. L. Bartlett (1999): Artificial Neural Network Learning - Theoretical Foundations,
 *  	Cambridge University Press, Cambridge, UK.
 *  
 *  3) Kearns, M. J., R. E. Schapire, and L. M. Sellie (1994): Towards Efficient Agnostic Learning, Machine
 *  	Learning, 17 (2) 115-141.
 *  
 *  4) Lee, W. S., P. L. Bartlett, and R. C. Williamson (1998): The Importance of Convexity in Learning with
 *  	Squared Loss, IEEE Transactions on Information Theory, 44 1974-1980.
 * 
 *  5) Vapnik, V. N. (1998): Statistical Learning Theory, Wiley, New York.
 *
 * @author Lakshmi Krishnamurthy
 */

public interface EmpiricalLearningMetricEstimator {

	/**
	 * Retrieve the Underlying Learner Function Class
	 * 
	 * @return The Underlying Learner Function Class
	 */

	public abstract org.drip.spaces.functionclass.NormedRxToNormedR1Finite functionClass();

	/**
	 * Retrieve the Regularizer Function
	 * 
	 * @return The Regularizer Function
	 */

	public abstract org.drip.learning.regularization.RegularizationFunction regularizerFunction();

	/**
	 * Retrieve the Loss Class Sample Covering Number - L-Infinity or L-p based Based
	 * 
	 * @param gvvi The Validated Instance Vector Sequence
	 * @param dblEpsilon The Deviation of the Empirical Mean from the Population Mean
	 * @param bSupremum TRUE To Use the Supremum Metric in place of the Built-in Metric
	 * 
	 * @return The Loss Class Sample Covering Number - L-Infinity or L-p based Based
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double lossSampleCoveringNumber (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi,
		final double dblEpsilon,
		final boolean bSupremum)
		throws java.lang.Exception;

	/**
	 * Compute the Empirical Sample Loss
	 * 
	 * @param funcLearnerR1ToR1 The R^1 To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Empirical Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Empirical Loss cannot be computed
	 */

	public abstract double empiricalLoss (
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Empirical Sample Loss
	 * 
	 * @param funcLearnerRdToR1 The R^d To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Empirical Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Empirical Loss cannot be computed
	 */

	public abstract double empiricalLoss (
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Supremum Empirical Sample Loss
	 * 
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Supremum Empirical Sample Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Supremum Empirical Sample Loss cannot be computed
	 */

	public abstract org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumEmpiricalLoss (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Structural Sample Loss
	 * 
	 * @param funcLearnerR1ToR1 The R^1 To R^1 Learner Function
	 * @param gvvi The Validated Predictor Instance
	 * 
	 * @return The Structural Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Structural Loss cannot be computed
	 */

	public abstract double structuralLoss (
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
		throws java.lang.Exception;

	/**
	 * Compute the Structural Sample Loss
	 * 
	 * @param funcLearnerRdToR1 The R^d To R^1 Learner Function
	 * @param gvvi The Validated Predictor Instance
	 * 
	 * @return The Structural Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Structural Loss cannot be computed
	 */

	public abstract double structuralLoss (
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
		throws java.lang.Exception;

	/**
	 * Compute the Supremum Structural Sample Loss
	 * 
	 * @param gvviX The Validated Predictor Instance
	 * 
	 * @return The Supremum Structural Sample Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Supremum Structural Sample Loss cannot be computed
	 */

	public abstract org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumStructuralLoss (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX)
		throws java.lang.Exception;

	/**
	 * Compute the Regularized Sample Loss (Empirical + Structural)
	 * 
	 * @param funcLearnerR1ToR1 The R^1 To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Regularized Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Regularized Loss cannot be computed
	 */

	public abstract double regularizedLoss (
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Regularized Sample Loss (Empirical + Structural)
	 * 
	 * @param funcLearnerRdToR1 The R^d To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Regularized Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Regularized Loss cannot be computed
	 */

	public abstract double regularizedLoss (
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Supremum Regularized Sample Loss
	 * 
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Supremum Regularized Sample Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Supremum Regularized Sample Loss cannot be computed
	 */

	public abstract org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumRegularizedLoss (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Empirical Sample Risk
	 * 
	 * @param distR1R1 R^1 R^1 Multivariate Measure
	 * @param funcLearnerR1ToR1 The R^1 To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Empirical Sample Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Empirical Sample Risk cannot be computed
	 */

	public abstract double empiricalRisk (
		final org.drip.measure.continuous.R1R1 distR1R1,
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Empirical Sample Risk
	 * 
	 * @param distRdR1 R^d R^1 Multivariate Measure
	 * @param funcLearnerRdToR1 The R^d To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Empirical Sample Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Empirical Sample Risk cannot be computed
	 */

	public abstract double empiricalRisk (
		final org.drip.measure.continuous.RdR1 distRdR1,
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Supremum Empirical Sample Risk
	 * 
	 * @param distR1R1 R^1 R^1 Multivariate Measure
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Supremum Empirical Sample Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Supremum Empirical Sample Loss cannot be computed
	 */

	public abstract org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumEmpiricalRisk (
		final org.drip.measure.continuous.R1R1 distR1R1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Supremum Empirical Sample Risk
	 * 
	 * @param distRdR1 R^d R^1 Multivariate Measure
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Supremum Empirical Sample Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Supremum Empirical Sample Loss cannot be computed
	 */

	public abstract org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumEmpiricalRisk (
		final org.drip.measure.continuous.RdR1 distRdR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Structural Sample Risk
	 * 
	 * @param distR1R1 R^1 R^1 Multivariate Measure
	 * @param funcLearnerR1ToR1 The R^1 To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Structural Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Structural Risk cannot be computed
	 */

	public abstract double structuralRisk (
		final org.drip.measure.continuous.R1R1 distR1R1,
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Structural Sample Risk
	 * 
	 * @param distRdR1 R^d R^1 Multivariate Measure
	 * @param funcLearnerRdToR1 The R^d To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Structural Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Structural Risk cannot be computed
	 */

	public abstract double structuralRisk (
		final org.drip.measure.continuous.RdR1 distRdR1,
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Supremum Structural Sample Risk
	 * 
	 * @param distR1R1 R^1 R^1 Multivariate Measure
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Supremum Structural Sample Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Supremum Structural Sample Risk cannot be computed
	 */

	public abstract org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumStructuralRisk (
		final org.drip.measure.continuous.R1R1 distR1R1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Supremum Structural Sample Risk
	 * 
	 * @param distRdR1 R^d R^1 Multivariate Measure
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Supremum Structural Sample Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Supremum Structural Sample Risk cannot be computed
	 */

	public abstract org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumStructuralRisk (
		final org.drip.measure.continuous.RdR1 distRdR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Regularized Sample Risk (Empirical + Structural)
	 * 
	 * @param distR1R1 R^1 R^1 Multivariate Measure
	 * @param funcLearnerR1ToR1 The R^1 To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Regularized Sample Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Regularized Sample Risk cannot be computed
	 */

	public abstract double regularizedRisk (
		final org.drip.measure.continuous.R1R1 distR1R1,
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Regularized Sample Risk (Empirical + Structural)
	 * 
	 * @param distRdR1 R^d R^1 Multivariate Measure
	 * @param funcLearnerRdToR1 The R^d To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Regularized Sample Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Regularized Sample Risk cannot be computed
	 */

	public abstract double regularizedRisk (
		final org.drip.measure.continuous.RdR1 distRdR1,
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Supremum Regularized Sample Risk
	 * 
	 * @param distR1R1 R^1 R^1 Multivariate Measure
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Supremum Regularized Sample Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Supremum Regularized Sample Risk cannot be computed
	 */

	public abstract org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumRegularizedRisk (
		final org.drip.measure.continuous.R1R1 distR1R1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Supremum Regularized Sample Risk
	 * 
	 * @param distRdR1 R^d R^1 Multivariate Measure
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Supremum Regularized Sample Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Supremum Regularized Sample Risk cannot be computed
	 */

	public abstract org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumRegularizedRisk (
		final org.drip.measure.continuous.RdR1 distRdR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;
}
