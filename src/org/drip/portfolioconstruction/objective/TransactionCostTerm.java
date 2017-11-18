
package org.drip.portfolioconstruction.objective;

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
 * TransactionCostTerm implements the Objective Term that models the Cost associated with a Portfolio
 *  Transaction.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class TransactionCostTerm extends org.drip.portfolioconstruction.optimizer.ObjectiveTerm
{
	private org.drip.portfolioconstruction.core.TransactionCost[] _aTransactionCost = null;

	protected TransactionCostTerm (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final double[] adblInitialHoldings,
		final org.drip.portfolioconstruction.core.TransactionCost[] aTransactionCost)
		throws java.lang.Exception
	{
		super (
			strName,
			strID,
			strDescription,
			"TRANSACTION_COST",
			adblInitialHoldings
		);

		int iNumAsset = adblInitialHoldings.length;

		if (null == (_aTransactionCost = aTransactionCost) || iNumAsset != _aTransactionCost.length)
			throw new java.lang.Exception ("TransactionCostTerm Constructor => Invalid Inputs");

		for (int i = 0; i < iNumAsset; ++i)
		{
			if (null == _aTransactionCost[i])
				throw new java.lang.Exception ("TransactionCostTerm Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of Transaction Costs
	 * 
	 * @return The Transaction Cost Array
	 */

	public org.drip.portfolioconstruction.core.TransactionCost[] transactionCost()
	{
		return _aTransactionCost;
	}
}
