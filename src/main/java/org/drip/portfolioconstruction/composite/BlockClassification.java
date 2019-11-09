
package org.drip.portfolioconstruction.composite;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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

public class BlockClassification extends org.drip.portfolioconstruction.core.Block
{
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Boolean> _membershipMap =
		new org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Boolean>();

	/**
	 * Classification Constructor
	 * 
	 * @param name The Name
	 * @param id The ID
	 * @param description The Description
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BlockClassification (
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
	 * Add an Asset's Membership
	 * 
	 * @param assetID The Asset ID
	 * @param membership The Membership TURUE or FALSE
	 * 
	 * @return TRUE - The Asset's Attribute successfully added.
	 */

	public boolean add (
		final java.lang.String assetID,
		final boolean membership)
	{
		if (null == assetID || assetID.isEmpty())
		{
			return false;
		}

		_membershipMap.put (
			assetID,
			membership
		);

		return true;
	}

	/**
	 * Retrieve the Asset's Membership
	 * 
	 * @param assetID The Asset ID
	 * 
	 * @return The Asset's Membership
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public boolean membership (
		final java.lang.String assetID)
		throws java.lang.Exception
	{
		if (null == assetID || assetID.isEmpty() || !_membershipMap.containsKey (assetID))
		{
			throw new java.lang.Exception ("BlockClassification::membership => Invalid Inputs");
		}

		return _membershipMap.get (assetID);
	}

	/**
	 * Retrieve the Map of Asset Classification
	 * 
	 * @return Map of the Asset Classification
	 */

	public java.util.Map<java.lang.String, java.lang.Boolean> membershipMap()
	{
		return _membershipMap;
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
		if (null == holdings)
		{
			return null;
		}

		java.util.Set<java.lang.String> assetIDSet = holdings.assetIDSet();

		java.util.List<java.lang.Double> classificationValueList =
			new java.util.ArrayList<java.lang.Double>();

		for (java.lang.String strAssetID : assetIDSet)
		{
			try
			{
				classificationValueList.add (
					java.lang.Double.parseDouble (
						java.lang.Boolean.toString (
							membership (
								strAssetID
							)
						).toString()
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

		if (classificationValueList.size() != assetCount)
		{
			return null;
		}

		int assetIndex = 0;
		double[] assetClassificationValueArray = new double[assetCount];

		for (double assetClassificationValue : classificationValueList)
		{
			assetClassificationValueArray[assetIndex++] = assetClassificationValue;
		}

		return assetClassificationValueArray;
	}
}
