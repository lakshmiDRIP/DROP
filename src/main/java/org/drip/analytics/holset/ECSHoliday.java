
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

public class ECSHoliday implements org.drip.analytics.holset.LocationHoliday {
	public ECSHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "ECS";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("23-FEB-1998", "Carnival Monday");

		lh.addStaticHoliday ("24-FEB-1998", "Carnival Tuesday");

		lh.addStaticHoliday ("10-APR-1998", "Good Friday");

		lh.addStaticHoliday ("01-MAY-1998", "Labor Day");

		lh.addStaticHoliday ("10-AUG-1998", "Independence Day");

		lh.addStaticHoliday ("09-OCT-1998", "Guayaquils Independence Day");

		lh.addStaticHoliday ("02-NOV-1998", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-1998", "Cuencas Independence Day");

		lh.addStaticHoliday ("25-DEC-1998", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("15-FEB-1999", "Carnival Monday");

		lh.addStaticHoliday ("16-FEB-1999", "Carnival Tuesday");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("24-MAY-1999", "Pichincha War Day");

		lh.addStaticHoliday ("10-AUG-1999", "Independence Day");

		lh.addStaticHoliday ("02-NOV-1999", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-1999", "Cuencas Independence Day");

		lh.addStaticHoliday ("06-DEC-1999", "Quitos Independence Day");

		lh.addStaticHoliday ("06-MAR-2000", "Carnival Monday");

		lh.addStaticHoliday ("07-MAR-2000", "Carnival Tuesday");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2000", "Labor Day");

		lh.addStaticHoliday ("24-MAY-2000", "Pichincha War Day");

		lh.addStaticHoliday ("10-AUG-2000", "Independence Day");

		lh.addStaticHoliday ("09-OCT-2000", "Guayaquils Independence Day");

		lh.addStaticHoliday ("02-NOV-2000", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-2000", "Cuencas Independence Day");

		lh.addStaticHoliday ("06-DEC-2000", "Quitos Independence Day");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("26-FEB-2001", "Carnival Monday");

		lh.addStaticHoliday ("27-FEB-2001", "Carnival Tuesday");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2001", "Labor Day");

		lh.addStaticHoliday ("24-MAY-2001", "Pichincha War Day");

		lh.addStaticHoliday ("10-AUG-2001", "Independence Day");

		lh.addStaticHoliday ("09-OCT-2001", "Guayaquils Independence Day");

		lh.addStaticHoliday ("02-NOV-2001", "Memorial Day");

		lh.addStaticHoliday ("06-DEC-2001", "Quitos Independence Day");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2002", "Carnival Monday");

		lh.addStaticHoliday ("12-FEB-2002", "Carnival Tuesday");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2002", "Labor Day");

		lh.addStaticHoliday ("24-MAY-2002", "Pichincha War Day");

		lh.addStaticHoliday ("09-OCT-2002", "Guayaquils Independence Day");

		lh.addStaticHoliday ("06-DEC-2002", "Quitos Independence Day");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("03-MAR-2003", "Carnival Monday");

		lh.addStaticHoliday ("04-MAR-2003", "Carnival Tuesday");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2003", "Labor Day");

		lh.addStaticHoliday ("09-OCT-2003", "Guayaquils Independence Day");

		lh.addStaticHoliday ("03-NOV-2003", "Cuencas Independence Day");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("23-FEB-2004", "Carnival Monday");

		lh.addStaticHoliday ("24-FEB-2004", "Carnival Tuesday");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2004", "Pichincha War Day");

		lh.addStaticHoliday ("10-AUG-2004", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2004", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-2004", "Cuencas Independence Day");

		lh.addStaticHoliday ("06-DEC-2004", "Quitos Independence Day");

		lh.addStaticHoliday ("07-FEB-2005", "Carnival Monday");

		lh.addStaticHoliday ("08-FEB-2005", "Carnival Tuesday");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2005", "Pichincha War Day");

		lh.addStaticHoliday ("10-AUG-2005", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2005", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-2005", "Cuencas Independence Day");

		lh.addStaticHoliday ("06-DEC-2005", "Quitos Independence Day");

		lh.addStaticHoliday ("27-FEB-2006", "Carnival Monday");

		lh.addStaticHoliday ("28-FEB-2006", "Carnival Tuesday");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2006", "Labor Day");

		lh.addStaticHoliday ("24-MAY-2006", "Pichincha War Day");

		lh.addStaticHoliday ("10-AUG-2006", "Independence Day");

		lh.addStaticHoliday ("09-OCT-2006", "Guayaquils Independence Day");

		lh.addStaticHoliday ("02-NOV-2006", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-2006", "Cuencas Independence Day");

		lh.addStaticHoliday ("06-DEC-2006", "Quitos Independence Day");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("19-FEB-2007", "Carnival Monday");

		lh.addStaticHoliday ("20-FEB-2007", "Carnival Tuesday");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2007", "Labor Day");

		lh.addStaticHoliday ("24-MAY-2007", "Pichincha War Day");

		lh.addStaticHoliday ("10-AUG-2007", "Independence Day");

		lh.addStaticHoliday ("09-OCT-2007", "Guayaquils Independence Day");

		lh.addStaticHoliday ("02-NOV-2007", "Memorial Day");

		lh.addStaticHoliday ("06-DEC-2007", "Quitos Independence Day");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("04-FEB-2008", "Carnival Monday");

		lh.addStaticHoliday ("05-FEB-2008", "Carnival Tuesday");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2008", "Labor Day");

		lh.addStaticHoliday ("09-OCT-2008", "Guayaquils Independence Day");

		lh.addStaticHoliday ("03-NOV-2008", "Cuencas Independence Day");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("23-FEB-2009", "Carnival Monday");

		lh.addStaticHoliday ("24-FEB-2009", "Carnival Tuesday");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2009", "Labor Day");

		lh.addStaticHoliday ("10-AUG-2009", "Independence Day");

		lh.addStaticHoliday ("09-OCT-2009", "Guayaquils Independence Day");

		lh.addStaticHoliday ("02-NOV-2009", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-2009", "Cuencas Independence Day");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("15-FEB-2010", "Carnival Monday");

		lh.addStaticHoliday ("16-FEB-2010", "Carnival Tuesday");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2010", "Pichincha War Day");

		lh.addStaticHoliday ("10-AUG-2010", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2010", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-2010", "Cuencas Independence Day");

		lh.addStaticHoliday ("06-DEC-2010", "Quitos Independence Day");

		lh.addStaticHoliday ("07-MAR-2011", "Carnival Monday");

		lh.addStaticHoliday ("08-MAR-2011", "Carnival Tuesday");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2011", "Pichincha War Day");

		lh.addStaticHoliday ("10-AUG-2011", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2011", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-2011", "Cuencas Independence Day");

		lh.addStaticHoliday ("06-DEC-2011", "Quitos Independence Day");

		lh.addStaticHoliday ("20-FEB-2012", "Carnival Monday");

		lh.addStaticHoliday ("21-FEB-2012", "Carnival Tuesday");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2012", "Labor Day");

		lh.addStaticHoliday ("24-MAY-2012", "Pichincha War Day");

		lh.addStaticHoliday ("10-AUG-2012", "Independence Day");

		lh.addStaticHoliday ("09-OCT-2012", "Guayaquils Independence Day");

		lh.addStaticHoliday ("02-NOV-2012", "Memorial Day");

		lh.addStaticHoliday ("06-DEC-2012", "Quitos Independence Day");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("11-FEB-2013", "Carnival Monday");

		lh.addStaticHoliday ("12-FEB-2013", "Carnival Tuesday");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2013", "Labor Day");

		lh.addStaticHoliday ("24-MAY-2013", "Pichincha War Day");

		lh.addStaticHoliday ("09-OCT-2013", "Guayaquils Independence Day");

		lh.addStaticHoliday ("06-DEC-2013", "Quitos Independence Day");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("03-MAR-2014", "Carnival Monday");

		lh.addStaticHoliday ("04-MAR-2014", "Carnival Tuesday");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2014", "Labor Day");

		lh.addStaticHoliday ("09-OCT-2014", "Guayaquils Independence Day");

		lh.addStaticHoliday ("03-NOV-2014", "Cuencas Independence Day");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("16-FEB-2015", "Carnival Monday");

		lh.addStaticHoliday ("17-FEB-2015", "Carnival Tuesday");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2015", "Labor Day");

		lh.addStaticHoliday ("10-AUG-2015", "Independence Day");

		lh.addStaticHoliday ("09-OCT-2015", "Guayaquils Independence Day");

		lh.addStaticHoliday ("02-NOV-2015", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-2015", "Cuencas Independence Day");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2016", "Carnival Monday");

		lh.addStaticHoliday ("09-FEB-2016", "Carnival Tuesday");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2016", "Pichincha War Day");

		lh.addStaticHoliday ("10-AUG-2016", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2016", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-2016", "Cuencas Independence Day");

		lh.addStaticHoliday ("06-DEC-2016", "Quitos Independence Day");

		lh.addStaticHoliday ("27-FEB-2017", "Carnival Monday");

		lh.addStaticHoliday ("28-FEB-2017", "Carnival Tuesday");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2017", "Labor Day");

		lh.addStaticHoliday ("24-MAY-2017", "Pichincha War Day");

		lh.addStaticHoliday ("10-AUG-2017", "Independence Day");

		lh.addStaticHoliday ("09-OCT-2017", "Guayaquils Independence Day");

		lh.addStaticHoliday ("02-NOV-2017", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-2017", "Cuencas Independence Day");

		lh.addStaticHoliday ("06-DEC-2017", "Quitos Independence Day");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("12-FEB-2018", "Carnival Monday");

		lh.addStaticHoliday ("13-FEB-2018", "Carnival Tuesday");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2018", "Labor Day");

		lh.addStaticHoliday ("24-MAY-2018", "Pichincha War Day");

		lh.addStaticHoliday ("10-AUG-2018", "Independence Day");

		lh.addStaticHoliday ("09-OCT-2018", "Guayaquils Independence Day");

		lh.addStaticHoliday ("02-NOV-2018", "Memorial Day");

		lh.addStaticHoliday ("06-DEC-2018", "Quitos Independence Day");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("04-MAR-2019", "Carnival Monday");

		lh.addStaticHoliday ("05-MAR-2019", "Carnival Tuesday");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2019", "Labor Day");

		lh.addStaticHoliday ("24-MAY-2019", "Pichincha War Day");

		lh.addStaticHoliday ("09-OCT-2019", "Guayaquils Independence Day");

		lh.addStaticHoliday ("06-DEC-2019", "Quitos Independence Day");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("24-FEB-2020", "Carnival Monday");

		lh.addStaticHoliday ("25-FEB-2020", "Carnival Tuesday");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2020", "Labor Day");

		lh.addStaticHoliday ("10-AUG-2020", "Independence Day");

		lh.addStaticHoliday ("09-OCT-2020", "Guayaquils Independence Day");

		lh.addStaticHoliday ("02-NOV-2020", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-2020", "Cuencas Independence Day");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("15-FEB-2021", "Carnival Monday");

		lh.addStaticHoliday ("16-FEB-2021", "Carnival Tuesday");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2021", "Pichincha War Day");

		lh.addStaticHoliday ("10-AUG-2021", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2021", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-2021", "Cuencas Independence Day");

		lh.addStaticHoliday ("06-DEC-2021", "Quitos Independence Day");

		lh.addStaticHoliday ("28-FEB-2022", "Carnival Monday");

		lh.addStaticHoliday ("01-MAR-2022", "Carnival Tuesday");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2022", "Pichincha War Day");

		lh.addStaticHoliday ("10-AUG-2022", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2022", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-2022", "Cuencas Independence Day");

		lh.addStaticHoliday ("06-DEC-2022", "Quitos Independence Day");

		lh.addStaticHoliday ("20-FEB-2023", "Carnival Monday");

		lh.addStaticHoliday ("21-FEB-2023", "Carnival Tuesday");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2023", "Labor Day");

		lh.addStaticHoliday ("24-MAY-2023", "Pichincha War Day");

		lh.addStaticHoliday ("10-AUG-2023", "Independence Day");

		lh.addStaticHoliday ("09-OCT-2023", "Guayaquils Independence Day");

		lh.addStaticHoliday ("02-NOV-2023", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-2023", "Cuencas Independence Day");

		lh.addStaticHoliday ("06-DEC-2023", "Quitos Independence Day");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("12-FEB-2024", "Carnival Monday");

		lh.addStaticHoliday ("13-FEB-2024", "Carnival Tuesday");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2024", "Labor Day");

		lh.addStaticHoliday ("24-MAY-2024", "Pichincha War Day");

		lh.addStaticHoliday ("09-OCT-2024", "Guayaquils Independence Day");

		lh.addStaticHoliday ("06-DEC-2024", "Quitos Independence Day");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("03-MAR-2025", "Carnival Monday");

		lh.addStaticHoliday ("04-MAR-2025", "Carnival Tuesday");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2025", "Labor Day");

		lh.addStaticHoliday ("09-OCT-2025", "Guayaquils Independence Day");

		lh.addStaticHoliday ("03-NOV-2025", "Cuencas Independence Day");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("16-FEB-2026", "Carnival Monday");

		lh.addStaticHoliday ("17-FEB-2026", "Carnival Tuesday");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2026", "Labor Day");

		lh.addStaticHoliday ("10-AUG-2026", "Independence Day");

		lh.addStaticHoliday ("09-OCT-2026", "Guayaquils Independence Day");

		lh.addStaticHoliday ("02-NOV-2026", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-2026", "Cuencas Independence Day");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("08-FEB-2027", "Carnival Monday");

		lh.addStaticHoliday ("09-FEB-2027", "Carnival Tuesday");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("24-MAY-2027", "Pichincha War Day");

		lh.addStaticHoliday ("10-AUG-2027", "Independence Day");

		lh.addStaticHoliday ("02-NOV-2027", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-2027", "Cuencas Independence Day");

		lh.addStaticHoliday ("06-DEC-2027", "Quitos Independence Day");

		lh.addStaticHoliday ("28-FEB-2028", "Carnival Monday");

		lh.addStaticHoliday ("29-FEB-2028", "Carnival Tuesday");

		lh.addStaticHoliday ("14-APR-2028", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2028", "Labor Day");

		lh.addStaticHoliday ("24-MAY-2028", "Pichincha War Day");

		lh.addStaticHoliday ("10-AUG-2028", "Independence Day");

		lh.addStaticHoliday ("09-OCT-2028", "Guayaquils Independence Day");

		lh.addStaticHoliday ("02-NOV-2028", "Memorial Day");

		lh.addStaticHoliday ("03-NOV-2028", "Cuencas Independence Day");

		lh.addStaticHoliday ("06-DEC-2028", "Quitos Independence Day");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStandardWeekend();

		return lh;
	}
}
