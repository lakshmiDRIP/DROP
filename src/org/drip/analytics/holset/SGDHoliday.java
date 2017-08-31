
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

public class SGDHoliday implements org.drip.analytics.holset.LocationHoliday {
	public SGDHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "SGD";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("19-JAN-1999", "Hari Raya Puasa");

		lh.addStaticHoliday ("16-FEB-1999", "Chinese New Year");

		lh.addStaticHoliday ("17-FEB-1999", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("29-MAR-1999", "Hari Raya Haji Observed");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("09-AUG-1999", "National Day");

		lh.addStaticHoliday ("08-NOV-1999", "Deepavali Observed");

		lh.addStaticHoliday ("31-DEC-1999", "Special Holiday");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("24-JAN-2001", "Chinese New Year");

		lh.addStaticHoliday ("25-JAN-2001", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("06-MAR-2001", "Hari Raya Haji");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2001", "Labour Day");

		lh.addStaticHoliday ("07-MAY-2001", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2001", "National Day");

		lh.addStaticHoliday ("03-NOV-2001", "Election Day");

		lh.addStaticHoliday ("14-NOV-2001", "Deepavali");

		lh.addStaticHoliday ("17-DEC-2001", "Hari Raya Puasa Observed");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("12-FEB-2002", "Chinese New Year");

		lh.addStaticHoliday ("13-FEB-2002", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("23-FEB-2002", "Hari Raya Haji");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2002", "Labour Day");

		lh.addStaticHoliday ("27-MAY-2002", "Vesak Day Observed");

		lh.addStaticHoliday ("09-AUG-2002", "National Day");

		lh.addStaticHoliday ("04-NOV-2002", "Deepavali");

		lh.addStaticHoliday ("06-DEC-2002", "Hari Raya Puasa");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("01-FEB-2003", "Chinese New Year");

		lh.addStaticHoliday ("03-FEB-2003", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("12-FEB-2003", "Hari Raya Haji");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2003", "Labour Day");

		lh.addStaticHoliday ("15-MAY-2003", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2003", "National Day");

		lh.addStaticHoliday ("24-OCT-2003", "Deepavali");

		lh.addStaticHoliday ("25-NOV-2003", "Hari Raya Puasa");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("22-JAN-2004", "Chinese New Year");

		lh.addStaticHoliday ("23-JAN-2004", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("01-FEB-2004", "Hari Raya Haji");

		lh.addStaticHoliday ("02-FEB-2004", "Special Holiday");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2004", "Labour Day");

		lh.addStaticHoliday ("02-JUN-2004", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2004", "National Day");

		lh.addStaticHoliday ("11-NOV-2004", "Deepavali");

		lh.addStaticHoliday ("14-NOV-2004", "Hari Raya Puasa");

		lh.addStaticHoliday ("15-NOV-2004", "Hari Raya Puasa Observed");

		lh.addStaticHoliday ("25-DEC-2004", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2005", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2005", "Hari Raya Haji");

		lh.addStaticHoliday ("09-FEB-2005", "Chinese New Year");

		lh.addStaticHoliday ("10-FEB-2005", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("02-MAY-2005", "Labour Day Observed");

		lh.addStaticHoliday ("23-MAY-2005", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2005", "National Day");

		lh.addStaticHoliday ("01-NOV-2005", "Deepavali");

		lh.addStaticHoliday ("03-NOV-2005", "Hari Raya Puasa");

		lh.addStaticHoliday ("26-DEC-2005", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2006", "New Years Day Observed");

		lh.addStaticHoliday ("10-JAN-2006", "Hari Raya Haji");

		lh.addStaticHoliday ("30-JAN-2006", "Chinese New Year Observed");

		lh.addStaticHoliday ("31-JAN-2006", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("06-MAY-2006", "Election Day");

		lh.addStaticHoliday ("12-MAY-2006", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2006", "National Day");

		lh.addStaticHoliday ("21-OCT-2006", "Deepavali");

		lh.addStaticHoliday ("24-OCT-2006", "Hari Raya Puasa");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2007", "Bank Holiday");

		lh.addStaticHoliday ("19-FEB-2007", "Chinese New Year Observed");

		lh.addStaticHoliday ("20-FEB-2007", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2007", "Labour Day");

		lh.addStaticHoliday ("31-MAY-2007", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2007", "National Day");

		lh.addStaticHoliday ("13-OCT-2007", "Hari Raya Puasa");

		lh.addStaticHoliday ("08-NOV-2007", "Deepavali");

		lh.addStaticHoliday ("20-DEC-2007", "Hari Raya Haji");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("07-FEB-2008", "Chinese New Year");

		lh.addStaticHoliday ("08-FEB-2008", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2008", "Labour Day");

		lh.addStaticHoliday ("19-MAY-2008", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2008", "National Day");

		lh.addStaticHoliday ("01-OCT-2008", "Hari Raya Puasa");

		lh.addStaticHoliday ("27-OCT-2008", "Deepavali");

		lh.addStaticHoliday ("08-DEC-2008", "Hari Raya Haji");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2009", "Chinese New Year");

		lh.addStaticHoliday ("27-JAN-2009", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2009", "Labour Day");

		lh.addStaticHoliday ("09-MAY-2009", "Vesak Day");

		lh.addStaticHoliday ("10-AUG-2009", "National Day Observed");

		lh.addStaticHoliday ("21-SEP-2009", "Hari Raya Puasa Observed");

		lh.addStaticHoliday ("17-OCT-2009", "Deepavali");

		lh.addStaticHoliday ("27-NOV-2009", "Hari Raya Haji");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("15-FEB-2010", "Chinese New Year Observed");

		lh.addStaticHoliday ("16-FEB-2010", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2010", "Labour Day");

		lh.addStaticHoliday ("28-MAY-2010", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2010", "National Day");

		lh.addStaticHoliday ("10-SEP-2010", "Hari Raya Puasa");

		lh.addStaticHoliday ("05-NOV-2010", "Deepavali");

		lh.addStaticHoliday ("17-NOV-2010", "Hari Raya Haji");

		lh.addStaticHoliday ("25-DEC-2010", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2011", "New Years Day");

		lh.addStaticHoliday ("03-FEB-2011", "Chinese New Year");

		lh.addStaticHoliday ("04-FEB-2011", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("02-MAY-2011", "Labour Day Observed");

		lh.addStaticHoliday ("07-MAY-2011", "Bank Holiday");

		lh.addStaticHoliday ("17-MAY-2011", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2011", "National Day");

		lh.addStaticHoliday ("30-AUG-2011", "Hari Raya Puasa");

		lh.addStaticHoliday ("26-OCT-2011", "Deepavali");

		lh.addStaticHoliday ("07-NOV-2011", "Hari Raya Haji Observed");

		lh.addStaticHoliday ("26-DEC-2011", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2012", "New Years Day Observed");

		lh.addStaticHoliday ("23-JAN-2012", "Chinese New Year");

		lh.addStaticHoliday ("24-JAN-2012", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2012", "Labour Day");

		lh.addStaticHoliday ("09-AUG-2012", "National Day");

		lh.addStaticHoliday ("20-AUG-2012", "Hari Raya Puasa Observed");

		lh.addStaticHoliday ("26-OCT-2012", "Hari Raya Haji");

		lh.addStaticHoliday ("14-NOV-2012", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2013", "Chinese New Year Observed");

		lh.addStaticHoliday ("12-FEB-2013", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2013", "Labour Day");

		lh.addStaticHoliday ("24-MAY-2013", "Vesak Day");

		lh.addStaticHoliday ("08-AUG-2013", "Hari Raya Puasa");

		lh.addStaticHoliday ("09-AUG-2013", "National Day");

		lh.addStaticHoliday ("15-OCT-2013", "Hari Raya Haji");

		lh.addStaticHoliday ("04-NOV-2013", "Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("31-JAN-2014", "Chinese New Year");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2014", "Labour Day");

		lh.addStaticHoliday ("13-MAY-2014", "Vesak Day");

		lh.addStaticHoliday ("28-JUL-2014", "Hari Raya Puasa");

		lh.addStaticHoliday ("06-OCT-2014", "Hari Raya Haji Observed");

		lh.addStaticHoliday ("22-OCT-2014", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("19-FEB-2015", "Chinese New Year");

		lh.addStaticHoliday ("20-FEB-2015", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2015", "Labour Day");

		lh.addStaticHoliday ("01-JUN-2015", "Vesak Day");

		lh.addStaticHoliday ("17-JUL-2015", "Hari Raya Puasa");

		lh.addStaticHoliday ("10-AUG-2015", "National Day Observed");

		lh.addStaticHoliday ("24-SEP-2015", "Hari Raya Haji");

		lh.addStaticHoliday ("10-NOV-2015", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2016", "Chinese New Year");

		lh.addStaticHoliday ("09-FEB-2016", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("02-MAY-2016", "Labour Day Observed");

		lh.addStaticHoliday ("06-JUL-2016", "Hari Raya Puasa");

		lh.addStaticHoliday ("09-AUG-2016", "National Day");

		lh.addStaticHoliday ("12-SEP-2016", "Hari Raya Haji");

		lh.addStaticHoliday ("26-DEC-2016", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2017", "New Years Day Observed");

		lh.addStaticHoliday ("30-JAN-2017", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2017", "Labour Day");

		lh.addStaticHoliday ("10-MAY-2017", "Vesak Day");

		lh.addStaticHoliday ("26-JUN-2017", "Hari Raya Puasa Observed");

		lh.addStaticHoliday ("09-AUG-2017", "National Day");

		lh.addStaticHoliday ("01-SEP-2017", "Hari Raya Haji");

		lh.addStaticHoliday ("18-OCT-2017", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("16-FEB-2018", "Chinese New Year");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2018", "Labour Day");

		lh.addStaticHoliday ("29-MAY-2018", "Vesak Day");

		lh.addStaticHoliday ("15-JUN-2018", "Hari Raya Puasa");

		lh.addStaticHoliday ("09-AUG-2018", "National Day");

		lh.addStaticHoliday ("22-AUG-2018", "Hari Raya Haji");

		lh.addStaticHoliday ("06-NOV-2018", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("05-FEB-2019", "Chinese New Year");

		lh.addStaticHoliday ("06-FEB-2019", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2019", "Labour Day");

		lh.addStaticHoliday ("20-MAY-2019", "Vesak Day Observed");

		lh.addStaticHoliday ("05-JUN-2019", "Hari Raya Puasa");

		lh.addStaticHoliday ("09-AUG-2019", "National Day");

		lh.addStaticHoliday ("12-AUG-2019", "Hari Raya Haji Observed");

		lh.addStaticHoliday ("28-OCT-2019", "Deepavali Observed");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("27-JAN-2020", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2020", "Labour Day");

		lh.addStaticHoliday ("07-MAY-2020", "Vesak Day");

		lh.addStaticHoliday ("25-MAY-2020", "Hari Raya Puasa Observed");

		lh.addStaticHoliday ("31-JUL-2020", "Hari Raya Haji");

		lh.addStaticHoliday ("10-AUG-2020", "National Day Observed");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("12-FEB-2021", "Chinese New Year");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("13-MAY-2021", "Hari Raya Puasa");

		lh.addStaticHoliday ("26-MAY-2021", "Vesak Day");

		lh.addStaticHoliday ("20-JUL-2021", "Hari Raya Haji");

		lh.addStaticHoliday ("09-AUG-2021", "National Day");

		lh.addStaticHoliday ("04-NOV-2021", "Deepavali");

		lh.addStaticHoliday ("01-FEB-2022", "Chinese New Year");

		lh.addStaticHoliday ("02-FEB-2022", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("02-MAY-2022", "Labour Day Observed");

		lh.addStaticHoliday ("16-MAY-2022", "Vesak Day Observed");

		lh.addStaticHoliday ("09-AUG-2022", "National Day");

		lh.addStaticHoliday ("24-OCT-2022", "Deepavali");

		lh.addStaticHoliday ("26-DEC-2022", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2023", "New Years Day Observed");

		lh.addStaticHoliday ("23-JAN-2023", "Chinese New Year Observed");

		lh.addStaticHoliday ("24-JAN-2023", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("21-APR-2023", "Hari Raya Puasa");

		lh.addStaticHoliday ("01-MAY-2023", "Labour Day");

		lh.addStaticHoliday ("02-JUN-2023", "Vesak Day");

		lh.addStaticHoliday ("28-JUN-2023", "Hari Raya Haji");

		lh.addStaticHoliday ("09-AUG-2023", "National Day");

		lh.addStaticHoliday ("13-NOV-2023", "Deepavali Observed");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("12-FEB-2024", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("10-APR-2024", "Hari Raya Puasa");

		lh.addStaticHoliday ("01-MAY-2024", "Labour Day");

		lh.addStaticHoliday ("22-MAY-2024", "Vesak Day");

		lh.addStaticHoliday ("17-JUN-2024", "Hari Raya Haji");

		lh.addStaticHoliday ("09-AUG-2024", "National Day");

		lh.addStaticHoliday ("31-OCT-2024", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("29-JAN-2025", "Chinese New Year");

		lh.addStaticHoliday ("30-JAN-2025", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("31-MAR-2025", "Hari Raya Puasa");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2025", "Labour Day");

		lh.addStaticHoliday ("12-MAY-2025", "Vesak Day");

		lh.addStaticHoliday ("06-JUN-2025", "Hari Raya Haji");

		lh.addStaticHoliday ("20-OCT-2025", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("17-FEB-2026", "Chinese New Year");

		lh.addStaticHoliday ("18-FEB-2026", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("20-MAR-2026", "Hari Raya Puasa");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2026", "Labour Day");

		lh.addStaticHoliday ("27-MAY-2026", "Hari Raya Haji");

		lh.addStaticHoliday ("01-JUN-2026", "Vesak Day Observed");

		lh.addStaticHoliday ("10-AUG-2026", "National Day Observed");

		lh.addStaticHoliday ("09-NOV-2026", "Deepavali Observed");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2027", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("10-MAR-2027", "Hari Raya Puasa");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("17-MAY-2027", "Hari Raya Haji");

		lh.addStaticHoliday ("20-MAY-2027", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2027", "National Day");

		lh.addStaticHoliday ("28-OCT-2027", "Deepavali");

		lh.addStaticHoliday ("26-JAN-2028", "Chinese New Year");

		lh.addStaticHoliday ("27-JAN-2028", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("28-FEB-2028", "Hari Raya Puasa Observed");

		lh.addStaticHoliday ("14-APR-2028", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2028", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2028", "Hari Raya Haji");

		lh.addStaticHoliday ("09-MAY-2028", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2028", "National Day");

		lh.addStaticHoliday ("17-OCT-2028", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2029", "New Years Day");

		lh.addStaticHoliday ("13-FEB-2029", "Chinese New Year");

		lh.addStaticHoliday ("14-FEB-2029", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("15-FEB-2029", "Hari Raya Puasa");

		lh.addStaticHoliday ("30-MAR-2029", "Good Friday");

		lh.addStaticHoliday ("24-APR-2029", "Hari Raya Haji");

		lh.addStaticHoliday ("01-MAY-2029", "Labour Day");

		lh.addStaticHoliday ("28-MAY-2029", "Vesak Day Observed");

		lh.addStaticHoliday ("09-AUG-2029", "National Day");

		lh.addStaticHoliday ("05-NOV-2029", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2029", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2030", "New Years Day");

		lh.addStaticHoliday ("04-FEB-2030", "Chinese New Year Observed");

		lh.addStaticHoliday ("05-FEB-2030", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("06-FEB-2030", "Hari Raya Puasa Observed");

		lh.addStaticHoliday ("19-APR-2030", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2030", "Labour Day");

		lh.addStaticHoliday ("16-MAY-2030", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2030", "National Day");

		lh.addStaticHoliday ("25-DEC-2030", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2031", "New Years Day");

		lh.addStaticHoliday ("23-JAN-2031", "Chinese New Year");

		lh.addStaticHoliday ("24-JAN-2031", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("03-APR-2031", "Hari Raya Haji");

		lh.addStaticHoliday ("11-APR-2031", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2031", "Labour Day");

		lh.addStaticHoliday ("06-MAY-2031", "Vesak Day");

		lh.addStaticHoliday ("14-NOV-2031", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2031", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2032", "New Years Day");

		lh.addStaticHoliday ("14-JAN-2032", "Hari Raya Puasa");

		lh.addStaticHoliday ("11-FEB-2032", "Chinese New Year");

		lh.addStaticHoliday ("12-FEB-2032", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("22-MAR-2032", "Hari Raya Haji");

		lh.addStaticHoliday ("26-MAR-2032", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2032", "Vesak Day Observed");

		lh.addStaticHoliday ("09-AUG-2032", "National Day");

		lh.addStaticHoliday ("02-NOV-2032", "Deepavali");

		lh.addStaticHoliday ("03-JAN-2033", "Hari Raya Puasa");

		lh.addStaticHoliday ("31-JAN-2033", "Chinese New Year");

		lh.addStaticHoliday ("01-FEB-2033", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("15-APR-2033", "Good Friday");

		lh.addStaticHoliday ("02-MAY-2033", "Labour Day Observed");

		lh.addStaticHoliday ("13-MAY-2033", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2033", "National Day");

		lh.addStaticHoliday ("23-DEC-2033", "Hari Raya Puasa");

		lh.addStaticHoliday ("26-DEC-2033", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2034", "New Years Day Observed");

		lh.addStaticHoliday ("20-FEB-2034", "Chinese New Year Observed");

		lh.addStaticHoliday ("21-FEB-2034", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("01-MAR-2034", "Hari Raya Haji");

		lh.addStaticHoliday ("07-APR-2034", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2034", "Labour Day");

		lh.addStaticHoliday ("01-JUN-2034", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2034", "National Day");

		lh.addStaticHoliday ("09-NOV-2034", "Deepavali");

		lh.addStaticHoliday ("12-DEC-2034", "Hari Raya Puasa");

		lh.addStaticHoliday ("25-DEC-2034", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2035", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2035", "Chinese New Year");

		lh.addStaticHoliday ("09-FEB-2035", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("19-FEB-2035", "Hari Raya Haji");

		lh.addStaticHoliday ("23-MAR-2035", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2035", "Labour Day");

		lh.addStaticHoliday ("22-MAY-2035", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2035", "National Day");

		lh.addStaticHoliday ("30-OCT-2035", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2035", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2036", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2036", "Chinese New Year");

		lh.addStaticHoliday ("29-JAN-2036", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("08-FEB-2036", "Hari Raya Haji");

		lh.addStaticHoliday ("11-APR-2036", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2036", "Labour Day");

		lh.addStaticHoliday ("19-NOV-2036", "Hari Raya Puasa");

		lh.addStaticHoliday ("25-DEC-2036", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2037", "New Years Day");

		lh.addStaticHoliday ("27-JAN-2037", "Hari Raya Haji");

		lh.addStaticHoliday ("16-FEB-2037", "Chinese New Year Observed");

		lh.addStaticHoliday ("17-FEB-2037", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("03-APR-2037", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2037", "Labour Day");

		lh.addStaticHoliday ("29-MAY-2037", "Vesak Day");

		lh.addStaticHoliday ("10-AUG-2037", "National Day Observed");

		lh.addStaticHoliday ("06-NOV-2037", "Deepavali");

		lh.addStaticHoliday ("09-NOV-2037", "Hari Raya Puasa");

		lh.addStaticHoliday ("25-DEC-2037", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2038", "New Years Day");

		lh.addStaticHoliday ("04-FEB-2038", "Chinese New Year");

		lh.addStaticHoliday ("05-FEB-2038", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("23-APR-2038", "Good Friday");

		lh.addStaticHoliday ("18-MAY-2038", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2038", "National Day");

		lh.addStaticHoliday ("27-OCT-2038", "Deepavali");

		lh.addStaticHoliday ("29-OCT-2038", "Hari Raya Puasa");

		lh.addStaticHoliday ("05-JAN-2039", "Hari Raya Haji");

		lh.addStaticHoliday ("24-JAN-2039", "Chinese New Year");

		lh.addStaticHoliday ("25-JAN-2039", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("08-APR-2039", "Good Friday");

		lh.addStaticHoliday ("02-MAY-2039", "Labour Day Observed");

		lh.addStaticHoliday ("09-AUG-2039", "National Day");

		lh.addStaticHoliday ("19-OCT-2039", "Hari Raya Puasa");

		lh.addStaticHoliday ("15-NOV-2039", "Deepavali");

		lh.addStaticHoliday ("26-DEC-2039", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2040", "New Years Day Observed");

		lh.addStaticHoliday ("13-FEB-2040", "Chinese New Year Observed");

		lh.addStaticHoliday ("14-FEB-2040", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("30-MAR-2040", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2040", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2040", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2040", "National Day");

		lh.addStaticHoliday ("08-OCT-2040", "Hari Raya Puasa");

		lh.addStaticHoliday ("25-DEC-2040", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2041", "New Years Day");

		lh.addStaticHoliday ("01-FEB-2041", "Chinese New Year");

		lh.addStaticHoliday ("19-APR-2041", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2041", "Labour Day");

		lh.addStaticHoliday ("14-MAY-2041", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2041", "National Day");

		lh.addStaticHoliday ("27-SEP-2041", "Hari Raya Puasa");

		lh.addStaticHoliday ("23-OCT-2041", "Deepavali");

		lh.addStaticHoliday ("04-DEC-2041", "Hari Raya Haji");

		lh.addStaticHoliday ("25-DEC-2041", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2042", "New Years Day");

		lh.addStaticHoliday ("22-JAN-2042", "Chinese New Year");

		lh.addStaticHoliday ("23-JAN-2042", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("04-APR-2042", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2042", "Labour Day");

		lh.addStaticHoliday ("02-JUN-2042", "Vesak Day");

		lh.addStaticHoliday ("16-SEP-2042", "Hari Raya Puasa");

		lh.addStaticHoliday ("11-NOV-2042", "Deepavali");

		lh.addStaticHoliday ("24-NOV-2042", "Hari Raya Haji Observed");

		lh.addStaticHoliday ("25-DEC-2042", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2043", "New Years Day");

		lh.addStaticHoliday ("10-FEB-2043", "Chinese New Year");

		lh.addStaticHoliday ("11-FEB-2043", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("27-MAR-2043", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2043", "Labour Day");

		lh.addStaticHoliday ("10-AUG-2043", "National Day Observed");

		lh.addStaticHoliday ("12-NOV-2043", "Hari Raya Haji");

		lh.addStaticHoliday ("25-DEC-2043", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2044", "New Years Day");

		lh.addStaticHoliday ("01-FEB-2044", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("15-APR-2044", "Good Friday");

		lh.addStaticHoliday ("02-MAY-2044", "Labour Day Observed");

		lh.addStaticHoliday ("12-MAY-2044", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2044", "National Day");

		lh.addStaticHoliday ("24-AUG-2044", "Hari Raya Puasa");

		lh.addStaticHoliday ("20-OCT-2044", "Deepavali");

		lh.addStaticHoliday ("31-OCT-2044", "Hari Raya Haji");

		lh.addStaticHoliday ("26-DEC-2044", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2045", "New Years Day Observed");

		lh.addStaticHoliday ("17-FEB-2045", "Chinese New Year");

		lh.addStaticHoliday ("07-APR-2045", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2045", "Labour Day");

		lh.addStaticHoliday ("31-MAY-2045", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2045", "National Day");

		lh.addStaticHoliday ("14-AUG-2045", "Hari Raya Puasa");

		lh.addStaticHoliday ("08-NOV-2045", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2045", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2046", "New Years Day");

		lh.addStaticHoliday ("06-FEB-2046", "Chinese New Year");

		lh.addStaticHoliday ("07-FEB-2046", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("23-MAR-2046", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2046", "Labour Day");

		lh.addStaticHoliday ("21-MAY-2046", "Vesak Day Observed");

		lh.addStaticHoliday ("09-AUG-2046", "National Day");

		lh.addStaticHoliday ("10-OCT-2046", "Hari Raya Haji");

		lh.addStaticHoliday ("29-OCT-2046", "Deepavali Observed");

		lh.addStaticHoliday ("25-DEC-2046", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2047", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2047", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("12-APR-2047", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2047", "Labour Day");

		lh.addStaticHoliday ("09-MAY-2047", "Vesak Day");

		lh.addStaticHoliday ("24-JUL-2047", "Hari Raya Puasa");

		lh.addStaticHoliday ("09-AUG-2047", "National Day");

		lh.addStaticHoliday ("30-SEP-2047", "Hari Raya Haji");

		lh.addStaticHoliday ("18-OCT-2047", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2047", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2048", "New Years Day");

		lh.addStaticHoliday ("14-FEB-2048", "Chinese New Year");

		lh.addStaticHoliday ("03-APR-2048", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2048", "Labour Day");

		lh.addStaticHoliday ("27-MAY-2048", "Vesak Day");

		lh.addStaticHoliday ("13-JUL-2048", "Hari Raya Puasa Observed");

		lh.addStaticHoliday ("10-AUG-2048", "National Day Observed");

		lh.addStaticHoliday ("05-NOV-2048", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2048", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2049", "New Years Day");

		lh.addStaticHoliday ("02-FEB-2049", "Chinese New Year");

		lh.addStaticHoliday ("03-FEB-2049", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("16-APR-2049", "Good Friday");

		lh.addStaticHoliday ("17-MAY-2049", "Vesak Day Observed");

		lh.addStaticHoliday ("01-JUL-2049", "Hari Raya Puasa");

		lh.addStaticHoliday ("09-AUG-2049", "National Day");

		lh.addStaticHoliday ("08-SEP-2049", "Hari Raya Haji");

		lh.addStaticHoliday ("25-OCT-2049", "Deepavali");

		lh.addStaticHoliday ("24-JAN-2050", "Chinese New Year Observed");

		lh.addStaticHoliday ("25-JAN-2050", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("08-APR-2050", "Good Friday");

		lh.addStaticHoliday ("02-MAY-2050", "Labour Day Observed");

		lh.addStaticHoliday ("05-MAY-2050", "Vesak Day");

		lh.addStaticHoliday ("21-JUN-2050", "Hari Raya Puasa");

		lh.addStaticHoliday ("09-AUG-2050", "National Day");

		lh.addStaticHoliday ("29-AUG-2050", "Hari Raya Haji Observed");

		lh.addStaticHoliday ("14-NOV-2050", "Deepavali Observed");

		lh.addStaticHoliday ("26-DEC-2050", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2051", "New Years Day Observed");

		lh.addStaticHoliday ("13-FEB-2051", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("31-MAR-2051", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2051", "Labour Day");

		lh.addStaticHoliday ("24-MAY-2051", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2051", "National Day");

		lh.addStaticHoliday ("17-AUG-2051", "Hari Raya Haji");

		lh.addStaticHoliday ("02-NOV-2051", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2051", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2052", "New Years Day");

		lh.addStaticHoliday ("01-FEB-2052", "Chinese New Year");

		lh.addStaticHoliday ("02-FEB-2052", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("19-APR-2052", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2052", "Labour Day");

		lh.addStaticHoliday ("13-MAY-2052", "Vesak Day");

		lh.addStaticHoliday ("30-MAY-2052", "Hari Raya Puasa");

		lh.addStaticHoliday ("05-AUG-2052", "Hari Raya Haji");

		lh.addStaticHoliday ("09-AUG-2052", "National Day");

		lh.addStaticHoliday ("21-OCT-2052", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2052", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2053", "New Years Day");

		lh.addStaticHoliday ("19-FEB-2053", "Chinese New Year");

		lh.addStaticHoliday ("20-FEB-2053", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("04-APR-2053", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2053", "Labour Day");

		lh.addStaticHoliday ("19-MAY-2053", "Hari Raya Puasa");

		lh.addStaticHoliday ("02-JUN-2053", "Vesak Day Observed");

		lh.addStaticHoliday ("10-NOV-2053", "Deepavali Observed");

		lh.addStaticHoliday ("25-DEC-2053", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2054", "New Years Day");

		lh.addStaticHoliday ("09-FEB-2054", "Chinese New Year Observed");

		lh.addStaticHoliday ("10-FEB-2054", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("27-MAR-2054", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2054", "Labour Day");

		lh.addStaticHoliday ("22-MAY-2054", "Vesak Day");

		lh.addStaticHoliday ("16-JUL-2054", "Hari Raya Haji");

		lh.addStaticHoliday ("10-AUG-2054", "National Day Observed");

		lh.addStaticHoliday ("30-OCT-2054", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2054", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2055", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2055", "Chinese New Year");

		lh.addStaticHoliday ("29-JAN-2055", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("16-APR-2055", "Good Friday");

		lh.addStaticHoliday ("28-APR-2055", "Hari Raya Puasa");

		lh.addStaticHoliday ("11-MAY-2055", "Vesak Day");

		lh.addStaticHoliday ("05-JUL-2055", "Hari Raya Haji");

		lh.addStaticHoliday ("09-AUG-2055", "National Day");

		lh.addStaticHoliday ("19-OCT-2055", "Deepavali");

		lh.addStaticHoliday ("15-FEB-2056", "Chinese New Year");

		lh.addStaticHoliday ("16-FEB-2056", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("31-MAR-2056", "Good Friday");

		lh.addStaticHoliday ("17-APR-2056", "Hari Raya Puasa Observed");

		lh.addStaticHoliday ("01-MAY-2056", "Labour Day");

		lh.addStaticHoliday ("29-MAY-2056", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2056", "National Day");

		lh.addStaticHoliday ("06-NOV-2056", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2056", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2057", "New Years Day");

		lh.addStaticHoliday ("05-FEB-2057", "Chinese New Year Observed");

		lh.addStaticHoliday ("06-FEB-2057", "Second Day of Chinese New Year Observed");

		lh.addStaticHoliday ("05-APR-2057", "Hari Raya Puasa");

		lh.addStaticHoliday ("20-APR-2057", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2057", "Labour Day");

		lh.addStaticHoliday ("18-MAY-2057", "Vesak Day");

		lh.addStaticHoliday ("13-JUN-2057", "Hari Raya Haji");

		lh.addStaticHoliday ("09-AUG-2057", "National Day");

		lh.addStaticHoliday ("25-DEC-2057", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2058", "New Years Day");

		lh.addStaticHoliday ("24-JAN-2058", "Chinese New Year");

		lh.addStaticHoliday ("25-JAN-2058", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("26-MAR-2058", "Hari Raya Puasa");

		lh.addStaticHoliday ("12-APR-2058", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2058", "Labour Day");

		lh.addStaticHoliday ("07-MAY-2058", "Vesak Day");

		lh.addStaticHoliday ("03-JUN-2058", "Hari Raya Haji Observed");

		lh.addStaticHoliday ("09-AUG-2058", "National Day");

		lh.addStaticHoliday ("15-NOV-2058", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2058", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2059", "New Years Day");

		lh.addStaticHoliday ("12-FEB-2059", "Chinese New Year");

		lh.addStaticHoliday ("13-FEB-2059", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("28-MAR-2059", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2059", "Labour Day");

		lh.addStaticHoliday ("22-MAY-2059", "Hari Raya Haji");

		lh.addStaticHoliday ("26-MAY-2059", "Vesak Day");

		lh.addStaticHoliday ("04-NOV-2059", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2059", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2060", "New Years Day");

		lh.addStaticHoliday ("02-FEB-2060", "Chinese New Year");

		lh.addStaticHoliday ("03-FEB-2060", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("04-MAR-2060", "Hari Raya Puasa");

		lh.addStaticHoliday ("16-APR-2060", "Good Friday");

		lh.addStaticHoliday ("11-MAY-2060", "Hari Raya Haji");

		lh.addStaticHoliday ("14-MAY-2060", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2060", "National Day");

		lh.addStaticHoliday ("21-JAN-2061", "Chinese New Year");

		lh.addStaticHoliday ("22-FEB-2061", "Hari Raya Puasa");

		lh.addStaticHoliday ("08-APR-2061", "Good Friday");

		lh.addStaticHoliday ("02-MAY-2061", "Labour Day Observed");

		lh.addStaticHoliday ("04-MAY-2061", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2061", "National Day");

		lh.addStaticHoliday ("11-NOV-2061", "Deepavali");

		lh.addStaticHoliday ("26-DEC-2061", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2062", "New Years Day Observed");

		lh.addStaticHoliday ("09-FEB-2062", "Chinese New Year");

		lh.addStaticHoliday ("10-FEB-2062", "Second Day of Chinese New Year");

		lh.addStaticHoliday ("24-MAR-2062", "Good Friday");

		lh.addStaticHoliday ("20-APR-2062", "Hari Raya Haji");

		lh.addStaticHoliday ("01-MAY-2062", "Labour Day");

		lh.addStaticHoliday ("23-MAY-2062", "Vesak Day");

		lh.addStaticHoliday ("09-AUG-2062", "National Day");

		lh.addStaticHoliday ("31-OCT-2062", "Deepavali");

		lh.addStaticHoliday ("25-DEC-2062", "Christmas Day");

		lh.addStandardWeekend();

		return lh;
	}
}
