
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
 * PortfolioMPoR estimates the MPoR Variation Margin and the Trade Payments for the Component MPoR's of a
 *  given Portfolio off of the Realized Market Path. The References are:
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

public class PortfolioMPoR implements org.drip.exposure.mpor.VariationMarginTradePaymentVertex
{
	private java.util.List<org.drip.exposure.mpor.VariationMarginTradePaymentVertex> _componentMPoRList = new
		java.util.ArrayList<org.drip.exposure.mpor.VariationMarginTradePaymentVertex>();

	/**
	 * Retrieve the List of Component MPoR's
	 * 
	 * @return The List of Component MPoR's
	 */

	public java.util.List<org.drip.exposure.mpor.VariationMarginTradePaymentVertex> componentMPoRList()
	{
		return _componentMPoRList;
	}

	@Override public double variationMarginEstimate (
		final int forwardDate,
		final org.drip.exposure.universe.MarketPath marketPath)
		throws java.lang.Exception
	{
		double variationMarginEstimate = 0.;

		for (org.drip.exposure.mpor.VariationMarginTradePaymentVertex componentMPoR : _componentMPoRList)
		{
			variationMarginEstimate += componentMPoR.variationMarginEstimate (
				forwardDate,
				marketPath
			);
		}

		return variationMarginEstimate;
	}

	@Override public org.drip.exposure.mpor.TradePayment tradePayment (
		final int forwardDate,
		final org.drip.exposure.universe.MarketPath marketPath)
	{
		double clientPayment = 0.;
		double dealerPayment = 0.;

		for (org.drip.exposure.mpor.VariationMarginTradePaymentVertex componentMPoR : _componentMPoRList)
		{
			org.drip.exposure.mpor.TradePayment tradePayment = componentMPoR.tradePayment (
				forwardDate,
				marketPath
			);

			if (null == tradePayment)
			{
				return null;
			}

			dealerPayment += tradePayment.dealer();

			clientPayment += tradePayment.client();
		}

		try
		{
			return new org.drip.exposure.mpor.TradePayment (
				dealerPayment,
				clientPayment
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.exposure.mpor.TradePayment[] denseTradePaymentArray (
		final int startDate,
		final int endDate,
		final org.drip.exposure.universe.MarketPath marketPath)
	{
		if (endDate < startDate)
		{
			return null;
		}

		int denseDateCount = endDate - startDate + 1;
		double[] clientTradePaymentArray = new double[denseDateCount];
		double[] dealerTradePaymentArray = new double[denseDateCount];
		org.drip.exposure.mpor.TradePayment[] denseTradePaymentArray = new
			org.drip.exposure.mpor.TradePayment[denseDateCount];

		for (int denseDateIndex = 0; denseDateIndex < denseDateCount; ++denseDateIndex)
		{
			clientTradePaymentArray[denseDateIndex] = 0.;
			dealerTradePaymentArray[denseDateIndex] = 0.;
		}

		for (org.drip.exposure.mpor.VariationMarginTradePaymentVertex componentMPoR : _componentMPoRList)
		{
			org.drip.exposure.mpor.TradePayment[] componentDenseTradePaymentArray =
				componentMPoR.denseTradePaymentArray (
					startDate,
					endDate,
					marketPath
				);

			if (null == componentDenseTradePaymentArray)
			{
				return null;
			}

			for (int denseDateIndex = 0; denseDateIndex < denseDateCount; ++denseDateIndex)
			{
				clientTradePaymentArray[denseDateIndex] +=
					componentDenseTradePaymentArray[denseDateIndex].client();

				dealerTradePaymentArray[denseDateIndex] +=
					componentDenseTradePaymentArray[denseDateIndex].dealer();
			}
		}

		for (int denseDateIndex = 0; denseDateIndex < denseDateCount; ++denseDateIndex)
		{
			try
			{
				denseTradePaymentArray[denseDateIndex] = new org.drip.exposure.mpor.TradePayment (
					dealerTradePaymentArray[denseDateIndex],
					clientTradePaymentArray[denseDateIndex]
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return denseTradePaymentArray;
	}
}
