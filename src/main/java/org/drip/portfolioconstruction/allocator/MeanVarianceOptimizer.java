
package org.drip.portfolioconstruction.allocator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>MeanVarianceOptimizer</i> exposes Portfolio Construction using Mean Variance Optimization Techniques.
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

public abstract class MeanVarianceOptimizer
{

	protected abstract org.drip.portfolioconstruction.allocator.HoldingsAllocationControl
		constrainedPCP (
			final org.drip.portfolioconstruction.allocator.HoldingsAllocationControl
				designPortfolioConstructionParameters,
			final double returnsConstraint);

	/**
	 * Allocate the Long-Only Maximum Returns Portfolio
	 * 
	 * @param portfolioConstructionParameters The Portfolio Construction Parameters
	 * @param assetUniverseStatisticalProperties The Asset Universe Statistical Properties Instance
	 * 
	 * @return The Long-Only Maximum Returns Portfolio
	 */

	public abstract org.drip.portfolioconstruction.allocator.HoldingsAllocation
		longOnlyMaximumReturnsAllocate (
			final org.drip.portfolioconstruction.allocator.HoldingsAllocationControl
				portfolioConstructionParameters,
			final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties
				assetUniverseStatisticalProperties);

	/**
	 * Allocate the Global Minimum Variance Portfolio without any Returns Constraints in the Parameters
	 * 
	 * @param portfolioConstructionParameters The Portfolio Construction Parameters
	 * @param assetUniverseStatisticalProperties The Asset Universe Statistical Properties Instance
	 * 
	 * @return The Global Minimum Variance Portfolio
	 */

	public abstract org.drip.portfolioconstruction.allocator.HoldingsAllocation
		globalMinimumVarianceAllocate (
			final org.drip.portfolioconstruction.allocator.HoldingsAllocationControl
				portfolioConstructionParameters,
			final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties
				assetUniverseStatisticalProperties);

	/**
	 * Allocate the Optimal Portfolio Weights given the Portfolio Construction Parameters
	 * 
	 * @param portfolioConstructionParameters The Portfolio Construction Parameters
	 * @param assetUniverseStatisticalProperties The Asset Universe Statistical Properties Instance
	 * 
	 * @return The Optimal Portfolio
	 */

	public abstract org.drip.portfolioconstruction.allocator.HoldingsAllocation allocate (
		final org.drip.portfolioconstruction.allocator.HoldingsAllocationControl
			portfolioConstructionParameters,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties
			assetUniverseStatisticalProperties);

	/**
	 * Generate the Efficient Frontier given the Portfolio Construction Parameters
	 * 
	 * @param portfolioConstructionParameters The Portfolio Construction Parameters
	 * @param assetUniverseStatisticalProperties The Asset Universe Statistical Properties Instance
	 * @param frontierSampleUnits The Number of Frontier Sample Units
	 * 
	 * @return The Efficient Frontier
	 */

	public org.drip.portfolioconstruction.mpt.MarkovitzBullet efficientFrontier (
		final org.drip.portfolioconstruction.allocator.HoldingsAllocationControl
			portfolioConstructionParameters,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties
			assetUniverseStatisticalProperties,
		final int frontierSampleUnits)
	{
		if (0 >= frontierSampleUnits)
		{
			return null;
		}

		org.drip.portfolioconstruction.allocator.HoldingsAllocation globalMinimumVarianceOptimizationOutput =
			globalMinimumVarianceAllocate (
				portfolioConstructionParameters,
				assetUniverseStatisticalProperties
			);

		if (null == globalMinimumVarianceOptimizationOutput)
		{
			return null;
		}

		org.drip.portfolioconstruction.allocator.HoldingsAllocation longOnlyMaximumReturnsOptimizationOutput
			= longOnlyMaximumReturnsAllocate (
				portfolioConstructionParameters,
				assetUniverseStatisticalProperties
			);

		if (null == longOnlyMaximumReturnsOptimizationOutput)
		{
			return null;
		}

		double globalMinimumVarianceReturns =
			globalMinimumVarianceOptimizationOutput.optimalMetrics().excessReturnsMean();

		double longOnlyMaximumReturns =
			longOnlyMaximumReturnsOptimizationOutput.optimalMetrics().excessReturnsMean();

		double returnsConstraintGridWidth = (longOnlyMaximumReturns - globalMinimumVarianceReturns) /
			frontierSampleUnits;
		double returnsConstraint = globalMinimumVarianceReturns + returnsConstraintGridWidth;
		org.drip.portfolioconstruction.mpt.MarkovitzBullet markovitzBullet = null;

		try
		{
			markovitzBullet = new org.drip.portfolioconstruction.mpt.MarkovitzBullet (
				globalMinimumVarianceOptimizationOutput,
				longOnlyMaximumReturnsOptimizationOutput
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		while (returnsConstraint <= longOnlyMaximumReturns)
		{
			try
			{
				markovitzBullet.addOptimalPortfolio (
					allocate (
						constrainedPCP (
							portfolioConstructionParameters,
							returnsConstraint
						),
						assetUniverseStatisticalProperties
					)
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}

			returnsConstraint += returnsConstraintGridWidth;
		}

		return markovitzBullet;
	}
}
