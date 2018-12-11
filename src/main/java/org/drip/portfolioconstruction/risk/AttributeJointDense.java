
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
 * <i>AttributeJointDense</i> contains the Joint Dense Attributes for the Pair of the Set of Assets.
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

public class AttributeJointDense extends org.drip.portfolioconstruction.core.Block {
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapAttribute = new
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

	/**
	 * AttributeJointDense Constructor
	 * 
	 * @param strName The Name
	 * @param strID The ID
	 * @param strDescription The Description
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AttributeJointDense (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription)
		throws java.lang.Exception
	{
		super (strName, strID, strDescription);
	}

	/**
	 * Add the Attribute for an Asset Pair
	 * 
	 * @param strAssetID1 The Asset ID #1
	 * @param strAssetID2 The Asset ID #2
	 * @param dblAttribute The Attribute
	 * 
	 * @return TRUE - The Asset Pair's Attribute successfully added.
	 */

	public boolean add (
		final java.lang.String strAssetID1,
		final java.lang.String strAssetID2,
		final double dblAttribute)
	{
		if (null == strAssetID1 || strAssetID1.isEmpty() || null == strAssetID2 || strAssetID2.isEmpty() ||
			!org.drip.quant.common.NumberUtil.IsValid (dblAttribute))
			return false;

		_mapAttribute.put (strAssetID1 + "::" + strAssetID2, dblAttribute);

		_mapAttribute.put (strAssetID2 + "::" + strAssetID1, dblAttribute);

		return true;
	}

	/**
	 * Retrieve the Pair Attribute
	 * 
	 * @param strAssetID1 The Asset ID #1
	 * @param strAssetID2 The Asset ID #2
	 * 
	 * @return The Pair Attribute
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double attribute (
		final java.lang.String strAssetID1,
		final java.lang.String strAssetID2)
		throws java.lang.Exception
	{
		if (null == strAssetID1 || strAssetID1.isEmpty() || null == strAssetID2 || strAssetID2.isEmpty())
			throw new java.lang.Exception ("AttributeJointDense::attribute => Invalid Inputs");

		java.lang.String strJointAtributeKey = strAssetID1 + "::" + strAssetID2;

		if (!_mapAttribute.containsKey (strAssetID1 + "::" + strAssetID2))
			throw new java.lang.Exception ("AttributeJointDense::attribute => Invalid Inputs");

		return _mapAttribute.get (strJointAtributeKey);
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
}
