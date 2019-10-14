
package org.drip.analytics.eventday;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>Locale</i> contains the set of regular holidays and the weekend holidays for a location. It also
 * provides the functionality to add custom holidays and weekends.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Date, Cash Flow, and Cash Flow Period Measure Generation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/eventday/README.md">Fixed, Variable, and Custom Holiday Creation</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Locale {
	private Weekend _wkend = null;

	private java.util.Set<Base> _setHolidays = new java.util.HashSet<Base>();

	/**
	 * Construct an empty LocHolidays instance
	 */

	public Locale()
	{
	}

	/**
	 * Add the array of weekend days
	 * 
	 * @param aiDays Array of weekend days
	 * 
	 * @return Succeeded (true), failure (false)
	 */

	public boolean addWeekend (
		final int[] aiDays)
	{
		try {
			return null != (_wkend = new Weekend (aiDays));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Add the regular SATURDAY/SUNDAY weekend
	 * 
	 * @return Succeeded (true), failure (false)
	 */

	public boolean addStandardWeekend()
	{
		_wkend = Weekend.StandardWeekend();

		return true;
	}

	/**
	 * Add the given date as a static holiday
	 * 
	 * @param dt Date
	 * @param strDescription Description
	 * 
	 * @return Succeeded (true), failure (false)
	 */

	public boolean addStaticHoliday (
		final org.drip.analytics.date.JulianDate dt,
		final java.lang.String strDescription)
	{
		if (null == dt) return false;

		try {
			_setHolidays.add (new Static (dt, strDescription));

			return true;
		} catch (java.lang.Exception e) {
			System.out.println (e.getMessage());
		}

		return false;
	}

	/**
	 * Add the given string date as a static holiday
	 * 
	 * @param strDate Date string
	 * @param strDescription Description
	 * 
	 * @return Succeeded (true), failure (false)
	 */

	public boolean addStaticHoliday (
		final java.lang.String strDate,
		final java.lang.String strDescription)
	{
		if (null == strDate || strDate.isEmpty()) return false;

		org.drip.analytics.date.JulianDate dtStaticHoliday =
			org.drip.analytics.date.DateUtil.CreateFromDDMMMYYYY (strDate);

		if (null == dtStaticHoliday) return false;

		return addStaticHoliday (dtStaticHoliday, strDescription);
	}

	/**
	 * Add a fixed holiday from the day and month
	 * 
	 * @param iDay Day
	 * @param iMonth Month
	 * @param strDescription Description
	 * 
	 * @return Succeeded (true), failure (false)
	 */

	public boolean addFixedHoliday (
		final int iDay,
		final int iMonth,
		final java.lang.String strDescription)
	{
		_setHolidays.add (new Fixed (iDay, iMonth, _wkend, strDescription));

		return true;
	}

	/**
	 * Add a floating holiday from the week in month, the day in week, the month, and whether holidays are
	 * 		calculated from front/back.
	 * 
	 * @param iWeekInMonth Week in the Month
	 * @param iWeekDay Day in the week
	 * @param iMonth Month
	 * @param bFromFront Holidays are calculated from the start of the month (True)
	 * @param strDescription Description
	 * 
	 * @return Succeeded (true), failure (false)
	 */

	public boolean addFloatingHoliday (
		final int iWeekInMonth,
		final int iWeekDay,
		final int iMonth,
		final boolean bFromFront,
		final java.lang.String strDescription)
	{
		_setHolidays.add (new Variable (iWeekInMonth, iWeekDay, iMonth, bFromFront, _wkend,
			strDescription));

		return true;
	}

	/**
	 * Return the weekend
	 * 
	 * @return Weekend
	 */

	public Weekend weekendDays()
	{
		return _wkend;
	}

	/**
	 * Return the set of week day holidays
	 * 
	 * @return Set of hoidays
	 */

	public java.util.Set<Base> holidays()
	{
		return _setHolidays;
	}
}
