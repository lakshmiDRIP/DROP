
package org.drip.market.otc;

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
 * <i>CrossFloatConventionContainer</i> contains the Conventions of Standard OTC Cross-Currency Float-Float
 * Swaps.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Static Market Fields - the Definitions, the OTC/Exchange Traded Products, and Treasury Settings</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/otc">OTC Dual Stream Option Container</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CrossFloatConventionContainer {
	private static java.util.Map<java.lang.String, org.drip.market.otc.CrossFloatSwapConvention>
		_mapConvention = null;

	private static final boolean AddCrossCurrencyConvention (
		final org.drip.market.otc.CrossFloatStreamConvention referenceConvention,
		final org.drip.market.otc.CrossFloatStreamConvention derivedConvention)
	{
		org.drip.market.otc.CrossFloatSwapConvention xccyConvention = null;

		try {
			xccyConvention = new org.drip.market.otc.CrossFloatSwapConvention (referenceConvention,
				derivedConvention, org.drip.param.period.FixingSetting.FIXING_PRESET_STATIC, false, 2);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		java.lang.String strDerivedCurrency = derivedConvention.currency();

		java.lang.String strReferenceCurrency = referenceConvention.currency();

		_mapConvention.put (strReferenceCurrency + "_" + strDerivedCurrency, xccyConvention);

		_mapConvention.put (strDerivedCurrency + "_" + strReferenceCurrency, xccyConvention);

		return true;
	}

	/**
	 * Initialize the Cross-Currency Float-Float Conventions Container with the pre-set Floating Stream
	 * 	Contracts
	 * 
	 * @return TRUE - The Cross-Currency Float-Float Conventions Container successfully initialized
	 */

	public static final boolean Init()
	{
		if (null != _mapConvention) return true;

		_mapConvention = new java.util.TreeMap<java.lang.String,
			org.drip.market.otc.CrossFloatSwapConvention>();

		try {
			org.drip.market.otc.CrossFloatStreamConvention referenceConventionDerivedQuote = new
				org.drip.market.otc.CrossFloatStreamConvention ("USD", "3M", false);

			if (!AddCrossCurrencyConvention (referenceConventionDerivedQuote, new
				org.drip.market.otc.CrossFloatStreamConvention ("AUD", "3M", true)))
				return false;

			if (!AddCrossCurrencyConvention (referenceConventionDerivedQuote, new
				org.drip.market.otc.CrossFloatStreamConvention ("CAD", "3M", true)))
				return false;

			if (!AddCrossCurrencyConvention (referenceConventionDerivedQuote, new
				org.drip.market.otc.CrossFloatStreamConvention ("CHF", "3M", true)))
				return false;

			if (!AddCrossCurrencyConvention (referenceConventionDerivedQuote, new
				org.drip.market.otc.CrossFloatStreamConvention ("DKK", "3M", true)))
				return false;

			if (!AddCrossCurrencyConvention (referenceConventionDerivedQuote, new
				org.drip.market.otc.CrossFloatStreamConvention ("EUR", "3M", true)))
				return false;

			if (!AddCrossCurrencyConvention (referenceConventionDerivedQuote, new
				org.drip.market.otc.CrossFloatStreamConvention ("GBP", "3M", true)))
				return false;

			if (!AddCrossCurrencyConvention (referenceConventionDerivedQuote, new
				org.drip.market.otc.CrossFloatStreamConvention ("JPY", "3M", true)))
				return false;

			if (!AddCrossCurrencyConvention (referenceConventionDerivedQuote, new
				org.drip.market.otc.CrossFloatStreamConvention ("NOK", "3M", true)))
				return false;

			if (!AddCrossCurrencyConvention (referenceConventionDerivedQuote, new
				org.drip.market.otc.CrossFloatStreamConvention ("PLN", "3M", true)))
				return false;

			if (!AddCrossCurrencyConvention (referenceConventionDerivedQuote, new
				org.drip.market.otc.CrossFloatStreamConvention ("SEK", "3M", true)))
				return false;

			org.drip.market.otc.CrossFloatStreamConvention referenceConventionReferenceQuote = new
				org.drip.market.otc.CrossFloatStreamConvention ("USD", "3M", true);

			if (!AddCrossCurrencyConvention (referenceConventionReferenceQuote, new
				org.drip.market.otc.CrossFloatStreamConvention ("CLP", "3M", false)))
				return false;

			if (!AddCrossCurrencyConvention (referenceConventionReferenceQuote, new
				org.drip.market.otc.CrossFloatStreamConvention ("MXN", "3M", false)))
				return false;

			return true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Retrieve the Cross-Currency Float-Float Convention Instance from the Jurisdiction Name
	 * 
	 * @param strCurrency The Jurisdiction Name
	 * 
	 * @return The Float-Float Convention Instance
	 */

	public static final org.drip.market.otc.CrossFloatSwapConvention ConventionFromJurisdiction (
		final java.lang.String strCurrency)
	{
		if (null == strCurrency || strCurrency.isEmpty()) return null;

		java.lang.String strKey = "USD_" + strCurrency;

		return !_mapConvention.containsKey (strKey) ? null : _mapConvention.get (strKey);
	}

	/**
	 * Retrieve the Cross-Currency Float-Float Convention Instance from the Reference/Derived Jurisdiction
	 * 	Names
	 * 
	 * @param strReferenceCurrency The Reference Jurisdiction Name
	 * @param strDerivedCurrency The Derived Jurisdiction Name
	 * 
	 * @return The Float-Float Convention Instance
	 */

	public static final org.drip.market.otc.CrossFloatSwapConvention ConventionFromJurisdiction (
		final java.lang.String strReferenceCurrency,
		final java.lang.String strDerivedCurrency)
	{
		if (null == strReferenceCurrency || strReferenceCurrency.isEmpty() || null == strDerivedCurrency ||
			strDerivedCurrency.isEmpty())
			return null;

		java.lang.String strKey = strReferenceCurrency + "_" + strDerivedCurrency;

		return !_mapConvention.containsKey (strKey) ? null : _mapConvention.get (strKey);
	}
}
