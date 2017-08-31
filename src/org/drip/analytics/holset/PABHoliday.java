
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

public class PABHoliday implements org.drip.analytics.holset.LocationHoliday {
	public PABHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "PAB";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("12-JAN-1998", "Day of Mourning");

		lh.addStaticHoliday ("23-FEB-1998", "Carnival Monday");

		lh.addStaticHoliday ("24-FEB-1998", "Carnival Tuesday");

		lh.addStaticHoliday ("10-APR-1998", "Good Friday");

		lh.addStaticHoliday ("04-MAY-1998", "Labour Day");

		lh.addStaticHoliday ("03-NOV-1998", "Independence from Columbia Day");

		lh.addStaticHoliday ("04-NOV-1998", "Flag Day");

		lh.addStaticHoliday ("09-NOV-1998", "First Cry of Independence Day");

		lh.addStaticHoliday ("07-DEC-1998", "Mothers Day");

		lh.addStaticHoliday ("25-DEC-1998", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("15-FEB-1999", "Carnival Monday");

		lh.addStaticHoliday ("16-FEB-1999", "Carnival Tuesday");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("03-NOV-1999", "Independence from Columbia Day");

		lh.addStaticHoliday ("04-NOV-1999", "Flag Day");

		lh.addStaticHoliday ("08-NOV-1999", "First Cry of Independence Day");

		lh.addStaticHoliday ("06-DEC-1999", "Mothers Day");

		lh.addStaticHoliday ("06-MAR-2000", "Carnival Monday");

		lh.addStaticHoliday ("07-MAR-2000", "Carnival Tuesday");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2000", "Labour Day");

		lh.addStaticHoliday ("15-AUG-2000", "Foundation of Panama City Day");

		lh.addStaticHoliday ("03-NOV-2000", "Independence from Columbia Day");

		lh.addStaticHoliday ("13-NOV-2000", "First Cry of Independence Day");

		lh.addStaticHoliday ("27-NOV-2000", "Independence from Spain Day");

		lh.addStaticHoliday ("11-DEC-2000", "Mothers Day");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("08-JAN-2001", "Day of Mourning");

		lh.addStaticHoliday ("26-FEB-2001", "Carnival Monday");

		lh.addStaticHoliday ("27-FEB-2001", "Carnival Tuesday");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("30-APR-2001", "Labour Day");

		lh.addStaticHoliday ("15-AUG-2001", "Foundation of Panama City Day");

		lh.addStaticHoliday ("26-NOV-2001", "Independence from Spain Day");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2002", "Day of Mourning");

		lh.addStaticHoliday ("11-FEB-2002", "Carnival Monday");

		lh.addStaticHoliday ("12-FEB-2002", "Carnival Tuesday");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("29-APR-2002", "Labour Day");

		lh.addStaticHoliday ("15-AUG-2002", "Foundation of Panama City Day");

		lh.addStaticHoliday ("04-NOV-2002", "Flag Day");

		lh.addStaticHoliday ("02-DEC-2002", "Independence from Spain Day");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("13-JAN-2003", "Day of Mourning");

		lh.addStaticHoliday ("03-MAR-2003", "Carnival Monday");

		lh.addStaticHoliday ("04-MAR-2003", "Carnival Tuesday");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("05-MAY-2003", "Labour Day");

		lh.addStaticHoliday ("15-AUG-2003", "Foundation of Panama City Day");

		lh.addStaticHoliday ("03-NOV-2003", "Independence from Columbia Day");

		lh.addStaticHoliday ("04-NOV-2003", "Flag Day");

		lh.addStaticHoliday ("10-NOV-2003", "First Cry of Independence Day");

		lh.addStaticHoliday ("01-DEC-2003", "Independence from Spain Day");

		lh.addStaticHoliday ("08-DEC-2003", "Mothers Day");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("12-JAN-2004", "Day of Mourning");

		lh.addStaticHoliday ("23-FEB-2004", "Carnival Monday");

		lh.addStaticHoliday ("24-FEB-2004", "Carnival Tuesday");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("03-NOV-2004", "Independence from Columbia Day");

		lh.addStaticHoliday ("04-NOV-2004", "Flag Day");

		lh.addStaticHoliday ("08-NOV-2004", "First Cry of Independence Day");

		lh.addStaticHoliday ("06-DEC-2004", "Mothers Day");

		lh.addStaticHoliday ("07-FEB-2005", "Carnival Monday");

		lh.addStaticHoliday ("08-FEB-2005", "Carnival Tuesday");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("15-AUG-2005", "Foundation of Panama City Day");

		lh.addStaticHoliday ("03-NOV-2005", "Independence from Columbia Day");

		lh.addStaticHoliday ("04-NOV-2005", "Flag Day");

		lh.addStaticHoliday ("14-NOV-2005", "First Cry of Independence Day");

		lh.addStaticHoliday ("28-NOV-2005", "Independence from Spain Day");

		lh.addStaticHoliday ("12-DEC-2005", "Mothers Day");

		lh.addStaticHoliday ("09-JAN-2006", "Day of Mourning");

		lh.addStaticHoliday ("27-FEB-2006", "Carnival Monday");

		lh.addStaticHoliday ("28-FEB-2006", "Carnival Tuesday");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("15-AUG-2006", "Foundation of Panama City Day");

		lh.addStaticHoliday ("03-NOV-2006", "Independence from Columbia Day");

		lh.addStaticHoliday ("13-NOV-2006", "First Cry of Independence Day");

		lh.addStaticHoliday ("27-NOV-2006", "Independence from Spain Day");

		lh.addStaticHoliday ("11-DEC-2006", "Mothers Day");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("08-JAN-2007", "Day of Mourning");

		lh.addStaticHoliday ("19-FEB-2007", "Carnival Monday");

		lh.addStaticHoliday ("20-FEB-2007", "Carnival Tuesday");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("30-APR-2007", "Labour Day");

		lh.addStaticHoliday ("15-AUG-2007", "Foundation of Panama City Day");

		lh.addStaticHoliday ("26-NOV-2007", "Independence from Spain Day");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2008", "Day of Mourning");

		lh.addStaticHoliday ("04-FEB-2008", "Carnival Monday");

		lh.addStaticHoliday ("05-FEB-2008", "Carnival Tuesday");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("05-MAY-2008", "Labour Day");

		lh.addStaticHoliday ("15-AUG-2008", "Foundation of Panama City Day");

		lh.addStaticHoliday ("03-NOV-2008", "Independence from Columbia Day");

		lh.addStaticHoliday ("04-NOV-2008", "Flag Day");

		lh.addStaticHoliday ("10-NOV-2008", "First Cry of Independence Day");

		lh.addStaticHoliday ("01-DEC-2008", "Independence from Spain Day");

		lh.addStaticHoliday ("08-DEC-2008", "Mothers Day");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("12-JAN-2009", "Day of Mourning");

		lh.addStaticHoliday ("23-FEB-2009", "Carnival Monday");

		lh.addStaticHoliday ("24-FEB-2009", "Carnival Tuesday");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("04-MAY-2009", "Labour Day");

		lh.addStaticHoliday ("03-NOV-2009", "Independence from Columbia Day");

		lh.addStaticHoliday ("04-NOV-2009", "Flag Day");

		lh.addStaticHoliday ("09-NOV-2009", "First Cry of Independence Day");

		lh.addStaticHoliday ("07-DEC-2009", "Mothers Day");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("15-FEB-2010", "Carnival Monday");

		lh.addStaticHoliday ("16-FEB-2010", "Carnival Tuesday");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("03-NOV-2010", "Independence from Columbia Day");

		lh.addStaticHoliday ("04-NOV-2010", "Flag Day");

		lh.addStaticHoliday ("08-NOV-2010", "First Cry of Independence Day");

		lh.addStaticHoliday ("06-DEC-2010", "Mothers Day");

		lh.addStaticHoliday ("07-MAR-2011", "Carnival Monday");

		lh.addStaticHoliday ("08-MAR-2011", "Carnival Tuesday");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("15-AUG-2011", "Foundation of Panama City Day");

		lh.addStaticHoliday ("03-NOV-2011", "Independence from Columbia Day");

		lh.addStaticHoliday ("04-NOV-2011", "Flag Day");

		lh.addStaticHoliday ("14-NOV-2011", "First Cry of Independence Day");

		lh.addStaticHoliday ("28-NOV-2011", "Independence from Spain Day");

		lh.addStaticHoliday ("12-DEC-2011", "Mothers Day");

		lh.addStaticHoliday ("09-JAN-2012", "Day of Mourning");

		lh.addStaticHoliday ("20-FEB-2012", "Carnival Monday");

		lh.addStaticHoliday ("21-FEB-2012", "Carnival Tuesday");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("30-APR-2012", "Labour Day");

		lh.addStaticHoliday ("15-AUG-2012", "Foundation of Panama City Day");

		lh.addStaticHoliday ("26-NOV-2012", "Independence from Spain Day");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2013", "Day of Mourning");

		lh.addStaticHoliday ("11-FEB-2013", "Carnival Monday");

		lh.addStaticHoliday ("12-FEB-2013", "Carnival Tuesday");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("29-APR-2013", "Labour Day");

		lh.addStaticHoliday ("15-AUG-2013", "Foundation of Panama City Day");

		lh.addStaticHoliday ("04-NOV-2013", "Flag Day");

		lh.addStaticHoliday ("02-DEC-2013", "Independence from Spain Day");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("13-JAN-2014", "Day of Mourning");

		lh.addStaticHoliday ("03-MAR-2014", "Carnival Monday");

		lh.addStaticHoliday ("04-MAR-2014", "Carnival Tuesday");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("05-MAY-2014", "Labour Day");

		lh.addStaticHoliday ("15-AUG-2014", "Foundation of Panama City Day");

		lh.addStaticHoliday ("03-NOV-2014", "Independence from Columbia Day");

		lh.addStaticHoliday ("04-NOV-2014", "Flag Day");

		lh.addStaticHoliday ("10-NOV-2014", "First Cry of Independence Day");

		lh.addStaticHoliday ("01-DEC-2014", "Independence from Spain Day");

		lh.addStaticHoliday ("08-DEC-2014", "Mothers Day");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("12-JAN-2015", "Day of Mourning");

		lh.addStaticHoliday ("16-FEB-2015", "Carnival Monday");

		lh.addStaticHoliday ("17-FEB-2015", "Carnival Tuesday");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("04-MAY-2015", "Labour Day");

		lh.addStaticHoliday ("03-NOV-2015", "Independence from Columbia Day");

		lh.addStaticHoliday ("04-NOV-2015", "Flag Day");

		lh.addStaticHoliday ("09-NOV-2015", "First Cry of Independence Day");

		lh.addStaticHoliday ("07-DEC-2015", "Mothers Day");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2016", "Carnival Monday");

		lh.addStaticHoliday ("09-FEB-2016", "Carnival Tuesday");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("15-AUG-2016", "Foundation of Panama City Day");

		lh.addStaticHoliday ("03-NOV-2016", "Independence from Columbia Day");

		lh.addStaticHoliday ("04-NOV-2016", "Flag Day");

		lh.addStaticHoliday ("14-NOV-2016", "First Cry of Independence Day");

		lh.addStaticHoliday ("28-NOV-2016", "Independence from Spain Day");

		lh.addStaticHoliday ("12-DEC-2016", "Mothers Day");

		lh.addStaticHoliday ("09-JAN-2017", "Day of Mourning");

		lh.addStaticHoliday ("27-FEB-2017", "Carnival Monday");

		lh.addStaticHoliday ("28-FEB-2017", "Carnival Tuesday");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2017", "Labour Day");

		lh.addStaticHoliday ("15-AUG-2017", "Foundation of Panama City Day");

		lh.addStaticHoliday ("03-NOV-2017", "Independence from Columbia Day");

		lh.addStaticHoliday ("13-NOV-2017", "First Cry of Independence Day");

		lh.addStaticHoliday ("27-NOV-2017", "Independence from Spain Day");

		lh.addStaticHoliday ("11-DEC-2017", "Mothers Day");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("08-JAN-2018", "Day of Mourning");

		lh.addStaticHoliday ("12-FEB-2018", "Carnival Monday");

		lh.addStaticHoliday ("13-FEB-2018", "Carnival Tuesday");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("30-APR-2018", "Labour Day");

		lh.addStaticHoliday ("15-AUG-2018", "Foundation of Panama City Day");

		lh.addStaticHoliday ("26-NOV-2018", "Independence from Spain Day");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2019", "Day of Mourning");

		lh.addStaticHoliday ("04-MAR-2019", "Carnival Monday");

		lh.addStaticHoliday ("05-MAR-2019", "Carnival Tuesday");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("29-APR-2019", "Labour Day");

		lh.addStaticHoliday ("15-AUG-2019", "Foundation of Panama City Day");

		lh.addStaticHoliday ("04-NOV-2019", "Flag Day");

		lh.addStaticHoliday ("02-DEC-2019", "Independence from Spain Day");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("13-JAN-2020", "Day of Mourning");

		lh.addStaticHoliday ("24-FEB-2020", "Carnival Monday");

		lh.addStaticHoliday ("25-FEB-2020", "Carnival Tuesday");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("04-MAY-2020", "Labour Day");

		lh.addStaticHoliday ("03-NOV-2020", "Independence from Columbia Day");

		lh.addStaticHoliday ("04-NOV-2020", "Flag Day");

		lh.addStaticHoliday ("09-NOV-2020", "First Cry of Independence Day");

		lh.addStaticHoliday ("07-DEC-2020", "Mothers Day");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("15-FEB-2021", "Carnival Monday");

		lh.addStaticHoliday ("16-FEB-2021", "Carnival Tuesday");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("03-NOV-2021", "Independence from Columbia Day");

		lh.addStaticHoliday ("04-NOV-2021", "Flag Day");

		lh.addStaticHoliday ("08-NOV-2021", "First Cry of Independence Day");

		lh.addStaticHoliday ("06-DEC-2021", "Mothers Day");

		lh.addStaticHoliday ("28-FEB-2022", "Carnival Monday");

		lh.addStaticHoliday ("01-MAR-2022", "Carnival Tuesday");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("15-AUG-2022", "Foundation of Panama City Day");

		lh.addStaticHoliday ("03-NOV-2022", "Independence from Columbia Day");

		lh.addStaticHoliday ("04-NOV-2022", "Flag Day");

		lh.addStaticHoliday ("14-NOV-2022", "First Cry of Independence Day");

		lh.addStaticHoliday ("28-NOV-2022", "Independence from Spain Day");

		lh.addStaticHoliday ("12-DEC-2022", "Mothers Day");

		lh.addStaticHoliday ("09-JAN-2023", "Day of Mourning");

		lh.addStaticHoliday ("20-FEB-2023", "Carnival Monday");

		lh.addStaticHoliday ("21-FEB-2023", "Carnival Tuesday");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2023", "Labour Day");

		lh.addStaticHoliday ("15-AUG-2023", "Foundation of Panama City Day");

		lh.addStaticHoliday ("03-NOV-2023", "Independence from Columbia Day");

		lh.addStaticHoliday ("13-NOV-2023", "First Cry of Independence Day");

		lh.addStaticHoliday ("27-NOV-2023", "Independence from Spain Day");

		lh.addStaticHoliday ("11-DEC-2023", "Mothers Day");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("08-JAN-2024", "Day of Mourning");

		lh.addStaticHoliday ("12-FEB-2024", "Carnival Monday");

		lh.addStaticHoliday ("13-FEB-2024", "Carnival Tuesday");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("29-APR-2024", "Labour Day");

		lh.addStaticHoliday ("15-AUG-2024", "Foundation of Panama City Day");

		lh.addStaticHoliday ("04-NOV-2024", "Flag Day");

		lh.addStaticHoliday ("02-DEC-2024", "Independence from Spain Day");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("13-JAN-2025", "Day of Mourning");

		lh.addStaticHoliday ("03-MAR-2025", "Carnival Monday");

		lh.addStaticHoliday ("04-MAR-2025", "Carnival Tuesday");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("05-MAY-2025", "Labour Day");

		lh.addStaticHoliday ("15-AUG-2025", "Foundation of Panama City Day");

		lh.addStaticHoliday ("03-NOV-2025", "Independence from Columbia Day");

		lh.addStaticHoliday ("04-NOV-2025", "Flag Day");

		lh.addStaticHoliday ("10-NOV-2025", "First Cry of Independence Day");

		lh.addStaticHoliday ("01-DEC-2025", "Independence from Spain Day");

		lh.addStaticHoliday ("08-DEC-2025", "Mothers Day");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("12-JAN-2026", "Day of Mourning");

		lh.addStaticHoliday ("16-FEB-2026", "Carnival Monday");

		lh.addStaticHoliday ("17-FEB-2026", "Carnival Tuesday");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("04-MAY-2026", "Labour Day");

		lh.addStaticHoliday ("03-NOV-2026", "Independence from Columbia Day");

		lh.addStaticHoliday ("04-NOV-2026", "Flag Day");

		lh.addStaticHoliday ("09-NOV-2026", "First Cry of Independence Day");

		lh.addStaticHoliday ("07-DEC-2026", "Mothers Day");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2027", "Carnival Monday");

		lh.addStaticHoliday ("09-FEB-2027", "Carnival Tuesday");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("03-NOV-2027", "Independence from Columbia Day");

		lh.addStaticHoliday ("04-NOV-2027", "Flag Day");

		lh.addStaticHoliday ("08-NOV-2027", "First Cry of Independence Day");

		lh.addStaticHoliday ("06-DEC-2027", "Mothers Day");

		lh.addStaticHoliday ("28-FEB-2028", "Carnival Monday");

		lh.addStaticHoliday ("29-FEB-2028", "Carnival Tuesday");

		lh.addStaticHoliday ("14-APR-2028", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2028", "Labour Day");

		lh.addStaticHoliday ("15-AUG-2028", "Foundation of Panama City Day");

		lh.addStaticHoliday ("03-NOV-2028", "Independence from Columbia Day");

		lh.addStaticHoliday ("13-NOV-2028", "First Cry of Independence Day");

		lh.addStaticHoliday ("27-NOV-2028", "Independence from Spain Day");

		lh.addStaticHoliday ("11-DEC-2028", "Mothers Day");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStandardWeekend();

		return lh;
	}
}
