
package org.drip.market.exchange;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>ShortTermFuturesContainer</i> holds the short term futures contracts.
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

public class ShortTermFuturesContainer {
	private static java.util.Map<java.lang.String, org.drip.market.exchange.ShortTermFutures> _mapFutures =
		null;

	/**
	 * Initialize the Short Term Futures Container with the pre-set Short Term Contracts
	 * 
	 * @return TRUE - The Short Term Futures Container successfully initialized with the pre-set Short Term
	 *  Contracts
	 */

	public static final boolean Init()
	{
		if (null != _mapFutures) return true;

		_mapFutures = new java.util.TreeMap<java.lang.String, org.drip.market.exchange.ShortTermFutures>();

		try {
			_mapFutures.put ("CAD-CDOR-3M", new org.drip.market.exchange.ShortTermFutures (new
				java.lang.String[] {"MX"}, 1000000.));

			_mapFutures.put ("CHF-LIBOR-3M", new org.drip.market.exchange.ShortTermFutures (new
				java.lang.String[] {"LIFFE"}, 1000000.));

			_mapFutures.put ("DKK-CIBOR-3M", new org.drip.market.exchange.ShortTermFutures (new
				java.lang.String[] {"OMX"}, 1000000.));

			_mapFutures.put ("EUR-EURIBOR-3M", new org.drip.market.exchange.ShortTermFutures (new
				java.lang.String[] {"EUREX", "LIFFE", "NLX"}, 1000000.));

			_mapFutures.put ("GBP-LIBOR-3M", new org.drip.market.exchange.ShortTermFutures (new
				java.lang.String[] {"LIFFE", "NLX"}, 500000.));

			_mapFutures.put ("JPY-LIBOR-3M", new org.drip.market.exchange.ShortTermFutures (new
				java.lang.String[] {"SGX"}, 100000000.));

			_mapFutures.put ("JPY-TIBOR-3M", new org.drip.market.exchange.ShortTermFutures (new
				java.lang.String[] {"CME", "SGX"}, 100000000.));

			_mapFutures.put ("USD-LIBOR-1M", new org.drip.market.exchange.ShortTermFutures (new
				java.lang.String[] {"CME"}, 3000000.));

			_mapFutures.put ("USD-LIBOR-3M", new org.drip.market.exchange.ShortTermFutures (new
				java.lang.String[] {"CME", "SGX"}, 1000000.));

			_mapFutures.put ("ZAR-JIBAR-3M", new org.drip.market.exchange.ShortTermFutures (new
				java.lang.String[] {"SAFEX"}, 1000000.));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Short Term Futures Exchange Info From the Corresponding Forward Label
	 * 
	 * @param forwardLabel The Forward Label
	 * 
	 * @return The Short Term Futures Exchange Info
	 */

	public static final org.drip.market.exchange.ShortTermFutures ExchangeInfo (
		final org.drip.state.identifier.ForwardLabel forwardLabel)
	{
		if (null == forwardLabel) return null;

		java.lang.String strFullyQualifiedName = forwardLabel.fullyQualifiedName();

		return _mapFutures.containsKey (strFullyQualifiedName) ? _mapFutures.get (strFullyQualifiedName) :
			null;
	}

	/**
	 * Retrieve the Short Term Futures Exchange Info From the Corresponding Forward Label
	 * 
	 * @param strFullyQualifiedName The Forward Label
	 * 
	 * @return The Short Term Futures Exchange Info
	 */

	public static final org.drip.market.exchange.ShortTermFutures ExchangeInfo (
		final java.lang.String strFullyQualifiedName)
	{
		return null != strFullyQualifiedName && _mapFutures.containsKey (strFullyQualifiedName) ?
			_mapFutures.get (strFullyQualifiedName) : null;
	}
}
