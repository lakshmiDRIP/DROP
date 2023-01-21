
package org.drip.analytics.holset;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*
 *    GENERATED on Fri Jan 11 19:54:07 EST 2013 ---- DO NOT DELETE
 */

/*!
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
 *
 * This file is part of CreditAnalytics, a free-software/open-source library for
 *		fixed income analysts and developers - http://www.credit-trader.org
 *
 * CreditAnalytics is a free, full featured, fixed income credit analytics library, developed with a special focus
 * 		towards the needs of the bonds and credit products community.
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
 * <i>RURHoliday</i> holds the RUR Holidays.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Date, Cash Flow, and Cash Flow Period Measure Generation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/holset/README.md">Built in Locale Holiday Set</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RURHoliday implements org.drip.analytics.holset.LocationHoliday {

	/**
	 * RURHoliday Constructor
	 */

	public RURHoliday()
	{
	}

	@Override public java.lang.String getHolidayLoc()
	{
		return "RUR";
	}

	@Override public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1997", "New Years Day");

		lh.addStaticHoliday ("02-JAN-1997", "New Years Holiday");

		lh.addStaticHoliday ("07-JAN-1997", "Christmas Day (Orthodox)");

		lh.addStaticHoliday ("08-MAR-1997", "International Womens Day");

		lh.addStaticHoliday ("10-MAR-1997", "International Womans Day");

		lh.addStaticHoliday ("01-APR-1997", "Foundation of the Commonwealth");

		lh.addStaticHoliday ("01-MAY-1997", "International Labor Day");

		lh.addStaticHoliday ("02-MAY-1997", "International Labor holiday");

		lh.addStaticHoliday ("09-MAY-1997", "Victory Day");

		lh.addStaticHoliday ("12-JUN-1997", "Declaration of independance");

		lh.addStaticHoliday ("07-NOV-1997", "80th An of October Revolution");

		lh.addStaticHoliday ("12-DEC-1997", "Constitution Day");

		lh.addStandardWeekend();

		return lh;
	}
}
