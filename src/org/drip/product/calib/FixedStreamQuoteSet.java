
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
 * FixedStreamQuoteSet extends the ProductQuoteSet by implementing the Calibration Parameters for the Fixed
 * 	Stream. Currently it exposes the PV and the Coupon Quote Fields.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FixedStreamQuoteSet extends org.drip.product.calib.ProductQuoteSet {

	/**
	 * FixedStreamQuoteSet Constructor
	 * 
	 * @param aLSS Array of Latent State Specification
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public FixedStreamQuoteSet (
		final org.drip.state.representation.LatentStateSpecification[] aLSS)
		throws java.lang.Exception
	{
		super (aLSS);
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
		return set ("PV", dblPV);
	}

	/**
	 * Indicate if the PV Field exists
	 * 
	 * @return TRUE - PV Field Exists
	 */

	public boolean containsPV()
	{
		return contains ("PV");
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
		return get ("PV");
	}

	/**
	 * Set the Coupon
	 * 
	 * @param dblCoupon The Coupon
	 * 
	 * @return TRUE - The Coupon successfully set
	 */

	public boolean setCoupon (
		final double dblCoupon)
	{
		return set ("Coupon", dblCoupon);
	}

	/**
	 * Indicate if the Coupon Field exists
	 * 
	 * @return TRUE - Coupon Field Exists
	 */

	public boolean containsCoupon()
	{
		return contains ("Coupon");
	}

	/**
	 * Retrieve the Coupon
	 * 
	 * @return The Coupon
	 * 
	 * @throws java.lang.Exception Thrown if the Coupon Field does not exist
	 */

	public double coupon()
		throws java.lang.Exception
	{
		return get ("Coupon");
	}

	/**
	 * Set the Coupon Basis
	 * 
	 * @param dblCouponBasis The Coupon Basis
	 * 
	 * @return TRUE - The Coupon Basis successfully set
	 */

	public boolean setCouponBasis (
		final double dblCouponBasis)
	{
		return set ("CouponBasis", dblCouponBasis);
	}

	/**
	 * Indicate if the Coupon Basis Field exists
	 * 
	 * @return TRUE - Coupon Basis Field Exists
	 */

	public boolean containsCouponBasis()
	{
		return contains ("CouponBasis");
	}

	/**
	 * Retrieve the Coupon Basis
	 * 
	 * @return The Coupon Basis
	 * 
	 * @throws java.lang.Exception Thrown if the Coupon Basis Field does not exist
	 */

	public double couponBasis()
		throws java.lang.Exception
	{
		return get ("CouponBasis");
	}
}
