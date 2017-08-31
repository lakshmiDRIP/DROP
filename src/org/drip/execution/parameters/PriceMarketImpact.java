
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
 * PriceMarketImpact contains the Price Market Impact Inputs used in the Construction of the Impact
 *  Parameters for the Almgren and Chriss (2000) Optimal Trajectory Generation Scheme. The References are:
 * 
 * 	- Almgren, R., and N. Chriss (1999): Value under Liquidation, Risk 12 (12).
 * 
 * 	- Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3 (2)
 * 		5-39.
 * 
 * 	- Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs, Journal of Financial Markets,
 * 		1, 1-50.
 *
 * 	- Chan, L. K. C., and J. Lakonishak (1995): The Behavior of Stock Prices around Institutional Trades,
 * 		Journal of Finance, 50, 1147-1174.
 *
 * 	- Keim, D. B., and A. Madhavan (1997): Transaction Costs and Investment Style: An Inter-exchange
 * 		Analysis of Institutional Equity Trades, Journal of Financial Economics, 46, 265-292.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class PriceMarketImpact {
	private double _dblPermanentImpactFactor = java.lang.Double.NaN;
	private double _dblTemporaryImpactFactor = java.lang.Double.NaN;
	private org.drip.execution.parameters.AssetTransactionSettings _ats = null;

	protected PriceMarketImpact (
		final org.drip.execution.parameters.AssetTransactionSettings ats,
		final double dblPermanentImpactFactor,
		final double dblTemporaryImpactFactor)
		throws java.lang.Exception
	{
		if (null == (_ats = ats) || !org.drip.quant.common.NumberUtil.IsValid (_dblPermanentImpactFactor =
			dblPermanentImpactFactor) || 0. > _dblPermanentImpactFactor ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblTemporaryImpactFactor =
					dblTemporaryImpactFactor) || 0. >= _dblTemporaryImpactFactor)
			throw new java.lang.Exception ("PriceMarketImpact Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the AssetTransactionSettings Instance
	 * 
	 * @return The AssetTransactionSettings Instance
	 */

	public org.drip.execution.parameters.AssetTransactionSettings ats()
	{
		return _ats;
	}

	/**
	 * Retrieve the Fraction of the Daily Volume that triggers One Bid-Ask of Permanent Impact Cost
	 * 
	 * @return The Fraction of the Daily Volume that triggers One Bid-Ask of Permanent Impact Cost
	 */

	public double permanentImpactFactor()
	{
		return _dblPermanentImpactFactor;
	}

	/**
	 * Retrieve the Fraction of the Daily Volume that triggers One Bid-Ask of Temporary Impact Cost
	 * 
	 * @return The Fraction of the Daily Volume that triggers One Bid-Ask of Temporary Impact Cost
	 */

	public double temporaryImpactFactor()
	{
		return _dblTemporaryImpactFactor;
	}

	/**
	 * Generate the Permanent Impact Transaction Function
	 * 
	 * @return The Permanent Impact Transaction Function
	 */

	abstract public org.drip.execution.impact.TransactionFunction permanentTransactionFunction();

	/**
	 * Generate the Temporary Impact Transaction Function
	 * 
	 * @return The Temporary Impact Transaction Function
	 */

	abstract public org.drip.execution.impact.TransactionFunction temporaryTransactionFunction();
}
