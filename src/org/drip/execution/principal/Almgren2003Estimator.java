
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
 * Almgren2003Estimator generates the Gross Profit Distribution and the Information Ratio for a given Level
 *  of Principal Discount for an Optimal Trajectory that is generated using the Almgren (2003) Scheme. The
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
 * 	- Almgren, R., and N. Chriss (2003): Bidding Principles, Risk 16 (6) 97-102.
 * 
 * 	- Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact, Risk 18 (7) 57-62.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Almgren2003Estimator extends org.drip.execution.principal.GrossProfitEstimator {
	private org.drip.execution.dynamics.LinearPermanentExpectationParameters _lpep =  null;

	/**
	 * Almgren2003Estimator Constructor
	 * 
	 * @param pic The Power Continuous Market Impact Trajectory
	 * @param lpep The Linear Permanent Expectation Paremeter
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Almgren2003Estimator (
		final org.drip.execution.optimum.PowerImpactContinuous pic,
		final org.drip.execution.dynamics.LinearPermanentExpectationParameters lpep)
		throws java.lang.Exception
	{
		super (pic);

		if (null == (_lpep = lpep))
			throw new java.lang.Exception ("Almgren2003Estimator Constructor => Invalid Inputs");
	}

	/**
	 * Generate the Horizon that results in the Optimal Information Ratio
	 * 
	 * @param dblD The Principal Discount "D"
	 * 
	 * @return The Horizon that results in the Optimal Information Ratio
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double optimalInformationRatioHorizon (
		final double dblD)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblD))
			throw new java.lang.Exception
				("Almgren2003Estimator::optimalInformationRatioHorizon => Invalid Inputs");

		org.drip.execution.impact.TransactionFunctionPower tfpTemporaryExpectation =
			(org.drip.execution.impact.TransactionFunctionPower)
				_lpep.temporaryExpectation().epochImpactFunction();

		double dblGamma = ((org.drip.execution.impact.TransactionFunctionLinear)
			_lpep.linearPermanentExpectation().epochImpactFunction()).slope();

		double dblEta = tfpTemporaryExpectation.constant();

		double dblK = tfpTemporaryExpectation.exponent();

		double dblX = efficientTrajectory().tradeSize();

		return dblX * java.lang.Math.pow (dblEta * (dblK + 1.) * (dblK + 1.) / (3. * dblK + 1.) / (dblD - 0.5
			* dblGamma * dblX), 1. / dblK);
	}

	/**
	 * Compute the Optimal Information Ratio
	 * 
	 * @param dblD The Principal Discount "D"
	 * 
	 * @return The Optimal Information Ratio
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double optimalInformationRatio (
		final double dblD)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblD))
			throw new java.lang.Exception
				("Almgren2003Estimator::optimalInformationRatio => Invalid Inputs");

		org.drip.execution.impact.TransactionFunctionPower tfpTemporaryExpectation =
			(org.drip.execution.impact.TransactionFunctionPower)
				_lpep.temporaryExpectation().epochImpactFunction();

		double dblSigma = _lpep.arithmeticPriceDynamicsSettings().epochVolatility();

		double dblGamma = ((org.drip.execution.impact.TransactionFunctionLinear)
			_lpep.linearPermanentExpectation().epochImpactFunction()).slope();

		double dblEta = tfpTemporaryExpectation.constant();

		double dblK = tfpTemporaryExpectation.exponent();

		double dblX = efficientTrajectory().tradeSize();

		return java.lang.Math.pow (3. * dblK + 1.              , (1. * dblK + 2.) / (2. * dblK)) /
			   java.lang.Math.pow (1. * dblK + 1.              , (3. * dblK + 4.) / (2. * dblK)) *
			   java.lang.Math.pow (dblD - 0.5 * dblGamma * dblX, (1. * dblK + 1.) / (1. * dblK)) /
			   java.lang.Math.pow (dblEta                      , (0. * dblK + 1.) / (1. * dblK)) /
			   (dblX * dblSigma);
	}

	/**
	 * Compute the Principal Discount Hurdle given the Information Ratio
	 * 
	 * @param dblI The Optimal Information Ratio "I"
	 * 
	 * @return The Principal Discount Hurdle
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double principalDiscountHurdle (
		final double dblI)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblI))
			throw new java.lang.Exception
				("Almgren2003Estimator::principalDiscountHurdle => Invalid Inputs");

		org.drip.execution.impact.TransactionFunctionPower tfpTemporaryExpectation =
			(org.drip.execution.impact.TransactionFunctionPower)
				_lpep.temporaryExpectation().epochImpactFunction();

		double dblEta = tfpTemporaryExpectation.constant();

		double dblGamma = ((org.drip.execution.impact.TransactionFunctionLinear)
			_lpep.linearPermanentExpectation().epochImpactFunction()).slope();

		double dblSigma = _lpep.arithmeticPriceDynamicsSettings().epochVolatility();

		double dblK = tfpTemporaryExpectation.exponent();

		double dblX = efficientTrajectory().tradeSize();

		return java.lang.Math.pow (
			   0.5 * dblGamma * dblX +
			   java.lang.Math.pow (1. * dblK + 1.              , (3. * dblK + 4.) / (2. * dblK)) /
			   java.lang.Math.pow (3. * dblK + 1.              , (1. * dblK + 2.) / (2. * dblK)) *
			   java.lang.Math.pow (dblEta                      , (0. * dblK + 1.) / (1. * dblK)) *
			   (dblX * dblSigma * dblI),
			   dblK / (dblK + 1.)
		);
	}

	/**
	 * Generate the Constant/Exponent Dependencies on the Market Parameters for the Optimal Execution Horizon
	 * 	/ Information Ratio
	 *  
	 * @return The Optimal Execution Horizon/Information Ratio Dependency
	 */

	public org.drip.execution.principal.HorizonInformationRatioDependence optimalMeasures()
	{
		org.drip.execution.impact.TransactionFunctionPower tfpTemporaryExpectation =
			(org.drip.execution.impact.TransactionFunctionPower)
				_lpep.temporaryExpectation().epochImpactFunction();

		double dblK = tfpTemporaryExpectation.exponent();

		try {
			return new org.drip.execution.principal.HorizonInformationRatioDependence (
				new org.drip.execution.principal.OptimalMeasureDependence (
					java.lang.Math.pow ((dblK + 1.) * (dblK + 1.) / (3. * dblK + 1.), 1. / dblK),
					1. / dblK,
					1.,
					0.,
					-1. / dblK
				),
				new org.drip.execution.principal.OptimalMeasureDependence (
					java.lang.Math.pow (3. * dblK + 1., (1. * dblK + 2.) / (2. * dblK)) /
						java.lang.Math.pow (1. * dblK + 1., (3. * dblK + 4.) / (2. * dblK)),
					-1. / dblK,
					-1.,
					-1.,
					(dblK + 1.) / dblK
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
