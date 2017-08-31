
package org.drip.param.period;

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
 * FixingSetting implements the custom setting parameters for the Latent State Fixing Settings.
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixingSetting {

	/**
	 * Fixing Based off of the Start of the Composite Period
	 */

	public static final int FIXING_COMPOSITE_PERIOD_START = 1;

	/**
	 * Fixing Based off of the End of the Composite Period
	 */

	public static final int FIXING_COMPOSITE_PERIOD_END = 2;

	/**
	 * Fixing Based off of the Start of a Pre-determined Static Date
	 */

	public static final int FIXING_PRESET_STATIC = 4;

	private int _iType = -1;
	private int _iStaticDate = java.lang.Integer.MIN_VALUE;
	private org.drip.analytics.daycount.DateAdjustParams _dap = null;

	/**
	 * Validate the Type of FX Fixing
	 * 
	 * @param iType The FX Fixing Type
	 * 
	 * @return TRUE - FX Fixing is One of the Valid Types
	 */

	public static final boolean ValidateType (
		final int iType)
	{
		return FIXING_COMPOSITE_PERIOD_START == iType || FIXING_COMPOSITE_PERIOD_END == iType ||
			FIXING_PRESET_STATIC == iType;
	}

	/**
	 * FixingSetting Constructor
	 * 
	 * @param iType The Fixing Type
	 * @param dap The Fixing DAP
	 * @param iStaticDate Static Fixing Date
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public FixingSetting (
		final int iType,
		final org.drip.analytics.daycount.DateAdjustParams dap,
		final int iStaticDate)
		throws java.lang.Exception
	{
		if (!ValidateType (_iType = iType))
			throw new java.lang.Exception ("FixingSetting ctr: Invalid Inputs");

		_dap = dap;
		_iStaticDate = iStaticDate;
	}

	/**
	 * Retrieve the Fixing Type
	 * 
	 * @return The Fixing Type
	 */

	public int type()
	{
		return _iType;
	}

	/**
	 * Retrieve the Fixing DAP
	 * 
	 * @return The Fixing DAP
	 */

	public org.drip.analytics.daycount.DateAdjustParams dap()
	{
		return _dap;
	}

	/**
	 * Retrieve the Static Fixing Date
	 * 
	 * @return The Static Fixing Date
	 */

	public int staticDate()
	{
		return _iStaticDate;
	}
}
