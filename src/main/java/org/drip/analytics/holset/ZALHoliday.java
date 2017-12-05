
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

public class ZALHoliday implements org.drip.analytics.holset.LocationHoliday {
	public ZALHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "ZAL";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-APR-1994", "Good Friday");

		lh.addStaticHoliday ("06-APR-1994", "Founders Day");

		lh.addStaticHoliday ("12-MAY-1994", "Ascension day");

		lh.addStaticHoliday ("31-MAY-1994", "Republic Day");

		lh.addStaticHoliday ("10-OCT-1994", "Kruger Day");

		lh.addStaticHoliday ("16-DEC-1994", "Day of the Vow");

		lh.addStaticHoliday ("26-DEC-1994", "Day of Goodwill");

		lh.addStaticHoliday ("01-JAN-1995", "");

		lh.addStaticHoliday ("04-APR-1995", "Easter Monday");

		lh.addStaticHoliday ("06-APR-1995", "Founders Day");

		lh.addStaticHoliday ("14-APR-1995", "Good Friday");

		lh.addStaticHoliday ("17-APR-1995", "Easter Monday");

		lh.addStaticHoliday ("27-APR-1995", "");

		lh.addStaticHoliday ("01-MAY-1995", "Workers day");

		lh.addStaticHoliday ("25-MAY-1995", "Ascension day");

		lh.addStaticHoliday ("31-MAY-1995", "Republic Day");

		lh.addStaticHoliday ("16-JUN-1995", "");

		lh.addStaticHoliday ("09-AUG-1995", "");

		lh.addStaticHoliday ("24-SEP-1995", "");

		lh.addStaticHoliday ("10-OCT-1995", "Kruger Day");

		lh.addStaticHoliday ("16-DEC-1995", "");

		lh.addStaticHoliday ("25-DEC-1995", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-1995", "Day of Goodwill");

		lh.addStaticHoliday ("01-JAN-1996", "");

		lh.addStaticHoliday ("21-MAR-1996", "");

		lh.addStaticHoliday ("05-APR-1996", "");

		lh.addStaticHoliday ("08-APR-1996", "");

		lh.addStaticHoliday ("27-APR-1996", "");

		lh.addStaticHoliday ("01-MAY-1996", "");

		lh.addStaticHoliday ("16-JUN-1996", "");

		lh.addStaticHoliday ("17-JUN-1996", "");

		lh.addStaticHoliday ("09-AUG-1996", "");

		lh.addStaticHoliday ("24-SEP-1996", "");

		lh.addStaticHoliday ("16-DEC-1996", "");

		lh.addStaticHoliday ("25-DEC-1996", "");

		lh.addStaticHoliday ("26-DEC-1996", "");

		lh.addStandardWeekend();

		return lh;
	}
}
