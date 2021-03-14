
package org.drip.simm.rates;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>IRSettingsContainer21</i> holds the ISDA SIMM 2.1 Tenor Vertex Risk Weights/Correlations for Single IR
 * Curves, Cross Currencies, and Inflation. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  			Calculations https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  			Framework for Forecasting Initial Margin Requirements
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin
 *  			Requirements - A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167
 *  				<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
 *  			https://www.isda.org/a/oFiDE/isda-simm-v2.pdf
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/rates/README.md">SIMM IR Risk Factor Settings</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class IRSettingsContainer21
{
	private static org.drip.simm.rates.IRWeight ZERO_RISK_WEIGHT = null;
	private static org.drip.measure.stochastic.LabelCorrelation s_SingleCurveTenorCorrelation = null;

	private static final java.util.Map<java.lang.String, org.drip.simm.rates.IRWeight> s_RiskWeightMap =
		new java.util.HashMap<java.lang.String, org.drip.simm.rates.IRWeight>();

	private static final boolean TenorCorrelation()
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
			s_SingleCurveTenorCorrelation = new org.drip.measure.stochastic.LabelCorrelation (
				tenorList,
				new double[][]
				{
					{1.00, 0.63, 0.59, 0.47, 0.31, 0.22, 0.18, 0.14, 0.09, 0.06, 0.04, 0.05},  //  2W
					{0.63, 1.00, 0.79, 0.67, 0.52, 0.42, 0.37, 0.30, 0.23, 0.18, 0.15, 0.13},  //  1M
					{0.59, 0.79, 1.00, 0.84, 0.68, 0.56, 0.50, 0.42, 0.32, 0.26, 0.24, 0.21},  //  3M
					{0.47, 0.67, 0.84, 1.00, 0.86, 0.76, 0.69, 0.60, 0.48, 0.42, 0.38, 0.33},  //  6M
					{0.31, 0.52, 0.68, 0.86, 1.00, 0.94, 0.89, 0.80, 0.67, 0.60, 0.57, 0.53},  //  1Y
					{0.22, 0.42, 0.56, 0.76, 0.94, 1.00, 0.98, 0.91, 0.79, 0.73, 0.70, 0.66},  //  2Y
					{0.18, 0.37, 0.50, 0.69, 0.89, 0.98, 1.00, 0.96, 0.87, 0.81, 0.78, 0.74},  //  3Y
					{0.14, 0.30, 0.42, 0.60, 0.80, 0.91, 0.96, 1.00, 0.95, 0.91, 0.88, 0.84},  //  5Y
					{0.09, 0.23, 0.32, 0.48, 0.67, 0.79, 0.87, 0.95, 1.00, 0.98, 0.97, 0.94},  // 10Y
					{0.06, 0.18, 0.26, 0.42, 0.60, 0.73, 0.81, 0.91, 0.98, 1.00, 0.99, 0.97},  // 15Y
					{0.04, 0.15, 0.24, 0.38, 0.57, 0.70, 0.78, 0.88, 0.97, 0.99, 1.00, 0.99},  // 20Y
					{0.05, 0.13, 0.21, 0.33, 0.53, 0.66, 0.74, 0.84, 0.94, 0.97, 0.99, 1.00}   // 30Y
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
		org.drip.simm.rates.IRWeight lowVolatilityRiskWeight = null;
		org.drip.simm.rates.IRWeight highVolatilityRiskWeight = null;
		org.drip.simm.rates.IRWeight regularVolatilityRiskWeight = null;

		java.util.Map<java.lang.String, java.lang.Double> zeroIRWeight = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		java.util.Map<java.lang.String, java.lang.Double> tenorDeltaWeightLowVolatility = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		java.util.Map<java.lang.String, java.lang.Double> tenorDeltaWeightHighVolatility = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		java.util.Map<java.lang.String, java.lang.Double> tenorDeltaWeightRegularVolatility = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		java.util.Map<java.lang.String, java.lang.Double> tenorVegaRiskWeight = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		tenorVegaRiskWeight.put (
			"2W",
			org.drip.simm.rates.IRSystemics21.VEGA_RISK_WEIGHT
		);

		tenorVegaRiskWeight.put (
			"1M",
			org.drip.simm.rates.IRSystemics21.VEGA_RISK_WEIGHT
		);

		tenorVegaRiskWeight.put (
			"3M",
			org.drip.simm.rates.IRSystemics21.VEGA_RISK_WEIGHT
		);

		tenorVegaRiskWeight.put (
			"6M",
			org.drip.simm.rates.IRSystemics21.VEGA_RISK_WEIGHT
		);

		tenorVegaRiskWeight.put (
			"1Y",
			org.drip.simm.rates.IRSystemics21.VEGA_RISK_WEIGHT
		);

		tenorVegaRiskWeight.put (
			"2Y",
			org.drip.simm.rates.IRSystemics21.VEGA_RISK_WEIGHT
		);

		tenorVegaRiskWeight.put (
			"3Y",
			org.drip.simm.rates.IRSystemics21.VEGA_RISK_WEIGHT
		);

		tenorVegaRiskWeight.put (
			"5Y",
			org.drip.simm.rates.IRSystemics21.VEGA_RISK_WEIGHT
		);

		tenorVegaRiskWeight.put (
			"10Y",
			org.drip.simm.rates.IRSystemics21.VEGA_RISK_WEIGHT
		);

		tenorVegaRiskWeight.put (
			"15Y",
			org.drip.simm.rates.IRSystemics21.VEGA_RISK_WEIGHT
		);

		tenorVegaRiskWeight.put (
			"20Y",
			org.drip.simm.rates.IRSystemics21.VEGA_RISK_WEIGHT
		);

		tenorVegaRiskWeight.put (
			"30Y",
			org.drip.simm.rates.IRSystemics21.VEGA_RISK_WEIGHT
		);

		tenorDeltaWeightRegularVolatility.put (
			"2W",
			114.
		);

		tenorDeltaWeightRegularVolatility.put (
			"1M",
			115.
		);

		tenorDeltaWeightRegularVolatility.put (
			"3M",
			102.
		);

		tenorDeltaWeightRegularVolatility.put (
			"6M",
			71.
		);

		tenorDeltaWeightRegularVolatility.put (
			"1Y",
			61.
		);

		tenorDeltaWeightRegularVolatility.put (
			"2Y",
			52.
		);

		tenorDeltaWeightRegularVolatility.put (
			"3Y",
			50.
		);

		tenorDeltaWeightRegularVolatility.put (
			"5Y",
			51.
		);

		tenorDeltaWeightRegularVolatility.put (
			"10Y",
			51.
		);

		tenorDeltaWeightRegularVolatility.put (
			"15Y",
			51.
		);

		tenorDeltaWeightRegularVolatility.put (
			"20Y",
			54.
		);

		tenorDeltaWeightRegularVolatility.put (
			"30Y",
			62.
		);

		tenorDeltaWeightLowVolatility.put (
			"2W",
			33.
		);

		tenorDeltaWeightLowVolatility.put (
			"1M",
			20.
		);

		tenorDeltaWeightLowVolatility.put (
			"3M",
			10.
		);

		tenorDeltaWeightLowVolatility.put (
			"6M",
			10.
		);

		tenorDeltaWeightLowVolatility.put (
			"1Y",
			14.
		);

		tenorDeltaWeightLowVolatility.put (
			"2Y",
			20.
		);

		tenorDeltaWeightLowVolatility.put (
			"3Y",
			22.
		);

		tenorDeltaWeightLowVolatility.put (
			"5Y",
			20.
		);

		tenorDeltaWeightLowVolatility.put (
			"10Y",
			20.
		);

		tenorDeltaWeightLowVolatility.put (
			"15Y",
			21.
		);

		tenorDeltaWeightLowVolatility.put (
			"20Y",
			23.
		);

		tenorDeltaWeightLowVolatility.put (
			"30Y",
			27.
		);

		tenorDeltaWeightHighVolatility.put (
			"2W",
			91.
		);

		tenorDeltaWeightHighVolatility.put (
			"1M",
			91.
		);

		tenorDeltaWeightHighVolatility.put (
			"3M",
			95.
		);

		tenorDeltaWeightHighVolatility.put (
			"6M",
			88.
		);

		tenorDeltaWeightHighVolatility.put (
			"1Y",
			99.
		);

		tenorDeltaWeightHighVolatility.put (
			"2Y",
			101.
		);

		tenorDeltaWeightHighVolatility.put (
			"3Y",
			101.
		);

		tenorDeltaWeightHighVolatility.put (
			"5Y",
			99.
		);

		tenorDeltaWeightHighVolatility.put (
			"10Y",
			108.
		);

		tenorDeltaWeightHighVolatility.put (
			"15Y",
			100.
		);

		tenorDeltaWeightHighVolatility.put (
			"20Y",
			101.
		);

		tenorDeltaWeightHighVolatility.put (
			"30Y",
			101.
		);

		zeroIRWeight.put (
			"2W",
			0.
		);

		zeroIRWeight.put (
			"1M",
			0.
		);

		zeroIRWeight.put (
			"3M",
			0.
		);

		zeroIRWeight.put (
			"6M",
			0.
		);

		zeroIRWeight.put (
			"1Y",
			0.
		);

		zeroIRWeight.put (
			"2Y",
			0.
		);

		zeroIRWeight.put (
			"3Y",
			0.
		);

		zeroIRWeight.put (
			"5Y",
			0.
		);

		zeroIRWeight.put (
			"10Y",
			0.
		);

		zeroIRWeight.put (
			"15Y",
			0.
		);

		zeroIRWeight.put (
			"20Y",
			0.
		);

		zeroIRWeight.put (
			"30Y",
			0.
		);

		try
		{
			regularVolatilityRiskWeight = new org.drip.simm.rates.IRWeight (
				org.drip.simm.rates.IRSystemics.VOLATILITY_TYPE_REGULAR,
				tenorDeltaWeightRegularVolatility,
				tenorVegaRiskWeight
			);

			lowVolatilityRiskWeight = new org.drip.simm.rates.IRWeight (
				org.drip.simm.rates.IRSystemics.VOLATILITY_TYPE_LOW,
				tenorDeltaWeightLowVolatility,
				tenorVegaRiskWeight
			);

			highVolatilityRiskWeight = new org.drip.simm.rates.IRWeight (
				org.drip.simm.rates.IRSystemics.VOLATILITY_TYPE_HIGH,
				tenorDeltaWeightHighVolatility,
				tenorVegaRiskWeight
			);

			ZERO_RISK_WEIGHT = new org.drip.simm.rates.IRWeight (
				org.drip.simm.rates.IRSystemics.VOLATILITY_TYPE_NULL,
				zeroIRWeight,
				tenorVegaRiskWeight
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		s_RiskWeightMap.put (
			"AUD",
			regularVolatilityRiskWeight
		);

		s_RiskWeightMap.put (
			"CAD",
			regularVolatilityRiskWeight
		);

		s_RiskWeightMap.put (
			"CHF",
			regularVolatilityRiskWeight
		);

		s_RiskWeightMap.put (
			"DKK",
			regularVolatilityRiskWeight
		);

		s_RiskWeightMap.put (
			"EUR",
			regularVolatilityRiskWeight
		);

		s_RiskWeightMap.put (
			"GBP",
			regularVolatilityRiskWeight
		);

		s_RiskWeightMap.put (
			"HKD",
			regularVolatilityRiskWeight
		);

		s_RiskWeightMap.put (
			"JPY",
			lowVolatilityRiskWeight
		);

		s_RiskWeightMap.put (
			"KRW",
			regularVolatilityRiskWeight
		);

		s_RiskWeightMap.put (
			"NOK",
			regularVolatilityRiskWeight
		);

		s_RiskWeightMap.put (
			"OTHER",
			highVolatilityRiskWeight
		);

		s_RiskWeightMap.put (
			"USD",
			regularVolatilityRiskWeight
		);

		s_RiskWeightMap.put (
			"SEK",
			regularVolatilityRiskWeight
		);

		s_RiskWeightMap.put (
			"SGD",
			regularVolatilityRiskWeight
		);

		s_RiskWeightMap.put (
			"TWD",
			regularVolatilityRiskWeight
		);

		s_RiskWeightMap.put (
			"USD",
			regularVolatilityRiskWeight
		);

		return TenorCorrelation();
	}

	/**
	 * Retrieve the Standard ISDA Rates Tenor Set
	 * 
	 * @return The Standard ISDA Rates Tenor Set
	 */

	public static final java.util.Set<java.lang.String> TenorSet()
	{
		return s_RiskWeightMap.get ("USD").tenors();
	}

	/**
	 * Indicate if the Sub-Curve is supported for the specified Currency
	 * 
	 * @param currency The Currency
	 * @param subCurve The sub-Curve Type
	 * 
	 * @return TRUE - The Sub-Curve is supported for the specified Currency
	 */

	public static final boolean SubCurveSupported (
		final java.lang.String currency,
		final java.lang.String subCurve)
	{
		if (null == currency || currency.isEmpty() || null == subCurve)
		{
			return false;
		}

		if (org.drip.simm.rates.IRSystemics.SUB_CURVE_OIS.equalsIgnoreCase (subCurve) ||
			org.drip.simm.rates.IRSystemics.SUB_CURVE_LIBOR_1M.equalsIgnoreCase (subCurve) ||
			org.drip.simm.rates.IRSystemics.SUB_CURVE_LIBOR_3M.equalsIgnoreCase (subCurve) ||
			org.drip.simm.rates.IRSystemics.SUB_CURVE_LIBOR_6M.equalsIgnoreCase (subCurve) ||
			org.drip.simm.rates.IRSystemics.SUB_CURVE_LIBOR_12M.equalsIgnoreCase (subCurve))
		{
			return true;
		}

		if (org.drip.simm.rates.IRSystemics.SUB_CURVE_PRIME.equalsIgnoreCase (subCurve) ||
			org.drip.simm.rates.IRSystemics.SUB_CURVE_MUNICIPAL.equalsIgnoreCase (subCurve))
		{
			return "USD".equalsIgnoreCase (currency);
		}

		return false;
	}

	/**
	 * Retrieve the Set of all Available Currencies
	 * 
	 * @return The Set of all Available Currencies
	 */

	public static final java.util.Set<java.lang.String> CurrencySet()
	{
		return s_RiskWeightMap.keySet();
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

		for (java.util.Map.Entry<java.lang.String, org.drip.simm.rates.IRWeight> irRiskWeightMapEntry :
			s_RiskWeightMap.entrySet())
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
		return VolatilityTypeCurrencySet (org.drip.simm.rates.IRSystemics.VOLATILITY_TYPE_REGULAR);
	}

	/**
	 * Retrieve the Low Volatility Currency Set
	 * 
	 * @return The Low Volatility Currency Set
	 */

	public static final java.util.Set<java.lang.String> LowVolatilityCurrencySet()
	{
		return VolatilityTypeCurrencySet (org.drip.simm.rates.IRSystemics.VOLATILITY_TYPE_LOW);
	}

	/**
	 * Retrieve the High Volatility Currency Set
	 * 
	 * @return The High Volatility Currency Set
	 */

	public static final java.util.Set<java.lang.String> HighVolatilityCurrencySet()
	{
		return VolatilityTypeCurrencySet (org.drip.simm.rates.IRSystemics.VOLATILITY_TYPE_HIGH);
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
		return null != currency && !currency.isEmpty() && s_RiskWeightMap.containsKey (currency);
	}

	/**
	 * Indicate if the IR Risk Weight is available for the specified Currency
	 * 
	 * @param currency The Currency
	 * @param subCurve The sub-Curve Type
	 * 
	 * @return TRUE - The IR Risk Weight is available for the specified Currency
	 */

	public static final boolean ContainsRiskWeight (
		final java.lang.String currency,
		final java.lang.String subCurve)
	{
		return SubCurveSupported (
			currency,
			subCurve
		) && s_RiskWeightMap.containsKey (currency);
	}

	/**
	 * Retrieve the IR Risk Weight for the specified Currency
	 * 
	 * @param currency The Currency
	 * 
	 * @return The IR Risk Weight for the specified Currency
	 */

	public static final org.drip.simm.rates.IRWeight RiskWeight (
		final java.lang.String currency)
	{
		return ContainsRiskWeight (currency) ? s_RiskWeightMap.get (currency) : s_RiskWeightMap.get
			("OTHER");
	}

	/**
	 * Retrieve the IR Risk Weight for the specified Currency
	 * 
	 * @param currency The Currency
	 * @param subCurve The sub-Curve Type
	 * 
	 * @return The IR Risk Weight for the specified Currency
	 */

	public static final org.drip.simm.rates.IRWeight RiskWeight (
		final java.lang.String currency,
		final java.lang.String subCurve)
	{
		if (!SubCurveSupported (
			currency,
			subCurve
		))
		{
			return ZERO_RISK_WEIGHT;
		}

		return ContainsRiskWeight (currency) ? s_RiskWeightMap.get (currency) : s_RiskWeightMap.get
			("OTHER");
	}

	/**
	 * Retrieve the Interest Rate Single Curve Tenor Correlation Instance
	 * 
	 * @return The Interest Rate Single Curve Tenor Correlation Instance
	 */

	public static final org.drip.measure.stochastic.LabelCorrelation SingleCurveTenorCorrelation()
	{
		return s_SingleCurveTenorCorrelation;
	}

	/**
	 * Retrieve the Interest Rate Risk Weight Term Structure based on the Volatility Type
	 * 
	 * @return The Interest Rate Risk Weight Term Structure based on the Volatility Type
	 */

	public static final java.util.Map<java.lang.String, org.drip.simm.rates.IRWeight> RiskWeight()
	{
		return s_RiskWeightMap;
	}

	/**
	 * Retrieve the Currency Pair Principal Co-variance Matrix
	 * 
	 * @param currency1 Currency #1
	 * @param currency2 Currency #2
	 * 
	 * @return The Currency Pair Principal Co-variance Matrix
	 */

	public static final org.drip.simm.foundation.RiskGroupPrincipalCovariance CurrencyPairPrincipalCovariance (
		final java.lang.String currency1,
		final java.lang.String currency2)
	{
		if (null == currency1 || currency1.isEmpty() ||
			null == currency2 || currency2.isEmpty())
		{
			return null;
		}

		org.drip.simm.rates.IRThreshold irThreshold1 = org.drip.simm.rates.IRThresholdContainer21.Threshold
			(currency1);

		org.drip.simm.rates.IRThreshold irThreshold2 = org.drip.simm.rates.IRThresholdContainer21.Threshold
			(currency2);

		if (null == irThreshold1 || null == irThreshold2)
		{
			return null;
		}

		return org.drip.simm.foundation.RiskGroupPrincipalCovariance.Standard (
			s_SingleCurveTenorCorrelation.matrix(),
			irThreshold1.currencyRiskGroup().volatilityType().equalsIgnoreCase (
				irThreshold2.currencyRiskGroup().volatilityType()
			) ? 1. : org.drip.simm.rates.IRSystemics21.SINGLE_CURRENCY_CROSS_CURVE_CORRELATION
		);
	}
}
