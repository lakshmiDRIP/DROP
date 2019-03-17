
package org.drip.portfolioconstruction.risk;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>AttributeJointFactor</i> contains the Factor Based Loadings that determines the Joint Attributes
 * between the Pair of Assets.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/risk">Risk</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class AttributeJointFactor extends org.drip.portfolioconstruction.core.Block {
	private java.util.Map<java.lang.String, java.lang.Double> _mapAssetFactorLoading = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	private java.util.Map<java.lang.String, java.lang.Double> _mapFactorAssetLoading = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	private java.util.Map<java.lang.String, java.lang.Double> _mapFactorFactorAttribute = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	private java.util.Map<java.lang.String, java.lang.Double> _mapAssetSpecificAttribute = new
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
	 * @param aadblCrossFactorAttribute Matrix of Factor-Factor Attributes
	 * @param adblSpecificAttribute Array of Specific Attributes
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
		final double[][] aadblCrossFactorAttribute,
		final double[] adblSpecificAttribute)
	{
		if (null == astrAssetID || null == astrFactorID || null == aadblAssetFactorLoading || null ==
			aadblCrossFactorAttribute || null == adblSpecificAttribute)
			return null;

		AttributeJointFactor ajf = null;
		int iNumAsset = astrAssetID.length;
		int iNumFactor = astrFactorID.length;

		if (0 == iNumAsset || 0 == iNumFactor || iNumAsset != aadblAssetFactorLoading.length || iNumFactor !=
			aadblCrossFactorAttribute.length || iNumAsset != adblSpecificAttribute.length)
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

			if (!ajf.addSpecificAttribute (astrAssetID[iAsset], adblSpecificAttribute[iAsset])) return null;
		}

		for (int iFactor1 = 0; iFactor1 < iNumFactor; ++iFactor1) {
			for (int iFactor2 = 0; iFactor2 < iNumFactor; ++iFactor2) {
				if (!ajf.addFactorAttribute (astrFactorID[iFactor1], astrFactorID[iFactor2],
					aadblCrossFactorAttribute[iFactor1][iFactor2]))
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
	 * Retrieve the Factor-to-Factor Attribute Map
	 * 
	 * @return The Factor-to-Joint Attribute Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> factorJointAttribute()
	{
		return _mapFactorFactorAttribute;
	}

	/**
	 * Retrieve the Asset Specific Attribute
	 * 
	 * @return The Asset Specific Attribute
	 */

	public java.util.Map<java.lang.String, java.lang.Double> specificRisk()
	{
		return _mapAssetSpecificAttribute;
	}

	/**
	 * Add the Asset's Factor Loading Coefficient
	 * 
	 * @param strAssetID The Asset ID
	 * @param strFactorID The Factor ID
	 * @param dblFactorLoading The Factor Loading Coefficient
	 * 
	 * @return TRUE - The Asset's Factor Loading Coefficient successfully added
	 */

	public boolean addAssetFactorLoading (
		final java.lang.String strAssetID,
		final java.lang.String strFactorID,
		final double dblFactorLoading)
	{
		if (null == strAssetID || strAssetID.isEmpty() || null == strFactorID || strFactorID.isEmpty() ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblFactorLoading))
			return false;

		_mapAssetFactorLoading.put (strAssetID + "::" + strFactorID, dblFactorLoading);

		_mapFactorAssetLoading.put (strFactorID + "::" + strAssetID, dblFactorLoading);

		return true;
	}

	/**
	 * Add the Cross Factor Attribute
	 * 
	 * @param strFactorID1 The Factor #1 ID
	 * @param strFactorID2 The Factor #2 ID
	 * @param dblCrossFactorAttribute The Cross Factor Attribute
	 * 
	 * @return TRUE - The Cross Factor Attribute successfully added
	 */

	public boolean addFactorAttribute (
		final java.lang.String strFactorID1,
		final java.lang.String strFactorID2,
		final double dblCrossFactorAttribute)
	{
		if (null == strFactorID1 || strFactorID1.isEmpty() || null == strFactorID2 || strFactorID2.isEmpty()
			|| !org.drip.numerical.common.NumberUtil.IsValid (dblCrossFactorAttribute))
			return false;

		_mapFactorFactorAttribute.put (strFactorID1 + "::" + strFactorID2, dblCrossFactorAttribute);

		_mapFactorFactorAttribute.put (strFactorID2 + "::" + strFactorID1, dblCrossFactorAttribute);

		return true;
	}

	/**
	 * Add the Asset's Specific Attribute
	 * 
	 * @param strAssetID The Asset ID
	 * @param dblSpecificAttribute The Asset's Specific Attribute
	 * 
	 * @return TRUE - The Asset's Specific Risk successfully added
	 */

	public boolean addSpecificAttribute (
		final java.lang.String strAssetID,
		final double dblSpecificAttribute)
	{
		if (null == strAssetID || strAssetID.isEmpty() || !org.drip.numerical.common.NumberUtil.IsValid
			(dblSpecificAttribute))
			return false;

		_mapAssetSpecificAttribute.put (strAssetID, dblSpecificAttribute);

		return true;
	}

	/**
	 * Check if the Asset is represented
	 * 
	 * @param strAssetID The Asset ID
	 * 
	 * @return TRUE - The Asset is represented
	 */

	public boolean containsAsset (
		final java.lang.String strAssetID)
	{
		return null != strAssetID && !strAssetID.isEmpty() && _mapAssetFactorLoading.containsKey
			(strAssetID) && _mapAssetSpecificAttribute.containsKey (strAssetID);
	}

	/**
	 * Check if the Factor is available
	 * 
	 * @param strFactorID The Factor ID
	 * 
	 * @return TRUE - The Factor is available
	 */

	public boolean containsFactor (
		final java.lang.String strFactorID)
	{
		return null != strFactorID && !strFactorID.isEmpty() && _mapFactorAssetLoading.containsKey
			(strFactorID) && _mapFactorFactorAttribute.containsKey (strFactorID);
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
	 * Retrieve the Cross Factor Attribute Entry
	 * 
	 * @param strFactorID1 The Factor ID #1
	 * @param strFactorID2 The Factor ID #2
	 * 
	 * @return The Cross Factor Attribute Entry
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double crossFactorAttribute (
		final java.lang.String strFactorID1,
		final java.lang.String strFactorID2)
		throws java.lang.Exception
	{
		if (!containsFactor (strFactorID1) || !containsFactor (strFactorID2))
			throw new java.lang.Exception ("AttributeJointFactor::crossFactorAttribute => Invalid Inputs");

		return _mapFactorFactorAttribute.get (strFactorID1 + "::" + strFactorID2);
	}

	/**
	 * Retrieve the Asset Specific Attribute
	 * 
	 * @param strAssetID The Asset ID
	 * 
	 * @return The Asset Specific Attribute
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double assetSpecificAttribute (
		final java.lang.String strAssetID)
		throws java.lang.Exception
	{
		if (!containsFactor (strAssetID))
			throw new java.lang.Exception ("AttributeJointFactor::assetSpecificAttribute => Invalid Inputs");

		return _mapAssetSpecificAttribute.get (strAssetID);
	}

	/**
	 * Compute the Cross Asset Attribute
	 * 
	 * @param strAssetID1 Asset ID #1
	 * @param strAssetID2 Asset ID #2
	 * 
	 * @return The Cross Asset Attribute
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double crossAssetAttribute (
		final java.lang.String strAssetID1,
		final java.lang.String strAssetID2)
		throws java.lang.Exception
	{
		java.util.Map<java.lang.String, java.lang.Double> mapAsset1FactorLoading = assetFactorLoading
			(strAssetID1);

		java.util.Map<java.lang.String, java.lang.Double> mapAsset2FactorLoading = assetFactorLoading
			(strAssetID2);

		if (null == mapAsset1FactorLoading || null == mapAsset2FactorLoading)
			throw new java.lang.Exception
				("AttributeJointFactor::crossAssetAttribute => Invalid Factor Loadings");

		double dblCrossAssetAttribute = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> meAsset1FactorLoading :
			mapAsset1FactorLoading.entrySet())
		{
			java.lang.String strFactorID = meAsset1FactorLoading.getKey();

			if (!mapAsset2FactorLoading.containsKey (strFactorID) || !_mapFactorFactorAttribute.containsKey
				(strFactorID))
				throw new java.lang.Exception
					("AttributeJointFactor::crossAssetAttribute => Loading not available for " + strAssetID2
						+ "for factor " + strFactorID);

			dblCrossAssetAttribute += mapAsset1FactorLoading.get (strFactorID) * mapAsset2FactorLoading.get
				(strFactorID) * crossFactorAttribute (strFactorID, strFactorID);
		}

		return dblCrossAssetAttribute;
	}
}
