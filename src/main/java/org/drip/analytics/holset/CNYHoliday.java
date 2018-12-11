
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

public class CNYHoliday implements org.drip.analytics.holset.LocationHoliday {
	public CNYHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "CNY";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("16-FEB-1999", "Chinese New Year");

		lh.addStaticHoliday ("17-FEB-1999", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("18-FEB-1999", "Third Day of Chinese New Year");

		lh.addStaticHoliday ("19-FEB-1999", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("20-FEB-1999", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("22-FEB-1999", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("01-MAY-1999", "Labour Day");

		lh.addStaticHoliday ("01-OCT-1999", "National Day");

		lh.addStaticHoliday ("02-OCT-1999", "National Day");

		lh.addStaticHoliday ("20-DEC-1999", "Special Holiday");

		lh.addStaticHoliday ("31-DEC-1999", "BANK HOLIDAY");

		lh.addStaticHoliday ("03-JAN-2000", "New Years Day Observed");

		lh.addStaticHoliday ("07-FEB-2000", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("08-FEB-2000", "Third Day of Chinese New Year Observed");

		lh.addStaticHoliday ("09-FEB-2000", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("10-FEB-2000", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("11-FEB-2000", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("01-MAY-2000", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2000", "Labour Day");

		lh.addStaticHoliday ("03-MAY-2000", "Labour Day");

		lh.addStaticHoliday ("04-MAY-2000", "Bridging Day");

		lh.addStaticHoliday ("05-MAY-2000", "Bridging Day");

		lh.addStaticHoliday ("02-OCT-2000", "National Day");

		lh.addStaticHoliday ("03-OCT-2000", "National Day");

		lh.addStaticHoliday ("04-OCT-2000", "National Day Observed");

		lh.addStaticHoliday ("05-OCT-2000", "Bridging Day");

		lh.addStaticHoliday ("06-OCT-2000", "Bridging Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("24-JAN-2001", "Chinese New Year");

		lh.addStaticHoliday ("25-JAN-2001", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("26-JAN-2001", "Third Day of Chinese New Year");

		lh.addStaticHoliday ("29-JAN-2001", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("30-JAN-2001", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("01-MAY-2001", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2001", "Labour Day");

		lh.addStaticHoliday ("03-MAY-2001", "Labour Day");

		lh.addStaticHoliday ("04-MAY-2001", "BANK HOLIDAY");

		lh.addStaticHoliday ("07-MAY-2001", "BANK HOLIDAY");

		lh.addStaticHoliday ("01-OCT-2001", "National Day");

		lh.addStaticHoliday ("02-OCT-2001", "National Day");

		lh.addStaticHoliday ("03-OCT-2001", "National Day");

		lh.addStaticHoliday ("04-OCT-2001", "National Day");

		lh.addStaticHoliday ("05-OCT-2001", "National Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2002", "New Years Holiday");

		lh.addStaticHoliday ("03-JAN-2002", "New Years Holiday");

		lh.addStaticHoliday ("12-FEB-2002", "Chinese New Year");

		lh.addStaticHoliday ("13-FEB-2002", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("14-FEB-2002", "Third Day of Chinese New Year");

		lh.addStaticHoliday ("15-FEB-2002", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("18-FEB-2002", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("01-MAY-2002", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2002", "Labour Day");

		lh.addStaticHoliday ("03-MAY-2002", "Labour Day");

		lh.addStaticHoliday ("06-MAY-2002", "Labour Day");

		lh.addStaticHoliday ("07-MAY-2002", "Labour Day");

		lh.addStaticHoliday ("01-OCT-2002", "National Day");

		lh.addStaticHoliday ("02-OCT-2002", "National Day");

		lh.addStaticHoliday ("03-OCT-2002", "National Day");

		lh.addStaticHoliday ("04-OCT-2002", "National Day");

		lh.addStaticHoliday ("07-OCT-2002", "National Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("03-FEB-2003", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("04-FEB-2003", "Third Day of Chinese New Year Observed");

		lh.addStaticHoliday ("05-FEB-2003", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("06-FEB-2003", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("07-FEB-2003", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("01-MAY-2003", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2003", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2003", "Labour Day");

		lh.addStaticHoliday ("01-OCT-2003", "National Day");

		lh.addStaticHoliday ("02-OCT-2003", "National Day");

		lh.addStaticHoliday ("03-OCT-2003", "National Day");

		lh.addStaticHoliday ("06-OCT-2003", "National Day");

		lh.addStaticHoliday ("07-OCT-2003", "National Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("22-JAN-2004", "Chinese New Year");

		lh.addStaticHoliday ("23-JAN-2004", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("26-JAN-2004", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("27-JAN-2004", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("28-JAN-2004", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("03-MAY-2004", "Labour Day");

		lh.addStaticHoliday ("04-MAY-2004", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2004", "Labour Day");

		lh.addStaticHoliday ("06-MAY-2004", "Labour Day");

		lh.addStaticHoliday ("07-MAY-2004", "Labour Day");

		lh.addStaticHoliday ("01-OCT-2004", "National Day");

		lh.addStaticHoliday ("04-OCT-2004", "National Day");

		lh.addStaticHoliday ("05-OCT-2004", "National Day");

		lh.addStaticHoliday ("06-OCT-2004", "National Day");

		lh.addStaticHoliday ("07-OCT-2004", "National Day");

		lh.addStaticHoliday ("03-JAN-2005", "New Years Day Observed");

		lh.addStaticHoliday ("09-FEB-2005", "Chinese New Year");

		lh.addStaticHoliday ("10-FEB-2005", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("11-FEB-2005", "Third Day of Chinese New Year");

		lh.addStaticHoliday ("14-FEB-2005", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("15-FEB-2005", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("02-MAY-2005", "Labour Day");

		lh.addStaticHoliday ("03-MAY-2005", "Labour Day");

		lh.addStaticHoliday ("04-MAY-2005", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2005", "Labour Day");

		lh.addStaticHoliday ("06-MAY-2005", "Labour Day");

		lh.addStaticHoliday ("03-OCT-2005", "National Day");

		lh.addStaticHoliday ("04-OCT-2005", "National Day");

		lh.addStaticHoliday ("05-OCT-2005", "National Day");

		lh.addStaticHoliday ("06-OCT-2005", "National Day");

		lh.addStaticHoliday ("07-OCT-2005", "National Day");

		lh.addStaticHoliday ("02-JAN-2006", "New Years Day Observed");

		lh.addStaticHoliday ("03-JAN-2006", "New Years Holiday");

		lh.addStaticHoliday ("30-JAN-2006", "Chinese New Year Observed");

		lh.addStaticHoliday ("31-JAN-2006", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("01-FEB-2006", "Third Day of Chinese New Year Observed");

		lh.addStaticHoliday ("02-FEB-2006", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("03-FEB-2006", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("01-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("03-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("04-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("02-OCT-2006", "National Day");

		lh.addStaticHoliday ("03-OCT-2006", "National Day");

		lh.addStaticHoliday ("04-OCT-2006", "National Day");

		lh.addStaticHoliday ("05-OCT-2006", "National Day");

		lh.addStaticHoliday ("06-OCT-2006", "National Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2007", "New Years Holiday");

		lh.addStaticHoliday ("03-JAN-2007", "New Years Holiday");

		lh.addStaticHoliday ("19-FEB-2007", "Chinese New Year Observed");

		lh.addStaticHoliday ("20-FEB-2007", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("21-FEB-2007", "Third Day of Chinese New Year Observed");

		lh.addStaticHoliday ("22-FEB-2007", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("23-FEB-2007", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("01-MAY-2007", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2007", "Labour Day");

		lh.addStaticHoliday ("03-MAY-2007", "Labour Day");

		lh.addStaticHoliday ("04-MAY-2007", "Labour Day");

		lh.addStaticHoliday ("07-MAY-2007", "Labour Day");

		lh.addStaticHoliday ("01-OCT-2007", "National Day");

		lh.addStaticHoliday ("02-OCT-2007", "National Day");

		lh.addStaticHoliday ("03-OCT-2007", "National Day");

		lh.addStaticHoliday ("04-OCT-2007", "National Day");

		lh.addStaticHoliday ("05-OCT-2007", "National Day");

		lh.addStaticHoliday ("31-DEC-2007", "New Years Eve");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("06-FEB-2008", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("07-FEB-2008", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("08-FEB-2008", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("11-FEB-2008", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("12-FEB-2008", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2008", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2008", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2008", "Bank Holiday");

		lh.addStaticHoliday ("09-JUN-2008", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("15-SEP-2008", "Mid-Autumn Festival");

		lh.addStaticHoliday ("29-SEP-2008", "National Day");

		lh.addStaticHoliday ("30-SEP-2008", "National Day");

		lh.addStaticHoliday ("01-OCT-2008", "National Day");

		lh.addStaticHoliday ("02-OCT-2008", "National Day");

		lh.addStaticHoliday ("03-OCT-2008", "National Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2009", "New Years Holiday");

		lh.addStaticHoliday ("26-JAN-2009", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("27-JAN-2009", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("28-JAN-2009", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("29-JAN-2009", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("30-JAN-2009", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("06-APR-2009", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("01-MAY-2009", "Labour Day");

		lh.addStaticHoliday ("28-MAY-2009", "Tuen Ng Festival");

		lh.addStaticHoliday ("29-MAY-2009", "Bank Holiday");

		lh.addStaticHoliday ("01-OCT-2009", "National Day");

		lh.addStaticHoliday ("02-OCT-2009", "National Day");

		lh.addStaticHoliday ("05-OCT-2009", "National Day");

		lh.addStaticHoliday ("06-OCT-2009", "National Day");

		lh.addStaticHoliday ("07-OCT-2009", "National Day");

		lh.addStaticHoliday ("08-OCT-2009", "National Day");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("15-FEB-2010", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("16-FEB-2010", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("17-FEB-2010", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("18-FEB-2010", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("19-FEB-2010", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-APR-2010", "Ching Ming Festival");

		lh.addStaticHoliday ("03-MAY-2010", "Labour Day Observed");

		lh.addStaticHoliday ("14-JUN-2010", "Bank Holiday");

		lh.addStaticHoliday ("15-JUN-2010", "Bank Holiday");

		lh.addStaticHoliday ("16-JUN-2010", "Tuen Ng Festival");

		lh.addStaticHoliday ("22-SEP-2010", "Mid-Autumn Festival");

		lh.addStaticHoliday ("23-SEP-2010", "Bank Holiday");

		lh.addStaticHoliday ("24-SEP-2010", "Bank Holiday");

		lh.addStaticHoliday ("01-OCT-2010", "National Day");

		lh.addStaticHoliday ("04-OCT-2010", "Bank Holiday");

		lh.addStaticHoliday ("05-OCT-2010", "Bank Holiday");

		lh.addStaticHoliday ("06-OCT-2010", "Bank Holiday");

		lh.addStaticHoliday ("07-OCT-2010", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2011", "New Years Day Observed");

		lh.addStaticHoliday ("02-FEB-2011", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("03-FEB-2011", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-FEB-2011", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("07-FEB-2011", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("08-FEB-2011", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2011", "Bank Holiday");

		lh.addStaticHoliday ("05-APR-2011", "Ching Ming Festival");

		lh.addStaticHoliday ("02-MAY-2011", "Labour Day Observed");

		lh.addStaticHoliday ("06-JUN-2011", "Tuen Ng Festival");

		lh.addStaticHoliday ("12-SEP-2011", "Mid-Autumn Festival");

		lh.addStaticHoliday ("03-OCT-2011", "National Day");

		lh.addStaticHoliday ("04-OCT-2011", "National Day");

		lh.addStaticHoliday ("05-OCT-2011", "National Day");

		lh.addStaticHoliday ("06-OCT-2011", "National Day");

		lh.addStaticHoliday ("07-OCT-2011", "National Day");

		lh.addStaticHoliday ("02-JAN-2012", "New Years Day Observed");

		lh.addStaticHoliday ("23-JAN-2012", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("24-JAN-2012", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("25-JAN-2012", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("26-JAN-2012", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("27-JAN-2012", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2012", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2012", "Labour Day");

		lh.addStaticHoliday ("25-JUN-2012", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("01-OCT-2012", "National Day");

		lh.addStaticHoliday ("02-OCT-2012", "National Day");

		lh.addStaticHoliday ("03-OCT-2012", "National Day");

		lh.addStaticHoliday ("04-OCT-2012", "National Day");

		lh.addStaticHoliday ("05-OCT-2012", "National Day");

		lh.addStaticHoliday ("31-DEC-2012", "New Years Eve");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2013", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("12-FEB-2013", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("13-FEB-2013", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("14-FEB-2013", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("15-FEB-2013", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2013", "Ching Ming Festival");

		lh.addStaticHoliday ("05-APR-2013", "Bank Holiday");

		lh.addStaticHoliday ("01-MAY-2013", "Labour Day");

		lh.addStaticHoliday ("10-JUN-2013", "Bank Holiday");

		lh.addStaticHoliday ("11-JUN-2013", "Bank Holiday");

		lh.addStaticHoliday ("12-JUN-2013", "Tuen Ng Festival");

		lh.addStaticHoliday ("19-SEP-2013", "Mid-Autumn Festival");

		lh.addStaticHoliday ("30-SEP-2013", "National Day");

		lh.addStaticHoliday ("01-OCT-2013", "National Day");

		lh.addStaticHoliday ("02-OCT-2013", "National Day");

		lh.addStaticHoliday ("03-OCT-2013", "National Day");

		lh.addStaticHoliday ("04-OCT-2013", "National Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("30-JAN-2014", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("31-JAN-2014", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("03-FEB-2014", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-FEB-2014", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-FEB-2014", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("07-APR-2014", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("01-MAY-2014", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2014", "Bank Holiday");

		lh.addStaticHoliday ("02-JUN-2014", "Tuen Ng Festival");

		lh.addStaticHoliday ("08-SEP-2014", "Mid-Autumn Festival");

		lh.addStaticHoliday ("29-SEP-2014", "National Day");

		lh.addStaticHoliday ("30-SEP-2014", "National Day");

		lh.addStaticHoliday ("01-OCT-2014", "National Day");

		lh.addStaticHoliday ("02-OCT-2014", "National Day");

		lh.addStaticHoliday ("03-OCT-2014", "National Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2015", "New Years Holiday");

		lh.addStaticHoliday ("18-FEB-2015", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("19-FEB-2015", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("20-FEB-2015", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("23-FEB-2015", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("24-FEB-2015", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("06-APR-2015", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("01-MAY-2015", "Labour Day");

		lh.addStaticHoliday ("22-JUN-2015", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("28-SEP-2015", "Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2015", "National Day");

		lh.addStaticHoliday ("02-OCT-2015", "National Day");

		lh.addStaticHoliday ("05-OCT-2015", "National Day");

		lh.addStaticHoliday ("06-OCT-2015", "National Day");

		lh.addStaticHoliday ("07-OCT-2015", "National Day");

		lh.addStaticHoliday ("08-OCT-2015", "National Day");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2016", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("09-FEB-2016", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("10-FEB-2016", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("11-FEB-2016", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("12-FEB-2016", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2016", "Ching Ming Festival");

		lh.addStaticHoliday ("02-MAY-2016", "Labour Day Observed");

		lh.addStaticHoliday ("09-JUN-2016", "Tuen Ng Festival");

		lh.addStaticHoliday ("10-JUN-2016", "Bank Holiday");

		lh.addStaticHoliday ("15-SEP-2016", "Mid-Autumn Festival");

		lh.addStaticHoliday ("03-OCT-2016", "National Day");

		lh.addStaticHoliday ("04-OCT-2016", "National Day");

		lh.addStaticHoliday ("05-OCT-2016", "National Day");

		lh.addStaticHoliday ("06-OCT-2016", "National Day");

		lh.addStaticHoliday ("07-OCT-2016", "National Day");

		lh.addStaticHoliday ("02-JAN-2017", "New Years Day Observed");

		lh.addStaticHoliday ("27-JAN-2017", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("30-JAN-2017", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("31-JAN-2017", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("01-FEB-2017", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("02-FEB-2017", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("03-APR-2017", "Bank Holiday");

		lh.addStaticHoliday ("04-APR-2017", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2017", "Labour Day");

		lh.addStaticHoliday ("29-MAY-2017", "Bank Holiday");

		lh.addStaticHoliday ("30-MAY-2017", "Tuen Ng Festival");

		lh.addStaticHoliday ("02-OCT-2017", "National Day");

		lh.addStaticHoliday ("03-OCT-2017", "National Day");

		lh.addStaticHoliday ("04-OCT-2017", "National Day");

		lh.addStaticHoliday ("05-OCT-2017", "National Day");

		lh.addStaticHoliday ("06-OCT-2017", "National Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("15-FEB-2018", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("16-FEB-2018", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("19-FEB-2018", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("20-FEB-2018", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("21-FEB-2018", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-APR-2018", "Ching Ming Festival");

		lh.addStaticHoliday ("06-APR-2018", "Bank Holiday");

		lh.addStaticHoliday ("30-APR-2018", "Bank Holiday");

		lh.addStaticHoliday ("01-MAY-2018", "Labour Day");

		lh.addStaticHoliday ("18-JUN-2018", "Tuen Ng Festival");

		lh.addStaticHoliday ("24-SEP-2018", "Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2018", "National Day");

		lh.addStaticHoliday ("02-OCT-2018", "National Day");

		lh.addStaticHoliday ("03-OCT-2018", "National Day");

		lh.addStaticHoliday ("04-OCT-2018", "National Day");

		lh.addStaticHoliday ("05-OCT-2018", "National Day");

		lh.addStaticHoliday ("31-DEC-2018", "New Years Eve");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("04-FEB-2019", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-FEB-2019", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("06-FEB-2019", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("07-FEB-2019", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("08-FEB-2019", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-APR-2019", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2019", "Labour Day");

		lh.addStaticHoliday ("07-JUN-2019", "Tuen Ng Festival");

		lh.addStaticHoliday ("13-SEP-2019", "Mid-Autumn Festival");

		lh.addStaticHoliday ("30-SEP-2019", "National Day");

		lh.addStaticHoliday ("01-OCT-2019", "National Day");

		lh.addStaticHoliday ("02-OCT-2019", "National Day");

		lh.addStaticHoliday ("03-OCT-2019", "National Day");

		lh.addStaticHoliday ("04-OCT-2019", "National Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("24-JAN-2020", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("27-JAN-2020", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("28-JAN-2020", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("29-JAN-2020", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("30-JAN-2020", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("06-APR-2020", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("01-MAY-2020", "Labour Day");

		lh.addStaticHoliday ("25-JUN-2020", "Tuen Ng Festival");

		lh.addStaticHoliday ("26-JUN-2020", "Bank Holiday");

		lh.addStaticHoliday ("01-OCT-2020", "National Day");

		lh.addStaticHoliday ("02-OCT-2020", "National Day");

		lh.addStaticHoliday ("05-OCT-2020", "National Day");

		lh.addStaticHoliday ("06-OCT-2020", "National Day");

		lh.addStaticHoliday ("07-OCT-2020", "National Day");

		lh.addStaticHoliday ("08-OCT-2020", "National Day");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2021", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("12-FEB-2021", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("15-FEB-2021", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("16-FEB-2021", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("17-FEB-2021", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-APR-2021", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("03-MAY-2021", "Labour Day Observed");

		lh.addStaticHoliday ("14-JUN-2021", "Tuen Ng Festival");

		lh.addStaticHoliday ("21-SEP-2021", "Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2021", "National Day");

		lh.addStaticHoliday ("04-OCT-2021", "National Day");

		lh.addStaticHoliday ("05-OCT-2021", "National Day");

		lh.addStaticHoliday ("06-OCT-2021", "National Day");

		lh.addStaticHoliday ("07-OCT-2021", "National Day");

		lh.addStaticHoliday ("03-JAN-2022", "New Years Day Observed");

		lh.addStaticHoliday ("31-JAN-2022", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("01-FEB-2022", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("02-FEB-2022", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("03-FEB-2022", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-FEB-2022", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2022", "Bank Holiday");

		lh.addStaticHoliday ("05-APR-2022", "Ching Ming Festival");

		lh.addStaticHoliday ("02-MAY-2022", "Labour Day Observed");

		lh.addStaticHoliday ("03-JUN-2022", "Tuen Ng Festival");

		lh.addStaticHoliday ("12-SEP-2022", "Mid-Autumn Festival");

		lh.addStaticHoliday ("03-OCT-2022", "National Day");

		lh.addStaticHoliday ("04-OCT-2022", "National Day");

		lh.addStaticHoliday ("05-OCT-2022", "National Day");

		lh.addStaticHoliday ("06-OCT-2022", "National Day");

		lh.addStaticHoliday ("07-OCT-2022", "National Day");

		lh.addStaticHoliday ("02-JAN-2023", "New Years Day Observed");

		lh.addStaticHoliday ("23-JAN-2023", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("24-JAN-2023", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("25-JAN-2023", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("26-JAN-2023", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("27-JAN-2023", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-APR-2023", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2023", "Labour Day");

		lh.addStaticHoliday ("22-JUN-2023", "Tuen Ng Festival");

		lh.addStaticHoliday ("23-JUN-2023", "Bank Holiday");

		lh.addStaticHoliday ("29-SEP-2023", "Mid-Autumn Festival");

		lh.addStaticHoliday ("02-OCT-2023", "National Day");

		lh.addStaticHoliday ("03-OCT-2023", "National Day");

		lh.addStaticHoliday ("04-OCT-2023", "National Day");

		lh.addStaticHoliday ("05-OCT-2023", "National Day");

		lh.addStaticHoliday ("06-OCT-2023", "National Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("09-FEB-2024", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("12-FEB-2024", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("13-FEB-2024", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("14-FEB-2024", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("15-FEB-2024", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2024", "Ching Ming Festival");

		lh.addStaticHoliday ("05-APR-2024", "Bank Holiday");

		lh.addStaticHoliday ("01-MAY-2024", "Labour Day");

		lh.addStaticHoliday ("10-JUN-2024", "Tuen Ng Festival");

		lh.addStaticHoliday ("17-SEP-2024", "Mid-Autumn Festival");

		lh.addStaticHoliday ("30-SEP-2024", "National Day");

		lh.addStaticHoliday ("01-OCT-2024", "National Day");

		lh.addStaticHoliday ("02-OCT-2024", "National Day");

		lh.addStaticHoliday ("03-OCT-2024", "National Day");

		lh.addStaticHoliday ("04-OCT-2024", "National Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2025", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("29-JAN-2025", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("30-JAN-2025", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("31-JAN-2025", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("03-FEB-2025", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2025", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2025", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2025", "Bank Holiday");

		lh.addStaticHoliday ("02-JUN-2025", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("29-SEP-2025", "National Day");

		lh.addStaticHoliday ("30-SEP-2025", "National Day");

		lh.addStaticHoliday ("01-OCT-2025", "National Day");

		lh.addStaticHoliday ("02-OCT-2025", "National Day");

		lh.addStaticHoliday ("03-OCT-2025", "National Day");

		lh.addStaticHoliday ("06-OCT-2025", "Mid-Autumn Festival");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2026", "New Years Holiday");

		lh.addStaticHoliday ("16-FEB-2026", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("17-FEB-2026", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("18-FEB-2026", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("19-FEB-2026", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("20-FEB-2026", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("06-APR-2026", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("01-MAY-2026", "Labour Day");

		lh.addStaticHoliday ("19-JUN-2026", "Tuen Ng Festival");

		lh.addStaticHoliday ("25-SEP-2026", "Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2026", "National Day");

		lh.addStaticHoliday ("02-OCT-2026", "National Day");

		lh.addStaticHoliday ("05-OCT-2026", "National Day");

		lh.addStaticHoliday ("06-OCT-2026", "National Day");

		lh.addStaticHoliday ("07-OCT-2026", "National Day");

		lh.addStaticHoliday ("08-OCT-2026", "National Day");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("05-FEB-2027", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("08-FEB-2027", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("09-FEB-2027", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("10-FEB-2027", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("11-FEB-2027", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-APR-2027", "Ching Ming Festival");

		lh.addStaticHoliday ("03-MAY-2027", "Labour Day Observed");

		lh.addStaticHoliday ("07-JUN-2027", "Bank Holiday");

		lh.addStaticHoliday ("08-JUN-2027", "Bank Holiday");

		lh.addStaticHoliday ("09-JUN-2027", "Tuen Ng Festival");

		lh.addStaticHoliday ("15-SEP-2027", "Mid-Autumn Festival");

		lh.addStaticHoliday ("16-SEP-2027", "Bank Holiday");

		lh.addStaticHoliday ("17-SEP-2027", "Bank Holiday");

		lh.addStaticHoliday ("01-OCT-2027", "National Day");

		lh.addStaticHoliday ("04-OCT-2027", "National Day");

		lh.addStaticHoliday ("05-OCT-2027", "National Day");

		lh.addStaticHoliday ("06-OCT-2027", "National Day");

		lh.addStaticHoliday ("07-OCT-2027", "National Day");

		lh.addStaticHoliday ("03-JAN-2028", "New Years Day Observed");

		lh.addStaticHoliday ("25-JAN-2028", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("26-JAN-2028", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("27-JAN-2028", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("28-JAN-2028", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("31-JAN-2028", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("03-APR-2028", "Bank Holiday");

		lh.addStaticHoliday ("04-APR-2028", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2028", "Labour Day");

		lh.addStaticHoliday ("29-MAY-2028", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("02-OCT-2028", "National Day");

		lh.addStaticHoliday ("03-OCT-2028", "National Day");

		lh.addStaticHoliday ("04-OCT-2028", "National Day");

		lh.addStaticHoliday ("05-OCT-2028", "National Day");

		lh.addStaticHoliday ("06-OCT-2028", "National Day");

		lh.addStaticHoliday ("01-JAN-2029", "New Years Day");

		lh.addStaticHoliday ("12-FEB-2029", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("13-FEB-2029", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("14-FEB-2029", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("15-FEB-2029", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("16-FEB-2029", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2029", "Ching Ming Festival");

		lh.addStaticHoliday ("30-APR-2029", "Bank Holiday");

		lh.addStaticHoliday ("01-MAY-2029", "Labour Day");

		lh.addStaticHoliday ("18-JUN-2029", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("24-SEP-2029", "Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2029", "National Day");

		lh.addStaticHoliday ("02-OCT-2029", "National Day");

		lh.addStaticHoliday ("03-OCT-2029", "National Day");

		lh.addStaticHoliday ("04-OCT-2029", "National Day");

		lh.addStaticHoliday ("05-OCT-2029", "National Day");

		lh.addStaticHoliday ("31-DEC-2029", "New Years Eve");

		lh.addStaticHoliday ("01-JAN-2030", "New Years Day");

		lh.addStaticHoliday ("04-FEB-2030", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-FEB-2030", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("06-FEB-2030", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("07-FEB-2030", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("08-FEB-2030", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-APR-2030", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2030", "Labour Day");

		lh.addStaticHoliday ("03-JUN-2030", "Bank Holiday");

		lh.addStaticHoliday ("04-JUN-2030", "Bank Holiday");

		lh.addStaticHoliday ("05-JUN-2030", "Tuen Ng Festival");

		lh.addStaticHoliday ("12-SEP-2030", "Mid-Autumn Festival");

		lh.addStaticHoliday ("30-SEP-2030", "National Day");

		lh.addStaticHoliday ("01-OCT-2030", "National Day");

		lh.addStaticHoliday ("02-OCT-2030", "National Day");

		lh.addStaticHoliday ("03-OCT-2030", "National Day");

		lh.addStaticHoliday ("04-OCT-2030", "National Day");

		lh.addStaticHoliday ("01-JAN-2031", "New Years Day");

		lh.addStaticHoliday ("22-JAN-2031", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("23-JAN-2031", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("24-JAN-2031", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("27-JAN-2031", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("28-JAN-2031", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("07-APR-2031", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("01-MAY-2031", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2031", "Bank Holiday");

		lh.addStaticHoliday ("23-JUN-2031", "Bank Holiday");

		lh.addStaticHoliday ("24-JUN-2031", "Tuen Ng Festival");

		lh.addStaticHoliday ("29-SEP-2031", "National Day");

		lh.addStaticHoliday ("30-SEP-2031", "National Day");

		lh.addStaticHoliday ("01-OCT-2031", "National Day");

		lh.addStaticHoliday ("02-OCT-2031", "National Day");

		lh.addStaticHoliday ("03-OCT-2031", "National Day");

		lh.addStaticHoliday ("01-JAN-2032", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2032", "New Years Holiday");

		lh.addStaticHoliday ("10-FEB-2032", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("11-FEB-2032", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("12-FEB-2032", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("13-FEB-2032", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("16-FEB-2032", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-APR-2032", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("03-MAY-2032", "Labour Day Observed");

		lh.addStaticHoliday ("14-JUN-2032", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("20-SEP-2032", "Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2032", "National Day");

		lh.addStaticHoliday ("04-OCT-2032", "National Day");

		lh.addStaticHoliday ("05-OCT-2032", "National Day");

		lh.addStaticHoliday ("06-OCT-2032", "National Day");

		lh.addStaticHoliday ("07-OCT-2032", "National Day");

		lh.addStaticHoliday ("03-JAN-2033", "New Years Day Observed");

		lh.addStaticHoliday ("31-JAN-2033", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("01-FEB-2033", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("02-FEB-2033", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("03-FEB-2033", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-FEB-2033", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2033", "Ching Ming Festival");

		lh.addStaticHoliday ("02-MAY-2033", "Labour Day Observed");

		lh.addStaticHoliday ("30-MAY-2033", "Bank Holiday");

		lh.addStaticHoliday ("31-MAY-2033", "Bank Holiday");

		lh.addStaticHoliday ("01-JUN-2033", "Tuen Ng Festival");

		lh.addStaticHoliday ("08-SEP-2033", "Mid-Autumn Festival");

		lh.addStaticHoliday ("03-OCT-2033", "National Day");

		lh.addStaticHoliday ("04-OCT-2033", "National Day");

		lh.addStaticHoliday ("05-OCT-2033", "National Day");

		lh.addStaticHoliday ("06-OCT-2033", "National Day");

		lh.addStaticHoliday ("07-OCT-2033", "National Day");

		lh.addStaticHoliday ("02-JAN-2034", "New Years Day Observed");

		lh.addStaticHoliday ("20-FEB-2034", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("21-FEB-2034", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("22-FEB-2034", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("23-FEB-2034", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("24-FEB-2034", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-APR-2034", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2034", "Labour Day");

		lh.addStaticHoliday ("19-JUN-2034", "Bank Holiday");

		lh.addStaticHoliday ("20-JUN-2034", "Tuen Ng Festival");

		lh.addStaticHoliday ("27-SEP-2034", "Mid-Autumn Festival");

		lh.addStaticHoliday ("28-SEP-2034", "Bank Holiday");

		lh.addStaticHoliday ("29-SEP-2034", "Bank Holiday");

		lh.addStaticHoliday ("02-OCT-2034", "National Day");

		lh.addStaticHoliday ("03-OCT-2034", "National Day");

		lh.addStaticHoliday ("04-OCT-2034", "National Day");

		lh.addStaticHoliday ("05-OCT-2034", "National Day");

		lh.addStaticHoliday ("06-OCT-2034", "National Day");

		lh.addStaticHoliday ("01-JAN-2035", "New Years Day");

		lh.addStaticHoliday ("07-FEB-2035", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("08-FEB-2035", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("09-FEB-2035", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("12-FEB-2035", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("13-FEB-2035", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-APR-2035", "Ching Ming Festival");

		lh.addStaticHoliday ("06-APR-2035", "Bank Holiday");

		lh.addStaticHoliday ("30-APR-2035", "Bank Holiday");

		lh.addStaticHoliday ("01-MAY-2035", "Labour Day");

		lh.addStaticHoliday ("11-JUN-2035", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("17-SEP-2035", "Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2035", "National Day");

		lh.addStaticHoliday ("02-OCT-2035", "National Day");

		lh.addStaticHoliday ("03-OCT-2035", "National Day");

		lh.addStaticHoliday ("04-OCT-2035", "National Day");

		lh.addStaticHoliday ("05-OCT-2035", "National Day");

		lh.addStaticHoliday ("31-DEC-2035", "New Years Eve");

		lh.addStaticHoliday ("01-JAN-2036", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2036", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("29-JAN-2036", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("30-JAN-2036", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("31-JAN-2036", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("01-FEB-2036", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2036", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2036", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2036", "Bank Holiday");

		lh.addStaticHoliday ("30-MAY-2036", "Tuen Ng Festival");

		lh.addStaticHoliday ("29-SEP-2036", "National Day");

		lh.addStaticHoliday ("30-SEP-2036", "National Day");

		lh.addStaticHoliday ("01-OCT-2036", "National Day");

		lh.addStaticHoliday ("02-OCT-2036", "National Day");

		lh.addStaticHoliday ("03-OCT-2036", "National Day");

		lh.addStaticHoliday ("06-OCT-2036", "Mid-Autumn Festival");

		lh.addStaticHoliday ("01-JAN-2037", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2037", "New Years Holiday");

		lh.addStaticHoliday ("16-FEB-2037", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("17-FEB-2037", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("18-FEB-2037", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("19-FEB-2037", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("20-FEB-2037", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("06-APR-2037", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("01-MAY-2037", "Labour Day");

		lh.addStaticHoliday ("18-JUN-2037", "Tuen Ng Festival");

		lh.addStaticHoliday ("19-JUN-2037", "Bank Holiday");

		lh.addStaticHoliday ("24-SEP-2037", "Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2037", "National Day");

		lh.addStaticHoliday ("02-OCT-2037", "National Day");

		lh.addStaticHoliday ("05-OCT-2037", "National Day");

		lh.addStaticHoliday ("06-OCT-2037", "National Day");

		lh.addStaticHoliday ("07-OCT-2037", "National Day");

		lh.addStaticHoliday ("08-OCT-2037", "National Day");

		lh.addStaticHoliday ("01-JAN-2038", "New Years Day");

		lh.addStaticHoliday ("03-FEB-2038", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-FEB-2038", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-FEB-2038", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("08-FEB-2038", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("09-FEB-2038", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-APR-2038", "Ching Ming Festival");

		lh.addStaticHoliday ("03-MAY-2038", "Labour Day Observed");

		lh.addStaticHoliday ("07-JUN-2038", "Tuen Ng Festival");

		lh.addStaticHoliday ("13-SEP-2038", "Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2038", "National Day");

		lh.addStaticHoliday ("04-OCT-2038", "National Day");

		lh.addStaticHoliday ("05-OCT-2038", "National Day");

		lh.addStaticHoliday ("06-OCT-2038", "National Day");

		lh.addStaticHoliday ("07-OCT-2038", "National Day");

		lh.addStaticHoliday ("03-JAN-2039", "New Years Day Observed");

		lh.addStaticHoliday ("24-JAN-2039", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("25-JAN-2039", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("26-JAN-2039", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("27-JAN-2039", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("28-JAN-2039", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2039", "Bank Holiday");

		lh.addStaticHoliday ("05-APR-2039", "Ching Ming Festival");

		lh.addStaticHoliday ("02-MAY-2039", "Labour Day Observed");

		lh.addStaticHoliday ("27-MAY-2039", "Tuen Ng Festival");

		lh.addStaticHoliday ("03-OCT-2039", "National Day");

		lh.addStaticHoliday ("04-OCT-2039", "National Day");

		lh.addStaticHoliday ("05-OCT-2039", "National Day");

		lh.addStaticHoliday ("06-OCT-2039", "National Day");

		lh.addStaticHoliday ("07-OCT-2039", "National Day");

		lh.addStaticHoliday ("02-JAN-2040", "New Years Day Observed");

		lh.addStaticHoliday ("13-FEB-2040", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("14-FEB-2040", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("15-FEB-2040", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("16-FEB-2040", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("17-FEB-2040", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2040", "Ching Ming Festival");

		lh.addStaticHoliday ("30-APR-2040", "Bank Holiday");

		lh.addStaticHoliday ("01-MAY-2040", "Labour Day");

		lh.addStaticHoliday ("14-JUN-2040", "Tuen Ng Festival");

		lh.addStaticHoliday ("15-JUN-2040", "Bank Holiday");

		lh.addStaticHoliday ("20-SEP-2040", "Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2040", "National Day");

		lh.addStaticHoliday ("02-OCT-2040", "National Day");

		lh.addStaticHoliday ("03-OCT-2040", "National Day");

		lh.addStaticHoliday ("04-OCT-2040", "National Day");

		lh.addStaticHoliday ("05-OCT-2040", "National Day");

		lh.addStaticHoliday ("31-DEC-2040", "New Years Eve");

		lh.addStaticHoliday ("01-JAN-2041", "New Years Day");

		lh.addStaticHoliday ("31-JAN-2041", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("01-FEB-2041", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-FEB-2041", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-FEB-2041", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("06-FEB-2041", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2041", "Ching Ming Festival");

		lh.addStaticHoliday ("05-APR-2041", "Bank Holiday");

		lh.addStaticHoliday ("01-MAY-2041", "Labour Day");

		lh.addStaticHoliday ("03-JUN-2041", "Tuen Ng Festival");

		lh.addStaticHoliday ("10-SEP-2041", "Mid-Autumn Festival");

		lh.addStaticHoliday ("30-SEP-2041", "National Day");

		lh.addStaticHoliday ("01-OCT-2041", "National Day");

		lh.addStaticHoliday ("02-OCT-2041", "National Day");

		lh.addStaticHoliday ("03-OCT-2041", "National Day");

		lh.addStaticHoliday ("04-OCT-2041", "National Day");

		lh.addStaticHoliday ("01-JAN-2042", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2042", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("22-JAN-2042", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("23-JAN-2042", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("24-JAN-2042", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("27-JAN-2042", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2042", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2042", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2042", "Bank Holiday");

		lh.addStaticHoliday ("23-JUN-2042", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("29-SEP-2042", "National Day");

		lh.addStaticHoliday ("30-SEP-2042", "National Day");

		lh.addStaticHoliday ("01-OCT-2042", "National Day");

		lh.addStaticHoliday ("02-OCT-2042", "National Day");

		lh.addStaticHoliday ("03-OCT-2042", "National Day");

		lh.addStaticHoliday ("01-JAN-2043", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2043", "New Years Holiday");

		lh.addStaticHoliday ("09-FEB-2043", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("10-FEB-2043", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("11-FEB-2043", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("12-FEB-2043", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("13-FEB-2043", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("06-APR-2043", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("01-MAY-2043", "Labour Day");

		lh.addStaticHoliday ("11-JUN-2043", "Tuen Ng Festival");

		lh.addStaticHoliday ("12-JUN-2043", "Bank Holiday");

		lh.addStaticHoliday ("17-SEP-2043", "Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2043", "National Day");

		lh.addStaticHoliday ("02-OCT-2043", "National Day");

		lh.addStaticHoliday ("05-OCT-2043", "National Day");

		lh.addStaticHoliday ("06-OCT-2043", "National Day");

		lh.addStaticHoliday ("07-OCT-2043", "National Day");

		lh.addStaticHoliday ("08-OCT-2043", "National Day");

		lh.addStaticHoliday ("01-JAN-2044", "New Years Day");

		lh.addStaticHoliday ("29-JAN-2044", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("01-FEB-2044", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("02-FEB-2044", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("03-FEB-2044", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-FEB-2044", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2044", "Ching Ming Festival");

		lh.addStaticHoliday ("02-MAY-2044", "Labour Day Observed");

		lh.addStaticHoliday ("30-MAY-2044", "Bank Holiday");

		lh.addStaticHoliday ("31-MAY-2044", "Tuen Ng Festival");

		lh.addStaticHoliday ("03-OCT-2044", "National Day");

		lh.addStaticHoliday ("04-OCT-2044", "National Day");

		lh.addStaticHoliday ("05-OCT-2044", "National Day");

		lh.addStaticHoliday ("06-OCT-2044", "National Day");

		lh.addStaticHoliday ("07-OCT-2044", "National Day");

		lh.addStaticHoliday ("02-JAN-2045", "New Years Day Observed");

		lh.addStaticHoliday ("16-FEB-2045", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("17-FEB-2045", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("20-FEB-2045", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("21-FEB-2045", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("22-FEB-2045", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("03-APR-2045", "Bank Holiday");

		lh.addStaticHoliday ("04-APR-2045", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2045", "Labour Day");

		lh.addStaticHoliday ("19-JUN-2045", "Tuen Ng Festival");

		lh.addStaticHoliday ("25-SEP-2045", "Mid-Autumn Festival");

		lh.addStaticHoliday ("02-OCT-2045", "National Day");

		lh.addStaticHoliday ("03-OCT-2045", "National Day");

		lh.addStaticHoliday ("04-OCT-2045", "National Day");

		lh.addStaticHoliday ("05-OCT-2045", "National Day");

		lh.addStaticHoliday ("06-OCT-2045", "National Day");

		lh.addStaticHoliday ("01-JAN-2046", "New Years Day");

		lh.addStaticHoliday ("05-FEB-2046", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("06-FEB-2046", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("07-FEB-2046", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("08-FEB-2046", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("09-FEB-2046", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2046", "Ching Ming Festival");

		lh.addStaticHoliday ("30-APR-2046", "Bank Holiday");

		lh.addStaticHoliday ("01-MAY-2046", "Labour Day");

		lh.addStaticHoliday ("08-JUN-2046", "Tuen Ng Festival");

		lh.addStaticHoliday ("17-SEP-2046", "Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2046", "National Day");

		lh.addStaticHoliday ("02-OCT-2046", "National Day");

		lh.addStaticHoliday ("03-OCT-2046", "National Day");

		lh.addStaticHoliday ("04-OCT-2046", "National Day");

		lh.addStaticHoliday ("05-OCT-2046", "National Day");

		lh.addStaticHoliday ("31-DEC-2046", "New Years Eve");

		lh.addStaticHoliday ("01-JAN-2047", "New Years Day");

		lh.addStaticHoliday ("25-JAN-2047", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("28-JAN-2047", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("29-JAN-2047", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("30-JAN-2047", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("31-JAN-2047", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-APR-2047", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2047", "Labour Day");

		lh.addStaticHoliday ("27-MAY-2047", "Bank Holiday");

		lh.addStaticHoliday ("28-MAY-2047", "Bank Holiday");

		lh.addStaticHoliday ("29-MAY-2047", "Tuen Ng Festival");

		lh.addStaticHoliday ("30-SEP-2047", "National Day");

		lh.addStaticHoliday ("01-OCT-2047", "National Day");

		lh.addStaticHoliday ("02-OCT-2047", "National Day");

		lh.addStaticHoliday ("03-OCT-2047", "National Day");

		lh.addStaticHoliday ("04-OCT-2047", "National Day");

		lh.addStaticHoliday ("01-JAN-2048", "New Years Day");

		lh.addStaticHoliday ("13-FEB-2048", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("14-FEB-2048", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("17-FEB-2048", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("18-FEB-2048", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("19-FEB-2048", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("06-APR-2048", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("01-MAY-2048", "Labour Day");

		lh.addStaticHoliday ("15-JUN-2048", "Tuen Ng Festival");

		lh.addStaticHoliday ("22-SEP-2048", "Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2048", "National Day");

		lh.addStaticHoliday ("02-OCT-2048", "National Day");

		lh.addStaticHoliday ("05-OCT-2048", "National Day");

		lh.addStaticHoliday ("06-OCT-2048", "National Day");

		lh.addStaticHoliday ("07-OCT-2048", "National Day");

		lh.addStaticHoliday ("08-OCT-2048", "National Day");

		lh.addStaticHoliday ("01-JAN-2049", "New Years Day");

		lh.addStaticHoliday ("01-FEB-2049", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("02-FEB-2049", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("03-FEB-2049", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-FEB-2049", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-FEB-2049", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-APR-2049", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("03-MAY-2049", "Labour Day Observed");

		lh.addStaticHoliday ("04-JUN-2049", "Tuen Ng Festival");

		lh.addStaticHoliday ("13-SEP-2049", "Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2049", "National Day");

		lh.addStaticHoliday ("04-OCT-2049", "National Day");

		lh.addStaticHoliday ("05-OCT-2049", "National Day");

		lh.addStaticHoliday ("06-OCT-2049", "National Day");

		lh.addStaticHoliday ("07-OCT-2049", "National Day");

		lh.addStaticHoliday ("03-JAN-2050", "New Years Day Observed");

		lh.addStaticHoliday ("24-JAN-2050", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("25-JAN-2050", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("26-JAN-2050", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("27-JAN-2050", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("28-JAN-2050", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2050", "Ching Ming Festival");

		lh.addStaticHoliday ("02-MAY-2050", "Labour Day Observed");

		lh.addStaticHoliday ("23-JUN-2050", "Tuen Ng Festival");

		lh.addStaticHoliday ("24-JUN-2050", "Bank Holiday");

		lh.addStaticHoliday ("30-SEP-2050", "Mid-Autumn Festival");

		lh.addStaticHoliday ("03-OCT-2050", "National Day");

		lh.addStaticHoliday ("04-OCT-2050", "National Day");

		lh.addStaticHoliday ("05-OCT-2050", "National Day");

		lh.addStaticHoliday ("06-OCT-2050", "National Day");

		lh.addStaticHoliday ("07-OCT-2050", "National Day");

		lh.addStaticHoliday ("02-JAN-2051", "New Years Day Observed");

		lh.addStaticHoliday ("10-FEB-2051", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("13-FEB-2051", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("14-FEB-2051", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("15-FEB-2051", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("16-FEB-2051", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-APR-2051", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2051", "Labour Day");

		lh.addStaticHoliday ("12-JUN-2051", "Bank Holiday");

		lh.addStaticHoliday ("13-JUN-2051", "Tuen Ng Festival");

		lh.addStaticHoliday ("19-SEP-2051", "Mid-Autumn Festival");

		lh.addStaticHoliday ("02-OCT-2051", "National Day");

		lh.addStaticHoliday ("03-OCT-2051", "National Day");

		lh.addStaticHoliday ("04-OCT-2051", "National Day");

		lh.addStaticHoliday ("05-OCT-2051", "National Day");

		lh.addStaticHoliday ("06-OCT-2051", "National Day");

		lh.addStaticHoliday ("01-JAN-2052", "New Years Day");

		lh.addStaticHoliday ("31-JAN-2052", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("01-FEB-2052", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("02-FEB-2052", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-FEB-2052", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("06-FEB-2052", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2052", "Ching Ming Festival");

		lh.addStaticHoliday ("05-APR-2052", "Bank Holiday");

		lh.addStaticHoliday ("01-MAY-2052", "Labour Day");

		lh.addStaticHoliday ("03-JUN-2052", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("09-SEP-2052", "Mid-Autumn Festival");

		lh.addStaticHoliday ("30-SEP-2052", "National Day");

		lh.addStaticHoliday ("01-OCT-2052", "National Day");

		lh.addStaticHoliday ("02-OCT-2052", "National Day");

		lh.addStaticHoliday ("03-OCT-2052", "National Day");

		lh.addStaticHoliday ("04-OCT-2052", "National Day");

		lh.addStaticHoliday ("01-JAN-2053", "New Years Day");

		lh.addStaticHoliday ("18-FEB-2053", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("19-FEB-2053", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("20-FEB-2053", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("21-FEB-2053", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("24-FEB-2053", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2053", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2053", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2053", "Bank Holiday");

		lh.addStaticHoliday ("20-JUN-2053", "Tuen Ng Festival");

		lh.addStaticHoliday ("26-SEP-2053", "Mid-Autumn Festival");

		lh.addStaticHoliday ("29-SEP-2053", "National Day");

		lh.addStaticHoliday ("30-SEP-2053", "National Day");

		lh.addStaticHoliday ("01-OCT-2053", "National Day");

		lh.addStaticHoliday ("02-OCT-2053", "National Day");

		lh.addStaticHoliday ("03-OCT-2053", "National Day");

		lh.addStaticHoliday ("01-JAN-2054", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2054", "New Years Holiday");

		lh.addStaticHoliday ("09-FEB-2054", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("10-FEB-2054", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("11-FEB-2054", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("12-FEB-2054", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("13-FEB-2054", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("06-APR-2054", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("01-MAY-2054", "Labour Day");

		lh.addStaticHoliday ("08-JUN-2054", "Bank Holiday");

		lh.addStaticHoliday ("09-JUN-2054", "Bank Holiday");

		lh.addStaticHoliday ("10-JUN-2054", "Tuen Ng Festival");

		lh.addStaticHoliday ("16-SEP-2054", "Mid-Autumn Festival");

		lh.addStaticHoliday ("17-SEP-2054", "Bank Holiday");

		lh.addStaticHoliday ("18-SEP-2054", "Bank Holiday");

		lh.addStaticHoliday ("01-OCT-2054", "National Day");

		lh.addStaticHoliday ("02-OCT-2054", "National Day");

		lh.addStaticHoliday ("05-OCT-2054", "National Day");

		lh.addStaticHoliday ("06-OCT-2054", "National Day");

		lh.addStaticHoliday ("07-OCT-2054", "National Day");

		lh.addStaticHoliday ("08-OCT-2054", "National Day");

		lh.addStaticHoliday ("01-JAN-2055", "New Years Day");

		lh.addStaticHoliday ("27-JAN-2055", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("28-JAN-2055", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("29-JAN-2055", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("01-FEB-2055", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("02-FEB-2055", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-APR-2055", "Ching Ming Festival");

		lh.addStaticHoliday ("03-MAY-2055", "Labour Day Observed");

		lh.addStaticHoliday ("31-MAY-2055", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("01-OCT-2055", "National Day");

		lh.addStaticHoliday ("04-OCT-2055", "National Day");

		lh.addStaticHoliday ("05-OCT-2055", "National Day");

		lh.addStaticHoliday ("06-OCT-2055", "National Day");

		lh.addStaticHoliday ("07-OCT-2055", "National Day");

		lh.addStaticHoliday ("03-JAN-2056", "New Years Day Observed");

		lh.addStaticHoliday ("14-FEB-2056", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("15-FEB-2056", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("16-FEB-2056", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("17-FEB-2056", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("18-FEB-2056", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("03-APR-2056", "Bank Holiday");

		lh.addStaticHoliday ("04-APR-2056", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2056", "Labour Day");

		lh.addStaticHoliday ("19-JUN-2056", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("25-SEP-2056", "Mid-Autumn Festival");

		lh.addStaticHoliday ("02-OCT-2056", "National Day");

		lh.addStaticHoliday ("03-OCT-2056", "National Day");

		lh.addStaticHoliday ("04-OCT-2056", "National Day");

		lh.addStaticHoliday ("05-OCT-2056", "National Day");

		lh.addStaticHoliday ("06-OCT-2056", "National Day");

		lh.addStaticHoliday ("01-JAN-2057", "New Years Day");

		lh.addStaticHoliday ("05-FEB-2057", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("06-FEB-2057", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("07-FEB-2057", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("08-FEB-2057", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("09-FEB-2057", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2057", "Ching Ming Festival");

		lh.addStaticHoliday ("30-APR-2057", "Bank Holiday");

		lh.addStaticHoliday ("01-MAY-2057", "Labour Day");

		lh.addStaticHoliday ("04-JUN-2057", "Bank Holiday");

		lh.addStaticHoliday ("05-JUN-2057", "Bank Holiday");

		lh.addStaticHoliday ("06-JUN-2057", "Tuen Ng Festival");

		lh.addStaticHoliday ("13-SEP-2057", "Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2057", "National Day");

		lh.addStaticHoliday ("02-OCT-2057", "National Day");

		lh.addStaticHoliday ("03-OCT-2057", "National Day");

		lh.addStaticHoliday ("04-OCT-2057", "National Day");

		lh.addStaticHoliday ("05-OCT-2057", "National Day");

		lh.addStaticHoliday ("31-DEC-2057", "New Years Eve");

		lh.addStaticHoliday ("01-JAN-2058", "New Years Day");

		lh.addStaticHoliday ("23-JAN-2058", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("24-JAN-2058", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("25-JAN-2058", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("28-JAN-2058", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("29-JAN-2058", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2058", "Ching Ming Festival");

		lh.addStaticHoliday ("05-APR-2058", "Bank Holiday");

		lh.addStaticHoliday ("01-MAY-2058", "Labour Day");

		lh.addStaticHoliday ("24-JUN-2058", "Bank Holiday");

		lh.addStaticHoliday ("25-JUN-2058", "Tuen Ng Festival");

		lh.addStaticHoliday ("30-SEP-2058", "National Day");

		lh.addStaticHoliday ("01-OCT-2058", "National Day");

		lh.addStaticHoliday ("02-OCT-2058", "National Day");

		lh.addStaticHoliday ("03-OCT-2058", "National Day");

		lh.addStaticHoliday ("04-OCT-2058", "National Day");

		lh.addStaticHoliday ("01-JAN-2059", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2059", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("12-FEB-2059", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("13-FEB-2059", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("14-FEB-2059", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("17-FEB-2059", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("07-APR-2059", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("01-MAY-2059", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2059", "Bank Holiday");

		lh.addStaticHoliday ("16-JUN-2059", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("22-SEP-2059", "Mid-Autumn Festival");

		lh.addStaticHoliday ("29-SEP-2059", "National Day");

		lh.addStaticHoliday ("30-SEP-2059", "National Day");

		lh.addStaticHoliday ("01-OCT-2059", "National Day");

		lh.addStaticHoliday ("02-OCT-2059", "National Day");

		lh.addStaticHoliday ("03-OCT-2059", "National Day");

		lh.addStaticHoliday ("01-JAN-2060", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2060", "New Years Holiday");

		lh.addStaticHoliday ("02-FEB-2060", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("03-FEB-2060", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-FEB-2060", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-FEB-2060", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("06-FEB-2060", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("05-APR-2060", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("03-MAY-2060", "Labour Day Observed");

		lh.addStaticHoliday ("03-JUN-2060", "Tuen Ng Festival");

		lh.addStaticHoliday ("04-JUN-2060", "Bank Holiday");

		lh.addStaticHoliday ("09-SEP-2060", "Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2060", "National Day");

		lh.addStaticHoliday ("04-OCT-2060", "National Day");

		lh.addStaticHoliday ("05-OCT-2060", "National Day");

		lh.addStaticHoliday ("06-OCT-2060", "National Day");

		lh.addStaticHoliday ("07-OCT-2060", "National Day");

		lh.addStaticHoliday ("03-JAN-2061", "New Years Day Observed");

		lh.addStaticHoliday ("20-JAN-2061", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("21-JAN-2061", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("24-JAN-2061", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("25-JAN-2061", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("26-JAN-2061", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("04-APR-2061", "Ching Ming Festival");

		lh.addStaticHoliday ("02-MAY-2061", "Labour Day Observed");

		lh.addStaticHoliday ("20-JUN-2061", "Bank Holiday");

		lh.addStaticHoliday ("21-JUN-2061", "Bank Holiday");

		lh.addStaticHoliday ("22-JUN-2061", "Tuen Ng Festival");

		lh.addStaticHoliday ("28-SEP-2061", "Mid-Autumn Festival");

		lh.addStaticHoliday ("29-SEP-2061", "Bank Holiday");

		lh.addStaticHoliday ("30-SEP-2061", "Bank Holiday");

		lh.addStaticHoliday ("03-OCT-2061", "National Day");

		lh.addStaticHoliday ("04-OCT-2061", "National Day");

		lh.addStaticHoliday ("05-OCT-2061", "National Day");

		lh.addStaticHoliday ("06-OCT-2061", "National Day");

		lh.addStaticHoliday ("07-OCT-2061", "National Day");

		lh.addStaticHoliday ("02-JAN-2062", "New Years Day Observed");

		lh.addStaticHoliday ("08-FEB-2062", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("09-FEB-2062", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("10-FEB-2062", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("13-FEB-2062", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("14-FEB-2062", "Chinese New Year Holiday Period");

		lh.addStaticHoliday ("03-APR-2062", "Bank Holiday");

		lh.addStaticHoliday ("04-APR-2062", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2062", "Labour Day");

		lh.addStaticHoliday ("12-JUN-2062", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("18-SEP-2062", "Mid-Autumn Festival");

		lh.addStaticHoliday ("02-OCT-2062", "National Day");

		lh.addStaticHoliday ("03-OCT-2062", "National Day");

		lh.addStaticHoliday ("04-OCT-2062", "National Day");

		lh.addStaticHoliday ("05-OCT-2062", "National Day");

		lh.addStaticHoliday ("06-OCT-2062", "National Day");

		lh.addStandardWeekend();

		return lh;
	}
}
