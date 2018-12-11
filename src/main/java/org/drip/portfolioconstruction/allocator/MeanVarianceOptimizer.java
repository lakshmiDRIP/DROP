
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
 * <i>MeanVarianceOptimizer</i> exposes Portfolio Construction using Mean Variance Optimization Techniques.
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

public abstract class MeanVarianceOptimizer {

	protected abstract org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters
		constrainedPCP (
			final org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters pcpDesign,
			final double dblReturnsConstraint);

	/**
	 * Allocate the Long-Only Maximum Returns Portfolio
	 * 
	 * @param pcp The Portfolio Construction Parameters
	 * @param ausp The Asset Universe Statistical Properties Instance
	 * 
	 * @return The Long-Only Maximum Returns Portfolio
	 */

	public abstract org.drip.portfolioconstruction.allocator.OptimizationOutput longOnlyMaximumReturnsAllocate
		(final org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters pcp,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties ausp);

	/**
	 * Allocate the Global Minimum Variance Portfolio without any Returns Constraints in the Parameters
	 * 
	 * @param pcp The Portfolio Construction Parameters
	 * @param ausp The Asset Universe Statistical Properties Instance
	 * 
	 * @return The Global Minimum Variance Portfolio
	 */

	public abstract org.drip.portfolioconstruction.allocator.OptimizationOutput globalMinimumVarianceAllocate (
		final org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters pcp,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties ausp);

	/**
	 * Allocate the Optimal Portfolio Weights given the Portfolio Construction Parameters
	 * 
	 * @param pcp The Portfolio Construction Parameters
	 * @param ausp The Asset Universe Statistical Properties Instance
	 * 
	 * @return The Optimal Portfolio
	 */

	public abstract org.drip.portfolioconstruction.allocator.OptimizationOutput allocate (
		final org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters pcp,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties ausp);

	/**
	 * Generate the Efficient Frontier given the Portfolio Construction Parameters
	 * 
	 * @param pcp The Portfolio Construction Parameters
	 * @param ausp The Asset Universe Statistical Properties Instance
	 * @param iFrontierSampleUnits The Number of Frontier Sample Units
	 * 
	 * @return The Efficient Frontier
	 */

	public org.drip.portfolioconstruction.mpt.MarkovitzBullet efficientFrontier (
		final org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters pcp,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties ausp,
		final int iFrontierSampleUnits)
	{
		if (0 >= iFrontierSampleUnits) return null;

		org.drip.portfolioconstruction.allocator.OptimizationOutput opGlobalMinimumVariance =
			globalMinimumVarianceAllocate (pcp, ausp);

		if (null == opGlobalMinimumVariance) return null;

		org.drip.portfolioconstruction.allocator.OptimizationOutput opLongOnlyMaximumReturns =
			longOnlyMaximumReturnsAllocate (pcp, ausp);

		if (null == opLongOnlyMaximumReturns) return null;

		double dblReturnsGlobalMinimumVariance =
			opGlobalMinimumVariance.optimalMetrics().excessReturnsMean();

		double dblReturnsLongOnlyMaximumReturns =
			opLongOnlyMaximumReturns.optimalMetrics().excessReturnsMean();

		double dblReturnsConstraintGridWidth = (dblReturnsLongOnlyMaximumReturns -
			dblReturnsGlobalMinimumVariance) / iFrontierSampleUnits;
		double dblReturnsConstraint = dblReturnsGlobalMinimumVariance + dblReturnsConstraintGridWidth;
		org.drip.portfolioconstruction.mpt.MarkovitzBullet mb = null;

		try {
			mb = new org.drip.portfolioconstruction.mpt.MarkovitzBullet (opGlobalMinimumVariance,
				opLongOnlyMaximumReturns);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		while (dblReturnsConstraint <= dblReturnsLongOnlyMaximumReturns) {
			try {
				mb.addOptimalPortfolio (allocate (constrainedPCP (pcp, dblReturnsConstraint), ausp));
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			dblReturnsConstraint += dblReturnsConstraintGridWidth;
		}

		return mb;
	}
}
