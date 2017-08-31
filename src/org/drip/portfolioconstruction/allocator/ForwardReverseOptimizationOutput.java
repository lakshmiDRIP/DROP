
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
 * ForwardReverseOptimizationOutput holds the Metrics that result from a Forward/Reverse Optimization Run.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ForwardReverseOptimizationOutput extends
	org.drip.portfolioconstruction.allocator.OptimizationOutput {
	private double _dblRiskAversion = java.lang.Double.NaN;
	private double[] _adblExpectedAssetExcessReturns = null;
	private double[][] _aadblAssetExcessReturnsCovariance = null;

	/**
	 * Construct an Instance of ForwardReverseOptimizationOutput from a Standard Reverse Optimize Operation
	 * 
	 * @param pfEquilibrium The Equilibrium Portfolio
	 * @param aadblAssetExcessReturnsCovariance Pair-wse Asset Excess Returns Co-variance Matrix
	 * @param dblRiskAversion The Risk Aversion Parameter
	 * 
	 * @return The Instance of ForwardReverseOptimizationOutput from a Standard Reverse Optimize Operation
	 */

	public static final ForwardReverseOptimizationOutput Reverse (
		final org.drip.portfolioconstruction.asset.Portfolio pfEquilibrium,
		final double[][] aadblAssetExcessReturnsCovariance,
		final double dblRiskAversion)
	{
		if (null == pfEquilibrium) return null;

		double[] adblAssetWeight = pfEquilibrium.weights();

		int iNumAsset = adblAssetWeight.length;

		double[] adblExpectedAssetExcessReturns = org.drip.quant.linearalgebra.Matrix.Product
			(aadblAssetExcessReturnsCovariance, pfEquilibrium.weights());

		if (null == adblExpectedAssetExcessReturns);

		for (int i = 0; i < iNumAsset; ++i)
			adblExpectedAssetExcessReturns[i] = adblExpectedAssetExcessReturns [i] * dblRiskAversion;

		return ForwardReverseOptimizationOutput.Standard (pfEquilibrium, dblRiskAversion,
			aadblAssetExcessReturnsCovariance, adblExpectedAssetExcessReturns);
	}

	/**
	 * Construct an Instance of ForwardReverseOptimizationOutput from a Standard Forward Optimize Operation
	 * 
	 * @param astrAssetID The Array of the Assets in the Portfolio
	 * @param adblExpectedAssetExcessReturns Array of Expected Excess Returns
	 * @param aadblAssetExcessReturnsCovariance Excess Returns Co-variance Matrix
	 * @param dblRiskAversion The Risk Aversion Parameter
	 * 
	 * @return The Instance of ForwardReverseOptimizationOutput from a Standard Forward Optimize Operation
	 */

	public static final ForwardReverseOptimizationOutput Forward (
		final java.lang.String[] astrAssetID,
		final double[] adblExpectedAssetExcessReturns,
		final double[][] aadblAssetExcessReturnsCovariance,
		final double dblRiskAversion)
	{
		if (null == astrAssetID) return null;

		int iNumAsset = astrAssetID.length;

		double[] adblAssetWeight = org.drip.quant.linearalgebra.Matrix.Product
			(org.drip.quant.linearalgebra.Matrix.InvertUsingGaussianElimination
				(aadblAssetExcessReturnsCovariance), adblExpectedAssetExcessReturns);

		if (null == adblAssetWeight || iNumAsset != adblAssetWeight.length) return null;

		for (int i = 0; i < iNumAsset; ++i)
			adblAssetWeight[i] = adblAssetWeight[i] / dblRiskAversion;

		return ForwardReverseOptimizationOutput.Standard
			(org.drip.portfolioconstruction.asset.Portfolio.Standard (astrAssetID, adblAssetWeight),
				dblRiskAversion, aadblAssetExcessReturnsCovariance, adblExpectedAssetExcessReturns);
	}

	/**
	 * Construct a Standard Instance of ForwardReverseOptimizationOutput
	 * 
	 * @param pfOptimal The Optimal Equilibrium Portfolio
	 * @param dblRiskAversion The Risk Aversion Parameter
	 * @param aadblAssetExcessReturnsCovariance Pair-wise Asset Excess Returns Co-variance Matrix
	 * @param adblExpectedAssetExcessReturns Array of Expected Excess Returns
	 * 
	 * @return The Standard Instance of ForwardReverseOptimizationOutput
	 */

	public static final ForwardReverseOptimizationOutput Standard (
		final org.drip.portfolioconstruction.asset.Portfolio pfOptimal,
		final double dblRiskAversion,
		final double[][] aadblAssetExcessReturnsCovariance,
		final double[] adblExpectedAssetExcessReturns)
	{
		if (null == pfOptimal || null == adblExpectedAssetExcessReturns) return null;

		double[] adblAssetWeight = pfOptimal.weights();

		int iNumAsset = adblAssetWeight.length;
		double dblPortfolioExcessReturnsMean = 0.;
		double dblPortfolioExcessReturnsVariance = 0.;

		if (iNumAsset != adblExpectedAssetExcessReturns.length) return null;

		double[] adblImpliedBeta = org.drip.quant.linearalgebra.Matrix.Product
			(aadblAssetExcessReturnsCovariance, adblAssetWeight);

		if (null == adblImpliedBeta) return null;

		for (int i = 0; i < iNumAsset; ++i) {
			dblPortfolioExcessReturnsMean += adblAssetWeight[i] * adblExpectedAssetExcessReturns[i];

			for (int j = 0; j < iNumAsset; ++j)
				dblPortfolioExcessReturnsVariance += adblAssetWeight[i] * adblAssetWeight[j] *
					aadblAssetExcessReturnsCovariance[i][j];
		}

		for (int i = 0; i < iNumAsset; ++i)
			adblImpliedBeta[i] = adblImpliedBeta[i] / dblPortfolioExcessReturnsVariance;

		double dblPortfolioExcessReturnsSigma = java.lang.Math.sqrt (dblPortfolioExcessReturnsVariance);

		try {
			return new ForwardReverseOptimizationOutput (pfOptimal, new
				org.drip.portfolioconstruction.asset.PortfolioMetrics (dblPortfolioExcessReturnsMean,
					dblPortfolioExcessReturnsVariance, dblPortfolioExcessReturnsSigma,
						dblPortfolioExcessReturnsMean / dblPortfolioExcessReturnsSigma, adblImpliedBeta),
							dblRiskAversion, aadblAssetExcessReturnsCovariance,
								adblExpectedAssetExcessReturns);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ForwardReverseOptimizationOutput Constructor
	 * 
	 * @param pfOptimal The Optimal Equilibrium Portfolio
	 * @param pmOptimal The Optimal Equilibrium Portfolio Metrics
	 * @param dblRiskAversion The Risk Aversion Parameter
	 * @param aadblAssetExcessReturnsCovariance Pair-wise Asset Excess Returns Co-variance Matrix
	 * @param adblExpectedAssetExcessReturns Array of Expected Excess Returns
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ForwardReverseOptimizationOutput (
		final org.drip.portfolioconstruction.asset.Portfolio pfOptimal,
		final org.drip.portfolioconstruction.asset.PortfolioMetrics pmOptimal,
		final double dblRiskAversion,
		final double[][] aadblAssetExcessReturnsCovariance,
		final double[] adblExpectedAssetExcessReturns)
		throws java.lang.Exception
	{
		super (pfOptimal, pmOptimal);

		if (null == (_aadblAssetExcessReturnsCovariance = aadblAssetExcessReturnsCovariance) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblRiskAversion = dblRiskAversion) || null ==
				(_adblExpectedAssetExcessReturns = adblExpectedAssetExcessReturns))
			throw new java.lang.Exception ("ForwardReverseOptimizationOutput Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Excess Returns Co-variance Matrix between each Pair-wise Asset
	 * 
	 * @return The Excess Returns Co-variance Matrix between each Pair-wise Asset
	 */

	public double[][] assetExcessReturnsCovariance()
	{
		return _aadblAssetExcessReturnsCovariance;
	}

	/**
	 * Retrieve the Risk Aversion Coefficient
	 * 
	 * @return The Risk Aversion Coefficient
	 */

	public double riskAversion()
	{
		return _dblRiskAversion;
	}

	/**
	 * Retrieve the Array of Expected Excess Returns for each Asset
	 * 
	 * @return The Array of Expected Excess Returns for each Asset
	 */

	public double[] expectedAssetExcessReturns()
	{
		return _adblExpectedAssetExcessReturns;
	}

	/**
	 * Compute the Portfolio Relative Metrics using the specified Benchmark
	 * 
	 * @param pmBenchmark The Benchmark Metrics
	 * 
	 * @return The Portfolio Relative Metrics using the specified Benchmark
	 */

	public org.drip.portfolioconstruction.asset.PortfolioBenchmarkMetrics benchmarkMetrics (
		final org.drip.portfolioconstruction.asset.PortfolioMetrics pmBenchmark)
	{
		if (null == pmBenchmark) return null;

		org.drip.portfolioconstruction.asset.PortfolioMetrics pm = optimalMetrics();

		try {
			double dblBeta = org.drip.quant.linearalgebra.Matrix.DotProduct (optimalPortfolio().weights(),
				pmBenchmark.impliedBeta());

			double dblActiveBeta = dblBeta - 1.;

			double dblPortfolioExcessReturnsMean = pm.excessReturnsMean();

			double dblBenchmarkExcessReturnsMean = pmBenchmark.excessReturnsMean();

			double dblBenchmarkExcessReturnsVariance = pmBenchmark.excessReturnsVariance();

			double dblResidualRisk = java.lang.Math.sqrt (pm.excessReturnsVariance() - dblBeta * dblBeta *
				dblBenchmarkExcessReturnsVariance);

			return new org.drip.portfolioconstruction.asset.PortfolioBenchmarkMetrics (dblBeta,
				dblActiveBeta, java.lang.Math.sqrt (dblResidualRisk * dblResidualRisk + dblActiveBeta *
					dblActiveBeta * dblBenchmarkExcessReturnsVariance), dblPortfolioExcessReturnsMean -
						dblBenchmarkExcessReturnsMean, dblResidualRisk, dblPortfolioExcessReturnsMean -
							dblBeta * dblBenchmarkExcessReturnsMean);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
