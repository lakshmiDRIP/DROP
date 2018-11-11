
package org.drip.market.otc;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>CrossFloatConventionContainer</i> contains the Conventions of Standard OTC Cross-Currency Float-Float
 * Swaps.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Market</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/otc">Over-the-Counter</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
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
