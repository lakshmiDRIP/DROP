
package org.drip.sample.date;

import java.util.*;

import org.drip.analytics.date.*;
import org.drip.analytics.daycount.Convention;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>CalendarAPI</i> demonstrates Calendar API Functionality.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/date/README.md">Calendar Date Roll Day Count</a></li>
 *  </ul>
 * <br><br>
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

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String astrArgs[])
		throws Exception
	{
		// String strConfig = "c:\\Lakshmi\\BondAnal\\Config.xml";

		String strConfig = "";

		EnvManager.InitEnv (strConfig);

		CalenderAPISample();

		EnvManager.TerminateEnv();
	}
}
