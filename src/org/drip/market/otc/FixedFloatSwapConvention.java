
package org.drip.market.otc;

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
 * FixedFloatSwapConvention contains the Details of the Fixed-Float Swap Component of an OTC contact.
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixedFloatSwapConvention {
	private int _iSpotLag = -1;
	private org.drip.market.otc.FixedStreamConvention _fixedConv = null;
	private org.drip.market.otc.FloatStreamConvention _floatConv = null;

	/**
	 * FixedFloatSwapConvention Constructor
	 * 
	 * @param fixedConv Fixed Stream Convention
	 * @param floatConv Float Stream Convention
	 * @param iSpotLag Spot Lag
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public FixedFloatSwapConvention (
		final org.drip.market.otc.FixedStreamConvention fixedConv,
		final org.drip.market.otc.FloatStreamConvention floatConv,
		final int iSpotLag)
		throws java.lang.Exception
	{
		if (null == (_fixedConv = fixedConv) || null == (_floatConv = floatConv) || 0 > (_iSpotLag =
			iSpotLag))
			throw new java.lang.Exception ("FixedFloatSwapConvention ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Fixed Stream Convention
	 * 
	 * @return The Fixed Stream Convention
	 */

	public org.drip.market.otc.FixedStreamConvention fixedStreamConvention()
	{
		return _fixedConv;
	}

	/**
	 * Retrieve the Float Stream Convention
	 * 
	 * @return The Float Stream Convention
	 */

	public org.drip.market.otc.FloatStreamConvention floatStreamConvention()
	{
		return _floatConv;
	}

	/**
	 * Retrieve the Spot Lag
	 * 
	 * @return The Spot Lag
	 */

	public int spotLag()
	{
		return _iSpotLag;
	}

	/**
	 * Create a Standardized Fixed-Float Component Instance from the Inputs
	 * 
	 * @param dtSpot The Spot Date
	 * @param strMaturityTenor The Maturity Tenor
	 * @param dblFixedCoupon The Fixed Coupon
	 * @param dblFloatBasis The Float Basis
	 * @param dblNotional Notional
	 * 
	 * @return The Standardized Fixed-Float Component Instance
	 */

	public org.drip.product.rates.FixFloatComponent createFixFloatComponent (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strMaturityTenor,
		final double dblFixedCoupon,
		final double dblFloatBasis,
		final double dblNotional)
	{
		if (null == dtSpot) return null;

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (_iSpotLag,
			_fixedConv.calendar());

		try {
			org.drip.product.rates.FixFloatComponent ffc = new org.drip.product.rates.FixFloatComponent
				(_fixedConv.createStream (dtEffective, strMaturityTenor, dblFixedCoupon, dblNotional),
					_floatConv.createStream (dtEffective, strMaturityTenor, dblFloatBasis, -1. *
						dblNotional), null);

			ffc.setPrimaryCode ("IRS::" + ffc.forwardLabel().get ("DERIVED").fullyQualifiedName() + "." +
				strMaturityTenor);

			return ffc;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public java.lang.String toString()
	{
		return "[SPOT LAG: " + _iSpotLag + "]  " + _fixedConv + "  " + _floatConv;
	}
}
