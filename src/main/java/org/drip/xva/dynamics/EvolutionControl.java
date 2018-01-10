
package org.drip.xva.dynamics;

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
 * EvolutionControl contains Time Settings the drive the State Variables along a VA Path. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
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

public class EvolutionControl
{
	private int[] _eventDateArray = null;
	private org.drip.analytics.date.JulianDate _spotDate = null;

	/**
	 * Generate the EvolutionControl Instance from the Spot Date, the Period Tenor, and the Period Count
	 * 
	 * @param spotDate The Spot Date
	 * @param periodTenor The Period Tenor
	 * @param periodCount The Period Count
	 * 
	 * @return The EvolutionControl Instance
	 */

	public static final EvolutionControl PeriodHorizon (
		final org.drip.analytics.date.JulianDate spotDate,
		final java.lang.String periodTenor,
		final int periodCount)
	{
		if (null == spotDate)
		{
			return null;
		}

		int[] eventDates = new int[periodCount];
		org.drip.analytics.date.JulianDate previousDate = spotDate;

		for (int i = 0; i < periodCount; ++i)
		{
			if (null == (previousDate = previousDate.addTenor (periodTenor)))
			{
				return null;
			}

			eventDates[i] = previousDate.julian();
		}

		try
		{
			return new EvolutionControl (
				spotDate,
				eventDates
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * EvolutionControl Constructor
	 * 
	 * @param spotDate Spot Date
	 * @param eventDateArray Array of Event Dates
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EvolutionControl (
		final org.drip.analytics.date.JulianDate spotDate,
		final int[] eventDateArray)
		throws java.lang.Exception
	{
		if (null == (_spotDate = spotDate) ||
			null == (_eventDateArray = eventDateArray) || 0 == _eventDateArray.length)
			throw new java.lang.Exception ("EvolutionControl Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Spot Date
	 * 
	 * @return The Spot Date
	 */

	public org.drip.analytics.date.JulianDate spotDate()
	{
		return _spotDate;
	}

	/**
	 * Retrieve the Array of Event Dates
	 * 
	 * @return Array of Event Dates
	 */

	public int[] eventDates()
	{
		return _eventDateArray;
	}
}
