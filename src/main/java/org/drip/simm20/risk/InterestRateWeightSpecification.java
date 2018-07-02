
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
 * InterestRateWeight holds the ISDA SIMM 2.0 Tenor Vertex Risk Weights for Currencies across all
 * 	Volatility Types. The References are:
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
	 * Same Currency Inflation Rate Risk Weight
	 */

	public static final double SINGLE_CURRENCY_INFLATION_RISK_WEIGHT = 46.;

	/**
	 * Same Currency Cross-Currency Basis Swap Spread
	 */

	public static final double SINGLE_CURRENCY_XCCY_BASIS_SWAP_SPREAD_RISK_WEIGHT = 20.;

	private static final java.util.Map<java.lang.String, org.drip.simm20.risk.InterestRateWeight>
		s_InterestRateRiskWeight = new java.util.HashMap<java.lang.String,
			org.drip.simm20.risk.InterestRateWeight>();

	/**
	 * Initialize the Interest Rate Weight Specification Container
	 * 
	 * @return TRUE - The Interest Rate Weight Specification Container successfully initialized
	 */

	public static final boolean Init()
	{
		try
		{
			
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}
		return true;
	}

	/**
	 * Retrieve the Set of all Available Currencies
	 * 
	 * @return The Set of all Available Currencies
	 */

	public static final java.util.Set<java.lang.String> AvailableCurrencySet()
	{
		return s_InterestRateRiskWeight.keySet();
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
			irRiskWeightMapEntry : s_InterestRateRiskWeight.entrySet())
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

	public boolean ContainsRiskWeight (
		final java.lang.String currency)
	{
		return null != currency && !currency.isEmpty() && s_InterestRateRiskWeight.containsKey (currency);
	}

	/**
	 * Retrieve the IR Risk Weight for the specified Currency
	 * 
	 * @param currency The Currency
	 * 
	 * @return TRUE - The IR Risk Weight for the specified Currency
	 */

	public org.drip.simm20.risk.InterestRateWeight RiskWeight (
		final java.lang.String currency)
	{
		return ContainsRiskWeight (currency) ? s_InterestRateRiskWeight.get (currency) : null;
	}

	/**
	 * Retrieve the Interest Rate Risk Weight Term Structure based on the Volatility Type
	 * 
	 * @return The Interest Rate Risk Weight Term Structure based on the Volatility Type
	 */

	public static final java.util.Map<java.lang.String, org.drip.simm20.risk.InterestRateWeight>
		InterestRateRiskWeight()
	{
		return s_InterestRateRiskWeight;
	}
}
