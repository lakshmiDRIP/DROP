
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

/**
 * <i>JMDHoliday</i> holds the JMD Holidays.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Date, Cash Flow, and Cash Flow Period Measure Generation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/holset/README.md">Built in Locale Holiday Set</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class JMDHoliday implements org.drip.analytics.holset.LocationHoliday {

	/**
	 * JMDHoliday Constructor
	 */

	public JMDHoliday()
	{
	}

	@Override public java.lang.String getHolidayLoc()
	{
		return "JMD";
	}

	@Override public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("25-FEB-1998", "Ash Wednesday");

		lh.addStaticHoliday ("10-APR-1998", "Good Friday");

		lh.addStaticHoliday ("13-APR-1998", "Easter Monday");

		lh.addStaticHoliday ("25-MAY-1998", "Labour Day Observed");

		lh.addStaticHoliday ("06-AUG-1998", "Independence Day");

		lh.addStaticHoliday ("19-OCT-1998", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-1998", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("17-FEB-1999", "Ash Wednesday");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("05-APR-1999", "Easter Monday");

		lh.addStaticHoliday ("24-MAY-1999", "Labour Day Observed");

		lh.addStaticHoliday ("06-AUG-1999", "Independence Day");

		lh.addStaticHoliday ("18-OCT-1999", "Heroes Day");

		lh.addStaticHoliday ("08-MAR-2000", "Ash Wednesday");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("24-APR-2000", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2000", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2000", "Emancipation Day");

		lh.addStaticHoliday ("16-OCT-2000", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2000", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("28-FEB-2001", "Ash Wednesday");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("16-APR-2001", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2001", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2001", "Emancipation Day");

		lh.addStaticHoliday ("06-AUG-2001", "Independence Day");

		lh.addStaticHoliday ("15-OCT-2001", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2001", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("13-FEB-2002", "Ash Wednesday");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("01-APR-2002", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2002", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2002", "Emancipation Day");

		lh.addStaticHoliday ("06-AUG-2002", "Independence Day");

		lh.addStaticHoliday ("21-OCT-2002", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2002", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("05-MAR-2003", "Ash Wednesday");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("21-APR-2003", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2003", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2003", "Emancipation Day");

		lh.addStaticHoliday ("06-AUG-2003", "Independence Day");

		lh.addStaticHoliday ("20-OCT-2003", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2003", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("25-FEB-2004", "Ash Wednesday");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("12-APR-2004", "Easter Monday");

		lh.addStaticHoliday ("24-MAY-2004", "Labour Day Observed");

		lh.addStaticHoliday ("06-AUG-2004", "Independence Day");

		lh.addStaticHoliday ("18-OCT-2004", "Heroes Day");

		lh.addStaticHoliday ("09-FEB-2005", "Ash Wednesday");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("28-MAR-2005", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2005", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2005", "Emancipation Day");

		lh.addStaticHoliday ("17-OCT-2005", "Heroes Day");

		lh.addStaticHoliday ("26-DEC-2005", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2005", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2006", "New Years Day Observed");

		lh.addStaticHoliday ("01-MAR-2006", "Ash Wednesday");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("17-APR-2006", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2006", "Emancipation Day");

		lh.addStaticHoliday ("16-OCT-2006", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2006", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("21-FEB-2007", "Ash Wednesday");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("09-APR-2007", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2007", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2007", "Emancipation Day");

		lh.addStaticHoliday ("06-AUG-2007", "Independence Day");

		lh.addStaticHoliday ("15-OCT-2007", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2007", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("06-FEB-2008", "Ash Wednesday");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("24-MAR-2008", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2008", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2008", "Emancipation Day");

		lh.addStaticHoliday ("06-AUG-2008", "Independence Day");

		lh.addStaticHoliday ("20-OCT-2008", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2008", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("25-FEB-2009", "Ash Wednesday");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("13-APR-2009", "Easter Monday");

		lh.addStaticHoliday ("25-MAY-2009", "Labour Day Observed");

		lh.addStaticHoliday ("06-AUG-2009", "Independence Day");

		lh.addStaticHoliday ("19-OCT-2009", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("17-FEB-2010", "Ash Wednesday");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("05-APR-2010", "Easter Monday");

		lh.addStaticHoliday ("24-MAY-2010", "Labour Day Observed");

		lh.addStaticHoliday ("06-AUG-2010", "Independence Day");

		lh.addStaticHoliday ("18-OCT-2010", "Heroes Day");

		lh.addStaticHoliday ("09-MAR-2011", "Ash Wednesday");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("25-APR-2011", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2011", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2011", "Emancipation Day");

		lh.addStaticHoliday ("17-OCT-2011", "Heroes Day");

		lh.addStaticHoliday ("26-DEC-2011", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2011", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2012", "New Years Day Observed");

		lh.addStaticHoliday ("22-FEB-2012", "Ash Wednesday");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("09-APR-2012", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2012", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2012", "Emancipation Day");

		lh.addStaticHoliday ("06-AUG-2012", "Independence Day");

		lh.addStaticHoliday ("15-OCT-2012", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2012", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("13-FEB-2013", "Ash Wednesday");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("01-APR-2013", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2013", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2013", "Emancipation Day");

		lh.addStaticHoliday ("06-AUG-2013", "Independence Day");

		lh.addStaticHoliday ("21-OCT-2013", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2013", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("05-MAR-2014", "Ash Wednesday");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("21-APR-2014", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2014", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2014", "Emancipation Day");

		lh.addStaticHoliday ("06-AUG-2014", "Independence Day");

		lh.addStaticHoliday ("20-OCT-2014", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2014", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("18-FEB-2015", "Ash Wednesday");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("06-APR-2015", "Easter Monday");

		lh.addStaticHoliday ("25-MAY-2015", "Labour Day Observed");

		lh.addStaticHoliday ("06-AUG-2015", "Independence Day");

		lh.addStaticHoliday ("19-OCT-2015", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("10-FEB-2016", "Ash Wednesday");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("28-MAR-2016", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2016", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2016", "Emancipation Day");

		lh.addStaticHoliday ("17-OCT-2016", "Heroes Day");

		lh.addStaticHoliday ("26-DEC-2016", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2016", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2017", "New Years Day Observed");

		lh.addStaticHoliday ("01-MAR-2017", "Ash Wednesday");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("17-APR-2017", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2017", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2017", "Emancipation Day");

		lh.addStaticHoliday ("16-OCT-2017", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2017", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("14-FEB-2018", "Ash Wednesday");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("02-APR-2018", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2018", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2018", "Emancipation Day");

		lh.addStaticHoliday ("06-AUG-2018", "Independence Day");

		lh.addStaticHoliday ("15-OCT-2018", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2018", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("06-MAR-2019", "Ash Wednesday");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("22-APR-2019", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2019", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2019", "Emancipation Day");

		lh.addStaticHoliday ("06-AUG-2019", "Independence Day");

		lh.addStaticHoliday ("21-OCT-2019", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2019", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("26-FEB-2020", "Ash Wednesday");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("13-APR-2020", "Easter Monday");

		lh.addStaticHoliday ("25-MAY-2020", "Labour Day Observed");

		lh.addStaticHoliday ("06-AUG-2020", "Independence Day");

		lh.addStaticHoliday ("19-OCT-2020", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("17-FEB-2021", "Ash Wednesday");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("05-APR-2021", "Easter Monday");

		lh.addStaticHoliday ("24-MAY-2021", "Labour Day Observed");

		lh.addStaticHoliday ("06-AUG-2021", "Independence Day");

		lh.addStaticHoliday ("18-OCT-2021", "Heroes Day");

		lh.addStaticHoliday ("02-MAR-2022", "Ash Wednesday");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("18-APR-2022", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2022", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2022", "Emancipation Day");

		lh.addStaticHoliday ("17-OCT-2022", "Heroes Day");

		lh.addStaticHoliday ("26-DEC-2022", "Christmas Day Observed");

		lh.addStaticHoliday ("27-DEC-2022", "Boxing Day Observed");

		lh.addStaticHoliday ("02-JAN-2023", "New Years Day Observed");

		lh.addStaticHoliday ("22-FEB-2023", "Ash Wednesday");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("10-APR-2023", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2023", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2023", "Emancipation Day");

		lh.addStaticHoliday ("16-OCT-2023", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2023", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("14-FEB-2024", "Ash Wednesday");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("01-APR-2024", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2024", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2024", "Emancipation Day");

		lh.addStaticHoliday ("06-AUG-2024", "Independence Day");

		lh.addStaticHoliday ("21-OCT-2024", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2024", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("05-MAR-2025", "Ash Wednesday");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("21-APR-2025", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2025", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2025", "Emancipation Day");

		lh.addStaticHoliday ("06-AUG-2025", "Independence Day");

		lh.addStaticHoliday ("20-OCT-2025", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2025", "Boxing Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("18-FEB-2026", "Ash Wednesday");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("06-APR-2026", "Easter Monday");

		lh.addStaticHoliday ("25-MAY-2026", "Labour Day Observed");

		lh.addStaticHoliday ("06-AUG-2026", "Independence Day");

		lh.addStaticHoliday ("19-OCT-2026", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("10-FEB-2027", "Ash Wednesday");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("29-MAR-2027", "Easter Monday");

		lh.addStaticHoliday ("24-MAY-2027", "Labour Day Observed");

		lh.addStaticHoliday ("06-AUG-2027", "Independence Day");

		lh.addStaticHoliday ("18-OCT-2027", "Heroes Day");

		lh.addStaticHoliday ("01-MAR-2028", "Ash Wednesday");

		lh.addStaticHoliday ("14-APR-2028", "Good Friday");

		lh.addStaticHoliday ("17-APR-2028", "Easter Monday");

		lh.addStaticHoliday ("23-MAY-2028", "Labour Day");

		lh.addStaticHoliday ("01-AUG-2028", "Emancipation Day");

		lh.addStaticHoliday ("16-OCT-2028", "Heroes Day");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2028", "Boxing Day");

		lh.addStandardWeekend();

		return lh;
	}
}
