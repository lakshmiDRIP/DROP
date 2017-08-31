
package org.drip.learning.regularization;

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
 * RegularizerBuilder constructs Custom Regularizers for the different Normed Learner Function Types. The
 *  References are:
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

public class RegularizerBuilder {

	/**
	 * Construct an Instance of R^1 Combinatorial To R^1 Continuous Regularizer
	 * 
	 * @param funcRegularizerR1ToR1 The R^1 To R^1 Regularizer Function
	 * @param funcSpaceR1ToR1 The R^1 Combinatorial To R^1 Continuous Learner Function Space
	 * @param dblLambda The Regularization Lambda
	 * 
	 * @return The R^1 Combinatorial To R^1 Continuous Regularizer Instance
	 */

	public static final org.drip.learning.regularization.RegularizerR1ToR1 R1CombinatorialToR1Continuous (
		final org.drip.function.definition.R1ToR1 funcRegularizerR1ToR1,
		final org.drip.spaces.rxtor1.NormedR1CombinatorialToR1Continuous funcSpaceR1ToR1,
		final double dblLambda)
	{
		try {
			return null == funcSpaceR1ToR1 ? null : new
				org.drip.learning.regularization.RegularizerR1CombinatorialToR1Continuous
					(funcRegularizerR1ToR1, (org.drip.spaces.metric.R1Combinatorial)
						funcSpaceR1ToR1.inputMetricVectorSpace(), (org.drip.spaces.metric.R1Continuous)
							funcSpaceR1ToR1.outputMetricVectorSpace(), dblLambda);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of R^1 Continuous To R^1 Continuous Regularizer
	 * 
	 * @param funcRegularizerR1ToR1 The R^1 To R^1 Regularizer Function
	 * @param funcSpaceR1ToR1 The R^1 Continuous To R^1 Continuous Learner Function Space
	 * @param dblLambda The Regularization Lambda
	 * 
	 * @return The R^1 Continuous To R^1 Continuous Regularizer Instance
	 */

	public static final org.drip.learning.regularization.RegularizerR1ToR1 R1ContinuousToR1Continuous (
		final org.drip.function.definition.R1ToR1 funcRegularizerR1ToR1,
		final org.drip.spaces.rxtor1.NormedR1ContinuousToR1Continuous funcSpaceR1ToR1,
		final double dblLambda)
	{
		try {
			return null == funcSpaceR1ToR1 ? null : new
				org.drip.learning.regularization.RegularizerR1ContinuousToR1Continuous
					(funcRegularizerR1ToR1, (org.drip.spaces.metric.R1Continuous)
						funcSpaceR1ToR1.inputMetricVectorSpace(), (org.drip.spaces.metric.R1Continuous)
							funcSpaceR1ToR1.outputMetricVectorSpace(), dblLambda);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of R^1 Combinatorial/Continuous To R^1 Continuous Regularizer
	 * 
	 * @param funcRegularizerR1ToR1 The R^1 To R^1 Regularizer Function
	 * @param r1Input The R^1 Combinatorial/Continuous Input Space
	 * @param r1ContinuousOutput The R^1 Continuous Output Space
	 * @param dblLambda The Regularization Lambda
	 * 
	 * @return The R^1 Combinatorial/Continuous To R^1 Continuous Regularizer Instance
	 */

	public static final org.drip.learning.regularization.RegularizerR1ToR1 ToR1Continuous (
		final org.drip.function.definition.R1ToR1 funcRegularizerR1ToR1,
		final org.drip.spaces.metric.R1Normed r1Input,
		final org.drip.spaces.metric.R1Continuous r1ContinuousOutput,
		final double dblLambda)
	{
		if (null == r1Input) return null;

		try {
			if (r1Input instanceof org.drip.spaces.metric.R1Continuous)
				return new org.drip.learning.regularization.RegularizerR1ContinuousToR1Continuous
					(funcRegularizerR1ToR1, (org.drip.spaces.metric.R1Continuous) r1Input,
						r1ContinuousOutput, dblLambda);

			return new org.drip.learning.regularization.RegularizerR1CombinatorialToR1Continuous
				(funcRegularizerR1ToR1, (org.drip.spaces.metric.R1Combinatorial) r1Input,
					r1ContinuousOutput, dblLambda);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of R^d Combinatorial To R^1 Continuous Regularizer
	 * 
	 * @param funcRegularizerRdToR1 The R^d To R^1 Regularizer Function
	 * @param funcSpaceRdToR1 The R^d Combinatorial To R^1 Continuous Learner Function Space
	 * @param dblLambda The Regularization Lambda
	 * 
	 * @return The R^d Combinatorial To R^1 Continuous Regularizer Instance
	 */

	public static final org.drip.learning.regularization.RegularizerRdToR1 RdCombinatorialToR1Continuous (
		final org.drip.function.definition.RdToR1 funcRegularizerRdToR1,
		final org.drip.spaces.rxtor1.NormedRdCombinatorialToR1Continuous funcSpaceRdToR1,
		final double dblLambda)
	{
		try {
			return null == funcSpaceRdToR1 ? null : new
				org.drip.learning.regularization.RegularizerRdCombinatorialToR1Continuous
					(funcRegularizerRdToR1, (org.drip.spaces.metric.RdCombinatorialBanach)
						funcSpaceRdToR1.inputMetricVectorSpace(), (org.drip.spaces.metric.R1Continuous)
							funcSpaceRdToR1.outputMetricVectorSpace(), dblLambda);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of R^d Continuous To R^1 Continuous Regularizer
	 * 
	 * @param funcRegularizerRdToR1 The R^d To R^1 Regularizer Function
	 * @param funcSpaceRdToR1 The R^d Continuous To R^1 Continuous Learner Function Space
	 * @param dblLambda The Regularization Lambda
	 * 
	 * @return The R^d Continuous To R^1 Continuous Regularizer Instance
	 */

	public static final org.drip.learning.regularization.RegularizerRdToR1 RdContinuousToR1Continuous (
		final org.drip.function.definition.RdToR1 funcRegularizerRdToR1,
		final org.drip.spaces.rxtor1.NormedRdContinuousToR1Continuous funcSpaceRdToR1,
		final double dblLambda)
	{
		try {
			return null == funcSpaceRdToR1 ? null : new
				org.drip.learning.regularization.RegularizerRdContinuousToR1Continuous
					(funcRegularizerRdToR1, (org.drip.spaces.metric.RdContinuousBanach)
						funcSpaceRdToR1.inputMetricVectorSpace(), (org.drip.spaces.metric.R1Continuous)
							funcSpaceRdToR1.outputMetricVectorSpace(), dblLambda);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of R^d Combinatorial/Continuous To R^1 Continuous Regularizer
	 * 
	 * @param funcRegularizerRdToR1 The R^d To R^1 Regularizer Function
	 * @param rdInput The R^d Combinatorial/Continuous Input Space
	 * @param r1ContinuousOutput The R^1 Continuous Output Space
	 * @param dblLambda The Regularization Lambda
	 * 
	 * @return The R^1 Combinatorial/Continuous To R^1 Continuous Regularizer Instance
	 */

	public static final org.drip.learning.regularization.RegularizerRdToR1 ToRdContinuous (
		final org.drip.function.definition.RdToR1 funcRegularizerRdToR1,
		final org.drip.spaces.metric.RdNormed rdInput,
		final org.drip.spaces.metric.R1Continuous r1ContinuousOutput,
		final double dblLambda)
	{
		if (null == rdInput) return null;

		try {
			if (rdInput instanceof org.drip.spaces.metric.RdContinuousBanach)
				return new org.drip.learning.regularization.RegularizerRdContinuousToR1Continuous
					(funcRegularizerRdToR1, (org.drip.spaces.metric.RdContinuousBanach) rdInput,
						r1ContinuousOutput, dblLambda);

			return new org.drip.learning.regularization.RegularizerRdCombinatorialToR1Continuous
				(funcRegularizerRdToR1, (org.drip.spaces.metric.RdCombinatorialBanach) rdInput,
					r1ContinuousOutput, dblLambda);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
