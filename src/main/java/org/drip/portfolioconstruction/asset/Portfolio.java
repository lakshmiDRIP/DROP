
package org.drip.portfolioconstruction.asset;

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
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>Portfolio</i> implements an Instance of the Portfolio of Assets.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/asset/README.md">Asset Characteristics, Bounds, Portfolio Benchmarks</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Portfolio
{
	private org.drip.portfolioconstruction.asset.AssetComponent[] _assetComponentArray = null;

	/**
	 * Construct a Portfolio Instance from the Array of Asset ID's and their Amounts
	 * 
	 * @param assetIDArray Array of Asset IDs
	 * @param amountArray Array of Amounts
	 * 
	 * @return The Portfolio Instance
	 */

	public static final Portfolio Standard (
		final java.lang.String[] assetIDArray,
		final double[] amountArray)
	{
		if (null == assetIDArray || null == amountArray)
		{
			return null;
		}

		int assetCount = assetIDArray.length;
		org.drip.portfolioconstruction.asset.AssetComponent[] assetComponentArray = 0 == assetCount ?
			null : new org.drip.portfolioconstruction.asset.AssetComponent[assetCount];

		if (0 == assetCount || assetCount != amountArray.length)
		{
			return null;
		}

		try
		{
			for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
			{
				assetComponentArray[assetIndex] = new org.drip.portfolioconstruction.asset.AssetComponent (
					assetIDArray[assetIndex],
					amountArray[assetIndex]
				);
			}

			return new Portfolio (assetComponentArray);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Portfolio Constructor
	 * 
	 * @param assetComponentArray Array of the Asset Components
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Portfolio (
		final org.drip.portfolioconstruction.asset.AssetComponent[] assetComponentArray)
		throws java.lang.Exception
	{
		if (null == (_assetComponentArray = assetComponentArray))
		{
			throw new java.lang.Exception ("Portfolio Constructor => Invalid Inputs");
		}

		int assetCount = _assetComponentArray.length;

		if (0 == assetCount)
		{
			throw new java.lang.Exception ("Portfolio Constructor => Invalid Inputs");
		}

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			if (null == _assetComponentArray[assetIndex])
			{
				throw new java.lang.Exception ("Portfolio Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Array of the Asset Components
	 * 
	 * @return Array of the Asset Components
	 */

	public org.drip.portfolioconstruction.asset.AssetComponent[] assetComponentArray()
	{
		return _assetComponentArray;
	}

	/**
	 * Retrieve the Notional of the Portfolio
	 * 
	 * @return Notional of the Portfolio
	 */

	public double notional()
	{
		double notional = 0.;
		int assetCount = _assetComponentArray.length;

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			notional += _assetComponentArray[assetIndex].amount();
		}

		return notional;
	}

	/**
	 * Retrieve the Array of Asset IDs
	 * 
	 * @return The Array of Asset IDs
	 */

	public java.lang.String[] assetIDArray()
	{
		int assetCount = _assetComponentArray.length;
		java.lang.String[] assetIDArray = new java.lang.String[assetCount];

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			assetIDArray[assetIndex] = _assetComponentArray[assetIndex].id();
		}

		return assetIDArray;
	}

	/**
	 * Retrieve the Multivariate Meta Instance around the Assets
	 * 
	 * @return The Multivariate Meta Instance around the Assets
	 */

	public org.drip.measure.state.LabelledRd meta()
	{
		try
		{
			return org.drip.measure.state.LabelledRd.FromArray (assetIDArray());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Array of Asset Weights
	 * 
	 * @return The Array of Asset Weights
	 */

	public double[] weightArray()
	{
		int assetCount = _assetComponentArray.length;
		double[] weightArray = new double[assetCount];

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			weightArray[assetIndex] = _assetComponentArray[assetIndex].amount();
		}

		return weightArray;
	}

	/**
	 * Retrieve the Asset Component with the Lowest Weight
	 * 
	 * @return The Asset Component with the Lowest Weight
	 */

	public org.drip.portfolioconstruction.asset.AssetComponent lowestWeightAsset()
	{
		int assetCount = _assetComponentArray.length;
		org.drip.portfolioconstruction.asset.AssetComponent lowestWeightAsset = _assetComponentArray[0];

		double lowestWeight = _assetComponentArray[0].amount();

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			double amount = _assetComponentArray[assetIndex].amount();

			if (amount < lowestWeight)
			{
				lowestWeightAsset = _assetComponentArray[assetIndex];
				lowestWeight = amount;
			}
		}

		return lowestWeightAsset;
	}

	/**
	 * Retrieve the Asset Component with the Highest Weight
	 * 
	 * @return The Asset Component with the Highest Weight
	 */

	public org.drip.portfolioconstruction.asset.AssetComponent highestWeightAsset()
	{
		int assetCount = _assetComponentArray.length;
		org.drip.portfolioconstruction.asset.AssetComponent highestWeightAsset = _assetComponentArray[0];

		double highestWeight = _assetComponentArray[0].amount();

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			double amount = _assetComponentArray[assetIndex].amount();

			if (amount > highestWeight)
			{
				highestWeightAsset = _assetComponentArray[assetIndex];
				highestWeight = amount;
			}
		}

		return highestWeightAsset;
	}

	/**
	 * Retrieve the Portfolio Asset Cardinality
	 * 
	 * @return The Portfolio Asset Cardinality
	 */

	public int cardinality()
	{
		int cardinality = 0;
		int assetCount = _assetComponentArray.length;

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			if (0. !=  _assetComponentArray[assetIndex].amount())
			{
				++cardinality;
			}
		}

		return cardinality;
	}

	/**
	 * Retrieve the Expected Returns of the Portfolio
	 * 
	 * @param assetUniverseStatisticalProperties The Asset Pool Statistical Properties Instance
	 * 
	 * @return Expected Returns of the Portfolio
	 * 
	 * @throws java.lang.Exception Thrown if the Expected Returns cannot be calculated
	 */

	public double expectedReturn (
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties
			assetUniverseStatisticalProperties)
		throws java.lang.Exception
	{
		int assetCount = _assetComponentArray.length;
		double expectedReturn = 0.;

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			org.drip.portfolioconstruction.params.AssetStatisticalProperties assetStatisticalProperties =
				assetUniverseStatisticalProperties.assetStatisticalProperties
					(_assetComponentArray[assetIndex].id());

			if (null == assetStatisticalProperties)
			{
				throw new java.lang.Exception ("Portfolio::expectedReturn => Invalid Inputs");
			}

			expectedReturn += _assetComponentArray[assetIndex].amount() *
				assetStatisticalProperties.expectedReturn();
		}

		return expectedReturn;
	}

	/**
	 * Retrieve the Variance of the Portfolio
	 * 
	 * @param assetUniverseStatisticalProperties The Asset Pool Statistical Properties Instance
	 * 
	 * @return Variance of the Portfolio
	 * 
	 * @throws java.lang.Exception Thrown if the Variance cannot be calculated
	 */

	public double variance (
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties
			assetUniverseStatisticalProperties)
		throws java.lang.Exception
	{
		double variance = 0.;
		int assetCount = _assetComponentArray.length;

		for (int assetIndexI = 0; assetIndexI < assetCount; ++assetIndexI)
		{
			double amountI = _assetComponentArray[assetIndexI].amount();

			java.lang.String idI = _assetComponentArray[assetIndexI].id();

			org.drip.portfolioconstruction.params.AssetStatisticalProperties assetStatisticalPropertiesI =
				assetUniverseStatisticalProperties.assetStatisticalProperties (idI);

			if (null == assetStatisticalPropertiesI)
			{
				throw new java.lang.Exception ("Portfolio::variance => Invalid Inputs");
			}

			double varianceI = assetStatisticalPropertiesI.variance();

			for (int assetIndexJ = 0; assetIndexJ < assetCount; ++assetIndexJ)
			{
				java.lang.String idJ = _assetComponentArray[assetIndexJ].id();

				org.drip.portfolioconstruction.params.AssetStatisticalProperties assetStatisticalPropertiesJ
					= assetUniverseStatisticalProperties.assetStatisticalProperties (idJ);

				if (null == assetStatisticalPropertiesJ)
				{
					throw new java.lang.Exception ("Portfolio::variance => Invalid Inputs");
				}

				variance += amountI * _assetComponentArray[assetIndexJ].amount() * java.lang.Math.sqrt (
					varianceI * assetStatisticalPropertiesJ.variance()
				) * (
					assetIndexI == assetIndexJ ? 1. : assetUniverseStatisticalProperties.correlation (
						idI,
						idJ
					)
				);
			}
		}

		return variance;
	}
}
