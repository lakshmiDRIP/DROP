
package org.drip.function.r1tor1;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * ExponentialDecay implements the scaled exponential decay Univariate Function.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ExponentialDecay extends org.drip.function.definition.R1ToR1 {
	private double _dblEpoch = java.lang.Double.NaN;
	private double _dblHazard = java.lang.Double.NaN;

	/**
	 * ExponentialDecay constructor
	 * 
	 * @param dblEpoch The Starting Epoch
	 * @param dblHazard The Exponential Decay Hazard Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public ExponentialDecay (
		final double dblEpoch,
		final double dblHazard)
		throws java.lang.Exception
	{
		super (null);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblEpoch = dblEpoch) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblHazard = dblHazard))
			throw new java.lang.Exception ("ExponentialDecay ctr => Invalid Inputs");
	}

	@Override public double evaluate (
		final double dblVariate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariate))
			throw new java.lang.Exception ("ExponentialDecay::evaluate => Invalid Inputs");

		return java.lang.Math.exp (-1. * _dblHazard * (dblVariate - _dblEpoch));
	}

	@Override public double derivative (
		final double dblVariate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariate) || 0 >= iOrder)
			throw new java.lang.Exception ("ExponentialDecay::derivative => Invalid Inputs");

		double dblDerivativeFactor = 1;

		for (int i = 0; i < iOrder; ++i)
			dblDerivativeFactor *= (-1. * _dblHazard);

		return dblDerivativeFactor * evaluate (dblVariate);
	}

	@Override public double integrate (
		final double dblBegin,
		final double dblEnd)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBegin) || !org.drip.quant.common.NumberUtil.IsValid
			(dblEnd))
			throw new java.lang.Exception ("ExponentialDecay::integrate => Invalid Inputs");

		return (evaluate (dblEnd) - evaluate (dblBegin)) / (-1. * _dblHazard);
	}

	/**
	 * Retrieve the Epoch
	 * 
	 * @return The Epoch
	 */

	public double epoch()
	{
		return _dblEpoch;
	}

	/**
	 * Retrieve the Hazard
	 * 
	 * @return The Hazard
	 */

	public double hazard()
	{
		return _dblHazard;
	}
}
