
package org.drip.sequence.custom;

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
 * GlivenkoCantelliFunctionSupremum contains the Implementation of the Supremum Class Objective Function
 *  dependent on Multivariate Random Variables where the Multivariate Function is a Linear Combination of Bounded
 * 	Univariate Functions acting on each Random Variate.
 *
 * @author Lakshmi Krishnamurthy
 */

public class GlivenkoCantelliFunctionSupremum extends org.drip.sequence.functional.MultivariateRandom
	implements org.drip.sequence.functional.SeparableMultivariateRandom {
	private double[] _adblWeight = null;
	private org.drip.sequence.functional.FunctionSupremumUnivariateRandom _fsur = null;

	/**
	 * Construct an Instance of GlivenkoCantelliFunctionSupremum from the Sample
	 * 
	 * @param fsur The Supremum Univariate Random Function
	 * @param iNumSample Number of Empirical Samples
	 * 
	 * @return The GlivenkoCantelliFunctionSupremum Instance
	 */

	public static final GlivenkoCantelliFunctionSupremum Create (
		final org.drip.sequence.functional.FunctionSupremumUnivariateRandom fsur,
		final int iNumSample)
	{
		try {
			return new GlivenkoCantelliFunctionSupremum (fsur,
				org.drip.analytics.support.Helper.NormalizedEqualWeightedArray (iNumSample));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * GlivenkoCantelliFunctionSupremum Constructor
	 * 
	 * @param fsur The Supremum Univariate Random Function
	 * @param adblWeight Array of Variable Weights
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public GlivenkoCantelliFunctionSupremum (
		final org.drip.sequence.functional.FunctionSupremumUnivariateRandom fsur,
		final double[] adblWeight)
		throws java.lang.Exception
	{
		if (null == (_adblWeight = adblWeight) || 0 == _adblWeight.length || null == (_fsur = fsur))
			throw new java.lang.Exception ("GlivenkoCantelliFunctionSupremum ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Supremum Univariate Random Function
	 * 
	 * @return The Supremum Univariate Random Function
	 */

	public org.drip.sequence.functional.FunctionSupremumUnivariateRandom separableUnivariateRandom()
	{
		return _fsur;
	}

	/**
	 * Retrieve the Weights
	 * 
	 * @return The Weights
	 */

	public double[] weights()
	{
		return _adblWeight;
	}

	@Override public int dimension()
	{
		return org.drip.function.definition.RdToR1.DIMENSION_NOT_FIXED;
	}

	@Override public double evaluate (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		double dblValue = 0.;
		int iNumVariate = adblVariate.length;

		if (_adblWeight.length < iNumVariate)
			throw new java.lang.Exception ("GlivenkoCantelliFunctionSupremum::evaluate => Invalid Inputs");

		for (int i = 0; i < iNumVariate; ++i)
			dblValue += _adblWeight[i] * _fsur.evaluate (adblVariate[i]);

		return dblValue;
	}

	@Override public double targetVariateVariance (
		final int iTargetVariateIndex)
		throws java.lang.Exception
	{
		org.drip.sequence.metrics.SingleSequenceAgnosticMetrics ssam = _fsur.sequenceMetrics();

		if (null == ssam)
			throw new java.lang.Exception
				("GlivenkoCantelliFunctionSupremum::targetVariateVariance => Cannot calculate Target Variate Metrics");

		return _adblWeight[iTargetVariateIndex] * ssam.empiricalVariance();
	}
}
