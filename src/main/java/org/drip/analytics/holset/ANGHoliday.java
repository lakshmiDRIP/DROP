
package org.drip.analytics.holset;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*
 *    GENERATED on Fri Jan 11 19:54:06 EST 2013 ---- DO NOT DELETE
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

public class ANGHoliday implements org.drip.analytics.holset.LocationHoliday {
	public ANGHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "ANG";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("23-FEB-1998", "Carnival Monday");

		lh.addStaticHoliday ("10-APR-1998", "Good Friday");

		lh.addStaticHoliday ("13-APR-1998", "Easter Monday");

		lh.addStaticHoliday ("30-APR-1998", "Coronation Day");

		lh.addStaticHoliday ("01-MAY-1998", "Labour Day");

		lh.addStaticHoliday ("21-MAY-1998", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-1998", "Curacao Flag Day");

		lh.addStaticHoliday ("21-OCT-1998", "Antilles Day");

		lh.addStaticHoliday ("25-DEC-1998", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("15-FEB-1999", "Carnival Monday");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("05-APR-1999", "Easter Monday");

		lh.addStaticHoliday ("30-APR-1999", "Coronation Day");

		lh.addStaticHoliday ("13-MAY-1999", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-1999", "Curacao Flag Day");

		lh.addStaticHoliday ("21-OCT-1999", "Antilles Day");

		lh.addStaticHoliday ("06-MAR-2000", "Carnival Monday");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("24-APR-2000", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2000", "Labour Day");

		lh.addStaticHoliday ("01-JUN-2000", "Ascension Day");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2000", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("26-FEB-2001", "Carnival Monday");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("16-APR-2001", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2001", "Coronation Day");

		lh.addStaticHoliday ("01-MAY-2001", "Labour Day");

		lh.addStaticHoliday ("24-MAY-2001", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2001", "Curacao Flag Day");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2001", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2002", "Carnival Monday");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("01-APR-2002", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2002", "Coronation Day");

		lh.addStaticHoliday ("01-MAY-2002", "Labour Day");

		lh.addStaticHoliday ("09-MAY-2002", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2002", "Curacao Flag Day");

		lh.addStaticHoliday ("21-OCT-2002", "Antilles Day");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2002", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("03-MAR-2003", "Carnival Monday");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("21-APR-2003", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2003", "Coronation Day");

		lh.addStaticHoliday ("01-MAY-2003", "Labour Day");

		lh.addStaticHoliday ("29-MAY-2003", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2003", "Curacao Flag Day");

		lh.addStaticHoliday ("21-OCT-2003", "Antilles Day");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2003", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("23-FEB-2004", "Carnival Monday");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("12-APR-2004", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2004", "Coronation Day");

		lh.addStaticHoliday ("20-MAY-2004", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2004", "Curacao Flag Day");

		lh.addStaticHoliday ("21-OCT-2004", "Antilles Day");

		lh.addStaticHoliday ("07-FEB-2005", "Carnival Monday");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("28-MAR-2005", "Easter Monday");

		lh.addStaticHoliday ("05-MAY-2005", "Ascension Day");

		lh.addStaticHoliday ("21-OCT-2005", "Antilles Day");

		lh.addStaticHoliday ("26-DEC-2005", "Boxing Day");

		lh.addStaticHoliday ("27-FEB-2006", "Carnival Monday");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("17-APR-2006", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2006", "Ascension Day");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2006", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("19-FEB-2007", "Carnival Monday");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("09-APR-2007", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2007", "Coronation Day");

		lh.addStaticHoliday ("01-MAY-2007", "Labour Day");

		lh.addStaticHoliday ("17-MAY-2007", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2007", "Curacao Flag Day");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2007", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("04-FEB-2008", "Carnival Monday");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("24-MAR-2008", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2008", "Coronation Day");

		lh.addStaticHoliday ("01-MAY-2008", "Labour Day");

		lh.addStaticHoliday ("02-JUL-2008", "Curacao Flag Day");

		lh.addStaticHoliday ("21-OCT-2008", "Antilles Day");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2008", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("23-FEB-2009", "Carnival Monday");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("13-APR-2009", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2009", "Coronation Day");

		lh.addStaticHoliday ("01-MAY-2009", "Labour Day");

		lh.addStaticHoliday ("21-MAY-2009", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2009", "Curacao Flag Day");

		lh.addStaticHoliday ("21-OCT-2009", "Antilles Day");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("15-FEB-2010", "Carnival Monday");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("05-APR-2010", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2010", "Coronation Day");

		lh.addStaticHoliday ("13-MAY-2010", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2010", "Curacao Flag Day");

		lh.addStaticHoliday ("21-OCT-2010", "Antilles Day");

		lh.addStaticHoliday ("07-MAR-2011", "Carnival Monday");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("25-APR-2011", "Easter Monday");

		lh.addStaticHoliday ("02-JUN-2011", "Ascension Day");

		lh.addStaticHoliday ("21-OCT-2011", "Antilles Day");

		lh.addStaticHoliday ("26-DEC-2011", "Boxing Day");

		lh.addStaticHoliday ("20-FEB-2012", "Carnival Monday");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("09-APR-2012", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2012", "Coronation Day");

		lh.addStaticHoliday ("01-MAY-2012", "Labour Day");

		lh.addStaticHoliday ("17-MAY-2012", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2012", "Curacao Flag Day");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2012", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2013", "Carnival Monday");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("01-APR-2013", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2013", "Coronation Day");

		lh.addStaticHoliday ("01-MAY-2013", "Labour Day");

		lh.addStaticHoliday ("09-MAY-2013", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2013", "Curacao Flag Day");

		lh.addStaticHoliday ("21-OCT-2013", "Antilles Day");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2013", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("03-MAR-2014", "Carnival Monday");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("21-APR-2014", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2014", "Coronation Day");

		lh.addStaticHoliday ("01-MAY-2014", "Labour Day");

		lh.addStaticHoliday ("29-MAY-2014", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2014", "Curacao Flag Day");

		lh.addStaticHoliday ("21-OCT-2014", "Antilles Day");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2014", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("16-FEB-2015", "Carnival Monday");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("06-APR-2015", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2015", "Coronation Day");

		lh.addStaticHoliday ("01-MAY-2015", "Labour Day");

		lh.addStaticHoliday ("14-MAY-2015", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2015", "Curacao Flag Day");

		lh.addStaticHoliday ("21-OCT-2015", "Antilles Day");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2016", "Carnival Monday");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("28-MAR-2016", "Easter Monday");

		lh.addStaticHoliday ("05-MAY-2016", "Ascension Day");

		lh.addStaticHoliday ("21-OCT-2016", "Antilles Day");

		lh.addStaticHoliday ("26-DEC-2016", "Boxing Day");

		lh.addStaticHoliday ("27-FEB-2017", "Carnival Monday");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("17-APR-2017", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2017", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2017", "Ascension Day");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2017", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("12-FEB-2018", "Carnival Monday");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("02-APR-2018", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2018", "Coronation Day");

		lh.addStaticHoliday ("01-MAY-2018", "Labour Day");

		lh.addStaticHoliday ("10-MAY-2018", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2018", "Curacao Flag Day");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2018", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("04-MAR-2019", "Carnival Monday");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("22-APR-2019", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2019", "Coronation Day");

		lh.addStaticHoliday ("01-MAY-2019", "Labour Day");

		lh.addStaticHoliday ("30-MAY-2019", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2019", "Curacao Flag Day");

		lh.addStaticHoliday ("21-OCT-2019", "Antilles Day");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2019", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("24-FEB-2020", "Carnival Monday");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("13-APR-2020", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2020", "Coronation Day");

		lh.addStaticHoliday ("01-MAY-2020", "Labour Day");

		lh.addStaticHoliday ("21-MAY-2020", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2020", "Curacao Flag Day");

		lh.addStaticHoliday ("21-OCT-2020", "Antilles Day");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("15-FEB-2021", "Carnival Monday");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("05-APR-2021", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2021", "Coronation Day");

		lh.addStaticHoliday ("13-MAY-2021", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2021", "Curacao Flag Day");

		lh.addStaticHoliday ("21-OCT-2021", "Antilles Day");

		lh.addStaticHoliday ("28-FEB-2022", "Carnival Monday");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("18-APR-2022", "Easter Monday");

		lh.addStaticHoliday ("26-MAY-2022", "Ascension Day");

		lh.addStaticHoliday ("21-OCT-2022", "Antilles Day");

		lh.addStaticHoliday ("26-DEC-2022", "Boxing Day");

		lh.addStaticHoliday ("20-FEB-2023", "Carnival Monday");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("10-APR-2023", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2023", "Labour Day");

		lh.addStaticHoliday ("18-MAY-2023", "Ascension Day");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2023", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("12-FEB-2024", "Carnival Monday");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("01-APR-2024", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2024", "Coronation Day");

		lh.addStaticHoliday ("01-MAY-2024", "Labour Day");

		lh.addStaticHoliday ("09-MAY-2024", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2024", "Curacao Flag Day");

		lh.addStaticHoliday ("21-OCT-2024", "Antilles Day");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2024", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("03-MAR-2025", "Carnival Monday");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("21-APR-2025", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2025", "Coronation Day");

		lh.addStaticHoliday ("01-MAY-2025", "Labour Day");

		lh.addStaticHoliday ("29-MAY-2025", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2025", "Curacao Flag Day");

		lh.addStaticHoliday ("21-OCT-2025", "Antilles Day");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2025", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("16-FEB-2026", "Carnival Monday");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("06-APR-2026", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2026", "Coronation Day");

		lh.addStaticHoliday ("01-MAY-2026", "Labour Day");

		lh.addStaticHoliday ("14-MAY-2026", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2026", "Curacao Flag Day");

		lh.addStaticHoliday ("21-OCT-2026", "Antilles Day");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2027", "Carnival Monday");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("29-MAR-2027", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2027", "Coronation Day");

		lh.addStaticHoliday ("06-MAY-2027", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2027", "Curacao Flag Day");

		lh.addStaticHoliday ("21-OCT-2027", "Antilles Day");

		lh.addStaticHoliday ("28-FEB-2028", "Carnival Monday");

		lh.addStaticHoliday ("14-APR-2028", "Good Friday");

		lh.addStaticHoliday ("17-APR-2028", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2028", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2028", "Ascension Day");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2028", "Boxing Day");

		lh.addStandardWeekend();

		return lh;
	}
}
