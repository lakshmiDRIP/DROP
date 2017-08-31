
package org.drip.spline.basis;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * ExponentialRationalSetParams implements per-segment parameters for the exponential rational basis set
 *  - the exponential tension and the rational tension parameters.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ExponentialRationalSetParams implements org.drip.spline.basis.FunctionSetBuilderParams {
	private double _dblRationalTension = java.lang.Double.NaN;
	private double _dblExponentialTension = java.lang.Double.NaN;

	/**
	 * ExponentialRationalSetParams constructor
	 * 
	 * @param dblExponentialTension Segment Tension
	 * @param dblRationalTension Segment Tension
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ExponentialRationalSetParams (
		final double dblExponentialTension,
		final double dblRationalTension)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblExponentialTension = dblExponentialTension) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblRationalTension = dblRationalTension))
			throw new java.lang.Exception ("ExponentialRationalSetParams ctr: Invalid Inputs");
	}

	/**
	 * Get the Exponential Tension
	 * 
	 * @return The Exponential Tension
	 */

	public double exponentialTension()
	{
		return _dblExponentialTension;
	}

	/**
	 * Get the Rational Tension
	 * 
	 * @return The Rational Tension
	 */

	public double rationalTension()
	{
		return _dblRationalTension;
	}
}
