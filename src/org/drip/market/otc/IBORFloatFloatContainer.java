
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
 * IBORFloatFloatContainer holds the settings of the standard OTC float-float swap contract Conventions.
 *
 * @author Lakshmi Krishnamurthy
 */

public class IBORFloatFloatContainer {
	private static final java.util.Map<java.lang.String, org.drip.market.otc.FloatFloatSwapConvention>
		_mapConvention = new java.util.TreeMap<java.lang.String, org.drip.market.otc.FloatFloatSwapConvention>();

	/**
	 * Initialize the Float-Float Conventions Container with the pre-set Float-Float Contracts
	 * 
	 * @return TRUE - The Float-Float Conventions Container successfully initialized with the pre-set
	 *  Float-Float Contracts
	 */

	public static final boolean Init()
	{
		try {
			_mapConvention.put ("AUD", new org.drip.market.otc.FloatFloatSwapConvention ("AUD", "6M", true,
				false, true, false, 1));

			_mapConvention.put ("CAD", new org.drip.market.otc.FloatFloatSwapConvention ("CAD", "6M", true,
				false, true, false, 0));

			_mapConvention.put ("CHF", new org.drip.market.otc.FloatFloatSwapConvention ("CHF", "6M", true,
				false, true, false, 2));

			_mapConvention.put ("CNY", new org.drip.market.otc.FloatFloatSwapConvention ("CNY", "6M", true,
				false, true, false, 2));

			_mapConvention.put ("DKK", new org.drip.market.otc.FloatFloatSwapConvention ("DKK", "6M", true,
				false, true, false, 2));

			_mapConvention.put ("EUR", new org.drip.market.otc.FloatFloatSwapConvention ("EUR", "6M", false,
				true, false, true, 2));

			_mapConvention.put ("GBP", new org.drip.market.otc.FloatFloatSwapConvention ("GBP", "6M", true,
				false, true, false, 0));

			_mapConvention.put ("HKD", new org.drip.market.otc.FloatFloatSwapConvention ("HKD", "6M", true,
				false, true, false, 0));

			_mapConvention.put ("INR", new org.drip.market.otc.FloatFloatSwapConvention ("INR", "6M", true,
				false, true, false, 2));

			_mapConvention.put ("JPY", new org.drip.market.otc.FloatFloatSwapConvention ("JPY", "6M", true,
				false, true, false, 2));

			_mapConvention.put ("NOK", new org.drip.market.otc.FloatFloatSwapConvention ("NOK", "6M", true,
				false, true, false, 2));

			_mapConvention.put ("NZD", new org.drip.market.otc.FloatFloatSwapConvention ("NZD", "6M", true,
				false, true, false, 0));

			_mapConvention.put ("PLN", new org.drip.market.otc.FloatFloatSwapConvention ("PLN", "6M", true,
				false, true, false, 2));

			_mapConvention.put ("SEK", new org.drip.market.otc.FloatFloatSwapConvention ("SEK", "6M", true,
				false, true, false, 2));

			_mapConvention.put ("SGD", new org.drip.market.otc.FloatFloatSwapConvention ("SGD", "6M", true,
				false, true, false, 2));

			_mapConvention.put ("USD", new org.drip.market.otc.FloatFloatSwapConvention ("USD", "6M", true,
				false, true, false, 2));

			_mapConvention.put ("ZAR", new org.drip.market.otc.FloatFloatSwapConvention ("ZAR", "6M", true,
				false, true, false, 0));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Float-Float Convention Instance from the Jurisdiction Name
	 * 
	 * @param strCurrency The Jurisdiction Name
	 * 
	 * @return The Float-Float Convention Instance
	 */

	public static final org.drip.market.otc.FloatFloatSwapConvention ConventionFromJurisdiction (
		final java.lang.String strCurrency)
	{
		return null == strCurrency || strCurrency.isEmpty() || !_mapConvention.containsKey (strCurrency) ?
			null : _mapConvention.get (strCurrency);
	}
}
