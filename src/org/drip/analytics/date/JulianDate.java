
package org.drip.analytics.date;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * Class provides a comprehensive representation of Julian date and date manipulation functionality. It
 * 	exports the following functionality:
 * 	- Explicit date construction, as well as date construction from several input string formats/today
 *  - Date Addition/Adjustment/Elapsed/Difference, add/subtract days/weeks/months/years and tenor codes
 *  - Leap Year Functionality (number of leap days in the given interval, is the given year a leap year etc.)
 *  - Generate the subsequent IMM date (CME IMM date, CDS/Credit ISDA IMM date etc)
 *  - Year/Month/Day in numbers/characters
 *  - Days Elapsed/Remaining, is EOM
 *  - Comparison with the Other, equals/hash-code/comparator
 *  - Export the date to a variety of date formats (Oracle, Julian, Bloomberg)
 *  - Serialization/De-serialization to and from Byte Arrays
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
			iTimeUnit = new java.lang.Integer (strTenor.substring (0, iNumChar - 1));
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
			iTimeUnit = new java.lang.Integer (strTenor.substring (0, iNumChar - 1));
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
			return org.drip.quant.common.FormatUtil.FormatDouble (DateUtil.Year (_iJulian), 4, 0, 1.) +
				strDelim + org.drip.quant.common.FormatUtil.FormatDouble
					(org.drip.analytics.date.DateUtil.Month (_iJulian), 2, 0, 1.) + strDelim +
						org.drip.quant.common.FormatUtil.FormatDouble (DateUtil.Date (_iJulian), 2, 0, 1.);
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
