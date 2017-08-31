
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
 * TransactionSignal holds the Realized Empirical Signals that have been emitted off of a Transaction Run,
 *  decomposed using the Scheme by Almgren, Thum, Hauptmann, and Li (2005), based off of the Parameterization
 *  of Almgren (2003). The References are:
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
 * 	- Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact, Risk 18 (7) 57-62.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class TransactionSignal {
	private double _dblDrift = java.lang.Double.NaN;
	private double _dblIWander = java.lang.Double.NaN;
	private double _dblJWander = java.lang.Double.NaN;

	/**
	 * TransactionSignal Constructor
	 * 
	 * @param dblDrift The Signal Drift
	 * @param dblIWander The "I" Signal Wander
	 * @param dblJWander The "J" Signal Wander
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TransactionSignal (
		final double dblDrift,
		final double dblIWander,
		final double dblJWander)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblDrift = dblDrift) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblIWander = dblIWander) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblJWander = dblJWander))
			throw new java.lang.Exception ("TransactionSignal Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Drift of the Transaction Signal
	 * 
	 * @return The Drift of the Transaction Signal
	 */

	public double drift()
	{
		return _dblDrift;
	}

	/**
	 * Retrieve the "I" Component Wander of the Transaction Signal
	 * 
	 * @return The "I" Component Wander of the Transaction Signal
	 */

	public double iWander()
	{
		return _dblIWander;
	}

	/**
	 * Retrieve the "J" Component Wander of the Transaction Signal
	 * 
	 * @return The "J" Component Wander of the Transaction Signal
	 */

	public double jWander()
	{
		return _dblJWander;
	}
}
