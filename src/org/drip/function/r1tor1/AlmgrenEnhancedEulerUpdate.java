
package org.drip.function.r1tor1;

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
 * AlmgrenEnhancedEulerUpdate is a R^1 To R^1 Function that is used in Almgren (2009, 2012) to illustrate the
 * 	Construction of the Enhanced Euler Update Scheme. The References are:
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
 * @author Lakshmi Krishnamurthy
 */

public class AlmgrenEnhancedEulerUpdate extends org.drip.function.definition.R1ToR1 {
	private double _dblA = java.lang.Double.NaN;
	private double _dblB = java.lang.Double.NaN;

	/**
	 * AlmgrenEnhancedEulerUpdate Constructor
	 * 
	 * @param dblA The "A" Parameter
	 * @param dblB The "B" Parameter
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AlmgrenEnhancedEulerUpdate (
		final double dblA,
		final double dblB)
		throws java.lang.Exception
	{
		super (null);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblA = dblA) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblB = dblB) || _dblA == _dblB)
			throw new java.lang.Exception ("AlmgrenEnhancedEulerUpdate Constructor => Inbalid Inputs");
	}

	/**
	 * Retrieve the "A" Parameter
	 * 
	 * @return The "A" Parameter
	 */

	public double a()
	{
		return _dblA;
	}

	/**
	 * Retrieve the "B" Parameter
	 * 
	 * @return The "B" Parameter
	 */

	public double b()
	{
		return _dblB;
	}

	@Override public double evaluate (
		final double dblT)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblT))
			throw new java.lang.Exception ("AlmgrenEnhancedEulerUpdate::evaluate => Invalid Inputs");

		double dblInvExpAT = java.lang.Math.exp (-1. * _dblA * dblT);

		double dblInvExpBT = java.lang.Math.exp (-1. * _dblB * dblT);

		return (_dblA * dblInvExpBT - _dblB * dblInvExpAT) / (dblInvExpBT - dblInvExpAT);
	}
}
