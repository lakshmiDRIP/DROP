
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
 * <i>ForwardReverseHoldingsAllocation</i> holds the Metrics that result from a Forward/Reverse Optimization
 * Run.
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

public class ForwardReverseHoldingsAllocation extends
	org.drip.portfolioconstruction.allocator.HoldingsAllocation
{
	private double _riskAversion = java.lang.Double.NaN;
	private double[] _expectedAssetExcessReturnsArray = null;
	private double[][] _assetExcessReturnsCovarianceMatrix = null;

	/**
	 * Construct an Instance of ForwardReverseHoldingsAllocation from a Standard Reverse Optimize Operation
	 * 
	 * @param equilibriumPortfolio The Equilibrium Portfolio
	 * @param assetExcessReturnsCovarianceMatrix Pair-wse Asset Excess Returns Co-variance Matrix
	 * @param riskAversion The Risk Aversion Parameter
	 * 
	 * @return The Instance of ForwardReverseHoldingsAllocation from a Standard Reverse Optimize Operation
	 */

	public static final ForwardReverseHoldingsAllocation Reverse (
		final org.drip.portfolioconstruction.asset.Portfolio equilibriumPortfolio,
		final double[][] assetExcessReturnsCovarianceMatrix,
		final double riskAversion)
	{
		if (null == equilibriumPortfolio)
		{
			return null;
		}

		double[] assetWeightArray = equilibriumPortfolio.weightArray();

		int assetCount = assetWeightArray.length;

		double[] expectedAssetExcessReturnsArray = org.drip.numerical.linearalgebra.MatrixUtil.Product (
			assetExcessReturnsCovarianceMatrix,
			assetWeightArray
		);

		if (null == expectedAssetExcessReturnsArray)
		{
			return null;
		}

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			expectedAssetExcessReturnsArray[assetIndex] = expectedAssetExcessReturnsArray [assetIndex] *
				riskAversion;
		}

		return ForwardReverseHoldingsAllocation.Standard (
			equilibriumPortfolio,
			riskAversion,
			assetExcessReturnsCovarianceMatrix,
			expectedAssetExcessReturnsArray
		);
	}

	/**
	 * Construct an Instance of ForwardReverseHoldingsAllocation from a Standard Forward Optimize Operation
	 * 
	 * @param assetIDArray The Array of the IDs of the Assets in the Portfolio
	 * @param expectedAssetExcessReturnsArray Array of Expected Excess Returns
	 * @param assetExcessReturnsCovarianceMatrix Excess Returns Co-variance Matrix
	 * @param riskAversion The Risk Aversion Parameter
	 * 
	 * @return The Instance of ForwardReverseHoldingsAllocation from a Standard Forward Optimize Operation
	 */

	public static final ForwardReverseHoldingsAllocation Forward (
		final java.lang.String[] assetIDArray,
		final double[] expectedAssetExcessReturnsArray,
		final double[][] assetExcessReturnsCovarianceMatrix,
		final double riskAversion)
	{
		if (null == assetIDArray)
		{
			return null;
		}

		int assetCount = assetIDArray.length;

		double[] assetWeightArray = org.drip.numerical.linearalgebra.MatrixUtil.Product (
			org.drip.numerical.linearalgebra.MatrixUtil.InvertUsingGaussianElimination (
				assetExcessReturnsCovarianceMatrix
			),
			expectedAssetExcessReturnsArray
		);

		if (null == assetWeightArray || assetCount != assetWeightArray.length)
		{
			return null;
		}

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			assetWeightArray[assetIndex] = assetWeightArray[assetIndex] / riskAversion;
		}

		return ForwardReverseHoldingsAllocation.Standard (
			org.drip.portfolioconstruction.asset.Portfolio.Standard (
				assetIDArray,
				assetWeightArray
			),
			riskAversion,
			assetExcessReturnsCovarianceMatrix,
			expectedAssetExcessReturnsArray
		);
	}

	/**
	 * Construct a Standard Instance of ForwardReverseHoldingsAllocation
	 * 
	 * @param equilibriumPortfolio The Optimal Equilibrium Portfolio
	 * @param riskAversion The Risk Aversion Parameter
	 * @param assetExcessReturnsCovarianceMatrix Pair-wise Asset Excess Returns Co-variance Matrix
	 * @param expectedAssetExcessReturnsArray Array of Expected Excess Returns
	 * 
	 * @return The Standard Instance of ForwardReverseHoldingsAllocation
	 */

	public static final ForwardReverseHoldingsAllocation Standard (
		final org.drip.portfolioconstruction.asset.Portfolio equilibriumPortfolio,
		final double riskAversion,
		final double[][] assetExcessReturnsCovarianceMatrix,
		final double[] expectedAssetExcessReturnsArray)
	{
		if (null == equilibriumPortfolio || null == expectedAssetExcessReturnsArray)
		{
			return null;
		}

		double[] assetWeightArray = equilibriumPortfolio.weightArray();

		double portfolioExcessReturnsMean = 0.;
		int assetCount = assetWeightArray.length;
		double portfolioExcessReturnsVariance = 0.;

		if (assetCount != expectedAssetExcessReturnsArray.length)
		{
			return null;
		}

		double[] impliedBetaArray = org.drip.numerical.linearalgebra.MatrixUtil.Product (
			assetExcessReturnsCovarianceMatrix,
			assetWeightArray
		);

		if (null == impliedBetaArray)
		{
			return null;
		}

		for (int assetIndexI = 0; assetIndexI < assetCount; ++assetIndexI)
		{
			portfolioExcessReturnsMean += assetWeightArray[assetIndexI] *
				expectedAssetExcessReturnsArray[assetIndexI];

			for (int assetIndexJ = 0; assetIndexJ < assetCount; ++assetIndexJ)
			{
				portfolioExcessReturnsVariance += assetWeightArray[assetIndexI] *
					assetWeightArray[assetIndexJ] *
					assetExcessReturnsCovarianceMatrix[assetIndexI][assetIndexJ];
			}
		}

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			impliedBetaArray[assetIndex] = impliedBetaArray[assetIndex] / portfolioExcessReturnsVariance;
		}

		double portfolioExcessReturnsSigma = java.lang.Math.sqrt (portfolioExcessReturnsVariance);

		try
		{
			return new ForwardReverseHoldingsAllocation (
				equilibriumPortfolio,
				new org.drip.portfolioconstruction.asset.PortfolioMetrics (
					portfolioExcessReturnsMean,
					portfolioExcessReturnsVariance,
					portfolioExcessReturnsSigma,
					portfolioExcessReturnsMean / portfolioExcessReturnsSigma,
					impliedBetaArray
				),
				riskAversion,
				assetExcessReturnsCovarianceMatrix,
				expectedAssetExcessReturnsArray
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ForwardReverseHoldingsAllocation Constructor
	 * 
	 * @param optimalEquilibriumPortfolio The Optimal Equilibrium Portfolio
	 * @param optimalEquilibriumPortfolioMetrics The Optimal Equilibrium Portfolio Metrics
	 * @param riskAversion The Risk Aversion Parameter
	 * @param assetExcessReturnsCovarianceMatrix Pair-wise Asset Excess Returns Co-variance Matrix
	 * @param expectedAssetExcessReturnsArray Array of Expected Excess Returns
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ForwardReverseHoldingsAllocation (
		final org.drip.portfolioconstruction.asset.Portfolio optimalEquilibriumPortfolio,
		final org.drip.portfolioconstruction.asset.PortfolioMetrics optimalEquilibriumPortfolioMetrics,
		final double riskAversion,
		final double[][] assetExcessReturnsCovarianceMatrix,
		final double[] expectedAssetExcessReturnsArray)
		throws java.lang.Exception
	{
		super (
			optimalEquilibriumPortfolio,
			optimalEquilibriumPortfolioMetrics
		);

		if (null == (_assetExcessReturnsCovarianceMatrix = assetExcessReturnsCovarianceMatrix) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_riskAversion = riskAversion) ||
			null == (_expectedAssetExcessReturnsArray = expectedAssetExcessReturnsArray))
		{
			throw new java.lang.Exception ("ForwardReverseHoldingsAllocation Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Excess Returns Co-variance Matrix between each Pair-wise Asset
	 * 
	 * @return The Excess Returns Co-variance Matrix between each Pair-wise Asset
	 */

	public double[][] assetExcessReturnsCovarianceMatrix()
	{
		return _assetExcessReturnsCovarianceMatrix;
	}

	/**
	 * Retrieve the Risk Aversion Coefficient
	 * 
	 * @return The Risk Aversion Coefficient
	 */

	public double riskAversion()
	{
		return _riskAversion;
	}

	/**
	 * Retrieve the Array of Expected Excess Returns Array for each Asset
	 * 
	 * @return The Array of Expected Excess Returns Array for each Asset
	 */

	public double[] expectedAssetExcessReturnsArray()
	{
		return _expectedAssetExcessReturnsArray;
	}

	/**
	 * Compute the Portfolio Relative Metrics using the specified Benchmark
	 * 
	 * @param benchmarkPortfolioMetrics The Benchmark Metrics
	 * 
	 * @return The Portfolio Relative Metrics using the specified Benchmark
	 */

	public org.drip.portfolioconstruction.asset.PortfolioBenchmarkMetrics benchmarkMetrics (
		final org.drip.portfolioconstruction.asset.PortfolioMetrics benchmarkPortfolioMetrics)
	{
		if (null == benchmarkPortfolioMetrics)
		{
			return null;
		}

		org.drip.portfolioconstruction.asset.PortfolioMetrics portfolioMetrics = optimalMetrics();

		try
		{
			double beta = org.drip.numerical.linearalgebra.MatrixUtil.DotProduct (
				optimalPortfolio().weightArray(),
				benchmarkPortfolioMetrics.impliedBeta()
			);

			double activeBeta = beta - 1.;

			double portfolioExcessReturnsMean = portfolioMetrics.excessReturnsMean();

			double benchmarkExcessReturnsMean = benchmarkPortfolioMetrics.excessReturnsMean();

			double benchmarkExcessReturnsVariance = benchmarkPortfolioMetrics.excessReturnsVariance();

			double residualRisk = java.lang.Math.sqrt (
				portfolioMetrics.excessReturnsVariance() - beta * beta * benchmarkExcessReturnsVariance
			);

			return new org.drip.portfolioconstruction.asset.PortfolioBenchmarkMetrics (
				beta,
				activeBeta,
				java.lang.Math.sqrt (
					residualRisk * residualRisk + activeBeta * activeBeta * benchmarkExcessReturnsVariance
				),
				portfolioExcessReturnsMean - benchmarkExcessReturnsMean,
				residualRisk,
				portfolioExcessReturnsMean - beta * benchmarkExcessReturnsMean
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
