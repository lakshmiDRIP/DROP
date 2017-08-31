
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
 * OptimalMeasureDependence contains the Dependence Exponents on Liquidity, Trade Size, and Permanent Impact
 * 	Adjusted Principal Discount for the Optimal Principal Horizon and the Optional Information Ratio. It also
 *  holds the Constant . The References are:
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

public class OptimalMeasureDependence {
	private double _dblConstant = java.lang.Double.NaN;
	private double _dblBlockSizeExponent = java.lang.Double.NaN;
	private double _dblLiquidityExponent = java.lang.Double.NaN;
	private double _dblVolatilityExponent = java.lang.Double.NaN;
	private double _dblAdjustedPrincipalDiscountExponent = java.lang.Double.NaN;

	/**
	 * OptimalMeasureDependence Constructor
	 * 
	 * @param dblConstant The Optimal Measure Constant
	 * @param dblLiquidityExponent The Optimal Measure Liquidity Exponent
	 * @param dblBlockSizeExponent The Optimal Measure Block Size Exponent
	 * @param dblVolatilityExponent The Optimal Measure Volatility Exponent
	 * @param dblAdjustedPrincipalDiscountExponent The Optimal Measure Adjusted Principal Discount Exponent
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public OptimalMeasureDependence (
		final double dblConstant,
		final double dblLiquidityExponent,
		final double dblBlockSizeExponent,
		final double dblVolatilityExponent,
		final double dblAdjustedPrincipalDiscountExponent)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblConstant = dblConstant) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblLiquidityExponent = dblLiquidityExponent) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblBlockSizeExponent = dblBlockSizeExponent) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblVolatilityExponent =
						dblVolatilityExponent) || !org.drip.quant.common.NumberUtil.IsValid
							(_dblAdjustedPrincipalDiscountExponent = dblAdjustedPrincipalDiscountExponent))
			throw new java.lang.Exception ("OptimalMeasureDependence Constructor => Invalid Inputs!");
	}

	/**
	 * Retrieve the Constant
	 * 
	 * @return The Constant
	 */

	public double constant()
	{
		return _dblConstant;
	}

	/**
	 * Retrieve the Block Size Dependence Exponent
	 * 
	 * @return The Block Size Dependence Exponent
	 */

	public double blockSizeExponent()
	{
		return _dblBlockSizeExponent;
	}

	/**
	 * Retrieve the Liquidity Dependence Exponent
	 * 
	 * @return The Liquidity Dependence Exponent
	 */

	public double liquidityExponent()
	{
		return _dblLiquidityExponent;
	}

	/**
	 * Retrieve the Volatility Dependence Exponent
	 * 
	 * @return The Volatility Dependence Exponent
	 */

	public double volatilityExponent()
	{
		return _dblVolatilityExponent;
	}

	/**
	 * Retrieve the Adjusted Principal Discount Dependence Exponent
	 * 
	 * @return The Adjusted Principal Discount Dependence Exponent
	 */

	public double adjustedPrincipalDiscountExponent()
	{
		return _dblAdjustedPrincipalDiscountExponent;
	}
}
