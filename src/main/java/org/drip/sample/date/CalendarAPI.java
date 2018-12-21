
package org.drip.sample.date;

import java.util.*;

import org.drip.analytics.date.*;
import org.drip.analytics.daycount.Convention;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/date/README.md">Date Management API</a></li>
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
