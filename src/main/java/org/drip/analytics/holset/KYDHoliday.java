
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

public class KYDHoliday implements org.drip.analytics.holset.LocationHoliday {
	public KYDHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "KYD";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("25-FEB-1998", "Ash Wednesday");

		lh.addStaticHoliday ("10-APR-1998", "Good Friday");

		lh.addStaticHoliday ("13-APR-1998", "Easter Monday");

		lh.addStaticHoliday ("18-MAY-1998", "Discovery Day");

		lh.addStaticHoliday ("15-JUN-1998", "Queens Birthday");

		lh.addStaticHoliday ("06-JUL-1998", "Constitution Day");

		lh.addStaticHoliday ("09-NOV-1998", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-1998", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-1998", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("17-FEB-1999", "Ash Wednesday");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("05-APR-1999", "Easter Monday");

		lh.addStaticHoliday ("17-MAY-1999", "Discovery Day");

		lh.addStaticHoliday ("14-JUN-1999", "Queens Birthday");

		lh.addStaticHoliday ("05-JUL-1999", "Constitution Day");

		lh.addStaticHoliday ("15-NOV-1999", "Remembrance Day");

		lh.addStaticHoliday ("27-DEC-1999", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-1999", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2000", "New Years Day Observed");

		lh.addStaticHoliday ("08-MAR-2000", "Ash Wednesday");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("24-APR-2000", "Easter Monday");

		lh.addStaticHoliday ("15-MAY-2000", "Discovery Day");

		lh.addStaticHoliday ("12-JUN-2000", "Queens Birthday");

		lh.addStaticHoliday ("03-JUL-2000", "Constitution Day");

		lh.addStaticHoliday ("13-NOV-2000", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2000", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("28-FEB-2001", "Ash Wednesday");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("16-APR-2001", "Easter Monday");

		lh.addStaticHoliday ("21-MAY-2001", "Discovery Day");

		lh.addStaticHoliday ("11-JUN-2001", "Queens Birthday");

		lh.addStaticHoliday ("02-JUL-2001", "Constitution Day");

		lh.addStaticHoliday ("12-NOV-2001", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2001", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("13-FEB-2002", "Ash Wednesday");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("01-APR-2002", "Easter Monday");

		lh.addStaticHoliday ("20-MAY-2002", "Discovery Day");

		lh.addStaticHoliday ("10-JUN-2002", "Queens Birthday");

		lh.addStaticHoliday ("01-JUL-2002", "Constitution Day");

		lh.addStaticHoliday ("11-NOV-2002", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2002", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("05-MAR-2003", "Ash Wednesday");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("21-APR-2003", "Easter Monday");

		lh.addStaticHoliday ("19-MAY-2003", "Discovery Day");

		lh.addStaticHoliday ("16-JUN-2003", "Queens Birthday");

		lh.addStaticHoliday ("07-JUL-2003", "Constitution Day");

		lh.addStaticHoliday ("10-NOV-2003", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2003", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("25-FEB-2004", "Ash Wednesday");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("12-APR-2004", "Easter Monday");

		lh.addStaticHoliday ("17-MAY-2004", "Discovery Day");

		lh.addStaticHoliday ("14-JUN-2004", "Queens Birthday");

		lh.addStaticHoliday ("05-JUL-2004", "Constitution Day");

		lh.addStaticHoliday ("15-NOV-2004", "Remembrance Day");

		lh.addStaticHoliday ("27-DEC-2004", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2004", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2005", "New Years Day Observed");

		lh.addStaticHoliday ("09-FEB-2005", "Ash Wednesday");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("28-MAR-2005", "Easter Monday");

		lh.addStaticHoliday ("16-MAY-2005", "Discovery Day");

		lh.addStaticHoliday ("13-JUN-2005", "Queens Birthday");

		lh.addStaticHoliday ("04-JUL-2005", "Constitution Day");

		lh.addStaticHoliday ("14-NOV-2005", "Remembrance Day");

		lh.addStaticHoliday ("26-DEC-2005", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2005", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2006", "New Years Day Observed");

		lh.addStaticHoliday ("01-MAR-2006", "Ash Wednesday");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("17-APR-2006", "Easter Monday");

		lh.addStaticHoliday ("15-MAY-2006", "Discovery Day");

		lh.addStaticHoliday ("12-JUN-2006", "Queens Birthday");

		lh.addStaticHoliday ("03-JUL-2006", "Constitution Day");

		lh.addStaticHoliday ("13-NOV-2006", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2006", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("21-FEB-2007", "Ash Wednesday");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("09-APR-2007", "Easter Monday");

		lh.addStaticHoliday ("21-MAY-2007", "Discovery Day");

		lh.addStaticHoliday ("11-JUN-2007", "Queens Birthday");

		lh.addStaticHoliday ("02-JUL-2007", "Constitution Day");

		lh.addStaticHoliday ("12-NOV-2007", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2007", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("06-FEB-2008", "Ash Wednesday");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("24-MAR-2008", "Easter Monday");

		lh.addStaticHoliday ("19-MAY-2008", "Discovery Day");

		lh.addStaticHoliday ("16-JUN-2008", "Queens Birthday");

		lh.addStaticHoliday ("07-JUL-2008", "Constitution Day");

		lh.addStaticHoliday ("10-NOV-2008", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2008", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("25-FEB-2009", "Ash Wednesday");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("13-APR-2009", "Easter Monday");

		lh.addStaticHoliday ("18-MAY-2009", "Discovery Day");

		lh.addStaticHoliday ("15-JUN-2009", "Queens Birthday");

		lh.addStaticHoliday ("06-JUL-2009", "Constitution Day");

		lh.addStaticHoliday ("09-NOV-2009", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2009", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("17-FEB-2010", "Ash Wednesday");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("05-APR-2010", "Easter Monday");

		lh.addStaticHoliday ("17-MAY-2010", "Discovery Day");

		lh.addStaticHoliday ("14-JUN-2010", "Queens Birthday");

		lh.addStaticHoliday ("05-JUL-2010", "Constitution Day");

		lh.addStaticHoliday ("15-NOV-2010", "Remembrance Day");

		lh.addStaticHoliday ("27-DEC-2010", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2010", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2011", "New Years Day Observed");

		lh.addStaticHoliday ("09-MAR-2011", "Ash Wednesday");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("25-APR-2011", "Easter Monday");

		lh.addStaticHoliday ("16-MAY-2011", "Discovery Day");

		lh.addStaticHoliday ("13-JUN-2011", "Queens Birthday");

		lh.addStaticHoliday ("04-JUL-2011", "Constitution Day");

		lh.addStaticHoliday ("14-NOV-2011", "Remembrance Day");

		lh.addStaticHoliday ("26-DEC-2011", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2011", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2012", "New Years Day Observed");

		lh.addStaticHoliday ("22-FEB-2012", "Ash Wednesday");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("09-APR-2012", "Easter Monday");

		lh.addStaticHoliday ("21-MAY-2012", "Discovery Day");

		lh.addStaticHoliday ("11-JUN-2012", "Queens Birthday");

		lh.addStaticHoliday ("02-JUL-2012", "Constitution Day");

		lh.addStaticHoliday ("12-NOV-2012", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2012", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("13-FEB-2013", "Ash Wednesday");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("01-APR-2013", "Easter Monday");

		lh.addStaticHoliday ("20-MAY-2013", "Discovery Day");

		lh.addStaticHoliday ("10-JUN-2013", "Queens Birthday");

		lh.addStaticHoliday ("01-JUL-2013", "Constitution Day");

		lh.addStaticHoliday ("11-NOV-2013", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2013", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("05-MAR-2014", "Ash Wednesday");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("21-APR-2014", "Easter Monday");

		lh.addStaticHoliday ("19-MAY-2014", "Discovery Day");

		lh.addStaticHoliday ("16-JUN-2014", "Queens Birthday");

		lh.addStaticHoliday ("07-JUL-2014", "Constitution Day");

		lh.addStaticHoliday ("10-NOV-2014", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2014", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("18-FEB-2015", "Ash Wednesday");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("06-APR-2015", "Easter Monday");

		lh.addStaticHoliday ("18-MAY-2015", "Discovery Day");

		lh.addStaticHoliday ("15-JUN-2015", "Queens Birthday");

		lh.addStaticHoliday ("06-JUL-2015", "Constitution Day");

		lh.addStaticHoliday ("09-NOV-2015", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2015", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("10-FEB-2016", "Ash Wednesday");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("28-MAR-2016", "Easter Monday");

		lh.addStaticHoliday ("16-MAY-2016", "Discovery Day");

		lh.addStaticHoliday ("13-JUN-2016", "Queens Birthday");

		lh.addStaticHoliday ("04-JUL-2016", "Constitution Day");

		lh.addStaticHoliday ("14-NOV-2016", "Remembrance Day");

		lh.addStaticHoliday ("26-DEC-2016", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2016", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2017", "New Years Day Observed");

		lh.addStaticHoliday ("01-MAR-2017", "Ash Wednesday");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("17-APR-2017", "Easter Monday");

		lh.addStaticHoliday ("15-MAY-2017", "Discovery Day");

		lh.addStaticHoliday ("12-JUN-2017", "Queens Birthday");

		lh.addStaticHoliday ("03-JUL-2017", "Constitution Day");

		lh.addStaticHoliday ("13-NOV-2017", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2017", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("14-FEB-2018", "Ash Wednesday");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("02-APR-2018", "Easter Monday");

		lh.addStaticHoliday ("21-MAY-2018", "Discovery Day");

		lh.addStaticHoliday ("11-JUN-2018", "Queens Birthday");

		lh.addStaticHoliday ("02-JUL-2018", "Constitution Day");

		lh.addStaticHoliday ("12-NOV-2018", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2018", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("06-MAR-2019", "Ash Wednesday");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("22-APR-2019", "Easter Monday");

		lh.addStaticHoliday ("20-MAY-2019", "Discovery Day");

		lh.addStaticHoliday ("10-JUN-2019", "Queens Birthday");

		lh.addStaticHoliday ("01-JUL-2019", "Constitution Day");

		lh.addStaticHoliday ("11-NOV-2019", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2019", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("26-FEB-2020", "Ash Wednesday");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("13-APR-2020", "Easter Monday");

		lh.addStaticHoliday ("18-MAY-2020", "Discovery Day");

		lh.addStaticHoliday ("15-JUN-2020", "Queens Birthday");

		lh.addStaticHoliday ("06-JUL-2020", "Constitution Day");

		lh.addStaticHoliday ("09-NOV-2020", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2020", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("17-FEB-2021", "Ash Wednesday");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("05-APR-2021", "Easter Monday");

		lh.addStaticHoliday ("17-MAY-2021", "Discovery Day");

		lh.addStaticHoliday ("14-JUN-2021", "Queens Birthday");

		lh.addStaticHoliday ("05-JUL-2021", "Constitution Day");

		lh.addStaticHoliday ("15-NOV-2021", "Remembrance Day");

		lh.addStaticHoliday ("27-DEC-2021", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2021", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2022", "New Years Day Observed");

		lh.addStaticHoliday ("02-MAR-2022", "Ash Wednesday");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("18-APR-2022", "Easter Monday");

		lh.addStaticHoliday ("16-MAY-2022", "Discovery Day");

		lh.addStaticHoliday ("13-JUN-2022", "Queens Birthday");

		lh.addStaticHoliday ("04-JUL-2022", "Constitution Day");

		lh.addStaticHoliday ("14-NOV-2022", "Remembrance Day");

		lh.addStaticHoliday ("26-DEC-2022", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2022", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2023", "New Years Day Observed");

		lh.addStaticHoliday ("22-FEB-2023", "Ash Wednesday");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("10-APR-2023", "Easter Monday");

		lh.addStaticHoliday ("15-MAY-2023", "Discovery Day");

		lh.addStaticHoliday ("12-JUN-2023", "Queens Birthday");

		lh.addStaticHoliday ("03-JUL-2023", "Constitution Day");

		lh.addStaticHoliday ("13-NOV-2023", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2023", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("14-FEB-2024", "Ash Wednesday");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("01-APR-2024", "Easter Monday");

		lh.addStaticHoliday ("20-MAY-2024", "Discovery Day");

		lh.addStaticHoliday ("10-JUN-2024", "Queens Birthday");

		lh.addStaticHoliday ("01-JUL-2024", "Constitution Day");

		lh.addStaticHoliday ("11-NOV-2024", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2024", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("05-MAR-2025", "Ash Wednesday");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("21-APR-2025", "Easter Monday");

		lh.addStaticHoliday ("19-MAY-2025", "Discovery Day");

		lh.addStaticHoliday ("16-JUN-2025", "Queens Birthday");

		lh.addStaticHoliday ("07-JUL-2025", "Constitution Day");

		lh.addStaticHoliday ("10-NOV-2025", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2025", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("18-FEB-2026", "Ash Wednesday");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("06-APR-2026", "Easter Monday");

		lh.addStaticHoliday ("18-MAY-2026", "Discovery Day");

		lh.addStaticHoliday ("15-JUN-2026", "Queens Birthday");

		lh.addStaticHoliday ("06-JUL-2026", "Constitution Day");

		lh.addStaticHoliday ("09-NOV-2026", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2026", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("10-FEB-2027", "Ash Wednesday");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("29-MAR-2027", "Easter Monday");

		lh.addStaticHoliday ("17-MAY-2027", "Discovery Day");

		lh.addStaticHoliday ("14-JUN-2027", "Queens Birthday");

		lh.addStaticHoliday ("05-JUL-2027", "Constitution Day");

		lh.addStaticHoliday ("15-NOV-2027", "Remembrance Day");

		lh.addStaticHoliday ("27-DEC-2027", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2027", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2028", "New Years Day Observed");

		lh.addStaticHoliday ("01-MAR-2028", "Ash Wednesday");

		lh.addStaticHoliday ("14-APR-2028", "Good Friday");

		lh.addStaticHoliday ("17-APR-2028", "Easter Monday");

		lh.addStaticHoliday ("15-MAY-2028", "Discovery Day");

		lh.addStaticHoliday ("12-JUN-2028", "Queens Birthday");

		lh.addStaticHoliday ("03-JUL-2028", "Constitution Day");

		lh.addStaticHoliday ("13-NOV-2028", "Remembrance Day");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2028", "Boxing Day");

		lh.addStandardWeekend();

		return lh;
	}
}
