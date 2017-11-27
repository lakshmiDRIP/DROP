
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
 * TransactionCostGroup contains the Transaction Cost Values for the specified Set of Assets.
 *
 * @author Lakshmi Krishnamurthy
 */

public class TransactionCostGroup {
	private
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.cost.TransactionCharge>
		_mapTransactionCost = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.cost.TransactionCharge>();

	/**
	 * Add an Asset's Transaction Cost
	 * 
	 * @param strAssetID The Asset ID
	 * @param tc The Asset's Transaction Cost
	 * 
	 * @return TRUE - The Asset's Transaction Cost successfully added.
	 */

	public boolean add (
		final java.lang.String strAssetID,
		final org.drip.portfolioconstruction.cost.TransactionCharge tc)
	{
		if (null == strAssetID || strAssetID.isEmpty() || null == tc) return false;

		_mapTransactionCost.put (strAssetID, tc);

		return true;
	}

	/**
	 * Indicate if the Asset's Transaction Cost is Available
	 * 
	 * @param strAssetID The Asset ID
	 * 
	 * @return TRUE - The Asset's Transaction Cost is Available
	 */

	public boolean contains (
		final java.lang.String strAssetID)
	{
		return null != strAssetID && !strAssetID.isEmpty() && _mapTransactionCost.containsKey (strAssetID);
	}

	/**
	 * Retrieve the Asset's Transaction Cost
	 * 
	 * @param strAssetID The Asset ID
	 * 
	 * @return The Asset's Transaction Cost
	 */

	public org.drip.portfolioconstruction.cost.TransactionCharge get (
		final java.lang.String strAssetID)
	{
		if (null == strAssetID || strAssetID.isEmpty() || !_mapTransactionCost.containsKey (strAssetID))
			return null;

		return _mapTransactionCost.get (strAssetID);
	}

	/**
	 * Retrieve the Map of Transaction Costs
	 * 
	 * @return Map of the Transaction Costs
	 */

	public java.util.Map<java.lang.String, org.drip.portfolioconstruction.cost.TransactionCharge> map()
	{
		return _mapTransactionCost;
	}

	/**
	 * Constrict the Transaction Cost Array to those of the Holdings
	 * 
	 * @param holdings The Holdings Instance
	 * 
	 * @return The Array of Transaction Cost Objects
	 */

	public org.drip.portfolioconstruction.cost.TransactionCharge[] constrict (
		final org.drip.portfolioconstruction.composite.Holdings holdings)
	{
		if (null == holdings) return null;

		java.util.Set<java.lang.String> setAsset = holdings.assets();

		java.util.List<org.drip.portfolioconstruction.cost.TransactionCharge> lsTransactionCost = new
			java.util.ArrayList<org.drip.portfolioconstruction.cost.TransactionCharge>();

		for (java.lang.String strAssetID : setAsset)
			lsTransactionCost.add (contains (strAssetID) ? get (strAssetID) : null);

		return (org.drip.portfolioconstruction.cost.TransactionCharge[]) lsTransactionCost.toArray();
	}
}
