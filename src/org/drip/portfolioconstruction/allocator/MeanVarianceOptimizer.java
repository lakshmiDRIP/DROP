
package org.drip.portfolioconstruction.allocator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * MeanVarianceOptimizer exposes Portfolio Construction using Mean Variance Optimization Techniques.
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
