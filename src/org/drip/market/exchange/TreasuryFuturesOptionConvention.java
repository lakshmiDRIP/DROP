
package org.drip.market.exchange;

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
 * TreasuryFuturesOptionConvention contains the Details for the Exchange-Traded Options of the
 *  Exchange-Traded Treasury Futures Contracts.
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
			!org.drip.quant.common.NumberUtil.IsValid (_dblNotional = dblNotional) || null == (_aLTDS =
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
