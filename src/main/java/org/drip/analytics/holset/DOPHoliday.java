
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

public class DOPHoliday implements org.drip.analytics.holset.LocationHoliday {
	public DOPHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "DOP";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("06-JAN-1998", "Epiphany");

		lh.addStaticHoliday ("21-JAN-1998", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("26-JAN-1998", "Duartes Day");

		lh.addStaticHoliday ("27-FEB-1998", "Independence Day");

		lh.addStaticHoliday ("10-APR-1998", "Good Friday");

		lh.addStaticHoliday ("01-MAY-1998", "Labor Day");

		lh.addStaticHoliday ("11-JUN-1998", "Corpus Christi");

		lh.addStaticHoliday ("24-SEP-1998", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-NOV-1998", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-1998", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("06-JAN-1999", "Epiphany");

		lh.addStaticHoliday ("21-JAN-1999", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("26-JAN-1999", "Duartes Day");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("03-JUN-1999", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-1999", "Restoration Day");

		lh.addStaticHoliday ("24-SEP-1999", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-JAN-2000", "Epiphany");

		lh.addStaticHoliday ("21-JAN-2000", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("26-JAN-2000", "Duartes Day");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2000", "Labor Day");

		lh.addStaticHoliday ("16-MAY-2000", "Election Day");

		lh.addStaticHoliday ("22-JUN-2000", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2000", "Restoration Day");

		lh.addStaticHoliday ("06-NOV-2000", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2001", "Duartes Day");

		lh.addStaticHoliday ("27-FEB-2001", "Independence Day");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2001", "Labor Day");

		lh.addStaticHoliday ("14-JUN-2001", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2001", "Restoration Day");

		lh.addStaticHoliday ("24-SEP-2001", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-NOV-2001", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2002", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("27-FEB-2002", "Independence Day");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2002", "Labor Day");

		lh.addStaticHoliday ("16-MAY-2002", "Election Day");

		lh.addStaticHoliday ("30-MAY-2002", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2002", "Restoration Day");

		lh.addStaticHoliday ("24-SEP-2002", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-NOV-2002", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2003", "Epiphany");

		lh.addStaticHoliday ("21-JAN-2003", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("27-FEB-2003", "Independence Day");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2003", "Labor Day");

		lh.addStaticHoliday ("19-JUN-2003", "Corpus Christi");

		lh.addStaticHoliday ("24-SEP-2003", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-NOV-2003", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2004", "Epiphany");

		lh.addStaticHoliday ("21-JAN-2004", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("26-JAN-2004", "Duartes Day");

		lh.addStaticHoliday ("27-FEB-2004", "Independence Day");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("10-JUN-2004", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2004", "Restoration Day");

		lh.addStaticHoliday ("24-SEP-2004", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-JAN-2005", "Epiphany");

		lh.addStaticHoliday ("21-JAN-2005", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("26-JAN-2005", "Duartes Day");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("26-MAY-2005", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2005", "Restoration Day");

		lh.addStaticHoliday ("06-JAN-2006", "Epiphany");

		lh.addStaticHoliday ("26-JAN-2006", "Duartes Day");

		lh.addStaticHoliday ("27-FEB-2006", "Independence Day");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2006", "Labor Day");

		lh.addStaticHoliday ("16-MAY-2006", "Election Day");

		lh.addStaticHoliday ("15-JUN-2006", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2006", "Restoration Day");

		lh.addStaticHoliday ("06-NOV-2006", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2007", "Duartes Day");

		lh.addStaticHoliday ("27-FEB-2007", "Independence Day");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2007", "Labor Day");

		lh.addStaticHoliday ("07-JUN-2007", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2007", "Restoration Day");

		lh.addStaticHoliday ("24-SEP-2007", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-NOV-2007", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2008", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("27-FEB-2008", "Independence Day");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2008", "Labor Day");

		lh.addStaticHoliday ("16-MAY-2008", "Election Day");

		lh.addStaticHoliday ("22-MAY-2008", "Corpus Christi");

		lh.addStaticHoliday ("24-SEP-2008", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-NOV-2008", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2009", "Epiphany");

		lh.addStaticHoliday ("21-JAN-2009", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("26-JAN-2009", "Duartes Day");

		lh.addStaticHoliday ("27-FEB-2009", "Independence Day");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2009", "Labor Day");

		lh.addStaticHoliday ("11-JUN-2009", "Corpus Christi");

		lh.addStaticHoliday ("24-SEP-2009", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-NOV-2009", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2010", "Epiphany");

		lh.addStaticHoliday ("21-JAN-2010", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("26-JAN-2010", "Duartes Day");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("03-JUN-2010", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2010", "Restoration Day");

		lh.addStaticHoliday ("24-SEP-2010", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-JAN-2011", "Epiphany");

		lh.addStaticHoliday ("21-JAN-2011", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("26-JAN-2011", "Duartes Day");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("23-JUN-2011", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2011", "Restoration Day");

		lh.addStaticHoliday ("06-JAN-2012", "Epiphany");

		lh.addStaticHoliday ("26-JAN-2012", "Duartes Day");

		lh.addStaticHoliday ("27-FEB-2012", "Independence Day");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2012", "Labor Day");

		lh.addStaticHoliday ("16-MAY-2012", "Election Day");

		lh.addStaticHoliday ("07-JUN-2012", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2012", "Restoration Day");

		lh.addStaticHoliday ("24-SEP-2012", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-NOV-2012", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2013", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("27-FEB-2013", "Independence Day");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2013", "Labor Day");

		lh.addStaticHoliday ("30-MAY-2013", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2013", "Restoration Day");

		lh.addStaticHoliday ("24-SEP-2013", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-NOV-2013", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2014", "Epiphany");

		lh.addStaticHoliday ("21-JAN-2014", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("27-FEB-2014", "Independence Day");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2014", "Labor Day");

		lh.addStaticHoliday ("16-MAY-2014", "Election Day");

		lh.addStaticHoliday ("19-JUN-2014", "Corpus Christi");

		lh.addStaticHoliday ("24-SEP-2014", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-NOV-2014", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2015", "Epiphany");

		lh.addStaticHoliday ("21-JAN-2015", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("26-JAN-2015", "Duartes Day");

		lh.addStaticHoliday ("27-FEB-2015", "Independence Day");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2015", "Labor Day");

		lh.addStaticHoliday ("04-JUN-2015", "Corpus Christi");

		lh.addStaticHoliday ("24-SEP-2015", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-NOV-2015", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2016", "Epiphany");

		lh.addStaticHoliday ("21-JAN-2016", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("26-JAN-2016", "Duartes Day");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("16-MAY-2016", "Election Day");

		lh.addStaticHoliday ("26-MAY-2016", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2016", "Restoration Day");

		lh.addStaticHoliday ("06-JAN-2017", "Epiphany");

		lh.addStaticHoliday ("26-JAN-2017", "Duartes Day");

		lh.addStaticHoliday ("27-FEB-2017", "Independence Day");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2017", "Labor Day");

		lh.addStaticHoliday ("15-JUN-2017", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2017", "Restoration Day");

		lh.addStaticHoliday ("06-NOV-2017", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2018", "Duartes Day");

		lh.addStaticHoliday ("27-FEB-2018", "Independence Day");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2018", "Labor Day");

		lh.addStaticHoliday ("16-MAY-2018", "Election Day");

		lh.addStaticHoliday ("31-MAY-2018", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2018", "Restoration Day");

		lh.addStaticHoliday ("24-SEP-2018", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-NOV-2018", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("21-JAN-2019", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("27-FEB-2019", "Independence Day");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2019", "Labor Day");

		lh.addStaticHoliday ("20-JUN-2019", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2019", "Restoration Day");

		lh.addStaticHoliday ("24-SEP-2019", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-NOV-2019", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2020", "Epiphany");

		lh.addStaticHoliday ("21-JAN-2020", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("27-FEB-2020", "Independence Day");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2020", "Labor Day");

		lh.addStaticHoliday ("11-JUN-2020", "Corpus Christi");

		lh.addStaticHoliday ("24-SEP-2020", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-NOV-2020", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2021", "Epiphany");

		lh.addStaticHoliday ("21-JAN-2021", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("26-JAN-2021", "Duartes Day");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("03-JUN-2021", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2021", "Restoration Day");

		lh.addStaticHoliday ("24-SEP-2021", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-JAN-2022", "Epiphany");

		lh.addStaticHoliday ("21-JAN-2022", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("26-JAN-2022", "Duartes Day");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("16-MAY-2022", "Election Day");

		lh.addStaticHoliday ("16-JUN-2022", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2022", "Restoration Day");

		lh.addStaticHoliday ("06-JAN-2023", "Epiphany");

		lh.addStaticHoliday ("26-JAN-2023", "Duartes Day");

		lh.addStaticHoliday ("27-FEB-2023", "Independence Day");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2023", "Labor Day");

		lh.addStaticHoliday ("08-JUN-2023", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2023", "Restoration Day");

		lh.addStaticHoliday ("06-NOV-2023", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("26-JAN-2024", "Duartes Day");

		lh.addStaticHoliday ("27-FEB-2024", "Independence Day");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2024", "Labor Day");

		lh.addStaticHoliday ("16-MAY-2024", "Election Day");

		lh.addStaticHoliday ("30-MAY-2024", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2024", "Restoration Day");

		lh.addStaticHoliday ("24-SEP-2024", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-NOV-2024", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2025", "Epiphany");

		lh.addStaticHoliday ("21-JAN-2025", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("27-FEB-2025", "Independence Day");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2025", "Labor Day");

		lh.addStaticHoliday ("19-JUN-2025", "Corpus Christi");

		lh.addStaticHoliday ("24-SEP-2025", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-NOV-2025", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2026", "Epiphany");

		lh.addStaticHoliday ("21-JAN-2026", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("26-JAN-2026", "Duartes Day");

		lh.addStaticHoliday ("27-FEB-2026", "Independence Day");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2026", "Labor Day");

		lh.addStaticHoliday ("04-JUN-2026", "Corpus Christi");

		lh.addStaticHoliday ("24-SEP-2026", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-NOV-2026", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("06-JAN-2027", "Epiphany");

		lh.addStaticHoliday ("21-JAN-2027", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("26-JAN-2027", "Duartes Day");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("27-MAY-2027", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2027", "Restoration Day");

		lh.addStaticHoliday ("24-SEP-2027", "Our Lady of Mercedes Day");

		lh.addStaticHoliday ("06-JAN-2028", "Epiphany");

		lh.addStaticHoliday ("21-JAN-2028", "Our Lady of Altagracia Day");

		lh.addStaticHoliday ("26-JAN-2028", "Duartes Day");

		lh.addStaticHoliday ("14-APR-2028", "Good Friday");

		lh.addStaticHoliday ("01-MAY-2028", "Labor Day");

		lh.addStaticHoliday ("16-MAY-2028", "Election Day");

		lh.addStaticHoliday ("15-JUN-2028", "Corpus Christi");

		lh.addStaticHoliday ("16-AUG-2028", "Restoration Day");

		lh.addStaticHoliday ("06-NOV-2028", "Constitution Day");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStandardWeekend();

		return lh;
	}
}
