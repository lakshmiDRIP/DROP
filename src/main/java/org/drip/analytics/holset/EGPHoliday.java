
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

public class EGPHoliday implements org.drip.analytics.holset.LocationHoliday {
	public EGPHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "EGP";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("28-JAN-1998", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("29-JAN-1998", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("05-APR-1998", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("06-APR-1998", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("07-APR-1998", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("08-APR-1998", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("09-APR-1998", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("19-APR-1998", "Orthodox Easter Day");

		lh.addStaticHoliday ("20-APR-1998", "Sham El Nessim");

		lh.addStaticHoliday ("26-APR-1998", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("27-APR-1998", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("28-APR-1998", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("01-JUL-1998", "Bank Holiday");

		lh.addStaticHoliday ("05-JUL-1998", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("06-JUL-1998", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("07-JUL-1998", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("23-JUL-1998", "Revolution Day");

		lh.addStaticHoliday ("06-OCT-1998", "Armed Forces Day");

		lh.addStaticHoliday ("17-JAN-1999", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("18-JAN-1999", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("19-JAN-1999", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("20-JAN-1999", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("28-MAR-1999", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("29-MAR-1999", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("30-MAR-1999", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("11-APR-1999", "Orthodox Easter Day");

		lh.addStaticHoliday ("12-APR-1999", "Sham El Nessim");

		lh.addStaticHoliday ("18-APR-1999", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("25-APR-1999", "Sinai Liberation Day");

		lh.addStaticHoliday ("27-JUN-1999", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("01-JUL-1999", "Bank Holiday");

		lh.addStaticHoliday ("06-OCT-1999", "Armed Forces Day");

		lh.addStaticHoliday ("06-JAN-2000", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("09-JAN-2000", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("14-MAR-2000", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("15-MAR-2000", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("16-MAR-2000", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("05-APR-2000", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("06-APR-2000", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("25-APR-2000", "Sinai Liberation Day");

		lh.addStaticHoliday ("30-APR-2000", "Orthodox Easter Day");

		lh.addStaticHoliday ("01-MAY-2000", "Sham El Nessim");

		lh.addStaticHoliday ("14-JUN-2000", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("15-JUN-2000", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("23-JUL-2000", "Revolution Day");

		lh.addStaticHoliday ("25-DEC-2000", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("26-DEC-2000", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("27-DEC-2000", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("28-DEC-2000", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("04-MAR-2001", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("05-MAR-2001", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("06-MAR-2001", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("07-MAR-2001", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("08-MAR-2001", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("25-MAR-2001", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("26-MAR-2001", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("27-MAR-2001", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("15-APR-2001", "Orthodox Easter Day");

		lh.addStaticHoliday ("16-APR-2001", "Sham El Nessim");

		lh.addStaticHoliday ("25-APR-2001", "Sinai Liberation Day");

		lh.addStaticHoliday ("01-MAY-2001", "Labour Day");

		lh.addStaticHoliday ("03-JUN-2001", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("04-JUN-2001", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("05-JUN-2001", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("01-JUL-2001", "Bank Holiday");

		lh.addStaticHoliday ("23-JUL-2001", "Revolution Day");

		lh.addStaticHoliday ("16-DEC-2001", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("17-DEC-2001", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("21-FEB-2002", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("24-FEB-2002", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("25-FEB-2002", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("17-MAR-2002", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("25-APR-2002", "Sinai Liberation Day");

		lh.addStaticHoliday ("01-MAY-2002", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2002", "Orthodox Easter Day");

		lh.addStaticHoliday ("06-MAY-2002", "Sham El Nessim");

		lh.addStaticHoliday ("26-MAY-2002", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("01-JUL-2002", "Bank Holiday");

		lh.addStaticHoliday ("23-JUL-2002", "Revolution Day");

		lh.addStaticHoliday ("06-OCT-2002", "Armed Forces Day");

		lh.addStaticHoliday ("04-DEC-2002", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("05-DEC-2002", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("10-FEB-2003", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("11-FEB-2003", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("12-FEB-2003", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("13-FEB-2003", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("04-MAR-2003", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("05-MAR-2003", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("06-MAR-2003", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("27-APR-2003", "Orthodox Easter Day");

		lh.addStaticHoliday ("28-APR-2003", "Sham El Nessim");

		lh.addStaticHoliday ("01-MAY-2003", "Labour Day");

		lh.addStaticHoliday ("13-MAY-2003", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("14-MAY-2003", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("15-MAY-2003", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("01-JUL-2003", "Bank Holiday");

		lh.addStaticHoliday ("23-JUL-2003", "Revolution Day");

		lh.addStaticHoliday ("06-OCT-2003", "Armed Forces Day");

		lh.addStaticHoliday ("23-NOV-2003", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("24-NOV-2003", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("25-NOV-2003", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("26-NOV-2003", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("01-FEB-2004", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("02-FEB-2004", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("03-FEB-2004", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("22-FEB-2004", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("11-APR-2004", "Orthodox Easter Day");

		lh.addStaticHoliday ("12-APR-2004", "Sham El Nessim");

		lh.addStaticHoliday ("25-APR-2004", "Sinai Liberation Day");

		lh.addStaticHoliday ("02-MAY-2004", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("03-MAY-2004", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("01-JUL-2004", "Bank Holiday");

		lh.addStaticHoliday ("06-OCT-2004", "Armed Forces Day");

		lh.addStaticHoliday ("14-NOV-2004", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("15-NOV-2004", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("19-JAN-2005", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("20-JAN-2005", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("23-JAN-2005", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("09-FEB-2005", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("10-FEB-2005", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("20-APR-2005", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("21-APR-2005", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("25-APR-2005", "Sinai Liberation Day");

		lh.addStaticHoliday ("01-MAY-2005", "Orthodox Easter Day");

		lh.addStaticHoliday ("02-MAY-2005", "Sham El Nessim");

		lh.addStaticHoliday ("06-OCT-2005", "Armed Forces Day");

		lh.addStaticHoliday ("01-NOV-2005", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("02-NOV-2005", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("03-NOV-2005", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("08-JAN-2006", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("09-JAN-2006", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("10-JAN-2006", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("11-JAN-2006", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("12-JAN-2006", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("30-JAN-2006", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("31-JAN-2006", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("01-FEB-2006", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("10-APR-2006", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("11-APR-2006", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("12-APR-2006", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("23-APR-2006", "Orthodox Easter Day");

		lh.addStaticHoliday ("24-APR-2006", "Sham El Nessim");

		lh.addStaticHoliday ("25-APR-2006", "Sinai Liberation Day");

		lh.addStaticHoliday ("01-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("23-JUL-2006", "Revolution Day");

		lh.addStaticHoliday ("22-OCT-2006", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("23-OCT-2006", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("24-OCT-2006", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("25-OCT-2006", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("31-DEC-2006", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("01-JAN-2007", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("02-JAN-2007", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("21-JAN-2007", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("01-APR-2007", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("08-APR-2007", "Orthodox Easter Day");

		lh.addStaticHoliday ("09-APR-2007", "Sham El Nessim");

		lh.addStaticHoliday ("25-APR-2007", "Sinai Liberation Day");

		lh.addStaticHoliday ("01-MAY-2007", "Labour Day");

		lh.addStaticHoliday ("01-JUL-2007", "Bank Holiday");

		lh.addStaticHoliday ("23-JUL-2007", "Revolution Day");

		lh.addStaticHoliday ("11-OCT-2007", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("14-OCT-2007", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("18-DEC-2007", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("19-DEC-2007", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("20-DEC-2007", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("09-JAN-2008", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("10-JAN-2008", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("19-MAR-2008", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("20-MAR-2008", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("27-APR-2008", "Orthodox Easter Day");

		lh.addStaticHoliday ("28-APR-2008", "Sham El Nessim");

		lh.addStaticHoliday ("01-MAY-2008", "Labour Day");

		lh.addStaticHoliday ("01-JUL-2008", "Bank Holiday");

		lh.addStaticHoliday ("23-JUL-2008", "Revolution Day");

		lh.addStaticHoliday ("29-SEP-2008", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("30-SEP-2008", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("01-OCT-2008", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("02-OCT-2008", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("06-OCT-2008", "Armed Forces Day");

		lh.addStaticHoliday ("07-DEC-2008", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("08-DEC-2008", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("09-DEC-2008", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("10-DEC-2008", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("28-DEC-2008", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("29-DEC-2008", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("30-DEC-2008", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("08-MAR-2009", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("09-MAR-2009", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("10-MAR-2009", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("19-APR-2009", "Orthodox Easter Day");

		lh.addStaticHoliday ("20-APR-2009", "Sham El Nessim");

		lh.addStaticHoliday ("01-JUL-2009", "Bank Holiday");

		lh.addStaticHoliday ("23-JUL-2009", "Revolution Day");

		lh.addStaticHoliday ("20-SEP-2009", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("21-SEP-2009", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("06-OCT-2009", "Armed Forces Day");

		lh.addStaticHoliday ("25-NOV-2009", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("26-NOV-2009", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("29-NOV-2009", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("17-DEC-2009", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("25-FEB-2010", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("04-APR-2010", "Orthodox Easter Day");

		lh.addStaticHoliday ("05-APR-2010", "Sham El Nessim");

		lh.addStaticHoliday ("25-APR-2010", "Sinai Liberation Day");

		lh.addStaticHoliday ("01-JUL-2010", "Bank Holiday");

		lh.addStaticHoliday ("08-SEP-2010", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("09-SEP-2010", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("06-OCT-2010", "Armed Forces Day");

		lh.addStaticHoliday ("15-NOV-2010", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("16-NOV-2010", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("17-NOV-2010", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("18-NOV-2010", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("06-DEC-2010", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("07-DEC-2010", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("08-DEC-2010", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("14-FEB-2011", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("15-FEB-2011", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("16-FEB-2011", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("24-APR-2011", "Orthodox Easter Day");

		lh.addStaticHoliday ("25-APR-2011", "Sham El Nessim");

		lh.addStaticHoliday ("01-MAY-2011", "Labour Day");

		lh.addStaticHoliday ("28-AUG-2011", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("29-AUG-2011", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("30-AUG-2011", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("31-AUG-2011", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("06-OCT-2011", "Armed Forces Day");

		lh.addStaticHoliday ("06-NOV-2011", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("07-NOV-2011", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("08-NOV-2011", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("27-NOV-2011", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("05-FEB-2012", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("06-FEB-2012", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("15-APR-2012", "Orthodox Easter Day");

		lh.addStaticHoliday ("16-APR-2012", "Sham El Nessim");

		lh.addStaticHoliday ("25-APR-2012", "Sinai Liberation Day");

		lh.addStaticHoliday ("01-MAY-2012", "Labour Day");

		lh.addStaticHoliday ("01-JUL-2012", "Bank Holiday");

		lh.addStaticHoliday ("23-JUL-2012", "Revolution Day");

		lh.addStaticHoliday ("19-AUG-2012", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("20-AUG-2012", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("24-OCT-2012", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("25-OCT-2012", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("28-OCT-2012", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("14-NOV-2012", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("15-NOV-2012", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("23-JAN-2013", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("24-JAN-2013", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("25-APR-2013", "Sinai Liberation Day");

		lh.addStaticHoliday ("01-MAY-2013", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2013", "Orthodox Easter Day");

		lh.addStaticHoliday ("06-MAY-2013", "Sham El Nessim");

		lh.addStaticHoliday ("01-JUL-2013", "Bank Holiday");

		lh.addStaticHoliday ("23-JUL-2013", "Revolution Day");

		lh.addStaticHoliday ("06-AUG-2013", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("07-AUG-2013", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("08-AUG-2013", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("06-OCT-2013", "Armed Forces Day");

		lh.addStaticHoliday ("13-OCT-2013", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("14-OCT-2013", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("15-OCT-2013", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("16-OCT-2013", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("17-OCT-2013", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("04-NOV-2013", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("05-NOV-2013", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("06-NOV-2013", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("13-JAN-2014", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("14-JAN-2014", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("15-JAN-2014", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("20-APR-2014", "Orthodox Easter Day");

		lh.addStaticHoliday ("21-APR-2014", "Sham El Nessim");

		lh.addStaticHoliday ("01-MAY-2014", "Labour Day");

		lh.addStaticHoliday ("01-JUL-2014", "Bank Holiday");

		lh.addStaticHoliday ("23-JUL-2014", "Revolution Day");

		lh.addStaticHoliday ("27-JUL-2014", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("28-JUL-2014", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("29-JUL-2014", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("30-JUL-2014", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("05-OCT-2014", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("06-OCT-2014", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("07-OCT-2014", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("26-OCT-2014", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("04-JAN-2015", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("12-APR-2015", "Orthodox Easter Day");

		lh.addStaticHoliday ("13-APR-2015", "Sham El Nessim");

		lh.addStaticHoliday ("01-JUL-2015", "Bank Holiday");

		lh.addStaticHoliday ("16-JUL-2015", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("19-JUL-2015", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("23-JUL-2015", "Revolution Day");

		lh.addStaticHoliday ("22-SEP-2015", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("23-SEP-2015", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("24-SEP-2015", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("06-OCT-2015", "Armed Forces Day");

		lh.addStaticHoliday ("14-OCT-2015", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("15-OCT-2015", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("23-DEC-2015", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("24-DEC-2015", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("25-APR-2016", "Sinai Liberation Day");

		lh.addStaticHoliday ("01-MAY-2016", "Orthodox Easter Day");

		lh.addStaticHoliday ("02-MAY-2016", "Sham El Nessim");

		lh.addStaticHoliday ("04-JUL-2016", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("05-JUL-2016", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("06-JUL-2016", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("07-JUL-2016", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("11-SEP-2016", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("12-SEP-2016", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("13-SEP-2016", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("14-SEP-2016", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("02-OCT-2016", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("03-OCT-2016", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("04-OCT-2016", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("06-OCT-2016", "Armed Forces Day");

		lh.addStaticHoliday ("11-DEC-2016", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("12-DEC-2016", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("13-DEC-2016", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("16-APR-2017", "Orthodox Easter Day");

		lh.addStaticHoliday ("17-APR-2017", "Sham El Nessim");

		lh.addStaticHoliday ("25-APR-2017", "Sinai Liberation Day");

		lh.addStaticHoliday ("01-MAY-2017", "Labour Day");

		lh.addStaticHoliday ("25-JUN-2017", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("26-JUN-2017", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("23-JUL-2017", "Revolution Day");

		lh.addStaticHoliday ("30-AUG-2017", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("31-AUG-2017", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("03-SEP-2017", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("21-SEP-2017", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("30-NOV-2017", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("08-APR-2018", "Orthodox Easter Day");

		lh.addStaticHoliday ("09-APR-2018", "Sham El Nessim");

		lh.addStaticHoliday ("25-APR-2018", "Sinai Liberation Day");

		lh.addStaticHoliday ("01-MAY-2018", "Labour Day");

		lh.addStaticHoliday ("13-JUN-2018", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("14-JUN-2018", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("01-JUL-2018", "Bank Holiday");

		lh.addStaticHoliday ("23-JUL-2018", "Revolution Day");

		lh.addStaticHoliday ("20-AUG-2018", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("21-AUG-2018", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("22-AUG-2018", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("23-AUG-2018", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("10-SEP-2018", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("11-SEP-2018", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("12-SEP-2018", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("19-NOV-2018", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("20-NOV-2018", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("21-NOV-2018", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("25-APR-2019", "Sinai Liberation Day");

		lh.addStaticHoliday ("28-APR-2019", "Orthodox Easter Day");

		lh.addStaticHoliday ("29-APR-2019", "Sham El Nessim");

		lh.addStaticHoliday ("01-MAY-2019", "Labour Day");

		lh.addStaticHoliday ("03-JUN-2019", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("04-JUN-2019", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("05-JUN-2019", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("06-JUN-2019", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("01-JUL-2019", "Bank Holiday");

		lh.addStaticHoliday ("23-JUL-2019", "Revolution Day");

		lh.addStaticHoliday ("11-AUG-2019", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("12-AUG-2019", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("13-AUG-2019", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("01-SEP-2019", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("06-OCT-2019", "Armed Forces Day");

		lh.addStaticHoliday ("10-NOV-2019", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("19-APR-2020", "Orthodox Easter Day");

		lh.addStaticHoliday ("20-APR-2020", "Sham El Nessim");

		lh.addStaticHoliday ("24-MAY-2020", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("25-MAY-2020", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("01-JUL-2020", "Bank Holiday");

		lh.addStaticHoliday ("23-JUL-2020", "Revolution Day");

		lh.addStaticHoliday ("29-JUL-2020", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("30-JUL-2020", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("02-AUG-2020", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("19-AUG-2020", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("20-AUG-2020", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("06-OCT-2020", "Armed Forces Day");

		lh.addStaticHoliday ("28-OCT-2020", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("29-OCT-2020", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("25-APR-2021", "Sinai Liberation Day");

		lh.addStaticHoliday ("02-MAY-2021", "Orthodox Easter Day");

		lh.addStaticHoliday ("03-MAY-2021", "Sham El Nessim");

		lh.addStaticHoliday ("11-MAY-2021", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("12-MAY-2021", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("13-MAY-2021", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("01-JUL-2021", "Bank Holiday");

		lh.addStaticHoliday ("18-JUL-2021", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("19-JUL-2021", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("20-JUL-2021", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("21-JUL-2021", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("22-JUL-2021", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("09-AUG-2021", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("10-AUG-2021", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("11-AUG-2021", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("06-OCT-2021", "Armed Forces Day");

		lh.addStaticHoliday ("17-OCT-2021", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("18-OCT-2021", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("19-OCT-2021", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("24-APR-2022", "Orthodox Easter Day");

		lh.addStaticHoliday ("25-APR-2022", "Sham El Nessim");

		lh.addStaticHoliday ("01-MAY-2022", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("02-MAY-2022", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("03-MAY-2022", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("07-JUL-2022", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("10-JUL-2022", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("11-JUL-2022", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("31-JUL-2022", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("06-OCT-2022", "Armed Forces Day");

		lh.addStaticHoliday ("09-OCT-2022", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("16-APR-2023", "Orthodox Easter Day");

		lh.addStaticHoliday ("17-APR-2023", "Sham El Nessim");

		lh.addStaticHoliday ("20-APR-2023", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("23-APR-2023", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("25-APR-2023", "Sinai Liberation Day");

		lh.addStaticHoliday ("01-MAY-2023", "Labour Day");

		lh.addStaticHoliday ("27-JUN-2023", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("28-JUN-2023", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("29-JUN-2023", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("18-JUL-2023", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("19-JUL-2023", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("20-JUL-2023", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("23-JUL-2023", "Revolution Day");

		lh.addStaticHoliday ("26-SEP-2023", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("27-SEP-2023", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("28-SEP-2023", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("08-APR-2024", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("09-APR-2024", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("10-APR-2024", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("11-APR-2024", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("25-APR-2024", "Sinai Liberation Day");

		lh.addStaticHoliday ("01-MAY-2024", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2024", "Orthodox Easter Day");

		lh.addStaticHoliday ("06-MAY-2024", "Sham El Nessim");

		lh.addStaticHoliday ("16-JUN-2024", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("17-JUN-2024", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("18-JUN-2024", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("19-JUN-2024", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("01-JUL-2024", "Bank Holiday");

		lh.addStaticHoliday ("07-JUL-2024", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("08-JUL-2024", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("23-JUL-2024", "Revolution Day");

		lh.addStaticHoliday ("15-SEP-2024", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("16-SEP-2024", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("06-OCT-2024", "Armed Forces Day");

		lh.addStaticHoliday ("30-MAR-2025", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("31-MAR-2025", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("01-APR-2025", "Ramadan Bairam Holiday Period");

		lh.addStaticHoliday ("20-APR-2025", "Orthodox Easter Day");

		lh.addStaticHoliday ("21-APR-2025", "Sham El Nessim");

		lh.addStaticHoliday ("01-MAY-2025", "Labour Day");

		lh.addStaticHoliday ("04-JUN-2025", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("05-JUN-2025", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("08-JUN-2025", "Courban Bairam Holiday Period");

		lh.addStaticHoliday ("25-JUN-2025", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("26-JUN-2025", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("01-JUL-2025", "Bank Holiday");

		lh.addStaticHoliday ("23-JUL-2025", "Revolution Day");

		lh.addStaticHoliday ("03-SEP-2025", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("04-SEP-2025", "Mawled El Nabi Holiday Period");

		lh.addStaticHoliday ("06-OCT-2025", "Armed Forces Day");

		lh.addStandardWeekend();

		return lh;
	}
}
