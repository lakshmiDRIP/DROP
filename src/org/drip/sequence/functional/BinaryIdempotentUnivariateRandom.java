
package org.drip.sequence.functional;

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
 * BinaryIdempotentUnivariateRandom contains the Implementation of the Objective Function dependent on
 * 	Binary Idempotent Univariate Random Variable.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BinaryIdempotentUnivariateRandom extends
	org.drip.sequence.functional.BoundedIdempotentUnivariateRandom {
	private double _dblPositiveProbability = java.lang.Double.NaN;

	/**
	 * BinaryIdempotentUnivariateRandom Constructor
	 * 
	 * @param dblOffset The Idempotent Offset
	 * @param dist The Underlying Distribution
	 * @param dblVariateBound The Variate Bound
	 * @param dblPositiveProbability Probability of reaching 1
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BinaryIdempotentUnivariateRandom (
		final double dblOffset,
		final org.drip.measure.continuous.R1 dist,
		final double dblVariateBound,
		final double dblPositiveProbability)
		throws java.lang.Exception
	{
		super (dblOffset, dist, dblVariateBound);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblPositiveProbability = dblPositiveProbability))
			throw new java.lang.Exception ("BinaryIdempotentUnivariateRandom ctr => Invalid Inputs");
	}

	/**
	 * Retrieve the Probability of reaching 1
	 * 
	 * @return The Probability of reaching 1
	 */

	public double positiveProbability()
	{
		return _dblPositiveProbability;
	}

	@Override public double agnosticVarianceBound()
	{
		return super.agnosticVarianceBound() * _dblPositiveProbability * (1. - _dblPositiveProbability);
	}
}
