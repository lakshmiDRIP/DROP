
package org.drip.exposure.csatimeline;

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
 * AndersenPykhtinSokolLag holds the Client/Dealer Margin Flow and Trade Flow Lags using the Parameterization
 * 	laid out in Andersen, Pykhtin, and Sokol (2017). The References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737, eSSRN.
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Anfuso, F., D. Aziz, P. Giltinan, and K Loukopoulus (2017): A Sound Modeling and Back-testing Framework
 *  	for Forecasting Initial Margin Requirements,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279, eSSRN.
 *  
 *  - BCBS (2015): Margin Requirements for Non-centrally Cleared Derivatives,
 *  	https://www.bis.org/bcbs/publ/d317.pdf.
 *  
 *  - Pykhtin, M. (2009): Modeling Credit Exposure for Collateralized Counter-parties, Journal of Credit
 *  	Risk, 5 (4) 3-27.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AndersenPykhtinSokolLag
{
	private int _clientTradeDelay = -1;
	private int _dealerTradeDelay = -1;
	private int _clientVariationMarginDelay = -1;
	private int _dealerVariationMarginDelay = -1;

	/**
	 * Generate the "Conservative" Parameterization of AndersenPykhtinSokolLag
	 * 
	 * @return The "Conservative" Parameterization of AndersenPykhtinSokolLag
	 */

	public static final AndersenPykhtinSokolLag Conservative()
	{
		try
		{
			return new AndersenPykhtinSokolLag (
				15,
				9,
				8,
				3
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the "Aggressive" Parameterization of AndersenPykhtinSokolLag
	 * 
	 * @return The "Aggressive" Parameterization of AndersenPykhtinSokolLag
	 */

	public static final AndersenPykhtinSokolLag Aggressive()
	{
		try
		{
			return new AndersenPykhtinSokolLag (
				7,
				6,
				4,
				4
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the "Classical+" Parameterization of AndersenPykhtinSokolLag
	 * 
	 * @return The "Classical+" Parameterization of AndersenPykhtinSokolLag
	 */

	public static final AndersenPykhtinSokolLag ClassicalPlus()
	{
		try
		{
			return new AndersenPykhtinSokolLag (
				10,
				10,
				0,
				0
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the "Classical-" Parameterization of AndersenPykhtinSokolLag
	 * 
	 * @return The "Classical-" Parameterization of AndersenPykhtinSokolLag
	 */

	public static final AndersenPykhtinSokolLag ClassicalMinus()
	{
		try
		{
			return new AndersenPykhtinSokolLag (
				10,
				10,
				10,
				10
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * AndersenPykhtinSokolLag Constructor
	 * 
	 * @param clientVariationMarginDelay Client Variation Margin Flow Delay (Business Days)
	 * @param dealerVariationMarginDelay Dealer Variation Margin Flow Gap (Business Days)
	 * @param clientTradeDelay Client Trade Flow Delay (Business Days)
	 * @param dealerTradeDelay Dealer Trade Flow Delay (Business Days)
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AndersenPykhtinSokolLag (
		final int clientVariationMarginDelay,
		final int dealerVariationMarginDelay,
		final int clientTradeDelay,
		final int dealerTradeDelay)
		throws java.lang.Exception
	{
		if (0 > (_clientVariationMarginDelay = clientVariationMarginDelay) ||
			0 > (_dealerVariationMarginDelay = dealerVariationMarginDelay) ||
			0 > (_clientTradeDelay = clientTradeDelay) ||
			0 > (_dealerTradeDelay = dealerTradeDelay) ||
			_clientVariationMarginDelay < _dealerVariationMarginDelay ||
			_dealerVariationMarginDelay < _clientTradeDelay ||
			_clientTradeDelay < _dealerTradeDelay)
		{
			throw new java.lang.Exception ("AndersenPykhtinSokolLag Constuctor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Client Variation Margin Flow Delay
	 * 
	 * @return The Client Variation Margin Flow Delay
	 */

	public int clientVariationMarginDelay()
	{
		return _clientVariationMarginDelay;
	}

	/**
	 * Retrieve the Dealer Variation Margin Flow Delay
	 * 
	 * @return The Dealer Variation Margin Flow Delay
	 */

	public int dealerVariationMarginDelay()
	{
		return _dealerVariationMarginDelay;
	}

	/**
	 * Retrieve the Client Trade Flow Delay
	 * 
	 * @return The Client Trade Flow Delay
	 */

	public int clientTradeDelay()
	{
		return _clientTradeDelay;
	}

	/**
	 * Retrieve the Dealer Trade Flow Delay
	 * 
	 * @return The Dealer Trade Flow Delay
	 */

	public int dealerTradeDelay()
	{
		return _dealerTradeDelay;
	}
}
