
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
 * CustomRiskUtilitySettings contains the settings used to generate the Risk Objective Utility Function. It
 *  accommodates both the Risk Tolerance and Risk Aversion Variants.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CustomRiskUtilitySettings {
	private double _dblRiskAversion = java.lang.Double.NaN;
	private double _dblRiskTolerance = java.lang.Double.NaN;

	/**
	 * The Variance Minimizer CustomRiskUtilitySettings Instance
	 * 
	 * @return The Variance Minimizer CustomRiskUtilitySettings Instance
	 */

	public static final CustomRiskUtilitySettings VarianceMinimizer()
	{
		try {
			return new CustomRiskUtilitySettings (1., 0.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * The Risk Tolerant Variance Minimizer CustomRiskUtilitySettings Instance
	 * 
	 * @param dblRiskTolerance The Risk Tolerance Parameter
	 * 
	 * @return The Risk Tolerant Variance Minimizer CustomRiskUtilitySettings Instance
	 */

	public static final CustomRiskUtilitySettings RiskTolerant (
		final double dblRiskTolerance)
	{
		try {
			return new CustomRiskUtilitySettings (1., dblRiskTolerance);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * The Risk Aversion Variance Minimizer CustomRiskUtilitySettings Instance
	 * 
	 * @param dblRiskAversion The Risk Aversion Parameter
	 * 
	 * @return The Risk Aversion Variance Minimizer CustomRiskUtilitySettings Instance
	 */

	public static final CustomRiskUtilitySettings RiskAversion (
		final double dblRiskAversion)
	{
		try {
			return new CustomRiskUtilitySettings (dblRiskAversion, 1.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * CustomRiskUtilitySettings Constructor
	 * 
	 * @param dblRiskAversion The Risk Aversion Parameter
	 * @param dblRiskTolerance The Risk Tolerance Parameter
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CustomRiskUtilitySettings (
		final double dblRiskAversion,
		final double dblRiskTolerance)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblRiskAversion = dblRiskAversion) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblRiskTolerance = dblRiskTolerance) || 0. >
				_dblRiskTolerance)
			throw new java.lang.Exception ("CustomRiskUtilitySettings Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Risk Aversion Factor
	 * 
	 * @return The Risk Aversion Factor
	 */

	public double riskAversion()
	{
		return _dblRiskAversion;
	}

	/**
	 * Retrieve the Risk Tolerance Factor
	 * 
	 * @return The Risk Tolerance Factor
	 */

	public double riskTolerance()
	{
		return _dblRiskTolerance;
	}

	/**
	 * Retrieve the Custom Risk Objective Utility Multivariate
	 * 
	 * @param astrAssetID Array of the Asset IDs
	 * @param ausp The Asset Universe Statistical Properties Instance
	 * 
	 * @return The Custom Risk Objective Utility Multivariate
	 */

	public org.drip.function.definition.RdToR1 riskObjectiveUtility (
		final java.lang.String[] astrAssetID,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties ausp)
	{
		if (null == astrAssetID || null == ausp) return null;

		int iNumAsset = astrAssetID.length;
		double[] adblExpectedReturns = new double[iNumAsset];
		double[][] aadblCovariance = new double[iNumAsset][iNumAsset];
		org.drip.portfolioconstruction.params.AssetStatisticalProperties[] aASP = new
			org.drip.portfolioconstruction.params.AssetStatisticalProperties[iNumAsset];

		if (0 == iNumAsset) return null;

		for (int i = 0; i < iNumAsset; ++i) {
			if (null == (aASP[i] = ausp.asp (astrAssetID[i]))) return null;

			adblExpectedReturns[i] = aASP[i].expectedReturn();
		}

		for (int i = 0; i < iNumAsset; ++i) {
			double dblVarianceI = aASP[i].variance();

			for (int j = 0; j < iNumAsset; ++j) {
				try {
					aadblCovariance[i][j] = java.lang.Math.sqrt (dblVarianceI * aASP[j].variance()) * (i == j
						? 1. : ausp.correlation (astrAssetID[i], astrAssetID[j]));
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		try {
			return new org.drip.function.rdtor1.RiskObjectiveUtilityMultivariate (aadblCovariance,
				adblExpectedReturns, _dblRiskAversion, _dblRiskTolerance, ausp.riskFreeRate());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
