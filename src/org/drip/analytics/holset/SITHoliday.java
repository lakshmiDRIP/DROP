
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

public class SITHoliday implements org.drip.analytics.holset.LocationHoliday {
	public SITHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "SIT";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("02-JAN-1998", "New Years Holiday");

		lh.addStaticHoliday ("13-APR-1998", "Easter Monday");

		lh.addStaticHoliday ("27-APR-1998", "National Holiday");

		lh.addStaticHoliday ("01-MAY-1998", "Labour Day");

		lh.addStaticHoliday ("25-JUN-1998", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("25-DEC-1998", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("08-FEB-1999", "Day of Slovenian Culture");

		lh.addStaticHoliday ("05-APR-1999", "Easter Monday");

		lh.addStaticHoliday ("27-APR-1999", "National Holiday");

		lh.addStaticHoliday ("25-JUN-1999", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("01-NOV-1999", "All Saints Day");

		lh.addStaticHoliday ("08-FEB-2000", "Day of Slovenian Culture");

		lh.addStaticHoliday ("24-APR-2000", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2000", "National Holiday");

		lh.addStaticHoliday ("01-MAY-2000", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2000", "Day after Labour Day");

		lh.addStaticHoliday ("15-AUG-2000", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2000", "Reformation Day");

		lh.addStaticHoliday ("01-NOV-2000", "All Saints Day");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2000", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2001", "New Years Holiday");

		lh.addStaticHoliday ("08-FEB-2001", "Day of Slovenian Culture");

		lh.addStaticHoliday ("16-APR-2001", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2001", "National Holiday");

		lh.addStaticHoliday ("01-MAY-2001", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2001", "Day after Labour Day");

		lh.addStaticHoliday ("25-JUN-2001", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("15-AUG-2001", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2001", "Reformation Day");

		lh.addStaticHoliday ("01-NOV-2001", "All Saints Day");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2001", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2002", "New Years Holiday");

		lh.addStaticHoliday ("08-FEB-2002", "Day of Slovenian Culture");

		lh.addStaticHoliday ("01-APR-2002", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2002", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2002", "Day after Labour Day");

		lh.addStaticHoliday ("25-JUN-2002", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("15-AUG-2002", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2002", "Reformation Day");

		lh.addStaticHoliday ("01-NOV-2002", "All Saints Day");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2002", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2003", "New Years Holiday");

		lh.addStaticHoliday ("21-APR-2003", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2003", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2003", "Day after Labour Day");

		lh.addStaticHoliday ("25-JUN-2003", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("15-AUG-2003", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2003", "Reformation Day");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2003", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2004", "New Years Holiday");

		lh.addStaticHoliday ("12-APR-2004", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2004", "National Holiday");

		lh.addStaticHoliday ("25-JUN-2004", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("01-NOV-2004", "All Saints Day");

		lh.addStaticHoliday ("08-FEB-2005", "Day of Slovenian Culture");

		lh.addStaticHoliday ("28-MAR-2005", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2005", "National Holiday");

		lh.addStaticHoliday ("02-MAY-2005", "Day after Labour Day");

		lh.addStaticHoliday ("15-AUG-2005", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2005", "Reformation Day");

		lh.addStaticHoliday ("01-NOV-2005", "All Saints Day");

		lh.addStaticHoliday ("26-DEC-2005", "Independence Day");

		lh.addStaticHoliday ("02-JAN-2006", "New Years Holiday");

		lh.addStaticHoliday ("08-FEB-2006", "Day of Slovenian Culture");

		lh.addStaticHoliday ("17-APR-2006", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2006", "National Holiday");

		lh.addStaticHoliday ("01-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2006", "Day after Labour Day");

		lh.addStaticHoliday ("15-AUG-2006", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2006", "Reformation Day");

		lh.addStaticHoliday ("01-NOV-2006", "All Saints Day");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2006", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2007", "New Years Holiday");

		lh.addStaticHoliday ("08-FEB-2007", "Day of Slovenian Culture");

		lh.addStaticHoliday ("09-APR-2007", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2007", "National Holiday");

		lh.addStaticHoliday ("01-MAY-2007", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2007", "Day after Labour Day");

		lh.addStaticHoliday ("25-JUN-2007", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("15-AUG-2007", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2007", "Reformation Day");

		lh.addStaticHoliday ("01-NOV-2007", "All Saints Day");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2007", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2008", "New Years Holiday");

		lh.addStaticHoliday ("08-FEB-2008", "Day of Slovenian Culture");

		lh.addStaticHoliday ("24-MAR-2008", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2008", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2008", "Day after Labour Day");

		lh.addStaticHoliday ("25-JUN-2008", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("15-AUG-2008", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2008", "Reformation Day");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2008", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2009", "New Years Holiday");

		lh.addStaticHoliday ("13-APR-2009", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2009", "National Holiday");

		lh.addStaticHoliday ("01-MAY-2009", "Labour Day");

		lh.addStaticHoliday ("25-JUN-2009", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2010", "Day of Slovenian Culture");

		lh.addStaticHoliday ("05-APR-2010", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2010", "National Holiday");

		lh.addStaticHoliday ("25-JUN-2010", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("01-NOV-2010", "All Saints Day");

		lh.addStaticHoliday ("08-FEB-2011", "Day of Slovenian Culture");

		lh.addStaticHoliday ("25-APR-2011", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2011", "National Holiday");

		lh.addStaticHoliday ("02-MAY-2011", "Day after Labour Day");

		lh.addStaticHoliday ("15-AUG-2011", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2011", "Reformation Day");

		lh.addStaticHoliday ("01-NOV-2011", "All Saints Day");

		lh.addStaticHoliday ("26-DEC-2011", "Independence Day");

		lh.addStaticHoliday ("02-JAN-2012", "New Years Holiday");

		lh.addStaticHoliday ("08-FEB-2012", "Day of Slovenian Culture");

		lh.addStaticHoliday ("09-APR-2012", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2012", "National Holiday");

		lh.addStaticHoliday ("01-MAY-2012", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2012", "Day after Labour Day");

		lh.addStaticHoliday ("25-JUN-2012", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("15-AUG-2012", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2012", "Reformation Day");

		lh.addStaticHoliday ("01-NOV-2012", "All Saints Day");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2012", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2013", "New Years Holiday");

		lh.addStaticHoliday ("08-FEB-2013", "Day of Slovenian Culture");

		lh.addStaticHoliday ("01-APR-2013", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2013", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2013", "Day after Labour Day");

		lh.addStaticHoliday ("25-JUN-2013", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("15-AUG-2013", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2013", "Reformation Day");

		lh.addStaticHoliday ("01-NOV-2013", "All Saints Day");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2013", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2014", "New Years Holiday");

		lh.addStaticHoliday ("21-APR-2014", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2014", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2014", "Day after Labour Day");

		lh.addStaticHoliday ("25-JUN-2014", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("15-AUG-2014", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2014", "Reformation Day");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2014", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2015", "New Years Holiday");

		lh.addStaticHoliday ("06-APR-2015", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2015", "National Holiday");

		lh.addStaticHoliday ("01-MAY-2015", "Labour Day");

		lh.addStaticHoliday ("25-JUN-2015", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2016", "Day of Slovenian Culture");

		lh.addStaticHoliday ("28-MAR-2016", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2016", "National Holiday");

		lh.addStaticHoliday ("02-MAY-2016", "Day after Labour Day");

		lh.addStaticHoliday ("15-AUG-2016", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2016", "Reformation Day");

		lh.addStaticHoliday ("01-NOV-2016", "All Saints Day");

		lh.addStaticHoliday ("26-DEC-2016", "Independence Day");

		lh.addStaticHoliday ("02-JAN-2017", "New Years Holiday");

		lh.addStaticHoliday ("08-FEB-2017", "Day of Slovenian Culture");

		lh.addStaticHoliday ("17-APR-2017", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2017", "National Holiday");

		lh.addStaticHoliday ("01-MAY-2017", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2017", "Day after Labour Day");

		lh.addStaticHoliday ("15-AUG-2017", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2017", "Reformation Day");

		lh.addStaticHoliday ("01-NOV-2017", "All Saints Day");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2017", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2018", "New Years Holiday");

		lh.addStaticHoliday ("08-FEB-2018", "Day of Slovenian Culture");

		lh.addStaticHoliday ("02-APR-2018", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2018", "National Holiday");

		lh.addStaticHoliday ("01-MAY-2018", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2018", "Day after Labour Day");

		lh.addStaticHoliday ("25-JUN-2018", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("15-AUG-2018", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2018", "Reformation Day");

		lh.addStaticHoliday ("01-NOV-2018", "All Saints Day");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2018", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2019", "New Years Holiday");

		lh.addStaticHoliday ("08-FEB-2019", "Day of Slovenian Culture");

		lh.addStaticHoliday ("22-APR-2019", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2019", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2019", "Day after Labour Day");

		lh.addStaticHoliday ("25-JUN-2019", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("15-AUG-2019", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2019", "Reformation Day");

		lh.addStaticHoliday ("01-NOV-2019", "All Saints Day");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2019", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2020", "New Years Holiday");

		lh.addStaticHoliday ("13-APR-2020", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2020", "National Holiday");

		lh.addStaticHoliday ("01-MAY-2020", "Labour Day");

		lh.addStaticHoliday ("25-JUN-2020", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2021", "Day of Slovenian Culture");

		lh.addStaticHoliday ("05-APR-2021", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2021", "National Holiday");

		lh.addStaticHoliday ("25-JUN-2021", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("01-NOV-2021", "All Saints Day");

		lh.addStaticHoliday ("08-FEB-2022", "Day of Slovenian Culture");

		lh.addStaticHoliday ("18-APR-2022", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2022", "National Holiday");

		lh.addStaticHoliday ("02-MAY-2022", "Day after Labour Day");

		lh.addStaticHoliday ("15-AUG-2022", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2022", "Reformation Day");

		lh.addStaticHoliday ("01-NOV-2022", "All Saints Day");

		lh.addStaticHoliday ("26-DEC-2022", "Independence Day");

		lh.addStaticHoliday ("02-JAN-2023", "New Years Holiday");

		lh.addStaticHoliday ("08-FEB-2023", "Day of Slovenian Culture");

		lh.addStaticHoliday ("10-APR-2023", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2023", "National Holiday");

		lh.addStaticHoliday ("01-MAY-2023", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2023", "Day after Labour Day");

		lh.addStaticHoliday ("15-AUG-2023", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2023", "Reformation Day");

		lh.addStaticHoliday ("01-NOV-2023", "All Saints Day");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2023", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2024", "New Years Holiday");

		lh.addStaticHoliday ("08-FEB-2024", "Day of Slovenian Culture");

		lh.addStaticHoliday ("01-APR-2024", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2024", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2024", "Day after Labour Day");

		lh.addStaticHoliday ("25-JUN-2024", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("15-AUG-2024", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2024", "Reformation Day");

		lh.addStaticHoliday ("01-NOV-2024", "All Saints Day");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2024", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2025", "New Years Holiday");

		lh.addStaticHoliday ("21-APR-2025", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2025", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2025", "Day after Labour Day");

		lh.addStaticHoliday ("25-JUN-2025", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("15-AUG-2025", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2025", "Reformation Day");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2025", "Independence Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2026", "New Years Holiday");

		lh.addStaticHoliday ("06-APR-2026", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2026", "National Holiday");

		lh.addStaticHoliday ("01-MAY-2026", "Labour Day");

		lh.addStaticHoliday ("25-JUN-2026", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2027", "Day of Slovenian Culture");

		lh.addStaticHoliday ("29-MAR-2027", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2027", "National Holiday");

		lh.addStaticHoliday ("25-JUN-2027", "Slovenian Sovereignty Day");

		lh.addStaticHoliday ("01-NOV-2027", "All Saints Day");

		lh.addStaticHoliday ("08-FEB-2028", "Day of Slovenian Culture");

		lh.addStaticHoliday ("17-APR-2028", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2028", "National Holiday");

		lh.addStaticHoliday ("01-MAY-2028", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2028", "Day after Labour Day");

		lh.addStaticHoliday ("15-AUG-2028", "Assumption Day");

		lh.addStaticHoliday ("31-OCT-2028", "Reformation Day");

		lh.addStaticHoliday ("01-NOV-2028", "All Saints Day");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2028", "Independence Day");

		lh.addStandardWeekend();

		return lh;
	}
}
