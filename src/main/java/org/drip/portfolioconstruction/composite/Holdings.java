
package org.drip.portfolioconstruction.composite;

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
 * <i>Holdings</i> is a Portfolio of Holdings in the specified Set of Assets.
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

public class Holdings extends org.drip.portfolioconstruction.core.Block
{
	private java.lang.String _currency = "";

	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _quantityMap =
		new org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

	/**
	 * Holdings Constructor
	 * 
	 * @param name The Asset Name
	 * @param id The Asset ID
	 * @param description The Asset Description
	 * @param currency The Account Currency
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Holdings (
		final java.lang.String name,
		final java.lang.String id,
		final java.lang.String description,
		final java.lang.String currency)
		throws java.lang.Exception
	{
		super (
			name,
			id,
			description
		);

		if (null == (_currency = currency))
		{
			throw new java.lang.Exception ("Holdings Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Set of Asset IDs
	 * 
	 * @return The Set of Asset IDs
	 */

	public java.util.Set<java.lang.String> assetIDSet()
	{
		return _quantityMap.keySet();
	}

	/**
	 * Retrieve the Map of Holdings Amount
	 * 
	 * @return The Map of Holdings Amount
	 */

	public java.util.Map<java.lang.String, java.lang.Double> quantityMap()
	{
		return _quantityMap;
	}

	/**
	 * Add an Asset/Amount Pair
	 * 
	 * @param assetID The Asset ID
	 * @param quantity The Amount in the Portfolio
	 * 
	 * @return TRUE - The Asset/Amount has been successfully added
	 */

	public boolean add (
		final java.lang.String assetID,
		final double quantity)
	{
		if (null == assetID || assetID.isEmpty() ||
			!org.drip.numerical.common.NumberUtil.IsValid (quantity))
		{
			return false;
		}

		_quantityMap.put (
			assetID,
			quantity
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
		return null != assetID && !_quantityMap.containsKey (assetID);
	}

	/**
	 * Retrieves the Holdings Quantity for the Asset (if it exists)
	 * 
	 * @param assetID The Asset ID
	 * 
	 * @return The Holdings Quantity for the Asset (if it exists)
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double quantity (
		final java.lang.String assetID)
		throws java.lang.Exception
	{
		if (!contains (assetID))
		{
			throw new java.lang.Exception ("Holdings::quantity => Invalid Inputs");
		}

		return _quantityMap.get (assetID);
	}

	/**
	 * Retrieve the Currency
	 * 
	 * @return The Currency
	 */

	public java.lang.String currency()
	{
		return _currency;
	}

	/**
	 * Retrieves the Cash Holdings
	 * 
	 * @return The Cash Holdings
	 */

	public double cash()
	{
		try
		{
			return quantity ("CASH::" + _currency);
		}
		catch (java.lang.Exception e)
		{
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
		int size = _quantityMap.size();

		if (0 == size)
		{
			return null;
		}

		int assetIndex = 0;
		double[] quantityArray = new double[size];

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> quantityMapEntry :
			_quantityMap.entrySet())
		{
			quantityArray[assetIndex++] = quantityMapEntry.getValue();
		}

		return quantityArray;
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
		if (null == holdingsOther)
		{
			return null;
		}

		java.util.Set<java.lang.String> assetIDSet = holdingsOther.assetIDSet();

		java.util.List<java.lang.Double> quantityList = new java.util.ArrayList<java.lang.Double>();

		for (java.lang.String assetID : assetIDSet)
		{
			try
			{
				quantityList.add (
					contains (
						assetID
					) ? quantity (
						assetID
					) : 0.
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		int assetCount = assetIDSet.size();

		if (quantityList.size() != assetCount)
		{
			return null;
		}

		int assetIndex = 0;
		double[] assetQuantityArray = new double[assetCount];

		for (double quantity : quantityList)
		{
			assetQuantityArray[assetIndex++] = quantity;
		}

		return assetQuantityArray;
	}
}
