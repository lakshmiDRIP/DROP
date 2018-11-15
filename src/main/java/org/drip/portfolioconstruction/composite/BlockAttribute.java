
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
 * <i>BlockAttribute</i> contains the Marginal Attributes for the specified Set of Assets.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/composite">Composite</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/AssetAllocation">Asset Allocation Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BlockAttribute extends org.drip.portfolioconstruction.core.Block {
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapAttribute = new
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

	/**
	 * BlockAttribute Constructor
	 * 
	 * @param strName The Name
	 * @param strID The ID
	 * @param strDescription The Description
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BlockAttribute (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription)
		throws java.lang.Exception
	{
		super (strName, strID, strDescription);
	}

	/**
	 * Add an Asset's Attribute
	 * 
	 * @param strAssetID The Asset ID
	 * @param dblAttribute The Attribute
	 * 
	 * @return TRUE - The Asset's Attribute successfully added.
	 */

	public boolean add (
		final java.lang.String strAssetID,
		final double dblAttribute)
	{
		if (null == strAssetID || strAssetID.isEmpty() || !org.drip.quant.common.NumberUtil.IsValid
			(dblAttribute))
			return false;

		_mapAttribute.put (strAssetID, dblAttribute);

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
		return null != strAssetID && !_mapAttribute.containsKey (strAssetID);
	}

	/**
	 * Retrieve the Asset's Attribute Value
	 * 
	 * @param strAssetID The Asset ID
	 * 
	 * @return The Asset's Attribute Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double value (
		final java.lang.String strAssetID)
		throws java.lang.Exception
	{
		if (null == strAssetID || strAssetID.isEmpty() || !_mapAttribute.containsKey (strAssetID))
			throw new java.lang.Exception ("BlockAttribute::attribute => Invalid Inputs");

		return _mapAttribute.get (strAssetID);
	}

	/**
	 * Retrieve the Map of Asset Attributes
	 * 
	 * @return Map of the Asset Attributes
	 */

	public java.util.Map<java.lang.String, java.lang.Double> attribute()
	{
		return _mapAttribute;
	}

	/**
	 * Constrict the Attribute Values to those of the Holdings
	 * 
	 * @param holdings The Holdings Instance
	 * 
	 * @return The Array of Attribute Values
	 */

	public double[] constrict (
		final org.drip.portfolioconstruction.composite.Holdings holdings)
	{
		if (null == holdings) return null;

		java.util.Set<java.lang.String> setAsset = holdings.assets();

		java.util.List<java.lang.Double> lsValue = new java.util.ArrayList<java.lang.Double>();

		for (java.lang.String strAssetID : setAsset) {
			try {
				lsValue.add (value (strAssetID));
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
