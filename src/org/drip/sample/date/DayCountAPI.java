
package org.drip.sample.date;

/* 
 * Generic imports
 */

import org.drip.analytics.date.*;
import org.drip.analytics.daycount.Convention;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;

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
 * DayCountAPI demonstrates Day-count API Functionality. It does the following:
 * - Get all the holiday locations in CreditAnalytics, and all the holidays in the year according the
 * 		calendar set.
 * - Calculate year fraction between 2 dates according to semi-annual, Act/360, and USD calendar.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class DayCountAPI {

	/**
	 * Sample API demonstrating the day count functionality
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void DayCountAPISample (
		final JulianDate dtStart,
		final JulianDate dtEnd,
		final String strDayCount)
		throws Exception
	{
		/*
		 * Calculate year fraction between 2 dates according to semi-annual, Act/360, and USD calendar
		 */

		double dblYearFraction = org.drip.analytics.daycount.Convention.YearFraction (
			dtStart.julian(),
			dtEnd.julian(),
			strDayCount,
			false,
			null,
			"USD"
		);

		int iDaysAccrued = org.drip.analytics.daycount.Convention.DaysAccrued (
			dtStart.julian(),
			dtEnd.julian(),
			strDayCount,
			false,
			null,
			"USD"
		);

		System.out.println (
			"\t[" + dtStart + " -> " + dtEnd + "] => " + FormatUtil.FormatDouble (dblYearFraction, 1, 4, 1.) + " | " + iDaysAccrued + " | " + strDayCount
		);
	}

	private static final void DayCountSequence (
		final JulianDate dtStart,
		final JulianDate dtEnd)
		throws Exception
	{
		DayCountAPISample (dtStart, dtEnd, "28/360");

		DayCountAPISample (dtStart, dtEnd, "30/365");

		DayCountAPISample (dtStart, dtEnd, "30/360");

		DayCountAPISample (dtStart, dtEnd, "30E/360 ISDA");

		DayCountAPISample (dtStart, dtEnd, "30E/360");

		DayCountAPISample (dtStart, dtEnd, "30E+/360");

		DayCountAPISample (dtStart, dtEnd, "Act/360");

		DayCountAPISample (dtStart, dtEnd, "Act/364");

		DayCountAPISample (dtStart, dtEnd, "Act/365");

		DayCountAPISample (dtStart, dtEnd, "Act/Act ISDA");

		DayCountAPISample (dtStart, dtEnd, "Act/Act");

		DayCountAPISample (dtStart, dtEnd, "NL/360");

		DayCountAPISample (dtStart, dtEnd, "NL/365");
	}

	public static final void main (
		final String astrArgs[])
		throws Exception
	{
		// String strConfig = "c:\\Lakshmi\\BondAnal\\Config.xml";

		String strConfig = "";

		EnvManager.InitEnv (strConfig);

		/*
		 * List available day count
		 */

		String strDCList = Convention.AvailableDC();

		System.out.println (strDCList + "\n--------------------\n");

		DayCountSequence (
			DateUtil.CreateFromYMD (2013, 5, 30),
			DateUtil.CreateFromYMD (2013, 6, 24)
		);

		System.out.println ("\n--------------------\n");

		DayCountSequence (
			DateUtil.CreateFromYMD (2010, 12, 30),
			DateUtil.CreateFromYMD (2012, 12, 30)
		);
	}
}
