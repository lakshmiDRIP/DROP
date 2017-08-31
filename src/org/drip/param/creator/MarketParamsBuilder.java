
package org.drip.param.creator;

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
 * MarketParamsBuilder implements the various ways of constructing, de-serializing, and building the Market
 *  Parameters.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MarketParamsBuilder {

	/**
	 * Create a Market Parameters instance with the funding discount curve, the forward discount curve, the
	 *  govvie curve, the credit curve, the component quote, the map of treasury benchmark quotes, and the
	 *  Latent State Fixings Instance.
	 * 
	 * @param dcFunding Funding Curve
	 * @param fc Forward Curve
	 * @param gc Govvie Curve
	 * @param cc Credit Curve
	 * @param strComponentCode Component Code
	 * @param compQuote Component quote
	 * @param mTSYQuotes Map of Treasury Benchmark Quotes
	 * @param lsfc The Latent State Fixings Instance
	 * 
	 * @return Market Parameters Instance
	 */

	public static final org.drip.param.market.CurveSurfaceQuoteContainer Create (
		final org.drip.state.discount.MergedDiscountForwardCurve dcFunding,
		final org.drip.state.forward.ForwardCurve fc,
		final org.drip.state.govvie.GovvieCurve gc,
		final org.drip.state.credit.CreditCurve cc,
		final java.lang.String strComponentCode,
		final org.drip.param.definition.ProductQuote compQuote,
		final org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote>
			mTSYQuotes,
		final org.drip.param.market.LatentStateFixingsContainer lsfc)
	{
		org.drip.param.market.CurveSurfaceQuoteContainer csqs = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		if (null != cc && !csqs.setCreditState (cc)) return null;

		if (null != gc && !csqs.setGovvieState (gc)) return null;

		if (null != lsfc && !csqs.setFixings (lsfc)) return null;

		if (null != dcFunding && !csqs.setFundingState (dcFunding)) return null;

		if (null != mTSYQuotes && !csqs.setQuoteMap (mTSYQuotes)) return null;

		if (null != compQuote && null != strComponentCode && !strComponentCode.isEmpty() &&
			!csqs.setProductQuote (strComponentCode, compQuote))
			return null;

		if (null != fc && !csqs.setForwardState (fc)) return null;

		return csqs;
	}

	/**
	 * Create a Market Parameters instance with the Funding Curve alone
	 * 
	 * @param dcFunding Funding Curve
	 * 
	 * @return Market Parameters instance
	 */

	public static final org.drip.param.market.CurveSurfaceQuoteContainer Discount (
		final org.drip.state.discount.MergedDiscountForwardCurve dcFunding)
	{
		return Create (dcFunding, null, null, null, "", null, null, null);
	}

	/**
	 * Create a Market Parameters instance with the Funding Curve and the forward Curve
	 * 
	 * @param dcFunding Funding Curve
	 * @param fc Forward Curve
	 * 
	 * @return Market Parameters instance
	 */

	public static final org.drip.param.market.CurveSurfaceQuoteContainer DiscountForward (
		final org.drip.state.discount.MergedDiscountForwardCurve dcFunding,
		final org.drip.state.forward.ForwardCurve fc)
	{
		return Create (dcFunding, fc, null, null, "", null, null, null);
	}

	/**
	 * Create a Market Parameters instance with the rates discount curve and the treasury discount curve alone
	 * 
	 * @param dcFunding Funding Curve
	 * @param gc Govvie Curve
	 * 
	 * @return Market Parameters instance
	 */

	public static final org.drip.param.market.CurveSurfaceQuoteContainer Govvie (
		final org.drip.state.discount.MergedDiscountForwardCurve dcFunding,
		final org.drip.state.govvie.GovvieCurve gc)
	{
		return Create (dcFunding, null, gc, null, "", null, null, null);
	}

	/**
	 * Create a Market Parameters Instance with the Funding Curve and the credit curve
	 * 
	 * @param dcFunding Funding Curve
	 * @param cc Credit Curve
	 * 
	 * @return The Market Parameters Instance
	 */

	public static final org.drip.param.market.CurveSurfaceQuoteContainer Credit (
		final org.drip.state.discount.MergedDiscountForwardCurve dcFunding,
		final org.drip.state.credit.CreditCurve cc)
	{
		return Create (dcFunding, null, null, cc, "", null, null, null);
	}

	/**
	 * Create a Market Parameters Instance with the Funding Curve, the Govvie Curve, the Credit Curve, the
	 *  component quote, the map of treasury benchmark quotes, and the Latent State Fixings Container
	 * 
	 * @param dcFunding Funding Curve
	 * @param gc Govvie Curve
	 * @param cc Credit Curve
	 * @param strComponentCode Component Code
	 * @param compQuote Component quote
	 * @param mTSYQuotes Map of Treasury Benchmark Quotes
	 * @param lsfc Latent State Fixings Container
	 * 
	 * @return Market Parameters Instance
	 */

	public static final org.drip.param.market.CurveSurfaceQuoteContainer Create (
		final org.drip.state.discount.MergedDiscountForwardCurve dcFunding,
		final org.drip.state.govvie.GovvieCurve gc,
		final org.drip.state.credit.CreditCurve cc,
		final java.lang.String strComponentCode,
		final org.drip.param.definition.ProductQuote compQuote,
		final org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote>
			mTSYQuotes,
		final org.drip.param.market.LatentStateFixingsContainer lsfc)
	{
		return Create (dcFunding, null, gc, cc, strComponentCode, compQuote, mTSYQuotes, lsfc);
	}

	/**
	 * Create MarketParams from the array of calibration instruments
	 * 
	 * @return MarketParams object
	 */

	public static final org.drip.param.definition.ScenarioMarketParams CreateMarketParams()
	{
		try {
			return new org.drip.param.market.CurveSurfaceScenarioContainer();
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
