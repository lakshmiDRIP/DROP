
package org.drip.exposure.holdings;

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
 * MarginTradeFlowEntry holds the Margin Flow and Trade Flow Exposures for a specific Forward Date. The
 *  References are:
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

public class MarginTradeFlowEntry
{
	private int _marginPostingDate = -1;
	private double _marginFlowActual = java.lang.Double.NaN;
	private double _tradeFlowExposure = java.lang.Double.NaN;
	private double _marginFlowExposure = java.lang.Double.NaN;

	/**
	 * MarginTradeFlowEntry Constructor
	 * 
	 * @param marginFlowExposure The Calculation Agent Generated Margin Flow Exposure
	 * @param marginPostingDate The Margin Posting Date
	 * @param marginFlowActual The Actual Margin Flow Realized from Collateral Rules
	 * @param tradeFlowExposure The Trade Flow Exposure
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarginTradeFlowEntry (
		final double marginFlowExposure,
		final int marginPostingDate,
		final double marginFlowActual,
		final double tradeFlowExposure)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_marginFlowExposure = marginFlowExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginFlowActual = marginFlowActual) ||
			!org.drip.quant.common.NumberUtil.IsValid (_tradeFlowExposure = tradeFlowExposure))
		{
			throw new java.lang.Exception ("MarginTradeFlowEntry Constructor => Invalid Inputs");
		}

		_marginPostingDate = marginPostingDate;
	}

	/**
	 * Retrieve the Calculation Agent Generated Margin Flow Exposure
	 * 
	 * @return The Calculation Agent Generated Margin Flow Exposure
	 */

	public double marginFlowExposure()
	{
		return _marginFlowExposure;
	}

	/**
	 * Retrieve the Margin Posting Date
	 * 
	 * @return The Margin Posting Date
	 */

	public int marginPostingDate()
	{
		return _marginPostingDate;
	}

	/**
	 * Retrieve the Actual Margin Flow Realized from Collateral Rules
	 * 
	 * @return The Actual Margin Flow Realized from Collateral Rules
	 */

	public double marginFlowActual()
	{
		return _marginFlowActual;
	}

	/**
	 * Retrieve the Trade Flow Exposure
	 * 
	 * @return The Trade Flow Exposure
	 */

	public double tradeFlowExposure()
	{
		return _tradeFlowExposure;
	}
}
