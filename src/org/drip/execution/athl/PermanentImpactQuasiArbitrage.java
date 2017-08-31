
package org.drip.execution.athl;

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
 * PermanentImpactQuasiArbitrage implements the Linear Permanent Market Impact with Coefficients that have
 *  been determined empirically by Almgren, Thum, Hauptmann, and Li (2005), independent of the no
 *  Quasi-Arbitrage Criterion identified by Huberman and Stanzl (2004). The References are:
 * 
 * 	- Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3 (2)
 * 		5-39.
 * 
 * 	- Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk,
 * 		Applied Mathematical Finance 10 (1) 1-18.
 *
 * 	- Almgren, R., and N. Chriss (2003): Bidding Principles, Risk 97-102.
 *
 * 	- Huberman, G., and W. Stanzl (2004): Price Manipulation and Quasi-arbitrage, Econometrica 72 (4)
 * 		1247-1275.
 * 
 * 	- Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact, Risk 18 (7) 57-62.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PermanentImpactQuasiArbitrage extends org.drip.execution.impact.TransactionFunctionPower {
	private double _dblLiquidityFactor = java.lang.Double.NaN;
	private org.drip.execution.parameters.AssetFlowSettings _afp = null;

	/**
	 * PermanentImpactQuasiArbitrage Constructor
	 * 
	 * @param afp The Asset Flow Parameters
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PermanentImpactQuasiArbitrage (
		final org.drip.execution.parameters.AssetFlowSettings afp)
		throws java.lang.Exception
	{
		if (null == (_afp = afp))
			throw new java.lang.Exception ("PermanentImpactQuasiArbitrage Constructor => Invalid Inputs");

		_dblLiquidityFactor = java.lang.Math.pow (afp.inverseTurnover(),
			org.drip.execution.athl.CalibrationEmpirics.PERMANENT_IMPACT_INVERSE_TURNOVER_EXPONENT);
	}

	/**
	 * Retrieve the Liquidity Factor
	 * 
	 * @return The Liquidity Factor
	 */

	public double liquidityFactor()
	{
		return _dblLiquidityFactor;
	}

	/**
	 * Retrieve the Asset Flow Parameters
	 * 
	 * @return The Asset Flow Parameters
	 */

	public org.drip.execution.parameters.AssetFlowSettings assetFlowParameters()
	{
		return _afp;
	}

	@Override public double regularize (
		final double dblTradeInterval)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblTradeInterval) || 0 >= dblTradeInterval)
			throw new java.lang.Exception ("PermanentImpactQuasiArbitrage::regularize => Invalid Inputs");

		return 1. / (_afp.averageDailyVolume() * dblTradeInterval);
	}

	@Override public double modulate (
		final double dblTradeInterval)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblTradeInterval) || 0 >= dblTradeInterval)
			throw new java.lang.Exception ("PermanentImpactQuasiArbitrage::modulate => Invalid Inputs");

		return dblTradeInterval * _afp.dailyVolatility();
	}

	@Override public double constant()
	{
		return org.drip.execution.athl.CalibrationEmpirics.PERMANENT_IMPACT_COEFFICIENT *
			_dblLiquidityFactor;
	}

	@Override public double exponent()
	{
		return org.drip.execution.athl.CalibrationEmpirics.PERMANENT_IMPACT_EXPONENT_ATHL2005;
	}

	@Override public double evaluate (
		final double dblNormalizedX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblNormalizedX))
			throw new java.lang.Exception ("PermanentImpactQuasiArbitrage::evaluate => Invalid Inputs");

		double dblAlpha = org.drip.execution.athl.CalibrationEmpirics.PERMANENT_IMPACT_EXPONENT_ATHL2005;
		double dblGamma = org.drip.execution.athl.CalibrationEmpirics.PERMANENT_IMPACT_COEFFICIENT;

		return 0.5 * dblGamma * (dblNormalizedX < 0. ? -1. : 1.) * java.lang.Math.pow (java.lang.Math.abs
			(dblNormalizedX), dblAlpha) * _dblLiquidityFactor;
	}

	@Override public double derivative  (
		final double dblNormalizedX,
		final int iOrder)
		throws java.lang.Exception
	{
		if (0 >= iOrder || !org.drip.quant.common.NumberUtil.IsValid (dblNormalizedX))
			throw new java.lang.Exception ("PermanentImpactQuasiArbitrage::derivative => Invalid Inputs");

		double dblCoefficient = 1.;
		double dblAlpha = org.drip.execution.athl.CalibrationEmpirics.PERMANENT_IMPACT_EXPONENT_ATHL2005;
		double dblGamma = org.drip.execution.athl.CalibrationEmpirics.PERMANENT_IMPACT_COEFFICIENT;

		for (int i = 0; i < iOrder; ++i)
			dblCoefficient = dblCoefficient * (dblAlpha - i);

		return 0.5 * dblGamma * (dblNormalizedX < 0. ? -1. : 1.) * dblCoefficient * java.lang.Math.pow
			(java.lang.Math.abs (dblNormalizedX), dblAlpha - iOrder) * _dblLiquidityFactor;
	}
}
