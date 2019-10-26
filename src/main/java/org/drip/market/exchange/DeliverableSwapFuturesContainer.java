
package org.drip.market.exchange;

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
 * <i>DeliverableSwapFuturesContainer</i> holds the Deliverable Swap Futures Contracts.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Static Market Fields - the Definitions, the OTC/Exchange Traded Products, and Treasury Settings</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/exchange">Deliverable Swap, STIR, Treasury Futures</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DeliverableSwapFuturesContainer {
	private static java.util.Map<java.lang.String, org.drip.market.exchange.DeliverableSwapFutures>
		_mapFutures = null;


	/**
	 * Initialize the Deliverable Swap Futures Container with the pre-set Deliverable Swap Futures Contract
	 * 
	 * @return TRUE - The Deliverable Swap Futures Container successfully initialized with the pre-set
	 *  Deliverable Swap Futures Contract
	 */

	public static final boolean Init()
	{
		if (null != _mapFutures) return true;

		_mapFutures = new java.util.TreeMap<java.lang.String,
			org.drip.market.exchange.DeliverableSwapFutures>();

		try {
			_mapFutures.put ("USD-2Y", new org.drip.market.exchange.DeliverableSwapFutures ("USD", "2Y",
				100000., 0.0025, new org.drip.product.params.LastTradingDateSetting
					(org.drip.product.params.LastTradingDateSetting.MID_CURVE_OPTION, "2D",
						java.lang.Integer.MIN_VALUE)));

			_mapFutures.put ("USD-5Y", new org.drip.market.exchange.DeliverableSwapFutures ("USD", "5Y",
				100000., 0.0025, new org.drip.product.params.LastTradingDateSetting
					(org.drip.product.params.LastTradingDateSetting.MID_CURVE_OPTION, "2D",
						java.lang.Integer.MIN_VALUE)));

			_mapFutures.put ("USD-10Y", new org.drip.market.exchange.DeliverableSwapFutures ("USD", "10Y",
				100000., 0.0025, new org.drip.product.params.LastTradingDateSetting
					(org.drip.product.params.LastTradingDateSetting.MID_CURVE_OPTION, "2D",
						java.lang.Integer.MIN_VALUE)));

			_mapFutures.put ("USD-30Y", new org.drip.market.exchange.DeliverableSwapFutures ("USD", "30Y",
				100000., 0.0025, new org.drip.product.params.LastTradingDateSetting
					(org.drip.product.params.LastTradingDateSetting.MID_CURVE_OPTION, "2D",
						java.lang.Integer.MIN_VALUE)));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Deliverable Swap Futures Info from the Currency and the Tenor
	 * 
	 * @param strCurrency The Currency
	 * @param strTenor The Tenor
	 * 
	 * @return The Deliverable Swap Futures Instance
	 */

	public static final org.drip.market.exchange.DeliverableSwapFutures ProductInfo (
		final java.lang.String strCurrency,
		final java.lang.String strTenor)
	{
		if (null == strCurrency || strCurrency.isEmpty() || null == strTenor || strTenor.isEmpty())
			return null;

		java.lang.String strKey = strCurrency + "-" + strTenor;

		return _mapFutures.containsKey (strKey) ? _mapFutures.get (strKey) : null;
	}
}
