
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
 * <i>TreasuryFuturesOptionConvention</i> contains the Details for the Exchange-Traded Options of the
 * Exchange-Traded Treasury Futures Contracts.
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

public class TreasuryFuturesOptionConvention {
	private boolean _bPremiumType = false;
	private java.lang.String[] _astrCode = null;
	private double _dblNotional = java.lang.Double.NaN;
	private java.lang.String _strTreasuryFuturesIndex = "";
	private org.drip.product.params.LastTradingDateSetting[] _aLTDS = null;

	/**
	 * TreasuryFuturesOptionConvention Constructor
	 * 
	 * @param astrCode Array of Option Codes
	 * @param strTreasuryFuturesIndex Underlying Futures Index
	 * @param dblNotional Exchange Notional
	 * @param bPremiumType TRUE - Premium Up-front Type; FALSE - Margin Type
	 * @param aLTDS Array of Last Trading Date Settings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public TreasuryFuturesOptionConvention (
		final java.lang.String[] astrCode,
		final java.lang.String strTreasuryFuturesIndex,
		final double dblNotional,
		final boolean bPremiumType,
		final org.drip.product.params.LastTradingDateSetting[] aLTDS)
		throws java.lang.Exception
	{
		if (null == (_astrCode = astrCode) || null == (_strTreasuryFuturesIndex = strTreasuryFuturesIndex) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblNotional = dblNotional) || null == (_aLTDS =
				aLTDS))
			throw new java.lang.Exception ("TreasuryFuturesOptionConvention ctr: Invalid Inputs!");

		_bPremiumType = bPremiumType;
		int iNumLTDS = _aLTDS.length;
		int iNumCode = _astrCode.length;

		if (0 == iNumLTDS || 0 == iNumCode)
			throw new java.lang.Exception ("TreasuryFuturesOptionConvention ctr: Invalid Inputs!");

		for (java.lang.String strCode : _astrCode) {
			if (null == strCode || strCode.isEmpty())
				throw new java.lang.Exception ("TreasuryFuturesOptionConvention ctr: Invalid Inputs!");
		}

		for (org.drip.product.params.LastTradingDateSetting ltds : _aLTDS) {
			if (null == ltds)
				throw new java.lang.Exception ("TreasuryFuturesOptionConvention ctr: Invalid Inputs!");
		}
	}

	/**
	 * Retrieve the Array of the Exchange Codes
	 * 
	 * @return The Array of the Exchange Codes
	 */

	public java.lang.String[] codes()
	{
		return _astrCode;
	}

	/**
	 * Retrieve the Array of Last Trading Date Settings
	 * 
	 * @return The Array of Last Trading Date Settings
	 */

	public org.drip.product.params.LastTradingDateSetting[] ltds()
	{
		return _aLTDS;
	}

	/**
	 * Retrieve the Treasury Futures Index
	 * 
	 * @return The Treasury Futures Index
	 */

	public java.lang.String FuturesIndex()
	{
		return _strTreasuryFuturesIndex;
	}

	/**
	 * Retrieve the Option Exchange Notional
	 * 
	 * @return The Option Exchange Notional
	 */

	public double notional()
	{
		return _dblNotional;
	}

	/**
	 * Retrieve the Trading Type PREMIUM/MARGIN
	 * 
	 * @return TRUE - Trading Type is PREMIUM
	 */

	public boolean premiumType()
	{
		return _bPremiumType;
	}

	@Override public java.lang.String toString()
	{
		java.lang.String strDump = "TreasuryFuturesIndex: " + _strTreasuryFuturesIndex + " | Premium Type: "
			+ (_bPremiumType ? "PREMIUM" : "MARGIN ");

		for (int i = 0; i < _astrCode.length; ++i) {
			if (0 == i)
				strDump += " | CODES => {";
			else
				strDump += ", ";

			strDump += _astrCode[i];

			if (_astrCode.length - 1 == i) strDump += "}";
		}

		for (int i = 0; i < _aLTDS.length; ++i)
			strDump += "\n\t" + _aLTDS[i];

		return strDump;
	}
}
