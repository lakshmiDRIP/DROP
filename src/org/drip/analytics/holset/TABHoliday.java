
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

public class TABHoliday implements org.drip.analytics.holset.LocationHoliday {
	public TABHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "TAB";
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

		lh.addStaticHoliday ("01-JAN-2030", "...from ChaseTools");

		lh.addStaticHoliday ("21-JAN-2030", "...from ChaseTools");

		lh.addStaticHoliday ("18-FEB-2030", "...from ChaseTools");

		lh.addStaticHoliday ("27-MAY-2030", "...from ChaseTools");

		lh.addStaticHoliday ("04-JUL-2030", "...from ChaseTools");

		lh.addStaticHoliday ("02-SEP-2030", "...from ChaseTools");

		lh.addStaticHoliday ("14-OCT-2030", "...from ChaseTools");

		lh.addStaticHoliday ("11-NOV-2030", "...from ChaseTools");

		lh.addStaticHoliday ("28-NOV-2030", "...from ChaseTools");

		lh.addStaticHoliday ("25-DEC-2030", "...from ChaseTools");

		lh.addStaticHoliday ("01-JAN-2031", "...from ChaseTools");

		lh.addStaticHoliday ("20-JAN-2031", "...from ChaseTools");

		lh.addStaticHoliday ("17-FEB-2031", "...from ChaseTools");

		lh.addStaticHoliday ("26-MAY-2031", "...from ChaseTools");

		lh.addStaticHoliday ("04-JUL-2031", "...from ChaseTools");

		lh.addStaticHoliday ("01-SEP-2031", "...from ChaseTools");

		lh.addStaticHoliday ("13-OCT-2031", "...from ChaseTools");

		lh.addStaticHoliday ("11-NOV-2031", "...from ChaseTools");

		lh.addStaticHoliday ("27-NOV-2031", "...from ChaseTools");

		lh.addStaticHoliday ("25-DEC-2031", "...from ChaseTools");

		lh.addStaticHoliday ("01-JAN-2032", "...from ChaseTools");

		lh.addStaticHoliday ("19-JAN-2032", "...from ChaseTools");

		lh.addStaticHoliday ("16-FEB-2032", "...from ChaseTools");

		lh.addStaticHoliday ("31-MAY-2032", "...from ChaseTools");

		lh.addStaticHoliday ("05-JUL-2032", "...from ChaseTools");

		lh.addStaticHoliday ("06-SEP-2032", "...from ChaseTools");

		lh.addStaticHoliday ("11-OCT-2032", "...from ChaseTools");

		lh.addStaticHoliday ("11-NOV-2032", "...from ChaseTools");

		lh.addStaticHoliday ("25-NOV-2032", "...from ChaseTools");

		lh.addStaticHoliday ("17-JAN-2033", "...from ChaseTools");

		lh.addStaticHoliday ("21-FEB-2033", "...from ChaseTools");

		lh.addStaticHoliday ("30-MAY-2033", "...from ChaseTools");

		lh.addStaticHoliday ("04-JUL-2033", "...from ChaseTools");

		lh.addStaticHoliday ("05-SEP-2033", "...from ChaseTools");

		lh.addStaticHoliday ("10-OCT-2033", "...from ChaseTools");

		lh.addStaticHoliday ("11-NOV-2033", "...from ChaseTools");

		lh.addStaticHoliday ("24-NOV-2033", "...from ChaseTools");

		lh.addStaticHoliday ("26-DEC-2033", "...from ChaseTools");

		lh.addStaticHoliday ("02-JAN-2034", "...from ChaseTools");

		lh.addStaticHoliday ("16-JAN-2034", "...from ChaseTools");

		lh.addStaticHoliday ("20-FEB-2034", "...from ChaseTools");

		lh.addStaticHoliday ("29-MAY-2034", "...from ChaseTools");

		lh.addStaticHoliday ("04-JUL-2034", "...from ChaseTools");

		lh.addStaticHoliday ("04-SEP-2034", "...from ChaseTools");

		lh.addStaticHoliday ("09-OCT-2034", "...from ChaseTools");

		lh.addStaticHoliday ("23-NOV-2034", "...from ChaseTools");

		lh.addStaticHoliday ("25-DEC-2034", "...from ChaseTools");

		lh.addStaticHoliday ("01-JAN-2035", "...from ChaseTools");

		lh.addStaticHoliday ("15-JAN-2035", "...from ChaseTools");

		lh.addStaticHoliday ("19-FEB-2035", "...from ChaseTools");

		lh.addStaticHoliday ("28-MAY-2035", "...from ChaseTools");

		lh.addStaticHoliday ("04-JUL-2035", "...from ChaseTools");

		lh.addStaticHoliday ("03-SEP-2035", "...from ChaseTools");

		lh.addStaticHoliday ("08-OCT-2035", "...from ChaseTools");

		lh.addStaticHoliday ("12-NOV-2035", "...from ChaseTools");

		lh.addStaticHoliday ("22-NOV-2035", "...from ChaseTools");

		lh.addStaticHoliday ("25-DEC-2035", "...from ChaseTools");

		lh.addStaticHoliday ("01-JAN-2036", "...from ChaseTools");

		lh.addStaticHoliday ("21-JAN-2036", "...from ChaseTools");

		lh.addStaticHoliday ("18-FEB-2036", "...from ChaseTools");

		lh.addStaticHoliday ("26-MAY-2036", "...from ChaseTools");

		lh.addStaticHoliday ("04-JUL-2036", "...from ChaseTools");

		lh.addStaticHoliday ("01-SEP-2036", "...from ChaseTools");

		lh.addStaticHoliday ("13-OCT-2036", "...from ChaseTools");

		lh.addStaticHoliday ("11-NOV-2036", "...from ChaseTools");

		lh.addStaticHoliday ("27-NOV-2036", "...from ChaseTools");

		lh.addStaticHoliday ("25-DEC-2036", "...from ChaseTools");

		lh.addStaticHoliday ("01-JAN-2037", "...from ChaseTools");

		lh.addStaticHoliday ("19-JAN-2037", "...from ChaseTools");

		lh.addStaticHoliday ("16-FEB-2037", "...from ChaseTools");

		lh.addStaticHoliday ("25-MAY-2037", "...from ChaseTools");

		lh.addStaticHoliday ("07-SEP-2037", "...from ChaseTools");

		lh.addStaticHoliday ("12-OCT-2037", "...from ChaseTools");

		lh.addStaticHoliday ("11-NOV-2037", "...from ChaseTools");

		lh.addStaticHoliday ("26-NOV-2037", "...from ChaseTools");

		lh.addStaticHoliday ("25-DEC-2037", "...from ChaseTools");

		lh.addStaticHoliday ("01-JAN-2038", "...from ChaseTools");

		lh.addStaticHoliday ("18-JAN-2038", "...from ChaseTools");

		lh.addStaticHoliday ("15-FEB-2038", "...from ChaseTools");

		lh.addStaticHoliday ("31-MAY-2038", "...from ChaseTools");

		lh.addStaticHoliday ("05-JUL-2038", "...from ChaseTools");

		lh.addStaticHoliday ("06-SEP-2038", "...from ChaseTools");

		lh.addStaticHoliday ("11-OCT-2038", "...from ChaseTools");

		lh.addStaticHoliday ("11-NOV-2038", "...from ChaseTools");

		lh.addStaticHoliday ("25-NOV-2038", "...from ChaseTools");

		lh.addStaticHoliday ("17-JAN-2039", "...from ChaseTools");

		lh.addStaticHoliday ("21-FEB-2039", "...from ChaseTools");

		lh.addStaticHoliday ("30-MAY-2039", "...from ChaseTools");

		lh.addStaticHoliday ("04-JUL-2039", "...from ChaseTools");

		lh.addStaticHoliday ("05-SEP-2039", "...from ChaseTools");

		lh.addStaticHoliday ("10-OCT-2039", "...from ChaseTools");

		lh.addStaticHoliday ("11-NOV-2039", "...from ChaseTools");

		lh.addStaticHoliday ("24-NOV-2039", "...from ChaseTools");

		lh.addStaticHoliday ("26-DEC-2039", "...from ChaseTools");

		lh.addStaticHoliday ("02-JAN-2040", "");

		lh.addStaticHoliday ("16-JAN-2040", "");

		lh.addStaticHoliday ("20-FEB-2040", "");

		lh.addStaticHoliday ("28-MAY-2040", "");

		lh.addStaticHoliday ("04-JUL-2040", "");

		lh.addStaticHoliday ("03-SEP-2040", "");

		lh.addStaticHoliday ("08-OCT-2040", "");

		lh.addStaticHoliday ("12-NOV-2040", "");

		lh.addStaticHoliday ("22-NOV-2040", "");

		lh.addStaticHoliday ("25-DEC-2040", "");

		lh.addStaticHoliday ("01-JAN-2041", "");

		lh.addStaticHoliday ("21-JAN-2041", "");

		lh.addStaticHoliday ("18-FEB-2041", "");

		lh.addStaticHoliday ("27-MAY-2041", "");

		lh.addStaticHoliday ("04-JUL-2041", "");

		lh.addStaticHoliday ("02-SEP-2041", "");

		lh.addStaticHoliday ("14-OCT-2041", "");

		lh.addStaticHoliday ("11-NOV-2041", "");

		lh.addStaticHoliday ("28-NOV-2041", "");

		lh.addStaticHoliday ("25-DEC-2041", "");

		lh.addStaticHoliday ("01-JAN-2042", "");

		lh.addStaticHoliday ("20-JAN-2042", "");

		lh.addStaticHoliday ("17-FEB-2042", "");

		lh.addStaticHoliday ("26-MAY-2042", "");

		lh.addStaticHoliday ("04-JUL-2042", "");

		lh.addStaticHoliday ("01-SEP-2042", "");

		lh.addStaticHoliday ("13-OCT-2042", "");

		lh.addStaticHoliday ("11-NOV-2042", "");

		lh.addStaticHoliday ("27-NOV-2042", "");

		lh.addStaticHoliday ("25-DEC-2042", "");

		lh.addStaticHoliday ("01-JAN-2043", "");

		lh.addStaticHoliday ("19-JAN-2043", "");

		lh.addStaticHoliday ("16-FEB-2043", "");

		lh.addStaticHoliday ("25-MAY-2043", "");

		lh.addStaticHoliday ("07-SEP-2043", "");

		lh.addStaticHoliday ("12-OCT-2043", "");

		lh.addStaticHoliday ("11-NOV-2043", "");

		lh.addStaticHoliday ("26-NOV-2043", "");

		lh.addStaticHoliday ("25-DEC-2043", "");

		lh.addStaticHoliday ("01-JAN-2044", "");

		lh.addStaticHoliday ("18-JAN-2044", "");

		lh.addStaticHoliday ("15-FEB-2044", "");

		lh.addStaticHoliday ("30-MAY-2044", "");

		lh.addStaticHoliday ("04-JUL-2044", "");

		lh.addStaticHoliday ("05-SEP-2044", "");

		lh.addStaticHoliday ("10-OCT-2044", "");

		lh.addStaticHoliday ("11-NOV-2044", "");

		lh.addStaticHoliday ("24-NOV-2044", "");

		lh.addStaticHoliday ("26-DEC-2044", "");

		lh.addStaticHoliday ("02-JAN-2045", "");

		lh.addStaticHoliday ("16-JAN-2045", "");

		lh.addStaticHoliday ("20-FEB-2045", "");

		lh.addStaticHoliday ("29-MAY-2045", "");

		lh.addStaticHoliday ("04-JUL-2045", "");

		lh.addStaticHoliday ("04-SEP-2045", "");

		lh.addStaticHoliday ("09-OCT-2045", "");

		lh.addStaticHoliday ("23-NOV-2045", "");

		lh.addStaticHoliday ("25-DEC-2045", "");

		lh.addStaticHoliday ("01-JAN-2046", "");

		lh.addStaticHoliday ("15-JAN-2046", "");

		lh.addStaticHoliday ("19-FEB-2046", "");

		lh.addStaticHoliday ("28-MAY-2046", "");

		lh.addStaticHoliday ("04-JUL-2046", "");

		lh.addStaticHoliday ("03-SEP-2046", "");

		lh.addStaticHoliday ("08-OCT-2046", "");

		lh.addStaticHoliday ("12-NOV-2046", "");

		lh.addStaticHoliday ("22-NOV-2046", "");

		lh.addStaticHoliday ("25-DEC-2046", "");

		lh.addStaticHoliday ("01-JAN-2047", "");

		lh.addStaticHoliday ("21-JAN-2047", "");

		lh.addStaticHoliday ("18-FEB-2047", "");

		lh.addStaticHoliday ("27-MAY-2047", "");

		lh.addStaticHoliday ("04-JUL-2047", "");

		lh.addStaticHoliday ("02-SEP-2047", "");

		lh.addStaticHoliday ("14-OCT-2047", "");

		lh.addStaticHoliday ("11-NOV-2047", "");

		lh.addStaticHoliday ("28-NOV-2047", "");

		lh.addStaticHoliday ("25-DEC-2047", "");

		lh.addStaticHoliday ("01-JAN-2048", "");

		lh.addStaticHoliday ("20-JAN-2048", "");

		lh.addStaticHoliday ("17-FEB-2048", "");

		lh.addStaticHoliday ("25-MAY-2048", "");

		lh.addStaticHoliday ("07-SEP-2048", "");

		lh.addStaticHoliday ("12-OCT-2048", "");

		lh.addStaticHoliday ("11-NOV-2048", "");

		lh.addStaticHoliday ("26-NOV-2048", "");

		lh.addStaticHoliday ("25-DEC-2048", "");

		lh.addStaticHoliday ("01-JAN-2049", "");

		lh.addStaticHoliday ("18-JAN-2049", "");

		lh.addStaticHoliday ("15-FEB-2049", "");

		lh.addStaticHoliday ("31-MAY-2049", "");

		lh.addStaticHoliday ("05-JUL-2049", "");

		lh.addStaticHoliday ("06-SEP-2049", "");

		lh.addStaticHoliday ("11-OCT-2049", "");

		lh.addStaticHoliday ("11-NOV-2049", "");

		lh.addStaticHoliday ("25-NOV-2049", "");

		lh.addStaticHoliday ("17-JAN-2050", "");

		lh.addStaticHoliday ("21-FEB-2050", "");

		lh.addStaticHoliday ("30-MAY-2050", "");

		lh.addStaticHoliday ("04-JUL-2050", "");

		lh.addStaticHoliday ("05-SEP-2050", "");

		lh.addStaticHoliday ("10-OCT-2050", "");

		lh.addStaticHoliday ("11-NOV-2050", "");

		lh.addStaticHoliday ("24-NOV-2050", "");

		lh.addStaticHoliday ("26-DEC-2050", "");

		lh.addStaticHoliday ("02-JAN-2051", "");

		lh.addStaticHoliday ("16-JAN-2051", "");

		lh.addStaticHoliday ("20-FEB-2051", "");

		lh.addStaticHoliday ("29-MAY-2051", "");

		lh.addStaticHoliday ("04-JUL-2051", "");

		lh.addStaticHoliday ("04-SEP-2051", "");

		lh.addStaticHoliday ("09-OCT-2051", "");

		lh.addStaticHoliday ("23-NOV-2051", "");

		lh.addStaticHoliday ("25-DEC-2051", "");

		lh.addStaticHoliday ("01-JAN-2052", "");

		lh.addStaticHoliday ("15-JAN-2052", "");

		lh.addStaticHoliday ("19-FEB-2052", "");

		lh.addStaticHoliday ("27-MAY-2052", "");

		lh.addStaticHoliday ("04-JUL-2052", "");

		lh.addStaticHoliday ("02-SEP-2052", "");

		lh.addStaticHoliday ("14-OCT-2052", "");

		lh.addStaticHoliday ("11-NOV-2052", "");

		lh.addStaticHoliday ("28-NOV-2052", "");

		lh.addStaticHoliday ("25-DEC-2052", "");

		lh.addStaticHoliday ("01-JAN-2053", "");

		lh.addStaticHoliday ("20-JAN-2053", "");

		lh.addStaticHoliday ("17-FEB-2053", "");

		lh.addStaticHoliday ("26-MAY-2053", "");

		lh.addStaticHoliday ("04-JUL-2053", "");

		lh.addStaticHoliday ("01-SEP-2053", "");

		lh.addStaticHoliday ("13-OCT-2053", "");

		lh.addStaticHoliday ("11-NOV-2053", "");

		lh.addStaticHoliday ("27-NOV-2053", "");

		lh.addStaticHoliday ("25-DEC-2053", "");

		lh.addStaticHoliday ("01-JAN-2054", "");

		lh.addStaticHoliday ("19-JAN-2054", "");

		lh.addStaticHoliday ("16-FEB-2054", "");

		lh.addStaticHoliday ("25-MAY-2054", "");

		lh.addStaticHoliday ("07-SEP-2054", "");

		lh.addStaticHoliday ("12-OCT-2054", "");

		lh.addStaticHoliday ("11-NOV-2054", "");

		lh.addStaticHoliday ("26-NOV-2054", "");

		lh.addStaticHoliday ("25-DEC-2054", "");

		lh.addStaticHoliday ("01-JAN-2055", "");

		lh.addStaticHoliday ("18-JAN-2055", "");

		lh.addStaticHoliday ("15-FEB-2055", "");

		lh.addStaticHoliday ("31-MAY-2055", "");

		lh.addStaticHoliday ("05-JUL-2055", "");

		lh.addStaticHoliday ("06-SEP-2055", "");

		lh.addStaticHoliday ("11-OCT-2055", "");

		lh.addStaticHoliday ("11-NOV-2055", "");

		lh.addStaticHoliday ("25-NOV-2055", "");

		lh.addStaticHoliday ("17-JAN-2056", "");

		lh.addStaticHoliday ("21-FEB-2056", "");

		lh.addStaticHoliday ("29-MAY-2056", "");

		lh.addStaticHoliday ("04-JUL-2056", "");

		lh.addStaticHoliday ("04-SEP-2056", "");

		lh.addStaticHoliday ("09-OCT-2056", "");

		lh.addStaticHoliday ("23-NOV-2056", "");

		lh.addStaticHoliday ("25-DEC-2056", "");

		lh.addStaticHoliday ("01-JAN-2057", "");

		lh.addStaticHoliday ("15-JAN-2057", "");

		lh.addStaticHoliday ("19-FEB-2057", "");

		lh.addStaticHoliday ("28-MAY-2057", "");

		lh.addStaticHoliday ("04-JUL-2057", "");

		lh.addStaticHoliday ("03-SEP-2057", "");

		lh.addStaticHoliday ("08-OCT-2057", "");

		lh.addStaticHoliday ("12-NOV-2057", "");

		lh.addStaticHoliday ("22-NOV-2057", "");

		lh.addStaticHoliday ("25-DEC-2057", "");

		lh.addStaticHoliday ("01-JAN-2058", "");

		lh.addStaticHoliday ("21-JAN-2058", "");

		lh.addStaticHoliday ("18-FEB-2058", "");

		lh.addStaticHoliday ("27-MAY-2058", "");

		lh.addStaticHoliday ("04-JUL-2058", "");

		lh.addStaticHoliday ("02-SEP-2058", "");

		lh.addStaticHoliday ("14-OCT-2058", "");

		lh.addStaticHoliday ("11-NOV-2058", "");

		lh.addStaticHoliday ("28-NOV-2058", "");

		lh.addStaticHoliday ("25-DEC-2058", "");

		lh.addStandardWeekend();

		return lh;
	}
}
