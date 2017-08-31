
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
 * ConstrainedMeanVarianceOptimizer builds an Optimal Portfolio Based on MPT Using the Asset Pool Statistical
 *  Properties with the Specified Lower/Upper Bounds on the Component Assets.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ConstrainedMeanVarianceOptimizer extends
	org.drip.portfolioconstruction.allocator.MeanVarianceOptimizer {
	private org.drip.function.rdtor1descent.LineStepEvolutionControl _lsec = null;
	private org.drip.function.rdtor1solver.InteriorPointBarrierControl _ipbc = null;

	protected org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters constrainedPCP (
		final org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters pcpDesign,
		final double dblReturnsConstraint)
	{
		java.lang.String[] astrAssetID = pcpDesign.assets();

		org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters bpcp =
			(org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters) pcpDesign;

		try {
			org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters bpcpMB = new
				org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters (astrAssetID,
					pcpDesign.optimizerSettings(),  new
						org.drip.portfolioconstruction.allocator.PortfolioEqualityConstraintSettings
							(pcpDesign.constraintSettings().constraintType() |
								org.drip.portfolioconstruction.allocator.PortfolioEqualityConstraintSettings.RETURNS_CONSTRAINT,
								dblReturnsConstraint));

			for (int i = 0; i < astrAssetID.length; ++i) {
				if (!bpcpMB.addBound (astrAssetID[i], bpcp.lowerBound (astrAssetID[i]), bpcp.upperBound
					(astrAssetID[i])))
					return null;
			}

			return bpcpMB;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ConstrainedMeanVarianceOptimizer Constructor
	 * 
	 * @param ipbc Interior Fixed Point Barrier Control Parameters
	 * @param lsec Line Step Evolution Control Parameters
	 */

	public ConstrainedMeanVarianceOptimizer (
		final org.drip.function.rdtor1solver.InteriorPointBarrierControl ipbc,
		final org.drip.function.rdtor1descent.LineStepEvolutionControl lsec)
	{
		if (null == (_ipbc = ipbc))
			_ipbc = org.drip.function.rdtor1solver.InteriorPointBarrierControl.Standard();

		_lsec = lsec;
	}

	@Override public org.drip.portfolioconstruction.allocator.OptimizationOutput longOnlyMaximumReturnsAllocate
		(final org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters pcp,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties ausp)
	{
		if (null == pcp || !(pcp instanceof
			org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters) || null == ausp)
			return null;

		java.lang.String[] astrAssetID = pcp.assets();

		int iPortfolioAssetIndex = 0;
		double dblCumulativeWeight = 0.;
		int iNumAsset = astrAssetID.length;
		org.drip.portfolioconstruction.asset.AssetComponent[] aAC = new
			org.drip.portfolioconstruction.asset.AssetComponent[iNumAsset];
		org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters bpcp =
			(org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters) pcp;

		double[] adblExpectedAssetReturns = ausp.expectedReturns (astrAssetID);

		if (null == adblExpectedAssetReturns || iNumAsset != adblExpectedAssetReturns.length) return null;

		java.util.TreeMap<java.lang.Double, java.lang.String> mapAssetReturns = new
			java.util.TreeMap<java.lang.Double, java.lang.String>();

		for (int i = 0; i < iNumAsset; ++i)
			mapAssetReturns.put (adblExpectedAssetReturns[i], astrAssetID[i]);

		java.util.Set<java.lang.Double> setAssetReturns = mapAssetReturns.descendingKeySet();

		for (double dblAssetReturns : setAssetReturns) {
			double dblAssetWeight = 0.;

			java.lang.String strAssetID = mapAssetReturns.get (dblAssetReturns);

			try {
				if (1. > dblCumulativeWeight) {
					double dblMaxAssetWeight = bpcp.upperBound (strAssetID);

					double dblMaxAllowedAssetWeight = 1. - dblCumulativeWeight;

					if (!org.drip.quant.common.NumberUtil.IsValid (dblMaxAssetWeight))
						dblMaxAssetWeight = dblMaxAllowedAssetWeight;

					dblAssetWeight = dblMaxAssetWeight < dblMaxAllowedAssetWeight ? dblMaxAssetWeight :
						dblMaxAllowedAssetWeight;
					dblCumulativeWeight += dblAssetWeight;
				}

				aAC[iPortfolioAssetIndex++] = new org.drip.portfolioconstruction.asset.AssetComponent
					(strAssetID, dblAssetWeight);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return org.drip.portfolioconstruction.allocator.OptimizationOutput.Create (aAC, ausp);
	}

	@Override public org.drip.portfolioconstruction.allocator.OptimizationOutput globalMinimumVarianceAllocate
		(final org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters pcp,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties ausp)
	{
		if (null == pcp || !(pcp instanceof
			org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters) || null == ausp)
			return null;

		java.lang.String[] astrAssetID = pcp.assets();

		double[][] aadblCovariance = ausp.covariance (astrAssetID);

		if (null == aadblCovariance) return null;

		int iNumAsset = astrAssetID.length;
		org.drip.portfolioconstruction.asset.AssetComponent[] aAC = new
			org.drip.portfolioconstruction.asset.AssetComponent[iNumAsset];
		org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters bpcp =
			(org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters) pcp;

		try {
			org.drip.function.rdtor1.LagrangianMultivariate lm = new
				org.drip.function.rdtor1.LagrangianMultivariate (pcp.optimizerSettings().riskObjectiveUtility
					(astrAssetID, ausp), new org.drip.function.definition.RdToR1[]
						{bpcp.fullyInvestedConstraint()});

			org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vcmt = new
				org.drip.function.rdtor1solver.BarrierFixedPointFinder (lm, bpcp.boundingConstraints
					(lm.constraintFunctionDimension()), _ipbc, _lsec).solve
						(bpcp.weightConstrainedFeasibleStart());

			if (null == vcmt) return null;

			double[] adblOptimalWeight = vcmt.variates();

			for (int i = 0; i < iNumAsset; ++i)
				aAC[i] = new org.drip.portfolioconstruction.asset.AssetComponent (astrAssetID[i],
					adblOptimalWeight[i]);

			return org.drip.portfolioconstruction.allocator.OptimizationOutput.Create (aAC, ausp);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.portfolioconstruction.allocator.OptimizationOutput allocate (
		final org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters pcp,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties ausp)
	{
		if (null == pcp || !(pcp instanceof
			org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters) || null == ausp)
			return null;

		java.lang.String[] astrAssetID = pcp.assets();

		double[][] aadblCovariance = ausp.covariance (astrAssetID);

		if (null == aadblCovariance) return null;

		int iNumAsset = astrAssetID.length;
		org.drip.portfolioconstruction.asset.AssetComponent[] aAC = new
			org.drip.portfolioconstruction.asset.AssetComponent[iNumAsset];
		org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters bpcp =
			(org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters) pcp;

		try {
			org.drip.function.rdtor1.LagrangianMultivariate lm = new
				org.drip.function.rdtor1.LagrangianMultivariate (pcp.optimizerSettings().riskObjectiveUtility
					(astrAssetID, ausp), bpcp.equalityConstraintRdToR1 (ausp));

			org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vcmt = new
				org.drip.function.rdtor1solver.BarrierFixedPointFinder (lm, bpcp.boundingConstraints
					(lm.constraintFunctionDimension()), _ipbc, _lsec).solve
						(bpcp.weightConstrainedFeasibleStart());

			if (null == vcmt) return null;

			double[] adblOptimalWeight = vcmt.variates();

			for (int i = 0; i < iNumAsset; ++i)
				aAC[i] = new org.drip.portfolioconstruction.asset.AssetComponent (astrAssetID[i],
					adblOptimalWeight[i]);

			return org.drip.portfolioconstruction.allocator.OptimizationOutput.Create (aAC, ausp);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
