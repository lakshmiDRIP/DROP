
package org.drip.portfolioconstruction.objective;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * RobustErrorTerm optimizes the Error in the Target Expected Absolute Return of the Portfolio on the
 *  Absence of Benchmark, and the Error in the Benchmark-Adjusted Returns Otherwise.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class RobustErrorTerm extends org.drip.portfolioconstruction.objective.ReturnsTerm {
	private double[][] _aadblAssetCovariance = null;
	private double[][] _aadblAlphaUncertainty = null;
	private double _dblConfidenceLevel = java.lang.Double.NaN;

	/**
	 * RobustErrorTerm Constructor
	 * 
	 * @param strName Name of the Expected Returns Objective Term
	 * @param adblInitialHoldings Initial Holdings
	 * @param adblAlpha Asset Alpha
	 * @param aadblAlphaUncertainty Alpha Uncertainty Matrix
	 * @param aadblAssetCovariance Asset Co-variance Matrix
	 * @param adblBenchmarkConstrictedHoldings Benchmark Constricted Holdings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RobustErrorTerm (
		final java.lang.String strName,
		final double[] adblInitialHoldings,
		final double[] adblAlpha,
		final double[][] aadblAlphaUncertainty,
		final double[][] aadblAssetCovariance,
		final double[] adblBenchmarkConstrictedHoldings)
		throws java.lang.Exception
	{
		super (
			strName,
			"OT_ROBUST",
			"Robust Error Portfolio Returns Objective Term",
			adblInitialHoldings,
			adblAlpha,
			adblBenchmarkConstrictedHoldings
		);

		int iNumAsset = adblInitialHoldings.length;

		if (null == (_aadblAlphaUncertainty = aadblAlphaUncertainty) || iNumAsset !=
			_aadblAlphaUncertainty.length)
			throw new java.lang.Exception ("RobustErrorTerm Constructor => Invalid Inputs");

		for (int i = 0; i < iNumAsset; ++i) {
			if (null == _aadblAlphaUncertainty[i] || !org.drip.quant.common.NumberUtil.IsValid
				(_aadblAlphaUncertainty[i]) || iNumAsset != _aadblAlphaUncertainty[i].length)
				throw new java.lang.Exception ("RobustErrorTerm Constructor => Invalid Inputs");
		}

		if (null == (_aadblAssetCovariance = aadblAssetCovariance) || iNumAsset !=
			_aadblAssetCovariance.length)
			throw new java.lang.Exception ("RobustErrorTerm Constructor => Invalid Inputs");

		for (int i = 0; i < iNumAsset; ++i) {
			if (null == _aadblAssetCovariance[i] || !org.drip.quant.common.NumberUtil.IsValid
				(_aadblAssetCovariance[i]) || iNumAsset != _aadblAssetCovariance[i].length)
				throw new java.lang.Exception ("RobustErrorTerm Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Confidence Level (i.e., Eta)
	 * 
	 * @return The Confidence Level (i.e., Eta)
	 */

	public double confidenceLevel()
	{
		return _dblConfidenceLevel;
	}

	/**
	 * Retrieve the Asset Co-variance Matrix
	 * 
	 * @return The Asset Co-variance Matrix
	 */

	public double[][] assetCovariance()
	{
		return _aadblAssetCovariance;
	}

	/**
	 * Retrieve the Alpha Uncertainty Matrix
	 * 
	 * @return The Alpha Uncertainty Matrix
	 */

	public double[][] alphaUncertainty()
	{
		return _aadblAlphaUncertainty;
	}
}
