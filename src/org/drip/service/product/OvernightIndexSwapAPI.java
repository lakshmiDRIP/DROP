
package org.drip.service.product;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * OvernightIndexSwapAPI exposes the Pricing and the Scenario Runs for an Overnight Index Swap.
 *
 * @author Lakshmi Krishnamurthy
 */

public class OvernightIndexSwapAPI {

	/**
	 * Generate Full Set of Metrics for the Specified OIS
	 * 
	 * @param strOISCurrency OIS Currency
	 * @param strOISTenor OIS Tenor
	 * @param dblOISCoupon OIS Coupon
	 * @param iSpotDate Spot Date
	 * @param astrOvernightCurveDepositTenor Overnight Curve Calibration Deposit Tenor
	 * @param adblOvernightCurveDepositQuote Overnight Tenor Calibration Deposit Quote
	 * @param astrOvernightCurveOISTenor Overnight Curve Calibration OIS Tenor
	 * @param adblOvernightCurveOISQuote Overnight Curve Calibration OIS Quote
	 * @param bFund TRUE - Floater Based off of Fund
	 * 
	 * @return Map of Valuation Metrics
	 */

	public static final java.util.Map<java.lang.String, java.lang.Double> ValuationMetrics (
		final java.lang.String strOISCurrency,
		final java.lang.String strOISTenor,
		final double dblOISCoupon,
		final int iSpotDate,
		final java.lang.String[] astrOvernightCurveDepositTenor,
		final double[] adblOvernightCurveDepositQuote,
		final java.lang.String[] astrOvernightCurveOISTenor,
		final double[] adblOvernightCurveOISQuote,
		final boolean bFund)
	{
		org.drip.service.env.EnvManager.InitEnv ("");

		org.drip.analytics.date.JulianDate dtSpot = new org.drip.analytics.date.JulianDate (iSpotDate);

		org.drip.state.discount.MergedDiscountForwardCurve dcOvernight =
			org.drip.service.template.LatentMarketStateBuilder.SmoothOvernightCurve (dtSpot, strOISCurrency,
				astrOvernightCurveDepositTenor, adblOvernightCurveDepositQuote, "Rate",
					astrOvernightCurveOISTenor, adblOvernightCurveOISQuote, "SwapRate", null, null, null,
						"SwapRate", null, null, "SwapRate");

		if (null == dcOvernight) return null;

		org.drip.product.rates.FixFloatComponent oisFixFloat =
			org.drip.service.template.OTCInstrumentBuilder.OISFixFloat (dtSpot, strOISCurrency, strOISTenor,
				dblOISCoupon, bFund);

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		return csqc.setFundingState (dcOvernight) ? oisFixFloat.value
			(org.drip.param.valuation.ValuationParams.Spot (iSpotDate), null, csqc, null) : null;
	}
}
