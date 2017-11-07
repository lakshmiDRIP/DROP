
package org.drip.analytics.cashflow;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * ComposableUnitFixedPeriod represents the Fixed Cash Flow Periods' Composable Period Details. Currently it
 *  holds the Accrual Start Date, the Accrual End Date, the Fixed Coupon, the Basis Spread, the Coupon Rate,
 *  and the Accrual Day Counts, as well as the EOM Adjustment Flags.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ComposableUnitFixedPeriod extends org.drip.analytics.cashflow.ComposableUnitPeriod {
	private org.drip.param.period.ComposableFixedUnitSetting _cfus = null;

	/**
	 * The ComposableUnitFixedPeriod constructor
	 * 
	 * @param iStartDate Accrual Start Date
	 * @param iEndDate Accrual End Date
	 * @param ucas Unit Coupon/Accrual Setting
	 * @param cfus Composable Unit Fixed Setting
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ComposableUnitFixedPeriod (
		final int iStartDate,
		final int iEndDate,
		final org.drip.param.period.UnitCouponAccrualSetting ucas,
		final org.drip.param.period.ComposableFixedUnitSetting cfus)
		throws java.lang.Exception
	{
		super (
			iStartDate,
			iEndDate,
			cfus.tenor(),
			ucas
		);

		if (null == (_cfus = cfus))
			throw new java.lang.Exception ("ComposableUnitFixedPeriod Constructor => Invalid Inputs");
	}

	@Override public double baseRate (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		return _cfus.fixedCoupon();
	}

	@Override public double basis()
	{
		return _cfus.basis();
	}

	@Override public java.lang.String couponCurrency()
	{
		return _cfus.couponCurrency();
	}
}
