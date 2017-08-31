
package org.drip.function.r1tor1;

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
 * UnivariateReflection provides the evaluation f(1-x) instead of f(x) for a given f.
 *
 * @author Lakshmi Krishnamurthy
 */

public class UnivariateReflection extends org.drip.function.definition.R1ToR1 {
	private org.drip.function.definition.R1ToR1 _au = null;

	/**
	 * UnivariateReflection constructor
	 * 
	 * @param au Univariate Function
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public UnivariateReflection (
		final org.drip.function.definition.R1ToR1 au)
		throws java.lang.Exception
	{
		super (null);

		if (null == (_au = au)) throw new java.lang.Exception ("UnivariateReflection ctr: Invalid Inputs");
	}

	@Override public double evaluate (
		final double dblVariate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariate))
			throw new java.lang.Exception ("UnivariateReflection::evaluate => Invalid Inputs");

		return _au.evaluate (1. - dblVariate);
	}

	@Override public double derivative (
		final double dblVariate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariate) || 0 >= iOrder)
			throw new java.lang.Exception ("UnivariateReflection::derivative => Invalid Inputs");

		return java.lang.Math.pow (-1., iOrder) * _au.derivative (1. - dblVariate, iOrder);
	}

	@Override public double integrate (
		final double dblBegin,
		final double dblEnd)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBegin) || !org.drip.quant.common.NumberUtil.IsValid
			(dblEnd))
			throw new java.lang.Exception ("UnivariateReflection::integrate => Invalid Inputs");

		return -1. * _au.integrate (1. - dblBegin, 1. - dblEnd);
	}

	public static final void main (
		final java.lang.String[] astrArgs)
		throws java.lang.Exception
	{
		UnivariateReflection ur = new UnivariateReflection (new Polynomial (4));

		System.out.println ("UnivariateReflection[0.0] = " + ur.evaluate (0.0));

		System.out.println ("UnivariateReflection[0.5] = " + ur.evaluate (0.5));

		System.out.println ("UnivariateReflection[1.0] = " + ur.evaluate (1.0));

		System.out.println ("UnivariateReflectionDeriv[0.0] = " + ur.derivative (0.0, 3));

		System.out.println ("UnivariateReflectionDeriv[0.5] = " + ur.derivative (0.5, 3));

		System.out.println ("UnivariateReflectionDeriv[1.0] = " + ur.derivative (1.0, 3));
	}
}
