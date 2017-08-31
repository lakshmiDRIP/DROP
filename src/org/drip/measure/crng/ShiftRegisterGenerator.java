
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
 * ShiftRegisterGenerator implements a RNG based on the Shift Register Generation Scheme.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ShiftRegisterGenerator extends org.drip.measure.crng.RandomNumberGenerator {
	private boolean[] _abA = null;
	private boolean[] _abX = null;
	private long _lMaximumPeriod = java.lang.Long.MIN_VALUE;

	/**
	 * Construct a Standard Instance of ShiftRegisterGenerator from the Specified Period Power
	 * 
	 * @param iK The Period Power
	 * 
	 * @return Instance of ShiftRegisterGenerator
	 */

	public static final ShiftRegisterGenerator Standard (
		final int iK)
	{
		if (3 >= iK) return null;

		boolean[] abA = new boolean[iK];
		boolean[] abX = new boolean[iK];

		for (int i = 0; i < iK; ++i) {
			abA[i] = true;

			abX[i] = 1 == (System.nanoTime() % 2) ? true : false;
		}

		try {
			return new ShiftRegisterGenerator (abA, abX);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ShiftRegisterGenerator Constructor
	 * 
	 * @param abA Array of Coefficients
	 * @param abX Array of State Values
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ShiftRegisterGenerator (
		final boolean[] abA,
		final boolean[] abX)
		throws java.lang.Exception
	{
		if (null == (_abA = abA) || null == (_abX = abX))
			throw new java.lang.Exception ("ShiftRegisterGenerator Constructor => Invalid Inputs");

		_lMaximumPeriod = 1;
		int iK = _abA.length;

		if (0 == iK || iK != _abX.length)
			throw new java.lang.Exception ("ShiftRegisterGenerator Constructor => Invalid Inputs");

		for (int i = 0; i < iK; ++i)
			_lMaximumPeriod = _lMaximumPeriod * 2;

		_lMaximumPeriod -= 1;
	}

	/**
	 * Retrieve the Array of Coefficients
	 * 
	 * @return The Array of Coefficients
	 */

	public boolean[] a()
	{
		return _abA;
	}

	/**
	 * Retrieve the Array of State Values
	 * 
	 * @return The Array of State Values
	 */

	public boolean[] x()
	{
		return _abX;
	}

	/**
	 * Retrieve the Maximum Period
	 * 
	 * @return The Maximum Period
	 */

	public long maximumPeriod()
	{
		return _lMaximumPeriod;
	}

	/**
	 * Generate the Next Long in the Sequence
	 * 
	 * @return The Next Long in the Sequence
	 */

	public long nextLong()
	{
		long lNext = 0L;
		int iTwosIndex = 1;
		int iK = _abA.length;

		for (int i = 0; i < iK; ++i) {
			if (_abA[i] && _abX[i]) lNext = lNext + iTwosIndex;

			iTwosIndex *= 2;
		}

		_abX[iK - 1] = 1 == (lNext  % 2) ? true : false;

		for (int i = 0; i < iK - 1; ++i)
			_abX[i] = _abX[i + 1];

		return lNext;
	}

	@Override public double nextDouble01()
	{
		return ((double) nextLong()) / ((double) _lMaximumPeriod);
	}
}
