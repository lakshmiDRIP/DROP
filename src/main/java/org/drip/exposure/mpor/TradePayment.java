
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
 * DealerClientTradePayment holds the Dealer (Negative) and Client (Positive) Trade Payments at an Exposure
 * 	Date. The References are:
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

public class TradePayment
{
	private double _client = java.lang.Double.NaN;
	private double _dealer = java.lang.Double.NaN;

	/**
	 * Construct a "Standard" TradePayment Instance
	 * 
	 * @param tradePayment The Trade Payment
	 * 
	 * @return The "Standard" TradePayment Instance
	 */

	public static final TradePayment Standard (
		final double tradePayment)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (tradePayment))
		{
			return null;
		}

		double clientTradePayment = 0.;
		double dealerTradePayment = 0.;

		if (0. > tradePayment)
		{
			dealerTradePayment = tradePayment;
		}
		else
		{
			clientTradePayment = tradePayment;
		}

		try
		{
			return new TradePayment (
				dealerTradePayment,
				clientTradePayment
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * TradePayment Constructor
	 * 
	 * @param dealer The Dealer Trade Payment
	 * @param client The Client Trade Payment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TradePayment (
		final double dealer,
		final double client)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dealer = dealer) ||
			!org.drip.quant.common.NumberUtil.IsValid (_client = client) ||
			_dealer > 0. || _client < 0.)
		{
			throw new java.lang.Exception ("TradePayment Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Dealer Trade Payment
	 * 
	 * @return The Dealer Trade Payment
	 */

	public double dealer()
	{
		return _dealer;
	}

	/**
	 * Retrieve the Client Trade Payment
	 * 
	 * @return The Client Trade Payment
	 */

	public double client()
	{
		return _client;
	}
}
