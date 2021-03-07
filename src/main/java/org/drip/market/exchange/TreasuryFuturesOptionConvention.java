
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
 * <i>TreasuryFuturesOptionConvention</i> contains the Details for the Exchange-Traded Options of the
 * Exchange-Traded Treasury Futures Contracts.
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
