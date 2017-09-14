
package org.drip.portfolioconstruction.core;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * LocalUniverse contains all the Assets in the Local Universe.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LocalUniverse {
	private java.util.Map<java.lang.String, org.drip.portfolioconstruction.core.Asset> _mapAsset = new
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.core.Asset>();

	/**
	 * Empty LocalUniverse Constructor
	 */

	public LocalUniverse()
	{
	}

	/**
	 * Add an Asset to the Local Universe
	 * 
	 * @param a Asset to be added
	 * 
	 * @return TRUE => The Asset has been added successfully
	 */

	public boolean add (
		final org.drip.portfolioconstruction.core.Asset a)
	{
		if (null == a) return false;

		_mapAsset.put (a.id(), a);

		return true;
	}

	/**
	 * Indicate if the Asset is contained in the Local Universe
	 * 
	 * @param a The Asset Instance
	 * 
	 * @return TRUE => The Asset is contained in the Local Universe
	 */

	public boolean contains (
		final org.drip.portfolioconstruction.core.Asset a)
	{
		return null != a && _mapAsset.containsKey (a.id());
	}

	/**
	 * Indicate if the Asset is contained in the Local Universe
	 * 
	 * @param strAssetID The Asset ID
	 * 
	 * @return TRUE => The Asset is contained in the Local Universe
	 */

	public boolean contains (
		final java.lang.String strAssetID)
	{
		return null != strAssetID && !strAssetID.isEmpty() && _mapAsset.containsKey (strAssetID);
	}

	/**
	 * Retrieve the List of the Asset Identifiers
	 * 
	 * @return The List of the Asset Identifiers
	 */

	public java.util.Set<java.lang.String> ids()
	{
		return _mapAsset.keySet();
	}
}
