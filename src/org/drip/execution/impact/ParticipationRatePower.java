
package org.drip.execution.impact;

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
 * ParticipationRatePower implements a Power-Law Based Temporary/Permanent Market Impact Function where the
 *  Price Change scales as a Power of the Trade Rate. The References are:
 * 
 * 	- Almgren, R., and N. Chriss (1999): Value under Liquidation, Risk 12 (12).
 * 
 * 	- Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3 (2)
 * 		5-39.
 * 
 * 	- Almgren, R., and N. Chriss (2003): Optimal Execution with Nonlinear Impact Functions and Trading-
 * 		Enhanced Risk, Applied Mathematical Finance 10 (1) 1-18.
 *
 * 	- Almgren, R., and N. Chriss (2003): Bidding Principles, Risk 97-102.
 * 
 * 	- Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs, Journal of Financial Markets,
 * 		1, 1-50.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ParticipationRatePower extends org.drip.execution.impact.TransactionFunctionPower {
	private double _dblConstant = java.lang.Double.NaN;
	private double _dblExponent = java.lang.Double.NaN;

	/**
	 * ParticipationRatePower Constructor
	 * 
	 * @param dblConstant The Market Impact Constant Parameter
	 * @param dblExponent The Market Impact Power Law Exponent
	 * 
	 * @throws java.lang.Exception Propagated up from R1ToR1
	 */

	public ParticipationRatePower (
		final double dblConstant,
		final double dblExponent)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblConstant = dblConstant) || 0. > _dblConstant ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblExponent = dblExponent) || 0. > _dblExponent)
			throw new java.lang.Exception ("ParticipationRatePower Constructor => Invalid Inputs");
	}

	@Override public double constant()
	{
		return _dblConstant;
	}

	@Override public double exponent()
	{
		return _dblExponent;
	}

	@Override public double regularize (
		final double dblTradeInterval)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblTradeInterval) || 0 >= dblTradeInterval)
			throw new java.lang.Exception ("ParticipationRatePower::regularize => Invalid Inputs");

		return 1. / dblTradeInterval;
	}

	@Override public double modulate (
		final double dblTradeInterval)
		throws java.lang.Exception
	{
		return 1.;
	}

	@Override public double evaluate  (
		final double dblTradeRate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblTradeRate))
			throw new java.lang.Exception ("ParticipationRatePower::evaluate => Invalid Inputs");

		return (dblTradeRate < 0. ? -1. : 1.) * _dblConstant * java.lang.Math.pow (java.lang.Math.abs
			(dblTradeRate), _dblExponent);
	}

	@Override public double derivative (
		final double dblTradeRate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (0 >= iOrder || !org.drip.quant.common.NumberUtil.IsValid (dblTradeRate))
			throw new java.lang.Exception ("ParticipationRatePower::derivative => Invalid Inputs");

		double dblCoefficient = 1.;

		for (int i = 0; i < iOrder; ++i)
			dblCoefficient = dblCoefficient * (_dblExponent - i);

		return (dblTradeRate < 0. ? -1. : 1.) * dblCoefficient * _dblConstant * java.lang.Math.pow
			(java.lang.Math.abs (dblTradeRate), _dblExponent - iOrder);
	}
}
