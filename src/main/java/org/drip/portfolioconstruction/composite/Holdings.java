
package org.drip.portfolioconstruction.composite;

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
 * <i>Holdings</i> is a Portfolio of Holdings in the specified Set of Assets.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/composite">Composite</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Holdings extends org.drip.portfolioconstruction.core.Block {
	private java.lang.String _strCurrency = "";

	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapQuantity = new
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

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
		if (null == strAssetID || strAssetID.isEmpty() || !org.drip.numerical.common.NumberUtil.IsValid
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

	/**
	 * Retrieve the Array Form of the Holdings Quantity
	 * 
	 * @return Array Form of the Holdings Quantity
	 */

	public double[] toArray()
	{
		int iSize = _mapQuantity.size();

		if (0 == iSize) return null;

		int i = 0;
		double[] adblQuantity = new double[iSize];

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> meQuantity : _mapQuantity.entrySet())
			adblQuantity[i++] = meQuantity.getValue();

		return adblQuantity;
	}

	/**
	 * Constrict "This" Holdings to those of the Assets in the "Other" Holdings
	 * 
	 * @param holdingsOther The Other Holdings Instance
	 * 
	 * @return Constriction of "This" Holdings
	 */

	public double[] constrict (
		final org.drip.portfolioconstruction.composite.Holdings holdingsOther)
	{
		if (null == holdingsOther) return null;

		java.util.Set<java.lang.String> setAsset = holdingsOther.assets();

		java.util.List<java.lang.Double> lsValue = new java.util.ArrayList<java.lang.Double>();

		for (java.lang.String strAssetID : setAsset) {
			try {
				lsValue.add (contains (strAssetID) ? quantity (strAssetID) : 0.);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		int iNumAsset = setAsset.size();

		if (lsValue.size() != iNumAsset) return null;

		int iAssetCount = 0;
		double[] adblAssetAttributeValue = new double[iNumAsset];

		for (double dblAssetValue : lsValue)
			adblAssetAttributeValue[iAssetCount++] = dblAssetValue;

		return adblAssetAttributeValue;
	}
}
