
package org.drip.product.params;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * CreditSetting contains the credit related valuation parameters - use default pay lag, use curve or the
 *  component recovery, component recovery, credit curve name, and whether there is accrual on default. It
 *  exports serialization into and de-serialization out of byte arrays.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CreditSetting implements org.drip.product.params.Validatable {
	private int _iLossPayLag = -1;
	private boolean _bUseCurveRecovery = true;
	private boolean _bAccrualOnDefault = false;
	private java.lang.String _strCreditCurveName = "";
	private double _dblRecovery = java.lang.Double.NaN;

	/**
	 * Construct the CreditSetting from the default pay lag, use curve or the component recovery flag,
	 *  component recovery, credit curve name, and whether there is accrual on default
	 * 
	 * @param iLossPayLag Loss Pay Lag
	 * @param dblRecovery Component Recovery
	 * @param bUseCurveRecovery Use the Curve Recovery (True) or Component Recovery (False)
	 * @param strCreditCurveName Credit curve name
	 * @param bAccrualOnDefault Accrual paid on default (True) 
	 */

	public CreditSetting (
		final int iLossPayLag,
		final double dblRecovery,
		final boolean bUseCurveRecovery,
		final java.lang.String strCreditCurveName,
		final boolean bAccrualOnDefault)
	{
		_iLossPayLag = iLossPayLag;
		_dblRecovery = dblRecovery;
		_bAccrualOnDefault = bAccrualOnDefault;
		_bUseCurveRecovery = bUseCurveRecovery;
		_strCreditCurveName = strCreditCurveName;
	}

	@Override public boolean validate()
	{
		if (null == _strCreditCurveName || _strCreditCurveName.isEmpty()) return true;

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblRecovery) && !_bUseCurveRecovery) return false;

		return true;
	}

	/**
	 * Retrieve the Loss Pay-out Lag
	 * 
	 * @return The Loss Pay-out Lag
	 */

	public int lossPayLag()
	{
		return _iLossPayLag;
	}

	/**
	 * Flag indicating whether or nor to use the Curve Recovery
	 * 
	 * @return TRUE - Use the Recovery From the Credit Curve
	 */

	public boolean useCurveRecovery()
	{
		return _bUseCurveRecovery;
	}

	/**
	 * Retrieve the Credit Curve Name
	 * 
	 * @return The Credit Curve Name
	 */

	public java.lang.String creditCurveName()
	{
		return _strCreditCurveName;
	}

	/**
	 * Retrieve the Accrual On Default Flag
	 * 
	 * @return TRUE - Accrual On Default
	 */

	public boolean accrualOnDefault()
	{
		return _bAccrualOnDefault;
	}

	/**
	 * Retrieve the Recovery Amount
	 * 
	 * @return The Recovery Amount
	 */

	public double recovery()
	{
		return _dblRecovery;
	}
}
