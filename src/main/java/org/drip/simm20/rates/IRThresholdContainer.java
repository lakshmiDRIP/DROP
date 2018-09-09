
package org.drip.simm20.rates;

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
 * IRThresholdContainer holds the ISDA SIMM 2.0 Interest Rate Thresholds - the Currency Risk Groups, and the
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

public class IRThresholdContainer
{
	private static final java.util.Map<java.lang.String, java.lang.Integer> s_CurrencyThresholdMap = new
		java.util.HashMap<java.lang.String, java.lang.Integer>();

	private static final java.util.Map<java.lang.Integer, org.drip.simm20.rates.IRThreshold> s_ThresholdMap =
		new java.util.TreeMap<java.lang.Integer, org.drip.simm20.rates.IRThreshold>();

	private static final boolean SetupCurrencyMap()
	{
		s_CurrencyThresholdMap.put (
			"USD",
			2
		);

		s_CurrencyThresholdMap.put (
			"EUR",
			2
		);

		s_CurrencyThresholdMap.put (
			"GBP",
			2
		);

		s_CurrencyThresholdMap.put (
			"AUD",
			3
		);

		s_CurrencyThresholdMap.put (
			"CAD",
			3
		);

		s_CurrencyThresholdMap.put (
			"CHF",
			3
		);

		s_CurrencyThresholdMap.put (
			"DKK",
			3
		);

		s_CurrencyThresholdMap.put (
			"HKD",
			3
		);

		s_CurrencyThresholdMap.put (
			"KRW",
			3
		);

		s_CurrencyThresholdMap.put (
			"NOK",
			3
		);

		s_CurrencyThresholdMap.put (
			"NZD",
			3
		);

		s_CurrencyThresholdMap.put (
			"SEK",
			3
		);

		s_CurrencyThresholdMap.put (
			"SGD",
			3
		);

		s_CurrencyThresholdMap.put (
			"TWD",
			3
		);

		s_CurrencyThresholdMap.put (
			"JPY",
			4
		);

		return true;
	}

	/**
	 * Initialize the Container
	 * 
	 * @return TRUE - The Container successfully Initialized
	 */

	public static final boolean Init()
	{
		try
		{
			s_ThresholdMap.put (
				1,
				new org.drip.simm20.rates.IRThreshold (
					new org.drip.simm20.rates.CurrencyRiskGroup (
						org.drip.simm20.rates.IRSystemics.VOLATILITY_TYPE_HIGH,
						org.drip.simm20.rates.IRSystemics.TRADE_FREQUENCY_LESS_WELL_TRADED,
						new java.lang.String[]
						{
							"Other"
						}
					),
					new org.drip.simm20.common.DeltaVegaThreshold (
						8.,
						110.
					)
				)
			);

			s_ThresholdMap.put (
				2,
				new org.drip.simm20.rates.IRThreshold (
					new org.drip.simm20.rates.CurrencyRiskGroup (
						org.drip.simm20.rates.IRSystemics.VOLATILITY_TYPE_REGULAR,
						org.drip.simm20.rates.IRSystemics.TRADE_FREQUENCY_WELL_TRADED,
						new java.lang.String[]
						{
							"USD",
							"EUR",
							"GBP"
						}
					),
					new org.drip.simm20.common.DeltaVegaThreshold (
						230.,
						2700.
					)
				)
			);

			s_ThresholdMap.put (
				3,
				new org.drip.simm20.rates.IRThreshold (
					new org.drip.simm20.rates.CurrencyRiskGroup (
						org.drip.simm20.rates.IRSystemics.VOLATILITY_TYPE_REGULAR,
						org.drip.simm20.rates.IRSystemics.TRADE_FREQUENCY_LESS_WELL_TRADED,
						new java.lang.String[]
						{
							"AUD",
							"CAD",
							"CHF",
							"DKK",
							"HKD",
							"KRW",
							"NOK",
							"NZD",
							"SEK",
							"SGD",
							"TWD"
						}
					),
					new org.drip.simm20.common.DeltaVegaThreshold (
						28.,
						150.
					)
				)
			);

			s_ThresholdMap.put (
				4,
				new org.drip.simm20.rates.IRThreshold (
					new org.drip.simm20.rates.CurrencyRiskGroup (
						org.drip.simm20.rates.IRSystemics.VOLATILITY_TYPE_LOW,
						org.drip.simm20.rates.IRSystemics.TRADE_FREQUENCY_WELL_TRADED,
						new java.lang.String[]
						{
							"JPY"
						}
					),
					new org.drip.simm20.common.DeltaVegaThreshold (
						82.,
						960.
					)
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return SetupCurrencyMap();
	}

	/**
	 * Retrieve the Interest Rate Threshold Container Bucket Index Set
	 * 
	 * @return The Interest Rate Threshold Container Bucket Index Set
	 */

	public static final java.util.Set<java.lang.Integer> IndexSet()
	{
		return s_ThresholdMap.keySet();
	}

	/**
	 * Retrieve the Interest Rate Threshold Container Currency Set
	 * 
	 * @return The Interest Rate Threshold Container Currency Set
	 */

	public static final java.util.Set<java.lang.String> CurrencySet()
	{
		return s_CurrencyThresholdMap.keySet();
	}

	/**
	 * Indicate if the Entry denoted by the Number is available as an Interest Rate Threshold
	 * 
	 * @param groupNumber The Group Number
	 * 
	 * @return TRUE - The Entry denoted by the Number is available as an Interest Rate Threshold
	 */

	public static final boolean ContainsThreshold (
		final int groupNumber)
	{
		return s_ThresholdMap.containsKey (groupNumber);
	}

	/**
	 * Indicate if the Currency is available as an Interest Rate Threshold
	 * 
	 * @param currency The Currency
	 * 
	 * @return TRUE - The Currency is available as an Interest Rate Threshold
	 */

	public static final boolean ContainsThreshold (
		final java.lang.String currency)
	{
		return s_CurrencyThresholdMap.containsKey (currency);
	}

	/**
	 * Retrieve the Interest Rate Threshold denoted by the Currency
	 * 
	 * @param currency The Currency
	 * 
	 * @return The Interest Rate Threshold
	 */

	public static final org.drip.simm20.rates.IRThreshold Threshold (
		final java.lang.String currency)
	{
		return ContainsThreshold (currency) ? s_ThresholdMap.get (s_CurrencyThresholdMap.get (currency)) :
			s_ThresholdMap.get (1);
	}

	/**
	 * Retrieve the Interest Rate Threshold denoted by the Group Number
	 * 
	 * @param groupNumber The Group Number
	 * 
	 * @return The Interest Rate Threshold
	 */

	public static final org.drip.simm20.rates.IRThreshold Threshold (
		final int groupNumber)
	{
		return ContainsThreshold (groupNumber) ? s_ThresholdMap.get (groupNumber) : null;
	}

	/**
	 * Retrieve the Currency Threshold Map
	 * 
	 * @return The Currency Threshold Map
	 */

	public static final java.util.Map<java.lang.String, java.lang.Integer> CurrencyThresholdMap()
	{
		return s_CurrencyThresholdMap;
	}

	/**
	 * Retrieve the Interest Rate Threshold Map
	 * 
	 * @return The Interest Rate Threshold Map
	 */

	public static final java.util.Map<java.lang.Integer, org.drip.simm20.rates.IRThreshold> ThresholdMap()
	{
		return s_ThresholdMap;
	}
}
