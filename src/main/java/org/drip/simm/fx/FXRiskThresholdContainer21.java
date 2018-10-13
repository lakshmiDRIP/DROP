
package org.drip.simm.fx;

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
 * FXRiskThresholdContainer21 holds the ISDA SIMM 2.1 FX Risk Thresholds - the FX Categories and the
 *  Delta/Vega Limits defined for the Concentration Thresholds. The References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  	Calculations, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488, eSSRN.
 *  
 *  - Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  	Framework for Forecasting Initial Margin Requirements,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279, eSSRN.
 *  
 *  - Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin Requirements
 *  	- A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167, eSSRN.
 *  
 *  - International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology,
 *  	https://www.isda.org/a/oFiDE/isda-simm-v2.pdf.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FXRiskThresholdContainer21
{
	private static final java.util.Map<java.lang.Integer, org.drip.simm.fx.FXRiskGroup> s_FXRiskGroupMap =
		new java.util.TreeMap<java.lang.Integer, org.drip.simm.fx.FXRiskGroup>();

	private static final java.util.Map<java.lang.Integer, java.lang.Double> s_CategoryDelta = new
		java.util.TreeMap<java.lang.Integer, java.lang.Double>();

	private static final java.util.Map<java.lang.String, java.lang.Double> s_CategoryVega = new
		java.util.HashMap<java.lang.String, java.lang.Double>();

	/**
	 * Initialize the FX Risk Threshold Container
	 * 
	 * @return TRUE - The FX Risk Threshold Container
	 */

	public static final boolean Init()
	{
		try
		{
			s_FXRiskGroupMap.put (
				1,
				new org.drip.simm.fx.FXRiskGroup (
					1,
					"Significantly Material",
					new java.lang.String[]
					{
						"USD",
						"EUR",
						"JPY",
						"GBP",
						"AUD",
						"CHF",
						"CAD"
					}
				)
			);

			s_FXRiskGroupMap.put (
				2,
				new org.drip.simm.fx.FXRiskGroup (
					2,
					"Frequently Traded",
					new java.lang.String[]
					{
						"BRL",
						"CNY",
						"HKD",
						"INR",
						"KRW",
						"MXN",
						"NOK",
						"NZD",
						"RUB",
						"SEK",
						"SGD",
						"TRY",
						"ZAR"
					}
				)
			);

			s_FXRiskGroupMap.put (
				3,
				new org.drip.simm.fx.FXRiskGroup (
					3,
					"Others",
					new java.lang.String[]
					{
						"Other"
					}
				)
			);

			s_CategoryDelta.put (
				1,
				9700.
			);

			s_CategoryDelta.put (
				2,
				2900.
			);

			s_CategoryDelta.put (
				3,
				450.
			);

			s_CategoryVega.put (
				"1__1",
				2000.
			);

			s_CategoryVega.put (
				"1__2",
				1000.
			);

			s_CategoryVega.put (
				"1__3",
				320.
			);

			s_CategoryVega.put (
				"2__1",
				1000.
			);

			s_CategoryVega.put (
				"2__2",
				410.
			);

			s_CategoryVega.put (
				"2__3",
				210.
			);

			s_CategoryVega.put (
				"3__1",
				320.
			);

			s_CategoryVega.put (
				"3__2",
				210.
			);

			s_CategoryVega.put (
				"3__3",
				150.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Category for the specified Currency
	 * 
	 * @param currency Currency
	 * 
	 * @return The Category
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final int CurrencyCategory (
		final java.lang.String currency)
		throws java.lang.Exception
	{
		if (null == currency || currency.isEmpty())
		{
			throw new java.lang.Exception ("FXRiskThresholdContainer::CurrencyCategory => Invalid Input");
		}

		for (java.util.Map.Entry<java.lang.Integer, org.drip.simm.fx.FXRiskGroup> fxRiskGroupEntry :
			s_FXRiskGroupMap.entrySet())
		{
			java.lang.String[] currencyArray = fxRiskGroupEntry.getValue().currencyArray();

			for (java.lang.String currencyEntry : currencyArray)
			{
				if (currencyEntry.equalsIgnoreCase (currency))
				{
					return fxRiskGroupEntry.getKey();
				}
			}
		}

		return s_FXRiskGroupMap.get (3).category();
	}

	/**
	 * Retrieve the Category Set
	 * 
	 * @return The Category Set
	 */

	public static final java.util.Set<java.lang.Integer> CategorySet()
	{
		return s_FXRiskGroupMap.keySet();
	}

	/**
	 * Indicate if the Category identified by the Number is available in the Map
	 * 
	 * @param categoryNumber The Category Number
	 * 
	 * @return TRUE - The Category identified by the Number is available in the Map
	 */

	public static final boolean ContainsCategory (
		final int categoryNumber)
	{
		return s_FXRiskGroupMap.containsKey (categoryNumber);
	}

	/**
	 * Retrieve the Risk Group identified by the Category Number
	 * 
	 * @param categoryNumber The Category Number
	 * 
	 * @return The Risk Group identified by the Category Number
	 */

	public static final org.drip.simm.fx.FXRiskGroup FXRiskGroup (
		final int categoryNumber)
	{
		return ContainsCategory (categoryNumber) ? s_FXRiskGroupMap.get (categoryNumber) : null;
	}

	/**
	 * Retrieve the Delta Threshold for the Category specified
	 * 
	 * @param categoryNumber The specified Category
	 * 
	 * @return Delta Threshold for the Category specified
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double CategoryDeltaThreshold (
		final int categoryNumber)
		throws java.lang.Exception
	{
		if (!s_CategoryDelta.containsKey (categoryNumber))
		{
			throw new java.lang.Exception
				("FXRiskThresholdContainer::CategoryDeltaThreshold => Invalid Category");
		}

		return s_CategoryDelta.get (categoryNumber);
	}

	/**
	 * Retrieve the Vega Threshold for the Category Pair specified
	 * 
	 * @param categoryNumber1 The specified Category #1
	 * @param categoryNumber2 The specified Category #2
	 * 
	 * @return Vega Threshold for the Category Pair specified
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double CategoryVegaThreshold (
		final int categoryNumber1,
		final int categoryNumber2)
		throws java.lang.Exception
	{
		java.lang.String categoryVegaThresholdKey = categoryNumber1 + "__" + categoryNumber2;

		if (!s_CategoryVega.containsKey (categoryVegaThresholdKey))
		{
			throw new java.lang.Exception
				("FXRiskThresholdContainer::CategoryVegaThreshold => Invalid Category");
		}

		return s_CategoryVega.get (categoryVegaThresholdKey);
	}

	/**
	 * Retrieve the FX Risk Group Map
	 * 
	 * @return The FX Risk Group Map
	 */

	public static final java.util.Map<java.lang.Integer, org.drip.simm.fx.FXRiskGroup>
		FXRiskGroupMap()
	{
		return s_FXRiskGroupMap;
	}

	/**
	 * Retrieve the Category Delta Concentration Threshold Map
	 * 
	 * @return The Category Delta Concentration Threshold Map
	 */

	public static final java.util.Map<java.lang.Integer, java.lang.Double> CategoryDeltaMap()
	{
		return s_CategoryDelta;
	}

	/**
	 * Retrieve the Category Vega Concentration Threshold Map
	 * 
	 * @return The Category Vega Concentration Threshold Map
	 */

	public static final java.util.Map<java.lang.String, java.lang.Double> CategoryVegaMap()
	{
		return s_CategoryVega;
	}
}
