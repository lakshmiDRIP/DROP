
package org.drip.portfolioconstruction.core;

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
 * TransactionChargeMarketImpact contains the Parameters for the Power Law Transaction Charge Scheme.
 *
 * @author Lakshmi Krishnamurthy
 */

public class TransactionChargeMarketImpact extends org.drip.portfolioconstruction.core.TransactionCharge
{
	private double _dblExponent = java.lang.Double.NaN;
	private double _dblCoefficient = java.lang.Double.NaN;

	/**
	 * Construction of the Two-Third's Power Law TransactionChargeMarketImpact Instance
	 * 
	 * @param strName Transaction Charge Name
	 * @param strID Transaction Charge ID
	 * @param strDescription Description of the Transaction Charge
	 * @param dblCoefficient Transaction Charge Coefficient
	 * 
	 * @return The Two-Third's Power Law TransactionChargeMarketImpact Instance
	 */

	public static final TransactionChargeMarketImpact TwoThirdsPowerLaw (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final double dblCoefficient)
	{
		try {
			return new TransactionChargeMarketImpact (
				strName,
				strID,
				strDescription,
				dblCoefficient,
				2. / 3.
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construction of the Three-Fifth's Power Law TransactionChargeMarketImpact Instance
	 * 
	 * @param strName Transaction Charge Name
	 * @param strID Transaction Charge ID
	 * @param strDescription Description of the Transaction Charge
	 * @param dblCoefficient Transaction Charge Coefficient
	 * 
	 * @return The Three-Fifth's Power Law TransactionChargeMarketImpact Instance
	 */

	public static final TransactionChargeMarketImpact ThreeFifthsPowerLaw (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final double dblCoefficient)
	{
		try {
			return new TransactionChargeMarketImpact (
				strName,
				strID,
				strDescription,
				dblCoefficient,
				3. / 5.
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * TransactionChargeMarketImpact Constructor
	 * 
	 * @param strName Transaction Charge Name
	 * @param strID Transaction Charge ID
	 * @param strDescription Description of the Transaction Charge
	 * @param dblCoefficient Transaction Charge Coefficient
	 * @param dblExponent Transaction Charge Exponent
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	 public TransactionChargeMarketImpact (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final double dblCoefficient,
		final double dblExponent)
		throws java.lang.Exception
	{
		super (
			strName,
			strID,
			strDescription
		);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblCoefficient = dblCoefficient) || 0. >
			_dblCoefficient || !org.drip.quant.common.NumberUtil.IsValid (_dblExponent = dblExponent) || 0. >
				_dblExponent)
			throw new java.lang.Exception
				("TransactionChargeMarketImpact Constuctor => Invalid Linear Charge");
	}

	/**
	 * Retrieve the Transaction Charge Coefficient
	 * 
	 * @return The Transaction Charge Coefficient
	 */

	public double coefficient()
	{
		return _dblCoefficient;
	}

	/**
	 * Retrieve the Transaction Charge Exponent
	 * 
	 * @return The Transaction Charge Exponent
	 */

	public double exponent()
	{
		return _dblExponent;
	}

	@Override public double estimate (
		final double dblInitial,
		final double dblFinal)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblInitial) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblFinal))
			throw new java.lang.Exception ("TransactionChargeMarketImpact::estimate => Invalid Inputs");

		return _dblCoefficient * java.lang.Math.pow (java.lang.Math.abs (dblFinal - dblInitial),
			_dblExponent);
	}
}
