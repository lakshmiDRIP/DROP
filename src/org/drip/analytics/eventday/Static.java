
package org.drip.analytics.eventday;

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
 * Static implements a complete date as a specific holiday.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Static extends Base {
	private int _iDate = java.lang.Integer.MIN_VALUE;

	/**
	 * Create a static holiday from the date string and the description
	 * 
	 * @param strDate Date string
	 * @param strDescription Description
	 * 
	 * @return StaticHoliday instance
	 */

	public static final Static CreateFromDateDescription (
		final java.lang.String strDate,
		final java.lang.String strDescription)
	{
		org.drip.analytics.date.JulianDate dtHol = org.drip.analytics.date.DateUtil.CreateFromDDMMMYYYY
			(strDate);

		if (null == dtHol) return null;

		try {
			return new Static (dtHol, strDescription);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a static holiday from the date and the description
	 * 
	 * @param dt Date
	 * @param strDescription Description
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public Static (
		final org.drip.analytics.date.JulianDate dt,
		final java.lang.String strDescription)
		throws java.lang.Exception
	{
		super (strDescription);

		if (null == dt) throw new java.lang.Exception ("Static ctr: Null date into Static Holiday");

		_iDate = dt.julian();
	}

	@Override public int dateInYear (
		final int iYear,
		final boolean bAdjusted)
	{
		return _iDate;
	}
}
