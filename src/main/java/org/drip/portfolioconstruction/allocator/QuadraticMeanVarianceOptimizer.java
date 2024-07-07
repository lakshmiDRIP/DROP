
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
 * <i>QuadraticMeanVarianceOptimizer</i> builds an Optimal Portfolio Based on MPT Using the Asset Pool
 * Statistical Properties using a Quadratic Optimization Function and Equality Constraints (if any).
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

public class QuadraticMeanVarianceOptimizer extends
	org.drip.portfolioconstruction.allocator.MeanVarianceOptimizer
{

	protected org.drip.portfolioconstruction.allocator.HoldingsAllocationControl constrainedPCP (
		final org.drip.portfolioconstruction.allocator.HoldingsAllocationControl
			designPortfolioConstructionParameters,
		final double returnsConstraint)
	{
		try {
			return new org.drip.portfolioconstruction.allocator.HoldingsAllocationControl (
				designPortfolioConstructionParameters.assetIDArray(),
				designPortfolioConstructionParameters.customRiskUtilitySettings(),
				new org.drip.portfolioconstruction.allocator.EqualityConstraintSettings (
					designPortfolioConstructionParameters.equalityConstraintSettings().constraintType() |
						org.drip.portfolioconstruction.allocator.EqualityConstraintSettings.RETURNS_CONSTRAINT,
					returnsConstraint
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Empty QuadraticMeanVarianceOptimizer Constructor
	 */

	public QuadraticMeanVarianceOptimizer()
	{
	}

	@Override public org.drip.portfolioconstruction.allocator.HoldingsAllocation
		longOnlyMaximumReturnsAllocate (
			final org.drip.portfolioconstruction.allocator.HoldingsAllocationControl
				portfolioConstructionParameters,
			final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties
				assetUniverseStatisticalProperties)
	{
		if (null == portfolioConstructionParameters || null == assetUniverseStatisticalProperties)
		{
			return null;
		}

		java.lang.String[] assetIDArray = portfolioConstructionParameters.assetIDArray();

		int assetCount = assetIDArray.length;
		org.drip.portfolioconstruction.asset.AssetComponent[] assetComponentArray = new
			org.drip.portfolioconstruction.asset.AssetComponent[assetCount];

		double[] expectedAssetReturnsArray = assetUniverseStatisticalProperties.expectedReturns (
			assetIDArray
		);

		if (null == expectedAssetReturnsArray || assetCount != expectedAssetReturnsArray.length)
		{
			return null;
		}

		double maximumReturns = expectedAssetReturnsArray[0];
		java.lang.String maximumReturnsAssetID = assetIDArray[0];

		for (int i = 1; i < assetCount; ++i)
		{
			if (expectedAssetReturnsArray[i] > maximumReturns)
			{
				maximumReturnsAssetID = assetIDArray[i];
				maximumReturns = expectedAssetReturnsArray[i];
			}
		}

		try
		{
			for (int i = 0; i < assetCount; ++i)
			{
				assetComponentArray[i] = new org.drip.portfolioconstruction.asset.AssetComponent (
					assetIDArray[i],
					assetIDArray[i].equalsIgnoreCase (maximumReturnsAssetID) ? 1. : 0.
				);
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		return org.drip.portfolioconstruction.allocator.HoldingsAllocation.Create (
			assetComponentArray,
			assetUniverseStatisticalProperties
		);
	}

	@Override public org.drip.portfolioconstruction.allocator.HoldingsAllocation
		globalMinimumVarianceAllocate (
			final org.drip.portfolioconstruction.allocator.HoldingsAllocationControl
				portfolioConstructionParameters,
			final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties
				assetUniverseStatisticalProperties)
	{
		if (null == portfolioConstructionParameters || null == assetUniverseStatisticalProperties)
		{
			return null;
		}

		java.lang.String[] assetIDArray = portfolioConstructionParameters.assetIDArray();

		int assetCount = assetIDArray.length;
		org.drip.function.rdtor1.LagrangianMultivariate lagrangianMultivariate = null;
		org.drip.portfolioconstruction.asset.AssetComponent[] assetComponentArray = new
			org.drip.portfolioconstruction.asset.AssetComponent[assetCount];

		try
		{
			lagrangianMultivariate = new org.drip.function.rdtor1.LagrangianMultivariate (
				portfolioConstructionParameters.customRiskUtilitySettings().riskObjectiveUtility (
					assetIDArray,
					assetUniverseStatisticalProperties
				),
				new org.drip.function.definition.RdToR1[]
				{
					portfolioConstructionParameters.fullyInvestedConstraint()
				}
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		int lagrangianDimension = lagrangianMultivariate.dimension();

		double[] rhsArray = new double[lagrangianDimension];
		double[] variateArray = new double[lagrangianDimension];

		double riskToleranceFactor =
			portfolioConstructionParameters.customRiskUtilitySettings().riskTolerance();

		double[] equalityConstraintRHSArray = portfolioConstructionParameters.equalityConstraintRHS();

		for (int lagrangianIndex = 0; lagrangianIndex < lagrangianDimension; ++lagrangianIndex)
		{
			variateArray[lagrangianIndex] = 0.;

			if (lagrangianIndex < assetCount)
			{
				if (0. != riskToleranceFactor)
				{
					org.drip.portfolioconstruction.params.AssetStatisticalProperties
						assetStatisticalProperties =
							assetUniverseStatisticalProperties.assetStatisticalProperties (
								assetIDArray[lagrangianIndex]
							);

					if (null == assetStatisticalProperties)
					{
						return null;
					}

					rhsArray[lagrangianIndex] = assetStatisticalProperties.expectedReturn() *
						riskToleranceFactor;
				}
				else
				{
					rhsArray[lagrangianIndex] = 0.;
				}
			}
			else
			{
				rhsArray[lagrangianIndex] = equalityConstraintRHSArray[lagrangianIndex - assetCount];
			}
		}

		org.drip.numerical.linearalgebra.LinearizationOutput linearizationOutput =
			org.drip.numerical.linearsolver.LinearSystem.SolveUsingMatrixInversion (
				lagrangianMultivariate.hessian (variateArray),
				rhsArray
			);

		if (null == linearizationOutput)
		{
			return null;
		}

		double[] assetHoldingsArray = linearizationOutput.getTransformedRHS();

		if (null == assetHoldingsArray || assetHoldingsArray.length != lagrangianDimension)
		{
			return null;
		}

		try
		{
			for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
			{
				assetComponentArray[assetIndex] = new org.drip.portfolioconstruction.asset.AssetComponent (
					assetIDArray[assetIndex],
					assetHoldingsArray[assetIndex]
				);
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		return org.drip.portfolioconstruction.allocator.HoldingsAllocation.Create (
			assetComponentArray,
			assetUniverseStatisticalProperties
		);
	}

	@Override public org.drip.portfolioconstruction.allocator.HoldingsAllocation allocate (
		final org.drip.portfolioconstruction.allocator.HoldingsAllocationControl
			portfolioConstructionParameters,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties
			assetUniverseStatisticalProperties)
	{
		if (null == portfolioConstructionParameters || null == assetUniverseStatisticalProperties)
		{
			return null;
		}

		java.lang.String[] assetIDArray = portfolioConstructionParameters.assetIDArray();

		int assetCount = assetIDArray.length;
		org.drip.function.rdtor1.LagrangianMultivariate lagrangianMultivariate = null;
		org.drip.portfolioconstruction.asset.AssetComponent[] assetComponentArray = new
			org.drip.portfolioconstruction.asset.AssetComponent[assetCount];

		try
		{
			lagrangianMultivariate = new org.drip.function.rdtor1.LagrangianMultivariate (
				portfolioConstructionParameters.customRiskUtilitySettings().riskObjectiveUtility (
					assetIDArray,
					assetUniverseStatisticalProperties
				),
				portfolioConstructionParameters.equalityConstraintArray (
					assetUniverseStatisticalProperties
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		int lagrangianDimension = lagrangianMultivariate.dimension();

		double[] variateArray = new double[lagrangianDimension];

		org.drip.numerical.linearalgebra.LinearizationOutput linearizationOutput =
			org.drip.numerical.linearsolver.LinearSystem.SolveUsingMatrixInversion (
				lagrangianMultivariate.hessian (variateArray),
				lagrangianMultivariate.jacobian (variateArray)
			);

		if (null == linearizationOutput)
		{
			return null;
		}

		double[] assetHoldingsArray = linearizationOutput.getTransformedRHS();

		if (null == assetHoldingsArray || assetHoldingsArray.length != lagrangianDimension)
		{
			return null;
		}

		try
		{
			for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
			{
				assetComponentArray[assetIndex] = new org.drip.portfolioconstruction.asset.AssetComponent (
					assetIDArray[assetIndex],
					-1. * assetHoldingsArray[assetIndex]
				);
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		return org.drip.portfolioconstruction.allocator.HoldingsAllocation.Create (
			assetComponentArray,
			assetUniverseStatisticalProperties
		);
	}
}
