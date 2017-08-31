
package org.drip.portfolioconstruction.mpt;

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
 * AssetSecurityCharacteristicLine holds the Asset's Alpha and Beta from which the Asset's Excess Returns
 *  over the Risk-Free Rate are estimated.
 *
 * @author Lakshmi Krishnamurthy
 */

public class AssetSecurityCharacteristicLine {
	private double _dblBeta = java.lang.Double.NaN;
	private double _dblAlpha = java.lang.Double.NaN;

	/**
	 * AssetSecurityCharacteristicLine Constructor
	 * 
	 * @param dblAlpha The Asset's Alpha
	 * @param dblBeta The Asset's Beta
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AssetSecurityCharacteristicLine (
		final double dblAlpha,
		final double dblBeta)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblAlpha = dblAlpha) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblBeta = dblBeta))
			throw new java.lang.Exception ("AssetSecurityCharacteristicLine Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Asset's Alpha
	 * 
	 * @return The Asset's Alpha
	 */

	public double alpha()
	{
		return _dblAlpha;
	}

	/**
	 * Retrieve the Asset's Beta
	 * 
	 * @return The Asset's Beta
	 */

	public double beta()
	{
		return _dblBeta;
	}

	/**
	 * Retrieve the Excess Returns over the Market for the Asset
	 * 
	 * @param dblMarketExcessReturns The Market Premium, i.e., the Excess Market Returns over the Risk Free
	 * 	Rate
	 * 
	 * @return The Excess Returns over the Market for the Asset
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double excessReturns (
		final double dblMarketExcessReturns)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblMarketExcessReturns))
			throw new java.lang.Exception
				("AssetSecurityCharacteristicLine::excessReturns => Invalid Inputs");

		return _dblAlpha + _dblBeta * dblMarketExcessReturns;
	}
}
