
package org.drip.portfolioconstruction.composite;

import org.drip.portfolioconstruction.core.BlockCategory;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/composite/README.md">Portfolio Construction Component Groups Suite</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BlockAttribute extends org.drip.portfolioconstruction.core.Block
{
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _attributeMap =
		new org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

	/**
	 * BlockAttribute Constructor
	 * 
	 * @param name The Name
	 * @param id The ID
	 * @param description The Description
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BlockAttribute (
		final java.lang.String name,
		final java.lang.String id,
		final java.lang.String description)
		throws java.lang.Exception
	{
		super (
			name,
			id,
			description,
			BlockCategory.BLOCK_ATTRIBUTE
		);
	}

	/**
	 * Add an Asset's Attribute
	 * 
	 * @param assetID The Asset ID
	 * @param attribute The Attribute
	 * 
	 * @return TRUE - The Asset's Attribute successfully added.
	 */

	public boolean add (
		final java.lang.String assetID,
		final double attribute)
	{
		if (null == assetID || assetID.isEmpty() ||
			!org.drip.numerical.common.NumberUtil.IsValid (attribute))
		{
			return false;
		}

		_attributeMap.put (
			assetID,
			attribute
		);

		return true;
	}

	/**
	 * Indicates if an Asset exists in the Holdings
	 * 
	 * @param assetID The Asset ID
	 * 
	 * @return TRUE - The Asset is Part of the Holdings (may have Zero Value though)
	 */

	public boolean contains (
		final java.lang.String assetID)
	{
		return null != assetID && !_attributeMap.containsKey (assetID);
	}

	/**
	 * Retrieve the Asset's Attribute Value
	 * 
	 * @param assetID The Asset ID
	 * 
	 * @return The Asset's Attribute Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double value (
		final java.lang.String assetID)
		throws java.lang.Exception
	{
		if (null == assetID || assetID.isEmpty() || !_attributeMap.containsKey (assetID))
		{
			throw new java.lang.Exception ("BlockAttribute::attribute => Invalid Inputs");
		}

		return _attributeMap.get (assetID);
	}

	/**
	 * Retrieve the Map of Asset Attributes
	 * 
	 * @return Map of the Asset Attributes
	 */

	public java.util.Map<java.lang.String, java.lang.Double> attribute()
	{
		return _attributeMap;
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
		if (null == holdings)
		{
			return null;
		}

		java.util.Set<java.lang.String> assetIDSet = holdings.assetIDSet();

		java.util.List<java.lang.Double> attributeValueList = new java.util.ArrayList<java.lang.Double>();

		for (java.lang.String assetID : assetIDSet)
		{
			try
			{
				attributeValueList.add (
					value (
						assetID
					)
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		int assetCount = assetIDSet.size();

		if (attributeValueList.size() != assetCount)
		{
			return null;
		}

		int assetIndex = 0;
		double[] assetAttributeValueArray = new double[assetCount];

		for (double assetAttributeValue : attributeValueList)
		{
			assetAttributeValueArray[assetIndex++] = assetAttributeValue;
		}

		return assetAttributeValueArray;
	}
}
