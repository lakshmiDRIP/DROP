
package org.drip.analytics.daycount;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * This class contains the parameters needed for adjusting dates. It exports the following functionality:
 * 	- Accessor for holiday calendar and adjustment type
 *  - Serialization/De-serialization to and from Byte Arrays
 *
 * @author Lakshmi Krishnamurthy
 */

public class DateAdjustParams {
	private int _iRollMode = 0;
	private int _iNumDaysToRoll = -1;
	private java.lang.String _strCalendar = "";

	/**
	 * Create a DateAdjustParams instance from the roll mode and the calendar
	 * 
	 * @param iRollMode Roll Mode
	 * @param iNumDaysToRoll Number of Days to Roll
	 * @param strCalendar Calendar
	 */

	public DateAdjustParams (
		final int iRollMode,
		final int iNumDaysToRoll,
		final java.lang.String strCalendar)
	{
		_iRollMode = iRollMode;
		_strCalendar = strCalendar;
		_iNumDaysToRoll = iNumDaysToRoll;
	}

	/**
	 * Retrieve the Roll Mode
	 * 
	 * @return The Roll Mode
	 */

	public int rollMode()
	{
		return _iRollMode;
	}

	/**
	 * Retrieve the Roll Holiday Calendar
	 * 
	 * @return The Roll Holiday Calendar
	 */

	public java.lang.String calendar()
	{
		return _strCalendar;
	}

	/**
	 * Roll the given Date
	 * 
	 * @param iDate date
	 * 
	 * @return The Rolled Date
	 * 
	 * @throws java.lang.Exception Thrown if the input day is invalid
	 */

	public int roll (
		final int iDate)
		throws java.lang.Exception
	{
		return org.drip.analytics.daycount.Convention.RollDate (iDate, _iRollMode, _strCalendar,
			_iNumDaysToRoll);
	}
}
