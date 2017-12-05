
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

public class PTEHoliday implements org.drip.analytics.holset.LocationHoliday {
	public PTEHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "PTE";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("24-FEB-1998", "Carnival Tuesday");

		lh.addStaticHoliday ("10-APR-1998", "Good Friday");

		lh.addStaticHoliday ("01-MAY-1998", "Labour Day");

		lh.addStaticHoliday ("10-JUN-1998", "Portugal Day");

		lh.addStaticHoliday ("11-JUN-1998", "Corpus Christi");

		lh.addStaticHoliday ("05-OCT-1998", "Republic Day");

		lh.addStaticHoliday ("01-DEC-1998", "Independence Day");

		lh.addStaticHoliday ("08-DEC-1998", "Immaculate Conception");

		lh.addStaticHoliday ("24-DEC-1998", "Christmas Eve");

		lh.addStaticHoliday ("25-DEC-1998", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("16-FEB-1999", "Carnival Tuesday");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("03-JUN-1999", "Corpus Christi");

		lh.addStaticHoliday ("10-JUN-1999", "Portugal Day");

		lh.addStaticHoliday ("05-OCT-1999", "Republic Day");

		lh.addStaticHoliday ("01-NOV-1999", "All Saints Day");

		lh.addStaticHoliday ("01-DEC-1999", "Independence Day");

		lh.addStaticHoliday ("08-DEC-1999", "Immaculate Conception");

		lh.addStaticHoliday ("24-DEC-1999", "Christmas Eve");

		lh.addStaticHoliday ("07-MAR-2000", "Carnival Tuesday");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("25-APR-2000", "Liberty Day");

		lh.addStaticHoliday ("01-MAY-2000", "Labour Day");

		lh.addStaticHoliday ("13-JUN-2000", "Lisbon Day");

		lh.addStaticHoliday ("22-JUN-2000", "Corpus Christi");

		lh.addStaticHoliday ("15-AUG-2000", "Assumption Day");

		lh.addStaticHoliday ("05-OCT-2000", "Republic Day");

		lh.addStaticHoliday ("01-NOV-2000", "All Saints Day");

		lh.addStaticHoliday ("01-DEC-2000", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2000", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("27-FEB-2001", "Carnival Tuesday");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("16-APR-2001", "Easter Monday");

		lh.addStaticHoliday ("25-APR-2001", "Liberty Day");

		lh.addStaticHoliday ("01-MAY-2001", "Labour Day");

		lh.addStaticHoliday ("24-MAY-2001", "Public Holiday");

		lh.addStaticHoliday ("13-JUN-2001", "Lisbon Day");

		lh.addStaticHoliday ("14-JUN-2001", "Corpus Christi");

		lh.addStaticHoliday ("15-AUG-2001", "Assumption Day");

		lh.addStaticHoliday ("05-OCT-2001", "Republic Day");

		lh.addStaticHoliday ("01-NOV-2001", "All Saints Day");

		lh.addStaticHoliday ("24-DEC-2001", "Christmas Eve");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("12-FEB-2002", "Carnival Tuesday");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("25-APR-2002", "Liberty Day");

		lh.addStaticHoliday ("01-MAY-2002", "Labour Day");

		lh.addStaticHoliday ("30-MAY-2002", "Corpus Christi");

		lh.addStaticHoliday ("10-JUN-2002", "Portugal Day");

		lh.addStaticHoliday ("13-JUN-2002", "Lisbon Day");

		lh.addStaticHoliday ("15-AUG-2002", "Assumption Day");

		lh.addStaticHoliday ("01-NOV-2002", "All Saints Day");

		lh.addStaticHoliday ("24-DEC-2002", "Christmas Eve");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("04-MAR-2003", "Carnival Tuesday");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("25-APR-2003", "Liberty Day");

		lh.addStaticHoliday ("01-MAY-2003", "Labour Day");

		lh.addStaticHoliday ("10-JUN-2003", "Portugal Day");

		lh.addStaticHoliday ("13-JUN-2003", "Lisbon Day");

		lh.addStaticHoliday ("19-JUN-2003", "Corpus Christi");

		lh.addStaticHoliday ("15-AUG-2003", "Assumption Day");

		lh.addStaticHoliday ("01-DEC-2003", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2003", "Immaculate Conception");

		lh.addStaticHoliday ("24-DEC-2003", "Christmas Eve");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("24-FEB-2004", "Carnival Tuesday");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("10-JUN-2004", "Portugal Day");

		lh.addStaticHoliday ("05-OCT-2004", "Republic Day");

		lh.addStaticHoliday ("01-NOV-2004", "All Saints Day");

		lh.addStaticHoliday ("01-DEC-2004", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2004", "Immaculate Conception");

		lh.addStaticHoliday ("24-DEC-2004", "Christmas Eve");

		lh.addStaticHoliday ("08-FEB-2005", "Carnival Tuesday");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("25-APR-2005", "Liberty Day");

		lh.addStaticHoliday ("26-MAY-2005", "Corpus Christi");

		lh.addStaticHoliday ("10-JUN-2005", "Portugal Day");

		lh.addStaticHoliday ("13-JUN-2005", "Lisbon Day");

		lh.addStaticHoliday ("15-AUG-2005", "Assumption Day");

		lh.addStaticHoliday ("05-OCT-2005", "Republic Day");

		lh.addStaticHoliday ("01-NOV-2005", "All Saints Day");

		lh.addStaticHoliday ("01-DEC-2005", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2005", "Immaculate Conception");

		lh.addStaticHoliday ("28-FEB-2006", "Carnival Tuesday");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("25-APR-2006", "Liberty Day");

		lh.addStaticHoliday ("01-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("13-JUN-2006", "Lisbon Day");

		lh.addStaticHoliday ("15-JUN-2006", "Corpus Christi");

		lh.addStaticHoliday ("15-AUG-2006", "Assumption Day");

		lh.addStaticHoliday ("05-OCT-2006", "Republic Day");

		lh.addStaticHoliday ("01-NOV-2006", "All Saints Day");

		lh.addStaticHoliday ("01-DEC-2006", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2006", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("20-FEB-2007", "Carnival Tuesday");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("25-APR-2007", "Liberty Day");

		lh.addStaticHoliday ("01-MAY-2007", "Labour Day");

		lh.addStaticHoliday ("07-JUN-2007", "Corpus Christi");

		lh.addStaticHoliday ("13-JUN-2007", "Lisbon Day");

		lh.addStaticHoliday ("15-AUG-2007", "Assumption Day");

		lh.addStaticHoliday ("05-OCT-2007", "Republic Day");

		lh.addStaticHoliday ("01-NOV-2007", "All Saints Day");

		lh.addStaticHoliday ("24-DEC-2007", "Christmas Eve");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("05-FEB-2008", "Carnival Tuesday");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("25-APR-2008", "Liberty Day");

		lh.addStaticHoliday ("01-MAY-2008", "Labour Day");

		lh.addStaticHoliday ("22-MAY-2008", "Corpus Christi");

		lh.addStaticHoliday ("10-JUN-2008", "Portugal Day");

		lh.addStaticHoliday ("13-JUN-2008", "Lisbon Day");

		lh.addStaticHoliday ("15-AUG-2008", "Assumption Day");

		lh.addStaticHoliday ("01-DEC-2008", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2008", "Immaculate Conception");

		lh.addStaticHoliday ("24-DEC-2008", "Christmas Eve");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("24-FEB-2009", "Carnival Tuesday");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2009", "Labour Day");

		lh.addStaticHoliday ("10-JUN-2009", "Portugal Day");

		lh.addStaticHoliday ("11-JUN-2009", "Corpus Christi");

		lh.addStaticHoliday ("05-OCT-2009", "Republic Day");

		lh.addStaticHoliday ("01-DEC-2009", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2009", "Immaculate Conception");

		lh.addStaticHoliday ("24-DEC-2009", "Christmas Eve");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("16-FEB-2010", "Carnival Tuesday");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("03-JUN-2010", "Corpus Christi");

		lh.addStaticHoliday ("10-JUN-2010", "Portugal Day");

		lh.addStaticHoliday ("05-OCT-2010", "Republic Day");

		lh.addStaticHoliday ("01-NOV-2010", "All Saints Day");

		lh.addStaticHoliday ("01-DEC-2010", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2010", "Immaculate Conception");

		lh.addStaticHoliday ("24-DEC-2010", "Christmas Eve");

		lh.addStaticHoliday ("08-MAR-2011", "Carnival Tuesday");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("25-APR-2011", "Liberty Day");

		lh.addStaticHoliday ("10-JUN-2011", "Portugal Day");

		lh.addStaticHoliday ("13-JUN-2011", "Lisbon Day");

		lh.addStaticHoliday ("23-JUN-2011", "Corpus Christi");

		lh.addStaticHoliday ("15-AUG-2011", "Assumption Day");

		lh.addStaticHoliday ("05-OCT-2011", "Republic Day");

		lh.addStaticHoliday ("01-NOV-2011", "All Saints Day");

		lh.addStaticHoliday ("01-DEC-2011", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2011", "Immaculate Conception");

		lh.addStaticHoliday ("21-FEB-2012", "Carnival Tuesday");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("25-APR-2012", "Liberty Day");

		lh.addStaticHoliday ("01-MAY-2012", "Labour Day");

		lh.addStaticHoliday ("07-JUN-2012", "Corpus Christi");

		lh.addStaticHoliday ("13-JUN-2012", "Lisbon Day");

		lh.addStaticHoliday ("15-AUG-2012", "Assumption Day");

		lh.addStaticHoliday ("05-OCT-2012", "Republic Day");

		lh.addStaticHoliday ("01-NOV-2012", "All Saints Day");

		lh.addStaticHoliday ("24-DEC-2012", "Christmas Eve");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("12-FEB-2013", "Carnival Tuesday");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("25-APR-2013", "Liberty Day");

		lh.addStaticHoliday ("01-MAY-2013", "Labour Day");

		lh.addStaticHoliday ("30-MAY-2013", "Corpus Christi");

		lh.addStaticHoliday ("10-JUN-2013", "Portugal Day");

		lh.addStaticHoliday ("13-JUN-2013", "Lisbon Day");

		lh.addStaticHoliday ("15-AUG-2013", "Assumption Day");

		lh.addStaticHoliday ("01-NOV-2013", "All Saints Day");

		lh.addStaticHoliday ("24-DEC-2013", "Christmas Eve");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("04-MAR-2014", "Carnival Tuesday");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("25-APR-2014", "Liberty Day");

		lh.addStaticHoliday ("01-MAY-2014", "Labour Day");

		lh.addStaticHoliday ("10-JUN-2014", "Portugal Day");

		lh.addStaticHoliday ("13-JUN-2014", "Lisbon Day");

		lh.addStaticHoliday ("19-JUN-2014", "Corpus Christi");

		lh.addStaticHoliday ("15-AUG-2014", "Assumption Day");

		lh.addStaticHoliday ("01-DEC-2014", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2014", "Immaculate Conception");

		lh.addStaticHoliday ("24-DEC-2014", "Christmas Eve");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("17-FEB-2015", "Carnival Tuesday");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2015", "Labour Day");

		lh.addStaticHoliday ("04-JUN-2015", "Corpus Christi");

		lh.addStaticHoliday ("10-JUN-2015", "Portugal Day");

		lh.addStaticHoliday ("05-OCT-2015", "Republic Day");

		lh.addStaticHoliday ("01-DEC-2015", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2015", "Immaculate Conception");

		lh.addStaticHoliday ("24-DEC-2015", "Christmas Eve");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("09-FEB-2016", "Carnival Tuesday");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("25-APR-2016", "Liberty Day");

		lh.addStaticHoliday ("26-MAY-2016", "Corpus Christi");

		lh.addStaticHoliday ("10-JUN-2016", "Portugal Day");

		lh.addStaticHoliday ("13-JUN-2016", "Lisbon Day");

		lh.addStaticHoliday ("15-AUG-2016", "Assumption Day");

		lh.addStaticHoliday ("05-OCT-2016", "Republic Day");

		lh.addStaticHoliday ("01-NOV-2016", "All Saints Day");

		lh.addStaticHoliday ("01-DEC-2016", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2016", "Immaculate Conception");

		lh.addStaticHoliday ("28-FEB-2017", "Carnival Tuesday");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("25-APR-2017", "Liberty Day");

		lh.addStaticHoliday ("01-MAY-2017", "Labour Day");

		lh.addStaticHoliday ("13-JUN-2017", "Lisbon Day");

		lh.addStaticHoliday ("15-JUN-2017", "Corpus Christi");

		lh.addStaticHoliday ("15-AUG-2017", "Assumption Day");

		lh.addStaticHoliday ("05-OCT-2017", "Republic Day");

		lh.addStaticHoliday ("01-NOV-2017", "All Saints Day");

		lh.addStaticHoliday ("01-DEC-2017", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2017", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("13-FEB-2018", "Carnival Tuesday");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("25-APR-2018", "Liberty Day");

		lh.addStaticHoliday ("01-MAY-2018", "Labour Day");

		lh.addStaticHoliday ("31-MAY-2018", "Corpus Christi");

		lh.addStaticHoliday ("13-JUN-2018", "Lisbon Day");

		lh.addStaticHoliday ("15-AUG-2018", "Assumption Day");

		lh.addStaticHoliday ("05-OCT-2018", "Republic Day");

		lh.addStaticHoliday ("01-NOV-2018", "All Saints Day");

		lh.addStaticHoliday ("24-DEC-2018", "Christmas Eve");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("05-MAR-2019", "Carnival Tuesday");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("25-APR-2019", "Liberty Day");

		lh.addStaticHoliday ("01-MAY-2019", "Labour Day");

		lh.addStaticHoliday ("10-JUN-2019", "Portugal Day");

		lh.addStaticHoliday ("13-JUN-2019", "Lisbon Day");

		lh.addStaticHoliday ("20-JUN-2019", "Corpus Christi");

		lh.addStaticHoliday ("15-AUG-2019", "Assumption Day");

		lh.addStaticHoliday ("01-NOV-2019", "All Saints Day");

		lh.addStaticHoliday ("24-DEC-2019", "Christmas Eve");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("25-FEB-2020", "Carnival Tuesday");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2020", "Labour Day");

		lh.addStaticHoliday ("10-JUN-2020", "Portugal Day");

		lh.addStaticHoliday ("11-JUN-2020", "Corpus Christi");

		lh.addStaticHoliday ("05-OCT-2020", "Republic Day");

		lh.addStaticHoliday ("01-DEC-2020", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2020", "Immaculate Conception");

		lh.addStaticHoliday ("24-DEC-2020", "Christmas Eve");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("16-FEB-2021", "Carnival Tuesday");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("03-JUN-2021", "Corpus Christi");

		lh.addStaticHoliday ("10-JUN-2021", "Portugal Day");

		lh.addStaticHoliday ("05-OCT-2021", "Republic Day");

		lh.addStaticHoliday ("01-NOV-2021", "All Saints Day");

		lh.addStaticHoliday ("01-DEC-2021", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2021", "Immaculate Conception");

		lh.addStaticHoliday ("24-DEC-2021", "Christmas Eve");

		lh.addStaticHoliday ("01-MAR-2022", "Carnival Tuesday");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("25-APR-2022", "Liberty Day");

		lh.addStaticHoliday ("10-JUN-2022", "Portugal Day");

		lh.addStaticHoliday ("13-JUN-2022", "Lisbon Day");

		lh.addStaticHoliday ("16-JUN-2022", "Corpus Christi");

		lh.addStaticHoliday ("15-AUG-2022", "Assumption Day");

		lh.addStaticHoliday ("05-OCT-2022", "Republic Day");

		lh.addStaticHoliday ("01-NOV-2022", "All Saints Day");

		lh.addStaticHoliday ("01-DEC-2022", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2022", "Immaculate Conception");

		lh.addStaticHoliday ("21-FEB-2023", "Carnival Tuesday");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("25-APR-2023", "Liberty Day");

		lh.addStaticHoliday ("01-MAY-2023", "Labour Day");

		lh.addStaticHoliday ("08-JUN-2023", "Corpus Christi");

		lh.addStaticHoliday ("13-JUN-2023", "Lisbon Day");

		lh.addStaticHoliday ("15-AUG-2023", "Assumption Day");

		lh.addStaticHoliday ("05-OCT-2023", "Republic Day");

		lh.addStaticHoliday ("01-NOV-2023", "All Saints Day");

		lh.addStaticHoliday ("01-DEC-2023", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2023", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("13-FEB-2024", "Carnival Tuesday");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("25-APR-2024", "Liberty Day");

		lh.addStaticHoliday ("01-MAY-2024", "Labour Day");

		lh.addStaticHoliday ("30-MAY-2024", "Corpus Christi");

		lh.addStaticHoliday ("10-JUN-2024", "Portugal Day");

		lh.addStaticHoliday ("13-JUN-2024", "Lisbon Day");

		lh.addStaticHoliday ("15-AUG-2024", "Assumption Day");

		lh.addStaticHoliday ("01-NOV-2024", "All Saints Day");

		lh.addStaticHoliday ("24-DEC-2024", "Christmas Eve");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("04-MAR-2025", "Carnival Tuesday");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("25-APR-2025", "Liberty Day");

		lh.addStaticHoliday ("01-MAY-2025", "Labour Day");

		lh.addStaticHoliday ("10-JUN-2025", "Portugal Day");

		lh.addStaticHoliday ("13-JUN-2025", "Lisbon Day");

		lh.addStaticHoliday ("19-JUN-2025", "Corpus Christi");

		lh.addStaticHoliday ("15-AUG-2025", "Assumption Day");

		lh.addStaticHoliday ("01-DEC-2025", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2025", "Immaculate Conception");

		lh.addStaticHoliday ("24-DEC-2025", "Christmas Eve");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("17-FEB-2026", "Carnival Tuesday");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2026", "Labour Day");

		lh.addStaticHoliday ("04-JUN-2026", "Corpus Christi");

		lh.addStaticHoliday ("10-JUN-2026", "Portugal Day");

		lh.addStaticHoliday ("05-OCT-2026", "Republic Day");

		lh.addStaticHoliday ("01-DEC-2026", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2026", "Immaculate Conception");

		lh.addStaticHoliday ("24-DEC-2026", "Christmas Eve");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("09-FEB-2027", "Carnival Tuesday");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("27-MAY-2027", "Corpus Christi");

		lh.addStaticHoliday ("10-JUN-2027", "Portugal Day");

		lh.addStaticHoliday ("05-OCT-2027", "Republic Day");

		lh.addStaticHoliday ("01-NOV-2027", "All Saints Day");

		lh.addStaticHoliday ("01-DEC-2027", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2027", "Immaculate Conception");

		lh.addStaticHoliday ("24-DEC-2027", "Christmas Eve");

		lh.addStaticHoliday ("29-FEB-2028", "Carnival Tuesday");

		lh.addStaticHoliday ("14-APR-2028", "Good Friday");

		lh.addStaticHoliday ("25-APR-2028", "Liberty Day");

		lh.addStaticHoliday ("01-MAY-2028", "Labour Day");

		lh.addStaticHoliday ("13-JUN-2028", "Lisbon Day");

		lh.addStaticHoliday ("15-JUN-2028", "Corpus Christi");

		lh.addStaticHoliday ("15-AUG-2028", "Assumption Day");

		lh.addStaticHoliday ("05-OCT-2028", "Republic Day");

		lh.addStaticHoliday ("01-NOV-2028", "All Saints Day");

		lh.addStaticHoliday ("01-DEC-2028", "Independence Day");

		lh.addStaticHoliday ("08-DEC-2028", "Immaculate Conception");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStandardWeekend();

		return lh;
	}
}
