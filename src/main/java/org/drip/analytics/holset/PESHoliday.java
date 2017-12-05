
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

public class PESHoliday implements org.drip.analytics.holset.LocationHoliday {
	public PESHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "PES";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("09-FEB-1994", "Lunar New Year");

		lh.addStaticHoliday ("10-FEB-1994", "Lunar New Year");

		lh.addStaticHoliday ("11-FEB-1994", "Lunar New Year");

		lh.addStaticHoliday ("01-MAR-1994", "Independence Movement Day");

		lh.addStaticHoliday ("05-APR-1994", "Arbor Day");

		lh.addStaticHoliday ("05-MAY-1994", "Childrens Day");

		lh.addStaticHoliday ("18-MAY-1994", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-1994", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-1994", "Liberation Day");

		lh.addStaticHoliday ("19-SEP-1994", "Chusok");

		lh.addStaticHoliday ("20-SEP-1994", "Chusok");

		lh.addStaticHoliday ("21-SEP-1994", "Chusok");

		lh.addStaticHoliday ("03-OCT-1994", "National Foundation Day");

		lh.addStaticHoliday ("01-JAN-1995", "");

		lh.addStaticHoliday ("02-JAN-1995", "");

		lh.addStaticHoliday ("30-JAN-1995", "");

		lh.addStaticHoliday ("01-FEB-1995", "");

		lh.addStaticHoliday ("01-MAR-1995", "");

		lh.addStaticHoliday ("05-APR-1995", "");

		lh.addStaticHoliday ("05-MAY-1995", "");

		lh.addStaticHoliday ("07-MAY-1995", "");

		lh.addStaticHoliday ("06-JUN-1995", "");

		lh.addStaticHoliday ("17-JUL-1995", "");

		lh.addStaticHoliday ("15-AUG-1995", "");

		lh.addStaticHoliday ("08-SEP-1995", "");

		lh.addStaticHoliday ("09-SEP-1995", "");

		lh.addStaticHoliday ("10-SEP-1995", "");

		lh.addStaticHoliday ("03-OCT-1995", "");

		lh.addStaticHoliday ("25-DEC-1995", "");

		lh.addStandardWeekend();

		return lh;
	}
}
