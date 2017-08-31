
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

public class COFHoliday implements org.drip.analytics.holset.LocationHoliday {
	public COFHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "COF";
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

		lh.addStaticHoliday ("08-DEC-2007", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("31-DEC-2007", "New Years day Bank Holiday");

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

		lh.addStaticHoliday ("31-DEC-2008", "New Years Eve");

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

		lh.addStaticHoliday ("31-DEC-2009", "New Years Eve Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2010", "COP");

		lh.addStaticHoliday ("11-JAN-2010", "COP");

		lh.addStaticHoliday ("22-MAR-2010", "COP");

		lh.addStaticHoliday ("01-APR-2010", "COP");

		lh.addStaticHoliday ("02-APR-2010", "COP");

		lh.addStaticHoliday ("17-MAY-2010", "COP");

		lh.addStaticHoliday ("07-JUN-2010", "COP");

		lh.addStaticHoliday ("14-JUN-2010", "COP");

		lh.addStaticHoliday ("05-JUL-2010", "COP");

		lh.addStaticHoliday ("20-JUL-2010", "COP");

		lh.addStaticHoliday ("16-AUG-2010", "COP");

		lh.addStaticHoliday ("18-OCT-2010", "COP");

		lh.addStaticHoliday ("01-NOV-2010", "COP");

		lh.addStaticHoliday ("15-NOV-2010", "COP");

		lh.addStaticHoliday ("08-DEC-2010", "COP");

		lh.addStaticHoliday ("10-JAN-2011", "COP");

		lh.addStaticHoliday ("21-MAR-2011", "COP");

		lh.addStaticHoliday ("21-APR-2011", "COP");

		lh.addStaticHoliday ("22-APR-2011", "COP");

		lh.addStaticHoliday ("06-JUN-2011", "COP");

		lh.addStaticHoliday ("27-JUN-2011", "COP");

		lh.addStaticHoliday ("04-JUL-2011", "COP");

		lh.addStaticHoliday ("20-JUL-2011", "COP");

		lh.addStaticHoliday ("15-AUG-2011", "COP");

		lh.addStaticHoliday ("17-OCT-2011", "COP");

		lh.addStaticHoliday ("07-NOV-2011", "COP");

		lh.addStaticHoliday ("14-NOV-2011", "COP");

		lh.addStaticHoliday ("08-DEC-2011", "COP");

		lh.addStaticHoliday ("09-JAN-2012", "COP");

		lh.addStaticHoliday ("19-MAR-2012", "COP");

		lh.addStaticHoliday ("05-APR-2012", "COP");

		lh.addStaticHoliday ("06-APR-2012", "COP");

		lh.addStaticHoliday ("01-MAY-2012", "COP");

		lh.addStaticHoliday ("21-MAY-2012", "COP");

		lh.addStaticHoliday ("11-JUN-2012", "COP");

		lh.addStaticHoliday ("18-JUN-2012", "COP");

		lh.addStaticHoliday ("02-JUL-2012", "COP");

		lh.addStaticHoliday ("20-JUL-2012", "COP");

		lh.addStaticHoliday ("07-AUG-2012", "COP");

		lh.addStaticHoliday ("20-AUG-2012", "COP");

		lh.addStaticHoliday ("15-OCT-2012", "COP");

		lh.addStaticHoliday ("05-NOV-2012", "COP");

		lh.addStaticHoliday ("12-NOV-2012", "COP");

		lh.addStaticHoliday ("25-DEC-2012", "COP");

		lh.addStaticHoliday ("01-JAN-2013", "COP");

		lh.addStaticHoliday ("07-JAN-2013", "COP");

		lh.addStaticHoliday ("25-MAR-2013", "COP");

		lh.addStaticHoliday ("28-MAR-2013", "COP");

		lh.addStaticHoliday ("29-MAR-2013", "COP");

		lh.addStaticHoliday ("01-MAY-2013", "COP");

		lh.addStaticHoliday ("13-MAY-2013", "COP");

		lh.addStaticHoliday ("03-JUN-2013", "COP");

		lh.addStaticHoliday ("10-JUN-2013", "COP");

		lh.addStaticHoliday ("01-JUL-2013", "COP");

		lh.addStaticHoliday ("07-AUG-2013", "COP");

		lh.addStaticHoliday ("19-AUG-2013", "COP");

		lh.addStaticHoliday ("14-OCT-2013", "COP");

		lh.addStaticHoliday ("04-NOV-2013", "COP");

		lh.addStaticHoliday ("11-NOV-2013", "COP");

		lh.addStaticHoliday ("25-DEC-2013", "COP");

		lh.addStaticHoliday ("01-JAN-2014", "COP");

		lh.addStaticHoliday ("06-JAN-2014", "COP");

		lh.addStaticHoliday ("24-MAR-2014", "COP");

		lh.addStaticHoliday ("17-APR-2014", "COP");

		lh.addStaticHoliday ("18-APR-2014", "COP");

		lh.addStaticHoliday ("01-MAY-2014", "COP");

		lh.addStaticHoliday ("02-JUN-2014", "COP");

		lh.addStaticHoliday ("23-JUN-2014", "COP");

		lh.addStaticHoliday ("30-JUN-2014", "COP");

		lh.addStaticHoliday ("07-AUG-2014", "COP");

		lh.addStaticHoliday ("18-AUG-2014", "COP");

		lh.addStaticHoliday ("13-OCT-2014", "COP");

		lh.addStaticHoliday ("03-NOV-2014", "COP");

		lh.addStaticHoliday ("17-NOV-2014", "COP");

		lh.addStaticHoliday ("08-DEC-2014", "COP");

		lh.addStaticHoliday ("25-DEC-2014", "COP");

		lh.addStaticHoliday ("01-JAN-2015", "COP");

		lh.addStaticHoliday ("12-JAN-2015", "COP");

		lh.addStaticHoliday ("23-MAR-2015", "COP");

		lh.addStaticHoliday ("02-APR-2015", "COP");

		lh.addStaticHoliday ("03-APR-2015", "COP");

		lh.addStaticHoliday ("01-MAY-2015", "COP");

		lh.addStaticHoliday ("18-MAY-2015", "COP");

		lh.addStaticHoliday ("08-JUN-2015", "COP");

		lh.addStaticHoliday ("15-JUN-2015", "COP");

		lh.addStaticHoliday ("29-JUN-2015", "COP");

		lh.addStaticHoliday ("20-JUL-2015", "COP");

		lh.addStaticHoliday ("07-AUG-2015", "COP");

		lh.addStaticHoliday ("17-AUG-2015", "COP");

		lh.addStaticHoliday ("12-OCT-2015", "COP");

		lh.addStaticHoliday ("02-NOV-2015", "COP");

		lh.addStaticHoliday ("16-NOV-2015", "COP");

		lh.addStaticHoliday ("08-DEC-2015", "COP");

		lh.addStaticHoliday ("25-DEC-2015", "COP");

		lh.addStaticHoliday ("01-JAN-2016", "COP");

		lh.addStaticHoliday ("11-JAN-2016", "COP");

		lh.addStaticHoliday ("21-MAR-2016", "COP");

		lh.addStaticHoliday ("24-MAR-2016", "COP");

		lh.addStaticHoliday ("25-MAR-2016", "COP");

		lh.addStaticHoliday ("09-MAY-2016", "COP");

		lh.addStaticHoliday ("30-MAY-2016", "COP");

		lh.addStaticHoliday ("06-JUN-2016", "COP");

		lh.addStaticHoliday ("04-JUL-2016", "COP");

		lh.addStaticHoliday ("20-JUL-2016", "COP");

		lh.addStaticHoliday ("15-AUG-2016", "COP");

		lh.addStaticHoliday ("17-OCT-2016", "COP");

		lh.addStaticHoliday ("07-NOV-2016", "COP");

		lh.addStaticHoliday ("14-NOV-2016", "COP");

		lh.addStaticHoliday ("08-DEC-2016", "COP");

		lh.addStaticHoliday ("09-JAN-2017", "COP");

		lh.addStaticHoliday ("20-MAR-2017", "COP");

		lh.addStaticHoliday ("13-APR-2017", "COP");

		lh.addStaticHoliday ("14-APR-2017", "COP");

		lh.addStaticHoliday ("01-MAY-2017", "COP");

		lh.addStaticHoliday ("29-MAY-2017", "COP");

		lh.addStaticHoliday ("19-JUN-2017", "COP");

		lh.addStaticHoliday ("26-JUN-2017", "COP");

		lh.addStaticHoliday ("03-JUL-2017", "COP");

		lh.addStaticHoliday ("20-JUL-2017", "COP");

		lh.addStaticHoliday ("07-AUG-2017", "COP");

		lh.addStaticHoliday ("21-AUG-2017", "COP");

		lh.addStaticHoliday ("16-OCT-2017", "COP");

		lh.addStaticHoliday ("06-NOV-2017", "COP");

		lh.addStaticHoliday ("13-NOV-2017", "COP");

		lh.addStaticHoliday ("08-DEC-2017", "COP");

		lh.addStaticHoliday ("25-DEC-2017", "COP");

		lh.addStaticHoliday ("01-JAN-2018", "COP");

		lh.addStaticHoliday ("08-JAN-2018", "COP");

		lh.addStaticHoliday ("19-MAR-2018", "COP");

		lh.addStaticHoliday ("29-MAR-2018", "COP");

		lh.addStaticHoliday ("30-MAR-2018", "COP");

		lh.addStaticHoliday ("01-MAY-2018", "COP");

		lh.addStaticHoliday ("14-MAY-2018", "COP");

		lh.addStaticHoliday ("04-JUN-2018", "COP");

		lh.addStaticHoliday ("11-JUN-2018", "COP");

		lh.addStaticHoliday ("02-JUL-2018", "COP");

		lh.addStaticHoliday ("20-JUL-2018", "COP");

		lh.addStaticHoliday ("07-AUG-2018", "COP");

		lh.addStaticHoliday ("20-AUG-2018", "COP");

		lh.addStaticHoliday ("15-OCT-2018", "COP");

		lh.addStaticHoliday ("05-NOV-2018", "COP");

		lh.addStaticHoliday ("12-NOV-2018", "COP");

		lh.addStaticHoliday ("25-DEC-2018", "COP");

		lh.addStaticHoliday ("01-JAN-2019", "COP");

		lh.addStaticHoliday ("07-JAN-2019", "COP");

		lh.addStaticHoliday ("25-MAR-2019", "COP");

		lh.addStaticHoliday ("18-APR-2019", "COP");

		lh.addStaticHoliday ("19-APR-2019", "COP");

		lh.addStaticHoliday ("01-MAY-2019", "COP");

		lh.addStaticHoliday ("03-JUN-2019", "COP");

		lh.addStaticHoliday ("24-JUN-2019", "COP");

		lh.addStaticHoliday ("01-JUL-2019", "COP");

		lh.addStaticHoliday ("07-AUG-2019", "COP");

		lh.addStaticHoliday ("19-AUG-2019", "COP");

		lh.addStaticHoliday ("14-OCT-2019", "COP");

		lh.addStaticHoliday ("04-NOV-2019", "COP");

		lh.addStaticHoliday ("11-NOV-2019", "COP");

		lh.addStaticHoliday ("25-DEC-2019", "COP");

		lh.addStaticHoliday ("01-JAN-2020", "COP");

		lh.addStaticHoliday ("06-JAN-2020", "COP");

		lh.addStaticHoliday ("23-MAR-2020", "COP");

		lh.addStaticHoliday ("09-APR-2020", "COP");

		lh.addStaticHoliday ("10-APR-2020", "COP");

		lh.addStaticHoliday ("01-MAY-2020", "COP");

		lh.addStaticHoliday ("25-MAY-2020", "COP");

		lh.addStaticHoliday ("15-JUN-2020", "COP");

		lh.addStaticHoliday ("22-JUN-2020", "COP");

		lh.addStaticHoliday ("29-JUN-2020", "COP");

		lh.addStaticHoliday ("20-JUL-2020", "COP");

		lh.addStaticHoliday ("07-AUG-2020", "COP");

		lh.addStaticHoliday ("17-AUG-2020", "COP");

		lh.addStaticHoliday ("12-OCT-2020", "COP");

		lh.addStaticHoliday ("02-NOV-2020", "COP");

		lh.addStaticHoliday ("16-NOV-2020", "COP");

		lh.addStaticHoliday ("08-DEC-2020", "COP");

		lh.addStaticHoliday ("25-DEC-2020", "COP");

		lh.addStaticHoliday ("01-JAN-2021", "COP");

		lh.addStaticHoliday ("11-JAN-2021", "COP");

		lh.addStaticHoliday ("22-MAR-2021", "COP");

		lh.addStaticHoliday ("01-APR-2021", "COP");

		lh.addStaticHoliday ("02-APR-2021", "COP");

		lh.addStaticHoliday ("17-MAY-2021", "COP");

		lh.addStaticHoliday ("07-JUN-2021", "COP");

		lh.addStaticHoliday ("14-JUN-2021", "COP");

		lh.addStaticHoliday ("05-JUL-2021", "COP");

		lh.addStaticHoliday ("20-JUL-2021", "COP");

		lh.addStaticHoliday ("16-AUG-2021", "COP");

		lh.addStaticHoliday ("18-OCT-2021", "COP");

		lh.addStaticHoliday ("01-NOV-2021", "COP");

		lh.addStaticHoliday ("15-NOV-2021", "COP");

		lh.addStaticHoliday ("08-DEC-2021", "COP");

		lh.addStaticHoliday ("10-JAN-2022", "COP");

		lh.addStaticHoliday ("21-MAR-2022", "COP");

		lh.addStaticHoliday ("14-APR-2022", "COP");

		lh.addStaticHoliday ("15-APR-2022", "COP");

		lh.addStaticHoliday ("30-MAY-2022", "COP");

		lh.addStaticHoliday ("20-JUN-2022", "COP");

		lh.addStaticHoliday ("27-JUN-2022", "COP");

		lh.addStaticHoliday ("04-JUL-2022", "COP");

		lh.addStaticHoliday ("20-JUL-2022", "COP");

		lh.addStaticHoliday ("15-AUG-2022", "COP");

		lh.addStaticHoliday ("17-OCT-2022", "COP");

		lh.addStaticHoliday ("07-NOV-2022", "COP");

		lh.addStaticHoliday ("14-NOV-2022", "COP");

		lh.addStaticHoliday ("08-DEC-2022", "COP");

		lh.addStaticHoliday ("09-JAN-2023", "COP");

		lh.addStaticHoliday ("20-MAR-2023", "COP");

		lh.addStaticHoliday ("06-APR-2023", "COP");

		lh.addStaticHoliday ("07-APR-2023", "COP");

		lh.addStaticHoliday ("01-MAY-2023", "COP");

		lh.addStaticHoliday ("22-MAY-2023", "COP");

		lh.addStaticHoliday ("12-JUN-2023", "COP");

		lh.addStaticHoliday ("19-JUN-2023", "COP");

		lh.addStaticHoliday ("03-JUL-2023", "COP");

		lh.addStaticHoliday ("20-JUL-2023", "COP");

		lh.addStaticHoliday ("07-AUG-2023", "COP");

		lh.addStaticHoliday ("21-AUG-2023", "COP");

		lh.addStaticHoliday ("16-OCT-2023", "COP");

		lh.addStaticHoliday ("06-NOV-2023", "COP");

		lh.addStaticHoliday ("13-NOV-2023", "COP");

		lh.addStaticHoliday ("08-DEC-2023", "COP");

		lh.addStaticHoliday ("25-DEC-2023", "COP");

		lh.addStaticHoliday ("01-JAN-2024", "COP");

		lh.addStaticHoliday ("08-JAN-2024", "COP");

		lh.addStaticHoliday ("25-MAR-2024", "COP");

		lh.addStaticHoliday ("28-MAR-2024", "COP");

		lh.addStaticHoliday ("29-MAR-2024", "COP");

		lh.addStaticHoliday ("01-MAY-2024", "COP");

		lh.addStaticHoliday ("13-MAY-2024", "COP");

		lh.addStaticHoliday ("03-JUN-2024", "COP");

		lh.addStaticHoliday ("10-JUN-2024", "COP");

		lh.addStaticHoliday ("01-JUL-2024", "COP");

		lh.addStaticHoliday ("07-AUG-2024", "COP");

		lh.addStaticHoliday ("19-AUG-2024", "COP");

		lh.addStaticHoliday ("14-OCT-2024", "COP");

		lh.addStaticHoliday ("04-NOV-2024", "COP");

		lh.addStaticHoliday ("11-NOV-2024", "COP");

		lh.addStaticHoliday ("25-DEC-2024", "COP");

		lh.addStaticHoliday ("01-JAN-2025", "COP");

		lh.addStaticHoliday ("06-JAN-2025", "COP");

		lh.addStaticHoliday ("24-MAR-2025", "COP");

		lh.addStaticHoliday ("17-APR-2025", "COP");

		lh.addStaticHoliday ("18-APR-2025", "COP");

		lh.addStaticHoliday ("01-MAY-2025", "COP");

		lh.addStaticHoliday ("02-JUN-2025", "COP");

		lh.addStaticHoliday ("23-JUN-2025", "COP");

		lh.addStaticHoliday ("30-JUN-2025", "COP");

		lh.addStaticHoliday ("07-AUG-2025", "COP");

		lh.addStaticHoliday ("18-AUG-2025", "COP");

		lh.addStaticHoliday ("13-OCT-2025", "COP");

		lh.addStaticHoliday ("03-NOV-2025", "COP");

		lh.addStaticHoliday ("17-NOV-2025", "COP");

		lh.addStaticHoliday ("08-DEC-2025", "COP");

		lh.addStaticHoliday ("25-DEC-2025", "COP");

		lh.addStaticHoliday ("01-JAN-2026", "COP");

		lh.addStaticHoliday ("12-JAN-2026", "COP");

		lh.addStaticHoliday ("23-MAR-2026", "COP");

		lh.addStaticHoliday ("02-APR-2026", "COP");

		lh.addStaticHoliday ("03-APR-2026", "COP");

		lh.addStaticHoliday ("01-MAY-2026", "COP");

		lh.addStaticHoliday ("18-MAY-2026", "COP");

		lh.addStaticHoliday ("08-JUN-2026", "COP");

		lh.addStaticHoliday ("15-JUN-2026", "COP");

		lh.addStaticHoliday ("29-JUN-2026", "COP");

		lh.addStaticHoliday ("20-JUL-2026", "COP");

		lh.addStaticHoliday ("07-AUG-2026", "COP");

		lh.addStaticHoliday ("17-AUG-2026", "COP");

		lh.addStaticHoliday ("12-OCT-2026", "COP");

		lh.addStaticHoliday ("02-NOV-2026", "COP");

		lh.addStaticHoliday ("16-NOV-2026", "COP");

		lh.addStaticHoliday ("08-DEC-2026", "COP");

		lh.addStaticHoliday ("25-DEC-2026", "COP");

		lh.addStaticHoliday ("01-JAN-2027", "COP");

		lh.addStaticHoliday ("11-JAN-2027", "COP");

		lh.addStaticHoliday ("22-MAR-2027", "COP");

		lh.addStaticHoliday ("25-MAR-2027", "COP");

		lh.addStaticHoliday ("26-MAR-2027", "COP");

		lh.addStaticHoliday ("10-MAY-2027", "COP");

		lh.addStaticHoliday ("31-MAY-2027", "COP");

		lh.addStaticHoliday ("07-JUN-2027", "COP");

		lh.addStaticHoliday ("05-JUL-2027", "COP");

		lh.addStaticHoliday ("20-JUL-2027", "COP");

		lh.addStaticHoliday ("16-AUG-2027", "COP");

		lh.addStaticHoliday ("18-OCT-2027", "COP");

		lh.addStaticHoliday ("01-NOV-2027", "COP");

		lh.addStaticHoliday ("15-NOV-2027", "COP");

		lh.addStaticHoliday ("08-DEC-2027", "COP");

		lh.addStaticHoliday ("10-JAN-2028", "COP");

		lh.addStaticHoliday ("20-MAR-2028", "COP");

		lh.addStaticHoliday ("13-APR-2028", "COP");

		lh.addStaticHoliday ("14-APR-2028", "COP");

		lh.addStaticHoliday ("01-MAY-2028", "COP");

		lh.addStaticHoliday ("29-MAY-2028", "COP");

		lh.addStaticHoliday ("19-JUN-2028", "COP");

		lh.addStaticHoliday ("26-JUN-2028", "COP");

		lh.addStaticHoliday ("03-JUL-2028", "COP");

		lh.addStaticHoliday ("20-JUL-2028", "COP");

		lh.addStaticHoliday ("07-AUG-2028", "COP");

		lh.addStaticHoliday ("21-AUG-2028", "COP");

		lh.addStaticHoliday ("16-OCT-2028", "COP");

		lh.addStaticHoliday ("06-NOV-2028", "COP");

		lh.addStaticHoliday ("13-NOV-2028", "COP");

		lh.addStaticHoliday ("08-DEC-2028", "COP");

		lh.addStaticHoliday ("25-DEC-2028", "COP");

		lh.addStaticHoliday ("01-JAN-2029", "COP");

		lh.addStaticHoliday ("08-JAN-2029", "COP");

		lh.addStaticHoliday ("19-MAR-2029", "COP");

		lh.addStaticHoliday ("29-MAR-2029", "COP");

		lh.addStaticHoliday ("30-MAR-2029", "COP");

		lh.addStaticHoliday ("01-MAY-2029", "COP");

		lh.addStaticHoliday ("14-MAY-2029", "COP");

		lh.addStaticHoliday ("04-JUN-2029", "COP");

		lh.addStaticHoliday ("11-JUN-2029", "COP");

		lh.addStaticHoliday ("02-JUL-2029", "COP");

		lh.addStaticHoliday ("20-JUL-2029", "COP");

		lh.addStaticHoliday ("07-AUG-2029", "COP");

		lh.addStaticHoliday ("20-AUG-2029", "COP");

		lh.addStaticHoliday ("15-OCT-2029", "COP");

		lh.addStaticHoliday ("05-NOV-2029", "COP");

		lh.addStaticHoliday ("12-NOV-2029", "COP");

		lh.addStaticHoliday ("25-DEC-2029", "COP");

		lh.addStaticHoliday ("01-JAN-2030", "COP");

		lh.addStaticHoliday ("07-JAN-2030", "COP");

		lh.addStaticHoliday ("25-MAR-2030", "COP");

		lh.addStaticHoliday ("18-APR-2030", "COP");

		lh.addStaticHoliday ("19-APR-2030", "COP");

		lh.addStaticHoliday ("01-MAY-2030", "COP");

		lh.addStaticHoliday ("03-JUN-2030", "COP");

		lh.addStaticHoliday ("24-JUN-2030", "COP");

		lh.addStaticHoliday ("01-JUL-2030", "COP");

		lh.addStaticHoliday ("07-AUG-2030", "COP");

		lh.addStaticHoliday ("19-AUG-2030", "COP");

		lh.addStaticHoliday ("14-OCT-2030", "COP");

		lh.addStaticHoliday ("04-NOV-2030", "COP");

		lh.addStaticHoliday ("11-NOV-2030", "COP");

		lh.addStaticHoliday ("25-DEC-2030", "COP");

		lh.addStaticHoliday ("01-JAN-2031", "COP");

		lh.addStaticHoliday ("06-JAN-2031", "COP");

		lh.addStaticHoliday ("24-MAR-2031", "COP");

		lh.addStaticHoliday ("10-APR-2031", "COP");

		lh.addStaticHoliday ("11-APR-2031", "COP");

		lh.addStaticHoliday ("01-MAY-2031", "COP");

		lh.addStaticHoliday ("26-MAY-2031", "COP");

		lh.addStaticHoliday ("16-JUN-2031", "COP");

		lh.addStaticHoliday ("23-JUN-2031", "COP");

		lh.addStaticHoliday ("30-JUN-2031", "COP");

		lh.addStaticHoliday ("07-AUG-2031", "COP");

		lh.addStaticHoliday ("18-AUG-2031", "COP");

		lh.addStaticHoliday ("13-OCT-2031", "COP");

		lh.addStaticHoliday ("03-NOV-2031", "COP");

		lh.addStaticHoliday ("17-NOV-2031", "COP");

		lh.addStaticHoliday ("08-DEC-2031", "COP");

		lh.addStaticHoliday ("25-DEC-2031", "COP");

		lh.addStaticHoliday ("01-JAN-2032", "COP");

		lh.addStaticHoliday ("12-JAN-2032", "COP");

		lh.addStaticHoliday ("22-MAR-2032", "COP");

		lh.addStaticHoliday ("25-MAR-2032", "COP");

		lh.addStaticHoliday ("26-MAR-2032", "COP");

		lh.addStaticHoliday ("10-MAY-2032", "COP");

		lh.addStaticHoliday ("31-MAY-2032", "COP");

		lh.addStaticHoliday ("07-JUN-2032", "COP");

		lh.addStaticHoliday ("05-JUL-2032", "COP");

		lh.addStaticHoliday ("20-JUL-2032", "COP");

		lh.addStaticHoliday ("16-AUG-2032", "COP");

		lh.addStaticHoliday ("18-OCT-2032", "COP");

		lh.addStaticHoliday ("01-NOV-2032", "COP");

		lh.addStaticHoliday ("15-NOV-2032", "COP");

		lh.addStaticHoliday ("08-DEC-2032", "COP");

		lh.addStaticHoliday ("10-JAN-2033", "COP");

		lh.addStaticHoliday ("21-MAR-2033", "COP");

		lh.addStaticHoliday ("14-APR-2033", "COP");

		lh.addStaticHoliday ("15-APR-2033", "COP");

		lh.addStaticHoliday ("30-MAY-2033", "COP");

		lh.addStaticHoliday ("20-JUN-2033", "COP");

		lh.addStaticHoliday ("27-JUN-2033", "COP");

		lh.addStaticHoliday ("04-JUL-2033", "COP");

		lh.addStaticHoliday ("20-JUL-2033", "COP");

		lh.addStaticHoliday ("15-AUG-2033", "COP");

		lh.addStaticHoliday ("17-OCT-2033", "COP");

		lh.addStaticHoliday ("07-NOV-2033", "COP");

		lh.addStaticHoliday ("14-NOV-2033", "COP");

		lh.addStaticHoliday ("08-DEC-2033", "COP");

		lh.addStaticHoliday ("09-JAN-2034", "COP");

		lh.addStaticHoliday ("20-MAR-2034", "COP");

		lh.addStaticHoliday ("06-APR-2034", "COP");

		lh.addStaticHoliday ("07-APR-2034", "COP");

		lh.addStaticHoliday ("01-MAY-2034", "COP");

		lh.addStaticHoliday ("22-MAY-2034", "COP");

		lh.addStaticHoliday ("12-JUN-2034", "COP");

		lh.addStaticHoliday ("19-JUN-2034", "COP");

		lh.addStaticHoliday ("03-JUL-2034", "COP");

		lh.addStaticHoliday ("20-JUL-2034", "COP");

		lh.addStaticHoliday ("07-AUG-2034", "COP");

		lh.addStaticHoliday ("21-AUG-2034", "COP");

		lh.addStaticHoliday ("16-OCT-2034", "COP");

		lh.addStaticHoliday ("06-NOV-2034", "COP");

		lh.addStaticHoliday ("13-NOV-2034", "COP");

		lh.addStaticHoliday ("08-DEC-2034", "COP");

		lh.addStaticHoliday ("25-DEC-2034", "COP");

		lh.addStaticHoliday ("01-JAN-2035", "COP");

		lh.addStaticHoliday ("08-JAN-2035", "COP");

		lh.addStaticHoliday ("19-MAR-2035", "COP");

		lh.addStaticHoliday ("22-MAR-2035", "COP");

		lh.addStaticHoliday ("23-MAR-2035", "COP");

		lh.addStaticHoliday ("01-MAY-2035", "COP");

		lh.addStaticHoliday ("07-MAY-2035", "COP");

		lh.addStaticHoliday ("28-MAY-2035", "COP");

		lh.addStaticHoliday ("04-JUN-2035", "COP");

		lh.addStaticHoliday ("02-JUL-2035", "COP");

		lh.addStaticHoliday ("20-JUL-2035", "COP");

		lh.addStaticHoliday ("07-AUG-2035", "COP");

		lh.addStaticHoliday ("20-AUG-2035", "COP");

		lh.addStaticHoliday ("15-OCT-2035", "COP");

		lh.addStaticHoliday ("05-NOV-2035", "COP");

		lh.addStaticHoliday ("12-NOV-2035", "COP");

		lh.addStaticHoliday ("25-DEC-2035", "COP");

		lh.addStaticHoliday ("01-JAN-2036", "COP");

		lh.addStaticHoliday ("07-JAN-2036", "COP");

		lh.addStaticHoliday ("24-MAR-2036", "COP");

		lh.addStaticHoliday ("10-APR-2036", "COP");

		lh.addStaticHoliday ("11-APR-2036", "COP");

		lh.addStaticHoliday ("01-MAY-2036", "COP");

		lh.addStaticHoliday ("26-MAY-2036", "COP");

		lh.addStaticHoliday ("16-JUN-2036", "COP");

		lh.addStaticHoliday ("23-JUN-2036", "COP");

		lh.addStaticHoliday ("30-JUN-2036", "COP");

		lh.addStaticHoliday ("07-AUG-2036", "COP");

		lh.addStaticHoliday ("18-AUG-2036", "COP");

		lh.addStaticHoliday ("13-OCT-2036", "COP");

		lh.addStaticHoliday ("03-NOV-2036", "COP");

		lh.addStaticHoliday ("17-NOV-2036", "COP");

		lh.addStaticHoliday ("08-DEC-2036", "COP");

		lh.addStaticHoliday ("25-DEC-2036", "COP");

		lh.addStaticHoliday ("01-JAN-2037", "COP");

		lh.addStaticHoliday ("12-JAN-2037", "COP");

		lh.addStaticHoliday ("23-MAR-2037", "COP");

		lh.addStaticHoliday ("02-APR-2037", "COP");

		lh.addStaticHoliday ("03-APR-2037", "COP");

		lh.addStaticHoliday ("01-MAY-2037", "COP");

		lh.addStaticHoliday ("18-MAY-2037", "COP");

		lh.addStaticHoliday ("08-JUN-2037", "COP");

		lh.addStaticHoliday ("15-JUN-2037", "COP");

		lh.addStaticHoliday ("29-JUN-2037", "COP");

		lh.addStaticHoliday ("20-JUL-2037", "COP");

		lh.addStaticHoliday ("07-AUG-2037", "COP");

		lh.addStaticHoliday ("17-AUG-2037", "COP");

		lh.addStaticHoliday ("12-OCT-2037", "COP");

		lh.addStaticHoliday ("02-NOV-2037", "COP");

		lh.addStaticHoliday ("16-NOV-2037", "COP");

		lh.addStaticHoliday ("08-DEC-2037", "COP");

		lh.addStaticHoliday ("25-DEC-2037", "COP");

		lh.addStaticHoliday ("01-JAN-2038", "COP");

		lh.addStaticHoliday ("11-JAN-2038", "COP");

		lh.addStaticHoliday ("22-MAR-2038", "COP");

		lh.addStaticHoliday ("22-APR-2038", "COP");

		lh.addStaticHoliday ("23-APR-2038", "COP");

		lh.addStaticHoliday ("07-JUN-2038", "COP");

		lh.addStaticHoliday ("28-JUN-2038", "COP");

		lh.addStaticHoliday ("05-JUL-2038", "COP");

		lh.addStaticHoliday ("20-JUL-2038", "COP");

		lh.addStaticHoliday ("16-AUG-2038", "COP");

		lh.addStaticHoliday ("18-OCT-2038", "COP");

		lh.addStaticHoliday ("01-NOV-2038", "COP");

		lh.addStaticHoliday ("15-NOV-2038", "COP");

		lh.addStaticHoliday ("08-DEC-2038", "COP");

		lh.addStaticHoliday ("10-JAN-2039", "COP");

		lh.addStaticHoliday ("21-MAR-2039", "COP");

		lh.addStaticHoliday ("07-APR-2039", "COP");

		lh.addStaticHoliday ("08-APR-2039", "COP");

		lh.addStaticHoliday ("23-MAY-2039", "COP");

		lh.addStaticHoliday ("13-JUN-2039", "COP");

		lh.addStaticHoliday ("20-JUN-2039", "COP");

		lh.addStaticHoliday ("04-JUL-2039", "COP");

		lh.addStaticHoliday ("20-JUL-2039", "COP");

		lh.addStaticHoliday ("15-AUG-2039", "COP");

		lh.addStaticHoliday ("17-OCT-2039", "COP");

		lh.addStaticHoliday ("07-NOV-2039", "COP");

		lh.addStaticHoliday ("14-NOV-2039", "COP");

		lh.addStaticHoliday ("08-DEC-2039", "COP");

		lh.addStaticHoliday ("09-JAN-2040", "COP");

		lh.addStaticHoliday ("19-MAR-2040", "COP");

		lh.addStaticHoliday ("29-MAR-2040", "COP");

		lh.addStaticHoliday ("30-MAR-2040", "COP");

		lh.addStaticHoliday ("01-MAY-2040", "COP");

		lh.addStaticHoliday ("14-MAY-2040", "COP");

		lh.addStaticHoliday ("04-JUN-2040", "COP");

		lh.addStaticHoliday ("11-JUN-2040", "COP");

		lh.addStaticHoliday ("02-JUL-2040", "COP");

		lh.addStaticHoliday ("20-JUL-2040", "COP");

		lh.addStaticHoliday ("07-AUG-2040", "COP");

		lh.addStaticHoliday ("20-AUG-2040", "COP");

		lh.addStaticHoliday ("15-OCT-2040", "COP");

		lh.addStaticHoliday ("05-NOV-2040", "COP");

		lh.addStaticHoliday ("12-NOV-2040", "COP");

		lh.addStaticHoliday ("25-DEC-2040", "COP");

		lh.addStaticHoliday ("01-JAN-2041", "COP");

		lh.addStaticHoliday ("07-JAN-2041", "COP");

		lh.addStaticHoliday ("25-MAR-2041", "COP");

		lh.addStaticHoliday ("18-APR-2041", "COP");

		lh.addStaticHoliday ("19-APR-2041", "COP");

		lh.addStaticHoliday ("01-MAY-2041", "COP");

		lh.addStaticHoliday ("03-JUN-2041", "COP");

		lh.addStaticHoliday ("24-JUN-2041", "COP");

		lh.addStaticHoliday ("01-JUL-2041", "COP");

		lh.addStaticHoliday ("07-AUG-2041", "COP");

		lh.addStaticHoliday ("19-AUG-2041", "COP");

		lh.addStaticHoliday ("14-OCT-2041", "COP");

		lh.addStaticHoliday ("04-NOV-2041", "COP");

		lh.addStaticHoliday ("11-NOV-2041", "COP");

		lh.addStaticHoliday ("25-DEC-2041", "COP");

		lh.addStaticHoliday ("01-JAN-2042", "COP");

		lh.addStaticHoliday ("06-JAN-2042", "COP");

		lh.addStaticHoliday ("24-MAR-2042", "COP");

		lh.addStaticHoliday ("03-APR-2042", "COP");

		lh.addStaticHoliday ("04-APR-2042", "COP");

		lh.addStaticHoliday ("01-MAY-2042", "COP");

		lh.addStaticHoliday ("19-MAY-2042", "COP");

		lh.addStaticHoliday ("09-JUN-2042", "COP");

		lh.addStaticHoliday ("16-JUN-2042", "COP");

		lh.addStaticHoliday ("30-JUN-2042", "COP");

		lh.addStaticHoliday ("07-AUG-2042", "COP");

		lh.addStaticHoliday ("18-AUG-2042", "COP");

		lh.addStaticHoliday ("13-OCT-2042", "COP");

		lh.addStaticHoliday ("03-NOV-2042", "COP");

		lh.addStaticHoliday ("17-NOV-2042", "COP");

		lh.addStaticHoliday ("08-DEC-2042", "COP");

		lh.addStaticHoliday ("25-DEC-2042", "COP");

		lh.addStaticHoliday ("01-JAN-2043", "COP");

		lh.addStaticHoliday ("12-JAN-2043", "COP");

		lh.addStaticHoliday ("23-MAR-2043", "COP");

		lh.addStaticHoliday ("26-MAR-2043", "COP");

		lh.addStaticHoliday ("27-MAR-2043", "COP");

		lh.addStaticHoliday ("01-MAY-2043", "COP");

		lh.addStaticHoliday ("11-MAY-2043", "COP");

		lh.addStaticHoliday ("01-JUN-2043", "COP");

		lh.addStaticHoliday ("08-JUN-2043", "COP");

		lh.addStaticHoliday ("29-JUN-2043", "COP");

		lh.addStaticHoliday ("20-JUL-2043", "COP");

		lh.addStaticHoliday ("07-AUG-2043", "COP");

		lh.addStaticHoliday ("17-AUG-2043", "COP");

		lh.addStaticHoliday ("12-OCT-2043", "COP");

		lh.addStaticHoliday ("02-NOV-2043", "COP");

		lh.addStaticHoliday ("16-NOV-2043", "COP");

		lh.addStaticHoliday ("08-DEC-2043", "COP");

		lh.addStaticHoliday ("25-DEC-2043", "COP");

		lh.addStaticHoliday ("01-JAN-2044", "COP");

		lh.addStaticHoliday ("11-JAN-2044", "COP");

		lh.addStaticHoliday ("21-MAR-2044", "COP");

		lh.addStaticHoliday ("14-APR-2044", "COP");

		lh.addStaticHoliday ("15-APR-2044", "COP");

		lh.addStaticHoliday ("30-MAY-2044", "COP");

		lh.addStaticHoliday ("20-JUN-2044", "COP");

		lh.addStaticHoliday ("27-JUN-2044", "COP");

		lh.addStaticHoliday ("04-JUL-2044", "COP");

		lh.addStaticHoliday ("20-JUL-2044", "COP");

		lh.addStaticHoliday ("15-AUG-2044", "COP");

		lh.addStaticHoliday ("17-OCT-2044", "COP");

		lh.addStaticHoliday ("07-NOV-2044", "COP");

		lh.addStaticHoliday ("14-NOV-2044", "COP");

		lh.addStaticHoliday ("08-DEC-2044", "COP");

		lh.addStaticHoliday ("09-JAN-2045", "COP");

		lh.addStaticHoliday ("20-MAR-2045", "COP");

		lh.addStaticHoliday ("06-APR-2045", "COP");

		lh.addStaticHoliday ("07-APR-2045", "COP");

		lh.addStaticHoliday ("01-MAY-2045", "COP");

		lh.addStaticHoliday ("22-MAY-2045", "COP");

		lh.addStaticHoliday ("12-JUN-2045", "COP");

		lh.addStaticHoliday ("19-JUN-2045", "COP");

		lh.addStaticHoliday ("03-JUL-2045", "COP");

		lh.addStaticHoliday ("20-JUL-2045", "COP");

		lh.addStaticHoliday ("07-AUG-2045", "COP");

		lh.addStaticHoliday ("21-AUG-2045", "COP");

		lh.addStaticHoliday ("16-OCT-2045", "COP");

		lh.addStaticHoliday ("06-NOV-2045", "COP");

		lh.addStaticHoliday ("13-NOV-2045", "COP");

		lh.addStaticHoliday ("08-DEC-2045", "COP");

		lh.addStaticHoliday ("25-DEC-2045", "COP");

		lh.addStaticHoliday ("01-JAN-2046", "COP");

		lh.addStaticHoliday ("08-JAN-2046", "COP");

		lh.addStaticHoliday ("19-MAR-2046", "COP");

		lh.addStaticHoliday ("22-MAR-2046", "COP");

		lh.addStaticHoliday ("23-MAR-2046", "COP");

		lh.addStaticHoliday ("01-MAY-2046", "COP");

		lh.addStaticHoliday ("07-MAY-2046", "COP");

		lh.addStaticHoliday ("28-MAY-2046", "COP");

		lh.addStaticHoliday ("04-JUN-2046", "COP");

		lh.addStaticHoliday ("02-JUL-2046", "COP");

		lh.addStaticHoliday ("20-JUL-2046", "COP");

		lh.addStaticHoliday ("07-AUG-2046", "COP");

		lh.addStaticHoliday ("20-AUG-2046", "COP");

		lh.addStaticHoliday ("15-OCT-2046", "COP");

		lh.addStaticHoliday ("05-NOV-2046", "COP");

		lh.addStaticHoliday ("12-NOV-2046", "COP");

		lh.addStaticHoliday ("25-DEC-2046", "COP");

		lh.addStaticHoliday ("01-JAN-2047", "COP");

		lh.addStaticHoliday ("07-JAN-2047", "COP");

		lh.addStaticHoliday ("25-MAR-2047", "COP");

		lh.addStaticHoliday ("11-APR-2047", "COP");

		lh.addStaticHoliday ("12-APR-2047", "COP");

		lh.addStaticHoliday ("01-MAY-2047", "COP");

		lh.addStaticHoliday ("27-MAY-2047", "COP");

		lh.addStaticHoliday ("17-JUN-2047", "COP");

		lh.addStaticHoliday ("24-JUN-2047", "COP");

		lh.addStaticHoliday ("01-JUL-2047", "COP");

		lh.addStaticHoliday ("07-AUG-2047", "COP");

		lh.addStaticHoliday ("19-AUG-2047", "COP");

		lh.addStaticHoliday ("14-OCT-2047", "COP");

		lh.addStaticHoliday ("04-NOV-2047", "COP");

		lh.addStaticHoliday ("11-NOV-2047", "COP");

		lh.addStaticHoliday ("25-DEC-2047", "COP");

		lh.addStaticHoliday ("01-JAN-2048", "COP");

		lh.addStaticHoliday ("06-JAN-2048", "COP");

		lh.addStaticHoliday ("23-MAR-2048", "COP");

		lh.addStaticHoliday ("02-APR-2048", "COP");

		lh.addStaticHoliday ("03-APR-2048", "COP");

		lh.addStaticHoliday ("01-MAY-2048", "COP");

		lh.addStaticHoliday ("18-MAY-2048", "COP");

		lh.addStaticHoliday ("08-JUN-2048", "COP");

		lh.addStaticHoliday ("15-JUN-2048", "COP");

		lh.addStaticHoliday ("29-JUN-2048", "COP");

		lh.addStaticHoliday ("20-JUL-2048", "COP");

		lh.addStaticHoliday ("07-AUG-2048", "COP");

		lh.addStaticHoliday ("17-AUG-2048", "COP");

		lh.addStaticHoliday ("12-OCT-2048", "COP");

		lh.addStaticHoliday ("02-NOV-2048", "COP");

		lh.addStaticHoliday ("16-NOV-2048", "COP");

		lh.addStaticHoliday ("08-DEC-2048", "COP");

		lh.addStaticHoliday ("25-DEC-2048", "COP");

		lh.addStaticHoliday ("01-JAN-2049", "COP");

		lh.addStaticHoliday ("11-JAN-2049", "COP");

		lh.addStaticHoliday ("22-MAR-2049", "COP");

		lh.addStaticHoliday ("15-APR-2049", "COP");

		lh.addStaticHoliday ("16-APR-2049", "COP");

		lh.addStaticHoliday ("31-MAY-2049", "COP");

		lh.addStaticHoliday ("21-JUN-2049", "COP");

		lh.addStaticHoliday ("28-JUN-2049", "COP");

		lh.addStaticHoliday ("05-JUL-2049", "COP");

		lh.addStaticHoliday ("20-JUL-2049", "COP");

		lh.addStaticHoliday ("16-AUG-2049", "COP");

		lh.addStaticHoliday ("18-OCT-2049", "COP");

		lh.addStaticHoliday ("01-NOV-2049", "COP");

		lh.addStaticHoliday ("15-NOV-2049", "COP");

		lh.addStaticHoliday ("08-DEC-2049", "COP");

		lh.addStaticHoliday ("10-JAN-2050", "COP");

		lh.addStaticHoliday ("21-MAR-2050", "COP");

		lh.addStaticHoliday ("07-APR-2050", "COP");

		lh.addStaticHoliday ("08-APR-2050", "COP");

		lh.addStaticHoliday ("23-MAY-2050", "COP");

		lh.addStaticHoliday ("13-JUN-2050", "COP");

		lh.addStaticHoliday ("20-JUN-2050", "COP");

		lh.addStaticHoliday ("04-JUL-2050", "COP");

		lh.addStaticHoliday ("20-JUL-2050", "COP");

		lh.addStaticHoliday ("15-AUG-2050", "COP");

		lh.addStaticHoliday ("17-OCT-2050", "COP");

		lh.addStaticHoliday ("07-NOV-2050", "COP");

		lh.addStaticHoliday ("14-NOV-2050", "COP");

		lh.addStaticHoliday ("08-DEC-2050", "COP");

		lh.addStaticHoliday ("09-JAN-2051", "COP");

		lh.addStaticHoliday ("20-MAR-2051", "COP");

		lh.addStaticHoliday ("30-MAR-2051", "COP");

		lh.addStaticHoliday ("31-MAR-2051", "COP");

		lh.addStaticHoliday ("01-MAY-2051", "COP");

		lh.addStaticHoliday ("15-MAY-2051", "COP");

		lh.addStaticHoliday ("05-JUN-2051", "COP");

		lh.addStaticHoliday ("12-JUN-2051", "COP");

		lh.addStaticHoliday ("03-JUL-2051", "COP");

		lh.addStaticHoliday ("20-JUL-2051", "COP");

		lh.addStaticHoliday ("07-AUG-2051", "COP");

		lh.addStaticHoliday ("21-AUG-2051", "COP");

		lh.addStaticHoliday ("16-OCT-2051", "COP");

		lh.addStaticHoliday ("06-NOV-2051", "COP");

		lh.addStaticHoliday ("13-NOV-2051", "COP");

		lh.addStaticHoliday ("08-DEC-2051", "COP");

		lh.addStaticHoliday ("25-DEC-2051", "COP");

		lh.addStaticHoliday ("01-JAN-2052", "COP");

		lh.addStaticHoliday ("08-JAN-2052", "COP");

		lh.addStaticHoliday ("25-MAR-2052", "COP");

		lh.addStaticHoliday ("18-APR-2052", "COP");

		lh.addStaticHoliday ("19-APR-2052", "COP");

		lh.addStaticHoliday ("01-MAY-2052", "COP");

		lh.addStaticHoliday ("03-JUN-2052", "COP");

		lh.addStaticHoliday ("24-JUN-2052", "COP");

		lh.addStaticHoliday ("01-JUL-2052", "COP");

		lh.addStaticHoliday ("07-AUG-2052", "COP");

		lh.addStaticHoliday ("19-AUG-2052", "COP");

		lh.addStaticHoliday ("14-OCT-2052", "COP");

		lh.addStaticHoliday ("04-NOV-2052", "COP");

		lh.addStaticHoliday ("11-NOV-2052", "COP");

		lh.addStaticHoliday ("25-DEC-2052", "COP");

		lh.addStaticHoliday ("01-JAN-2053", "COP");

		lh.addStaticHoliday ("06-JAN-2053", "COP");

		lh.addStaticHoliday ("24-MAR-2053", "COP");

		lh.addStaticHoliday ("03-APR-2053", "COP");

		lh.addStaticHoliday ("04-APR-2053", "COP");

		lh.addStaticHoliday ("01-MAY-2053", "COP");

		lh.addStaticHoliday ("19-MAY-2053", "COP");

		lh.addStaticHoliday ("09-JUN-2053", "COP");

		lh.addStaticHoliday ("16-JUN-2053", "COP");

		lh.addStaticHoliday ("30-JUN-2053", "COP");

		lh.addStaticHoliday ("07-AUG-2053", "COP");

		lh.addStaticHoliday ("18-AUG-2053", "COP");

		lh.addStaticHoliday ("13-OCT-2053", "COP");

		lh.addStaticHoliday ("03-NOV-2053", "COP");

		lh.addStaticHoliday ("17-NOV-2053", "COP");

		lh.addStaticHoliday ("08-DEC-2053", "COP");

		lh.addStaticHoliday ("25-DEC-2053", "COP");

		lh.addStaticHoliday ("01-JAN-2054", "COP");

		lh.addStaticHoliday ("12-JAN-2054", "COP");

		lh.addStaticHoliday ("23-MAR-2054", "COP");

		lh.addStaticHoliday ("26-MAR-2054", "COP");

		lh.addStaticHoliday ("27-MAR-2054", "COP");

		lh.addStaticHoliday ("01-MAY-2054", "COP");

		lh.addStaticHoliday ("11-MAY-2054", "COP");

		lh.addStaticHoliday ("01-JUN-2054", "COP");

		lh.addStaticHoliday ("08-JUN-2054", "COP");

		lh.addStaticHoliday ("29-JUN-2054", "COP");

		lh.addStaticHoliday ("20-JUL-2054", "COP");

		lh.addStaticHoliday ("07-AUG-2054", "COP");

		lh.addStaticHoliday ("17-AUG-2054", "COP");

		lh.addStaticHoliday ("12-OCT-2054", "COP");

		lh.addStaticHoliday ("02-NOV-2054", "COP");

		lh.addStaticHoliday ("16-NOV-2054", "COP");

		lh.addStaticHoliday ("08-DEC-2054", "COP");

		lh.addStaticHoliday ("25-DEC-2054", "COP");

		lh.addStaticHoliday ("01-JAN-2055", "COP");

		lh.addStaticHoliday ("11-JAN-2055", "COP");

		lh.addStaticHoliday ("22-MAR-2055", "COP");

		lh.addStaticHoliday ("15-APR-2055", "COP");

		lh.addStaticHoliday ("16-APR-2055", "COP");

		lh.addStaticHoliday ("31-MAY-2055", "COP");

		lh.addStaticHoliday ("21-JUN-2055", "COP");

		lh.addStaticHoliday ("28-JUN-2055", "COP");

		lh.addStaticHoliday ("05-JUL-2055", "COP");

		lh.addStaticHoliday ("20-JUL-2055", "COP");

		lh.addStaticHoliday ("16-AUG-2055", "COP");

		lh.addStaticHoliday ("18-OCT-2055", "COP");

		lh.addStaticHoliday ("01-NOV-2055", "COP");

		lh.addStaticHoliday ("15-NOV-2055", "COP");

		lh.addStaticHoliday ("08-DEC-2055", "COP");

		lh.addStaticHoliday ("10-JAN-2056", "COP");

		lh.addStaticHoliday ("20-MAR-2056", "COP");

		lh.addStaticHoliday ("30-MAR-2056", "COP");

		lh.addStaticHoliday ("31-MAR-2056", "COP");

		lh.addStaticHoliday ("01-MAY-2056", "COP");

		lh.addStaticHoliday ("15-MAY-2056", "COP");

		lh.addStaticHoliday ("05-JUN-2056", "COP");

		lh.addStaticHoliday ("12-JUN-2056", "COP");

		lh.addStaticHoliday ("03-JUL-2056", "COP");

		lh.addStaticHoliday ("20-JUL-2056", "COP");

		lh.addStaticHoliday ("07-AUG-2056", "COP");

		lh.addStaticHoliday ("21-AUG-2056", "COP");

		lh.addStaticHoliday ("16-OCT-2056", "COP");

		lh.addStaticHoliday ("06-NOV-2056", "COP");

		lh.addStaticHoliday ("13-NOV-2056", "COP");

		lh.addStaticHoliday ("08-DEC-2056", "COP");

		lh.addStaticHoliday ("25-DEC-2056", "COP");

		lh.addStaticHoliday ("01-JAN-2057", "COP");

		lh.addStaticHoliday ("08-JAN-2057", "COP");

		lh.addStaticHoliday ("19-MAR-2057", "COP");

		lh.addStaticHoliday ("19-APR-2057", "COP");

		lh.addStaticHoliday ("20-APR-2057", "COP");

		lh.addStaticHoliday ("01-MAY-2057", "COP");

		lh.addStaticHoliday ("04-JUN-2057", "COP");

		lh.addStaticHoliday ("25-JUN-2057", "COP");

		lh.addStaticHoliday ("02-JUL-2057", "COP");

		lh.addStaticHoliday ("20-JUL-2057", "COP");

		lh.addStaticHoliday ("07-AUG-2057", "COP");

		lh.addStaticHoliday ("20-AUG-2057", "COP");

		lh.addStaticHoliday ("15-OCT-2057", "COP");

		lh.addStaticHoliday ("05-NOV-2057", "COP");

		lh.addStaticHoliday ("12-NOV-2057", "COP");

		lh.addStaticHoliday ("25-DEC-2057", "COP");

		lh.addStaticHoliday ("01-JAN-2058", "COP");

		lh.addStaticHoliday ("07-JAN-2058", "COP");

		lh.addStaticHoliday ("25-MAR-2058", "COP");

		lh.addStaticHoliday ("11-APR-2058", "COP");

		lh.addStaticHoliday ("12-APR-2058", "COP");

		lh.addStaticHoliday ("01-MAY-2058", "COP");

		lh.addStaticHoliday ("27-MAY-2058", "COP");

		lh.addStaticHoliday ("17-JUN-2058", "COP");

		lh.addStaticHoliday ("24-JUN-2058", "COP");

		lh.addStaticHoliday ("01-JUL-2058", "COP");

		lh.addStaticHoliday ("07-AUG-2058", "COP");

		lh.addStaticHoliday ("19-AUG-2058", "COP");

		lh.addStaticHoliday ("14-OCT-2058", "COP");

		lh.addStaticHoliday ("04-NOV-2058", "COP");

		lh.addStaticHoliday ("11-NOV-2058", "COP");

		lh.addStaticHoliday ("25-DEC-2058", "COP");

		lh.addStaticHoliday ("01-JAN-2059", "COP");

		lh.addStaticHoliday ("06-JAN-2059", "COP");

		lh.addStaticHoliday ("24-MAR-2059", "COP");

		lh.addStaticHoliday ("27-MAR-2059", "COP");

		lh.addStaticHoliday ("28-MAR-2059", "COP");

		lh.addStaticHoliday ("01-MAY-2059", "COP");

		lh.addStaticHoliday ("12-MAY-2059", "COP");

		lh.addStaticHoliday ("02-JUN-2059", "COP");

		lh.addStaticHoliday ("09-JUN-2059", "COP");

		lh.addStaticHoliday ("30-JUN-2059", "COP");

		lh.addStaticHoliday ("07-AUG-2059", "COP");

		lh.addStaticHoliday ("18-AUG-2059", "COP");

		lh.addStaticHoliday ("13-OCT-2059", "COP");

		lh.addStaticHoliday ("03-NOV-2059", "COP");

		lh.addStaticHoliday ("17-NOV-2059", "COP");

		lh.addStaticHoliday ("08-DEC-2059", "COP");

		lh.addStaticHoliday ("25-DEC-2059", "COP");

		lh.addStaticHoliday ("01-JAN-2060", "COP");

		lh.addStaticHoliday ("12-JAN-2060", "COP");

		lh.addStaticHoliday ("22-MAR-2060", "COP");

		lh.addStaticHoliday ("15-APR-2060", "COP");

		lh.addStaticHoliday ("16-APR-2060", "COP");

		lh.addStaticHoliday ("31-MAY-2060", "COP");

		lh.addStaticHoliday ("21-JUN-2060", "COP");

		lh.addStaticHoliday ("28-JUN-2060", "COP");

		lh.addStaticHoliday ("05-JUL-2060", "COP");

		lh.addStaticHoliday ("20-JUL-2060", "COP");

		lh.addStaticHoliday ("16-AUG-2060", "COP");

		lh.addStaticHoliday ("18-OCT-2060", "COP");

		lh.addStaticHoliday ("01-NOV-2060", "COP");

		lh.addStaticHoliday ("15-NOV-2060", "COP");

		lh.addStaticHoliday ("08-DEC-2060", "COP");

		lh.addStandardWeekend();

		return lh;
	}
}
