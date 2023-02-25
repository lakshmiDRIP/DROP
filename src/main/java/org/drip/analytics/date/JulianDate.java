
package org.drip.analytics.date;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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

/**
 * <i>JulianDate</i> provides a comprehensive representation of Julian date and date manipulation
 * functionality. It exports the following functionality:
 *
 *	<br><br>
 *  <ul>
 * 		<li>
 * 			Explicit date construction, as well as date construction from several input string formats/today
 * 		</li>
 * 		<li>
 *  		Date Addition/Adjustment/Elapsed/Difference, add/subtract days/weeks/months/years and tenor codes
 * 		</li>
 * 		<li>
 *  		Leap Year Functionality (number of leap days in the given interval, is the given year a leap year
 *  			etc.)
 * 		</li>
 *  	<li>
 *  		Generate the subsequent IMM date (CME IMM date, CDS/Credit ISDA IMM date etc)
 *  	</li>
 *  	<li>
 *  		Year/Month/Day in numbers/characters
 *  	</li>
 *  	<li>
 *  		Days Elapsed/Remaining, is EOM
 *  	</li>
 *  	<li>
 *  		Comparison with the Other, equals/hash-code/comparator
 *  	</li>
 *  	<li>
 *  		Export the date to a variety of date formats (Oracle, Julian, Bloomberg)
 *  	</li>
 *  	<li>
 *  		Serialization/De-serialization to and from Byte Arrays
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *
 * The References are:
 * 	<ul>
 * 		<li>
 * 			Fliegel, H. F., and T. C. van Flandern (1968): A Machine Algorithm for Processing Calendar Dates
 * 				<i>Communications of the ACM</i> <b>11</b> 657
 * 		</li>
 * 		<li>
 * 			Fenton, D. (2001): Julian to Calendar Date Conversion
 * 				http://mathforum.org/library/drmath/view/51907.html
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Date, Cash Flow, and Cash Flow Period Measure Generation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/date/README.md">Date and Time Creation, Manipulation, and Usage</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class JulianDate implements java.lang.Comparable<JulianDate> {
	private int _iJulian = java.lang.Integer.MIN_VALUE;

	/**
	 * Create JulianDate from an Integer Julian Date Instance
	 * 
	 * @param iJulian Julian Date Integer Instance
	 */

	public JulianDate (
		final int iJulian)
	{
		_iJulian = iJulian;
	}

	/**
	 * Return the Integer Julian Date
	 * 
	 * @return The Integer Julian Date
	 */

	public int julian()
	{
		return _iJulian;
	}

	/**
	 * Add the given Number of Days and return a JulianDate Instance
	 * 
	 * @param iDays Number of Days to be added
	 * 
	 * @return The new JulianDate
	 */

	public JulianDate addDays (
		final int iDays)
	{
		try {
			return new JulianDate (_iJulian + iDays);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Subtract the given Number of Days and return the JulianDate Instance
	 * 
	 * @param iDays Number of days to be subtracted
	 * 
	 * @return The JulianDate Instance
	 */

	public JulianDate subtractDays (
		final int iDays)
	{
		try {
			return new JulianDate (_iJulian - iDays);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Add the given Number of Business Days and return a new JulianDate Instance
	 * 
	 * @param iDays Number of Days to be subtracted
	 * 
	 * @param strCalendarSet String representing the Calendar Set containing the Business Days
	 * 
	 * @return The new JulianDate Instance
	 */

	public JulianDate addBusDays (
		final int iDays,
		final java.lang.String strCalendarSet)
	{
		int iNumDaysToAdd = iDays;
		int iAdjusted = _iJulian;

		try {
			while (0 < iNumDaysToAdd--) {
				++iAdjusted;

				while (org.drip.analytics.daycount.Convention.IsHoliday (iAdjusted, strCalendarSet))
					++iAdjusted;
			}

			while (org.drip.analytics.daycount.Convention.IsHoliday (iAdjusted, strCalendarSet)) ++iAdjusted;

			return new JulianDate (iAdjusted);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Subtract the given Number of Business Days and return a new JulianDate Instance
	 * 
	 * @param iDays Number of Days to be subtracted
	 * 
	 * @param strCalendarSet String representing the Calendar Set containing the Business Days
	 * 
	 * @return The new JulianDate Instance
	 */

	public JulianDate subtractBusDays (
		final int iDays,
		final java.lang.String strCalendarSet)
	{
		int iNumDaysToAdd = iDays;
		int iDateAdjusted = _iJulian;

		try {
			while (0 < iNumDaysToAdd--) {
				--iDateAdjusted;

				while (org.drip.analytics.daycount.Convention.IsHoliday (iDateAdjusted, strCalendarSet))
					--iDateAdjusted;
			}

			while (org.drip.analytics.daycount.Convention.IsHoliday (iDateAdjusted, strCalendarSet))
				--iDateAdjusted;

			return new JulianDate (iDateAdjusted);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Add the given Number of Years and return a new JulianDate Instance
	 * 
	 * @param iNumYears Number of Years to be added
	 *  
	 * @return The New JulianDate Instance
	 */

	public JulianDate addYears (
		final int iNumYears)
	{
		int iP = _iJulian + 68569;
		int iQ = 4 * iP / 146097;
		int iR = iP - (146097 * iQ + 3) / 4;
		int iS = 4000 * (iR + 1) / 1461001;
		int iT = iR - (1461 * iS / 4) + 31;
		int iU = 80 * iT / 2447;
		int iV = iU / 11;
		int iY = 100 * (iQ - 49) + iS + iV;
		int iM = iU + 2 - 12 * iV;
		int iD = iT - (2447 * iU / 80);

		try {
			return org.drip.analytics.date.DateUtil.CreateFromYMD (iY + iNumYears, iM, iD);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Add the given Number of Months and return a New JulianDate Instance
	 * 
	 * @param iNumMonths Number of Months to be added
	 * 
	 * @return The new JulianDate Instance
	 */

	public JulianDate addMonths (
		final int iNumMonths)
	{
		int iP = _iJulian + 68569;
		int iQ = 4 * iP / 146097;
		int iR = iP - (146097 * iQ + 3) / 4;
		int iS = 4000 * (iR + 1) / 1461001;
		int iT = iR - (1461 * iS / 4) + 31;
		int iU = 80 * iT / 2447;
		int iV = iU / 11;
		int iYear = 100 * (iQ - 49) + iS + iV;
		int iMonth = iU + 2 - 12 * iV + iNumMonths;
		int iDate = iT - (2447 * iU / 80);

		while (12 < iMonth) {
			++iYear;
			iMonth -= 12;
		}

		while (0 >= iMonth) {
			--iYear;
			iMonth += 12;
		}

		try {
			int iDaysInMonth = org.drip.analytics.date.DateUtil.DaysInMonth (iMonth, iYear);

			while (iDate > iDaysInMonth)
				--iDate;

			return org.drip.analytics.date.DateUtil.CreateFromYMD (iYear, iMonth, iDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the First Rates Futures IMM Date from this JulianDate
	 * 
	 * @param iNumRollMonths Number of Months to Roll
	 * 
	 * @return The IMM JulianDate Instance
	 */

	public JulianDate nextRatesFuturesIMM (
		final int iNumRollMonths)
	{
		int iP = _iJulian + 68569;
		int iQ = 4 * iP / 146097;
		int iR = iP - (146097 * iQ + 3) / 4;
		int iS = 4000 * (iR + 1) / 1461001;
		int iT = iR - (1461 * iS / 4) + 31;
		int iU = 80 * iT / 2447;
		int iV = iU / 11;
		int iYear = 100 * (iQ - 49) + iS + iV;
		int iMonth = iU + 2 - 12 * iV;
		int iDate = iT - (2447 * iU / 80);

		if (15 <= iDate) {
			if (12 < ++iMonth) {
				++iYear;
				iMonth -= 12;
			}
		}

		while (0 != iMonth % iNumRollMonths) ++iMonth;

		try {
			return org.drip.analytics.date.DateUtil.CreateFromYMD (iYear, iMonth, 15);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the First Bond Futures IMM Date from this JulianDate according to the specified Calendar
	 * 
	 * @param iNumRollMonths Number of Months to Roll
	 * @param strCalendar Holiday Calendar
	 * 
	 * @return The IMM JulianDate Instance
	 */

	public JulianDate nextBondFuturesIMM (
		final int iNumRollMonths,
		final java.lang.String strCalendar)
	{
		int iP = _iJulian + 68569;
		int iQ = 4 * iP / 146097;
		int iR = iP - (146097 * iQ + 3) / 4;
		int iS = 4000 * (iR + 1) / 1461001;
		int iT = iR - (1461 * iS / 4) + 31;
		int iU = 80 * iT / 2447;
		int iV = iU / 11;
		int iYear = 100 * (iQ - 49) + iS + iV;
		int iMonth = iU + 2 - 12 * iV;

		while (0 != iMonth % iNumRollMonths) ++iMonth;

		try {
			return org.drip.analytics.date.DateUtil.CreateFromYMD (iYear, iMonth,
				org.drip.analytics.date.DateUtil.DaysInMonth (iMonth, iYear));

			/* return org.drip.analytics.date.DateUtil.CreateFromYMD (iYear, iMonth,
				org.drip.analytics.date.DateUtil.DaysInMonth (iMonth, iYear)).subtractBusDays (8,
					strCalendar); */
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the First Credit IMM roll date from this JulianDate
	 * 
	 * @param iNumRollMonths Number of Months to Roll
	 * 
	 * @return The IMM JulianDate Instance
	 */

	public JulianDate nextCreditIMM (
		final int iNumRollMonths)
	{
		int iP = _iJulian + 68569;
		int iQ = 4 * iP / 146097;
		int iR = iP - (146097 * iQ + 3) / 4;
		int iS = 4000 * (iR + 1) / 1461001;
		int iT = iR - (1461 * iS / 4) + 31;
		int iU = 80 * iT / 2447;
		int iV = iU / 11;
		int iYear = 100 * (iQ - 49) + iS + iV;
		int iMonth = iU + 2 - 12 * iV;
		int iDate = iT - (2447 * iU / 80);

		if (20 <= iDate) {
			if (12 < ++iMonth) {
				++iYear;
				iMonth -= 12;
			}
		}

		while (0 != iMonth % iNumRollMonths) ++iMonth;

		try {
			return org.drip.analytics.date.DateUtil.CreateFromYMD (iYear, iMonth, 20);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Add the tenor to the JulianDate to create a new date
	 * 
	 * @param strTenorIn String representing the Input Tenor to add
	 * 
	 * @return The new JulianDate
	 */

	public JulianDate addTenor (
		final java.lang.String strTenorIn)
	{
		if (null == strTenorIn || strTenorIn.isEmpty()) return null;

		java.lang.String strTenor = "ON".equalsIgnoreCase (strTenorIn) ? "1D" : strTenorIn;

		int iNumChar = strTenor.length();

		char chTenor = strTenor.charAt (iNumChar - 1);

		int iTimeUnit = -1;

		try {
			iTimeUnit = java.lang.Integer.parseInt (strTenor.substring (0, iNumChar - 1));
		} catch (java.lang.Exception e) {
			System.out.println ("Bad time unit " + iTimeUnit + " in tenor " + strTenor);

			return null;
		}

		if ('d' == chTenor || 'D' == chTenor) return addDays (iTimeUnit);

		if ('w' == chTenor || 'W' == chTenor) return addDays (iTimeUnit * 7);

		if ('l' == chTenor || 'L' == chTenor) return addDays (iTimeUnit * 28);

		if ('m' == chTenor || 'M' == chTenor) return addMonths (iTimeUnit);

		if ('y' == chTenor || 'Y' == chTenor) return addYears (iTimeUnit);

		System.out.println ("Unknown tenor format " + strTenor);

		return null;
	}

	/**
	 * Add the Tenor to the JulianDate and Adjust it to create a new Instance
	 * 
	 * @param strTenor The Tenor
	 * @param strCalendarSet The Holiday Calendar Set
	 * 
	 * @return The new JulianDate Instance
	 */

	public JulianDate addTenorAndAdjust (
		final java.lang.String strTenor,
		final java.lang.String strCalendarSet)
	{
		JulianDate dtNew = addTenor (strTenor);

		if (null == dtNew) return null;

		try {
			return new JulianDate (org.drip.analytics.daycount.Convention.RollDate (dtNew.julian(),
				org.drip.analytics.daycount.Convention.DATE_ROLL_FOLLOWING, strCalendarSet, 1));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Subtract the tenor to the JulianDate to create a new date
	 * 
	 * @param strTenorIn String representing the tenor to add
	 * 
	 * @return The new JulianDate
	 */

	public JulianDate subtractTenor (
		final java.lang.String strTenorIn)
	{
		if (null == strTenorIn || strTenorIn.isEmpty()) return null;

		java.lang.String strTenor = "ON".equalsIgnoreCase (strTenorIn) ? "1D" : strTenorIn;

		int iNumChar = strTenor.length();

		char chTenor = strTenor.charAt (iNumChar - 1);

		int iTimeUnit = -1;

		try {
			iTimeUnit = java.lang.Integer.parseInt (strTenor.substring (0, iNumChar - 1));
		} catch (java.lang.Exception e) {
			System.out.println ("Bad time unit " + iTimeUnit + " in tenor " + strTenor);

			return null;
		}

		if ('d' == chTenor || 'D' == chTenor) return addDays (-iTimeUnit);

		if ('w' == chTenor || 'W' == chTenor) return addDays (-iTimeUnit * 7);

		if ('l' == chTenor || 'L' == chTenor) return addDays (-iTimeUnit * 28);

		if ('m' == chTenor || 'M' == chTenor) return addMonths (-iTimeUnit);

		if ('y' == chTenor || 'Y' == chTenor) return addYears (-iTimeUnit);

		return null;
	}

	/**
	 * Subtract the tenor to the JulianDate to create a new business date
	 * 
	 * @param strTenor The Tenor
	 * @param strCalendarSet The Holiday Calendar Set
	 * 
	 * @return The new JulianDate
	 */

	public JulianDate subtractTenorAndAdjust (
		final java.lang.String strTenor,
		final java.lang.String strCalendarSet)
	{
		JulianDate dtNew = subtractTenor (strTenor);

		if (null == dtNew) return null;

		try {
			return new JulianDate (org.drip.analytics.daycount.Convention.RollDate (dtNew.julian(),
				org.drip.analytics.daycount.Convention.DATE_ROLL_FOLLOWING, strCalendarSet, 1));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Difference in Days between the Current and the Input Dates
	 * 
	 * @param dt Input Date
	 * 
	 * @return The Difference
	 * 
	 * @throws java.lang.Exception Thrown if Input Date is Invalid
	 */

	public int daysDiff (
		final JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("JulianDate::daysDiff => Invalid Input!");

		return _iJulian - dt.julian();
	}

	/**
	 * Return a Trigram Representation of the Date
	 * 
	 * @return String representing the Trigram Representation of Date
	 */

	public java.lang.String toOracleDate()
	{
		try {
			return DateUtil.Date (_iJulian) + "-" + org.drip.analytics.date.DateUtil.MonthTrigram
				(org.drip.analytics.date.DateUtil.Month (_iJulian)) + "-" +
					org.drip.analytics.date.DateUtil.Year (_iJulian);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Return a Representation of Date as YYYYMMDD
	 * 
	 * @param strDelimIn Field Delimiter
	 * 
	 * @return String of the YYYYMMDD Representation of Date
	 */

	public java.lang.String toYYYYMMDD (
		final java.lang.String strDelimIn)
	{
		java.lang.String strDelim = null == strDelimIn ? "" : strDelimIn;

		try {
			return org.drip.service.common.FormatUtil.FormatDouble (DateUtil.Year (_iJulian), 4, 0, 1.) +
				strDelim + org.drip.service.common.FormatUtil.FormatDouble
					(org.drip.analytics.date.DateUtil.Month (_iJulian), 2, 0, 1.) + strDelim +
						org.drip.service.common.FormatUtil.FormatDouble (DateUtil.Date (_iJulian), 2, 0, 1.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public boolean equals (
		final java.lang.Object o)
	{
		if (!(o instanceof JulianDate)) return false;

		return _iJulian == ((JulianDate) o)._iJulian;
	}

	@Override public int hashCode()
	{
		long lBits = java.lang.Double.doubleToLongBits (_iJulian);

		return (int) (lBits ^ (lBits >>> 32));
	}

	@Override public java.lang.String toString()
	{
		return org.drip.analytics.date.DateUtil.DDMMMYYYY (_iJulian);
	}

	@Override public int compareTo (
		final JulianDate dtOther)
	{
		if (_iJulian > dtOther._iJulian) return 1;

		if (_iJulian < dtOther._iJulian) return -1;

		return 0;
	}
}
