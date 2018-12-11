
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

public class MIXHoliday implements org.drip.analytics.holset.LocationHoliday {
	public MIXHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "MIX";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1996", "New Years Day");

		lh.addStaticHoliday ("02-JAN-1996", "New Years Holiday");

		lh.addStaticHoliday ("03-JAN-1996", "Bank Holiday");

		lh.addStaticHoliday ("15-JAN-1996", "Adults Day");

		lh.addStaticHoliday ("12-FEB-1996", "Founding Day");

		lh.addStaticHoliday ("20-MAR-1996", "Vernal Equinox");

		lh.addStaticHoliday ("05-APR-1996", "Good Friday");

		lh.addStaticHoliday ("08-APR-1996", "Easter Monday");

		lh.addStaticHoliday ("29-APR-1996", "Greenery Day");

		lh.addStaticHoliday ("03-MAY-1996", "Constitutional");

		lh.addStaticHoliday ("04-MAY-1996", "Bank Holiday");

		lh.addStaticHoliday ("06-MAY-1996", "May Day");

		lh.addStaticHoliday ("27-MAY-1996", "Late Spring Hol");

		lh.addStaticHoliday ("26-AUG-1996", "Late Summer Hol");

		lh.addStaticHoliday ("16-SEP-1996", "Aged Day");

		lh.addStaticHoliday ("23-SEP-1996", "Autumnal Equinox");

		lh.addStaticHoliday ("10-OCT-1996", "Physical Ed. Day");

		lh.addStaticHoliday ("04-NOV-1996", "Culture Day");

		lh.addStaticHoliday ("23-NOV-1996", "Labor Day");

		lh.addStaticHoliday ("23-DEC-1996", "Emperors Day");

		lh.addStaticHoliday ("25-DEC-1996", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-1996", "Boxing Day");

		lh.addStaticHoliday ("31-DEC-1996", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-1997", "New Years Day");

		lh.addStaticHoliday ("02-JAN-1997", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-1997", "Bank Holiday");

		lh.addStaticHoliday ("15-JAN-1997", "Adults Day");

		lh.addStaticHoliday ("11-FEB-1997", "National Foundation Day");

		lh.addStaticHoliday ("20-MAR-1997", "Vernal Equinox Day");

		lh.addStaticHoliday ("28-MAR-1997", "Good Friday");

		lh.addStaticHoliday ("31-MAR-1997", "Easter Monday");

		lh.addStaticHoliday ("29-APR-1997", "Greenery Day");

		lh.addStaticHoliday ("03-MAY-1997", "Constitutional");

		lh.addStaticHoliday ("05-MAY-1997", "May Day");

		lh.addStaticHoliday ("26-MAY-1997", "Spring Bank Holiday");

		lh.addStaticHoliday ("20-JUL-1997", "Maritime Day");

		lh.addStaticHoliday ("21-JUL-1997", "Ocean Day Observed");

		lh.addStaticHoliday ("25-AUG-1997", "August Bank Holiday");

		lh.addStaticHoliday ("15-SEP-1997", "Respect for the Aged Day");

		lh.addStaticHoliday ("23-SEP-1997", "Autumnal Equinox Day");

		lh.addStaticHoliday ("10-OCT-1997", "Sports Day");

		lh.addStaticHoliday ("03-NOV-1997", "Culture Day");

		lh.addStaticHoliday ("24-NOV-1997", "Labour Thanksgiving Day Observ");

		lh.addStaticHoliday ("23-DEC-1997", "Emperors Birthday");

		lh.addStaticHoliday ("25-DEC-1997", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-1997", "Boxing Day");

		lh.addStaticHoliday ("31-DEC-1997", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("02-JAN-1998", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-1998", "Bank Holiday");

		lh.addStaticHoliday ("15-JAN-1998", "Adults Day");

		lh.addStaticHoliday ("11-FEB-1998", "National Foundation Day");

		lh.addStaticHoliday ("21-MAR-1998", "Vernal Equinox");

		lh.addStaticHoliday ("10-APR-1998", "Good Friday");

		lh.addStaticHoliday ("13-APR-1998", "Easter Monday");

		lh.addStaticHoliday ("29-APR-1998", "Greenery Day");

		lh.addStaticHoliday ("04-MAY-1998", "May Day");

		lh.addStaticHoliday ("05-MAY-1998", "Childrens Day");

		lh.addStaticHoliday ("25-MAY-1998", "Spring Bank Holiday");

		lh.addStaticHoliday ("20-JUL-1998", "Ocean Day");

		lh.addStaticHoliday ("31-AUG-1998", "August Bank Holiday");

		lh.addStaticHoliday ("15-SEP-1998", "Respect for the Aged Day");

		lh.addStaticHoliday ("23-SEP-1998", "Autumnal Equinox Day");

		lh.addStaticHoliday ("10-OCT-1998", "Physical Ed. Day");

		lh.addStaticHoliday ("03-NOV-1998", "Culture Day");

		lh.addStaticHoliday ("23-NOV-1998", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("23-DEC-1998", "Emperors Birthday");

		lh.addStaticHoliday ("25-DEC-1998", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-1998", "Boxing Day Observed");

		lh.addStaticHoliday ("31-DEC-1998", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("02-JAN-1999", "New Years Holiday");

		lh.addStaticHoliday ("03-JAN-1999", "New Year Observance");

		lh.addStaticHoliday ("15-JAN-1999", "Adults Day");

		lh.addStaticHoliday ("11-FEB-1999", "National Foundation Day");

		lh.addStaticHoliday ("22-MAR-1999", "Vernal Equinox Day Observed");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("05-APR-1999", "Easter Monday");

		lh.addStaticHoliday ("29-APR-1999", "Greenery Day");

		lh.addStaticHoliday ("03-MAY-1999", "May Day");

		lh.addStaticHoliday ("04-MAY-1999", "National Holiday");

		lh.addStaticHoliday ("05-MAY-1999", "Childrens Day");

		lh.addStaticHoliday ("31-MAY-1999", "Spring Bank Holiday");

		lh.addStaticHoliday ("20-JUL-1999", "Ocean Day");

		lh.addStaticHoliday ("30-AUG-1999", "August Bank Holiday");

		lh.addStaticHoliday ("15-SEP-1999", "Respect for the Aged Day");

		lh.addStaticHoliday ("23-SEP-1999", "Autumnal Equinox Day");

		lh.addStaticHoliday ("11-OCT-1999", "Sports Day Observed");

		lh.addStaticHoliday ("03-NOV-1999", "Culture Day");

		lh.addStaticHoliday ("23-NOV-1999", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("23-DEC-1999", "Emperors Birthday");

		lh.addStaticHoliday ("27-DEC-1999", "Boxing Day Observed");

		lh.addStaticHoliday ("28-DEC-1999", "Christmas Day Observed");

		lh.addStaticHoliday ("31-DEC-1999", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2000", "New Year");

		lh.addStaticHoliday ("03-JAN-2000", "New Years Day Observed");

		lh.addStaticHoliday ("10-JAN-2000", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2000", "National Foundation Day");

		lh.addStaticHoliday ("20-MAR-2000", "Vernal Equinox Day");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("24-APR-2000", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2000", "Greenery Day");

		lh.addStaticHoliday ("01-MAY-2000", "May Day");

		lh.addStaticHoliday ("03-MAY-2000", "Constitution Memorial Day");

		lh.addStaticHoliday ("04-MAY-2000", "National Holiday");

		lh.addStaticHoliday ("05-MAY-2000", "Childrens Day");

		lh.addStaticHoliday ("29-MAY-2000", "Spring Bank Holiday");

		lh.addStaticHoliday ("20-JUL-2000", "Ocean Day");

		lh.addStaticHoliday ("28-AUG-2000", "August Bank Holiday");

		lh.addStaticHoliday ("15-SEP-2000", "Respect for the Aged Day");

		lh.addStaticHoliday ("23-SEP-2000", "Autumnal Equinox");

		lh.addStaticHoliday ("09-OCT-2000", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2000", "Culture Day");

		lh.addStaticHoliday ("23-NOV-2000", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("23-DEC-2000", "Emperors Day");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2000", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2001", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2001", "Bank Holiday");

		lh.addStaticHoliday ("08-JAN-2001", "Adults Day");

		lh.addStaticHoliday ("12-FEB-2001", "National Foundation Day Observ");

		lh.addStaticHoliday ("20-MAR-2001", "Vernal Equinox Day");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("16-APR-2001", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2001", "Greenery Day Observed");

		lh.addStaticHoliday ("03-MAY-2001", "Constitution Memorial Day");

		lh.addStaticHoliday ("04-MAY-2001", "National Holiday");

		lh.addStaticHoliday ("05-MAY-2001", "Childrens Day");

		lh.addStaticHoliday ("07-MAY-2001", "May Day");

		lh.addStaticHoliday ("28-MAY-2001", "Spring Bank Holiday");

		lh.addStaticHoliday ("20-JUL-2001", "Ocean Day");

		lh.addStaticHoliday ("27-AUG-2001", "August Bank Holiday");

		lh.addStaticHoliday ("15-SEP-2001", "Aged Day");

		lh.addStaticHoliday ("24-SEP-2001", "Autumnal Equinox Day Observed");

		lh.addStaticHoliday ("08-OCT-2001", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2001", "Culture Day");

		lh.addStaticHoliday ("23-NOV-2001", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("24-DEC-2001", "Emperors Birthday Observed");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2001", "Boxing Day");

		lh.addStaticHoliday ("31-DEC-2001", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2002", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2002", "Bank Holiday");

		lh.addStaticHoliday ("14-JAN-2002", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2002", "National Foundation Day");

		lh.addStaticHoliday ("21-MAR-2002", "Vernal Equinox Day");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("01-APR-2002", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2002", "Greenery Day");

		lh.addStaticHoliday ("03-MAY-2002", "Constitution Memorial Day");

		lh.addStaticHoliday ("04-MAY-2002", "Bank Holiday");

		lh.addStaticHoliday ("06-MAY-2002", "May Day");

		lh.addStaticHoliday ("03-JUN-2002", "Spring Bank Holiday");

		lh.addStaticHoliday ("04-JUN-2002", "Queens 50th Jubilee");

		lh.addStaticHoliday ("26-AUG-2002", "August Bank Holiday");

		lh.addStaticHoliday ("16-SEP-2002", "Respect for the Aged Day Obser");

		lh.addStaticHoliday ("23-SEP-2002", "Autumnal Equinox Day");

		lh.addStaticHoliday ("14-OCT-2002", "Sports Day");

		lh.addStaticHoliday ("04-NOV-2002", "Culture Day Observed");

		lh.addStaticHoliday ("23-NOV-2002", "Labor Thanksgiving Day");

		lh.addStaticHoliday ("23-DEC-2002", "Emperors Birthday");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2002", "Boxing Day");

		lh.addStaticHoliday ("31-DEC-2002", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2003", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2003", "Bank Holiday");

		lh.addStaticHoliday ("13-JAN-2003", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2003", "National Foundation Day");

		lh.addStaticHoliday ("21-MAR-2003", "Vernal Equinox Day");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("21-APR-2003", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2003", "Greenery Day");

		lh.addStaticHoliday ("03-MAY-2003", "Constitutional memory Day");

		lh.addStaticHoliday ("05-MAY-2003", "May Day");

		lh.addStaticHoliday ("26-MAY-2003", "Spring Bank Holiday");

		lh.addStaticHoliday ("21-JUL-2003", "Ocean Day Observed");

		lh.addStaticHoliday ("25-AUG-2003", "August Bank Holiday");

		lh.addStaticHoliday ("15-SEP-2003", "Respect for the Aged Day");

		lh.addStaticHoliday ("23-SEP-2003", "Autumnal Equinox Day");

		lh.addStaticHoliday ("13-OCT-2003", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2003", "Culture Day");

		lh.addStaticHoliday ("24-NOV-2003", "Labour Thanksgiving Day Observ");

		lh.addStaticHoliday ("23-DEC-2003", "Emperors Birthday");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2003", "Boxing Day");

		lh.addStaticHoliday ("31-DEC-2003", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2004", "Bank Holiday");

		lh.addStaticHoliday ("12-JAN-2004", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2004", "National Foundation Day");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("12-APR-2004", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2004", "Greenery Day");

		lh.addStaticHoliday ("03-MAY-2004", "May Day");

		lh.addStaticHoliday ("04-MAY-2004", "National Holiday");

		lh.addStaticHoliday ("05-MAY-2004", "Childrens Day");

		lh.addStaticHoliday ("31-MAY-2004", "Spring Bank Holiday");

		lh.addStaticHoliday ("19-JUL-2004", "Ocean Day");

		lh.addStaticHoliday ("30-AUG-2004", "August Bank Holiday");

		lh.addStaticHoliday ("20-SEP-2004", "Respect for the Aged Day");

		lh.addStaticHoliday ("23-SEP-2004", "Autumnal Equinox Day");

		lh.addStaticHoliday ("11-OCT-2004", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2004", "Culture Day");

		lh.addStaticHoliday ("23-NOV-2004", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("23-DEC-2004", "Emperors Birthday");

		lh.addStaticHoliday ("27-DEC-2004", "Boxing Day Observed");

		lh.addStaticHoliday ("28-DEC-2004", "Christmas Day Observed");

		lh.addStaticHoliday ("31-DEC-2004", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2005", "New Years Day Observed");

		lh.addStaticHoliday ("10-JAN-2005", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2005", "National Foundation Day");

		lh.addStaticHoliday ("21-MAR-2005", "Vernal Equinox Day Observed");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("28-MAR-2005", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2005", "Greenery Day");

		lh.addStaticHoliday ("02-MAY-2005", "May Day");

		lh.addStaticHoliday ("03-MAY-2005", "Constitution Memorial Day");

		lh.addStaticHoliday ("04-MAY-2005", "National Holiday");

		lh.addStaticHoliday ("05-MAY-2005", "Childrens Day");

		lh.addStaticHoliday ("30-MAY-2005", "Spring Bank Holiday");

		lh.addStaticHoliday ("18-JUL-2005", "Ocean Day");

		lh.addStaticHoliday ("29-AUG-2005", "August Bank Holiday");

		lh.addStaticHoliday ("19-SEP-2005", "Respect for the Aged Day");

		lh.addStaticHoliday ("23-SEP-2005", "Autumnal Equinox Day");

		lh.addStaticHoliday ("10-OCT-2005", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2005", "Culture Day");

		lh.addStaticHoliday ("23-NOV-2005", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("23-DEC-2005", "Emperors Birthday");

		lh.addStaticHoliday ("26-DEC-2005", "Boxing Day");

		lh.addStaticHoliday ("27-DEC-2005", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2006", "New Years Day Observed");

		lh.addStaticHoliday ("03-JAN-2006", "Bank Holiday");

		lh.addStaticHoliday ("09-JAN-2006", "Adults Day");

		lh.addStaticHoliday ("21-MAR-2006", "Vernal Equinox Day");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("17-APR-2006", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2006", "May Day");

		lh.addStaticHoliday ("03-MAY-2006", "Constitution Memorial Day");

		lh.addStaticHoliday ("04-MAY-2006", "National Holiday");

		lh.addStaticHoliday ("05-MAY-2006", "Childrens Day");

		lh.addStaticHoliday ("29-MAY-2006", "Spring Bank Holiday");

		lh.addStaticHoliday ("17-JUL-2006", "Ocean Day");

		lh.addStaticHoliday ("28-AUG-2006", "August Bank Holiday");

		lh.addStaticHoliday ("18-SEP-2006", "Respect for the Aged Day");

		lh.addStaticHoliday ("09-OCT-2006", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2006", "Culture Day");

		lh.addStaticHoliday ("23-NOV-2006", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2006", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2007", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2007", "Bank Holiday");

		lh.addStaticHoliday ("08-JAN-2007", "Adults Day");

		lh.addStaticHoliday ("12-FEB-2007", "National Foundation Day Observ");

		lh.addStaticHoliday ("21-MAR-2007", "Vernal Equinox Day");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("09-APR-2007", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2007", "Greenery Day Observed");

		lh.addStaticHoliday ("03-MAY-2007", "Constitution Memorial Day");

		lh.addStaticHoliday ("04-MAY-2007", "National Holiday");

		lh.addStaticHoliday ("07-MAY-2007", "May Day");

		lh.addStaticHoliday ("28-MAY-2007", "Spring Bank Holiday");

		lh.addStaticHoliday ("16-JUL-2007", "Ocean Day");

		lh.addStaticHoliday ("27-AUG-2007", "August Bank Holiday");

		lh.addStaticHoliday ("17-SEP-2007", "Respect for the Aged Day");

		lh.addStaticHoliday ("24-SEP-2007", "Autumnal Equinox Day Observed");

		lh.addStaticHoliday ("08-OCT-2007", "Sports Day");

		lh.addStaticHoliday ("23-NOV-2007", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("24-DEC-2007", "Emperors Birthday Observed");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2007", "Boxing Day");

		lh.addStaticHoliday ("31-DEC-2007", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2008", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2008", "Bank Holiday");

		lh.addStaticHoliday ("14-JAN-2008", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2008", "National Foundation Day");

		lh.addStaticHoliday ("20-MAR-2008", "Vernal Equinox Day");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("24-MAR-2008", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2008", "Greenery Day");

		lh.addStaticHoliday ("05-MAY-2008", "May Day");

		lh.addStaticHoliday ("26-MAY-2008", "Spring Bank Holiday");

		lh.addStaticHoliday ("21-JUL-2008", "Ocean Day Observed");

		lh.addStaticHoliday ("25-AUG-2008", "August Bank Holiday");

		lh.addStaticHoliday ("15-SEP-2008", "Respect for the Aged Day");

		lh.addStaticHoliday ("23-SEP-2008", "Autumnal Equinox Day");

		lh.addStaticHoliday ("13-OCT-2008", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2008", "Culture Day");

		lh.addStaticHoliday ("24-NOV-2008", "Labour Thanksgiving Day Observ");

		lh.addStaticHoliday ("23-DEC-2008", "Emperors Birthday");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2008", "Boxing Day");

		lh.addStaticHoliday ("31-DEC-2008", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2009", "Bank Holiday");

		lh.addStaticHoliday ("12-JAN-2009", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2009", "National Foundation Day");

		lh.addStaticHoliday ("20-MAR-2009", "Vernal Equinox Day");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("13-APR-2009", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2009", "Greenery Day");

		lh.addStaticHoliday ("04-MAY-2009", "May Day");

		lh.addStaticHoliday ("05-MAY-2009", "Childrens Day");

		lh.addStaticHoliday ("25-MAY-2009", "Spring Bank Holiday");

		lh.addStaticHoliday ("20-JUL-2009", "Ocean Day");

		lh.addStaticHoliday ("31-AUG-2009", "August Bank Holiday");

		lh.addStaticHoliday ("21-SEP-2009", "Respect for the Aged Day");

		lh.addStaticHoliday ("22-SEP-2009", "New Holiday");

		lh.addStaticHoliday ("23-SEP-2009", "Autumnal Equinox Day");

		lh.addStaticHoliday ("12-OCT-2009", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2009", "Culture Day");

		lh.addStaticHoliday ("23-NOV-2009", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("23-DEC-2009", "Emperors Birthday");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2009", "Boxing Day Observed");

		lh.addStaticHoliday ("31-DEC-2009", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("11-JAN-2010", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2010", "National Foundation Day");

		lh.addStaticHoliday ("22-MAR-2010", "Vernal Equinox Day Observed");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("05-APR-2010", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2010", "Greenery Day");

		lh.addStaticHoliday ("03-MAY-2010", "May Day");

		lh.addStaticHoliday ("04-MAY-2010", "National Holiday");

		lh.addStaticHoliday ("05-MAY-2010", "Childrens Day");

		lh.addStaticHoliday ("31-MAY-2010", "Spring Bank Holiday");

		lh.addStaticHoliday ("19-JUL-2010", "Ocean Day");

		lh.addStaticHoliday ("30-AUG-2010", "August Bank Holiday");

		lh.addStaticHoliday ("20-SEP-2010", "Respect for the Aged Day");

		lh.addStaticHoliday ("23-SEP-2010", "Autumnal Equinox Day");

		lh.addStaticHoliday ("11-OCT-2010", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2010", "Culture Day");

		lh.addStaticHoliday ("23-NOV-2010", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("23-DEC-2010", "Emperors Birthday");

		lh.addStaticHoliday ("27-DEC-2010", "Boxing Day Observed");

		lh.addStaticHoliday ("28-DEC-2010", "Christmas Day Observed");

		lh.addStaticHoliday ("31-DEC-2010", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2011", "New Years Day Observed");

		lh.addStaticHoliday ("10-JAN-2011", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2011", "National Foundation Day");

		lh.addStaticHoliday ("21-MAR-2011", "Vernal Equinox Day");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("25-APR-2011", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2011", "Greenery Day");

		lh.addStaticHoliday ("02-MAY-2011", "May Day");

		lh.addStaticHoliday ("03-MAY-2011", "Constitution Memorial Day");

		lh.addStaticHoliday ("04-MAY-2011", "National Holiday");

		lh.addStaticHoliday ("05-MAY-2011", "Childrens Day");

		lh.addStaticHoliday ("30-MAY-2011", "Spring Bank Holiday");

		lh.addStaticHoliday ("18-JUL-2011", "Ocean Day");

		lh.addStaticHoliday ("29-AUG-2011", "August Bank Holiday");

		lh.addStaticHoliday ("19-SEP-2011", "Respect for the Aged Day");

		lh.addStaticHoliday ("23-SEP-2011", "Autumnal Equinox Day");

		lh.addStaticHoliday ("10-OCT-2011", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2011", "Culture Day");

		lh.addStaticHoliday ("23-NOV-2011", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("23-DEC-2011", "Emperors Birthday");

		lh.addStaticHoliday ("26-DEC-2011", "Boxing Day");

		lh.addStaticHoliday ("27-DEC-2011", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2012", "New Years Day Observed");

		lh.addStaticHoliday ("03-JAN-2012", "Bank Holiday");

		lh.addStaticHoliday ("09-JAN-2012", "Adults Day");

		lh.addStaticHoliday ("20-MAR-2012", "Vernal Equinox Day");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("09-APR-2012", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2012", "Greenery Day Observed");

		lh.addStaticHoliday ("03-MAY-2012", "Constitution Memorial Day");

		lh.addStaticHoliday ("04-MAY-2012", "National Holiday");

		lh.addStaticHoliday ("07-MAY-2012", "May Day");

		lh.addStaticHoliday ("28-MAY-2012", "Spring Bank Holiday");

		lh.addStaticHoliday ("16-JUL-2012", "Ocean Day");

		lh.addStaticHoliday ("27-AUG-2012", "August Bank Holiday");

		lh.addStaticHoliday ("17-SEP-2012", "Respect for the Aged Day");

		lh.addStaticHoliday ("08-OCT-2012", "Sports Day");

		lh.addStaticHoliday ("23-NOV-2012", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("24-DEC-2012", "Emperors Birthday Observed");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2012", "Boxing Day");

		lh.addStaticHoliday ("31-DEC-2012", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2013", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2013", "Bank Holiday");

		lh.addStaticHoliday ("14-JAN-2013", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2013", "National Foundation Day");

		lh.addStaticHoliday ("20-MAR-2013", "Vernal Equinox Day");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("01-APR-2013", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2013", "Greenery Day");

		lh.addStaticHoliday ("03-MAY-2013", "Constitution Memorial Day");

		lh.addStaticHoliday ("06-MAY-2013", "May Day");

		lh.addStaticHoliday ("27-MAY-2013", "Spring Bank Holiday");

		lh.addStaticHoliday ("15-JUL-2013", "Ocean Day Observed");

		lh.addStaticHoliday ("26-AUG-2013", "August Bank Holiday");

		lh.addStaticHoliday ("16-SEP-2013", "Respect for the Aged Day Obser");

		lh.addStaticHoliday ("23-SEP-2013", "Autumnal Equinox Day");

		lh.addStaticHoliday ("14-OCT-2013", "Sports Day");

		lh.addStaticHoliday ("04-NOV-2013", "Culture Day Observed");

		lh.addStaticHoliday ("23-DEC-2013", "Emperors Birthday");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2013", "Boxing Day");

		lh.addStaticHoliday ("31-DEC-2013", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2014", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2014", "Bank Holiday");

		lh.addStaticHoliday ("13-JAN-2014", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2014", "National Foundation Day");

		lh.addStaticHoliday ("21-MAR-2014", "Vernal Equinox Day");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("21-APR-2014", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2014", "Greenery Day");

		lh.addStaticHoliday ("05-MAY-2014", "May Day");

		lh.addStaticHoliday ("26-MAY-2014", "Spring Bank Holiday");

		lh.addStaticHoliday ("21-JUL-2014", "Ocean Day Observed");

		lh.addStaticHoliday ("25-AUG-2014", "August Bank Holiday");

		lh.addStaticHoliday ("15-SEP-2014", "Respect for the Aged Day");

		lh.addStaticHoliday ("23-SEP-2014", "Autumnal Equinox Day");

		lh.addStaticHoliday ("13-OCT-2014", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2014", "Culture Day");

		lh.addStaticHoliday ("24-NOV-2014", "Labour Thanksgiving Day Observ");

		lh.addStaticHoliday ("23-DEC-2014", "Emperors Birthday");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2014", "Boxing Day");

		lh.addStaticHoliday ("31-DEC-2014", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2015", "Bank Holiday");

		lh.addStaticHoliday ("12-JAN-2015", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2015", "National Foundation Day");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("06-APR-2015", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2015", "Greenery Day");

		lh.addStaticHoliday ("04-MAY-2015", "May Day");

		lh.addStaticHoliday ("05-MAY-2015", "Childrens Day");

		lh.addStaticHoliday ("25-MAY-2015", "Spring Bank Holiday");

		lh.addStaticHoliday ("20-JUL-2015", "Ocean Day");

		lh.addStaticHoliday ("31-AUG-2015", "August Bank Holiday");

		lh.addStaticHoliday ("21-SEP-2015", "Respect for the Aged Day");

		lh.addStaticHoliday ("23-SEP-2015", "Autumnal Equinox Day");

		lh.addStaticHoliday ("12-OCT-2015", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2015", "Culture Day");

		lh.addStaticHoliday ("23-NOV-2015", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("23-DEC-2015", "Emperors Birthday");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2015", "Boxing Day Observed");

		lh.addStaticHoliday ("31-DEC-2015", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("11-JAN-2016", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2016", "National Foundation Day");

		lh.addStaticHoliday ("21-MAR-2016", "Vernal Equinox Day Observed");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("28-MAR-2016", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2016", "Greenery Day");

		lh.addStaticHoliday ("02-MAY-2016", "May Day");

		lh.addStaticHoliday ("03-MAY-2016", "Constitution Memorial Day");

		lh.addStaticHoliday ("04-MAY-2016", "National Holiday");

		lh.addStaticHoliday ("05-MAY-2016", "Childrens Day");

		lh.addStaticHoliday ("30-MAY-2016", "Spring Bank Holiday");

		lh.addStaticHoliday ("18-JUL-2016", "Ocean Day");

		lh.addStaticHoliday ("29-AUG-2016", "August Bank Holiday");

		lh.addStaticHoliday ("19-SEP-2016", "Respect for the Aged Day");

		lh.addStaticHoliday ("22-SEP-2016", "Autumnal Equinox Day");

		lh.addStaticHoliday ("10-OCT-2016", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2016", "Culture Day");

		lh.addStaticHoliday ("23-NOV-2016", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("23-DEC-2016", "Emperors Birthday");

		lh.addStaticHoliday ("26-DEC-2016", "Boxing Day");

		lh.addStaticHoliday ("27-DEC-2016", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2017", "New Years Day Observed");

		lh.addStaticHoliday ("03-JAN-2017", "Bank Holiday");

		lh.addStaticHoliday ("09-JAN-2017", "Adults Day");

		lh.addStaticHoliday ("20-MAR-2017", "Vernal Equinox Day");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("17-APR-2017", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2017", "May Day");

		lh.addStaticHoliday ("03-MAY-2017", "Constitution Memorial Day");

		lh.addStaticHoliday ("04-MAY-2017", "National Holiday");

		lh.addStaticHoliday ("05-MAY-2017", "Childrens Day");

		lh.addStaticHoliday ("29-MAY-2017", "Spring Bank Holiday");

		lh.addStaticHoliday ("17-JUL-2017", "Ocean Day");

		lh.addStaticHoliday ("28-AUG-2017", "August Bank Holiday");

		lh.addStaticHoliday ("18-SEP-2017", "Respect for the Aged Day");

		lh.addStaticHoliday ("09-OCT-2017", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2017", "Culture Day");

		lh.addStaticHoliday ("23-NOV-2017", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2017", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2018", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2018", "Bank Holiday");

		lh.addStaticHoliday ("08-JAN-2018", "Adults Day");

		lh.addStaticHoliday ("12-FEB-2018", "National Foundation Day Observ");

		lh.addStaticHoliday ("21-MAR-2018", "Vernal Equinox Day");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("02-APR-2018", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2018", "Greenery Day Observed");

		lh.addStaticHoliday ("03-MAY-2018", "Constitution Memorial Day");

		lh.addStaticHoliday ("04-MAY-2018", "National Holiday");

		lh.addStaticHoliday ("07-MAY-2018", "May Day");

		lh.addStaticHoliday ("28-MAY-2018", "Spring Bank Holiday");

		lh.addStaticHoliday ("16-JUL-2018", "Ocean Day");

		lh.addStaticHoliday ("27-AUG-2018", "August Bank Holiday");

		lh.addStaticHoliday ("17-SEP-2018", "Respect for the Aged Day");

		lh.addStaticHoliday ("24-SEP-2018", "Autumnal Equinox Day Observed");

		lh.addStaticHoliday ("08-OCT-2018", "Sports Day");

		lh.addStaticHoliday ("23-NOV-2018", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("24-DEC-2018", "Emperors Birthday Observed");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2018", "Boxing Day");

		lh.addStaticHoliday ("31-DEC-2018", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2019", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2019", "Bank Holiday");

		lh.addStaticHoliday ("14-JAN-2019", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2019", "National Foundation Day");

		lh.addStaticHoliday ("21-MAR-2019", "Vernal Equinox Day");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("22-APR-2019", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2019", "Greenery Day");

		lh.addStaticHoliday ("03-MAY-2019", "Constitution Memorial Day");

		lh.addStaticHoliday ("06-MAY-2019", "May Day");

		lh.addStaticHoliday ("27-MAY-2019", "Spring Bank Holiday");

		lh.addStaticHoliday ("15-JUL-2019", "Ocean Day");

		lh.addStaticHoliday ("26-AUG-2019", "August Bank Holiday");

		lh.addStaticHoliday ("16-SEP-2019", "Respect for the Aged Day Obser");

		lh.addStaticHoliday ("23-SEP-2019", "Autumnal Equinox Day");

		lh.addStaticHoliday ("14-OCT-2019", "Sports Day");

		lh.addStaticHoliday ("04-NOV-2019", "Culture Day Observed");

		lh.addStaticHoliday ("23-DEC-2019", "Emperors Birthday");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2019", "Boxing Day");

		lh.addStaticHoliday ("31-DEC-2019", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2020", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2020", "Bank Holiday");

		lh.addStaticHoliday ("13-JAN-2020", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2020", "National Foundation Day");

		lh.addStaticHoliday ("20-MAR-2020", "Vernal Equinox Day");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("13-APR-2020", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2020", "Greenery Day");

		lh.addStaticHoliday ("04-MAY-2020", "May Day");

		lh.addStaticHoliday ("05-MAY-2020", "Childrens Day");

		lh.addStaticHoliday ("25-MAY-2020", "Spring Bank Holiday");

		lh.addStaticHoliday ("20-JUL-2020", "Ocean Day");

		lh.addStaticHoliday ("31-AUG-2020", "August Bank Holiday");

		lh.addStaticHoliday ("21-SEP-2020", "Respect for the Aged Day");

		lh.addStaticHoliday ("22-SEP-2020", "Autumnal Equinox Day");

		lh.addStaticHoliday ("12-OCT-2020", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2020", "Culture Day");

		lh.addStaticHoliday ("23-NOV-2020", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("23-DEC-2020", "Emperors Birthday");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2020", "Boxing Day Observed");

		lh.addStaticHoliday ("31-DEC-2020", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("11-JAN-2021", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2021", "National Foundation Day");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("05-APR-2021", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2021", "Greenery Day");

		lh.addStaticHoliday ("03-MAY-2021", "May Day");

		lh.addStaticHoliday ("04-MAY-2021", "National Holiday");

		lh.addStaticHoliday ("05-MAY-2021", "Childrens Day");

		lh.addStaticHoliday ("31-MAY-2021", "Spring Bank Holiday");

		lh.addStaticHoliday ("19-JUL-2021", "Ocean Day");

		lh.addStaticHoliday ("30-AUG-2021", "August Bank Holiday");

		lh.addStaticHoliday ("20-SEP-2021", "Respect for the Aged Day");

		lh.addStaticHoliday ("23-SEP-2021", "Autumnal Equinox Day");

		lh.addStaticHoliday ("11-OCT-2021", "Sports Day Observed");

		lh.addStaticHoliday ("03-NOV-2021", "Culture Day");

		lh.addStaticHoliday ("23-NOV-2021", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("23-DEC-2021", "Emperors Birthday");

		lh.addStaticHoliday ("27-DEC-2021", "Boxing Day Observed");

		lh.addStaticHoliday ("28-DEC-2021", "Christmas Day Observed");

		lh.addStaticHoliday ("31-DEC-2021", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2022", "New Years Day Observed");

		lh.addStaticHoliday ("10-JAN-2022", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2022", "National Foundation Day");

		lh.addStaticHoliday ("21-MAR-2022", "Vernal Equinox Day");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("18-APR-2022", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2022", "Greenery Day");

		lh.addStaticHoliday ("02-MAY-2022", "May Day");

		lh.addStaticHoliday ("03-MAY-2022", "Constitution Memorial Day");

		lh.addStaticHoliday ("04-MAY-2022", "National Holiday");

		lh.addStaticHoliday ("05-MAY-2022", "Childrens Day");

		lh.addStaticHoliday ("30-MAY-2022", "Spring Bank Holiday");

		lh.addStaticHoliday ("18-JUL-2022", "Ocean Day");

		lh.addStaticHoliday ("29-AUG-2022", "August Bank Holiday");

		lh.addStaticHoliday ("19-SEP-2022", "Respect for the Aged Day");

		lh.addStaticHoliday ("23-SEP-2022", "Autumnal Equinox Day");

		lh.addStaticHoliday ("10-OCT-2022", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2022", "Culture Day");

		lh.addStaticHoliday ("23-NOV-2022", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("23-DEC-2022", "Emperors Birthday");

		lh.addStaticHoliday ("26-DEC-2022", "Boxing Day");

		lh.addStaticHoliday ("27-DEC-2022", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2023", "New Years Day Observed");

		lh.addStaticHoliday ("03-JAN-2023", "Bank Holiday");

		lh.addStaticHoliday ("09-JAN-2023", "Adults Day");

		lh.addStaticHoliday ("21-MAR-2023", "Vernal Equinox Day");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("10-APR-2023", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2023", "May Day");

		lh.addStaticHoliday ("03-MAY-2023", "Constitution Memorial Day");

		lh.addStaticHoliday ("04-MAY-2023", "National Holiday");

		lh.addStaticHoliday ("05-MAY-2023", "Childrens Day");

		lh.addStaticHoliday ("29-MAY-2023", "Spring Bank Holiday");

		lh.addStaticHoliday ("17-JUL-2023", "Ocean Day");

		lh.addStaticHoliday ("28-AUG-2023", "August Bank Holiday");

		lh.addStaticHoliday ("18-SEP-2023", "Respect for the Aged Day");

		lh.addStaticHoliday ("09-OCT-2023", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2023", "Culture Day");

		lh.addStaticHoliday ("23-NOV-2023", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2023", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2024", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2024", "Bank Holiday");

		lh.addStaticHoliday ("08-JAN-2024", "Adults Day");

		lh.addStaticHoliday ("12-FEB-2024", "National Foundation Day Observ");

		lh.addStaticHoliday ("20-MAR-2024", "Vernal Equinox Day");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("01-APR-2024", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2024", "Greenery Day");

		lh.addStaticHoliday ("03-MAY-2024", "Constitution Memorial Day");

		lh.addStaticHoliday ("06-MAY-2024", "May Day");

		lh.addStaticHoliday ("27-MAY-2024", "Spring Bank Holiday");

		lh.addStaticHoliday ("15-JUL-2024", "Ocean Day");

		lh.addStaticHoliday ("26-AUG-2024", "August Bank Holiday");

		lh.addStaticHoliday ("16-SEP-2024", "Respect for the Aged Day Obser");

		lh.addStaticHoliday ("23-SEP-2024", "Autumnal Equinox Day Observed");

		lh.addStaticHoliday ("14-OCT-2024", "Sports Day");

		lh.addStaticHoliday ("04-NOV-2024", "Culture Day Observed");

		lh.addStaticHoliday ("23-DEC-2024", "Emperors Birthday");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2024", "Boxing Day");

		lh.addStaticHoliday ("31-DEC-2024", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2025", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2025", "Bank Holiday");

		lh.addStaticHoliday ("13-JAN-2025", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2025", "National Foundation Day");

		lh.addStaticHoliday ("20-MAR-2025", "Vernal Equinox Day");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("21-APR-2025", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2025", "Greenery Day");

		lh.addStaticHoliday ("05-MAY-2025", "May Day");

		lh.addStaticHoliday ("26-MAY-2025", "Spring Bank Holiday");

		lh.addStaticHoliday ("21-JUL-2025", "Ocean Day Observed");

		lh.addStaticHoliday ("25-AUG-2025", "August Bank Holiday");

		lh.addStaticHoliday ("15-SEP-2025", "Respect for the Aged Day");

		lh.addStaticHoliday ("23-SEP-2025", "Autumnal Equinox Day");

		lh.addStaticHoliday ("13-OCT-2025", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2025", "Culture Day");

		lh.addStaticHoliday ("24-NOV-2025", "Labour Thanksgiving Day Observ");

		lh.addStaticHoliday ("23-DEC-2025", "Emperors Birthday");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2025", "Boxing Day");

		lh.addStaticHoliday ("31-DEC-2025", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2026", "Bank Holiday");

		lh.addStaticHoliday ("12-JAN-2026", "Adults Day");

		lh.addStaticHoliday ("11-FEB-2026", "National Foundation Day");

		lh.addStaticHoliday ("20-MAR-2026", "Vernal Equinox Day");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("06-APR-2026", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2026", "Greenery Day");

		lh.addStaticHoliday ("04-MAY-2026", "May Day");

		lh.addStaticHoliday ("05-MAY-2026", "Childrens Day");

		lh.addStaticHoliday ("25-MAY-2026", "Spring Bank Holiday");

		lh.addStaticHoliday ("20-JUL-2026", "Ocean Day");

		lh.addStaticHoliday ("31-AUG-2026", "August Bank Holiday");

		lh.addStaticHoliday ("21-SEP-2026", "Respect for the Aged Day");

		lh.addStaticHoliday ("23-SEP-2026", "Autumnal Equinox Day");

		lh.addStaticHoliday ("12-OCT-2026", "Sports Day");

		lh.addStaticHoliday ("03-NOV-2026", "Culture Day");

		lh.addStaticHoliday ("23-NOV-2026", "Labour Thanksgiving Day");

		lh.addStaticHoliday ("23-DEC-2026", "Emperors Birthday");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2026", "Boxing Day Observed");

		lh.addStaticHoliday ("31-DEC-2026", "Bank Holiday");

		lh.addStaticHoliday ("null", "");

		lh.addStandardWeekend();

		return lh;
	}
}
