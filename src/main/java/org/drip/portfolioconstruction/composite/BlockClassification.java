
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
 * <i>BlockClassification</i> contains the Classifications for the specified Set of Assets.
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

public class BlockClassification extends org.drip.portfolioconstruction.core.Block {
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Boolean> _mapMembership = new
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Boolean>();

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

		java.util.List<java.lang.Double> lsValue = new java.util.ArrayList<java.lang.Double>();

		for (java.lang.String strAssetID : setAsset) {
			try {
				lsValue.add (java.lang.Double.parseDouble (java.lang.Boolean.toString (membership
					(strAssetID)).toString()));
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
