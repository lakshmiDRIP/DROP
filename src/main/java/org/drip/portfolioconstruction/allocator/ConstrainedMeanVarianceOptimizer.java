
package org.drip.portfolioconstruction.allocator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>ConstrainedMeanVarianceOptimizer</i> builds an Optimal Portfolio Based on MPT Using the Asset Pool
 * Statistical Properties with the Specified Lower/Upper Bounds on the Component Assets.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator">Allocator</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ConstrainedMeanVarianceOptimizer extends
	org.drip.portfolioconstruction.allocator.MeanVarianceOptimizer
{
	private org.drip.function.rdtor1descent.LineStepEvolutionControl _lineStepEvolutionControl = null;
	private org.drip.function.rdtor1solver.InteriorPointBarrierControl _interiorPointBarrierControl = null;

	protected org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters constrainedPCP (
		final org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters
			designPortfolioConstructionParameters,
		final double returnsConstraint)
	{
		java.lang.String[] assetIDArray = designPortfolioConstructionParameters.assetIDArray();

		int assetCount = assetIDArray.length;
		org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters
			boundedPortfolioConstructionParametersIn =
				(org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters)
					designPortfolioConstructionParameters;

		try
		{
			org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters
				boundedPortfolioConstructionParametersOut =
					new org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters (
						assetIDArray,
						designPortfolioConstructionParameters.customRiskUtilitySettings(),
						new org.drip.portfolioconstruction.allocator.EqualityConstraintSettings (
							designPortfolioConstructionParameters.equalityConstraintSettings().constraintType() |
								org.drip.portfolioconstruction.allocator.EqualityConstraintSettings.RETURNS_CONSTRAINT,
							returnsConstraint
						)
					);

			for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
			{
				if (!boundedPortfolioConstructionParametersOut.addBound (
					assetIDArray[assetIndex],
					boundedPortfolioConstructionParametersIn.lowerBound (assetIDArray[assetIndex]),
					boundedPortfolioConstructionParametersIn.upperBound (assetIDArray[assetIndex])
				))
				{
					return null;
				}
			}

			return boundedPortfolioConstructionParametersOut;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ConstrainedMeanVarianceOptimizer Constructor
	 * 
	 * @param interiorPointBarrierControl Interior Fixed Point Barrier Control Parameters
	 * @param lineStepEvolutionControl Line Step Evolution Control Parameters
	 */

	public ConstrainedMeanVarianceOptimizer (
		final org.drip.function.rdtor1solver.InteriorPointBarrierControl interiorPointBarrierControl,
		final org.drip.function.rdtor1descent.LineStepEvolutionControl lineStepEvolutionControl)
	{
		if (null == (_interiorPointBarrierControl = interiorPointBarrierControl))
		{
			_interiorPointBarrierControl =
				org.drip.function.rdtor1solver.InteriorPointBarrierControl.Standard();
		}

		_lineStepEvolutionControl = lineStepEvolutionControl;
	}

	@Override public org.drip.portfolioconstruction.allocator.OptimizationOutput
		longOnlyMaximumReturnsAllocate (
			final org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters
				portfolioConstructionParameters,
			final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties
				assetUniverseStatisticalProperties)
	{
		if (null == portfolioConstructionParameters ||
			!(portfolioConstructionParameters instanceof
				org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters) ||
			null == assetUniverseStatisticalProperties)
		{
			return null;
		}

		java.lang.String[] assetIDArray = portfolioConstructionParameters.assetIDArray();

		int portfolioAssetIndex = 0;
		double cumulativeWeight = 0.;
		int assetCount = assetIDArray.length;
		org.drip.portfolioconstruction.asset.AssetComponent[] assetComponentArray = new
			org.drip.portfolioconstruction.asset.AssetComponent[assetCount];
		org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters
			boundedPortfolioConstructionParameters =
				(org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters)
					portfolioConstructionParameters;

		double[] expectedAssetReturnsArray = assetUniverseStatisticalProperties.expectedReturns (
			assetIDArray
		);

		if (null == expectedAssetReturnsArray || assetCount != expectedAssetReturnsArray.length)
		{
			return null;
		}

		java.util.TreeMap<java.lang.Double, java.lang.String> assetReturnsMap =
			new java.util.TreeMap<java.lang.Double, java.lang.String>();

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			assetReturnsMap.put (
				expectedAssetReturnsArray[assetIndex],
				assetIDArray[assetIndex]
			);
		}

		java.util.Set<java.lang.Double> descendingAssetReturnsSet = assetReturnsMap.descendingKeySet();

		for (double assetReturns : descendingAssetReturnsSet)
		{
			double assetWeight = 0.;

			java.lang.String assetID = assetReturnsMap.get (assetReturns);

			try
			{
				if (1. > cumulativeWeight)
				{
					double assetWeightUpperBound = boundedPortfolioConstructionParameters.upperBound (
						assetID
					);

					double maximumAllowedAssetWeight = 1. - cumulativeWeight;

					if (!org.drip.numerical.common.NumberUtil.IsValid (assetWeightUpperBound))
					{
						assetWeightUpperBound = maximumAllowedAssetWeight;
					}

					assetWeight = assetWeightUpperBound < maximumAllowedAssetWeight ? assetWeightUpperBound :
						maximumAllowedAssetWeight;
					cumulativeWeight += assetWeight;
				}

				assetComponentArray[portfolioAssetIndex++] =
					new org.drip.portfolioconstruction.asset.AssetComponent (
						assetID,
						assetWeight
					);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return org.drip.portfolioconstruction.allocator.OptimizationOutput.Create (
			assetComponentArray,
			assetUniverseStatisticalProperties
		);
	}

	@Override public org.drip.portfolioconstruction.allocator.OptimizationOutput
		globalMinimumVarianceAllocate (
			final org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters
				portfolioConstructionParameters,
			final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties
				assetUniverseStatisticalProperties)
	{
		if (null == portfolioConstructionParameters ||
			!(portfolioConstructionParameters instanceof
				org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters) ||
			null == assetUniverseStatisticalProperties)
		{
			return null;
		}

		java.lang.String[] assetIDArray = portfolioConstructionParameters.assetIDArray();

		double[][] assetCovarianceMatrix = assetUniverseStatisticalProperties.covariance (assetIDArray);

		if (null == assetCovarianceMatrix)
		{
			return null;
		}

		int assetCount = assetIDArray.length;
		org.drip.portfolioconstruction.asset.AssetComponent[] assetComponentArray = new
			org.drip.portfolioconstruction.asset.AssetComponent[assetCount];
		org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters
			boundedPortfolioConstructionParameters =
				(org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters)
					portfolioConstructionParameters;

		try
		{
			org.drip.function.rdtor1.LagrangianMultivariate lagrangianMultivariate =
				new org.drip.function.rdtor1.LagrangianMultivariate (
					portfolioConstructionParameters.customRiskUtilitySettings().riskObjectiveUtility (
						assetIDArray,
						assetUniverseStatisticalProperties
					),
					new org.drip.function.definition.RdToR1[]
					{
						boundedPortfolioConstructionParameters.fullyInvestedConstraint()
					}
				);

			org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier
				variateInequalityConstraintMultiplier =
					new org.drip.function.rdtor1solver.BarrierFixedPointFinder (
						lagrangianMultivariate,
						boundedPortfolioConstructionParameters.boundingConstraintsArray (
							lagrangianMultivariate.constraintFunctionDimension()
						),
						_interiorPointBarrierControl,
						_lineStepEvolutionControl
					).solve (
						boundedPortfolioConstructionParameters.weightConstrainedFeasibleStart()
					);

			if (null == variateInequalityConstraintMultiplier)
			{
				return null;
			}

			double[] optimalWeightArray = variateInequalityConstraintMultiplier.variateArray();

			for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
			{
				assetComponentArray[assetIndex] = new org.drip.portfolioconstruction.asset.AssetComponent (
					assetIDArray[assetIndex],
					optimalWeightArray[assetIndex]
				);
			}

			return org.drip.portfolioconstruction.allocator.OptimizationOutput.Create (
				assetComponentArray,
				assetUniverseStatisticalProperties
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.portfolioconstruction.allocator.OptimizationOutput allocate (
		final org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters
			portfolioConstructionParameters,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties
			assetUniverseStatisticalProperties)
	{
		if (null == portfolioConstructionParameters ||
			!(portfolioConstructionParameters instanceof
				org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters) ||
			null == assetUniverseStatisticalProperties)
		{
			return null;
		}

		java.lang.String[] assetIDArray = portfolioConstructionParameters.assetIDArray();

		double[][] aadblCovariance = assetUniverseStatisticalProperties.covariance (assetIDArray);

		if (null == aadblCovariance)
		{
			return null;
		}

		int assetCount = assetIDArray.length;
		org.drip.portfolioconstruction.asset.AssetComponent[] assetComponentArray = new
			org.drip.portfolioconstruction.asset.AssetComponent[assetCount];
		org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters
			boundedPortfolioConstructionParameters =
				(org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters)
					portfolioConstructionParameters;

		try
		{
			org.drip.function.rdtor1.LagrangianMultivariate lagrangianMultivariate =
				new org.drip.function.rdtor1.LagrangianMultivariate (
					portfolioConstructionParameters.customRiskUtilitySettings().riskObjectiveUtility (
						assetIDArray,
						assetUniverseStatisticalProperties
					),
					boundedPortfolioConstructionParameters.equalityConstraintArray (
						assetUniverseStatisticalProperties
					)
				);

			org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier
				variateInequalityConstraintMultiplier =
					new org.drip.function.rdtor1solver.BarrierFixedPointFinder (
						lagrangianMultivariate,
						boundedPortfolioConstructionParameters.boundingConstraintsArray (
							lagrangianMultivariate.constraintFunctionDimension()
						),
						_interiorPointBarrierControl,
						_lineStepEvolutionControl
					).solve (
						boundedPortfolioConstructionParameters.weightConstrainedFeasibleStart()
					);

			if (null == variateInequalityConstraintMultiplier)
			{
				return null;
			}

			double[] optimalWeightArray = variateInequalityConstraintMultiplier.variateArray();

			for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
			{
				assetComponentArray[assetIndex] = new org.drip.portfolioconstruction.asset.AssetComponent (
					assetIDArray[assetIndex],
					optimalWeightArray[assetIndex]
				);
			}

			return org.drip.portfolioconstruction.allocator.OptimizationOutput.Create (
				assetComponentArray,
				assetUniverseStatisticalProperties
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
