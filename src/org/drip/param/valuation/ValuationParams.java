
package org.drip.param.valuation;

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
 * ValuationParams is the place-holder for the valuation parameters for a given product. It contains the
 * 	valuation and the cash pay/settle dates, as well as the calendar. It also exposes a number of methods to
 *  construct standard valuation parameters.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ValuationParams {
	private java.lang.String _strCalendar = "";
	private int _iValueDate = java.lang.Integer.MIN_VALUE;
	private int _iCashPayDate = java.lang.Integer.MIN_VALUE;

	/**
	 * Create the valuation parameters object instance from the valuation date, the cash settle lag, and the
	 * 	settle calendar.
	 * 
	 * @param dtValue Valuation Date
	 * @param iCashSettleLag Cash settle lag
	 * @param strCalendar Calendar Set
	 * @param iAdjustMode The Adjustment Mode Flag
	 * 
	 * @return Valuation Parameters instance
	 */

	public static final ValuationParams Spot (
		final org.drip.analytics.date.JulianDate dtValue,
		final int iCashSettleLag,
		final java.lang.String strCalendar,
		final int iAdjustMode)
	{
		try {
			return null == dtValue ? null : new ValuationParams (dtValue, new
				org.drip.analytics.date.JulianDate (org.drip.analytics.daycount.Convention.Adjust
					(dtValue.addDays (iCashSettleLag).julian(), strCalendar, iAdjustMode)), strCalendar);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create the spot valuation parameters for the given valuation date (uses the T+0 settle)
	 *  
	 * @param iDate Valuation Date
	 * 
	 * @return Valuation Parameters instance
	 */

	public static final ValuationParams Spot (
		final int iDate)
	{
		org.drip.analytics.date.JulianDate dtValue = new org.drip.analytics.date.JulianDate (iDate);

		try {
			return new ValuationParams (dtValue, dtValue, "");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create the standard T+2B settle parameters for the given valuation date and calendar
	 *  
	 * @param dtValue Valuation Date
	 * @param strCalendar Settle Calendar
	 * 
	 * @return Valuation Parameters instance
	 */

	public static final ValuationParams Standard (
		final org.drip.analytics.date.JulianDate dtValue,
		final java.lang.String strCalendar)
	{
		return Spot (dtValue, 2, strCalendar, org.drip.analytics.daycount.Convention.DATE_ROLL_FOLLOWING);
	}

	/**
	 * Construct ValuationParams from the Valuation Date and the Cash Pay Date parameters
	 * 
	 * @param dtValue Valuation Date
	 * @param dtCashPay Cash Pay Date
	 * @param strCalendar Calendar Set
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public ValuationParams (
		final org.drip.analytics.date.JulianDate dtValue,
		final org.drip.analytics.date.JulianDate dtCashPay,
		final java.lang.String strCalendar)
		throws java.lang.Exception
	{
		if (null == dtValue || null ==  dtCashPay)
			throw new java.lang.Exception ("ValuationParams ctr: Invalid settle/Cash pay into Val Params!");

		_iValueDate = dtValue.julian();

		_iCashPayDate = dtCashPay.julian();

		_strCalendar = strCalendar;
	}

	/**
	 * Retrieve the Valuation Date
	 * 
	 * @return The Valuation Date
	 */

	public int valueDate()
	{
		return _iValueDate;
	}

	/**
	 * Retrieve the Cash Pay Date
	 * 
	 * @return The Cash Pay Date
	 */

	public int cashPayDate()
	{
		return _iCashPayDate;
	}

	/**
	 * Retrieve the Calendar
	 * 
	 * @return The Calendar
	 */

	public java.lang.String calendar()
	{
		return _strCalendar;
	}
}
