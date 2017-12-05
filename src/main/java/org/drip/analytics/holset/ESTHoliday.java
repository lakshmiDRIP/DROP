
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

public class ESTHoliday implements org.drip.analytics.holset.LocationHoliday {
	public ESTHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "EST";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1997", "New Years Day");

		lh.addStaticHoliday ("06-JAN-1997", "Epiphany");

		lh.addStaticHoliday ("27-MAR-1997", "Holy Thursday");

		lh.addStaticHoliday ("28-MAR-1997", "Good Friday");

		lh.addStaticHoliday ("01-MAY-1997", "Labour Day");

		lh.addStaticHoliday ("02-MAY-1997", "Madrid Day");

		lh.addStaticHoliday ("15-MAY-1997", "San Isidro");

		lh.addStaticHoliday ("25-JUL-1997", "St. James Day");

		lh.addStaticHoliday ("15-AUG-1997", "Assumption Day");

		lh.addStaticHoliday ("09-SEP-1997", "Special Holiday");

		lh.addStaticHoliday ("08-DEC-1997", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-1997", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("06-JAN-1998", "Epiphany");

		lh.addStaticHoliday ("19-MAR-1998", "*St. Josephs Day");

		lh.addStaticHoliday ("09-APR-1998", "Holy Thursday");

		lh.addStaticHoliday ("10-APR-1998", "Good Friday");

		lh.addStaticHoliday ("01-MAY-1998", "Labour Day");

		lh.addStaticHoliday ("15-MAY-1998", "San Isidro");

		lh.addStaticHoliday ("12-OCT-1998", "National Holiday");

		lh.addStaticHoliday ("09-NOV-1998", "Our Lady of Almudena");

		lh.addStaticHoliday ("08-DEC-1998", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-1998", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("06-JAN-1999", "Epiphany");

		lh.addStaticHoliday ("19-MAR-1999", "St. Josephs Day");

		lh.addStaticHoliday ("01-APR-1999", "Holy Thursday");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("12-OCT-1999", "National Holiday");

		lh.addStaticHoliday ("01-NOV-1999", "All Saints Day");

		lh.addStaticHoliday ("09-NOV-1999", "Our Lady of Almudena");

		lh.addStaticHoliday ("06-DEC-1999", "Constitution Day");

		lh.addStaticHoliday ("08-DEC-1999", "Immaculate Conception");

		lh.addStaticHoliday ("31-DEC-1999", "Special Holiday");

		lh.addStaticHoliday ("06-JAN-2000", "Epiphany");

		lh.addStaticHoliday ("20-APR-2000", "Holy Thursday");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2000", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2000", "Madrid Day");

		lh.addStaticHoliday ("15-MAY-2000", "San Isidro");

		lh.addStaticHoliday ("25-JUL-2000", "*St. James Day");

		lh.addStaticHoliday ("15-AUG-2000", "Assumption Day");

		lh.addStaticHoliday ("12-OCT-2000", "National Holiday");

		lh.addStaticHoliday ("01-NOV-2000", "All Saints Day");

		lh.addStaticHoliday ("09-NOV-2000", "Our Lady of Almudena");

		lh.addStaticHoliday ("06-DEC-2000", "Constitution Day");

		lh.addStaticHoliday ("08-DEC-2000", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("19-MAR-2001", "*St. Josephs Day");

		lh.addStaticHoliday ("12-APR-2001", "Holy Thursday");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2001", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2001", "Madrid Day");

		lh.addStaticHoliday ("15-MAY-2001", "San Isidro");

		lh.addStaticHoliday ("25-JUL-2001", "*St. James Day");

		lh.addStaticHoliday ("15-AUG-2001", "Assumption Day");

		lh.addStaticHoliday ("12-OCT-2001", "National Holiday");

		lh.addStaticHoliday ("01-NOV-2001", "All Saints Day");

		lh.addStaticHoliday ("09-NOV-2001", "Our Lady of Almudena");

		lh.addStaticHoliday ("06-DEC-2001", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("19-MAR-2002", "*St. Josephs Day");

		lh.addStaticHoliday ("28-MAR-2002", "Holy Thursday");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2002", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2002", "Madrid Day");

		lh.addStaticHoliday ("15-MAY-2002", "San Isidro");

		lh.addStaticHoliday ("25-JUL-2002", "*St. James Day");

		lh.addStaticHoliday ("15-AUG-2002", "Assumption Day");

		lh.addStaticHoliday ("01-NOV-2002", "All Saints Day");

		lh.addStaticHoliday ("06-DEC-2002", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2003", "Epiphany");

		lh.addStaticHoliday ("19-MAR-2003", "*St. Josephs Day");

		lh.addStaticHoliday ("17-APR-2003", "Holy Thursday");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2003", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2003", "Madrid Day");

		lh.addStaticHoliday ("15-MAY-2003", "San Isidro");

		lh.addStaticHoliday ("25-JUL-2003", "St. James Day");

		lh.addStaticHoliday ("15-AUG-2003", "Assumption Day");

		lh.addStaticHoliday ("08-DEC-2003", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2004", "Epiphany");

		lh.addStaticHoliday ("19-MAR-2004", "St. Josephs Day");

		lh.addStaticHoliday ("08-APR-2004", "Holy Thursday");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("12-OCT-2004", "National Holiday");

		lh.addStaticHoliday ("01-NOV-2004", "All Saints Day");

		lh.addStaticHoliday ("09-NOV-2004", "Our Lady of Almudena");

		lh.addStaticHoliday ("06-DEC-2004", "Constitution Day");

		lh.addStaticHoliday ("08-DEC-2004", "Immaculate Conception");

		lh.addStaticHoliday ("06-JAN-2005", "Epiphany");

		lh.addStaticHoliday ("24-MAR-2005", "Holy Thursday");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("02-MAY-2005", "Madrid Day");

		lh.addStaticHoliday ("25-JUL-2005", "*St. James Day");

		lh.addStaticHoliday ("15-AUG-2005", "Assumption Day");

		lh.addStaticHoliday ("12-OCT-2005", "National Holiday");

		lh.addStaticHoliday ("01-NOV-2005", "All Saints Day");

		lh.addStaticHoliday ("09-NOV-2005", "Our Lady of Almudena");

		lh.addStaticHoliday ("06-DEC-2005", "Constitution Day");

		lh.addStaticHoliday ("08-DEC-2005", "Immaculate Conception");

		lh.addStaticHoliday ("06-JAN-2006", "Epiphany");

		lh.addStaticHoliday ("13-APR-2006", "Holy Thursday");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2006", "Madrid Day");

		lh.addStaticHoliday ("15-MAY-2006", "San Isidro");

		lh.addStaticHoliday ("25-JUL-2006", "*St. James Day");

		lh.addStaticHoliday ("15-AUG-2006", "Assumption Day");

		lh.addStaticHoliday ("12-OCT-2006", "National Holiday");

		lh.addStaticHoliday ("01-NOV-2006", "All Saints Day");

		lh.addStaticHoliday ("09-NOV-2006", "Our Lady of Almudena");

		lh.addStaticHoliday ("06-DEC-2006", "Constitution Day");

		lh.addStaticHoliday ("08-DEC-2006", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("19-MAR-2007", "*St. Josephs Day");

		lh.addStaticHoliday ("05-APR-2007", "Holy Thursday");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2007", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2007", "Madrid Day");

		lh.addStaticHoliday ("15-MAY-2007", "San Isidro");

		lh.addStaticHoliday ("07-JUN-2007", "*Corpus Christi");

		lh.addStaticHoliday ("25-JUL-2007", "*St. James Day");

		lh.addStaticHoliday ("15-AUG-2007", "Assumption Day");

		lh.addStaticHoliday ("12-OCT-2007", "National Holiday");

		lh.addStaticHoliday ("01-NOV-2007", "All Saints Day");

		lh.addStaticHoliday ("09-NOV-2007", "Our Lady of Almudena");

		lh.addStaticHoliday ("06-DEC-2007", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("19-MAR-2008", "*St. Josephs Day");

		lh.addStaticHoliday ("20-MAR-2008", "Holy Thursday");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2008", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2008", "Madrid Day");

		lh.addStaticHoliday ("15-MAY-2008", "San Isidro");

		lh.addStaticHoliday ("22-MAY-2008", "*Corpus Christi");

		lh.addStaticHoliday ("25-JUL-2008", "*St. James Day");

		lh.addStaticHoliday ("15-AUG-2008", "Assumption Day");

		lh.addStaticHoliday ("08-DEC-2008", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2009", "Epiphany");

		lh.addStaticHoliday ("19-MAR-2009", "*St. Josephs Day");

		lh.addStaticHoliday ("09-APR-2009", "Holy Thursday");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2009", "Labour Day");

		lh.addStaticHoliday ("15-MAY-2009", "San Isidro");

		lh.addStaticHoliday ("12-OCT-2009", "National Holiday");

		lh.addStaticHoliday ("09-NOV-2009", "Our Lady of Almudena");

		lh.addStaticHoliday ("08-DEC-2009", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2010", "Epiphany");

		lh.addStaticHoliday ("19-MAR-2010", "St. Josephs Day");

		lh.addStaticHoliday ("01-APR-2010", "Holy Thursday");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("12-OCT-2010", "National Holiday");

		lh.addStaticHoliday ("01-NOV-2010", "All Saints Day");

		lh.addStaticHoliday ("09-NOV-2010", "Our Lady of Almudena");

		lh.addStaticHoliday ("06-DEC-2010", "Constitution Day");

		lh.addStaticHoliday ("08-DEC-2010", "Immaculate Conception");

		lh.addStaticHoliday ("06-JAN-2011", "Epiphany");

		lh.addStaticHoliday ("21-APR-2011", "Holy Thursday");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("02-MAY-2011", "Madrid Day");

		lh.addStaticHoliday ("25-JUL-2011", "*St. James Day");

		lh.addStaticHoliday ("15-AUG-2011", "Assumption Day");

		lh.addStaticHoliday ("12-OCT-2011", "National Holiday");

		lh.addStaticHoliday ("01-NOV-2011", "All Saints Day");

		lh.addStaticHoliday ("09-NOV-2011", "Our Lady of Almudena");

		lh.addStaticHoliday ("06-DEC-2011", "Constitution Day");

		lh.addStaticHoliday ("08-DEC-2011", "Immaculate Conception");

		lh.addStaticHoliday ("06-JAN-2012", "Epiphany");

		lh.addStaticHoliday ("19-MAR-2012", "*St. Josephs Day");

		lh.addStaticHoliday ("05-APR-2012", "Holy Thursday");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2012", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2012", "Madrid Day");

		lh.addStaticHoliday ("15-MAY-2012", "San Isidro");

		lh.addStaticHoliday ("25-JUL-2012", "*St. James Day");

		lh.addStaticHoliday ("15-AUG-2012", "Assumption Day");

		lh.addStaticHoliday ("12-OCT-2012", "National Holiday");

		lh.addStaticHoliday ("01-NOV-2012", "All Saints Day");

		lh.addStaticHoliday ("09-NOV-2012", "Our Lady of Almudena");

		lh.addStaticHoliday ("06-DEC-2012", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("19-MAR-2013", "*St. Josephs Day");

		lh.addStaticHoliday ("28-MAR-2013", "Holy Thursday");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2013", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2013", "Madrid Day");

		lh.addStaticHoliday ("15-MAY-2013", "San Isidro");

		lh.addStaticHoliday ("25-JUL-2013", "*St. James Day");

		lh.addStaticHoliday ("15-AUG-2013", "Assumption Day");

		lh.addStaticHoliday ("01-NOV-2013", "All Saints Day");

		lh.addStaticHoliday ("06-DEC-2013", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2014", "Epiphany");

		lh.addStaticHoliday ("19-MAR-2014", "*St. Josephs Day");

		lh.addStaticHoliday ("17-APR-2014", "Holy Thursday");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2014", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2014", "Madrid Day");

		lh.addStaticHoliday ("15-MAY-2014", "San Isidro");

		lh.addStaticHoliday ("25-JUL-2014", "St. James Day");

		lh.addStaticHoliday ("15-AUG-2014", "Assumption Day");

		lh.addStaticHoliday ("08-DEC-2014", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2015", "Epiphany");

		lh.addStaticHoliday ("19-MAR-2015", "*St. Josephs Day");

		lh.addStaticHoliday ("02-APR-2015", "Holy Thursday");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2015", "Labour Day");

		lh.addStaticHoliday ("15-MAY-2015", "San Isidro");

		lh.addStaticHoliday ("12-OCT-2015", "National Holiday");

		lh.addStaticHoliday ("09-NOV-2015", "Our Lady of Almudena");

		lh.addStaticHoliday ("08-DEC-2015", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2016", "Epiphany");

		lh.addStaticHoliday ("24-MAR-2016", "Holy Thursday");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("02-MAY-2016", "Madrid Day");

		lh.addStaticHoliday ("25-JUL-2016", "St. James Day");

		lh.addStaticHoliday ("15-AUG-2016", "Assumption Day");

		lh.addStaticHoliday ("12-OCT-2016", "National Holiday");

		lh.addStaticHoliday ("01-NOV-2016", "All Saints Day");

		lh.addStaticHoliday ("09-NOV-2016", "Our Lady of Almudena");

		lh.addStaticHoliday ("06-DEC-2016", "Constitution Day");

		lh.addStaticHoliday ("08-DEC-2016", "Immaculate Conception");

		lh.addStaticHoliday ("06-JAN-2017", "Epiphany");

		lh.addStaticHoliday ("13-APR-2017", "Holy Thursday");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2017", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2017", "Madrid Day");

		lh.addStaticHoliday ("15-MAY-2017", "San Isidro");

		lh.addStaticHoliday ("25-JUL-2017", "*St. James Day");

		lh.addStaticHoliday ("15-AUG-2017", "Assumption Day");

		lh.addStaticHoliday ("12-OCT-2017", "National Holiday");

		lh.addStaticHoliday ("01-NOV-2017", "All Saints Day");

		lh.addStaticHoliday ("09-NOV-2017", "Our Lady of Almudena");

		lh.addStaticHoliday ("06-DEC-2017", "Constitution Day");

		lh.addStaticHoliday ("08-DEC-2017", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("19-MAR-2018", "*St. Josephs Day");

		lh.addStaticHoliday ("29-MAR-2018", "Holy Thursday");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2018", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2018", "Madrid Day");

		lh.addStaticHoliday ("15-MAY-2018", "San Isidro");

		lh.addStaticHoliday ("25-JUL-2018", "*St. James Day");

		lh.addStaticHoliday ("15-AUG-2018", "Assumption Day");

		lh.addStaticHoliday ("12-OCT-2018", "National Holiday");

		lh.addStaticHoliday ("01-NOV-2018", "All Saints Day");

		lh.addStaticHoliday ("09-NOV-2018", "Our Lady of Almudena");

		lh.addStaticHoliday ("06-DEC-2018", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("19-MAR-2019", "*St. Josephs Day");

		lh.addStaticHoliday ("18-APR-2019", "Holy Thursday");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2019", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2019", "Madrid Day");

		lh.addStaticHoliday ("15-MAY-2019", "San Isidro");

		lh.addStaticHoliday ("25-JUL-2019", "*St. James Day");

		lh.addStaticHoliday ("15-AUG-2019", "Assumption Day");

		lh.addStaticHoliday ("01-NOV-2019", "All Saints Day");

		lh.addStaticHoliday ("06-DEC-2019", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2020", "Epiphany");

		lh.addStaticHoliday ("19-MAR-2020", "*St. Josephs Day");

		lh.addStaticHoliday ("09-APR-2020", "Holy Thursday");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2020", "Labour Day");

		lh.addStaticHoliday ("15-MAY-2020", "San Isidro");

		lh.addStaticHoliday ("12-OCT-2020", "National Holiday");

		lh.addStaticHoliday ("09-NOV-2020", "Our Lady of Almudena");

		lh.addStaticHoliday ("08-DEC-2020", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2021", "Epiphany");

		lh.addStaticHoliday ("19-MAR-2021", "St. Josephs Day");

		lh.addStaticHoliday ("01-APR-2021", "Holy Thursday");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("12-OCT-2021", "National Holiday");

		lh.addStaticHoliday ("01-NOV-2021", "All Saints Day");

		lh.addStaticHoliday ("09-NOV-2021", "Our Lady of Almudena");

		lh.addStaticHoliday ("06-DEC-2021", "Constitution Day");

		lh.addStaticHoliday ("08-DEC-2021", "Immaculate Conception");

		lh.addStaticHoliday ("06-JAN-2022", "Epiphany");

		lh.addStaticHoliday ("14-APR-2022", "Holy Thursday");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("02-MAY-2022", "Madrid Day");

		lh.addStaticHoliday ("25-JUL-2022", "*St. James Day");

		lh.addStaticHoliday ("15-AUG-2022", "Assumption Day");

		lh.addStaticHoliday ("12-OCT-2022", "National Holiday");

		lh.addStaticHoliday ("01-NOV-2022", "All Saints Day");

		lh.addStaticHoliday ("09-NOV-2022", "Our Lady of Almudena");

		lh.addStaticHoliday ("06-DEC-2022", "Constitution Day");

		lh.addStaticHoliday ("08-DEC-2022", "Immaculate Conception");

		lh.addStaticHoliday ("06-JAN-2023", "Epiphany");

		lh.addStaticHoliday ("06-APR-2023", "Holy Thursday");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2023", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2023", "Madrid Day");

		lh.addStaticHoliday ("15-MAY-2023", "San Isidro");

		lh.addStaticHoliday ("25-JUL-2023", "*St. James Day");

		lh.addStaticHoliday ("15-AUG-2023", "Assumption Day");

		lh.addStaticHoliday ("12-OCT-2023", "National Holiday");

		lh.addStaticHoliday ("01-NOV-2023", "All Saints Day");

		lh.addStaticHoliday ("09-NOV-2023", "Our Lady of Almudena");

		lh.addStaticHoliday ("06-DEC-2023", "Constitution Day");

		lh.addStaticHoliday ("08-DEC-2023", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("19-MAR-2024", "*St. Josephs Day");

		lh.addStaticHoliday ("28-MAR-2024", "Holy Thursday");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2024", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2024", "Madrid Day");

		lh.addStaticHoliday ("15-MAY-2024", "San Isidro");

		lh.addStaticHoliday ("30-MAY-2024", "*Corpus Christi");

		lh.addStaticHoliday ("25-JUL-2024", "*St. James Day");

		lh.addStaticHoliday ("15-AUG-2024", "Assumption Day");

		lh.addStaticHoliday ("01-NOV-2024", "All Saints Day");

		lh.addStaticHoliday ("06-DEC-2024", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2025", "Epiphany");

		lh.addStaticHoliday ("19-MAR-2025", "*St. Josephs Day");

		lh.addStaticHoliday ("17-APR-2025", "Holy Thursday");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2025", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2025", "Madrid Day");

		lh.addStaticHoliday ("15-MAY-2025", "San Isidro");

		lh.addStaticHoliday ("19-JUN-2025", "*Corpus Christi");

		lh.addStaticHoliday ("25-JUL-2025", "St. James Day");

		lh.addStaticHoliday ("15-AUG-2025", "Assumption Day");

		lh.addStaticHoliday ("08-DEC-2025", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2026", "Epiphany");

		lh.addStaticHoliday ("19-MAR-2026", "*St. Josephs Day");

		lh.addStaticHoliday ("02-APR-2026", "Holy Thursday");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2026", "Labour Day");

		lh.addStaticHoliday ("15-MAY-2026", "San Isidro");

		lh.addStaticHoliday ("04-JUN-2026", "*Corpus Christi");

		lh.addStaticHoliday ("12-OCT-2026", "National Holiday");

		lh.addStaticHoliday ("09-NOV-2026", "Our Lady of Almudena");

		lh.addStaticHoliday ("08-DEC-2026", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2027", "Epiphany");

		lh.addStaticHoliday ("19-MAR-2027", "St. Josephs Day");

		lh.addStaticHoliday ("25-MAR-2027", "Holy Thursday");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("12-OCT-2027", "National Holiday");

		lh.addStaticHoliday ("01-NOV-2027", "All Saints Day");

		lh.addStaticHoliday ("09-NOV-2027", "Our Lady of Almudena");

		lh.addStaticHoliday ("06-DEC-2027", "Constitution Day");

		lh.addStaticHoliday ("08-DEC-2027", "Immaculate Conception");

		lh.addStandardWeekend();

		return lh;
	}
}
