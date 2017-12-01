
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
 * LimitChargeTermIssuer constrains the Limit Issuer Transaction Charge Term.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LimitChargeTermIssuer extends org.drip.portfolioconstruction.optimizer.ConstraintTerm
{

	/**
	 * Construct a Static Instance of LimitChargeTermIssuer
	 * 
	 * @param strName Name of the LimitChargeTermIssuer Constraint
	 * @param scope Scope of the LimitChargeTermIssuer Constraint
	 * @param unit Unit of the LimitChargeTermIssuer Constraint
	 * @param dblMinimum Minimum Value for the LimitChargeTermIssuer Constraint
	 * @param dblMaximum Maximum Value for the LimitChargeTermIssuer Constraint
	 * @param adblInitialHoldings Array of Initial Holdings
	 * @param aTransactionCharge Array of Transaction Charge
	 * 
	 * @return Instance of LimitChargeTermIssuer
	 */

	public static final LimitChargeTermIssuer Standard (
		final java.lang.String strName,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double dblMinimum,
		final double dblMaximum,
		final double[] adblInitialHoldings,
		final org.drip.portfolioconstruction.cost.TransactionCharge[] aTransactionCharge)
	{
		try {
			return new LimitChargeTermIssuer (
				strName,
				"CT_LIMIT_TRANSACTION_CHARGE",
				"Constrains the Total Transaction Charge",
				scope,
				unit,
				dblMinimum,
				dblMaximum,
				adblInitialHoldings,
				aTransactionCharge);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Static Instance of GoldmanSachsShortfall LimitChargeTermIssuer
	 * 
	 * @param strName Name of the LimitChargeTermIssuer Constraint
	 * @param scope Scope of the LimitChargeTermIssuer Constraint
	 * @param unit Unit of the LimitChargeTermIssuer Constraint
	 * @param dblMinimum Minimum Value for the LimitChargeTermIssuer Constraint
	 * @param dblMaximum Maximum Value for the LimitChargeTermIssuer Constraint
	 * @param adblInitialHoldings Array of Initial Holdings
	 * @param aTransactionChargeGSS Array of GoldmanSachsShortfall Transaction Charge
	 * 
	 * @return Instance of GoldmanSachsShortfall LimitChargeTermIssuer
	 */

	public static final LimitChargeTermIssuer GoldmanSachsShortfall (
		final java.lang.String strName,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double dblMinimum,
		final double dblMaximum,
		final double[] adblInitialHoldings,
		final org.drip.portfolioconstruction.cost.TransactionChargeGoldmanSachsShortfall[]
			aTransactionChargeGSS)
	{
		try {
			return new LimitChargeTermIssuer (
				strName,
				"CT_LIMIT_GOLDMAN_SACHS_SHORTFALL",
				"Constrains the Total Transaction Charge using the Goldman Sachs Shortfall Model",
				scope,
				unit,
				dblMinimum,
				dblMaximum,
				adblInitialHoldings,
				aTransactionChargeGSS);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private double[] _adblInitialHoldings = null;
	private org.drip.portfolioconstruction.cost.TransactionCharge[] _aTransactionCharge = null;

	/**
	 * LimitChargeTermIssuer Constructor
	 * 
	 * @param strName Name of the LimitChargeTermIssuer Constraint
	 * @param strID ID of the LimitChargeTermIssuer Constraint
	 * @param strDescription Description of the LimitChargeTermIssuer Constraint
	 * @param scope Scope of the LimitChargeTermIssuer Constraint
	 * @param unit Unit of the LimitChargeTermIssuer Constraint
	 * @param dblMinimum Minimum Value for the LimitChargeTermIssuer Constraint
	 * @param dblMaximum Maximum Value for the LimitChargeTermIssuer Constraint
	 * @param adblInitialHoldings Array of Initial Holdings
	 * @param aTransactionCharge Array of Transaction Charge
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LimitChargeTermIssuer (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double dblMinimum,
		final double dblMaximum,
		final double[] adblInitialHoldings,
		final org.drip.portfolioconstruction.cost.TransactionCharge[] aTransactionCharge)
		throws java.lang.Exception
	{
		super (
			strName,
			strID,
			strDescription,
			"LIMIT_TRANSACTION_CHARGE",
			scope,
			unit,
			dblMinimum,
			dblMaximum
		);

		if (null == (_adblInitialHoldings = adblInitialHoldings) ||
			null == (_aTransactionCharge = aTransactionCharge))
			throw new java.lang.Exception ("LimitChargeTermIssuer Constructor => Invalid Inputs");

		int iNumAsset = _adblInitialHoldings.length;

		if (0 == iNumAsset || iNumAsset != _aTransactionCharge.length)
			throw new java.lang.Exception ("LimitChargeTermIssuer Constructor => Invalid Inputs");

		for (int i = 0; i < iNumAsset; ++i)
		{
			if (!org.drip.quant.common.NumberUtil.IsValid (_adblInitialHoldings[i]) ||
				null == _aTransactionCharge[i])
				throw new java.lang.Exception ("LimitChargeTermIssuer Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of Initial Holdings
	 * 
	 * @return The Initial Holdings Array
	 */

	public double[] initialHoldings()
	{
		return _adblInitialHoldings;
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
				return initialHoldings().length;
			}

			@Override public double evaluate (
				final double[] adblFinalHoldings)
				throws java.lang.Exception
			{
				double dblConstraintValue = 0.;
				int iNumAsset = _adblInitialHoldings.length;

				if (null == adblFinalHoldings || !org.drip.quant.common.NumberUtil.IsValid
					(adblFinalHoldings) || adblFinalHoldings.length != iNumAsset)
					throw new java.lang.Exception
						("LimitChargeTermIssuer::rdToR1::evaluate => Invalid Variate Dimension");

				for (int i = 0; i < iNumAsset; ++i) {
					dblConstraintValue +=
						_aTransactionCharge[i].estimate (
							_adblInitialHoldings[i],
							adblFinalHoldings[i]
					);
				}

				return dblConstraintValue;
			}
		};
	}
}
