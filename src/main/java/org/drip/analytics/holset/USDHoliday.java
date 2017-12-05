
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

public class USDHoliday implements org.drip.analytics.holset.LocationHoliday {
	public USDHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "USD";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("03-JAN-1994", "...from ChaseTools");

		lh.addStaticHoliday ("02-JAN-1995", "...from ChaseTools");

		lh.addStaticHoliday ("29-MAY-1995", "...from ChaseTools");

		lh.addStaticHoliday ("04-JUL-1995", "...from ChaseTools");

		lh.addStaticHoliday ("04-SEP-1995", "...from ChaseTools");

		lh.addStaticHoliday ("09-OCT-1995", "...from ChaseTools");

		lh.addStaticHoliday ("10-NOV-1995", "...from ChaseTools");

		lh.addStaticHoliday ("23-NOV-1995", "...from ChaseTools");

		lh.addStaticHoliday ("25-DEC-1995", "...from ChaseTools");

		lh.addStaticHoliday ("01-JAN-1996", "...from ChaseTools");

		lh.addStaticHoliday ("15-JAN-1996", "...from ChaseTools");

		lh.addStaticHoliday ("19-FEB-1996", "...from ChaseTools");

		lh.addStaticHoliday ("27-MAY-1996", "...from ChaseTools");

		lh.addStaticHoliday ("04-JUL-1996", "...from ChaseTools");

		lh.addStaticHoliday ("02-SEP-1996", "...from ChaseTools");

		lh.addStaticHoliday ("14-OCT-1996", "...from ChaseTools");

		lh.addStaticHoliday ("11-NOV-1996", "...from ChaseTools");

		lh.addStaticHoliday ("28-NOV-1996", "...from ChaseTools");

		lh.addStaticHoliday ("25-DEC-1996", "...from ChaseTools");

		lh.addStaticHoliday ("01-JAN-1997", "...from ChaseTools");

		lh.addStaticHoliday ("20-JAN-1997", "...from ChaseTools");

		lh.addStaticHoliday ("17-FEB-1997", "...from ChaseTools");

		lh.addStaticHoliday ("26-MAY-1997", "...from ChaseTools");

		lh.addStaticHoliday ("04-JUL-1997", "...from ChaseTools");

		lh.addStaticHoliday ("01-SEP-1997", "...from ChaseTools");

		lh.addStaticHoliday ("13-OCT-1997", "...from ChaseTools");

		lh.addStaticHoliday ("11-NOV-1997", "...from ChaseTools");

		lh.addStaticHoliday ("27-NOV-1997", "...from ChaseTools");

		lh.addStaticHoliday ("25-DEC-1997", "...from ChaseTools");

		lh.addStaticHoliday ("01-JAN-1998", "...from ChaseTools");

		lh.addStaticHoliday ("19-JAN-1998", "...from ChaseTools");

		lh.addStaticHoliday ("16-FEB-1998", "...from ChaseTools");

		lh.addStaticHoliday ("25-MAY-1998", "...from ChaseTools");

		lh.addStaticHoliday ("07-SEP-1998", "...from ChaseTools");

		lh.addStaticHoliday ("12-OCT-1998", "...from ChaseTools");

		lh.addStaticHoliday ("11-NOV-1998", "...from ChaseTools");

		lh.addStaticHoliday ("26-NOV-1998", "...from ChaseTools");

		lh.addStaticHoliday ("25-DEC-1998", "...from ChaseTools");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("18-JAN-1999", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("15-FEB-1999", "Washingtons Birthday");

		lh.addStaticHoliday ("31-MAY-1999", "Memorial Day");

		lh.addStaticHoliday ("05-JUL-1999", "Independence Day Observed");

		lh.addStaticHoliday ("06-SEP-1999", "Labor Day");

		lh.addStaticHoliday ("11-OCT-1999", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-1999", "Veterans Day");

		lh.addStaticHoliday ("25-NOV-1999", "Thanksgiving Day");

		lh.addStaticHoliday ("17-JAN-2000", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("21-FEB-2000", "Washingtons Birthday");

		lh.addStaticHoliday ("29-MAY-2000", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2000", "Independence Day");

		lh.addStaticHoliday ("04-SEP-2000", "Labor Day");

		lh.addStaticHoliday ("09-OCT-2000", "Columbus Day");

		lh.addStaticHoliday ("23-NOV-2000", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("15-JAN-2001", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("19-FEB-2001", "Washingtons Birthday");

		lh.addStaticHoliday ("28-MAY-2001", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2001", "Independence Day");

		lh.addStaticHoliday ("03-SEP-2001", "Labor Day");

		lh.addStaticHoliday ("08-OCT-2001", "Columbus Day");

		lh.addStaticHoliday ("12-NOV-2001", "Veterans Day Observed");

		lh.addStaticHoliday ("22-NOV-2001", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2002", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("18-FEB-2002", "Washingtons Birthday");

		lh.addStaticHoliday ("27-MAY-2002", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2002", "Independence Day");

		lh.addStaticHoliday ("02-SEP-2002", "Labor Day");

		lh.addStaticHoliday ("14-OCT-2002", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2002", "Veterans Day");

		lh.addStaticHoliday ("28-NOV-2002", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("20-JAN-2003", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("17-FEB-2003", "Washingtons Birthday");

		lh.addStaticHoliday ("26-MAY-2003", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2003", "Independence Day");

		lh.addStaticHoliday ("01-SEP-2003", "Labor Day");

		lh.addStaticHoliday ("13-OCT-2003", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2003", "Veterans Day");

		lh.addStaticHoliday ("27-NOV-2003", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("19-JAN-2004", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("16-FEB-2004", "Washingtons Birthday");

		lh.addStaticHoliday ("31-MAY-2004", "Memorial Day");

		lh.addStaticHoliday ("05-JUL-2004", "Independence Day Observed");

		lh.addStaticHoliday ("06-SEP-2004", "Labor Day");

		lh.addStaticHoliday ("11-OCT-2004", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2004", "Veterans Day");

		lh.addStaticHoliday ("25-NOV-2004", "Thanksgiving Day");

		lh.addStaticHoliday ("17-JAN-2005", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("21-FEB-2005", "Washingtons Birthday");

		lh.addStaticHoliday ("30-MAY-2005", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2005", "Independence Day");

		lh.addStaticHoliday ("05-SEP-2005", "Labor Day");

		lh.addStaticHoliday ("10-OCT-2005", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2005", "Veterans Day");

		lh.addStaticHoliday ("24-NOV-2005", "Thanksgiving Day");

		lh.addStaticHoliday ("26-DEC-2005", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2006", "New Years Day Observed");

		lh.addStaticHoliday ("16-JAN-2006", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("20-FEB-2006", "Washingtons Birthday");

		lh.addStaticHoliday ("29-MAY-2006", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2006", "Independence Day");

		lh.addStaticHoliday ("04-SEP-2006", "Labor Day");

		lh.addStaticHoliday ("09-OCT-2006", "Columbus Day");

		lh.addStaticHoliday ("23-NOV-2006", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("15-JAN-2007", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("19-FEB-2007", "Washingtons Birthday");

		lh.addStaticHoliday ("28-MAY-2007", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2007", "Independence Day");

		lh.addStaticHoliday ("03-SEP-2007", "Labor Day");

		lh.addStaticHoliday ("08-OCT-2007", "Columbus Day");

		lh.addStaticHoliday ("12-NOV-2007", "Veterans Day Observed");

		lh.addStaticHoliday ("22-NOV-2007", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2008", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("18-FEB-2008", "Washingtons Birthday");

		lh.addStaticHoliday ("26-MAY-2008", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2008", "Independence Day");

		lh.addStaticHoliday ("01-SEP-2008", "Labor Day");

		lh.addStaticHoliday ("13-OCT-2008", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2008", "Veterans Day");

		lh.addStaticHoliday ("27-NOV-2008", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("19-JAN-2009", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("16-FEB-2009", "Washingtons Birthday");

		lh.addStaticHoliday ("25-MAY-2009", "Memorial Day");

		lh.addStaticHoliday ("07-SEP-2009", "Labor Day");

		lh.addStaticHoliday ("12-OCT-2009", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2009", "Veterans Day");

		lh.addStaticHoliday ("26-NOV-2009", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("18-JAN-2010", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("15-FEB-2010", "Washingtons Birthday");

		lh.addStaticHoliday ("31-MAY-2010", "Memorial Day");

		lh.addStaticHoliday ("05-JUL-2010", "Independence Day Observed");

		lh.addStaticHoliday ("06-SEP-2010", "Labor Day");

		lh.addStaticHoliday ("11-OCT-2010", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2010", "Veterans Day");

		lh.addStaticHoliday ("25-NOV-2010", "Thanksgiving Day");

		lh.addStaticHoliday ("17-JAN-2011", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("21-FEB-2011", "Washingtons Birthday");

		lh.addStaticHoliday ("30-MAY-2011", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2011", "Independence Day");

		lh.addStaticHoliday ("05-SEP-2011", "Labor Day");

		lh.addStaticHoliday ("10-OCT-2011", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2011", "Veterans Day");

		lh.addStaticHoliday ("24-NOV-2011", "Thanksgiving Day");

		lh.addStaticHoliday ("26-DEC-2011", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2012", "New Years Day Observed");

		lh.addStaticHoliday ("16-JAN-2012", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("20-FEB-2012", "Washingtons Birthday");

		lh.addStaticHoliday ("28-MAY-2012", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2012", "Independence Day");

		lh.addStaticHoliday ("03-SEP-2012", "Labor Day");

		lh.addStaticHoliday ("08-OCT-2012", "Columbus Day");

		lh.addStaticHoliday ("12-NOV-2012", "Veterans Day Observed");

		lh.addStaticHoliday ("22-NOV-2012", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2013", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("18-FEB-2013", "Washingtons Birthday");

		lh.addStaticHoliday ("27-MAY-2013", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2013", "Independence Day");

		lh.addStaticHoliday ("02-SEP-2013", "Labor Day");

		lh.addStaticHoliday ("14-OCT-2013", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2013", "Veterans Day");

		lh.addStaticHoliday ("28-NOV-2013", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("20-JAN-2014", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("17-FEB-2014", "Washingtons Birthday");

		lh.addStaticHoliday ("26-MAY-2014", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2014", "Independence Day");

		lh.addStaticHoliday ("01-SEP-2014", "Labor Day");

		lh.addStaticHoliday ("13-OCT-2014", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2014", "Veterans Day");

		lh.addStaticHoliday ("27-NOV-2014", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("19-JAN-2015", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("16-FEB-2015", "Washingtons Birthday");

		lh.addStaticHoliday ("25-MAY-2015", "Memorial Day");

		lh.addStaticHoliday ("07-SEP-2015", "Labor Day");

		lh.addStaticHoliday ("12-OCT-2015", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2015", "Veterans Day");

		lh.addStaticHoliday ("26-NOV-2015", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("18-JAN-2016", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("15-FEB-2016", "Washingtons Birthday");

		lh.addStaticHoliday ("30-MAY-2016", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2016", "Independence Day");

		lh.addStaticHoliday ("05-SEP-2016", "Labor Day");

		lh.addStaticHoliday ("10-OCT-2016", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2016", "Veterans Day");

		lh.addStaticHoliday ("24-NOV-2016", "Thanksgiving Day");

		lh.addStaticHoliday ("26-DEC-2016", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2017", "New Years Day Observed");

		lh.addStaticHoliday ("16-JAN-2017", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("20-FEB-2017", "Washingtons Birthday");

		lh.addStaticHoliday ("29-MAY-2017", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2017", "Independence Day");

		lh.addStaticHoliday ("04-SEP-2017", "Labor Day");

		lh.addStaticHoliday ("09-OCT-2017", "Columbus Day");

		lh.addStaticHoliday ("23-NOV-2017", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("15-JAN-2018", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("19-FEB-2018", "Washingtons Birthday");

		lh.addStaticHoliday ("28-MAY-2018", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2018", "Independence Day");

		lh.addStaticHoliday ("03-SEP-2018", "Labor Day");

		lh.addStaticHoliday ("08-OCT-2018", "Columbus Day");

		lh.addStaticHoliday ("12-NOV-2018", "Veterans Day Observed");

		lh.addStaticHoliday ("22-NOV-2018", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2019", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("18-FEB-2019", "Washingtons Birthday");

		lh.addStaticHoliday ("27-MAY-2019", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2019", "Independence Day");

		lh.addStaticHoliday ("02-SEP-2019", "Labor Day");

		lh.addStaticHoliday ("14-OCT-2019", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2019", "Veterans Day");

		lh.addStaticHoliday ("28-NOV-2019", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("20-JAN-2020", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("17-FEB-2020", "Washingtons Birthday");

		lh.addStaticHoliday ("25-MAY-2020", "Memorial Day");

		lh.addStaticHoliday ("07-SEP-2020", "Labor Day");

		lh.addStaticHoliday ("12-OCT-2020", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2020", "Veterans Day");

		lh.addStaticHoliday ("26-NOV-2020", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("18-JAN-2021", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("15-FEB-2021", "Washingtons Birthday");

		lh.addStaticHoliday ("31-MAY-2021", "Memorial Day");

		lh.addStaticHoliday ("05-JUL-2021", "Independence Day Observed");

		lh.addStaticHoliday ("06-SEP-2021", "Labor Day");

		lh.addStaticHoliday ("11-OCT-2021", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2021", "Veterans Day");

		lh.addStaticHoliday ("25-NOV-2021", "Thanksgiving Day");

		lh.addStaticHoliday ("17-JAN-2022", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("21-FEB-2022", "Washingtons Birthday");

		lh.addStaticHoliday ("30-MAY-2022", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2022", "Independence Day");

		lh.addStaticHoliday ("05-SEP-2022", "Labor Day");

		lh.addStaticHoliday ("10-OCT-2022", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2022", "Veterans Day");

		lh.addStaticHoliday ("24-NOV-2022", "Thanksgiving Day");

		lh.addStaticHoliday ("26-DEC-2022", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2023", "New Years Day Observed");

		lh.addStaticHoliday ("16-JAN-2023", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("20-FEB-2023", "Washingtons Birthday");

		lh.addStaticHoliday ("29-MAY-2023", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2023", "Independence Day");

		lh.addStaticHoliday ("04-SEP-2023", "Labor Day");

		lh.addStaticHoliday ("09-OCT-2023", "Columbus Day");

		lh.addStaticHoliday ("23-NOV-2023", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("15-JAN-2024", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("19-FEB-2024", "Washingtons Birthday");

		lh.addStaticHoliday ("27-MAY-2024", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2024", "Independence Day");

		lh.addStaticHoliday ("02-SEP-2024", "Labor Day");

		lh.addStaticHoliday ("14-OCT-2024", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2024", "Veterans Day");

		lh.addStaticHoliday ("28-NOV-2024", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("20-JAN-2025", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("17-FEB-2025", "Washingtons Birthday");

		lh.addStaticHoliday ("26-MAY-2025", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2025", "Independence Day");

		lh.addStaticHoliday ("01-SEP-2025", "Labor Day");

		lh.addStaticHoliday ("13-OCT-2025", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2025", "Veterans Day");

		lh.addStaticHoliday ("27-NOV-2025", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("19-JAN-2026", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("16-FEB-2026", "Washingtons Birthday");

		lh.addStaticHoliday ("25-MAY-2026", "Memorial Day");

		lh.addStaticHoliday ("07-SEP-2026", "Labor Day");

		lh.addStaticHoliday ("12-OCT-2026", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2026", "Veterans Day");

		lh.addStaticHoliday ("26-NOV-2026", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("18-JAN-2027", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("15-FEB-2027", "Washingtons Birthday");

		lh.addStaticHoliday ("31-MAY-2027", "Memorial Day");

		lh.addStaticHoliday ("05-JUL-2027", "Independence Day Observed");

		lh.addStaticHoliday ("06-SEP-2027", "Labor Day");

		lh.addStaticHoliday ("11-OCT-2027", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2027", "Veterans Day");

		lh.addStaticHoliday ("25-NOV-2027", "Thanksgiving Day");

		lh.addStaticHoliday ("17-JAN-2028", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("21-FEB-2028", "Washingtons Birthday");

		lh.addStaticHoliday ("29-MAY-2028", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2028", "Independence Day");

		lh.addStaticHoliday ("04-SEP-2028", "Labor Day");

		lh.addStaticHoliday ("09-OCT-2028", "Columbus Day");

		lh.addStaticHoliday ("23-NOV-2028", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2029", "New Years Day");

		lh.addStaticHoliday ("15-JAN-2029", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("19-FEB-2029", "Washingtons Birthday");

		lh.addStaticHoliday ("28-MAY-2029", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2029", "Independence Day");

		lh.addStaticHoliday ("03-SEP-2029", "Labor Day");

		lh.addStaticHoliday ("08-OCT-2029", "Columbus Day");

		lh.addStaticHoliday ("12-NOV-2029", "Veterans Day Observed");

		lh.addStaticHoliday ("22-NOV-2029", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2029", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2030", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2030", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("18-FEB-2030", "Washingtons Birthday");

		lh.addStaticHoliday ("27-MAY-2030", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2030", "Independence Day");

		lh.addStaticHoliday ("02-SEP-2030", "Labor Day");

		lh.addStaticHoliday ("14-OCT-2030", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2030", "Veterans Day Observed");

		lh.addStaticHoliday ("28-NOV-2030", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2030", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2031", "New Years Day");

		lh.addStaticHoliday ("20-JAN-2031", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("17-FEB-2031", "Washingtons Birthday");

		lh.addStaticHoliday ("26-MAY-2031", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2031", "Independence Day");

		lh.addStaticHoliday ("01-SEP-2031", "Labor Day");

		lh.addStaticHoliday ("13-OCT-2031", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2031", "Veterans Day Observed");

		lh.addStaticHoliday ("27-NOV-2031", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2031", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2032", "New Years Day");

		lh.addStaticHoliday ("19-JAN-2032", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("16-FEB-2032", "Washingtons Birthday");

		lh.addStaticHoliday ("31-MAY-2032", "Memorial Day");

		lh.addStaticHoliday ("05-JUL-2032", "Independence Day");

		lh.addStaticHoliday ("06-SEP-2032", "Labor Day");

		lh.addStaticHoliday ("11-OCT-2032", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2032", "Veterans Day Observed");

		lh.addStaticHoliday ("25-NOV-2032", "Thanksgiving Day");

		lh.addStaticHoliday ("17-JAN-2033", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("21-FEB-2033", "Washingtons Birthday");

		lh.addStaticHoliday ("30-MAY-2033", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2033", "Independence Day");

		lh.addStaticHoliday ("05-SEP-2033", "Labor Day");

		lh.addStaticHoliday ("10-OCT-2033", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2033", "Veterans Day Observed");

		lh.addStaticHoliday ("24-NOV-2033", "Thanksgiving Day");

		lh.addStaticHoliday ("26-DEC-2033", "Christmas Day");

		lh.addStaticHoliday ("02-JAN-2034", "New Years Day");

		lh.addStaticHoliday ("16-JAN-2034", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("20-FEB-2034", "Washingtons Birthday");

		lh.addStaticHoliday ("29-MAY-2034", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2034", "Independence Day");

		lh.addStaticHoliday ("04-SEP-2034", "Labor Day");

		lh.addStaticHoliday ("09-OCT-2034", "Columbus Day");

		lh.addStaticHoliday ("23-NOV-2034", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2034", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2035", "New Years Day");

		lh.addStaticHoliday ("15-JAN-2035", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("19-FEB-2035", "Washingtons Birthday");

		lh.addStaticHoliday ("28-MAY-2035", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2035", "Independence Day");

		lh.addStaticHoliday ("03-SEP-2035", "Labor Day");

		lh.addStaticHoliday ("08-OCT-2035", "Columbus Day");

		lh.addStaticHoliday ("12-NOV-2035", "Veterans Day Observed");

		lh.addStaticHoliday ("22-NOV-2035", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2035", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2036", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2036", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("18-FEB-2036", "Washingtons Birthday");

		lh.addStaticHoliday ("26-MAY-2036", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2036", "Independence Day");

		lh.addStaticHoliday ("01-SEP-2036", "Labor Day");

		lh.addStaticHoliday ("13-OCT-2036", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2036", "Veterans Day Observed");

		lh.addStaticHoliday ("27-NOV-2036", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2036", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2037", "New Years Day");

		lh.addStaticHoliday ("19-JAN-2037", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("16-FEB-2037", "Washingtons Birthday");

		lh.addStaticHoliday ("25-MAY-2037", "Memorial Day");

		lh.addStaticHoliday ("07-SEP-2037", "Labor Day");

		lh.addStaticHoliday ("12-OCT-2037", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2037", "Veterans Day Observed");

		lh.addStaticHoliday ("26-NOV-2037", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2037", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2038", "New Years Day");

		lh.addStaticHoliday ("18-JAN-2038", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("15-FEB-2038", "Washingtons Birthday");

		lh.addStaticHoliday ("31-MAY-2038", "Memorial Day");

		lh.addStaticHoliday ("05-JUL-2038", "Independence Day");

		lh.addStaticHoliday ("06-SEP-2038", "Labor Day");

		lh.addStaticHoliday ("11-OCT-2038", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2038", "Veterans Day Observed");

		lh.addStaticHoliday ("25-NOV-2038", "Thanksgiving Day");

		lh.addStaticHoliday ("17-JAN-2039", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("21-FEB-2039", "Washingtons Birthday");

		lh.addStaticHoliday ("30-MAY-2039", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2039", "Independence Day");

		lh.addStaticHoliday ("05-SEP-2039", "Labor Day");

		lh.addStaticHoliday ("10-OCT-2039", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2039", "Veterans Day Observed");

		lh.addStaticHoliday ("24-NOV-2039", "Thanksgiving Day");

		lh.addStaticHoliday ("26-DEC-2039", "Christmas Day");

		lh.addStaticHoliday ("02-JAN-2040", "New Years Day");

		lh.addStaticHoliday ("16-JAN-2040", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("20-FEB-2040", "Washingtons Birthday");

		lh.addStaticHoliday ("28-MAY-2040", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2040", "Independence Day");

		lh.addStaticHoliday ("03-SEP-2040", "Labor Day");

		lh.addStaticHoliday ("08-OCT-2040", "Columbus Day");

		lh.addStaticHoliday ("12-NOV-2040", "Veterans Day Observed");

		lh.addStaticHoliday ("22-NOV-2040", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2040", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2041", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2041", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("18-FEB-2041", "Washingtons Birthday");

		lh.addStaticHoliday ("27-MAY-2041", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2041", "Independence Day");

		lh.addStaticHoliday ("02-SEP-2041", "Labor Day");

		lh.addStaticHoliday ("14-OCT-2041", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2041", "Veterans Day Observed");

		lh.addStaticHoliday ("28-NOV-2041", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2041", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2042", "New Years Day");

		lh.addStaticHoliday ("20-JAN-2042", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("17-FEB-2042", "Washingtons Birthday");

		lh.addStaticHoliday ("26-MAY-2042", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2042", "Independence Day");

		lh.addStaticHoliday ("01-SEP-2042", "Labor Day");

		lh.addStaticHoliday ("13-OCT-2042", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2042", "Veterans Day Observed");

		lh.addStaticHoliday ("27-NOV-2042", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2042", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2043", "New Years Day");

		lh.addStaticHoliday ("19-JAN-2043", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("16-FEB-2043", "Washingtons Birthday");

		lh.addStaticHoliday ("25-MAY-2043", "Memorial Day");

		lh.addStaticHoliday ("07-SEP-2043", "Labor Day");

		lh.addStaticHoliday ("12-OCT-2043", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2043", "Veterans Day Observed");

		lh.addStaticHoliday ("26-NOV-2043", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2043", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2044", "New Years Day");

		lh.addStaticHoliday ("18-JAN-2044", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("15-FEB-2044", "Washingtons Birthday");

		lh.addStaticHoliday ("30-MAY-2044", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2044", "Independence Day");

		lh.addStaticHoliday ("05-SEP-2044", "Labor Day");

		lh.addStaticHoliday ("10-OCT-2044", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2044", "Veterans Day Observed");

		lh.addStaticHoliday ("24-NOV-2044", "Thanksgiving Day");

		lh.addStaticHoliday ("26-DEC-2044", "Christmas Day");

		lh.addStaticHoliday ("02-JAN-2045", "New Years Day");

		lh.addStaticHoliday ("16-JAN-2045", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("20-FEB-2045", "Washingtons Birthday");

		lh.addStaticHoliday ("29-MAY-2045", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2045", "Independence Day");

		lh.addStaticHoliday ("04-SEP-2045", "Labor Day");

		lh.addStaticHoliday ("09-OCT-2045", "Columbus Day");

		lh.addStaticHoliday ("23-NOV-2045", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2045", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2046", "New Years Day");

		lh.addStaticHoliday ("15-JAN-2046", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("19-FEB-2046", "Washingtons Birthday");

		lh.addStaticHoliday ("28-MAY-2046", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2046", "Independence Day");

		lh.addStaticHoliday ("03-SEP-2046", "Labor Day");

		lh.addStaticHoliday ("08-OCT-2046", "Columbus Day");

		lh.addStaticHoliday ("12-NOV-2046", "Veterans Day Observed");

		lh.addStaticHoliday ("22-NOV-2046", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2046", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2047", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2047", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("18-FEB-2047", "Washingtons Birthday");

		lh.addStaticHoliday ("27-MAY-2047", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2047", "Independence Day");

		lh.addStaticHoliday ("02-SEP-2047", "Labor Day");

		lh.addStaticHoliday ("14-OCT-2047", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2047", "Veterans Day Observed");

		lh.addStaticHoliday ("28-NOV-2047", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2047", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2048", "New Years Day");

		lh.addStaticHoliday ("20-JAN-2048", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("17-FEB-2048", "Washingtons Birthday");

		lh.addStaticHoliday ("25-MAY-2048", "Memorial Day");

		lh.addStaticHoliday ("07-SEP-2048", "Labor Day");

		lh.addStaticHoliday ("12-OCT-2048", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2048", "Veterans Day Observed");

		lh.addStaticHoliday ("26-NOV-2048", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2048", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2049", "New Years Day");

		lh.addStaticHoliday ("18-JAN-2049", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("15-FEB-2049", "Washingtons Birthday");

		lh.addStaticHoliday ("31-MAY-2049", "Memorial Day");

		lh.addStaticHoliday ("05-JUL-2049", "Independence Day");

		lh.addStaticHoliday ("06-SEP-2049", "Labor Day");

		lh.addStaticHoliday ("11-OCT-2049", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2049", "Veterans Day Observed");

		lh.addStaticHoliday ("25-NOV-2049", "Thanksgiving Day");

		lh.addStaticHoliday ("17-JAN-2050", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("21-FEB-2050", "Washingtons Birthday");

		lh.addStaticHoliday ("30-MAY-2050", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2050", "Independence Day");

		lh.addStaticHoliday ("05-SEP-2050", "Labor Day");

		lh.addStaticHoliday ("10-OCT-2050", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2050", "Veterans Day Observed");

		lh.addStaticHoliday ("24-NOV-2050", "Thanksgiving Day");

		lh.addStaticHoliday ("26-DEC-2050", "Christmas Day");

		lh.addStaticHoliday ("02-JAN-2051", "New Years Day");

		lh.addStaticHoliday ("16-JAN-2051", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("20-FEB-2051", "Washingtons Birthday");

		lh.addStaticHoliday ("29-MAY-2051", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2051", "Independence Day");

		lh.addStaticHoliday ("04-SEP-2051", "Labor Day");

		lh.addStaticHoliday ("09-OCT-2051", "Columbus Day");

		lh.addStaticHoliday ("23-NOV-2051", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2051", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2052", "New Years Day");

		lh.addStaticHoliday ("15-JAN-2052", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("19-FEB-2052", "Washingtons Birthday");

		lh.addStaticHoliday ("27-MAY-2052", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2052", "Independence Day");

		lh.addStaticHoliday ("02-SEP-2052", "Labor Day");

		lh.addStaticHoliday ("14-OCT-2052", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2052", "Veterans Day Observed");

		lh.addStaticHoliday ("28-NOV-2052", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2052", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2053", "New Years Day");

		lh.addStaticHoliday ("20-JAN-2053", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("17-FEB-2053", "Washingtons Birthday");

		lh.addStaticHoliday ("26-MAY-2053", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2053", "Independence Day");

		lh.addStaticHoliday ("01-SEP-2053", "Labor Day");

		lh.addStaticHoliday ("13-OCT-2053", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2053", "Veterans Day Observed");

		lh.addStaticHoliday ("27-NOV-2053", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2053", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2054", "New Years Day");

		lh.addStaticHoliday ("19-JAN-2054", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("16-FEB-2054", "Washingtons Birthday");

		lh.addStaticHoliday ("25-MAY-2054", "Memorial Day");

		lh.addStaticHoliday ("07-SEP-2054", "Labor Day");

		lh.addStaticHoliday ("12-OCT-2054", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2054", "Veterans Day Observed");

		lh.addStaticHoliday ("26-NOV-2054", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2054", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2055", "New Years Day");

		lh.addStaticHoliday ("18-JAN-2055", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("15-FEB-2055", "Washingtons Birthday");

		lh.addStaticHoliday ("31-MAY-2055", "Memorial Day");

		lh.addStaticHoliday ("05-JUL-2055", "Independence Day");

		lh.addStaticHoliday ("06-SEP-2055", "Labor Day");

		lh.addStaticHoliday ("11-OCT-2055", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2055", "Veterans Day Observed");

		lh.addStaticHoliday ("25-NOV-2055", "Thanksgiving Day");

		lh.addStaticHoliday ("17-JAN-2056", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("21-FEB-2056", "Washingtons Birthday");

		lh.addStaticHoliday ("29-MAY-2056", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2056", "Independence Day");

		lh.addStaticHoliday ("04-SEP-2056", "Labor Day");

		lh.addStaticHoliday ("09-OCT-2056", "Columbus Day");

		lh.addStaticHoliday ("23-NOV-2056", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2056", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2057", "New Years Day");

		lh.addStaticHoliday ("15-JAN-2057", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("19-FEB-2057", "Washingtons Birthday");

		lh.addStaticHoliday ("28-MAY-2057", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2057", "Independence Day");

		lh.addStaticHoliday ("03-SEP-2057", "Labor Day");

		lh.addStaticHoliday ("08-OCT-2057", "Columbus Day");

		lh.addStaticHoliday ("12-NOV-2057", "Veterans Day Observed");

		lh.addStaticHoliday ("22-NOV-2057", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2057", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2058", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2058", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("18-FEB-2058", "Washingtons Birthday");

		lh.addStaticHoliday ("27-MAY-2058", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2058", "Independence Day");

		lh.addStaticHoliday ("02-SEP-2058", "Labor Day");

		lh.addStaticHoliday ("14-OCT-2058", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2058", "Veterans Day Observed");

		lh.addStaticHoliday ("28-NOV-2058", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2058", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2059", "New Years Day");

		lh.addStaticHoliday ("20-JAN-2059", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("17-FEB-2059", "Washingtons Birthday");

		lh.addStaticHoliday ("26-MAY-2059", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2059", "Independence Day");

		lh.addStaticHoliday ("01-SEP-2059", "Labor Day");

		lh.addStaticHoliday ("13-OCT-2059", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2059", "Veterans Day Observed");

		lh.addStaticHoliday ("27-NOV-2059", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2059", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2060", "New Years Day");

		lh.addStaticHoliday ("19-JAN-2060", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("16-FEB-2060", "Washingtons Birthday");

		lh.addStaticHoliday ("31-MAY-2060", "Memorial Day");

		lh.addStaticHoliday ("05-JUL-2060", "Independence Day");

		lh.addStaticHoliday ("06-SEP-2060", "Labor Day");

		lh.addStaticHoliday ("11-OCT-2060", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2060", "Veterans Day Observed");

		lh.addStaticHoliday ("25-NOV-2060", "Thanksgiving Day");

		lh.addStaticHoliday ("17-JAN-2061", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("21-FEB-2061", "Washingtons Birthday");

		lh.addStaticHoliday ("30-MAY-2061", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2061", "Independence Day");

		lh.addStaticHoliday ("05-SEP-2061", "Labor Day");

		lh.addStaticHoliday ("10-OCT-2061", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2061", "Veterans Day Observed");

		lh.addStaticHoliday ("24-NOV-2061", "Thanksgiving Day");

		lh.addStaticHoliday ("26-DEC-2061", "Christmas Day");

		lh.addStaticHoliday ("02-JAN-2062", "New Years Day");

		lh.addStaticHoliday ("16-JAN-2062", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("20-FEB-2062", "Washingtons Birthday");

		lh.addStaticHoliday ("29-MAY-2062", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2062", "Independence Day");

		lh.addStaticHoliday ("04-SEP-2062", "Labor Day");

		lh.addStaticHoliday ("09-OCT-2062", "Columbus Day");

		lh.addStaticHoliday ("23-NOV-2062", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2062", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2063", "New Years Day");

		lh.addStaticHoliday ("15-JAN-2063", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("19-FEB-2063", "Washingtons Birthday");

		lh.addStaticHoliday ("28-MAY-2063", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2063", "Independence Day");

		lh.addStaticHoliday ("03-SEP-2063", "Labor Day");

		lh.addStaticHoliday ("08-OCT-2063", "Columbus Day");

		lh.addStaticHoliday ("12-NOV-2063", "Veterans Day Observed");

		lh.addStaticHoliday ("22-NOV-2063", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2063", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2064", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2064", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("18-FEB-2064", "Washingtons Birthday");

		lh.addStaticHoliday ("26-MAY-2064", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2064", "Independence Day");

		lh.addStaticHoliday ("01-SEP-2064", "Labor Day");

		lh.addStaticHoliday ("13-OCT-2064", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2064", "Veterans Day Observed");

		lh.addStaticHoliday ("27-NOV-2064", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2064", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2065", "New Years Day");

		lh.addStaticHoliday ("19-JAN-2065", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("16-FEB-2065", "Washingtons Birthday");

		lh.addStaticHoliday ("25-MAY-2065", "Memorial Day");

		lh.addStaticHoliday ("07-SEP-2065", "Labor Day");

		lh.addStaticHoliday ("12-OCT-2065", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2065", "Veterans Day Observed");

		lh.addStaticHoliday ("26-NOV-2065", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2065", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2066", "New Years Day");

		lh.addStaticHoliday ("18-JAN-2066", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("15-FEB-2066", "Washingtons Birthday");

		lh.addStaticHoliday ("31-MAY-2066", "Memorial Day");

		lh.addStaticHoliday ("05-JUL-2066", "Independence Day");

		lh.addStaticHoliday ("06-SEP-2066", "Labor Day");

		lh.addStaticHoliday ("11-OCT-2066", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2066", "Veterans Day Observed");

		lh.addStaticHoliday ("25-NOV-2066", "Thanksgiving Day");

		lh.addStaticHoliday ("17-JAN-2067", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("21-FEB-2067", "Washingtons Birthday");

		lh.addStaticHoliday ("30-MAY-2067", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2067", "Independence Day");

		lh.addStaticHoliday ("05-SEP-2067", "Labor Day");

		lh.addStaticHoliday ("10-OCT-2067", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2067", "Veterans Day Observed");

		lh.addStaticHoliday ("24-NOV-2067", "Thanksgiving Day");

		lh.addStaticHoliday ("26-DEC-2067", "Christmas Day");

		lh.addStaticHoliday ("02-JAN-2068", "New Years Day");

		lh.addStaticHoliday ("16-JAN-2068", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("20-FEB-2068", "Washingtons Birthday");

		lh.addStaticHoliday ("28-MAY-2068", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2068", "Independence Day");

		lh.addStaticHoliday ("03-SEP-2068", "Labor Day");

		lh.addStaticHoliday ("08-OCT-2068", "Columbus Day");

		lh.addStaticHoliday ("12-NOV-2068", "Veterans Day Observed");

		lh.addStaticHoliday ("22-NOV-2068", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2068", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2069", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2069", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("18-FEB-2069", "Washingtons Birthday");

		lh.addStaticHoliday ("27-MAY-2069", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2069", "Independence Day");

		lh.addStaticHoliday ("02-SEP-2069", "Labor Day");

		lh.addStaticHoliday ("14-OCT-2069", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2069", "Veterans Day Observed");

		lh.addStaticHoliday ("28-NOV-2069", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2069", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2070", "New Years Day");

		lh.addStaticHoliday ("20-JAN-2070", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("17-FEB-2070", "Washingtons Birthday");

		lh.addStaticHoliday ("26-MAY-2070", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2070", "Independence Day");

		lh.addStaticHoliday ("01-SEP-2070", "Labor Day");

		lh.addStaticHoliday ("13-OCT-2070", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2070", "Veterans Day Observed");

		lh.addStaticHoliday ("27-NOV-2070", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2070", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2071", "New Years Day");

		lh.addStaticHoliday ("19-JAN-2071", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("16-FEB-2071", "Washingtons Birthday");

		lh.addStaticHoliday ("25-MAY-2071", "Memorial Day");

		lh.addStaticHoliday ("07-SEP-2071", "Labor Day");

		lh.addStaticHoliday ("12-OCT-2071", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2071", "Veterans Day Observed");

		lh.addStaticHoliday ("26-NOV-2071", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2071", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2072", "New Years Day");

		lh.addStaticHoliday ("18-JAN-2072", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("15-FEB-2072", "Washingtons Birthday");

		lh.addStaticHoliday ("30-MAY-2072", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2072", "Independence Day");

		lh.addStaticHoliday ("05-SEP-2072", "Labor Day");

		lh.addStaticHoliday ("10-OCT-2072", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2072", "Veterans Day Observed");

		lh.addStaticHoliday ("24-NOV-2072", "Thanksgiving Day");

		lh.addStaticHoliday ("26-DEC-2072", "Christmas Day");

		lh.addStaticHoliday ("02-JAN-2073", "New Years Day");

		lh.addStaticHoliday ("16-JAN-2073", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("20-FEB-2073", "Washingtons Birthday");

		lh.addStaticHoliday ("29-MAY-2073", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2073", "Independence Day");

		lh.addStaticHoliday ("04-SEP-2073", "Labor Day");

		lh.addStaticHoliday ("09-OCT-2073", "Columbus Day");

		lh.addStaticHoliday ("23-NOV-2073", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2073", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2074", "New Years Day");

		lh.addStaticHoliday ("15-JAN-2074", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("19-FEB-2074", "Washingtons Birthday");

		lh.addStaticHoliday ("28-MAY-2074", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2074", "Independence Day");

		lh.addStaticHoliday ("03-SEP-2074", "Labor Day");

		lh.addStaticHoliday ("08-OCT-2074", "Columbus Day");

		lh.addStaticHoliday ("12-NOV-2074", "Veterans Day Observed");

		lh.addStaticHoliday ("22-NOV-2074", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2074", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2075", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2075", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("18-FEB-2075", "Washingtons Birthday");

		lh.addStaticHoliday ("27-MAY-2075", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2075", "Independence Day");

		lh.addStaticHoliday ("02-SEP-2075", "Labor Day");

		lh.addStaticHoliday ("14-OCT-2075", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2075", "Veterans Day Observed");

		lh.addStaticHoliday ("28-NOV-2075", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2075", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2076", "New Years Day");

		lh.addStaticHoliday ("20-JAN-2076", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("17-FEB-2076", "Washingtons Birthday");

		lh.addStaticHoliday ("25-MAY-2076", "Memorial Day");

		lh.addStaticHoliday ("07-SEP-2076", "Labor Day");

		lh.addStaticHoliday ("12-OCT-2076", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2076", "Veterans Day Observed");

		lh.addStaticHoliday ("26-NOV-2076", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2076", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2077", "New Years Day");

		lh.addStaticHoliday ("18-JAN-2077", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("15-FEB-2077", "Washingtons Birthday");

		lh.addStaticHoliday ("31-MAY-2077", "Memorial Day");

		lh.addStaticHoliday ("05-JUL-2077", "Independence Day");

		lh.addStaticHoliday ("06-SEP-2077", "Labor Day");

		lh.addStaticHoliday ("11-OCT-2077", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2077", "Veterans Day Observed");

		lh.addStaticHoliday ("25-NOV-2077", "Thanksgiving Day");

		lh.addStaticHoliday ("17-JAN-2078", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("21-FEB-2078", "Washingtons Birthday");

		lh.addStaticHoliday ("30-MAY-2078", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2078", "Independence Day");

		lh.addStaticHoliday ("05-SEP-2078", "Labor Day");

		lh.addStaticHoliday ("10-OCT-2078", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2078", "Veterans Day Observed");

		lh.addStaticHoliday ("24-NOV-2078", "Thanksgiving Day");

		lh.addStaticHoliday ("26-DEC-2078", "Christmas Day");

		lh.addStaticHoliday ("02-JAN-2079", "New Years Day");

		lh.addStaticHoliday ("16-JAN-2079", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("20-FEB-2079", "Washingtons Birthday");

		lh.addStaticHoliday ("29-MAY-2079", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2079", "Independence Day");

		lh.addStaticHoliday ("04-SEP-2079", "Labor Day");

		lh.addStaticHoliday ("09-OCT-2079", "Columbus Day");

		lh.addStaticHoliday ("23-NOV-2079", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2079", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2080", "New Years Day");

		lh.addStaticHoliday ("15-JAN-2080", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("19-FEB-2080", "Washingtons Birthday");

		lh.addStaticHoliday ("27-MAY-2080", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2080", "Independence Day");

		lh.addStaticHoliday ("02-SEP-2080", "Labor Day");

		lh.addStaticHoliday ("14-OCT-2080", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2080", "Veterans Day Observed");

		lh.addStaticHoliday ("28-NOV-2080", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2080", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2081", "New Years Day");

		lh.addStaticHoliday ("20-JAN-2081", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("17-FEB-2081", "Washingtons Birthday");

		lh.addStaticHoliday ("26-MAY-2081", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2081", "Independence Day");

		lh.addStaticHoliday ("01-SEP-2081", "Labor Day");

		lh.addStaticHoliday ("13-OCT-2081", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2081", "Veterans Day Observed");

		lh.addStaticHoliday ("27-NOV-2081", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2081", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2082", "New Years Day");

		lh.addStaticHoliday ("19-JAN-2082", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("16-FEB-2082", "Washingtons Birthday");

		lh.addStaticHoliday ("25-MAY-2082", "Memorial Day");

		lh.addStaticHoliday ("07-SEP-2082", "Labor Day");

		lh.addStaticHoliday ("12-OCT-2082", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2082", "Veterans Day Observed");

		lh.addStaticHoliday ("26-NOV-2082", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2082", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2083", "New Years Day");

		lh.addStaticHoliday ("18-JAN-2083", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("15-FEB-2083", "Washingtons Birthday");

		lh.addStaticHoliday ("31-MAY-2083", "Memorial Day");

		lh.addStaticHoliday ("05-JUL-2083", "Independence Day");

		lh.addStaticHoliday ("06-SEP-2083", "Labor Day");

		lh.addStaticHoliday ("11-OCT-2083", "Columbus Day");

		lh.addStaticHoliday ("11-NOV-2083", "Veterans Day Observed");

		lh.addStaticHoliday ("25-NOV-2083", "Thanksgiving Day");

		lh.addStaticHoliday ("17-JAN-2084", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("21-FEB-2084", "Washingtons Birthday");

		lh.addStaticHoliday ("29-MAY-2084", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2084", "Independence Day");

		lh.addStaticHoliday ("04-SEP-2084", "Labor Day");

		lh.addStaticHoliday ("09-OCT-2084", "Columbus Day");

		lh.addStaticHoliday ("23-NOV-2084", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2084", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2085", "New Years Day");

		lh.addStaticHoliday ("15-JAN-2085", "Martin Luther Kings Birthday");

		lh.addStaticHoliday ("19-FEB-2085", "Washingtons Birthday");

		lh.addStaticHoliday ("28-MAY-2085", "Memorial Day");

		lh.addStaticHoliday ("04-JUL-2085", "Independence Day");

		lh.addStaticHoliday ("03-SEP-2085", "Labor Day");

		lh.addStaticHoliday ("08-OCT-2085", "Columbus Day");

		lh.addStaticHoliday ("12-NOV-2085", "Veterans Day Observed");

		lh.addStaticHoliday ("22-NOV-2085", "Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2085", "Christmas Day");

		lh.addStandardWeekend();

		return lh;
	}
}
