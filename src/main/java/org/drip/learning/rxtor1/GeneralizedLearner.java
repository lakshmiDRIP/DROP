
package org.drip.learning.rxtor1;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>GeneralizedLearner</i> implements the Learner Class that holds the Space of Normed R<sup>x</sup> To
 * Normed R<sup>1</sup> Learning Functions along with their Custom Empirical Loss. Class-Specific Asymptotic
 * Sample, Covering Number based Upper Probability Bounds and other Parameters are also maintained.
 *  
 * <br><br>
 * The References are:
 *  
 * <br><br>
 * <ul>
 * 	<li>
 *  	Alon, N., S. Ben-David, N. Cesa Bianchi, and D. Haussler (1997): Scale-sensitive Dimensions, Uniform
 *  		Convergence, and Learnability <i>Journal of Association of Computational Machinery</i> <b>44
 *  		(4)</b> 615-631
 * 	</li>
 * 	<li>
 *  	Anthony, M., and P. L. Bartlett (1999): <i>Artificial Neural Network Learning - Theoretical
 *  		Foundations</i> <b>Cambridge University Press</b> Cambridge, UK
 * 	</li>
 * 	<li>
 *  	Kearns, M. J., R. E. Schapire, and L. M. Sellie (1994): <i>Towards Efficient Agnostic Learning</i>
 *  		Machine Learning <b>17 (2)</b> 115-141
 * 	</li>
 * 	<li>
 *  	Lee, W. S., P. L. Bartlett, and R. C. Williamson (1998): The Importance of Convexity in Learning with
 *  		Squared Loss <i>IEEE Transactions on Information Theory</i> <b>44</b> 1974-1980
 * 	</li>
 * 	<li>
 *  	Vapnik, V. N. (1998): <i>Statistical learning Theory</i> <b>Wiley</b> New York
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Agnostic Learning Bounds under Empirical Loss Minimization Schemes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/rxtor1">Statistical Learning Empirical Loss Penalizer</a></li>
 *  </ul>
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblEpsilon) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblDeviationUpperProbabilityBound))
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
		if (null == gvvi || !org.drip.numerical.common.NumberUtil.IsValid (dblEpsilon) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblDeviationUpperProbabilityBound))
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblEpsilon) || 0. >= dblEpsilon || iSampleSize < (2. /
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblEpsilon) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblDeviationUpperProbabilityBound))
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblEpsilon) || 0. >= dblEpsilon || iSampleSize < (2. /
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
		if (null == gvvi || !org.drip.numerical.common.NumberUtil.IsValid (dblEpsilon) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblDeviationUpperProbabilityBound))
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
