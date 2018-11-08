
package org.drip.feed.loader;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>CreditStaticAndMarks</i> contains functionality to load a variety of Credit and Rates Product reference
 * data and closing marks. It exposes the following functionality:
 * <ul>
 * 	<li>
 * 		Load the bond reference data, static data, amortization schedule and EOS
 * 	</li>
 * 	<li>
 * 		Build the bond instance entities from the reference data
 * 	</li>
 * 	<li>
 * 		Load the bond, CDS, and Rates product Closing Marks
 * 	</li>
 * 	<li>
 * 		Load and build the Holiday Calendars
 * 	</li>
 * </ul>
 * 	<br>
 * 
 * CreditStaticAndMarks assumes the appropriate connections are available to load the data.
 * 	<br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed">Feed</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/loader">Loader</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

class CreditStaticAndMarks {
	private static final boolean m_bBlog = false;
	private static java.lang.String INVALID_BBG_FIELD_START = "#N/A";

	public static java.lang.String makeSQLClearISINOrCUSIP (
		final java.lang.String[] astrRecord)
	{
		if (null == astrRecord || 0 == astrRecord.length) return null;

		java.lang.String strISIN = astrRecord[1];
		java.lang.String strCUSIP = astrRecord[0];

		if (null == strISIN || strISIN.isEmpty()) return null;

		if (null == strCUSIP || strCUSIP.isEmpty()) return null;

		java.lang.StringBuilder sb = new java.lang.StringBuilder();

		sb.append ("delete from BondRef where ISIN = '").append (strISIN).append ("' or CUSIP = '").append
			(strCUSIP).append ("'");

		return sb.toString();
	}

	private static java.lang.String makeSQLClearMarksForBondDate (
		final java.lang.String[] astrRecord)
	{
		if (null == astrRecord || 0 == astrRecord.length) return null;

		java.lang.String strIDType = astrRecord[6];

		if (null == strIDType || 0 == strIDType.length() || (!"ISIN".equalsIgnoreCase (strIDType) &&
			!"CUSIP".equalsIgnoreCase (strIDType))) {
			System.out.println ("Unknown ID Type: " + strIDType);

			return null;
		}

		java.lang.String strID = astrRecord[1];
		java.lang.String strORAMarkDate = "";

		try {
			strORAMarkDate = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrRecord[3].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad mark date " + astrRecord[1] + " for " + strIDType + " " + strID);

			return null;
		}

		java.lang.StringBuilder sb = new java.lang.StringBuilder();

		sb.append ("delete from BondMarks where ID = '").append (strID).append ("' and MarkDate = '").append
			(strORAMarkDate).append ("'");

		return sb.toString();
	}

	private static java.lang.String makeSQLBondMarksInsert (
		final java.lang.String[] astrRecord)
	{
		if (null == astrRecord || 0 == astrRecord.length) return null;

		java.lang.String strIDType = astrRecord[6];

		if (null == strIDType || 0 == strIDType.length() || (!"ISIN".equalsIgnoreCase (strIDType) &&
			!"CUSIP".equalsIgnoreCase (strIDType))) {
			System.out.println ("Unknown ID Type: " + strIDType);

			return null;
		}

		java.lang.String strID = astrRecord[1];
		java.lang.String strORAMarkDate = "";

		try {
			strORAMarkDate = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate (astrRecord[3].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad mark date " + astrRecord[1] + " for " + strIDType + " " + strID);

			return null;
		}

		double dblMarkValue = java.lang.Double.NaN;

		try {
			dblMarkValue = java.lang.Double.parseDouble (astrRecord[2].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad mark " + astrRecord[2] + " for " + strIDType + " " + strID);

			return null;
		}

		java.lang.StringBuilder sb = new java.lang.StringBuilder();

		sb.append ("insert into BondMarks values('").append (strIDType).append ("', '").append (strID).append
			("', '").append (strORAMarkDate).append ("', ").append (dblMarkValue).append (")");

		return sb.toString();
	}

	private static java.lang.String makeSQLClear_FODATA (
		final java.lang.String[] astrValRecord)
	{
		if (null == astrValRecord || 0 == astrValRecord.length) return null;

		java.lang.String strISIN = astrValRecord[40];

		if (null == strISIN || strISIN.isEmpty()) return null;

		return "delete from BondRef where ISIN = '" + strISIN + "'";
	}

	private static java.lang.String makeSQLInsert_FODATA (
		final java.lang.String[] astrValRecord)
	{
		if (null == astrValRecord || 0 == astrValRecord.length) return null;

		java.lang.String strCUSIP = astrValRecord[42];

		if (null == strCUSIP || strCUSIP.isEmpty()) return null;

		java.lang.String strISIN = astrValRecord[40];

		if (null == strISIN || strISIN.isEmpty()) return null;

		java.lang.String strName = astrValRecord[59];

		if (null == strName || strName.isEmpty()) return null;

		java.lang.String strDescription = astrValRecord[59];

		if (null == strDescription || strDescription.isEmpty()) return null;

		java.lang.String strTicker = astrValRecord[3];

		if (null == strTicker || strTicker.isEmpty()) return null;

		double dblCoupon = 0.;

		try {
			dblCoupon = java.lang.Double.parseDouble (astrValRecord[4].trim());
		} catch (java.lang.Exception e) {
			if (m_bBlog) System.out.println ("Bad coupon " + astrValRecord[4] + " for ISIN " + strISIN);
		}

		java.lang.String strOracleMaturity = "";

		try {
			strOracleMaturity = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrValRecord[5].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad maturity " + astrValRecord[5] + " for ISIN " + strISIN);

			return null;
		}

		java.lang.String strOracleAnnounce = "";

		try {
			strOracleAnnounce = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrValRecord[29].trim());
		} catch (java.lang.Exception e) {
			if (m_bBlog)
				System.out.println ("Bad announce date " + astrValRecord[29] + " for ISIN " + strISIN);
		}

		java.lang.String strOracleFirstSettle = "";

		try {
			strOracleFirstSettle = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrValRecord[31].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad first settle " + astrValRecord[31] + " for ISIN " + strISIN);

			return null;
		}

		java.lang.String strOracleFirstCoupon = "";

		try {
			strOracleFirstCoupon = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrValRecord[33].trim());
		} catch (java.lang.Exception e) {
		}

		if (null == strOracleFirstCoupon || strOracleFirstCoupon.isEmpty())
			strOracleFirstCoupon = strOracleFirstSettle;

		if (null == strOracleAnnounce || strOracleAnnounce.isEmpty())
			strOracleAnnounce = strOracleFirstCoupon;

		java.lang.String strCurrency = astrValRecord[67];

		if (null == strCurrency || strCurrency.isEmpty()) return null;

		int iCouponFreq = 0;

		try {
			iCouponFreq = (int) java.lang.Double.parseDouble (astrValRecord[12].trim());
		} catch (java.lang.Exception e) {
			if (m_bBlog)
				System.out.println ("Bad coupon freq " + astrValRecord[12] + " for ISIN " + strISIN);
		}

		java.lang.String strDayCount = org.drip.analytics.support.Helper.GetDayCountFromBBGCode
			(astrValRecord[16]);

		if (null == strDayCount || strDayCount.isEmpty()) return null;

		double dblFloatSpread = 0.;

		try {
			dblFloatSpread = java.lang.Double.parseDouble (astrValRecord[78].trim());
		} catch (java.lang.Exception e) {
			if (m_bBlog)
				System.out.println ("Bad float spread " + astrValRecord[78] + " for ISIN " + strISIN);
		}

		java.lang.String strRateIndex =
			org.drip.analytics.support.Helper.RateIndexFromCcyAndCouponFreq (strCurrency,
				iCouponFreq);

		java.lang.String strCalendar = astrValRecord[19];

		if (strCalendar.startsWith (INVALID_BBG_FIELD_START)) strCalendar = "";

		java.lang.String strMoodys = "";
		java.lang.String strSnP = "";
		java.lang.String strFitch = "";
		java.lang.String strIndustrySector = astrValRecord[54];
		java.lang.String strIndustryGroup = astrValRecord[55];
		java.lang.String strSnrSub = astrValRecord[20];
		java.lang.String strIssuer = astrValRecord[0];
		double dblAmountIssued = 0.;

		try {
			dblAmountIssued = java.lang.Double.parseDouble (astrValRecord[21].trim());
		} catch (java.lang.Exception e) {
			if (m_bBlog) System.out.println ("Bad issue amt " + astrValRecord[21] + " for ISIN " + strISIN);
		}

		double dblAmountOutstanding = 0.;

		try {
			dblAmountOutstanding = java.lang.Double.parseDouble (astrValRecord[22].trim());
		} catch (java.lang.Exception e) {
			if (m_bBlog)
				System.out.println ("Bad outstanding amt " + astrValRecord[22] + " for ISIN " + strISIN);
		}

		java.lang.String strOracleIssue = "";

		try {
			strOracleIssue = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrValRecord[29].trim());
		} catch (java.lang.Exception e) {
			if (m_bBlog) System.out.println ("Bad issue date " + astrValRecord[29] + " for ISIN " + strISIN);
		}

		java.lang.String strCouponDates = "";
		java.lang.String strCouponFactors = "";
		java.lang.String strNotionalDates = "";
		java.lang.String strNotionalFactors = "";
		java.lang.String strIssuerSPN = "";
		java.lang.String strFloatQuote = "FullCoupon";

		java.lang.StringBuilder sb = new java.lang.StringBuilder();

		sb.append ("INSERT INTO BondRef VALUES (");

		sb.append ("'" + strISIN + "',");

		sb.append ("'" + strCUSIP + "',");

		sb.append ("'" + strName + "',");

		sb.append ("'" + strDescription + "',");

		sb.append ("'" + strTicker + "',");

		sb.append (dblCoupon + ",");

		sb.append ("'" + strOracleMaturity + "',");

		sb.append ("'" + strOracleAnnounce + "',");

		sb.append ("'" + strOracleFirstSettle + "',");

		sb.append ("'" + strOracleFirstCoupon + "',");

		sb.append ("'" + strCurrency + "',");

		sb.append (iCouponFreq + ",");

		sb.append ("'" + strDayCount + "',");

		sb.append (dblFloatSpread + ",");

		sb.append ("'" + strRateIndex + "',");

		sb.append ("'" + strCalendar + "',");

		sb.append ("'" + strMoodys + "',");

		sb.append ("'" + strSnP + "',");

		sb.append ("'" + strFitch + "',");

		sb.append ("'" + strIndustrySector + "',");

		sb.append ("'" + strIndustryGroup + "',");

		sb.append ("'" + strSnrSub + "',");

		sb.append ("'" + strIssuer + "',");

		sb.append (dblAmountIssued + ",");

		sb.append (dblAmountOutstanding + ",");

		sb.append ("'" + strOracleIssue + "',");

		sb.append ("'" + strCouponDates + "',");

		sb.append ("'" + strCouponFactors + "',");

		sb.append ("'" + strNotionalDates + "',");

		sb.append ("'" + strNotionalFactors + "',");

		sb.append ("'" + strIssuerSPN + "',");

		sb.append ("'" + strFloatQuote + "')");

		return sb.toString();
	}

	private static final java.lang.String ClearISINDateTypeFromEOS (
		final java.lang.String[] astrEOS)
	{
		java.lang.String strISIN = astrEOS[1].trim();

		java.lang.String strOracleExerciseStartDate = "";

		try {
			strOracleExerciseStartDate = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrEOS[2].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad exercise date " + astrEOS[2] + " for ISIN " + strISIN);

			return "";
		}

		java.lang.String strOracleExerciseEndDate = "";

		try {
			strOracleExerciseEndDate = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrEOS[4].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad exercise date " + astrEOS[4] + " for ISIN " + strISIN);

			return "";
		}

		java.lang.String strCallOrPut = astrEOS[8].trim();

		java.lang.String strEuroOrAmer = astrEOS[9].trim();

		return "delete from EOS where ISIN = '" + strISIN + "' and ExerciseStartDate = '" +
			strOracleExerciseStartDate + "' and ExerciseEndDate = '" + strOracleExerciseEndDate +
				"' and CallOrPut = '" + strCallOrPut + "' and EuroAmer = '" + strEuroOrAmer + "'";
	}

	private static final java.lang.String InsertIntoEOS (
		final java.lang.String[] astrEOS)
	{
		java.lang.String strISIN = astrEOS[1].trim();

		java.lang.String strOracleExerciseStartDate = "";

		try {
			strOracleExerciseStartDate = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrEOS[2].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad exercise start " + astrEOS[2] + " for ISIN " + strISIN);

			return null;
		}

		java.lang.String strOracleExerciseEndDate = "";

		try {
			strOracleExerciseEndDate = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrEOS[4].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad exercise end " + astrEOS[4] + " for ISIN " + strISIN);

			return null;
		}

		double dblExerciseFactor = java.lang.Double.NaN;

		try {
			dblExerciseFactor = java.lang.Double.parseDouble (astrEOS[6].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad exercise factor " + astrEOS[6] + " for ISIN " + strISIN);

			return null;
		}

		java.lang.String strExerciseCallOrPut = astrEOS[8].trim();

		java.lang.String strExerciseEuroOrAmer = astrEOS[9].trim();

		java.lang.String strStrikeType = astrEOS[12].trim();

		int iKnockoutOnDefault = 1;

		try {
			iKnockoutOnDefault = (int) java.lang.Double.parseDouble (astrEOS[13].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad knockout on default " + astrEOS[13] + " for ISIN " + strISIN);

			return null;
		}

		return "insert into EOS values('" + strISIN + "', '" + strOracleExerciseStartDate + "', '" +
			strOracleExerciseEndDate + "', " + dblExerciseFactor + ", '" + strExerciseCallOrPut + "', '" +
				strExerciseEuroOrAmer + "', '" + strStrikeType + "', " + iKnockoutOnDefault + ", 'N')";
	}

	private static final java.lang.String ClearFromIREOD (
		final java.lang.String[] astrIREOD)
	{
		java.lang.String strType = astrIREOD[3].trim();

		java.lang.String strCurrency = astrIREOD[4].trim();

		java.lang.String strOracleCloseDate = "";

		try {
			strOracleCloseDate = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrIREOD[1].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad date " + astrIREOD[1] + " for " + strCurrency);

			return null;
		}

		return "delete from IR_EOD where EOD = '" + strOracleCloseDate + "' and Currency = '" +
			strCurrency + "' and Type = '" + strType + "'";
	}

	private static final java.lang.String InsertIntoIREOD (
		final java.lang.String[] astrIREOD)
	{
		java.lang.String strType = astrIREOD[3].trim();

		java.lang.String strCurrency = astrIREOD[4].trim();

		java.lang.String strOracleCloseDate = "";
		double dbl1D = java.lang.Double.NaN;
		double dbl1M = java.lang.Double.NaN;
		double dbl2M = java.lang.Double.NaN;
		double dbl3M = java.lang.Double.NaN;
		double dbl4M = java.lang.Double.NaN;
		double dbl5M = java.lang.Double.NaN;
		double dbl6M = java.lang.Double.NaN;
		double dbl9M = java.lang.Double.NaN;
		double dbl1Y = java.lang.Double.NaN;
		double dbl18M = java.lang.Double.NaN;
		double dbl2Y = java.lang.Double.NaN;
		double dbl3Y = java.lang.Double.NaN;
		double dbl4Y = java.lang.Double.NaN;
		double dbl5Y = java.lang.Double.NaN;
		double dbl6Y = java.lang.Double.NaN;
		double dbl7Y = java.lang.Double.NaN;
		double dbl8Y = java.lang.Double.NaN;
		double dbl9Y = java.lang.Double.NaN;
		double dbl10Y = java.lang.Double.NaN;
		double dbl12Y = java.lang.Double.NaN;
		double dbl15Y = java.lang.Double.NaN;
		double dbl20Y = java.lang.Double.NaN;
		double dbl25Y = java.lang.Double.NaN;
		double dbl30Y = java.lang.Double.NaN;
		double dbl40Y = java.lang.Double.NaN;

		try {
			strOracleCloseDate = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrIREOD[1].trim());

			dbl1D = java.lang.Double.parseDouble (astrIREOD[5].trim());

			dbl1M = java.lang.Double.parseDouble (astrIREOD[6].trim());

			dbl2M = java.lang.Double.parseDouble (astrIREOD[7].trim());

			dbl3M = java.lang.Double.parseDouble (astrIREOD[8].trim());

			dbl4M = java.lang.Double.parseDouble (astrIREOD[9].trim());

			dbl5M = java.lang.Double.parseDouble (astrIREOD[10].trim());

			dbl6M = java.lang.Double.parseDouble (astrIREOD[11].trim());

			dbl9M = java.lang.Double.parseDouble (astrIREOD[12].trim());

			dbl1Y = java.lang.Double.parseDouble (astrIREOD[13].trim());

			dbl18M = java.lang.Double.parseDouble (astrIREOD[14].trim());

			dbl2Y = java.lang.Double.parseDouble (astrIREOD[15].trim());

			dbl3Y = java.lang.Double.parseDouble (astrIREOD[16].trim());

			dbl4Y = java.lang.Double.parseDouble (astrIREOD[17].trim());

			dbl5Y = java.lang.Double.parseDouble (astrIREOD[18].trim());

			dbl6Y = java.lang.Double.parseDouble (astrIREOD[19].trim());

			dbl7Y = java.lang.Double.parseDouble (astrIREOD[20].trim());

			dbl8Y = java.lang.Double.parseDouble (astrIREOD[21].trim());

			dbl9Y = java.lang.Double.parseDouble (astrIREOD[22].trim());

			dbl10Y = java.lang.Double.parseDouble (astrIREOD[23].trim());

			dbl12Y = java.lang.Double.parseDouble (astrIREOD[24].trim());

			dbl15Y = java.lang.Double.parseDouble (astrIREOD[25].trim());

			dbl20Y = java.lang.Double.parseDouble (astrIREOD[26].trim());

			dbl25Y = java.lang.Double.parseDouble (astrIREOD[27].trim());

			dbl30Y = java.lang.Double.parseDouble (astrIREOD[28].trim());

			dbl40Y = java.lang.Double.parseDouble (astrIREOD[29].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad input marks for ccy " + strCurrency);

			return null;
		}

		java.lang.String str1DType = astrIREOD[30].trim();

		java.lang.String str1MType = astrIREOD[31].trim();

		java.lang.String str2MType = astrIREOD[32].trim();

		java.lang.String str3MType = astrIREOD[33].trim();

		java.lang.String str4MType = astrIREOD[34].trim();

		java.lang.String str5MType = astrIREOD[35].trim();

		java.lang.String str6MType = astrIREOD[36].trim();

		java.lang.String str9MType = astrIREOD[37].trim();

		java.lang.String str1YType = astrIREOD[38].trim();

		java.lang.String str18MType = astrIREOD[39].trim();

		java.lang.String str2YType = astrIREOD[40].trim();

		java.lang.String str3YType = astrIREOD[41].trim();

		java.lang.String str4YType = astrIREOD[42].trim();

		java.lang.String str5YType = astrIREOD[43].trim();

		java.lang.String str6YType = astrIREOD[44].trim();

		java.lang.String str7YType = astrIREOD[45].trim();

		java.lang.String str8YType = astrIREOD[46].trim();

		java.lang.String str9YType = astrIREOD[47].trim();

		java.lang.String str10YType = astrIREOD[48].trim();

		java.lang.String str12YType = astrIREOD[49].trim();

		java.lang.String str15YType = astrIREOD[50].trim();

		java.lang.String str20YType = astrIREOD[51].trim();

		java.lang.String str25YType = astrIREOD[52].trim();

		java.lang.String str30YType = astrIREOD[53].trim();

		java.lang.String str40YType = astrIREOD[54].trim();

		java.lang.StringBuilder sbInsertIREOD = new java.lang.StringBuilder();

		sbInsertIREOD.append ("insert into IR_EOD values(");

		sbInsertIREOD.append ("'").append (strOracleCloseDate).append ("', ");

		sbInsertIREOD.append ("'").append (strType).append ("', ");

		sbInsertIREOD.append ("'").append (strCurrency).append ("', ");

		sbInsertIREOD.append (dbl1D).append (", ");

		sbInsertIREOD.append (dbl1M).append (", ");

		sbInsertIREOD.append (dbl2M).append (", ");

		sbInsertIREOD.append (dbl3M).append (", ");

		sbInsertIREOD.append (dbl4M).append (", ");

		sbInsertIREOD.append (dbl5M).append (", ");

		sbInsertIREOD.append (dbl6M).append (", ");

		sbInsertIREOD.append (dbl9M).append (", ");

		sbInsertIREOD.append (dbl1Y).append (", ");

		sbInsertIREOD.append (dbl18M).append (", ");

		sbInsertIREOD.append (dbl2Y).append (", ");

		sbInsertIREOD.append (dbl3Y).append (", ");

		sbInsertIREOD.append (dbl4Y).append (", ");

		sbInsertIREOD.append (dbl5Y).append (", ");

		sbInsertIREOD.append (dbl6Y).append (", ");

		sbInsertIREOD.append (dbl7Y).append (", ");

		sbInsertIREOD.append (dbl8Y).append (", ");

		sbInsertIREOD.append (dbl9Y).append (", ");

		sbInsertIREOD.append (dbl10Y).append (", ");

		sbInsertIREOD.append (dbl12Y).append (", ");

		sbInsertIREOD.append (dbl15Y).append (", ");

		sbInsertIREOD.append (dbl20Y).append (", ");

		sbInsertIREOD.append (dbl25Y).append (", ");

		sbInsertIREOD.append (dbl30Y).append (", ");

		sbInsertIREOD.append (dbl40Y).append (", ");

		sbInsertIREOD.append ("'").append (str1DType).append ("', ");

		sbInsertIREOD.append ("'").append (str1MType).append ("', ");

		sbInsertIREOD.append ("'").append (str2MType).append ("', ");

		sbInsertIREOD.append ("'").append (str3MType).append ("', ");

		sbInsertIREOD.append ("'").append (str4MType).append ("', ");

		sbInsertIREOD.append ("'").append (str5MType).append ("', ");

		sbInsertIREOD.append ("'").append (str6MType).append ("', ");

		sbInsertIREOD.append ("'").append (str9MType).append ("', ");

		sbInsertIREOD.append ("'").append (str1YType).append ("', ");

		sbInsertIREOD.append ("'").append (str18MType).append ("', ");

		sbInsertIREOD.append ("'").append (str2YType).append ("', ");

		sbInsertIREOD.append ("'").append (str3YType).append ("', ");

		sbInsertIREOD.append ("'").append (str4YType).append ("', ");

		sbInsertIREOD.append ("'").append (str5YType).append ("', ");

		sbInsertIREOD.append ("'").append (str6YType).append ("', ");

		sbInsertIREOD.append ("'").append (str7YType).append ("', ");

		sbInsertIREOD.append ("'").append (str8YType).append ("', ");

		sbInsertIREOD.append ("'").append (str9YType).append ("', ");

		sbInsertIREOD.append ("'").append (str10YType).append ("', ");

		sbInsertIREOD.append ("'").append (str12YType).append ("', ");

		sbInsertIREOD.append ("'").append (str15YType).append ("', ");

		sbInsertIREOD.append ("'").append (str20YType).append ("', ");

		sbInsertIREOD.append ("'").append (str25YType).append ("', ");

		sbInsertIREOD.append ("'").append (str30YType).append ("', ");

		sbInsertIREOD.append ("'").append (str40YType).append ("')");

		return sbInsertIREOD.toString();
	}

	private static final java.lang.String ClearFromCREOD (
		final java.lang.String[] astrCREOD)
	{
		java.lang.String strSPN = astrCREOD[6].trim();

		java.lang.String strOracleCloseDate = "";

		try {
			strOracleCloseDate = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrCREOD[2].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad date " + astrCREOD[2] + " for " + strSPN);

			return null;
		}

		return "delete from CR_EOD where EOD = '" + strOracleCloseDate + "' and SPN = '" + strSPN + "'";
	}

	private static final java.lang.String InsertIntoCREOD (
		final java.lang.String[] astrCREOD)
	{
		java.lang.String strName = astrCREOD[4].trim();

		java.lang.String strSPN = astrCREOD[6].trim();

		java.lang.String strOracleCloseDate = "";
		double dblRecRate = java.lang.Double.NaN;
		double dblCR3M = java.lang.Double.NaN;
		double dblCR6M = java.lang.Double.NaN;
		double dblCR9M = java.lang.Double.NaN;
		double dblCR1Y = java.lang.Double.NaN;
		double dblCR18M = java.lang.Double.NaN;
		double dblCR2Y = java.lang.Double.NaN;
		double dblCR3Y = java.lang.Double.NaN;
		double dblCR4Y = java.lang.Double.NaN;
		double dblCR5Y = java.lang.Double.NaN;
		double dblCR6Y = java.lang.Double.NaN;
		double dblCR7Y = java.lang.Double.NaN;
		double dblCR8Y = java.lang.Double.NaN;
		double dblCR9Y = java.lang.Double.NaN;
		double dblCR10Y = java.lang.Double.NaN;
		double dblCR11Y = java.lang.Double.NaN;
		double dblCR12Y = java.lang.Double.NaN;
		double dblCR15Y = java.lang.Double.NaN;
		double dblCR20Y = java.lang.Double.NaN;
		double dblCR30Y = java.lang.Double.NaN;
		double dblCR40Y = java.lang.Double.NaN;

		try {
			strOracleCloseDate = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrCREOD[2].trim());

			dblRecRate = java.lang.Double.parseDouble (astrCREOD[7].trim());

			dblCR3M = java.lang.Double.parseDouble (astrCREOD[8].trim());

			dblCR6M = java.lang.Double.parseDouble (astrCREOD[9].trim());

			dblCR9M = java.lang.Double.parseDouble (astrCREOD[10].trim());

			dblCR1Y = java.lang.Double.parseDouble (astrCREOD[11].trim());

			dblCR18M = java.lang.Double.parseDouble (astrCREOD[12].trim());

			dblCR2Y = java.lang.Double.parseDouble (astrCREOD[13].trim());

			dblCR3Y = java.lang.Double.parseDouble (astrCREOD[14].trim());

			dblCR4Y = java.lang.Double.parseDouble (astrCREOD[15].trim());

			dblCR5Y = java.lang.Double.parseDouble (astrCREOD[16].trim());

			dblCR6Y = java.lang.Double.parseDouble (astrCREOD[17].trim());

			dblCR7Y = java.lang.Double.parseDouble (astrCREOD[18].trim());

			dblCR8Y = java.lang.Double.parseDouble (astrCREOD[19].trim());

			dblCR9Y = java.lang.Double.parseDouble (astrCREOD[20].trim());

			dblCR10Y = java.lang.Double.parseDouble (astrCREOD[21].trim());

			dblCR11Y = java.lang.Double.parseDouble (astrCREOD[22].trim());

			dblCR12Y = java.lang.Double.parseDouble (astrCREOD[23].trim());

			dblCR15Y = java.lang.Double.parseDouble (astrCREOD[26].trim());

			dblCR20Y = java.lang.Double.parseDouble (astrCREOD[31].trim());

			dblCR30Y = java.lang.Double.parseDouble (astrCREOD[37].trim());

			dblCR40Y = java.lang.Double.parseDouble (astrCREOD[39].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad input marks for SPN " + strSPN);

			e.printStackTrace();

			return null;
		}

		java.lang.StringBuilder sb = new java.lang.StringBuilder();

		sb.append ("insert into CR_EOD values(");

		sb.append ("'" + strOracleCloseDate + "', ");

		sb.append ("'" + strName + "', ");

		sb.append ("'" + strSPN + "', ");

		sb.append (dblRecRate + ", ");

		sb.append (dblCR3M + ", ");

		sb.append (dblCR6M + ", ");

		sb.append (dblCR9M + ", ");

		sb.append (dblCR1Y + ", ");

		sb.append (dblCR18M + ", ");

		sb.append (dblCR2Y + ", ");

		sb.append (dblCR3Y + ", ");

		sb.append (dblCR4Y + ", ");

		sb.append (dblCR5Y + ", ");

		sb.append (dblCR6Y + ", ");

		sb.append (dblCR7Y + ", ");

		sb.append (dblCR8Y + ", ");

		sb.append (dblCR9Y + ", ");

		sb.append (dblCR10Y + ", ");

		sb.append (dblCR11Y + ", ");

		sb.append (dblCR12Y + ", ");

		sb.append (dblCR15Y + ", ");

		sb.append (dblCR20Y + ", ");

		sb.append (dblCR30Y + ", ");

		sb.append (dblCR40Y + ")");

		return sb.toString();
	}

	private static java.lang.String makeSQLClearISIN2 (
		final java.lang.String[] astrBondRef2)
	{
		java.lang.String strISIN = astrBondRef2[0];

		if (null == strISIN || strISIN.isEmpty()) return null;

		return "delete from BondRef where ISIN = '" + strISIN + "'";
	}

	private static java.lang.String makeSQLInsert2 (
		final java.lang.String[] astrBondRef2)
	{
		java.lang.String strCUSIP = astrBondRef2[40];

		if (null == strCUSIP || strCUSIP.isEmpty()) return null;

		java.lang.String strISIN = astrBondRef2[0];

		if (null == strISIN || strISIN.isEmpty()) return null;

		java.lang.String strName = astrBondRef2[2];

		if (null == strName || strName.isEmpty()) return null;

		java.lang.String strDescription = astrBondRef2[2];

		if (null == strDescription || strDescription.isEmpty()) return null;

		System.out.println (astrBondRef2[1]);

		java.lang.String[] astrTicker = astrBondRef2[1].split (" ");

		java.lang.String strTicker = astrTicker[0];
		double dblCoupon = 0.;

		try {
			dblCoupon = java.lang.Double.parseDouble (astrBondRef2[11].trim());
		} catch (java.lang.Exception e) {
			if (m_bBlog) System.out.println ("Bad coupon " + astrBondRef2[11] + " for ISIN " + strISIN);
		}

		java.lang.String strOracleMaturity = "";

		try {
			strOracleMaturity = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrBondRef2[8].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad maturity " + astrBondRef2[8] + " for ISIN " + strISIN);

			return null;
		}

		java.lang.String strOracleFirstSettle = "";

		try {
			strOracleFirstSettle = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrBondRef2[6].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad first settle " + astrBondRef2[6] + " for ISIN " + strISIN);

			return null;
		}

		java.lang.String strOracleFirstCoupon = "";

		try {
			strOracleFirstCoupon = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrBondRef2[6].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad first coupon " + astrBondRef2[6] + " for ISIN " + strISIN);

			return null;
		}

		java.lang.String strOracleAnnounce = strOracleFirstCoupon;

		java.lang.String strCurrency = astrBondRef2[5];

		if (null == strCurrency || strCurrency.isEmpty()) return null;

		int iCouponFreq = 0;

		java.lang.String strCouponFreq = astrBondRef2[13];

		if ("A".equalsIgnoreCase (strCouponFreq))
			iCouponFreq = 1;
		else if ("M".equalsIgnoreCase (strCouponFreq))
			iCouponFreq = 12;
		else if ("Q".equalsIgnoreCase (strCouponFreq))
			iCouponFreq = 4;
		else if ("S".equalsIgnoreCase (strCouponFreq))
			iCouponFreq = 2;

		java.lang.String strDayCount = astrBondRef2[14];

		if (null == strDayCount || strDayCount.isEmpty()) return null;

		double dblFloatSpread = 0.;

		try {
			dblFloatSpread = java.lang.Double.parseDouble (astrBondRef2[12].trim());
		} catch (java.lang.Exception e) {
			if (m_bBlog)
				System.out.println ("Bad float spread " + astrBondRef2[12] + " for ISIN " + strISIN);
		}

		java.lang.String strRateIndex = "";
		java.lang.String strCalendar = astrBondRef2[5];

		if (strCalendar.startsWith (INVALID_BBG_FIELD_START)) strCalendar = "";

		java.lang.String strMoodys = "";
		java.lang.String strSnP = "";
		java.lang.String strFitch = "";
		java.lang.String strIndustrySector = "";
		java.lang.String strIndustryGroup = "";
		java.lang.String strSnrSub = "";
		java.lang.String strIssuer = astrBondRef2[2];
		double dblAmountIssued = 0.;
		double dblAmountOutstanding = 0.;
		java.lang.String strOracleIssue = "";

		try {
			strOracleIssue = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrBondRef2[6].trim());
		} catch (java.lang.Exception e) {
			if (m_bBlog) System.out.println ("Bad issue date " + astrBondRef2[6] + " for ISIN " + strISIN);
		}

		java.lang.String strCouponDates = "";
		java.lang.String strCouponFactors = "";
		java.lang.String strNotionalDates = "";
		java.lang.String strNotionalFactors = "";
		java.lang.String strIssuerSPN = astrBondRef2[3];
		java.lang.String strFloatQuote = "";

		java.lang.StringBuilder sb = new java.lang.StringBuilder();

		sb.append ("INSERT INTO BondRef VALUES (");

		sb.append ("'" + strISIN + "',");

		sb.append ("'" + strCUSIP + "',");

		sb.append ("'" + strName + "',");

		sb.append ("'" + strDescription + "',");

		sb.append ("'" + strTicker + "',");

		sb.append (dblCoupon + ",");

		sb.append ("'" + strOracleMaturity + "',");

		sb.append ("'" + strOracleAnnounce + "',");

		sb.append ("'" + strOracleFirstSettle + "',");

		sb.append ("'" + strOracleFirstCoupon + "',");

		sb.append ("'" + strCurrency + "',");

		sb.append (iCouponFreq + ",");

		sb.append ("'" + strDayCount + "',");

		sb.append (dblFloatSpread + ",");

		sb.append ("'" + strRateIndex + "',");

		sb.append ("'" + strCalendar + "',");

		sb.append ("'" + strMoodys + "',");

		sb.append ("'" + strSnP + "',");

		sb.append ("'" + strFitch + "',");

		sb.append ("'" + strIndustrySector + "',");

		sb.append ("'" + strIndustryGroup + "',");

		sb.append ("'" + strSnrSub + "',");

		sb.append ("'" + strIssuer + "',");

		sb.append (dblAmountIssued + ",");

		sb.append (dblAmountOutstanding + ",");

		sb.append ("'" + strOracleIssue + "',");

		sb.append ("'" + strCouponDates + "',");

		sb.append ("'" + strCouponFactors + "',");

		sb.append ("'" + strNotionalDates + "',");

		sb.append ("'" + strNotionalFactors + "',");

		sb.append ("'" + strIssuerSPN + "',");

		sb.append ("'" + strFloatQuote + "')");

		return sb.toString();
	}

	private static java.lang.String makeSQLClearAmortScheduleEntry (
		final java.lang.String[] astrAmortizationSchedule)
	{
		if (null == astrAmortizationSchedule || 5 != astrAmortizationSchedule.length) return null;

		java.lang.String strOracleAmortizationDate = "";

		java.lang.String strCUSIP = astrAmortizationSchedule[1].trim();

		if (null == strCUSIP || strCUSIP.isEmpty()) return null;

		java.lang.StringBuilder sb = new java.lang.StringBuilder();

		try {
			strOracleAmortizationDate = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrAmortizationSchedule[2].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad date " + astrAmortizationSchedule[2] + " for CUSIP " + strCUSIP);

			return null;
		}

		sb.append ("delete from AmortizationSchedule where CUSIP = '").append (strCUSIP).append
			("' and AmortDate = '").append (strOracleAmortizationDate).append ("'");

		return sb.toString();
	}

	private static final java.lang.String InsertIntoAmortizationSchedule (
		final java.lang.String[] astrAmortizationSchedule)
	{
		if (null == astrAmortizationSchedule || 5 != astrAmortizationSchedule.length) return null;

		java.lang.String strCUSIP = astrAmortizationSchedule[1].trim();

		java.lang.String strOracleAmortizationDate = "";
		double dblPrincipalPaydown = java.lang.Double.NaN;

		try {
			strOracleAmortizationDate = org.drip.analytics.date.DateUtil.MakeOracleDateFromBBGDate
				(astrAmortizationSchedule[2].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad date " + astrAmortizationSchedule[2] + " for CUSIP " + strCUSIP);

			return null;
		}

		try {
			dblPrincipalPaydown = java.lang.Double.parseDouble (astrAmortizationSchedule[4].trim());
		} catch (java.lang.Exception e) {
			System.out.println ("Bad paydown factor " + astrAmortizationSchedule[4] + " for CUSIP " +
				strCUSIP);

			return null;
		}

		return "insert into AmortizationSchedule values('" + strCUSIP + "', '" + strOracleAmortizationDate +
			"', " + dblPrincipalPaydown + ")";
	}

	private static void LoadBondRef()
		throws java.lang.Exception
	{
		/* int iNumBonds = 0;
		java.lang.String strValDataLine = "";
		java.io.BufferedReader inValData = null;

		System.out.println ("Loading BondRef ...");

		oracle.jdbc.pool.OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();

		ds.setDriverType ("thin");

		ds.setServerName ("localhost");

		ds.setPortNumber (1521);

		ds.setDatabaseName ("XE");

		ds.setUser ("hr");

		ds.setPassword ("hr");

		java.sql.Connection con = ds.getConnection();

		java.sql.Statement stmt = con.createStatement();

		try {
			inValData = new java.io.BufferedReader (new java.io.FileReader
				("c:\\Lakshmi\\java\\BondAnal\\Data\\BondStaticRef\\FullTickers_Base\\RefDataSet.csv"));
		} catch (java.lang.Exception e) {
			System.out.println ("Cannot open Val Data record file");

			return;
		}

		while (null != (strValDataLine = inValData.readLine())) {
			int iNumRecord = 0;
			java.lang.String astrValRecord[] = new java.lang.String[33];

			java.util.StringTokenizer stValData = new java.util.StringTokenizer (strValDataLine, ",");

			while (stValData.hasMoreTokens()) {
				java.lang.String stValDataField = stValData.nextToken();

				astrValRecord[iNumRecord++] = stValDataField;
			}

			java.lang.String strSQLClear = makeSQLClearISINOrCUSIP (astrValRecord);

			if (null != strSQLClear) stmt.executeUpdate (strSQLClear);

			java.lang.String strSQLInsert = makeSQLInsert (astrValRecord);

			if (null != strSQLInsert) {
				if (m_bBlog) System.out.println ("SQL[" + iNumBonds + "]: " + strSQLInsert);

				stmt.executeUpdate (strSQLInsert);
			}

			++iNumBonds;
		}

		inValData.close();

		System.out.println ("Loaded " + iNumBonds + " into BondRef."); */
	}

	private static void LoadBondRef2()
		throws java.lang.Exception
	{
		int iNumBonds = 0;
		java.lang.String strBondRef2Line = "";
		java.io.BufferedReader inBondRef2 = null;

		System.out.println ("Loading BondRef2 ...");

		java.sql.Statement stmt = org.drip.param.config.ConfigLoader.OracleInit
			("c:\\Lakshmi\\java\\BondAnal\\Config.xml");

		try {
			inBondRef2 = new java.io.BufferedReader (new java.io.FileReader
				("C:\\Lakshmi\\RefDataAndMarks\\17Jan2012\\Bond.csv"));
		} catch (java.lang.Exception e) {
			System.out.println ("Cannot open BondRef record file");

			return;
		}

		while (null != (strBondRef2Line = inBondRef2.readLine())) {
			java.lang.String[] astrBondRef2Record = strBondRef2Line.split (",");

			java.lang.String strSQLClear = makeSQLClearISIN2 (astrBondRef2Record);

			if (null != strSQLClear) stmt.executeUpdate (strSQLClear);

			java.lang.String strSQLInsert = makeSQLInsert2 (astrBondRef2Record);

			if (null != strSQLInsert) {
				if (m_bBlog) System.out.println ("SQL[" + iNumBonds + "]: " + strSQLInsert);

				stmt.executeUpdate (strSQLInsert);
			}

			++iNumBonds;
		}

		inBondRef2.close();

		System.out.println ("Loaded " + iNumBonds + " into BondRef2");
	}

	private static void LoadBondRef_FODATA()
		throws java.lang.Exception
	{
		int iNumBonds = 0;
		java.lang.String strBondRefLine = "";
		java.io.BufferedReader inBondRef = null;

		System.out.println ("Loading BondRef_FODATA ...");

		java.sql.Statement stmt = org.drip.param.config.ConfigLoader.OracleInit
			("c:\\Lakshmi\\java\\BondAnal\\Config.xml");

		try {
			inBondRef = new java.io.BufferedReader (new java.io.FileReader
				("C:\\Lakshmi\\RefDataAndMarks\\17Jan2012\\Bond.csv"));
		} catch (java.lang.Exception e) {
			System.out.println ("Cannot open BondRef record file");

			return;
		}

		while (null != (strBondRefLine = inBondRef.readLine())) {
			java.lang.String[] astrBondRefRecord = strBondRefLine.split (",");

			java.lang.String strSQLClear = makeSQLClear_FODATA (astrBondRefRecord);

			if (null != strSQLClear) stmt.executeUpdate (strSQLClear);

			java.lang.String strSQLInsert = makeSQLInsert_FODATA (astrBondRefRecord);

			if (null != strSQLInsert) {
				if (m_bBlog) System.out.println ("SQL[" + iNumBonds + "]: " + strSQLInsert);

				stmt.executeUpdate (strSQLInsert);
			}

			++iNumBonds;
		}

		inBondRef.close();

		System.out.println ("Loaded " + iNumBonds + " into BondRef_FODATA");
	}

	private static void LoadBondMarks()
		throws java.lang.Exception
	{
		int iNumMarks = 0;
		java.lang.String strMarksLine = "";
		java.io.BufferedReader inMarks = null;

		System.out.println ("Loading BondMark ...");

		java.sql.Statement stmt = org.drip.param.config.ConfigLoader.OracleInit
			("c:\\Lakshmi\\java\\BondAnal\\Config.xml");

		try {
			inMarks = new java.io.BufferedReader (new java.io.FileReader
				("c:\\Lakshmi\\java\\BondAnal\\Data\\BondStaticRef\\Marks\\MatrixPricer_Prices_0715.csv"));
		} catch (java.lang.Exception e) {
			System.out.println ("Cannot open marks file");

			return;
		}

		while (null != (strMarksLine = inMarks.readLine())) {
			int iNumMarksRecord = 0;
			java.lang.String astrMarks[] = new java.lang.String[9];

			java.util.StringTokenizer stMarks = new java.util.StringTokenizer (strMarksLine, ",");

			while (stMarks.hasMoreTokens()) {
				java.lang.String strMarks = stMarks.nextToken();

				astrMarks[iNumMarksRecord++] = strMarks;
			}

			java.lang.String strSQLClearMarks = makeSQLClearMarksForBondDate (astrMarks);

			if (null != strSQLClearMarks) {
				if (m_bBlog) System.out.println ("SQL[" + iNumMarks + "]: " + strSQLClearMarks);

				stmt.executeUpdate (strSQLClearMarks);
			}

			java.lang.String strSQLInsertMarks = makeSQLBondMarksInsert (astrMarks);

			if (null != strSQLInsertMarks) {
				if (m_bBlog) System.out.println ("SQL[" + iNumMarks + "]: " + strSQLInsertMarks);

				stmt.executeUpdate (strSQLInsertMarks);
			}

			++iNumMarks;
		}

		inMarks.close();

		System.out.println ("Loaded " + iNumMarks + " into BondMark.");
	}

	private static void LoadHolidayCalendar (
		final java.lang.String strHolLoc,
		final java.lang.String strHolLocFile)
		throws java.lang.Exception
	{
		java.lang.String strHolsLine = "";
		java.io.BufferedReader inHolCal = null;

		System.out.println ("Loading " + strHolLoc + " from " + strHolLocFile);

		java.sql.Statement stmt = org.drip.param.config.ConfigLoader.OracleInit
			("c:\\Lakshmi\\java\\BondAnal\\Config.xml");

		try {
			inHolCal = new java.io.BufferedReader (new java.io.FileReader (strHolLocFile));
		} catch (java.lang.Exception e) {
			System.out.println ("Cannot open hol cal file");

			return;
		}

		while (null != (strHolsLine = inHolCal.readLine())) {
			java.util.StringTokenizer stHolFields = new java.util.StringTokenizer (strHolsLine, "	");

			try {
				int iFieldNum = 0;
				java.lang.String[] astrHolFields = new java.lang.String[2];
				astrHolFields[0] = "";
				astrHolFields[1] = "";
				java.lang.String strORAHoliday = "";

				while (stHolFields.hasMoreTokens() && iFieldNum < astrHolFields.length)
					astrHolFields[iFieldNum++] = stHolFields.nextToken();

				java.lang.String strTextHol = astrHolFields[0].trim();

				try {
					strORAHoliday = org.drip.analytics.date.DateUtil.MakeOracleDateFromYYYYMMDD (strTextHol);
				} catch (java.lang.Exception e) {
					java.util.StringTokenizer stTextHol = new java.util.StringTokenizer (strTextHol, " ");

					strORAHoliday = org.drip.analytics.date.DateUtil.MakeOracleDateFromYYYYMMDD
						(stTextHol.nextToken().trim());
				}

				java.lang.String strDescription = astrHolFields[1].trim();

				if (null != strDescription && !strDescription.isEmpty())
					strDescription = strDescription.replaceAll ("'", "");
				else
					strDescription = "";

				stmt.executeUpdate ("delete from Holidays where Location = '" + strHolLoc +
					"' and Holiday = '" + strORAHoliday + "'");

				stmt.executeUpdate ("insert into Holidays values('" + strHolLoc + "', '" + strORAHoliday +
					"', '" + strDescription + "')");
			} catch (java.lang.Exception e) {
				System.out.println (strHolLoc + ": Check line " + strHolsLine);
			}
		}

		inHolCal.close();
	}

	private static void LoadHolsToHolderSource (
		final java.io.BufferedWriter bw,
		final java.lang.String strHolLoc,
		final java.lang.String strHolLocFile)
		throws java.lang.Exception
	{
		java.lang.String strHolsLine = "";
		java.io.BufferedReader inHolCal = null;

		System.out.println ("Loading " + strHolLoc + " from " + strHolLocFile);

		try {
			inHolCal = new java.io.BufferedReader (new java.io.FileReader (strHolLocFile));
		} catch (java.lang.Exception e) {
			System.out.println ("Cannot open hol cal file");

			return;
		}

		while (null != (strHolsLine = inHolCal.readLine())) {
			java.util.StringTokenizer stHolFields = new java.util.StringTokenizer (strHolsLine, "	");

			try {
				java.lang.String[] astrHolFields = new java.lang.String[2];
				java.lang.String strORAHoliday = "";
				astrHolFields[0] = "";
				astrHolFields[1] = "";
				int iFieldNum = 0;

				while (stHolFields.hasMoreTokens() && iFieldNum < astrHolFields.length)
					astrHolFields[iFieldNum++] = stHolFields.nextToken();

				java.lang.String strTextHol = astrHolFields[0].trim();

				try {
					strORAHoliday = org.drip.analytics.date.DateUtil.MakeOracleDateFromYYYYMMDD (strTextHol);
				} catch (java.lang.Exception e) {
					java.util.StringTokenizer stTextHol = new java.util.StringTokenizer (strTextHol, " ");

					strORAHoliday = org.drip.analytics.date.DateUtil.MakeOracleDateFromYYYYMMDD
						(stTextHol.nextToken().trim());
				}

				java.lang.String strDescription = astrHolFields[1].trim();

				if (null != strDescription && !strDescription.isEmpty())
					strDescription = strDescription.replaceAll ("'", "");
				else
					strDescription = "";

				java.lang.String strLocHolDates = "lh.addStaticHoliday (\"" + strORAHoliday + "\", \"" +
					strDescription + "\");";

				if (m_bBlog) System.out.println (strLocHolDates);

				bw.write ("\t\t" + strLocHolDates + "\n\n");
			} catch (java.lang.Exception e) {
				System.out.println (strHolLoc + ": Check line " + strHolsLine);
			}
		}

		bw.flush();

		inHolCal.close();
	}

	private static final void LoadHolCals()
		throws java.lang.Exception
	{
		java.lang.String strHolDir = "c:\\Lakshmi\\java\\BondAnal\\Data\\Holidays\\";

		java.io.File f = new java.io.File (strHolDir);

		java.lang.String[] astrFile = f.list();

		for (int i = 0; i < astrFile.length; ++i) {
			java.util.StringTokenizer stLoc = new java.util.StringTokenizer (astrFile[i], ".");

			java.lang.String strPrefix = stLoc.nextToken();

			if (!"holiday".equalsIgnoreCase (strPrefix)) continue;

			LoadHolidayCalendar (stLoc.nextToken().toUpperCase(), strHolDir + astrFile[i]);
		}
	}

	private static final void AccumulateHolidays()
		throws java.lang.Exception
	{
		java.lang.String strHolDir = "c:\\Lakshmi\\BondAnal\\Data\\Holidays\\";

		java.io.File f = new java.io.File (strHolDir);

		java.lang.String[] astrFile = f.list();

		for (int i = 0; i < astrFile.length; ++i) {
			java.util.StringTokenizer stLoc = new java.util.StringTokenizer (astrFile[i], ".");

			java.lang.String strPrefix = stLoc.nextToken();

			if (!"holiday".equalsIgnoreCase (strPrefix)) continue;

			java.lang.String strSuffix = stLoc.nextToken().toUpperCase();

			java.io.BufferedWriter bw = new java.io.BufferedWriter (new java.io.FileWriter
				("c:\\DRIP\\CreditAnalytics\\org\\drip\\analytics\\holset\\" + strSuffix + "Holiday.java"));

			bw.write ("\npackage org.drip.analytics.holset;\n\n");

			bw.write
				("/*\n * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-\n */\n");

			bw.write ("\n/*\n *    GENERATED on " + new java.util.Date().toString() +
				" ---- DO NOT DELETE\n */\n");

			bw.write ("\n/*!\n * Copyright (C) 2013 Lakshmi Krishnamurthy\n");

			bw.write (" * Copyright (C) 2012 Lakshmi Krishnamurthy\n");

			bw.write (" * Copyright (C) 2011 Lakshmi Krishnamurthy\n *\n");

			bw.write (" * This file is part of CreditAnalytics, a free-software/open-source library for\n");

			bw.write (" *		fixed income analysts and developers - http://www.credit-trader.org\n *\n");

			bw.write (" * CreditAnalytics is a free, full featured, fixed income credit analytics library,");

			bw.write (" developed with a special focus\n");

			bw.write (" * 		towards the needs of the bonds and credit products community.\n *\n");

			bw.write (" *  Licensed under the Apache License, Version 2.0 (the \"License\");\n");

			bw.write (" *   	you may not use this file except in compliance with the License.\n");

			bw.write (" *\n *  You may obtain a copy of the License at\n");

			bw.write (" *  	http://www.apache.org/licenses/LICENSE-2.0\n *\n");

			bw.write (" *  Unless required by applicable law or agreed to in writing, software\n");

			bw.write (" *  	distributed under the License is distributed on an \"AS IS\" BASIS,\n");

			bw.write (" *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n");

			bw.write (" *  \n *  See the License for the specific language governing permissions and\n");

			bw.write (" *  	limitations under the License.\n */\n\n");

			bw.write ("public class " + strSuffix +
				"Holiday implements org.drip.analytics.holset.LocationHoliday {\n");

			bw.write ("\tpublic " + strSuffix + "Holiday()\n\t{\n\t}\n\n");

			bw.write ("\tpublic java.lang.String getHolidayLoc()\n\t{\n");

			bw.write ("\t\treturn \"" + strSuffix + "\";\n\t}\n\n");

			bw.write ("\tpublic org.drip.analytics.holiday.Locale getHolidaySet()\n\t{");

			bw.write ("\n\t\torg.drip.analytics.holiday.Locale lh = new\n");

			bw.write ("\t\t\torg.drip.analytics.holiday.Locale();\n\n");

			LoadHolsToHolderSource (bw, strSuffix, strHolDir + astrFile[i]);

			bw.write ("\t\tlh.addStandardWeekend();\n\n");

			bw.write ("\t\treturn lh;\n\t}\n}\n");

			bw.close();
		}
	}

	private static final void LoadEOS()
		throws java.lang.Exception
	{
		int iNumSchedules = 0;
		java.lang.String strEOSLine = "";
		java.io.BufferedReader inEOS = null;

		java.sql.Statement stmt = org.drip.param.config.ConfigLoader.OracleInit
			("c:\\Lakshmi\\BondAnal\\Config.xml");

		System.out.println ("Loading EOS ...");

		try {
			inEOS = new java.io.BufferedReader (new java.io.FileReader
				("C:\\Lakshmi\\RefDataAndMarks\\17Jan2012\\OptionSchedule.csv"));
		} catch (java.lang.Exception e) {
			System.out.println ("Cannot open EOS file");

			return;
		}

		while (null != (strEOSLine = inEOS.readLine())) {
			int iNumRecord = 0;
			java.lang.String astrEOS[] = new java.lang.String[14];

			java.util.StringTokenizer stEOS = new java.util.StringTokenizer (strEOSLine, ",");

			while (stEOS.hasMoreTokens()) {
				java.lang.String strEOS = stEOS.nextToken();

				astrEOS[iNumRecord++] = strEOS;
			}

			java.lang.String strClearEOS = ClearISINDateTypeFromEOS (astrEOS);

			if (null != strClearEOS && !strClearEOS.isEmpty()) stmt.executeQuery (strClearEOS);

			java.lang.String strInsertEOS = InsertIntoEOS (astrEOS);

			if (null != strInsertEOS && !strInsertEOS.isEmpty())
				stmt.executeQuery (strInsertEOS);

			++iNumSchedules;
		}

		inEOS.close();

		System.out.println ("Loaded " + iNumSchedules + " into EOS.");
	}

	private static final void LoadAmortizationSchedule()
		throws java.lang.Exception
	{
		int iNumSchedules = 0;
		java.lang.String strAmortizationScheduleLine = "";
		java.io.BufferedReader inAmortizationSchedule = null;

		java.sql.Statement stmt = org.drip.param.config.ConfigLoader.OracleInit
			("c:\\Lakshmi\\java\\BondAnal\\Config.xml");

		System.out.println ("Loading AmortizationSchedule ...");

		try {
			inAmortizationSchedule = new java.io.BufferedReader (new java.io.FileReader
				("C:\\Lakshmi\\RefDataAndMarks\\17Jan2012\\AmortizationSchedule.csv"));
		} catch (java.lang.Exception e) {
			System.out.println ("Cannot open AmortizationSchedule file");

			return;
		}

		while (null != (strAmortizationScheduleLine = inAmortizationSchedule.readLine())) {
			int iNumRecord = 0;
			java.lang.String astrAmortizationSchedule[] = new java.lang.String[5];

			java.util.StringTokenizer stAmortizationSchedule = new java.util.StringTokenizer
				(strAmortizationScheduleLine, ",");

			while (stAmortizationSchedule.hasMoreTokens()) {
				java.lang.String strAmortizationSchedule = stAmortizationSchedule.nextToken();

				astrAmortizationSchedule[iNumRecord++] = strAmortizationSchedule;
			}

			java.lang.String strClearAmortizationSchedule = makeSQLClearAmortScheduleEntry
				(astrAmortizationSchedule);

			if (null != strClearAmortizationSchedule && !strClearAmortizationSchedule.isEmpty())
				stmt.executeQuery (strClearAmortizationSchedule);

			java.lang.String strInsertAmortizationSchedule = InsertIntoAmortizationSchedule
				(astrAmortizationSchedule);

			if (null != strInsertAmortizationSchedule && !strInsertAmortizationSchedule.isEmpty())
				stmt.executeQuery (strInsertAmortizationSchedule);

			++iNumSchedules;
		}

		inAmortizationSchedule.close();

		System.out.println ("Loaded " + iNumSchedules + " into AmortizationSchedule.");
	}

	private static final void LoadIREOD()
		throws java.lang.Exception
	{
		int iNumIREOD = 0;
		java.lang.String strIREODLine = "";
		java.io.BufferedReader inIREOD = null;

		System.out.println ("Loading IREOD ...");

		java.sql.Statement stmt = org.drip.param.config.ConfigLoader.OracleInit
			("c:\\Lakshmi\\BondAnal\\Config.xml");

		try {
			inIREOD = new java.io.BufferedReader (new java.io.FileReader
				("C:\\Lakshmi\\RefDataAndMarks\\17Jan2012\\EODIRCurves.csv"));
		} catch (java.lang.Exception e) {
			System.out.println ("Cannot open IREOD file");

			return;
		}

		while (null != (strIREODLine = inIREOD.readLine())) {
			int iNumRecord = 0;
			java.lang.String astrIREOD[] = new java.lang.String[58];

			java.util.StringTokenizer stIREOD = new java.util.StringTokenizer (strIREODLine, ",");

			while (stIREOD.hasMoreTokens())
				astrIREOD[iNumRecord++] = stIREOD.nextToken();

			java.lang.String strClearIREOD = ClearFromIREOD (astrIREOD);

			if (null != strClearIREOD && !strClearIREOD.isEmpty()) stmt.executeQuery (strClearIREOD);

			java.lang.String strInsertIREOD = InsertIntoIREOD (astrIREOD);

			if (null != strInsertIREOD && !strInsertIREOD.isEmpty()) stmt.executeQuery (strInsertIREOD);

			++iNumIREOD;
		}

		inIREOD.close();

		System.out.println ("Loaded " + iNumIREOD + " into IR_EOD.");
	}

	private static final void LoadBondSPN()
		throws java.lang.Exception
	{
		int iNumSPN = 0;
		java.lang.String strSPNLine = "";
		java.io.BufferedReader inSPN = null;

		System.out.println ("Loading SPN ...");

		java.sql.Statement stmt = org.drip.param.config.ConfigLoader.OracleInit
			("c:\\Lakshmi\\java\\BondAnal\\Config.xml");

		try {
			inSPN = new java.io.BufferedReader (new java.io.FileReader
				("c:\\Lakshmi\\BondAnal\\Data\\BondStaticRef\\Closes\\Bond.csv"));
		} catch (java.lang.Exception e) {
			System.out.println ("Cannot open SPN file");

			return;
		}

		while (null != (strSPNLine = inSPN.readLine())) {
			java.lang.String[] astrSPN = strSPNLine.split (",");

			java.lang.String strISIN = astrSPN[0];
			java.lang.String strSPN = astrSPN[3];

			if (null != strSPN && !strSPN.isEmpty() && null != strSPN && !strSPN.isEmpty()) {
				stmt.executeQuery ("update BondRef set IssuerSPN = '" + strSPN + "' where ISIN = '" + strISIN
					+ "'");

				++iNumSPN;
			}
		}

		System.out.println ("Loaded " + iNumSPN + " SPNs into BondRef.");

		inSPN.close();
	}

	private static final void LoadCREOD()
		throws java.lang.Exception
	{
		int iNumCREOD = 0;
		java.lang.String strCREODLine = "";
		java.io.BufferedReader inCREOD = null;

		System.out.println ("Loading CREOD ...");

		java.sql.Statement stmt = org.drip.param.config.ConfigLoader.OracleInit
			("c:\\Lakshmi\\BondAnal\\Config.xml");

		try {
			inCREOD = new java.io.BufferedReader (new java.io.FileReader
				("c:\\Lakshmi\\BondAnal\\Data\\BondStaticRef\\Closes\\EODCR_2Q_2011.csv"));
		} catch (java.lang.Exception e) {
			System.out.println ("Cannot open CREOD file");

			return;
		}

		while (null != (strCREODLine = inCREOD.readLine())) {
			java.lang.String[] astrCREOD = strCREODLine.split (",");

			java.lang.String strClearCREOD = ClearFromCREOD (astrCREOD);

			if (null != strClearCREOD && !strClearCREOD.isEmpty()) stmt.executeQuery (strClearCREOD);

			java.lang.String strInsertCREOD = InsertIntoCREOD (astrCREOD);

			if (null != strInsertCREOD && !strInsertCREOD.isEmpty()) {
				System.out.println (strInsertCREOD);

				stmt.executeQuery (strInsertCREOD);
			}

			++iNumCREOD;
		}

		System.out.println ("Loaded " + iNumCREOD + " into CREOD.");

		inCREOD.close();
	}

	public static void main (
		final java.lang.String[] astrArgs)
		throws java.lang.Exception
	{
		if (0 < astrArgs.length && "-loadbondref2".equalsIgnoreCase (astrArgs[0])) {
			LoadBondRef2();

			return;
		}

		if (0 < astrArgs.length && "-loadbondref_fodata".equalsIgnoreCase (astrArgs[0])) {
			LoadBondRef_FODATA();

			return;
		}

		if (0 < astrArgs.length && "-bondmarks".equalsIgnoreCase (astrArgs[0])) {
			LoadBondMarks();

			return;
		}

		if (0 < astrArgs.length && "-holcals".equalsIgnoreCase (astrArgs[0])) {
			LoadHolCals();

			return;
		}

		if (0 < astrArgs.length && "-acchols".equalsIgnoreCase (astrArgs[0])) {
			AccumulateHolidays();

			return;
		}

		if (0 < astrArgs.length && "-eos".equalsIgnoreCase (astrArgs[0])) {
			LoadEOS();

			return;
		}

		if (0 < astrArgs.length && "-amortsched".equalsIgnoreCase (astrArgs[0])) {
			LoadAmortizationSchedule();

			return;
		}

		if (0 < astrArgs.length && "-ireod".equalsIgnoreCase (astrArgs[0])) {
			LoadIREOD();

			return;
		}

		if (0 < astrArgs.length && "-creod".equalsIgnoreCase (astrArgs[0])) {
			LoadCREOD();

			return;
		}

		if (0 < astrArgs.length && "-spn".equalsIgnoreCase (astrArgs[0])) {
			LoadBondSPN();

			return;
		}

		LoadBondRef();

		LoadEOS();

		LoadBondSPN();
	}
}
