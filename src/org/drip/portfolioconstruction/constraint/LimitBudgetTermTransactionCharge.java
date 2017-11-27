
package org.drip.portfolioconstruction.constraint;

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
 * LimitBudgetTermTransactionCharge holds the Details of a After Transaction Charge Limit Budget Constraint
 *  Term.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LimitBudgetTermTransactionCharge extends
	org.drip.portfolioconstruction.constraint.LimitBudgetTerm
{
	private double[] _adblInitialHoldings = null;
	private org.drip.portfolioconstruction.cost.TransactionCharge[] _aTransactionCharge = null;

	/**
	 * LimitBudgetTermTransactionCharge Constructor
	 * 
	 * @param strName Name of the Constraint
	 * @param scope Scope of the Constraint - ACCOUNT/ASSET/SET
	 * @param unit Unit of the Constraint
	 * @param dblBudget Budget Value of the Constraint
	 * @param adblWeight Array of the Exposure Weights
	 * @param adblInitialHoldings Array of Initial Holdings
	 * @param aTransactionCharge Array of Transaction Charge Instances
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LimitBudgetTermTransactionCharge (
		final java.lang.String strName,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double dblBudget,
		final double[] adblWeight,
		final double[] adblInitialHoldings,
		final org.drip.portfolioconstruction.cost.TransactionCharge[] aTransactionCharge)
		throws java.lang.Exception
	{
		super (
			strName,
			"CT_AFTER_TRANSACTION_CHARGE_BUDGET",
			"Constrains the After Transaction Charge Allocation Budget",
			scope,
			unit,
			dblBudget,
			adblWeight
		);

		if (null == (_adblInitialHoldings = adblInitialHoldings) || 0 == _adblInitialHoldings.length ||
			!org.drip.quant.common.NumberUtil.IsValid (_adblInitialHoldings))
			throw new java.lang.Exception ("LimitBudgetTermTransactionCharge Constructor => Invalid Inputs");

		int iNumAsset = _adblInitialHoldings.length;

		if (null == (_aTransactionCharge = aTransactionCharge) || iNumAsset != _aTransactionCharge.length ||
			iNumAsset != adblWeight.length)
			throw new java.lang.Exception ("LimitBudgetTermTransactionCharge Constructor => Invalid Inputs");

		for (int i = 0; i < iNumAsset; ++i)
		{
			if (null == _aTransactionCharge[i])
				throw new java.lang.Exception
					("LimitBudgetTermTransactionCharge Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of Transaction Charges
	 * 
	 * @return The Transaction Charge Array
	 */

	public org.drip.portfolioconstruction.cost.TransactionCharge[] transactionCharge()
	{
		return _aTransactionCharge;
	}

	@Override public org.drip.function.definition.RdToR1 rdtoR1()
	{
		return new org.drip.function.definition.RdToR1 (null)
		{
			@Override public int dimension()
			{
				return weight().length;
			}

			@Override public double evaluate (
				final double[] adblVariate)
				throws java.lang.Exception
			{
				double[] adblWeight = weight();

				double dblConstraintValue = 0.;
				int iNumAsset = adblWeight.length;

				if (null == adblVariate || !org.drip.quant.common.NumberUtil.IsValid (adblVariate) ||
					adblVariate.length != iNumAsset)
					throw new java.lang.Exception
						("LimitBudgetTermTransactionCharge::rdToR1::evaluate => Invalid Variate Dimension");

				for (int i = 0; i < iNumAsset; ++i) {
					dblConstraintValue += (adblWeight[i] * adblVariate[i] -
						_aTransactionCharge[i].estimate (
							_adblInitialHoldings[i],
							adblVariate[i]
						)
					);
				}

				return dblConstraintValue;
			}
		};
	}
}

