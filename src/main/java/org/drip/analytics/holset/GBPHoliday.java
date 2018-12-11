
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

public class GBPHoliday implements org.drip.analytics.holset.LocationHoliday {
	public GBPHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "GBP";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("10-APR-1998", "Good Friday");

		lh.addStaticHoliday ("13-APR-1998", "Easter Monday");

		lh.addStaticHoliday ("04-MAY-1998", "May Day");

		lh.addStaticHoliday ("25-MAY-1998", "Spring Bank Holiday");

		lh.addStaticHoliday ("31-AUG-1998", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-1998", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-1998", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("05-APR-1999", "Easter Monday");

		lh.addStaticHoliday ("03-MAY-1999", "May Day");

		lh.addStaticHoliday ("31-MAY-1999", "Spring Bank Holiday");

		lh.addStaticHoliday ("30-AUG-1999", "August Bank Holiday");

		lh.addStaticHoliday ("27-DEC-1999", "Boxing Day Observed");

		lh.addStaticHoliday ("28-DEC-1999", "Christmas Day Observed");

		lh.addStaticHoliday ("31-DEC-1999", "Special Holiday");

		lh.addStaticHoliday ("03-JAN-2000", "New Years Day Observed");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("24-APR-2000", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2000", "May Day");

		lh.addStaticHoliday ("29-MAY-2000", "Spring Bank Holiday");

		lh.addStaticHoliday ("28-AUG-2000", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2000", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("16-APR-2001", "Easter Monday");

		lh.addStaticHoliday ("07-MAY-2001", "May Day");

		lh.addStaticHoliday ("28-MAY-2001", "Spring Bank Holiday");

		lh.addStaticHoliday ("27-AUG-2001", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2001", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("01-APR-2002", "Easter Monday");

		lh.addStaticHoliday ("06-MAY-2002", "May Day");

		lh.addStaticHoliday ("03-JUN-2002", "Golden Jubilee");

		lh.addStaticHoliday ("04-JUN-2002", "Golden Jubilee");

		lh.addStaticHoliday ("26-AUG-2002", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2002", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("21-APR-2003", "Easter Monday");

		lh.addStaticHoliday ("05-MAY-2003", "May Day");

		lh.addStaticHoliday ("26-MAY-2003", "Spring Bank Holiday");

		lh.addStaticHoliday ("25-AUG-2003", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2003", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("12-APR-2004", "Easter Monday");

		lh.addStaticHoliday ("03-MAY-2004", "May Day");

		lh.addStaticHoliday ("31-MAY-2004", "Spring Bank Holiday");

		lh.addStaticHoliday ("30-AUG-2004", "August Bank Holiday");

		lh.addStaticHoliday ("27-DEC-2004", "Boxing Day Observed");

		lh.addStaticHoliday ("28-DEC-2004", "Christmas Day Observed");

		lh.addStaticHoliday ("03-JAN-2005", "New Years Day Observed");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("28-MAR-2005", "Easter Monday");

		lh.addStaticHoliday ("02-MAY-2005", "May Day");

		lh.addStaticHoliday ("30-MAY-2005", "Spring Bank Holiday");

		lh.addStaticHoliday ("29-AUG-2005", "August Bank Holiday");

		lh.addStaticHoliday ("26-DEC-2005", "Boxing Day");

		lh.addStaticHoliday ("27-DEC-2005", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2006", "New Years Day Observed");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("17-APR-2006", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2006", "May Day");

		lh.addStaticHoliday ("29-MAY-2006", "Spring Bank Holiday");

		lh.addStaticHoliday ("28-AUG-2006", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2006", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("09-APR-2007", "Easter Monday");

		lh.addStaticHoliday ("07-MAY-2007", "May Day");

		lh.addStaticHoliday ("28-MAY-2007", "Spring Bank Holiday");

		lh.addStaticHoliday ("27-AUG-2007", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2007", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("24-MAR-2008", "Easter Monday");

		lh.addStaticHoliday ("05-MAY-2008", "May Day");

		lh.addStaticHoliday ("26-MAY-2008", "Spring Bank Holiday");

		lh.addStaticHoliday ("25-AUG-2008", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2008", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("13-APR-2009", "Easter Monday");

		lh.addStaticHoliday ("04-MAY-2009", "May Day");

		lh.addStaticHoliday ("25-MAY-2009", "Spring Bank Holiday");

		lh.addStaticHoliday ("31-AUG-2009", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2009", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("05-APR-2010", "Easter Monday");

		lh.addStaticHoliday ("03-MAY-2010", "May Day");

		lh.addStaticHoliday ("31-MAY-2010", "Spring Bank Holiday");

		lh.addStaticHoliday ("30-AUG-2010", "August Bank Holiday");

		lh.addStaticHoliday ("27-DEC-2010", "Boxing Day Observed");

		lh.addStaticHoliday ("28-DEC-2010", "Christmas Day Observed");

		lh.addStaticHoliday ("03-JAN-2011", "New Years Day Observed");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("25-APR-2011", "Easter Monday");

		lh.addStaticHoliday ("29-APR-2011", "Royal Wedding");

		lh.addStaticHoliday ("02-MAY-2011", "May Day");

		lh.addStaticHoliday ("30-MAY-2011", "Spring Bank Holiday");

		lh.addStaticHoliday ("29-AUG-2011", "August Bank Holiday");

		lh.addStaticHoliday ("26-DEC-2011", "Boxing Day");

		lh.addStaticHoliday ("27-DEC-2011", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2012", "New Years Day Observed");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("09-APR-2012", "Easter Monday");

		lh.addStaticHoliday ("07-MAY-2012", "May Day");

		lh.addStaticHoliday ("04-JUN-2012", "Spring Bank Holiday");

		lh.addStaticHoliday ("05-JUN-2012", "Queens Diamond Jubilee");

		lh.addStaticHoliday ("27-AUG-2012", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2012", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("01-APR-2013", "Easter Monday");

		lh.addStaticHoliday ("06-MAY-2013", "May Day");

		lh.addStaticHoliday ("27-MAY-2013", "Spring Bank Holiday");

		lh.addStaticHoliday ("26-AUG-2013", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2013", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("21-APR-2014", "Easter Monday");

		lh.addStaticHoliday ("05-MAY-2014", "May Day");

		lh.addStaticHoliday ("26-MAY-2014", "Spring Bank Holiday");

		lh.addStaticHoliday ("25-AUG-2014", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2014", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("06-APR-2015", "Easter Monday");

		lh.addStaticHoliday ("04-MAY-2015", "May Day");

		lh.addStaticHoliday ("25-MAY-2015", "Spring Bank Holiday");

		lh.addStaticHoliday ("31-AUG-2015", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2015", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("28-MAR-2016", "Easter Monday");

		lh.addStaticHoliday ("02-MAY-2016", "May Day");

		lh.addStaticHoliday ("30-MAY-2016", "Spring Bank Holiday");

		lh.addStaticHoliday ("29-AUG-2016", "August Bank Holiday");

		lh.addStaticHoliday ("26-DEC-2016", "Boxing Day");

		lh.addStaticHoliday ("27-DEC-2016", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2017", "New Years Day Observed");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("17-APR-2017", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2017", "May Day");

		lh.addStaticHoliday ("29-MAY-2017", "Spring Bank Holiday");

		lh.addStaticHoliday ("28-AUG-2017", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2017", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("02-APR-2018", "Easter Monday");

		lh.addStaticHoliday ("07-MAY-2018", "May Day");

		lh.addStaticHoliday ("28-MAY-2018", "Spring Bank Holiday");

		lh.addStaticHoliday ("27-AUG-2018", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2018", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("22-APR-2019", "Easter Monday");

		lh.addStaticHoliday ("06-MAY-2019", "May Day");

		lh.addStaticHoliday ("27-MAY-2019", "Spring Bank Holiday");

		lh.addStaticHoliday ("26-AUG-2019", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2019", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("13-APR-2020", "Easter Monday");

		lh.addStaticHoliday ("04-MAY-2020", "May Day");

		lh.addStaticHoliday ("25-MAY-2020", "Spring Bank Holiday");

		lh.addStaticHoliday ("31-AUG-2020", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2020", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("05-APR-2021", "Easter Monday");

		lh.addStaticHoliday ("03-MAY-2021", "May Day");

		lh.addStaticHoliday ("31-MAY-2021", "Spring Bank Holiday");

		lh.addStaticHoliday ("30-AUG-2021", "August Bank Holiday");

		lh.addStaticHoliday ("27-DEC-2021", "Boxing Day Observed");

		lh.addStaticHoliday ("28-DEC-2021", "Christmas Day Observed");

		lh.addStaticHoliday ("03-JAN-2022", "New Years Day Observed");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("18-APR-2022", "Easter Monday");

		lh.addStaticHoliday ("02-MAY-2022", "May Day");

		lh.addStaticHoliday ("30-MAY-2022", "Spring Bank Holiday");

		lh.addStaticHoliday ("29-AUG-2022", "August Bank Holiday");

		lh.addStaticHoliday ("26-DEC-2022", "Boxing Day");

		lh.addStaticHoliday ("27-DEC-2022", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2023", "New Years Day Observed");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("10-APR-2023", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2023", "May Day");

		lh.addStaticHoliday ("29-MAY-2023", "Spring Bank Holiday");

		lh.addStaticHoliday ("28-AUG-2023", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2023", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("01-APR-2024", "Easter Monday");

		lh.addStaticHoliday ("06-MAY-2024", "May Day");

		lh.addStaticHoliday ("27-MAY-2024", "Spring Bank Holiday");

		lh.addStaticHoliday ("26-AUG-2024", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2024", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("21-APR-2025", "Easter Monday");

		lh.addStaticHoliday ("05-MAY-2025", "May Day");

		lh.addStaticHoliday ("26-MAY-2025", "Spring Bank Holiday");

		lh.addStaticHoliday ("25-AUG-2025", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2025", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("06-APR-2026", "Easter Monday");

		lh.addStaticHoliday ("04-MAY-2026", "May Day");

		lh.addStaticHoliday ("25-MAY-2026", "Spring Bank Holiday");

		lh.addStaticHoliday ("31-AUG-2026", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2026", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("29-MAR-2027", "Easter Monday");

		lh.addStaticHoliday ("03-MAY-2027", "May Day");

		lh.addStaticHoliday ("31-MAY-2027", "Spring Bank Holiday");

		lh.addStaticHoliday ("30-AUG-2027", "August Bank Holiday");

		lh.addStaticHoliday ("27-DEC-2027", "Boxing Day Observed");

		lh.addStaticHoliday ("28-DEC-2027", "Christmas Day Observed");

		lh.addStaticHoliday ("03-JAN-2028", "New Years Day Observed");

		lh.addStaticHoliday ("14-APR-2028", "Good Friday");

		lh.addStaticHoliday ("17-APR-2028", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2028", "May Day");

		lh.addStaticHoliday ("29-MAY-2028", "Spring Bank Holiday");

		lh.addStaticHoliday ("28-AUG-2028", "August Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2028", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2029", "Holiday");

		lh.addStaticHoliday ("30-MAR-2029", "Holiday");

		lh.addStaticHoliday ("02-APR-2029", "Holiday");

		lh.addStaticHoliday ("07-MAY-2029", "Holiday");

		lh.addStaticHoliday ("28-MAY-2029", "Holiday");

		lh.addStaticHoliday ("27-AUG-2029", "Holiday");

		lh.addStaticHoliday ("25-DEC-2029", "Holiday");

		lh.addStaticHoliday ("26-DEC-2029", "Holiday");

		lh.addStaticHoliday ("01-JAN-2030", "Holiday");

		lh.addStaticHoliday ("19-APR-2030", "Holiday");

		lh.addStaticHoliday ("22-APR-2030", "Holiday");

		lh.addStaticHoliday ("06-MAY-2030", "Holiday");

		lh.addStaticHoliday ("27-MAY-2030", "Holiday");

		lh.addStaticHoliday ("26-AUG-2030", "Holiday");

		lh.addStaticHoliday ("25-DEC-2030", "Holiday");

		lh.addStaticHoliday ("26-DEC-2030", "Holiday");

		lh.addStaticHoliday ("01-JAN-2031", "Holiday");

		lh.addStaticHoliday ("11-APR-2031", "Holiday");

		lh.addStaticHoliday ("14-APR-2031", "Holiday");

		lh.addStaticHoliday ("05-MAY-2031", "Holiday");

		lh.addStaticHoliday ("26-MAY-2031", "Holiday");

		lh.addStaticHoliday ("25-AUG-2031", "Holiday");

		lh.addStaticHoliday ("25-DEC-2031", "Holiday");

		lh.addStaticHoliday ("26-DEC-2031", "Holiday");

		lh.addStaticHoliday ("01-JAN-2032", "Holiday");

		lh.addStaticHoliday ("26-MAR-2032", "Holiday");

		lh.addStaticHoliday ("29-MAR-2032", "Holiday");

		lh.addStaticHoliday ("03-MAY-2032", "Holiday");

		lh.addStaticHoliday ("31-MAY-2032", "Holiday");

		lh.addStaticHoliday ("30-AUG-2032", "Holiday");

		lh.addStaticHoliday ("27-DEC-2032", "Holiday");

		lh.addStaticHoliday ("28-DEC-2032", "Holiday");

		lh.addStaticHoliday ("03-JAN-2033", "Holiday");

		lh.addStaticHoliday ("15-APR-2033", "Holiday");

		lh.addStaticHoliday ("18-APR-2033", "Holiday");

		lh.addStaticHoliday ("02-MAY-2033", "Holiday");

		lh.addStaticHoliday ("30-MAY-2033", "Holiday");

		lh.addStaticHoliday ("29-AUG-2033", "Holiday");

		lh.addStaticHoliday ("26-DEC-2033", "Holiday");

		lh.addStaticHoliday ("27-DEC-2033", "Holiday");

		lh.addStaticHoliday ("02-JAN-2034", "Holiday");

		lh.addStaticHoliday ("07-APR-2034", "Holiday");

		lh.addStaticHoliday ("10-APR-2034", "Holiday");

		lh.addStaticHoliday ("01-MAY-2034", "Holiday");

		lh.addStaticHoliday ("29-MAY-2034", "Holiday");

		lh.addStaticHoliday ("28-AUG-2034", "Holiday");

		lh.addStaticHoliday ("25-DEC-2034", "Holiday");

		lh.addStaticHoliday ("26-DEC-2034", "Holiday");

		lh.addStaticHoliday ("01-JAN-2035", "Holiday");

		lh.addStaticHoliday ("23-MAR-2035", "Holiday");

		lh.addStaticHoliday ("26-MAR-2035", "Holiday");

		lh.addStaticHoliday ("07-MAY-2035", "Holiday");

		lh.addStaticHoliday ("28-MAY-2035", "Holiday");

		lh.addStaticHoliday ("27-AUG-2035", "Holiday");

		lh.addStaticHoliday ("25-DEC-2035", "Holiday");

		lh.addStaticHoliday ("26-DEC-2035", "Holiday");

		lh.addStaticHoliday ("01-JAN-2036", "Holiday");

		lh.addStaticHoliday ("11-APR-2036", "Holiday");

		lh.addStaticHoliday ("14-APR-2036", "Holiday");

		lh.addStaticHoliday ("05-MAY-2036", "Holiday");

		lh.addStaticHoliday ("26-MAY-2036", "Holiday");

		lh.addStaticHoliday ("25-AUG-2036", "Holiday");

		lh.addStaticHoliday ("25-DEC-2036", "Holiday");

		lh.addStaticHoliday ("26-DEC-2036", "Holiday");

		lh.addStaticHoliday ("01-JAN-2037", "Holiday");

		lh.addStaticHoliday ("03-APR-2037", "Holiday");

		lh.addStaticHoliday ("06-APR-2037", "Holiday");

		lh.addStaticHoliday ("04-MAY-2037", "Holiday");

		lh.addStaticHoliday ("25-MAY-2037", "Holiday");

		lh.addStaticHoliday ("31-AUG-2037", "Holiday");

		lh.addStaticHoliday ("25-DEC-2037", "Holiday");

		lh.addStaticHoliday ("28-DEC-2037", "Holiday");

		lh.addStaticHoliday ("01-JAN-2038", "Holiday");

		lh.addStaticHoliday ("23-APR-2038", "Holiday");

		lh.addStaticHoliday ("26-APR-2038", "Holiday");

		lh.addStaticHoliday ("03-MAY-2038", "Holiday");

		lh.addStaticHoliday ("31-MAY-2038", "Holiday");

		lh.addStaticHoliday ("30-AUG-2038", "Holiday");

		lh.addStaticHoliday ("27-DEC-2038", "Holiday");

		lh.addStaticHoliday ("28-DEC-2038", "Holiday");

		lh.addStaticHoliday ("03-JAN-2039", "Holiday");

		lh.addStaticHoliday ("08-APR-2039", "Holiday");

		lh.addStaticHoliday ("11-APR-2039", "Holiday");

		lh.addStaticHoliday ("02-MAY-2039", "Holiday");

		lh.addStaticHoliday ("30-MAY-2039", "Holiday");

		lh.addStaticHoliday ("29-AUG-2039", "Holiday");

		lh.addStaticHoliday ("26-DEC-2039", "Holiday");

		lh.addStaticHoliday ("27-DEC-2039", "Holiday");

		lh.addStaticHoliday ("02-JAN-2040", "Holiday");

		lh.addStaticHoliday ("30-MAR-2040", "Holiday");

		lh.addStaticHoliday ("02-APR-2040", "Holiday");

		lh.addStaticHoliday ("07-MAY-2040", "Holiday");

		lh.addStaticHoliday ("28-MAY-2040", "Holiday");

		lh.addStaticHoliday ("27-AUG-2040", "Holiday");

		lh.addStaticHoliday ("25-DEC-2040", "Holiday");

		lh.addStaticHoliday ("26-DEC-2040", "Holiday");

		lh.addStaticHoliday ("01-JAN-2041", "Holiday");

		lh.addStaticHoliday ("19-APR-2041", "Holiday");

		lh.addStaticHoliday ("22-APR-2041", "Holiday");

		lh.addStaticHoliday ("06-MAY-2041", "Holiday");

		lh.addStaticHoliday ("27-MAY-2041", "Holiday");

		lh.addStaticHoliday ("26-AUG-2041", "Holiday");

		lh.addStaticHoliday ("25-DEC-2041", "Holiday");

		lh.addStaticHoliday ("26-DEC-2041", "Holiday");

		lh.addStaticHoliday ("01-JAN-2042", "Holiday");

		lh.addStaticHoliday ("04-APR-2042", "Holiday");

		lh.addStaticHoliday ("07-APR-2042", "Holiday");

		lh.addStaticHoliday ("05-MAY-2042", "Holiday");

		lh.addStaticHoliday ("26-MAY-2042", "Holiday");

		lh.addStaticHoliday ("25-AUG-2042", "Holiday");

		lh.addStaticHoliday ("25-DEC-2042", "Holiday");

		lh.addStaticHoliday ("26-DEC-2042", "Holiday");

		lh.addStaticHoliday ("01-JAN-2043", "Holiday");

		lh.addStaticHoliday ("27-MAR-2043", "Holiday");

		lh.addStaticHoliday ("30-MAR-2043", "Holiday");

		lh.addStaticHoliday ("04-MAY-2043", "Holiday");

		lh.addStaticHoliday ("25-MAY-2043", "Holiday");

		lh.addStaticHoliday ("31-AUG-2043", "Holiday");

		lh.addStaticHoliday ("25-DEC-2043", "Holiday");

		lh.addStaticHoliday ("28-DEC-2043", "Holiday");

		lh.addStaticHoliday ("01-JAN-2044", "Holiday");

		lh.addStaticHoliday ("15-APR-2044", "Holiday");

		lh.addStaticHoliday ("18-APR-2044", "Holiday");

		lh.addStaticHoliday ("02-MAY-2044", "Holiday");

		lh.addStaticHoliday ("30-MAY-2044", "Holiday");

		lh.addStaticHoliday ("29-AUG-2044", "Holiday");

		lh.addStaticHoliday ("26-DEC-2044", "Holiday");

		lh.addStaticHoliday ("27-DEC-2044", "Holiday");

		lh.addStaticHoliday ("02-JAN-2045", "Holiday");

		lh.addStaticHoliday ("07-APR-2045", "Holiday");

		lh.addStaticHoliday ("10-APR-2045", "Holiday");

		lh.addStaticHoliday ("01-MAY-2045", "Holiday");

		lh.addStaticHoliday ("29-MAY-2045", "Holiday");

		lh.addStaticHoliday ("28-AUG-2045", "Holiday");

		lh.addStaticHoliday ("25-DEC-2045", "Holiday");

		lh.addStaticHoliday ("26-DEC-2045", "Holiday");

		lh.addStaticHoliday ("01-JAN-2046", "Holiday");

		lh.addStaticHoliday ("23-MAR-2046", "Holiday");

		lh.addStaticHoliday ("26-MAR-2046", "Holiday");

		lh.addStaticHoliday ("07-MAY-2046", "Holiday");

		lh.addStaticHoliday ("28-MAY-2046", "Holiday");

		lh.addStaticHoliday ("27-AUG-2046", "Holiday");

		lh.addStaticHoliday ("25-DEC-2046", "Holiday");

		lh.addStaticHoliday ("26-DEC-2046", "Holiday");

		lh.addStaticHoliday ("01-JAN-2047", "Holiday");

		lh.addStaticHoliday ("12-APR-2047", "Holiday");

		lh.addStaticHoliday ("15-APR-2047", "Holiday");

		lh.addStaticHoliday ("06-MAY-2047", "Holiday");

		lh.addStaticHoliday ("27-MAY-2047", "Holiday");

		lh.addStaticHoliday ("26-AUG-2047", "Holiday");

		lh.addStaticHoliday ("25-DEC-2047", "Holiday");

		lh.addStaticHoliday ("26-DEC-2047", "Holiday");

		lh.addStaticHoliday ("01-JAN-2048", "Holiday");

		lh.addStaticHoliday ("03-APR-2048", "Holiday");

		lh.addStaticHoliday ("06-APR-2048", "Holiday");

		lh.addStaticHoliday ("04-MAY-2048", "Holiday");

		lh.addStaticHoliday ("25-MAY-2048", "Holiday");

		lh.addStaticHoliday ("31-AUG-2048", "Holiday");

		lh.addStaticHoliday ("25-DEC-2048", "Holiday");

		lh.addStaticHoliday ("28-DEC-2048", "Holiday");

		lh.addStaticHoliday ("01-JAN-2049", "Holiday");

		lh.addStaticHoliday ("16-APR-2049", "Holiday");

		lh.addStaticHoliday ("19-APR-2049", "Holiday");

		lh.addStaticHoliday ("03-MAY-2049", "Holiday");

		lh.addStaticHoliday ("31-MAY-2049", "Holiday");

		lh.addStaticHoliday ("30-AUG-2049", "Holiday");

		lh.addStaticHoliday ("27-DEC-2049", "Holiday");

		lh.addStaticHoliday ("28-DEC-2049", "Holiday");

		lh.addStaticHoliday ("03-JAN-2050", "Holiday");

		lh.addStaticHoliday ("08-APR-2050", "Holiday");

		lh.addStaticHoliday ("11-APR-2050", "Holiday");

		lh.addStaticHoliday ("02-MAY-2050", "Holiday");

		lh.addStaticHoliday ("30-MAY-2050", "Holiday");

		lh.addStaticHoliday ("29-AUG-2050", "Holiday");

		lh.addStaticHoliday ("26-DEC-2050", "Holiday");

		lh.addStaticHoliday ("27-DEC-2050", "Holiday");

		lh.addStaticHoliday ("02-JAN-2051", "Holiday");

		lh.addStaticHoliday ("31-MAR-2051", "Holiday");

		lh.addStaticHoliday ("03-APR-2051", "Holiday");

		lh.addStaticHoliday ("01-MAY-2051", "Holiday");

		lh.addStaticHoliday ("29-MAY-2051", "Holiday");

		lh.addStaticHoliday ("28-AUG-2051", "Holiday");

		lh.addStaticHoliday ("25-DEC-2051", "Holiday");

		lh.addStaticHoliday ("26-DEC-2051", "Holiday");

		lh.addStaticHoliday ("01-JAN-2052", "Holiday");

		lh.addStaticHoliday ("19-APR-2052", "Holiday");

		lh.addStaticHoliday ("22-APR-2052", "Holiday");

		lh.addStaticHoliday ("06-MAY-2052", "Holiday");

		lh.addStaticHoliday ("27-MAY-2052", "Holiday");

		lh.addStaticHoliday ("26-AUG-2052", "Holiday");

		lh.addStaticHoliday ("25-DEC-2052", "Holiday");

		lh.addStaticHoliday ("26-DEC-2052", "Holiday");

		lh.addStaticHoliday ("01-JAN-2053", "Holiday");

		lh.addStaticHoliday ("04-APR-2053", "Holiday");

		lh.addStaticHoliday ("07-APR-2053", "Holiday");

		lh.addStaticHoliday ("05-MAY-2053", "Holiday");

		lh.addStaticHoliday ("26-MAY-2053", "Holiday");

		lh.addStaticHoliday ("25-AUG-2053", "Holiday");

		lh.addStaticHoliday ("25-DEC-2053", "Holiday");

		lh.addStaticHoliday ("26-DEC-2053", "Holiday");

		lh.addStaticHoliday ("01-JAN-2054", "Holiday");

		lh.addStaticHoliday ("27-MAR-2054", "Holiday");

		lh.addStaticHoliday ("30-MAR-2054", "Holiday");

		lh.addStaticHoliday ("04-MAY-2054", "Holiday");

		lh.addStaticHoliday ("25-MAY-2054", "Holiday");

		lh.addStaticHoliday ("31-AUG-2054", "Holiday");

		lh.addStaticHoliday ("25-DEC-2054", "Holiday");

		lh.addStaticHoliday ("28-DEC-2054", "Holiday");

		lh.addStaticHoliday ("01-JAN-2055", "Holiday");

		lh.addStaticHoliday ("16-APR-2055", "Holiday");

		lh.addStaticHoliday ("19-APR-2055", "Holiday");

		lh.addStaticHoliday ("03-MAY-2055", "Holiday");

		lh.addStaticHoliday ("31-MAY-2055", "Holiday");

		lh.addStaticHoliday ("30-AUG-2055", "Holiday");

		lh.addStaticHoliday ("27-DEC-2055", "Holiday");

		lh.addStaticHoliday ("28-DEC-2055", "Holiday");

		lh.addStaticHoliday ("03-JAN-2056", "Holiday");

		lh.addStaticHoliday ("31-MAR-2056", "Holiday");

		lh.addStaticHoliday ("03-APR-2056", "Holiday");

		lh.addStaticHoliday ("01-MAY-2056", "Holiday");

		lh.addStaticHoliday ("29-MAY-2056", "Holiday");

		lh.addStaticHoliday ("28-AUG-2056", "Holiday");

		lh.addStaticHoliday ("25-DEC-2056", "Holiday");

		lh.addStaticHoliday ("26-DEC-2056", "Holiday");

		lh.addStaticHoliday ("01-JAN-2057", "Holiday");

		lh.addStaticHoliday ("20-APR-2057", "Holiday");

		lh.addStaticHoliday ("23-APR-2057", "Holiday");

		lh.addStaticHoliday ("07-MAY-2057", "Holiday");

		lh.addStaticHoliday ("28-MAY-2057", "Holiday");

		lh.addStaticHoliday ("27-AUG-2057", "Holiday");

		lh.addStaticHoliday ("25-DEC-2057", "Holiday");

		lh.addStaticHoliday ("26-DEC-2057", "Holiday");

		lh.addStaticHoliday ("01-JAN-2058", "Holiday");

		lh.addStaticHoliday ("12-APR-2058", "Holiday");

		lh.addStaticHoliday ("15-APR-2058", "Holiday");

		lh.addStaticHoliday ("06-MAY-2058", "Holiday");

		lh.addStaticHoliday ("27-MAY-2058", "Holiday");

		lh.addStaticHoliday ("26-AUG-2058", "Holiday");

		lh.addStaticHoliday ("25-DEC-2058", "Holiday");

		lh.addStaticHoliday ("26-DEC-2058", "Holiday");

		lh.addStaticHoliday ("01-JAN-2059", "Holiday");

		lh.addStaticHoliday ("28-MAR-2059", "Holiday");

		lh.addStaticHoliday ("31-MAR-2059", "Holiday");

		lh.addStaticHoliday ("05-MAY-2059", "Holiday");

		lh.addStaticHoliday ("26-MAY-2059", "Holiday");

		lh.addStaticHoliday ("25-AUG-2059", "Holiday");

		lh.addStaticHoliday ("25-DEC-2059", "Holiday");

		lh.addStaticHoliday ("26-DEC-2059", "Holiday");

		lh.addStaticHoliday ("01-JAN-2060", "Holiday");

		lh.addStaticHoliday ("16-APR-2060", "Holiday");

		lh.addStaticHoliday ("19-APR-2060", "Holiday");

		lh.addStaticHoliday ("03-MAY-2060", "Holiday");

		lh.addStaticHoliday ("31-MAY-2060", "Holiday");

		lh.addStaticHoliday ("30-AUG-2060", "Holiday");

		lh.addStaticHoliday ("27-DEC-2060", "Holiday");

		lh.addStaticHoliday ("28-DEC-2060", "Holiday");

		lh.addStaticHoliday ("03-JAN-2061", "Holiday");

		lh.addStaticHoliday ("08-APR-2061", "Holiday");

		lh.addStaticHoliday ("11-APR-2061", "Holiday");

		lh.addStaticHoliday ("02-MAY-2061", "Holiday");

		lh.addStaticHoliday ("30-MAY-2061", "Holiday");

		lh.addStaticHoliday ("29-AUG-2061", "Holiday");

		lh.addStaticHoliday ("26-DEC-2061", "Holiday");

		lh.addStaticHoliday ("27-DEC-2061", "Holiday");

		lh.addStaticHoliday ("02-JAN-2062", "Holiday");

		lh.addStaticHoliday ("24-MAR-2062", "Holiday");

		lh.addStaticHoliday ("27-MAR-2062", "Holiday");

		lh.addStaticHoliday ("01-MAY-2062", "Holiday");

		lh.addStaticHoliday ("29-MAY-2062", "Holiday");

		lh.addStaticHoliday ("28-AUG-2062", "Holiday");

		lh.addStaticHoliday ("25-DEC-2062", "Holiday");

		lh.addStaticHoliday ("26-DEC-2062", "Holiday");

		lh.addStaticHoliday ("01-JAN-2063", "Holiday");

		lh.addStaticHoliday ("13-APR-2063", "Holiday");

		lh.addStaticHoliday ("16-APR-2063", "Holiday");

		lh.addStaticHoliday ("07-MAY-2063", "Holiday");

		lh.addStaticHoliday ("28-MAY-2063", "Holiday");

		lh.addStaticHoliday ("27-AUG-2063", "Holiday");

		lh.addStaticHoliday ("25-DEC-2063", "Holiday");

		lh.addStaticHoliday ("26-DEC-2063", "Holiday");

		lh.addStaticHoliday ("01-JAN-2064", "Holiday");

		lh.addStaticHoliday ("04-APR-2064", "Holiday");

		lh.addStaticHoliday ("07-APR-2064", "Holiday");

		lh.addStaticHoliday ("05-MAY-2064", "Holiday");

		lh.addStaticHoliday ("26-MAY-2064", "Holiday");

		lh.addStaticHoliday ("25-AUG-2064", "Holiday");

		lh.addStaticHoliday ("25-DEC-2064", "Holiday");

		lh.addStaticHoliday ("26-DEC-2064", "Holiday");

		lh.addStaticHoliday ("01-JAN-2065", "Holiday");

		lh.addStaticHoliday ("27-MAR-2065", "Holiday");

		lh.addStaticHoliday ("30-MAR-2065", "Holiday");

		lh.addStaticHoliday ("04-MAY-2065", "Holiday");

		lh.addStaticHoliday ("25-MAY-2065", "Holiday");

		lh.addStaticHoliday ("31-AUG-2065", "Holiday");

		lh.addStaticHoliday ("25-DEC-2065", "Holiday");

		lh.addStaticHoliday ("28-DEC-2065", "Holiday");

		lh.addStaticHoliday ("01-JAN-2066", "Holiday");

		lh.addStaticHoliday ("09-APR-2066", "Holiday");

		lh.addStaticHoliday ("12-APR-2066", "Holiday");

		lh.addStaticHoliday ("03-MAY-2066", "Holiday");

		lh.addStaticHoliday ("31-MAY-2066", "Holiday");

		lh.addStaticHoliday ("30-AUG-2066", "Holiday");

		lh.addStaticHoliday ("27-DEC-2066", "Holiday");

		lh.addStaticHoliday ("28-DEC-2066", "Holiday");

		lh.addStaticHoliday ("03-JAN-2067", "Holiday");

		lh.addStaticHoliday ("01-APR-2067", "Holiday");

		lh.addStaticHoliday ("04-APR-2067", "Holiday");

		lh.addStaticHoliday ("02-MAY-2067", "Holiday");

		lh.addStaticHoliday ("30-MAY-2067", "Holiday");

		lh.addStaticHoliday ("29-AUG-2067", "Holiday");

		lh.addStaticHoliday ("26-DEC-2067", "Holiday");

		lh.addStaticHoliday ("27-DEC-2067", "Holiday");

		lh.addStaticHoliday ("02-JAN-2068", "Holiday");

		lh.addStaticHoliday ("20-APR-2068", "Holiday");

		lh.addStaticHoliday ("23-APR-2068", "Holiday");

		lh.addStaticHoliday ("07-MAY-2068", "Holiday");

		lh.addStaticHoliday ("28-MAY-2068", "Holiday");

		lh.addStaticHoliday ("27-AUG-2068", "Holiday");

		lh.addStaticHoliday ("25-DEC-2068", "Holiday");

		lh.addStaticHoliday ("26-DEC-2068", "Holiday");

		lh.addStaticHoliday ("01-JAN-2069", "Holiday");

		lh.addStaticHoliday ("12-APR-2069", "Holiday");

		lh.addStaticHoliday ("15-APR-2069", "Holiday");

		lh.addStaticHoliday ("06-MAY-2069", "Holiday");

		lh.addStaticHoliday ("27-MAY-2069", "Holiday");

		lh.addStaticHoliday ("26-AUG-2069", "Holiday");

		lh.addStaticHoliday ("25-DEC-2069", "Holiday");

		lh.addStaticHoliday ("26-DEC-2069", "Holiday");

		lh.addStaticHoliday ("01-JAN-2070", "Holiday");

		lh.addStaticHoliday ("28-MAR-2070", "Holiday");

		lh.addStaticHoliday ("31-MAR-2070", "Holiday");

		lh.addStaticHoliday ("05-MAY-2070", "Holiday");

		lh.addStaticHoliday ("26-MAY-2070", "Holiday");

		lh.addStaticHoliday ("25-AUG-2070", "Holiday");

		lh.addStaticHoliday ("25-DEC-2070", "Holiday");

		lh.addStaticHoliday ("26-DEC-2070", "Holiday");

		lh.addStaticHoliday ("01-JAN-2071", "Holiday");

		lh.addStaticHoliday ("17-APR-2071", "Holiday");

		lh.addStaticHoliday ("20-APR-2071", "Holiday");

		lh.addStaticHoliday ("04-MAY-2071", "Holiday");

		lh.addStaticHoliday ("25-MAY-2071", "Holiday");

		lh.addStaticHoliday ("31-AUG-2071", "Holiday");

		lh.addStaticHoliday ("25-DEC-2071", "Holiday");

		lh.addStaticHoliday ("28-DEC-2071", "Holiday");

		lh.addStaticHoliday ("01-JAN-2072", "Holiday");

		lh.addStaticHoliday ("08-APR-2072", "Holiday");

		lh.addStaticHoliday ("11-APR-2072", "Holiday");

		lh.addStaticHoliday ("02-MAY-2072", "Holiday");

		lh.addStaticHoliday ("30-MAY-2072", "Holiday");

		lh.addStaticHoliday ("29-AUG-2072", "Holiday");

		lh.addStaticHoliday ("26-DEC-2072", "Holiday");

		lh.addStaticHoliday ("27-DEC-2072", "Holiday");

		lh.addStaticHoliday ("02-JAN-2073", "Holiday");

		lh.addStaticHoliday ("24-MAR-2073", "Holiday");

		lh.addStaticHoliday ("27-MAR-2073", "Holiday");

		lh.addStaticHoliday ("01-MAY-2073", "Holiday");

		lh.addStaticHoliday ("29-MAY-2073", "Holiday");

		lh.addStaticHoliday ("28-AUG-2073", "Holiday");

		lh.addStaticHoliday ("25-DEC-2073", "Holiday");

		lh.addStaticHoliday ("26-DEC-2073", "Holiday");

		lh.addStaticHoliday ("01-JAN-2074", "Holiday");

		lh.addStaticHoliday ("13-APR-2074", "Holiday");

		lh.addStaticHoliday ("16-APR-2074", "Holiday");

		lh.addStaticHoliday ("07-MAY-2074", "Holiday");

		lh.addStaticHoliday ("28-MAY-2074", "Holiday");

		lh.addStaticHoliday ("27-AUG-2074", "Holiday");

		lh.addStaticHoliday ("25-DEC-2074", "Holiday");

		lh.addStaticHoliday ("26-DEC-2074", "Holiday");

		lh.addStaticHoliday ("01-JAN-2075", "Holiday");

		lh.addStaticHoliday ("05-APR-2075", "Holiday");

		lh.addStaticHoliday ("08-APR-2075", "Holiday");

		lh.addStaticHoliday ("06-MAY-2075", "Holiday");

		lh.addStaticHoliday ("27-MAY-2075", "Holiday");

		lh.addStaticHoliday ("26-AUG-2075", "Holiday");

		lh.addStaticHoliday ("25-DEC-2075", "Holiday");

		lh.addStaticHoliday ("26-DEC-2075", "Holiday");

		lh.addStaticHoliday ("01-JAN-2076", "Holiday");

		lh.addStaticHoliday ("17-APR-2076", "Holiday");

		lh.addStaticHoliday ("20-APR-2076", "Holiday");

		lh.addStaticHoliday ("04-MAY-2076", "Holiday");

		lh.addStaticHoliday ("25-MAY-2076", "Holiday");

		lh.addStaticHoliday ("31-AUG-2076", "Holiday");

		lh.addStaticHoliday ("25-DEC-2076", "Holiday");

		lh.addStaticHoliday ("28-DEC-2076", "Holiday");

		lh.addStaticHoliday ("01-JAN-2077", "Holiday");

		lh.addStaticHoliday ("09-APR-2077", "Holiday");

		lh.addStaticHoliday ("12-APR-2077", "Holiday");

		lh.addStaticHoliday ("03-MAY-2077", "Holiday");

		lh.addStaticHoliday ("31-MAY-2077", "Holiday");

		lh.addStaticHoliday ("30-AUG-2077", "Holiday");

		lh.addStaticHoliday ("27-DEC-2077", "Holiday");

		lh.addStaticHoliday ("28-DEC-2077", "Holiday");

		lh.addStaticHoliday ("03-JAN-2078", "Holiday");

		lh.addStaticHoliday ("01-APR-2078", "Holiday");

		lh.addStaticHoliday ("04-APR-2078", "Holiday");

		lh.addStaticHoliday ("02-MAY-2078", "Holiday");

		lh.addStaticHoliday ("30-MAY-2078", "Holiday");

		lh.addStaticHoliday ("29-AUG-2078", "Holiday");

		lh.addStaticHoliday ("26-DEC-2078", "Holiday");

		lh.addStaticHoliday ("27-DEC-2078", "Holiday");

		lh.addStaticHoliday ("02-JAN-2079", "Holiday");

		lh.addStaticHoliday ("21-APR-2079", "Holiday");

		lh.addStaticHoliday ("24-APR-2079", "Holiday");

		lh.addStaticHoliday ("01-MAY-2079", "Holiday");

		lh.addStaticHoliday ("29-MAY-2079", "Holiday");

		lh.addStaticHoliday ("28-AUG-2079", "Holiday");

		lh.addStaticHoliday ("25-DEC-2079", "Holiday");

		lh.addStaticHoliday ("26-DEC-2079", "Holiday");

		lh.addStaticHoliday ("01-JAN-2080", "Holiday");

		lh.addStaticHoliday ("05-APR-2080", "Holiday");

		lh.addStaticHoliday ("08-APR-2080", "Holiday");

		lh.addStaticHoliday ("06-MAY-2080", "Holiday");

		lh.addStaticHoliday ("27-MAY-2080", "Holiday");

		lh.addStaticHoliday ("26-AUG-2080", "Holiday");

		lh.addStaticHoliday ("25-DEC-2080", "Holiday");

		lh.addStaticHoliday ("26-DEC-2080", "Holiday");

		lh.addStaticHoliday ("01-JAN-2081", "Holiday");

		lh.addStaticHoliday ("28-MAR-2081", "Holiday");

		lh.addStaticHoliday ("31-MAR-2081", "Holiday");

		lh.addStaticHoliday ("05-MAY-2081", "Holiday");

		lh.addStaticHoliday ("26-MAY-2081", "Holiday");

		lh.addStaticHoliday ("25-AUG-2081", "Holiday");

		lh.addStaticHoliday ("25-DEC-2081", "Holiday");

		lh.addStaticHoliday ("26-DEC-2081", "Holiday");

		lh.addStaticHoliday ("01-JAN-2082", "Holiday");

		lh.addStaticHoliday ("17-APR-2082", "Holiday");

		lh.addStaticHoliday ("20-APR-2082", "Holiday");

		lh.addStaticHoliday ("04-MAY-2082", "Holiday");

		lh.addStaticHoliday ("25-MAY-2082", "Holiday");

		lh.addStaticHoliday ("31-AUG-2082", "Holiday");

		lh.addStaticHoliday ("25-DEC-2082", "Holiday");

		lh.addStaticHoliday ("28-DEC-2082", "Holiday");

		lh.addStaticHoliday ("01-JAN-2083", "Holiday");

		lh.addStaticHoliday ("02-APR-2083", "Holiday");

		lh.addStaticHoliday ("05-APR-2083", "Holiday");

		lh.addStaticHoliday ("03-MAY-2083", "Holiday");

		lh.addStaticHoliday ("31-MAY-2083", "Holiday");

		lh.addStaticHoliday ("30-AUG-2083", "Holiday");

		lh.addStaticHoliday ("27-DEC-2083", "Holiday");

		lh.addStaticHoliday ("28-DEC-2083", "Holiday");

		lh.addStaticHoliday ("03-JAN-2084", "Holiday");

		lh.addStaticHoliday ("24-MAR-2084", "Holiday");

		lh.addStaticHoliday ("27-MAR-2084", "Holiday");

		lh.addStaticHoliday ("01-MAY-2084", "Holiday");

		lh.addStaticHoliday ("29-MAY-2084", "Holiday");

		lh.addStaticHoliday ("28-AUG-2084", "Holiday");

		lh.addStaticHoliday ("25-DEC-2084", "Holiday");

		lh.addStaticHoliday ("26-DEC-2084", "Holiday");

		lh.addStandardWeekend();

		return lh;
	}
}
