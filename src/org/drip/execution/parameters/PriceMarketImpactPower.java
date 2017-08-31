
package org.drip.execution.parameters;

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
 *  	pricing/valuation, risk, and market making.
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
 * PriceMarketImpactPower contains the Power Law based Price Market Impact Inputs used in the Construction of
 *  the Impact Parameters for the Almgren and Chriss (2000) Optimal Trajectory Generation Scheme. The
 *  References are:
 * 
 * 	- Almgren, R., and N. Chriss (1999): Value under Liquidation, Risk 12 (12).
 * 
 * 	- Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3 (2)
 * 		5-39.
 * 
 * 	- Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk,
 * 		Applied Mathematical Finance 10 (1) 1-18.
 *
 * 	- Almgren, R., and N. Chriss (2003): Bidding Principles, Risk 97-102.
 * 
 * 	- Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs, Journal of Financial Markets,
 * 		1, 1-50.
 *
 * @author Lakshmi Krishnamurthy
 */

public class PriceMarketImpactPower extends org.drip.execution.parameters.PriceMarketImpact {
	private double _dblTemporaryImpactExponent = java.lang.Double.NaN;
	private double _dblDailyVolumeExecutionFactor = java.lang.Double.NaN;

	/**
	 * PriceMarketImpactPower Constructor
	 * 
	 * @param ats The Asset Transaction Settings Instance
	 * @param dblPermanentImpactFactor The Fraction of the Daily Volume that triggers One Bid-Ask of
	 *  Permanent Impact Cost
	 * @param dblTemporaryImpactFactor The Fraction of the Daily Volume that triggers One Bid-Ask of
	 *  Temporary Impact Cost
	 * @param dblDailyVolumeExecutionFactor The Daily Reference Execution Rate as a Proportion of the Daily
	 * 	Volume
	 * @param dblTemporaryImpactExponent The Temporary Impact Exponent
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PriceMarketImpactPower (
		final org.drip.execution.parameters.AssetTransactionSettings ats,
		final double dblPermanentImpactFactor,
		final double dblTemporaryImpactFactor,
		final double dblDailyVolumeExecutionFactor,
		final double dblTemporaryImpactExponent)
		throws java.lang.Exception
	{
		super (ats, dblPermanentImpactFactor, dblTemporaryImpactFactor);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblDailyVolumeExecutionFactor =
			dblDailyVolumeExecutionFactor) || 0. >= _dblDailyVolumeExecutionFactor ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblTemporaryImpactExponent =
					dblTemporaryImpactExponent))
			throw new java.lang.Exception ("PriceMarketImpactPower Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Daily Reference Execution Rate as a Proportion of the Daily Volume
	 * 
	 * @return The Daily Reference Execution Rate as a Proportion of the Daily Volume
	 */

	public double dailyVolumeExecutionFactor()
	{
		return _dblDailyVolumeExecutionFactor;
	}

	/**
	 * Generate the Permanent Impact Transaction Function
	 * 
	 * @return The Permanent Impact Transaction Function
	 */

	public org.drip.execution.impact.TransactionFunction permanentTransactionFunction()
	{
		try {
			return new org.drip.execution.impact.ParticipationRateLinear (0., 0.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Temporary Impact Transaction Function
	 * 
	 * @return The Temporary Impact Transaction Function
	 */

	public org.drip.execution.impact.TransactionFunction temporaryTransactionFunction()
	{
		org.drip.execution.parameters.AssetTransactionSettings ats = ats();

		try {
			return new org.drip.execution.impact.ParticipationRatePower (ats.price() *
				temporaryImpactFactor() / java.lang.Math.pow (ats.backgroundVolume() *
					_dblDailyVolumeExecutionFactor, _dblTemporaryImpactExponent),
						_dblTemporaryImpactExponent);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
