
package org.drip.simm20.risk;

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
 * InterestRateWeightSpecification holds the ISDA SIMM 2.0 Tenor Vertex Risk Weights/Correlations for Single
 * 	Curves, Cross Currencies, and Inflation. The References are:
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

public class InterestRateWeightSpecification
{

	/**
	 * Interest Rate Type - Regular Volatility
	 */

	public static final java.lang.String VOLATILITY_TYPE_REGULAR = "REGULAR";

	/**
	 * Interest Rate Type - Low Volatility
	 */

	public static final java.lang.String VOLATILITY_TYPE_LOW = "LOW";

	/**
	 * Interest Rate Type - High Volatility
	 */

	public static final java.lang.String VOLATILITY_TYPE_HIGH = "HIGH";

	/**
	 * Same Currency Curve Inflation Rate Risk Weight
	 */

	public static final double SINGLE_CURRENCY_CURVE_INFLATION_RISK_WEIGHT = 46.;

	/**
	 * Single Currency Single Curve Basis Swap Spread
	 */

	public static final double SINGLE_CURRENCY_CURVE_BASIS_SWAP_SPREAD_RISK_WEIGHT = 20.;

	/**
	 * Interest Rate Vega Risk Weight
	 */

	public static final double VEGA_RISK_WEIGHT = 0.21;

	/**
	 * Single Currency Cross-Curve Correlation
	 */

	public static final double SINGLE_CURRENCY_CROSS_CURVE_CORRELATION = 0.98;

	/**
	 * Single Currency Curve Inflation Correlation
	 */

	public static final double SINGLE_CURRENCY_CURVE_INFLATION_CORRELATION = 0.29;

	/**
	 * Single Currency Curve Inflation Volatility Correlation
	 */

	public static final double SINGLE_CURRENCY_CURVE_INFLATION_VOLATILITY_CORRELATION = 0.29;

	/**
	 * Single Currency Curve Basis Swap Spread Correlation
	 */

	public static final double SINGLE_CURRENCY_CURVE_BASIS_SWAP_SPREAD_CORRELATION = 0.20;

	/**
	 * Single Currency Basis Swap Spread Inflation Correlation
	 */

	public static final double SINGLE_CURRENCY_BASIS_SWAP_SPREAD_INFLATION_CORRELATION = 0.20;

	/**
	 * Cross Currency Curve Correlation
	 */

	public static final double CROSS_CURRENCY_CORRELATION = 0.23;

	private static org.drip.simm20.risk.InterestRateTenorCorrelation s_Correlation = null;

	private static final java.util.Map<java.lang.String, org.drip.simm20.risk.InterestRateWeight>
		s_RiskWeight = new java.util.HashMap<java.lang.String, org.drip.simm20.risk.InterestRateWeight>();

	private static final boolean SetupTenorCorrelation()
	{
		java.util.List<java.lang.String> tenorList = new java.util.ArrayList<java.lang.String>();

		tenorList.add ("2W");

		tenorList.add ("1M");

		tenorList.add ("3M");

		tenorList.add ("6M");

		tenorList.add ("1Y");

		tenorList.add ("2Y");

		tenorList.add ("3Y");

		tenorList.add ("5Y");

		tenorList.add ("10Y");

		tenorList.add ("15Y");

		tenorList.add ("20Y");

		tenorList.add ("30Y");

		try
		{
			s_Correlation = new org.drip.simm20.risk.InterestRateTenorCorrelation (
				tenorList,
				new double[][]
				{
					{1.00, 0.99, 0.79, 0.67, 0.53, 0.42, 0.37, 0.30, 0.22, 0.18, 0.16, 0.12},
					{0.99, 1.00, 0.79, 0.67, 0.53, 0.42, 0.37, 0.30, 0.22, 0.18, 0.16, 0.12},
					{0.79, 0.79, 1.00, 0.85, 0.69, 0.57, 0.50, 0.42, 0.32, 0.25, 0.23, 0.20},
					{0.67, 0.67, 0.85, 1.00, 0.86, 0.76, 0.69, 0.59, 0.47, 0.40, 0.37, 0.32},
					{0.53, 0.53, 0.69, 0.86, 1.00, 0.93, 0.87, 0.77, 0.63, 0.57, 0.54, 0.50},
					{0.42, 0.42, 0.57, 0.76, 0.93, 1.00, 0.98, 0.90, 0.77, 0.70, 0.67, 0.63},
					{0.37, 0.37, 0.50, 0.69, 0.87, 0.98, 1.00, 0.96, 0.84, 0.78, 0.75, 0.71},
					{0.30, 0.30, 0.42, 0.59, 0.77, 0.90, 0.96, 1.00, 0.93, 0.89, 0.86, 0.82},
					{0.22, 0.22, 0.32, 0.47, 0.63, 0.77, 0.84, 0.93, 1.00, 0.98, 0.96, 0.94},
					{0.18, 0.18, 0.25, 0.40, 0.57, 0.70, 0.78, 0.89, 0.98, 1.00, 0.99, 0.98},
					{0.16, 0.16, 0.23, 0.37, 0.54, 0.67, 0.75, 0.86, 0.96, 0.99, 1.00, 0.99},
					{0.12, 0.12, 0.20, 0.32, 0.50, 0.63, 0.71, 0.82, 0.94, 0.98, 0.99, 1.00}
				}
			);

			return true;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Initialize the Interest Rate Weight Specification Container
	 * 
	 * @return TRUE - The Interest Rate Weight Specification Container successfully initialized
	 */

	public static final boolean Init()
	{
		org.drip.simm20.risk.InterestRateWeight lowVolatilityRiskWeight = null;
		org.drip.simm20.risk.InterestRateWeight highVolatilityRiskWeight = null;
		org.drip.simm20.risk.InterestRateWeight regularVolatilityRiskWeight = null;

		java.util.Map<java.lang.String, java.lang.Double> tenorWeightMapLowVolatility = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		java.util.Map<java.lang.String, java.lang.Double> tenorWeightMapHighVolatility = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		java.util.Map<java.lang.String, java.lang.Double> tenorWeightMapRegularVolatility = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		tenorWeightMapRegularVolatility.put (
			"2W",
			113.
		);

		tenorWeightMapRegularVolatility.put (
			"1M",
			113.
		);

		tenorWeightMapRegularVolatility.put (
			"3M",
			98.
		);

		tenorWeightMapRegularVolatility.put (
			"6M",
			69.
		);

		tenorWeightMapRegularVolatility.put (
			"1Y",
			56.
		);

		tenorWeightMapRegularVolatility.put (
			"2Y",
			52.
		);

		tenorWeightMapRegularVolatility.put (
			"3Y",
			51.
		);

		tenorWeightMapRegularVolatility.put (
			"5Y",
			51.
		);

		tenorWeightMapRegularVolatility.put (
			"10Y",
			51.
		);

		tenorWeightMapRegularVolatility.put (
			"15Y",
			53.
		);

		tenorWeightMapRegularVolatility.put (
			"20Y",
			56.
		);

		tenorWeightMapRegularVolatility.put (
			"30Y",
			64.
		);

		tenorWeightMapHighVolatility.put (
			"2W",
			21.
		);

		tenorWeightMapLowVolatility.put (
			"1M",
			21.
		);

		tenorWeightMapLowVolatility.put (
			"3M",
			10.
		);

		tenorWeightMapLowVolatility.put (
			"6M",
			11.
		);

		tenorWeightMapLowVolatility.put (
			"1Y",
			15.
		);

		tenorWeightMapLowVolatility.put (
			"2Y",
			20.
		);

		tenorWeightMapLowVolatility.put (
			"3Y",
			22.
		);

		tenorWeightMapLowVolatility.put (
			"5Y",
			21.
		);

		tenorWeightMapLowVolatility.put (
			"10Y",
			19.
		);

		tenorWeightMapLowVolatility.put (
			"15Y",
			20.
		);

		tenorWeightMapLowVolatility.put (
			"20Y",
			23.
		);

		tenorWeightMapLowVolatility.put (
			"30Y",
			27.
		);

		tenorWeightMapHighVolatility.put (
			"2W",
			93.
		);

		tenorWeightMapHighVolatility.put (
			"1M",
			93.
		);

		tenorWeightMapHighVolatility.put (
			"3M",
			90.
		);

		tenorWeightMapHighVolatility.put (
			"6M",
			94.
		);

		tenorWeightMapHighVolatility.put (
			"1Y",
			97.
		);

		tenorWeightMapHighVolatility.put (
			"2Y",
			103.
		);

		tenorWeightMapHighVolatility.put (
			"3Y",
			101.
		);

		tenorWeightMapHighVolatility.put (
			"5Y",
			103.
		);

		tenorWeightMapHighVolatility.put (
			"10Y",
			102.
		);

		tenorWeightMapHighVolatility.put (
			"15Y",
			101.
		);

		tenorWeightMapHighVolatility.put (
			"20Y",
			102.
		);

		tenorWeightMapHighVolatility.put (
			"30Y",
			101.
		);

		try
		{
			regularVolatilityRiskWeight = new org.drip.simm20.risk.InterestRateWeight (
				VOLATILITY_TYPE_REGULAR,
				tenorWeightMapRegularVolatility
			);

			lowVolatilityRiskWeight = new org.drip.simm20.risk.InterestRateWeight (
				VOLATILITY_TYPE_LOW,
				tenorWeightMapLowVolatility
			);

			highVolatilityRiskWeight = new org.drip.simm20.risk.InterestRateWeight (
				VOLATILITY_TYPE_HIGH,
				tenorWeightMapHighVolatility
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		s_RiskWeight.put (
			"AUD",
			regularVolatilityRiskWeight
		);

		s_RiskWeight.put (
			"CAD",
			regularVolatilityRiskWeight
		);

		s_RiskWeight.put (
			"CHF",
			regularVolatilityRiskWeight
		);

		s_RiskWeight.put (
			"DKK",
			regularVolatilityRiskWeight
		);

		s_RiskWeight.put (
			"EUR",
			regularVolatilityRiskWeight
		);

		s_RiskWeight.put (
			"GBP",
			regularVolatilityRiskWeight
		);

		s_RiskWeight.put (
			"HKD",
			regularVolatilityRiskWeight
		);

		s_RiskWeight.put (
			"JPY",
			lowVolatilityRiskWeight
		);

		s_RiskWeight.put (
			"KRW",
			regularVolatilityRiskWeight
		);

		s_RiskWeight.put (
			"NOK",
			regularVolatilityRiskWeight
		);

		s_RiskWeight.put (
			"OTHER",
			highVolatilityRiskWeight
		);

		s_RiskWeight.put (
			"USD",
			regularVolatilityRiskWeight
		);

		s_RiskWeight.put (
			"SEK",
			regularVolatilityRiskWeight
		);

		s_RiskWeight.put (
			"SGD",
			regularVolatilityRiskWeight
		);

		s_RiskWeight.put (
			"TWD",
			regularVolatilityRiskWeight
		);

		s_RiskWeight.put (
			"USD",
			regularVolatilityRiskWeight
		);

		return SetupTenorCorrelation();
	}

	/**
	 * Retrieve the Set of all Available Currencies
	 * 
	 * @return The Set of all Available Currencies
	 */

	public static final java.util.Set<java.lang.String> AvailableCurrencySet()
	{
		return s_RiskWeight.keySet();
	}

	/**
	 * Retrieve the Set of Currencies for the specified Volatility Type
	 * 
	 * @param volatilityType The Volatility Type
	 * 
	 * @return The Set of Currencies for the specified Volatility Type
	 */

	public static final java.util.Set<java.lang.String> VolatilityTypeCurrencySet (
		final java.lang.String volatilityType)
	{
		if (null == volatilityType || volatilityType.isEmpty())
		{
			return null;
		}

		java.util.Set<java.lang.String> currencySet = new java.util.HashSet<java.lang.String>();

		for (java.util.Map.Entry<java.lang.String, org.drip.simm20.risk.InterestRateWeight>
			irRiskWeightMapEntry : s_RiskWeight.entrySet())
		{
			if (irRiskWeightMapEntry.getValue().volatilityType().equalsIgnoreCase (volatilityType))
			{
				currencySet.add (irRiskWeightMapEntry.getKey());
			}
		}

		return currencySet;
	}

	/**
	 * Retrieve the Regular Volatility Currency Set
	 * 
	 * @return The Regular Volatility Currency Set
	 */

	public static final java.util.Set<java.lang.String> RegularVolatilityCurrencySet()
	{
		return VolatilityTypeCurrencySet (VOLATILITY_TYPE_REGULAR);
	}

	/**
	 * Retrieve the Low Volatility Currency Set
	 * 
	 * @return The Low Volatility Currency Set
	 */

	public static final java.util.Set<java.lang.String> LowVolatilityCurrencySet()
	{
		return VolatilityTypeCurrencySet (VOLATILITY_TYPE_LOW);
	}

	/**
	 * Retrieve the High Volatility Currency Set
	 * 
	 * @return The High Volatility Currency Set
	 */

	public static final java.util.Set<java.lang.String> HighVolatilityCurrencySet()
	{
		return VolatilityTypeCurrencySet (VOLATILITY_TYPE_HIGH);
	}

	/**
	 * Indicate if the IR Risk Weight is available for the specified Currency
	 * 
	 * @param currency The Currency
	 * 
	 * @return TRUE - The IR Risk Weight is available for the specified Currency
	 */

	public static final boolean ContainsRiskWeight (
		final java.lang.String currency)
	{
		return null != currency && !currency.isEmpty() && s_RiskWeight.containsKey (currency);
	}

	/**
	 * Retrieve the IR Risk Weight for the specified Currency
	 * 
	 * @param currency The Currency
	 * 
	 * @return TRUE - The IR Risk Weight for the specified Currency
	 */

	public static final org.drip.simm20.risk.InterestRateWeight RiskWeight (
		final java.lang.String currency)
	{
		return ContainsRiskWeight (currency) ? s_RiskWeight.get (currency) :
			s_RiskWeight.get ("OTHER");
	}

	/**
	 * Retrieve the Interest Rate Tenor Correlation Instance
	 * 
	 * @return The Interest Rate Tenor Correlation Instance
	 */

	public static final org.drip.simm20.risk.InterestRateTenorCorrelation correlation()
	{
		return s_Correlation;
	}

	/**
	 * Retrieve the Interest Rate Risk Weight Term Structure based on the Volatility Type
	 * 
	 * @return The Interest Rate Risk Weight Term Structure based on the Volatility Type
	 */

	public static final java.util.Map<java.lang.String, org.drip.simm20.risk.InterestRateWeight>
		InterestRateRiskWeight()
	{
		return s_RiskWeight;
	}
}
