
package org.drip.product.calib;

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
 * StreamQuoteSet extends the ProductQuoteSet by implementing the Calibration Parameters for the Universal
 *  Stream.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class StreamQuoteSet {
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapQuote = new
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

	/**
	 * Empty StreamQuoteSet Constructor
	 */

	public StreamQuoteSet()
	{
	}

	/**
	 * Set the PV
	 * 
	 * @param dblPV The PV
	 * 
	 * @return TRUE - PV successfully set
	 */

	public boolean setPV (
		final double dblPV)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPV)) return false;

		_mapQuote.put ("PV", dblPV);

		return true;
	}

	/**
	 * Indicate if the PV Field exists
	 * 
	 * @return TRUE - PV Field Exists
	 */

	public boolean containsPV()
	{
		return _mapQuote.containsKey ("PV");
	}

	/**
	 * Retrieve the PV
	 * 
	 * @return The PV
	 * 
	 * @throws java.lang.Exception Thrown if the PV Field does not exist
	 */

	public double pv()
		throws java.lang.Exception
	{
		if (!containsPV()) throw new java.lang.Exception ("StreamQuoteSet::pv - Does not contain PV");

		return _mapQuote.get ("PV");
	}

	/**
	 * Set the Coupon/Spread
	 * 
	 * @param dblCouponSpread The Coupon/Spread
	 * 
	 * @return TRUE - The Coupon/Spread successfully set
	 */

	public boolean setCouponSpread (
		final double dblCouponSpread)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblCouponSpread)) return false;

		_mapQuote.put ("CouponSpread", dblCouponSpread);

		return true;
	}

	/**
	 * Indicate if the Coupon/Spread Field exists
	 * 
	 * @return TRUE - Coupon/Spread Field Exists
	 */

	public boolean containsCouponSpread()
	{
		return _mapQuote.containsKey ("CouponSpread");
	}

	/**
	 * Retrieve the Coupon/Spread
	 * 
	 * @return The Coupon/Spread
	 * 
	 * @throws java.lang.Exception Thrown if the Coupon/Spread Field does not exist
	 */

	public double couponSpread()
		throws java.lang.Exception
	{
		if (!containsCouponSpread())
			throw new java.lang.Exception ("StreamQuoteSet::couponSpread - Does not contain Coupon/spread");

		return _mapQuote.get ("CouponSpread");
	}

	/**
	 * Set the Basis
	 * 
	 * @param dblBasis The Basis
	 * 
	 * @return TRUE - The Basis successfully set
	 */

	public boolean setBasis (
		final double dblBasis)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBasis)) return false;

		_mapQuote.put ("Basis", dblBasis);

		return true;
	}

	/**
	 * Indicate if the Basis Field exists
	 * 
	 * @return TRUE - Basis Field Exists
	 */

	public boolean containsBasis()
	{
		return _mapQuote.containsKey ("Basis");
	}

	/**
	 * Retrieve the Basis
	 * 
	 * @return The Basis
	 * 
	 * @throws java.lang.Exception Thrown if the Basis Field does not exist
	 */

	public double basis()
		throws java.lang.Exception
	{
		if (!containsBasis())
			throw new java.lang.Exception ("StreamQuoteSet::basis - Does not contain Basis");

		return _mapQuote.get ("Basis");
	}
}
