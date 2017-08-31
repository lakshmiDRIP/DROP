
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
 * NonDimensionalCostCorrelated contains the Level, the Gradient, and the Jacobian of the HJB Non-dimensional
 *  Cost Value Function to the Individual Correlated Market States. The References are:
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

public class NonDimensionalCostCorrelated extends org.drip.execution.hjb.NonDimensionalCost {
	private double _dblLiquidityGradient = java.lang.Double.NaN;
	private double _dblLiquidityJacobian = java.lang.Double.NaN;
	private double _dblVolatilityGradient = java.lang.Double.NaN;
	private double _dblVolatilityJacobian = java.lang.Double.NaN;
	private double _dblLiquidityVolatilityGradient = java.lang.Double.NaN;

	/**
	 * Generate a Zero Sensitivity Correlated Non-dimensional Cost Instance
	 * 
	 * @return The Zero Sensitivity Correlated Non-dimensional Cost Instance
	 */

	public static final NonDimensionalCostCorrelated Zero()
	{
		try {
			return new NonDimensionalCostCorrelated (0., 0., 0., 0., 0., 0., 0.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * NonDimensionalCostCorrelated Constructor
	 * 
	 * @param dblRealization The Realized Non Dimensional Value
	 * @param dblNonDimensionalTradeRate The Non Dimensional Trade Rate
	 * @param dblLiquidityGradient The Realized Non Dimensional Value Liquidity Gradient
	 * @param dblLiquidityJacobian The Realized Non Dimensional Value Liquidity Jacobian
	 * @param dblVolatilityGradient The Realized Non Dimensional Value Volatility Gradient
	 * @param dblVolatilityJacobian The Realized Non Dimensional Value Volatility Jacobian
	 * @param dblLiquidityVolatilityGradient The Realized Non Dimensional Value Liquidity/Volatility Gradient
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NonDimensionalCostCorrelated (
		final double dblRealization,
		final double dblLiquidityGradient,
		final double dblLiquidityJacobian,
		final double dblVolatilityGradient,
		final double dblVolatilityJacobian,
		final double dblLiquidityVolatilityGradient,
		final double dblNonDimensionalTradeRate)
		throws java.lang.Exception
	{
		super (dblRealization, dblNonDimensionalTradeRate);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblLiquidityGradient = dblLiquidityGradient) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblLiquidityJacobian = dblLiquidityJacobian) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblVolatilityGradient = dblVolatilityGradient) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblVolatilityJacobian =
						dblVolatilityJacobian) || !org.drip.quant.common.NumberUtil.IsValid
							(_dblLiquidityVolatilityGradient = dblLiquidityVolatilityGradient))
			throw new java.lang.Exception ("NonDimensionalCostCorrelated Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Non Dimensional Value Liquidity Gradient
	 * 
	 * @return The Non Dimensional Value Liquidity Gradient
	 */

	public double liquidityGradient()
	{
		return _dblLiquidityGradient;
	}

	/**
	 * Retrieve the Non Dimensional Value Liquidity Jacobian
	 * 
	 * @return The Non Dimensional Value Liquidity Jacobian
	 */

	public double liquidityJacobian()
	{
		return _dblLiquidityJacobian;
	}

	/**
	 * Retrieve the Non Dimensional Value Volatility Gradient
	 * 
	 * @return The Non Dimensional Value Volatility Gradient
	 */

	public double volatilityGradient()
	{
		return _dblVolatilityGradient;
	}

	/**
	 * Retrieve the Non Dimensional Value Volatility Jacobian
	 * 
	 * @return The Non Dimensional Value Volatility Jacobian
	 */

	public double volatilityJacobian()
	{
		return _dblVolatilityJacobian;
	}

	/**
	 * Retrieve the Non Dimensional Value Liquidity/Volatility Gradient
	 * 
	 * @return The Non Dimensional Value Liquidity/Volatility Gradient
	 */

	public double liquidityVolatilityGradient()
	{
		return _dblLiquidityVolatilityGradient;
	}
}
