
package org.drip.feed.loader;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>CDXRefData</i> contains the functionality to load the standard CDX reference data and definitions, and
 * create compile time static classes for these definitions.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Load, Transform, and compute Target Metrics across Feeds</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/loader/README.md">Reference/Market Data Feed Loader</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CDXRefData {
	private static final boolean s_bBlog = false;
	private static final boolean s_bSuppressErr = true;
	private static final boolean s_bPrintCDXRefDataDump = false;

	private static final org.drip.product.params.CDXRefDataParams CreateCDXRefDataFromRecord (
		final java.lang.String[] astrArgs,
		final java.io.BufferedWriter bw)
	{
		if (null == astrArgs || 43 != astrArgs.length) System.exit (333);

		java.lang.String strCurveID = org.drip.service.common.StringUtil.ProcessInputForNULL (astrArgs[0],
			false);

		if (s_bBlog) System.out.println ("Curve ID: " + strCurveID);

		java.lang.String strSPN = org.drip.service.common.StringUtil.ProcessInputForNULL (astrArgs[1], false);

		if (s_bBlog) System.out.println ("SPN: " + strSPN);

		java.lang.String strIndexLabel = org.drip.service.common.StringUtil.ProcessInputForNULL (astrArgs[2],
			false);

		if (s_bBlog) System.out.println ("Index Label: " + strIndexLabel);

		java.lang.String strIndexName = org.drip.service.common.StringUtil.ProcessInputForNULL (astrArgs[3],
			false);

		if (s_bBlog) System.out.println ("Index Name: " + strIndexName);

		java.lang.String strCurveName = org.drip.service.common.StringUtil.ProcessInputForNULL (astrArgs[4],
			false);

		if (s_bBlog) System.out.println ("Curve Name: " + strCurveName);

		org.drip.analytics.date.JulianDate dtIssueDate =
			org.drip.analytics.date.DateUtil.MakeJulianFromDDMMMYY
				(org.drip.service.common.StringUtil.ProcessInputForNULL (astrArgs[5], false), "-");

		if (null == dtIssueDate) {
			System.out.println ("Bad Issue Date for Curve ID " + strCurveID);

			return null;
		}

		if (s_bBlog) System.out.println ("Issue Date: " + dtIssueDate);

		org.drip.analytics.date.JulianDate dtMaturityDate =
			org.drip.analytics.date.DateUtil.MakeJulianFromDDMMMYY
				(org.drip.service.common.StringUtil.ProcessInputForNULL (astrArgs[6], false), "-");

		if (null == dtMaturityDate) {
			System.out.println ("Bad Maturity Date for Curve ID " + strCurveID);

			return null;
		}

		if (s_bBlog) System.out.println ("Maturity Date: " + dtMaturityDate);

		double dblCoupon = java.lang.Double.NaN;

		try {
			dblCoupon = 0.0001 * java.lang.Double.parseDouble (astrArgs[7]);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErr) e.printStackTrace();

			dblCoupon = 0.;
		}

		if (s_bBlog) System.out.println ("Coupon: " + dblCoupon);

		java.lang.String strCurrency = org.drip.service.common.StringUtil.ProcessInputForNULL (astrArgs[8],
			false);

		if (s_bBlog) System.out.println ("Currency: " + strCurrency);

		java.lang.String strDayCount = org.drip.service.common.StringUtil.ProcessInputForNULL (astrArgs[9],
			false);

		if (s_bBlog) System.out.println ("DayCount: " + strDayCount);

		java.lang.String strFullFirstStub = org.drip.service.common.StringUtil.ProcessInputForNULL
			(astrArgs[10], false);

		boolean bFullFirstStub = "lf".equalsIgnoreCase (strFullFirstStub);

		if (s_bBlog) System.out.println ("Full First Stub: " + bFullFirstStub);

		double dblRecovery = java.lang.Double.NaN;

		try {
			dblRecovery = 0.01 * java.lang.Double.parseDouble (astrArgs[11]);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErr) e.printStackTrace();

			dblRecovery = 0.;
		}

		if (s_bBlog) System.out.println ("Recovery: " + dblRecovery);

		int iFrequency = 0;

		java.lang.String strFrequency = org.drip.service.common.StringUtil.ProcessInputForNULL (astrArgs[12],
			false);

		if ("Q".equalsIgnoreCase (strFrequency))
			iFrequency = 4;
		else if ("S".equalsIgnoreCase (strFrequency))
			iFrequency = 2;

		if (s_bBlog) System.out.println ("Frequency: " + iFrequency);

		java.lang.String strRedID = org.drip.service.common.StringUtil.ProcessInputForNULL (astrArgs[13],
			false);

		if (s_bBlog) System.out.println ("RedID: " + strRedID);

		java.lang.String strIndexClass = org.drip.service.common.StringUtil.ProcessInputForNULL (astrArgs[14],
			false);

		if (s_bBlog) System.out.println ("Index Class: " + strIndexClass);

		int iIndexSeries = 0;

		try {
			iIndexSeries = java.lang.Integer.parseInt (astrArgs[15]);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErr) e.printStackTrace();
		}

		if (s_bBlog) System.out.println ("Index Series: " + iIndexSeries);

		java.lang.String strIndexGroupName = org.drip.service.common.StringUtil.ProcessInputForNULL
			(astrArgs[16], false);

		if (s_bBlog) System.out.println ("Index Group Name: " + strIndexGroupName);

		java.lang.String strIndexShortName = org.drip.service.common.StringUtil.ProcessInputForNULL
			(astrArgs[17], false);

		if (s_bBlog) System.out.println ("Index Short Name: " + strIndexShortName);

		java.lang.String strIndexShortGroupName = org.drip.service.common.StringUtil.ProcessInputForNULL
			(astrArgs[18], false);

		if (s_bBlog) System.out.println ("Index Short Group Name: " + strIndexShortGroupName);

		int iIndexVersion = 0;

		try {
			iIndexVersion = java.lang.Integer.parseInt (astrArgs[19]);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErr) e.printStackTrace();
		}

		if (s_bBlog) System.out.println ("Index Version: " + iIndexVersion);

		int iIndexLifeSpan = 0;

		try {
			iIndexLifeSpan = java.lang.Integer.parseInt (astrArgs[20]);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErr) e.printStackTrace();
		}

		if (s_bBlog) System.out.println ("Index Life Span: " + iIndexLifeSpan);

		java.lang.String strCurvyCurveID = org.drip.service.common.StringUtil.ProcessInputForNULL (astrArgs[21],
			false);

		if (s_bBlog) System.out.println ("Curvy Curve ID: " + strCurvyCurveID);

		double dblIndexFactor = java.lang.Double.NaN;

		try {
			dblIndexFactor = 0.01 * java.lang.Double.parseDouble (astrArgs[22]);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErr) e.printStackTrace();
		}

		if (s_bBlog) System.out.println ("Index Factor: " + dblIndexFactor);

		int iOriginalComponentCount = 0;

		try {
			iOriginalComponentCount = java.lang.Integer.parseInt (astrArgs[23]);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErr) e.printStackTrace();
		}

		if (s_bBlog) System.out.println ("Original Component Count: " + iOriginalComponentCount);

		int iDefaultedComponentCount = 0;

		try {
			java.lang.Integer.parseInt (astrArgs[24]);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErr) e.printStackTrace();
		}

		if (s_bBlog) System.out.println ("Defaulted Component Count: " + iDefaultedComponentCount);

		java.lang.String strLocation = org.drip.service.common.StringUtil.ProcessInputForNULL (astrArgs[26],
			false);

		if (s_bBlog) System.out.println ("Location: " + strLocation);

		boolean bPayAccrued = "1".equalsIgnoreCase (astrArgs[27]);

		if (s_bBlog) System.out.println ("Pay Accrued: " + bPayAccrued);

		boolean bKnockOutOnDefault = "1".equalsIgnoreCase (astrArgs[28]);

		if (s_bBlog) System.out.println ("KO Def: " + bKnockOutOnDefault);

		boolean bQuoteAsCDS = "1".equalsIgnoreCase (astrArgs[33]);

		if (s_bBlog) System.out.println ("Quote As CDS: " + bQuoteAsCDS);

		java.lang.String strBBGTicker = org.drip.service.common.StringUtil.ProcessInputForNULL (astrArgs[41],
			false);

		if (s_bBlog) System.out.println ("BBG Ticker: " + strBBGTicker);

		java.lang.String strShortName = org.drip.service.common.StringUtil.ProcessInputForNULL (astrArgs[42],
			false);

		if (s_bBlog) System.out.println ("Short Name: " + strShortName);

		org.drip.product.params.CDXRefDataParams cdxrd =
			org.drip.product.params.CDXRefDataParams.CreateCDXRefDataBuilder (strCurveID, strSPN,
				strIndexLabel, strIndexName, strCurveName, dtIssueDate.julian(),
					dtMaturityDate.julian(), dblCoupon, strCurrency, strDayCount, bFullFirstStub,
						dblRecovery, iFrequency, strRedID, strIndexClass, iIndexSeries, strIndexGroupName,
							strIndexShortName, strIndexShortGroupName, iIndexVersion, iIndexLifeSpan,
								strCurvyCurveID, dblIndexFactor, iOriginalComponentCount,
									iDefaultedComponentCount, strLocation, bPayAccrued, bKnockOutOnDefault,
										bQuoteAsCDS, strBBGTicker, strShortName);

		if (s_bBlog) System.out.println ("CDXRD = " + cdxrd);

		if (null == cdxrd) return null;

		java.lang.String strCDXRD = cdxrd.setConstructionString();

		if (s_bPrintCDXRefDataDump) System.out.println (strCDXRD);

		try {
			if (null != bw) {
				bw.write (strCDXRD);

				bw.flush();
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return cdxrd;
	}

	private static final boolean LoadCDXDefinitions (
		final java.lang.String strCDXRefDataFile)
	{
		if (null == strCDXRefDataFile || strCDXRefDataFile.isEmpty()) {
			System.out.println ("Invalid CDX Ref Data File: " + strCDXRefDataFile);

			return false;
		}

		int iNumIndices = 0;
		int iNumFunctions = 0;
		int iNumFailedToLoad = 0;
		int iNumIndicesPerFunction = 100;
		java.io.BufferedWriter bw = null;
		java.lang.String strCDXRefDataLine = "";
		java.io.BufferedReader iobrCDXRefData = null;

		try {
			iobrCDXRefData = new java.io.BufferedReader (new java.io.FileReader (strCDXRefDataFile));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		try {
			bw = new java.io.BufferedWriter (new java.io.FileWriter
				("C:\\Lakshmi\\BondAnal\\org\\drip\\product\\creator\\CDXRefDataHolder.java"));

			bw.write ("\npackage org.drip.product.creator;\n\n");

			bw.write
				("/*\n * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-\n */\n");

			bw.write ("\n/*\n *    GENERATED on " + new java.util.Date().toString() +
				" ---- DO NOT DELETE\n */\n");

			bw.write ("\n/*!\n * Copyright (C) 2012 Lakshmi Krishnamurthy\n *\n");

			bw.write (" * This file is part of CreditAnalytics, a free-software/open-source library for\n");

			bw.write
				(" *		fixed income analysts and developers - http://www.credit-trader.org\n *\n");

			bw.write
				(" * CreditAnalytics is a free, full featured, fixed income credit analytics library,\n");

			bw.write (" * 		developed with a special focus");

			bw.write (" towards the needs of the bonds and credit products community.\n *\n");

			bw.write (" *  Licensed under the Apache License, Version 2.0 (the \"License\");\n");

			bw.write (" *   	you may not use this file except in compliance with the License.\n");

			bw.write (" *\n *  You may obtain a copy of the License at\n");

			bw.write (" *  	http://www.apache.org/licenses/LICENSE-2.0\n *\n");

			bw.write (" *  Unless required by applicable law or agreed to in writing, software\n");

			bw.write (" *  	distributed under the License is distributed on an \"AS IS\" BASIS,\n");

			bw.write (" *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n");

			bw.write (" *  \n *  See the License for the specific language governing permissions and\n");

			bw.write (" *  	limitations under the License.\n */\n\n");

			bw.write ("public class CDXRefDataHolder {\n\tpublic ");

			bw.write ("static org.drip.analytics.support.CaseInsensitiveMap<org.drip.product.creator.CDXRefDataBuilder> ");

			bw.write ("_mapCDXRefData\n\t\t= new ");

			bw.write ("org.drip.analytics.support.AnalyticsHelper.CaseInsensitiveMap<org.drip.product.creator.CDXRefDataBuilder>();");

			bw.write ("\n\n\tpublic static org.drip.analytics.support.CaseInsensitiveMap<");

			bw.write ("java.util.Map<org.drip.analytics.date.JulianDate,\n\t\tjava.lang.Integer>> ");

			bw.write ("_mmCDXRDBFirstCouponSeries = new org.drip.analytics.support.CaseInsensitiveMap<\n\t\t\t");

			bw.write ("java.util.Map<org.drip.analytics.date.JulianDate, java.lang.Integer>>();\n\n\t");

			bw.write ("public static org.drip.analytics.support.CaseInsensitiveMap<java.util.Map<java.lang.Integer,");

			bw.write ("\n\t\torg.drip.analytics.date.JulianDate>> _mmCDXRDBSeriesFirstCoupon = new ");

			bw.write ("org.drip.analytics.support.CaseInsensitiveMap<\n\t\t\t");

			bw.write ("java.util.Map<java.lang.Integer, org.drip.analytics.date.JulianDate>>();\n\n\t");

			bw.write ("private static final boolean UpdateCDXRefDataMap (\n\t\tfinal java.lang.String ");

			bw.write ("strCDXName,\n\t\tfinal org.drip.product.creator.CDXRefDataBuilder cdxrd)\n\t{\n");

			bw.write ("\t\tif (null == cdxrd) {\n");

			bw.write ("\t\t\tSystem.out.println (\"No CDX ref data for \" + strCDXName);\n\n");

			bw.write ("\t\t\treturn false;\n\t\t}\n\n");

			bw.write ("\t\t_mapCDXRefData.put (strCDXName, cdxrd);\n\n\t\t");

			bw.write ("java.util.Map<org.drip.analytics.date.JulianDate, java.lang.Integer> ");

			bw.write ("mapFirstCouponSeries =\n\t\t\t_mmCDXRDBFirstCouponSeries.get ");

			bw.write ("(cdxrd._strIndexClass + \".\" + cdxrd._strIndexGroupName);\n\n\t\t");

			bw.write ("if (null == mapFirstCouponSeries)\n\t\t\tmapFirstCouponSeries = new ");

			bw.write ("java.util.TreeMap<org.drip.analytics.date.JulianDate, java.lang.Integer>();\n\n\t\t");

			bw.write ("mapFirstCouponSeries.put (cdxrd._dtMaturity.subtractTenor (cdxrd._iIndexLifeSpan + ");

			bw.write ("\"Y\"),\n\t\t\tcdxrd._iIndexSeries);");

			bw.write ("\n\n\t\t_mmCDXRDBFirstCouponSeries.put (cdxrd._strIndexClass + \".\" + ");

			bw.write ("cdxrd._strIndexGroupName,\n\t\t\tmapFirstCouponSeries);\n\n");

			bw.write ("\t\tjava.util.Map<java.lang.Integer, org.drip.analytics.date.JulianDate> ");

			bw.write ("mapSeriesFirstCoupon = \n\t\t\t");

			bw.write ("_mmCDXRDBSeriesFirstCoupon.get (cdxrd._strIndexClass + \".\" + ");

			bw.write ("cdxrd._strIndexGroupName);\n\n");

			bw.write ("\t\tif (null == mapSeriesFirstCoupon)\n");

			bw.write ("\t\t\tmapSeriesFirstCoupon = new java.util.TreeMap<java.lang.Integer, ");

			bw.write ("org.drip.analytics.date.JulianDate>();\n\n");

			bw.write ("\t\tmapSeriesFirstCoupon.put (cdxrd._iIndexSeries, cdxrd._dtMaturity.subtractTenor ");

			bw.write ("(cdxrd._iIndexLifeSpan\n\t\t\t+ \"Y\"));\n\n");

			bw.write ("\t\t_mmCDXRDBSeriesFirstCoupon.put (cdxrd._strIndexClass + \".\" + ");

			bw.write ("cdxrd._strIndexGroupName,\n\t\t\tmapSeriesFirstCoupon);\n\n");

			bw.write ("\t\treturn true;\n\t}\n");

			bw.write ("\n\tprivate static final boolean InitCDXRefDataSet" + ++iNumFunctions + "()\n\t{\n");

			while (null != (strCDXRefDataLine = iobrCDXRefData.readLine())) {
				++iNumIndices;

				java.lang.String[] astrCDXRefDataRecord = strCDXRefDataLine.split (",");

				org.drip.product.params.CDXRefDataParams cdxrd = CreateCDXRefDataFromRecord
					(astrCDXRefDataRecord, bw);

				if (null == cdxrd)
					System.out.println (++iNumFailedToLoad + " / " + iNumIndices + " failed to load!");

				if (0 == (iNumIndices % iNumIndicesPerFunction)) {
					bw.write ("\t\treturn true;\n\t}\n\n\tprivate ");

					bw.write ("static final boolean InitCDXRefDataSet" + ++iNumFunctions + "()\n\t{\n");
				}
			}

			bw.write ("\t\treturn true;\n\t}\n\n\t");

			bw.write ("public static final boolean InitFullCDXRefDataSet()\n\t{\n");

			for (int i = 1 ; i <= iNumFunctions; ++i)
				bw.write ("\t\tif (!InitCDXRefDataSet" + i + "()) return false;\n\n");

			bw.write ("\t\treturn true;\n\t}\n}\n");

			bw.close();

			iobrCDXRefData.close();
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		System.out.println (iNumFailedToLoad + " / " + iNumIndices + " failed to load!");

		return true;
	}

	public static void main (
		final java.lang.String[] astrArgs)
	{
		LoadCDXDefinitions ("C:\\Lakshmi\\RefDataAndMarks\\CDXRefData1Raw.csv");
	}
}
