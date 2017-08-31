
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

public class IDRHoliday implements org.drip.analytics.holset.LocationHoliday {
	public IDRHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "IDR";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("19-JAN-1999", "Idul Fitri");

		lh.addStaticHoliday ("20-JAN-1999", "Idul Fitri");

		lh.addStaticHoliday ("18-MAR-1999", "Saka New Year");

		lh.addStaticHoliday ("31-MAR-1999", "Bank Holiday");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("17-APR-1999", "First Day of Muharram");

		lh.addStaticHoliday ("13-MAY-1999", "Ascension Day");

		lh.addStaticHoliday ("07-JUN-1999", "Election Day");

		lh.addStaticHoliday ("26-JUN-1999", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("17-AUG-1999", "Independence Day");

		lh.addStaticHoliday ("06-NOV-1999", "Ascension of Muhammad");

		lh.addStaticHoliday ("25-DEC-1999", "Christmas Day");

		lh.addStaticHoliday ("31-DEC-1999", "Bank Holiday");

		lh.addStaticHoliday ("16-MAR-2000", "Idul Adha");

		lh.addStaticHoliday ("31-MAR-2000", "Bank Holiday");

		lh.addStaticHoliday ("04-APR-2000", "Saka New Year");

		lh.addStaticHoliday ("06-APR-2000", "First Day of Muharram");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("18-MAY-2000", "Waisak Day");

		lh.addStaticHoliday ("01-JUN-2000", "Ascension Day");

		lh.addStaticHoliday ("15-JUN-2000", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("17-AUG-2000", "Independence Day");

		lh.addStaticHoliday ("25-OCT-2000", "Ascension of Muhammad");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2000", "Bank Holiday");

		lh.addStaticHoliday ("27-DEC-2000", "Idul Fitri");

		lh.addStaticHoliday ("28-DEC-2000", "Idul Fitri");

		lh.addStaticHoliday ("29-DEC-2000", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("05-MAR-2001", "Idul Adha");

		lh.addStaticHoliday ("26-MAR-2001", "First Day of Muharram");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("07-MAY-2001", "Waisak Day");

		lh.addStaticHoliday ("24-MAY-2001", "Ascension Day");

		lh.addStaticHoliday ("04-JUN-2001", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("17-AUG-2001", "Independence Day");

		lh.addStaticHoliday ("15-OCT-2001", "Ascension of Muhammad");

		lh.addStaticHoliday ("17-DEC-2001", "Idul Fitri");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("22-FEB-2002", "IDUL ADHA");

		lh.addStaticHoliday ("15-MAR-2002", "Saka New Year");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("09-MAY-2002", "Ascension Day");

		lh.addStaticHoliday ("04-OCT-2002", "Ascension of Muhammad");

		lh.addStaticHoliday ("06-DEC-2002", "Idul Fitri");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("31-DEC-2002", "BANK HOLIDAY");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("12-FEB-2003", "Idul Adha");

		lh.addStaticHoliday ("03-MAR-2003", "First Day of Muharram");

		lh.addStaticHoliday ("02-APR-2003", "Saka New Year");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("15-MAY-2003", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("16-MAY-2003", "Waisak Day");

		lh.addStaticHoliday ("30-MAY-2003", "Ascension Day");

		lh.addStaticHoliday ("18-AUG-2003", "Independence Day");

		lh.addStaticHoliday ("22-SEP-2003", "Ascension of Muhammad");

		lh.addStaticHoliday ("24-NOV-2003", "Idul Fitri");

		lh.addStaticHoliday ("25-NOV-2003", "Idul Fitri");

		lh.addStaticHoliday ("26-NOV-2003", "Idul Fitri");

		lh.addStaticHoliday ("27-NOV-2003", "Idul Fitri");

		lh.addStaticHoliday ("28-NOV-2003", "Idul Fitri");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2003", "Christmas Day Observed");

		lh.addStaticHoliday ("31-DEC-2003", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("22-JAN-2004", "Chinese New Year");

		lh.addStaticHoliday ("02-FEB-2004", "Idul Adha");

		lh.addStaticHoliday ("23-FEB-2004", "New Years Day");

		lh.addStaticHoliday ("22-MAR-2004", "Saka New Year");

		lh.addStaticHoliday ("05-APR-2004", "Election Day");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("03-MAY-2004", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("20-MAY-2004", "Ascension Day");

		lh.addStaticHoliday ("03-JUN-2004", "Vesak Day");

		lh.addStaticHoliday ("05-JUL-2004", "Election Day");

		lh.addStaticHoliday ("17-AUG-2004", "Independence Day");

		lh.addStaticHoliday ("13-SEP-2004", "Ascension of Muhammad");

		lh.addStaticHoliday ("20-SEP-2004", "Election Day");

		lh.addStaticHoliday ("15-NOV-2004", "Idul Fitri");

		lh.addStaticHoliday ("16-NOV-2004", "Idul Fitri");

		lh.addStaticHoliday ("17-NOV-2004", "Idul Fitri");

		lh.addStaticHoliday ("18-NOV-2004", "Idul Fitri");

		lh.addStaticHoliday ("19-NOV-2004", "Idul Fitri");

		lh.addStaticHoliday ("31-DEC-2004", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2005", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2005", "Idul Adha");

		lh.addStaticHoliday ("09-FEB-2005", "Chinese New Year");

		lh.addStaticHoliday ("10-FEB-2005", "New Years Day");

		lh.addStaticHoliday ("11-MAR-2005", "Saka New Year");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("22-APR-2005", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("05-MAY-2005", "Ascension Day");

		lh.addStaticHoliday ("24-MAY-2005", "Waisak Day");

		lh.addStaticHoliday ("17-AUG-2005", "Independence Day");

		lh.addStaticHoliday ("02-SEP-2005", "Ascension of Muhammad");

		lh.addStaticHoliday ("02-NOV-2005", "Idul Fitri");

		lh.addStaticHoliday ("03-NOV-2005", "Idul Fitri");

		lh.addStaticHoliday ("04-NOV-2005", "Idul Fitri");

		lh.addStaticHoliday ("07-NOV-2005", "Idul Fitri");

		lh.addStaticHoliday ("08-NOV-2005", "Idul Fitri");

		lh.addStaticHoliday ("25-DEC-2005", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2006", "New Years Day");

		lh.addStaticHoliday ("10-JAN-2006", "Idul Adha");

		lh.addStaticHoliday ("29-JAN-2006", "Chinese New Year");

		lh.addStaticHoliday ("31-JAN-2006", "Muharram");

		lh.addStaticHoliday ("30-MAR-2006", "Saka New Year");

		lh.addStaticHoliday ("10-APR-2006", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("13-MAY-2006", "Waisak Day");

		lh.addStaticHoliday ("25-MAY-2006", "Ascension Day");

		lh.addStaticHoliday ("17-AUG-2006", "Independence Day");

		lh.addStaticHoliday ("21-AUG-2006", "Ascension of Muhammad");

		lh.addStaticHoliday ("23-OCT-2006", "Idul Fitri");

		lh.addStaticHoliday ("24-OCT-2006", "Idul Fitri");

		lh.addStaticHoliday ("25-OCT-2006", "Idul Fitri");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("29-DEC-2006", "BANK HOLIDAY");

		lh.addStaticHoliday ("31-DEC-2006", "Idul Adha");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("19-MAR-2007", "Saka New Year");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("17-MAY-2007", "Ascension Day");

		lh.addStaticHoliday ("01-JUN-2007", "Waisak Day");

		lh.addStaticHoliday ("17-AUG-2007", "Independence Day");

		lh.addStaticHoliday ("12-OCT-2007", "Idul Fitri");

		lh.addStaticHoliday ("15-OCT-2007", "BANK HOLIDAYS");

		lh.addStaticHoliday ("16-OCT-2007", "BANK HOLIDAYS");

		lh.addStaticHoliday ("20-DEC-2007", "Idul Adha");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("31-DEC-2007", "BANK HOLIDAY");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("10-JAN-2008", "First Day of Muharram");

		lh.addStaticHoliday ("07-FEB-2008", "Chinese New Year");

		lh.addStaticHoliday ("07-MAR-2008", "Saka New Year");

		lh.addStaticHoliday ("20-MAR-2008", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2008", "Ascension Day Observed");

		lh.addStaticHoliday ("20-MAY-2008", "Waisak Day");

		lh.addStaticHoliday ("30-JUL-2008", "Ascension of Muhammad Holiday Period");

		lh.addStaticHoliday ("18-AUG-2008", "Independence Day Observed");

		lh.addStaticHoliday ("30-SEP-2008", "Idul Fitri");

		lh.addStaticHoliday ("01-OCT-2008", "Idul Fitri");

		lh.addStaticHoliday ("02-OCT-2008", "Idul Fitri");

		lh.addStaticHoliday ("03-OCT-2008", "Idul Fitri");

		lh.addStaticHoliday ("08-DEC-2008", "Idul Adha");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("29-DEC-2008", "First Day of Muharram");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2009", "Bank Holiday");

		lh.addStaticHoliday ("26-JAN-2009", "Chinese New Year");

		lh.addStaticHoliday ("09-MAR-2009", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("26-MAR-2009", "Saka New Year");

		lh.addStaticHoliday ("09-APR-2009", "Election Day");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("09-MAY-2009", "Vesak Day");

		lh.addStaticHoliday ("21-MAY-2009", "Ascension Day");

		lh.addStaticHoliday ("08-JUL-2009", "Election Day");

		lh.addStaticHoliday ("20-JUL-2009", "Ascension of Muhammad");

		lh.addStaticHoliday ("17-AUG-2009", "Independence Day");

		lh.addStaticHoliday ("21-SEP-2009", "Idul Fitri");

		lh.addStaticHoliday ("22-SEP-2009", "Idul Fitri");

		lh.addStaticHoliday ("23-SEP-2009", "Bank Holiday");

		lh.addStaticHoliday ("27-NOV-2009", "Idul Adha");

		lh.addStaticHoliday ("18-DEC-2009", "First Day of Muharram");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("26-FEB-2010", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("16-MAR-2010", "Saka New Year");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("13-MAY-2010", "Ascension Day");

		lh.addStaticHoliday ("28-MAY-2010", "Waisak Day");

		lh.addStaticHoliday ("17-AUG-2010", "Independence Day");

		lh.addStaticHoliday ("09-SEP-2010", "Idul Fitri");

		lh.addStaticHoliday ("10-SEP-2010", "Idul Fitri");

		lh.addStaticHoliday ("13-SEP-2010", "Idul Fitri");

		lh.addStaticHoliday ("17-NOV-2010", "Idul Adha");

		lh.addStaticHoliday ("07-DEC-2010", "First Day of Muharram");

		lh.addStaticHoliday ("24-DEC-2010", "Christmas Day");

		lh.addStaticHoliday ("03-FEB-2011", "Chinese New Year");

		lh.addStaticHoliday ("15-FEB-2011", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("17-MAY-2011", "Waisak Day");

		lh.addStaticHoliday ("02-JUN-2011", "Ascension Day");

		lh.addStaticHoliday ("29-JUN-2011", "Ascension of Muhammad");

		lh.addStaticHoliday ("17-AUG-2011", "Independence Day");

		lh.addStaticHoliday ("29-AUG-2011", "Idul Fitri");

		lh.addStaticHoliday ("30-AUG-2011", "Idul Fitri");

		lh.addStaticHoliday ("31-AUG-2011", "Idul Fitri");

		lh.addStaticHoliday ("01-SEP-2011", "Idul Fitri");

		lh.addStaticHoliday ("02-SEP-2011", "Idul Fitri");

		lh.addStaticHoliday ("26-DEC-2011", "Christmas Day");

		lh.addStaticHoliday ("23-JAN-2012", "Chinese New Year");

		lh.addStaticHoliday ("23-MAR-2012", "Saka New Year");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("17-MAY-2012", "Ascension Day");

		lh.addStaticHoliday ("17-AUG-2012", "Independence Day");

		lh.addStaticHoliday ("20-AUG-2012", "Idul Fitri");

		lh.addStaticHoliday ("21-AUG-2012", "Idul Fitri");

		lh.addStaticHoliday ("22-AUG-2012", "Idul Fitri");

		lh.addStaticHoliday ("26-OCT-2012", "Idul Adha");

		lh.addStaticHoliday ("15-NOV-2012", "First Day of Muharram");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("24-JAN-2013", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("12-MAR-2013", "Saka New Year");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("09-MAY-2013", "Ascension Day");

		lh.addStaticHoliday ("06-JUN-2013", "Ascension of Muhammad");

		lh.addStaticHoliday ("07-AUG-2013", "Idul Fitri");

		lh.addStaticHoliday ("08-AUG-2013", "Idul Fitri");

		lh.addStaticHoliday ("09-AUG-2013", "Idul Fitri");

		lh.addStaticHoliday ("12-AUG-2013", "Idul Fitri");

		lh.addStaticHoliday ("13-AUG-2013", "Idul Fitri");

		lh.addStaticHoliday ("15-OCT-2013", "Idul Adha");

		lh.addStaticHoliday ("05-NOV-2013", "First Day of Muharram");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("14-JAN-2014", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("31-JAN-2014", "Chinese New Year");

		lh.addStaticHoliday ("31-MAR-2014", "Saka New Year");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("15-MAY-2014", "Waisak Day");

		lh.addStaticHoliday ("27-MAY-2014", "Ascension of Muhammad");

		lh.addStaticHoliday ("29-MAY-2014", "Ascension Day");

		lh.addStaticHoliday ("25-JUL-2014", "Idul Fitri");

		lh.addStaticHoliday ("28-JUL-2014", "Idul Fitri");

		lh.addStaticHoliday ("29-JUL-2014", "Idul Fitri");

		lh.addStaticHoliday ("30-JUL-2014", "Idul Fitri");

		lh.addStaticHoliday ("31-JUL-2014", "Idul Fitri");

		lh.addStaticHoliday ("01-AUG-2014", "Idul Fitri");

		lh.addStaticHoliday ("18-AUG-2014", "Independence Day Observed");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("19-FEB-2015", "Chinese New Year");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("14-MAY-2015", "Ascension Day");

		lh.addStaticHoliday ("02-JUN-2015", "Waisak Day");

		lh.addStaticHoliday ("16-JUL-2015", "Idul Fitri");

		lh.addStaticHoliday ("17-JUL-2015", "Idul Fitri");

		lh.addStaticHoliday ("17-AUG-2015", "Independence Day");

		lh.addStaticHoliday ("24-SEP-2015", "Idul Adha");

		lh.addStaticHoliday ("14-OCT-2015", "First Day of Muharram");

		lh.addStaticHoliday ("24-DEC-2015", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2016", "Chinese New Year");

		lh.addStaticHoliday ("09-MAR-2016", "Saka New Year");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("05-MAY-2016", "Ascension Day");

		lh.addStaticHoliday ("05-JUL-2016", "Idul Fitri");

		lh.addStaticHoliday ("06-JUL-2016", "Idul Fitri");

		lh.addStaticHoliday ("07-JUL-2016", "Idul Fitri");

		lh.addStaticHoliday ("08-JUL-2016", "Idul Fitri");

		lh.addStaticHoliday ("17-AUG-2016", "Independence Day");

		lh.addStaticHoliday ("12-SEP-2016", "Idul Adha");

		lh.addStaticHoliday ("12-DEC-2016", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("28-MAR-2017", "Saka New Year");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("24-APR-2017", "Ascension of Muhammad");

		lh.addStaticHoliday ("11-MAY-2017", "Waisak Day");

		lh.addStaticHoliday ("25-MAY-2017", "Ascension Day");

		lh.addStaticHoliday ("26-JUN-2017", "Idul Fitri");

		lh.addStaticHoliday ("27-JUN-2017", "Idul Fitri");

		lh.addStaticHoliday ("28-JUN-2017", "Idul Fitri");

		lh.addStaticHoliday ("17-AUG-2017", "Independence Day");

		lh.addStaticHoliday ("01-SEP-2017", "Idul Adha");

		lh.addStaticHoliday ("21-SEP-2017", "First Day of Muharram");

		lh.addStaticHoliday ("01-DEC-2017", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("16-FEB-2018", "Chinese New Year");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("10-MAY-2018", "Ascension Day");

		lh.addStaticHoliday ("29-MAY-2018", "Waisak Day");

		lh.addStaticHoliday ("14-JUN-2018", "Idul Fitri");

		lh.addStaticHoliday ("15-JUN-2018", "Idul Fitri");

		lh.addStaticHoliday ("17-AUG-2018", "Independence Day");

		lh.addStaticHoliday ("22-AUG-2018", "Idul Adha");

		lh.addStaticHoliday ("11-SEP-2018", "First Day of Muharram");

		lh.addStaticHoliday ("20-NOV-2018", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("05-FEB-2019", "Chinese New Year");

		lh.addStaticHoliday ("03-APR-2019", "Ascension of Muhammad");

		lh.addStaticHoliday ("05-APR-2019", "Saka New Year");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("30-MAY-2019", "Ascension Day");

		lh.addStaticHoliday ("04-JUN-2019", "Idul Fitri");

		lh.addStaticHoliday ("05-JUN-2019", "Idul Fitri");

		lh.addStaticHoliday ("06-JUN-2019", "Idul Fitri");

		lh.addStaticHoliday ("07-JUN-2019", "Idul Fitri");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("25-MAR-2020", "Saka New Year");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("07-MAY-2020", "Waisak Day");

		lh.addStaticHoliday ("21-MAY-2020", "Ascension Day");

		lh.addStaticHoliday ("25-MAY-2020", "Idul Fitri");

		lh.addStaticHoliday ("26-MAY-2020", "Idul Fitri");

		lh.addStaticHoliday ("27-MAY-2020", "Idul Fitri");

		lh.addStaticHoliday ("31-JUL-2020", "Idul Adha");

		lh.addStaticHoliday ("17-AUG-2020", "Independence Day");

		lh.addStaticHoliday ("20-AUG-2020", "First Day of Muharram");

		lh.addStaticHoliday ("29-OCT-2020", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("12-FEB-2021", "Chinese New Year");

		lh.addStaticHoliday ("11-MAR-2021", "Ascension of Muhammad");

		lh.addStaticHoliday ("15-MAR-2021", "Saka New Year");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("12-MAY-2021", "Idul Fitri");

		lh.addStaticHoliday ("13-MAY-2021", "Idul Fitri");

		lh.addStaticHoliday ("14-MAY-2021", "Idul Fitri");

		lh.addStaticHoliday ("17-MAY-2021", "Idul Fitri");

		lh.addStaticHoliday ("18-MAY-2021", "Idul Fitri");

		lh.addStaticHoliday ("26-MAY-2021", "Waisak Day");

		lh.addStaticHoliday ("20-JUL-2021", "Idul Adha");

		lh.addStaticHoliday ("10-AUG-2021", "First Day of Muharram");

		lh.addStaticHoliday ("17-AUG-2021", "Independence Day");

		lh.addStaticHoliday ("19-OCT-2021", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("01-FEB-2022", "Chinese New Year");

		lh.addStaticHoliday ("28-FEB-2022", "Ascension of Muhammad");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("29-APR-2022", "Idul Fitri");

		lh.addStaticHoliday ("02-MAY-2022", "Idul Fitri");

		lh.addStaticHoliday ("03-MAY-2022", "Idul Fitri");

		lh.addStaticHoliday ("04-MAY-2022", "Idul Fitri");

		lh.addStaticHoliday ("05-MAY-2022", "Idul Fitri");

		lh.addStaticHoliday ("06-MAY-2022", "Idul Fitri");

		lh.addStaticHoliday ("16-MAY-2022", "Waisak Day");

		lh.addStaticHoliday ("26-MAY-2022", "Ascension Day");

		lh.addStaticHoliday ("17-AUG-2022", "Independence Day");

		lh.addStaticHoliday ("22-MAR-2023", "Saka New Year");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("20-APR-2023", "Idul Fitri");

		lh.addStaticHoliday ("21-APR-2023", "Idul Fitri");

		lh.addStaticHoliday ("18-MAY-2023", "Ascension Day");

		lh.addStaticHoliday ("29-JUN-2023", "Idul Adha");

		lh.addStaticHoliday ("19-JUL-2023", "First Day of Muharram");

		lh.addStaticHoliday ("17-AUG-2023", "Independence Day");

		lh.addStaticHoliday ("27-SEP-2023", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2024", "Ascension of Muhammad");

		lh.addStaticHoliday ("11-MAR-2024", "Saka New Year");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("09-APR-2024", "Idul Fitri");

		lh.addStaticHoliday ("10-APR-2024", "Idul Fitri");

		lh.addStaticHoliday ("11-APR-2024", "Idul Fitri");

		lh.addStaticHoliday ("12-APR-2024", "Idul Fitri");

		lh.addStaticHoliday ("09-MAY-2024", "Ascension Day");

		lh.addStaticHoliday ("23-MAY-2024", "Waisak Day");

		lh.addStaticHoliday ("17-JUN-2024", "Idul Adha");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("27-JAN-2025", "Ascension of Muhammad");

		lh.addStaticHoliday ("29-JAN-2025", "Chinese New Year");

		lh.addStaticHoliday ("28-MAR-2025", "Idul Fitri");

		lh.addStaticHoliday ("31-MAR-2025", "Idul Fitri");

		lh.addStaticHoliday ("01-APR-2025", "Idul Fitri");

		lh.addStaticHoliday ("02-APR-2025", "Idul Fitri");

		lh.addStaticHoliday ("03-APR-2025", "Idul Fitri");

		lh.addStaticHoliday ("04-APR-2025", "Idul Fitri");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("12-MAY-2025", "Waisak Day");

		lh.addStaticHoliday ("29-MAY-2025", "Ascension Day");

		lh.addStaticHoliday ("06-JUN-2025", "Idul Adha");

		lh.addStaticHoliday ("27-JUN-2025", "First Day of Muharram");

		lh.addStaticHoliday ("18-AUG-2025", "Independence Day Observed");

		lh.addStaticHoliday ("05-SEP-2025", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("16-JAN-2026", "Ascension of Muhammad");

		lh.addStaticHoliday ("17-FEB-2026", "Chinese New Year");

		lh.addStaticHoliday ("19-MAR-2026", "Idul Fitri");

		lh.addStaticHoliday ("20-MAR-2026", "Idul Fitri");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("17-APR-2026", "Saka New Year");

		lh.addStaticHoliday ("14-MAY-2026", "Ascension Day");

		lh.addStaticHoliday ("27-MAY-2026", "Idul Adha");

		lh.addStaticHoliday ("16-JUN-2026", "First Day of Muharram");

		lh.addStaticHoliday ("17-AUG-2026", "Independence Day");

		lh.addStaticHoliday ("25-AUG-2026", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("05-JAN-2027", "Ascension of Muhammad");

		lh.addStaticHoliday ("09-MAR-2027", "Idul Fitri");

		lh.addStaticHoliday ("10-MAR-2027", "Idul Fitri");

		lh.addStaticHoliday ("11-MAR-2027", "Idul Fitri");

		lh.addStaticHoliday ("12-MAR-2027", "Idul Fitri");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("07-APR-2027", "Saka New Year");

		lh.addStaticHoliday ("06-MAY-2027", "Ascension Day");

		lh.addStaticHoliday ("17-MAY-2027", "Idul Adha");

		lh.addStaticHoliday ("20-MAY-2027", "Waisak Day");

		lh.addStaticHoliday ("17-AUG-2027", "Independence Day");

		lh.addStaticHoliday ("26-JAN-2028", "Chinese New Year");

		lh.addStaticHoliday ("28-FEB-2028", "Idul Fitri");

		lh.addStaticHoliday ("29-FEB-2028", "Idul Fitri");

		lh.addStaticHoliday ("01-MAR-2028", "Idul Fitri");

		lh.addStaticHoliday ("27-MAR-2028", "Saka New Year");

		lh.addStaticHoliday ("14-APR-2028", "Good Friday");

		lh.addStaticHoliday ("05-MAY-2028", "Idul Adha");

		lh.addStaticHoliday ("09-MAY-2028", "Waisak Day");

		lh.addStaticHoliday ("25-MAY-2028", "Ascension Day");

		lh.addStaticHoliday ("26-MAY-2028", "First Day of Muharram");

		lh.addStaticHoliday ("03-AUG-2028", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("17-AUG-2028", "Independence Day");

		lh.addStaticHoliday ("14-DEC-2028", "Ascension of Muhammad");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2029", "New Years Day");

		lh.addStaticHoliday ("13-FEB-2029", "Chinese New Year");

		lh.addStaticHoliday ("14-FEB-2029", "Idul Fitri");

		lh.addStaticHoliday ("15-FEB-2029", "Idul Fitri");

		lh.addStaticHoliday ("16-FEB-2029", "Idul Fitri");

		lh.addStaticHoliday ("19-FEB-2029", "Idul Fitri");

		lh.addStaticHoliday ("20-FEB-2029", "Idul Fitri");

		lh.addStaticHoliday ("30-MAR-2029", "Good Friday");

		lh.addStaticHoliday ("24-APR-2029", "Idul Adha");

		lh.addStaticHoliday ("10-MAY-2029", "Ascension Day");

		lh.addStaticHoliday ("15-MAY-2029", "First Day of Muharram");

		lh.addStaticHoliday ("28-MAY-2029", "Waisak Day");

		lh.addStaticHoliday ("24-JUL-2029", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("17-AUG-2029", "Independence Day");

		lh.addStaticHoliday ("03-DEC-2029", "Ascension of Muhammad");

		lh.addStaticHoliday ("25-DEC-2029", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2030", "New Years Day");

		lh.addStaticHoliday ("01-FEB-2030", "Idul Fitri");

		lh.addStaticHoliday ("04-FEB-2030", "Idul Fitri");

		lh.addStaticHoliday ("05-FEB-2030", "Idul Fitri");

		lh.addStaticHoliday ("06-FEB-2030", "Idul Fitri");

		lh.addStaticHoliday ("07-FEB-2030", "Idul Fitri");

		lh.addStaticHoliday ("08-FEB-2030", "Idul Fitri");

		lh.addStaticHoliday ("03-APR-2030", "Saka New Year");

		lh.addStaticHoliday ("19-APR-2030", "Good Friday");

		lh.addStaticHoliday ("17-MAY-2030", "Waisak Day");

		lh.addStaticHoliday ("30-MAY-2030", "Ascension Day");

		lh.addStaticHoliday ("25-DEC-2030", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2031", "New Years Day");

		lh.addStaticHoliday ("23-JAN-2031", "Chinese New Year");

		lh.addStaticHoliday ("24-JAN-2031", "Idul Fitri");

		lh.addStaticHoliday ("27-JAN-2031", "Idul Fitri");

		lh.addStaticHoliday ("28-JAN-2031", "Idul Fitri");

		lh.addStaticHoliday ("24-MAR-2031", "Saka New Year");

		lh.addStaticHoliday ("03-APR-2031", "Idul Adha");

		lh.addStaticHoliday ("11-APR-2031", "Good Friday");

		lh.addStaticHoliday ("23-APR-2031", "First Day of Muharram");

		lh.addStaticHoliday ("07-MAY-2031", "Waisak Day");

		lh.addStaticHoliday ("22-MAY-2031", "Ascension Day");

		lh.addStaticHoliday ("02-JUL-2031", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("18-AUG-2031", "Independence Day Observed");

		lh.addStaticHoliday ("13-NOV-2031", "Ascension of Muhammad");

		lh.addStaticHoliday ("25-DEC-2031", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2032", "New Years Day");

		lh.addStaticHoliday ("13-JAN-2032", "Idul Fitri");

		lh.addStaticHoliday ("14-JAN-2032", "Idul Fitri");

		lh.addStaticHoliday ("15-JAN-2032", "Idul Fitri");

		lh.addStaticHoliday ("16-JAN-2032", "Idul Fitri");

		lh.addStaticHoliday ("11-FEB-2032", "Chinese New Year");

		lh.addStaticHoliday ("22-MAR-2032", "Idul Adha");

		lh.addStaticHoliday ("26-MAR-2032", "Good Friday");

		lh.addStaticHoliday ("12-APR-2032", "Saka New Year");

		lh.addStaticHoliday ("06-MAY-2032", "Ascension Day");

		lh.addStaticHoliday ("25-MAY-2032", "Waisak Day");

		lh.addStaticHoliday ("17-AUG-2032", "Independence Day");

		lh.addStaticHoliday ("01-NOV-2032", "Ascension of Muhammad");

		lh.addStaticHoliday ("31-DEC-2032", "Idul Fitri");

		lh.addStaticHoliday ("03-JAN-2033", "Idul Fitri");

		lh.addStaticHoliday ("04-JAN-2033", "Idul Fitri");

		lh.addStaticHoliday ("05-JAN-2033", "Idul Fitri");

		lh.addStaticHoliday ("06-JAN-2033", "Idul Fitri");

		lh.addStaticHoliday ("07-JAN-2033", "Idul Fitri");

		lh.addStaticHoliday ("31-JAN-2033", "Chinese New Year");

		lh.addStaticHoliday ("31-MAR-2033", "Saka New Year");

		lh.addStaticHoliday ("01-APR-2033", "First Day of Muharram");

		lh.addStaticHoliday ("15-APR-2033", "Good Friday");

		lh.addStaticHoliday ("26-MAY-2033", "Ascension Day");

		lh.addStaticHoliday ("10-JUN-2033", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("17-AUG-2033", "Independence Day");

		lh.addStaticHoliday ("21-OCT-2033", "Ascension of Muhammad");

		lh.addStaticHoliday ("22-DEC-2033", "Idul Fitri");

		lh.addStaticHoliday ("23-DEC-2033", "Idul Fitri");

		lh.addStaticHoliday ("01-MAR-2034", "Idul Adha");

		lh.addStaticHoliday ("22-MAR-2034", "First Day of Muharram");

		lh.addStaticHoliday ("07-APR-2034", "Good Friday");

		lh.addStaticHoliday ("19-APR-2034", "Saka New Year");

		lh.addStaticHoliday ("18-MAY-2034", "Ascension Day");

		lh.addStaticHoliday ("30-MAY-2034", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("02-JUN-2034", "Waisak Day");

		lh.addStaticHoliday ("17-AUG-2034", "Independence Day");

		lh.addStaticHoliday ("10-OCT-2034", "Ascension of Muhammad");

		lh.addStaticHoliday ("11-DEC-2034", "Idul Fitri");

		lh.addStaticHoliday ("12-DEC-2034", "Idul Fitri");

		lh.addStaticHoliday ("13-DEC-2034", "Idul Fitri");

		lh.addStaticHoliday ("14-DEC-2034", "Idul Fitri");

		lh.addStaticHoliday ("15-DEC-2034", "Idul Fitri");

		lh.addStaticHoliday ("25-DEC-2034", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2035", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2035", "Chinese New Year");

		lh.addStaticHoliday ("19-FEB-2035", "Idul Adha");

		lh.addStaticHoliday ("23-MAR-2035", "Good Friday");

		lh.addStaticHoliday ("09-APR-2035", "Saka New Year");

		lh.addStaticHoliday ("03-MAY-2035", "Ascension Day");

		lh.addStaticHoliday ("22-MAY-2035", "Waisak Day");

		lh.addStaticHoliday ("17-AUG-2035", "Independence Day");

		lh.addStaticHoliday ("30-NOV-2035", "Idul Fitri");

		lh.addStaticHoliday ("03-DEC-2035", "Idul Fitri");

		lh.addStaticHoliday ("04-DEC-2035", "Idul Fitri");

		lh.addStaticHoliday ("25-DEC-2035", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2036", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2036", "Chinese New Year");

		lh.addStaticHoliday ("08-FEB-2036", "Idul Adha");

		lh.addStaticHoliday ("28-FEB-2036", "First Day of Muharram");

		lh.addStaticHoliday ("28-MAR-2036", "Saka New Year");

		lh.addStaticHoliday ("11-APR-2036", "Good Friday");

		lh.addStaticHoliday ("09-MAY-2036", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("22-MAY-2036", "Ascension Day");

		lh.addStaticHoliday ("18-AUG-2036", "Independence Day Observed");

		lh.addStaticHoliday ("18-SEP-2036", "Ascension of Muhammad");

		lh.addStaticHoliday ("18-NOV-2036", "Idul Fitri");

		lh.addStaticHoliday ("19-NOV-2036", "Idul Fitri");

		lh.addStaticHoliday ("20-NOV-2036", "Idul Fitri");

		lh.addStaticHoliday ("21-NOV-2036", "Idul Fitri");

		lh.addStaticHoliday ("25-DEC-2036", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2037", "New Years Day");

		lh.addStaticHoliday ("27-JAN-2037", "Idul Adha");

		lh.addStaticHoliday ("16-FEB-2037", "First Day of Muharram");

		lh.addStaticHoliday ("17-MAR-2037", "Saka New Year");

		lh.addStaticHoliday ("03-APR-2037", "Good Friday");

		lh.addStaticHoliday ("28-APR-2037", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("14-MAY-2037", "Ascension Day");

		lh.addStaticHoliday ("29-MAY-2037", "Waisak Day");

		lh.addStaticHoliday ("17-AUG-2037", "Independence Day");

		lh.addStaticHoliday ("08-SEP-2037", "Ascension of Muhammad");

		lh.addStaticHoliday ("06-NOV-2037", "Idul Fitri");

		lh.addStaticHoliday ("09-NOV-2037", "Idul Fitri");

		lh.addStaticHoliday ("10-NOV-2037", "Idul Fitri");

		lh.addStaticHoliday ("11-NOV-2037", "Idul Fitri");

		lh.addStaticHoliday ("12-NOV-2037", "Idul Fitri");

		lh.addStaticHoliday ("13-NOV-2037", "Idul Fitri");

		lh.addStaticHoliday ("25-DEC-2037", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2038", "New Years Day");

		lh.addStaticHoliday ("04-FEB-2038", "Chinese New Year");

		lh.addStaticHoliday ("05-FEB-2038", "First Day of Muharram");

		lh.addStaticHoliday ("05-APR-2038", "Saka New Year");

		lh.addStaticHoliday ("23-APR-2038", "Good Friday");

		lh.addStaticHoliday ("19-MAY-2038", "Waisak Day");

		lh.addStaticHoliday ("03-JUN-2038", "Ascension Day");

		lh.addStaticHoliday ("17-AUG-2038", "Independence Day");

		lh.addStaticHoliday ("28-OCT-2038", "Idul Fitri");

		lh.addStaticHoliday ("29-OCT-2038", "Idul Fitri");

		lh.addStaticHoliday ("05-JAN-2039", "Idul Adha");

		lh.addStaticHoliday ("24-JAN-2039", "Chinese New Year");

		lh.addStaticHoliday ("26-JAN-2039", "First Day of Muharram");

		lh.addStaticHoliday ("25-MAR-2039", "Saka New Year");

		lh.addStaticHoliday ("06-APR-2039", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("08-APR-2039", "Good Friday");

		lh.addStaticHoliday ("19-MAY-2039", "Ascension Day");

		lh.addStaticHoliday ("17-AUG-2039", "Independence Day");

		lh.addStaticHoliday ("18-AUG-2039", "Ascension of Muhammad");

		lh.addStaticHoliday ("18-OCT-2039", "Idul Fitri");

		lh.addStaticHoliday ("19-OCT-2039", "Idul Fitri");

		lh.addStaticHoliday ("20-OCT-2039", "Idul Fitri");

		lh.addStaticHoliday ("21-OCT-2039", "Idul Fitri");

		lh.addStaticHoliday ("26-DEC-2039", "Idul Adha");

		lh.addStaticHoliday ("14-MAR-2040", "Saka New Year");

		lh.addStaticHoliday ("30-MAR-2040", "Good Friday");

		lh.addStaticHoliday ("10-MAY-2040", "Ascension Day");

		lh.addStaticHoliday ("06-AUG-2040", "Ascension of Muhammad");

		lh.addStaticHoliday ("17-AUG-2040", "Independence Day");

		lh.addStaticHoliday ("08-OCT-2040", "Idul Fitri");

		lh.addStaticHoliday ("09-OCT-2040", "Idul Fitri");

		lh.addStaticHoliday ("10-OCT-2040", "Idul Fitri");

		lh.addStaticHoliday ("25-DEC-2040", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2041", "New Years Day");

		lh.addStaticHoliday ("04-JAN-2041", "First Day of Muharram");

		lh.addStaticHoliday ("01-FEB-2041", "Chinese New Year");

		lh.addStaticHoliday ("15-MAR-2041", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("02-APR-2041", "Saka New Year");

		lh.addStaticHoliday ("19-APR-2041", "Good Friday");

		lh.addStaticHoliday ("16-MAY-2041", "Waisak Day");

		lh.addStaticHoliday ("30-MAY-2041", "Ascension Day");

		lh.addStaticHoliday ("26-JUL-2041", "Ascension of Muhammad");

		lh.addStaticHoliday ("26-SEP-2041", "Idul Fitri");

		lh.addStaticHoliday ("27-SEP-2041", "Idul Fitri");

		lh.addStaticHoliday ("04-DEC-2041", "Idul Adha");

		lh.addStaticHoliday ("25-DEC-2041", "First Day of Muharram");

		lh.addStaticHoliday ("01-JAN-2042", "New Years Day");

		lh.addStaticHoliday ("22-JAN-2042", "Chinese New Year");

		lh.addStaticHoliday ("04-MAR-2042", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("04-APR-2042", "Good Friday");

		lh.addStaticHoliday ("15-MAY-2042", "Ascension Day");

		lh.addStaticHoliday ("04-JUN-2042", "Waisak Day");

		lh.addStaticHoliday ("15-JUL-2042", "Ascension of Muhammad");

		lh.addStaticHoliday ("18-AUG-2042", "Independence Day Observed");

		lh.addStaticHoliday ("15-SEP-2042", "Idul Fitri");

		lh.addStaticHoliday ("16-SEP-2042", "Idul Fitri");

		lh.addStaticHoliday ("17-SEP-2042", "Idul Fitri");

		lh.addStaticHoliday ("18-SEP-2042", "Idul Fitri");

		lh.addStaticHoliday ("19-SEP-2042", "Idul Fitri");

		lh.addStaticHoliday ("25-DEC-2042", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2043", "New Years Day");

		lh.addStaticHoliday ("10-FEB-2043", "Chinese New Year");

		lh.addStaticHoliday ("27-MAR-2043", "Good Friday");

		lh.addStaticHoliday ("10-APR-2043", "Saka New Year");

		lh.addStaticHoliday ("07-MAY-2043", "Ascension Day");

		lh.addStaticHoliday ("17-AUG-2043", "Independence Day");

		lh.addStaticHoliday ("04-SEP-2043", "Idul Fitri");

		lh.addStaticHoliday ("07-SEP-2043", "Idul Fitri");

		lh.addStaticHoliday ("08-SEP-2043", "Idul Fitri");

		lh.addStaticHoliday ("12-NOV-2043", "Idul Adha");

		lh.addStaticHoliday ("03-DEC-2043", "First Day of Muharram");

		lh.addStaticHoliday ("25-DEC-2043", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2044", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2044", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("29-MAR-2044", "Saka New Year");

		lh.addStaticHoliday ("15-APR-2044", "Good Friday");

		lh.addStaticHoliday ("12-MAY-2044", "Waisak Day");

		lh.addStaticHoliday ("26-MAY-2044", "Ascension Day");

		lh.addStaticHoliday ("24-JUN-2044", "Ascension of Muhammad");

		lh.addStaticHoliday ("17-AUG-2044", "Independence Day");

		lh.addStaticHoliday ("23-AUG-2044", "Idul Fitri");

		lh.addStaticHoliday ("24-AUG-2044", "Idul Fitri");

		lh.addStaticHoliday ("25-AUG-2044", "Idul Fitri");

		lh.addStaticHoliday ("26-AUG-2044", "Idul Fitri");

		lh.addStaticHoliday ("31-OCT-2044", "Idul Adha");

		lh.addStaticHoliday ("21-NOV-2044", "First Day of Muharram");

		lh.addStaticHoliday ("30-JAN-2045", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("17-FEB-2045", "Chinese New Year");

		lh.addStaticHoliday ("20-MAR-2045", "Saka New Year");

		lh.addStaticHoliday ("07-APR-2045", "Good Friday");

		lh.addStaticHoliday ("18-MAY-2045", "Ascension Day");

		lh.addStaticHoliday ("31-MAY-2045", "Waisak Day");

		lh.addStaticHoliday ("13-JUN-2045", "Ascension of Muhammad");

		lh.addStaticHoliday ("11-AUG-2045", "Idul Fitri");

		lh.addStaticHoliday ("14-AUG-2045", "Idul Fitri");

		lh.addStaticHoliday ("15-AUG-2045", "Idul Fitri");

		lh.addStaticHoliday ("16-AUG-2045", "Idul Fitri");

		lh.addStaticHoliday ("17-AUG-2045", "Idul Fitri");

		lh.addStaticHoliday ("18-AUG-2045", "Idul Fitri");

		lh.addStaticHoliday ("10-NOV-2045", "First Day of Muharram");

		lh.addStaticHoliday ("25-DEC-2045", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2046", "New Years Day");

		lh.addStaticHoliday ("19-JAN-2046", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("06-FEB-2046", "Chinese New Year");

		lh.addStaticHoliday ("23-MAR-2046", "Good Friday");

		lh.addStaticHoliday ("03-MAY-2046", "Ascension Day");

		lh.addStaticHoliday ("03-AUG-2046", "Idul Fitri");

		lh.addStaticHoliday ("06-AUG-2046", "Idul Fitri");

		lh.addStaticHoliday ("07-AUG-2046", "Idul Fitri");

		lh.addStaticHoliday ("17-AUG-2046", "Independence Day");

		lh.addStaticHoliday ("10-OCT-2046", "Idul Adha");

		lh.addStaticHoliday ("31-OCT-2046", "First Day of Muharram");

		lh.addStaticHoliday ("25-DEC-2046", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2047", "New Years Day");

		lh.addStaticHoliday ("09-JAN-2047", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("27-MAR-2047", "Saka New Year");

		lh.addStaticHoliday ("12-APR-2047", "Good Friday");

		lh.addStaticHoliday ("10-MAY-2047", "Waisak Day");

		lh.addStaticHoliday ("23-MAY-2047", "Ascension Day");

		lh.addStaticHoliday ("23-JUL-2047", "Idul Fitri");

		lh.addStaticHoliday ("24-JUL-2047", "Idul Fitri");

		lh.addStaticHoliday ("25-JUL-2047", "Idul Fitri");

		lh.addStaticHoliday ("26-JUL-2047", "Idul Fitri");

		lh.addStaticHoliday ("30-SEP-2047", "Idul Adha");

		lh.addStaticHoliday ("25-DEC-2047", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2048", "New Years Day");

		lh.addStaticHoliday ("14-FEB-2048", "Chinese New Year");

		lh.addStaticHoliday ("16-MAR-2048", "Saka New Year");

		lh.addStaticHoliday ("03-APR-2048", "Good Friday");

		lh.addStaticHoliday ("11-MAY-2048", "Ascension of Muhammad");

		lh.addStaticHoliday ("14-MAY-2048", "Ascension Day");

		lh.addStaticHoliday ("28-MAY-2048", "Waisak Day");

		lh.addStaticHoliday ("13-JUL-2048", "Idul Fitri");

		lh.addStaticHoliday ("14-JUL-2048", "Idul Fitri");

		lh.addStaticHoliday ("15-JUL-2048", "Idul Fitri");

		lh.addStaticHoliday ("17-AUG-2048", "Independence Day");

		lh.addStaticHoliday ("09-OCT-2048", "First Day of Muharram");

		lh.addStaticHoliday ("18-DEC-2048", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("25-DEC-2048", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2049", "New Years Day");

		lh.addStaticHoliday ("02-FEB-2049", "Chinese New Year");

		lh.addStaticHoliday ("16-APR-2049", "Good Friday");

		lh.addStaticHoliday ("30-APR-2049", "Ascension of Muhammad");

		lh.addStaticHoliday ("17-MAY-2049", "Waisak Day");

		lh.addStaticHoliday ("27-MAY-2049", "Ascension Day");

		lh.addStaticHoliday ("30-JUN-2049", "Idul Fitri");

		lh.addStaticHoliday ("01-JUL-2049", "Idul Fitri");

		lh.addStaticHoliday ("02-JUL-2049", "Idul Fitri");

		lh.addStaticHoliday ("05-JUL-2049", "Idul Fitri");

		lh.addStaticHoliday ("06-JUL-2049", "Idul Fitri");

		lh.addStaticHoliday ("17-AUG-2049", "Independence Day");

		lh.addStaticHoliday ("08-SEP-2049", "Idul Adha");

		lh.addStaticHoliday ("28-SEP-2049", "First Day of Muharram");

		lh.addStaticHoliday ("07-DEC-2049", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("24-MAR-2050", "Saka New Year");

		lh.addStaticHoliday ("08-APR-2050", "Good Friday");

		lh.addStaticHoliday ("19-APR-2050", "Ascension of Muhammad");

		lh.addStaticHoliday ("19-MAY-2050", "Ascension Day");

		lh.addStaticHoliday ("20-JUN-2050", "Idul Fitri");

		lh.addStaticHoliday ("21-JUN-2050", "Idul Fitri");

		lh.addStaticHoliday ("22-JUN-2050", "Idul Fitri");

		lh.addStaticHoliday ("23-JUN-2050", "Idul Fitri");

		lh.addStaticHoliday ("24-JUN-2050", "Idul Fitri");

		lh.addStaticHoliday ("17-AUG-2050", "Independence Day");

		lh.addStaticHoliday ("31-MAR-2051", "Good Friday");

		lh.addStaticHoliday ("12-APR-2051", "Saka New Year");

		lh.addStaticHoliday ("11-MAY-2051", "Ascension Day");

		lh.addStaticHoliday ("26-MAY-2051", "Waisak Day");

		lh.addStaticHoliday ("09-JUN-2051", "Idul Fitri");

		lh.addStaticHoliday ("12-JUN-2051", "Idul Fitri");

		lh.addStaticHoliday ("13-JUN-2051", "Idul Fitri");

		lh.addStaticHoliday ("17-AUG-2051", "Idul Adha");

		lh.addStaticHoliday ("07-SEP-2051", "First Day of Muharram");

		lh.addStaticHoliday ("16-NOV-2051", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("25-DEC-2051", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2052", "New Years Day");

		lh.addStaticHoliday ("01-FEB-2052", "Chinese New Year");

		lh.addStaticHoliday ("29-MAR-2052", "Ascension of Muhammad");

		lh.addStaticHoliday ("01-APR-2052", "Saka New Year");

		lh.addStaticHoliday ("19-APR-2052", "Good Friday");

		lh.addStaticHoliday ("14-MAY-2052", "Waisak Day");

		lh.addStaticHoliday ("29-MAY-2052", "Idul Fitri");

		lh.addStaticHoliday ("30-MAY-2052", "Idul Fitri");

		lh.addStaticHoliday ("31-MAY-2052", "Idul Fitri");

		lh.addStaticHoliday ("03-JUN-2052", "Idul Fitri");

		lh.addStaticHoliday ("04-JUN-2052", "Idul Fitri");

		lh.addStaticHoliday ("05-AUG-2052", "Idul Adha");

		lh.addStaticHoliday ("26-AUG-2052", "First Day of Muharram");

		lh.addStaticHoliday ("04-NOV-2052", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("25-DEC-2052", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2053", "New Years Day");

		lh.addStaticHoliday ("19-FEB-2053", "Chinese New Year");

		lh.addStaticHoliday ("18-MAR-2053", "Ascension of Muhammad");

		lh.addStaticHoliday ("20-MAR-2053", "Saka New Year");

		lh.addStaticHoliday ("04-APR-2053", "Good Friday");

		lh.addStaticHoliday ("15-MAY-2053", "Ascension Day");

		lh.addStaticHoliday ("16-MAY-2053", "Idul Fitri");

		lh.addStaticHoliday ("19-MAY-2053", "Idul Fitri");

		lh.addStaticHoliday ("20-MAY-2053", "Idul Fitri");

		lh.addStaticHoliday ("21-MAY-2053", "Idul Fitri");

		lh.addStaticHoliday ("22-MAY-2053", "Idul Fitri");

		lh.addStaticHoliday ("23-MAY-2053", "Idul Fitri");

		lh.addStaticHoliday ("15-AUG-2053", "First Day of Muharram");

		lh.addStaticHoliday ("18-AUG-2053", "Independence Day Observed");

		lh.addStaticHoliday ("24-OCT-2053", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("25-DEC-2053", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2054", "New Years Day");

		lh.addStaticHoliday ("27-MAR-2054", "Good Friday");

		lh.addStaticHoliday ("08-APR-2054", "Saka New Year");

		lh.addStaticHoliday ("07-MAY-2054", "Ascension Day");

		lh.addStaticHoliday ("08-MAY-2054", "Idul Fitri");

		lh.addStaticHoliday ("11-MAY-2054", "Idul Fitri");

		lh.addStaticHoliday ("12-MAY-2054", "Idul Fitri");

		lh.addStaticHoliday ("21-MAY-2054", "Waisak Day");

		lh.addStaticHoliday ("16-JUL-2054", "Idul Adha");

		lh.addStaticHoliday ("05-AUG-2054", "First Day of Muharram");

		lh.addStaticHoliday ("17-AUG-2054", "Independence Day");

		lh.addStaticHoliday ("14-OCT-2054", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("25-DEC-2054", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2055", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2055", "Chinese New Year");

		lh.addStaticHoliday ("24-FEB-2055", "Ascension of Muhammad");

		lh.addStaticHoliday ("29-MAR-2055", "Saka New Year");

		lh.addStaticHoliday ("16-APR-2055", "Good Friday");

		lh.addStaticHoliday ("27-APR-2055", "Idul Fitri");

		lh.addStaticHoliday ("28-APR-2055", "Idul Fitri");

		lh.addStaticHoliday ("29-APR-2055", "Idul Fitri");

		lh.addStaticHoliday ("30-APR-2055", "Idul Fitri");

		lh.addStaticHoliday ("11-MAY-2055", "Waisak Day");

		lh.addStaticHoliday ("27-MAY-2055", "Ascension Day");

		lh.addStaticHoliday ("05-JUL-2055", "Idul Adha");

		lh.addStaticHoliday ("26-JUL-2055", "First Day of Muharram");

		lh.addStaticHoliday ("17-AUG-2055", "Independence Day");

		lh.addStaticHoliday ("15-FEB-2056", "Chinese New Year");

		lh.addStaticHoliday ("17-MAR-2056", "Saka New Year");

		lh.addStaticHoliday ("31-MAR-2056", "Good Friday");

		lh.addStaticHoliday ("17-APR-2056", "Idul Fitri");

		lh.addStaticHoliday ("18-APR-2056", "Idul Fitri");

		lh.addStaticHoliday ("19-APR-2056", "Idul Fitri");

		lh.addStaticHoliday ("11-MAY-2056", "Ascension Day");

		lh.addStaticHoliday ("29-MAY-2056", "Waisak Day");

		lh.addStaticHoliday ("14-JUL-2056", "First Day of Muharram");

		lh.addStaticHoliday ("17-AUG-2056", "Independence Day");

		lh.addStaticHoliday ("22-SEP-2056", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("25-DEC-2056", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2057", "New Years Day");

		lh.addStaticHoliday ("02-FEB-2057", "Ascension of Muhammad");

		lh.addStaticHoliday ("04-APR-2057", "Idul Fitri");

		lh.addStaticHoliday ("05-APR-2057", "Idul Fitri");

		lh.addStaticHoliday ("06-APR-2057", "Idul Fitri");

		lh.addStaticHoliday ("09-APR-2057", "Idul Fitri");

		lh.addStaticHoliday ("10-APR-2057", "Idul Fitri");

		lh.addStaticHoliday ("20-APR-2057", "Good Friday");

		lh.addStaticHoliday ("31-MAY-2057", "Ascension Day");

		lh.addStaticHoliday ("13-JUN-2057", "Idul Adha");

		lh.addStaticHoliday ("03-JUL-2057", "First Day of Muharram");

		lh.addStaticHoliday ("17-AUG-2057", "Independence Day");

		lh.addStaticHoliday ("11-SEP-2057", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("25-DEC-2057", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2058", "New Years Day");

		lh.addStaticHoliday ("22-JAN-2058", "Ascension of Muhammad");

		lh.addStaticHoliday ("24-JAN-2058", "Chinese New Year");

		lh.addStaticHoliday ("22-MAR-2058", "Idul Fitri");

		lh.addStaticHoliday ("25-MAR-2058", "Idul Fitri");

		lh.addStaticHoliday ("26-MAR-2058", "Idul Fitri");

		lh.addStaticHoliday ("27-MAR-2058", "Idul Fitri");

		lh.addStaticHoliday ("28-MAR-2058", "Idul Fitri");

		lh.addStaticHoliday ("29-MAR-2058", "Idul Fitri");

		lh.addStaticHoliday ("12-APR-2058", "Good Friday");

		lh.addStaticHoliday ("24-APR-2058", "Saka New Year");

		lh.addStaticHoliday ("08-MAY-2058", "Waisak Day");

		lh.addStaticHoliday ("23-MAY-2058", "Ascension Day");

		lh.addStaticHoliday ("25-DEC-2058", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2059", "New Years Day");

		lh.addStaticHoliday ("12-FEB-2059", "Chinese New Year");

		lh.addStaticHoliday ("14-MAR-2059", "Idul Fitri");

		lh.addStaticHoliday ("17-MAR-2059", "Idul Fitri");

		lh.addStaticHoliday ("18-MAR-2059", "Idul Fitri");

		lh.addStaticHoliday ("28-MAR-2059", "Good Friday");

		lh.addStaticHoliday ("14-APR-2059", "Saka New Year");

		lh.addStaticHoliday ("08-MAY-2059", "Ascension Day");

		lh.addStaticHoliday ("22-MAY-2059", "Idul Adha");

		lh.addStaticHoliday ("27-MAY-2059", "Waisak Day");

		lh.addStaticHoliday ("12-JUN-2059", "First Day of Muharram");

		lh.addStaticHoliday ("18-AUG-2059", "Independence Day Observed");

		lh.addStaticHoliday ("21-AUG-2059", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("25-DEC-2059", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2060", "New Years Day");

		lh.addStaticHoliday ("02-FEB-2060", "Chinese New Year");

		lh.addStaticHoliday ("03-MAR-2060", "Idul Fitri");

		lh.addStaticHoliday ("04-MAR-2060", "Idul Fitri");

		lh.addStaticHoliday ("05-MAR-2060", "Idul Fitri");

		lh.addStaticHoliday ("08-MAR-2060", "Idul Fitri");

		lh.addStaticHoliday ("09-MAR-2060", "Idul Fitri");

		lh.addStaticHoliday ("01-APR-2060", "Saka New Year");

		lh.addStaticHoliday ("16-APR-2060", "Good Friday");

		lh.addStaticHoliday ("11-MAY-2060", "Idul Adha");

		lh.addStaticHoliday ("27-MAY-2060", "Ascension Day");

		lh.addStaticHoliday ("31-MAY-2060", "First Day of Muharram");

		lh.addStaticHoliday ("09-AUG-2060", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("17-AUG-2060", "Independence Day");

		lh.addStaticHoliday ("20-DEC-2060", "Ascension of Muhammad");

		lh.addStaticHoliday ("21-JAN-2061", "Chinese New Year");

		lh.addStaticHoliday ("18-FEB-2061", "Idul Fitri");

		lh.addStaticHoliday ("21-FEB-2061", "Idul Fitri");

		lh.addStaticHoliday ("22-FEB-2061", "Idul Fitri");

		lh.addStaticHoliday ("23-FEB-2061", "Idul Fitri");

		lh.addStaticHoliday ("24-FEB-2061", "Idul Fitri");

		lh.addStaticHoliday ("25-FEB-2061", "Idul Fitri");

		lh.addStaticHoliday ("08-APR-2061", "Good Friday");

		lh.addStaticHoliday ("20-APR-2061", "Saka New Year");

		lh.addStaticHoliday ("04-MAY-2061", "Waisak Day");

		lh.addStaticHoliday ("19-MAY-2061", "Ascension Day");

		lh.addStaticHoliday ("29-JUL-2061", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("17-AUG-2061", "Independence Day");

		lh.addStaticHoliday ("09-FEB-2062", "Chinese New Year");

		lh.addStaticHoliday ("10-FEB-2062", "Idul Fitri");

		lh.addStaticHoliday ("13-FEB-2062", "Idul Fitri");

		lh.addStaticHoliday ("14-FEB-2062", "Idul Fitri");

		lh.addStaticHoliday ("24-MAR-2062", "Good Friday");

		lh.addStaticHoliday ("10-APR-2062", "Saka New Year");

		lh.addStaticHoliday ("20-APR-2062", "Idul Adha");

		lh.addStaticHoliday ("04-MAY-2062", "Ascension Day");

		lh.addStaticHoliday ("11-MAY-2062", "First Day of Muharram");

		lh.addStaticHoliday ("23-MAY-2062", "Waisak Day");

		lh.addStaticHoliday ("19-JUL-2062", "Prophet Muhammads Birthday");

		lh.addStaticHoliday ("17-AUG-2062", "Independence Day");

		lh.addStaticHoliday ("29-NOV-2062", "Ascension of Muhammad");

		lh.addStaticHoliday ("25-DEC-2062", "Christmas Day");

		lh.addStandardWeekend();

		return lh;
	}
}
