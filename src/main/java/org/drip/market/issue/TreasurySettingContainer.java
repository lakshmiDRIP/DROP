
package org.drip.market.issue;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>TreasurySettingContainer</i> contains the Parameters related to the Jurisdiction-specific Treasuries.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Static Market Fields - the Definitions, the OTC/Exchange Traded Products, and Treasury Settings</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/issue">Market Issue Treasury Setting Container</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TreasurySettingContainer {
	private static java.util.Map<java.lang.String, java.lang.String> _mapCurrencyTreasuryCode = null;
	private static java.util.Map<java.lang.String, org.drip.market.issue.TreasurySetting> _mapTreasurySetting
		= null;

	private static final boolean AddSetting (
		final java.lang.String strCode,
		final java.lang.String strCurrency,
		final int iFrequency,
		final java.lang.String strDayCount,
		final java.lang.String strCalendar)
	{
		try {
			_mapTreasurySetting.put (strCode, new org.drip.market.issue.TreasurySetting (strCode,
				strCurrency, iFrequency, strDayCount, strCalendar));

			return true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Initialize the Treasury Settings Container
	 * 
	 * @return TRUE - Initialization Successful
	 */

	public static final boolean Init()
	{
		if (null != _mapCurrencyTreasuryCode) return true;

		_mapCurrencyTreasuryCode = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.String>();

		 _mapTreasurySetting = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.market.issue.TreasurySetting>();

		if (!AddSetting ("AGB", "AUD", 2, "DCAct_Act_UST", "AUD")) return false;

		if (!AddSetting ("BTPS", "EUR", 2, "DCAct_Act_UST", "EUR")) return false;

		if (!AddSetting ("CAN", "CAD", 2, "DCAct_Act_UST", "CAD")) return false;

		if (!AddSetting ("DBR", "EUR", 2, "DCAct_Act_UST", "EUR")) return false;

		if (!AddSetting ("DGB", "DKK", 2, "DCAct_Act_UST", "DKK")) return false;

		if (!AddSetting ("FRTR", "EUR", 2, "DCAct_Act_UST", "EUR")) return false;

		if (!AddSetting ("GGB", "EUR", 2, "DCAct_Act_UST", "EUR")) return false;

		if (!AddSetting ("GILT", "GBP", 2, "DCAct_Act_UST", "GBP")) return false;

		if (!AddSetting ("GSWISS", "CHF", 2, "DCAct_Act_UST", "CHF")) return false;

		if (!AddSetting ("JGB", "JPY", 2, "DCAct_Act_UST", "JPY")) return false;

		if (!AddSetting ("MBONO", "MXN", 2, "DCAct_364", "MXN")) return false;

		if (!AddSetting ("NGB", "NOK", 2, "DCAct_Act_UST", "NOK")) return false;

		if (!AddSetting ("NZGB", "NZD", 2, "DCAct_Act_UST", "NZD")) return false;

		if (!AddSetting ("SGB", "SEK", 2, "DCAct_Act_UST", "SEK")) return false;

		if (!AddSetting ("SPGB", "EUR", 2, "DCAct_Act_UST", "EUR")) return false;

		if (!AddSetting ("UST", "USD", 2, "DCAct_Act_UST", "USD")) return false;

		_mapCurrencyTreasuryCode.put ("AUD", "AGB");

		_mapCurrencyTreasuryCode.put ("CAD", "CAN");

		_mapCurrencyTreasuryCode.put ("CHF", "GSWISS");

		_mapCurrencyTreasuryCode.put ("DKK", "DGB");

		_mapCurrencyTreasuryCode.put ("EUR", "DBR");

		_mapCurrencyTreasuryCode.put ("GBP", "GILT");

		_mapCurrencyTreasuryCode.put ("JPY", "JGB");

		_mapCurrencyTreasuryCode.put ("MXN", "MBONO");

		_mapCurrencyTreasuryCode.put ("NOK", "NGB");

		_mapCurrencyTreasuryCode.put ("NZD", "NZGB");

		_mapCurrencyTreasuryCode.put ("SEK", "SGB");

		_mapCurrencyTreasuryCode.put ("USD", "UST");

		return true;
	}

	/**
	 * Retrieve the Treasury Settings corresponding to the Code
	 * 
	 * @param strTreasuryCode The Treasury Code
	 * 
	 * @return The Settings that correspond to the Code
	 */

	public static final org.drip.market.issue.TreasurySetting TreasurySetting (
		final java.lang.String strTreasuryCode)
	{
		return !_mapTreasurySetting.containsKey (strTreasuryCode) ? null : _mapTreasurySetting.get
			(strTreasuryCode);
	}

	/**
	 * Retrieve the Benchmark Treasury Code for the specified Currency
	 * 
	 * @param strCurrency The Currency
	 * 
	 * @return The Benchmark Treasury Code
	 */

	public static final java.lang.String CurrencyBenchmarkCode (
		final java.lang.String strCurrency)
	{
		return !_mapCurrencyTreasuryCode.containsKey (strCurrency) ? null : _mapCurrencyTreasuryCode.get
			(strCurrency);
	}
}
