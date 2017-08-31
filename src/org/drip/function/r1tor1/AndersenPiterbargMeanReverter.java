
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
 * AndersenPiterbargMeanReverter implements the mean-reverting Univariate Function detailed in:
 * 
 * 	- Andersen and Piterbarg (2010): Interest Rate Modeling (3 Volumes), Atlantic Financial Press.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AndersenPiterbargMeanReverter extends org.drip.function.definition.R1ToR1 {
	private org.drip.function.r1tor1.ExponentialDecay _auExpDecay = null;
	private org.drip.function.definition.R1ToR1 _auSteadyState = null;

	/**
	 * AndersenPiterbargMeanReverter constructor
	 * 
	 * @param auExpDecay The Exponential Decay Univariate Function
	 * @param auSteadyState The Steady State (i.e., Infinite Time) Undamped Behavior Univariate Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public AndersenPiterbargMeanReverter (
		final org.drip.function.r1tor1.ExponentialDecay auExpDecay,
		final org.drip.function.definition.R1ToR1 auSteadyState)
		throws java.lang.Exception
	{
		super (null);

		if (null == (_auExpDecay = auExpDecay) || null == (_auSteadyState = auSteadyState))
			throw new java.lang.Exception ("AndersenPiterbargMeanReverter ctr => Invalid Inputs");
	}

	@Override public double evaluate (
		final double dblVariate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariate))
			throw new java.lang.Exception ("AndersenPiterbargMeanReverter::evaluate => Invalid Inputs");

		return (1. - _auExpDecay.evaluate (dblVariate)) * _auSteadyState.evaluate (dblVariate);
	}
}
