
package org.drip.analytics.date;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * DateUtil contains Various Utilities for manipulating Date. The Julian Date - Gregorian Date Inter
 * 	Conversion follows the following References:
 * 
 * 	1) Fliegel, H. F., and T. C. van Flandern (1968): A Machine Algorithm for Processing Calendar Dates,
 * 		Communications of the ACM 11 657.
 * 
 * 	2) Fenton, D. (2001): Julian to Calendar Date Conversion,
 * 		http://mathforum.org/library/drmath/view/51907.html.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class DateUtil {

	/**
	 * HALF_SECOND Constant for Julian Date Construction
	 */

	public static double HALFSECOND = 0.5;

	/**
	 * JGREG Constant for Julian Date Construction
	 */

  	public static int JGREG = 15 + 31 * (10 + 12 * 1582);

	/**
	 * LEFT_INCLUDE includes the start date in the Feb29 check
	 */

	public static final int LEFT_INCLUDE = 1;

	/**
	 * RIGHT_INCLUDE includes the end date in the Feb29 check
	 */

	public static final int RIGHT_INCLUDE = 2;

	/**
	 * Days of the week - Monday
	 */

	public static final int MONDAY = 0;

	/**
	 * Days of the week - Tuesday
	 */

	public static final int TUESDAY = 1;

	/**
	 * Days of the week - Wednesday
	 */

	public static final int WEDNESDAY = 2;

	/**
	 * Days of the week - Thursday
	 */

	public static final int THURSDAY = 3;

	/**
	 * Days of the week - Friday
	 */

	public static final int FRIDAY = 4;

	/**
	 * Days of the week - Saturday
	 */

	public static final int SATURDAY = 5;

	/**
	 * Days of the week - Sunday
	 */

	public static final int SUNDAY = 6;

	/**
	 * Integer Month - January
	 */

	public static final int JANUARY = 1;

	/**
	 * Integer Month - February
	 */

	public static final int FEBRUARY = 2;

	/**
	 * Integer Month - March
	 */

	public static final int MARCH = 3;

	/**
	 * Integer Month - April
	 */

	public static final int APRIL = 4;

	/**
	 * Integer Month - May
	 */

	public static final int MAY = 5;

	/**
	 * Integer Month - June
	 */

	public static final int JUNE = 6;

	/**
	 * Integer Month - July
	 */

	public static final int JULY = 7;

	/**
	 * Integer Month - August
	 */

	public static final int AUGUST = 8;

	/**
	 * Integer Month - September
	 */

	public static final int SEPTEMBER = 9;

	/**
	 * Integer Month - October
	 */

	public static final int OCTOBER = 10;

	/**
	 * Integer Month - November
	 */

	public static final int NOVEMBER = 11;

	/**
	 * Integer Month - December
	 */

	public static final int DECEMBER = 12;

	/**
	 * Convert YMD to an Integer Julian Date.
	 * 
	 * @param iYear Year
	 * @param iMonth Month
	 * @param iDay Day
	 * 
	 * @return Integer representing the Julian Date
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public static int ToJulian (
		final int iYear,
		final int iMonth,
		final int iDay)
		throws java.lang.Exception
	{
		if (0 > iYear || 0 > iMonth || 0 > iDay)
			throw new java.lang.Exception ("DateUtil::ToJulian => Invalid Inputs");

		int iM1 = (iMonth - 14) / 12;
		int iY1 = iYear + 4800;
		return 1461 * (iY1 + iM1) / 4 + 367 * (iMonth - 2 - 12 * iM1) / 12 -
			(3 * ((iY1 + iM1 + 100) / 100)) / 4 + iDay - 32075;
	}

	/**
	 * Create an YYYY/MM/DD String from the Input Julian Integer
	 * 
	 * @param iJulian Integer representing Julian Date
	 * 
	 * @return YYYY/MM/DD Date String
	 */

	public static java.lang.String YYYYMMDD (
		final int iJulian)
	{
		int iP = iJulian + 68569;
		int iQ = 4 * iP / 146097;
		int iR = iP - (146097 * iQ + 3) / 4;
		int iS = 4000 * (iR + 1) / 1461001;
		int iT = iR - (1461 * iS / 4) + 31;
		int iU = 80 * iT / 2447;
		int iV = iU / 11;
		int iY = 100 * (iQ - 49) + iS + iV;
		int iM = iU + 2 - 12 * iV;
		int iD = iT - (2447 * iU / 80);

		return "" + iY + "/" + org.drip.quant.common.FormatUtil.FormatDouble (iM, 2, 0, 1., false) + "/" +
			org.drip.quant.common.FormatUtil.FormatDouble (iD, 2, 0, 1., false);
	}

	/**
	 * Create an DD/MMM/YYYY String from the Input Julian Integer
	 * 
	 * @param iJulian Integer representing Julian Date
	 * 
	 * @return DD/MMM/YYYY Date String
	 */

	public static java.lang.String DDMMMYYYY (
		final int iJulian)
	{
		int iP = iJulian + 68569;
		int iQ = 4 * iP / 146097;
		int iR = iP - (146097 * iQ + 3) / 4;
		int iS = 4000 * (iR + 1) / 1461001;
		int iT = iR - (1461 * iS / 4) + 31;
		int iU = 80 * iT / 2447;
		int iV = iU / 11;
		int iY = 100 * (iQ - 49) + iS + iV;
		int iM = iU + 2 - 12 * iV;
		int iD = iT - (2447 * iU / 80);

		return org.drip.quant.common.FormatUtil.FormatDouble (iD, 2, 0, 1., false) + "-" + MonthTrigram (iM)
			+ "-" + iY;
	}

	/**
	 * Return the Year corresponding to the Julian Date
	 * 
	 * @param iJulian Integer representing Julian Date
	 * 
	 * @return integer representing the month
	 * 
	 * @throws java.lang.Exception Thrown if the Input Date in invalid
	 */

	public static int Year (
		final int iJulian)
		throws java.lang.Exception
	{
		int iP = iJulian + 68569;
		int iQ = 4 * iP / 146097;
		int iR = iP - (146097 * iQ + 3) / 4;
		int iS = 4000 * (iR + 1) / 1461001;
		int iT = iR - (1461 * iS / 4) + 31;
		int iU = 80 * iT / 2447;
		int iV = iU / 11;
		return 100 * (iQ - 49) + iS + iV;
	}

	/**
	 * Return the Month given the Julian Date represented by the Integer.
	 * 
	 * @param iJulian Integer representing Julian Date
	 * 
	 * @return Integer representing the Month
	 * 
	 * @throws java.lang.Exception Thrown if the Input Date is invalid
	 */

	public static int Month (
		final int iJulian)
		throws java.lang.Exception
	{
		int iP = iJulian + 68569;
		int iQ = 4 * iP / 146097;
		int iR = iP - (146097 * iQ + 3) / 4;
		int iS = 4000 * (iR + 1) / 1461001;
		int iT = iR - (1461 * iS / 4) + 31;
		int iU = 80 * iT / 2447;
		int iV = iU / 11;
		return iU + 2 - 12 * iV;
	}

	/**
	 * Return the Date given the Julian Date represented by the Integer.
	 * 
	 * @param iJulian Integer representing Julian Date
	 * 
	 * @return Integer representing the Date
	 * 
	 * @throws java.lang.Exception Thrown if the Input Date is invalid
	 */

	public static int Date (
		final int iJulian)
		throws java.lang.Exception
	{
		int iP = iJulian + 68569;
		int iQ = 4 * iP / 146097;
		int iR = iP - (146097 * iQ + 3) / 4;
		int iS = 4000 * (iR + 1) / 1461001;
		int iT = iR - (1461 * iS / 4) + 31;
		int iU = 80 * iT / 2447;
		return iT - (2447 * iU / 80);
	}

	/**
	 * Return the Vintage corresponding to the Julian Date
	 * 
	 * @param iJulian Integer representing Julian Date
	 * 
	 * @return String Representing the Vintage
	 */

	public static java.lang.String Vintage (
		final int iJulian)
	{
		int iP = iJulian + 68569;
		int iQ = 4 * iP / 146097;
		int iR = iP - (146097 * iQ + 3) / 4;
		int iS = 4000 * (iR + 1) / 1461001;
		int iT = iR - (1461 * iS / 4) + 31;
		int iU = 80 * iT / 2447;
		int iV = iU / 11;
		int iY = 100 * (iQ - 49) + iS + iV;
		int iM = iU + 2 - 12 * iV;

		return MonthTrigram (iM) + "-" + iY;
	}

	/**
	 * Number of Days elapsed in the Year represented by the given Julian Date
	 * 
	 * @param iDate Integer representing Julian Date
	 * 
	 * @return Integer representing the Number of Days in the Current Year
	 * 
	 * @throws java.lang.Exception Thrown if the Input Date is invalid
	 */

	public static final int DaysElapsed (
		final int iDate)
		throws java.lang.Exception
	{
		return iDate - ToJulian (Year (iDate), JANUARY, 1);
	}

	/**
	 * Number of Days remaining in the Year represented by the given Julian Date
	 * 
	 * @param iDate Integer representing Julian Date
	 * 
	 * @return Integer representing the Number of Days remaining in the Current Year
	 * 
	 * @throws java.lang.Exception Thrown if the Input Date is invalid
	 */

	public static final int DaysRemaining (
		final int iDate)
		throws java.lang.Exception
	{
		return ToJulian (Year (iDate), DECEMBER, 31) - iDate;
	}

	/**
	 * Indicate if the Year of the given Julian Date is a Leap Year
	 * 
	 * @param iDate Input Date
	 * 
	 * @return TRUE - Date falls on a Leap Year
	 * 
	 * @throws java.lang.Exception Thrown if Input is invalid
	 */

	public static final boolean IsLeapYear (
		final int iDate)
		throws java.lang.Exception
	{
		return 0 == (Year (iDate) % 4);
	}

	/**
	 * Indicate whether there is at least One Leap Day between 2 given Dates
	 * 
	 * @param iStartDate The Start Date
	 * @param iEndDate The End Date
	 * @param iIncludeSide INCLUDE_LEFT or INCLUDE_RIGHT indicating whether the starting date, the ending
	 * 	date, or both dates are to be included
	 *  
	 * @return TRUE - There is at least One Feb29 between the Dates
	 * 
	 * @throws java.lang.Exception If inputs are invalid
	 */

	public static final boolean ContainsFeb29 (
		final int iStartDate,
		final int iEndDate,
		final int iIncludeSide)
		throws java.lang.Exception
	{
		if (iStartDate >= iEndDate) return false;

		int iLeftDate = iStartDate;
		int iRightDate = iEndDate;

		if (0 == (iIncludeSide & LEFT_INCLUDE)) ++iLeftDate;

		if (0 == (iIncludeSide & RIGHT_INCLUDE)) --iRightDate;

		for (int iDate = iLeftDate; iDate <= iRightDate; ++iDate) {
			if (FEBRUARY == Month (iDate) && 29 == Date (iDate)) return true;
		}

		return false;
	}

	/**
	 * Calculate how many Leap Days exist between the 2 given Dates
	 * 
	 * @param iStartDate The Start Date
	 * @param iEndDate The End Date
	 * @param iIncludeSide INCLUDE_LEFT or INCLUDE_RIGHT indicating whether the starting date, the ending
	 * 	date, or both dates are to be included
	 * 
	 * @return Number of Leap Days
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public static final int NumFeb29 (
		final int iStartDate,
		final int iEndDate,
		final int iIncludeSide)
		throws java.lang.Exception
	{
		int iNumFeb29 = 0;
		boolean bLoop = true;
		int iDate = iStartDate;

		while (bLoop) {
			int iPeriodEndDate = iDate + 365;

			if (iPeriodEndDate > iEndDate) {
				bLoop = false;
				iPeriodEndDate = iEndDate;
			}

			if (ContainsFeb29 (iDate, iPeriodEndDate, iIncludeSide)) ++iNumFeb29;

			iDate = iPeriodEndDate;
		}

		return iNumFeb29;
	}

	/**
	 * Return the English word corresponding to the input integer month
	 *  
	 * @param iMonth Integer representing the month
	 * 
	 * @return String of the English word
	 */

	public static final java.lang.String MonthChar (
		final int iMonth)
	{
		if (JANUARY == iMonth) return "January";

		if (FEBRUARY == iMonth) return "February";

		if (MARCH == iMonth) return "March";

		if (APRIL == iMonth) return "April";

		if (MAY == iMonth) return "May";

		if (JUNE == iMonth) return "June";

		if (JULY == iMonth) return "July";

		if (AUGUST == iMonth) return "August";

		if (SEPTEMBER == iMonth) return "September";

		if (OCTOBER == iMonth) return "October";

		if (NOVEMBER == iMonth) return "November";

		if (DECEMBER == iMonth) return "December";

		return null;
	}

	/**
	 * Return the Month Trigram corresponding to the Input Integer Month
	 * 
	 * @param iMonth Integer representing the Month
	 * 
	 * @return String representing the Month Trigram (used, e.g., in Oracle DB)
	 */

	public static java.lang.String MonthTrigram (
		final int iMonth)
	{
		if (JANUARY == iMonth) return "JAN";

		if (FEBRUARY == iMonth) return "FEB";

		if (MARCH == iMonth) return "MAR";

		if (APRIL == iMonth) return "APR";

		if (MAY == iMonth) return "MAY";

		if (JUNE == iMonth) return "JUN";

		if (JULY == iMonth) return "JUL";

		if (AUGUST == iMonth) return "AUG";

		if (SEPTEMBER == iMonth) return "SEP";

		if (OCTOBER == iMonth) return "OCT";

		if (NOVEMBER == iMonth) return "NOV";

		if (DECEMBER == iMonth) return "DEC";

		return null;
	}

	/**
	 * Convert the month trigram/word to the corresponding month integer
	 * 
	 * @param strMonth Month trigram or English Word
	 * 
	 * @return Integer representing the Month
	 * 
	 * @throws java.lang.Exception Thrown on Invalid Input Month
	 */

	public static final int MonthFromMonthChars (
		final java.lang.String strMonth)
		throws java.lang.Exception
	{
		if (null == strMonth || strMonth.isEmpty())
			throw new java.lang.Exception ("DateUtil::MonthFromMonthChars => Invalid Month!");

		if (strMonth.equalsIgnoreCase ("JAN") || strMonth.equalsIgnoreCase ("JANUARY")) return JANUARY;

		if (strMonth.equalsIgnoreCase ("FEB") || strMonth.equalsIgnoreCase ("FEBRUARY")) return FEBRUARY;

		if (strMonth.equalsIgnoreCase ("MAR") || strMonth.equalsIgnoreCase ("MARCH")) return MARCH;

		if (strMonth.equalsIgnoreCase ("APR") || strMonth.equalsIgnoreCase ("APRIL")) return APRIL;

		if (strMonth.equalsIgnoreCase ("MAY")) return MAY;

		if (strMonth.equalsIgnoreCase ("JUN") || strMonth.equalsIgnoreCase ("JUNE")) return JUNE;

		if (strMonth.equalsIgnoreCase ("JUL") || strMonth.equalsIgnoreCase ("JULY")) return JULY;

		if (strMonth.equalsIgnoreCase ("AUG") || strMonth.equalsIgnoreCase ("AUGUST")) return AUGUST;

		if (strMonth.equalsIgnoreCase ("SEP") || strMonth.equalsIgnoreCase ("SEPTEMBER") ||
			strMonth.equalsIgnoreCase ("SEPT"))
			return SEPTEMBER;

		if (strMonth.equalsIgnoreCase ("OCT") || strMonth.equalsIgnoreCase ("OCTOBER")) return OCTOBER;

		if (strMonth.equalsIgnoreCase ("NOV") || strMonth.equalsIgnoreCase ("NOVEMBER")) return NOVEMBER;

		if (strMonth.equalsIgnoreCase ("DEC") || strMonth.equalsIgnoreCase ("DECEMBER")) return DECEMBER;

		throw new java.lang.Exception ("DateUtil::MonthFromMonthChars => Invalid Month: " + strMonth);
	}

	/**
	 * Get the English word for day corresponding to the input integer
	 * 
	 * @param iDay Integer representing the day
	 * 
	 * @return String representing the English word for the day
	 */

	public static java.lang.String DayChars (
		final int iDay)
	{
		if (MONDAY == iDay) return "Monday";

		if (TUESDAY == iDay) return "Tuesday";

		if (WEDNESDAY == iDay) return "Wednesday";

		if (THURSDAY == iDay) return "Thursday";

		if (FRIDAY == iDay) return "Friday";

		if (SATURDAY == iDay) return "Saturday";

		if (SUNDAY == iDay) return "Sunday";

		return "";
	}

	/**
	 * Get the maximum number of days in the given month and year
	 * 
	 * @param iMonth Integer representing the month
	 * @param iYear Integer representing the year
	 * 
	 * @return Integer representing the maximum days
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public static final int DaysInMonth (
		final int iMonth,
		final int iYear)
		throws java.lang.Exception
	{
		if (JANUARY == iMonth) return 31;

		if (FEBRUARY == iMonth) return 0 == (iYear % 4) ? 29 : 28;

		if (MARCH == iMonth) return 31;

		if (APRIL == iMonth) return 30;

		if (MAY == iMonth) return 31;

		if (JUNE == iMonth) return 30;

		if (JULY == iMonth) return 31;

		if (AUGUST == iMonth) return 31;

		if (SEPTEMBER == iMonth) return 30;

		if (OCTOBER == iMonth) return 31;

		if (NOVEMBER == iMonth) return 30;

		if (DECEMBER == iMonth) return 31;

		throw new java.lang.Exception ("DateUtil::DaysInMonth => Invalid Month: " + iMonth);
	}

	/**
	 * Indicate if the given Date corresponds to a Month End
	 * 
	 * @param iDate The Date
	 * 
	 * @return TRUE - Date Corresponds to EOM
	 * 
	 * @throws java.lang.Exception Thrown if input date is invalid
	 */

	public static final boolean IsEOM (
		final int iDate)
		throws java.lang.Exception
	{
		return Date (iDate) == DaysInMonth (Month (iDate), Year (iDate)) ? true : false;
	}

	/**
	 * Create a JulianDate from the Year/Month/Date
	 *  
	 * @param iYear Year
	 * @param iMonth Month
	 * @param iDate Date
	 * 
	 * @return Julian Date corresponding to the specified Year/Month/Date
	 */

	public static final org.drip.analytics.date.JulianDate CreateFromYMD (
		final int iYear,
		final int iMonth,
		final int iDate)
	{
		try {
			return new org.drip.analytics.date.JulianDate (ToJulian (iYear, iMonth, iDate));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Return a Julian Date corresponding to Today
	 *  
	 * @return JulianDate corresponding to Today
	 */

	public static final org.drip.analytics.date.JulianDate Today()
	{
		java.util.Date dtNow = new java.util.Date();

		try {
			return CreateFromYMD (Year (dtNow), Month (dtNow), Day (dtNow));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a JulianDate from a String containing the Date in the DDMMMYYYY Format
	 * 
	 * @param strDate String containing the Date in the DDMMMYYYY Format
	 * 
	 * @return The JulianDate Instance
	 */

	public static final JulianDate CreateFromDDMMMYYYY (
		final java.lang.String strDate)
	{
		if (null == strDate || strDate.isEmpty()) return null;

		java.lang.String[] astrParts = strDate.split ("-");

		if (3 != astrParts.length) return null;

		try {
			return CreateFromYMD (new java.lang.Integer (astrParts[2]), MonthFromMonthChars (astrParts[1]),
				new java.lang.Integer (astrParts[0]));
		} catch (java.lang.Exception e) {
		}

		return null;
	}

	/**
	 * Create a JulianDate from a String containing Date in the DDMMYYYY Format
	 * 
	 * @param strMDY String containing Date in the MM/DD/YYYY Format
	 * @param strDelim String Delimiter
	 * 
	 * @return The JulianDate Instance
	 */

	public static final org.drip.analytics.date.JulianDate CreateFromMDY (
		final java.lang.String strMDY,
		final java.lang.String strDelim)
	{
		if (null == strMDY || strMDY.isEmpty() || null == strDelim || strDelim.isEmpty()) return null;

		java.lang.String[] astrParts = strMDY.split (strDelim);

		if (3 != astrParts.length) return null;

		try {
			return CreateFromYMD (new java.lang.Integer (astrParts[2]), new java.lang.Integer (astrParts[0]),
				new java.lang.Integer (astrParts[1]));
		} catch (java.lang.Exception e) {
		}

		return null;
	}

	/**
	 * Create a JulianDate from a String containing Date in the YYYYMMDD Format
	 * 
	 * @param strYMD String containing Date in the YYYYMMDD Format
	 * @param strDelim String Delimiter
	 * 
	 * @return The JulianDate Instance
	 */

	public static final org.drip.analytics.date.JulianDate CreateFromYMD (
		final java.lang.String strYMD,
		final java.lang.String strDelim)
	{
		if (null == strYMD || strYMD.isEmpty() || null == strDelim || strDelim.isEmpty()) return null;

		java.lang.String[] astrParts = strYMD.split (strDelim);

		if (3 != astrParts.length) return null;

		try {
			return CreateFromYMD (new java.lang.Integer (astrParts[0]), new java.lang.Integer (astrParts[1]),
				new java.lang.Integer (astrParts[2]));
		} catch (java.lang.Exception e) {
		}

		return null;
	}

	/**
	 * Return the Day of the Week corresponding to the java.util.Date Instance
	 * 
	 * @param dt The java.util.Date Instance
	 * 
	 * @return The Day Of The Week
	 * 
	 * @throws java.lang.Exception Thrown if Input Date is invalid
	 */

	public static final int DayOfTheWeek (
		final java.util.Date dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("DateUtil::DayOfTheWeek => Invalid Date");

		java.util.Calendar cal = java.util.Calendar.getInstance();

		cal.setTime (dt);

		return cal.get (java.util.Calendar.DAY_OF_WEEK);
	}

	/**
	 * Return the Day corresponding to the java.util.Date Instance
	 * 
	 * @param dt The java.util.Date Instance
	 * 
	 * @return The Day
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public static final int Day (
		final java.util.Date dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("DateUtil::Day => Invalid Date");

		java.util.Calendar cal = java.util.Calendar.getInstance();

		cal.setTime (dt);

		return cal.get (java.util.Calendar.DATE);
	}

	/**
	 * Return the Month corresponding to the java.util.Date Instance. 1 is January, and 12 is December
	 * 
	 * @param dt The java.util.Date Instance
	 * 
	 * @return The Month
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public static final int Month (
		final java.util.Date dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("DateUtil::Month => Invalid Date");

		java.util.Calendar cal = java.util.Calendar.getInstance();

		cal.setTime (dt);

		return cal.get (java.util.Calendar.MONTH) + 1;
	}

	/**
	 * Return the Year corresponding to the java.util.Date Instance
	 * 
	 * @param dt The java.util.Date Instance
	 * 
	 * @return The Year
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public static final int Year (
		final java.util.Date dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("DateUtil::Year => Invalid Date");

		java.util.Calendar cal = java.util.Calendar.getInstance();

		cal.setTime (dt);

		return cal.get (java.util.Calendar.YEAR);
	}

	/**
	 * Create an Oracle Date Trigram from a YYYYMMDD String
	 * 
	 * @param strYYYYMMDD Date String in the YYYYMMDD Format.
	 * 
	 * @return Oracle Date Trigram String
	 */

	public static java.lang.String MakeOracleDateFromYYYYMMDD (
		final java.lang.String strYYYYMMDD)
	{
		if (null == strYYYYMMDD || strYYYYMMDD.isEmpty()) return null;

		try {
			return strYYYYMMDD.substring (6) + "-" + MonthTrigram ((new java.lang.Integer
				(strYYYYMMDD.substring (4, 6))).intValue()) + "-" + strYYYYMMDD.substring (0, 4);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Oracle date trigram from a Bloomberg date string
	 * 
	 * @param strBBGDate Bloomberg date string
	 * 
	 * @return Oracle date trigram string
	 */

	public static java.lang.String MakeOracleDateFromBBGDate (
		final java.lang.String strBBGDate)
	{
		if (null == strBBGDate || strBBGDate.isEmpty()) return null;

		java.util.StringTokenizer st = new java.util.StringTokenizer (strBBGDate, "/");

		try {
			java.lang.String strMonth = MonthTrigram (new java.lang.Integer (st.nextToken()));

			if (null == strMonth) return null;

			return st.nextToken() + "-" + strMonth + "-" + st.nextToken();
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a JulianDate from the java Date
	 * 
	 * @param dt Java Date input
	 * 
	 * @return JulianDate output
	 */

	public static final org.drip.analytics.date.JulianDate MakeJulianFromRSEntry (
		final java.util.Date dt)
	{
		if (null == dt) return null;

		try {
			return CreateFromYMD (Year (dt), Month (dt), Day (dt));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve a Java Date Instance from the Julian Date Instance
	 * 
	 * @param dt Julian Date Instance
	 * 
	 * @return The Java Date Instance
	 */

	public static final java.util.Date JavaDateFromJulianDate (
		final org.drip.analytics.date.JulianDate dt)
	{
		if (null == dt) return null;

		java.util.Calendar cal = java.util.Calendar.getInstance();

		int iDate = dt.julian();

		try {
			cal.set (Year (iDate), Month (iDate) - 1, Date (iDate));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return cal.getTime();
	}

	/**
	 * Create a JulianDate from the DD MMM YY
	 * 
	 * @param strDDMMMYY Java Date input as delimited DD MMM YY
	 * @param strDelim Delimiter
	 * 
	 * @return JulianDate output
	 */

	public static final org.drip.analytics.date.JulianDate MakeJulianFromDDMMMYY (
		final java.lang.String strDDMMMYY,
		final java.lang.String strDelim)
	{
		if (null == strDDMMMYY || strDDMMMYY.isEmpty() || null == strDelim || strDelim.isEmpty())
			return null;

		java.lang.String[] astrDMY = strDDMMMYY.split (strDelim);

		if (null == astrDMY || 3 != astrDMY.length) return null;

		try {
			return CreateFromYMD (2000 + new java.lang.Integer (astrDMY[2].trim()), MonthFromMonthChars
				(astrDMY[1].trim()), new java.lang.Integer (astrDMY[0].trim()));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a JulianDate from the YYYY MM DD
	 * 
	 * @param strYYYYMMDD Java Date input as delimited YYYY MM DD
	 * @param strDelim Delimiter
	 * 
	 * @return JulianDate output
	 */

	public static final org.drip.analytics.date.JulianDate MakeJulianFromYYYYMMDD (
		final java.lang.String strYYYYMMDD,
		final java.lang.String strDelim)
	{
		if (null == strYYYYMMDD || strYYYYMMDD.isEmpty() || null == strDelim || strDelim.isEmpty())
			return null;

		java.lang.String[] astrYYYYMMDD = strYYYYMMDD.split (strDelim);

		if (null == astrYYYYMMDD || 3 != astrYYYYMMDD.length) return null;

		try {
			return CreateFromYMD (new java.lang.Integer (astrYYYYMMDD[0].trim()), new java.lang.Integer
				(astrYYYYMMDD[1].trim()), new java.lang.Integer (astrYYYYMMDD[2].trim()));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a JulianDate from the MDY
	 * 
	 * @param strMDY Java Date input as delimited M/D/Y
	 * @param strDelim Delimiter
	 * 
	 * @return JulianDate output
	 */

	public static final org.drip.analytics.date.JulianDate FromMDY (
		final java.lang.String strMDY,
		final java.lang.String strDelim)
	{
		if (null == strMDY || strMDY.isEmpty() || null == strDelim || strDelim.isEmpty())
			return null;

		java.lang.String[] astrDMY = strMDY.split (strDelim);

		if (null == astrDMY || 3 != astrDMY.length) return null;

		try {
			return CreateFromYMD (new java.lang.Integer (astrDMY[2].trim()), new java.lang.Integer
				(astrDMY[0].trim()), new java.lang.Integer (astrDMY[1].trim()));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a JulianDate from Bloomberg date string
	 * 
	 * @param strBBGDate Bloomberg date string
	 * 
	 * @return The new JulianDate
	 */

	public static final org.drip.analytics.date.JulianDate MakeJulianDateFromBBGDate (
		final java.lang.String strBBGDate)
	{
		if (null == strBBGDate || strBBGDate.isEmpty()) return null;

		java.lang.String[] astrFields = strBBGDate.split ("/");

		if (3 != astrFields.length) return null;

		try {
			return CreateFromYMD ((int) new java.lang.Double (astrFields[2].trim()).doubleValue(), (int) new
				java.lang.Double (astrFields[0].trim()).doubleValue(), (int) new java.lang.Double
					(astrFields[1].trim()).doubleValue());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Month corresponding to the Month Digit Code
	 * 
	 * @param ch The Month Digit Code
	 * 
	 * @return The Month corresponding to the Month Digit Code
	 * 
	 * @throws java.lang.Exception Thrown if the Digit Code is Invalid
	 */

	public static final int MonthFromCode (
		final char ch)
		throws java.lang.Exception
	{
		if ('F' == ch) return JANUARY;

		if ('G' == ch) return FEBRUARY;

		if ('H' == ch) return MARCH;

		if ('J' == ch) return APRIL;

		if ('K' == ch) return MAY;

		if ('M' == ch) return JUNE;

		if ('N' == ch) return JULY;

		if ('Q' == ch) return AUGUST;

		if ('U' == ch) return SEPTEMBER;

		if ('V' == ch) return OCTOBER;

		if ('X' == ch) return NOVEMBER;

		if ('Z' == ch) return DECEMBER;

		throw new java.lang.Exception ("DateUtil::MonthFromCode => Invalid Character: " + ch);
	}

	/**
	 * Retrieve the Digit Code corresponding to the Month
	 * 
	 * @param iMonth The Month
	 * 
	 * @return The Digit Code corresponding to the Month
	 * 
	 * @throws java.lang.Exception Thrown if the Digit Code cannot be computed
	 */

	public static final char CodeFromMonth (
		final int iMonth)
		throws java.lang.Exception
	{
		if (JANUARY == iMonth) return 'F';

		if (FEBRUARY == iMonth) return 'G';

		if (MARCH == iMonth) return 'H';

		if (APRIL == iMonth) return 'J';

		if (MAY == iMonth) return 'K';

		if (JUNE == iMonth) return 'M';

		if (JULY == iMonth) return 'N';

		if (AUGUST == iMonth) return 'Q';

		if (SEPTEMBER == iMonth) return 'U';

		if (OCTOBER == iMonth) return 'V';

		if (NOVEMBER == iMonth) return 'X';

		if (DECEMBER == iMonth) return 'Z';

		throw new java.lang.Exception ("DateUtil::CodeFromMonth => Invalid Month: " + iMonth);
	}
}
