
package org.drip.execution.hjb;

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
 * NonDimensionalCostSystemic contains the Level, the Gradient, and the Jacobian of the HJB Non Dimensional
 *  Cost Value Function to the Systemic Market State. The References are:
 * 
 * 	- Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3
 * 		(2) 5-39.
 *
 * 	- Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 * 		https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf.
 *
 * 	- Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility, SIAM Journal of
 * 		Financial Mathematics  3 (1) 163-181.
 * 
 * 	- Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes, Mathematical Finance 11 (1)
 * 		79-96.
 * 
 * 	- Jones, C. M., G. Kaul, and M. L. Lipson (1994): Transactions, Volume, and Volatility, Review of
 * 		Financial Studies 7 (4) 631-651.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class NonDimensionalCostSystemic extends org.drip.execution.hjb.NonDimensionalCost {
	private double _dblGradient = java.lang.Double.NaN;
	private double _dblJacobian = java.lang.Double.NaN;

	/**
	 * Generate a Zero Sensitivity Systemic Non Dimensional Cost Instance
	 * 
	 * @return The Zero Sensitivity Systemic Non Dimensional Cost Instance
	 */

	public static final NonDimensionalCostSystemic Zero()
	{
		try {
			return new NonDimensionalCostSystemic (0., 0., 0., 0.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a Linear Trading Systemic Non Dimensional Cost Instance
	 * 
	 * @param dblMarketStateExponentiation The Exponentiated Market State
	 * @param dblNonDimensionalTime The Non Dimensional Time
	 * 
	 * @return The Linear Trading Systemic Non Dimensional Cost Instance
	 */

	public static final NonDimensionalCostSystemic LinearThreshold (
		final double dblMarketStateExponentiation,
		final double dblNonDimensionalTime)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblMarketStateExponentiation) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblNonDimensionalTime) || 0. >= dblNonDimensionalTime)
			return null;

		double dblNonDimensionalUrgency = 1. / dblNonDimensionalTime;
		double dblNonDimensionalCostThreshold = dblMarketStateExponentiation * dblNonDimensionalUrgency;

		try {
			return new NonDimensionalCostSystemic (dblNonDimensionalCostThreshold,
				dblNonDimensionalCostThreshold, dblNonDimensionalCostThreshold, dblNonDimensionalUrgency);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a Euler Enhanced Linear Trading Systemic Non Dimensional Cost Instance
	 * 
	 * @param dblMarketState The Market State
	 * @param dblNonDimensionalCost The Non Dimensional Cost
	 * @param dblNonDimensionalCostCross The Non Dimensional Cost Cross Term
	 * 
	 * @return The Euler Enhanced Linear Trading Systemic Non Dimensional Cost Instance
	 */

	public static final NonDimensionalCostSystemic EulerEnhancedLinearThreshold (
		final double dblMarketState,
		final double dblNonDimensionalCost,
		final double dblNonDimensionalCostCross)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblMarketState) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblNonDimensionalCost) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblNonDimensionalCostCross))
			return null;

		try {
			return new NonDimensionalCostSystemic (dblNonDimensionalCost, dblNonDimensionalCost +
				dblNonDimensionalCostCross, dblNonDimensionalCost + 2. * dblNonDimensionalCostCross,
					java.lang.Math.exp (-dblMarketState) * dblNonDimensionalCost);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * NonDimensionalCostSystemic Constructor
	 * 
	 * @param dblRealization The Non Dimensional Cost Value Function Realization
	 * @param dblGradient The Non Dimensional Cost Value Function Gradient to the Systemic Market State
	 * @param dblJacobian The Non Dimensional Cost Value Function Jacobian to the Systemic Market State
	 * @param dblNonDimensionalTradeRate The Non-dimensional Trade Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NonDimensionalCostSystemic (
		final double dblRealization,
		final double dblGradient,
		final double dblJacobian,
		final double dblNonDimensionalTradeRate)
		throws java.lang.Exception
	{
		super (dblRealization, dblNonDimensionalTradeRate);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblGradient = dblGradient) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblJacobian = dblJacobian))
			throw new java.lang.Exception ("NonDimensionalCostSystemic Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Realized Non Dimensional Cost Value Function Gradient to the Systemic Market State
	 * 
	 * @return The Realized Non Dimensional Cost Value Function Gradient to the Systemic Market State
	 */

	public double gradient()
	{
		return _dblGradient;
	}

	/**
	 * Retrieve the Realized Non Dimensional Cost Value Function Jacobian to the Systemic Market State
	 * 
	 * @return The Realized Non Dimensional Cost Value Function Jacobian to the Systemic Market State
	 */

	public double jacobian()
	{
		return _dblJacobian;
	}
}
