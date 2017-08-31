
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
 * LpLossLearner implements the Learner Class that holds the Space of Normed R^x To Normed R^1 Learning
 *  Functions for the Family of Loss Functions that are Polynomial, i.e.,
 * 
 * 				loss (eta) = (eta ^ p) / p,  for p greater than 1.
 * 
 * This is Lipschitz, with a Lipschitz Slope of
 * 
 * 				C = (b - a) ^ (p - 1)
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
 *  5) Vapnik, V. N. (1998): Statistical learning Theory, Wiley, New York.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LpLossLearner extends org.drip.learning.rxtor1.GeneralizedLearner {
	private double _dblLossExponent = java.lang.Double.NaN;

	/**
	 * LpLossLearner Constructor
	 * 
	 * @param funcClassRxToR1 R^x To R^1 Function Class
	 * @param cdpb The Covering Number based Deviation Upper Probability Bound Generator
	 * @param regularizerFunc The Regularizer Function
	 * @param dblLossExponent The Loss Exponent
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LpLossLearner (
		final org.drip.spaces.functionclass.NormedRxToNormedR1Finite funcClassRxToR1,
		final org.drip.learning.bound.CoveringNumberLossBound cdpb,
		final org.drip.learning.regularization.RegularizationFunction regularizerFunc,
		final double dblLossExponent)
		throws java.lang.Exception
	{
		super (funcClassRxToR1, cdpb, regularizerFunc);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblLossExponent = dblLossExponent) || 1. >
			_dblLossExponent)
			throw new java.lang.Exception ("LpLossLearner ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Loss Exponent
	 * 
	 * @return The Loss Exponent
	 */

	public double lossExponent()
	{
		return _dblLossExponent;
	}

	/**
	 * Retrieve the Lipschitz Slope Bound
	 * 
	 * @return The Lipschitz Slope Bound
	 */

	public double lipschitzSlope()
	{
		org.drip.spaces.metric.GeneralizedMetricVectorSpace gmvsInput =
			functionClass().inputMetricVectorSpace();

		return java.lang.Math.pow (gmvsInput.rightEdge() - gmvsInput.leftEdge(), _dblLossExponent - 1.);
	}

	@Override public double lossSampleCoveringNumber (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi,
		final double dblEpsilon,
		final boolean bSupremum)
		throws java.lang.Exception
	{
		if (null == gvvi || !org.drip.quant.common.NumberUtil.IsValid (dblEpsilon) || 0. >= dblEpsilon)
			throw new java.lang.Exception ("LpLossLearner::lossSampleCoveringNumber => Invalid Inputs");

		double dblLipschitzCover = dblEpsilon / lipschitzSlope();

		org.drip.spaces.functionclass.NormedRxToNormedR1Finite funcClassRxToR1 = functionClass();

		org.drip.learning.bound.LipschitzCoveringNumberBound llcn = new
			org.drip.learning.bound.LipschitzCoveringNumberBound
				(funcClassRxToR1.sampleSupremumCoveringNumber (gvvi, dblLipschitzCover),
					funcClassRxToR1.sampleCoveringNumber (gvvi, gvvi.sampleSize() * dblLipschitzCover));

		return bSupremum ? llcn.supremumUpperBound() : llcn.lpUpperBound();
	}

	@Override public double empiricalLoss (
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception
	{
		if (null == funcLearnerR1ToR1 || null == gvviX || !(gvviX instanceof
			org.drip.spaces.instance.ValidatedR1) || null == gvviY || !(gvviY instanceof
				org.drip.spaces.instance.ValidatedR1))
			throw new java.lang.Exception ("LpLossLearner::empiricalLoss => Invalid Inputs");

		double[] adblX = ((org.drip.spaces.instance.ValidatedR1) gvviX).instance();

		double[] adblY = ((org.drip.spaces.instance.ValidatedR1) gvviY).instance();

		double dblEmpiricalLoss = 0.;
		int iNumSample = adblX.length;

		if (iNumSample != adblY.length)
			throw new java.lang.Exception ("LpLossLearner::empiricalLoss => Invalid Inputs");

		for (int i = 0; i < iNumSample; ++i)
			dblEmpiricalLoss += java.lang.Math.pow (java.lang.Math.abs (funcLearnerR1ToR1.evaluate (adblX[i])
				- adblY[i]), _dblLossExponent);

		return dblEmpiricalLoss / _dblLossExponent;
	}

	@Override public double empiricalLoss (
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception
	{
		if (null == funcLearnerRdToR1 || null == gvviX || !(gvviX instanceof
			org.drip.spaces.instance.ValidatedRd) || null == gvviY || !(gvviY instanceof
				org.drip.spaces.instance.ValidatedR1))
			throw new java.lang.Exception ("LpLossLearner::empiricalLoss => Invalid Inputs");

		double[][] aadblX = ((org.drip.spaces.instance.ValidatedRd) gvviX).instance();

		double[] adblY = ((org.drip.spaces.instance.ValidatedR1) gvviY).instance();

		double dblEmpiricalLoss = 0.;
		int iNumSample = aadblX.length;

		if (iNumSample != adblY.length)
			throw new java.lang.Exception ("LpLossLearner::empiricalLoss => Invalid Inputs");

		for (int i = 0; i < iNumSample; ++i)
			dblEmpiricalLoss += java.lang.Math.pow (java.lang.Math.abs (funcLearnerRdToR1.evaluate
				(aadblX[i]) - adblY[i]), _dblLossExponent);

		return dblEmpiricalLoss / _dblLossExponent;
	}

	@Override public double empiricalRisk (
		final org.drip.measure.continuous.R1R1 distR1R1,
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception
	{
		if (null == distR1R1 || null == funcLearnerR1ToR1 || null == gvviX || !(gvviX instanceof
			org.drip.spaces.instance.ValidatedR1) || null == gvviY || !(gvviY instanceof
				org.drip.spaces.instance.ValidatedR1))
			throw new java.lang.Exception ("LpLossLearner::empiricalRisk => Invalid Inputs");

		double[] adblX = ((org.drip.spaces.instance.ValidatedR1) gvviX).instance();

		double[] adblY = ((org.drip.spaces.instance.ValidatedR1) gvviY).instance();

		double dblNormalizer = 0.;
		double dblEmpiricalLoss = 0.;
		int iNumSample = adblX.length;

		if (iNumSample != adblY.length)
			throw new java.lang.Exception ("LpLossLearner::empiricalRisk => Invalid Inputs");

		for (int i = 0; i < iNumSample; ++i) {
			double dblDensity = distR1R1.density (adblX[i], adblY[i]);

			dblNormalizer += dblDensity;

			dblEmpiricalLoss += dblDensity * java.lang.Math.pow (java.lang.Math.abs
				(funcLearnerR1ToR1.evaluate (adblX[i]) - adblY[i]), _dblLossExponent);
		}

		return dblEmpiricalLoss / _dblLossExponent / dblNormalizer;
	}

	@Override public double empiricalRisk (
		final org.drip.measure.continuous.RdR1 distRdR1,
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception
	{
		if (null == distRdR1 || null == funcLearnerRdToR1 || null == gvviX || !(gvviX instanceof
			org.drip.spaces.instance.ValidatedRd) || null == gvviY || !(gvviY instanceof
				org.drip.spaces.instance.ValidatedR1))
			throw new java.lang.Exception ("LpLossLearner::empiricalRisk => Invalid Inputs");

		double[][] aadblX = ((org.drip.spaces.instance.ValidatedRd) gvviX).instance();

		double[] adblY = ((org.drip.spaces.instance.ValidatedR1) gvviY).instance();

		double dblNormalizer = 0.;
		double dblEmpiricalLoss = 0.;
		int iNumSample = aadblX.length;

		if (iNumSample != adblY.length)
			throw new java.lang.Exception ("LpLossLearner::empiricalRisk => Invalid Inputs");

		for (int i = 0; i < iNumSample; ++i) {
			double dblDensity = distRdR1.density (aadblX[i], adblY[i]);

			dblNormalizer += dblDensity;

			dblEmpiricalLoss += dblDensity * java.lang.Math.pow (java.lang.Math.abs
				(funcLearnerRdToR1.evaluate (aadblX[i]) - adblY[i]), _dblLossExponent);
		}

		return dblEmpiricalLoss / _dblLossExponent / dblNormalizer;
	}
}
