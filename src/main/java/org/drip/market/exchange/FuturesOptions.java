
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
 * <i>FuturesOptions</i> contains the details of the exchange-traded Short-Term Futures Options Contracts.
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

public class FuturesOptions {
	private java.lang.String _strTradingMode = "";
	private java.lang.String _strFullyQualifiedName = "";
	private
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.product.params.LastTradingDateSetting[]>
			_mapLTDS = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.product.params.LastTradingDateSetting[]>();

	/**
	 * FuturesOptions Constructor
	 * 
	 * @param strFullyQualifiedName Fully Qualified Name
	 * @param strTradingMode Trading Mode - PREMIUM | MARGIN
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public FuturesOptions (
		final java.lang.String strFullyQualifiedName,
		final java.lang.String strTradingMode)
		throws java.lang.Exception
	{
		if (null == (_strFullyQualifiedName = strFullyQualifiedName) || _strFullyQualifiedName.isEmpty() ||
			null == (_strTradingMode = strTradingMode) || _strTradingMode.isEmpty())
			throw new java.lang.Exception ("FuturesOptions ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Fully Qualified Name
	 * 
	 * @return The Fully Qualified Name
	 */

	public java.lang.String fullyQualifiedName()
	{
		return _strFullyQualifiedName;
	}

	/**
	 * Retrieve the Trading Mode
	 * 
	 * @return The Trading Mode
	 */

	public java.lang.String tradingMode()
	{
		return _strTradingMode;
	}

	/**
	 * Add a Named Exchange LTDS Array Map Entry
	 * 
	 * @param strExchange Named Exchange
	 * @param aLTDS Array of LTDS
	 * 
	 * @return TRUE - Named Exchange LTDS Array Map Entry successfully added
	 */

	public boolean setLDTS (
		final java.lang.String strExchange,
		final org.drip.product.params.LastTradingDateSetting[] aLTDS)
	{
		if (null == strExchange || strExchange.isEmpty() || null == aLTDS || 0 == aLTDS.length) return false;

		_mapLTDS.put (strExchange, aLTDS);

		return true;
	}

	/**
	 * Retrieve the LTDS Array corresponding to the Exchange
	 * 
	 * @param strExchange The Exchange
	 * 
	 * @return The LTDS Array
	 */

	public org.drip.product.params.LastTradingDateSetting[] ltdsArray (
		final java.lang.String strExchange)
	{
		if (null == strExchange || strExchange.isEmpty() || !_mapLTDS.containsKey (strExchange)) return null;

		return _mapLTDS.get (strExchange);
	}

	/**
	 * Retrieve the Set of Traded Exchanges
	 * 
	 * @return The Set of Traded Exchanges
	 */

	public java.util.Set<java.lang.String> exchanges()
	{
		return 0 == _mapLTDS.size() ? null : _mapLTDS.keySet();
	}
}
