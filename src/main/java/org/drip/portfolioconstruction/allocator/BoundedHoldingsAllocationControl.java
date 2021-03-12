
package org.drip.portfolioconstruction.allocator;

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
 * <i>BoundedHoldingsAllocationControl</i> holds the Parameters needed to build the Portfolio with
 * Bounds on the Underlying Assets.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator/README.md">MVO Based Portfolio Allocation Construction</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BoundedHoldingsAllocationControl extends
	org.drip.portfolioconstruction.allocator.HoldingsAllocationControl
{
	private org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.asset.AssetBounds>
		_assetBoundsMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.asset.AssetBounds>();

	private double lowerPivotStartingWeight (
		final java.lang.String assetID)
	{
		org.drip.portfolioconstruction.asset.AssetBounds assetBounds = _assetBoundsMap.get (
			assetID
		);

		double lowerWeight = assetBounds.lower();

		if (org.drip.numerical.common.NumberUtil.IsValid (
			lowerWeight
		))
		{
			return lowerWeight;
		}

		double upperWeight = assetBounds.upper();

		return !org.drip.numerical.common.NumberUtil.IsValid (
			upperWeight
		) ? 0. : upperWeight - 0.5;
	}

	/**
	 * BoundedHoldingsAllocationControl Constructor
	 * 
	 * @param assetIDArray Array of Assets ID
	 * @param customRiskUtilitySettings The Quadratic Custom Risk Utility Settings
	 * @param equalityConstraintSettings The Portfolio Equality Constraint Settings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BoundedHoldingsAllocationControl (
		final java.lang.String[] assetIDArray,
		final org.drip.portfolioconstruction.allocator.CustomRiskUtilitySettings customRiskUtilitySettings,
		final org.drip.portfolioconstruction.allocator.EqualityConstraintSettings equalityConstraintSettings)
		throws java.lang.Exception
	{
		super (
			assetIDArray,
			customRiskUtilitySettings,
			equalityConstraintSettings
		);
	}

	/**
	 * Retrieve the Portfolio Asset Bounds Map
	 * 
	 * @return The Portfolio Asset Bounds Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.asset.AssetBounds>
			assetBoundsMap()
	{
		return _assetBoundsMap;
	}

	/**
	 * Set the Bounds for the specified Asset
	 * 
	 * @param assetID The Asset ID
	 * @param lowerBound The Asset Share Lower Bound
	 * @param upperBound The Asset Share Upper Bound
	 * 
	 * @return TRUE - The Asset Bounds successfully set
	 */

	public boolean addBound (
		final java.lang.String assetID,
		final double lowerBound,
		final double upperBound)
	{
		if (null == assetID || assetID.isEmpty())
		{
			return false;
		}

		try
		{
			_assetBoundsMap.put (
				assetID,
				new org.drip.portfolioconstruction.asset.AssetBounds (
					lowerBound,
					upperBound
				)
			);

			return true;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Retrieve the Lower Bound for the Specified Asset ID
	 * 
	 * @param assetID The Asset ID
	 * 
	 * @return The Lower Bound for the Specified Asset ID
	 * 
	 * @throws java.lang.Exception Thrown if the Bound cannot be extracted
	 */

	public double lowerBound (
		final java.lang.String assetID)
		throws java.lang.Exception
	{
		if (!_assetBoundsMap.containsKey (assetID))
		{
			throw new java.lang.Exception
				("BoundedHoldingsAllocationControl::lowerBound => Invalid Inputs");
		}

		return _assetBoundsMap.get (assetID).lower();
	}

	/**
	 * Retrieve the Upper Bound for the Specified Asset ID
	 * 
	 * @param assetID The Asset ID
	 * 
	 * @return The Upper Bound for the Specified Asset ID
	 * 
	 * @throws java.lang.Exception Thrown if the Bound cannot be extracted
	 */

	public double upperBound (
		final java.lang.String assetID)
		throws java.lang.Exception
	{
		if (!_assetBoundsMap.containsKey (assetID))
		{
			throw new java.lang.Exception
				("BoundedHoldingsAllocationControl::upperBound => Invalid Inputs");
		}

		return _assetBoundsMap.get (assetID).upper();
	}

	/**
	 * Retrieve the Array of the Inequality Constraint Functions
	 * 
	 * @param extraneousVariateCount Number of Extraneous Variates
	 * 
	 * @return The Array of the Inequality Constraint Functions
	 */

	public org.drip.function.rdtor1.AffineBoundMultivariate[] boundingConstraintsArray (
		final int extraneousVariateCount)
	{
		java.util.List<org.drip.function.rdtor1.AffineBoundMultivariate> boundingConstraintsList =
			new java.util.ArrayList<org.drip.function.rdtor1.AffineBoundMultivariate>();

		java.lang.String[] assetIDArray = assetIDArray();

		int assetCount = assetIDArray.length;

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			if (!_assetBoundsMap.containsKey (assetIDArray[assetIndex]))
			{
				continue;
			}

			org.drip.portfolioconstruction.asset.AssetBounds assetBounds = _assetBoundsMap.get (
				assetIDArray[assetIndex]
			);

			double lowerBound = assetBounds.lower();

			double upperBound = assetBounds.upper();

			try
			{
				if (org.drip.numerical.common.NumberUtil.IsValid (lowerBound))
				{
					boundingConstraintsList.add (
						new org.drip.function.rdtor1.AffineBoundMultivariate (
							false,
							assetIndex,
							assetCount + extraneousVariateCount,
							lowerBound
						)
					);
				}

				if (org.drip.numerical.common.NumberUtil.IsValid (upperBound))
				{
					boundingConstraintsList.add (
						new org.drip.function.rdtor1.AffineBoundMultivariate (
							true,
							assetIndex,
							assetCount + extraneousVariateCount,
							upperBound
						)
					);
				}
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		int constraintCount = boundingConstraintsList.size();

		if (0 == constraintCount)
		{
			return null;
		}

		org.drip.function.rdtor1.AffineBoundMultivariate[] boundingConstraintsArray =
			new org.drip.function.rdtor1.AffineBoundMultivariate[constraintCount];

		for (int constraintIndex = 0; constraintIndex < constraintCount; ++constraintIndex)
		{
			boundingConstraintsArray[constraintIndex] = boundingConstraintsList.get (constraintIndex);
		}

		return boundingConstraintsArray;
	}

	/**
	 * Retrieve an Array of Viable Starting Variates From Within the Feasible Region
	 * 
	 * @return An Array of Viable Starting Variates From Within the Feasible Region
	 */

	public double[] feasibleStart()
	{
		boolean returnsConstraintPresent = org.drip.numerical.common.NumberUtil.IsValid (
			equalityConstraintSettings().returnsConstraint()
		);

		java.lang.String[] assetIDArray = assetIDArray();

		int assetCount = assetIDArray.length;
		double[] startingVariateArray = new double[assetCount + (returnsConstraintPresent ? 2 : 1)];

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			startingVariateArray[assetIndex] = _assetBoundsMap.get (assetIDArray[assetIndex]).feasibleStart();
		}

		if (returnsConstraintPresent)
		{
			startingVariateArray[assetCount + 1] = 0.;
		}

		startingVariateArray[assetCount] = 0.;
		return startingVariateArray;
	}

	/**
	 * Retrieve an Array of Viable Weight Constrained Starting Variates From Within the Feasible Region
	 * 
	 * @return An Array of Viable Weight Constrained Starting Variates From Within the Feasible Region
	 */

	public double[] weightConstrainedFeasibleStart()
	{
		boolean returnsConstraintPresent = org.drip.numerical.common.NumberUtil.IsValid (
			equalityConstraintSettings().returnsConstraint()
		);

		java.lang.String[] assetIDArray = assetIDArray();

		double cumulativeWeight = 0.;
		double allocationQuantumFactor = 0.1;
		int assetCount = assetIDArray.length;
		double[] startingVariateArray = new double[assetCount + (returnsConstraintPresent ? 2 : 1)];

		for (int i = 0; i < assetCount; ++i)
		{
			startingVariateArray[i] = lowerPivotStartingWeight (
				assetIDArray[i]
			);

			cumulativeWeight += startingVariateArray[i];
		}

		if (1. < cumulativeWeight)
		{
			return null;
		}

		boolean allocationLoop = true;
		double allocationWeightQuantum = allocationQuantumFactor * (1. - cumulativeWeight) / assetCount;

		while (allocationLoop)
		{
			for (int i = 0; i < assetCount; ++i)
			{
				if (!allocationLoop)
				{
					break;
				}

				org.drip.portfolioconstruction.asset.AssetBounds assetBounds = _assetBoundsMap.get (
					assetIDArray[i]
				);

				double newStartingWeight = startingVariateArray[i] + allocationWeightQuantum;

				if (assetBounds.lower() <= newStartingWeight && assetBounds.upper() >= newStartingWeight)
				{
					startingVariateArray[i] = newStartingWeight;
					cumulativeWeight = cumulativeWeight + allocationWeightQuantum;;
				}

				if (1. <= cumulativeWeight)
				{
					allocationLoop = false;
				}
			}
		}

		if (returnsConstraintPresent)
		{
			startingVariateArray[assetCount + 1] = 0.;
		}

		startingVariateArray[assetCount] = 0.;
		return startingVariateArray;
	}
}
