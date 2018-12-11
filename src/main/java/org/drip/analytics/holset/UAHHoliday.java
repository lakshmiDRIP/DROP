
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

public class UAHHoliday implements org.drip.analytics.holset.LocationHoliday {
	public UAHHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "UAH";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("07-JAN-1998", "Orthodox Christmas Day");

		lh.addStaticHoliday ("09-MAR-1998", "Womens Day Observed");

		lh.addStaticHoliday ("20-APR-1998", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-1998", "Labor Day");

		lh.addStaticHoliday ("04-MAY-1998", "Day after Labor Day Observed");

		lh.addStaticHoliday ("11-MAY-1998", "Victory Day Observed");

		lh.addStaticHoliday ("08-JUN-1998", "Whit Monday");

		lh.addStaticHoliday ("29-JUN-1998", "Constitution Day Observed");

		lh.addStaticHoliday ("24-AUG-1998", "Independence Day");

		lh.addStaticHoliday ("09-NOV-1998", "Day after Great October Revolution Day Observed");

		lh.addStaticHoliday ("10-NOV-1998", "Great October Revolution Day Observed");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("07-JAN-1999", "Orthodox Christmas Day");

		lh.addStaticHoliday ("08-MAR-1999", "Womens Day");

		lh.addStaticHoliday ("12-APR-1999", "Orthodox Easter Monday");

		lh.addStaticHoliday ("03-MAY-1999", "Day after Labor Day Observed");

		lh.addStaticHoliday ("04-MAY-1999", "Labor Day Observed");

		lh.addStaticHoliday ("10-MAY-1999", "Victory Day Observed");

		lh.addStaticHoliday ("31-MAY-1999", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-1999", "Constitution Day");

		lh.addStaticHoliday ("24-AUG-1999", "Independence Day");

		lh.addStaticHoliday ("08-NOV-1999", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("09-NOV-1999", "Great October Revolution Day Observed");

		lh.addStaticHoliday ("03-JAN-2000", "New Years Day Observed");

		lh.addStaticHoliday ("07-JAN-2000", "Orthodox Christmas Day");

		lh.addStaticHoliday ("08-MAR-2000", "Womens Day");

		lh.addStaticHoliday ("01-MAY-2000", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2000", "Day after Labor Day");

		lh.addStaticHoliday ("09-MAY-2000", "Victory Day");

		lh.addStaticHoliday ("19-JUN-2000", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2000", "Constitution Day");

		lh.addStaticHoliday ("24-AUG-2000", "Independence Day");

		lh.addStaticHoliday ("07-NOV-2000", "Great October Revolution Day");

		lh.addStaticHoliday ("08-NOV-2000", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("08-JAN-2001", "Orthodox Christmas Day Observed");

		lh.addStaticHoliday ("08-MAR-2001", "Womens Day");

		lh.addStaticHoliday ("16-APR-2001", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2001", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2001", "Day after Labor Day");

		lh.addStaticHoliday ("09-MAY-2001", "Victory Day");

		lh.addStaticHoliday ("04-JUN-2001", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2001", "Constitution Day");

		lh.addStaticHoliday ("24-AUG-2001", "Independence Day");

		lh.addStaticHoliday ("07-NOV-2001", "Great October Revolution Day");

		lh.addStaticHoliday ("08-NOV-2001", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2002", "Orthodox Christmas Day");

		lh.addStaticHoliday ("08-MAR-2002", "Womens Day");

		lh.addStaticHoliday ("01-MAY-2002", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2002", "Day after Labor Day");

		lh.addStaticHoliday ("06-MAY-2002", "Orthodox Easter Monday");

		lh.addStaticHoliday ("09-MAY-2002", "Victory Day");

		lh.addStaticHoliday ("24-JUN-2002", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2002", "Constitution Day");

		lh.addStaticHoliday ("26-AUG-2002", "Independence Day Observed");

		lh.addStaticHoliday ("07-NOV-2002", "Great October Revolution Day");

		lh.addStaticHoliday ("08-NOV-2002", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2003", "Orthodox Christmas Day");

		lh.addStaticHoliday ("10-MAR-2003", "Womens Day Observed");

		lh.addStaticHoliday ("28-APR-2003", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2003", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2003", "Day after Labor Day");

		lh.addStaticHoliday ("09-MAY-2003", "Victory Day");

		lh.addStaticHoliday ("16-JUN-2003", "Whit Monday");

		lh.addStaticHoliday ("30-JUN-2003", "Constitution Day Observed");

		lh.addStaticHoliday ("25-AUG-2003", "Independence Day Observed");

		lh.addStaticHoliday ("07-NOV-2003", "Great October Revolution Day");

		lh.addStaticHoliday ("10-NOV-2003", "Day after Great October Revolution Day Observed");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2004", "Orthodox Christmas Day");

		lh.addStaticHoliday ("08-MAR-2004", "Womens Day");

		lh.addStaticHoliday ("12-APR-2004", "Orthodox Easter Monday");

		lh.addStaticHoliday ("03-MAY-2004", "Day after Labor Day Observed");

		lh.addStaticHoliday ("04-MAY-2004", "Labor Day Observed");

		lh.addStaticHoliday ("10-MAY-2004", "Victory Day Observed");

		lh.addStaticHoliday ("31-MAY-2004", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2004", "Constitution Day");

		lh.addStaticHoliday ("24-AUG-2004", "Independence Day");

		lh.addStaticHoliday ("08-NOV-2004", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("09-NOV-2004", "Great October Revolution Day Observed");

		lh.addStaticHoliday ("03-JAN-2005", "New Years Day Observed");

		lh.addStaticHoliday ("07-JAN-2005", "Orthodox Christmas Day");

		lh.addStaticHoliday ("08-MAR-2005", "Womens Day");

		lh.addStaticHoliday ("02-MAY-2005", "Day after Labor Day");

		lh.addStaticHoliday ("03-MAY-2005", "Labor Day Observed");

		lh.addStaticHoliday ("09-MAY-2005", "Victory Day");

		lh.addStaticHoliday ("20-JUN-2005", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2005", "Constitution Day");

		lh.addStaticHoliday ("24-AUG-2005", "Independence Day");

		lh.addStaticHoliday ("07-NOV-2005", "Great October Revolution Day");

		lh.addStaticHoliday ("08-NOV-2005", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("02-JAN-2006", "New Years Day Observed");

		lh.addStaticHoliday ("09-JAN-2006", "Orthodox Christmas Day Observed");

		lh.addStaticHoliday ("08-MAR-2006", "Womens Day");

		lh.addStaticHoliday ("24-APR-2006", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2006", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2006", "Day after Labor Day");

		lh.addStaticHoliday ("09-MAY-2006", "Victory Day");

		lh.addStaticHoliday ("12-JUN-2006", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2006", "Constitution Day");

		lh.addStaticHoliday ("24-AUG-2006", "Independence Day");

		lh.addStaticHoliday ("07-NOV-2006", "Great October Revolution Day");

		lh.addStaticHoliday ("08-NOV-2006", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("08-JAN-2007", "Orthodox Christmas Day Observed");

		lh.addStaticHoliday ("08-MAR-2007", "Womens Day");

		lh.addStaticHoliday ("09-APR-2007", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2007", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2007", "Day after Labor Day");

		lh.addStaticHoliday ("09-MAY-2007", "Victory Day");

		lh.addStaticHoliday ("28-MAY-2007", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2007", "Constitution Day");

		lh.addStaticHoliday ("24-AUG-2007", "Independence Day");

		lh.addStaticHoliday ("07-NOV-2007", "Great October Revolution Day");

		lh.addStaticHoliday ("08-NOV-2007", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2008", "Orthodox Christmas Day");

		lh.addStaticHoliday ("10-MAR-2008", "Womens Day Observed");

		lh.addStaticHoliday ("28-APR-2008", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2008", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2008", "Day after Labor Day");

		lh.addStaticHoliday ("09-MAY-2008", "Victory Day");

		lh.addStaticHoliday ("16-JUN-2008", "Whit Monday");

		lh.addStaticHoliday ("30-JUN-2008", "Constitution Day Observed");

		lh.addStaticHoliday ("25-AUG-2008", "Independence Day Observed");

		lh.addStaticHoliday ("07-NOV-2008", "Great October Revolution Day");

		lh.addStaticHoliday ("10-NOV-2008", "Day after Great October Revolution Day Observed");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2009", "Orthodox Christmas Day");

		lh.addStaticHoliday ("09-MAR-2009", "Womens Day Observed");

		lh.addStaticHoliday ("20-APR-2009", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2009", "Labor Day");

		lh.addStaticHoliday ("04-MAY-2009", "Day after Labor Day Observed");

		lh.addStaticHoliday ("11-MAY-2009", "Victory Day Observed");

		lh.addStaticHoliday ("08-JUN-2009", "Whit Monday");

		lh.addStaticHoliday ("29-JUN-2009", "Constitution Day Observed");

		lh.addStaticHoliday ("24-AUG-2009", "Independence Day");

		lh.addStaticHoliday ("09-NOV-2009", "Day after Great October Revolution Day Observed");

		lh.addStaticHoliday ("10-NOV-2009", "Great October Revolution Day Observed");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2010", "Orthodox Christmas Day");

		lh.addStaticHoliday ("08-MAR-2010", "Womens Day");

		lh.addStaticHoliday ("05-APR-2010", "Orthodox Easter Monday");

		lh.addStaticHoliday ("03-MAY-2010", "Day after Labor Day Observed");

		lh.addStaticHoliday ("04-MAY-2010", "Labor Day Observed");

		lh.addStaticHoliday ("10-MAY-2010", "Victory Day Observed");

		lh.addStaticHoliday ("24-MAY-2010", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2010", "Constitution Day");

		lh.addStaticHoliday ("24-AUG-2010", "Independence Day");

		lh.addStaticHoliday ("08-NOV-2010", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("09-NOV-2010", "Great October Revolution Day Observed");

		lh.addStaticHoliday ("03-JAN-2011", "New Years Day Observed");

		lh.addStaticHoliday ("07-JAN-2011", "Orthodox Christmas Day");

		lh.addStaticHoliday ("08-MAR-2011", "Womens Day");

		lh.addStaticHoliday ("25-APR-2011", "Orthodox Easter Monday");

		lh.addStaticHoliday ("02-MAY-2011", "Day after Labor Day");

		lh.addStaticHoliday ("03-MAY-2011", "Labor Day Observed");

		lh.addStaticHoliday ("09-MAY-2011", "Victory Day");

		lh.addStaticHoliday ("13-JUN-2011", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2011", "Constitution Day");

		lh.addStaticHoliday ("24-AUG-2011", "Independence Day");

		lh.addStaticHoliday ("07-NOV-2011", "Great October Revolution Day");

		lh.addStaticHoliday ("08-NOV-2011", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("02-JAN-2012", "New Years Day Observed");

		lh.addStaticHoliday ("09-JAN-2012", "Orthodox Christmas Day Observed");

		lh.addStaticHoliday ("08-MAR-2012", "Womens Day");

		lh.addStaticHoliday ("16-APR-2012", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2012", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2012", "Day after Labor Day");

		lh.addStaticHoliday ("09-MAY-2012", "Victory Day");

		lh.addStaticHoliday ("04-JUN-2012", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2012", "Constitution Day");

		lh.addStaticHoliday ("24-AUG-2012", "Independence Day");

		lh.addStaticHoliday ("07-NOV-2012", "Great October Revolution Day");

		lh.addStaticHoliday ("08-NOV-2012", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2013", "Orthodox Christmas Day");

		lh.addStaticHoliday ("08-MAR-2013", "Womens Day");

		lh.addStaticHoliday ("01-MAY-2013", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2013", "Day after Labor Day");

		lh.addStaticHoliday ("06-MAY-2013", "Orthodox Easter Monday");

		lh.addStaticHoliday ("09-MAY-2013", "Victory Day");

		lh.addStaticHoliday ("24-JUN-2013", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2013", "Constitution Day");

		lh.addStaticHoliday ("26-AUG-2013", "Independence Day Observed");

		lh.addStaticHoliday ("07-NOV-2013", "Great October Revolution Day");

		lh.addStaticHoliday ("08-NOV-2013", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2014", "Orthodox Christmas Day");

		lh.addStaticHoliday ("10-MAR-2014", "Womens Day Observed");

		lh.addStaticHoliday ("21-APR-2014", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2014", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2014", "Day after Labor Day");

		lh.addStaticHoliday ("09-MAY-2014", "Victory Day");

		lh.addStaticHoliday ("09-JUN-2014", "Whit Monday");

		lh.addStaticHoliday ("30-JUN-2014", "Constitution Day Observed");

		lh.addStaticHoliday ("25-AUG-2014", "Independence Day Observed");

		lh.addStaticHoliday ("07-NOV-2014", "Great October Revolution Day");

		lh.addStaticHoliday ("10-NOV-2014", "Day after Great October Revolution Day Observed");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2015", "Orthodox Christmas Day");

		lh.addStaticHoliday ("09-MAR-2015", "Womens Day Observed");

		lh.addStaticHoliday ("13-APR-2015", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2015", "Labor Day");

		lh.addStaticHoliday ("04-MAY-2015", "Day after Labor Day Observed");

		lh.addStaticHoliday ("11-MAY-2015", "Victory Day Observed");

		lh.addStaticHoliday ("01-JUN-2015", "Whit Monday");

		lh.addStaticHoliday ("29-JUN-2015", "Constitution Day Observed");

		lh.addStaticHoliday ("24-AUG-2015", "Independence Day");

		lh.addStaticHoliday ("09-NOV-2015", "Day after Great October Revolution Day Observed");

		lh.addStaticHoliday ("10-NOV-2015", "Great October Revolution Day Observed");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2016", "Orthodox Christmas Day");

		lh.addStaticHoliday ("08-MAR-2016", "Womens Day");

		lh.addStaticHoliday ("02-MAY-2016", "Day after Labor Day");

		lh.addStaticHoliday ("03-MAY-2016", "Labor Day Observed");

		lh.addStaticHoliday ("09-MAY-2016", "Victory Day");

		lh.addStaticHoliday ("20-JUN-2016", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2016", "Constitution Day");

		lh.addStaticHoliday ("24-AUG-2016", "Independence Day");

		lh.addStaticHoliday ("07-NOV-2016", "Great October Revolution Day");

		lh.addStaticHoliday ("08-NOV-2016", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("02-JAN-2017", "New Years Day Observed");

		lh.addStaticHoliday ("09-JAN-2017", "Orthodox Christmas Day Observed");

		lh.addStaticHoliday ("08-MAR-2017", "Womens Day");

		lh.addStaticHoliday ("17-APR-2017", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2017", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2017", "Day after Labor Day");

		lh.addStaticHoliday ("09-MAY-2017", "Victory Day");

		lh.addStaticHoliday ("05-JUN-2017", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2017", "Constitution Day");

		lh.addStaticHoliday ("24-AUG-2017", "Independence Day");

		lh.addStaticHoliday ("07-NOV-2017", "Great October Revolution Day");

		lh.addStaticHoliday ("08-NOV-2017", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("08-JAN-2018", "Orthodox Christmas Day Observed");

		lh.addStaticHoliday ("08-MAR-2018", "Womens Day");

		lh.addStaticHoliday ("09-APR-2018", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2018", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2018", "Day after Labor Day");

		lh.addStaticHoliday ("09-MAY-2018", "Victory Day");

		lh.addStaticHoliday ("28-MAY-2018", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2018", "Constitution Day");

		lh.addStaticHoliday ("24-AUG-2018", "Independence Day");

		lh.addStaticHoliday ("07-NOV-2018", "Great October Revolution Day");

		lh.addStaticHoliday ("08-NOV-2018", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2019", "Orthodox Christmas Day");

		lh.addStaticHoliday ("08-MAR-2019", "Womens Day");

		lh.addStaticHoliday ("29-APR-2019", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2019", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2019", "Day after Labor Day");

		lh.addStaticHoliday ("09-MAY-2019", "Victory Day");

		lh.addStaticHoliday ("17-JUN-2019", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2019", "Constitution Day");

		lh.addStaticHoliday ("26-AUG-2019", "Independence Day Observed");

		lh.addStaticHoliday ("07-NOV-2019", "Great October Revolution Day");

		lh.addStaticHoliday ("08-NOV-2019", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2020", "Orthodox Christmas Day");

		lh.addStaticHoliday ("09-MAR-2020", "Womens Day Observed");

		lh.addStaticHoliday ("20-APR-2020", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2020", "Labor Day");

		lh.addStaticHoliday ("04-MAY-2020", "Day after Labor Day Observed");

		lh.addStaticHoliday ("11-MAY-2020", "Victory Day Observed");

		lh.addStaticHoliday ("08-JUN-2020", "Whit Monday");

		lh.addStaticHoliday ("29-JUN-2020", "Constitution Day Observed");

		lh.addStaticHoliday ("24-AUG-2020", "Independence Day");

		lh.addStaticHoliday ("09-NOV-2020", "Day after Great October Revolution Day Observed");

		lh.addStaticHoliday ("10-NOV-2020", "Great October Revolution Day Observed");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2021", "Orthodox Christmas Day");

		lh.addStaticHoliday ("08-MAR-2021", "Womens Day");

		lh.addStaticHoliday ("03-MAY-2021", "Day after Labor Day Observed");

		lh.addStaticHoliday ("04-MAY-2021", "Labor Day Observed");

		lh.addStaticHoliday ("10-MAY-2021", "Victory Day Observed");

		lh.addStaticHoliday ("21-JUN-2021", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2021", "Constitution Day");

		lh.addStaticHoliday ("24-AUG-2021", "Independence Day");

		lh.addStaticHoliday ("08-NOV-2021", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("09-NOV-2021", "Great October Revolution Day Observed");

		lh.addStaticHoliday ("03-JAN-2022", "New Years Day Observed");

		lh.addStaticHoliday ("07-JAN-2022", "Orthodox Christmas Day");

		lh.addStaticHoliday ("08-MAR-2022", "Womens Day");

		lh.addStaticHoliday ("25-APR-2022", "Orthodox Easter Monday");

		lh.addStaticHoliday ("02-MAY-2022", "Day after Labor Day");

		lh.addStaticHoliday ("03-MAY-2022", "Labor Day Observed");

		lh.addStaticHoliday ("09-MAY-2022", "Victory Day");

		lh.addStaticHoliday ("13-JUN-2022", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2022", "Constitution Day");

		lh.addStaticHoliday ("24-AUG-2022", "Independence Day");

		lh.addStaticHoliday ("07-NOV-2022", "Great October Revolution Day");

		lh.addStaticHoliday ("08-NOV-2022", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("02-JAN-2023", "New Years Day Observed");

		lh.addStaticHoliday ("09-JAN-2023", "Orthodox Christmas Day Observed");

		lh.addStaticHoliday ("08-MAR-2023", "Womens Day");

		lh.addStaticHoliday ("17-APR-2023", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2023", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2023", "Day after Labor Day");

		lh.addStaticHoliday ("09-MAY-2023", "Victory Day");

		lh.addStaticHoliday ("05-JUN-2023", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2023", "Constitution Day");

		lh.addStaticHoliday ("24-AUG-2023", "Independence Day");

		lh.addStaticHoliday ("07-NOV-2023", "Great October Revolution Day");

		lh.addStaticHoliday ("08-NOV-2023", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("08-JAN-2024", "Orthodox Christmas Day Observed");

		lh.addStaticHoliday ("08-MAR-2024", "Womens Day");

		lh.addStaticHoliday ("01-MAY-2024", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2024", "Day after Labor Day");

		lh.addStaticHoliday ("06-MAY-2024", "Orthodox Easter Monday");

		lh.addStaticHoliday ("09-MAY-2024", "Victory Day");

		lh.addStaticHoliday ("24-JUN-2024", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2024", "Constitution Day");

		lh.addStaticHoliday ("26-AUG-2024", "Independence Day Observed");

		lh.addStaticHoliday ("07-NOV-2024", "Great October Revolution Day");

		lh.addStaticHoliday ("08-NOV-2024", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2025", "Orthodox Christmas Day");

		lh.addStaticHoliday ("10-MAR-2025", "Womens Day Observed");

		lh.addStaticHoliday ("21-APR-2025", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2025", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2025", "Day after Labor Day");

		lh.addStaticHoliday ("09-MAY-2025", "Victory Day");

		lh.addStaticHoliday ("09-JUN-2025", "Whit Monday");

		lh.addStaticHoliday ("30-JUN-2025", "Constitution Day Observed");

		lh.addStaticHoliday ("25-AUG-2025", "Independence Day Observed");

		lh.addStaticHoliday ("07-NOV-2025", "Great October Revolution Day");

		lh.addStaticHoliday ("10-NOV-2025", "Day after Great October Revolution Day Observed");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2026", "Orthodox Christmas Day");

		lh.addStaticHoliday ("09-MAR-2026", "Womens Day Observed");

		lh.addStaticHoliday ("13-APR-2026", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2026", "Labor Day");

		lh.addStaticHoliday ("04-MAY-2026", "Day after Labor Day Observed");

		lh.addStaticHoliday ("11-MAY-2026", "Victory Day Observed");

		lh.addStaticHoliday ("01-JUN-2026", "Whit Monday");

		lh.addStaticHoliday ("29-JUN-2026", "Constitution Day Observed");

		lh.addStaticHoliday ("24-AUG-2026", "Independence Day");

		lh.addStaticHoliday ("09-NOV-2026", "Day after Great October Revolution Day Observed");

		lh.addStaticHoliday ("10-NOV-2026", "Great October Revolution Day Observed");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("07-JAN-2027", "Orthodox Christmas Day");

		lh.addStaticHoliday ("08-MAR-2027", "Womens Day");

		lh.addStaticHoliday ("03-MAY-2027", "Day after Labor Day Observed");

		lh.addStaticHoliday ("04-MAY-2027", "Labor Day Observed");

		lh.addStaticHoliday ("10-MAY-2027", "Victory Day Observed");

		lh.addStaticHoliday ("21-JUN-2027", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2027", "Constitution Day");

		lh.addStaticHoliday ("24-AUG-2027", "Independence Day");

		lh.addStaticHoliday ("08-NOV-2027", "Day after Great October Revolution Day");

		lh.addStaticHoliday ("09-NOV-2027", "Great October Revolution Day Observed");

		lh.addStaticHoliday ("03-JAN-2028", "New Years Day Observed");

		lh.addStaticHoliday ("07-JAN-2028", "Orthodox Christmas Day");

		lh.addStaticHoliday ("08-MAR-2028", "Womens Day");

		lh.addStaticHoliday ("17-APR-2028", "Orthodox Easter Monday");

		lh.addStaticHoliday ("01-MAY-2028", "Labor Day");

		lh.addStaticHoliday ("02-MAY-2028", "Day after Labor Day");

		lh.addStaticHoliday ("09-MAY-2028", "Victory Day");

		lh.addStaticHoliday ("05-JUN-2028", "Whit Monday");

		lh.addStaticHoliday ("28-JUN-2028", "Constitution Day");

		lh.addStaticHoliday ("24-AUG-2028", "Independence Day");

		lh.addStaticHoliday ("07-NOV-2028", "Great October Revolution Day");

		lh.addStaticHoliday ("08-NOV-2028", "Day after Great October Revolution Day");

		lh.addStandardWeekend();

		return lh;
	}
}
