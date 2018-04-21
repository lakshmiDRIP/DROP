
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
 * MarginTradeVertexExposure holds the Margin and Trade Flows and Exposures for a specific Forward Vertex
 *  Date. The References are:
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

public class MarginTradeVertexExposure
{
	private double _netTradePaymentGap = java.lang.Double.NaN;
	private double _clientTradePaymentGap = java.lang.Double.NaN;
	private double _variationMarginPosting = java.lang.Double.NaN;
	private double _variationMarginEstimate = java.lang.Double.NaN;
	private org.drip.exposure.csatimeline.LastFlowDates _lastFlowDates = null;

	/**
	 * MarginTradeVertexExposure Constructor
	 * 
	 * @param variationMarginEstimate The Calculation Agent Generated Variation Margin Estimate
	 * @param variationMarginPosting The Actual Variation Margin Posted from Collateral Rules and Operational
	 * 		Delays
	 * @param clientTradePaymentGap The Client Trade Payment Gap
	 * @param netTradePaymentGap The Net Trade Payment Gap
	 * @param lastFlowDates The Last Flow Dates
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarginTradeVertexExposure (
		final double variationMarginEstimate,
		final double variationMarginPosting,
		final double clientTradePaymentGap,
		final double netTradePaymentGap,
		final org.drip.exposure.csatimeline.LastFlowDates lastFlowDates)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_variationMarginEstimate = variationMarginEstimate) ||
			!org.drip.quant.common.NumberUtil.IsValid (_variationMarginPosting = variationMarginPosting) ||
			!org.drip.quant.common.NumberUtil.IsValid (_clientTradePaymentGap = clientTradePaymentGap) ||
			!org.drip.quant.common.NumberUtil.IsValid (_netTradePaymentGap = netTradePaymentGap) ||
			null == (_lastFlowDates = lastFlowDates))
		{
			throw new java.lang.Exception ("MarginTradeVertexExposure Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Calculation Agent Generated Variation Margin Estimate
	 * 
	 * @return The Calculation Agent Generated Variation Margin Estimate
	 */

	public double variationMarginEstimate()
	{
		return _variationMarginEstimate;
	}

	/**
	 * Retrieve the Actual Variation Margin Posted from Collateral Rules and Operational Delays
	 * 
	 * @return The Actual Variation Margin Posted from Collateral Rules and Operational Delays
	 */

	public double variationMarginPosting()
	{
		return _variationMarginPosting;
	}

	/**
	 * Retrieve the Client Trade Payment Gap
	 * 
	 * @return The Client Trade Payment Gap
	 */

	public double clientTradePaymentGap()
	{
		return _clientTradePaymentGap;
	}

	/**
	 * Retrieve the Net Trade Payment Gap
	 * 
	 * @return The Net Trade Payment Gap
	 */

	public double netTradePaymentGap()
	{
		return _netTradePaymentGap;
	}

	/**
	 * Retrieve the Trade Payment Gap
	 * 
	 * @return The Trade Payment Gap
	 */

	public double tradePaymentGap()
	{
		return _clientTradePaymentGap + _netTradePaymentGap;
	}

	/**
	 * Retrieve the Last Flow Dates
	 * 
	 * @return The Last Flow Dates
	 */

	public org.drip.exposure.csatimeline.LastFlowDates lastFlowDates()
	{
		return _lastFlowDates;
	}
}
