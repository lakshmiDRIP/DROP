
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

public class SVCHoliday implements org.drip.analytics.holset.LocationHoliday {
	public SVCHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "SVC";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("08-APR-1998", "Holy Wednesday");

		lh.addStaticHoliday ("09-APR-1998", "Holy Thursday");

		lh.addStaticHoliday ("10-APR-1998", "Good Friday");

		lh.addStaticHoliday ("01-MAY-1998", "Labor Day");

		lh.addStaticHoliday ("30-JUN-1998", "Bank Holiday");

		lh.addStaticHoliday ("04-AUG-1998", "Agostinas Holiday");

		lh.addStaticHoliday ("05-AUG-1998", "Agostinas Holiday");

		lh.addStaticHoliday ("06-AUG-1998", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-1998", "Independence Day");

		lh.addStaticHoliday ("02-NOV-1998", "All Souls Day");

		lh.addStaticHoliday ("25-DEC-1998", "Christmas Day");

		lh.addStaticHoliday ("31-DEC-1998", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("31-MAR-1999", "Holy Wednesday");

		lh.addStaticHoliday ("01-APR-1999", "Holy Thursday");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("30-JUN-1999", "Bank Holiday");

		lh.addStaticHoliday ("04-AUG-1999", "Agostinas Holiday");

		lh.addStaticHoliday ("05-AUG-1999", "Agostinas Holiday");

		lh.addStaticHoliday ("06-AUG-1999", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-1999", "Independence Day");

		lh.addStaticHoliday ("02-NOV-1999", "All Souls Day");

		lh.addStaticHoliday ("31-DEC-1999", "Bank Holiday");

		lh.addStaticHoliday ("19-APR-2000", "Holy Wednesday");

		lh.addStaticHoliday ("20-APR-2000", "Holy Thursday");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2000", "Labor Day");

		lh.addStaticHoliday ("30-JUN-2000", "Bank Holiday");

		lh.addStaticHoliday ("04-AUG-2000", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-2000", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2000", "All Souls Day");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("11-APR-2001", "Holy Wednesday");

		lh.addStaticHoliday ("12-APR-2001", "Holy Thursday");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2001", "Labor Day");

		lh.addStaticHoliday ("06-AUG-2001", "Agostinas Holiday");

		lh.addStaticHoliday ("02-NOV-2001", "All Souls Day");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("31-DEC-2001", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("27-MAR-2002", "Holy Wednesday");

		lh.addStaticHoliday ("28-MAR-2002", "Holy Thursday");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2002", "Labor Day");

		lh.addStaticHoliday ("05-AUG-2002", "Agostinas Holiday");

		lh.addStaticHoliday ("06-AUG-2002", "Agostinas Holiday");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("30-DEC-2002", "Bank Holiday");

		lh.addStaticHoliday ("31-DEC-2002", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("16-APR-2003", "Holy Wednesday");

		lh.addStaticHoliday ("17-APR-2003", "Holy Thursday");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2003", "Labor Day");

		lh.addStaticHoliday ("30-JUN-2003", "Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2003", "Agostinas Holiday");

		lh.addStaticHoliday ("06-AUG-2003", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-2003", "Independence Day");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("30-DEC-2003", "Bank Holiday");

		lh.addStaticHoliday ("31-DEC-2003", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("07-APR-2004", "Holy Wednesday");

		lh.addStaticHoliday ("08-APR-2004", "Holy Thursday");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("30-JUN-2004", "Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2004", "Agostinas Holiday");

		lh.addStaticHoliday ("06-AUG-2004", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-2004", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2004", "All Souls Day");

		lh.addStaticHoliday ("30-DEC-2004", "Bank Holiday");

		lh.addStaticHoliday ("31-DEC-2004", "Bank Holiday");

		lh.addStaticHoliday ("23-MAR-2005", "Holy Wednesday");

		lh.addStaticHoliday ("24-MAR-2005", "Holy Thursday");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("30-JUN-2005", "Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2005", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-2005", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2005", "All Souls Day");

		lh.addStaticHoliday ("30-DEC-2005", "Bank Holiday");

		lh.addStaticHoliday ("12-APR-2006", "Holy Wednesday");

		lh.addStaticHoliday ("13-APR-2006", "Holy Thursday");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2006", "Labor Day");

		lh.addStaticHoliday ("30-JUN-2006", "Bank Holiday");

		lh.addStaticHoliday ("15-SEP-2006", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2006", "All Souls Day");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("04-APR-2007", "Holy Wednesday");

		lh.addStaticHoliday ("05-APR-2007", "Holy Thursday");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2007", "Labor Day");

		lh.addStaticHoliday ("06-AUG-2007", "Agostinas Holiday");

		lh.addStaticHoliday ("02-NOV-2007", "All Souls Day");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("31-DEC-2007", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("19-MAR-2008", "Holy Wednesday");

		lh.addStaticHoliday ("20-MAR-2008", "Holy Thursday");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2008", "Labor Day");

		lh.addStaticHoliday ("30-JUN-2008", "Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2008", "Agostinas Holiday");

		lh.addStaticHoliday ("06-AUG-2008", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-2008", "Independence Day");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("30-DEC-2008", "Bank Holiday");

		lh.addStaticHoliday ("31-DEC-2008", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("08-APR-2009", "Holy Wednesday");

		lh.addStaticHoliday ("09-APR-2009", "Holy Thursday");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2009", "Labor Day");

		lh.addStaticHoliday ("30-JUN-2009", "Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2009", "Agostinas Holiday");

		lh.addStaticHoliday ("06-AUG-2009", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-2009", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2009", "All Souls Day");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("30-DEC-2009", "Bank Holiday");

		lh.addStaticHoliday ("31-DEC-2009", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("31-MAR-2010", "Holy Wednesday");

		lh.addStaticHoliday ("01-APR-2010", "Holy Thursday");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("30-JUN-2010", "Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2010", "Agostinas Holiday");

		lh.addStaticHoliday ("06-AUG-2010", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-2010", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2010", "All Souls Day");

		lh.addStaticHoliday ("30-DEC-2010", "Bank Holiday");

		lh.addStaticHoliday ("31-DEC-2010", "Bank Holiday");

		lh.addStaticHoliday ("20-APR-2011", "Holy Wednesday");

		lh.addStaticHoliday ("21-APR-2011", "Holy Thursday");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("30-JUN-2011", "Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2011", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-2011", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2011", "All Souls Day");

		lh.addStaticHoliday ("30-DEC-2011", "Bank Holiday");

		lh.addStaticHoliday ("04-APR-2012", "Holy Wednesday");

		lh.addStaticHoliday ("05-APR-2012", "Holy Thursday");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2012", "Labor Day");

		lh.addStaticHoliday ("06-AUG-2012", "Agostinas Holiday");

		lh.addStaticHoliday ("02-NOV-2012", "All Souls Day");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("31-DEC-2012", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("27-MAR-2013", "Holy Wednesday");

		lh.addStaticHoliday ("28-MAR-2013", "Holy Thursday");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2013", "Labor Day");

		lh.addStaticHoliday ("05-AUG-2013", "Agostinas Holiday");

		lh.addStaticHoliday ("06-AUG-2013", "Agostinas Holiday");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("30-DEC-2013", "Bank Holiday");

		lh.addStaticHoliday ("31-DEC-2013", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("16-APR-2014", "Holy Wednesday");

		lh.addStaticHoliday ("17-APR-2014", "Holy Thursday");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2014", "Labor Day");

		lh.addStaticHoliday ("30-JUN-2014", "Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2014", "Agostinas Holiday");

		lh.addStaticHoliday ("06-AUG-2014", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-2014", "Independence Day");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("30-DEC-2014", "Bank Holiday");

		lh.addStaticHoliday ("31-DEC-2014", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("01-APR-2015", "Holy Wednesday");

		lh.addStaticHoliday ("02-APR-2015", "Holy Thursday");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2015", "Labor Day");

		lh.addStaticHoliday ("30-JUN-2015", "Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2015", "Agostinas Holiday");

		lh.addStaticHoliday ("06-AUG-2015", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-2015", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2015", "All Souls Day");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("30-DEC-2015", "Bank Holiday");

		lh.addStaticHoliday ("31-DEC-2015", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("23-MAR-2016", "Holy Wednesday");

		lh.addStaticHoliday ("24-MAR-2016", "Holy Thursday");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("30-JUN-2016", "Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2016", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-2016", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2016", "All Souls Day");

		lh.addStaticHoliday ("30-DEC-2016", "Bank Holiday");

		lh.addStaticHoliday ("12-APR-2017", "Holy Wednesday");

		lh.addStaticHoliday ("13-APR-2017", "Holy Thursday");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2017", "Labor Day");

		lh.addStaticHoliday ("30-JUN-2017", "Bank Holiday");

		lh.addStaticHoliday ("15-SEP-2017", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2017", "All Souls Day");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("28-MAR-2018", "Holy Wednesday");

		lh.addStaticHoliday ("29-MAR-2018", "Holy Thursday");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2018", "Labor Day");

		lh.addStaticHoliday ("06-AUG-2018", "Agostinas Holiday");

		lh.addStaticHoliday ("02-NOV-2018", "All Souls Day");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("31-DEC-2018", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("17-APR-2019", "Holy Wednesday");

		lh.addStaticHoliday ("18-APR-2019", "Holy Thursday");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2019", "Labor Day");

		lh.addStaticHoliday ("05-AUG-2019", "Agostinas Holiday");

		lh.addStaticHoliday ("06-AUG-2019", "Agostinas Holiday");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("30-DEC-2019", "Bank Holiday");

		lh.addStaticHoliday ("31-DEC-2019", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("08-APR-2020", "Holy Wednesday");

		lh.addStaticHoliday ("09-APR-2020", "Holy Thursday");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2020", "Labor Day");

		lh.addStaticHoliday ("30-JUN-2020", "Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2020", "Agostinas Holiday");

		lh.addStaticHoliday ("06-AUG-2020", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-2020", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2020", "All Souls Day");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("30-DEC-2020", "Bank Holiday");

		lh.addStaticHoliday ("31-DEC-2020", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("31-MAR-2021", "Holy Wednesday");

		lh.addStaticHoliday ("01-APR-2021", "Holy Thursday");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("30-JUN-2021", "Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2021", "Agostinas Holiday");

		lh.addStaticHoliday ("06-AUG-2021", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-2021", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2021", "All Souls Day");

		lh.addStaticHoliday ("30-DEC-2021", "Bank Holiday");

		lh.addStaticHoliday ("31-DEC-2021", "Bank Holiday");

		lh.addStaticHoliday ("13-APR-2022", "Holy Wednesday");

		lh.addStaticHoliday ("14-APR-2022", "Holy Thursday");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("30-JUN-2022", "Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2022", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-2022", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2022", "All Souls Day");

		lh.addStaticHoliday ("30-DEC-2022", "Bank Holiday");

		lh.addStaticHoliday ("05-APR-2023", "Holy Wednesday");

		lh.addStaticHoliday ("06-APR-2023", "Holy Thursday");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2023", "Labor Day");

		lh.addStaticHoliday ("30-JUN-2023", "Bank Holiday");

		lh.addStaticHoliday ("15-SEP-2023", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2023", "All Souls Day");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("27-MAR-2024", "Holy Wednesday");

		lh.addStaticHoliday ("28-MAR-2024", "Holy Thursday");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2024", "Labor Day");

		lh.addStaticHoliday ("05-AUG-2024", "Agostinas Holiday");

		lh.addStaticHoliday ("06-AUG-2024", "Agostinas Holiday");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("30-DEC-2024", "Bank Holiday");

		lh.addStaticHoliday ("31-DEC-2024", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("16-APR-2025", "Holy Wednesday");

		lh.addStaticHoliday ("17-APR-2025", "Holy Thursday");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2025", "Labor Day");

		lh.addStaticHoliday ("30-JUN-2025", "Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2025", "Agostinas Holiday");

		lh.addStaticHoliday ("06-AUG-2025", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-2025", "Independence Day");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("30-DEC-2025", "Bank Holiday");

		lh.addStaticHoliday ("31-DEC-2025", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("01-APR-2026", "Holy Wednesday");

		lh.addStaticHoliday ("02-APR-2026", "Holy Thursday");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2026", "Labor Day");

		lh.addStaticHoliday ("30-JUN-2026", "Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2026", "Agostinas Holiday");

		lh.addStaticHoliday ("06-AUG-2026", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-2026", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2026", "All Souls Day");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("30-DEC-2026", "Bank Holiday");

		lh.addStaticHoliday ("31-DEC-2026", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2027", "Holy Wednesday");

		lh.addStaticHoliday ("25-MAR-2027", "Holy Thursday");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("30-JUN-2027", "Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2027", "Agostinas Holiday");

		lh.addStaticHoliday ("06-AUG-2027", "Agostinas Holiday");

		lh.addStaticHoliday ("15-SEP-2027", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2027", "All Souls Day");

		lh.addStaticHoliday ("30-DEC-2027", "Bank Holiday");

		lh.addStaticHoliday ("31-DEC-2027", "Bank Holiday");

		lh.addStaticHoliday ("12-APR-2028", "Holy Wednesday");

		lh.addStaticHoliday ("13-APR-2028", "Holy Thursday");

		lh.addStaticHoliday ("14-APR-2028", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2028", "Labor Day");

		lh.addStaticHoliday ("30-JUN-2028", "Bank Holiday");

		lh.addStaticHoliday ("15-SEP-2028", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2028", "All Souls Day");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStandardWeekend();

		return lh;
	}
}
