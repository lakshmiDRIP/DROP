
package org.drip.portfolioconstruction.composite;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * Holdings is a Portfolio of Holdings in the specified Set of Assets.
 *
 * @author Lakshmi Krishnamurthy
 */

public class Holdings extends org.drip.portfolioconstruction.core.Block {
	private java.lang.String _strCurrency = "";

	private org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> _mapQuantity = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	/**
	 * Holdings Constructor
	 * 
	 * @param strName The Asset Name
	 * @param strID The Asset ID
	 * @param strDescription The Asset Description
	 * @param strCurrency The Account Currency
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Holdings (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final java.lang.String strCurrency)
		throws java.lang.Exception
	{
		super (strName, strID, strDescription);

		if (null == (_strCurrency = strCurrency))
			throw new java.lang.Exception ("Holdings Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Set of Asset IDs
	 * 
	 * @return The Set of Asset IDs
	 */

	public java.util.Set<java.lang.String> assets()
	{
		return _mapQuantity.keySet();
	}

	/**
	 * Retrieve the Map of Holdings Amount
	 * 
	 * @return The Map of Holdings Amount
	 */

	public java.util.Map<java.lang.String, java.lang.Double> quantityMap()
	{
		return _mapQuantity;
	}

	/**
	 * Add an Asset/Amount Pair
	 * 
	 * @param strAssetID The Asset ID
	 * @param dblQuantity The Amount in the Portfolio
	 * 
	 * @return TRUE - The Asset/Amount has been successfully added
	 */

	public boolean add (
		final java.lang.String strAssetID,
		final double dblQuantity)
	{
		if (null == strAssetID || strAssetID.isEmpty() || !org.drip.quant.common.NumberUtil.IsValid
			(dblQuantity))
			return false;

		_mapQuantity.put (strAssetID, dblQuantity);

		return true;
	}

	/**
	 * Indicates if an Asset exists in the Holdings
	 * 
	 * @param strAssetID The Asset ID
	 * 
	 * @return TRUE - The Asset is Part of the Holdings (may have Zero Value though)
	 */

	public boolean contains (
		final java.lang.String strAssetID)
	{
		return null != strAssetID && !_mapQuantity.containsKey (strAssetID);
	}

	/**
	 * Retrieves the Holdings Quantity for the Asset (if it exists)
	 * 
	 * @param strID The Asset ID
	 * 
	 * @return The Holdings Quantity for the Asset (if it exists)
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double quantity (
		final java.lang.String strID)
		throws java.lang.Exception
	{
		if (!contains (strID)) throw new java.lang.Exception ("Holdings::quantity => Invalid Inputs");

		return _mapQuantity.get (strID);
	}

	/**
	 * Retrieve the Currency
	 * 
	 * @return The Currency
	 */

	public java.lang.String currency()
	{
		return _strCurrency;
	}

	/**
	 * Retrieves the Cash Holdings
	 * 
	 * @return The Cash Holdings
	 */

	public double cash()
	{
		try {
			return quantity ("CASH::" + _strCurrency);
		} catch (java.lang.Exception e) {
		}

		return 0.;
	}
}
