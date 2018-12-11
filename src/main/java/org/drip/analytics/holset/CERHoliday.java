
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

public class CERHoliday implements org.drip.analytics.holset.LocationHoliday {
	public CERHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "CER";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("01-APR-1999", "Holy Thursday");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("25-MAY-1999", "Liberation Day");

		lh.addStaticHoliday ("14-JUN-1999", "Malvinas Islands Memorial Day Observed");

		lh.addStaticHoliday ("21-JUN-1999", "Flag Day");

		lh.addStaticHoliday ("09-JUL-1999", "Independence Day");

		lh.addStaticHoliday ("16-AUG-1999", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("11-OCT-1999", "Day of the Race Observed");

		lh.addStaticHoliday ("08-DEC-1999", "Immaculate Conception");

		lh.addStaticHoliday ("31-DEC-1999", "Last Weekday of the Year");

		lh.addStaticHoliday ("20-APR-2000", "Holy Thursday");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2000", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2000", "Liberation Day");

		lh.addStaticHoliday ("19-JUN-2000", "Flag Day");

		lh.addStaticHoliday ("21-AUG-2000", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("16-OCT-2000", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2000", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2000", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("29-DEC-2000", "Last Weekday of the Year");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("02-APR-2001", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("12-APR-2001", "Holy Thursday");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2001", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2001", "Liberation Day");

		lh.addStaticHoliday ("18-JUN-2001", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2001", "Independence Day");

		lh.addStaticHoliday ("20-AUG-2001", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("08-OCT-2001", "Columbus Day");

		lh.addStaticHoliday ("06-NOV-2001", "Bank Holiday");

		lh.addStaticHoliday ("21-DEC-2001", "Special Holiday");

		lh.addStaticHoliday ("24-DEC-2001", "Special Holiday");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2001", "Special Holiday");

		lh.addStaticHoliday ("31-DEC-2001", "Special Holiday");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("28-MAR-2002", "Holy Thursday");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("02-APR-2002", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("01-MAY-2002", "Labour Day");

		lh.addStaticHoliday ("17-JUN-2002", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2002", "Independence Day");

		lh.addStaticHoliday ("19-AUG-2002", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("31-MAR-2003", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("17-APR-2003", "Holy Thursday");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2003", "Labour Day");

		lh.addStaticHoliday ("16-JUN-2003", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2003", "Independence Day");

		lh.addStaticHoliday ("18-AUG-2003", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("13-OCT-2003", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2003", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2003", "Immaculate Conception");

		lh.addStaticHoliday ("24-DEC-2003", "Christmas Eve");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("05-APR-2004", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("08-APR-2004", "Holy Thursday");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("25-MAY-2004", "Liberation Day");

		lh.addStaticHoliday ("21-JUN-2004", "Flag Day");

		lh.addStaticHoliday ("02-JUL-2004", "Special Holiday");

		lh.addStaticHoliday ("05-JUL-2004", "Special Holiday");

		lh.addStaticHoliday ("09-JUL-2004", "Independence Day");

		lh.addStaticHoliday ("16-AUG-2004", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("11-OCT-2004", "Day of the Race");

		lh.addStaticHoliday ("08-DEC-2004", "Immaculate Conception");

		lh.addStaticHoliday ("24-MAR-2005", "Holy Thursday");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("25-MAY-2005", "Liberation Day");

		lh.addStaticHoliday ("20-JUN-2005", "Flag Day");

		lh.addStaticHoliday ("15-AUG-2005", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("10-OCT-2005", "Day of the Race");

		lh.addStaticHoliday ("08-DEC-2005", "Immaculate Conception");

		lh.addStaticHoliday ("24-MAR-2006", "Memorial Day");

		lh.addStaticHoliday ("13-APR-2006", "Holy Thursday");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2006", "Liberation Day");

		lh.addStaticHoliday ("19-JUN-2006", "Flag Day");

		lh.addStaticHoliday ("21-AUG-2006", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("16-OCT-2006", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2006", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2006", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("02-APR-2007", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("05-APR-2007", "Holy Thursday");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2007", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2007", "Liberation Day");

		lh.addStaticHoliday ("18-JUN-2007", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2007", "Independence Day");

		lh.addStaticHoliday ("20-AUG-2007", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("15-OCT-2007", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2007", "Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("20-MAR-2008", "Holy Thursday");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("24-MAR-2008", "Memorial Day");

		lh.addStaticHoliday ("02-APR-2008", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("01-MAY-2008", "Labour Day");

		lh.addStaticHoliday ("16-JUN-2008", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2008", "Independence Day");

		lh.addStaticHoliday ("18-AUG-2008", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("06-NOV-2008", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2008", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2009", "Memorial Day");

		lh.addStaticHoliday ("02-APR-2009", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("09-APR-2009", "Holy Thursday");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2009", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2009", "Liberation Day");

		lh.addStaticHoliday ("15-JUN-2009", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2009", "Independence Day");

		lh.addStaticHoliday ("17-AUG-2009", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("12-OCT-2009", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2009", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2009", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2010", "Memorial Day");

		lh.addStaticHoliday ("01-APR-2010", "Holy Thursday");

		lh.addStaticHoliday ("02-APR-2010", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("25-MAY-2010", "Liberation Day");

		lh.addStaticHoliday ("21-JUN-2010", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2010", "Independence Day");

		lh.addStaticHoliday ("16-AUG-2010", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("11-OCT-2010", "Day of the Race");

		lh.addStaticHoliday ("08-DEC-2010", "Immaculate Conception");

		lh.addStaticHoliday ("24-MAR-2011", "Memorial Day");

		lh.addStaticHoliday ("21-APR-2011", "Holy Thursday");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("25-MAY-2011", "Liberation Day");

		lh.addStaticHoliday ("20-JUN-2011", "Flag Day");

		lh.addStaticHoliday ("15-AUG-2011", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("10-OCT-2011", "Day of the Race");

		lh.addStaticHoliday ("08-DEC-2011", "Immaculate Conception");

		lh.addStaticHoliday ("02-APR-2012", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("05-APR-2012", "Holy Thursday");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2012", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2012", "Liberation Day");

		lh.addStaticHoliday ("18-JUN-2012", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2012", "Independence Day");

		lh.addStaticHoliday ("20-AUG-2012", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("15-OCT-2012", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2012", "Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("28-MAR-2013", "Holy Thursday");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("02-APR-2013", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("01-MAY-2013", "Labour Day");

		lh.addStaticHoliday ("17-JUN-2013", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2013", "Independence Day");

		lh.addStaticHoliday ("19-AUG-2013", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("06-NOV-2013", "Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2014", "Memorial Day");

		lh.addStaticHoliday ("02-APR-2014", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("17-APR-2014", "Holy Thursday");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2014", "Labour Day");

		lh.addStaticHoliday ("16-JUN-2014", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2014", "Independence Day");

		lh.addStaticHoliday ("18-AUG-2014", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("06-NOV-2014", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2014", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2015", "Memorial Day");

		lh.addStaticHoliday ("02-APR-2015", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2015", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2015", "Liberation Day");

		lh.addStaticHoliday ("15-JUN-2015", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2015", "Independence Day");

		lh.addStaticHoliday ("17-AUG-2015", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("12-OCT-2015", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2015", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2015", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2016", "Memorial Day");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("25-MAY-2016", "Liberation Day");

		lh.addStaticHoliday ("20-JUN-2016", "Flag Day");

		lh.addStaticHoliday ("15-AUG-2016", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("10-OCT-2016", "Day of the Race");

		lh.addStaticHoliday ("08-DEC-2016", "Immaculate Conception");

		lh.addStaticHoliday ("24-MAR-2017", "Memorial Day");

		lh.addStaticHoliday ("13-APR-2017", "Holy Thursday");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2017", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2017", "Liberation Day");

		lh.addStaticHoliday ("19-JUN-2017", "Flag Day");

		lh.addStaticHoliday ("21-AUG-2017", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("16-OCT-2017", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2017", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2017", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("29-MAR-2018", "Holy Thursday");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("02-APR-2018", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("01-MAY-2018", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2018", "Liberation Day");

		lh.addStaticHoliday ("18-JUN-2018", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2018", "Independence Day");

		lh.addStaticHoliday ("20-AUG-2018", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("15-OCT-2018", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2018", "Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("02-APR-2019", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("18-APR-2019", "Holy Thursday");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2019", "Labour Day");

		lh.addStaticHoliday ("17-JUN-2019", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2019", "Independence Day");

		lh.addStaticHoliday ("19-AUG-2019", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("06-NOV-2019", "Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2020", "Memorial Day");

		lh.addStaticHoliday ("02-APR-2020", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("09-APR-2020", "Holy Thursday");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2020", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2020", "Liberation Day");

		lh.addStaticHoliday ("15-JUN-2020", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2020", "Independence Day");

		lh.addStaticHoliday ("17-AUG-2020", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("12-OCT-2020", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2020", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2020", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2021", "Memorial Day");

		lh.addStaticHoliday ("01-APR-2021", "Holy Thursday");

		lh.addStaticHoliday ("02-APR-2021", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("25-MAY-2021", "Liberation Day");

		lh.addStaticHoliday ("21-JUN-2021", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2021", "Independence Day");

		lh.addStaticHoliday ("16-AUG-2021", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("11-OCT-2021", "Day of the Race");

		lh.addStaticHoliday ("08-DEC-2021", "Immaculate Conception");

		lh.addStaticHoliday ("24-MAR-2022", "Memorial Day");

		lh.addStaticHoliday ("14-APR-2022", "Holy Thursday");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("25-MAY-2022", "Liberation Day");

		lh.addStaticHoliday ("20-JUN-2022", "Flag Day");

		lh.addStaticHoliday ("15-AUG-2022", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("10-OCT-2022", "Day of the Race");

		lh.addStaticHoliday ("08-DEC-2022", "Immaculate Conception");

		lh.addStaticHoliday ("24-MAR-2023", "Memorial Day");

		lh.addStaticHoliday ("06-APR-2023", "Holy Thursday");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2023", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2023", "Liberation Day");

		lh.addStaticHoliday ("19-JUN-2023", "Flag Day");

		lh.addStaticHoliday ("21-AUG-2023", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("16-OCT-2023", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2023", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2023", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("28-MAR-2024", "Holy Thursday");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("02-APR-2024", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("01-MAY-2024", "Labour Day");

		lh.addStaticHoliday ("17-JUN-2024", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2024", "Independence Day");

		lh.addStaticHoliday ("19-AUG-2024", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("06-NOV-2024", "Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2025", "Memorial Day");

		lh.addStaticHoliday ("02-APR-2025", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("17-APR-2025", "Holy Thursday");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2025", "Labour Day");

		lh.addStaticHoliday ("16-JUN-2025", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2025", "Independence Day");

		lh.addStaticHoliday ("18-AUG-2025", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("06-NOV-2025", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2025", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2026", "Memorial Day");

		lh.addStaticHoliday ("02-APR-2026", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2026", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2026", "Liberation Day");

		lh.addStaticHoliday ("15-JUN-2026", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2026", "Independence Day");

		lh.addStaticHoliday ("17-AUG-2026", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("12-OCT-2026", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2026", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2026", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2027", "Memorial Day");

		lh.addStaticHoliday ("25-MAR-2027", "Holy Thursday");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("02-APR-2027", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("25-MAY-2027", "Liberation Day");

		lh.addStaticHoliday ("21-JUN-2027", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2027", "Independence Day");

		lh.addStaticHoliday ("16-AUG-2027", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("11-OCT-2027", "Day of the Race");

		lh.addStaticHoliday ("08-DEC-2027", "Immaculate Conception");

		lh.addStaticHoliday ("24-MAR-2028", "Memorial Day");

		lh.addStaticHoliday ("13-APR-2028", "Holy Thursday");

		lh.addStaticHoliday ("14-APR-2028", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2028", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2028", "Liberation Day");

		lh.addStaticHoliday ("19-JUN-2028", "Flag Day");

		lh.addStaticHoliday ("21-AUG-2028", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("16-OCT-2028", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2028", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2028", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2029", "New Years Day");

		lh.addStaticHoliday ("29-MAR-2029", "Holy Thursday");

		lh.addStaticHoliday ("30-MAR-2029", "Good Friday");

		lh.addStaticHoliday ("02-APR-2029", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("01-MAY-2029", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2029", "Liberation Day");

		lh.addStaticHoliday ("18-JUN-2029", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2029", "Independence Day");

		lh.addStaticHoliday ("20-AUG-2029", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("15-OCT-2029", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2029", "Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2029", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2030", "New Years Day");

		lh.addStaticHoliday ("02-APR-2030", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("18-APR-2030", "Holy Thursday");

		lh.addStaticHoliday ("19-APR-2030", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2030", "Labour Day");

		lh.addStaticHoliday ("17-JUN-2030", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2030", "Independence Day");

		lh.addStaticHoliday ("19-AUG-2030", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("06-NOV-2030", "Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2030", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2031", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2031", "Memorial Day");

		lh.addStaticHoliday ("02-APR-2031", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("10-APR-2031", "Holy Thursday");

		lh.addStaticHoliday ("11-APR-2031", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2031", "Labour Day");

		lh.addStaticHoliday ("16-JUN-2031", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2031", "Independence Day");

		lh.addStaticHoliday ("18-AUG-2031", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("06-NOV-2031", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2031", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2031", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2032", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2032", "Memorial Day");

		lh.addStaticHoliday ("25-MAR-2032", "Holy Thursday");

		lh.addStaticHoliday ("26-MAR-2032", "Good Friday");

		lh.addStaticHoliday ("02-APR-2032", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("25-MAY-2032", "Liberation Day");

		lh.addStaticHoliday ("21-JUN-2032", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2032", "Independence Day");

		lh.addStaticHoliday ("16-AUG-2032", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("11-OCT-2032", "Day of the Race");

		lh.addStaticHoliday ("08-DEC-2032", "Immaculate Conception");

		lh.addStaticHoliday ("24-MAR-2033", "Memorial Day");

		lh.addStaticHoliday ("14-APR-2033", "Holy Thursday");

		lh.addStaticHoliday ("15-APR-2033", "Good Friday");

		lh.addStaticHoliday ("25-MAY-2033", "Liberation Day");

		lh.addStaticHoliday ("20-JUN-2033", "Flag Day");

		lh.addStaticHoliday ("15-AUG-2033", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("10-OCT-2033", "Day of the Race");

		lh.addStaticHoliday ("08-DEC-2033", "Immaculate Conception");

		lh.addStaticHoliday ("24-MAR-2034", "Memorial Day");

		lh.addStaticHoliday ("06-APR-2034", "Holy Thursday");

		lh.addStaticHoliday ("07-APR-2034", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2034", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2034", "Liberation Day");

		lh.addStaticHoliday ("19-JUN-2034", "Flag Day");

		lh.addStaticHoliday ("21-AUG-2034", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("16-OCT-2034", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2034", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2034", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2034", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2035", "New Years Day");

		lh.addStaticHoliday ("22-MAR-2035", "Holy Thursday");

		lh.addStaticHoliday ("23-MAR-2035", "Good Friday");

		lh.addStaticHoliday ("02-APR-2035", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("01-MAY-2035", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2035", "Liberation Day");

		lh.addStaticHoliday ("18-JUN-2035", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2035", "Independence Day");

		lh.addStaticHoliday ("20-AUG-2035", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("15-OCT-2035", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2035", "Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2035", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2036", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2036", "Memorial Day");

		lh.addStaticHoliday ("02-APR-2036", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("10-APR-2036", "Holy Thursday");

		lh.addStaticHoliday ("11-APR-2036", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2036", "Labour Day");

		lh.addStaticHoliday ("16-JUN-2036", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2036", "Independence Day");

		lh.addStaticHoliday ("18-AUG-2036", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("06-NOV-2036", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2036", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2036", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2037", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2037", "Memorial Day");

		lh.addStaticHoliday ("02-APR-2037", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("03-APR-2037", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2037", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2037", "Liberation Day");

		lh.addStaticHoliday ("15-JUN-2037", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2037", "Independence Day");

		lh.addStaticHoliday ("17-AUG-2037", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("12-OCT-2037", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2037", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2037", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2037", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2038", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2038", "Memorial Day");

		lh.addStaticHoliday ("02-APR-2038", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("22-APR-2038", "Holy Thursday");

		lh.addStaticHoliday ("23-APR-2038", "Good Friday");

		lh.addStaticHoliday ("25-MAY-2038", "Liberation Day");

		lh.addStaticHoliday ("21-JUN-2038", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2038", "Independence Day");

		lh.addStaticHoliday ("16-AUG-2038", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("11-OCT-2038", "Day of the Race");

		lh.addStaticHoliday ("08-DEC-2038", "Immaculate Conception");

		lh.addStaticHoliday ("24-MAR-2039", "Memorial Day");

		lh.addStaticHoliday ("07-APR-2039", "Holy Thursday");

		lh.addStaticHoliday ("08-APR-2039", "Good Friday");

		lh.addStaticHoliday ("25-MAY-2039", "Liberation Day");

		lh.addStaticHoliday ("20-JUN-2039", "Flag Day");

		lh.addStaticHoliday ("15-AUG-2039", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("10-OCT-2039", "Day of the Race");

		lh.addStaticHoliday ("08-DEC-2039", "Immaculate Conception");

		lh.addStaticHoliday ("29-MAR-2040", "Holy Thursday");

		lh.addStaticHoliday ("30-MAR-2040", "Good Friday");

		lh.addStaticHoliday ("02-APR-2040", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("01-MAY-2040", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2040", "Liberation Day");

		lh.addStaticHoliday ("18-JUN-2040", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2040", "Independence Day");

		lh.addStaticHoliday ("20-AUG-2040", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("15-OCT-2040", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2040", "Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2040", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2041", "New Years Day");

		lh.addStaticHoliday ("02-APR-2041", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("18-APR-2041", "Holy Thursday");

		lh.addStaticHoliday ("19-APR-2041", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2041", "Labour Day");

		lh.addStaticHoliday ("17-JUN-2041", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2041", "Independence Day");

		lh.addStaticHoliday ("19-AUG-2041", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("06-NOV-2041", "Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2041", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2042", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2042", "Memorial Day");

		lh.addStaticHoliday ("02-APR-2042", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("03-APR-2042", "Holy Thursday");

		lh.addStaticHoliday ("04-APR-2042", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2042", "Labour Day");

		lh.addStaticHoliday ("16-JUN-2042", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2042", "Independence Day");

		lh.addStaticHoliday ("18-AUG-2042", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("06-NOV-2042", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2042", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2042", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2043", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2043", "Memorial Day");

		lh.addStaticHoliday ("26-MAR-2043", "Holy Thursday");

		lh.addStaticHoliday ("27-MAR-2043", "Good Friday");

		lh.addStaticHoliday ("02-APR-2043", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("01-MAY-2043", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2043", "Liberation Day");

		lh.addStaticHoliday ("15-JUN-2043", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2043", "Independence Day");

		lh.addStaticHoliday ("17-AUG-2043", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("12-OCT-2043", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2043", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2043", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2043", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2044", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2044", "Memorial Day");

		lh.addStaticHoliday ("14-APR-2044", "Holy Thursday");

		lh.addStaticHoliday ("15-APR-2044", "Good Friday");

		lh.addStaticHoliday ("25-MAY-2044", "Liberation Day");

		lh.addStaticHoliday ("20-JUN-2044", "Flag Day");

		lh.addStaticHoliday ("15-AUG-2044", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("10-OCT-2044", "Day of the Race");

		lh.addStaticHoliday ("08-DEC-2044", "Immaculate Conception");

		lh.addStaticHoliday ("24-MAR-2045", "Memorial Day");

		lh.addStaticHoliday ("06-APR-2045", "Holy Thursday");

		lh.addStaticHoliday ("07-APR-2045", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2045", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2045", "Liberation Day");

		lh.addStaticHoliday ("19-JUN-2045", "Flag Day");

		lh.addStaticHoliday ("21-AUG-2045", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("16-OCT-2045", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2045", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2045", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2045", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2046", "New Years Day");

		lh.addStaticHoliday ("22-MAR-2046", "Holy Thursday");

		lh.addStaticHoliday ("23-MAR-2046", "Good Friday");

		lh.addStaticHoliday ("02-APR-2046", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("01-MAY-2046", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2046", "Liberation Day");

		lh.addStaticHoliday ("18-JUN-2046", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2046", "Independence Day");

		lh.addStaticHoliday ("20-AUG-2046", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("15-OCT-2046", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2046", "Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2046", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2047", "New Years Day");

		lh.addStaticHoliday ("02-APR-2047", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("11-APR-2047", "Holy Thursday");

		lh.addStaticHoliday ("12-APR-2047", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2047", "Labour Day");

		lh.addStaticHoliday ("17-JUN-2047", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2047", "Independence Day");

		lh.addStaticHoliday ("19-AUG-2047", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("06-NOV-2047", "Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2047", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2048", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2048", "Memorial Day");

		lh.addStaticHoliday ("02-APR-2048", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("03-APR-2048", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2048", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2048", "Liberation Day");

		lh.addStaticHoliday ("15-JUN-2048", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2048", "Independence Day");

		lh.addStaticHoliday ("17-AUG-2048", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("12-OCT-2048", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2048", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2048", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2048", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2049", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2049", "Memorial Day");

		lh.addStaticHoliday ("02-APR-2049", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("15-APR-2049", "Holy Thursday");

		lh.addStaticHoliday ("16-APR-2049", "Good Friday");

		lh.addStaticHoliday ("25-MAY-2049", "Liberation Day");

		lh.addStaticHoliday ("21-JUN-2049", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2049", "Independence Day");

		lh.addStaticHoliday ("16-AUG-2049", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("11-OCT-2049", "Day of the Race");

		lh.addStaticHoliday ("08-DEC-2049", "Immaculate Conception");

		lh.addStaticHoliday ("24-MAR-2050", "Memorial Day");

		lh.addStaticHoliday ("07-APR-2050", "Holy Thursday");

		lh.addStaticHoliday ("08-APR-2050", "Good Friday");

		lh.addStaticHoliday ("25-MAY-2050", "Liberation Day");

		lh.addStaticHoliday ("20-JUN-2050", "Flag Day");

		lh.addStaticHoliday ("15-AUG-2050", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("10-OCT-2050", "Day of the Race");

		lh.addStaticHoliday ("08-DEC-2050", "Immaculate Conception");

		lh.addStaticHoliday ("24-MAR-2051", "Memorial Day");

		lh.addStaticHoliday ("30-MAR-2051", "Holy Thursday");

		lh.addStaticHoliday ("31-MAR-2051", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2051", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2051", "Liberation Day");

		lh.addStaticHoliday ("19-JUN-2051", "Flag Day");

		lh.addStaticHoliday ("21-AUG-2051", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("16-OCT-2051", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2051", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2051", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2051", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2052", "New Years Day");

		lh.addStaticHoliday ("02-APR-2052", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("18-APR-2052", "Holy Thursday");

		lh.addStaticHoliday ("19-APR-2052", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2052", "Labour Day");

		lh.addStaticHoliday ("17-JUN-2052", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2052", "Independence Day");

		lh.addStaticHoliday ("19-AUG-2052", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("06-NOV-2052", "Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2052", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2053", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2053", "Memorial Day");

		lh.addStaticHoliday ("02-APR-2053", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("03-APR-2053", "Holy Thursday");

		lh.addStaticHoliday ("04-APR-2053", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2053", "Labour Day");

		lh.addStaticHoliday ("16-JUN-2053", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2053", "Independence Day");

		lh.addStaticHoliday ("18-AUG-2053", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("06-NOV-2053", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2053", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2053", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2054", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2054", "Memorial Day");

		lh.addStaticHoliday ("26-MAR-2054", "Holy Thursday");

		lh.addStaticHoliday ("27-MAR-2054", "Good Friday");

		lh.addStaticHoliday ("02-APR-2054", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("01-MAY-2054", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2054", "Liberation Day");

		lh.addStaticHoliday ("15-JUN-2054", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2054", "Independence Day");

		lh.addStaticHoliday ("17-AUG-2054", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("12-OCT-2054", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2054", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2054", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2054", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2055", "New Years Day");

		lh.addStaticHoliday ("24-MAR-2055", "Memorial Day");

		lh.addStaticHoliday ("02-APR-2055", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("15-APR-2055", "Holy Thursday");

		lh.addStaticHoliday ("16-APR-2055", "Good Friday");

		lh.addStaticHoliday ("25-MAY-2055", "Liberation Day");

		lh.addStaticHoliday ("21-JUN-2055", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2055", "Independence Day");

		lh.addStaticHoliday ("16-AUG-2055", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("11-OCT-2055", "Day of the Race");

		lh.addStaticHoliday ("08-DEC-2055", "Immaculate Conception");

		lh.addStaticHoliday ("24-MAR-2056", "Memorial Day");

		lh.addStaticHoliday ("30-MAR-2056", "Holy Thursday");

		lh.addStaticHoliday ("31-MAR-2056", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2056", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2056", "Liberation Day");

		lh.addStaticHoliday ("19-JUN-2056", "Flag Day");

		lh.addStaticHoliday ("21-AUG-2056", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("16-OCT-2056", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2056", "Bank Holiday");

		lh.addStaticHoliday ("08-DEC-2056", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2056", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2057", "New Years Day");

		lh.addStaticHoliday ("02-APR-2057", "Malvinas Islands Memorial Day");

		lh.addStaticHoliday ("19-APR-2057", "Holy Thursday");

		lh.addStaticHoliday ("20-APR-2057", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2057", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2057", "Liberation Day");

		lh.addStaticHoliday ("18-JUN-2057", "Flag Day");

		lh.addStaticHoliday ("09-JUL-2057", "Independence Day");

		lh.addStaticHoliday ("20-AUG-2057", "Anniversary of the Death of General San Martin");

		lh.addStaticHoliday ("15-OCT-2057", "Day of the Race");

		lh.addStaticHoliday ("06-NOV-2057", "Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2057", "Christmas Day");

		lh.addStandardWeekend();

		return lh;
	}
}
