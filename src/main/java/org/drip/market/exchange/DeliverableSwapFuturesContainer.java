
package org.drip.market.exchange;

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
 * <i>DeliverableSwapFuturesContainer</i> holds the Deliverable Swap Futures Contracts.
 * 
 * <br><br>
 * 	<ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Market</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/exchange">Exchange</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
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
