
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

public class KRWHoliday implements org.drip.analytics.holset.LocationHoliday {
	public KRWHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "KRW";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("15-FEB-1999", "Lunar New Years Eve");

		lh.addStaticHoliday ("16-FEB-1999", "Lunar New Years Day");

		lh.addStaticHoliday ("17-FEB-1999", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-1999", "Independence Movement Day");

		lh.addStaticHoliday ("05-APR-1999", "Arbor Day");

		lh.addStaticHoliday ("01-MAY-1999", "Labour Day");

		lh.addStaticHoliday ("05-MAY-1999", "Childrens Day");

		lh.addStaticHoliday ("22-MAY-1999", "Buddhas Birthday");

		lh.addStaticHoliday ("17-JUL-1999", "Constitution Day");

		lh.addStaticHoliday ("23-SEP-1999", "Chusok");

		lh.addStaticHoliday ("24-SEP-1999", "Chusok");

		lh.addStaticHoliday ("25-SEP-1999", "Chusok");

		lh.addStaticHoliday ("25-DEC-1999", "Christmas Day");

		lh.addStaticHoliday ("31-DEC-1999", "Special Holiday");

		lh.addStaticHoliday ("01-JAN-2000", "New Years Day");

		lh.addStaticHoliday ("03-JAN-2000", "Special Holiday");

		lh.addStaticHoliday ("04-FEB-2000", "Lunar New Years Eve");

		lh.addStaticHoliday ("05-FEB-2000", "Lunar New Years Day");

		lh.addStaticHoliday ("01-MAR-2000", "Independence Movement Day");

		lh.addStaticHoliday ("05-APR-2000", "Arbor Day");

		lh.addStaticHoliday ("13-APR-2000", "Election Day");

		lh.addStaticHoliday ("01-MAY-2000", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2000", "Childrens Day");

		lh.addStaticHoliday ("11-MAY-2000", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2000", "Memorial Day");

		lh.addStaticHoliday ("17-JUL-2000", "Constitution Day");

		lh.addStaticHoliday ("15-AUG-2000", "Liberation Day");

		lh.addStaticHoliday ("11-SEP-2000", "Chusok");

		lh.addStaticHoliday ("12-SEP-2000", "Chusok");

		lh.addStaticHoliday ("13-SEP-2000", "Chusok");

		lh.addStaticHoliday ("03-OCT-2000", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("23-JAN-2001", "Lunar New Years Eve");

		lh.addStaticHoliday ("24-JAN-2001", "Lunar New Years Day");

		lh.addStaticHoliday ("25-JAN-2001", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2001", "Independence Movement Day");

		lh.addStaticHoliday ("05-APR-2001", "Arbor Day");

		lh.addStaticHoliday ("01-MAY-2001", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2001", "Childrens Day");

		lh.addStaticHoliday ("06-JUN-2001", "Memorial Day");

		lh.addStaticHoliday ("17-JUL-2001", "Constitution Day");

		lh.addStaticHoliday ("15-AUG-2001", "Liberation Day");

		lh.addStaticHoliday ("01-OCT-2001", "Chusok");

		lh.addStaticHoliday ("02-OCT-2001", "Chusok");

		lh.addStaticHoliday ("03-OCT-2001", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2002", "Lunar New Years Eve");

		lh.addStaticHoliday ("12-FEB-2002", "Lunar New Years Day");

		lh.addStaticHoliday ("13-FEB-2002", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2002", "Independence Movement Day");

		lh.addStaticHoliday ("05-APR-2002", "Arbor Day");

		lh.addStaticHoliday ("01-MAY-2002", "Labour Day");

		lh.addStaticHoliday ("06-JUN-2002", "Memorial Day");

		lh.addStaticHoliday ("13-JUN-2002", "Special Holiday");

		lh.addStaticHoliday ("01-JUL-2002", "NATIONAL HOLIDAY");

		lh.addStaticHoliday ("17-JUL-2002", "Constitution Day");

		lh.addStaticHoliday ("15-AUG-2002", "Liberation Day");

		lh.addStaticHoliday ("20-SEP-2002", "Chusok");

		lh.addStaticHoliday ("21-SEP-2002", "Chusok");

		lh.addStaticHoliday ("03-OCT-2002", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("31-JAN-2003", "Lunar New Years Eve");

		lh.addStaticHoliday ("01-MAY-2003", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2003", "Childrens Day");

		lh.addStaticHoliday ("08-MAY-2003", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2003", "Memorial Day");

		lh.addStaticHoliday ("17-JUL-2003", "Constitution Day");

		lh.addStaticHoliday ("15-AUG-2003", "Liberation Day");

		lh.addStaticHoliday ("10-SEP-2003", "Chusok");

		lh.addStaticHoliday ("11-SEP-2003", "Chusok");

		lh.addStaticHoliday ("12-SEP-2003", "Chusok");

		lh.addStaticHoliday ("03-OCT-2003", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2004", "Lunar New Years Eve");

		lh.addStaticHoliday ("22-JAN-2004", "Lunar New Years Day");

		lh.addStaticHoliday ("23-JAN-2004", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2004", "Independence Movement Day");

		lh.addStaticHoliday ("05-APR-2004", "Arbor Day");

		lh.addStaticHoliday ("15-APR-2004", "Election Day");

		lh.addStaticHoliday ("05-MAY-2004", "Childrens Day");

		lh.addStaticHoliday ("26-MAY-2004", "Buddhas Birthday");

		lh.addStaticHoliday ("27-SEP-2004", "Chusok");

		lh.addStaticHoliday ("28-SEP-2004", "Chusok");

		lh.addStaticHoliday ("29-SEP-2004", "Chusok");

		lh.addStaticHoliday ("08-FEB-2005", "Lunar New Years Eve");

		lh.addStaticHoliday ("09-FEB-2005", "Lunar New Years Day");

		lh.addStaticHoliday ("10-FEB-2005", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2005", "Independence Movement Day");

		lh.addStaticHoliday ("05-APR-2005", "Arbor Day");

		lh.addStaticHoliday ("05-MAY-2005", "Childrens Day");

		lh.addStaticHoliday ("06-JUN-2005", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2005", "Liberation Day");

		lh.addStaticHoliday ("19-SEP-2005", "Chusok");

		lh.addStaticHoliday ("03-OCT-2005", "National Foundation Day");

		lh.addStaticHoliday ("30-JAN-2006", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2006", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2006", "Childrens Day");

		lh.addStaticHoliday ("31-MAY-2006", "Election Day");

		lh.addStaticHoliday ("06-JUN-2006", "Memorial Day");

		lh.addStaticHoliday ("17-JUL-2006", "Constitution Day");

		lh.addStaticHoliday ("15-AUG-2006", "Liberation Day");

		lh.addStaticHoliday ("03-OCT-2006", "National Foundation Day");

		lh.addStaticHoliday ("05-OCT-2006", "Chusok");

		lh.addStaticHoliday ("06-OCT-2006", "Chusok");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("19-FEB-2007", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2007", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2007", "Labour Day");

		lh.addStaticHoliday ("24-MAY-2007", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2007", "Memorial Day");

		lh.addStaticHoliday ("17-JUL-2007", "Constitution Day");

		lh.addStaticHoliday ("15-AUG-2007", "Liberation Day");

		lh.addStaticHoliday ("24-SEP-2007", "Chusok");

		lh.addStaticHoliday ("25-SEP-2007", "Chusok");

		lh.addStaticHoliday ("26-SEP-2007", "Chusok");

		lh.addStaticHoliday ("03-OCT-2007", "National Foundation Day");

		lh.addStaticHoliday ("19-DEC-2007", "Election Day");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("06-FEB-2008", "Lunar New Years Eve");

		lh.addStaticHoliday ("07-FEB-2008", "Lunar New Years Day");

		lh.addStaticHoliday ("08-FEB-2008", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("09-APR-2008", "Election Day");

		lh.addStaticHoliday ("01-MAY-2008", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2008", "Childrens Day");

		lh.addStaticHoliday ("12-MAY-2008", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2008", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2008", "Liberation Day");

		lh.addStaticHoliday ("15-SEP-2008", "Chusok");

		lh.addStaticHoliday ("03-OCT-2008", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2009", "Lunar New Years Day");

		lh.addStaticHoliday ("27-JAN-2009", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAY-2009", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2009", "Childrens Day");

		lh.addStaticHoliday ("02-OCT-2009", "Chusok");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("13-FEB-2010", "Lunar New Years Day");

		lh.addStaticHoliday ("15-FEB-2010", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2010", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2010", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2010", "Childrens Day");

		lh.addStaticHoliday ("21-MAY-2010", "Buddhas Birthday");

		lh.addStaticHoliday ("02-JUN-2010", "Election Day");

		lh.addStaticHoliday ("21-SEP-2010", "Chusok");

		lh.addStaticHoliday ("22-SEP-2010", "Chusok");

		lh.addStaticHoliday ("23-SEP-2010", "Chusok");

		lh.addStaticHoliday ("25-DEC-2010", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2011", "New Years Day");

		lh.addStaticHoliday ("02-FEB-2011", "Lunar New Years Eve");

		lh.addStaticHoliday ("03-FEB-2011", "Lunar New Years Day");

		lh.addStaticHoliday ("04-FEB-2011", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2011", "Independence Movement Day");

		lh.addStaticHoliday ("05-MAY-2011", "Childrens Day");

		lh.addStaticHoliday ("10-MAY-2011", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2011", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2011", "Liberation Day");

		lh.addStaticHoliday ("12-SEP-2011", "Chusok");

		lh.addStaticHoliday ("13-SEP-2011", "Chusok");

		lh.addStaticHoliday ("03-OCT-2011", "National Foundation Day");

		lh.addStaticHoliday ("23-JAN-2012", "Lunar New Years Day");

		lh.addStaticHoliday ("24-JAN-2012", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2012", "Independence Movement Day");

		lh.addStaticHoliday ("11-APR-2012", "Election Day");

		lh.addStaticHoliday ("01-MAY-2012", "Labour Day");

		lh.addStaticHoliday ("28-MAY-2012", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2012", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2012", "Liberation Day");

		lh.addStaticHoliday ("01-OCT-2012", "Chusok");

		lh.addStaticHoliday ("03-OCT-2012", "National Foundation Day");

		lh.addStaticHoliday ("19-DEC-2012", "Election Day");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2013", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2013", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2013", "Labour Day");

		lh.addStaticHoliday ("17-MAY-2013", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2013", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2013", "Liberation Day");

		lh.addStaticHoliday ("18-SEP-2013", "Chusok");

		lh.addStaticHoliday ("19-SEP-2013", "Chusok");

		lh.addStaticHoliday ("20-SEP-2013", "Chusok");

		lh.addStaticHoliday ("03-OCT-2013", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("30-JAN-2014", "Lunar New Years Eve");

		lh.addStaticHoliday ("31-JAN-2014", "Lunar New Years Day");

		lh.addStaticHoliday ("01-MAY-2014", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2014", "Childrens Day");

		lh.addStaticHoliday ("06-MAY-2014", "Buddhas Birthday");

		lh.addStaticHoliday ("04-JUN-2014", "Election Day");

		lh.addStaticHoliday ("06-JUN-2014", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2014", "Liberation Day");

		lh.addStaticHoliday ("08-SEP-2014", "Chusok");

		lh.addStaticHoliday ("09-SEP-2014", "Chusok");

		lh.addStaticHoliday ("03-OCT-2014", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("18-FEB-2015", "Lunar New Years Eve");

		lh.addStaticHoliday ("19-FEB-2015", "Lunar New Years Day");

		lh.addStaticHoliday ("20-FEB-2015", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAY-2015", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2015", "Childrens Day");

		lh.addStaticHoliday ("25-MAY-2015", "Buddhas Birthday");

		lh.addStaticHoliday ("28-SEP-2015", "Chusok");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2016", "Lunar New Years Day");

		lh.addStaticHoliday ("09-FEB-2016", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2016", "Independence Movement Day");

		lh.addStaticHoliday ("13-APR-2016", "Election Day");

		lh.addStaticHoliday ("05-MAY-2016", "Childrens Day");

		lh.addStaticHoliday ("06-JUN-2016", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2016", "Liberation Day");

		lh.addStaticHoliday ("14-SEP-2016", "Chusok");

		lh.addStaticHoliday ("15-SEP-2016", "Chusok");

		lh.addStaticHoliday ("16-SEP-2016", "Chusok");

		lh.addStaticHoliday ("03-OCT-2016", "National Foundation Day");

		lh.addStaticHoliday ("27-JAN-2017", "Lunar New Years Eve");

		lh.addStaticHoliday ("01-MAR-2017", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2017", "Labour Day");

		lh.addStaticHoliday ("03-MAY-2017", "Buddhas Birthday");

		lh.addStaticHoliday ("05-MAY-2017", "Childrens Day");

		lh.addStaticHoliday ("06-JUN-2017", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2017", "Liberation Day");

		lh.addStaticHoliday ("03-OCT-2017", "Chusok");

		lh.addStaticHoliday ("04-OCT-2017", "Chusok");

		lh.addStaticHoliday ("05-OCT-2017", "Chusok");

		lh.addStaticHoliday ("20-DEC-2017", "Election Day");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("15-FEB-2018", "Lunar New Years Eve");

		lh.addStaticHoliday ("16-FEB-2018", "Lunar New Years Day");

		lh.addStaticHoliday ("01-MAR-2018", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2018", "Labour Day");

		lh.addStaticHoliday ("22-MAY-2018", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2018", "Memorial Day");

		lh.addStaticHoliday ("13-JUN-2018", "Election Day");

		lh.addStaticHoliday ("15-AUG-2018", "Liberation Day");

		lh.addStaticHoliday ("24-SEP-2018", "Chusok");

		lh.addStaticHoliday ("25-SEP-2018", "Chusok");

		lh.addStaticHoliday ("03-OCT-2018", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("04-FEB-2019", "Lunar New Years Eve");

		lh.addStaticHoliday ("05-FEB-2019", "Lunar New Years Day");

		lh.addStaticHoliday ("06-FEB-2019", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2019", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2019", "Labour Day");

		lh.addStaticHoliday ("06-JUN-2019", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2019", "Liberation Day");

		lh.addStaticHoliday ("12-SEP-2019", "Chusok");

		lh.addStaticHoliday ("13-SEP-2019", "Chusok");

		lh.addStaticHoliday ("03-OCT-2019", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("24-JAN-2020", "Lunar New Years Eve");

		lh.addStaticHoliday ("15-APR-2020", "Election Day");

		lh.addStaticHoliday ("30-APR-2020", "Buddhas Birthday");

		lh.addStaticHoliday ("01-MAY-2020", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2020", "Childrens Day");

		lh.addStaticHoliday ("30-SEP-2020", "Chusok");

		lh.addStaticHoliday ("01-OCT-2020", "Chusok");

		lh.addStaticHoliday ("02-OCT-2020", "Chusok");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2021", "Lunar New Years Eve");

		lh.addStaticHoliday ("12-FEB-2021", "Lunar New Years Day");

		lh.addStaticHoliday ("01-MAR-2021", "Independence Movement Day");

		lh.addStaticHoliday ("05-MAY-2021", "Childrens Day");

		lh.addStaticHoliday ("19-MAY-2021", "Buddhas Birthday");

		lh.addStaticHoliday ("20-SEP-2021", "Chusok");

		lh.addStaticHoliday ("21-SEP-2021", "Chusok");

		lh.addStaticHoliday ("22-SEP-2021", "Chusok");

		lh.addStaticHoliday ("31-JAN-2022", "Lunar New Years Eve");

		lh.addStaticHoliday ("01-FEB-2022", "Lunar New Years Day");

		lh.addStaticHoliday ("02-FEB-2022", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2022", "Independence Movement Day");

		lh.addStaticHoliday ("05-MAY-2022", "Childrens Day");

		lh.addStaticHoliday ("01-JUN-2022", "Election Day");

		lh.addStaticHoliday ("06-JUN-2022", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2022", "Liberation Day");

		lh.addStaticHoliday ("09-SEP-2022", "Chusok");

		lh.addStaticHoliday ("03-OCT-2022", "National Foundation Day");

		lh.addStaticHoliday ("21-DEC-2022", "Election Day");

		lh.addStaticHoliday ("23-JAN-2023", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2023", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2023", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2023", "Childrens Day");

		lh.addStaticHoliday ("06-JUN-2023", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2023", "Liberation Day");

		lh.addStaticHoliday ("28-SEP-2023", "Chusok");

		lh.addStaticHoliday ("29-SEP-2023", "Chusok");

		lh.addStaticHoliday ("03-OCT-2023", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("09-FEB-2024", "Lunar New Years Eve");

		lh.addStaticHoliday ("01-MAR-2024", "Independence Movement Day");

		lh.addStaticHoliday ("10-APR-2024", "Election Day");

		lh.addStaticHoliday ("01-MAY-2024", "Labour Day");

		lh.addStaticHoliday ("15-MAY-2024", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2024", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2024", "Liberation Day");

		lh.addStaticHoliday ("16-SEP-2024", "Chusok");

		lh.addStaticHoliday ("17-SEP-2024", "Chusok");

		lh.addStaticHoliday ("18-SEP-2024", "Chusok");

		lh.addStaticHoliday ("03-OCT-2024", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2025", "Lunar New Years Eve");

		lh.addStaticHoliday ("29-JAN-2025", "Lunar New Years Day");

		lh.addStaticHoliday ("30-JAN-2025", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAY-2025", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2025", "Childrens Day");

		lh.addStaticHoliday ("06-JUN-2025", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2025", "Liberation Day");

		lh.addStaticHoliday ("03-OCT-2025", "National Foundation Day");

		lh.addStaticHoliday ("06-OCT-2025", "Chusok");

		lh.addStaticHoliday ("07-OCT-2025", "Chusok");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("16-FEB-2026", "Lunar New Years Eve");

		lh.addStaticHoliday ("17-FEB-2026", "Lunar New Years Day");

		lh.addStaticHoliday ("18-FEB-2026", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAY-2026", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2026", "Childrens Day");

		lh.addStaticHoliday ("03-JUN-2026", "Election Day");

		lh.addStaticHoliday ("24-SEP-2026", "Chusok");

		lh.addStaticHoliday ("25-SEP-2026", "Chusok");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2027", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2027", "Independence Movement Day");

		lh.addStaticHoliday ("05-MAY-2027", "Childrens Day");

		lh.addStaticHoliday ("13-MAY-2027", "Buddhas Birthday");

		lh.addStaticHoliday ("14-SEP-2027", "Chusok");

		lh.addStaticHoliday ("15-SEP-2027", "Chusok");

		lh.addStaticHoliday ("16-SEP-2027", "Chusok");

		lh.addStaticHoliday ("22-DEC-2027", "Election Day");

		lh.addStaticHoliday ("26-JAN-2028", "Lunar New Years Eve");

		lh.addStaticHoliday ("27-JAN-2028", "Lunar New Years Day");

		lh.addStaticHoliday ("28-JAN-2028", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2028", "Independence Movement Day");

		lh.addStaticHoliday ("12-APR-2028", "Election Day");

		lh.addStaticHoliday ("01-MAY-2028", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2028", "Buddhas Birthday");

		lh.addStaticHoliday ("05-MAY-2028", "Childrens Day");

		lh.addStaticHoliday ("06-JUN-2028", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2028", "Liberation Day");

		lh.addStaticHoliday ("02-OCT-2028", "Chusok");

		lh.addStaticHoliday ("03-OCT-2028", "Chusok");

		lh.addStaticHoliday ("04-OCT-2028", "Chusok");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2029", "New Years Day");

		lh.addStaticHoliday ("12-FEB-2029", "Lunar New Years Eve");

		lh.addStaticHoliday ("13-FEB-2029", "Lunar New Years Day");

		lh.addStaticHoliday ("14-FEB-2029", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2029", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2029", "Labour Day");

		lh.addStaticHoliday ("06-JUN-2029", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2029", "Liberation Day");

		lh.addStaticHoliday ("21-SEP-2029", "Chusok");

		lh.addStaticHoliday ("03-OCT-2029", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2029", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2030", "New Years Day");

		lh.addStaticHoliday ("04-FEB-2030", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2030", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2030", "Labour Day");

		lh.addStaticHoliday ("09-MAY-2030", "Buddhas Birthday");

		lh.addStaticHoliday ("05-JUN-2030", "Election Day");

		lh.addStaticHoliday ("06-JUN-2030", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2030", "Liberation Day");

		lh.addStaticHoliday ("11-SEP-2030", "Chusok");

		lh.addStaticHoliday ("12-SEP-2030", "Chusok");

		lh.addStaticHoliday ("13-SEP-2030", "Chusok");

		lh.addStaticHoliday ("03-OCT-2030", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2030", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2031", "New Years Day");

		lh.addStaticHoliday ("22-JAN-2031", "Lunar New Years Eve");

		lh.addStaticHoliday ("23-JAN-2031", "Lunar New Years Day");

		lh.addStaticHoliday ("24-JAN-2031", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAY-2031", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2031", "Childrens Day");

		lh.addStaticHoliday ("28-MAY-2031", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2031", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2031", "Liberation Day");

		lh.addStaticHoliday ("30-SEP-2031", "Chusok");

		lh.addStaticHoliday ("01-OCT-2031", "Chusok");

		lh.addStaticHoliday ("02-OCT-2031", "Chusok");

		lh.addStaticHoliday ("03-OCT-2031", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2031", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2032", "New Years Day");

		lh.addStaticHoliday ("10-FEB-2032", "Lunar New Years Eve");

		lh.addStaticHoliday ("11-FEB-2032", "Lunar New Years Day");

		lh.addStaticHoliday ("12-FEB-2032", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2032", "Independence Movement Day");

		lh.addStaticHoliday ("14-APR-2032", "Election Day");

		lh.addStaticHoliday ("05-MAY-2032", "Childrens Day");

		lh.addStaticHoliday ("20-SEP-2032", "Chusok");

		lh.addStaticHoliday ("22-DEC-2032", "Election Day");

		lh.addStaticHoliday ("31-JAN-2033", "Lunar New Years Day");

		lh.addStaticHoliday ("01-FEB-2033", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2033", "Independence Movement Day");

		lh.addStaticHoliday ("05-MAY-2033", "Childrens Day");

		lh.addStaticHoliday ("06-MAY-2033", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2033", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2033", "Liberation Day");

		lh.addStaticHoliday ("07-SEP-2033", "Chusok");

		lh.addStaticHoliday ("08-SEP-2033", "Chusok");

		lh.addStaticHoliday ("09-SEP-2033", "Chusok");

		lh.addStaticHoliday ("03-OCT-2033", "National Foundation Day");

		lh.addStaticHoliday ("20-FEB-2034", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2034", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2034", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2034", "Childrens Day");

		lh.addStaticHoliday ("25-MAY-2034", "Buddhas Birthday");

		lh.addStaticHoliday ("31-MAY-2034", "Election Day");

		lh.addStaticHoliday ("06-JUN-2034", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2034", "Liberation Day");

		lh.addStaticHoliday ("26-SEP-2034", "Chusok");

		lh.addStaticHoliday ("27-SEP-2034", "Chusok");

		lh.addStaticHoliday ("28-SEP-2034", "Chusok");

		lh.addStaticHoliday ("03-OCT-2034", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2034", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2035", "New Years Day");

		lh.addStaticHoliday ("07-FEB-2035", "Lunar New Years Eve");

		lh.addStaticHoliday ("08-FEB-2035", "Lunar New Years Day");

		lh.addStaticHoliday ("09-FEB-2035", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2035", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2035", "Labour Day");

		lh.addStaticHoliday ("15-MAY-2035", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2035", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2035", "Liberation Day");

		lh.addStaticHoliday ("17-SEP-2035", "Chusok");

		lh.addStaticHoliday ("03-OCT-2035", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2035", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2036", "New Years Day");

		lh.addStaticHoliday ("28-JAN-2036", "Lunar New Years Day");

		lh.addStaticHoliday ("29-JAN-2036", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("09-APR-2036", "Election Day");

		lh.addStaticHoliday ("01-MAY-2036", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2036", "Childrens Day");

		lh.addStaticHoliday ("06-JUN-2036", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2036", "Liberation Day");

		lh.addStaticHoliday ("03-OCT-2036", "Chusok");

		lh.addStaticHoliday ("25-DEC-2036", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2037", "New Years Day");

		lh.addStaticHoliday ("16-FEB-2037", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAY-2037", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2037", "Childrens Day");

		lh.addStaticHoliday ("22-MAY-2037", "Buddhas Birthday");

		lh.addStaticHoliday ("23-SEP-2037", "Chusok");

		lh.addStaticHoliday ("24-SEP-2037", "Chusok");

		lh.addStaticHoliday ("25-SEP-2037", "Chusok");

		lh.addStaticHoliday ("23-DEC-2037", "Election Day");

		lh.addStaticHoliday ("25-DEC-2037", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2038", "New Years Day");

		lh.addStaticHoliday ("03-FEB-2038", "Lunar New Years Eve");

		lh.addStaticHoliday ("04-FEB-2038", "Lunar New Years Day");

		lh.addStaticHoliday ("05-FEB-2038", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2038", "Independence Movement Day");

		lh.addStaticHoliday ("05-MAY-2038", "Childrens Day");

		lh.addStaticHoliday ("11-MAY-2038", "Buddhas Birthday");

		lh.addStaticHoliday ("02-JUN-2038", "Election Day");

		lh.addStaticHoliday ("13-SEP-2038", "Chusok");

		lh.addStaticHoliday ("14-SEP-2038", "Chusok");

		lh.addStaticHoliday ("24-JAN-2039", "Lunar New Years Day");

		lh.addStaticHoliday ("25-JAN-2039", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2039", "Independence Movement Day");

		lh.addStaticHoliday ("05-MAY-2039", "Childrens Day");

		lh.addStaticHoliday ("06-JUN-2039", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2039", "Liberation Day");

		lh.addStaticHoliday ("03-OCT-2039", "Chusok");

		lh.addStaticHoliday ("13-FEB-2040", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2040", "Independence Movement Day");

		lh.addStaticHoliday ("11-APR-2040", "Election Day");

		lh.addStaticHoliday ("01-MAY-2040", "Labour Day");

		lh.addStaticHoliday ("18-MAY-2040", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2040", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2040", "Liberation Day");

		lh.addStaticHoliday ("20-SEP-2040", "Chusok");

		lh.addStaticHoliday ("21-SEP-2040", "Chusok");

		lh.addStaticHoliday ("03-OCT-2040", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2040", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2041", "New Years Day");

		lh.addStaticHoliday ("31-JAN-2041", "Lunar New Years Eve");

		lh.addStaticHoliday ("01-FEB-2041", "Lunar New Years Day");

		lh.addStaticHoliday ("01-MAR-2041", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2041", "Labour Day");

		lh.addStaticHoliday ("07-MAY-2041", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2041", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2041", "Liberation Day");

		lh.addStaticHoliday ("09-SEP-2041", "Chusok");

		lh.addStaticHoliday ("10-SEP-2041", "Chusok");

		lh.addStaticHoliday ("11-SEP-2041", "Chusok");

		lh.addStaticHoliday ("03-OCT-2041", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2041", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2042", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2042", "Lunar New Years Eve");

		lh.addStaticHoliday ("22-JAN-2042", "Lunar New Years Day");

		lh.addStaticHoliday ("23-JAN-2042", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAY-2042", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2042", "Childrens Day");

		lh.addStaticHoliday ("26-MAY-2042", "Buddhas Birthday");

		lh.addStaticHoliday ("04-JUN-2042", "Election Day");

		lh.addStaticHoliday ("06-JUN-2042", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2042", "Liberation Day");

		lh.addStaticHoliday ("29-SEP-2042", "Chusok");

		lh.addStaticHoliday ("03-OCT-2042", "National Foundation Day");

		lh.addStaticHoliday ("17-DEC-2042", "Election Day");

		lh.addStaticHoliday ("25-DEC-2042", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2043", "New Years Day");

		lh.addStaticHoliday ("09-FEB-2043", "Lunar New Years Eve");

		lh.addStaticHoliday ("10-FEB-2043", "Lunar New Years Day");

		lh.addStaticHoliday ("11-FEB-2043", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAY-2043", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2043", "Childrens Day");

		lh.addStaticHoliday ("16-SEP-2043", "Chusok");

		lh.addStaticHoliday ("17-SEP-2043", "Chusok");

		lh.addStaticHoliday ("18-SEP-2043", "Chusok");

		lh.addStaticHoliday ("25-DEC-2043", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2044", "New Years Day");

		lh.addStaticHoliday ("29-JAN-2044", "Lunar New Years Eve");

		lh.addStaticHoliday ("01-MAR-2044", "Independence Movement Day");

		lh.addStaticHoliday ("13-APR-2044", "Election Day");

		lh.addStaticHoliday ("05-MAY-2044", "Childrens Day");

		lh.addStaticHoliday ("06-JUN-2044", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2044", "Liberation Day");

		lh.addStaticHoliday ("03-OCT-2044", "National Foundation Day");

		lh.addStaticHoliday ("04-OCT-2044", "Chusok");

		lh.addStaticHoliday ("05-OCT-2044", "Chusok");

		lh.addStaticHoliday ("06-OCT-2044", "Chusok");

		lh.addStaticHoliday ("16-FEB-2045", "Lunar New Years Eve");

		lh.addStaticHoliday ("17-FEB-2045", "Lunar New Years Day");

		lh.addStaticHoliday ("01-MAR-2045", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2045", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2045", "Childrens Day");

		lh.addStaticHoliday ("24-MAY-2045", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2045", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2045", "Liberation Day");

		lh.addStaticHoliday ("25-SEP-2045", "Chusok");

		lh.addStaticHoliday ("26-SEP-2045", "Chusok");

		lh.addStaticHoliday ("03-OCT-2045", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2045", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2046", "New Years Day");

		lh.addStaticHoliday ("05-FEB-2046", "Lunar New Years Eve");

		lh.addStaticHoliday ("06-FEB-2046", "Lunar New Years Day");

		lh.addStaticHoliday ("07-FEB-2046", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2046", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2046", "Labour Day");

		lh.addStaticHoliday ("06-JUN-2046", "Memorial Day");

		lh.addStaticHoliday ("13-JUN-2046", "Election Day");

		lh.addStaticHoliday ("15-AUG-2046", "Liberation Day");

		lh.addStaticHoliday ("14-SEP-2046", "Chusok");

		lh.addStaticHoliday ("03-OCT-2046", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2046", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2047", "New Years Day");

		lh.addStaticHoliday ("25-JAN-2047", "Lunar New Years Eve");

		lh.addStaticHoliday ("01-MAR-2047", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2047", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2047", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2047", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2047", "Liberation Day");

		lh.addStaticHoliday ("03-OCT-2047", "Chusok");

		lh.addStaticHoliday ("04-OCT-2047", "Chusok");

		lh.addStaticHoliday ("18-DEC-2047", "Election Day");

		lh.addStaticHoliday ("25-DEC-2047", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2048", "New Years Day");

		lh.addStaticHoliday ("13-FEB-2048", "Lunar New Years Eve");

		lh.addStaticHoliday ("14-FEB-2048", "Lunar New Years Day");

		lh.addStaticHoliday ("15-APR-2048", "Election Day");

		lh.addStaticHoliday ("01-MAY-2048", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2048", "Childrens Day");

		lh.addStaticHoliday ("20-MAY-2048", "Buddhas Birthday");

		lh.addStaticHoliday ("21-SEP-2048", "Chusok");

		lh.addStaticHoliday ("22-SEP-2048", "Chusok");

		lh.addStaticHoliday ("23-SEP-2048", "Chusok");

		lh.addStaticHoliday ("25-DEC-2048", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2049", "New Years Day");

		lh.addStaticHoliday ("01-FEB-2049", "Lunar New Years Eve");

		lh.addStaticHoliday ("02-FEB-2049", "Lunar New Years Day");

		lh.addStaticHoliday ("03-FEB-2049", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2049", "Independence Movement Day");

		lh.addStaticHoliday ("05-MAY-2049", "Childrens Day");

		lh.addStaticHoliday ("10-SEP-2049", "Chusok");

		lh.addStaticHoliday ("24-JAN-2050", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2050", "Independence Movement Day");

		lh.addStaticHoliday ("05-MAY-2050", "Childrens Day");

		lh.addStaticHoliday ("01-JUN-2050", "Election Day");

		lh.addStaticHoliday ("06-JUN-2050", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2050", "Liberation Day");

		lh.addStaticHoliday ("29-SEP-2050", "Chusok");

		lh.addStaticHoliday ("30-SEP-2050", "Chusok");

		lh.addStaticHoliday ("03-OCT-2050", "National Foundation Day");

		lh.addStaticHoliday ("10-FEB-2051", "Lunar New Years Eve");

		lh.addStaticHoliday ("01-MAR-2051", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2051", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2051", "Childrens Day");

		lh.addStaticHoliday ("17-MAY-2051", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2051", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2051", "Liberation Day");

		lh.addStaticHoliday ("18-SEP-2051", "Chusok");

		lh.addStaticHoliday ("19-SEP-2051", "Chusok");

		lh.addStaticHoliday ("20-SEP-2051", "Chusok");

		lh.addStaticHoliday ("03-OCT-2051", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2051", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2052", "New Years Day");

		lh.addStaticHoliday ("31-JAN-2052", "Lunar New Years Eve");

		lh.addStaticHoliday ("01-FEB-2052", "Lunar New Years Day");

		lh.addStaticHoliday ("02-FEB-2052", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2052", "Independence Movement Day");

		lh.addStaticHoliday ("10-APR-2052", "Election Day");

		lh.addStaticHoliday ("01-MAY-2052", "Labour Day");

		lh.addStaticHoliday ("06-MAY-2052", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2052", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2052", "Liberation Day");

		lh.addStaticHoliday ("06-SEP-2052", "Chusok");

		lh.addStaticHoliday ("03-OCT-2052", "National Foundation Day");

		lh.addStaticHoliday ("18-DEC-2052", "Election Day");

		lh.addStaticHoliday ("25-DEC-2052", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2053", "New Years Day");

		lh.addStaticHoliday ("18-FEB-2053", "Lunar New Years Eve");

		lh.addStaticHoliday ("19-FEB-2053", "Lunar New Years Day");

		lh.addStaticHoliday ("20-FEB-2053", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAY-2053", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2053", "Childrens Day");

		lh.addStaticHoliday ("06-JUN-2053", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2053", "Liberation Day");

		lh.addStaticHoliday ("25-SEP-2053", "Chusok");

		lh.addStaticHoliday ("26-SEP-2053", "Chusok");

		lh.addStaticHoliday ("03-OCT-2053", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2053", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2054", "New Years Day");

		lh.addStaticHoliday ("09-FEB-2054", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAY-2054", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2054", "Childrens Day");

		lh.addStaticHoliday ("15-MAY-2054", "Buddhas Birthday");

		lh.addStaticHoliday ("03-JUN-2054", "Election Day");

		lh.addStaticHoliday ("15-SEP-2054", "Chusok");

		lh.addStaticHoliday ("16-SEP-2054", "Chusok");

		lh.addStaticHoliday ("17-SEP-2054", "Chusok");

		lh.addStaticHoliday ("25-DEC-2054", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2055", "New Years Day");

		lh.addStaticHoliday ("27-JAN-2055", "Lunar New Years Eve");

		lh.addStaticHoliday ("28-JAN-2055", "Lunar New Years Day");

		lh.addStaticHoliday ("29-JAN-2055", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2055", "Independence Movement Day");

		lh.addStaticHoliday ("04-MAY-2055", "Buddhas Birthday");

		lh.addStaticHoliday ("05-MAY-2055", "Childrens Day");

		lh.addStaticHoliday ("04-OCT-2055", "Chusok");

		lh.addStaticHoliday ("05-OCT-2055", "Chusok");

		lh.addStaticHoliday ("06-OCT-2055", "Chusok");

		lh.addStaticHoliday ("14-FEB-2056", "Lunar New Years Eve");

		lh.addStaticHoliday ("15-FEB-2056", "Lunar New Years Day");

		lh.addStaticHoliday ("16-FEB-2056", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2056", "Independence Movement Day");

		lh.addStaticHoliday ("12-APR-2056", "Election Day");

		lh.addStaticHoliday ("01-MAY-2056", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2056", "Childrens Day");

		lh.addStaticHoliday ("22-MAY-2056", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2056", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2056", "Liberation Day");

		lh.addStaticHoliday ("25-SEP-2056", "Chusok");

		lh.addStaticHoliday ("03-OCT-2056", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2056", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2057", "New Years Day");

		lh.addStaticHoliday ("05-FEB-2057", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2057", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2057", "Labour Day");

		lh.addStaticHoliday ("11-MAY-2057", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2057", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2057", "Liberation Day");

		lh.addStaticHoliday ("12-SEP-2057", "Chusok");

		lh.addStaticHoliday ("13-SEP-2057", "Chusok");

		lh.addStaticHoliday ("14-SEP-2057", "Chusok");

		lh.addStaticHoliday ("03-OCT-2057", "National Foundation Day");

		lh.addStaticHoliday ("19-DEC-2057", "Election Day");

		lh.addStaticHoliday ("25-DEC-2057", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2058", "New Years Day");

		lh.addStaticHoliday ("23-JAN-2058", "Lunar New Years Eve");

		lh.addStaticHoliday ("24-JAN-2058", "Lunar New Years Day");

		lh.addStaticHoliday ("25-JAN-2058", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2058", "Independence Movement Day");

		lh.addStaticHoliday ("30-APR-2058", "Buddhas Birthday");

		lh.addStaticHoliday ("01-MAY-2058", "Labour Day");

		lh.addStaticHoliday ("05-JUN-2058", "Election Day");

		lh.addStaticHoliday ("06-JUN-2058", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2058", "Liberation Day");

		lh.addStaticHoliday ("01-OCT-2058", "Chusok");

		lh.addStaticHoliday ("02-OCT-2058", "Chusok");

		lh.addStaticHoliday ("03-OCT-2058", "Chusok");

		lh.addStaticHoliday ("25-DEC-2058", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2059", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2059", "Lunar New Years Eve");

		lh.addStaticHoliday ("12-FEB-2059", "Lunar New Years Day");

		lh.addStaticHoliday ("13-FEB-2059", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAY-2059", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2059", "Childrens Day");

		lh.addStaticHoliday ("19-MAY-2059", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2059", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2059", "Liberation Day");

		lh.addStaticHoliday ("22-SEP-2059", "Chusok");

		lh.addStaticHoliday ("03-OCT-2059", "National Foundation Day");

		lh.addStaticHoliday ("25-DEC-2059", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2060", "New Years Day");

		lh.addStaticHoliday ("02-FEB-2060", "Lunar New Years Day");

		lh.addStaticHoliday ("03-FEB-2060", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2060", "Independence Movement Day");

		lh.addStaticHoliday ("14-APR-2060", "Election Day");

		lh.addStaticHoliday ("05-MAY-2060", "Childrens Day");

		lh.addStaticHoliday ("07-MAY-2060", "Buddhas Birthday");

		lh.addStaticHoliday ("08-SEP-2060", "Chusok");

		lh.addStaticHoliday ("09-SEP-2060", "Chusok");

		lh.addStaticHoliday ("10-SEP-2060", "Chusok");

		lh.addStaticHoliday ("21-JAN-2061", "Lunar New Years Eve");

		lh.addStaticHoliday ("01-MAR-2061", "Independence Movement Day");

		lh.addStaticHoliday ("05-MAY-2061", "Childrens Day");

		lh.addStaticHoliday ("26-MAY-2061", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2061", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2061", "Liberation Day");

		lh.addStaticHoliday ("27-SEP-2061", "Chusok");

		lh.addStaticHoliday ("28-SEP-2061", "Chusok");

		lh.addStaticHoliday ("29-SEP-2061", "Chusok");

		lh.addStaticHoliday ("03-OCT-2061", "National Foundation Day");

		lh.addStaticHoliday ("08-FEB-2062", "Lunar New Years Eve");

		lh.addStaticHoliday ("09-FEB-2062", "Lunar New Years Day");

		lh.addStaticHoliday ("10-FEB-2062", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("01-MAR-2062", "Independence Movement Day");

		lh.addStaticHoliday ("01-MAY-2062", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2062", "Childrens Day");

		lh.addStaticHoliday ("16-MAY-2062", "Buddhas Birthday");

		lh.addStaticHoliday ("31-MAY-2062", "Election Day");

		lh.addStaticHoliday ("06-JUN-2062", "Memorial Day");

		lh.addStaticHoliday ("15-AUG-2062", "Liberation Day");

		lh.addStaticHoliday ("18-SEP-2062", "Chusok");

		lh.addStaticHoliday ("03-OCT-2062", "National Foundation Day");

		lh.addStaticHoliday ("20-DEC-2062", "Election Day");

		lh.addStaticHoliday ("25-DEC-2062", "Christmas Day");

		lh.addStandardWeekend();

		return lh;
	}
}
