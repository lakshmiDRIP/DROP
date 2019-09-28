
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

public class AttributeJointDense
	extends org.drip.portfolioconstruction.core.Block
{
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _attributeMap =
		new org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

	/**
	 * AttributeJointDense Constructor
	 * 
	 * @param name The Name
	 * @param id The ID
	 * @param description The Description
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AttributeJointDense (
		final java.lang.String name,
		final java.lang.String id,
		final java.lang.String description)
		throws java.lang.Exception
	{
		super (
			name,
			id,
			description
		);
	}

	/**
	 * Add the Attribute for an Asset Pair
	 * 
	 * @param assetID1 The Asset ID #1
	 * @param assetID2 The Asset ID #2
	 * @param attribute The Attribute
	 * 
	 * @return TRUE - The Asset Pair's Attribute successfully added.
	 */

	public boolean add (
		final java.lang.String assetID1,
		final java.lang.String assetID2,
		final double attribute)
	{
		if (null == assetID1 || assetID1.isEmpty() ||
			null == assetID2 || assetID2.isEmpty() ||
			!org.drip.numerical.common.NumberUtil.IsValid (attribute))
		{
			return false;
		}

		_attributeMap.put (
			assetID1 + "::" + assetID2,
			attribute
		);

		_attributeMap.put (
			assetID2 + "::" + assetID1,
			attribute
		);

		return true;
	}

	/**
	 * Retrieve the Pair Attribute
	 * 
	 * @param assetID1 The Asset ID #1
	 * @param assetID2 The Asset ID #2
	 * 
	 * @return The Pair Attribute
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double attribute (
		final java.lang.String assetID1,
		final java.lang.String assetID2)
		throws java.lang.Exception
	{
		if (null == assetID1 || assetID1.isEmpty() ||
			null == assetID2 || assetID2.isEmpty())
		{
			throw new java.lang.Exception ("AttributeJointDense::attribute => Invalid Inputs");
		}

		java.lang.String jointAtributeKey = assetID1 + "::" + assetID2;

		if (!_attributeMap.containsKey (
			jointAtributeKey
		))
		{
			throw new java.lang.Exception ("AttributeJointDense::attribute => Invalid Inputs");
		}

		return _attributeMap.get (jointAtributeKey);
	}

	/**
	 * Retrieve the Map of Asset Attributes
	 * 
	 * @return Map of the Asset Attributes
	 */

	public java.util.Map<java.lang.String, java.lang.Double> attributeMap()
	{
		return _attributeMap;
	}
}
