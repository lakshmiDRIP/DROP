
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
 * <i>DayCountAPI</i> demonstrates Day-count API Functionality. It does the following:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Get all the holiday locations in CreditAnalytics, and all the holidays in the year according the
 * 				calendar set.
 *  	</li>
 *  	<li>
 * 			Calculate year fraction between 2 dates according to semi-annual, Act/360, and USD calendar.
 *  	</li>
 *  </ul>
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

		EnvManager.TerminateEnv();
	}
}
