
package org.drip.exposure.generator;

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
 * FixFloatMPoR estimates the MPoR Variation Margin and the Trade Payments for the given Fix Float Component
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

public class FixFloatMPoR implements org.drip.exposure.generator.VariationMarginTradePaymentVertex
{
	private org.drip.exposure.generator.FixedStreamMPoR _fixedStreamMPoR = null;
	private org.drip.exposure.generator.FloatStreamMPoR _floatStreamMPoR = null;

	/**
	 * FixFloatMPoR Constructor
	 * 
	 * @param fixFloatComponent The Fix Float Component Instance
	 * @param notional The Fix Float Component Notional
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FixFloatMPoR (
		final org.drip.product.rates.FixFloatComponent fixFloatComponent,
		final double notional)
		throws java.lang.Exception
	{
		if (null == fixFloatComponent)
		{
			throw new java.lang.Exception ("FixFloatMPoR Construtor => Invalid Inputs");
		}

		_fixedStreamMPoR = new org.drip.exposure.generator.FixedStreamMPoR (
			fixFloatComponent.referenceStream(),
			notional
		);

		_floatStreamMPoR = new org.drip.exposure.generator.FloatStreamMPoR (
			fixFloatComponent.derivedStream(),
			notional
		);
	}

	/**
	 * Retrieve the Fixed Stream MPoR
	 * 
	 * @return The Fixed Stream MPoR
	 */

	public org.drip.exposure.generator.FixedStreamMPoR fixedStreamMPoR()
	{
		return _fixedStreamMPoR;
	}

	/**
	 * Retrieve the Float Stream MPoR
	 * 
	 * @return The Float Stream MPoR
	 */

	public org.drip.exposure.generator.FloatStreamMPoR floatStreamMPoR()
	{
		return _floatStreamMPoR;
	}

	/**
	 * Retrieve the Underlying Fix Float Notional
	 * 
	 * @return The Underlying Fix Float Notional
	 */

	public double notional()
	{
		return _fixedStreamMPoR.notional();
	}

	@Override public double variationMarginEstimate (
		final int forwardDate,
		final org.drip.exposure.universe.MarketPath marketPath)
		throws java.lang.Exception
	{
		return _fixedStreamMPoR.variationMarginEstimate (
			forwardDate,
			marketPath
		) + _floatStreamMPoR.variationMarginEstimate (
			forwardDate,
			marketPath
		);
	}

	@Override public org.drip.exposure.mpor.TradePayment tradePayment (
		final int forwardDate,
		final org.drip.exposure.universe.MarketPath marketPath)
	{
		org.drip.exposure.mpor.TradePayment fixedStreamTradePayment = _fixedStreamMPoR.tradePayment (
			forwardDate,
			marketPath
		);

		if (null == fixedStreamTradePayment)
		{
			return null;
		}

		org.drip.exposure.mpor.TradePayment floatStreamTradePayment = _floatStreamMPoR.tradePayment (
			forwardDate,
			marketPath
		);
	
		if (null == floatStreamTradePayment)
		{
			return null;
		}

		try
		{
			return new org.drip.exposure.mpor.TradePayment (
				fixedStreamTradePayment.dealer() + floatStreamTradePayment.dealer(),
				fixedStreamTradePayment.client() + floatStreamTradePayment.client()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
