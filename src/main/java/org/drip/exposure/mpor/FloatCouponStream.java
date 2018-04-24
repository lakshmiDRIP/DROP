
package org.drip.exposure.mpor;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * FloatCouponStream estimates the Variation Margin and the Trade Payments for the given Float Coupon Stream
 *  off of the Realized Market Path. The References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737, eSSRN.
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, eSSRN, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955.
 *  
 *  - Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting, eSSRN,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FloatCouponStream implements org.drip.exposure.mpor.MarginTradeVertex
{
	private double _notional = java.lang.Double.NaN;
	private org.drip.product.rates.Stream _stream = null;

	/**
	 * FloatCouponStream Constructor
	 * 
	 * @param stream The Fixed Coupon Stream Instance
	 * @param notional The Fixed Coupon Stream Notional
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FloatCouponStream (
		final org.drip.product.rates.Stream stream,
		final double notional)
		throws java.lang.Exception
	{
		if (null == (_stream = stream) ||
			!org.drip.quant.common.NumberUtil.IsValid (_notional = notional))
		{
			throw new java.lang.Exception ("FloatCouponStream Constructor => Invalid Inputs");
		}
	}

	@Override public double variationMarginEstimate (
		final int forwardDate,
		final org.drip.exposure.universe.MarketPath marketPath)
		throws java.lang.Exception
	{
		if (null == marketPath)
		{
			throw new java.lang.Exception ("FloatCouponStream::variationMarginEstimate => Invalid Inputs");
		}

		if (forwardDate <= _stream.effective().julian() || forwardDate >= _stream.maturity().julian())
		{
			return 0.;
		}

		double variationMarginEstimate = 0.;

		double overnightReplicatorForward = marketPath.marketVertex (forwardDate).overnightReplicator();

		for (org.drip.analytics.cashflow.CompositePeriod period : _stream.periods())
		{
			int periodEndDate = period.endDate();

			if (periodEndDate < forwardDate)
			{
				continue;
			}

			org.drip.analytics.cashflow.ComposableUnitFloatingPeriod composableUnitFloatingPeriod =
				(org.drip.analytics.cashflow.ComposableUnitFloatingPeriod) period.periods().get (0);

			variationMarginEstimate += period.couponDCF() *
				period.notional (periodEndDate) *
				marketPath.marketVertex
					(composableUnitFloatingPeriod.referenceIndexPeriod().fixingDate()).positionManifestValue() *
				period.couponFactor (periodEndDate) *
				overnightReplicatorForward /
				marketPath.marketVertex (period.payDate()).overnightReplicator();
		}

		return variationMarginEstimate * _notional;
	}

	@Override public org.drip.exposure.mpor.TradePayment tradePayment (
		final int forwardDate,
		final org.drip.exposure.universe.MarketPath marketPath)
	{
		if (null == marketPath)
		{
			return null;
		}

		if (forwardDate < _stream.effective().julian() || forwardDate > _stream.maturity().julian())
		{
			return org.drip.exposure.mpor.TradePayment.Standard (0.);
		}

		double overnightReplicatorForward = marketPath.marketVertex (forwardDate).overnightReplicator();

		for (org.drip.analytics.cashflow.CompositePeriod period : _stream.periods())
		{
			int periodPayDate = period.payDate();

			if (periodPayDate == forwardDate)
			{
				int periodEndDate = period.endDate();

				org.drip.analytics.cashflow.ComposableUnitFloatingPeriod composableUnitFloatingPeriod =
					(org.drip.analytics.cashflow.ComposableUnitFloatingPeriod) period.periods().get (0);

				try
				{
					return org.drip.exposure.mpor.TradePayment.Standard (
						_notional * period.couponDCF() *
						period.notional (periodEndDate) *
						marketPath.marketVertex
							(composableUnitFloatingPeriod.referenceIndexPeriod().fixingDate()).positionManifestValue() *
						period.couponFactor (periodEndDate) *
						overnightReplicatorForward /
						marketPath.marketVertex (period.payDate()).overnightReplicator()
					);
				}
				catch (java.lang.Exception e)
				{
					e.printStackTrace();

					return null;
				}
			}
		}

		return org.drip.exposure.mpor.TradePayment.Standard (0.);
	}
}
