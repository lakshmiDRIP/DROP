
package org.drip.measure.crng;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * LinearCongruentialGenerator implements a RNG based on Recurrence Based on Modular Integer Arithmetic.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LinearCongruentialGenerator extends org.drip.measure.crng.RandomNumberGenerator {
	private long _lA = java.lang.Long.MIN_VALUE;
	private long _lB = java.lang.Long.MIN_VALUE;
	private long _lM = java.lang.Long.MIN_VALUE;
	private org.drip.measure.crng.RecursiveGenerator _rg = null;

	/**
	 * Construct an Instance of LinearCongruentialGenerator with the MRG of Type MRG32k3a
	 * 
	 * @param lA A
	 * @param lB B
	 * @param lM M
	 * 
	 * @return Instance of LinearCongruentialGenerator with the MRG of Type MRG32k3a
	 */

	public static final LinearCongruentialGenerator MRG32k3a (
		final long lA,
		final long lB,
		final long lM)
	{
		try {
			return new LinearCongruentialGenerator (lA, lB, lM,
				org.drip.measure.crng.MultipleRecursiveGeneratorLEcuyer.MRG32k3a());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a NumericalRecipes Version of LinearCongruentialGenerator
	 * 
	 * @param rg The Recursive Generator Instance
	 * 
	 * @return NumericalRecipes Version of LinearCongruentialGenerator
	 */

	public static final LinearCongruentialGenerator NumericalRecipes (
		final org.drip.measure.crng.RecursiveGenerator rg)
	{
		long l2Power32 = 1;

		for (int i = 0; i < 32; ++i)
			l2Power32 *= 2;

		try {
			return new LinearCongruentialGenerator (1664525L, 1013904223L, l2Power32, rg);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * LinearCongruentialGenerator Contructor
	 * 
	 * @param lA A
	 * @param lB B
	 * @param lM M
	 * @param rg The MultipleRecursiveGenerator Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LinearCongruentialGenerator (
		final long lA,
		final long lB,
		final long lM,
		final org.drip.measure.crng.RecursiveGenerator rg)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_lA = lA) || !org.drip.quant.common.NumberUtil.IsValid
			(_lB = lB) || !org.drip.quant.common.NumberUtil.IsValid (_lM = lM) || null == (_rg = rg))
			throw new java.lang.Exception ("LinearCongruentialGenerator Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve A
	 * 
	 * @return A
	 */

	public long a()
	{
		return _lA;
	}

	/**
	 * Retrieve B
	 * 
	 * @return B
	 */

	public long b()
	{
		return _lB;
	}

	/**
	 * Retrieve M
	 * 
	 * @return M
	 */

	public long m()
	{
		return _lM;
	}

	/**
	 * Retrieve the Recursive Generator Instance
	 * 
	 * @return The Recursive Generator Instance
	 */

	public org.drip.measure.crng.RecursiveGenerator recursiveGenerator()
	{
		return _rg;
	}

	/**
	 * Retrieve the Next Pseudo-random Long
	 * 
	 * @return The Next Pseudo-random Long
	 */

	public long nextLong()
	{
		return (_lA * _rg.next() + _lB) % _lM;
	}

	/**
	 * Retrieve a Random Number between -1 and 1
	 * 
	 * @return Random Number between -1 and 1
	 */

	public double nextDouble()
	{
		return ((double) nextLong()) / ((double) _lM);
	}

	@Override public double nextDouble01()
	{
		return 0.5 * (1. + nextDouble());
	}
}
