
package org.drip.param.creator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>MarketParamsBuilder</i> implements the various ways of constructing, de-serializing, and building the
 * Market Parameters.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/README.md">Product Cash Flow, Valuation, Market, Pricing, and Quoting Parameters</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/creator">Market Curves Surfaces Quotes Builder</a></li>
 *  </ul>
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
