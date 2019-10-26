
package org.drip.market.otc;

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
 * <i>IBORFloatFloatContainer</i> holds the settings of the standard OTC float-float swap contract
 * Conventions.
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

public class IBORFloatFloatContainer {
	private static java.util.Map<java.lang.String, org.drip.market.otc.FloatFloatSwapConvention>
		_mapConvention = null;

	/**
	 * Initialize the Float-Float Conventions Container with the pre-set Float-Float Contracts
	 * 
	 * @return TRUE - The Float-Float Conventions Container successfully initialized with the pre-set
	 *  Float-Float Contracts
	 */

	public static final boolean Init()
	{
		if (null != _mapConvention) return true;

		_mapConvention = new java.util.TreeMap<java.lang.String,
			org.drip.market.otc.FloatFloatSwapConvention>();

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
