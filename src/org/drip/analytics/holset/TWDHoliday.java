
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

public class TWDHoliday implements org.drip.analytics.holset.LocationHoliday {
	public TWDHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "TWD";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("02-JAN-1999", "Bank Holiday");

		lh.addStaticHoliday ("04-JAN-1999", "Bank Holiday Observed");

		lh.addStaticHoliday ("15-FEB-1999", "Lunar New Years Eve");

		lh.addStaticHoliday ("16-FEB-1999", "Lunar New Years Day");

		lh.addStaticHoliday ("17-FEB-1999", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("18-FEB-1999", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("05-APR-1999", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-1999", "May Day");

		lh.addStaticHoliday ("18-JUN-1999", "Dragon Boat Festival");

		lh.addStaticHoliday ("19-JUN-1999", "Bridging Day");

		lh.addStaticHoliday ("01-JUL-1999", "Bank Holiday");

		lh.addStaticHoliday ("24-SEP-1999", "Moon Festival Day");

		lh.addStaticHoliday ("12-NOV-1999", "Sun Yat-Sens Birthday");

		lh.addStaticHoliday ("31-DEC-1999", "Special Holiday");

		lh.addStaticHoliday ("01-JAN-2000", "New Years Day");

		lh.addStaticHoliday ("03-JAN-2000", "Bank Holiday Observed");

		lh.addStaticHoliday ("04-FEB-2000", "Lunar New Years Eve");

		lh.addStaticHoliday ("05-FEB-2000", "Lunar New Years Day");

		lh.addStaticHoliday ("07-FEB-2000", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2000", "Memorial Day");

		lh.addStaticHoliday ("18-MAR-2000", "Election Day");

		lh.addStaticHoliday ("03-APR-2000", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2000", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2000", "May Day");

		lh.addStaticHoliday ("06-JUN-2000", "Dragon Boat Festival");

		lh.addStaticHoliday ("01-JUL-2000", "Bank Holiday");

		lh.addStaticHoliday ("23-AUG-2000", "TYPHOON-OFFICE CLOSE");

		lh.addStaticHoliday ("12-SEP-2000", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2000", "National Day");

		lh.addStaticHoliday ("25-DEC-2000", "Constitution Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("22-JAN-2001", "Bridging Day");

		lh.addStaticHoliday ("23-JAN-2001", "Lunar New Years Eve");

		lh.addStaticHoliday ("24-JAN-2001", "Lunar New Years Day");

		lh.addStaticHoliday ("25-JAN-2001", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("26-JAN-2001", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("28-FEB-2001", "Memorial Day");

		lh.addStaticHoliday ("05-APR-2001", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2001", "May Day");

		lh.addStaticHoliday ("25-JUN-2001", "Dragon Boat Festival");

		lh.addStaticHoliday ("30-JUL-2001", "TYPHOON-OFFICE CLOSE");

		lh.addStaticHoliday ("17-SEP-2001", "TYPHOON-OFFICE CLOSE");

		lh.addStaticHoliday ("18-SEP-2001", "TYPHOON-OFFICE CLOSE");

		lh.addStaticHoliday ("01-OCT-2001", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2001", "National Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2002", "Lunar New Years Eve");

		lh.addStaticHoliday ("12-FEB-2002", "Lunar New Years Day");

		lh.addStaticHoliday ("13-FEB-2002", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("14-FEB-2002", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("28-FEB-2002", "MEMORIAL DAY");

		lh.addStaticHoliday ("02-MAR-2002", "Special Holiday");

		lh.addStaticHoliday ("05-APR-2002", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2002", "LABOR DAY");

		lh.addStaticHoliday ("10-OCT-2002", "National Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("31-JAN-2003", "Lunar New Years Eve");

		lh.addStaticHoliday ("03-FEB-2003", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("04-FEB-2003", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("05-FEB-2003", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("28-FEB-2003", "Special Holiday");

		lh.addStaticHoliday ("01-MAY-2003", "May Day");

		lh.addStaticHoliday ("04-JUN-2003", "Dragon Boat Festival");

		lh.addStaticHoliday ("11-SEP-2003", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2003", "National Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2004", "Lunar New Years Eve");

		lh.addStaticHoliday ("22-JAN-2004", "Lunar New Years Day");

		lh.addStaticHoliday ("23-JAN-2004", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("26-JAN-2004", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("22-JUN-2004", "Dragon Boat Festival");

		lh.addStaticHoliday ("24-AUG-2004", "Special Holiday");

		lh.addStaticHoliday ("25-AUG-2004", "Special Holiday");

		lh.addStaticHoliday ("28-SEP-2004", "Moon Festival Day");

		lh.addStaticHoliday ("25-OCT-2004", "Special Holiday");

		lh.addStaticHoliday ("07-FEB-2005", "Bridging Day");

		lh.addStaticHoliday ("08-FEB-2005", "Lunar New Years Eve");

		lh.addStaticHoliday ("09-FEB-2005", "Lunar New Years Day");

		lh.addStaticHoliday ("10-FEB-2005", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("11-FEB-2005", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("28-FEB-2005", "Memorial Day");

		lh.addStaticHoliday ("05-APR-2005", "Tomb Sweeping Day");

		lh.addStaticHoliday ("02-MAY-2005", "Labor Day Observed");

		lh.addStaticHoliday ("18-JUL-2005", "Special Holiday");

		lh.addStaticHoliday ("05-AUG-2005", "Special Holiday");

		lh.addStaticHoliday ("01-SEP-2005", "Special Holiday");

		lh.addStaticHoliday ("10-OCT-2005", "National Day");

		lh.addStaticHoliday ("28-JAN-2006", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("29-JAN-2006", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("30-JAN-2006", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("31-JAN-2006", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("01-FEB-2006", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("02-FEB-2006", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("28-FEB-2006", "Memorial Day");

		lh.addStaticHoliday ("05-APR-2006", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2006", "Labor Day Observed");

		lh.addStaticHoliday ("31-MAY-2006", "Dragon Boat Festival");

		lh.addStaticHoliday ("06-OCT-2006", "Moon Festival Day");

		lh.addStaticHoliday ("09-OCT-2006", "Bank Holiday");

		lh.addStaticHoliday ("10-OCT-2006", "National Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("19-FEB-2007", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("20-FEB-2007", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("21-FEB-2007", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("22-FEB-2007", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("23-FEB-2007", "Bridging Day");

		lh.addStaticHoliday ("28-FEB-2007", "Memorial Day");

		lh.addStaticHoliday ("05-APR-2007", "Tomb Sweeping Day");

		lh.addStaticHoliday ("06-APR-2007", "Bridging Day");

		lh.addStaticHoliday ("01-MAY-2007", "May Day");

		lh.addStaticHoliday ("18-JUN-2007", "Bridging Day");

		lh.addStaticHoliday ("19-JUN-2007", "Dragon Boat Festival");

		lh.addStaticHoliday ("18-SEP-2007", "Special Holiday");

		lh.addStaticHoliday ("24-SEP-2007", "Bridging Day");

		lh.addStaticHoliday ("25-SEP-2007", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2007", "National Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("06-FEB-2008", "Lunar New Years Eve");

		lh.addStaticHoliday ("07-FEB-2008", "Lunar New Years Day");

		lh.addStaticHoliday ("08-FEB-2008", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("11-FEB-2008", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2008", "Memorial Day");

		lh.addStaticHoliday ("04-APR-2008", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2008", "May Day");

		lh.addStaticHoliday ("28-JUL-2008", "Special Holiday");

		lh.addStaticHoliday ("29-SEP-2008", "Special Holiday");

		lh.addStaticHoliday ("10-OCT-2008", "National Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2009", "Bridging Day");

		lh.addStaticHoliday ("26-JAN-2009", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("27-JAN-2009", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("28-JAN-2009", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("29-JAN-2009", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("30-JAN-2009", "Bridging Day");

		lh.addStaticHoliday ("01-MAY-2009", "May Day");

		lh.addStaticHoliday ("28-MAY-2009", "Dragon Boat Festival");

		lh.addStaticHoliday ("29-MAY-2009", "Dragon Boat Festival");

		lh.addStaticHoliday ("07-AUG-2009", "Special Holiday");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("13-FEB-2010", "Chinese New Year");

		lh.addStaticHoliday ("14-FEB-2010", "Chinese New Year");

		lh.addStaticHoliday ("15-FEB-2010", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("16-FEB-2010", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("17-FEB-2010", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("18-FEB-2010", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("19-FEB-2010", "Bridging Day");

		lh.addStaticHoliday ("28-FEB-2010", "Peace Day");

		lh.addStaticHoliday ("05-APR-2010", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2010", "Labour Day");

		lh.addStaticHoliday ("16-JUN-2010", "Dragon Boat Festival");

		lh.addStaticHoliday ("22-SEP-2010", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2010", "National Day");

		lh.addStaticHoliday ("02-FEB-2011", "Lunar New Years Eve");

		lh.addStaticHoliday ("03-FEB-2011", "Lunar New Years Day");

		lh.addStaticHoliday ("04-FEB-2011", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("07-FEB-2011", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2011", "Memorial Day");

		lh.addStaticHoliday ("04-APR-2011", "Childrens Day");

		lh.addStaticHoliday ("05-APR-2011", "Tomb Sweeping Day");

		lh.addStaticHoliday ("02-MAY-2011", "May Day Observed");

		lh.addStaticHoliday ("06-JUN-2011", "Dragon Boat Festival");

		lh.addStaticHoliday ("12-SEP-2011", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2011", "National Day");

		lh.addStaticHoliday ("23-JAN-2012", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("24-JAN-2012", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("25-JAN-2012", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("26-JAN-2012", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("27-JAN-2012", "Bridging Day");

		lh.addStaticHoliday ("28-FEB-2012", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2012", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2012", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2012", "May Day");

		lh.addStaticHoliday ("10-OCT-2012", "National Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2013", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("12-FEB-2013", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("13-FEB-2013", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("14-FEB-2013", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("15-FEB-2013", "Bridging Day");

		lh.addStaticHoliday ("28-FEB-2013", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2013", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2013", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2013", "May Day");

		lh.addStaticHoliday ("12-JUN-2013", "Dragon Boat Festival");

		lh.addStaticHoliday ("19-SEP-2013", "Moon Festival Day");

		lh.addStaticHoliday ("20-SEP-2013", "Bridging Day");

		lh.addStaticHoliday ("10-OCT-2013", "National Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("30-JAN-2014", "Lunar New Years Eve");

		lh.addStaticHoliday ("31-JAN-2014", "Lunar New Years Day");

		lh.addStaticHoliday ("03-FEB-2014", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("04-FEB-2014", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2014", "Memorial Day");

		lh.addStaticHoliday ("04-APR-2014", "Childrens Day");

		lh.addStaticHoliday ("01-MAY-2014", "May Day");

		lh.addStaticHoliday ("02-JUN-2014", "Dragon Boat Festival");

		lh.addStaticHoliday ("08-SEP-2014", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2014", "National Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("18-FEB-2015", "Lunar New Years Eve");

		lh.addStaticHoliday ("19-FEB-2015", "Lunar New Years Day");

		lh.addStaticHoliday ("20-FEB-2015", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("23-FEB-2015", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("01-MAY-2015", "May Day");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2016", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("09-FEB-2016", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("10-FEB-2016", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("11-FEB-2016", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("12-FEB-2016", "Bridging Day");

		lh.addStaticHoliday ("04-APR-2016", "Tomb Sweeping Day");

		lh.addStaticHoliday ("02-MAY-2016", "May Day Observed");

		lh.addStaticHoliday ("09-JUN-2016", "Dragon Boat Festival");

		lh.addStaticHoliday ("10-JUN-2016", "Bridging Day");

		lh.addStaticHoliday ("15-SEP-2016", "Moon Festival Day");

		lh.addStaticHoliday ("16-SEP-2016", "Bridging Day");

		lh.addStaticHoliday ("10-OCT-2016", "National Day");

		lh.addStaticHoliday ("27-JAN-2017", "Lunar New Years Eve");

		lh.addStaticHoliday ("30-JAN-2017", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("31-JAN-2017", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("01-FEB-2017", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2017", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2017", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2017", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2017", "May Day");

		lh.addStaticHoliday ("29-MAY-2017", "Bridging Day");

		lh.addStaticHoliday ("30-MAY-2017", "Dragon Boat Festival");

		lh.addStaticHoliday ("04-OCT-2017", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2017", "National Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("15-FEB-2018", "Lunar New Years Eve");

		lh.addStaticHoliday ("16-FEB-2018", "Lunar New Years Day");

		lh.addStaticHoliday ("19-FEB-2018", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("20-FEB-2018", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2018", "Memorial Day");

		lh.addStaticHoliday ("04-APR-2018", "Childrens Day");

		lh.addStaticHoliday ("05-APR-2018", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2018", "May Day");

		lh.addStaticHoliday ("18-JUN-2018", "Dragon Boat Festival");

		lh.addStaticHoliday ("24-SEP-2018", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2018", "National Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("04-FEB-2019", "Lunar New Years Eve");

		lh.addStaticHoliday ("05-FEB-2019", "Lunar New Years Day");

		lh.addStaticHoliday ("06-FEB-2019", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("07-FEB-2019", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("28-FEB-2019", "Memorial Day");

		lh.addStaticHoliday ("04-APR-2019", "Childrens Day");

		lh.addStaticHoliday ("05-APR-2019", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2019", "May Day");

		lh.addStaticHoliday ("07-JUN-2019", "Dragon Boat Festival");

		lh.addStaticHoliday ("13-SEP-2019", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2019", "National Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("24-JAN-2020", "Lunar New Years Eve");

		lh.addStaticHoliday ("27-JAN-2020", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("28-JAN-2020", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("29-JAN-2020", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2020", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2020", "Childrens Day");

		lh.addStaticHoliday ("01-MAY-2020", "May Day");

		lh.addStaticHoliday ("25-JUN-2020", "Dragon Boat Festival");

		lh.addStaticHoliday ("26-JUN-2020", "Bridging Day");

		lh.addStaticHoliday ("01-OCT-2020", "Moon Festival Day");

		lh.addStaticHoliday ("02-OCT-2020", "Bridging Day");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2021", "Lunar New Years Eve");

		lh.addStaticHoliday ("12-FEB-2021", "Lunar New Years Day");

		lh.addStaticHoliday ("15-FEB-2021", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("16-FEB-2021", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("14-JUN-2021", "Dragon Boat Festival");

		lh.addStaticHoliday ("20-SEP-2021", "Bridging Day");

		lh.addStaticHoliday ("21-SEP-2021", "Moon Festival Day");

		lh.addStaticHoliday ("31-JAN-2022", "Lunar New Years Eve");

		lh.addStaticHoliday ("01-FEB-2022", "Lunar New Years Day");

		lh.addStaticHoliday ("02-FEB-2022", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("03-FEB-2022", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("28-FEB-2022", "Memorial Day");

		lh.addStaticHoliday ("04-APR-2022", "Childrens Day");

		lh.addStaticHoliday ("05-APR-2022", "Tomb Sweeping Day");

		lh.addStaticHoliday ("02-MAY-2022", "May Day Observed");

		lh.addStaticHoliday ("03-JUN-2022", "Dragon Boat Festival");

		lh.addStaticHoliday ("10-OCT-2022", "National Day");

		lh.addStaticHoliday ("23-JAN-2023", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("24-JAN-2023", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("25-JAN-2023", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("26-JAN-2023", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("27-JAN-2023", "Bridging Day");

		lh.addStaticHoliday ("28-FEB-2023", "Memorial Day");

		lh.addStaticHoliday ("04-APR-2023", "Childrens Day");

		lh.addStaticHoliday ("05-APR-2023", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2023", "May Day");

		lh.addStaticHoliday ("22-JUN-2023", "Dragon Boat Festival");

		lh.addStaticHoliday ("23-JUN-2023", "Bridging Day");

		lh.addStaticHoliday ("29-SEP-2023", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2023", "National Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("09-FEB-2024", "Lunar New Years Eve");

		lh.addStaticHoliday ("12-FEB-2024", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("13-FEB-2024", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("14-FEB-2024", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2024", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2024", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2024", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2024", "May Day");

		lh.addStaticHoliday ("10-JUN-2024", "Dragon Boat Festival");

		lh.addStaticHoliday ("16-SEP-2024", "Bridging Day");

		lh.addStaticHoliday ("17-SEP-2024", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2024", "National Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("27-JAN-2025", "Bridging Day");

		lh.addStaticHoliday ("28-JAN-2025", "Lunar New Years Eve");

		lh.addStaticHoliday ("29-JAN-2025", "Lunar New Years Day");

		lh.addStaticHoliday ("30-JAN-2025", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("31-JAN-2025", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("28-FEB-2025", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2025", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2025", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2025", "May Day");

		lh.addStaticHoliday ("06-OCT-2025", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2025", "National Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("16-FEB-2026", "Lunar New Years Eve");

		lh.addStaticHoliday ("17-FEB-2026", "Lunar New Years Day");

		lh.addStaticHoliday ("18-FEB-2026", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("19-FEB-2026", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAY-2026", "May Day");

		lh.addStaticHoliday ("19-JUN-2026", "Dragon Boat Festival");

		lh.addStaticHoliday ("25-SEP-2026", "Moon Festival Day");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("05-FEB-2027", "Lunar New Years Eve");

		lh.addStaticHoliday ("08-FEB-2027", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("09-FEB-2027", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("10-FEB-2027", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("05-APR-2027", "Tomb Sweeping Day");

		lh.addStaticHoliday ("09-JUN-2027", "Dragon Boat Festival");

		lh.addStaticHoliday ("15-SEP-2027", "Moon Festival Day");

		lh.addStaticHoliday ("24-JAN-2028", "Bridging Day");

		lh.addStaticHoliday ("25-JAN-2028", "Lunar New Years Eve");

		lh.addStaticHoliday ("26-JAN-2028", "Lunar New Years Day");

		lh.addStaticHoliday ("27-JAN-2028", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("28-JAN-2028", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("28-FEB-2028", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2028", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2028", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2028", "May Day");

		lh.addStaticHoliday ("02-OCT-2028", "Bridging Day");

		lh.addStaticHoliday ("03-OCT-2028", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2028", "National Day");

		lh.addStaticHoliday ("01-JAN-2029", "New Years Day");

		lh.addStaticHoliday ("12-FEB-2029", "Lunar New Years Eve");

		lh.addStaticHoliday ("13-FEB-2029", "Lunar New Years Day");

		lh.addStaticHoliday ("14-FEB-2029", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("15-FEB-2029", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("28-FEB-2029", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2029", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2029", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2029", "May Day");

		lh.addStaticHoliday ("10-OCT-2029", "National Day");

		lh.addStaticHoliday ("01-JAN-2030", "New Years Day");

		lh.addStaticHoliday ("04-FEB-2030", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("05-FEB-2030", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("06-FEB-2030", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("07-FEB-2030", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("08-FEB-2030", "Bridging Day");

		lh.addStaticHoliday ("28-FEB-2030", "Memorial Day");

		lh.addStaticHoliday ("04-APR-2030", "Childrens Day");

		lh.addStaticHoliday ("05-APR-2030", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2030", "May Day");

		lh.addStaticHoliday ("05-JUN-2030", "Dragon Boat Festival");

		lh.addStaticHoliday ("12-SEP-2030", "Moon Festival Day");

		lh.addStaticHoliday ("13-SEP-2030", "Bridging Day");

		lh.addStaticHoliday ("10-OCT-2030", "National Day");

		lh.addStaticHoliday ("01-JAN-2031", "New Years Day");

		lh.addStaticHoliday ("22-JAN-2031", "Lunar New Years Eve");

		lh.addStaticHoliday ("23-JAN-2031", "Lunar New Years Day");

		lh.addStaticHoliday ("24-JAN-2031", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("27-JAN-2031", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2031", "Memorial Day");

		lh.addStaticHoliday ("04-APR-2031", "Childrens Day");

		lh.addStaticHoliday ("01-MAY-2031", "May Day");

		lh.addStaticHoliday ("23-JUN-2031", "Bridging Day");

		lh.addStaticHoliday ("24-JUN-2031", "Dragon Boat Festival");

		lh.addStaticHoliday ("01-OCT-2031", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2031", "National Day");

		lh.addStaticHoliday ("01-JAN-2032", "New Years Day");

		lh.addStaticHoliday ("09-FEB-2032", "Bridging Day");

		lh.addStaticHoliday ("10-FEB-2032", "Lunar New Years Eve");

		lh.addStaticHoliday ("11-FEB-2032", "Lunar New Years Day");

		lh.addStaticHoliday ("12-FEB-2032", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("13-FEB-2032", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("31-JAN-2033", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("01-FEB-2033", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("02-FEB-2033", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("03-FEB-2033", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("04-FEB-2033", "Bridging Day");

		lh.addStaticHoliday ("28-FEB-2033", "Memorial Day");

		lh.addStaticHoliday ("04-APR-2033", "Tomb Sweeping Day");

		lh.addStaticHoliday ("02-MAY-2033", "May Day Observed");

		lh.addStaticHoliday ("01-JUN-2033", "Dragon Boat Festival");

		lh.addStaticHoliday ("08-SEP-2033", "Moon Festival Day");

		lh.addStaticHoliday ("09-SEP-2033", "Bridging Day");

		lh.addStaticHoliday ("10-OCT-2033", "National Day");

		lh.addStaticHoliday ("20-FEB-2034", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("21-FEB-2034", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("22-FEB-2034", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("23-FEB-2034", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("24-FEB-2034", "Bridging Day");

		lh.addStaticHoliday ("28-FEB-2034", "Memorial Day");

		lh.addStaticHoliday ("04-APR-2034", "Childrens Day");

		lh.addStaticHoliday ("05-APR-2034", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2034", "May Day");

		lh.addStaticHoliday ("19-JUN-2034", "Bridging Day");

		lh.addStaticHoliday ("20-JUN-2034", "Dragon Boat Festival");

		lh.addStaticHoliday ("27-SEP-2034", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2034", "National Day");

		lh.addStaticHoliday ("01-JAN-2035", "New Years Day");

		lh.addStaticHoliday ("07-FEB-2035", "Lunar New Years Eve");

		lh.addStaticHoliday ("08-FEB-2035", "Lunar New Years Day");

		lh.addStaticHoliday ("09-FEB-2035", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("12-FEB-2035", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2035", "Memorial Day");

		lh.addStaticHoliday ("04-APR-2035", "Childrens Day");

		lh.addStaticHoliday ("05-APR-2035", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2035", "May Day");

		lh.addStaticHoliday ("10-OCT-2035", "National Day");

		lh.addStaticHoliday ("01-JAN-2036", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2036", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("29-JAN-2036", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("30-JAN-2036", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("31-JAN-2036", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("01-FEB-2036", "Bridging Day");

		lh.addStaticHoliday ("28-FEB-2036", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2036", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2036", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2036", "May Day");

		lh.addStaticHoliday ("30-MAY-2036", "Dragon Boat Festival");

		lh.addStaticHoliday ("10-OCT-2036", "National Day");

		lh.addStaticHoliday ("01-JAN-2037", "New Years Day");

		lh.addStaticHoliday ("16-FEB-2037", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("17-FEB-2037", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("18-FEB-2037", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("19-FEB-2037", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("20-FEB-2037", "Bridging Day");

		lh.addStaticHoliday ("03-APR-2037", "Childrens Day");

		lh.addStaticHoliday ("01-MAY-2037", "May Day");

		lh.addStaticHoliday ("18-JUN-2037", "Dragon Boat Festival");

		lh.addStaticHoliday ("19-JUN-2037", "Bridging Day");

		lh.addStaticHoliday ("24-SEP-2037", "Moon Festival Day");

		lh.addStaticHoliday ("25-SEP-2037", "Bridging Day");

		lh.addStaticHoliday ("01-JAN-2038", "New Years Day");

		lh.addStaticHoliday ("03-FEB-2038", "Lunar New Years Eve");

		lh.addStaticHoliday ("04-FEB-2038", "Lunar New Years Day");

		lh.addStaticHoliday ("05-FEB-2038", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("08-FEB-2038", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("05-APR-2038", "Tomb Sweeping Day");

		lh.addStaticHoliday ("07-JUN-2038", "Dragon Boat Festival");

		lh.addStaticHoliday ("13-SEP-2038", "Moon Festival Day");

		lh.addStaticHoliday ("24-JAN-2039", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("25-JAN-2039", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2039", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("27-JAN-2039", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-JAN-2039", "Bridging Day");

		lh.addStaticHoliday ("28-FEB-2039", "Memorial Day");

		lh.addStaticHoliday ("04-APR-2039", "Childrens Day");

		lh.addStaticHoliday ("05-APR-2039", "Tomb Sweeping Day");

		lh.addStaticHoliday ("02-MAY-2039", "May Day Observed");

		lh.addStaticHoliday ("27-MAY-2039", "Dragon Boat Festival");

		lh.addStaticHoliday ("10-OCT-2039", "National Day");

		lh.addStaticHoliday ("13-FEB-2040", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("14-FEB-2040", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("15-FEB-2040", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("16-FEB-2040", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("17-FEB-2040", "Bridging Day");

		lh.addStaticHoliday ("28-FEB-2040", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2040", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2040", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2040", "May Day");

		lh.addStaticHoliday ("14-JUN-2040", "Dragon Boat Festival");

		lh.addStaticHoliday ("15-JUN-2040", "Bridging Day");

		lh.addStaticHoliday ("20-SEP-2040", "Moon Festival Day");

		lh.addStaticHoliday ("21-SEP-2040", "Bridging Day");

		lh.addStaticHoliday ("10-OCT-2040", "National Day");

		lh.addStaticHoliday ("01-JAN-2041", "New Years Day");

		lh.addStaticHoliday ("31-JAN-2041", "Lunar New Years Eve");

		lh.addStaticHoliday ("01-FEB-2041", "Lunar New Years Day");

		lh.addStaticHoliday ("04-FEB-2041", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("05-FEB-2041", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2041", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2041", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2041", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2041", "May Day");

		lh.addStaticHoliday ("03-JUN-2041", "Dragon Boat Festival");

		lh.addStaticHoliday ("09-SEP-2041", "Bridging Day");

		lh.addStaticHoliday ("10-SEP-2041", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2041", "National Day");

		lh.addStaticHoliday ("01-JAN-2042", "New Years Day");

		lh.addStaticHoliday ("20-JAN-2042", "Bridging Day");

		lh.addStaticHoliday ("21-JAN-2042", "Lunar New Years Eve");

		lh.addStaticHoliday ("22-JAN-2042", "Lunar New Years Day");

		lh.addStaticHoliday ("23-JAN-2042", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("24-JAN-2042", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("28-FEB-2042", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2042", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2042", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2042", "May Day");

		lh.addStaticHoliday ("10-OCT-2042", "National Day");

		lh.addStaticHoliday ("01-JAN-2043", "New Years Day");

		lh.addStaticHoliday ("09-FEB-2043", "Lunar New Years Eve");

		lh.addStaticHoliday ("10-FEB-2043", "Lunar New Years Day");

		lh.addStaticHoliday ("11-FEB-2043", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("12-FEB-2043", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAY-2043", "May Day");

		lh.addStaticHoliday ("11-JUN-2043", "Dragon Boat Festival");

		lh.addStaticHoliday ("12-JUN-2043", "Bridging Day");

		lh.addStaticHoliday ("17-SEP-2043", "Moon Festival Day");

		lh.addStaticHoliday ("18-SEP-2043", "Bridging Day");

		lh.addStaticHoliday ("01-JAN-2044", "New Years Day");

		lh.addStaticHoliday ("29-JAN-2044", "Lunar New Years Eve");

		lh.addStaticHoliday ("01-FEB-2044", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("02-FEB-2044", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("03-FEB-2044", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("04-APR-2044", "Tomb Sweeping Day");

		lh.addStaticHoliday ("02-MAY-2044", "May Day Observed");

		lh.addStaticHoliday ("30-MAY-2044", "Bridging Day");

		lh.addStaticHoliday ("31-MAY-2044", "Dragon Boat Festival");

		lh.addStaticHoliday ("05-OCT-2044", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2044", "National Day");

		lh.addStaticHoliday ("16-FEB-2045", "Lunar New Years Eve");

		lh.addStaticHoliday ("17-FEB-2045", "Lunar New Years Day");

		lh.addStaticHoliday ("20-FEB-2045", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("21-FEB-2045", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2045", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2045", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2045", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2045", "May Day");

		lh.addStaticHoliday ("19-JUN-2045", "Dragon Boat Festival");

		lh.addStaticHoliday ("25-SEP-2045", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2045", "National Day");

		lh.addStaticHoliday ("01-JAN-2046", "New Years Day");

		lh.addStaticHoliday ("05-FEB-2046", "Lunar New Years Eve");

		lh.addStaticHoliday ("06-FEB-2046", "Lunar New Years Day");

		lh.addStaticHoliday ("07-FEB-2046", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("08-FEB-2046", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("28-FEB-2046", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2046", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2046", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2046", "May Day");

		lh.addStaticHoliday ("08-JUN-2046", "Dragon Boat Festival");

		lh.addStaticHoliday ("10-OCT-2046", "National Day");

		lh.addStaticHoliday ("01-JAN-2047", "New Years Day");

		lh.addStaticHoliday ("25-JAN-2047", "Lunar New Years Eve");

		lh.addStaticHoliday ("28-JAN-2047", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("29-JAN-2047", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("30-JAN-2047", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2047", "Memorial Day");

		lh.addStaticHoliday ("04-APR-2047", "Childrens Day");

		lh.addStaticHoliday ("05-APR-2047", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2047", "May Day");

		lh.addStaticHoliday ("29-MAY-2047", "Dragon Boat Festival");

		lh.addStaticHoliday ("04-OCT-2047", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2047", "National Day");

		lh.addStaticHoliday ("01-JAN-2048", "New Years Day");

		lh.addStaticHoliday ("13-FEB-2048", "Lunar New Years Eve");

		lh.addStaticHoliday ("14-FEB-2048", "Lunar New Years Day");

		lh.addStaticHoliday ("17-FEB-2048", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("18-FEB-2048", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2048", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2048", "Childrens Day");

		lh.addStaticHoliday ("01-MAY-2048", "May Day");

		lh.addStaticHoliday ("15-JUN-2048", "Dragon Boat Festival");

		lh.addStaticHoliday ("21-SEP-2048", "Bridging Day");

		lh.addStaticHoliday ("22-SEP-2048", "Moon Festival Day");

		lh.addStaticHoliday ("01-JAN-2049", "New Years Day");

		lh.addStaticHoliday ("01-FEB-2049", "Lunar New Years Eve");

		lh.addStaticHoliday ("02-FEB-2049", "Lunar New Years Day");

		lh.addStaticHoliday ("03-FEB-2049", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("04-FEB-2049", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("04-JUN-2049", "Dragon Boat Festival");

		lh.addStaticHoliday ("24-JAN-2050", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("25-JAN-2050", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2050", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("27-JAN-2050", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-JAN-2050", "Bridging Day");

		lh.addStaticHoliday ("28-FEB-2050", "Memorial Day");

		lh.addStaticHoliday ("04-APR-2050", "Tomb Sweeping Day");

		lh.addStaticHoliday ("02-MAY-2050", "May Day Observed");

		lh.addStaticHoliday ("23-JUN-2050", "Dragon Boat Festival");

		lh.addStaticHoliday ("24-JUN-2050", "Bridging Day");

		lh.addStaticHoliday ("30-SEP-2050", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2050", "National Day");

		lh.addStaticHoliday ("10-FEB-2051", "Lunar New Years Eve");

		lh.addStaticHoliday ("13-FEB-2051", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("14-FEB-2051", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("15-FEB-2051", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2051", "Memorial Day");

		lh.addStaticHoliday ("04-APR-2051", "Childrens Day");

		lh.addStaticHoliday ("05-APR-2051", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2051", "May Day");

		lh.addStaticHoliday ("12-JUN-2051", "Bridging Day");

		lh.addStaticHoliday ("13-JUN-2051", "Dragon Boat Festival");

		lh.addStaticHoliday ("18-SEP-2051", "Bridging Day");

		lh.addStaticHoliday ("19-SEP-2051", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2051", "National Day");

		lh.addStaticHoliday ("01-JAN-2052", "New Years Day");

		lh.addStaticHoliday ("31-JAN-2052", "Lunar New Years Eve");

		lh.addStaticHoliday ("01-FEB-2052", "Lunar New Years Day");

		lh.addStaticHoliday ("02-FEB-2052", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("05-FEB-2052", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2052", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2052", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2052", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2052", "May Day");

		lh.addStaticHoliday ("10-OCT-2052", "National Day");

		lh.addStaticHoliday ("01-JAN-2053", "New Years Day");

		lh.addStaticHoliday ("17-FEB-2053", "Bridging Day");

		lh.addStaticHoliday ("18-FEB-2053", "Lunar New Years Eve");

		lh.addStaticHoliday ("19-FEB-2053", "Lunar New Years Day");

		lh.addStaticHoliday ("20-FEB-2053", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("21-FEB-2053", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("28-FEB-2053", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2053", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2053", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2053", "May Day");

		lh.addStaticHoliday ("20-JUN-2053", "Dragon Boat Festival");

		lh.addStaticHoliday ("26-SEP-2053", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2053", "National Day");

		lh.addStaticHoliday ("01-JAN-2054", "New Years Day");

		lh.addStaticHoliday ("09-FEB-2054", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("10-FEB-2054", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("11-FEB-2054", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("12-FEB-2054", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("13-FEB-2054", "Bridging Day");

		lh.addStaticHoliday ("03-APR-2054", "Childrens Day");

		lh.addStaticHoliday ("01-MAY-2054", "May Day");

		lh.addStaticHoliday ("10-JUN-2054", "Dragon Boat Festival");

		lh.addStaticHoliday ("16-SEP-2054", "Moon Festival Day");

		lh.addStaticHoliday ("01-JAN-2055", "New Years Day");

		lh.addStaticHoliday ("27-JAN-2055", "Lunar New Years Eve");

		lh.addStaticHoliday ("28-JAN-2055", "Lunar New Years Day");

		lh.addStaticHoliday ("29-JAN-2055", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-FEB-2055", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("05-APR-2055", "Tomb Sweeping Day");

		lh.addStaticHoliday ("04-OCT-2055", "Bridging Day");

		lh.addStaticHoliday ("05-OCT-2055", "Moon Festival Day");

		lh.addStaticHoliday ("14-FEB-2056", "Lunar New Years Eve");

		lh.addStaticHoliday ("15-FEB-2056", "Lunar New Years Day");

		lh.addStaticHoliday ("16-FEB-2056", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("17-FEB-2056", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("28-FEB-2056", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2056", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2056", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2056", "May Day");

		lh.addStaticHoliday ("10-OCT-2056", "National Day");

		lh.addStaticHoliday ("01-JAN-2057", "New Years Day");

		lh.addStaticHoliday ("05-FEB-2057", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("06-FEB-2057", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("07-FEB-2057", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("08-FEB-2057", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("09-FEB-2057", "Bridging Day");

		lh.addStaticHoliday ("28-FEB-2057", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2057", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2057", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2057", "May Day");

		lh.addStaticHoliday ("06-JUN-2057", "Dragon Boat Festival");

		lh.addStaticHoliday ("13-SEP-2057", "Moon Festival Day");

		lh.addStaticHoliday ("14-SEP-2057", "Bridging Day");

		lh.addStaticHoliday ("10-OCT-2057", "National Day");

		lh.addStaticHoliday ("01-JAN-2058", "New Years Day");

		lh.addStaticHoliday ("23-JAN-2058", "Lunar New Years Eve");

		lh.addStaticHoliday ("24-JAN-2058", "Lunar New Years Day");

		lh.addStaticHoliday ("25-JAN-2058", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("28-JAN-2058", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2058", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2058", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2058", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2058", "May Day");

		lh.addStaticHoliday ("24-JUN-2058", "Bridging Day");

		lh.addStaticHoliday ("25-JUN-2058", "Dragon Boat Festival");

		lh.addStaticHoliday ("02-OCT-2058", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2058", "National Day");

		lh.addStaticHoliday ("01-JAN-2059", "New Years Day");

		lh.addStaticHoliday ("10-FEB-2059", "Bridging Day");

		lh.addStaticHoliday ("11-FEB-2059", "Lunar New Years Eve");

		lh.addStaticHoliday ("12-FEB-2059", "Lunar New Years Day");

		lh.addStaticHoliday ("13-FEB-2059", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("14-FEB-2059", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("28-FEB-2059", "Memorial Day");

		lh.addStaticHoliday ("04-APR-2059", "Childrens Day");

		lh.addStaticHoliday ("01-MAY-2059", "May Day");

		lh.addStaticHoliday ("10-OCT-2059", "National Day");

		lh.addStaticHoliday ("01-JAN-2060", "New Years Day");

		lh.addStaticHoliday ("02-FEB-2060", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("03-FEB-2060", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("04-FEB-2060", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("05-FEB-2060", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("06-FEB-2060", "Bridging Day");

		lh.addStaticHoliday ("03-JUN-2060", "Dragon Boat Festival");

		lh.addStaticHoliday ("04-JUN-2060", "Bridging Day");

		lh.addStaticHoliday ("09-SEP-2060", "Moon Festival Day");

		lh.addStaticHoliday ("10-SEP-2060", "Bridging Day");

		lh.addStaticHoliday ("20-JAN-2061", "Lunar New Years Eve");

		lh.addStaticHoliday ("21-JAN-2061", "Lunar New Years Day");

		lh.addStaticHoliday ("24-JAN-2061", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("25-JAN-2061", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2061", "Memorial Day");

		lh.addStaticHoliday ("04-APR-2061", "Tomb Sweeping Day");

		lh.addStaticHoliday ("02-MAY-2061", "May Day Observed");

		lh.addStaticHoliday ("22-JUN-2061", "Dragon Boat Festival");

		lh.addStaticHoliday ("28-SEP-2061", "Moon Festival Day");

		lh.addStaticHoliday ("10-OCT-2061", "National Day");

		lh.addStaticHoliday ("08-FEB-2062", "Lunar New Years Eve");

		lh.addStaticHoliday ("09-FEB-2062", "Lunar New Years Day");

		lh.addStaticHoliday ("10-FEB-2062", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("13-FEB-2062", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("28-FEB-2062", "Memorial Day");

		lh.addStaticHoliday ("03-APR-2062", "Childrens Day");

		lh.addStaticHoliday ("04-APR-2062", "Tomb Sweeping Day");

		lh.addStaticHoliday ("01-MAY-2062", "May Day");

		lh.addStaticHoliday ("10-OCT-2062", "National Day");

		lh.addStandardWeekend();

		return lh;
	}
}
