
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

public class VNDHoliday implements org.drip.analytics.holset.LocationHoliday {
	public VNDHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "VND";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("27-JAN-1998", "Lunar New Years Eve");

		lh.addStaticHoliday ("28-JAN-1998", "Lunar New Years Day");

		lh.addStaticHoliday ("29-JAN-1998", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("30-JAN-1998", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("30-APR-1998", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-1998", "Labor Day");

		lh.addStaticHoliday ("02-SEP-1998", "Independence Day");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("15-FEB-1999", "Lunar New Years Eve");

		lh.addStaticHoliday ("16-FEB-1999", "Lunar New Years Day");

		lh.addStaticHoliday ("17-FEB-1999", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("18-FEB-1999", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("30-APR-1999", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-1999", "Labor Day");

		lh.addStaticHoliday ("02-SEP-1999", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2000", "New Years Day");

		lh.addStaticHoliday ("04-FEB-2000", "Lunar New Years Eve");

		lh.addStaticHoliday ("05-FEB-2000", "Lunar New Years Day");

		lh.addStaticHoliday ("07-FEB-2000", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("08-FEB-2000", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("01-MAY-2000", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2000", "Day before Labor Day Observed");

		lh.addStaticHoliday ("02-SEP-2000", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("23-JAN-2001", "Lunar New Years Eve");

		lh.addStaticHoliday ("24-JAN-2001", "Lunar New Years Day");

		lh.addStaticHoliday ("25-JAN-2001", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("26-JAN-2001", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("30-APR-2001", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2001", "Labor Day");

		lh.addStaticHoliday ("03-SEP-2001", "Independence Day Observed");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2002", "Lunar New Years Eve");

		lh.addStaticHoliday ("12-FEB-2002", "Lunar New Years Day");

		lh.addStaticHoliday ("13-FEB-2002", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("14-FEB-2002", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("30-APR-2002", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2002", "Labor Day");

		lh.addStaticHoliday ("02-SEP-2002", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("31-JAN-2003", "Lunar New Years Eve");

		lh.addStaticHoliday ("01-FEB-2003", "Lunar New Years Day");

		lh.addStaticHoliday ("03-FEB-2003", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("04-FEB-2003", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("30-APR-2003", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2003", "Labor Day");

		lh.addStaticHoliday ("02-SEP-2003", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2004", "Lunar New Years Eve");

		lh.addStaticHoliday ("22-JAN-2004", "Lunar New Years Day");

		lh.addStaticHoliday ("23-JAN-2004", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("24-JAN-2004", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("30-APR-2004", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2004", "Labor Day");

		lh.addStaticHoliday ("02-SEP-2004", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2005", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2005", "Lunar New Years Eve");

		lh.addStaticHoliday ("09-FEB-2005", "Lunar New Years Day");

		lh.addStaticHoliday ("10-FEB-2005", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("11-FEB-2005", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("30-APR-2005", "Day before Labor Day");

		lh.addStaticHoliday ("02-MAY-2005", "Labor Day Observed");

		lh.addStaticHoliday ("02-SEP-2005", "Independence Day");

		lh.addStaticHoliday ("02-JAN-2006", "New Years Day Observed");

		lh.addStaticHoliday ("28-JAN-2006", "Lunar New Years Eve");

		lh.addStaticHoliday ("30-JAN-2006", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("31-JAN-2006", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("01-FEB-2006", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("01-MAY-2006", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2006", "Day before Labor Day Observed");

		lh.addStaticHoliday ("02-SEP-2006", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("17-FEB-2007", "Lunar New Years Eve");

		lh.addStaticHoliday ("19-FEB-2007", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("20-FEB-2007", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("21-FEB-2007", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("30-APR-2007", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2007", "Labor Day");

		lh.addStaticHoliday ("03-SEP-2007", "Independence Day Observed");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("06-FEB-2008", "Lunar New Years Eve");

		lh.addStaticHoliday ("07-FEB-2008", "Lunar New Years Day");

		lh.addStaticHoliday ("08-FEB-2008", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("09-FEB-2008", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("30-APR-2008", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2008", "Labor Day");

		lh.addStaticHoliday ("02-SEP-2008", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2009", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("27-JAN-2009", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("28-JAN-2009", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("29-JAN-2009", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("30-APR-2009", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2009", "Labor Day");

		lh.addStaticHoliday ("02-SEP-2009", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("13-FEB-2010", "Lunar New Years Eve");

		lh.addStaticHoliday ("15-FEB-2010", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("16-FEB-2010", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("17-FEB-2010", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("30-APR-2010", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2010", "Labor Day");

		lh.addStaticHoliday ("02-SEP-2010", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2011", "New Years Day");

		lh.addStaticHoliday ("02-FEB-2011", "Lunar New Years Eve");

		lh.addStaticHoliday ("03-FEB-2011", "Lunar New Years Day");

		lh.addStaticHoliday ("04-FEB-2011", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("05-FEB-2011", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("30-APR-2011", "Day before Labor Day");

		lh.addStaticHoliday ("02-MAY-2011", "Labor Day Observed");

		lh.addStaticHoliday ("02-SEP-2011", "Independence Day");

		lh.addStaticHoliday ("02-JAN-2012", "New Years Day Observed");

		lh.addStaticHoliday ("23-JAN-2012", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("24-JAN-2012", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("25-JAN-2012", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("26-JAN-2012", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("30-APR-2012", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2012", "Labor Day");

		lh.addStaticHoliday ("03-SEP-2012", "Independence Day Observed");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("09-FEB-2013", "Lunar New Years Eve");

		lh.addStaticHoliday ("11-FEB-2013", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("12-FEB-2013", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("13-FEB-2013", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("30-APR-2013", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2013", "Labor Day");

		lh.addStaticHoliday ("02-SEP-2013", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("30-JAN-2014", "Lunar New Years Eve");

		lh.addStaticHoliday ("31-JAN-2014", "Lunar New Years Day");

		lh.addStaticHoliday ("01-FEB-2014", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("03-FEB-2014", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("30-APR-2014", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2014", "Labor Day");

		lh.addStaticHoliday ("02-SEP-2014", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("18-FEB-2015", "Lunar New Years Eve");

		lh.addStaticHoliday ("19-FEB-2015", "Lunar New Years Day");

		lh.addStaticHoliday ("20-FEB-2015", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("21-FEB-2015", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("30-APR-2015", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2015", "Labor Day");

		lh.addStaticHoliday ("02-SEP-2015", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2016", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("09-FEB-2016", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("10-FEB-2016", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("11-FEB-2016", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("30-APR-2016", "Day before Labor Day");

		lh.addStaticHoliday ("02-MAY-2016", "Labor Day Observed");

		lh.addStaticHoliday ("02-SEP-2016", "Independence Day");

		lh.addStaticHoliday ("02-JAN-2017", "New Years Day Observed");

		lh.addStaticHoliday ("27-JAN-2017", "Lunar New Years Eve");

		lh.addStaticHoliday ("28-JAN-2017", "Lunar New Years Day");

		lh.addStaticHoliday ("30-JAN-2017", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("31-JAN-2017", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("01-MAY-2017", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2017", "Day before Labor Day Observed");

		lh.addStaticHoliday ("02-SEP-2017", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("15-FEB-2018", "Lunar New Years Eve");

		lh.addStaticHoliday ("16-FEB-2018", "Lunar New Years Day");

		lh.addStaticHoliday ("17-FEB-2018", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("19-FEB-2018", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("30-APR-2018", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2018", "Labor Day");

		lh.addStaticHoliday ("03-SEP-2018", "Independence Day Observed");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("04-FEB-2019", "Lunar New Years Eve");

		lh.addStaticHoliday ("05-FEB-2019", "Lunar New Years Day");

		lh.addStaticHoliday ("06-FEB-2019", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("07-FEB-2019", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("30-APR-2019", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2019", "Labor Day");

		lh.addStaticHoliday ("02-SEP-2019", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("24-JAN-2020", "Lunar New Years Eve");

		lh.addStaticHoliday ("25-JAN-2020", "Lunar New Years Day");

		lh.addStaticHoliday ("27-JAN-2020", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-JAN-2020", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("30-APR-2020", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2020", "Labor Day");

		lh.addStaticHoliday ("02-SEP-2020", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2021", "Lunar New Years Eve");

		lh.addStaticHoliday ("12-FEB-2021", "Lunar New Years Day");

		lh.addStaticHoliday ("13-FEB-2021", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("15-FEB-2021", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("30-APR-2021", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2021", "Labor Day");

		lh.addStaticHoliday ("02-SEP-2021", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2022", "New Years Day");

		lh.addStaticHoliday ("31-JAN-2022", "Lunar New Years Eve");

		lh.addStaticHoliday ("01-FEB-2022", "Lunar New Years Day");

		lh.addStaticHoliday ("02-FEB-2022", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("03-FEB-2022", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("30-APR-2022", "Day before Labor Day");

		lh.addStaticHoliday ("02-MAY-2022", "Labor Day Observed");

		lh.addStaticHoliday ("02-SEP-2022", "Independence Day");

		lh.addStaticHoliday ("02-JAN-2023", "New Years Day Observed");

		lh.addStaticHoliday ("21-JAN-2023", "Lunar New Years Eve");

		lh.addStaticHoliday ("23-JAN-2023", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("24-JAN-2023", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("25-JAN-2023", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("01-MAY-2023", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2023", "Day before Labor Day Observed");

		lh.addStaticHoliday ("02-SEP-2023", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("09-FEB-2024", "Lunar New Years Eve");

		lh.addStaticHoliday ("10-FEB-2024", "Lunar New Years Day");

		lh.addStaticHoliday ("12-FEB-2024", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("13-FEB-2024", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("30-APR-2024", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2024", "Labor Day");

		lh.addStaticHoliday ("02-SEP-2024", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2025", "Lunar New Years Eve");

		lh.addStaticHoliday ("29-JAN-2025", "Lunar New Years Day");

		lh.addStaticHoliday ("30-JAN-2025", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("31-JAN-2025", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("30-APR-2025", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2025", "Labor Day");

		lh.addStaticHoliday ("02-SEP-2025", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("16-FEB-2026", "Lunar New Years Eve");

		lh.addStaticHoliday ("17-FEB-2026", "Lunar New Years Day");

		lh.addStaticHoliday ("18-FEB-2026", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("19-FEB-2026", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("30-APR-2026", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2026", "Labor Day");

		lh.addStaticHoliday ("02-SEP-2026", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("05-FEB-2027", "Lunar New Years Eve");

		lh.addStaticHoliday ("06-FEB-2027", "Lunar New Years Day");

		lh.addStaticHoliday ("08-FEB-2027", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("09-FEB-2027", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("30-APR-2027", "Day before Labor Day");

		lh.addStaticHoliday ("01-MAY-2027", "Labor Day");

		lh.addStaticHoliday ("02-SEP-2027", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2028", "New Years Day");

		lh.addStaticHoliday ("25-JAN-2028", "Lunar New Years Eve");

		lh.addStaticHoliday ("26-JAN-2028", "Lunar New Years Day");

		lh.addStaticHoliday ("27-JAN-2028", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("28-JAN-2028", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAY-2028", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2028", "Day before Labor Day Observed");

		lh.addStaticHoliday ("02-SEP-2028", "Independence Day");

		lh.addStandardWeekend();

		return lh;
	}
}
