
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
 * BlockClassification contains the Classifications for the specified Set of Assets.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BlockClassification extends org.drip.portfolioconstruction.core.Block {
	private org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Boolean> _mapMembership = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Boolean>();

	/**
	 * Classification Constructor
	 * 
	 * @param strName The Name
	 * @param strID The ID
	 * @param strDescription The Description
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BlockClassification (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription)
		throws java.lang.Exception
	{
		super (strName, strID, strDescription);
	}

	/**
	 * Add an Asset's Membership
	 * 
	 * @param strAssetID The Asset ID
	 * @param bMembership The Membership TURUE or FALSE
	 * 
	 * @return TRUE - The Asset's Attribute successfully added.
	 */

	public boolean add (
		final java.lang.String strAssetID,
		final boolean bMembership)
	{
		if (null == strAssetID || strAssetID.isEmpty()) return false;

		_mapMembership.put (strAssetID, bMembership);

		return true;
	}

	/**
	 * Retrieve the Asset's Membership
	 * 
	 * @param strAssetID The Asset ID
	 * 
	 * @return The Asset's Membership
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public boolean membership (
		final java.lang.String strAssetID)
		throws java.lang.Exception
	{
		if (null == strAssetID || strAssetID.isEmpty() || !_mapMembership.containsKey (strAssetID))
			throw new java.lang.Exception ("Classification::membership => Invalid Inputs");

		return _mapMembership.get (strAssetID);
	}

	/**
	 * Retrieve the Map of Asset Classification
	 * 
	 * @return Map of the Asset Classification
	 */

	public java.util.Map<java.lang.String, java.lang.Boolean> membership()
	{
		return _mapMembership;
	}

	/**
	 * Constrict the Classification Values to those of the Holdings
	 * 
	 * @param holdings The Holdings Instance
	 * 
	 * @return The Array of Classification Values
	 */

	public double[] constrict (
		final org.drip.portfolioconstruction.composite.Holdings holdings)
	{
		if (null == holdings) return null;

		java.util.Set<java.lang.String> setAsset = holdings.assets();

		java.util.Set<java.lang.Double> setValue = new java.util.HashSet<java.lang.Double>();

		for (java.lang.String strAssetID : setAsset) {
			try {
				setValue.add (java.lang.Double.parseDouble (new java.lang.Boolean (membership
					(strAssetID)).toString()));
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		int iNumAsset = setAsset.size();

		if (setValue.size() != iNumAsset) return null;

		int iAssetCount = 0;
		double[] adblAssetAttributeValue = new double[iNumAsset];

		for (double dblAssetValue : setValue)
			adblAssetAttributeValue[iAssetCount++] = dblAssetValue;

		return adblAssetAttributeValue;
	}
}
