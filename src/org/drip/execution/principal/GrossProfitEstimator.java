
package org.drip.execution.principal;

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
 * GrossProfitEstimator generates the Gross Profit Distribution and the Information Ratio for a given Level
 *  of Principal Discount. The References are:
 * 
 * 	- Almgren, R., and N. Chriss (1999): Value under Liquidation, Risk 12 (12).
 * 
 * 	- Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3 (2)
 * 		5-39.
 * 
 * 	- Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk,
 * 		Applied Mathematical Finance 10 (1) 1-18.
 *
 * 	- Almgren, R., and N. Chriss (2003): Bidding Principles, Risk 16 (6) 97-102.
 * 
 * 	- Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact, Risk 18 (7) 57-62.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class GrossProfitEstimator {
	private org.drip.execution.optimum.EfficientTradingTrajectory _ett = null;

	/**
	 * GrossProfitEstimator Constructor
	 * 
	 * @param ett The efficient Trading Trajectory Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public GrossProfitEstimator (
		final org.drip.execution.optimum.EfficientTradingTrajectory ett)
		throws java.lang.Exception
	{
		if (null == (_ett = ett))
			throw new java.lang.Exception ("GrossProfitEstimator Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Optimal Efficient Trajectory
	 * 
	 * @return The Optimal "Efficient" Trajectory
	 */

	public org.drip.execution.optimum.EfficientTradingTrajectory efficientTrajectory()
	{
		return _ett;
	}

	/**
	 * Generate R^1 Univariate Normal Gross Profit Distribution from the specified Principal Discount
	 * 
	 * @param dblPrincipalDiscount The Principal Discount
	 * 
	 * @return The R^1 Univariate Normal Gross Profit Distribution from the specified Principal Discount
	 */

	public org.drip.measure.gaussian.R1UnivariateNormal principalMeasure (
		final double dblPrincipalDiscount)
	{
		try {
			return new org.drip.measure.gaussian.R1UnivariateNormal (dblPrincipalDiscount * _ett.tradeSize()
				- _ett.transactionCostExpectation(), java.lang.Math.sqrt (_ett.transactionCostVariance()));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Break-even Principal Discount
	 * 
	 * @return The Break-even Principal Discount
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double breakevenPrincipalDiscount()
		throws java.lang.Exception
	{
		return _ett.transactionCostExpectation() / _ett.tradeSize();
	}

	/**
	 * Generate R^1 Univariate Normal Gross Profit Distribution from the specified Principal Discount
	 * 
	 * @param dblPrincipalDiscount The Principal Discount
	 * 
	 * @return The R^1 Univariate Normal Gross Profit Distribution from the specified Principal Discount
	 */

	public org.drip.measure.gaussian.R1UnivariateNormal horizonPrincipalMeasure (
		final double dblPrincipalDiscount)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPrincipalDiscount)) return null;

		double dblInverseHorizon = 1. / _ett.executionTime();

		try {
			return new org.drip.measure.gaussian.R1UnivariateNormal (dblPrincipalDiscount * _ett.tradeSize()
				- _ett.transactionCostExpectation() * dblInverseHorizon, java.lang.Math.sqrt
					(_ett.transactionCostVariance() * dblInverseHorizon));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Information Ratio given the Principal Discount
	 * 
	 * @param dblPrincipalDiscount The Principal Discount
	 * 
	 * @return The Information Ratio
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs cannot be calculated
	 */

	public double informationRatio (
		final double dblPrincipalDiscount)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPrincipalDiscount))
			throw new java.lang.Exception ("GrossProfitEstimator::informationRatio => Invalid Inputs");

		double dblInverseHorizon = 1. / _ett.executionTime();

		return dblInverseHorizon * (dblPrincipalDiscount * _ett.tradeSize() -
			_ett.transactionCostExpectation()) / java.lang.Math.sqrt (dblInverseHorizon *
				_ett.transactionCostVariance());
	}
}
