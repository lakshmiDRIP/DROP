
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
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator">Allocator</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/AssetAllocation">Asset Allocation Library</a></li>
 *  </ul>
 * <br><br>
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
