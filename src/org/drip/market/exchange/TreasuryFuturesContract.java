
package org.drip.market.exchange;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * TreasuryFuturesContract holds the Parameters/Settings of the Common Treasury Futures Contracts.
 *
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryFuturesContract {
	private java.lang.String _strID = "";
	private java.lang.String _strCode = "";
	private java.lang.String _strType = "";
	private java.lang.String _strTenor = "";

	/**
	 * TreasuryFuturesContract Constructor
	 * 
	 * @param strID Contract ID
	 * @param strCode Underling Treasury Code
	 * @param strType Underlying Treasury Type
	 * @param strTenor Contract Tenor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TreasuryFuturesContract (
		final java.lang.String strID,
		final java.lang.String strCode,
		final java.lang.String strType,
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (null == (_strID = strID) || _strID.isEmpty() || null == (_strCode = strCode) ||
			_strCode.isEmpty() || null == (_strType = strType) || _strType.isEmpty() || null == (_strTenor =
				strTenor) || _strTenor.isEmpty())
			throw new java.lang.Exception ("TreasuryFuturesContract ctr => Invalid Inputs!");
	}

	/**
	 * Retrieve the Treasury Futures Contract ID
	 * 
	 * @return The Contract ID
	 */

	public java.lang.String id()
	{
		return _strID;
	}

	/**
	 * Retrieve the Underlying Treasury Code
	 * 
	 * @return The Underlying Treasury Code
	 */

	public java.lang.String code()
	{
		return _strCode;
	}

	/**
	 * Retrieve the Underlying Treasury Type
	 * 
	 * @return The Underlying Treasury Type
	 */

	public java.lang.String type()
	{
		return _strType;
	}

	/**
	 * Retrieve the Contract Tenor
	 * 
	 * @return The Contract Tenor
	 */

	public java.lang.String tenor()
	{
		return _strTenor;
	}
}
