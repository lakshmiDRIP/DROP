
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

public class TRYHoliday implements org.drip.analytics.holset.LocationHoliday {
	public TRYHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "TRY";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("29-JAN-1998", "Ramadan Feast Day");

		lh.addStaticHoliday ("30-JAN-1998", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("07-APR-1998", "Sacrifice Feast Day");

		lh.addStaticHoliday ("08-APR-1998", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("09-APR-1998", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("10-APR-1998", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("23-APR-1998", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("19-MAY-1998", "Youth & Sports Day");

		lh.addStaticHoliday ("29-OCT-1998", "Republic Day");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("19-JAN-1999", "Ramadan Feast Day");

		lh.addStaticHoliday ("20-JAN-1999", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("21-JAN-1999", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("29-MAR-1999", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("30-MAR-1999", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("31-MAR-1999", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("23-APR-1999", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("19-MAY-1999", "Youth & Sports Day");

		lh.addStaticHoliday ("30-AUG-1999", "Victory Day");

		lh.addStaticHoliday ("29-OCT-1999", "Republic Day");

		lh.addStaticHoliday ("07-JAN-2000", "Ramadan Feast Day");

		lh.addStaticHoliday ("10-JAN-2000", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("16-MAR-2000", "Sacrifice Feast Day");

		lh.addStaticHoliday ("17-MAR-2000", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("19-MAY-2000", "Youth & Sports Day");

		lh.addStaticHoliday ("30-AUG-2000", "Victory Day");

		lh.addStaticHoliday ("27-DEC-2000", "Ramadan Feast Day");

		lh.addStaticHoliday ("28-DEC-2000", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("29-DEC-2000", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("05-MAR-2001", "Sacrifice Feast Day");

		lh.addStaticHoliday ("06-MAR-2001", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("07-MAR-2001", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("08-MAR-2001", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("23-APR-2001", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("30-AUG-2001", "Victory Day");

		lh.addStaticHoliday ("29-OCT-2001", "Republic Day");

		lh.addStaticHoliday ("17-DEC-2001", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("22-FEB-2002", "Sacrifice Feast Day");

		lh.addStaticHoliday ("25-FEB-2002", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("23-APR-2002", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("30-AUG-2002", "Victory Day");

		lh.addStaticHoliday ("29-OCT-2002", "Republic Day");

		lh.addStaticHoliday ("05-DEC-2002", "Ramadan Feast Day");

		lh.addStaticHoliday ("06-DEC-2002", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2003", "Sacrifice Feast Day");

		lh.addStaticHoliday ("12-FEB-2003", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("13-FEB-2003", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("14-FEB-2003", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("23-APR-2003", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("19-MAY-2003", "Youth & Sports Day");

		lh.addStaticHoliday ("29-OCT-2003", "Republic Day");

		lh.addStaticHoliday ("24-NOV-2003", "Ramadan Feast Day");

		lh.addStaticHoliday ("25-NOV-2003", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("26-NOV-2003", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("02-FEB-2004", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("03-FEB-2004", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("23-APR-2004", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("19-MAY-2004", "Youth & Sports Day");

		lh.addStaticHoliday ("30-AUG-2004", "Victory Day");

		lh.addStaticHoliday ("29-OCT-2004", "Republic Day");

		lh.addStaticHoliday ("15-NOV-2004", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("20-JAN-2005", "Sacrifice Feast Day");

		lh.addStaticHoliday ("21-JAN-2005", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("19-MAY-2005", "Youth & Sports Day");

		lh.addStaticHoliday ("30-AUG-2005", "Victory Day");

		lh.addStaticHoliday ("02-NOV-2005", "Ramadan Feast Day");

		lh.addStaticHoliday ("03-NOV-2005", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("04-NOV-2005", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("09-JAN-2006", "Sacrifice Feast Day");

		lh.addStaticHoliday ("10-JAN-2006", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("11-JAN-2006", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("12-JAN-2006", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("19-MAY-2006", "Youth & Sports Day");

		lh.addStaticHoliday ("30-AUG-2006", "Victory Day");

		lh.addStaticHoliday ("23-OCT-2006", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("24-OCT-2006", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("02-JAN-2007", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("23-APR-2007", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("30-AUG-2007", "Victory Day");

		lh.addStaticHoliday ("11-OCT-2007", "Ramadan Feast Day");

		lh.addStaticHoliday ("12-OCT-2007", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("29-OCT-2007", "Republic Day");

		lh.addStaticHoliday ("19-DEC-2007", "Sacrifice Feast Day");

		lh.addStaticHoliday ("20-DEC-2007", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("21-DEC-2007", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("23-APR-2008", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("19-MAY-2008", "Youth & Sports Day");

		lh.addStaticHoliday ("30-SEP-2008", "Ramadan Feast Day");

		lh.addStaticHoliday ("01-OCT-2008", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("02-OCT-2008", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("29-OCT-2008", "Republic Day");

		lh.addStaticHoliday ("08-DEC-2008", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("09-DEC-2008", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("10-DEC-2008", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("23-APR-2009", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("19-MAY-2009", "Youth & Sports Day");

		lh.addStaticHoliday ("21-SEP-2009", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("29-OCT-2009", "Republic Day");

		lh.addStaticHoliday ("26-NOV-2009", "Sacrifice Feast Day");

		lh.addStaticHoliday ("27-NOV-2009", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("23-APR-2010", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("19-MAY-2010", "Youth & Sports Day");

		lh.addStaticHoliday ("30-AUG-2010", "Victory Day");

		lh.addStaticHoliday ("09-SEP-2010", "Ramadan Feast Day");

		lh.addStaticHoliday ("10-SEP-2010", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("29-OCT-2010", "Republic Day");

		lh.addStaticHoliday ("16-NOV-2010", "Sacrifice Feast Day");

		lh.addStaticHoliday ("17-NOV-2010", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("18-NOV-2010", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("19-NOV-2010", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("19-MAY-2011", "Youth & Sports Day");

		lh.addStaticHoliday ("29-AUG-2011", "Ramadan Feast Day");

		lh.addStaticHoliday ("30-AUG-2011", "Victory Day");

		lh.addStaticHoliday ("31-AUG-2011", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("07-NOV-2011", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("08-NOV-2011", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("23-APR-2012", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("20-AUG-2012", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("30-AUG-2012", "Victory Day");

		lh.addStaticHoliday ("25-OCT-2012", "Sacrifice Feast Day");

		lh.addStaticHoliday ("26-OCT-2012", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("29-OCT-2012", "Republic Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("23-APR-2013", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("07-AUG-2013", "Ramadan Feast Day");

		lh.addStaticHoliday ("08-AUG-2013", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("09-AUG-2013", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("30-AUG-2013", "Victory Day");

		lh.addStaticHoliday ("14-OCT-2013", "Sacrifice Feast Day");

		lh.addStaticHoliday ("15-OCT-2013", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("16-OCT-2013", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("17-OCT-2013", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("29-OCT-2013", "Republic Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("23-APR-2014", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("19-MAY-2014", "Youth & Sports Day");

		lh.addStaticHoliday ("28-JUL-2014", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("03-OCT-2014", "Sacrifice Feast Day");

		lh.addStaticHoliday ("06-OCT-2014", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("29-OCT-2014", "Republic Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("23-APR-2015", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("19-MAY-2015", "Youth & Sports Day");

		lh.addStaticHoliday ("16-JUL-2015", "Ramadan Feast Day");

		lh.addStaticHoliday ("17-JUL-2015", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("23-SEP-2015", "Sacrifice Feast Day");

		lh.addStaticHoliday ("24-SEP-2015", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("25-SEP-2015", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("29-OCT-2015", "Republic Day");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("19-MAY-2016", "Youth & Sports Day");

		lh.addStaticHoliday ("05-JUL-2016", "Ramadan Feast Day");

		lh.addStaticHoliday ("06-JUL-2016", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("07-JUL-2016", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("30-AUG-2016", "Victory Day");

		lh.addStaticHoliday ("12-SEP-2016", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("13-SEP-2016", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("14-SEP-2016", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("19-MAY-2017", "Youth & Sports Day");

		lh.addStaticHoliday ("26-JUN-2017", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("30-AUG-2017", "Victory Day");

		lh.addStaticHoliday ("31-AUG-2017", "Sacrifice Feast Day");

		lh.addStaticHoliday ("01-SEP-2017", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("23-APR-2018", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("14-JUN-2018", "Ramadan Feast Day");

		lh.addStaticHoliday ("15-JUN-2018", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("21-AUG-2018", "Sacrifice Feast Day");

		lh.addStaticHoliday ("22-AUG-2018", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("23-AUG-2018", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("24-AUG-2018", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("30-AUG-2018", "Victory Day");

		lh.addStaticHoliday ("29-OCT-2018", "Republic Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("23-APR-2019", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("04-JUN-2019", "Ramadan Feast Day");

		lh.addStaticHoliday ("05-JUN-2019", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("06-JUN-2019", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("12-AUG-2019", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("13-AUG-2019", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("30-AUG-2019", "Victory Day");

		lh.addStaticHoliday ("29-OCT-2019", "Republic Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("23-APR-2020", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("19-MAY-2020", "Youth & Sports Day");

		lh.addStaticHoliday ("25-MAY-2020", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("30-JUL-2020", "Sacrifice Feast Day");

		lh.addStaticHoliday ("31-JUL-2020", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("29-OCT-2020", "Republic Day");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("23-APR-2021", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("12-MAY-2021", "Ramadan Feast Day");

		lh.addStaticHoliday ("13-MAY-2021", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("14-MAY-2021", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("19-MAY-2021", "Youth & Sports Day");

		lh.addStaticHoliday ("19-JUL-2021", "Sacrifice Feast Day");

		lh.addStaticHoliday ("20-JUL-2021", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("21-JUL-2021", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("22-JUL-2021", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("30-AUG-2021", "Victory Day");

		lh.addStaticHoliday ("29-OCT-2021", "Republic Day");

		lh.addStaticHoliday ("02-MAY-2022", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("03-MAY-2022", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("19-MAY-2022", "Youth & Sports Day");

		lh.addStaticHoliday ("08-JUL-2022", "Sacrifice Feast Day");

		lh.addStaticHoliday ("11-JUL-2022", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("30-AUG-2022", "Victory Day");

		lh.addStaticHoliday ("20-APR-2023", "Ramadan Feast Day");

		lh.addStaticHoliday ("21-APR-2023", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("19-MAY-2023", "Youth & Sports Day");

		lh.addStaticHoliday ("27-JUN-2023", "Sacrifice Feast Day");

		lh.addStaticHoliday ("28-JUN-2023", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("29-JUN-2023", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("30-JUN-2023", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("30-AUG-2023", "Victory Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("09-APR-2024", "Ramadan Feast Day");

		lh.addStaticHoliday ("10-APR-2024", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("11-APR-2024", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("23-APR-2024", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("17-JUN-2024", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("18-JUN-2024", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("19-JUN-2024", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("30-AUG-2024", "Victory Day");

		lh.addStaticHoliday ("29-OCT-2024", "Republic Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("31-MAR-2025", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("01-APR-2025", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("23-APR-2025", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("19-MAY-2025", "Youth & Sports Day");

		lh.addStaticHoliday ("05-JUN-2025", "Sacrifice Feast Day");

		lh.addStaticHoliday ("06-JUN-2025", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("29-OCT-2025", "Republic Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("19-MAR-2026", "Ramadan Feast Day");

		lh.addStaticHoliday ("20-MAR-2026", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("23-APR-2026", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("19-MAY-2026", "Youth & Sports Day");

		lh.addStaticHoliday ("26-MAY-2026", "Sacrifice Feast Day");

		lh.addStaticHoliday ("27-MAY-2026", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("28-MAY-2026", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("29-MAY-2026", "Fourth Day of Sacrifice Feast");

		lh.addStaticHoliday ("29-OCT-2026", "Republic Day");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("08-MAR-2027", "Ramadan Feast Day");

		lh.addStaticHoliday ("09-MAR-2027", "Second Day of Ramadan Feast");

		lh.addStaticHoliday ("10-MAR-2027", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("23-APR-2027", "National Sovereignty and Childrens Day");

		lh.addStaticHoliday ("17-MAY-2027", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("18-MAY-2027", "Third Day of Sacrifice Feast");

		lh.addStaticHoliday ("19-MAY-2027", "Youth & Sports Day");

		lh.addStaticHoliday ("30-AUG-2027", "Victory Day");

		lh.addStaticHoliday ("29-OCT-2027", "Republic Day");

		lh.addStaticHoliday ("28-FEB-2028", "Third Day of Ramadan Feast");

		lh.addStaticHoliday ("04-MAY-2028", "Sacrifice Feast Day");

		lh.addStaticHoliday ("05-MAY-2028", "Second Day of Sacrifice Feast");

		lh.addStaticHoliday ("19-MAY-2028", "Youth & Sports Day");

		lh.addStaticHoliday ("30-AUG-2028", "Victory Day");

		lh.addStandardWeekend();

		return lh;
	}
}
