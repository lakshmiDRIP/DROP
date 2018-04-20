
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
 * MarginTradeFlowTrajectory holds the Margin Flow and Trade Flow Exposures for the given Stream off of the
 * 	Realized Market Path. The References are:
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

public class MarginTradeFlowTrajectory
{
	private java.util.Map<java.lang.Integer, org.drip.exposure.holdings.MarginTradeFlowEntry>
		_mapMarginTradeFlowEntry = null;

	/**
	 * Construct a Standard Instance of MarginTradeFlowTrajectory
	 * 
	 * @param forwardDateArray The Forward Exposure Date Array
	 * @param marginTradeFlowPath The Margin Flow/Trade Flow Generator
	 * @param marketPath The Market Path
	 * @param andersenPykhtinSokolLag The Andersen Pykhtin Sokol Lag Parameterization
	 * 
	 * @return Standard Instance of MarginTradeFlowTrajectory
	 */

	public static final MarginTradeFlowTrajectory Standard (
		final int[] forwardDateArray,
		final org.drip.exposure.holdings.MarginTradeFlowPath marginTradeFlowPath,
		final org.drip.exposure.universe.MarketPath marketPath,
		final org.drip.exposure.csatimeline.AndersenPykhtinSokolLag andersenPykhtinSokolLag)
	{
		if (null == forwardDateArray ||
			null == marginTradeFlowPath ||
			null == marketPath)
		{
			return null;
		}

		java.util.Map<java.lang.Integer, java.lang.Double> mapMarginExposure = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		java.util.Map<java.lang.Integer, org.drip.exposure.holdings.MarginTradeFlowEntry>
			mapMarginTradeFlowEntry = new java.util.TreeMap<java.lang.Integer,
				org.drip.exposure.holdings.MarginTradeFlowEntry>();

		int forwardDateCount = forwardDateArray.length;
		int[] marginPostingDateArray = 0 == forwardDateCount? null : new int[forwardDateCount];
		double[] marginFlowExposureArray = 0 == forwardDateCount? null : new double[forwardDateCount];

		if (0 == forwardDateCount)
		{
			return null;
		}

		try
		{
			for (int forwardDateIndex = 0; forwardDateIndex < forwardDateCount; ++forwardDateIndex)
			{
				marginFlowExposureArray[forwardDateIndex] = marginTradeFlowPath.marginFlowExposure (
					forwardDateArray[forwardDateIndex],
					marketPath
				);

				mapMarginExposure.put (
					forwardDateArray[forwardDateIndex],
					marginFlowExposureArray[forwardDateIndex]
				);

				marginPostingDateArray[forwardDateIndex] = forwardDateArray[forwardDateIndex];
			}

			for (int forwardDateIndex = 0; forwardDateIndex < forwardDateCount; ++forwardDateIndex)
			{
				mapMarginTradeFlowEntry.put (
					forwardDateArray[forwardDateIndex],
					new org.drip.exposure.holdings.MarginTradeFlowEntry (
						marginFlowExposureArray[forwardDateIndex],
						marginPostingDateArray[forwardDateIndex],
						mapMarginExposure.containsKey (marginPostingDateArray[forwardDateIndex]) ?
							mapMarginExposure.get (marginPostingDateArray[forwardDateIndex]) :
							forwardDateArray[forwardDateIndex],
						marginTradeFlowPath.tradeFlowExposure (
							forwardDateArray[forwardDateIndex],
							marketPath
						)
					)
				);
			}

			return new MarginTradeFlowTrajectory (mapMarginTradeFlowEntry);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * MarginTradeFlowTrajectory Constructor
	 * 
	 * @param mapMarginTradeFlowEntry Map of the Margin Flow and Trade Flow Entries
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarginTradeFlowTrajectory (
		final java.util.Map<java.lang.Integer, org.drip.exposure.holdings.MarginTradeFlowEntry>
			mapMarginTradeFlowEntry)
		throws java.lang.Exception
	{
		if (null == (_mapMarginTradeFlowEntry = mapMarginTradeFlowEntry) ||
			0 == _mapMarginTradeFlowEntry.size())
		{
			throw new java.lang.Exception ("MarginTradeFlowTrajectory Constuctor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Map of Dated Margin Flow and Trade Flow Entries
	 * 
	 * @return Map of Dated Margin Flow and Trade Flow Entries
	 */

	public java.util.Map<java.lang.Integer, org.drip.exposure.holdings.MarginTradeFlowEntry>
		mapMarginTradeFlowEntry()
	{
		return _mapMarginTradeFlowEntry;
	}
}
