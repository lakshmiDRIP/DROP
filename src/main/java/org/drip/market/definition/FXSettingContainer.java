
package org.drip.market.definition;

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
 * <i>FXSettingContainer</i> contains the Parameters related to the FX Settings.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Static Market Fields - the Definitions, the OTC/Exchange Traded Products, and Treasury Settings</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/definition">IBOR, FX, Overnight Index Container</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FXSettingContainer {
	private static java.util.Map<java.lang.String, java.lang.Integer> _mapCurrencyOrder = null;
	private static java.util.Map<java.lang.String, org.drip.product.params.CurrencyPair> _mapCurrencyPair =
		null;

	private static final void SetUpCurrencyPair (
		final java.lang.String strCurrency1,
		final java.lang.String strCurrency2,
		final org.drip.product.params.CurrencyPair cp)
	{
		_mapCurrencyPair.put (strCurrency1 + strCurrency2, cp);
		
		_mapCurrencyPair.put (strCurrency2 + strCurrency1, cp);
	}

	/**
	 * Initialize the FXSettingContainer
	 * 
	 * @return TRUE - FXSettingsContainer successfully initialized
	 */

	public static final boolean Init()
	{
		if (null != _mapCurrencyOrder) return true;

		_mapCurrencyOrder = new java.util.TreeMap<java.lang.String, java.lang.Integer>();

		_mapCurrencyPair = new java.util.TreeMap<java.lang.String, org.drip.product.params.CurrencyPair>();

		_mapCurrencyOrder.put ("EUR", 1);

		_mapCurrencyOrder.put ("GBP", 2);

		_mapCurrencyOrder.put ("AUD", 3);

		_mapCurrencyOrder.put ("NZD", 4);

		_mapCurrencyOrder.put ("USD", 5);

		_mapCurrencyOrder.put ("CAD", 6);

		_mapCurrencyOrder.put ("CHF", 7);

		_mapCurrencyOrder.put ("JPY", 8);

		try {
			SetUpCurrencyPair ("AUD", "EUR", new org.drip.product.params.CurrencyPair ("AUD", "EUR", "EUR",
				10000.));

			SetUpCurrencyPair ("AUD", "USD", new org.drip.product.params.CurrencyPair ("AUD", "USD", "USD",
				10000.));

			SetUpCurrencyPair ("EUR", "GBP", new org.drip.product.params.CurrencyPair ("EUR", "GBP", "GBP",
				10000.));

			SetUpCurrencyPair ("EUR", "JPY", new org.drip.product.params.CurrencyPair ("EUR", "JPY", "JPY",
				100.));

			SetUpCurrencyPair ("EUR", "USD", new org.drip.product.params.CurrencyPair ("EUR", "USD", "USD",
				10000.));

			SetUpCurrencyPair ("GBP", "JPY", new org.drip.product.params.CurrencyPair ("GBP", "JPY", "JPY",
				100.));

			SetUpCurrencyPair ("GBP", "USD", new org.drip.product.params.CurrencyPair ("GBP", "USD", "USD",
				10000.));

			SetUpCurrencyPair ("USD", "BRL", new org.drip.product.params.CurrencyPair ("USD", "BRL", "BRL",
				10000.));

			SetUpCurrencyPair ("USD", "CAD", new org.drip.product.params.CurrencyPair ("USD", "CAD", "CAD",
				10000.));

			SetUpCurrencyPair ("USD", "CHF", new org.drip.product.params.CurrencyPair ("USD", "CHF", "CHF",
				10000.));

			SetUpCurrencyPair ("USD", "CNY", new org.drip.product.params.CurrencyPair ("USD", "CNY", "CNY",
				1.));

			SetUpCurrencyPair ("USD", "EGP", new org.drip.product.params.CurrencyPair ("USD", "EGP", "EGP",
				10000.));

			SetUpCurrencyPair ("USD", "HUF", new org.drip.product.params.CurrencyPair ("USD", "HUF", "HUF",
				100.));

			SetUpCurrencyPair ("USD", "INR", new org.drip.product.params.CurrencyPair ("USD", "INR", "INR",
				100.));

			SetUpCurrencyPair ("USD", "JPY", new org.drip.product.params.CurrencyPair ("USD", "JPY", "JPY",
				100.));

			SetUpCurrencyPair ("USD", "KRW", new org.drip.product.params.CurrencyPair ("USD", "KRW", "KRW",
				1.));

			SetUpCurrencyPair ("USD", "MXN", new org.drip.product.params.CurrencyPair ("USD", "MXN", "MXN",
				10000.));

			SetUpCurrencyPair ("USD", "PLN", new org.drip.product.params.CurrencyPair ("USD", "PLN", "PLN",
				100.));

			SetUpCurrencyPair ("USD", "TRY", new org.drip.product.params.CurrencyPair ("USD", "TRY", "TRY",
				100.));

			SetUpCurrencyPair ("USD", "TWD", new org.drip.product.params.CurrencyPair ("USD", "TWD", "TWD",
				1.));

			SetUpCurrencyPair ("USD", "ZAR", new org.drip.product.params.CurrencyPair ("USD", "ZAR", "ZAR",
				10000.));

			return true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Retrieve the Order corresponding to the specified Currency
	 * 
	 * @param strCurrency The Currency
	 * 
	 * @return The Order corresponding to the specified Currency
	 * 
	 * @throws java.lang.Exception Thrown if Inputs of Invalid
	 */

	public static final int CurrencyOrder (
		final java.lang.String strCurrency)
		throws java.lang.Exception
	{
		if (null == strCurrency || strCurrency.isEmpty())
			throw new java.lang.Exception ("FXSettingsContainer::CurrencyOrder => Invalid Input");

		return _mapCurrencyOrder.containsKey (strCurrency) ? _mapCurrencyOrder.get (strCurrency) : 0;
	}

	/**
	 * Retrieve the Currency Pair Instance from the specified Currencies
	 * 
	 * @param strCurrency1 Currency #1
	 * @param strCurrency2 Currency #2
	 * 
	 * @return The Currency Pair Instance
	 */

	public static final org.drip.product.params.CurrencyPair CurrencyPair (
		final java.lang.String strCurrency1,
		final java.lang.String strCurrency2)
	{
		if (null == strCurrency1 || strCurrency1.isEmpty() || null == strCurrency2 || strCurrency2.isEmpty())
			return null;

		java.lang.String strCurrencyPairCode = strCurrency1 + strCurrency2;

		return _mapCurrencyPair.containsKey (strCurrencyPairCode) ? _mapCurrencyPair.get
			(strCurrencyPairCode) : null;
	}
}
