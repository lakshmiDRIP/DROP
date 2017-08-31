
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

public class GFRHoliday implements org.drip.analytics.holset.LocationHoliday {
	public GFRHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "GFR";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("24-DEC-2004", "Christmas Eve");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("03-JUL-2009", "Good Friday");

		lh.addStaticHoliday ("24-DEC-2010", "Christmas Eve");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("03-JUL-2015", "Independence Day Observed");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("03-JUL-2020", "Independence Day Observed");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("24-DEC-2021", "Christmas Day Observed");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("03-JUL-2026", "Independence Day Observed");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("24-DEC-2027", "Christmas Day Observed");

		lh.addStaticHoliday ("14-APR-2028", "Good Friday");

		lh.addStaticHoliday ("30-MAR-2029", "Good Friday");

		lh.addStaticHoliday ("19-APR-2030", "Good Friday");

		lh.addStaticHoliday ("11-APR-2031", "Good Friday");

		lh.addStaticHoliday ("26-MAR-2032", "Good Friday");

		lh.addStaticHoliday ("24-DEC-2032", "Christmas Day Observed");

		lh.addStaticHoliday ("15-APR-2033", "Good Friday");

		lh.addStaticHoliday ("07-APR-2034", "Good Friday");

		lh.addStaticHoliday ("23-MAR-2035", "Good Friday");

		lh.addStaticHoliday ("11-APR-2036", "Good Friday");

		lh.addStaticHoliday ("03-APR-2037", "Good Friday");

		lh.addStaticHoliday ("03-JUL-2037", "Independence Day Observed");

		lh.addStaticHoliday ("23-APR-2038", "Good Friday");

		lh.addStaticHoliday ("24-DEC-2038", "Christmas Day Observed");

		lh.addStaticHoliday ("08-APR-2039", "Good Friday");

		lh.addStaticHoliday ("30-MAR-2040", "Good Friday");

		lh.addStaticHoliday ("19-APR-2041", "Good Friday");

		lh.addStaticHoliday ("04-APR-2042", "Good Friday");

		lh.addStaticHoliday ("27-MAR-2043", "Good Friday");

		lh.addStaticHoliday ("03-JUL-2043", "Independence Day Observed");

		lh.addStaticHoliday ("15-APR-2044", "Good Friday");

		lh.addStaticHoliday ("07-APR-2045", "Good Friday");

		lh.addStaticHoliday ("23-MAR-2046", "Good Friday");

		lh.addStaticHoliday ("12-APR-2047", "Good Friday");

		lh.addStaticHoliday ("03-APR-2048", "Good Friday");

		lh.addStaticHoliday ("03-JUL-2048", "Independence Day Observed");

		lh.addStaticHoliday ("16-APR-2049", "Good Friday");

		lh.addStaticHoliday ("24-DEC-2049", "Christmas Day Observed");

		lh.addStaticHoliday ("08-APR-2050", "Good Friday");

		lh.addStaticHoliday ("31-MAR-2051", "Good Friday");

		lh.addStaticHoliday ("19-APR-2052", "Good Friday");

		lh.addStaticHoliday ("04-APR-2053", "Good Friday");

		lh.addStaticHoliday ("27-MAR-2054", "Good Friday");

		lh.addStaticHoliday ("03-JUL-2054", "Independence Day Observed");

		lh.addStaticHoliday ("16-APR-2055", "Good Friday");

		lh.addStaticHoliday ("24-DEC-2055", "Christmas Day Observed");

		lh.addStaticHoliday ("31-MAR-2056", "Good Friday");

		lh.addStaticHoliday ("20-APR-2057", "Good Friday");

		lh.addStaticHoliday ("12-APR-2058", "Good Friday");

		lh.addStaticHoliday ("28-MAR-2059", "Good Friday");

		lh.addStaticHoliday ("16-APR-2060", "Good Friday");

		lh.addStaticHoliday ("08-APR-2061", "Good Friday");

		lh.addStaticHoliday ("24-MAR-2062", "Good Friday");

		lh.addStaticHoliday ("13-APR-2063", "Good Friday");

		lh.addStaticHoliday ("04-APR-2064", "Good Friday");

		lh.addStaticHoliday ("27-MAR-2065", "Good Friday");

		lh.addStaticHoliday ("09-APR-2066", "Good Friday");

		lh.addStaticHoliday ("01-APR-2067", "Good Friday");

		lh.addStaticHoliday ("20-APR-2068", "Good Friday");

		lh.addStaticHoliday ("12-APR-2069", "Good Friday");

		lh.addStaticHoliday ("28-MAR-2070", "Good Friday");

		lh.addStaticHoliday ("17-APR-2071", "Good Friday");

		lh.addStaticHoliday ("08-APR-2072", "Good Friday");

		lh.addStaticHoliday ("24-MAR-2073", "Good Friday");

		lh.addStaticHoliday ("13-APR-2074", "Good Friday");

		lh.addStaticHoliday ("05-APR-2075", "Good Friday");

		lh.addStaticHoliday ("17-APR-2076", "Good Friday");

		lh.addStaticHoliday ("09-APR-2077", "Good Friday");

		lh.addStaticHoliday ("01-APR-2078", "Good Friday");

		lh.addStaticHoliday ("21-APR-2079", "Good Friday");

		lh.addStaticHoliday ("05-APR-2080", "Good Friday");

		lh.addStaticHoliday ("28-MAR-2081", "Good Friday");

		lh.addStaticHoliday ("17-APR-2082", "Good Friday");

		lh.addStaticHoliday ("02-APR-2083", "Good Friday");

		lh.addStaticHoliday ("24-MAR-2084", "Good Friday");

		lh.addStandardWeekend();

		return lh;
	}
}
