
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

public class HKDHoliday implements org.drip.analytics.holset.LocationHoliday {
	public HKDHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "HKD";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1999", "First Weekday in January");

		lh.addStaticHoliday ("16-FEB-1999", "Lunar New Years Day");

		lh.addStaticHoliday ("17-FEB-1999", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("18-FEB-1999", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("05-APR-1999", "Easter Monday");

		lh.addStaticHoliday ("06-APR-1999", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("18-JUN-1999", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-1999", "SAR Establishment Day");

		lh.addStaticHoliday ("01-OCT-1999", "Chinese National Day");

		lh.addStaticHoliday ("18-OCT-1999", "Chung Yeung Festival Observed");

		lh.addStaticHoliday ("27-DEC-1999", "Christmas Holiday");

		lh.addStaticHoliday ("31-DEC-1999", "Special Holiday");

		lh.addStaticHoliday ("04-FEB-2000", "Lunar New Years Eve");

		lh.addStaticHoliday ("07-FEB-2000", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("04-APR-2000", "Ching Ming Festival");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("24-APR-2000", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2000", "Labour Day");

		lh.addStaticHoliday ("11-MAY-2000", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2000", "Tuen Ng Festival");

		lh.addStaticHoliday ("13-SEP-2000", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("02-OCT-2000", "Chinese National Day Observed");

		lh.addStaticHoliday ("06-OCT-2000", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2000", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2001", "First Weekday in January");

		lh.addStaticHoliday ("24-JAN-2001", "Lunar New Years Day");

		lh.addStaticHoliday ("25-JAN-2001", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("26-JAN-2001", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("05-APR-2001", "Ching Ming Festival");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("16-APR-2001", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2001", "Buddhas Birthday");

		lh.addStaticHoliday ("01-MAY-2001", "Labour Day");

		lh.addStaticHoliday ("25-JUN-2001", "Tuen Ng Festival");

		lh.addStaticHoliday ("02-JUL-2001", "SAR Establishment Day Observed");

		lh.addStaticHoliday ("06-JUL-2001", "TYPHOON-OFFICE CLOSE");

		lh.addStaticHoliday ("25-JUL-2001", "TYPHOON-OFFICE CLOSE");

		lh.addStaticHoliday ("01-OCT-2001", "Chinese National Day");

		lh.addStaticHoliday ("02-OCT-2001", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("25-OCT-2001", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2001", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2002", "First Weekday in January");

		lh.addStaticHoliday ("12-FEB-2002", "Lunar New Years Day");

		lh.addStaticHoliday ("13-FEB-2002", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("14-FEB-2002", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("01-APR-2002", "Easter Monday");

		lh.addStaticHoliday ("05-APR-2002", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2002", "Labour Day");

		lh.addStaticHoliday ("20-MAY-2002", "Buddhas Birthday Observed");

		lh.addStaticHoliday ("01-JUL-2002", "SAR Establishment Day");

		lh.addStaticHoliday ("01-OCT-2002", "Chinese National Day");

		lh.addStaticHoliday ("14-OCT-2002", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2002", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2003", "First Weekday in January");

		lh.addStaticHoliday ("31-JAN-2003", "Lunar New Years Eve");

		lh.addStaticHoliday ("03-FEB-2003", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("21-APR-2003", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2003", "Labour Day");

		lh.addStaticHoliday ("08-MAY-2003", "Buddhas Birthday");

		lh.addStaticHoliday ("04-JUN-2003", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2003", "SAR Establishment Day");

		lh.addStaticHoliday ("12-SEP-2003", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2003", "Chinese National Day");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2003", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2004", "First Weekday in January");

		lh.addStaticHoliday ("22-JAN-2004", "Special Holiday");

		lh.addStaticHoliday ("23-JAN-2004", "Special Holiday");

		lh.addStaticHoliday ("24-JAN-2004", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("05-APR-2004", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("10-APR-2004", "Special Holiday");

		lh.addStaticHoliday ("12-APR-2004", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2004", "Labor Day");

		lh.addStaticHoliday ("26-MAY-2004", "Buddhas Birthday");

		lh.addStaticHoliday ("22-JUN-2004", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2004", "SAR Establishment Day");

		lh.addStaticHoliday ("29-SEP-2004", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2004", "Chinese National Day");

		lh.addStaticHoliday ("22-OCT-2004", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2004", "Christmas Day");

		lh.addStaticHoliday ("27-DEC-2004", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2005", "First Weekday in January");

		lh.addStaticHoliday ("09-FEB-2005", "Lunar New Years Day");

		lh.addStaticHoliday ("10-FEB-2005", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("11-FEB-2005", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("26-MAR-2005", "Day after Good Friday");

		lh.addStaticHoliday ("28-MAR-2005", "Easter Monday");

		lh.addStaticHoliday ("05-APR-2005", "Ching Ming Festival");

		lh.addStaticHoliday ("02-MAY-2005", "Labour Day Observed");

		lh.addStaticHoliday ("16-MAY-2005", "Buddhas Birthday Observed");

		lh.addStaticHoliday ("11-JUN-2005", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2005", "SAR Establishment Day");

		lh.addStaticHoliday ("19-SEP-2005", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2005", "National Day");

		lh.addStaticHoliday ("11-OCT-2005", "Chung Yeung Festival");

		lh.addStaticHoliday ("26-DEC-2005", "Christmas Holiday");

		lh.addStaticHoliday ("27-DEC-2005", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2006", "First Weekday in January");

		lh.addStaticHoliday ("28-JAN-2006", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("30-JAN-2006", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("31-JAN-2006", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("05-APR-2006", "Ching Ming Festival");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("15-APR-2006", "Day after Good Friday");

		lh.addStaticHoliday ("17-APR-2006", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2006", "Buddhas Birthday");

		lh.addStaticHoliday ("31-MAY-2006", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2006", "SAR Establishment Day");

		lh.addStaticHoliday ("02-OCT-2006", "Chinese National Day Observed");

		lh.addStaticHoliday ("07-OCT-2006", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("30-OCT-2006", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2006", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2007", "First Weekday in January");

		lh.addStaticHoliday ("19-FEB-2007", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("20-FEB-2007", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("05-APR-2007", "Ching Ming Festival");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("07-APR-2007", "Day after Good Friday");

		lh.addStaticHoliday ("09-APR-2007", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2007", "Labour Day");

		lh.addStaticHoliday ("24-MAY-2007", "Buddhas Birthday");

		lh.addStaticHoliday ("19-JUN-2007", "Tuen Ng Festival");

		lh.addStaticHoliday ("02-JUL-2007", "SAR Establishment Day Observed");

		lh.addStaticHoliday ("26-SEP-2007", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2007", "Chinese National Day");

		lh.addStaticHoliday ("19-OCT-2007", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2007", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2008", "First Weekday in January");

		lh.addStaticHoliday ("07-FEB-2008", "Lunar New Years Day");

		lh.addStaticHoliday ("08-FEB-2008", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("09-FEB-2008", "Third Day of Lunar New Year Observed");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("22-MAR-2008", "Day after Good Friday");

		lh.addStaticHoliday ("24-MAR-2008", "Easter Monday");

		lh.addStaticHoliday ("04-APR-2008", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2008", "Labour Day");

		lh.addStaticHoliday ("12-MAY-2008", "Buddhas Birthday");

		lh.addStaticHoliday ("09-JUN-2008", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("01-JUL-2008", "SAR Establishment Day");

		lh.addStaticHoliday ("06-AUG-2008", "Special Holiday");

		lh.addStaticHoliday ("22-AUG-2008", "Special Holiday");

		lh.addStaticHoliday ("15-SEP-2008", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2008", "Chinese National Day");

		lh.addStaticHoliday ("07-OCT-2008", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2008", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2009", "First Weekday in January");

		lh.addStaticHoliday ("26-JAN-2009", "Lunar New Years Day");

		lh.addStaticHoliday ("27-JAN-2009", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("28-JAN-2009", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("04-APR-2009", "Ching Ming Festival");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("11-APR-2009", "Day after Good Friday");

		lh.addStaticHoliday ("13-APR-2009", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2009", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2009", "Buddhas Birthday");

		lh.addStaticHoliday ("28-MAY-2009", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2009", "SAR Establishment Day");

		lh.addStaticHoliday ("01-OCT-2009", "Chinese National Day");

		lh.addStaticHoliday ("03-OCT-2009", "Mid-Autumn Festival");

		lh.addStaticHoliday ("26-OCT-2009", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2009", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2010", "First Weekday in January");

		lh.addStaticHoliday ("13-FEB-2010", "Lunar New Years Eve Observed");

		lh.addStaticHoliday ("15-FEB-2010", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("16-FEB-2010", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("03-APR-2010", "Day after Good Friday");

		lh.addStaticHoliday ("05-APR-2010", "Easter Monday");

		lh.addStaticHoliday ("06-APR-2010", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("01-MAY-2010", "Labour Day");

		lh.addStaticHoliday ("21-MAY-2010", "Buddhas Birthday");

		lh.addStaticHoliday ("16-JUN-2010", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2010", "SAR Establishment Day");

		lh.addStaticHoliday ("23-SEP-2010", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2010", "Chinese National Day");

		lh.addStaticHoliday ("16-OCT-2010", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2010", "Christmas Day");

		lh.addStaticHoliday ("27-DEC-2010", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2011", "First Weekday in January");

		lh.addStaticHoliday ("03-FEB-2011", "Lunar New Years Day");

		lh.addStaticHoliday ("04-FEB-2011", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("05-FEB-2011", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("05-APR-2011", "Ching Ming Festival");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("23-APR-2011", "Day after Good Friday");

		lh.addStaticHoliday ("25-APR-2011", "Easter Monday");

		lh.addStaticHoliday ("02-MAY-2011", "Labour Day Observed");

		lh.addStaticHoliday ("10-MAY-2011", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2011", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2011", "SAR Establishment Day");

		lh.addStaticHoliday ("13-SEP-2011", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2011", "National Day");

		lh.addStaticHoliday ("05-OCT-2011", "Chung Yeung Festival");

		lh.addStaticHoliday ("26-DEC-2011", "Christmas Holiday");

		lh.addStaticHoliday ("27-DEC-2011", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2012", "First Weekday in January");

		lh.addStaticHoliday ("23-JAN-2012", "Lunar New Years Day");

		lh.addStaticHoliday ("24-JAN-2012", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("25-JAN-2012", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("04-APR-2012", "Ching Ming Festival");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("09-APR-2012", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2012", "Labour Day");

		lh.addStaticHoliday ("02-JUL-2012", "SAR Establishment Day Observed");

		lh.addStaticHoliday ("01-OCT-2012", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("02-OCT-2012", "Chinese National Day Observed");

		lh.addStaticHoliday ("23-OCT-2012", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2012", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2013", "First Weekday in January");

		lh.addStaticHoliday ("11-FEB-2013", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("12-FEB-2013", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("01-APR-2013", "Easter Monday");

		lh.addStaticHoliday ("04-APR-2013", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2013", "Labour Day");

		lh.addStaticHoliday ("17-MAY-2013", "Buddhas Birthday");

		lh.addStaticHoliday ("12-JUN-2013", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2013", "SAR Establishment Day");

		lh.addStaticHoliday ("20-SEP-2013", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2013", "Chinese National Day");

		lh.addStaticHoliday ("14-OCT-2013", "Chung Yeung Festival Observed");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2013", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2014", "First Weekday in January");

		lh.addStaticHoliday ("30-JAN-2014", "Lunar New Years Eve");

		lh.addStaticHoliday ("31-JAN-2014", "Lunar New Years Day");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("21-APR-2014", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2014", "Labour Day");

		lh.addStaticHoliday ("06-MAY-2014", "Buddhas Birthday");

		lh.addStaticHoliday ("02-JUN-2014", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2014", "SAR Establishment Day");

		lh.addStaticHoliday ("09-SEP-2014", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2014", "Chinese National Day");

		lh.addStaticHoliday ("02-OCT-2014", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2014", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2015", "First Weekday in January");

		lh.addStaticHoliday ("19-FEB-2015", "Lunar New Years Day");

		lh.addStaticHoliday ("20-FEB-2015", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("06-APR-2015", "Easter Monday");

		lh.addStaticHoliday ("07-APR-2015", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("01-MAY-2015", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2015", "Buddhas Birthday");

		lh.addStaticHoliday ("01-JUL-2015", "SAR Establishment Day");

		lh.addStaticHoliday ("28-SEP-2015", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2015", "Chinese National Day");

		lh.addStaticHoliday ("21-OCT-2015", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2016", "First Weekday in January");

		lh.addStaticHoliday ("08-FEB-2016", "Lunar New Years Day");

		lh.addStaticHoliday ("09-FEB-2016", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("10-FEB-2016", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("28-MAR-2016", "Easter Monday");

		lh.addStaticHoliday ("04-APR-2016", "Ching Ming Festival");

		lh.addStaticHoliday ("02-MAY-2016", "Labour Day Observed");

		lh.addStaticHoliday ("09-JUN-2016", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2016", "SAR Establishment Day");

		lh.addStaticHoliday ("16-SEP-2016", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("10-OCT-2016", "Chung Yeung Festival Observed");

		lh.addStaticHoliday ("26-DEC-2016", "Christmas Holiday");

		lh.addStaticHoliday ("27-DEC-2016", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2017", "First Weekday in January");

		lh.addStaticHoliday ("27-JAN-2017", "Lunar New Years Eve");

		lh.addStaticHoliday ("30-JAN-2017", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("04-APR-2017", "Ching Ming Festival");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("17-APR-2017", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2017", "Labour Day");

		lh.addStaticHoliday ("03-MAY-2017", "Buddhas Birthday");

		lh.addStaticHoliday ("30-MAY-2017", "Tuen Ng Festival");

		lh.addStaticHoliday ("02-OCT-2017", "Chinese National Day Observed");

		lh.addStaticHoliday ("05-OCT-2017", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2017", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2018", "First Weekday in January");

		lh.addStaticHoliday ("15-FEB-2018", "Lunar New Years Eve");

		lh.addStaticHoliday ("16-FEB-2018", "Lunar New Years Day");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("02-APR-2018", "Easter Monday");

		lh.addStaticHoliday ("05-APR-2018", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2018", "Labour Day");

		lh.addStaticHoliday ("22-MAY-2018", "Buddhas Birthday");

		lh.addStaticHoliday ("18-JUN-2018", "Tuen Ng Festival");

		lh.addStaticHoliday ("02-JUL-2018", "SAR Establishment Day Observed");

		lh.addStaticHoliday ("25-SEP-2018", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2018", "Chinese National Day");

		lh.addStaticHoliday ("17-OCT-2018", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2018", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2019", "First Weekday in January");

		lh.addStaticHoliday ("05-FEB-2019", "Lunar New Years Day");

		lh.addStaticHoliday ("06-FEB-2019", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("07-FEB-2019", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("05-APR-2019", "Ching Ming Festival");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("22-APR-2019", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2019", "Labour Day");

		lh.addStaticHoliday ("13-MAY-2019", "Buddhas Birthday Observed");

		lh.addStaticHoliday ("07-JUN-2019", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2019", "SAR Establishment Day");

		lh.addStaticHoliday ("01-OCT-2019", "Chinese National Day");

		lh.addStaticHoliday ("07-OCT-2019", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2019", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2020", "First Weekday in January");

		lh.addStaticHoliday ("24-JAN-2020", "Lunar New Years Eve");

		lh.addStaticHoliday ("27-JAN-2020", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("13-APR-2020", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2020", "Buddhas Birthday");

		lh.addStaticHoliday ("01-MAY-2020", "Labour Day");

		lh.addStaticHoliday ("25-JUN-2020", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2020", "SAR Establishment Day");

		lh.addStaticHoliday ("01-OCT-2020", "Chinese National Day");

		lh.addStaticHoliday ("02-OCT-2020", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("26-OCT-2020", "Chung Yeung Festival Observed");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2021", "First Weekday in January");

		lh.addStaticHoliday ("11-FEB-2021", "Lunar New Years Eve");

		lh.addStaticHoliday ("12-FEB-2021", "Lunar New Years Day");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("05-APR-2021", "Easter Monday");

		lh.addStaticHoliday ("06-APR-2021", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("19-MAY-2021", "Buddhas Birthday");

		lh.addStaticHoliday ("14-JUN-2021", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2021", "SAR Establishment Day");

		lh.addStaticHoliday ("22-SEP-2021", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2021", "Chinese National Day");

		lh.addStaticHoliday ("14-OCT-2021", "Chung Yeung Festival");

		lh.addStaticHoliday ("27-DEC-2021", "Christmas Holiday");

		lh.addStaticHoliday ("01-FEB-2022", "Lunar New Years Day");

		lh.addStaticHoliday ("02-FEB-2022", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("03-FEB-2022", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("05-APR-2022", "Ching Ming Festival");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("18-APR-2022", "Easter Monday");

		lh.addStaticHoliday ("02-MAY-2022", "Labour Day Observed");

		lh.addStaticHoliday ("09-MAY-2022", "Buddhas Birthday Observed");

		lh.addStaticHoliday ("03-JUN-2022", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2022", "SAR Establishment Day");

		lh.addStaticHoliday ("04-OCT-2022", "Chung Yeung Festival");

		lh.addStaticHoliday ("26-DEC-2022", "Christmas Holiday");

		lh.addStaticHoliday ("27-DEC-2022", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2023", "First Weekday in January");

		lh.addStaticHoliday ("23-JAN-2023", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("24-JAN-2023", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("05-APR-2023", "Ching Ming Festival");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("10-APR-2023", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2023", "Labour Day");

		lh.addStaticHoliday ("26-MAY-2023", "Buddhas Birthday");

		lh.addStaticHoliday ("22-JUN-2023", "Tuen Ng Festival");

		lh.addStaticHoliday ("02-OCT-2023", "Chinese National Day Observed");

		lh.addStaticHoliday ("23-OCT-2023", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2023", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2024", "First Weekday in January");

		lh.addStaticHoliday ("09-FEB-2024", "Lunar New Years Eve");

		lh.addStaticHoliday ("12-FEB-2024", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("01-APR-2024", "Easter Monday");

		lh.addStaticHoliday ("04-APR-2024", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2024", "Labour Day");

		lh.addStaticHoliday ("15-MAY-2024", "Buddhas Birthday");

		lh.addStaticHoliday ("10-JUN-2024", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2024", "SAR Establishment Day");

		lh.addStaticHoliday ("18-SEP-2024", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2024", "Chinese National Day");

		lh.addStaticHoliday ("11-OCT-2024", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2024", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2025", "First Weekday in January");

		lh.addStaticHoliday ("29-JAN-2025", "Lunar New Years Day");

		lh.addStaticHoliday ("30-JAN-2025", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("31-JAN-2025", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("04-APR-2025", "Ching Ming Festival");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("21-APR-2025", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2025", "Labour Day");

		lh.addStaticHoliday ("05-MAY-2025", "Buddhas Birthday");

		lh.addStaticHoliday ("01-JUL-2025", "SAR Establishment Day");

		lh.addStaticHoliday ("01-OCT-2025", "Chinese National Day");

		lh.addStaticHoliday ("07-OCT-2025", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("29-OCT-2025", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2025", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2026", "First Weekday in January");

		lh.addStaticHoliday ("17-FEB-2026", "Lunar New Years Day");

		lh.addStaticHoliday ("18-FEB-2026", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("19-FEB-2026", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("06-APR-2026", "Easter Monday");

		lh.addStaticHoliday ("07-APR-2026", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("01-MAY-2026", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2026", "Buddhas Birthday Observed");

		lh.addStaticHoliday ("19-JUN-2026", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2026", "SAR Establishment Day");

		lh.addStaticHoliday ("01-OCT-2026", "Chinese National Day");

		lh.addStaticHoliday ("19-OCT-2026", "Chung Yeung Festival Observed");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2027", "First Weekday in January");

		lh.addStaticHoliday ("05-FEB-2027", "Lunar New Years Eve");

		lh.addStaticHoliday ("08-FEB-2027", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("29-MAR-2027", "Easter Monday");

		lh.addStaticHoliday ("05-APR-2027", "Ching Ming Festival");

		lh.addStaticHoliday ("13-MAY-2027", "Buddhas Birthday");

		lh.addStaticHoliday ("09-JUN-2027", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2027", "SAR Establishment Day");

		lh.addStaticHoliday ("16-SEP-2027", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2027", "Chinese National Day");

		lh.addStaticHoliday ("08-OCT-2027", "Chung Yeung Festival");

		lh.addStaticHoliday ("27-DEC-2027", "Christmas Holiday");

		lh.addStaticHoliday ("26-JAN-2028", "Lunar New Years Day");

		lh.addStaticHoliday ("27-JAN-2028", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("28-JAN-2028", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("04-APR-2028", "Ching Ming Festival");

		lh.addStaticHoliday ("14-APR-2028", "Good Friday");

		lh.addStaticHoliday ("17-APR-2028", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2028", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2028", "Buddhas Birthday");

		lh.addStaticHoliday ("29-MAY-2028", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("02-OCT-2028", "Chinese National Day Observed");

		lh.addStaticHoliday ("04-OCT-2028", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("26-OCT-2028", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2028", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2029", "First Weekday in January");

		lh.addStaticHoliday ("13-FEB-2029", "Lunar New Years Day");

		lh.addStaticHoliday ("14-FEB-2029", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("15-FEB-2029", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("30-MAR-2029", "Good Friday");

		lh.addStaticHoliday ("02-APR-2029", "Easter Monday");

		lh.addStaticHoliday ("04-APR-2029", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2029", "Labour Day");

		lh.addStaticHoliday ("21-MAY-2029", "Buddhas Birthday Observed");

		lh.addStaticHoliday ("02-JUL-2029", "SAR Establishment Day Observed");

		lh.addStaticHoliday ("01-OCT-2029", "Chinese National Day");

		lh.addStaticHoliday ("16-OCT-2029", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2029", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2029", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2030", "First Weekday in January");

		lh.addStaticHoliday ("04-FEB-2030", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("05-FEB-2030", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("05-APR-2030", "Ching Ming Festival");

		lh.addStaticHoliday ("19-APR-2030", "Good Friday");

		lh.addStaticHoliday ("22-APR-2030", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2030", "Labour Day");

		lh.addStaticHoliday ("09-MAY-2030", "Buddhas Birthday");

		lh.addStaticHoliday ("05-JUN-2030", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2030", "SAR Establishment Day");

		lh.addStaticHoliday ("13-SEP-2030", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2030", "Chinese National Day");

		lh.addStaticHoliday ("25-DEC-2030", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2030", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2031", "First Weekday in January");

		lh.addStaticHoliday ("23-JAN-2031", "Lunar New Years Day");

		lh.addStaticHoliday ("24-JAN-2031", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("11-APR-2031", "Good Friday");

		lh.addStaticHoliday ("14-APR-2031", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2031", "Labour Day");

		lh.addStaticHoliday ("28-MAY-2031", "Buddhas Birthday");

		lh.addStaticHoliday ("24-JUN-2031", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2031", "SAR Establishment Day");

		lh.addStaticHoliday ("01-OCT-2031", "Chinese National Day");

		lh.addStaticHoliday ("02-OCT-2031", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("24-OCT-2031", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2031", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2031", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2032", "First Weekday in January");

		lh.addStaticHoliday ("11-FEB-2032", "Lunar New Years Day");

		lh.addStaticHoliday ("12-FEB-2032", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("13-FEB-2032", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("26-MAR-2032", "Good Friday");

		lh.addStaticHoliday ("29-MAR-2032", "Easter Monday");

		lh.addStaticHoliday ("05-APR-2032", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("17-MAY-2032", "Buddhas Birthday Observed");

		lh.addStaticHoliday ("01-JUL-2032", "SAR Establishment Day");

		lh.addStaticHoliday ("20-SEP-2032", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2032", "Chinese National Day");

		lh.addStaticHoliday ("12-OCT-2032", "Chung Yeung Festival");

		lh.addStaticHoliday ("27-DEC-2032", "Christmas Holiday");

		lh.addStaticHoliday ("31-JAN-2033", "Lunar New Years Day");

		lh.addStaticHoliday ("01-FEB-2033", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("02-FEB-2033", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("04-APR-2033", "Ching Ming Festival");

		lh.addStaticHoliday ("15-APR-2033", "Good Friday");

		lh.addStaticHoliday ("18-APR-2033", "Easter Monday");

		lh.addStaticHoliday ("02-MAY-2033", "Labour Day Observed");

		lh.addStaticHoliday ("06-MAY-2033", "Buddhas Birthday");

		lh.addStaticHoliday ("01-JUN-2033", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2033", "SAR Establishment Day");

		lh.addStaticHoliday ("09-SEP-2033", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("26-DEC-2033", "Christmas Holiday");

		lh.addStaticHoliday ("27-DEC-2033", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2034", "First Weekday in January");

		lh.addStaticHoliday ("20-FEB-2034", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("21-FEB-2034", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("05-APR-2034", "Ching Ming Festival");

		lh.addStaticHoliday ("07-APR-2034", "Good Friday");

		lh.addStaticHoliday ("10-APR-2034", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2034", "Labour Day");

		lh.addStaticHoliday ("25-MAY-2034", "Buddhas Birthday");

		lh.addStaticHoliday ("20-JUN-2034", "Tuen Ng Festival");

		lh.addStaticHoliday ("28-SEP-2034", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("02-OCT-2034", "Chinese National Day Observed");

		lh.addStaticHoliday ("20-OCT-2034", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2034", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2034", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2035", "First Weekday in January");

		lh.addStaticHoliday ("08-FEB-2035", "Lunar New Years Day");

		lh.addStaticHoliday ("09-FEB-2035", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("23-MAR-2035", "Good Friday");

		lh.addStaticHoliday ("26-MAR-2035", "Easter Monday");

		lh.addStaticHoliday ("05-APR-2035", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2035", "Labour Day");

		lh.addStaticHoliday ("15-MAY-2035", "Buddhas Birthday");

		lh.addStaticHoliday ("11-JUN-2035", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("02-JUL-2035", "SAR Establishment Day Observed");

		lh.addStaticHoliday ("17-SEP-2035", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2035", "Chinese National Day");

		lh.addStaticHoliday ("09-OCT-2035", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2035", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2035", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2036", "First Weekday in January");

		lh.addStaticHoliday ("28-JAN-2036", "Lunar New Years Day");

		lh.addStaticHoliday ("29-JAN-2036", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("30-JAN-2036", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("04-APR-2036", "Ching Ming Festival");

		lh.addStaticHoliday ("11-APR-2036", "Good Friday");

		lh.addStaticHoliday ("14-APR-2036", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2036", "Labour Day");

		lh.addStaticHoliday ("30-MAY-2036", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2036", "SAR Establishment Day");

		lh.addStaticHoliday ("01-OCT-2036", "Chinese National Day");

		lh.addStaticHoliday ("27-OCT-2036", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2036", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2036", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2037", "First Weekday in January");

		lh.addStaticHoliday ("16-FEB-2037", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("17-FEB-2037", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("03-APR-2037", "Good Friday");

		lh.addStaticHoliday ("06-APR-2037", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2037", "Labour Day");

		lh.addStaticHoliday ("22-MAY-2037", "Buddhas Birthday");

		lh.addStaticHoliday ("18-JUN-2037", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2037", "SAR Establishment Day");

		lh.addStaticHoliday ("25-SEP-2037", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2037", "Chinese National Day");

		lh.addStaticHoliday ("25-DEC-2037", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2038", "First Weekday in January");

		lh.addStaticHoliday ("04-FEB-2038", "Lunar New Years Day");

		lh.addStaticHoliday ("05-FEB-2038", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("05-APR-2038", "Ching Ming Festival");

		lh.addStaticHoliday ("23-APR-2038", "Good Friday");

		lh.addStaticHoliday ("26-APR-2038", "Easter Monday");

		lh.addStaticHoliday ("11-MAY-2038", "Buddhas Birthday");

		lh.addStaticHoliday ("07-JUN-2038", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2038", "SAR Establishment Day");

		lh.addStaticHoliday ("14-SEP-2038", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2038", "Chinese National Day");

		lh.addStaticHoliday ("07-OCT-2038", "Chung Yeung Festival");

		lh.addStaticHoliday ("27-DEC-2038", "Christmas Holiday");

		lh.addStaticHoliday ("24-JAN-2039", "Lunar New Years Day");

		lh.addStaticHoliday ("25-JAN-2039", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("26-JAN-2039", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("05-APR-2039", "Ching Ming Festival");

		lh.addStaticHoliday ("08-APR-2039", "Good Friday");

		lh.addStaticHoliday ("11-APR-2039", "Easter Monday");

		lh.addStaticHoliday ("02-MAY-2039", "Labour Day Observed");

		lh.addStaticHoliday ("27-MAY-2039", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2039", "SAR Establishment Day");

		lh.addStaticHoliday ("03-OCT-2039", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("26-OCT-2039", "Chung Yeung Festival");

		lh.addStaticHoliday ("26-DEC-2039", "Christmas Holiday");

		lh.addStaticHoliday ("27-DEC-2039", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2040", "First Weekday in January");

		lh.addStaticHoliday ("13-FEB-2040", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("14-FEB-2040", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("30-MAR-2040", "Good Friday");

		lh.addStaticHoliday ("02-APR-2040", "Easter Monday");

		lh.addStaticHoliday ("04-APR-2040", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2040", "Labour Day");

		lh.addStaticHoliday ("18-MAY-2040", "Buddhas Birthday");

		lh.addStaticHoliday ("14-JUN-2040", "Tuen Ng Festival");

		lh.addStaticHoliday ("02-JUL-2040", "SAR Establishment Day Observed");

		lh.addStaticHoliday ("21-SEP-2040", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2040", "Chinese National Day");

		lh.addStaticHoliday ("15-OCT-2040", "Chung Yeung Festival Observed");

		lh.addStaticHoliday ("25-DEC-2040", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2040", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2041", "First Weekday in January");

		lh.addStaticHoliday ("31-JAN-2041", "Lunar New Years Eve");

		lh.addStaticHoliday ("01-FEB-2041", "Lunar New Years Day");

		lh.addStaticHoliday ("04-APR-2041", "Ching Ming Festival");

		lh.addStaticHoliday ("19-APR-2041", "Good Friday");

		lh.addStaticHoliday ("22-APR-2041", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2041", "Labour Day");

		lh.addStaticHoliday ("07-MAY-2041", "Buddhas Birthday");

		lh.addStaticHoliday ("03-JUN-2041", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2041", "SAR Establishment Day");

		lh.addStaticHoliday ("11-SEP-2041", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2041", "Chinese National Day");

		lh.addStaticHoliday ("03-OCT-2041", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2041", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2041", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2042", "First Weekday in January");

		lh.addStaticHoliday ("22-JAN-2042", "Lunar New Years Day");

		lh.addStaticHoliday ("23-JAN-2042", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("24-JAN-2042", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("03-APR-2042", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("04-APR-2042", "Good Friday");

		lh.addStaticHoliday ("07-APR-2042", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2042", "Labour Day");

		lh.addStaticHoliday ("26-MAY-2042", "Buddhas Birthday");

		lh.addStaticHoliday ("23-JUN-2042", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("01-JUL-2042", "SAR Establishment Day");

		lh.addStaticHoliday ("29-SEP-2042", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2042", "Chinese National Day");

		lh.addStaticHoliday ("22-OCT-2042", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2042", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2042", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2043", "First Weekday in January");

		lh.addStaticHoliday ("10-FEB-2043", "Lunar New Years Day");

		lh.addStaticHoliday ("11-FEB-2043", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("12-FEB-2043", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("27-MAR-2043", "Good Friday");

		lh.addStaticHoliday ("30-MAR-2043", "Easter Monday");

		lh.addStaticHoliday ("06-APR-2043", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("01-MAY-2043", "Labour Day");

		lh.addStaticHoliday ("11-JUN-2043", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2043", "SAR Establishment Day");

		lh.addStaticHoliday ("18-SEP-2043", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2043", "Chinese National Day");

		lh.addStaticHoliday ("12-OCT-2043", "Chung Yeung Festival Observed");

		lh.addStaticHoliday ("25-DEC-2043", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2044", "First Weekday in January");

		lh.addStaticHoliday ("29-JAN-2044", "Lunar New Years Eve");

		lh.addStaticHoliday ("01-FEB-2044", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("04-APR-2044", "Ching Ming Festival");

		lh.addStaticHoliday ("15-APR-2044", "Good Friday");

		lh.addStaticHoliday ("18-APR-2044", "Easter Monday");

		lh.addStaticHoliday ("02-MAY-2044", "Labour Day Observed");

		lh.addStaticHoliday ("05-MAY-2044", "Buddhas Birthday");

		lh.addStaticHoliday ("31-MAY-2044", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2044", "SAR Establishment Day");

		lh.addStaticHoliday ("06-OCT-2044", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("26-DEC-2044", "Christmas Holiday");

		lh.addStaticHoliday ("27-DEC-2044", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2045", "First Weekday in January");

		lh.addStaticHoliday ("16-FEB-2045", "Lunar New Years Eve");

		lh.addStaticHoliday ("17-FEB-2045", "Lunar New Years Day");

		lh.addStaticHoliday ("04-APR-2045", "Ching Ming Festival");

		lh.addStaticHoliday ("07-APR-2045", "Good Friday");

		lh.addStaticHoliday ("10-APR-2045", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2045", "Labour Day");

		lh.addStaticHoliday ("24-MAY-2045", "Buddhas Birthday");

		lh.addStaticHoliday ("19-JUN-2045", "Tuen Ng Festival");

		lh.addStaticHoliday ("26-SEP-2045", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("02-OCT-2045", "Chinese National Day Observed");

		lh.addStaticHoliday ("18-OCT-2045", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2045", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2045", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2046", "First Weekday in January");

		lh.addStaticHoliday ("06-FEB-2046", "Lunar New Years Day");

		lh.addStaticHoliday ("07-FEB-2046", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("08-FEB-2046", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("23-MAR-2046", "Good Friday");

		lh.addStaticHoliday ("26-MAR-2046", "Easter Monday");

		lh.addStaticHoliday ("04-APR-2046", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2046", "Labour Day");

		lh.addStaticHoliday ("14-MAY-2046", "Buddhas Birthday Observed");

		lh.addStaticHoliday ("08-JUN-2046", "Tuen Ng Festival");

		lh.addStaticHoliday ("02-JUL-2046", "SAR Establishment Day Observed");

		lh.addStaticHoliday ("01-OCT-2046", "Chinese National Day");

		lh.addStaticHoliday ("08-OCT-2046", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2046", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2046", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2047", "First Weekday in January");

		lh.addStaticHoliday ("25-JAN-2047", "Lunar New Years Eve");

		lh.addStaticHoliday ("28-JAN-2047", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("05-APR-2047", "Ching Ming Festival");

		lh.addStaticHoliday ("12-APR-2047", "Good Friday");

		lh.addStaticHoliday ("15-APR-2047", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2047", "Labour Day");

		lh.addStaticHoliday ("02-MAY-2047", "Buddhas Birthday");

		lh.addStaticHoliday ("29-MAY-2047", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2047", "SAR Establishment Day");

		lh.addStaticHoliday ("01-OCT-2047", "Chinese National Day");

		lh.addStaticHoliday ("28-OCT-2047", "Chung Yeung Festival Observed");

		lh.addStaticHoliday ("25-DEC-2047", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2047", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2048", "First Weekday in January");

		lh.addStaticHoliday ("13-FEB-2048", "Lunar New Years Eve");

		lh.addStaticHoliday ("14-FEB-2048", "Lunar New Years Day");

		lh.addStaticHoliday ("03-APR-2048", "Good Friday");

		lh.addStaticHoliday ("06-APR-2048", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2048", "Labour Day");

		lh.addStaticHoliday ("20-MAY-2048", "Buddhas Birthday");

		lh.addStaticHoliday ("15-JUN-2048", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2048", "SAR Establishment Day");

		lh.addStaticHoliday ("23-SEP-2048", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2048", "Chinese National Day");

		lh.addStaticHoliday ("16-OCT-2048", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2048", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2049", "First Weekday in January");

		lh.addStaticHoliday ("02-FEB-2049", "Lunar New Years Day");

		lh.addStaticHoliday ("03-FEB-2049", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("04-FEB-2049", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("05-APR-2049", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("16-APR-2049", "Good Friday");

		lh.addStaticHoliday ("19-APR-2049", "Easter Monday");

		lh.addStaticHoliday ("10-MAY-2049", "Buddhas Birthday Observed");

		lh.addStaticHoliday ("04-JUN-2049", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2049", "SAR Establishment Day");

		lh.addStaticHoliday ("01-OCT-2049", "Chinese National Day");

		lh.addStaticHoliday ("05-OCT-2049", "Chung Yeung Festival");

		lh.addStaticHoliday ("27-DEC-2049", "Christmas Holiday");

		lh.addStaticHoliday ("24-JAN-2050", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("25-JAN-2050", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("04-APR-2050", "Ching Ming Festival");

		lh.addStaticHoliday ("08-APR-2050", "Good Friday");

		lh.addStaticHoliday ("11-APR-2050", "Easter Monday");

		lh.addStaticHoliday ("02-MAY-2050", "Labour Day Observed");

		lh.addStaticHoliday ("23-JUN-2050", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2050", "SAR Establishment Day");

		lh.addStaticHoliday ("24-OCT-2050", "Chung Yeung Festival");

		lh.addStaticHoliday ("26-DEC-2050", "Christmas Holiday");

		lh.addStaticHoliday ("27-DEC-2050", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2051", "First Weekday in January");

		lh.addStaticHoliday ("10-FEB-2051", "Lunar New Years Eve");

		lh.addStaticHoliday ("13-FEB-2051", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("31-MAR-2051", "Good Friday");

		lh.addStaticHoliday ("03-APR-2051", "Easter Monday");

		lh.addStaticHoliday ("05-APR-2051", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2051", "Labour Day");

		lh.addStaticHoliday ("17-MAY-2051", "Buddhas Birthday");

		lh.addStaticHoliday ("13-JUN-2051", "Tuen Ng Festival");

		lh.addStaticHoliday ("20-SEP-2051", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("02-OCT-2051", "Chinese National Day Observed");

		lh.addStaticHoliday ("13-OCT-2051", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2051", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2051", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2052", "First Weekday in January");

		lh.addStaticHoliday ("01-FEB-2052", "Lunar New Years Day");

		lh.addStaticHoliday ("02-FEB-2052", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("04-APR-2052", "Ching Ming Festival");

		lh.addStaticHoliday ("19-APR-2052", "Good Friday");

		lh.addStaticHoliday ("22-APR-2052", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2052", "Labour Day");

		lh.addStaticHoliday ("06-MAY-2052", "Buddhas Birthday");

		lh.addStaticHoliday ("01-JUL-2052", "SAR Establishment Day");

		lh.addStaticHoliday ("01-OCT-2052", "Chinese National Day");

		lh.addStaticHoliday ("30-OCT-2052", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2052", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2052", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2053", "First Weekday in January");

		lh.addStaticHoliday ("19-FEB-2053", "Lunar New Years Day");

		lh.addStaticHoliday ("20-FEB-2053", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("21-FEB-2053", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("03-APR-2053", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("04-APR-2053", "Good Friday");

		lh.addStaticHoliday ("07-APR-2053", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2053", "Labour Day");

		lh.addStaticHoliday ("26-MAY-2053", "Buddhas Birthday Observed");

		lh.addStaticHoliday ("20-JUN-2053", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2053", "SAR Establishment Day");

		lh.addStaticHoliday ("01-OCT-2053", "Chinese National Day");

		lh.addStaticHoliday ("20-OCT-2053", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2053", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2053", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2054", "First Weekday in January");

		lh.addStaticHoliday ("09-FEB-2054", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("10-FEB-2054", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("27-MAR-2054", "Good Friday");

		lh.addStaticHoliday ("30-MAR-2054", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2054", "Labour Day");

		lh.addStaticHoliday ("15-MAY-2054", "Buddhas Birthday");

		lh.addStaticHoliday ("10-JUN-2054", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2054", "SAR Establishment Day");

		lh.addStaticHoliday ("17-SEP-2054", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2054", "Chinese National Day");

		lh.addStaticHoliday ("09-OCT-2054", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2054", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2055", "First Weekday in January");

		lh.addStaticHoliday ("28-JAN-2055", "Lunar New Years Day");

		lh.addStaticHoliday ("29-JAN-2055", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("05-APR-2055", "Ching Ming Festival");

		lh.addStaticHoliday ("16-APR-2055", "Good Friday");

		lh.addStaticHoliday ("19-APR-2055", "Easter Monday");

		lh.addStaticHoliday ("04-MAY-2055", "Buddhas Birthday");

		lh.addStaticHoliday ("31-MAY-2055", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("01-JUL-2055", "SAR Establishment Day");

		lh.addStaticHoliday ("01-OCT-2055", "Chinese National Day");

		lh.addStaticHoliday ("06-OCT-2055", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("28-OCT-2055", "Chung Yeung Festival");

		lh.addStaticHoliday ("27-DEC-2055", "Christmas Holiday");

		lh.addStaticHoliday ("15-FEB-2056", "Lunar New Years Day");

		lh.addStaticHoliday ("16-FEB-2056", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("17-FEB-2056", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("31-MAR-2056", "Good Friday");

		lh.addStaticHoliday ("03-APR-2056", "Easter Monday");

		lh.addStaticHoliday ("04-APR-2056", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2056", "Labour Day");

		lh.addStaticHoliday ("22-MAY-2056", "Buddhas Birthday");

		lh.addStaticHoliday ("25-SEP-2056", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("02-OCT-2056", "Chinese National Day Observed");

		lh.addStaticHoliday ("17-OCT-2056", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2056", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2056", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2057", "First Weekday in January");

		lh.addStaticHoliday ("05-FEB-2057", "Lunar New Years Day Observed");

		lh.addStaticHoliday ("06-FEB-2057", "Second Day of Lunar New Year Observed");

		lh.addStaticHoliday ("04-APR-2057", "Ching Ming Festival");

		lh.addStaticHoliday ("20-APR-2057", "Good Friday");

		lh.addStaticHoliday ("23-APR-2057", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2057", "Labour Day");

		lh.addStaticHoliday ("11-MAY-2057", "Buddhas Birthday");

		lh.addStaticHoliday ("06-JUN-2057", "Tuen Ng Festival");

		lh.addStaticHoliday ("02-JUL-2057", "SAR Establishment Day Observed");

		lh.addStaticHoliday ("14-SEP-2057", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2057", "Chinese National Day");

		lh.addStaticHoliday ("25-DEC-2057", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2057", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2058", "First Weekday in January");

		lh.addStaticHoliday ("24-JAN-2058", "Lunar New Years Day");

		lh.addStaticHoliday ("25-JAN-2058", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("04-APR-2058", "Ching Ming Festival");

		lh.addStaticHoliday ("12-APR-2058", "Good Friday");

		lh.addStaticHoliday ("15-APR-2058", "Easter Monday");

		lh.addStaticHoliday ("30-APR-2058", "Buddhas Birthday");

		lh.addStaticHoliday ("01-MAY-2058", "Labour Day");

		lh.addStaticHoliday ("25-JUN-2058", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2058", "SAR Establishment Day");

		lh.addStaticHoliday ("01-OCT-2058", "Chinese National Day");

		lh.addStaticHoliday ("03-OCT-2058", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("25-OCT-2058", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2058", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2058", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2059", "First Weekday in January");

		lh.addStaticHoliday ("12-FEB-2059", "Lunar New Years Day");

		lh.addStaticHoliday ("13-FEB-2059", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("14-FEB-2059", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("28-MAR-2059", "Good Friday");

		lh.addStaticHoliday ("31-MAR-2059", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2059", "Labour Day");

		lh.addStaticHoliday ("19-MAY-2059", "Buddhas Birthday");

		lh.addStaticHoliday ("01-JUL-2059", "SAR Establishment Day");

		lh.addStaticHoliday ("22-SEP-2059", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2059", "Chinese National Day");

		lh.addStaticHoliday ("14-OCT-2059", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2059", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2059", "Christmas Holiday");

		lh.addStaticHoliday ("01-JAN-2060", "First Weekday in January");

		lh.addStaticHoliday ("02-FEB-2060", "Lunar New Years Day");

		lh.addStaticHoliday ("03-FEB-2060", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("04-FEB-2060", "Third Day of Lunar New Year");

		lh.addStaticHoliday ("05-APR-2060", "Ching Ming Festival Observed");

		lh.addStaticHoliday ("16-APR-2060", "Good Friday");

		lh.addStaticHoliday ("19-APR-2060", "Easter Monday");

		lh.addStaticHoliday ("07-MAY-2060", "Buddhas Birthday");

		lh.addStaticHoliday ("03-JUN-2060", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2060", "SAR Establishment Day");

		lh.addStaticHoliday ("10-SEP-2060", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("01-OCT-2060", "Chinese National Day");

		lh.addStaticHoliday ("27-DEC-2060", "Christmas Holiday");

		lh.addStaticHoliday ("20-JAN-2061", "Lunar New Years Eve");

		lh.addStaticHoliday ("21-JAN-2061", "Lunar New Years Day");

		lh.addStaticHoliday ("04-APR-2061", "Ching Ming Festival");

		lh.addStaticHoliday ("08-APR-2061", "Good Friday");

		lh.addStaticHoliday ("11-APR-2061", "Easter Monday");

		lh.addStaticHoliday ("02-MAY-2061", "Labour Day Observed");

		lh.addStaticHoliday ("26-MAY-2061", "Buddhas Birthday");

		lh.addStaticHoliday ("22-JUN-2061", "Tuen Ng Festival");

		lh.addStaticHoliday ("01-JUL-2061", "SAR Establishment Day");

		lh.addStaticHoliday ("29-SEP-2061", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("21-OCT-2061", "Chung Yeung Festival");

		lh.addStaticHoliday ("26-DEC-2061", "Christmas Holiday");

		lh.addStaticHoliday ("27-DEC-2061", "Christmas Day Observed");

		lh.addStaticHoliday ("02-JAN-2062", "First Weekday in January");

		lh.addStaticHoliday ("09-FEB-2062", "Lunar New Years Day");

		lh.addStaticHoliday ("10-FEB-2062", "Second Day of Lunar New Year");

		lh.addStaticHoliday ("24-MAR-2062", "Good Friday");

		lh.addStaticHoliday ("27-MAR-2062", "Easter Monday");

		lh.addStaticHoliday ("04-APR-2062", "Ching Ming Festival");

		lh.addStaticHoliday ("01-MAY-2062", "Labour Day");

		lh.addStaticHoliday ("16-MAY-2062", "Buddhas Birthday");

		lh.addStaticHoliday ("12-JUN-2062", "Tuen Ng Festival Observed");

		lh.addStaticHoliday ("18-SEP-2062", "Day following Mid-Autumn Festival");

		lh.addStaticHoliday ("02-OCT-2062", "Chinese National Day Observed");

		lh.addStaticHoliday ("11-OCT-2062", "Chung Yeung Festival");

		lh.addStaticHoliday ("25-DEC-2062", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2062", "Christmas Holiday");

		lh.addStandardWeekend();

		return lh;
	}
}
