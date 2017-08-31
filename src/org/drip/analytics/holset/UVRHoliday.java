
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

public class UVRHoliday implements org.drip.analytics.holset.LocationHoliday {
	public UVRHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "UVR";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("12-JAN-1998", "Epiphany");

		lh.addStaticHoliday ("23-MAR-1998", "St. Josephs Day");

		lh.addStaticHoliday ("09-APR-1998", "Holy Thursday");

		lh.addStaticHoliday ("10-APR-1998", "Good Friday");

		lh.addStaticHoliday ("01-MAY-1998", "Labor Day");

		lh.addStaticHoliday ("25-MAY-1998", "Ascension Day Observed");

		lh.addStaticHoliday ("15-JUN-1998", "Corpus Christi Observed");

		lh.addStaticHoliday ("22-JUN-1998", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("29-JUN-1998", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-1998", "Independence Day");

		lh.addStaticHoliday ("07-AUG-1998", "National Holiday");

		lh.addStaticHoliday ("17-AUG-1998", "Assumption Day");

		lh.addStaticHoliday ("12-OCT-1998", "Columbus Day");

		lh.addStaticHoliday ("02-NOV-1998", "All Saints Day");

		lh.addStaticHoliday ("16-NOV-1998", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-1998", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-1998", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("11-JAN-1999", "Epiphany");

		lh.addStaticHoliday ("22-MAR-1999", "St. Josephs Day");

		lh.addStaticHoliday ("01-APR-1999", "Holy Thursday");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("17-MAY-1999", "Ascension Day Observed");

		lh.addStaticHoliday ("07-JUN-1999", "Corpus Christi Observed");

		lh.addStaticHoliday ("14-JUN-1999", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("05-JUL-1999", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-1999", "Independence Day");

		lh.addStaticHoliday ("16-AUG-1999", "Assumption Day");

		lh.addStaticHoliday ("18-OCT-1999", "Columbus Day");

		lh.addStaticHoliday ("01-NOV-1999", "All Saints Day");

		lh.addStaticHoliday ("15-NOV-1999", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-1999", "Immaculate Conception");

		lh.addStaticHoliday ("10-JAN-2000", "Epiphany");

		lh.addStaticHoliday ("20-MAR-2000", "St. Josephs Day");

		lh.addStaticHoliday ("20-APR-2000", "Holy Thursday");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2000", "Labor Day");

		lh.addStaticHoliday ("05-JUN-2000", "Ascension Day Observed");

		lh.addStaticHoliday ("26-JUN-2000", "Corpus Christi Observed");

		lh.addStaticHoliday ("03-JUL-2000", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2000", "Independence Day");

		lh.addStaticHoliday ("07-AUG-2000", "National Holiday");

		lh.addStaticHoliday ("21-AUG-2000", "Assumption Day");

		lh.addStaticHoliday ("16-OCT-2000", "Columbus Day");

		lh.addStaticHoliday ("06-NOV-2000", "All Saints Day");

		lh.addStaticHoliday ("13-NOV-2000", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2000", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("08-JAN-2001", "Epiphany");

		lh.addStaticHoliday ("19-MAR-2001", "St. Josephs Day");

		lh.addStaticHoliday ("12-APR-2001", "Holy Thursday");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2001", "Labor Day");

		lh.addStaticHoliday ("28-MAY-2001", "Ascension Day Observed");

		lh.addStaticHoliday ("18-JUN-2001", "Corpus Christi Observed");

		lh.addStaticHoliday ("25-JUN-2001", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("02-JUL-2001", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2001", "Independence Day");

		lh.addStaticHoliday ("07-AUG-2001", "National Holiday");

		lh.addStaticHoliday ("20-AUG-2001", "Assumption Day");

		lh.addStaticHoliday ("15-OCT-2001", "Columbus Day");

		lh.addStaticHoliday ("05-NOV-2001", "All Saints Day");

		lh.addStaticHoliday ("12-NOV-2001", "Independence of Cartagena");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2002", "Epiphany");

		lh.addStaticHoliday ("25-MAR-2002", "St. Josephs Day");

		lh.addStaticHoliday ("28-MAR-2002", "Holy Thursday");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2002", "Labor Day");

		lh.addStaticHoliday ("13-MAY-2002", "Ascension Day Observed");

		lh.addStaticHoliday ("03-JUN-2002", "Corpus Christi Observed");

		lh.addStaticHoliday ("10-JUN-2002", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("01-JUL-2002", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("07-AUG-2002", "National Holiday");

		lh.addStaticHoliday ("19-AUG-2002", "Assumption Day");

		lh.addStaticHoliday ("14-OCT-2002", "Columbus Day");

		lh.addStaticHoliday ("04-NOV-2002", "All Saints Day");

		lh.addStaticHoliday ("11-NOV-2002", "Independence of Cartagena");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2003", "Epiphany");

		lh.addStaticHoliday ("24-MAR-2003", "St. Josephs Day");

		lh.addStaticHoliday ("17-APR-2003", "Holy Thursday");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2003", "Labor Day");

		lh.addStaticHoliday ("02-JUN-2003", "Ascension Day Observed");

		lh.addStaticHoliday ("23-JUN-2003", "Corpus Christi Observed");

		lh.addStaticHoliday ("30-JUN-2003", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("07-AUG-2003", "National Holiday");

		lh.addStaticHoliday ("18-AUG-2003", "Assumption Day");

		lh.addStaticHoliday ("13-OCT-2003", "Columbus Day");

		lh.addStaticHoliday ("03-NOV-2003", "All Saints Day");

		lh.addStaticHoliday ("17-NOV-2003", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2003", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("12-JAN-2004", "Epiphany");

		lh.addStaticHoliday ("22-MAR-2004", "St. Josephs Day");

		lh.addStaticHoliday ("08-APR-2004", "Holy Thursday");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2004", "Ascension Day Observed");

		lh.addStaticHoliday ("14-JUN-2004", "Corpus Christi Observed");

		lh.addStaticHoliday ("21-JUN-2004", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("05-JUL-2004", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2004", "Independence Day");

		lh.addStaticHoliday ("16-AUG-2004", "Assumption Day");

		lh.addStaticHoliday ("18-OCT-2004", "Columbus Day");

		lh.addStaticHoliday ("01-NOV-2004", "All Saints Day");

		lh.addStaticHoliday ("15-NOV-2004", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2004", "Immaculate Conception");

		lh.addStaticHoliday ("10-JAN-2005", "Epiphany");

		lh.addStaticHoliday ("21-MAR-2005", "St. Josephs Day");

		lh.addStaticHoliday ("24-MAR-2005", "Holy Thursday");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("09-MAY-2005", "Ascension Day Observed");

		lh.addStaticHoliday ("30-MAY-2005", "Corpus Christi Observed");

		lh.addStaticHoliday ("06-JUN-2005", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("04-JUL-2005", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2005", "Independence Day");

		lh.addStaticHoliday ("15-AUG-2005", "Assumption Day");

		lh.addStaticHoliday ("17-OCT-2005", "Columbus Day");

		lh.addStaticHoliday ("07-NOV-2005", "All Saints Day");

		lh.addStaticHoliday ("14-NOV-2005", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2005", "Immaculate Conception");

		lh.addStaticHoliday ("09-JAN-2006", "Epiphany");

		lh.addStaticHoliday ("20-MAR-2006", "St. Josephs Day");

		lh.addStaticHoliday ("13-APR-2006", "Holy Thursday");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2006", "Labor Day");

		lh.addStaticHoliday ("29-MAY-2006", "Ascension Day Observed");

		lh.addStaticHoliday ("19-JUN-2006", "Corpus Christi Observed");

		lh.addStaticHoliday ("26-JUN-2006", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("03-JUL-2006", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2006", "Independence Day");

		lh.addStaticHoliday ("07-AUG-2006", "National Holiday");

		lh.addStaticHoliday ("21-AUG-2006", "Assumption Day");

		lh.addStaticHoliday ("16-OCT-2006", "Columbus Day");

		lh.addStaticHoliday ("06-NOV-2006", "All Saints Day");

		lh.addStaticHoliday ("13-NOV-2006", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2006", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("08-JAN-2007", "Epiphany");

		lh.addStaticHoliday ("19-MAR-2007", "St. Josephs Day");

		lh.addStaticHoliday ("05-APR-2007", "Holy Thursday");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2007", "Labor Day");

		lh.addStaticHoliday ("21-MAY-2007", "Ascension Day Observed");

		lh.addStaticHoliday ("11-JUN-2007", "Corpus Christi Observed");

		lh.addStaticHoliday ("18-JUN-2007", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("02-JUL-2007", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2007", "Independence Day");

		lh.addStaticHoliday ("07-AUG-2007", "National Holiday");

		lh.addStaticHoliday ("20-AUG-2007", "Assumption Day");

		lh.addStaticHoliday ("15-OCT-2007", "Columbus Day");

		lh.addStaticHoliday ("05-NOV-2007", "All Saints Day");

		lh.addStaticHoliday ("12-NOV-2007", "Independence of Cartagena");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2008", "Epiphany");

		lh.addStaticHoliday ("20-MAR-2008", "Holy Thursday");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("24-MAR-2008", "St. Josephs Day");

		lh.addStaticHoliday ("01-MAY-2008", "Labor Day");

		lh.addStaticHoliday ("05-MAY-2008", "Ascension Day Observed");

		lh.addStaticHoliday ("26-MAY-2008", "Corpus Christi Observed");

		lh.addStaticHoliday ("02-JUN-2008", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("30-JUN-2008", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("07-AUG-2008", "National Holiday");

		lh.addStaticHoliday ("18-AUG-2008", "Assumption Day");

		lh.addStaticHoliday ("13-OCT-2008", "Columbus Day");

		lh.addStaticHoliday ("03-NOV-2008", "All Saints Day");

		lh.addStaticHoliday ("17-NOV-2008", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2008", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("12-JAN-2009", "Epiphany");

		lh.addStaticHoliday ("23-MAR-2009", "St. Josephs Day");

		lh.addStaticHoliday ("09-APR-2009", "Holy Thursday");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2009", "Labor Day");

		lh.addStaticHoliday ("25-MAY-2009", "Ascension Day Observed");

		lh.addStaticHoliday ("15-JUN-2009", "Corpus Christi Observed");

		lh.addStaticHoliday ("22-JUN-2009", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("29-JUN-2009", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2009", "Independence Day");

		lh.addStaticHoliday ("07-AUG-2009", "National Holiday");

		lh.addStaticHoliday ("17-AUG-2009", "Assumption Day");

		lh.addStaticHoliday ("12-OCT-2009", "Columbus Day");

		lh.addStaticHoliday ("02-NOV-2009", "All Saints Day");

		lh.addStaticHoliday ("16-NOV-2009", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2009", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("11-JAN-2010", "Epiphany");

		lh.addStaticHoliday ("22-MAR-2010", "St. Josephs Day");

		lh.addStaticHoliday ("01-APR-2010", "Holy Thursday");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("17-MAY-2010", "Ascension Day Observed");

		lh.addStaticHoliday ("07-JUN-2010", "Corpus Christi Observed");

		lh.addStaticHoliday ("14-JUN-2010", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("05-JUL-2010", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2010", "Independence Day");

		lh.addStaticHoliday ("16-AUG-2010", "Assumption Day");

		lh.addStaticHoliday ("18-OCT-2010", "Columbus Day");

		lh.addStaticHoliday ("01-NOV-2010", "All Saints Day");

		lh.addStaticHoliday ("15-NOV-2010", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2010", "Immaculate Conception");

		lh.addStaticHoliday ("10-JAN-2011", "Epiphany");

		lh.addStaticHoliday ("21-MAR-2011", "St. Josephs Day");

		lh.addStaticHoliday ("21-APR-2011", "Holy Thursday");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("06-JUN-2011", "Ascension Day Observed");

		lh.addStaticHoliday ("27-JUN-2011", "Corpus Christi Observed");

		lh.addStaticHoliday ("04-JUL-2011", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2011", "Independence Day");

		lh.addStaticHoliday ("15-AUG-2011", "Assumption Day");

		lh.addStaticHoliday ("17-OCT-2011", "Columbus Day");

		lh.addStaticHoliday ("07-NOV-2011", "All Saints Day");

		lh.addStaticHoliday ("14-NOV-2011", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2011", "Immaculate Conception");

		lh.addStaticHoliday ("09-JAN-2012", "Epiphany");

		lh.addStaticHoliday ("19-MAR-2012", "St. Josephs Day");

		lh.addStaticHoliday ("05-APR-2012", "Holy Thursday");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2012", "Labor Day");

		lh.addStaticHoliday ("21-MAY-2012", "Ascension Day Observed");

		lh.addStaticHoliday ("11-JUN-2012", "Corpus Christi Observed");

		lh.addStaticHoliday ("18-JUN-2012", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("02-JUL-2012", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2012", "Independence Day");

		lh.addStaticHoliday ("07-AUG-2012", "National Holiday");

		lh.addStaticHoliday ("20-AUG-2012", "Assumption Day");

		lh.addStaticHoliday ("15-OCT-2012", "Columbus Day");

		lh.addStaticHoliday ("05-NOV-2012", "All Saints Day");

		lh.addStaticHoliday ("12-NOV-2012", "Independence of Cartagena");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2013", "Epiphany");

		lh.addStaticHoliday ("25-MAR-2013", "St. Josephs Day");

		lh.addStaticHoliday ("28-MAR-2013", "Holy Thursday");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2013", "Labor Day");

		lh.addStaticHoliday ("13-MAY-2013", "Ascension Day Observed");

		lh.addStaticHoliday ("03-JUN-2013", "Corpus Christi Observed");

		lh.addStaticHoliday ("10-JUN-2013", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("01-JUL-2013", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("07-AUG-2013", "National Holiday");

		lh.addStaticHoliday ("19-AUG-2013", "Assumption Day");

		lh.addStaticHoliday ("14-OCT-2013", "Columbus Day");

		lh.addStaticHoliday ("04-NOV-2013", "All Saints Day");

		lh.addStaticHoliday ("11-NOV-2013", "Independence of Cartagena");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2014", "Epiphany");

		lh.addStaticHoliday ("24-MAR-2014", "St. Josephs Day");

		lh.addStaticHoliday ("17-APR-2014", "Holy Thursday");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2014", "Labor Day");

		lh.addStaticHoliday ("02-JUN-2014", "Ascension Day Observed");

		lh.addStaticHoliday ("23-JUN-2014", "Corpus Christi Observed");

		lh.addStaticHoliday ("30-JUN-2014", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("07-AUG-2014", "National Holiday");

		lh.addStaticHoliday ("18-AUG-2014", "Assumption Day");

		lh.addStaticHoliday ("13-OCT-2014", "Columbus Day");

		lh.addStaticHoliday ("03-NOV-2014", "All Saints Day");

		lh.addStaticHoliday ("17-NOV-2014", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2014", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("12-JAN-2015", "Epiphany");

		lh.addStaticHoliday ("23-MAR-2015", "St. Josephs Day");

		lh.addStaticHoliday ("02-APR-2015", "Holy Thursday");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2015", "Labor Day");

		lh.addStaticHoliday ("18-MAY-2015", "Ascension Day Observed");

		lh.addStaticHoliday ("08-JUN-2015", "Corpus Christi Observed");

		lh.addStaticHoliday ("15-JUN-2015", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("29-JUN-2015", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2015", "Independence Day");

		lh.addStaticHoliday ("07-AUG-2015", "National Holiday");

		lh.addStaticHoliday ("17-AUG-2015", "Assumption Day");

		lh.addStaticHoliday ("12-OCT-2015", "Columbus Day");

		lh.addStaticHoliday ("02-NOV-2015", "All Saints Day");

		lh.addStaticHoliday ("16-NOV-2015", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2015", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("11-JAN-2016", "Epiphany");

		lh.addStaticHoliday ("21-MAR-2016", "St. Josephs Day");

		lh.addStaticHoliday ("24-MAR-2016", "Holy Thursday");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("09-MAY-2016", "Ascension Day Observed");

		lh.addStaticHoliday ("30-MAY-2016", "Corpus Christi Observed");

		lh.addStaticHoliday ("06-JUN-2016", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("04-JUL-2016", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2016", "Independence Day");

		lh.addStaticHoliday ("15-AUG-2016", "Assumption Day");

		lh.addStaticHoliday ("17-OCT-2016", "Columbus Day");

		lh.addStaticHoliday ("07-NOV-2016", "All Saints Day");

		lh.addStaticHoliday ("14-NOV-2016", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2016", "Immaculate Conception");

		lh.addStaticHoliday ("09-JAN-2017", "Epiphany");

		lh.addStaticHoliday ("20-MAR-2017", "St. Josephs Day");

		lh.addStaticHoliday ("13-APR-2017", "Holy Thursday");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2017", "Labor Day");

		lh.addStaticHoliday ("29-MAY-2017", "Ascension Day Observed");

		lh.addStaticHoliday ("19-JUN-2017", "Corpus Christi Observed");

		lh.addStaticHoliday ("26-JUN-2017", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("03-JUL-2017", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2017", "Independence Day");

		lh.addStaticHoliday ("07-AUG-2017", "National Holiday");

		lh.addStaticHoliday ("21-AUG-2017", "Assumption Day");

		lh.addStaticHoliday ("16-OCT-2017", "Columbus Day");

		lh.addStaticHoliday ("06-NOV-2017", "All Saints Day");

		lh.addStaticHoliday ("13-NOV-2017", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2017", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("08-JAN-2018", "Epiphany");

		lh.addStaticHoliday ("19-MAR-2018", "St. Josephs Day");

		lh.addStaticHoliday ("29-MAR-2018", "Holy Thursday");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2018", "Labor Day");

		lh.addStaticHoliday ("14-MAY-2018", "Ascension Day Observed");

		lh.addStaticHoliday ("04-JUN-2018", "Corpus Christi Observed");

		lh.addStaticHoliday ("11-JUN-2018", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("02-JUL-2018", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2018", "Independence Day");

		lh.addStaticHoliday ("07-AUG-2018", "National Holiday");

		lh.addStaticHoliday ("20-AUG-2018", "Assumption Day");

		lh.addStaticHoliday ("15-OCT-2018", "Columbus Day");

		lh.addStaticHoliday ("05-NOV-2018", "All Saints Day");

		lh.addStaticHoliday ("12-NOV-2018", "Independence of Cartagena");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2019", "Epiphany");

		lh.addStaticHoliday ("25-MAR-2019", "St. Josephs Day");

		lh.addStaticHoliday ("18-APR-2019", "Holy Thursday");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2019", "Labor Day");

		lh.addStaticHoliday ("03-JUN-2019", "Ascension Day Observed");

		lh.addStaticHoliday ("24-JUN-2019", "Corpus Christi Observed");

		lh.addStaticHoliday ("01-JUL-2019", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("07-AUG-2019", "National Holiday");

		lh.addStaticHoliday ("19-AUG-2019", "Assumption Day");

		lh.addStaticHoliday ("14-OCT-2019", "Columbus Day");

		lh.addStaticHoliday ("04-NOV-2019", "All Saints Day");

		lh.addStaticHoliday ("11-NOV-2019", "Independence of Cartagena");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2020", "Epiphany");

		lh.addStaticHoliday ("23-MAR-2020", "St. Josephs Day");

		lh.addStaticHoliday ("09-APR-2020", "Holy Thursday");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2020", "Labor Day");

		lh.addStaticHoliday ("25-MAY-2020", "Ascension Day Observed");

		lh.addStaticHoliday ("15-JUN-2020", "Corpus Christi Observed");

		lh.addStaticHoliday ("22-JUN-2020", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("29-JUN-2020", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2020", "Independence Day");

		lh.addStaticHoliday ("07-AUG-2020", "National Holiday");

		lh.addStaticHoliday ("17-AUG-2020", "Assumption Day");

		lh.addStaticHoliday ("12-OCT-2020", "Columbus Day");

		lh.addStaticHoliday ("02-NOV-2020", "All Saints Day");

		lh.addStaticHoliday ("16-NOV-2020", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2020", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("11-JAN-2021", "Epiphany");

		lh.addStaticHoliday ("22-MAR-2021", "St. Josephs Day");

		lh.addStaticHoliday ("01-APR-2021", "Holy Thursday");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("17-MAY-2021", "Ascension Day Observed");

		lh.addStaticHoliday ("07-JUN-2021", "Corpus Christi Observed");

		lh.addStaticHoliday ("14-JUN-2021", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("05-JUL-2021", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2021", "Independence Day");

		lh.addStaticHoliday ("16-AUG-2021", "Assumption Day");

		lh.addStaticHoliday ("18-OCT-2021", "Columbus Day");

		lh.addStaticHoliday ("01-NOV-2021", "All Saints Day");

		lh.addStaticHoliday ("15-NOV-2021", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2021", "Immaculate Conception");

		lh.addStaticHoliday ("10-JAN-2022", "Epiphany");

		lh.addStaticHoliday ("21-MAR-2022", "St. Josephs Day");

		lh.addStaticHoliday ("14-APR-2022", "Holy Thursday");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("30-MAY-2022", "Ascension Day Observed");

		lh.addStaticHoliday ("20-JUN-2022", "Corpus Christi Observed");

		lh.addStaticHoliday ("27-JUN-2022", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("04-JUL-2022", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2022", "Independence Day");

		lh.addStaticHoliday ("15-AUG-2022", "Assumption Day");

		lh.addStaticHoliday ("17-OCT-2022", "Columbus Day");

		lh.addStaticHoliday ("07-NOV-2022", "All Saints Day");

		lh.addStaticHoliday ("14-NOV-2022", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2022", "Immaculate Conception");

		lh.addStaticHoliday ("09-JAN-2023", "Epiphany");

		lh.addStaticHoliday ("20-MAR-2023", "St. Josephs Day");

		lh.addStaticHoliday ("06-APR-2023", "Holy Thursday");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2023", "Labor Day");

		lh.addStaticHoliday ("22-MAY-2023", "Ascension Day Observed");

		lh.addStaticHoliday ("12-JUN-2023", "Corpus Christi Observed");

		lh.addStaticHoliday ("19-JUN-2023", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("03-JUL-2023", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2023", "Independence Day");

		lh.addStaticHoliday ("07-AUG-2023", "National Holiday");

		lh.addStaticHoliday ("21-AUG-2023", "Assumption Day");

		lh.addStaticHoliday ("16-OCT-2023", "Columbus Day");

		lh.addStaticHoliday ("06-NOV-2023", "All Saints Day");

		lh.addStaticHoliday ("13-NOV-2023", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2023", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("08-JAN-2024", "Epiphany");

		lh.addStaticHoliday ("25-MAR-2024", "St. Josephs Day");

		lh.addStaticHoliday ("28-MAR-2024", "Holy Thursday");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2024", "Labor Day");

		lh.addStaticHoliday ("13-MAY-2024", "Ascension Day Observed");

		lh.addStaticHoliday ("03-JUN-2024", "Corpus Christi Observed");

		lh.addStaticHoliday ("10-JUN-2024", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("01-JUL-2024", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("07-AUG-2024", "National Holiday");

		lh.addStaticHoliday ("19-AUG-2024", "Assumption Day");

		lh.addStaticHoliday ("14-OCT-2024", "Columbus Day");

		lh.addStaticHoliday ("04-NOV-2024", "All Saints Day");

		lh.addStaticHoliday ("11-NOV-2024", "Independence of Cartagena");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2025", "Epiphany");

		lh.addStaticHoliday ("24-MAR-2025", "St. Josephs Day");

		lh.addStaticHoliday ("17-APR-2025", "Holy Thursday");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2025", "Labor Day");

		lh.addStaticHoliday ("02-JUN-2025", "Ascension Day Observed");

		lh.addStaticHoliday ("23-JUN-2025", "Corpus Christi Observed");

		lh.addStaticHoliday ("30-JUN-2025", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("07-AUG-2025", "National Holiday");

		lh.addStaticHoliday ("18-AUG-2025", "Assumption Day");

		lh.addStaticHoliday ("13-OCT-2025", "Columbus Day");

		lh.addStaticHoliday ("03-NOV-2025", "All Saints Day");

		lh.addStaticHoliday ("17-NOV-2025", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2025", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("12-JAN-2026", "Epiphany");

		lh.addStaticHoliday ("23-MAR-2026", "St. Josephs Day");

		lh.addStaticHoliday ("02-APR-2026", "Holy Thursday");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2026", "Labor Day");

		lh.addStaticHoliday ("18-MAY-2026", "Ascension Day Observed");

		lh.addStaticHoliday ("08-JUN-2026", "Corpus Christi Observed");

		lh.addStaticHoliday ("15-JUN-2026", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("29-JUN-2026", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2026", "Independence Day");

		lh.addStaticHoliday ("07-AUG-2026", "National Holiday");

		lh.addStaticHoliday ("17-AUG-2026", "Assumption Day");

		lh.addStaticHoliday ("12-OCT-2026", "Columbus Day");

		lh.addStaticHoliday ("02-NOV-2026", "All Saints Day");

		lh.addStaticHoliday ("16-NOV-2026", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2026", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("11-JAN-2027", "Epiphany");

		lh.addStaticHoliday ("22-MAR-2027", "St. Josephs Day");

		lh.addStaticHoliday ("25-MAR-2027", "Holy Thursday");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("10-MAY-2027", "Ascension Day Observed");

		lh.addStaticHoliday ("31-MAY-2027", "Corpus Christi Observed");

		lh.addStaticHoliday ("07-JUN-2027", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("05-JUL-2027", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2027", "Independence Day");

		lh.addStaticHoliday ("16-AUG-2027", "Assumption Day");

		lh.addStaticHoliday ("18-OCT-2027", "Columbus Day");

		lh.addStaticHoliday ("01-NOV-2027", "All Saints Day");

		lh.addStaticHoliday ("15-NOV-2027", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2027", "Immaculate Conception");

		lh.addStaticHoliday ("10-JAN-2028", "Epiphany");

		lh.addStaticHoliday ("20-MAR-2028", "St. Josephs Day");

		lh.addStaticHoliday ("13-APR-2028", "Holy Thursday");

		lh.addStaticHoliday ("14-APR-2028", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2028", "Labor Day");

		lh.addStaticHoliday ("29-MAY-2028", "Ascension Day Observed");

		lh.addStaticHoliday ("19-JUN-2028", "Corpus Christi Observed");

		lh.addStaticHoliday ("26-JUN-2028", "Sacred Heart of Jesus");

		lh.addStaticHoliday ("03-JUL-2028", "Sts. Peter and Paul Day");

		lh.addStaticHoliday ("20-JUL-2028", "Independence Day");

		lh.addStaticHoliday ("07-AUG-2028", "National Holiday");

		lh.addStaticHoliday ("21-AUG-2028", "Assumption Day");

		lh.addStaticHoliday ("16-OCT-2028", "Columbus Day");

		lh.addStaticHoliday ("06-NOV-2028", "All Saints Day");

		lh.addStaticHoliday ("13-NOV-2028", "Independence of Cartagena");

		lh.addStaticHoliday ("08-DEC-2028", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStandardWeekend();

		return lh;
	}
}
