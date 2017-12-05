
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

public class VEBHoliday implements org.drip.analytics.holset.LocationHoliday {
	public VEBHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "VEB";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("05-JAN-1998", "Epiphany Observed");

		lh.addStaticHoliday ("23-FEB-1998", "Carnival Monday");

		lh.addStaticHoliday ("24-FEB-1998", "Carnival Tuesday");

		lh.addStaticHoliday ("19-MAR-1998", "St. Josephs Day");

		lh.addStaticHoliday ("09-APR-1998", "Holy Thursday");

		lh.addStaticHoliday ("10-APR-1998", "Good Friday");

		lh.addStaticHoliday ("01-MAY-1998", "Labour Day");

		lh.addStaticHoliday ("25-MAY-1998", "Ascension Day");

		lh.addStaticHoliday ("15-JUN-1998", "Corpus Christi");

		lh.addStaticHoliday ("24-JUN-1998", "Battle of Carabobo Day");

		lh.addStaticHoliday ("29-JUN-1998", "Saints Peter and Paul Day");

		lh.addStaticHoliday ("24-JUL-1998", "Bolivars Birthday");

		lh.addStaticHoliday ("12-OCT-1998", "Day of the Race");

		lh.addStaticHoliday ("07-DEC-1998", "Immaculate Conception Observed");

		lh.addStaticHoliday ("25-DEC-1998", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("11-JAN-1999", "Epiphany Observed");

		lh.addStaticHoliday ("15-FEB-1999", "Carnival Monday");

		lh.addStaticHoliday ("16-FEB-1999", "Carnival Tuesday");

		lh.addStaticHoliday ("19-MAR-1999", "St. Josephs Day");

		lh.addStaticHoliday ("01-APR-1999", "Holy Thursday");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("19-APR-1999", "Constitution Day");

		lh.addStaticHoliday ("17-MAY-1999", "Ascension Day");

		lh.addStaticHoliday ("07-JUN-1999", "Corpus Christi");

		lh.addStaticHoliday ("24-JUN-1999", "Battle of Carabobo Day");

		lh.addStaticHoliday ("28-JUN-1999", "Saints Peter and Paul Day Observed");

		lh.addStaticHoliday ("05-JUL-1999", "Independence Day");

		lh.addStaticHoliday ("12-OCT-1999", "Day of the Race");

		lh.addStaticHoliday ("01-NOV-1999", "All Saints Day");

		lh.addStaticHoliday ("06-DEC-1999", "Immaculate Conception Observed");

		lh.addStaticHoliday ("10-JAN-2000", "Epiphany Observed");

		lh.addStaticHoliday ("06-MAR-2000", "Carnival Monday");

		lh.addStaticHoliday ("07-MAR-2000", "Carnival Tuesday");

		lh.addStaticHoliday ("19-APR-2000", "Constitution Day");

		lh.addStaticHoliday ("20-APR-2000", "Holy Thursday");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2000", "Labour Day");

		lh.addStaticHoliday ("05-JUN-2000", "Ascension Day");

		lh.addStaticHoliday ("26-JUN-2000", "Corpus Christi");

		lh.addStaticHoliday ("05-JUL-2000", "Independence Day");

		lh.addStaticHoliday ("24-JUL-2000", "Bolivars Birthday");

		lh.addStaticHoliday ("14-AUG-2000", "Assumption Day Observed");

		lh.addStaticHoliday ("12-OCT-2000", "Day of the Race");

		lh.addStaticHoliday ("30-OCT-2000", "All Saints Day Observed");

		lh.addStaticHoliday ("11-DEC-2000", "Immaculate Conception Observed");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("26-FEB-2001", "Carnival Monday");

		lh.addStaticHoliday ("27-FEB-2001", "Carnival Tuesday");

		lh.addStaticHoliday ("19-MAR-2001", "St. Josephs Day");

		lh.addStaticHoliday ("12-APR-2001", "Holy Thursday");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("19-APR-2001", "Constitution Day");

		lh.addStaticHoliday ("01-MAY-2001", "Labour Day");

		lh.addStaticHoliday ("28-MAY-2001", "Ascension Day");

		lh.addStaticHoliday ("18-JUN-2001", "Corpus Christi");

		lh.addStaticHoliday ("02-JUL-2001", "Saints Peter and Paul Day Observed");

		lh.addStaticHoliday ("05-JUL-2001", "Independence Day");

		lh.addStaticHoliday ("24-JUL-2001", "Bolivars Birthday");

		lh.addStaticHoliday ("13-AUG-2001", "Assumption Day Observed");

		lh.addStaticHoliday ("12-OCT-2001", "Day of the Race");

		lh.addStaticHoliday ("05-NOV-2001", "All Saints Day Observed");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2002", "Carnival Monday");

		lh.addStaticHoliday ("12-FEB-2002", "Carnival Tuesday");

		lh.addStaticHoliday ("19-MAR-2002", "St. Josephs Day");

		lh.addStaticHoliday ("28-MAR-2002", "Holy Thursday");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("19-APR-2002", "Constitution Day");

		lh.addStaticHoliday ("01-MAY-2002", "Labour Day");

		lh.addStaticHoliday ("13-MAY-2002", "Ascension Day");

		lh.addStaticHoliday ("03-JUN-2002", "Corpus Christi");

		lh.addStaticHoliday ("24-JUN-2002", "Battle of Carabobo Day");

		lh.addStaticHoliday ("05-JUL-2002", "Independence Day");

		lh.addStaticHoliday ("24-JUL-2002", "Bolivars Birthday");

		lh.addStaticHoliday ("19-AUG-2002", "Assumption Day Observed");

		lh.addStaticHoliday ("04-NOV-2002", "All Saints Day Observed");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2003", "Epiphany");

		lh.addStaticHoliday ("03-MAR-2003", "Carnival Monday");

		lh.addStaticHoliday ("04-MAR-2003", "Carnival Tuesday");

		lh.addStaticHoliday ("19-MAR-2003", "St. Josephs Day");

		lh.addStaticHoliday ("17-APR-2003", "Holy Thursday");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2003", "Labour Day");

		lh.addStaticHoliday ("02-JUN-2003", "Ascension Day");

		lh.addStaticHoliday ("23-JUN-2003", "Corpus Christi");

		lh.addStaticHoliday ("24-JUN-2003", "Battle of Carabobo Day");

		lh.addStaticHoliday ("24-JUL-2003", "Bolivars Birthday");

		lh.addStaticHoliday ("18-AUG-2003", "Assumption Day Observed");

		lh.addStaticHoliday ("08-DEC-2003", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("05-JAN-2004", "Epiphany Observed");

		lh.addStaticHoliday ("23-FEB-2004", "Carnival Monday");

		lh.addStaticHoliday ("24-FEB-2004", "Carnival Tuesday");

		lh.addStaticHoliday ("19-MAR-2004", "St. Josephs Day");

		lh.addStaticHoliday ("08-APR-2004", "Holy Thursday");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("19-APR-2004", "Constitution Day");

		lh.addStaticHoliday ("24-MAY-2004", "Ascension Day");

		lh.addStaticHoliday ("14-JUN-2004", "Corpus Christi");

		lh.addStaticHoliday ("24-JUN-2004", "Battle of Carabobo Day");

		lh.addStaticHoliday ("28-JUN-2004", "Saints Peter and Paul Day Observed");

		lh.addStaticHoliday ("05-JUL-2004", "Independence Day");

		lh.addStaticHoliday ("12-OCT-2004", "Day of the Race");

		lh.addStaticHoliday ("01-NOV-2004", "All Saints Day");

		lh.addStaticHoliday ("06-DEC-2004", "Immaculate Conception Observed");

		lh.addStaticHoliday ("10-JAN-2005", "Epiphany Observed");

		lh.addStaticHoliday ("07-FEB-2005", "Carnival Monday");

		lh.addStaticHoliday ("08-FEB-2005", "Carnival Tuesday");

		lh.addStaticHoliday ("24-MAR-2005", "Holy Thursday");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("19-APR-2005", "Constitution Day");

		lh.addStaticHoliday ("09-MAY-2005", "Ascension Day");

		lh.addStaticHoliday ("30-MAY-2005", "Corpus Christi");

		lh.addStaticHoliday ("24-JUN-2005", "Battle of Carabobo Day");

		lh.addStaticHoliday ("27-JUN-2005", "Saints Peter and Paul Day Observed");

		lh.addStaticHoliday ("05-JUL-2005", "Independence Day");

		lh.addStaticHoliday ("15-AUG-2005", "Assumption Day");

		lh.addStaticHoliday ("12-OCT-2005", "Day of the Race");

		lh.addStaticHoliday ("31-OCT-2005", "All Saints Day Observed");

		lh.addStaticHoliday ("12-DEC-2005", "Immaculate Conception Observed");

		lh.addStaticHoliday ("09-JAN-2006", "Epiphany Observed");

		lh.addStaticHoliday ("27-FEB-2006", "Carnival Monday");

		lh.addStaticHoliday ("28-FEB-2006", "Carnival Tuesday");

		lh.addStaticHoliday ("13-APR-2006", "Holy Thursday");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("19-APR-2006", "Constitution Day");

		lh.addStaticHoliday ("01-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("29-MAY-2006", "Ascension Day");

		lh.addStaticHoliday ("19-JUN-2006", "Corpus Christi");

		lh.addStaticHoliday ("26-JUN-2006", "Saints Peter and Paul Day Observed");

		lh.addStaticHoliday ("05-JUL-2006", "Independence Day");

		lh.addStaticHoliday ("24-JUL-2006", "Bolivars Birthday");

		lh.addStaticHoliday ("14-AUG-2006", "Assumption Day Observed");

		lh.addStaticHoliday ("12-OCT-2006", "Day of the Race");

		lh.addStaticHoliday ("30-OCT-2006", "All Saints Day Observed");

		lh.addStaticHoliday ("11-DEC-2006", "Immaculate Conception Observed");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("19-FEB-2007", "Carnival Monday");

		lh.addStaticHoliday ("20-FEB-2007", "Carnival Tuesday");

		lh.addStaticHoliday ("19-MAR-2007", "St. Josephs Day");

		lh.addStaticHoliday ("05-APR-2007", "Holy Thursday");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("19-APR-2007", "Constitution Day");

		lh.addStaticHoliday ("01-MAY-2007", "Labour Day");

		lh.addStaticHoliday ("21-MAY-2007", "Ascension Day");

		lh.addStaticHoliday ("11-JUN-2007", "Corpus Christi");

		lh.addStaticHoliday ("02-JUL-2007", "Saints Peter and Paul Day Observed");

		lh.addStaticHoliday ("05-JUL-2007", "Independence Day");

		lh.addStaticHoliday ("24-JUL-2007", "Bolivars Birthday");

		lh.addStaticHoliday ("13-AUG-2007", "Assumption Day Observed");

		lh.addStaticHoliday ("12-OCT-2007", "Day of the Race");

		lh.addStaticHoliday ("05-NOV-2007", "All Saints Day Observed");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("04-FEB-2008", "Carnival Monday");

		lh.addStaticHoliday ("05-FEB-2008", "Carnival Tuesday");

		lh.addStaticHoliday ("19-MAR-2008", "St. Josephs Day");

		lh.addStaticHoliday ("20-MAR-2008", "Holy Thursday");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2008", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2008", "Ascension Day");

		lh.addStaticHoliday ("26-MAY-2008", "Corpus Christi");

		lh.addStaticHoliday ("24-JUN-2008", "Battle of Carabobo Day");

		lh.addStaticHoliday ("24-JUL-2008", "Bolivars Birthday");

		lh.addStaticHoliday ("18-AUG-2008", "Assumption Day Observed");

		lh.addStaticHoliday ("08-DEC-2008", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("05-JAN-2009", "Epiphany Observed");

		lh.addStaticHoliday ("23-FEB-2009", "Carnival Monday");

		lh.addStaticHoliday ("24-FEB-2009", "Carnival Tuesday");

		lh.addStaticHoliday ("19-MAR-2009", "St. Josephs Day");

		lh.addStaticHoliday ("09-APR-2009", "Holy Thursday");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2009", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2009", "Ascension Day");

		lh.addStaticHoliday ("15-JUN-2009", "Corpus Christi");

		lh.addStaticHoliday ("24-JUN-2009", "Battle of Carabobo Day");

		lh.addStaticHoliday ("29-JUN-2009", "Saints Peter and Paul Day");

		lh.addStaticHoliday ("24-JUL-2009", "Bolivars Birthday");

		lh.addStaticHoliday ("12-OCT-2009", "Day of the Race");

		lh.addStaticHoliday ("07-DEC-2009", "Immaculate Conception Observed");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("11-JAN-2010", "Epiphany Observed");

		lh.addStaticHoliday ("15-FEB-2010", "Carnival Monday");

		lh.addStaticHoliday ("16-FEB-2010", "Carnival Tuesday");

		lh.addStaticHoliday ("19-MAR-2010", "St. Josephs Day");

		lh.addStaticHoliday ("01-APR-2010", "Holy Thursday");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("19-APR-2010", "Constitution Day");

		lh.addStaticHoliday ("17-MAY-2010", "Ascension Day");

		lh.addStaticHoliday ("07-JUN-2010", "Corpus Christi");

		lh.addStaticHoliday ("24-JUN-2010", "Battle of Carabobo Day");

		lh.addStaticHoliday ("28-JUN-2010", "Saints Peter and Paul Day Observed");

		lh.addStaticHoliday ("05-JUL-2010", "Independence Day");

		lh.addStaticHoliday ("12-OCT-2010", "Day of the Race");

		lh.addStaticHoliday ("01-NOV-2010", "All Saints Day");

		lh.addStaticHoliday ("06-DEC-2010", "Immaculate Conception Observed");

		lh.addStaticHoliday ("10-JAN-2011", "VEB");

		lh.addStaticHoliday ("07-MAR-2011", "VEB");

		lh.addStaticHoliday ("08-MAR-2011", "VEB");

		lh.addStaticHoliday ("19-APR-2011", "VEB");

		lh.addStaticHoliday ("21-APR-2011", "VEB");

		lh.addStaticHoliday ("22-APR-2011", "VEB");

		lh.addStaticHoliday ("06-JUN-2011", "VEB");

		lh.addStaticHoliday ("24-JUN-2011", "VEB");

		lh.addStaticHoliday ("27-JUN-2011", "VEB");

		lh.addStaticHoliday ("04-JUL-2011", "VEB");

		lh.addStaticHoliday ("05-JUL-2011", "VEB");

		lh.addStaticHoliday ("15-AUG-2011", "VEB");

		lh.addStaticHoliday ("12-OCT-2011", "VEB");

		lh.addStaticHoliday ("31-OCT-2011", "VEB");

		lh.addStaticHoliday ("12-DEC-2011", "VEB");

		lh.addStaticHoliday ("09-JAN-2012", "VEB");

		lh.addStaticHoliday ("20-FEB-2012", "VEB");

		lh.addStaticHoliday ("21-FEB-2012", "VEB");

		lh.addStaticHoliday ("19-MAR-2012", "VEB");

		lh.addStaticHoliday ("05-APR-2012", "VEB");

		lh.addStaticHoliday ("06-APR-2012", "VEB");

		lh.addStaticHoliday ("19-APR-2012", "VEB");

		lh.addStaticHoliday ("01-MAY-2012", "VEB");

		lh.addStaticHoliday ("21-MAY-2012", "VEB");

		lh.addStaticHoliday ("11-JUN-2012", "VEB");

		lh.addStaticHoliday ("02-JUL-2012", "VEB");

		lh.addStaticHoliday ("05-JUL-2012", "VEB");

		lh.addStaticHoliday ("24-JUL-2012", "VEB");

		lh.addStaticHoliday ("20-AUG-2012", "VEB");

		lh.addStaticHoliday ("12-OCT-2012", "VEB");

		lh.addStaticHoliday ("05-NOV-2012", "VEB");

		lh.addStaticHoliday ("25-DEC-2012", "VEB");

		lh.addStaticHoliday ("01-JAN-2013", "VEB");

		lh.addStaticHoliday ("11-FEB-2013", "VEB");

		lh.addStaticHoliday ("12-FEB-2013", "VEB");

		lh.addStaticHoliday ("19-MAR-2013", "VEB");

		lh.addStaticHoliday ("28-MAR-2013", "VEB");

		lh.addStaticHoliday ("29-MAR-2013", "VEB");

		lh.addStaticHoliday ("19-APR-2013", "VEB");

		lh.addStaticHoliday ("01-MAY-2013", "VEB");

		lh.addStaticHoliday ("13-MAY-2013", "VEB");

		lh.addStaticHoliday ("03-JUN-2013", "VEB");

		lh.addStaticHoliday ("24-JUN-2013", "VEB");

		lh.addStaticHoliday ("05-JUL-2013", "VEB");

		lh.addStaticHoliday ("24-JUL-2013", "VEB");

		lh.addStaticHoliday ("19-AUG-2013", "VEB");

		lh.addStaticHoliday ("04-NOV-2013", "VEB");

		lh.addStaticHoliday ("25-DEC-2013", "VEB");

		lh.addStaticHoliday ("01-JAN-2014", "VEB");

		lh.addStaticHoliday ("06-JAN-2014", "VEB");

		lh.addStaticHoliday ("03-MAR-2014", "VEB");

		lh.addStaticHoliday ("04-MAR-2014", "VEB");

		lh.addStaticHoliday ("19-MAR-2014", "VEB");

		lh.addStaticHoliday ("17-APR-2014", "VEB");

		lh.addStaticHoliday ("18-APR-2014", "VEB");

		lh.addStaticHoliday ("01-MAY-2014", "VEB");

		lh.addStaticHoliday ("02-JUN-2014", "VEB");

		lh.addStaticHoliday ("16-JUN-2014", "VEB");

		lh.addStaticHoliday ("24-JUN-2014", "VEB");

		lh.addStaticHoliday ("24-JUL-2014", "VEB");

		lh.addStaticHoliday ("18-AUG-2014", "VEB");

		lh.addStaticHoliday ("08-DEC-2014", "VEB");

		lh.addStaticHoliday ("25-DEC-2014", "VEB");

		lh.addStaticHoliday ("01-JAN-2015", "VEB");

		lh.addStaticHoliday ("05-JAN-2015", "VEB");

		lh.addStaticHoliday ("16-FEB-2015", "VEB");

		lh.addStaticHoliday ("17-FEB-2015", "VEB");

		lh.addStaticHoliday ("19-MAR-2015", "VEB");

		lh.addStaticHoliday ("02-APR-2015", "VEB");

		lh.addStaticHoliday ("03-APR-2015", "VEB");

		lh.addStaticHoliday ("01-MAY-2015", "VEB");

		lh.addStaticHoliday ("18-MAY-2015", "VEB");

		lh.addStaticHoliday ("08-JUN-2015", "VEB");

		lh.addStaticHoliday ("24-JUN-2015", "VEB");

		lh.addStaticHoliday ("29-JUN-2015", "VEB");

		lh.addStaticHoliday ("24-JUL-2015", "VEB");

		lh.addStaticHoliday ("12-OCT-2015", "VEB");

		lh.addStaticHoliday ("07-DEC-2015", "VEB");

		lh.addStaticHoliday ("25-DEC-2015", "VEB");

		lh.addStaticHoliday ("01-JAN-2016", "VEB");

		lh.addStaticHoliday ("04-JAN-2016", "VEB");

		lh.addStaticHoliday ("08-FEB-2016", "VEB");

		lh.addStaticHoliday ("09-FEB-2016", "VEB");

		lh.addStaticHoliday ("24-MAR-2016", "VEB");

		lh.addStaticHoliday ("25-MAR-2016", "VEB");

		lh.addStaticHoliday ("19-APR-2016", "VEB");

		lh.addStaticHoliday ("09-MAY-2016", "VEB");

		lh.addStaticHoliday ("30-MAY-2016", "VEB");

		lh.addStaticHoliday ("24-JUN-2016", "VEB");

		lh.addStaticHoliday ("27-JUN-2016", "VEB");

		lh.addStaticHoliday ("05-JUL-2016", "VEB");

		lh.addStaticHoliday ("15-AUG-2016", "VEB");

		lh.addStaticHoliday ("12-OCT-2016", "VEB");

		lh.addStaticHoliday ("31-OCT-2016", "VEB");

		lh.addStaticHoliday ("12-DEC-2016", "VEB");

		lh.addStaticHoliday ("09-JAN-2017", "VEB");

		lh.addStaticHoliday ("27-FEB-2017", "VEB");

		lh.addStaticHoliday ("28-FEB-2017", "VEB");

		lh.addStaticHoliday ("13-APR-2017", "VEB");

		lh.addStaticHoliday ("14-APR-2017", "VEB");

		lh.addStaticHoliday ("19-APR-2017", "VEB");

		lh.addStaticHoliday ("01-MAY-2017", "VEB");

		lh.addStaticHoliday ("29-MAY-2017", "VEB");

		lh.addStaticHoliday ("19-JUN-2017", "VEB");

		lh.addStaticHoliday ("03-JUL-2017", "VEB");

		lh.addStaticHoliday ("05-JUL-2017", "VEB");

		lh.addStaticHoliday ("24-JUL-2017", "VEB");

		lh.addStaticHoliday ("14-AUG-2017", "VEB");

		lh.addStaticHoliday ("12-OCT-2017", "VEB");

		lh.addStaticHoliday ("06-NOV-2017", "VEB");

		lh.addStaticHoliday ("11-DEC-2017", "VEB");

		lh.addStaticHoliday ("25-DEC-2017", "VEB");

		lh.addStaticHoliday ("01-JAN-2018", "VEB");

		lh.addStaticHoliday ("12-FEB-2018", "VEB");

		lh.addStaticHoliday ("13-FEB-2018", "VEB");

		lh.addStaticHoliday ("19-MAR-2018", "VEB");

		lh.addStaticHoliday ("29-MAR-2018", "VEB");

		lh.addStaticHoliday ("30-MAR-2018", "VEB");

		lh.addStaticHoliday ("19-APR-2018", "VEB");

		lh.addStaticHoliday ("01-MAY-2018", "VEB");

		lh.addStaticHoliday ("14-MAY-2018", "VEB");

		lh.addStaticHoliday ("04-JUN-2018", "VEB");

		lh.addStaticHoliday ("02-JUL-2018", "VEB");

		lh.addStaticHoliday ("05-JUL-2018", "VEB");

		lh.addStaticHoliday ("24-JUL-2018", "VEB");

		lh.addStaticHoliday ("20-AUG-2018", "VEB");

		lh.addStaticHoliday ("12-OCT-2018", "VEB");

		lh.addStaticHoliday ("05-NOV-2018", "VEB");

		lh.addStaticHoliday ("25-DEC-2018", "VEB");

		lh.addStaticHoliday ("01-JAN-2019", "VEB");

		lh.addStaticHoliday ("04-MAR-2019", "VEB");

		lh.addStaticHoliday ("05-MAR-2019", "VEB");

		lh.addStaticHoliday ("19-MAR-2019", "VEB");

		lh.addStaticHoliday ("18-APR-2019", "VEB");

		lh.addStaticHoliday ("19-APR-2019", "VEB");

		lh.addStaticHoliday ("01-MAY-2019", "VEB");

		lh.addStaticHoliday ("03-JUN-2019", "VEB");

		lh.addStaticHoliday ("24-JUN-2019", "VEB");

		lh.addStaticHoliday ("05-JUL-2019", "VEB");

		lh.addStaticHoliday ("24-JUL-2019", "VEB");

		lh.addStaticHoliday ("19-AUG-2019", "VEB");

		lh.addStaticHoliday ("04-NOV-2019", "VEB");

		lh.addStaticHoliday ("25-DEC-2019", "VEB");

		lh.addStaticHoliday ("01-JAN-2020", "VEB");

		lh.addStaticHoliday ("06-JAN-2020", "VEB");

		lh.addStaticHoliday ("24-FEB-2020", "VEB");

		lh.addStaticHoliday ("25-FEB-2020", "VEB");

		lh.addStaticHoliday ("19-MAR-2020", "VEB");

		lh.addStaticHoliday ("09-APR-2020", "VEB");

		lh.addStaticHoliday ("10-APR-2020", "VEB");

		lh.addStaticHoliday ("01-MAY-2020", "VEB");

		lh.addStaticHoliday ("25-MAY-2020", "VEB");

		lh.addStaticHoliday ("15-JUN-2020", "VEB");

		lh.addStaticHoliday ("24-JUN-2020", "VEB");

		lh.addStaticHoliday ("29-JUN-2020", "VEB");

		lh.addStaticHoliday ("24-JUL-2020", "VEB");

		lh.addStaticHoliday ("12-OCT-2020", "VEB");

		lh.addStaticHoliday ("07-DEC-2020", "VEB");

		lh.addStaticHoliday ("25-DEC-2020", "VEB");

		lh.addStaticHoliday ("01-JAN-2021", "VEB");

		lh.addStaticHoliday ("04-JAN-2021", "VEB");

		lh.addStaticHoliday ("15-FEB-2021", "VEB");

		lh.addStaticHoliday ("16-FEB-2021", "VEB");

		lh.addStaticHoliday ("19-MAR-2021", "VEB");

		lh.addStaticHoliday ("01-APR-2021", "VEB");

		lh.addStaticHoliday ("02-APR-2021", "VEB");

		lh.addStaticHoliday ("19-APR-2021", "VEB");

		lh.addStaticHoliday ("17-MAY-2021", "VEB");

		lh.addStaticHoliday ("07-JUN-2021", "VEB");

		lh.addStaticHoliday ("24-JUN-2021", "VEB");

		lh.addStaticHoliday ("28-JUN-2021", "VEB");

		lh.addStaticHoliday ("05-JUL-2021", "VEB");

		lh.addStaticHoliday ("12-OCT-2021", "VEB");

		lh.addStaticHoliday ("01-NOV-2021", "VEB");

		lh.addStaticHoliday ("06-DEC-2021", "VEB");

		lh.addStaticHoliday ("10-JAN-2022", "VEB");

		lh.addStaticHoliday ("28-FEB-2022", "VEB");

		lh.addStaticHoliday ("01-MAR-2022", "VEB");

		lh.addStaticHoliday ("14-APR-2022", "VEB");

		lh.addStaticHoliday ("15-APR-2022", "VEB");

		lh.addStaticHoliday ("19-APR-2022", "VEB");

		lh.addStaticHoliday ("30-MAY-2022", "VEB");

		lh.addStaticHoliday ("20-JUN-2022", "VEB");

		lh.addStaticHoliday ("24-JUN-2022", "VEB");

		lh.addStaticHoliday ("27-JUN-2022", "VEB");

		lh.addStaticHoliday ("05-JUL-2022", "VEB");

		lh.addStaticHoliday ("15-AUG-2022", "VEB");

		lh.addStaticHoliday ("12-OCT-2022", "VEB");

		lh.addStaticHoliday ("31-OCT-2022", "VEB");

		lh.addStaticHoliday ("12-DEC-2022", "VEB");

		lh.addStaticHoliday ("09-JAN-2023", "VEB");

		lh.addStaticHoliday ("20-FEB-2023", "VEB");

		lh.addStaticHoliday ("21-FEB-2023", "VEB");

		lh.addStaticHoliday ("06-APR-2023", "VEB");

		lh.addStaticHoliday ("07-APR-2023", "VEB");

		lh.addStaticHoliday ("19-APR-2023", "VEB");

		lh.addStaticHoliday ("01-MAY-2023", "VEB");

		lh.addStaticHoliday ("22-MAY-2023", "VEB");

		lh.addStaticHoliday ("12-JUN-2023", "VEB");

		lh.addStaticHoliday ("03-JUL-2023", "VEB");

		lh.addStaticHoliday ("05-JUL-2023", "VEB");

		lh.addStaticHoliday ("24-JUL-2023", "VEB");

		lh.addStaticHoliday ("14-AUG-2023", "VEB");

		lh.addStaticHoliday ("12-OCT-2023", "VEB");

		lh.addStaticHoliday ("06-NOV-2023", "VEB");

		lh.addStaticHoliday ("11-DEC-2023", "VEB");

		lh.addStaticHoliday ("25-DEC-2023", "VEB");

		lh.addStaticHoliday ("01-JAN-2024", "VEB");

		lh.addStaticHoliday ("12-FEB-2024", "VEB");

		lh.addStaticHoliday ("13-FEB-2024", "VEB");

		lh.addStaticHoliday ("19-MAR-2024", "VEB");

		lh.addStaticHoliday ("28-MAR-2024", "VEB");

		lh.addStaticHoliday ("29-MAR-2024", "VEB");

		lh.addStaticHoliday ("19-APR-2024", "VEB");

		lh.addStaticHoliday ("01-MAY-2024", "VEB");

		lh.addStaticHoliday ("13-MAY-2024", "VEB");

		lh.addStaticHoliday ("03-JUN-2024", "VEB");

		lh.addStaticHoliday ("24-JUN-2024", "VEB");

		lh.addStaticHoliday ("05-JUL-2024", "VEB");

		lh.addStaticHoliday ("24-JUL-2024", "VEB");

		lh.addStaticHoliday ("19-AUG-2024", "VEB");

		lh.addStaticHoliday ("04-NOV-2024", "VEB");

		lh.addStaticHoliday ("25-DEC-2024", "VEB");

		lh.addStaticHoliday ("01-JAN-2025", "VEB");

		lh.addStaticHoliday ("06-JAN-2025", "VEB");

		lh.addStaticHoliday ("03-MAR-2025", "VEB");

		lh.addStaticHoliday ("04-MAR-2025", "VEB");

		lh.addStaticHoliday ("19-MAR-2025", "VEB");

		lh.addStaticHoliday ("17-APR-2025", "VEB");

		lh.addStaticHoliday ("18-APR-2025", "VEB");

		lh.addStaticHoliday ("01-MAY-2025", "VEB");

		lh.addStaticHoliday ("02-JUN-2025", "VEB");

		lh.addStaticHoliday ("16-JUN-2025", "VEB");

		lh.addStaticHoliday ("24-JUN-2025", "VEB");

		lh.addStaticHoliday ("24-JUL-2025", "VEB");

		lh.addStaticHoliday ("18-AUG-2025", "VEB");

		lh.addStaticHoliday ("08-DEC-2025", "VEB");

		lh.addStaticHoliday ("25-DEC-2025", "VEB");

		lh.addStaticHoliday ("01-JAN-2026", "VEB");

		lh.addStaticHoliday ("05-JAN-2026", "VEB");

		lh.addStaticHoliday ("16-FEB-2026", "VEB");

		lh.addStaticHoliday ("17-FEB-2026", "VEB");

		lh.addStaticHoliday ("19-MAR-2026", "VEB");

		lh.addStaticHoliday ("02-APR-2026", "VEB");

		lh.addStaticHoliday ("03-APR-2026", "VEB");

		lh.addStaticHoliday ("01-MAY-2026", "VEB");

		lh.addStaticHoliday ("18-MAY-2026", "VEB");

		lh.addStaticHoliday ("08-JUN-2026", "VEB");

		lh.addStaticHoliday ("24-JUN-2026", "VEB");

		lh.addStaticHoliday ("29-JUN-2026", "VEB");

		lh.addStaticHoliday ("24-JUL-2026", "VEB");

		lh.addStaticHoliday ("12-OCT-2026", "VEB");

		lh.addStaticHoliday ("07-DEC-2026", "VEB");

		lh.addStaticHoliday ("25-DEC-2026", "VEB");

		lh.addStaticHoliday ("01-JAN-2027", "VEB");

		lh.addStaticHoliday ("04-JAN-2027", "VEB");

		lh.addStaticHoliday ("08-FEB-2027", "VEB");

		lh.addStaticHoliday ("09-FEB-2027", "VEB");

		lh.addStaticHoliday ("19-MAR-2027", "VEB");

		lh.addStaticHoliday ("25-MAR-2027", "VEB");

		lh.addStaticHoliday ("26-MAR-2027", "VEB");

		lh.addStaticHoliday ("19-APR-2027", "VEB");

		lh.addStaticHoliday ("10-MAY-2027", "VEB");

		lh.addStaticHoliday ("31-MAY-2027", "VEB");

		lh.addStaticHoliday ("24-JUN-2027", "VEB");

		lh.addStaticHoliday ("28-JUN-2027", "VEB");

		lh.addStaticHoliday ("05-JUL-2027", "VEB");

		lh.addStaticHoliday ("12-OCT-2027", "VEB");

		lh.addStaticHoliday ("01-NOV-2027", "VEB");

		lh.addStaticHoliday ("06-DEC-2027", "VEB");

		lh.addStaticHoliday ("10-JAN-2028", "VEB");

		lh.addStaticHoliday ("28-FEB-2028", "VEB");

		lh.addStaticHoliday ("29-FEB-2028", "VEB");

		lh.addStaticHoliday ("13-APR-2028", "VEB");

		lh.addStaticHoliday ("14-APR-2028", "VEB");

		lh.addStaticHoliday ("19-APR-2028", "VEB");

		lh.addStaticHoliday ("01-MAY-2028", "VEB");

		lh.addStaticHoliday ("29-MAY-2028", "VEB");

		lh.addStaticHoliday ("19-JUN-2028", "VEB");

		lh.addStaticHoliday ("03-JUL-2028", "VEB");

		lh.addStaticHoliday ("05-JUL-2028", "VEB");

		lh.addStaticHoliday ("24-JUL-2028", "VEB");

		lh.addStaticHoliday ("14-AUG-2028", "VEB");

		lh.addStaticHoliday ("12-OCT-2028", "VEB");

		lh.addStaticHoliday ("06-NOV-2028", "VEB");

		lh.addStaticHoliday ("11-DEC-2028", "VEB");

		lh.addStaticHoliday ("25-DEC-2028", "VEB");

		lh.addStaticHoliday ("01-JAN-2029", "VEB");

		lh.addStaticHoliday ("12-FEB-2029", "VEB");

		lh.addStaticHoliday ("13-FEB-2029", "VEB");

		lh.addStaticHoliday ("19-MAR-2029", "VEB");

		lh.addStaticHoliday ("29-MAR-2029", "VEB");

		lh.addStaticHoliday ("30-MAR-2029", "VEB");

		lh.addStaticHoliday ("19-APR-2029", "VEB");

		lh.addStaticHoliday ("01-MAY-2029", "VEB");

		lh.addStaticHoliday ("14-MAY-2029", "VEB");

		lh.addStaticHoliday ("04-JUN-2029", "VEB");

		lh.addStaticHoliday ("02-JUL-2029", "VEB");

		lh.addStaticHoliday ("05-JUL-2029", "VEB");

		lh.addStaticHoliday ("24-JUL-2029", "VEB");

		lh.addStaticHoliday ("20-AUG-2029", "VEB");

		lh.addStaticHoliday ("12-OCT-2029", "VEB");

		lh.addStaticHoliday ("05-NOV-2029", "VEB");

		lh.addStaticHoliday ("25-DEC-2029", "VEB");

		lh.addStaticHoliday ("01-JAN-2030", "VEB");

		lh.addStaticHoliday ("04-MAR-2030", "VEB");

		lh.addStaticHoliday ("05-MAR-2030", "VEB");

		lh.addStaticHoliday ("19-MAR-2030", "VEB");

		lh.addStaticHoliday ("18-APR-2030", "VEB");

		lh.addStaticHoliday ("19-APR-2030", "VEB");

		lh.addStaticHoliday ("01-MAY-2030", "VEB");

		lh.addStaticHoliday ("03-JUN-2030", "VEB");

		lh.addStaticHoliday ("24-JUN-2030", "VEB");

		lh.addStaticHoliday ("05-JUL-2030", "VEB");

		lh.addStaticHoliday ("24-JUL-2030", "VEB");

		lh.addStaticHoliday ("19-AUG-2030", "VEB");

		lh.addStaticHoliday ("04-NOV-2030", "VEB");

		lh.addStaticHoliday ("25-DEC-2030", "VEB");

		lh.addStaticHoliday ("01-JAN-2031", "VEB");

		lh.addStaticHoliday ("06-JAN-2031", "VEB");

		lh.addStaticHoliday ("24-FEB-2031", "VEB");

		lh.addStaticHoliday ("25-FEB-2031", "VEB");

		lh.addStaticHoliday ("19-MAR-2031", "VEB");

		lh.addStaticHoliday ("10-APR-2031", "VEB");

		lh.addStaticHoliday ("11-APR-2031", "VEB");

		lh.addStaticHoliday ("01-MAY-2031", "VEB");

		lh.addStaticHoliday ("26-MAY-2031", "VEB");

		lh.addStaticHoliday ("16-JUN-2031", "VEB");

		lh.addStaticHoliday ("24-JUN-2031", "VEB");

		lh.addStaticHoliday ("24-JUL-2031", "VEB");

		lh.addStaticHoliday ("18-AUG-2031", "VEB");

		lh.addStaticHoliday ("08-DEC-2031", "VEB");

		lh.addStaticHoliday ("25-DEC-2031", "VEB");

		lh.addStaticHoliday ("01-JAN-2032", "VEB");

		lh.addStaticHoliday ("05-JAN-2032", "VEB");

		lh.addStaticHoliday ("09-FEB-2032", "VEB");

		lh.addStaticHoliday ("10-FEB-2032", "VEB");

		lh.addStaticHoliday ("19-MAR-2032", "VEB");

		lh.addStaticHoliday ("25-MAR-2032", "VEB");

		lh.addStaticHoliday ("26-MAR-2032", "VEB");

		lh.addStaticHoliday ("19-APR-2032", "VEB");

		lh.addStaticHoliday ("10-MAY-2032", "VEB");

		lh.addStaticHoliday ("31-MAY-2032", "VEB");

		lh.addStaticHoliday ("24-JUN-2032", "VEB");

		lh.addStaticHoliday ("28-JUN-2032", "VEB");

		lh.addStaticHoliday ("05-JUL-2032", "VEB");

		lh.addStaticHoliday ("12-OCT-2032", "VEB");

		lh.addStaticHoliday ("01-NOV-2032", "VEB");

		lh.addStaticHoliday ("06-DEC-2032", "VEB");

		lh.addStaticHoliday ("10-JAN-2033", "VEB");

		lh.addStaticHoliday ("28-FEB-2033", "VEB");

		lh.addStaticHoliday ("01-MAR-2033", "VEB");

		lh.addStaticHoliday ("14-APR-2033", "VEB");

		lh.addStaticHoliday ("15-APR-2033", "VEB");

		lh.addStaticHoliday ("19-APR-2033", "VEB");

		lh.addStaticHoliday ("30-MAY-2033", "VEB");

		lh.addStaticHoliday ("20-JUN-2033", "VEB");

		lh.addStaticHoliday ("24-JUN-2033", "VEB");

		lh.addStaticHoliday ("27-JUN-2033", "VEB");

		lh.addStaticHoliday ("05-JUL-2033", "VEB");

		lh.addStaticHoliday ("15-AUG-2033", "VEB");

		lh.addStaticHoliday ("12-OCT-2033", "VEB");

		lh.addStaticHoliday ("31-OCT-2033", "VEB");

		lh.addStaticHoliday ("12-DEC-2033", "VEB");

		lh.addStaticHoliday ("09-JAN-2034", "VEB");

		lh.addStaticHoliday ("20-FEB-2034", "VEB");

		lh.addStaticHoliday ("21-FEB-2034", "VEB");

		lh.addStaticHoliday ("06-APR-2034", "VEB");

		lh.addStaticHoliday ("07-APR-2034", "VEB");

		lh.addStaticHoliday ("19-APR-2034", "VEB");

		lh.addStaticHoliday ("01-MAY-2034", "VEB");

		lh.addStaticHoliday ("22-MAY-2034", "VEB");

		lh.addStaticHoliday ("12-JUN-2034", "VEB");

		lh.addStaticHoliday ("03-JUL-2034", "VEB");

		lh.addStaticHoliday ("05-JUL-2034", "VEB");

		lh.addStaticHoliday ("24-JUL-2034", "VEB");

		lh.addStaticHoliday ("14-AUG-2034", "VEB");

		lh.addStaticHoliday ("12-OCT-2034", "VEB");

		lh.addStaticHoliday ("06-NOV-2034", "VEB");

		lh.addStaticHoliday ("11-DEC-2034", "VEB");

		lh.addStaticHoliday ("25-DEC-2034", "VEB");

		lh.addStaticHoliday ("01-JAN-2035", "VEB");

		lh.addStaticHoliday ("05-FEB-2035", "VEB");

		lh.addStaticHoliday ("06-FEB-2035", "VEB");

		lh.addStaticHoliday ("19-MAR-2035", "VEB");

		lh.addStaticHoliday ("22-MAR-2035", "VEB");

		lh.addStaticHoliday ("23-MAR-2035", "VEB");

		lh.addStaticHoliday ("19-APR-2035", "VEB");

		lh.addStaticHoliday ("01-MAY-2035", "VEB");

		lh.addStaticHoliday ("07-MAY-2035", "VEB");

		lh.addStaticHoliday ("28-MAY-2035", "VEB");

		lh.addStaticHoliday ("02-JUL-2035", "VEB");

		lh.addStaticHoliday ("05-JUL-2035", "VEB");

		lh.addStaticHoliday ("24-JUL-2035", "VEB");

		lh.addStaticHoliday ("20-AUG-2035", "VEB");

		lh.addStaticHoliday ("12-OCT-2035", "VEB");

		lh.addStaticHoliday ("05-NOV-2035", "VEB");

		lh.addStaticHoliday ("25-DEC-2035", "VEB");

		lh.addStaticHoliday ("01-JAN-2036", "VEB");

		lh.addStaticHoliday ("25-FEB-2036", "VEB");

		lh.addStaticHoliday ("26-FEB-2036", "VEB");

		lh.addStaticHoliday ("19-MAR-2036", "VEB");

		lh.addStaticHoliday ("10-APR-2036", "VEB");

		lh.addStaticHoliday ("11-APR-2036", "VEB");

		lh.addStaticHoliday ("01-MAY-2036", "VEB");

		lh.addStaticHoliday ("26-MAY-2036", "VEB");

		lh.addStaticHoliday ("16-JUN-2036", "VEB");

		lh.addStaticHoliday ("24-JUN-2036", "VEB");

		lh.addStaticHoliday ("24-JUL-2036", "VEB");

		lh.addStaticHoliday ("18-AUG-2036", "VEB");

		lh.addStaticHoliday ("08-DEC-2036", "VEB");

		lh.addStaticHoliday ("25-DEC-2036", "VEB");

		lh.addStaticHoliday ("01-JAN-2037", "VEB");

		lh.addStaticHoliday ("05-JAN-2037", "VEB");

		lh.addStaticHoliday ("16-FEB-2037", "VEB");

		lh.addStaticHoliday ("17-FEB-2037", "VEB");

		lh.addStaticHoliday ("19-MAR-2037", "VEB");

		lh.addStaticHoliday ("02-APR-2037", "VEB");

		lh.addStaticHoliday ("03-APR-2037", "VEB");

		lh.addStaticHoliday ("01-MAY-2037", "VEB");

		lh.addStaticHoliday ("18-MAY-2037", "VEB");

		lh.addStaticHoliday ("08-JUN-2037", "VEB");

		lh.addStaticHoliday ("24-JUN-2037", "VEB");

		lh.addStaticHoliday ("29-JUN-2037", "VEB");

		lh.addStaticHoliday ("24-JUL-2037", "VEB");

		lh.addStaticHoliday ("12-OCT-2037", "VEB");

		lh.addStaticHoliday ("07-DEC-2037", "VEB");

		lh.addStaticHoliday ("25-DEC-2037", "VEB");

		lh.addStaticHoliday ("01-JAN-2038", "VEB");

		lh.addStaticHoliday ("04-JAN-2038", "VEB");

		lh.addStaticHoliday ("08-MAR-2038", "VEB");

		lh.addStaticHoliday ("09-MAR-2038", "VEB");

		lh.addStaticHoliday ("19-MAR-2038", "VEB");

		lh.addStaticHoliday ("19-APR-2038", "VEB");

		lh.addStaticHoliday ("22-APR-2038", "VEB");

		lh.addStaticHoliday ("23-APR-2038", "VEB");

		lh.addStaticHoliday ("07-JUN-2038", "VEB");

		lh.addStaticHoliday ("24-JUN-2038", "VEB");

		lh.addStaticHoliday ("28-JUN-2038", "VEB");

		lh.addStaticHoliday ("05-JUL-2038", "VEB");

		lh.addStaticHoliday ("12-OCT-2038", "VEB");

		lh.addStaticHoliday ("01-NOV-2038", "VEB");

		lh.addStaticHoliday ("06-DEC-2038", "VEB");

		lh.addStaticHoliday ("10-JAN-2039", "VEB");

		lh.addStaticHoliday ("21-FEB-2039", "VEB");

		lh.addStaticHoliday ("22-FEB-2039", "VEB");

		lh.addStaticHoliday ("07-APR-2039", "VEB");

		lh.addStaticHoliday ("08-APR-2039", "VEB");

		lh.addStaticHoliday ("19-APR-2039", "VEB");

		lh.addStaticHoliday ("23-MAY-2039", "VEB");

		lh.addStaticHoliday ("13-JUN-2039", "VEB");

		lh.addStaticHoliday ("24-JUN-2039", "VEB");

		lh.addStaticHoliday ("27-JUN-2039", "VEB");

		lh.addStaticHoliday ("05-JUL-2039", "VEB");

		lh.addStaticHoliday ("15-AUG-2039", "VEB");

		lh.addStaticHoliday ("12-OCT-2039", "VEB");

		lh.addStaticHoliday ("31-OCT-2039", "VEB");

		lh.addStaticHoliday ("12-DEC-2039", "VEB");

		lh.addStaticHoliday ("09-JAN-2040", "VEB");

		lh.addStaticHoliday ("13-FEB-2040", "VEB");

		lh.addStaticHoliday ("14-FEB-2040", "VEB");

		lh.addStaticHoliday ("19-MAR-2040", "VEB");

		lh.addStaticHoliday ("29-MAR-2040", "VEB");

		lh.addStaticHoliday ("30-MAR-2040", "VEB");

		lh.addStaticHoliday ("19-APR-2040", "VEB");

		lh.addStaticHoliday ("01-MAY-2040", "VEB");

		lh.addStaticHoliday ("14-MAY-2040", "VEB");

		lh.addStaticHoliday ("04-JUN-2040", "VEB");

		lh.addStaticHoliday ("02-JUL-2040", "VEB");

		lh.addStaticHoliday ("05-JUL-2040", "VEB");

		lh.addStaticHoliday ("24-JUL-2040", "VEB");

		lh.addStaticHoliday ("20-AUG-2040", "VEB");

		lh.addStaticHoliday ("12-OCT-2040", "VEB");

		lh.addStaticHoliday ("05-NOV-2040", "VEB");

		lh.addStaticHoliday ("25-DEC-2040", "VEB");

		lh.addStaticHoliday ("01-JAN-2041", "VEB");

		lh.addStaticHoliday ("04-MAR-2041", "VEB");

		lh.addStaticHoliday ("05-MAR-2041", "VEB");

		lh.addStaticHoliday ("19-MAR-2041", "VEB");

		lh.addStaticHoliday ("18-APR-2041", "VEB");

		lh.addStaticHoliday ("19-APR-2041", "VEB");

		lh.addStaticHoliday ("01-MAY-2041", "VEB");

		lh.addStaticHoliday ("03-JUN-2041", "VEB");

		lh.addStaticHoliday ("24-JUN-2041", "VEB");

		lh.addStaticHoliday ("05-JUL-2041", "VEB");

		lh.addStaticHoliday ("24-JUL-2041", "VEB");

		lh.addStaticHoliday ("19-AUG-2041", "VEB");

		lh.addStaticHoliday ("04-NOV-2041", "VEB");

		lh.addStaticHoliday ("25-DEC-2041", "VEB");

		lh.addStaticHoliday ("01-JAN-2042", "VEB");

		lh.addStaticHoliday ("06-JAN-2042", "VEB");

		lh.addStaticHoliday ("17-FEB-2042", "VEB");

		lh.addStaticHoliday ("18-FEB-2042", "VEB");

		lh.addStaticHoliday ("19-MAR-2042", "VEB");

		lh.addStaticHoliday ("03-APR-2042", "VEB");

		lh.addStaticHoliday ("04-APR-2042", "VEB");

		lh.addStaticHoliday ("01-MAY-2042", "VEB");

		lh.addStaticHoliday ("19-MAY-2042", "VEB");

		lh.addStaticHoliday ("09-JUN-2042", "VEB");

		lh.addStaticHoliday ("24-JUN-2042", "VEB");

		lh.addStaticHoliday ("24-JUL-2042", "VEB");

		lh.addStaticHoliday ("18-AUG-2042", "VEB");

		lh.addStaticHoliday ("08-DEC-2042", "VEB");

		lh.addStaticHoliday ("25-DEC-2042", "VEB");

		lh.addStaticHoliday ("01-JAN-2043", "VEB");

		lh.addStaticHoliday ("05-JAN-2043", "VEB");

		lh.addStaticHoliday ("09-FEB-2043", "VEB");

		lh.addStaticHoliday ("10-FEB-2043", "VEB");

		lh.addStaticHoliday ("19-MAR-2043", "VEB");

		lh.addStaticHoliday ("26-MAR-2043", "VEB");

		lh.addStaticHoliday ("27-MAR-2043", "VEB");

		lh.addStaticHoliday ("01-MAY-2043", "VEB");

		lh.addStaticHoliday ("11-MAY-2043", "VEB");

		lh.addStaticHoliday ("01-JUN-2043", "VEB");

		lh.addStaticHoliday ("24-JUN-2043", "VEB");

		lh.addStaticHoliday ("29-JUN-2043", "VEB");

		lh.addStaticHoliday ("24-JUL-2043", "VEB");

		lh.addStaticHoliday ("12-OCT-2043", "VEB");

		lh.addStaticHoliday ("07-DEC-2043", "VEB");

		lh.addStaticHoliday ("25-DEC-2043", "VEB");

		lh.addStaticHoliday ("01-JAN-2044", "VEB");

		lh.addStaticHoliday ("04-JAN-2044", "VEB");

		lh.addStaticHoliday ("29-FEB-2044", "VEB");

		lh.addStaticHoliday ("01-MAR-2044", "VEB");

		lh.addStaticHoliday ("14-APR-2044", "VEB");

		lh.addStaticHoliday ("15-APR-2044", "VEB");

		lh.addStaticHoliday ("19-APR-2044", "VEB");

		lh.addStaticHoliday ("30-MAY-2044", "VEB");

		lh.addStaticHoliday ("20-JUN-2044", "VEB");

		lh.addStaticHoliday ("24-JUN-2044", "VEB");

		lh.addStaticHoliday ("27-JUN-2044", "VEB");

		lh.addStaticHoliday ("05-JUL-2044", "VEB");

		lh.addStaticHoliday ("15-AUG-2044", "VEB");

		lh.addStaticHoliday ("12-OCT-2044", "VEB");

		lh.addStaticHoliday ("31-OCT-2044", "VEB");

		lh.addStaticHoliday ("12-DEC-2044", "VEB");

		lh.addStaticHoliday ("09-JAN-2045", "VEB");

		lh.addStaticHoliday ("20-FEB-2045", "VEB");

		lh.addStaticHoliday ("21-FEB-2045", "VEB");

		lh.addStaticHoliday ("06-APR-2045", "VEB");

		lh.addStaticHoliday ("07-APR-2045", "VEB");

		lh.addStaticHoliday ("19-APR-2045", "VEB");

		lh.addStaticHoliday ("01-MAY-2045", "VEB");

		lh.addStaticHoliday ("22-MAY-2045", "VEB");

		lh.addStaticHoliday ("12-JUN-2045", "VEB");

		lh.addStaticHoliday ("03-JUL-2045", "VEB");

		lh.addStaticHoliday ("05-JUL-2045", "VEB");

		lh.addStaticHoliday ("24-JUL-2045", "VEB");

		lh.addStaticHoliday ("14-AUG-2045", "VEB");

		lh.addStaticHoliday ("12-OCT-2045", "VEB");

		lh.addStaticHoliday ("06-NOV-2045", "VEB");

		lh.addStaticHoliday ("11-DEC-2045", "VEB");

		lh.addStaticHoliday ("25-DEC-2045", "VEB");

		lh.addStaticHoliday ("01-JAN-2046", "VEB");

		lh.addStaticHoliday ("05-FEB-2046", "VEB");

		lh.addStaticHoliday ("06-FEB-2046", "VEB");

		lh.addStaticHoliday ("19-MAR-2046", "VEB");

		lh.addStaticHoliday ("22-MAR-2046", "VEB");

		lh.addStaticHoliday ("23-MAR-2046", "VEB");

		lh.addStaticHoliday ("19-APR-2046", "VEB");

		lh.addStaticHoliday ("01-MAY-2046", "VEB");

		lh.addStaticHoliday ("07-MAY-2046", "VEB");

		lh.addStaticHoliday ("28-MAY-2046", "VEB");

		lh.addStaticHoliday ("02-JUL-2046", "VEB");

		lh.addStaticHoliday ("05-JUL-2046", "VEB");

		lh.addStaticHoliday ("24-JUL-2046", "VEB");

		lh.addStaticHoliday ("20-AUG-2046", "VEB");

		lh.addStaticHoliday ("12-OCT-2046", "VEB");

		lh.addStaticHoliday ("05-NOV-2046", "VEB");

		lh.addStaticHoliday ("25-DEC-2046", "VEB");

		lh.addStaticHoliday ("01-JAN-2047", "VEB");

		lh.addStaticHoliday ("25-FEB-2047", "VEB");

		lh.addStaticHoliday ("26-FEB-2047", "VEB");

		lh.addStaticHoliday ("19-MAR-2047", "VEB");

		lh.addStaticHoliday ("11-APR-2047", "VEB");

		lh.addStaticHoliday ("12-APR-2047", "VEB");

		lh.addStaticHoliday ("19-APR-2047", "VEB");

		lh.addStaticHoliday ("01-MAY-2047", "VEB");

		lh.addStaticHoliday ("27-MAY-2047", "VEB");

		lh.addStaticHoliday ("17-JUN-2047", "VEB");

		lh.addStaticHoliday ("24-JUN-2047", "VEB");

		lh.addStaticHoliday ("05-JUL-2047", "VEB");

		lh.addStaticHoliday ("24-JUL-2047", "VEB");

		lh.addStaticHoliday ("19-AUG-2047", "VEB");

		lh.addStaticHoliday ("04-NOV-2047", "VEB");

		lh.addStaticHoliday ("25-DEC-2047", "VEB");

		lh.addStaticHoliday ("01-JAN-2048", "VEB");

		lh.addStaticHoliday ("06-JAN-2048", "VEB");

		lh.addStaticHoliday ("17-FEB-2048", "VEB");

		lh.addStaticHoliday ("18-FEB-2048", "VEB");

		lh.addStaticHoliday ("19-MAR-2048", "VEB");

		lh.addStaticHoliday ("02-APR-2048", "VEB");

		lh.addStaticHoliday ("03-APR-2048", "VEB");

		lh.addStaticHoliday ("01-MAY-2048", "VEB");

		lh.addStaticHoliday ("18-MAY-2048", "VEB");

		lh.addStaticHoliday ("08-JUN-2048", "VEB");

		lh.addStaticHoliday ("24-JUN-2048", "VEB");

		lh.addStaticHoliday ("29-JUN-2048", "VEB");

		lh.addStaticHoliday ("24-JUL-2048", "VEB");

		lh.addStaticHoliday ("12-OCT-2048", "VEB");

		lh.addStaticHoliday ("07-DEC-2048", "VEB");

		lh.addStaticHoliday ("25-DEC-2048", "VEB");

		lh.addStaticHoliday ("01-JAN-2049", "VEB");

		lh.addStaticHoliday ("04-JAN-2049", "VEB");

		lh.addStaticHoliday ("01-MAR-2049", "VEB");

		lh.addStaticHoliday ("02-MAR-2049", "VEB");

		lh.addStaticHoliday ("19-MAR-2049", "VEB");

		lh.addStaticHoliday ("15-APR-2049", "VEB");

		lh.addStaticHoliday ("16-APR-2049", "VEB");

		lh.addStaticHoliday ("19-APR-2049", "VEB");

		lh.addStaticHoliday ("31-MAY-2049", "VEB");

		lh.addStaticHoliday ("21-JUN-2049", "VEB");

		lh.addStaticHoliday ("24-JUN-2049", "VEB");

		lh.addStaticHoliday ("28-JUN-2049", "VEB");

		lh.addStaticHoliday ("05-JUL-2049", "VEB");

		lh.addStaticHoliday ("12-OCT-2049", "VEB");

		lh.addStaticHoliday ("01-NOV-2049", "VEB");

		lh.addStaticHoliday ("06-DEC-2049", "VEB");

		lh.addStaticHoliday ("10-JAN-2050", "VEB");

		lh.addStaticHoliday ("21-FEB-2050", "VEB");

		lh.addStaticHoliday ("22-FEB-2050", "VEB");

		lh.addStaticHoliday ("07-APR-2050", "VEB");

		lh.addStaticHoliday ("08-APR-2050", "VEB");

		lh.addStaticHoliday ("19-APR-2050", "VEB");

		lh.addStaticHoliday ("23-MAY-2050", "VEB");

		lh.addStaticHoliday ("13-JUN-2050", "VEB");

		lh.addStaticHoliday ("24-JUN-2050", "VEB");

		lh.addStaticHoliday ("27-JUN-2050", "VEB");

		lh.addStaticHoliday ("05-JUL-2050", "VEB");

		lh.addStaticHoliday ("15-AUG-2050", "VEB");

		lh.addStaticHoliday ("12-OCT-2050", "VEB");

		lh.addStaticHoliday ("31-OCT-2050", "VEB");

		lh.addStaticHoliday ("12-DEC-2050", "VEB");

		lh.addStaticHoliday ("09-JAN-2051", "VEB");

		lh.addStaticHoliday ("13-FEB-2051", "VEB");

		lh.addStaticHoliday ("14-FEB-2051", "VEB");

		lh.addStaticHoliday ("30-MAR-2051", "VEB");

		lh.addStaticHoliday ("31-MAR-2051", "VEB");

		lh.addStaticHoliday ("19-APR-2051", "VEB");

		lh.addStaticHoliday ("01-MAY-2051", "VEB");

		lh.addStaticHoliday ("15-MAY-2051", "VEB");

		lh.addStaticHoliday ("05-JUN-2051", "VEB");

		lh.addStaticHoliday ("03-JUL-2051", "VEB");

		lh.addStaticHoliday ("05-JUL-2051", "VEB");

		lh.addStaticHoliday ("24-JUL-2051", "VEB");

		lh.addStaticHoliday ("14-AUG-2051", "VEB");

		lh.addStaticHoliday ("12-OCT-2051", "VEB");

		lh.addStaticHoliday ("06-NOV-2051", "VEB");

		lh.addStaticHoliday ("11-DEC-2051", "VEB");

		lh.addStaticHoliday ("25-DEC-2051", "VEB");

		lh.addStaticHoliday ("01-JAN-2052", "VEB");

		lh.addStaticHoliday ("04-MAR-2052", "VEB");

		lh.addStaticHoliday ("05-MAR-2052", "VEB");

		lh.addStaticHoliday ("19-MAR-2052", "VEB");

		lh.addStaticHoliday ("18-APR-2052", "VEB");

		lh.addStaticHoliday ("19-APR-2052", "VEB");

		lh.addStaticHoliday ("01-MAY-2052", "VEB");

		lh.addStaticHoliday ("03-JUN-2052", "VEB");

		lh.addStaticHoliday ("24-JUN-2052", "VEB");

		lh.addStaticHoliday ("05-JUL-2052", "VEB");

		lh.addStaticHoliday ("24-JUL-2052", "VEB");

		lh.addStaticHoliday ("19-AUG-2052", "VEB");

		lh.addStaticHoliday ("04-NOV-2052", "VEB");

		lh.addStaticHoliday ("25-DEC-2052", "VEB");

		lh.addStaticHoliday ("01-JAN-2053", "VEB");

		lh.addStaticHoliday ("06-JAN-2053", "VEB");

		lh.addStaticHoliday ("17-FEB-2053", "VEB");

		lh.addStaticHoliday ("18-FEB-2053", "VEB");

		lh.addStaticHoliday ("19-MAR-2053", "VEB");

		lh.addStaticHoliday ("03-APR-2053", "VEB");

		lh.addStaticHoliday ("04-APR-2053", "VEB");

		lh.addStaticHoliday ("01-MAY-2053", "VEB");

		lh.addStaticHoliday ("19-MAY-2053", "VEB");

		lh.addStaticHoliday ("09-JUN-2053", "VEB");

		lh.addStaticHoliday ("24-JUN-2053", "VEB");

		lh.addStaticHoliday ("24-JUL-2053", "VEB");

		lh.addStaticHoliday ("18-AUG-2053", "VEB");

		lh.addStaticHoliday ("08-DEC-2053", "VEB");

		lh.addStaticHoliday ("25-DEC-2053", "VEB");

		lh.addStaticHoliday ("01-JAN-2054", "VEB");

		lh.addStaticHoliday ("05-JAN-2054", "VEB");

		lh.addStaticHoliday ("09-FEB-2054", "VEB");

		lh.addStaticHoliday ("10-FEB-2054", "VEB");

		lh.addStaticHoliday ("19-MAR-2054", "VEB");

		lh.addStaticHoliday ("26-MAR-2054", "VEB");

		lh.addStaticHoliday ("27-MAR-2054", "VEB");

		lh.addStaticHoliday ("01-MAY-2054", "VEB");

		lh.addStaticHoliday ("11-MAY-2054", "VEB");

		lh.addStaticHoliday ("01-JUN-2054", "VEB");

		lh.addStaticHoliday ("24-JUN-2054", "VEB");

		lh.addStaticHoliday ("29-JUN-2054", "VEB");

		lh.addStaticHoliday ("24-JUL-2054", "VEB");

		lh.addStaticHoliday ("12-OCT-2054", "VEB");

		lh.addStaticHoliday ("07-DEC-2054", "VEB");

		lh.addStaticHoliday ("25-DEC-2054", "VEB");

		lh.addStaticHoliday ("01-JAN-2055", "VEB");

		lh.addStaticHoliday ("04-JAN-2055", "VEB");

		lh.addStaticHoliday ("01-MAR-2055", "VEB");

		lh.addStaticHoliday ("02-MAR-2055", "VEB");

		lh.addStaticHoliday ("19-MAR-2055", "VEB");

		lh.addStaticHoliday ("15-APR-2055", "VEB");

		lh.addStaticHoliday ("16-APR-2055", "VEB");

		lh.addStaticHoliday ("19-APR-2055", "VEB");

		lh.addStaticHoliday ("31-MAY-2055", "VEB");

		lh.addStaticHoliday ("21-JUN-2055", "VEB");

		lh.addStaticHoliday ("24-JUN-2055", "VEB");

		lh.addStaticHoliday ("28-JUN-2055", "VEB");

		lh.addStaticHoliday ("05-JUL-2055", "VEB");

		lh.addStaticHoliday ("12-OCT-2055", "VEB");

		lh.addStaticHoliday ("01-NOV-2055", "VEB");

		lh.addStaticHoliday ("06-DEC-2055", "VEB");

		lh.addStaticHoliday ("10-JAN-2056", "VEB");

		lh.addStaticHoliday ("14-FEB-2056", "VEB");

		lh.addStaticHoliday ("15-FEB-2056", "VEB");

		lh.addStaticHoliday ("30-MAR-2056", "VEB");

		lh.addStaticHoliday ("31-MAR-2056", "VEB");

		lh.addStaticHoliday ("19-APR-2056", "VEB");

		lh.addStaticHoliday ("01-MAY-2056", "VEB");

		lh.addStaticHoliday ("15-MAY-2056", "VEB");

		lh.addStaticHoliday ("05-JUN-2056", "VEB");

		lh.addStaticHoliday ("03-JUL-2056", "VEB");

		lh.addStaticHoliday ("05-JUL-2056", "VEB");

		lh.addStaticHoliday ("24-JUL-2056", "VEB");

		lh.addStaticHoliday ("14-AUG-2056", "VEB");

		lh.addStaticHoliday ("12-OCT-2056", "VEB");

		lh.addStaticHoliday ("06-NOV-2056", "VEB");

		lh.addStaticHoliday ("11-DEC-2056", "VEB");

		lh.addStaticHoliday ("25-DEC-2056", "VEB");

		lh.addStaticHoliday ("01-JAN-2057", "VEB");

		lh.addStaticHoliday ("05-MAR-2057", "VEB");

		lh.addStaticHoliday ("06-MAR-2057", "VEB");

		lh.addStaticHoliday ("19-MAR-2057", "VEB");

		lh.addStaticHoliday ("19-APR-2057", "VEB");

		lh.addStaticHoliday ("20-APR-2057", "VEB");

		lh.addStaticHoliday ("01-MAY-2057", "VEB");

		lh.addStaticHoliday ("04-JUN-2057", "VEB");

		lh.addStaticHoliday ("25-JUN-2057", "VEB");

		lh.addStaticHoliday ("02-JUL-2057", "VEB");

		lh.addStaticHoliday ("05-JUL-2057", "VEB");

		lh.addStaticHoliday ("24-JUL-2057", "VEB");

		lh.addStaticHoliday ("20-AUG-2057", "VEB");

		lh.addStaticHoliday ("12-OCT-2057", "VEB");

		lh.addStaticHoliday ("05-NOV-2057", "VEB");

		lh.addStaticHoliday ("25-DEC-2057", "VEB");

		lh.addStaticHoliday ("01-JAN-2058", "VEB");

		lh.addStaticHoliday ("25-FEB-2058", "VEB");

		lh.addStaticHoliday ("26-FEB-2058", "VEB");

		lh.addStaticHoliday ("19-MAR-2058", "VEB");

		lh.addStaticHoliday ("11-APR-2058", "VEB");

		lh.addStaticHoliday ("12-APR-2058", "VEB");

		lh.addStaticHoliday ("19-APR-2058", "VEB");

		lh.addStaticHoliday ("01-MAY-2058", "VEB");

		lh.addStaticHoliday ("27-MAY-2058", "VEB");

		lh.addStaticHoliday ("17-JUN-2058", "VEB");

		lh.addStaticHoliday ("24-JUN-2058", "VEB");

		lh.addStaticHoliday ("05-JUL-2058", "VEB");

		lh.addStaticHoliday ("24-JUL-2058", "VEB");

		lh.addStaticHoliday ("19-AUG-2058", "VEB");

		lh.addStaticHoliday ("04-NOV-2058", "VEB");

		lh.addStaticHoliday ("25-DEC-2058", "VEB");

		lh.addStaticHoliday ("01-JAN-2059", "VEB");

		lh.addStaticHoliday ("06-JAN-2059", "VEB");

		lh.addStaticHoliday ("10-FEB-2059", "VEB");

		lh.addStaticHoliday ("11-FEB-2059", "VEB");

		lh.addStaticHoliday ("19-MAR-2059", "VEB");

		lh.addStaticHoliday ("27-MAR-2059", "VEB");

		lh.addStaticHoliday ("28-MAR-2059", "VEB");

		lh.addStaticHoliday ("01-MAY-2059", "VEB");

		lh.addStaticHoliday ("12-MAY-2059", "VEB");

		lh.addStaticHoliday ("02-JUN-2059", "VEB");

		lh.addStaticHoliday ("24-JUN-2059", "VEB");

		lh.addStaticHoliday ("24-JUL-2059", "VEB");

		lh.addStaticHoliday ("18-AUG-2059", "VEB");

		lh.addStaticHoliday ("08-DEC-2059", "VEB");

		lh.addStaticHoliday ("25-DEC-2059", "VEB");

		lh.addStaticHoliday ("01-JAN-2060", "VEB");

		lh.addStaticHoliday ("05-JAN-2060", "VEB");

		lh.addStaticHoliday ("01-MAR-2060", "VEB");

		lh.addStaticHoliday ("02-MAR-2060", "VEB");

		lh.addStaticHoliday ("19-MAR-2060", "VEB");

		lh.addStaticHoliday ("15-APR-2060", "VEB");

		lh.addStaticHoliday ("16-APR-2060", "VEB");

		lh.addStaticHoliday ("19-APR-2060", "VEB");

		lh.addStaticHoliday ("31-MAY-2060", "VEB");

		lh.addStaticHoliday ("21-JUN-2060", "VEB");

		lh.addStaticHoliday ("24-JUN-2060", "VEB");

		lh.addStaticHoliday ("28-JUN-2060", "VEB");

		lh.addStaticHoliday ("05-JUL-2060", "VEB");

		lh.addStaticHoliday ("12-OCT-2060", "VEB");

		lh.addStaticHoliday ("01-NOV-2060", "VEB");

		lh.addStaticHoliday ("06-DEC-2060", "VEB");

		lh.addStandardWeekend();

		return lh;
	}
}
