
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
 * This class implements the basis spline function set.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FunctionSet {
	private org.drip.function.definition.R1ToR1[] _aAUResponseBasis = null;

	/**
	 * @param aAUResponseBasis Array of the Basis Function Set
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public FunctionSet (
		final org.drip.function.definition.R1ToR1[] aAUResponseBasis)
		throws java.lang.Exception
	{
		if (null == (_aAUResponseBasis = aAUResponseBasis) || 0 == _aAUResponseBasis.length)
			throw new java.lang.Exception ("FunctionSet ctr: Invalid Inputs!");
	}

	/**
	 * Retrieve the Number of Basis Functions
	 * 
	 * @return Number of Basis Functions
	 */

	public int numBasis()
	{
		return _aAUResponseBasis.length;
	}

	/**
	 * Retrieve the Basis Function identified by the specified Index
	 * 
	 * @param iBasisIndex The Basis Function Index
	 * 
	 * @return The Basis Function identified by the specified Index
	 */

	public org.drip.function.definition.R1ToR1 indexedBasisFunction (
		final int iBasisIndex)
	{
		if (iBasisIndex >= numBasis()) return null;

		return _aAUResponseBasis[iBasisIndex];
	}
}
