
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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>ShortTermFuturesContainer</i> holds the short term futures contracts.
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
