
package org.drip.measure.continuous;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * MultivariateMeta holds a Group of Variable Names - each of which separately is a Valid Single R^1/R^d
 *  Variable.
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultivariateMeta {
	private java.lang.String[] _astrName = null;

	/**
	 * MultivariateMeta Constructor
	 * 
	 * @param astrName Array of the Variate Names
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MultivariateMeta (
		final java.lang.String[] astrName)
		throws java.lang.Exception
	{
		if (null == (_astrName = astrName))
			throw new java.lang.Exception ("MultivariateMeta Constructor => Invalid Inputs");

		int iNumVariable = _astrName.length;

		if (0 >= iNumVariable)
			throw new java.lang.Exception ("MultivariateMeta Constructor => Invalid Inputs");

		for (int i = 0; i < iNumVariable; ++i) {
			if (null == _astrName[i] || _astrName[i].isEmpty())
				throw new java.lang.Exception ("MultivariateMeta Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Number of Variate
	 * 
	 * @return The Number of Variate
	 */

	public int numVariable()
	{
		return _astrName.length;
	}

	/**
	 * Retrieve the Array of the Variate Names
	 * 
	 * @return The Array of the Variate Names
	 */

	public java.lang.String[] names()
	{
		return _astrName;
	}

	/**
	 * Retrieve the Index of the Named Variate
	 * 
	 * @param strName The Named Variate
	 * 
	 * @return Index of the Named Variate
	 */

	public int variateIndex (
		final java.lang.String strName)
	{
		if (null == strName || strName.isEmpty()) return -1;

		int iNumVariable = numVariable();

		for (int i = 0; i < iNumVariable; ++i) {
			if (strName.equalsIgnoreCase (_astrName[i])) return i;
		}

		return -1;
	}
}
