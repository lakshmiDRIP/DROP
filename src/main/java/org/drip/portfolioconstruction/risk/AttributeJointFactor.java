
package org.drip.portfolioconstruction.risk;

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
 * <i>AttributeJointFactor</i> contains the Factor Based Loadings that determines the Joint Attributes
 * between the Pair of Assets.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/risk/README.md">Portfolio Construction Risk/Covariance Component</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class AttributeJointFactor
	extends org.drip.portfolioconstruction.core.Block
{
	private java.util.Map<java.lang.String, java.lang.Double> _assetFactorLoadingMap =
		new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	private java.util.Map<java.lang.String, java.lang.Double> _factorAssetLoadingMap =
		new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	private java.util.Map<java.lang.String, java.lang.Double> _factorFactorAttributeMap =
		new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	private java.util.Map<java.lang.String, java.lang.Double> _mapAssetSpecificAttribute =
		new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	/**
	 * Generate a Standard Instance of AttributeJointFactor
	 * 
	 * @param name AttributeJointFactor Instance Name
	 * @param id AttributeJointFactor Instance ID
	 * @param description AttributeJointFactor Description
	 * @param assetIDArray Array of Asset IDs
	 * @param factorIDArray Array of FactorIDs
	 * @param assetFactorLoadingGrid Matrix of Asset-Factor Loadings
	 * @param crossFactorAttributeGrid Matrix of Factor-Factor Attributes
	 * @param assetSpecificAttributeArray Array of Specific Attributes
	 * 
	 * @return The Standard Instance of AttributeJointFactor
	 */

	public static final AttributeJointFactor Standard (
		final java.lang.String name,
		final java.lang.String id,
		final java.lang.String description,
		final java.lang.String[] assetIDArray,
		final java.lang.String[] factorIDArray,
		final double[][] assetFactorLoadingGrid,
		final double[][] crossFactorAttributeGrid,
		final double[] assetSpecificAttributeArray)
	{
		if (null == assetIDArray ||
			null == factorIDArray ||
			null == assetFactorLoadingGrid ||
			null == crossFactorAttributeGrid ||
			null == assetSpecificAttributeArray)
		{
			return null;
		}

		int assetCount = assetIDArray.length;
		int factorCount = factorIDArray.length;
		AttributeJointFactor attributeJointFactor = null;

		if (0 == assetCount ||
			0 == factorCount ||
			assetCount != assetFactorLoadingGrid.length ||
			factorCount != crossFactorAttributeGrid.length ||
			assetCount != assetSpecificAttributeArray.length)
		{
			return null;
		}

		try
		{
			attributeJointFactor = new AttributeJointFactor (
				name,
				id,
				description
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (int assetIndex = 0;
			assetIndex < assetCount;
			++assetIndex)
		{
			for (int factorIndex = 0;
				factorIndex < factorCount;
				++factorIndex)
			{
				if (!attributeJointFactor.addAssetFactorLoading (
					assetIDArray[assetIndex],
					factorIDArray[factorIndex],
					assetFactorLoadingGrid[assetIndex][factorIndex]
				))
				{
					return null;
				}
			}

			if (!attributeJointFactor.addSpecificAttribute (
				assetIDArray[assetIndex],
				assetSpecificAttributeArray[assetIndex]
			))
			{
				return null;
			}
		}

		for (int factorIndex1 = 0;
			factorIndex1 < factorCount;
			++factorIndex1)
		{
			for (int factorIndex2 = 0;
				factorIndex2 < factorCount;
				++factorIndex2)
			{
				if (!attributeJointFactor.addFactorAttribute (
					factorIDArray[factorIndex1],
					factorIDArray[factorIndex2],
					crossFactorAttributeGrid[factorIndex1][factorIndex2]
				))
				{
					return null;
				}
			}
		}

		return attributeJointFactor;
	}

	/**
	 * AttributeJointFactor Constructor
	 * 
	 * @param name The Name
	 * @param id The ID
	 * @param description The Description
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AttributeJointFactor (
		final java.lang.String name,
		final java.lang.String id,
		final java.lang.String description)
		throws java.lang.Exception
	{
		super (name, id, description);
	}

	/**
	 * Retrieve the Joint Asset-Factor Loading Map
	 * 
	 * @return The Joint Asset-Factor Loading Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> assetFactorLoading()
	{
		return _assetFactorLoadingMap;
	}

	/**
	 * Retrieve the Joint Factor-Asset Loading Map
	 * 
	 * @return The Joint Factor-Asset Loading Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> factorAssetLoading()
	{
		return _factorAssetLoadingMap;
	}

	/**
	 * Retrieve the Factor-to-Factor Attribute Map
	 * 
	 * @return The Factor-to-Joint Attribute Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> factorJointAttribute()
	{
		return _factorFactorAttributeMap;
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
	 * @param assetID The Asset ID
	 * @param factorID The Factor ID
	 * @param factorLoading The Factor Loading Coefficient
	 * 
	 * @return TRUE - The Asset's Factor Loading Coefficient successfully added
	 */

	public boolean addAssetFactorLoading (
		final java.lang.String assetID,
		final java.lang.String factorID,
		final double factorLoading)
	{
		if (null == assetID || assetID.isEmpty() ||
			null == factorID || factorID.isEmpty() ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				factorLoading
			)
		)
		{
			return false;
		}

		_assetFactorLoadingMap.put (
			assetID + "::" + factorID,
			factorLoading
		);

		_factorAssetLoadingMap.put (
			factorID + "::" + assetID,
			factorLoading
		);

		return true;
	}

	/**
	 * Add the Cross Factor Attribute
	 * 
	 * @param factorID1 The Factor #1 ID
	 * @param factorID2 The Factor #2 ID
	 * @param crossFactorAttribute The Cross Factor Attribute
	 * 
	 * @return TRUE - The Cross Factor Attribute successfully added
	 */

	public boolean addFactorAttribute (
		final java.lang.String factorID1,
		final java.lang.String factorID2,
		final double crossFactorAttribute)
	{
		if (null == factorID1 || factorID1.isEmpty() ||
			null == factorID2 || factorID2.isEmpty() ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				crossFactorAttribute
			)
		)
		{
			return false;
		}

		_factorFactorAttributeMap.put (
			factorID1 + "::" + factorID2,
			crossFactorAttribute
		);

		_factorFactorAttributeMap.put (
			factorID2 + "::" + factorID1,
			crossFactorAttribute
		);

		return true;
	}

	/**
	 * Add the Asset's Specific Attribute
	 * 
	 * @param assetID The Asset ID
	 * @param specificAttribute The Asset's Specific Attribute
	 * 
	 * @return TRUE - The Asset's Specific Risk successfully added
	 */

	public boolean addSpecificAttribute (
		final java.lang.String assetID,
		final double specificAttribute)
	{
		if (null == assetID || assetID.isEmpty() ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				specificAttribute
			)
		)
		{
			return false;
		}

		_mapAssetSpecificAttribute.put (
			assetID,
			specificAttribute
		);

		return true;
	}

	/**
	 * Check if the Asset is represented
	 * 
	 * @param assetID The Asset ID
	 * 
	 * @return TRUE - The Asset is represented
	 */

	public boolean containsAsset (
		final java.lang.String assetID)
	{
		return null != assetID && !assetID.isEmpty() &&
			_assetFactorLoadingMap.containsKey (
				assetID
			) && _mapAssetSpecificAttribute.containsKey (
				assetID
			);
	}

	/**
	 * Check if the Factor is available
	 * 
	 * @param factorID The Factor ID
	 * 
	 * @return TRUE - The Factor is available
	 */

	public boolean containsFactor (
		final java.lang.String factorID)
	{
		return null != factorID && !factorID.isEmpty() &&
			_factorAssetLoadingMap.containsKey (
				factorID
			) && _factorFactorAttributeMap.containsKey (
				factorID
			);
	}

	/**
	 * Retrieve the Factor Loading for the specified Asset
	 * 
	 * @param assetID The Asset ID
	 * 
	 * @return The Factor Loading for the specified Asset
	 */

	public java.util.Map<java.lang.String, java.lang.Double> assetFactorLoading (
		final java.lang.String assetID)
	{
		if (!containsAsset (
			assetID
		))
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> assetFactorLoadingMap =
			new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> assetFactorLoadingEntry :
			_assetFactorLoadingMap.entrySet())
		{
			if (assetFactorLoadingEntry.getKey().startsWith (
				assetID
			))
			{
				assetFactorLoadingMap.put (
					assetID,
					assetFactorLoadingEntry.getValue()
				);
			}
		}

		return assetFactorLoadingMap;
	}

	/**
	 * Retrieve the Loadings for the specified Factor
	 * 
	 * @param factorID The Factor ID
	 * 
	 * @return The Loadings for the specified Factor
	 */

	public java.util.Map<java.lang.String, java.lang.Double> factorAssetLoading (
		final java.lang.String factorID)
	{
		if (!containsFactor (
			factorID
		))
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> factorAssetLoadingMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> factorAssetLoadingEntry :
			_factorAssetLoadingMap.entrySet())
		{
			if (factorAssetLoadingEntry.getKey().startsWith (
				factorID
			))
			{
				factorAssetLoadingMap.put (
					factorID,
					factorAssetLoadingEntry.getValue()
				);
			}
		}

		return factorAssetLoadingMap;
	}

	/**
	 * Retrieve the Cross Factor Attribute Entry
	 * 
	 * @param factorID1 The Factor ID #1
	 * @param factorID2 The Factor ID #2
	 * 
	 * @return The Cross Factor Attribute Entry
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double crossFactorAttribute (
		final java.lang.String factorID1,
		final java.lang.String factorID2)
		throws java.lang.Exception
	{
		if (!containsFactor (
				factorID1
			) || !containsFactor (
				factorID2
			)
		)
		{
			throw new java.lang.Exception (
				"AttributeJointFactor::crossFactorAttribute => Invalid Inputs"
			);
		}

		return _factorFactorAttributeMap.get (
			factorID1 + "::" + factorID2
		);
	}

	/**
	 * Retrieve the Asset Specific Attribute
	 * 
	 * @param assetID The Asset ID
	 * 
	 * @return The Asset Specific Attribute
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double assetSpecificAttribute (
		final java.lang.String assetID)
		throws java.lang.Exception
	{
		if (!containsFactor (
			assetID
		))
		{
			throw new java.lang.Exception (
				"AttributeJointFactor::assetSpecificAttribute => Invalid Inputs"
			);
		}

		return _mapAssetSpecificAttribute.get (
			assetID
		);
	}

	/**
	 * Compute the Cross Asset Attribute
	 * 
	 * @param assetID1 Asset ID #1
	 * @param assetID2 Asset ID #2
	 * 
	 * @return The Cross Asset Attribute
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double crossAssetAttribute (
		final java.lang.String assetID1,
		final java.lang.String assetID2)
		throws java.lang.Exception
	{
		java.util.Map<java.lang.String, java.lang.Double> asset1FactorLoadingMap = assetFactorLoading (
			assetID1
		);

		java.util.Map<java.lang.String, java.lang.Double> asset2FactorLoadingMap = assetFactorLoading (
			assetID2
		);

		if (null == asset1FactorLoadingMap || null == asset2FactorLoadingMap)
		{
			throw new java.lang.Exception (
				"AttributeJointFactor::crossAssetAttribute => Invalid Factor Loadings"
			);
		}

		double crossAssetAttribute = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> asset1FactorLoadingEntry :
			asset1FactorLoadingMap.entrySet())
		{
			java.lang.String factorID = asset1FactorLoadingEntry.getKey();

			if (!asset2FactorLoadingMap.containsKey (
					factorID
				) || !_factorFactorAttributeMap.containsKey (
					factorID
				)
			)
			{
				throw new java.lang.Exception (
					"AttributeJointFactor::crossAssetAttribute => Loading not available for " + assetID2 +
						" for factor " + factorID
				);
			}

			crossAssetAttribute += asset1FactorLoadingMap.get (
				factorID
			) * asset2FactorLoadingMap.get (
				factorID
			) * crossFactorAttribute (
				factorID,
				factorID
			);
		}

		return crossAssetAttribute;
	}
}
