
package org.drip.market.exchange;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * DeliverableSwapFutures contains the details of the exchange-traded Deliverable Swap Futures Contracts.
 *
 * @author Lakshmi Krishnamurthy
 */

public class DeliverableSwapFutures {
	private java.lang.String _strTenor = "";
	private java.lang.String _strCurrency = "";
	private double _dblNominal = java.lang.Double.NaN;
	private double _dblRateIncrement = java.lang.Double.NaN;
	private org.drip.product.params.LastTradingDateSetting _ltds = null;

	/**
	 * DeliverableSwapFutures constructor
	 * 
	 * @param strCurrency Currency
	 * @param strTenor Tenor
	 * @param dblNominal Nominal
	 * @param dblRateIncrement Rate Increment
	 * @param ltds Late Trading Date Setting
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DeliverableSwapFutures (
		final java.lang.String strCurrency,
		final java.lang.String strTenor,
		final double dblNominal,
		final double dblRateIncrement,
		final org.drip.product.params.LastTradingDateSetting ltds)
		throws java.lang.Exception
	{
		if (null == (_strCurrency = strCurrency) || _strCurrency.isEmpty() || null == (_strTenor = strTenor)
			|| _strTenor.isEmpty() || !org.drip.quant.common.NumberUtil.IsValid (_dblNominal = dblNominal) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblRateIncrement = dblRateIncrement))
			throw new java.lang.Exception ("DeliverableSwapFutures ctr: Invalid Inputs");

		_ltds = ltds;
	}

	/**
	 * Retrieve the Currency
	 * 
	 * @return The Currency
	 */

	public java.lang.String currency()
	{
		return _strCurrency;
	}

	/**
	 * Retrieve the Tenor
	 * 
	 * @return The Tenor
	 */

	public java.lang.String tenor()
	{
		return _strTenor;
	}

	/**
	 * Retrieve the Nominal
	 * 
	 * @return The Nominal
	 */

	public double nominal()
	{
		return _dblNominal;
	}

	/**
	 * Retrieve the Rate Increment
	 * 
	 * @return The Rate Increment
	 */

	public double rateIncrement()
	{
		return _dblRateIncrement;
	}

	/**
	 * Retrieve the Last Trading Date Setting
	 * 
	 * @return The Last Trading Date Setting
	 */

	public org.drip.product.params.LastTradingDateSetting ltds()
	{
		return _ltds;
	}

	/**
	 * Create an Instance of the Deliverable Swaps Futures
	 * 
	 * @param dtSpot Spot Date
	 * @param dblFixedCoupon Fixed Coupon
	 * 
	 * @return Instance of the Deliverable Swaps Futures
	 */

	public org.drip.product.rates.FixFloatComponent Create (
		final org.drip.analytics.date.JulianDate dtSpot,
		final double dblFixedCoupon)
	{
		org.drip.market.otc.FixedFloatSwapConvention ffConv =
			org.drip.market.otc.IBORFixedFloatContainer.ConventionFromJurisdictionMaturity (_strCurrency,
				_strTenor);

		return null == ffConv ? null : ffConv.createFixFloatComponent (dtSpot, _strTenor, dblFixedCoupon, 0.,
			_dblNominal);
	}
}
