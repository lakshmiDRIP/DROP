
package org.drip.portfolioconstruction.optimizer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * Rebalancer holds the Details of a given Rebalancing Run.
 *
 * @author Lakshmi Krishnamurthy
 */

public class Rebalancer extends org.drip.portfolioconstruction.core.Block
{
	private org.drip.portfolioconstruction.core.Account _account = null;
	private org.drip.portfolioconstruction.optimizer.Strategy _strategy = null;

	/**
	 * Rebalancer Constructor
	 * 
	 * @param strName The Rebalancer Name
	 * @param strID The Rebalancer ID
	 * @param strDescription The Rebalancer Description
	 * @param account The Account to Rebalance
	 * @param strategy The Strategy to use for the Rebalancing
	 * 
	 * @throws java.lang.Exception Thrown if gthe Inputs are Invalid
	 */

	public Rebalancer (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final org.drip.portfolioconstruction.core.Account account,
		final org.drip.portfolioconstruction.optimizer.Strategy strategy)
		throws java.lang.Exception
	{
		super (strName, strID, strDescription);

		if (null == (_account = account) || null == (_strategy = strategy))
			throw new java.lang.Exception ("Rebalancer Construtor => Invalid Inputs");
	}

	/**
	 * Retrieve the Account Instance
	 * 
	 * @return The Account Instance
	 */

	public org.drip.portfolioconstruction.core.Account account()
	{
		return _account;
	}

	/**
	 * Retrieve the Strategy Instance
	 * 
	 * @return The Strategy Instance
	 */

	public org.drip.portfolioconstruction.optimizer.Strategy strategy()
	{
		return _strategy;
	}

	/**
	 * Conduct an Optimization Run to Generate the Rebalancer Analytics
	 * 
	 * @return The Rebalancer Analytics
	 */

	public org.drip.portfolioconstruction.optimizer.RebalancerAnalytics optimize()
	{
		return null;
	}
}
