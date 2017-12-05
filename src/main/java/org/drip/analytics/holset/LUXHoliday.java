
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

public class LUXHoliday implements org.drip.analytics.holset.LocationHoliday {
	public LUXHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "LUX";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01               CHASE-JAN-1998", "");

		lh.addStaticHoliday ("13               CHASE-APR-1998", "");

		lh.addStaticHoliday ("01               CHASE-MAY-1998", "");

		lh.addStaticHoliday ("21               CHASE-MAY-1998", "");

		lh.addStaticHoliday ("01               CHASE-JUN-1998", "");

		lh.addStaticHoliday ("23               CHASE-JUN-1998", "");

		lh.addStaticHoliday ("02               CHASE-NOV-1998", "");

		lh.addStaticHoliday ("25               CHASE-DEC-1998", "");

		lh.addStaticHoliday ("01               CHASE-JAN-1999", "");

		lh.addStaticHoliday ("05               CHASE-APR-1999", "");

		lh.addStaticHoliday ("13               CHASE-MAY-1999", "");

		lh.addStaticHoliday ("24               CHASE-MAY-1999", "");

		lh.addStaticHoliday ("23               CHASE-JUN-1999", "");

		lh.addStaticHoliday ("16               CHASE-AUG-1999", "");

		lh.addStaticHoliday ("01               CHASE-NOV-1999", "");

		lh.addStaticHoliday ("27               CHASE-DEC-1999", "");

		lh.addStaticHoliday ("24               CHASE-APR-2000", "");

		lh.addStaticHoliday ("01               CHASE-MAY-2000", "");

		lh.addStaticHoliday ("01               CHASE-JUN-2000", "");

		lh.addStaticHoliday ("12               CHASE-JUN-2000", "");

		lh.addStaticHoliday ("23               CHASE-JUN-2000", "");

		lh.addStaticHoliday ("15               CHASE-AUG-2000", "");

		lh.addStaticHoliday ("01               CHASE-NOV-2000", "");

		lh.addStaticHoliday ("25               CHASE-DEC-2000", "");

		lh.addStaticHoliday ("26               CHASE-DEC-2000", "");

		lh.addStaticHoliday ("01               CHASE-JAN-2001", "");

		lh.addStaticHoliday ("16               CHASE-APR-2001", "");

		lh.addStaticHoliday ("01               CHASE-MAY-2001", "");

		lh.addStaticHoliday ("24               CHASE-MAY-2001", "");

		lh.addStaticHoliday ("04               CHASE-JUN-2001", "");

		lh.addStaticHoliday ("15               CHASE-AUG-2001", "");

		lh.addStaticHoliday ("01               CHASE-NOV-2001", "");

		lh.addStaticHoliday ("25               CHASE-DEC-2001", "");

		lh.addStaticHoliday ("26               CHASE-DEC-2001", "");

		lh.addStaticHoliday ("01               CHASE-JAN-2002", "");

		lh.addStaticHoliday ("01               CHASE-APR-2002", "");

		lh.addStaticHoliday ("01               CHASE-MAY-2002", "");

		lh.addStaticHoliday ("09               CHASE-MAY-2002", "");

		lh.addStaticHoliday ("20               CHASE-MAY-2002", "");

		lh.addStaticHoliday ("24               CHASE-JUN-2002", "");

		lh.addStaticHoliday ("15               CHASE-AUG-2002", "");

		lh.addStaticHoliday ("01               CHASE-NOV-2002", "");

		lh.addStaticHoliday ("25               CHASE-DEC-2002", "");

		lh.addStaticHoliday ("26               CHASE-DEC-2002", "");

		lh.addStaticHoliday ("01               CHASE-JAN-2003", "");

		lh.addStaticHoliday ("21               CHASE-APR-2003", "");

		lh.addStaticHoliday ("01               CHASE-MAY-2003", "");

		lh.addStaticHoliday ("29               CHASE-MAY-2003", "");

		lh.addStaticHoliday ("09               CHASE-JUN-2003", "");

		lh.addStaticHoliday ("23               CHASE-JUN-2003", "");

		lh.addStaticHoliday ("15               CHASE-AUG-2003", "");

		lh.addStaticHoliday ("25               CHASE-DEC-2003", "");

		lh.addStaticHoliday ("26               CHASE-DEC-2003", "");

		lh.addStaticHoliday ("01               CHASE-JAN-2004", "");

		lh.addStaticHoliday ("09-APR-2004", "IRFE");

		lh.addStaticHoliday ("12        BOTH-APR-2004", "");

		lh.addStaticHoliday ("20        BOTH-MAY-2004", "");

		lh.addStaticHoliday ("31        BOTH-MAY-2004", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2004", "");

		lh.addStaticHoliday ("16        BOTH-AUG-2004", "");

		lh.addStaticHoliday ("01        BOTH-NOV-2004", "");

		lh.addStaticHoliday ("27        BOTH-DEC-2004", "");

		lh.addStaticHoliday ("31-DEC-2004", "IRFE");

		lh.addStaticHoliday ("25-MAR-2005", "IRFE");

		lh.addStaticHoliday ("28        BOTH-MAR-2005", "");

		lh.addStaticHoliday ("02        BOTH-MAY-2005", "");

		lh.addStaticHoliday ("05        BOTH-MAY-2005", "");

		lh.addStaticHoliday ("16        BOTH-MAY-2005", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2005", "");

		lh.addStaticHoliday ("15        BOTH-AUG-2005", "");

		lh.addStaticHoliday ("01        BOTH-NOV-2005", "");

		lh.addStaticHoliday ("26        BOTH-DEC-2005", "");

		lh.addStaticHoliday ("27        BOTH-DEC-2005", "");

		lh.addStaticHoliday ("02        BOTH-JAN-2006", "");

		lh.addStaticHoliday ("14-APR-2006", "IRFE");

		lh.addStaticHoliday ("17        BOTH-APR-2006", "");

		lh.addStaticHoliday ("01        BOTH-MAY-2006", "");

		lh.addStaticHoliday ("25        BOTH-MAY-2006", "");

		lh.addStaticHoliday ("05        BOTH-JUN-2006", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2006", "");

		lh.addStaticHoliday ("15        BOTH-AUG-2006", "");

		lh.addStaticHoliday ("01        BOTH-NOV-2006", "");

		lh.addStaticHoliday ("25        BOTH-DEC-2006", "");

		lh.addStaticHoliday ("26        BOTH-DEC-2006", "");

		lh.addStaticHoliday ("01        BOTH-JAN-2007", "");

		lh.addStaticHoliday ("06-APR-2007", "IRFE");

		lh.addStaticHoliday ("09        BOTH-APR-2007", "");

		lh.addStaticHoliday ("01        BOTH-MAY-2007", "");

		lh.addStaticHoliday ("17        BOTH-MAY-2007", "");

		lh.addStaticHoliday ("28        BOTH-MAY-2007", "");

		lh.addStaticHoliday ("15        BOTH-AUG-2007", "");

		lh.addStaticHoliday ("01        BOTH-NOV-2007", "");

		lh.addStaticHoliday ("25        BOTH-DEC-2007", "");

		lh.addStaticHoliday ("26        BOTH-DEC-2007", "");

		lh.addStaticHoliday ("01        BOTH-JAN-2008", "");

		lh.addStaticHoliday ("21-MAR-2008", "IRFE");

		lh.addStaticHoliday ("24        BOTH-MAR-2008", "");

		lh.addStaticHoliday ("01        BOTH-MAY-2008", "");

		lh.addStaticHoliday ("12        BOTH-MAY-2008", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2008", "");

		lh.addStaticHoliday ("15        BOTH-AUG-2008", "");

		lh.addStaticHoliday ("25        BOTH-DEC-2008", "");

		lh.addStaticHoliday ("26        BOTH-DEC-2008", "");

		lh.addStaticHoliday ("01        BOTH-JAN-2009", "");

		lh.addStaticHoliday ("10-APR-2009", "IRFE");

		lh.addStaticHoliday ("13        BOTH-APR-2009", "");

		lh.addStaticHoliday ("01        BOTH-MAY-2009", "");

		lh.addStaticHoliday ("21        BOTH-MAY-2009", "");

		lh.addStaticHoliday ("01        BOTH-JUN-2009", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2009", "");

		lh.addStaticHoliday ("02        BOTH-NOV-2009", "");

		lh.addStaticHoliday ("25        BOTH-DEC-2009", "");

		lh.addStaticHoliday ("01        BOTH-JAN-2010", "");

		lh.addStaticHoliday ("02-APR-2010", "IRFE");

		lh.addStaticHoliday ("05        BOTH-APR-2010", "");

		lh.addStaticHoliday ("13        BOTH-MAY-2010", "");

		lh.addStaticHoliday ("24        BOTH-MAY-2010", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2010", "");

		lh.addStaticHoliday ("16        BOTH-AUG-2010", "");

		lh.addStaticHoliday ("01        BOTH-NOV-2010", "");

		lh.addStaticHoliday ("27        BOTH-DEC-2010", "");

		lh.addStaticHoliday ("22-APR-2011", "IRFE");

		lh.addStaticHoliday ("25        BOTH-APR-2011", "");

		lh.addStaticHoliday ("02        BOTH-MAY-2011", "");

		lh.addStaticHoliday ("02        BOTH-JUN-2011", "");

		lh.addStaticHoliday ("13        BOTH-JUN-2011", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2011", "");

		lh.addStaticHoliday ("15        BOTH-AUG-2011", "");

		lh.addStaticHoliday ("01        BOTH-NOV-2011", "");

		lh.addStaticHoliday ("26        BOTH-DEC-2011", "");

		lh.addStaticHoliday ("27        BOTH-DEC-2011", "");

		lh.addStaticHoliday ("02        BOTH-JAN-2012", "");

		lh.addStaticHoliday ("06-APR-2012", "IRFE");

		lh.addStaticHoliday ("09        BOTH-APR-2012", "");

		lh.addStaticHoliday ("01        BOTH-MAY-2012", "");

		lh.addStaticHoliday ("17        BOTH-MAY-2012", "");

		lh.addStaticHoliday ("28        BOTH-MAY-2012", "");

		lh.addStaticHoliday ("15        BOTH-AUG-2012", "");

		lh.addStaticHoliday ("01        BOTH-NOV-2012", "");

		lh.addStaticHoliday ("25        BOTH-DEC-2012", "");

		lh.addStaticHoliday ("26        BOTH-DEC-2012", "");

		lh.addStaticHoliday ("01        BOTH-JAN-2013", "");

		lh.addStaticHoliday ("29-MAR-2013", "IRFE");

		lh.addStaticHoliday ("01        BOTH-APR-2013", "");

		lh.addStaticHoliday ("01        BOTH-MAY-2013", "");

		lh.addStaticHoliday ("09        BOTH-MAY-2013", "");

		lh.addStaticHoliday ("20        BOTH-MAY-2013", "");

		lh.addStaticHoliday ("24        BOTH-JUN-2013", "");

		lh.addStaticHoliday ("15        BOTH-AUG-2013", "");

		lh.addStaticHoliday ("01        BOTH-NOV-2013", "");

		lh.addStaticHoliday ("25        BOTH-DEC-2013", "");

		lh.addStaticHoliday ("26        BOTH-DEC-2013", "");

		lh.addStaticHoliday ("01        BOTH-JAN-2014", "");

		lh.addStaticHoliday ("18-APR-2014", "IRFE");

		lh.addStaticHoliday ("21        BOTH-APR-2014", "");

		lh.addStaticHoliday ("01        BOTH-MAY-2014", "");

		lh.addStaticHoliday ("29        BOTH-MAY-2014", "");

		lh.addStaticHoliday ("09        BOTH-JUN-2014", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2014", "");

		lh.addStaticHoliday ("15        BOTH-AUG-2014", "");

		lh.addStaticHoliday ("25        BOTH-DEC-2014", "");

		lh.addStaticHoliday ("26        BOTH-DEC-2014", "");

		lh.addStaticHoliday ("01        BOTH-JAN-2015", "");

		lh.addStaticHoliday ("03-APR-2015", "IRFE");

		lh.addStaticHoliday ("06        BOTH-APR-2015", "");

		lh.addStaticHoliday ("01        BOTH-MAY-2015", "");

		lh.addStaticHoliday ("14        BOTH-MAY-2015", "");

		lh.addStaticHoliday ("25        BOTH-MAY-2015", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2015", "");

		lh.addStaticHoliday ("02        BOTH-NOV-2015", "");

		lh.addStaticHoliday ("25        BOTH-DEC-2015", "");

		lh.addStaticHoliday ("01        BOTH-JAN-2016", "");

		lh.addStaticHoliday ("25-MAR-2016", "IRFE");

		lh.addStaticHoliday ("28        BOTH-MAR-2016", "");

		lh.addStaticHoliday ("02        BOTH-MAY-2016", "");

		lh.addStaticHoliday ("05        BOTH-MAY-2016", "");

		lh.addStaticHoliday ("16        BOTH-MAY-2016", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2016", "");

		lh.addStaticHoliday ("15        BOTH-AUG-2016", "");

		lh.addStaticHoliday ("01        BOTH-NOV-2016", "");

		lh.addStaticHoliday ("26        BOTH-DEC-2016", "");

		lh.addStaticHoliday ("27        BOTH-DEC-2016", "");

		lh.addStaticHoliday ("02        BOTH-JAN-2017", "");

		lh.addStaticHoliday ("14-APR-2017", "IRFE");

		lh.addStaticHoliday ("17        BOTH-APR-2017", "");

		lh.addStaticHoliday ("01        BOTH-MAY-2017", "");

		lh.addStaticHoliday ("25        BOTH-MAY-2017", "");

		lh.addStaticHoliday ("05        BOTH-JUN-2017", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2017", "");

		lh.addStaticHoliday ("15        BOTH-AUG-2017", "");

		lh.addStaticHoliday ("01        BOTH-NOV-2017", "");

		lh.addStaticHoliday ("25        BOTH-DEC-2017", "");

		lh.addStaticHoliday ("26        BOTH-DEC-2017", "");

		lh.addStaticHoliday ("01        BOTH-JAN-2018", "");

		lh.addStaticHoliday ("30-MAR-2018", "IRFE");

		lh.addStaticHoliday ("02        BOTH-APR-2018", "");

		lh.addStaticHoliday ("01        BOTH-MAY-2018", "");

		lh.addStaticHoliday ("10        BOTH-MAY-2018", "");

		lh.addStaticHoliday ("21        BOTH-MAY-2018", "");

		lh.addStaticHoliday ("15        BOTH-AUG-2018", "");

		lh.addStaticHoliday ("01        BOTH-NOV-2018", "");

		lh.addStaticHoliday ("25        BOTH-DEC-2018", "");

		lh.addStaticHoliday ("26        BOTH-DEC-2018", "");

		lh.addStaticHoliday ("01        BOTH-JAN-2019", "");

		lh.addStaticHoliday ("19-APR-2019", "IRFE");

		lh.addStaticHoliday ("22        BOTH-APR-2019", "");

		lh.addStaticHoliday ("01        BOTH-MAY-2019", "");

		lh.addStaticHoliday ("30        BOTH-MAY-2019", "");

		lh.addStaticHoliday ("10        BOTH-JUN-2019", "");

		lh.addStaticHoliday ("24        BOTH-JUN-2019", "");

		lh.addStaticHoliday ("15        BOTH-AUG-2019", "");

		lh.addStaticHoliday ("01        BOTH-NOV-2019", "");

		lh.addStaticHoliday ("25        BOTH-DEC-2019", "");

		lh.addStaticHoliday ("26        BOTH-DEC-2019", "");

		lh.addStaticHoliday ("01        BOTH-JAN-2020", "");

		lh.addStaticHoliday ("10-APR-2020", "IRFE");

		lh.addStaticHoliday ("13        BOTH-APR-2020", "");

		lh.addStaticHoliday ("01        BOTH-MAY-2020", "");

		lh.addStaticHoliday ("21        BOTH-MAY-2020", "");

		lh.addStaticHoliday ("01        BOTH-JUN-2020", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2020", "");

		lh.addStaticHoliday ("02        BOTH-NOV-2020", "");

		lh.addStaticHoliday ("25        BOTH-DEC-2020", "");

		lh.addStaticHoliday ("01        BOTH-JAN-2021", "");

		lh.addStaticHoliday ("02-APR-2021", "IRFE");

		lh.addStaticHoliday ("05        BOTH-APR-2021", "");

		lh.addStaticHoliday ("13        BOTH-MAY-2021", "");

		lh.addStaticHoliday ("24        BOTH-MAY-2021", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2021", "");

		lh.addStaticHoliday ("16        BOTH-AUG-2021", "");

		lh.addStaticHoliday ("01        BOTH-NOV-2021", "");

		lh.addStaticHoliday ("27        BOTH-DEC-2021", "");

		lh.addStaticHoliday ("15-APR-2022", "IRFE");

		lh.addStaticHoliday ("18        BOTH-APR-2022", "");

		lh.addStaticHoliday ("02        BOTH-MAY-2022", "");

		lh.addStaticHoliday ("26        BOTH-MAY-2022", "");

		lh.addStaticHoliday ("06        BOTH-JUN-2022", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2022", "");

		lh.addStaticHoliday ("15        BOTH-AUG-2022", "");

		lh.addStaticHoliday ("01        BOTH-NOV-2022", "");

		lh.addStaticHoliday ("26        BOTH-DEC-2022", "");

		lh.addStaticHoliday ("27        BOTH-DEC-2022", "");

		lh.addStaticHoliday ("02        BOTH-JAN-2023", "");

		lh.addStaticHoliday ("07-APR-2023", "IRFE");

		lh.addStaticHoliday ("10        BOTH-APR-2023", "");

		lh.addStaticHoliday ("01        BOTH-MAY-2023", "");

		lh.addStaticHoliday ("18        BOTH-MAY-2023", "");

		lh.addStaticHoliday ("29        BOTH-MAY-2023", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2023", "");

		lh.addStaticHoliday ("15        BOTH-AUG-2023", "");

		lh.addStaticHoliday ("01        BOTH-NOV-2023", "");

		lh.addStaticHoliday ("25        BOTH-DEC-2023", "");

		lh.addStaticHoliday ("26        BOTH-DEC-2023", "");

		lh.addStaticHoliday ("01        BOTH-JAN-2024", "");

		lh.addStaticHoliday ("29-MAR-2024", "IRFE");

		lh.addStaticHoliday ("01        BOTH-APR-2024", "");

		lh.addStaticHoliday ("01        BOTH-MAY-2024", "");

		lh.addStaticHoliday ("09        BOTH-MAY-2024", "");

		lh.addStaticHoliday ("20        BOTH-MAY-2024", "");

		lh.addStaticHoliday ("24        BOTH-JUN-2024", "");

		lh.addStaticHoliday ("15        BOTH-AUG-2024", "");

		lh.addStaticHoliday ("01        BOTH-NOV-2024", "");

		lh.addStaticHoliday ("25        BOTH-DEC-2024", "");

		lh.addStaticHoliday ("26        BOTH-DEC-2024", "");

		lh.addStaticHoliday ("01        BOTH-JAN-2025", "");

		lh.addStaticHoliday ("18-APR-2025", "IRFE");

		lh.addStaticHoliday ("21        BOTH-APR-2025", "");

		lh.addStaticHoliday ("01        BOTH-MAY-2025", "");

		lh.addStaticHoliday ("29        BOTH-MAY-2025", "");

		lh.addStaticHoliday ("09        BOTH-JUN-2025", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2025", "");

		lh.addStaticHoliday ("15        BOTH-AUG-2025", "");

		lh.addStaticHoliday ("25        BOTH-DEC-2025", "");

		lh.addStaticHoliday ("26        BOTH-DEC-2025", "");

		lh.addStaticHoliday ("01        BOTH-JAN-2026", "");

		lh.addStaticHoliday ("03-APR-2026", "IRFE");

		lh.addStaticHoliday ("06        BOTH-APR-2026", "");

		lh.addStaticHoliday ("01        BOTH-MAY-2026", "");

		lh.addStaticHoliday ("14        BOTH-MAY-2026", "");

		lh.addStaticHoliday ("25        BOTH-MAY-2026", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2026", "");

		lh.addStaticHoliday ("02        BOTH-NOV-2026", "");

		lh.addStaticHoliday ("25        BOTH-DEC-2026", "");

		lh.addStaticHoliday ("01        BOTH-JAN-2027", "");

		lh.addStaticHoliday ("26-MAR-2027", "IRFE");

		lh.addStaticHoliday ("29        BOTH-MAR-2027", "");

		lh.addStaticHoliday ("06        BOTH-MAY-2027", "");

		lh.addStaticHoliday ("17        BOTH-MAY-2027", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2027", "");

		lh.addStaticHoliday ("16        BOTH-AUG-2027", "");

		lh.addStaticHoliday ("01        BOTH-NOV-2027", "");

		lh.addStaticHoliday ("27        BOTH-DEC-2027", "");

		lh.addStaticHoliday ("14-APR-2028", "IRFE");

		lh.addStaticHoliday ("17        BOTH-APR-2028", "");

		lh.addStaticHoliday ("01        BOTH-MAY-2028", "");

		lh.addStaticHoliday ("25        BOTH-MAY-2028", "");

		lh.addStaticHoliday ("05        BOTH-JUN-2028", "");

		lh.addStaticHoliday ("23        BOTH-JUN-2028", "");

		lh.addStaticHoliday ("15        BOTH-AUG-2028", "");

		lh.addStaticHoliday ("01        BOTH-NOV-2028", "");

		lh.addStaticHoliday ("25        BOTH-DEC-2028", "");

		lh.addStaticHoliday ("26        BOTH-DEC-2028", "");

		lh.addStaticHoliday ("01-JAN-2029", "IRFE");

		lh.addStaticHoliday ("30-MAR-2029", "IRFE");

		lh.addStaticHoliday ("02-APR-2029", "IRFE");

		lh.addStaticHoliday ("01-MAY-2029", "IRFE");

		lh.addStaticHoliday ("10-MAY-2029", "IRFE");

		lh.addStaticHoliday ("21-MAY-2029", "IRFE");

		lh.addStaticHoliday ("15-AUG-2029", "IRFE");

		lh.addStaticHoliday ("01-NOV-2029", "IRFE");

		lh.addStaticHoliday ("25-DEC-2029", "IRFE");

		lh.addStaticHoliday ("26-DEC-2029", "IRFE");

		lh.addStaticHoliday ("01-JAN-2030", "IRFE");

		lh.addStaticHoliday ("19-APR-2030", "IRFE");

		lh.addStaticHoliday ("22-APR-2030", "IRFE");

		lh.addStaticHoliday ("01-MAY-2030", "IRFE");

		lh.addStaticHoliday ("30-MAY-2030", "IRFE");

		lh.addStaticHoliday ("10-JUN-2030", "IRFE");

		lh.addStaticHoliday ("15-AUG-2030", "IRFE");

		lh.addStaticHoliday ("01-NOV-2030", "IRFE");

		lh.addStaticHoliday ("25-DEC-2030", "IRFE");

		lh.addStaticHoliday ("26-DEC-2030", "IRFE");

		lh.addStandardWeekend();

		return lh;
	}
}
