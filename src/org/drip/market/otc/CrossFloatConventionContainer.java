
package org.drip.market.otc;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * CrossFloatConventionContainer contains the Conventions of Standard OTC Cross-Currency Float-Float Swaps.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CrossFloatConventionContainer {
	private static final java.util.Map<java.lang.String, org.drip.market.otc.CrossFloatSwapConvention>
		_mapConvention = new java.util.TreeMap<java.lang.String,
			org.drip.market.otc.CrossFloatSwapConvention>();

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
