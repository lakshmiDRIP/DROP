
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

public class MKDHoliday implements org.drip.analytics.holset.LocationHoliday {
	public MKDHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "MKD";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("02-JAN-1998", "New Years Holiday");

		lh.addStaticHoliday ("07-JAN-1998", "Orthodox Christmas Day");

		lh.addStaticHoliday ("20-APR-1998", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-1998", "Labour Day");

		lh.addStaticHoliday ("04-MAY-1998", "Day after Labour Day Observed");

		lh.addStaticHoliday ("03-AUG-1998", "National Holiday Observed");

		lh.addStaticHoliday ("08-SEP-1998", "Independence Day");

		lh.addStaticHoliday ("12-OCT-1998", "Liberation Day Observed");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("04-JAN-1999", "New Years Holiday Observed");

		lh.addStaticHoliday ("07-JAN-1999", "Orthodox Christmas Day");

		lh.addStaticHoliday ("12-APR-1999", "Orthodox Easter Monday");

		lh.addStaticHoliday ("03-MAY-1999", "Labour Day Observed");

		lh.addStaticHoliday ("02-AUG-1999", "National Holiday");

		lh.addStaticHoliday ("08-SEP-1999", "Independence Day");

		lh.addStaticHoliday ("11-OCT-1999", "Liberation Day");

		lh.addStaticHoliday ("03-JAN-2000", "New Years Day Observed");

		lh.addStaticHoliday ("07-JAN-2000", "Orthodox Christmas Day");

		lh.addStaticHoliday ("01-MAY-2000", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2000", "Day after Labour Day");

		lh.addStaticHoliday ("02-AUG-2000", "National Holiday");

		lh.addStaticHoliday ("08-SEP-2000", "Independence Day");

		lh.addStaticHoliday ("11-OCT-2000", "Liberation Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2001", "New Years Holiday");

		lh.addStaticHoliday ("08-JAN-2001", "Orthodox Christmas Day Observed");

		lh.addStaticHoliday ("16-APR-2001", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2001", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2001", "Day after Labour Day");

		lh.addStaticHoliday ("02-AUG-2001", "National Holiday");

		lh.addStaticHoliday ("10-SEP-2001", "Independence Day Observed");

		lh.addStaticHoliday ("11-OCT-2001", "Liberation Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2002", "New Years Holiday");

		lh.addStaticHoliday ("07-JAN-2002", "Orthodox Christmas Day");

		lh.addStaticHoliday ("01-MAY-2002", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2002", "Day after Labour Day");

		lh.addStaticHoliday ("06-MAY-2002", "Orthodox Easter Monday");

		lh.addStaticHoliday ("02-AUG-2002", "National Holiday");

		lh.addStaticHoliday ("09-SEP-2002", "Independence Day Observed");

		lh.addStaticHoliday ("11-OCT-2002", "Liberation Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2003", "New Years Holiday");

		lh.addStaticHoliday ("07-JAN-2003", "Orthodox Christmas Day");

		lh.addStaticHoliday ("28-APR-2003", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2003", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2003", "Day after Labour Day");

		lh.addStaticHoliday ("04-AUG-2003", "National Holiday Observed");

		lh.addStaticHoliday ("08-SEP-2003", "Independence Day");

		lh.addStaticHoliday ("13-OCT-2003", "Liberation Day Observed");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2004", "New Years Holiday");

		lh.addStaticHoliday ("07-JAN-2004", "Orthodox Christmas Day");

		lh.addStaticHoliday ("12-APR-2004", "Orthodox Easter Monday");

		lh.addStaticHoliday ("03-MAY-2004", "Labour Day Observed");

		lh.addStaticHoliday ("02-AUG-2004", "National Holiday");

		lh.addStaticHoliday ("08-SEP-2004", "Independence Day");

		lh.addStaticHoliday ("11-OCT-2004", "Liberation Day");

		lh.addStaticHoliday ("03-JAN-2005", "New Years Day Observed");

		lh.addStaticHoliday ("07-JAN-2005", "Orthodox Christmas Day");

		lh.addStaticHoliday ("02-MAY-2005", "Day after Labour Day");

		lh.addStaticHoliday ("03-MAY-2005", "Labour Day Observed");

		lh.addStaticHoliday ("02-AUG-2005", "National Holiday");

		lh.addStaticHoliday ("08-SEP-2005", "Independence Day");

		lh.addStaticHoliday ("11-OCT-2005", "Liberation Day");

		lh.addStaticHoliday ("02-JAN-2006", "New Years Holiday");

		lh.addStaticHoliday ("03-JAN-2006", "New Years Day Observed");

		lh.addStaticHoliday ("09-JAN-2006", "Orthodox Christmas Day Observed");

		lh.addStaticHoliday ("24-APR-2006", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2006", "Day after Labour Day");

		lh.addStaticHoliday ("02-AUG-2006", "National Holiday");

		lh.addStaticHoliday ("08-SEP-2006", "Independence Day");

		lh.addStaticHoliday ("11-OCT-2006", "Liberation Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2007", "New Years Holiday");

		lh.addStaticHoliday ("08-JAN-2007", "Orthodox Christmas Day Observed");

		lh.addStaticHoliday ("09-APR-2007", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2007", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2007", "Day after Labour Day");

		lh.addStaticHoliday ("02-AUG-2007", "National Holiday");

		lh.addStaticHoliday ("10-SEP-2007", "Independence Day Observed");

		lh.addStaticHoliday ("11-OCT-2007", "Liberation Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2008", "New Years Holiday");

		lh.addStaticHoliday ("07-JAN-2008", "Orthodox Christmas Day");

		lh.addStaticHoliday ("28-APR-2008", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2008", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2008", "Day after Labour Day");

		lh.addStaticHoliday ("04-AUG-2008", "National Holiday Observed");

		lh.addStaticHoliday ("08-SEP-2008", "Independence Day");

		lh.addStaticHoliday ("13-OCT-2008", "Liberation Day Observed");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2009", "New Years Holiday");

		lh.addStaticHoliday ("07-JAN-2009", "Orthodox Christmas Day");

		lh.addStaticHoliday ("20-APR-2009", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2009", "Labour Day");

		lh.addStaticHoliday ("04-MAY-2009", "Day after Labour Day Observed");

		lh.addStaticHoliday ("03-AUG-2009", "National Holiday Observed");

		lh.addStaticHoliday ("08-SEP-2009", "Independence Day");

		lh.addStaticHoliday ("12-OCT-2009", "Liberation Day Observed");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("04-JAN-2010", "New Years Holiday Observed");

		lh.addStaticHoliday ("07-JAN-2010", "Orthodox Christmas Day");

		lh.addStaticHoliday ("05-APR-2010", "Orthodox Easter Monday");

		lh.addStaticHoliday ("03-MAY-2010", "Labour Day Observed");

		lh.addStaticHoliday ("02-AUG-2010", "National Holiday");

		lh.addStaticHoliday ("08-SEP-2010", "Independence Day");

		lh.addStaticHoliday ("11-OCT-2010", "Liberation Day");

		lh.addStaticHoliday ("03-JAN-2011", "New Years Day Observed");

		lh.addStaticHoliday ("07-JAN-2011", "Orthodox Christmas Day");

		lh.addStaticHoliday ("25-APR-2011", "Orthodox Easter Monday");

		lh.addStaticHoliday ("02-MAY-2011", "Day after Labour Day");

		lh.addStaticHoliday ("03-MAY-2011", "Labour Day Observed");

		lh.addStaticHoliday ("02-AUG-2011", "National Holiday");

		lh.addStaticHoliday ("08-SEP-2011", "Independence Day");

		lh.addStaticHoliday ("11-OCT-2011", "Liberation Day");

		lh.addStaticHoliday ("02-JAN-2012", "New Years Holiday");

		lh.addStaticHoliday ("03-JAN-2012", "New Years Day Observed");

		lh.addStaticHoliday ("09-JAN-2012", "Orthodox Christmas Day Observed");

		lh.addStaticHoliday ("16-APR-2012", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2012", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2012", "Day after Labour Day");

		lh.addStaticHoliday ("02-AUG-2012", "National Holiday");

		lh.addStaticHoliday ("10-SEP-2012", "Independence Day Observed");

		lh.addStaticHoliday ("11-OCT-2012", "Liberation Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2013", "New Years Holiday");

		lh.addStaticHoliday ("07-JAN-2013", "Orthodox Christmas Day");

		lh.addStaticHoliday ("01-MAY-2013", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2013", "Day after Labour Day");

		lh.addStaticHoliday ("06-MAY-2013", "Orthodox Easter Monday");

		lh.addStaticHoliday ("02-AUG-2013", "National Holiday");

		lh.addStaticHoliday ("09-SEP-2013", "Independence Day Observed");

		lh.addStaticHoliday ("11-OCT-2013", "Liberation Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2014", "New Years Holiday");

		lh.addStaticHoliday ("07-JAN-2014", "Orthodox Christmas Day");

		lh.addStaticHoliday ("21-APR-2014", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2014", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2014", "Day after Labour Day");

		lh.addStaticHoliday ("04-AUG-2014", "National Holiday Observed");

		lh.addStaticHoliday ("08-SEP-2014", "Independence Day");

		lh.addStaticHoliday ("13-OCT-2014", "Liberation Day Observed");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2015", "New Years Holiday");

		lh.addStaticHoliday ("07-JAN-2015", "Orthodox Christmas Day");

		lh.addStaticHoliday ("13-APR-2015", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2015", "Labour Day");

		lh.addStaticHoliday ("04-MAY-2015", "Day after Labour Day Observed");

		lh.addStaticHoliday ("03-AUG-2015", "National Holiday Observed");

		lh.addStaticHoliday ("08-SEP-2015", "Independence Day");

		lh.addStaticHoliday ("12-OCT-2015", "Liberation Day Observed");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("04-JAN-2016", "New Years Holiday Observed");

		lh.addStaticHoliday ("07-JAN-2016", "Orthodox Christmas Day");

		lh.addStaticHoliday ("02-MAY-2016", "Day after Labour Day");

		lh.addStaticHoliday ("03-MAY-2016", "Labour Day Observed");

		lh.addStaticHoliday ("02-AUG-2016", "National Holiday");

		lh.addStaticHoliday ("08-SEP-2016", "Independence Day");

		lh.addStaticHoliday ("11-OCT-2016", "Liberation Day");

		lh.addStaticHoliday ("02-JAN-2017", "New Years Holiday");

		lh.addStaticHoliday ("03-JAN-2017", "New Years Day Observed");

		lh.addStaticHoliday ("09-JAN-2017", "Orthodox Christmas Day Observed");

		lh.addStaticHoliday ("17-APR-2017", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2017", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2017", "Day after Labour Day");

		lh.addStaticHoliday ("02-AUG-2017", "National Holiday");

		lh.addStaticHoliday ("08-SEP-2017", "Independence Day");

		lh.addStaticHoliday ("11-OCT-2017", "Liberation Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2018", "New Years Holiday");

		lh.addStaticHoliday ("08-JAN-2018", "Orthodox Christmas Day Observed");

		lh.addStaticHoliday ("09-APR-2018", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2018", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2018", "Day after Labour Day");

		lh.addStaticHoliday ("02-AUG-2018", "National Holiday");

		lh.addStaticHoliday ("10-SEP-2018", "Independence Day Observed");

		lh.addStaticHoliday ("11-OCT-2018", "Liberation Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2019", "New Years Holiday");

		lh.addStaticHoliday ("07-JAN-2019", "Orthodox Christmas Day");

		lh.addStaticHoliday ("29-APR-2019", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2019", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2019", "Day after Labour Day");

		lh.addStaticHoliday ("02-AUG-2019", "National Holiday");

		lh.addStaticHoliday ("09-SEP-2019", "Independence Day Observed");

		lh.addStaticHoliday ("11-OCT-2019", "Liberation Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2020", "New Years Holiday");

		lh.addStaticHoliday ("07-JAN-2020", "Orthodox Christmas Day");

		lh.addStaticHoliday ("20-APR-2020", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2020", "Labour Day");

		lh.addStaticHoliday ("04-MAY-2020", "Day after Labour Day Observed");

		lh.addStaticHoliday ("03-AUG-2020", "National Holiday Observed");

		lh.addStaticHoliday ("08-SEP-2020", "Independence Day");

		lh.addStaticHoliday ("12-OCT-2020", "Liberation Day Observed");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("04-JAN-2021", "New Years Holiday Observed");

		lh.addStaticHoliday ("07-JAN-2021", "Orthodox Christmas Day");

		lh.addStaticHoliday ("03-MAY-2021", "Labour Day Observed");

		lh.addStaticHoliday ("02-AUG-2021", "National Holiday");

		lh.addStaticHoliday ("08-SEP-2021", "Independence Day");

		lh.addStaticHoliday ("11-OCT-2021", "Liberation Day");

		lh.addStaticHoliday ("03-JAN-2022", "New Years Day Observed");

		lh.addStaticHoliday ("07-JAN-2022", "Orthodox Christmas Day");

		lh.addStaticHoliday ("25-APR-2022", "Orthodox Easter Monday");

		lh.addStaticHoliday ("02-MAY-2022", "Day after Labour Day");

		lh.addStaticHoliday ("03-MAY-2022", "Labour Day Observed");

		lh.addStaticHoliday ("02-AUG-2022", "National Holiday");

		lh.addStaticHoliday ("08-SEP-2022", "Independence Day");

		lh.addStaticHoliday ("11-OCT-2022", "Liberation Day");

		lh.addStaticHoliday ("02-JAN-2023", "New Years Holiday");

		lh.addStaticHoliday ("03-JAN-2023", "New Years Day Observed");

		lh.addStaticHoliday ("09-JAN-2023", "Orthodox Christmas Day Observed");

		lh.addStaticHoliday ("17-APR-2023", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2023", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2023", "Day after Labour Day");

		lh.addStaticHoliday ("02-AUG-2023", "National Holiday");

		lh.addStaticHoliday ("08-SEP-2023", "Independence Day");

		lh.addStaticHoliday ("11-OCT-2023", "Liberation Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2024", "New Years Holiday");

		lh.addStaticHoliday ("08-JAN-2024", "Orthodox Christmas Day Observed");

		lh.addStaticHoliday ("01-MAY-2024", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2024", "Day after Labour Day");

		lh.addStaticHoliday ("06-MAY-2024", "Orthodox Easter Monday");

		lh.addStaticHoliday ("02-AUG-2024", "National Holiday");

		lh.addStaticHoliday ("09-SEP-2024", "Independence Day Observed");

		lh.addStaticHoliday ("11-OCT-2024", "Liberation Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2025", "New Years Holiday");

		lh.addStaticHoliday ("07-JAN-2025", "Orthodox Christmas Day");

		lh.addStaticHoliday ("21-APR-2025", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2025", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2025", "Day after Labour Day");

		lh.addStaticHoliday ("04-AUG-2025", "National Holiday Observed");

		lh.addStaticHoliday ("08-SEP-2025", "Independence Day");

		lh.addStaticHoliday ("13-OCT-2025", "Liberation Day Observed");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2026", "New Years Holiday");

		lh.addStaticHoliday ("07-JAN-2026", "Orthodox Christmas Day");

		lh.addStaticHoliday ("13-APR-2026", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2026", "Labour Day");

		lh.addStaticHoliday ("04-MAY-2026", "Day after Labour Day Observed");

		lh.addStaticHoliday ("03-AUG-2026", "National Holiday Observed");

		lh.addStaticHoliday ("08-SEP-2026", "Independence Day");

		lh.addStaticHoliday ("12-OCT-2026", "Liberation Day Observed");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("04-JAN-2027", "New Years Holiday Observed");

		lh.addStaticHoliday ("07-JAN-2027", "Orthodox Christmas Day");

		lh.addStaticHoliday ("03-MAY-2027", "Labour Day Observed");

		lh.addStaticHoliday ("02-AUG-2027", "National Holiday");

		lh.addStaticHoliday ("08-SEP-2027", "Independence Day");

		lh.addStaticHoliday ("11-OCT-2027", "Liberation Day");

		lh.addStaticHoliday ("03-JAN-2028", "New Years Day Observed");

		lh.addStaticHoliday ("07-JAN-2028", "Orthodox Christmas Day");

		lh.addStaticHoliday ("17-APR-2028", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2028", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2028", "Day after Labour Day");

		lh.addStaticHoliday ("02-AUG-2028", "National Holiday");

		lh.addStaticHoliday ("08-SEP-2028", "Independence Day");

		lh.addStaticHoliday ("11-OCT-2028", "Liberation Day");

		lh.addStandardWeekend();

		return lh;
	}
}
