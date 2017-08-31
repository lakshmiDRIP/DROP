
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
 * GeneralizedLearner implements the Learner Class that holds the Space of Normed R^x To Normed R^1 Learning
 * 	Functions along with their Custom Empirical Loss. Class-Specific Asymptotic Sample, Covering Number based
 *  Upper Probability Bounds and other Parameters are also maintained.
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

public abstract class GeneralizedLearner implements org.drip.learning.rxtor1.EmpiricalLearningMetricEstimator
{
	private org.drip.learning.bound.CoveringNumberLossBound _funcClassCNLB = null;
	private org.drip.spaces.functionclass.NormedRxToNormedR1Finite _funcClassRxToR1 = null;
	private org.drip.learning.regularization.RegularizationFunction _regularizerFunc = null;

	/**
	 * GeneralizedLearner Constructor
	 * 
	 * @param funcClassRxToR1 R^x To R^1 Function Class
	 * @param funcClassCNLB The Function Class Covering Number based Deviation Upper Probability Bound
	 * 	Generator
	 * @param regularizerFunc The Regularizer Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public GeneralizedLearner (
		final org.drip.spaces.functionclass.NormedRxToNormedR1Finite funcClassRxToR1,
		final org.drip.learning.bound.CoveringNumberLossBound funcClassCNLB,
		final org.drip.learning.regularization.RegularizationFunction regularizerFunc)
		throws java.lang.Exception
	{
		if (null == (_funcClassRxToR1 = funcClassRxToR1) || null == (_funcClassCNLB = funcClassCNLB) || null
			== (_regularizerFunc = regularizerFunc))
			throw new java.lang.Exception ("GeneralizedLearner ctr: Invalid Inputs");
	}

	@Override public org.drip.spaces.functionclass.NormedRxToNormedR1Finite functionClass()
	{
		return _funcClassRxToR1;
	}

	@Override public org.drip.learning.regularization.RegularizationFunction regularizerFunction()
	{
		return _regularizerFunc;
	}

	@Override public org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumEmpiricalLoss (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
	{
		try {
			return new org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator
				(org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator.SUPREMUM_PENALTY_EMPIRICAL_LOSS,
					this, gvviY, null, null).supremum (gvviX);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public double structuralLoss (
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
		throws java.lang.Exception
	{
		if (null == gvvi || !(gvvi instanceof org.drip.spaces.instance.ValidatedR1) &&
			(_funcClassRxToR1 instanceof org.drip.spaces.functionclass.NormedR1ToNormedR1Finite))
			throw new java.lang.Exception ("GeneralizedLearner::structuralLoss => Invalid Inputs");

		org.drip.function.definition.R1ToR1 funcRegularizerR1ToR1 = _regularizerFunc.r1Tor1();

		if (null == funcRegularizerR1ToR1)
			throw new java.lang.Exception ("GeneralizedLearner::structuralLoss => Invalid Inputs");

		org.drip.spaces.functionclass.NormedR1ToNormedR1Finite finiteClassR1ToR1 =
			(org.drip.spaces.functionclass.NormedR1ToNormedR1Finite) _funcClassRxToR1;

		org.drip.spaces.metric.GeneralizedMetricVectorSpace gmvsInput =
			finiteClassR1ToR1.inputMetricVectorSpace();

		if (gmvsInput instanceof org.drip.spaces.metric.R1Normed)
			throw new java.lang.Exception ("GeneralizedLearner::structuralLoss => Invalid Inputs");

		org.drip.spaces.metric.GeneralizedMetricVectorSpace gmvsOutput =
			finiteClassR1ToR1.outputMetricVectorSpace();

		if (gmvsOutput instanceof org.drip.spaces.metric.R1Continuous)
			throw new java.lang.Exception ("GeneralizedLearner::structuralLoss => Invalid Inputs");

		org.drip.learning.regularization.RegularizerR1ToR1 regularizerR1ToR1 =
			org.drip.learning.regularization.RegularizerBuilder.ToR1Continuous (funcRegularizerR1ToR1,
				(org.drip.spaces.metric.R1Normed) gmvsInput, (org.drip.spaces.metric.R1Continuous)
					gmvsOutput, _regularizerFunc.lambda());

		if (null == regularizerR1ToR1)
			throw new java.lang.Exception ("GeneralizedLearner::structuralLoss => Invalid Inputs");

		return regularizerR1ToR1.structuralLoss (funcLearnerR1ToR1, ((org.drip.spaces.instance.ValidatedR1)
			gvvi).instance());
	}

	@Override public double structuralLoss (
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
		throws java.lang.Exception
	{
		if (null == gvvi || !(gvvi instanceof org.drip.spaces.instance.ValidatedRd) &&
			(_funcClassRxToR1 instanceof org.drip.spaces.functionclass.NormedRdToNormedR1Finite))
			throw new java.lang.Exception ("GeneralizedLearner::structuralLoss => Invalid Inputs");

		org.drip.function.definition.RdToR1 funcRegularizerRdToR1 = _regularizerFunc.rdTor1();

		if (null == funcRegularizerRdToR1)
			throw new java.lang.Exception ("GeneralizedLearner::structuralLoss => Invalid Inputs");

		org.drip.spaces.functionclass.NormedRdToNormedR1Finite finiteClassRdToR1 =
			(org.drip.spaces.functionclass.NormedRdToNormedR1Finite) _funcClassRxToR1;

		org.drip.spaces.metric.GeneralizedMetricVectorSpace gmvsInput =
			finiteClassRdToR1.inputMetricVectorSpace();

		if (gmvsInput instanceof org.drip.spaces.metric.RdNormed)
			throw new java.lang.Exception ("GeneralizedLearner::structuralLoss => Invalid Inputs");

		org.drip.spaces.metric.GeneralizedMetricVectorSpace gmvsOutput =
			finiteClassRdToR1.outputMetricVectorSpace();

		if (gmvsOutput instanceof org.drip.spaces.metric.R1Continuous)
			throw new java.lang.Exception ("GeneralizedLearner::structuralLoss => Invalid Inputs");

		org.drip.learning.regularization.RegularizerRdToR1 regularizerRdToR1 =
			org.drip.learning.regularization.RegularizerBuilder.ToRdContinuous (funcRegularizerRdToR1,
				(org.drip.spaces.metric.RdNormed) gmvsInput, (org.drip.spaces.metric.R1Continuous)
					gmvsOutput, _regularizerFunc.lambda());

		if (null == regularizerRdToR1)
			throw new java.lang.Exception ("GeneralizedLearner::structuralLoss => Invalid Inputs");

		return regularizerRdToR1.structuralLoss (funcLearnerRdToR1, ((org.drip.spaces.instance.ValidatedRd)
			gvvi).instance());
	}

	@Override public org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumStructuralLoss (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX)
	{
		try {
			return new org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator
				(org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator.SUPREMUM_PENALTY_STRUCTURAL_LOSS,
					this, null, null, null).supremum (gvviX);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public double regularizedLoss (
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception
	{
		return empiricalLoss (funcLearnerR1ToR1, gvviX, gvviY) + structuralLoss (funcLearnerR1ToR1, gvviX);
	}

	@Override public double regularizedLoss (
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception
	{
		return empiricalLoss (funcLearnerRdToR1, gvviX, gvviY) + structuralLoss (funcLearnerRdToR1, gvviX);
	}

	@Override public org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumRegularizedLoss (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
	{
		try {
			return new org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator
				(org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator.SUPREMUM_PENALTY_REGULARIZED_LOSS,
				this, gvviY, null, null).supremum (gvviX);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumEmpiricalRisk (
		final org.drip.measure.continuous.R1R1 distR1R1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
	{
		try {
			return new org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator
				(org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator.SUPREMUM_PENALTY_EMPIRICAL_RISK,
					this, gvviY, distR1R1, null).supremum (gvviX);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumEmpiricalRisk (
		final org.drip.measure.continuous.RdR1 distRdR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
	{
		try {
			return new org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator
				(org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator.SUPREMUM_PENALTY_EMPIRICAL_RISK,
					this, gvviY, null, distRdR1).supremum (gvviX);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public double structuralRisk (
		final org.drip.measure.continuous.R1R1 distR1R1,
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception
	{
		if (null == distR1R1 || null == gvviX || null == gvviY || !(gvviX instanceof
			org.drip.spaces.instance.ValidatedR1) || !(gvviY instanceof
				org.drip.spaces.instance.ValidatedR1) && !(_funcClassRxToR1 instanceof
					org.drip.spaces.functionclass.NormedR1ToNormedR1Finite))
			throw new java.lang.Exception ("GeneralizedLearner::structuralRisk => Invalid Inputs");

		org.drip.function.definition.R1ToR1 funcRegularizerR1ToR1 = _regularizerFunc.r1Tor1();

		if (null == funcRegularizerR1ToR1)
			throw new java.lang.Exception ("GeneralizedLearner::structuralRisk => Invalid Inputs");

		org.drip.spaces.functionclass.NormedR1ToNormedR1Finite finiteClassR1ToR1 =
			(org.drip.spaces.functionclass.NormedR1ToNormedR1Finite) _funcClassRxToR1;

		org.drip.spaces.metric.GeneralizedMetricVectorSpace gmvsInput =
			finiteClassR1ToR1.inputMetricVectorSpace();

		if (gmvsInput instanceof org.drip.spaces.metric.R1Normed)
			throw new java.lang.Exception ("GeneralizedLearner::structuralRisk => Invalid Inputs");

		org.drip.spaces.metric.GeneralizedMetricVectorSpace gmvsOutput =
			finiteClassR1ToR1.outputMetricVectorSpace();

		if (gmvsOutput instanceof org.drip.spaces.metric.R1Continuous)
			throw new java.lang.Exception ("GeneralizedLearner::structuralRisk => Invalid Inputs");

		org.drip.learning.regularization.RegularizerR1ToR1 regularizerR1ToR1 =
			org.drip.learning.regularization.RegularizerBuilder.ToR1Continuous (funcRegularizerR1ToR1,
				(org.drip.spaces.metric.R1Normed) gmvsInput, (org.drip.spaces.metric.R1Continuous)
					gmvsOutput, _regularizerFunc.lambda());

		if (null == regularizerR1ToR1)
			throw new java.lang.Exception ("GeneralizedLearner::structuralRisk => Invalid Inputs");

		return regularizerR1ToR1.structuralRisk (distR1R1, funcLearnerR1ToR1,
			((org.drip.spaces.instance.ValidatedR1) gvviX).instance(),
				((org.drip.spaces.instance.ValidatedR1) gvviY).instance());
	}

	@Override public double structuralRisk (
		final org.drip.measure.continuous.RdR1 distRdR1,
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception
	{
		if (null == distRdR1 || null == gvviX || null == gvviY || !(gvviX instanceof
			org.drip.spaces.instance.ValidatedRd) || !(gvviY instanceof
				org.drip.spaces.instance.ValidatedR1) && !(_funcClassRxToR1 instanceof
					org.drip.spaces.functionclass.NormedR1ToNormedR1Finite))
			throw new java.lang.Exception ("GeneralizedLearner::structuralRisk => Invalid Inputs");

		org.drip.function.definition.RdToR1 funcRegularizerRdToR1 = _regularizerFunc.rdTor1();

		if (null == funcRegularizerRdToR1)
			throw new java.lang.Exception ("GeneralizedLearner::structuralRisk => Invalid Inputs");

		org.drip.spaces.functionclass.NormedRdToNormedR1Finite finiteClassRdToR1 =
			(org.drip.spaces.functionclass.NormedRdToNormedR1Finite) _funcClassRxToR1;

		org.drip.spaces.metric.GeneralizedMetricVectorSpace gmvsInput =
			finiteClassRdToR1.inputMetricVectorSpace();

		if (gmvsInput instanceof org.drip.spaces.metric.RdNormed)
			throw new java.lang.Exception ("GeneralizedLearner::structuralRisk => Invalid Inputs");

		org.drip.spaces.metric.GeneralizedMetricVectorSpace gmvsOutput =
			finiteClassRdToR1.outputMetricVectorSpace();

		if (gmvsOutput instanceof org.drip.spaces.metric.R1Continuous)
			throw new java.lang.Exception ("GeneralizedLearner::structuralRisk => Invalid Inputs");

		org.drip.learning.regularization.RegularizerRdToR1 regularizerRdToR1 =
			org.drip.learning.regularization.RegularizerBuilder.ToRdContinuous (funcRegularizerRdToR1,
				(org.drip.spaces.metric.RdNormed) gmvsInput, (org.drip.spaces.metric.R1Continuous)
					gmvsOutput, _regularizerFunc.lambda());

		if (null == regularizerRdToR1)
			throw new java.lang.Exception ("GeneralizedLearner::structuralRisk => Invalid Inputs");

		return regularizerRdToR1.structuralRisk (distRdR1, funcLearnerRdToR1,
			((org.drip.spaces.instance.ValidatedRd) gvviX).instance(),
				((org.drip.spaces.instance.ValidatedR1) gvviY).instance());
	}

	@Override public org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumStructuralRisk (
		final org.drip.measure.continuous.R1R1 distR1R1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
	{
		try {
			return new org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator
				(org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator.SUPREMUM_PENALTY_STRUCTURAL_RISK,
					this, gvviY, distR1R1, null).supremum (gvviX);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumStructuralRisk (
		final org.drip.measure.continuous.RdR1 distRdR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
	{
		try {
			return new org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator
				(org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator.SUPREMUM_PENALTY_STRUCTURAL_RISK,
					this, gvviY, null, distRdR1).supremum (gvviX);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public double regularizedRisk (
		final org.drip.measure.continuous.R1R1 distR1R1,
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception
	{
		return empiricalRisk (distR1R1, funcLearnerR1ToR1, gvviX, gvviY) + structuralRisk (distR1R1,
			funcLearnerR1ToR1, gvviX, gvviY);
	}

	@Override public double regularizedRisk (
		final org.drip.measure.continuous.RdR1 distRdR1,
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception
	{
		return empiricalRisk (distRdR1, funcLearnerRdToR1, gvviX, gvviY) + structuralRisk (distRdR1,
			funcLearnerRdToR1, gvviX, gvviY);
	}

	@Override public org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumRegularizedRisk (
		final org.drip.measure.continuous.R1R1 distR1R1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
	{
		try {
			return new org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator
				(org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator.SUPREMUM_PENALTY_REGULARIZED_RISK,
				this, gvviY, distR1R1, null).supremum (gvviX);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumRegularizedRisk (
		final org.drip.measure.continuous.RdR1 distRdR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
	{
		try {
			return new org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator
				(org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator.SUPREMUM_PENALTY_REGULARIZED_RISK,
				this, gvviY, null, distRdR1).supremum (gvviX);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Covering Number based Deviation Upper Probability Bound Generator
	 * 
	 * @return The Covering Number based Deviation Upper Probability Bound Generator
	 */

	public org.drip.learning.bound.CoveringNumberLossBound coveringLossBoundEvaluator()
	{
		return _funcClassCNLB;
	}

	/**
	 * Compute the Upper Bound of the Probability of the Absolute Deviation of the Empirical Mean from the
	 * 	Population Mean using the Function Class Supremum Covering Number for General-Purpose Learning
	 * 
	 * @param iSampleSize The Sample Size
	 * @param dblEpsilon The Deviation of the Empirical Mean from the Population Mean
	 * @param bSupremum TRUE To Use the Supremum Metric in place of the Built-in Metric
	 * 
	 * @return The Upper Bound of the Probability of the Absolute Deviation of the Empirical Mean from the
	 * 	Population Mean using the Function Class Supremum Covering Number for General-Purpose Learning
	 * 
	 * @throws java.lang.Exception Thrown if the Upper Probability Bound cannot be computed
	 */

	public double genericCoveringProbabilityBound (
		final int iSampleSize,
		final double dblEpsilon,
		final boolean bSupremum)
		throws java.lang.Exception
	{
		return _funcClassCNLB.deviationProbabilityUpperBound (iSampleSize, dblEpsilon) * (bSupremum ?
			_funcClassRxToR1.populationSupremumCoveringNumber (dblEpsilon) :
				_funcClassRxToR1.populationCoveringNumber (dblEpsilon));
	}

	/**
	 * Compute the Minimum Possible Sample Size needed to generate the required Upper Probability Bound for
	 *  the Specified Empirical Deviation using the Covering Number Convergence Bounds.
	 *  
	 * @param dblEpsilon The Deviation of the Empirical Mean from the Population Mean
	 * @param dblDeviationUpperProbabilityBound The Upper Bound of the Probability for the given Deviation
	 * @param bSupremum TRUE To Use the Supremum Metric in place of the Built-in Metric
	 * 
	 * @return The Minimum Possible Sample Size
	 * 
	 * @throws java.lang.Exception Thrown if the Minimum Sample Size cannot be computed
	 */

	public double genericCoveringSampleSize (
		final double dblEpsilon,
		final double dblDeviationUpperProbabilityBound,
		final boolean bSupremum)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblEpsilon) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblDeviationUpperProbabilityBound))
			throw new java.lang.Exception
				("GeneralizedLearner::genericCoveringSampleSize => Invalid Inputs");

		org.drip.function.definition.R1ToR1 funcDeviationUpperProbabilityBound = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblSampleSize)
				throws java.lang.Exception
			{
				return genericCoveringProbabilityBound ((int) dblSampleSize, dblEpsilon, bSupremum);
			}
		};

		org.drip.function.r1tor1solver.FixedPointFinderOutput fpfo = new
			org.drip.function.r1tor1solver.FixedPointFinderZheng (dblDeviationUpperProbabilityBound,
				funcDeviationUpperProbabilityBound, false).findRoot();

		if (null == fpfo || !fpfo.containsRoot())
			throw new java.lang.Exception
				("GeneralizedLearner::genericCoveringSampleSize => Cannot Estimate Minimal Sample Size");

		return fpfo.getRoot();
	}

	/**
	 * Compute the Sample/Data Dependent Upper Bound of the Probability of the Absolute Deviation between
	 *  the Empirical and the Population Means using the Function Class Supremum Covering Number for
	 *  General-Purpose Learning
	 * 
	 * @param gvvi The Validated Instance Vector Sequence
	 * @param iSampleSize The Sample Size
	 * @param dblEpsilon The Deviation of the Empirical Mean from the Population Mean
	 * @param bSupremum TRUE To Use the Supremum Metric in place of the Built-in Metric
	 * 
	 * @return The Sample/Data Dependent Upper Bound of the Probability of the Absolute Deviation between
	 *  the Empirical and the Population Means using the Function Class Supremum Covering Number for
	 *  General-Purpose Learning
	 * 
	 * @throws java.lang.Exception Thrown if the Upper Probability Bound cannot be computed
	 */

	public double genericCoveringProbabilityBound (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi,
		final int iSampleSize,
		final double dblEpsilon,
		final boolean bSupremum)
		throws java.lang.Exception
	{
		return _funcClassCNLB.deviationProbabilityUpperBound (iSampleSize, dblEpsilon) *
			lossSampleCoveringNumber (gvvi, dblEpsilon, bSupremum);
	}

	/**
	 * Compute the Minimum Possible Sample Size needed to generate the required Upper Probability Bound for
	 *  the Specified Empirical Deviation using the Covering Number Convergence Bounds.
	 *  
	 * @param gvvi The Validated Instance Vector Sequence
	 * @param dblEpsilon The Deviation of the Empirical Mean from the Population Mean
	 * @param dblDeviationUpperProbabilityBound The Upper Bound of the Probability for the given Deviation
	 * @param bSupremum TRUE To Use the Supremum Metric in place of the Built-in Metric
	 * 
	 * @return The Minimum Possible Sample Size
	 * 
	 * @throws java.lang.Exception Thrown if the Minimum Sample Size cannot be computed
	 */

	public double genericCoveringSampleSize (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi,
		final double dblEpsilon,
		final double dblDeviationUpperProbabilityBound,
		final boolean bSupremum)
		throws java.lang.Exception
	{
		if (null == gvvi || !org.drip.quant.common.NumberUtil.IsValid (dblEpsilon) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblDeviationUpperProbabilityBound))
			throw new java.lang.Exception
				("GeneralizedLearner::genericCoveringSampleSize => Invalid Inputs");

		org.drip.function.definition.R1ToR1 funcDeviationUpperProbabilityBound = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblSampleSize)
				throws java.lang.Exception
			{
				return genericCoveringProbabilityBound (gvvi, (int) dblSampleSize, dblEpsilon, bSupremum);
			}
		};

		org.drip.function.r1tor1solver.FixedPointFinderOutput fpfo = new
			org.drip.function.r1tor1solver.FixedPointFinderZheng (dblDeviationUpperProbabilityBound,
				funcDeviationUpperProbabilityBound, false).findRoot();

		if (null == fpfo || !fpfo.containsRoot())
			throw new java.lang.Exception
				("GeneralizedLearner::genericCoveringSampleSize => Cannot Estimate Minimal Sample Size");

		return fpfo.getRoot();
	}

	/**
	 * Compute the Upper Bound of the Probability of the Absolute Deviation between the Empirical and the
	 * 	Population Means using the Function Class Supremum Covering Number for Regression Learning
	 * 
	 * @param iSampleSize The Sample Size
	 * @param dblEpsilon The Deviation of the Empirical Mean from the Population Mean
	 * @param bSupremum TRUE To Use the Supremum Metric in place of the Built-in Metric
	 * 
	 * @return The Upper Bound of the Probability of the Absolute Deviation between the Empirical and the
	 * 	Population Means using the Function Class Supremum Covering Number for Regression Learning
	 * 
	 * @throws java.lang.Exception Thrown if the Upper Probability Bound cannot be computed
	 */

	public double regressorCoveringProbabilityBound (
		final int iSampleSize,
		final double dblEpsilon,
		final boolean bSupremum)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblEpsilon) || 0. >= dblEpsilon || iSampleSize < (2. /
			(dblEpsilon * dblEpsilon)))
			throw new java.lang.Exception
				("GeneralizedLearner::regressorCoveringProbabilityBound => Invalid Inputs");

		org.drip.function.definition.R1ToR1 funcSampleCoefficient = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblSampleSize)
				throws java.lang.Exception
			{
				return 12. * dblSampleSize;
			}
		};

		return (new org.drip.learning.bound.CoveringNumberLossBound (funcSampleCoefficient, 2.,
			36.)).deviationProbabilityUpperBound (iSampleSize, dblEpsilon) * (bSupremum ?
				_funcClassRxToR1.populationSupremumCoveringNumber (dblEpsilon / 6.) :
					_funcClassRxToR1.populationCoveringNumber (dblEpsilon / 6.));
	}

	/**
	 * Compute the Minimum Possible Sample Size needed to generate the required Upper Probability Bound for
	 *  the Specified Empirical Deviation using the Covering Number Convergence Bounds for Regression
	 *  Learning.
	 *  
	 * @param dblEpsilon The Deviation of the Empirical Mean from the Population Mean
	 * @param dblDeviationUpperProbabilityBound The Upper Bound of the Probability for the given Deviation
	 * @param bSupremum TRUE To Use the Supremum Metric in place of the Built-in Metric
	 * 
	 * @return The Minimum Possible Sample Size
	 * 
	 * @throws java.lang.Exception Thrown if the Minimum Sample Size cannot be computed
	 */

	public double regressorCoveringSampleSize (
		final double dblEpsilon,
		final double dblDeviationUpperProbabilityBound,
		final boolean bSupremum)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblEpsilon) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblDeviationUpperProbabilityBound))
			throw new java.lang.Exception
				("GeneralizedLearner::regressorCoveringSampleSize => Invalid Inputs");

		org.drip.function.definition.R1ToR1 funcDeviationUpperProbabilityBound = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblSampleSize)
				throws java.lang.Exception
			{
				return regressorCoveringProbabilityBound ((int) dblSampleSize, dblEpsilon, bSupremum);
			}
		};

		org.drip.function.r1tor1solver.FixedPointFinderOutput fpfo = new
			org.drip.function.r1tor1solver.FixedPointFinderZheng (dblDeviationUpperProbabilityBound,
				funcDeviationUpperProbabilityBound, false).findRoot();

		if (null == fpfo || !fpfo.containsRoot())
			throw new java.lang.Exception
				("GeneralizedLearner::regressorCoveringSampleSize => Cannot Estimate Minimal Sample Size");

		return fpfo.getRoot();
	}

	/**
	 * Compute the Sample/Data Dependent Upper Bound of the Probability of the Absolute Deviation between
	 *  the Empirical and the Population Means using the Function Class Supremum Covering Number for
	 *  Regression Learning
	 * 
	 * @param gvvi The Validated Instance Vector Sequence
	 * @param iSampleSize The Sample Size
	 * @param dblEpsilon The Deviation of the Empirical Mean from the Population Mean
	 * @param bSupremum TRUE To Use the Supremum Metric in place of the Built-in Metric
	 * 
	 * @return The Sample/Data Dependent Upper Bound of the Probability of the Absolute Deviation between
	 *  the Empirical and the Population Means using the Function Class Supremum Covering Number for
	 *  Regression Learning
	 * 
	 * @throws java.lang.Exception Thrown if the Upper Probability Bound cannot be computed
	 */

	public double regressorCoveringProbabilityBound (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi,
		final int iSampleSize,
		final double dblEpsilon,
		final boolean bSupremum)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblEpsilon) || 0. >= dblEpsilon || iSampleSize < (2. /
			(dblEpsilon * dblEpsilon)))
			throw new java.lang.Exception
				("GeneralizedLearner::regressorCoveringProbabilityBound => Invalid Inputs");

		org.drip.function.definition.R1ToR1 funcSampleCoefficient = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblSampleSize)
				throws java.lang.Exception
			{
				return 12. * dblSampleSize;
			}
		};

		return (new org.drip.learning.bound.CoveringNumberLossBound (funcSampleCoefficient, 2.,
			36.)).deviationProbabilityUpperBound (iSampleSize, dblEpsilon) * lossSampleCoveringNumber (gvvi,
				dblEpsilon / 6., bSupremum);
	}

	/**
	 * Compute the Minimum Possible Sample Size needed to generate the required Upper Probability Bound for
	 *  the Specified Empirical Deviation using the Covering Number Convergence Bounds for Regression
	 *  Learning.
	 *  
	 * @param gvvi The Validated Instance Vector Sequence
	 * @param dblEpsilon The Deviation of the Empirical Mean from the Population Mean
	 * @param dblDeviationUpperProbabilityBound The Upper Bound of the Probability for the given Deviation
	 * @param bSupremum TRUE To Use the Supremum Metric in place of the Built-in Metric
	 * 
	 * @return The Minimum Possible Sample Size
	 * 
	 * @throws java.lang.Exception Thrown if the Minimum Sample Size cannot be computed
	 */

	public double regressorCoveringSampleSize (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi,
		final double dblEpsilon,
		final double dblDeviationUpperProbabilityBound,
		final boolean bSupremum)
		throws java.lang.Exception
	{
		if (null == gvvi || !org.drip.quant.common.NumberUtil.IsValid (dblEpsilon) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblDeviationUpperProbabilityBound))
			throw new java.lang.Exception
				("GeneralizedLearner::regressorCoveringSampleSize => Invalid Inputs");

		org.drip.function.definition.R1ToR1 funcDeviationUpperProbabilityBound = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblSampleSize)
				throws java.lang.Exception
			{
				return regressorCoveringProbabilityBound (gvvi, (int) dblSampleSize, dblEpsilon, bSupremum);
			}
		};

		org.drip.function.r1tor1solver.FixedPointFinderOutput fpfo = new
			org.drip.function.r1tor1solver.FixedPointFinderZheng (dblDeviationUpperProbabilityBound,
				funcDeviationUpperProbabilityBound, false).findRoot();

		if (null == fpfo || !fpfo.containsRoot())
			throw new java.lang.Exception
				("GeneralizedLearner::regressorCoveringSampleSize => Cannot Estimate Minimal Sample Size");

		return fpfo.getRoot();
	}
}
