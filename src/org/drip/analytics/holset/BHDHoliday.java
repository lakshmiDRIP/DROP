
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

public class BHDHoliday implements org.drip.analytics.holset.LocationHoliday {
	public BHDHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "BHD";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("28-JAN-1998", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("29-JAN-1998", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("31-JAN-1998", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("01-FEB-1998", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("05-APR-1998", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("06-APR-1998", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("07-APR-1998", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("08-APR-1998", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("09-APR-1998", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("25-APR-1998", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("26-APR-1998", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("27-APR-1998", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("05-MAY-1998", "Ashoora Holiday Period");

		lh.addStaticHoliday ("06-MAY-1998", "Ashoora Holiday Period");

		lh.addStaticHoliday ("07-MAY-1998", "Ashoora Holiday Period");

		lh.addStaticHoliday ("04-JUL-1998", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("05-JUL-1998", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("06-JUL-1998", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("16-DEC-1998", "National Day");

		lh.addStaticHoliday ("02-JAN-1999", "New Years Day Observed");

		lh.addStaticHoliday ("17-JAN-1999", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("18-JAN-1999", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("19-JAN-1999", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("20-JAN-1999", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("21-JAN-1999", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("27-MAR-1999", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("28-MAR-1999", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("29-MAR-1999", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("30-MAR-1999", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("15-APR-1999", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("17-APR-1999", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("25-APR-1999", "Ashoora Holiday Period");

		lh.addStaticHoliday ("26-APR-1999", "Ashoora Holiday Period");

		lh.addStaticHoliday ("27-APR-1999", "Ashoora Holiday Period");

		lh.addStaticHoliday ("28-APR-1999", "Ashoora Holiday Period");

		lh.addStaticHoliday ("24-JUN-1999", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("26-JUN-1999", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("16-DEC-1999", "National Day");

		lh.addStaticHoliday ("01-JAN-2000", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2000", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("08-JAN-2000", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("09-JAN-2000", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("10-JAN-2000", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("14-MAR-2000", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("15-MAR-2000", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("16-MAR-2000", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("18-MAR-2000", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("04-APR-2000", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("05-APR-2000", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("06-APR-2000", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("15-APR-2000", "Ashoora Holiday Period");

		lh.addStaticHoliday ("16-APR-2000", "Ashoora Holiday Period");

		lh.addStaticHoliday ("17-APR-2000", "Ashoora Holiday Period");

		lh.addStaticHoliday ("13-JUN-2000", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("14-JUN-2000", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("15-JUN-2000", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("16-DEC-2000", "National Day");

		lh.addStaticHoliday ("25-DEC-2000", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("26-DEC-2000", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("27-DEC-2000", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("28-DEC-2000", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("04-MAR-2001", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("05-MAR-2001", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("06-MAR-2001", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("07-MAR-2001", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("08-MAR-2001", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("24-MAR-2001", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("25-MAR-2001", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("26-MAR-2001", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("03-APR-2001", "Ashoora Holiday Period");

		lh.addStaticHoliday ("04-APR-2001", "Ashoora Holiday Period");

		lh.addStaticHoliday ("05-APR-2001", "Ashoora Holiday Period");

		lh.addStaticHoliday ("02-JUN-2001", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("03-JUN-2001", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("04-JUN-2001", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("15-DEC-2001", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("16-DEC-2001", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("17-DEC-2001", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("18-DEC-2001", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("21-FEB-2002", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("23-FEB-2002", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("24-FEB-2002", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("25-FEB-2002", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("14-MAR-2002", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("16-MAR-2002", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("24-MAR-2002", "Ashoora Holiday Period");

		lh.addStaticHoliday ("25-MAR-2002", "Ashoora Holiday Period");

		lh.addStaticHoliday ("26-MAR-2002", "Ashoora Holiday Period");

		lh.addStaticHoliday ("27-MAR-2002", "Ashoora Holiday Period");

		lh.addStaticHoliday ("23-MAY-2002", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("25-MAY-2002", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("04-DEC-2002", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("05-DEC-2002", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("07-DEC-2002", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("08-DEC-2002", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("16-DEC-2002", "National Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("10-FEB-2003", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("11-FEB-2003", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("12-FEB-2003", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("13-FEB-2003", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("03-MAR-2003", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("04-MAR-2003", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("05-MAR-2003", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("13-MAR-2003", "Ashoora Holiday Period");

		lh.addStaticHoliday ("15-MAR-2003", "Ashoora Holiday Period");

		lh.addStaticHoliday ("16-MAR-2003", "Ashoora Holiday Period");

		lh.addStaticHoliday ("12-MAY-2003", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("13-MAY-2003", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("14-MAY-2003", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("23-NOV-2003", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("24-NOV-2003", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("25-NOV-2003", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("26-NOV-2003", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("27-NOV-2003", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("16-DEC-2003", "National Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("31-JAN-2004", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("01-FEB-2004", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("02-FEB-2004", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("03-FEB-2004", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("19-FEB-2004", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("21-FEB-2004", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("29-FEB-2004", "Ashoora Holiday Period");

		lh.addStaticHoliday ("01-MAR-2004", "Ashoora Holiday Period");

		lh.addStaticHoliday ("02-MAR-2004", "Ashoora Holiday Period");

		lh.addStaticHoliday ("03-MAR-2004", "Ashoora Holiday Period");

		lh.addStaticHoliday ("01-MAY-2004", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("02-MAY-2004", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("13-NOV-2004", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("14-NOV-2004", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("15-NOV-2004", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("16-NOV-2004", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("16-DEC-2004", "National Day");

		lh.addStaticHoliday ("01-JAN-2005", "New Years Day");

		lh.addStaticHoliday ("19-JAN-2005", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("20-JAN-2005", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("22-JAN-2005", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("23-JAN-2005", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("08-FEB-2005", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("09-FEB-2005", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("10-FEB-2005", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("19-FEB-2005", "Ashoora Holiday Period");

		lh.addStaticHoliday ("20-FEB-2005", "Ashoora Holiday Period");

		lh.addStaticHoliday ("21-FEB-2005", "Ashoora Holiday Period");

		lh.addStaticHoliday ("19-APR-2005", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("20-APR-2005", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("21-APR-2005", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("01-NOV-2005", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("02-NOV-2005", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("03-NOV-2005", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("05-NOV-2005", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("17-DEC-2005", "National Day Observed");

		lh.addStaticHoliday ("01-JAN-2006", "New Years Day");

		lh.addStaticHoliday ("08-JAN-2006", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("09-JAN-2006", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("10-JAN-2006", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("11-JAN-2006", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("12-JAN-2006", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("29-JAN-2006", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("30-JAN-2006", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("31-JAN-2006", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("08-FEB-2006", "Ashoora Holiday Period");

		lh.addStaticHoliday ("09-FEB-2006", "Ashoora Holiday Period");

		lh.addStaticHoliday ("11-FEB-2006", "Ashoora Holiday Period");

		lh.addStaticHoliday ("09-APR-2006", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("10-APR-2006", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("11-APR-2006", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("22-OCT-2006", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("23-OCT-2006", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("24-OCT-2006", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("25-OCT-2006", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("26-OCT-2006", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("16-DEC-2006", "National Day");

		lh.addStaticHoliday ("30-DEC-2006", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("31-DEC-2006", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("01-JAN-2007", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("02-JAN-2007", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("18-JAN-2007", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("20-JAN-2007", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("28-JAN-2007", "Ashoora Holiday Period");

		lh.addStaticHoliday ("29-JAN-2007", "Ashoora Holiday Period");

		lh.addStaticHoliday ("30-JAN-2007", "Ashoora Holiday Period");

		lh.addStaticHoliday ("31-JAN-2007", "Ashoora Holiday Period");

		lh.addStaticHoliday ("29-MAR-2007", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("31-MAR-2007", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("11-OCT-2007", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("13-OCT-2007", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("14-OCT-2007", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("15-OCT-2007", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("16-DEC-2007", "National Day");

		lh.addStaticHoliday ("18-DEC-2007", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("19-DEC-2007", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("20-DEC-2007", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("22-DEC-2007", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("08-JAN-2008", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("09-JAN-2008", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("10-JAN-2008", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("19-JAN-2008", "Ashoora Holiday Period");

		lh.addStaticHoliday ("20-JAN-2008", "Ashoora Holiday Period");

		lh.addStaticHoliday ("21-JAN-2008", "Ashoora Holiday Period");

		lh.addStaticHoliday ("18-MAR-2008", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("19-MAR-2008", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("20-MAR-2008", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("29-SEP-2008", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("30-SEP-2008", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("01-OCT-2008", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("02-OCT-2008", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("06-DEC-2008", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("07-DEC-2008", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("08-DEC-2008", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("09-DEC-2008", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("10-DEC-2008", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("16-DEC-2008", "National Day");

		lh.addStaticHoliday ("27-DEC-2008", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("28-DEC-2008", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("29-DEC-2008", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2009", "Ashoora Holiday Period");

		lh.addStaticHoliday ("07-JAN-2009", "Ashoora Holiday Period");

		lh.addStaticHoliday ("08-JAN-2009", "Ashoora Holiday Period");

		lh.addStaticHoliday ("07-MAR-2009", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("08-MAR-2009", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("09-MAR-2009", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("19-SEP-2009", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("20-SEP-2009", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("21-SEP-2009", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("22-SEP-2009", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("25-NOV-2009", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("26-NOV-2009", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("28-NOV-2009", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("29-NOV-2009", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("16-DEC-2009", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("17-DEC-2009", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("26-DEC-2009", "Ashoora Holiday Period");

		lh.addStaticHoliday ("27-DEC-2009", "Ashoora Holiday Period");

		lh.addStaticHoliday ("28-DEC-2009", "Ashoora Holiday Period");

		lh.addStaticHoliday ("29-DEC-2009", "Ashoora Holiday Period");

		lh.addStaticHoliday ("02-JAN-2010", "New Years Day Observed");

		lh.addStaticHoliday ("24-FEB-2010", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("25-FEB-2010", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("08-SEP-2010", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("09-SEP-2010", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("11-SEP-2010", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("12-SEP-2010", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("15-NOV-2010", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("16-NOV-2010", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("17-NOV-2010", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("18-NOV-2010", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("05-DEC-2010", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("06-DEC-2010", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("07-DEC-2010", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("15-DEC-2010", "Ashoora Holiday Period");

		lh.addStaticHoliday ("16-DEC-2010", "Ashoora Holiday Period");

		lh.addStaticHoliday ("18-DEC-2010", "Ashoora Holiday Period");

		lh.addStaticHoliday ("01-JAN-2011", "New Years Day");

		lh.addStaticHoliday ("13-FEB-2011", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("14-FEB-2011", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("15-FEB-2011", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("28-AUG-2011", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("29-AUG-2011", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("30-AUG-2011", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("31-AUG-2011", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("01-SEP-2011", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("05-NOV-2011", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("06-NOV-2011", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("07-NOV-2011", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("08-NOV-2011", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("24-NOV-2011", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("26-NOV-2011", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("04-DEC-2011", "Ashoora Holiday Period");

		lh.addStaticHoliday ("05-DEC-2011", "Ashoora Holiday Period");

		lh.addStaticHoliday ("06-DEC-2011", "Ashoora Holiday Period");

		lh.addStaticHoliday ("07-DEC-2011", "Ashoora Holiday Period");

		lh.addStaticHoliday ("17-DEC-2011", "National Day Observed");

		lh.addStaticHoliday ("01-JAN-2012", "New Years Day");

		lh.addStaticHoliday ("04-FEB-2012", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("05-FEB-2012", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("18-AUG-2012", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("19-AUG-2012", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("20-AUG-2012", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("21-AUG-2012", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("24-OCT-2012", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("25-OCT-2012", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("27-OCT-2012", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("28-OCT-2012", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("13-NOV-2012", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("14-NOV-2012", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("15-NOV-2012", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("24-NOV-2012", "Ashoora Holiday Period");

		lh.addStaticHoliday ("25-NOV-2012", "Ashoora Holiday Period");

		lh.addStaticHoliday ("26-NOV-2012", "Ashoora Holiday Period");

		lh.addStaticHoliday ("16-DEC-2012", "National Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("22-JAN-2013", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("23-JAN-2013", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("24-JAN-2013", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("06-AUG-2013", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("07-AUG-2013", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("08-AUG-2013", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("10-AUG-2013", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("13-OCT-2013", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("14-OCT-2013", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("15-OCT-2013", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("16-OCT-2013", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("17-OCT-2013", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("03-NOV-2013", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("04-NOV-2013", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("05-NOV-2013", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("13-NOV-2013", "Ashoora Holiday Period");

		lh.addStaticHoliday ("14-NOV-2013", "Ashoora Holiday Period");

		lh.addStaticHoliday ("16-NOV-2013", "Ashoora Holiday Period");

		lh.addStaticHoliday ("16-DEC-2013", "National Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("12-JAN-2014", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("13-JAN-2014", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("14-JAN-2014", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("27-JUL-2014", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("28-JUL-2014", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("29-JUL-2014", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("30-JUL-2014", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("31-JUL-2014", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("04-OCT-2014", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("05-OCT-2014", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("06-OCT-2014", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("07-OCT-2014", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("23-OCT-2014", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("25-OCT-2014", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("02-NOV-2014", "Ashoora Holiday Period");

		lh.addStaticHoliday ("03-NOV-2014", "Ashoora Holiday Period");

		lh.addStaticHoliday ("04-NOV-2014", "Ashoora Holiday Period");

		lh.addStaticHoliday ("05-NOV-2014", "Ashoora Holiday Period");

		lh.addStaticHoliday ("16-DEC-2014", "National Day");

		lh.addStaticHoliday ("01-JAN-2015", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("03-JAN-2015", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("16-JUL-2015", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("18-JUL-2015", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("19-JUL-2015", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("20-JUL-2015", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("22-SEP-2015", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("23-SEP-2015", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("24-SEP-2015", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("26-SEP-2015", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("13-OCT-2015", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("14-OCT-2015", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("15-OCT-2015", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("24-OCT-2015", "Ashoora Holiday Period");

		lh.addStaticHoliday ("25-OCT-2015", "Ashoora Holiday Period");

		lh.addStaticHoliday ("26-OCT-2015", "Ashoora Holiday Period");

		lh.addStaticHoliday ("16-DEC-2015", "National Day");

		lh.addStaticHoliday ("22-DEC-2015", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("23-DEC-2015", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("24-DEC-2015", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("02-JAN-2016", "New Years Day Observed");

		lh.addStaticHoliday ("04-JUL-2016", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("05-JUL-2016", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("06-JUL-2016", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("07-JUL-2016", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("10-SEP-2016", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("11-SEP-2016", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("12-SEP-2016", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("13-SEP-2016", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("14-SEP-2016", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("01-OCT-2016", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("02-OCT-2016", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("03-OCT-2016", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("11-OCT-2016", "Ashoora Holiday Period");

		lh.addStaticHoliday ("12-OCT-2016", "Ashoora Holiday Period");

		lh.addStaticHoliday ("13-OCT-2016", "Ashoora Holiday Period");

		lh.addStaticHoliday ("10-DEC-2016", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("11-DEC-2016", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("12-DEC-2016", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("17-DEC-2016", "National Day Observed");

		lh.addStaticHoliday ("01-JAN-2017", "New Years Day");

		lh.addStaticHoliday ("24-JUN-2017", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("25-JUN-2017", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("26-JUN-2017", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("27-JUN-2017", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("30-AUG-2017", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("31-AUG-2017", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("02-SEP-2017", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("03-SEP-2017", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("20-SEP-2017", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("21-SEP-2017", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("30-SEP-2017", "Ashoora Holiday Period");

		lh.addStaticHoliday ("01-OCT-2017", "Ashoora Holiday Period");

		lh.addStaticHoliday ("02-OCT-2017", "Ashoora Holiday Period");

		lh.addStaticHoliday ("03-OCT-2017", "Ashoora Holiday Period");

		lh.addStaticHoliday ("29-NOV-2017", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("30-NOV-2017", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("16-DEC-2017", "National Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("13-JUN-2018", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("14-JUN-2018", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("16-JUN-2018", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("17-JUN-2018", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("20-AUG-2018", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("21-AUG-2018", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("22-AUG-2018", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("23-AUG-2018", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("09-SEP-2018", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("10-SEP-2018", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("11-SEP-2018", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("19-SEP-2018", "Ashoora Holiday Period");

		lh.addStaticHoliday ("20-SEP-2018", "Ashoora Holiday Period");

		lh.addStaticHoliday ("22-SEP-2018", "Ashoora Holiday Period");

		lh.addStaticHoliday ("18-NOV-2018", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("19-NOV-2018", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("20-NOV-2018", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("16-DEC-2018", "National Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("03-JUN-2019", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("04-JUN-2019", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("05-JUN-2019", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("06-JUN-2019", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("10-AUG-2019", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("11-AUG-2019", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("12-AUG-2019", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("13-AUG-2019", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("29-AUG-2019", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("31-AUG-2019", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("08-SEP-2019", "Ashoora Holiday Period");

		lh.addStaticHoliday ("09-SEP-2019", "Ashoora Holiday Period");

		lh.addStaticHoliday ("10-SEP-2019", "Ashoora Holiday Period");

		lh.addStaticHoliday ("11-SEP-2019", "Ashoora Holiday Period");

		lh.addStaticHoliday ("07-NOV-2019", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("09-NOV-2019", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("16-DEC-2019", "National Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("23-MAY-2020", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("24-MAY-2020", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("25-MAY-2020", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("26-MAY-2020", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("29-JUL-2020", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("30-JUL-2020", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("01-AUG-2020", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("02-AUG-2020", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("18-AUG-2020", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("19-AUG-2020", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("20-AUG-2020", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("29-AUG-2020", "Ashoora Holiday Period");

		lh.addStaticHoliday ("30-AUG-2020", "Ashoora Holiday Period");

		lh.addStaticHoliday ("31-AUG-2020", "Ashoora Holiday Period");

		lh.addStaticHoliday ("27-OCT-2020", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("28-OCT-2020", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("29-OCT-2020", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("16-DEC-2020", "National Day");

		lh.addStaticHoliday ("02-JAN-2021", "New Years Day Observed");

		lh.addStaticHoliday ("11-MAY-2021", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("12-MAY-2021", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("13-MAY-2021", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("15-MAY-2021", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("18-JUL-2021", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("19-JUL-2021", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("20-JUL-2021", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("21-JUL-2021", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("22-JUL-2021", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("08-AUG-2021", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("09-AUG-2021", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("10-AUG-2021", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("18-AUG-2021", "Ashoora Holiday Period");

		lh.addStaticHoliday ("19-AUG-2021", "Ashoora Holiday Period");

		lh.addStaticHoliday ("21-AUG-2021", "Ashoora Holiday Period");

		lh.addStaticHoliday ("16-OCT-2021", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("17-OCT-2021", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("18-OCT-2021", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("16-DEC-2021", "National Day");

		lh.addStaticHoliday ("01-JAN-2022", "New Years Day");

		lh.addStaticHoliday ("30-APR-2022", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("01-MAY-2022", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("02-MAY-2022", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("03-MAY-2022", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("04-MAY-2022", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("07-JUL-2022", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("09-JUL-2022", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("10-JUL-2022", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("11-JUL-2022", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("28-JUL-2022", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("30-JUL-2022", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("07-AUG-2022", "Ashoora Holiday Period");

		lh.addStaticHoliday ("08-AUG-2022", "Ashoora Holiday Period");

		lh.addStaticHoliday ("09-AUG-2022", "Ashoora Holiday Period");

		lh.addStaticHoliday ("10-AUG-2022", "Ashoora Holiday Period");

		lh.addStaticHoliday ("06-OCT-2022", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("08-OCT-2022", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("17-DEC-2022", "National Day Observed");

		lh.addStaticHoliday ("01-JAN-2023", "New Years Day");

		lh.addStaticHoliday ("20-APR-2023", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("22-APR-2023", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("23-APR-2023", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("24-APR-2023", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("27-JUN-2023", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("28-JUN-2023", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("29-JUN-2023", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("01-JUL-2023", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("17-JUL-2023", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("18-JUL-2023", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("19-JUL-2023", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("27-JUL-2023", "Ashoora Holiday Period");

		lh.addStaticHoliday ("29-JUL-2023", "Ashoora Holiday Period");

		lh.addStaticHoliday ("30-JUL-2023", "Ashoora Holiday Period");

		lh.addStaticHoliday ("25-SEP-2023", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("26-SEP-2023", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("27-SEP-2023", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("16-DEC-2023", "National Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("08-APR-2024", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("09-APR-2024", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("10-APR-2024", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("11-APR-2024", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("15-JUN-2024", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("16-JUN-2024", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("17-JUN-2024", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("18-JUN-2024", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("19-JUN-2024", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("06-JUL-2024", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("07-JUL-2024", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("15-JUL-2024", "Ashoora Holiday Period");

		lh.addStaticHoliday ("16-JUL-2024", "Ashoora Holiday Period");

		lh.addStaticHoliday ("17-JUL-2024", "Ashoora Holiday Period");

		lh.addStaticHoliday ("18-JUL-2024", "Ashoora Holiday Period");

		lh.addStaticHoliday ("14-SEP-2024", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("15-SEP-2024", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("16-DEC-2024", "National Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("29-MAR-2025", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("30-MAR-2025", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("31-MAR-2025", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("01-APR-2025", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("02-APR-2025", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("04-JUN-2025", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("05-JUN-2025", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("07-JUN-2025", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("08-JUN-2025", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("24-JUN-2025", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("25-JUN-2025", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("26-JUN-2025", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("05-JUL-2025", "Ashoora Holiday Period");

		lh.addStaticHoliday ("06-JUL-2025", "Ashoora Holiday Period");

		lh.addStaticHoliday ("07-JUL-2025", "Ashoora Holiday Period");

		lh.addStaticHoliday ("02-SEP-2025", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("03-SEP-2025", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("04-SEP-2025", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("16-DEC-2025", "National Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("18-MAR-2026", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("19-MAR-2026", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("21-MAR-2026", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("22-MAR-2026", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("25-MAY-2026", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("26-MAY-2026", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("27-MAY-2026", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("28-MAY-2026", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("14-JUN-2026", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("15-JUN-2026", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("16-JUN-2026", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("24-JUN-2026", "Ashoora Holiday Period");

		lh.addStaticHoliday ("25-JUN-2026", "Ashoora Holiday Period");

		lh.addStaticHoliday ("27-JUN-2026", "Ashoora Holiday Period");

		lh.addStaticHoliday ("23-AUG-2026", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("24-AUG-2026", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("25-AUG-2026", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("16-DEC-2026", "National Day");

		lh.addStaticHoliday ("02-JAN-2027", "New Years Day Observed");

		lh.addStaticHoliday ("08-MAR-2027", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("09-MAR-2027", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("10-MAR-2027", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("11-MAR-2027", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("15-MAY-2027", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("16-MAY-2027", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("17-MAY-2027", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("18-MAY-2027", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("19-MAY-2027", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("05-JUN-2027", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("06-JUN-2027", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("14-JUN-2027", "Ashoora Holiday Period");

		lh.addStaticHoliday ("15-JUN-2027", "Ashoora Holiday Period");

		lh.addStaticHoliday ("16-JUN-2027", "Ashoora Holiday Period");

		lh.addStaticHoliday ("17-JUN-2027", "Ashoora Holiday Period");

		lh.addStaticHoliday ("14-AUG-2027", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("15-AUG-2027", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("16-DEC-2027", "National Day");

		lh.addStaticHoliday ("01-JAN-2028", "New Years Day");

		lh.addStaticHoliday ("26-FEB-2028", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("27-FEB-2028", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("28-FEB-2028", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("29-FEB-2028", "Eid Al Fiter Holiday Period");

		lh.addStaticHoliday ("03-MAY-2028", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("04-MAY-2028", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("06-MAY-2028", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("07-MAY-2028", "Eid Al Adha Holiday Period");

		lh.addStaticHoliday ("23-MAY-2028", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("24-MAY-2028", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("25-MAY-2028", "Al Hijrah New Year Holiday Period");

		lh.addStaticHoliday ("03-JUN-2028", "Ashoora Holiday Period");

		lh.addStaticHoliday ("04-JUN-2028", "Ashoora Holiday Period");

		lh.addStaticHoliday ("05-JUN-2028", "Ashoora Holiday Period");

		lh.addStaticHoliday ("01-AUG-2028", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("02-AUG-2028", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("03-AUG-2028", "Prophet Muhammads Birthday Holiday Period");

		lh.addStaticHoliday ("16-DEC-2028", "National Day");

		lh.addStandardWeekend();

		return lh;
	}
}
