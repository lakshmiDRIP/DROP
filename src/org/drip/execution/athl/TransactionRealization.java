
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
 * TransactionRealization holds the Suite of Empirical Drift/Wander Signals that have been emitted off of a
 *  Transaction Run using the Scheme by Almgren, Thum, Hauptmann, and Li (2005), using the Parameterization
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

public class TransactionRealization {
	private double _dblT = java.lang.Double.NaN;
	private double _dblX = java.lang.Double.NaN;
	private double _dblTPost = java.lang.Double.NaN;
	private double _dblTSQRT = java.lang.Double.NaN;
	private double _dblVolatility = java.lang.Double.NaN;
	private org.drip.execution.impact.TransactionFunction _tfPermanent = null;
	private org.drip.execution.impact.TransactionFunction _tfTemporary = null;

	/**
	 * TransactionRealization Constructor
	 * 
	 * @param tfPermanent The Permanent Market Impact Transaction Function
	 * @param tfTemporary The Temporary Market Impact Transaction Function
	 * @param dblVolatility The Asset Daily Volatility
	 * @param dblX The Transaction Amount
	 * @param dblT The Transaction Completion Time in Days
	 * @param dblTPost The Transaction Completion Time in Days Adjusted for the Permanent Lag
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TransactionRealization (
		final org.drip.execution.impact.TransactionFunction tfPermanent,
		final org.drip.execution.impact.TransactionFunction tfTemporary,
		final double dblVolatility,
		final double dblX,
		final double dblT,
		final double dblTPost)
		throws java.lang.Exception
	{
		if (null == (_tfPermanent = tfPermanent) || null == (_tfTemporary = tfTemporary) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblVolatility = dblVolatility) || 0. > _dblVolatility
				|| !org.drip.quant.common.NumberUtil.IsValid (_dblX = dblX) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblT = dblT) || 0. > _dblT ||
						!org.drip.quant.common.NumberUtil.IsValid (_dblTPost = dblTPost) || _dblT >=
							_dblTPost)
			throw new java.lang.Exception  ("TransactionRealization Constructor => Invalid Inputs");

		_dblTSQRT = java.lang.Math.sqrt (_dblT);
	}

	/**
	 * Retrieve the Permanent Market Impact Transaction Function
	 * 
	 * @return The Permanent Market Impact Transaction Function
	 */

	public org.drip.execution.impact.TransactionFunction permanentMarketImpactFunction()
	{
		return _tfPermanent;
	}

	/**
	 * Retrieve the Temporary Market Impact Transaction Function
	 * 
	 * @return The Temporary Market Impact Transaction Function
	 */

	public org.drip.execution.impact.TransactionFunction temporaryMarketImpactFunction()
	{
		return _tfTemporary;
	}

	/**
	 * Retrieve the Asset Daily Volatility
	 * 
	 * @return The Asset Daily Volatility
	 */

	public double volatility()
	{
		return _dblVolatility;
	}

	/**
	 * Retrieve the Transaction Amount X
	 * 
	 * @return The Transaction Amount X
	 */

	public double x()
	{
		return _dblX;
	}

	/**
	 * Retrieve the Transaction Completion Time T in Days
	 * 
	 * @return The Transaction Completion Time T in Days
	 */

	public double t()
	{
		return _dblT;
	}

	/**
	 * Retrieve the Transaction Completion Time in Days Adjusted for the Permanent Lag TPost
	 * 
	 * @return The Transaction Completion Time in Days Adjusted for the Permanent Lag TPost
	 */

	public double tPost()
	{
		return _dblTPost;
	}

	/**
	 * Emit the IJK Signal
	 * 
	 * @param dblIRandom The Random "I" Instance
	 * @param dblJRandom The Random "J" Instance
	 * 
	 * @return The IJK Signal Instance
	 */

	public org.drip.execution.athl.IJK emitSignal (
		final double dblIRandom,
		final double dblJRandom)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblIRandom) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblJRandom))
			return null;

		try {
			return new org.drip.execution.athl.IJK (new org.drip.execution.athl.TransactionSignal
				(_tfPermanent.evaluate (_dblX, _dblT), _dblVolatility * _dblTSQRT * dblIRandom, 0.), new
					org.drip.execution.athl.TransactionSignal (_tfTemporary.evaluate (_dblX, _dblT),
						_dblVolatility * java.lang.Math.sqrt (_dblT / 12. * (4. - (3. * _dblT / _dblTPost)))
							* dblJRandom, 0.5 * (_dblTPost - _dblT) / _dblTSQRT * dblIRandom));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
