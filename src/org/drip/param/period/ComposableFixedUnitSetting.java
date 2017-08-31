
package org.drip.param.period;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * ComposableFixedUnitSetting contains the fixed unit details. Currently it holds the coupon currency, the
 * 	fixed coupon, and the basis.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ComposableFixedUnitSetting extends org.drip.param.period.ComposableUnitBuilderSetting {
	private double _dblBasis = java.lang.Double.NaN;
	private java.lang.String _strCouponCurrency = "";
	private double _dblFixedCoupon = java.lang.Double.NaN;

	/**
	 * ComposableFixedUnitSetting constructor
	 * 
	 * @param strTenor Unit Tenor
	 * @param iEdgeDateSequenceScheme Edge Date Generation Scheme
	 * @param dapEdge Date Adjust Parameter Settings for the Edge Dates
	 * @param dblFixedCoupon Fixed Coupon (Annualized)
	 * @param dblBasis Basis over the Fixed Coupon in the same units
	 * @param strCouponCurrency Coupon Currency
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public ComposableFixedUnitSetting (
		final java.lang.String strTenor,
		final int iEdgeDateSequenceScheme,
		final org.drip.analytics.daycount.DateAdjustParams dapEdge,
		final double dblFixedCoupon,
		final double dblBasis,
		final java.lang.String strCouponCurrency)
		throws java.lang.Exception
	{
		super (strTenor, iEdgeDateSequenceScheme, dapEdge);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblFixedCoupon = dblFixedCoupon) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblBasis = dblBasis) || null ==
				(_strCouponCurrency = strCouponCurrency) || _strCouponCurrency.isEmpty())
			throw new java.lang.Exception ("ComposableFixedUnitSetting ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Fixed Coupon
	 * 
	 * @return The Fixed Coupon
	 */

	public double fixedCoupon()
	{
		return _dblFixedCoupon;
	}

	/**
	 * Retrieve the Fixed Coupon Basis
	 * 
	 * @return The Fixed Coupon Basis
	 */

	public double basis()
	{
		return _dblBasis;
	}

	/**
	 * Retrieve the Fixed Coupon Currency
	 * 
	 * @return The Fixed Coupon Currency
	 */

	public java.lang.String couponCurrency()
	{
		return _strCouponCurrency;
	}
}
