
package org.drip.sample.date;

import java.util.*;

import org.drip.analytics.date.*;
import org.drip.analytics.daycount.Convention;
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
 * CalendarAPI demonstrates Calendar API Functionality.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CalendarAPI {

	/**
	 * Sample demonstrating the calendar API
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void CalenderAPISample()
		throws Exception
	{
		/*
		 * Get all the holiday locations in CreditAnalytics
		 */

		/* Set<String> setLoc = CreditAnalytics.GetHolLocations();

		System.out.println ("Num Hol Locations: " + setLoc.size());

		for (String strLoc : setLoc)
			System.out.println (strLoc); */

		/*
		 * Get all the holidays in the year according the calendar set
		 */

		/* JulianDate[] adtHols = CreditAnalytics.GetHolsInYear (
			"USD,GBP",
			2011
		);

		System.out.println ("USD,GBP has " + adtHols.length + " hols");

		for (int i = 0; i < adtHols.length; ++i)
			System.out.println (adtHols[i]); */

		/*
		 * Get all the week day holidays in the year according the calendar set
		 */

		/* JulianDate[] adtWeekDayHols = CreditAnalytics.GetWeekDayHolsInYear (
			"USD,GBP",
			2011
		);

		System.out.println ("USD,GBP has " + adtWeekDayHols.length + " week day hols");

		for (int i = 0; i < adtWeekDayHols.length; ++i)
			System.out.println (adtWeekDayHols[i]); */

		/*
		 * Get all the weekend holidays in the year according the calendar set
		 */

		/* JulianDate[] adtWeekendHols = CreditAnalytics.GetWeekendHolsInYear (
			"USD,GBP",
			2011
		);

		System.out.println ("USD,GBP has " + adtWeekendHols.length + " weekend hols");

		for (int i = 0; i < adtWeekendHols.length; ++i)
			System.out.println (adtWeekendHols[i]); */

		/*
		 * Indicate which days correspond to the weekend for the given calendar set
		 */

		/* int[] aiWkendDays = CreditAnalytics.GetWeekendDays ("USD,GBP");

		for (int i = 0; i < aiWkendDays.length; ++i)
			System.out.println (DateUtil.DayChars (aiWkendDays[i]));

		System.out.println ("USD,GBP has " + aiWkendDays.length + " weekend days"); */

		/*
		 * Check if the given day is a holiday
		 */

		/* boolean bIsHoliday = CreditAnalytics.IsHoliday (
			"USD,GBP",
			DateUtil.CreateFromYMD (
				2011,
				12,
				28
			)
		);

		System.out.println (DateUtil.CreateFromYMD (2011, 12, 28) + " is a USD,GBP holiday? " + bIsHoliday); */

		JulianDate dtToday = DateUtil.Today();

		/*
		 * List all the holidays between the specified days according to the calendar set
		 */

		List<Integer> lsHols = Convention.HolidaySet
			(dtToday.julian(), dtToday.addYears (1).julian(), "USD,GBP");

		for (int iDate : lsHols)
			System.out.println (new JulianDate (iDate).toOracleDate());
	}

	public static final void main (
		final String astrArgs[])
		throws Exception
	{
		// String strConfig = "c:\\Lakshmi\\BondAnal\\Config.xml";

		String strConfig = "";

		EnvManager.InitEnv (strConfig);

		CalenderAPISample();
	}
}
