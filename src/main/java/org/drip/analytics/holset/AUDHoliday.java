
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

public class AUDHoliday implements org.drip.analytics.holset.LocationHoliday {
	public AUDHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "AUD";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("26-JAN-1999", "Australia Day");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("05-APR-1999", "Easter Monday");

		lh.addStaticHoliday ("26-APR-1999", "Anzac Day Observed");

		lh.addStaticHoliday ("14-JUN-1999", "Queens Birthday");

		lh.addStaticHoliday ("02-AUG-1999", "Bank Holiday");

		lh.addStaticHoliday ("04-OCT-1999", "Labour Day");

		lh.addStaticHoliday ("27-DEC-1999", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-1999", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2000", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2000", "Australia Day");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("24-APR-2000", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2000", "Anzac Day");

		lh.addStaticHoliday ("12-JUN-2000", "Queens Birthday");

		lh.addStaticHoliday ("07-AUG-2000", "Bank Holiday");

		lh.addStaticHoliday ("02-OCT-2000", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2000", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2001", "Australia Day");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("16-APR-2001", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2001", "Anzac Day");

		lh.addStaticHoliday ("11-JUN-2001", "Queens Birthday");

		lh.addStaticHoliday ("06-AUG-2001", "Bank Holiday");

		lh.addStaticHoliday ("01-OCT-2001", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2001", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2002", "Australia Day");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("01-APR-2002", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2002", "Anzac Day");

		lh.addStaticHoliday ("10-JUN-2002", "Queens Birthday");

		lh.addStaticHoliday ("05-AUG-2002", "Bank Holiday");

		lh.addStaticHoliday ("07-OCT-2002", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2002", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("27-JAN-2003", "Australia Day Observed");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("21-APR-2003", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2003", "Anzac Day");

		lh.addStaticHoliday ("09-JUN-2003", "Queens Birthday");

		lh.addStaticHoliday ("04-AUG-2003", "Bank Holiday");

		lh.addStaticHoliday ("06-OCT-2003", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2003", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2004", "Australia Day");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("12-APR-2004", "Easter Monday");

		lh.addStaticHoliday ("14-JUN-2004", "Queens Birthday");

		lh.addStaticHoliday ("02-AUG-2004", "Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2004", "Christmas Day");

		lh.addStaticHoliday ("27-DEC-2004", "Boxing Day");

		lh.addStaticHoliday ("28-DEC-2004", "National Holiday");

		lh.addStaticHoliday ("03-JAN-2005", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2005", "Australia Day");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("28-MAR-2005", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2005", "Anzac Day");

		lh.addStaticHoliday ("13-JUN-2005", "Queens Birthday");

		lh.addStaticHoliday ("26-DEC-2005", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2005", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2006", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2006", "Australia Day");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("17-APR-2006", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2006", "Anzac Day");

		lh.addStaticHoliday ("12-JUN-2006", "Queens Birthday");

		lh.addStaticHoliday ("07-AUG-2006", "Bank Holiday");

		lh.addStaticHoliday ("02-OCT-2006", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2006", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2007", "Australia Day");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("09-APR-2007", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2007", "Anzac Day");

		lh.addStaticHoliday ("11-JUN-2007", "Queens Birthday");

		lh.addStaticHoliday ("06-AUG-2007", "Bank Holiday");

		lh.addStaticHoliday ("07-SEP-2007", "Special Holiday");

		lh.addStaticHoliday ("01-OCT-2007", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2007", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2008", "Australia Day Observed");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("24-MAR-2008", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2008", "Anzac Day");

		lh.addStaticHoliday ("06-OCT-2008", "Labor Day");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2008", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2009", "Australia Day");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("13-APR-2009", "Easter Monday");

		lh.addStaticHoliday ("08-JUN-2009", "Queens Birthday");

		lh.addStaticHoliday ("03-AUG-2009", "Bank Holiday");

		lh.addStaticHoliday ("05-OCT-2009", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2009", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2010", "Australia Day");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("05-APR-2010", "Easter Monday");

		lh.addStaticHoliday ("26-APR-2010", "Anzac Day Observed");

		lh.addStaticHoliday ("14-JUN-2010", "Queens Birthday");

		lh.addStaticHoliday ("02-AUG-2010", "Bank Holiday");

		lh.addStaticHoliday ("04-OCT-2010", "Labour Day");

		lh.addStaticHoliday ("27-DEC-2010", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2010", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2011", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2011", "Australia Day");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("25-APR-2011", "Easter Monday");

		lh.addStaticHoliday ("26-APR-2011", "Anzac Day Observed");

		lh.addStaticHoliday ("13-JUN-2011", "Queens Birthday");

		lh.addStaticHoliday ("01-AUG-2011", "Bank Holiday");

		lh.addStaticHoliday ("03-OCT-2011", "Labour Day");

		lh.addStaticHoliday ("26-DEC-2011", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2011", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2012", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2012", "Australia Day");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("09-APR-2012", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2012", "Anzac Day");

		lh.addStaticHoliday ("11-JUN-2012", "Queens Birthday");

		lh.addStaticHoliday ("06-AUG-2012", "Bank Holiday");

		lh.addStaticHoliday ("01-OCT-2012", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2012", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2013", "Australia Day Observed");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("01-APR-2013", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2013", "Anzac Day");

		lh.addStaticHoliday ("10-JUN-2013", "Queens Birthday");

		lh.addStaticHoliday ("05-AUG-2013", "Bank Holiday");

		lh.addStaticHoliday ("07-OCT-2013", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2013", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("27-JAN-2014", "Australia Day Observed");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("21-APR-2014", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2014", "Anzac Day");

		lh.addStaticHoliday ("09-JUN-2014", "Queens Birthday");

		lh.addStaticHoliday ("04-AUG-2014", "Bank Holiday");

		lh.addStaticHoliday ("06-OCT-2014", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2014", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2015", "Australia Day");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("06-APR-2015", "Easter Monday");

		lh.addStaticHoliday ("08-JUN-2015", "Queens Birthday");

		lh.addStaticHoliday ("03-AUG-2015", "Bank Holiday");

		lh.addStaticHoliday ("05-OCT-2015", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2015", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2016", "Australia Day");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("28-MAR-2016", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2016", "Anzac Day");

		lh.addStaticHoliday ("13-JUN-2016", "Queens Birthday");

		lh.addStaticHoliday ("01-AUG-2016", "Bank Holiday");

		lh.addStaticHoliday ("03-OCT-2016", "Labour Day");

		lh.addStaticHoliday ("26-DEC-2016", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2016", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2017", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2017", "Australia Day");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("17-APR-2017", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2017", "Anzac Day");

		lh.addStaticHoliday ("12-JUN-2017", "Queens Birthday");

		lh.addStaticHoliday ("07-AUG-2017", "Bank Holiday");

		lh.addStaticHoliday ("02-OCT-2017", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2017", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2018", "Australia Day");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("02-APR-2018", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2018", "Anzac Day");

		lh.addStaticHoliday ("11-JUN-2018", "Queens Birthday");

		lh.addStaticHoliday ("06-AUG-2018", "Bank Holiday");

		lh.addStaticHoliday ("01-OCT-2018", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2018", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2019", "Australia Day Observed");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("22-APR-2019", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2019", "Anzac Day");

		lh.addStaticHoliday ("10-JUN-2019", "Queens Birthday");

		lh.addStaticHoliday ("05-AUG-2019", "Bank Holiday");

		lh.addStaticHoliday ("07-OCT-2019", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2019", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("27-JAN-2020", "Australia Day Observed");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("13-APR-2020", "Easter Monday");

		lh.addStaticHoliday ("08-JUN-2020", "Queens Birthday");

		lh.addStaticHoliday ("03-AUG-2020", "Bank Holiday");

		lh.addStaticHoliday ("05-OCT-2020", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2020", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2021", "Australia Day");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("05-APR-2021", "Easter Monday");

		lh.addStaticHoliday ("26-APR-2021", "Anzac Day Observed");

		lh.addStaticHoliday ("14-JUN-2021", "Queens Birthday");

		lh.addStaticHoliday ("02-AUG-2021", "Bank Holiday");

		lh.addStaticHoliday ("04-OCT-2021", "Labour Day");

		lh.addStaticHoliday ("27-DEC-2021", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2021", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2022", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2022", "Australia Day");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("18-APR-2022", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2022", "Anzac Day");

		lh.addStaticHoliday ("13-JUN-2022", "Queens Birthday");

		lh.addStaticHoliday ("01-AUG-2022", "Bank Holiday");

		lh.addStaticHoliday ("03-OCT-2022", "Labour Day");

		lh.addStaticHoliday ("26-DEC-2022", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2022", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2023", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2023", "Australia Day");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("10-APR-2023", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2023", "Anzac Day");

		lh.addStaticHoliday ("12-JUN-2023", "Queens Birthday");

		lh.addStaticHoliday ("07-AUG-2023", "Bank Holiday");

		lh.addStaticHoliday ("02-OCT-2023", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2023", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2024", "Australia Day");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("01-APR-2024", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2024", "Anzac Day");

		lh.addStaticHoliday ("10-JUN-2024", "Queens Birthday");

		lh.addStaticHoliday ("05-AUG-2024", "Bank Holiday");

		lh.addStaticHoliday ("07-OCT-2024", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2024", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("27-JAN-2025", "Australia Day Observed");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("21-APR-2025", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2025", "Anzac Day");

		lh.addStaticHoliday ("09-JUN-2025", "Queens Birthday");

		lh.addStaticHoliday ("04-AUG-2025", "Bank Holiday");

		lh.addStaticHoliday ("06-OCT-2025", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2025", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2026", "Australia Day");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("06-APR-2026", "Easter Monday");

		lh.addStaticHoliday ("08-JUN-2026", "Queens Birthday");

		lh.addStaticHoliday ("03-AUG-2026", "Bank Holiday");

		lh.addStaticHoliday ("05-OCT-2026", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2026", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2027", "Australia Day");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("29-MAR-2027", "Easter Monday");

		lh.addStaticHoliday ("26-APR-2027", "Anzac Day Observed");

		lh.addStaticHoliday ("14-JUN-2027", "Queens Birthday");

		lh.addStaticHoliday ("02-AUG-2027", "Bank Holiday");

		lh.addStaticHoliday ("04-OCT-2027", "Labour Day");

		lh.addStaticHoliday ("27-DEC-2027", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2027", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2028", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2028", "Australia Day");

		lh.addStaticHoliday ("14-APR-2028", "Good Friday");

		lh.addStaticHoliday ("17-APR-2028", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2028", "Anzac Day");

		lh.addStaticHoliday ("12-JUN-2028", "Queens Birthday");

		lh.addStaticHoliday ("07-AUG-2028", "Bank Holiday");

		lh.addStaticHoliday ("02-OCT-2028", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2028", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2029", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2029", "Australia Day");

		lh.addStaticHoliday ("30-MAR-2029", "Good Friday");

		lh.addStaticHoliday ("02-APR-2029", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2029", "Anzac Day");

		lh.addStaticHoliday ("11-JUN-2029", "Queens Birthday");

		lh.addStaticHoliday ("06-AUG-2029", "Bank Holiday");

		lh.addStaticHoliday ("01-OCT-2029", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2029", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2029", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2030", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2030", "Australia Day Observed");

		lh.addStaticHoliday ("19-APR-2030", "Good Friday");

		lh.addStaticHoliday ("22-APR-2030", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2030", "Anzac Day");

		lh.addStaticHoliday ("10-JUN-2030", "Queens Birthday");

		lh.addStaticHoliday ("05-AUG-2030", "Bank Holiday");

		lh.addStaticHoliday ("07-OCT-2030", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2030", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2030", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2031", "New Years Day");

		lh.addStaticHoliday ("27-JAN-2031", "Australia Day Observed");

		lh.addStaticHoliday ("11-APR-2031", "Good Friday");

		lh.addStaticHoliday ("14-APR-2031", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2031", "Anzac Day");

		lh.addStaticHoliday ("09-JUN-2031", "Queens Birthday");

		lh.addStaticHoliday ("04-AUG-2031", "Bank Holiday");

		lh.addStaticHoliday ("06-OCT-2031", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2031", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2031", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2032", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2032", "Australia Day");

		lh.addStaticHoliday ("26-MAR-2032", "Good Friday");

		lh.addStaticHoliday ("29-MAR-2032", "Easter Monday");

		lh.addStaticHoliday ("26-APR-2032", "Anzac Day Observed");

		lh.addStaticHoliday ("14-JUN-2032", "Queens Birthday");

		lh.addStaticHoliday ("02-AUG-2032", "Bank Holiday");

		lh.addStaticHoliday ("04-OCT-2032", "Labour Day");

		lh.addStaticHoliday ("27-DEC-2032", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2032", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2033", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2033", "Australia Day");

		lh.addStaticHoliday ("15-APR-2033", "Good Friday");

		lh.addStaticHoliday ("18-APR-2033", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2033", "Anzac Day");

		lh.addStaticHoliday ("13-JUN-2033", "Queens Birthday");

		lh.addStaticHoliday ("01-AUG-2033", "Bank Holiday");

		lh.addStaticHoliday ("03-OCT-2033", "Labour Day");

		lh.addStaticHoliday ("26-DEC-2033", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2033", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2034", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2034", "Australia Day");

		lh.addStaticHoliday ("07-APR-2034", "Good Friday");

		lh.addStaticHoliday ("10-APR-2034", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2034", "Anzac Day");

		lh.addStaticHoliday ("12-JUN-2034", "Queens Birthday");

		lh.addStaticHoliday ("07-AUG-2034", "Bank Holiday");

		lh.addStaticHoliday ("02-OCT-2034", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2034", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2034", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2035", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2035", "Australia Day");

		lh.addStaticHoliday ("23-MAR-2035", "Good Friday");

		lh.addStaticHoliday ("26-MAR-2035", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2035", "Anzac Day");

		lh.addStaticHoliday ("11-JUN-2035", "Queens Birthday");

		lh.addStaticHoliday ("06-AUG-2035", "Bank Holiday");

		lh.addStaticHoliday ("01-OCT-2035", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2035", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2035", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2036", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2036", "Australia Day Observed");

		lh.addStaticHoliday ("11-APR-2036", "Good Friday");

		lh.addStaticHoliday ("14-APR-2036", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2036", "Anzac Day");

		lh.addStaticHoliday ("09-JUN-2036", "Queens Birthday");

		lh.addStaticHoliday ("04-AUG-2036", "Bank Holiday");

		lh.addStaticHoliday ("06-OCT-2036", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2036", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2036", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2037", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2037", "Australia Day");

		lh.addStaticHoliday ("03-APR-2037", "Good Friday");

		lh.addStaticHoliday ("06-APR-2037", "Easter Monday");

		lh.addStaticHoliday ("08-JUN-2037", "Queens Birthday");

		lh.addStaticHoliday ("03-AUG-2037", "Bank Holiday");

		lh.addStaticHoliday ("05-OCT-2037", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2037", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2037", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2038", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2038", "Australia Day");

		lh.addStaticHoliday ("23-APR-2038", "Good Friday");

		lh.addStaticHoliday ("26-APR-2038", "Easter Monday");

		lh.addStaticHoliday ("27-APR-2038", "Anzac Day Observed");

		lh.addStaticHoliday ("14-JUN-2038", "Queens Birthday");

		lh.addStaticHoliday ("02-AUG-2038", "Bank Holiday");

		lh.addStaticHoliday ("04-OCT-2038", "Labour Day");

		lh.addStaticHoliday ("27-DEC-2038", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2038", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2039", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2039", "Australia Day");

		lh.addStaticHoliday ("08-APR-2039", "Good Friday");

		lh.addStaticHoliday ("11-APR-2039", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2039", "Anzac Day");

		lh.addStaticHoliday ("13-JUN-2039", "Queens Birthday");

		lh.addStaticHoliday ("01-AUG-2039", "Bank Holiday");

		lh.addStaticHoliday ("03-OCT-2039", "Labour Day");

		lh.addStaticHoliday ("26-DEC-2039", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2039", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2040", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2040", "Australia Day");

		lh.addStaticHoliday ("30-MAR-2040", "Good Friday");

		lh.addStaticHoliday ("02-APR-2040", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2040", "Anzac Day");

		lh.addStaticHoliday ("11-JUN-2040", "Queens Birthday");

		lh.addStaticHoliday ("06-AUG-2040", "Bank Holiday");

		lh.addStaticHoliday ("01-OCT-2040", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2040", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2040", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2041", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2041", "Australia Day Observed");

		lh.addStaticHoliday ("19-APR-2041", "Good Friday");

		lh.addStaticHoliday ("22-APR-2041", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2041", "Anzac Day");

		lh.addStaticHoliday ("10-JUN-2041", "Queens Birthday");

		lh.addStaticHoliday ("05-AUG-2041", "Bank Holiday");

		lh.addStaticHoliday ("07-OCT-2041", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2041", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2041", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2042", "New Years Day");

		lh.addStaticHoliday ("27-JAN-2042", "Australia Day Observed");

		lh.addStaticHoliday ("04-APR-2042", "Good Friday");

		lh.addStaticHoliday ("07-APR-2042", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2042", "Anzac Day");

		lh.addStaticHoliday ("09-JUN-2042", "Queens Birthday");

		lh.addStaticHoliday ("04-AUG-2042", "Bank Holiday");

		lh.addStaticHoliday ("06-OCT-2042", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2042", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2042", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2043", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2043", "Australia Day");

		lh.addStaticHoliday ("27-MAR-2043", "Good Friday");

		lh.addStaticHoliday ("30-MAR-2043", "Easter Monday");

		lh.addStaticHoliday ("08-JUN-2043", "Queens Birthday");

		lh.addStaticHoliday ("03-AUG-2043", "Bank Holiday");

		lh.addStaticHoliday ("05-OCT-2043", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2043", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2043", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2044", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2044", "Australia Day");

		lh.addStaticHoliday ("15-APR-2044", "Good Friday");

		lh.addStaticHoliday ("18-APR-2044", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2044", "Anzac Day");

		lh.addStaticHoliday ("13-JUN-2044", "Queens Birthday");

		lh.addStaticHoliday ("01-AUG-2044", "Bank Holiday");

		lh.addStaticHoliday ("03-OCT-2044", "Labour Day");

		lh.addStaticHoliday ("26-DEC-2044", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2044", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2045", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2045", "Australia Day");

		lh.addStaticHoliday ("07-APR-2045", "Good Friday");

		lh.addStaticHoliday ("10-APR-2045", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2045", "Anzac Day");

		lh.addStaticHoliday ("12-JUN-2045", "Queens Birthday");

		lh.addStaticHoliday ("07-AUG-2045", "Bank Holiday");

		lh.addStaticHoliday ("02-OCT-2045", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2045", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2045", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2046", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2046", "Australia Day");

		lh.addStaticHoliday ("23-MAR-2046", "Good Friday");

		lh.addStaticHoliday ("26-MAR-2046", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2046", "Anzac Day");

		lh.addStaticHoliday ("11-JUN-2046", "Queens Birthday");

		lh.addStaticHoliday ("06-AUG-2046", "Bank Holiday");

		lh.addStaticHoliday ("01-OCT-2046", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2046", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2046", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2047", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2047", "Australia Day Observed");

		lh.addStaticHoliday ("12-APR-2047", "Good Friday");

		lh.addStaticHoliday ("15-APR-2047", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2047", "Anzac Day");

		lh.addStaticHoliday ("10-JUN-2047", "Queens Birthday");

		lh.addStaticHoliday ("05-AUG-2047", "Bank Holiday");

		lh.addStaticHoliday ("07-OCT-2047", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2047", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2047", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2048", "New Years Day");

		lh.addStaticHoliday ("27-JAN-2048", "Australia Day Observed");

		lh.addStaticHoliday ("03-APR-2048", "Good Friday");

		lh.addStaticHoliday ("06-APR-2048", "Easter Monday");

		lh.addStaticHoliday ("08-JUN-2048", "Queens Birthday");

		lh.addStaticHoliday ("03-AUG-2048", "Bank Holiday");

		lh.addStaticHoliday ("05-OCT-2048", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2048", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2048", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2049", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2049", "Australia Day");

		lh.addStaticHoliday ("16-APR-2049", "Good Friday");

		lh.addStaticHoliday ("19-APR-2049", "Easter Monday");

		lh.addStaticHoliday ("26-APR-2049", "Anzac Day Observed");

		lh.addStaticHoliday ("14-JUN-2049", "Queens Birthday");

		lh.addStaticHoliday ("02-AUG-2049", "Bank Holiday");

		lh.addStaticHoliday ("04-OCT-2049", "Labour Day");

		lh.addStaticHoliday ("27-DEC-2049", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2049", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2050", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2050", "Australia Day");

		lh.addStaticHoliday ("08-APR-2050", "Good Friday");

		lh.addStaticHoliday ("11-APR-2050", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2050", "Anzac Day");

		lh.addStaticHoliday ("13-JUN-2050", "Queens Birthday");

		lh.addStaticHoliday ("01-AUG-2050", "Bank Holiday");

		lh.addStaticHoliday ("03-OCT-2050", "Labour Day");

		lh.addStaticHoliday ("26-DEC-2050", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2050", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2051", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2051", "Australia Day");

		lh.addStaticHoliday ("31-MAR-2051", "Good Friday");

		lh.addStaticHoliday ("03-APR-2051", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2051", "Anzac Day");

		lh.addStaticHoliday ("12-JUN-2051", "Queens Birthday");

		lh.addStaticHoliday ("07-AUG-2051", "Bank Holiday");

		lh.addStaticHoliday ("02-OCT-2051", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2051", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2051", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2052", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2052", "Australia Day");

		lh.addStaticHoliday ("19-APR-2052", "Good Friday");

		lh.addStaticHoliday ("22-APR-2052", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2052", "Anzac Day");

		lh.addStaticHoliday ("10-JUN-2052", "Queens Birthday");

		lh.addStaticHoliday ("05-AUG-2052", "Bank Holiday");

		lh.addStaticHoliday ("07-OCT-2052", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2052", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2052", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2053", "New Years Day");

		lh.addStaticHoliday ("27-JAN-2053", "Australia Day Observed");

		lh.addStaticHoliday ("04-APR-2053", "Good Friday");

		lh.addStaticHoliday ("07-APR-2053", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2053", "Anzac Day");

		lh.addStaticHoliday ("09-JUN-2053", "Queens Birthday");

		lh.addStaticHoliday ("04-AUG-2053", "Bank Holiday");

		lh.addStaticHoliday ("06-OCT-2053", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2053", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2053", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2054", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2054", "Australia Day");

		lh.addStaticHoliday ("27-MAR-2054", "Good Friday");

		lh.addStaticHoliday ("30-MAR-2054", "Easter Monday");

		lh.addStaticHoliday ("08-JUN-2054", "Queens Birthday");

		lh.addStaticHoliday ("03-AUG-2054", "Bank Holiday");

		lh.addStaticHoliday ("05-OCT-2054", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2054", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2054", "Boxing Day Observed");

		lh.addStaticHoliday ("01-JAN-2055", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2055", "Australia Day");

		lh.addStaticHoliday ("16-APR-2055", "Good Friday");

		lh.addStaticHoliday ("19-APR-2055", "Easter Monday");

		lh.addStaticHoliday ("26-APR-2055", "Anzac Day Observed");

		lh.addStaticHoliday ("14-JUN-2055", "Queens Birthday");

		lh.addStaticHoliday ("02-AUG-2055", "Bank Holiday");

		lh.addStaticHoliday ("04-OCT-2055", "Labour Day");

		lh.addStaticHoliday ("27-DEC-2055", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2055", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2056", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2056", "Australia Day");

		lh.addStaticHoliday ("31-MAR-2056", "Good Friday");

		lh.addStaticHoliday ("03-APR-2056", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2056", "Anzac Day");

		lh.addStaticHoliday ("12-JUN-2056", "Queens Birthday");

		lh.addStaticHoliday ("07-AUG-2056", "Bank Holiday");

		lh.addStaticHoliday ("02-OCT-2056", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2056", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2056", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2057", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2057", "Australia Day");

		lh.addStaticHoliday ("20-APR-2057", "Good Friday");

		lh.addStaticHoliday ("23-APR-2057", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2057", "Anzac Day");

		lh.addStaticHoliday ("11-JUN-2057", "Queens Birthday");

		lh.addStaticHoliday ("06-AUG-2057", "Bank Holiday");

		lh.addStaticHoliday ("01-OCT-2057", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2057", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2057", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2058", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2058", "Australia Day Observed");

		lh.addStaticHoliday ("12-APR-2058", "Good Friday");

		lh.addStaticHoliday ("15-APR-2058", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2058", "Anzac Day");

		lh.addStaticHoliday ("10-JUN-2058", "Queens Birthday");

		lh.addStaticHoliday ("05-AUG-2058", "Bank Holiday");

		lh.addStaticHoliday ("07-OCT-2058", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2058", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2058", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2059", "New Years Day");

		lh.addStaticHoliday ("27-JAN-2059", "Australia Day Observed");

		lh.addStaticHoliday ("28-MAR-2059", "Good Friday");

		lh.addStaticHoliday ("31-MAR-2059", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2059", "Anzac Day");

		lh.addStaticHoliday ("09-JUN-2059", "Queens Birthday");

		lh.addStaticHoliday ("04-AUG-2059", "Bank Holiday");

		lh.addStaticHoliday ("06-OCT-2059", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2059", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2059", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2060", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2060", "Australia Day");

		lh.addStaticHoliday ("16-APR-2060", "Good Friday");

		lh.addStaticHoliday ("19-APR-2060", "Easter Monday");

		lh.addStaticHoliday ("26-APR-2060", "Anzac Day Observed");

		lh.addStaticHoliday ("14-JUN-2060", "Queens Birthday");

		lh.addStaticHoliday ("02-AUG-2060", "Bank Holiday");

		lh.addStaticHoliday ("04-OCT-2060", "Labour Day");

		lh.addStaticHoliday ("27-DEC-2060", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2060", "Boxing Day Observed");

		lh.addStaticHoliday ("03-JAN-2061", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2061", "Australia Day");

		lh.addStaticHoliday ("08-APR-2061", "Good Friday");

		lh.addStaticHoliday ("11-APR-2061", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2061", "Anzac Day");

		lh.addStaticHoliday ("13-JUN-2061", "Queens Birthday");

		lh.addStaticHoliday ("01-AUG-2061", "Bank Holiday");

		lh.addStaticHoliday ("03-OCT-2061", "Labour Day");

		lh.addStaticHoliday ("26-DEC-2061", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2061", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2062", "New Years Day Observed");

		lh.addStaticHoliday ("26-JAN-2062", "Australia Day");

		lh.addStaticHoliday ("24-MAR-2062", "Good Friday");

		lh.addStaticHoliday ("27-MAR-2062", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2062", "Anzac Day");

		lh.addStaticHoliday ("12-JUN-2062", "Queens Birthday");

		lh.addStaticHoliday ("07-AUG-2062", "Bank Holiday");

		lh.addStaticHoliday ("02-OCT-2062", "Labour Day");

		lh.addStaticHoliday ("25-DEC-2062", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2062", "Boxing Day");

		lh.addStandardWeekend();

		return lh;
	}
}
