
package org.drip.market.issue;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * TreasurySettingContainer contains the Parameters related to the Jurisdiction-specific Treasuries.
 *
 * @author Lakshmi Krishnamurthy
 */

public class TreasurySettingContainer {
	private static final java.util.Map<java.lang.String, org.drip.market.issue.TreasurySetting>
		_mapTreasurySetting = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.market.issue.TreasurySetting>();

	private static final java.util.Map<java.lang.String, java.lang.String> _mapCurrencyTreasuryCode = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.String>();

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
