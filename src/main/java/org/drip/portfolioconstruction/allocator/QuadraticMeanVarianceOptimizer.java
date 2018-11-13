
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
 * <i>QuadraticMeanVarianceOptimizer</i> builds an Optimal Portfolio Based on MPT Using the Asset Pool
 * Statistical Properties using a Quadratic Optimization Function and Equality Constraints (if any).
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

public class QuadraticMeanVarianceOptimizer extends
	org.drip.portfolioconstruction.allocator.MeanVarianceOptimizer {

	protected org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters constrainedPCP (
		final org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters pcpDesign,
		final double dblReturnsConstraint)
	{
		try {
			return new org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters
				(pcpDesign.assets(), pcpDesign.optimizerSettings(), new
					org.drip.portfolioconstruction.allocator.PortfolioEqualityConstraintSettings
						(pcpDesign.constraintSettings().constraintType() |
							org.drip.portfolioconstruction.allocator.PortfolioEqualityConstraintSettings.RETURNS_CONSTRAINT,
							dblReturnsConstraint));
		} catch (java.lang.Exception e) {
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

	@Override public org.drip.portfolioconstruction.allocator.OptimizationOutput longOnlyMaximumReturnsAllocate
		(final org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters pcp,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties ausp)
	{
		if (null == pcp || null == ausp) return null;

		java.lang.String[] astrAssetID = pcp.assets();

		int iNumAsset = astrAssetID.length;
		org.drip.portfolioconstruction.asset.AssetComponent[] aAC = new
			org.drip.portfolioconstruction.asset.AssetComponent[iNumAsset];

		double[] adblExpectedAssetReturns = ausp.expectedReturns (astrAssetID);

		if (null == adblExpectedAssetReturns || iNumAsset != adblExpectedAssetReturns.length) return null;

		double dblMaximumReturns = adblExpectedAssetReturns[0];
		java.lang.String strMaximumReturnsAsset = astrAssetID[0];

		for (int i = 1; i < iNumAsset; ++i) {
			if (adblExpectedAssetReturns[i] > dblMaximumReturns) {
				strMaximumReturnsAsset = astrAssetID[i];
				dblMaximumReturns = adblExpectedAssetReturns[i];
			}
		}

		try {
			for (int i = 0; i < iNumAsset; ++i)
				aAC[i] = new org.drip.portfolioconstruction.asset.AssetComponent (astrAssetID[i],
					astrAssetID[i].equalsIgnoreCase (strMaximumReturnsAsset) ? 1. : 0.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return org.drip.portfolioconstruction.allocator.OptimizationOutput.Create (aAC, ausp);
	}

	@Override public org.drip.portfolioconstruction.allocator.OptimizationOutput globalMinimumVarianceAllocate
		(final org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters pcp,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties ausp)
	{
		if (null == pcp || null == ausp) return null;

		java.lang.String[] astrAssetID = pcp.assets();

		int iNumAsset = astrAssetID.length;
		org.drip.function.rdtor1.LagrangianMultivariate lm = null;
		org.drip.portfolioconstruction.asset.AssetComponent[] aAC = new
			org.drip.portfolioconstruction.asset.AssetComponent[iNumAsset];

		try {
			lm = new org.drip.function.rdtor1.LagrangianMultivariate
				(pcp.optimizerSettings().riskObjectiveUtility (astrAssetID, ausp), new
					org.drip.function.definition.RdToR1[] {pcp.fullyInvestedConstraint()});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		int iLagrangianDimension = lm.dimension();

		double[] adblRHS = new double[iLagrangianDimension];
		double[] adblVariate = new double[iLagrangianDimension];

		double dblRiskToleranceFactor = pcp.optimizerSettings().riskTolerance();

		double[] adblEqualityConstraintValue = pcp.equalityConstraintValue (ausp);

		for (int i = 0; i < iLagrangianDimension; ++i) {
			adblVariate[i] = 0.;

			if (i < iNumAsset) {
				if (0. != dblRiskToleranceFactor) {
					org.drip.portfolioconstruction.params.AssetStatisticalProperties asp = ausp.asp
						(astrAssetID[i]);

					if (null == asp) return null;

					adblRHS[i] = asp.expectedReturn() * dblRiskToleranceFactor;
				} else
					adblRHS[i] = 0.;
			} else
				adblRHS[i] = adblEqualityConstraintValue[i - iNumAsset];
		}

		org.drip.quant.linearalgebra.LinearizationOutput lo =
			org.drip.quant.linearalgebra.LinearSystemSolver.SolveUsingMatrixInversion (lm.hessian
				(adblVariate), adblRHS);

		if (null == lo) return null;

		double[] adblAmount = lo.getTransformedRHS();

		if (null == adblAmount || adblAmount.length != iLagrangianDimension) return null;

		try {
			for (int i = 0; i < iNumAsset; ++i)
				aAC[i] = new org.drip.portfolioconstruction.asset.AssetComponent (astrAssetID[i],
					adblAmount[i]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return org.drip.portfolioconstruction.allocator.OptimizationOutput.Create (aAC, ausp);
	}

	@Override public org.drip.portfolioconstruction.allocator.OptimizationOutput allocate (
		final org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters pcp,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties ausp)
	{
		if (null == pcp || null == ausp) return null;

		java.lang.String[] astrAssetID = pcp.assets();

		int iNumAsset = astrAssetID.length;
		org.drip.function.rdtor1.LagrangianMultivariate lm = null;
		org.drip.portfolioconstruction.asset.AssetComponent[] aAC = new
			org.drip.portfolioconstruction.asset.AssetComponent[iNumAsset];

		try {
			lm = new org.drip.function.rdtor1.LagrangianMultivariate
				(pcp.optimizerSettings().riskObjectiveUtility (astrAssetID, ausp),
					pcp.equalityConstraintRdToR1 (ausp));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		int iLagrangianDimension = lm.dimension();

		double[] adblVariate = new double[iLagrangianDimension];

		org.drip.quant.linearalgebra.LinearizationOutput lo =
			org.drip.quant.linearalgebra.LinearSystemSolver.SolveUsingMatrixInversion (lm.hessian
				(adblVariate), lm.jacobian (adblVariate));

		if (null == lo) return null;

		double[] adblAmount = lo.getTransformedRHS();

		if (null == adblAmount || adblAmount.length != iLagrangianDimension) return null;

		try {
			for (int i = 0; i < iNumAsset; ++i)
				aAC[i] = new org.drip.portfolioconstruction.asset.AssetComponent (astrAssetID[i], -1. *
					adblAmount[i]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return org.drip.portfolioconstruction.allocator.OptimizationOutput.Create (aAC, ausp);
	}
}
