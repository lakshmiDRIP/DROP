
package org.drip.execution.tradingtime;

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
 * CoordinatedVariation implements the Coordinated Variation of the Volatility and Liquidity as described in
 * 	the "Trading Time" Model. The References are:
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

public class CoordinatedVariation {
	private double _dblInvariant = java.lang.Double.NaN;
	private double _dblReferenceLiquidity = java.lang.Double.NaN;
	private double _dblReferenceVolatility = java.lang.Double.NaN;

	/**
	 * CoordinatedVariation Constructor
	 * 
	 * @param dblReferenceVolatility The Reference Volatility
	 * @param dblReferenceLiquidity The Reference Liquidity
	 * 
	 * @throws java.lang.Exception Thrwon if the Inputs are Invalid
	 */

	public CoordinatedVariation (
		final double dblReferenceVolatility,
		final double dblReferenceLiquidity)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblReferenceVolatility = dblReferenceVolatility) ||
			0. >= _dblReferenceVolatility || !org.drip.quant.common.NumberUtil.IsValid
				(_dblReferenceLiquidity = dblReferenceLiquidity) || 0. >= _dblReferenceLiquidity)
			throw new java.lang.Exception ("CoordinatedVariation Constructor => Invalid Inputs");

		_dblInvariant = _dblReferenceVolatility * _dblReferenceVolatility * _dblReferenceLiquidity;
	}

	/**
	 * Retrieve the Reference Liquidity
	 * 
	 * @return The Reference Liquidity
	 */

	public double referenceLiquidity()
	{
		return _dblReferenceLiquidity;
	}

	/**
	 * Retrieve the Reference Volatility
	 * 
	 * @return The Reference Volatility
	 */

	public double referenceVolatility()
	{
		return _dblReferenceVolatility;
	}

	/**
	 * Retrieve the Volatility/Liquidity Invariant
	 * 
	 * @return The Volatility/Liquidity Invariant
	 */

	public double invariant()
	{
		return _dblInvariant;
	}

	/**
	 * Estimate the Liquidity given the Volatility
	 * 
	 * @param dblVolatility The Volatility
	 * 
	 * @return Liquidity Estimate using the Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double liquidity (
		final double dblVolatility)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVolatility))
			throw new java.lang.Exception ("CoordinatedVariation::liquidity => Invalid Inputs");

		return _dblInvariant / (dblVolatility * dblVolatility);
	}

	/**
	 * Estimate the Volatility given the Liquidity
	 * 
	 * @param dblLiquidity The Liquidity
	 * 
	 * @return Volatility Estimate using the Liquidity
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double volatility (
		final double dblLiquidity)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblLiquidity))
			throw new java.lang.Exception ("CoordinatedVariation::volatility => Invalid Inputs");

		return java.lang.Math.sqrt (_dblInvariant / dblLiquidity);
	}

	/**
	 * Compute the Volatility Function from the Liquidity Function
	 * 
	 * @param r1ToR1Liquidity The R^1 To R^1 Liquidity Function
	 * 
	 * @return The R^1 To R^1 Volatility Function
	 */

	public org.drip.function.definition.R1ToR1 volatilityFunction (
		final org.drip.function.definition.R1ToR1 r1ToR1Liquidity)
	{
		if (null == r1ToR1Liquidity) return null;

		return new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				return java.lang.Math.sqrt (_dblInvariant / r1ToR1Liquidity.evaluate (dblTime));
			}
		};
	}
}
