
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
 * <i>FuturesOptions</i> contains the details of the exchange-traded Short-Term Futures Options Contracts.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Market</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/exchange">Exchange</a></li>
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
