
package org.drip.portfolioconstruction.risk;

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
 * AttributeJointFactor contains the Factor Based Attributes that determines the Correlation between the Pair
 *	of Assets.
 *
 * @author Lakshmi Krishnamurthy
 */

public class AttributeJointFactor extends org.drip.portfolioconstruction.core.Block {
	private java.util.Map<java.lang.String, java.lang.Double> _mapAssetFactorLoading = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	private java.util.Map<java.lang.String, java.lang.Double> _mapFactorAssetLoading = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	private java.util.Map<java.lang.String, java.lang.Double> _mapFactorCovariance = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	private java.util.Map<java.lang.String, java.lang.Double> _mapSpecificRisk = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	/**
	 * Generate a Standard Instance of AttributeJointFactor
	 * 
	 * @param strName AttributeJointFactor Instance Name
	 * @param strID AttributeJointFactor Instance ID
	 * @param strDescription AttributeJointFactor Description
	 * @param astrAssetID Array of Asset IDs
	 * @param astrFactorID Array of FactorIDs
	 * @param aadblAssetFactorLoading Matrix of Asset-Factor Loadings
	 * @param aadblFactorCovariance Matrix of Factor Covariances
	 * @param adblSpecificRisk Array of Specific Risks
	 * 
	 * @return The Standard Instance of AttributeJointFactor
	 */

	public static final AttributeJointFactor Standard (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final java.lang.String[] astrAssetID,
		final java.lang.String[] astrFactorID,
		final double[][] aadblAssetFactorLoading,
		final double[][] aadblFactorCovariance,
		final double[] adblSpecificRisk)
	{
		if (null == astrAssetID || null == astrFactorID || null == aadblAssetFactorLoading || null ==
			aadblFactorCovariance || null == adblSpecificRisk)
			return null;

		AttributeJointFactor ajf = null;
		int iNumAsset = astrAssetID.length;
		int iNumFactor = astrFactorID.length;

		if (0 == iNumAsset || 0 == iNumFactor || iNumAsset != aadblAssetFactorLoading.length || iNumFactor !=
			aadblFactorCovariance.length || iNumAsset != adblSpecificRisk.length)
			return null;

		try {
			ajf = new AttributeJointFactor (strName, strID, strDescription);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int iAsset = 0; iAsset < iNumAsset; ++iAsset) {
			for (int iFactor = 0; iFactor < iNumFactor; ++iFactor) {
				if (!ajf.addAssetFactorLoading (astrAssetID[iAsset], astrFactorID[iAsset],
					aadblAssetFactorLoading[iAsset][iFactor]))
					return null;
			}

			if (!ajf.addSpecificRisk (astrAssetID[iAsset], adblSpecificRisk[iAsset])) return null;
		}

		for (int iFactor1 = 0; iFactor1 < iNumFactor; ++iFactor1) {
			for (int iFactor2 = 0; iFactor2 < iNumFactor; ++iFactor2) {
				if (!ajf.addFactorCovariance (astrFactorID[iFactor1], astrFactorID[iFactor2],
					aadblFactorCovariance[iFactor1][iFactor2]))
					return null;
			}
		}

		return ajf;
	}

	/**
	 * AttributeJointFactor Constructor
	 * 
	 * @param strName The Name
	 * @param strID The ID
	 * @param strDescription The Description
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AttributeJointFactor (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription)
		throws java.lang.Exception
	{
		super (strName, strID, strDescription);
	}

	/**
	 * Retrieve the Joint Asset-Factor Loading Map
	 * 
	 * @return The Joint Asset-Factor Loading Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> assetFactorLoading()
	{
		return _mapAssetFactorLoading;
	}

	/**
	 * Retrieve the Joint Factor-Asset Loading Map
	 * 
	 * @return The Joint Factor-Asset Loading Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> factorAssetLoading()
	{
		return _mapFactorAssetLoading;
	}

	/**
	 * Retrieve the Factor Covariance Map
	 * 
	 * @return The Factor Covariance Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> factorCovariance()
	{
		return _mapFactorCovariance;
	}

	/**
	 * Retrieve the Specific Risk
	 * 
	 * @return The Specific Risk
	 */

	public java.util.Map<java.lang.String, java.lang.Double> specificRisk()
	{
		return _mapSpecificRisk;
	}

	/**
	 * Add the Asset's Factor Loading Coefficient
	 * 
	 * @param strAssetID The Asset ID
	 * @param strFactorID The Factor ID
	 * @param dblFactorLoading The Factor Loading Coefficient
	 * 
	 * @return TRUE => The Asset's Factor Loading Coefficient successfully added
	 */

	public boolean addAssetFactorLoading (
		final java.lang.String strAssetID,
		final java.lang.String strFactorID,
		final double dblFactorLoading)
	{
		if (null == strAssetID || strAssetID.isEmpty() || null == strFactorID || strFactorID.isEmpty() ||
			!org.drip.quant.common.NumberUtil.IsValid (dblFactorLoading))
			return false;

		_mapAssetFactorLoading.put (strAssetID + "::" + strFactorID, dblFactorLoading);

		_mapFactorAssetLoading.put (strFactorID + "::" + strAssetID, dblFactorLoading);

		return true;
	}

	/**
	 * Add the Cross Factor Covariance
	 * 
	 * @param strFactorID1 The Factor #1 ID
	 * @param strFactorID2 The Factor #2 ID
	 * @param dblFactorCovariance The Cross Factor Covariance
	 * 
	 * @return TRUE => The Cross Factor Covariance successfully added
	 */

	public boolean addFactorCovariance (
		final java.lang.String strFactorID1,
		final java.lang.String strFactorID2,
		final double dblFactorCovariance)
	{
		if (null == strFactorID1 || strFactorID1.isEmpty() || null == strFactorID2 || strFactorID2.isEmpty()
			|| !org.drip.quant.common.NumberUtil.IsValid (dblFactorCovariance))
			return false;

		_mapFactorCovariance.put (strFactorID1 + "::" + strFactorID2, dblFactorCovariance);

		_mapFactorCovariance.put (strFactorID2 + "::" + strFactorID1, dblFactorCovariance);

		return true;
	}

	/**
	 * Add the Asset's Specific Risk
	 * 
	 * @param strAssetID The Asset ID
	 * @param dblSpecificRisk The Asset's Specific Risk
	 * 
	 * @return TRUE => The Asset's Specific Risk successfully added
	 */

	public boolean addSpecificRisk (
		final java.lang.String strAssetID,
		final double dblSpecificRisk)
	{
		if (null == strAssetID || strAssetID.isEmpty() || !org.drip.quant.common.NumberUtil.IsValid
			(dblSpecificRisk))
			return false;

		_mapSpecificRisk.put (strAssetID, dblSpecificRisk);

		return true;
	}

	/**
	 * Check if the Asset is represented
	 * 
	 * @param strAssetID The Asset ID
	 * 
	 * @return TRUE => The Asset is represented
	 */

	public boolean containsAsset (
		final java.lang.String strAssetID)
	{
		return null != strAssetID && !strAssetID.isEmpty() && _mapAssetFactorLoading.containsKey
			(strAssetID) && _mapSpecificRisk.containsKey (strAssetID);
	}

	/**
	 * Check if the Factor is available
	 * 
	 * @param strFactorID The Factor ID
	 * 
	 * @return TRUE => The Factor is available
	 */

	public boolean containsFactor (
		final java.lang.String strFactorID)
	{
		return null != strFactorID && !strFactorID.isEmpty() && _mapFactorAssetLoading.containsKey
			(strFactorID) && _mapFactorCovariance.containsKey (strFactorID);
	}

	/**
	 * Retrieve the Factor Loading for the specified Asset
	 * 
	 * @param strAssetID The Asset ID
	 * 
	 * @return The Factor Loading for the specified Asset
	 */

	public java.util.Map<java.lang.String, java.lang.Double> assetFactorLoading (
		final java.lang.String strAssetID)
	{
		if (!containsAsset (strAssetID)) return null;

		java.util.Map<java.lang.String, java.lang.Double> mapAssetFactorLoading = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> meAssetFactorLoading :
			_mapAssetFactorLoading.entrySet())
		{
			if (meAssetFactorLoading.getKey().startsWith (strAssetID))
				mapAssetFactorLoading.put (strAssetID, meAssetFactorLoading.getValue());
		}

		return mapAssetFactorLoading;
	}

	/**
	 * Retrieve the Loadings for the specified Factor
	 * 
	 * @param strFactorID The Factor ID
	 * 
	 * @return The Loadings for the specified Factor
	 */

	public java.util.Map<java.lang.String, java.lang.Double> factorAssetLoading (
		final java.lang.String strFactorID)
	{
		if (!containsFactor (strFactorID)) return null;

		java.util.Map<java.lang.String, java.lang.Double> mapFactorAssetLoading = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> meFactorAssetLoading :
			_mapFactorAssetLoading.entrySet())
		{
			if (meFactorAssetLoading.getKey().startsWith (strFactorID))
				mapFactorAssetLoading.put (strFactorID, meFactorAssetLoading.getValue());
		}

		return mapFactorAssetLoading;
	}

	/**
	 * Retrieve the Cross Factor Covariance Entry
	 * 
	 * @param strFactorID1 The Factor ID #1
	 * @param strFactorID2 The Factor ID #2
	 * 
	 * @return The Cross Factor Covariance Entry
	 * 
	 * @throws Thrown if the Inputs are Invalid
	 */

	public double factorCovariance (
		final java.lang.String strFactorID1,
		final java.lang.String strFactorID2)
		throws java.lang.Exception
	{
		if (!containsFactor (strFactorID1) || !containsFactor (strFactorID2))
			throw new java.lang.Exception ("AttributeJointFactor::factorCovariance => Invalid Inputs");

		return _mapFactorCovariance.get (strFactorID1 + "::" + strFactorID2);
	}

	/**
	 * Retrieve the Specific Risk Entry
	 * 
	 * @param strAssetID The Asset ID
	 * 
	 * @return The Specific Risk Entry
	 * 
	 * @throws Thrown if the Inputs are Invalid
	 */

	public double specificRisk (
		final java.lang.String strAssetID)
		throws java.lang.Exception
	{
		if (!containsFactor (strAssetID))
			throw new java.lang.Exception ("AttributeJointFactor::specificRisk => Invalid Inputs");

		return _mapSpecificRisk.get (strAssetID);
	}
}
