
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

public class ILSHoliday implements org.drip.analytics.holset.LocationHoliday {
	public ILSHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "ILS";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("12-MAR-1998", "Feast of Purim");

		lh.addStaticHoliday ("17-APR-1998", "Last Day of Passover");

		lh.addStaticHoliday ("30-APR-1998", "Independence Day");

		lh.addStaticHoliday ("21-SEP-1998", "Jewish New Year");

		lh.addStaticHoliday ("22-SEP-1998", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("29-SEP-1998", "Day of Atonement Eve");

		lh.addStaticHoliday ("30-SEP-1998", "Day of Atonement");

		lh.addStaticHoliday ("05-OCT-1998", "First Day of Tabernacles");

		lh.addStaticHoliday ("12-OCT-1998", "Last Day of Tabernacles");

		lh.addStaticHoliday ("02-MAR-1999", "Feast of Purim");

		lh.addStaticHoliday ("01-APR-1999", "First Day of Passover");

		lh.addStaticHoliday ("07-APR-1999", "Last Day of Passover");

		lh.addStaticHoliday ("21-APR-1999", "Independence Day");

		lh.addStaticHoliday ("21-MAY-1999", "Feast of Pentecost");

		lh.addStaticHoliday ("22-JUL-1999", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("20-SEP-1999", "Day of Atonement");

		lh.addStaticHoliday ("21-MAR-2000", "Feast of Purim");

		lh.addStaticHoliday ("20-APR-2000", "First Day of Passover");

		lh.addStaticHoliday ("26-APR-2000", "Last Day of Passover");

		lh.addStaticHoliday ("10-MAY-2000", "Independence Day");

		lh.addStaticHoliday ("09-JUN-2000", "Feast of Pentecost");

		lh.addStaticHoliday ("10-AUG-2000", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("09-OCT-2000", "Day of Atonement");

		lh.addStaticHoliday ("09-MAR-2001", "Feast of Purim");

		lh.addStaticHoliday ("26-APR-2001", "Independence Day");

		lh.addStaticHoliday ("28-MAY-2001", "Feast of Pentecost");

		lh.addStaticHoliday ("18-SEP-2001", "Jewish New Year");

		lh.addStaticHoliday ("19-SEP-2001", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("26-SEP-2001", "Day of Atonement Eve");

		lh.addStaticHoliday ("27-SEP-2001", "Day of Atonement");

		lh.addStaticHoliday ("02-OCT-2001", "First Day of Tabernacles");

		lh.addStaticHoliday ("09-OCT-2001", "Last Day of Tabernacles");

		lh.addStaticHoliday ("26-FEB-2002", "Feast of Purim");

		lh.addStaticHoliday ("28-MAR-2002", "First Day of Passover");

		lh.addStaticHoliday ("03-APR-2002", "Last Day of Passover");

		lh.addStaticHoliday ("17-APR-2002", "Independence Day");

		lh.addStaticHoliday ("17-MAY-2002", "Feast of Pentecost");

		lh.addStaticHoliday ("18-JUL-2002", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("16-SEP-2002", "Day of Atonement");

		lh.addStaticHoliday ("18-MAR-2003", "Feast of Purim");

		lh.addStaticHoliday ("17-APR-2003", "First Day of Passover");

		lh.addStaticHoliday ("23-APR-2003", "Last Day of Passover");

		lh.addStaticHoliday ("07-MAY-2003", "Independence Day");

		lh.addStaticHoliday ("06-JUN-2003", "Feast of Pentecost");

		lh.addStaticHoliday ("07-AUG-2003", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("06-OCT-2003", "Day of Atonement");

		lh.addStaticHoliday ("06-APR-2004", "First Day of Passover");

		lh.addStaticHoliday ("12-APR-2004", "Last Day of Passover");

		lh.addStaticHoliday ("26-APR-2004", "Independence Day");

		lh.addStaticHoliday ("26-MAY-2004", "Feast of Pentecost");

		lh.addStaticHoliday ("27-JUL-2004", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("16-SEP-2004", "Jewish New Year");

		lh.addStaticHoliday ("17-SEP-2004", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("24-SEP-2004", "Day of Atonement Eve");

		lh.addStaticHoliday ("30-SEP-2004", "First Day of Tabernacles");

		lh.addStaticHoliday ("07-OCT-2004", "Last Day of Tabernacles");

		lh.addStaticHoliday ("25-MAR-2005", "Feast of Purim");

		lh.addStaticHoliday ("12-MAY-2005", "Independence Day");

		lh.addStaticHoliday ("13-JUN-2005", "Feast of Pentecost");

		lh.addStaticHoliday ("04-OCT-2005", "Jewish New Year");

		lh.addStaticHoliday ("05-OCT-2005", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("12-OCT-2005", "Day of Atonement Eve");

		lh.addStaticHoliday ("13-OCT-2005", "Day of Atonement");

		lh.addStaticHoliday ("18-OCT-2005", "First Day of Tabernacles");

		lh.addStaticHoliday ("25-OCT-2005", "Last Day of Tabernacles");

		lh.addStaticHoliday ("14-MAR-2006", "Feast of Purim");

		lh.addStaticHoliday ("13-APR-2006", "First Day of Passover");

		lh.addStaticHoliday ("19-APR-2006", "Last Day of Passover");

		lh.addStaticHoliday ("03-MAY-2006", "Independence Day");

		lh.addStaticHoliday ("02-JUN-2006", "Feast of Pentecost");

		lh.addStaticHoliday ("03-AUG-2006", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("02-OCT-2006", "Day of Atonement");

		lh.addStaticHoliday ("03-APR-2007", "First Day of Passover");

		lh.addStaticHoliday ("09-APR-2007", "Last Day of Passover");

		lh.addStaticHoliday ("23-APR-2007", "Independence Day");

		lh.addStaticHoliday ("23-MAY-2007", "Feast of Pentecost");

		lh.addStaticHoliday ("24-JUL-2007", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("13-SEP-2007", "Jewish New Year");

		lh.addStaticHoliday ("14-SEP-2007", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("21-SEP-2007", "Day of Atonement Eve");

		lh.addStaticHoliday ("27-SEP-2007", "First Day of Tabernacles");

		lh.addStaticHoliday ("04-OCT-2007", "Last Day of Tabernacles");

		lh.addStaticHoliday ("21-MAR-2008", "Feast of Purim");

		lh.addStaticHoliday ("08-MAY-2008", "Independence Day");

		lh.addStaticHoliday ("09-JUN-2008", "Feast of Pentecost");

		lh.addStaticHoliday ("30-SEP-2008", "Jewish New Year");

		lh.addStaticHoliday ("01-OCT-2008", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("08-OCT-2008", "Day of Atonement Eve");

		lh.addStaticHoliday ("09-OCT-2008", "Day of Atonement");

		lh.addStaticHoliday ("14-OCT-2008", "First Day of Tabernacles");

		lh.addStaticHoliday ("21-OCT-2008", "Last Day of Tabernacles");

		lh.addStaticHoliday ("10-MAR-2009", "Feast of Purim");

		lh.addStaticHoliday ("09-APR-2009", "First Day of Passover");

		lh.addStaticHoliday ("15-APR-2009", "Last Day of Passover");

		lh.addStaticHoliday ("29-APR-2009", "Independence Day");

		lh.addStaticHoliday ("29-MAY-2009", "Feast of Pentecost");

		lh.addStaticHoliday ("30-JUL-2009", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("28-SEP-2009", "Day of Atonement");

		lh.addStaticHoliday ("30-MAR-2010", "First Day of Passover");

		lh.addStaticHoliday ("05-APR-2010", "Last Day of Passover");

		lh.addStaticHoliday ("19-APR-2010", "Independence Day");

		lh.addStaticHoliday ("19-MAY-2010", "Feast of Pentecost");

		lh.addStaticHoliday ("20-JUL-2010", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("09-SEP-2010", "Jewish New Year");

		lh.addStaticHoliday ("10-SEP-2010", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("17-SEP-2010", "Day of Atonement Eve");

		lh.addStaticHoliday ("23-SEP-2010", "First Day of Tabernacles");

		lh.addStaticHoliday ("30-SEP-2010", "Last Day of Tabernacles");

		lh.addStaticHoliday ("19-APR-2011", "First Day of Passover");

		lh.addStaticHoliday ("25-APR-2011", "Last Day of Passover");

		lh.addStaticHoliday ("09-MAY-2011", "Independence Day");

		lh.addStaticHoliday ("08-JUN-2011", "Feast of Pentecost");

		lh.addStaticHoliday ("09-AUG-2011", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("29-SEP-2011", "Jewish New Year");

		lh.addStaticHoliday ("30-SEP-2011", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("07-OCT-2011", "Day of Atonement Eve");

		lh.addStaticHoliday ("13-OCT-2011", "First Day of Tabernacles");

		lh.addStaticHoliday ("20-OCT-2011", "Last Day of Tabernacles");

		lh.addStaticHoliday ("08-MAR-2012", "Feast of Purim");

		lh.addStaticHoliday ("13-APR-2012", "Last Day of Passover");

		lh.addStaticHoliday ("26-APR-2012", "Independence Day");

		lh.addStaticHoliday ("17-SEP-2012", "Jewish New Year");

		lh.addStaticHoliday ("18-SEP-2012", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("25-SEP-2012", "Day of Atonement Eve");

		lh.addStaticHoliday ("26-SEP-2012", "Day of Atonement");

		lh.addStaticHoliday ("01-OCT-2012", "First Day of Tabernacles");

		lh.addStaticHoliday ("08-OCT-2012", "Last Day of Tabernacles");

		lh.addStaticHoliday ("26-MAR-2013", "First Day of Passover");

		lh.addStaticHoliday ("01-APR-2013", "Last Day of Passover");

		lh.addStaticHoliday ("15-APR-2013", "Independence Day");

		lh.addStaticHoliday ("15-MAY-2013", "Feast of Pentecost");

		lh.addStaticHoliday ("16-JUL-2013", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("05-SEP-2013", "Jewish New Year");

		lh.addStaticHoliday ("06-SEP-2013", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("13-SEP-2013", "Day of Atonement Eve");

		lh.addStaticHoliday ("19-SEP-2013", "First Day of Tabernacles");

		lh.addStaticHoliday ("26-SEP-2013", "Last Day of Tabernacles");

		lh.addStaticHoliday ("15-APR-2014", "First Day of Passover");

		lh.addStaticHoliday ("21-APR-2014", "Last Day of Passover");

		lh.addStaticHoliday ("05-MAY-2014", "Independence Day");

		lh.addStaticHoliday ("04-JUN-2014", "Feast of Pentecost");

		lh.addStaticHoliday ("05-AUG-2014", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("25-SEP-2014", "Jewish New Year");

		lh.addStaticHoliday ("26-SEP-2014", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("03-OCT-2014", "Day of Atonement Eve");

		lh.addStaticHoliday ("09-OCT-2014", "First Day of Tabernacles");

		lh.addStaticHoliday ("16-OCT-2014", "Last Day of Tabernacles");

		lh.addStaticHoliday ("05-MAR-2015", "Feast of Purim");

		lh.addStaticHoliday ("10-APR-2015", "Last Day of Passover");

		lh.addStaticHoliday ("23-APR-2015", "Independence Day");

		lh.addStaticHoliday ("14-SEP-2015", "Jewish New Year");

		lh.addStaticHoliday ("15-SEP-2015", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("22-SEP-2015", "Day of Atonement Eve");

		lh.addStaticHoliday ("23-SEP-2015", "Day of Atonement");

		lh.addStaticHoliday ("28-SEP-2015", "First Day of Tabernacles");

		lh.addStaticHoliday ("05-OCT-2015", "Last Day of Tabernacles");

		lh.addStaticHoliday ("24-MAR-2016", "Feast of Purim");

		lh.addStaticHoliday ("29-APR-2016", "Last Day of Passover");

		lh.addStaticHoliday ("12-MAY-2016", "Independence Day");

		lh.addStaticHoliday ("03-OCT-2016", "Jewish New Year");

		lh.addStaticHoliday ("04-OCT-2016", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("11-OCT-2016", "Day of Atonement Eve");

		lh.addStaticHoliday ("12-OCT-2016", "Day of Atonement");

		lh.addStaticHoliday ("17-OCT-2016", "First Day of Tabernacles");

		lh.addStaticHoliday ("24-OCT-2016", "Last Day of Tabernacles");

		lh.addStaticHoliday ("11-APR-2017", "First Day of Passover");

		lh.addStaticHoliday ("17-APR-2017", "Last Day of Passover");

		lh.addStaticHoliday ("01-MAY-2017", "Independence Day");

		lh.addStaticHoliday ("31-MAY-2017", "Feast of Pentecost");

		lh.addStaticHoliday ("01-AUG-2017", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("21-SEP-2017", "Jewish New Year");

		lh.addStaticHoliday ("22-SEP-2017", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("29-SEP-2017", "Day of Atonement Eve");

		lh.addStaticHoliday ("05-OCT-2017", "First Day of Tabernacles");

		lh.addStaticHoliday ("12-OCT-2017", "Last Day of Tabernacles");

		lh.addStaticHoliday ("01-MAR-2018", "Feast of Purim");

		lh.addStaticHoliday ("06-APR-2018", "Last Day of Passover");

		lh.addStaticHoliday ("19-APR-2018", "Independence Day");

		lh.addStaticHoliday ("10-SEP-2018", "Jewish New Year");

		lh.addStaticHoliday ("11-SEP-2018", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("18-SEP-2018", "Day of Atonement Eve");

		lh.addStaticHoliday ("19-SEP-2018", "Day of Atonement");

		lh.addStaticHoliday ("24-SEP-2018", "First Day of Tabernacles");

		lh.addStaticHoliday ("01-OCT-2018", "Last Day of Tabernacles");

		lh.addStaticHoliday ("21-MAR-2019", "Feast of Purim");

		lh.addStaticHoliday ("26-APR-2019", "Last Day of Passover");

		lh.addStaticHoliday ("09-MAY-2019", "Independence Day");

		lh.addStaticHoliday ("30-SEP-2019", "Jewish New Year");

		lh.addStaticHoliday ("01-OCT-2019", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("08-OCT-2019", "Day of Atonement Eve");

		lh.addStaticHoliday ("09-OCT-2019", "Day of Atonement");

		lh.addStaticHoliday ("14-OCT-2019", "First Day of Tabernacles");

		lh.addStaticHoliday ("21-OCT-2019", "Last Day of Tabernacles");

		lh.addStaticHoliday ("10-MAR-2020", "Feast of Purim");

		lh.addStaticHoliday ("09-APR-2020", "First Day of Passover");

		lh.addStaticHoliday ("15-APR-2020", "Last Day of Passover");

		lh.addStaticHoliday ("29-APR-2020", "Independence Day");

		lh.addStaticHoliday ("29-MAY-2020", "Feast of Pentecost");

		lh.addStaticHoliday ("30-JUL-2020", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("28-SEP-2020", "Day of Atonement");

		lh.addStaticHoliday ("26-FEB-2021", "Feast of Purim");

		lh.addStaticHoliday ("15-APR-2021", "Independence Day");

		lh.addStaticHoliday ("17-MAY-2021", "Feast of Pentecost");

		lh.addStaticHoliday ("07-SEP-2021", "Jewish New Year");

		lh.addStaticHoliday ("08-SEP-2021", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("15-SEP-2021", "Day of Atonement Eve");

		lh.addStaticHoliday ("16-SEP-2021", "Day of Atonement");

		lh.addStaticHoliday ("21-SEP-2021", "First Day of Tabernacles");

		lh.addStaticHoliday ("28-SEP-2021", "Last Day of Tabernacles");

		lh.addStaticHoliday ("17-MAR-2022", "Feast of Purim");

		lh.addStaticHoliday ("22-APR-2022", "Last Day of Passover");

		lh.addStaticHoliday ("05-MAY-2022", "Independence Day");

		lh.addStaticHoliday ("26-SEP-2022", "Jewish New Year");

		lh.addStaticHoliday ("27-SEP-2022", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("04-OCT-2022", "Day of Atonement Eve");

		lh.addStaticHoliday ("05-OCT-2022", "Day of Atonement");

		lh.addStaticHoliday ("10-OCT-2022", "First Day of Tabernacles");

		lh.addStaticHoliday ("17-OCT-2022", "Last Day of Tabernacles");

		lh.addStaticHoliday ("07-MAR-2023", "Feast of Purim");

		lh.addStaticHoliday ("06-APR-2023", "First Day of Passover");

		lh.addStaticHoliday ("12-APR-2023", "Last Day of Passover");

		lh.addStaticHoliday ("26-APR-2023", "Independence Day");

		lh.addStaticHoliday ("26-MAY-2023", "Feast of Pentecost");

		lh.addStaticHoliday ("27-JUL-2023", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("25-SEP-2023", "Day of Atonement");

		lh.addStaticHoliday ("23-APR-2024", "First Day of Passover");

		lh.addStaticHoliday ("29-APR-2024", "Last Day of Passover");

		lh.addStaticHoliday ("13-MAY-2024", "Independence Day");

		lh.addStaticHoliday ("12-JUN-2024", "Feast of Pentecost");

		lh.addStaticHoliday ("13-AUG-2024", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("03-OCT-2024", "Jewish New Year");

		lh.addStaticHoliday ("04-OCT-2024", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("11-OCT-2024", "Day of Atonement Eve");

		lh.addStaticHoliday ("17-OCT-2024", "First Day of Tabernacles");

		lh.addStaticHoliday ("24-OCT-2024", "Last Day of Tabernacles");

		lh.addStaticHoliday ("14-MAR-2025", "Feast of Purim");

		lh.addStaticHoliday ("01-MAY-2025", "Independence Day");

		lh.addStaticHoliday ("02-JUN-2025", "Feast of Pentecost");

		lh.addStaticHoliday ("23-SEP-2025", "Jewish New Year");

		lh.addStaticHoliday ("24-SEP-2025", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("01-OCT-2025", "Day of Atonement Eve");

		lh.addStaticHoliday ("02-OCT-2025", "Day of Atonement");

		lh.addStaticHoliday ("07-OCT-2025", "First Day of Tabernacles");

		lh.addStaticHoliday ("14-OCT-2025", "Last Day of Tabernacles");

		lh.addStaticHoliday ("03-MAR-2026", "Feast of Purim");

		lh.addStaticHoliday ("02-APR-2026", "First Day of Passover");

		lh.addStaticHoliday ("08-APR-2026", "Last Day of Passover");

		lh.addStaticHoliday ("22-APR-2026", "Independence Day");

		lh.addStaticHoliday ("22-MAY-2026", "Feast of Pentecost");

		lh.addStaticHoliday ("23-JUL-2026", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("21-SEP-2026", "Day of Atonement");

		lh.addStaticHoliday ("23-MAR-2027", "Feast of Purim");

		lh.addStaticHoliday ("22-APR-2027", "First Day of Passover");

		lh.addStaticHoliday ("28-APR-2027", "Last Day of Passover");

		lh.addStaticHoliday ("12-MAY-2027", "Independence Day");

		lh.addStaticHoliday ("11-JUN-2027", "Feast of Pentecost");

		lh.addStaticHoliday ("12-AUG-2027", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("11-OCT-2027", "Day of Atonement");

		lh.addStaticHoliday ("11-APR-2028", "First Day of Passover");

		lh.addStaticHoliday ("17-APR-2028", "Last Day of Passover");

		lh.addStaticHoliday ("01-MAY-2028", "Independence Day");

		lh.addStaticHoliday ("31-MAY-2028", "Feast of Pentecost");

		lh.addStaticHoliday ("01-AUG-2028", "Fast of Ninth of Ab");

		lh.addStaticHoliday ("21-SEP-2028", "Jewish New Year");

		lh.addStaticHoliday ("22-SEP-2028", "Second Day of the Jewish New Year");

		lh.addStaticHoliday ("29-SEP-2028", "Day of Atonement Eve");

		lh.addStaticHoliday ("05-OCT-2028", "First Day of Tabernacles");

		lh.addStaticHoliday ("12-OCT-2028", "Last Day of Tabernacles");

		lh.addStandardWeekend();

		return lh;
	}
}
