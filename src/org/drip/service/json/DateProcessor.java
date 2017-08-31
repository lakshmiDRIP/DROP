
package org.drip.service.json;

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
 * DateProcessor Sets Up and Executes a JSON Based In/Out Date Related Service.
 *
 * @author Lakshmi Krishnamurthy
 */

public class DateProcessor {

	/**
	 * JSON Based in/out Date Holiday Check Thunker
	 * 
	 * @param jsonParameter JSON Date Request Parameters
	 * 
	 * @return JSON Date Holiday Check Response
	 */

	@SuppressWarnings ("unchecked") static final org.drip.json.simple.JSONObject IsHoliday (
		final org.drip.json.simple.JSONObject jsonParameter)
	{
		org.drip.analytics.date.JulianDate dt = org.drip.json.parser.Converter.DateEntry (jsonParameter,
			"Date");

		if (null == dt) return null;

		boolean bIsHoliday = false;

		try {
			bIsHoliday = org.drip.analytics.daycount.Convention.IsHoliday (dt.julian(),
				org.drip.json.parser.Converter.StringEntry (jsonParameter, "Calendar"));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.json.simple.JSONObject jsonResponse = new org.drip.json.simple.JSONObject();

		jsonResponse.put ("IsHoliday", bIsHoliday);

		return jsonResponse;
	}

	/**
	 * JSON Based in/out Date Adjustment Thunker
	 * 
	 * @param jsonParameter JSON Date Request Parameters
	 * 
	 * @return JSON Date Adjustment Response
	 */

	@SuppressWarnings ("unchecked") static final org.drip.json.simple.JSONObject AdjustBusinessDays (
		final org.drip.json.simple.JSONObject jsonParameter)
	{
		org.drip.analytics.date.JulianDate dt = org.drip.json.parser.Converter.DateEntry (jsonParameter,
			"Date");

		if (null == dt) return null;

		java.lang.String strCalendar = org.drip.json.parser.Converter.StringEntry (jsonParameter,
			"Calendar");

		int iDaysToAdjust = 0;

		try {
			iDaysToAdjust = org.drip.json.parser.Converter.IntegerEntry (jsonParameter, "DaysToAdjust");
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.date.JulianDate dtOut = dt.addBusDays (iDaysToAdjust, strCalendar);

		if (null == dtOut) return null;

		org.drip.json.simple.JSONObject jsonResponse = new org.drip.json.simple.JSONObject();

		jsonResponse.put ("DateOut", dtOut.toString());

		return jsonResponse;
	}

	/**
	 * JSON Based in/out Date Offset Thunker
	 * 
	 * @param jsonParameter JSON Date Request Parameters
	 * 
	 * @return JSON Date Offset Response
	 */

	@SuppressWarnings ("unchecked") static final org.drip.json.simple.JSONObject AddDays (
		final org.drip.json.simple.JSONObject jsonParameter)
	{
		org.drip.analytics.date.JulianDate dt = org.drip.json.parser.Converter.DateEntry (jsonParameter,
			"Date");

		if (null == dt) return null;

		int iDaysToAdd = 0;

		try {
			iDaysToAdd = org.drip.json.parser.Converter.IntegerEntry (jsonParameter, "DaysToAdd");
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.date.JulianDate dtOut = dt.addDays (iDaysToAdd);

		if (null == dtOut) return null;

		org.drip.json.simple.JSONObject jsonResponse = new org.drip.json.simple.JSONObject();

		jsonResponse.put ("DateOut", dtOut.toString());

		return jsonResponse;
	}
}
