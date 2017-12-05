
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

public class BMDHoliday implements org.drip.analytics.holset.LocationHoliday {
	public BMDHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "BMD";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("10-APR-1998", "Good Friday");

		lh.addStaticHoliday ("25-MAY-1998", "Bermuda Day Observed");

		lh.addStaticHoliday ("15-JUN-1998", "Queens Birthday");

		lh.addStaticHoliday ("30-JUL-1998", "Cup Match Day");

		lh.addStaticHoliday ("31-JUL-1998", "Somers Day");

		lh.addStaticHoliday ("07-SEP-1998", "Labour Day");

		lh.addStaticHoliday ("11-NOV-1998", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-1998", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-1998", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("24-MAY-1999", "Bermuda Day");

		lh.addStaticHoliday ("21-JUN-1999", "Queens Birthday");

		lh.addStaticHoliday ("29-JUL-1999", "Cup Match Day");

		lh.addStaticHoliday ("30-JUL-1999", "Somers Day");

		lh.addStaticHoliday ("06-SEP-1999", "Labour Day");

		lh.addStaticHoliday ("11-NOV-1999", "Remembrance Day");

		lh.addStaticHoliday ("27-DEC-1999", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-1999", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2000", "New Years Day Observed");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2000", "Bermuda Day");

		lh.addStaticHoliday ("19-JUN-2000", "Queens Birthday");

		lh.addStaticHoliday ("03-AUG-2000", "Cup Match Day");

		lh.addStaticHoliday ("04-AUG-2000", "Somers Day");

		lh.addStaticHoliday ("04-SEP-2000", "Labour Day");

		lh.addStaticHoliday ("13-NOV-2000", "Remembrance Day Observed");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2000", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2001", "Bermuda Day");

		lh.addStaticHoliday ("18-JUN-2001", "Queens Birthday");

		lh.addStaticHoliday ("02-AUG-2001", "Cup Match Day");

		lh.addStaticHoliday ("03-AUG-2001", "Somers Day");

		lh.addStaticHoliday ("03-SEP-2001", "Labour Day");

		lh.addStaticHoliday ("12-NOV-2001", "Remembrance Day Observed");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2001", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2002", "Bermuda Day");

		lh.addStaticHoliday ("17-JUN-2002", "Queens Birthday");

		lh.addStaticHoliday ("01-AUG-2002", "Cup Match Day");

		lh.addStaticHoliday ("02-AUG-2002", "Somers Day");

		lh.addStaticHoliday ("02-SEP-2002", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2002", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2002", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("26-MAY-2003", "Bermuda Day Observed");

		lh.addStaticHoliday ("16-JUN-2003", "Queens Birthday");

		lh.addStaticHoliday ("31-JUL-2003", "Cup Match Day");

		lh.addStaticHoliday ("01-AUG-2003", "Somers Day");

		lh.addStaticHoliday ("01-SEP-2003", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2003", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2003", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2004", "Bermuda Day");

		lh.addStaticHoliday ("21-JUN-2004", "Queens Birthday");

		lh.addStaticHoliday ("29-JUL-2004", "Cup Match Day");

		lh.addStaticHoliday ("30-JUL-2004", "Somers Day");

		lh.addStaticHoliday ("06-SEP-2004", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2004", "Remembrance Day");

		lh.addStaticHoliday ("27-DEC-2004", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2004", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2005", "New Years Day Observed");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2005", "Bermuda Day");

		lh.addStaticHoliday ("20-JUN-2005", "Queens Birthday");

		lh.addStaticHoliday ("28-JUL-2005", "Cup Match Day");

		lh.addStaticHoliday ("29-JUL-2005", "Somers Day");

		lh.addStaticHoliday ("05-SEP-2005", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2005", "Remembrance Day");

		lh.addStaticHoliday ("26-DEC-2005", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2005", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2006", "New Years Day Observed");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2006", "Bermuda Day");

		lh.addStaticHoliday ("19-JUN-2006", "Queens Birthday");

		lh.addStaticHoliday ("03-AUG-2006", "Cup Match Day");

		lh.addStaticHoliday ("04-AUG-2006", "Somers Day");

		lh.addStaticHoliday ("04-SEP-2006", "Labour Day");

		lh.addStaticHoliday ("13-NOV-2006", "Remembrance Day Observed");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2006", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2007", "Bermuda Day");

		lh.addStaticHoliday ("18-JUN-2007", "Queens Birthday");

		lh.addStaticHoliday ("02-AUG-2007", "Cup Match Day");

		lh.addStaticHoliday ("03-AUG-2007", "Somers Day");

		lh.addStaticHoliday ("03-SEP-2007", "Labour Day");

		lh.addStaticHoliday ("12-NOV-2007", "Remembrance Day Observed");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2007", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("26-MAY-2008", "Bermuda Day Observed");

		lh.addStaticHoliday ("16-JUN-2008", "Queens Birthday");

		lh.addStaticHoliday ("31-JUL-2008", "Cup Match Day");

		lh.addStaticHoliday ("01-AUG-2008", "Somers Day");

		lh.addStaticHoliday ("01-SEP-2008", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2008", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2008", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("25-MAY-2009", "Bermuda Day Observed");

		lh.addStaticHoliday ("15-JUN-2009", "Queens Birthday");

		lh.addStaticHoliday ("30-JUL-2009", "Cup Match Day");

		lh.addStaticHoliday ("31-JUL-2009", "Somers Day");

		lh.addStaticHoliday ("07-SEP-2009", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2009", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2009", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2010", "Bermuda Day");

		lh.addStaticHoliday ("21-JUN-2010", "Queens Birthday");

		lh.addStaticHoliday ("29-JUL-2010", "Cup Match Day");

		lh.addStaticHoliday ("30-JUL-2010", "Somers Day");

		lh.addStaticHoliday ("06-SEP-2010", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2010", "Remembrance Day");

		lh.addStaticHoliday ("27-DEC-2010", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2010", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2011", "New Years Day Observed");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2011", "Bermuda Day");

		lh.addStaticHoliday ("20-JUN-2011", "Queens Birthday");

		lh.addStaticHoliday ("28-JUL-2011", "Cup Match Day");

		lh.addStaticHoliday ("29-JUL-2011", "Somers Day");

		lh.addStaticHoliday ("05-SEP-2011", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2011", "Remembrance Day");

		lh.addStaticHoliday ("26-DEC-2011", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2011", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2012", "New Years Day Observed");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2012", "Bermuda Day");

		lh.addStaticHoliday ("18-JUN-2012", "Queens Birthday");

		lh.addStaticHoliday ("02-AUG-2012", "Cup Match Day");

		lh.addStaticHoliday ("03-AUG-2012", "Somers Day");

		lh.addStaticHoliday ("03-SEP-2012", "Labour Day");

		lh.addStaticHoliday ("12-NOV-2012", "Remembrance Day Observed");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2012", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2013", "Bermuda Day");

		lh.addStaticHoliday ("17-JUN-2013", "Queens Birthday");

		lh.addStaticHoliday ("01-AUG-2013", "Cup Match Day");

		lh.addStaticHoliday ("02-AUG-2013", "Somers Day");

		lh.addStaticHoliday ("02-SEP-2013", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2013", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2013", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("26-MAY-2014", "Bermuda Day Observed");

		lh.addStaticHoliday ("16-JUN-2014", "Queens Birthday");

		lh.addStaticHoliday ("31-JUL-2014", "Cup Match Day");

		lh.addStaticHoliday ("01-AUG-2014", "Somers Day");

		lh.addStaticHoliday ("01-SEP-2014", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2014", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2014", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("25-MAY-2015", "Bermuda Day Observed");

		lh.addStaticHoliday ("15-JUN-2015", "Queens Birthday");

		lh.addStaticHoliday ("30-JUL-2015", "Cup Match Day");

		lh.addStaticHoliday ("31-JUL-2015", "Somers Day");

		lh.addStaticHoliday ("07-SEP-2015", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2015", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2015", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2016", "Bermuda Day");

		lh.addStaticHoliday ("20-JUN-2016", "Queens Birthday");

		lh.addStaticHoliday ("28-JUL-2016", "Cup Match Day");

		lh.addStaticHoliday ("29-JUL-2016", "Somers Day");

		lh.addStaticHoliday ("05-SEP-2016", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2016", "Remembrance Day");

		lh.addStaticHoliday ("26-DEC-2016", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2016", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2017", "New Years Day Observed");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2017", "Bermuda Day");

		lh.addStaticHoliday ("19-JUN-2017", "Queens Birthday");

		lh.addStaticHoliday ("03-AUG-2017", "Cup Match Day");

		lh.addStaticHoliday ("04-AUG-2017", "Somers Day");

		lh.addStaticHoliday ("04-SEP-2017", "Labour Day");

		lh.addStaticHoliday ("13-NOV-2017", "Remembrance Day Observed");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2017", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2018", "Bermuda Day");

		lh.addStaticHoliday ("18-JUN-2018", "Queens Birthday");

		lh.addStaticHoliday ("02-AUG-2018", "Cup Match Day");

		lh.addStaticHoliday ("03-AUG-2018", "Somers Day");

		lh.addStaticHoliday ("03-SEP-2018", "Labour Day");

		lh.addStaticHoliday ("12-NOV-2018", "Remembrance Day Observed");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2018", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2019", "Bermuda Day");

		lh.addStaticHoliday ("17-JUN-2019", "Queens Birthday");

		lh.addStaticHoliday ("01-AUG-2019", "Cup Match Day");

		lh.addStaticHoliday ("02-AUG-2019", "Somers Day");

		lh.addStaticHoliday ("02-SEP-2019", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2019", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2019", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("25-MAY-2020", "Bermuda Day Observed");

		lh.addStaticHoliday ("15-JUN-2020", "Queens Birthday");

		lh.addStaticHoliday ("30-JUL-2020", "Cup Match Day");

		lh.addStaticHoliday ("31-JUL-2020", "Somers Day");

		lh.addStaticHoliday ("07-SEP-2020", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2020", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2020", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2021", "Bermuda Day");

		lh.addStaticHoliday ("21-JUN-2021", "Queens Birthday");

		lh.addStaticHoliday ("29-JUL-2021", "Cup Match Day");

		lh.addStaticHoliday ("30-JUL-2021", "Somers Day");

		lh.addStaticHoliday ("06-SEP-2021", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2021", "Remembrance Day");

		lh.addStaticHoliday ("27-DEC-2021", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2021", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2022", "New Years Day Observed");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2022", "Bermuda Day");

		lh.addStaticHoliday ("20-JUN-2022", "Queens Birthday");

		lh.addStaticHoliday ("28-JUL-2022", "Cup Match Day");

		lh.addStaticHoliday ("29-JUL-2022", "Somers Day");

		lh.addStaticHoliday ("05-SEP-2022", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2022", "Remembrance Day");

		lh.addStaticHoliday ("26-DEC-2022", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2022", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2023", "New Years Day Observed");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2023", "Bermuda Day");

		lh.addStaticHoliday ("19-JUN-2023", "Queens Birthday");

		lh.addStaticHoliday ("03-AUG-2023", "Cup Match Day");

		lh.addStaticHoliday ("04-AUG-2023", "Somers Day");

		lh.addStaticHoliday ("04-SEP-2023", "Labour Day");

		lh.addStaticHoliday ("13-NOV-2023", "Remembrance Day Observed");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2023", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2024", "Bermuda Day");

		lh.addStaticHoliday ("17-JUN-2024", "Queens Birthday");

		lh.addStaticHoliday ("01-AUG-2024", "Cup Match Day");

		lh.addStaticHoliday ("02-AUG-2024", "Somers Day");

		lh.addStaticHoliday ("02-SEP-2024", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2024", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2024", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("26-MAY-2025", "Bermuda Day Observed");

		lh.addStaticHoliday ("16-JUN-2025", "Queens Birthday");

		lh.addStaticHoliday ("31-JUL-2025", "Cup Match Day");

		lh.addStaticHoliday ("01-AUG-2025", "Somers Day");

		lh.addStaticHoliday ("01-SEP-2025", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2025", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2025", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("25-MAY-2026", "Bermuda Day Observed");

		lh.addStaticHoliday ("15-JUN-2026", "Queens Birthday");

		lh.addStaticHoliday ("30-JUL-2026", "Cup Match Day");

		lh.addStaticHoliday ("31-JUL-2026", "Somers Day");

		lh.addStaticHoliday ("07-SEP-2026", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2026", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2026", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2027", "Bermuda Day");

		lh.addStaticHoliday ("21-JUN-2027", "Queens Birthday");

		lh.addStaticHoliday ("29-JUL-2027", "Cup Match Day");

		lh.addStaticHoliday ("30-JUL-2027", "Somers Day");

		lh.addStaticHoliday ("06-SEP-2027", "Labour Day");

		lh.addStaticHoliday ("11-NOV-2027", "Remembrance Day");

		lh.addStaticHoliday ("27-DEC-2027", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2027", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2028", "New Years Day Observed");

		lh.addStaticHoliday ("14-APR-2028", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2028", "Bermuda Day");

		lh.addStaticHoliday ("19-JUN-2028", "Queens Birthday");

		lh.addStaticHoliday ("03-AUG-2028", "Cup Match Day");

		lh.addStaticHoliday ("04-AUG-2028", "Somers Day");

		lh.addStaticHoliday ("04-SEP-2028", "Labour Day");

		lh.addStaticHoliday ("13-NOV-2028", "Remembrance Day Observed");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2028", "Boxing Day");

		lh.addStandardWeekend();

		return lh;
	}
}
