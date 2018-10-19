
package org.drip.sample.simm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.simm.estimator.ProductClassMargin;
import org.drip.simm.estimator.ProductClassSensitivity;
import org.drip.simm.estimator.ProductClassSettings;
import org.drip.simm.fx.FXRiskThresholdContainer20;
import org.drip.simm.margin.RiskClassAggregate;
import org.drip.simm.product.BucketSensitivity;
import org.drip.simm.product.RiskClassSensitivity;
import org.drip.simm.product.RiskMeasureSensitivity;

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
 * ProductMargin20 illustrates the Computation of the ISDA SIMM 2.0 Product Margin for across a Group of Risk
 *  Factor Exposure Sensitivities. The References are:
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

public class ProductMargin20
{

	private static final void AddEquityBucketRiskFactorSensitivity (
		final Map<String, Map<String, Double>> bucketRiskFactorSensitivityMap,
		final int bucketIndex,
		final double notional,
		final String[] equityArray)
	{
		Map<String, Double> riskFactorSensitivityMap = new CaseInsensitiveHashMap<Double>();

		for (String equity : equityArray)
		{
			riskFactorSensitivityMap.put (
				equity,
				notional * (Math.random() - 0.5)
			);
		}

		bucketRiskFactorSensitivityMap.put (
			"" + bucketIndex,
			riskFactorSensitivityMap
		);
	}

	private static final void AddCommodityBucketRiskFactorSensitivity (
		final Map<String, Map<String, Double>> bucketRiskFactorSensitivityMap,
		final int bucketIndex,
		final double notional,
		final String commodity)
	{
		Map<String, Double> riskFactorSensitivityMap = new CaseInsensitiveHashMap<Double>();

		riskFactorSensitivityMap.put (
			commodity,
			notional * (Math.random() - 0.5)
		);

		bucketRiskFactorSensitivityMap.put (
			"" + bucketIndex,
			riskFactorSensitivityMap
		);
	}

	private static final Map<String, Map<String, Double>> FXCategorySensitivityMap (
		final String[] currencyArray,
		final double notional)
		throws Exception
	{
		Map<String, Map<String, Double>> currencySentivityMap = new TreeMap<String, Map<String, Double>>();

		for (String currency : currencyArray)
		{
			int categoryIndex = FXRiskThresholdContainer20.CurrencyCategory (currency);

			if (currencySentivityMap.containsKey ("" + categoryIndex))
			{
				Map<String, Double> riskFactorSensitivityMap = currencySentivityMap.get ("" + categoryIndex);

				riskFactorSensitivityMap.put (
					currency,
					notional * (Math.random() - 0.5)
				);
			}
			else
			{
				Map<String, Double> riskFactorSensitivityMap = new CaseInsensitiveHashMap<Double>();

				riskFactorSensitivityMap.put (
					currency,
					notional * (Math.random() - 0.5)
				);

				currencySentivityMap.put (
					"" + categoryIndex,
					riskFactorSensitivityMap
				);
			}
		}

		return currencySentivityMap;
	}

	private static final Map<String, Map<String, Double>> EquityRiskFactorSensitivityMap (
		final double notional)
		throws Exception
	{
		Map<String, Map<String, Double>> bucketRiskFactorSensitivityMap =
			new TreeMap<String, Map<String, Double>>();

		AddEquityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			-1,
			notional,
			new String[]
			{
				"BOEING  ",
				"LOCKHEED",
				"RAND    ",
				"RAYTHEON",
			}
		);

		AddEquityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			1,
			notional,
			new String[]
			{
				"ADP     ",
				"PSEANDG ",
				"STAPLES ",
				"U-HAUL  ",
			}
		);

		AddEquityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			2,
			notional,
			new String[]
			{
				"CISCO   ",
				"DEERE   ",
				"HALIBTN ",
				"VERIZON ",
			}
		);

		AddEquityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			3,
			notional,
			new String[]
			{
				"DUKE    ",
				"MONSANTO",
				"MMM     ",
				"VEDANTA ",
			}
		);

		AddEquityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			4,
			notional,
			new String[]
			{
				"AMAZON  ",
				"GOLDMAN ",
				"MORGAN  ",
				"REMAX   ",
			}
		);

		AddEquityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			5,
			notional,
			new String[]
			{
				"ALDI    ",
				"INFOSYS ",
				"OLLA    ",
				"RELIANCE",
			}
		);

		AddEquityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			6,
			notional,
			new String[]
			{
				"GCC     ",
				"NOKIA   ",
				"SIEMENS ",
				"VODAFONE",
			}
		);

		AddEquityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			7,
			notional,
			new String[]
			{
				"ADIDAS  ",
				"BAYER   ",
				"BILLERTN",
				"DE BEER ",
			}
		);

		AddEquityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			8,
			notional,
			new String[]
			{
				"NOKIA   ",
				"NOMURA  ",
				"QATARSOV",
				"SOTHEBY ",
			}
		);

		AddEquityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			9,
			notional,
			new String[]
			{
				"AUTODESK",
				"CALYPSO ",
				"NUMERIX ",
				"WEBLOGIC",
			}
		);

		AddEquityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			10,
			notional,
			new String[]
			{
				"COGNIZAN",
				"TATAMOTO",
				"TOBLERON",
				"TVS     ",
			}
		);

		AddEquityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			11,
			notional,
			new String[]
			{
				"DJIA    ",
				"LEHMAN  ",
				"RUSSELL ",
				"SANDP   ",
			}
		);

		AddEquityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			12,
			notional,
			new String[]
			{
				"CBOE    ",
				"CITI    ",
				"RUSSELL ",
				"VIX     ",
			}
		);

		return bucketRiskFactorSensitivityMap;
	}

	private static final Map<String, Map<String, Double>> CommodityRiskFactorSensitivityMap (
		final double notional)
		throws Exception
	{
		Map<String, Map<String, Double>> bucketRiskFactorSensitivityMap =
			new HashMap<String, Map<String, Double>>();

		AddCommodityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			1,
			notional,
			"COAL                          "
		);

		AddCommodityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			2,
			notional,
			"CRUDE                         "
		);

		AddCommodityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			3,
			notional,
			"LIGHT ENDS                    "
		);

		AddCommodityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			4,
			notional,
			"MIDDLE DISTILLATES            "
		);

		AddCommodityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			5,
			notional,
			"HEAVY DISTILLATES             "
		);

		AddCommodityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			6,
			notional,
			"NORTH AMERICAN NATURAL GAS    "
		);

		AddCommodityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			7,
			notional,
			"EUROPEAN NATURAL GAS          "
		);

		AddCommodityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			8,
			notional,
			"NORTH AMERICAN POWER          "
		);

		AddCommodityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			9,
			notional,
			"EUROPEAN POWER                "
		);

		AddCommodityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			10,
			notional,
			"FREIGHT                       "
		);

		AddCommodityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			11,
			notional,
			"BASE METALS                   "
		);

		AddCommodityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			12,
			notional,
			"PRECIOUS METALS               "
		);

		AddCommodityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			13,
			notional,
			"GRAINS                        "
		);

		AddCommodityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			14,
			notional,
			"SOFTS                         "
		);

		AddCommodityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			15,
			notional,
			"LIVESTOCK                     "
		);

		AddCommodityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			16,
			notional,
			"OTHER                         "
		);

		AddCommodityBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			17,
			notional,
			"INDEXES                       "
		);

		return bucketRiskFactorSensitivityMap;
	}

	private static final void AddFXBucketRiskFactorSensitivity (
		final Map<String, Map<String, Double>> bucketRiskFactorSensitivityMap,
		final String bucketKey,
		final double notional,
		final String[] fxPairArray)
	{
		Map<String, Double> riskFactorSensitivityMap = new CaseInsensitiveHashMap<Double>();

		for (String fxPair : fxPairArray)
		{
			riskFactorSensitivityMap.put (
				fxPair,
				notional * (Math.random() - 0.5)
			);
		}

		bucketRiskFactorSensitivityMap.put (
			bucketKey,
			riskFactorSensitivityMap
		);
	}

	private static final Map<String, BucketSensitivity> BucketVegaSensitivityMap (
		final double notional)
		throws Exception
	{
		Map<String, Map<String, Double>> bucketVegaMap = new TreeMap<String, Map<String, Double>>();

		AddFXBucketRiskFactorSensitivity (
			bucketVegaMap,
			"1__1",
			notional,
			new String[]
			{
				"USD_EUR",
				"USD_JPY",
				"USD_GBP",
				"USD_AUD",
			}
		);

		AddFXBucketRiskFactorSensitivity (
			bucketVegaMap,
			"1__2",
			notional,
			new String[]
			{
				"USD_BRL",
				"USD_CNY",
				"USD_HKD",
				"USD_INR",
			}
		);

		AddFXBucketRiskFactorSensitivity (
			bucketVegaMap,
			"2__1",
			notional,
			new String[]
			{
				"BRL_USD",
				"CNY_USD",
				"HKD_USD",
				"INR_USD",
			}
		);

		AddFXBucketRiskFactorSensitivity (
			bucketVegaMap,
			"2__2",
			notional,
			new String[]
			{
				"BRL_CNY",
				"BRL_KDD",
				"BRL_INR",
				"BRL_KRW",
			}
		);

		AddFXBucketRiskFactorSensitivity (
			bucketVegaMap,
			"1__3",
			notional,
			new String[]
			{
				"USD_IDR",
				"USD_PKR",
				"USD_SRL",
				"USD_BNT",
			}
		);

		AddFXBucketRiskFactorSensitivity (
			bucketVegaMap,
			"2__3",
			notional,
			new String[]
			{
				"BRL_IDR",
				"BRL_PKR",
				"BRL_SRL",
				"BRL_BNT",
			}
		);

		AddFXBucketRiskFactorSensitivity (
			bucketVegaMap,
			"3__1",
			notional,
			new String[]
			{
				"IDR_USD",
				"PKR_USD",
				"SRL_USD",
				"BNT_USD",
			}
		);

		AddFXBucketRiskFactorSensitivity (
			bucketVegaMap,
			"3__2",
			notional,
			new String[]
			{
				"IDR_BRL",
				"PKR_BRL",
				"SRL_BRL",
				"BNT_BRL",
			}
		);

		AddFXBucketRiskFactorSensitivity (
			bucketVegaMap,
			"3__3",
			notional,
			new String[]
			{
				"IDR_PKR",
				"PKR_SRL",
				"SRL_IDR",
				"BNT_SRL",
			}
		);

		Map<String, BucketSensitivity> bucketVegaSensitivityMap = new TreeMap<String, BucketSensitivity>();

		for (Map.Entry<String, Map<String, Double>> bucketVegaMapEntry : bucketVegaMap.entrySet())
		{
			bucketVegaSensitivityMap.put (
				bucketVegaMapEntry.getKey(),
				new BucketSensitivity (bucketVegaMapEntry.getValue())
			);
		}

		return bucketVegaSensitivityMap;
	}

	private static final RiskClassSensitivity EquitySensitivity (
		final double notional,
		final int vegaDurationDays)
		throws Exception
	{
		Map<String, Map<String, Double>> bucketDeltaMap = EquityRiskFactorSensitivityMap (notional);

		Map<String, BucketSensitivity> bucketDeltaSensitivityMap = new HashMap<String, BucketSensitivity>();

		for (Map.Entry<String, Map<String, Double>> bucketDeltaMapEntry : bucketDeltaMap.entrySet())
		{
			bucketDeltaSensitivityMap.put (
				bucketDeltaMapEntry.getKey(),
				new BucketSensitivity (bucketDeltaMapEntry.getValue())
			);
		}

		Map<String, Map<String, Double>> bucketVegaMap = EquityRiskFactorSensitivityMap (notional);

		Map<String, BucketSensitivity> bucketVegaSensitivityMap = new HashMap<String, BucketSensitivity>();

		for (Map.Entry<String, Map<String, Double>> bucketVegaMapEntry : bucketVegaMap.entrySet())
		{
			bucketVegaSensitivityMap.put (
				bucketVegaMapEntry.getKey(),
				new BucketSensitivity (bucketVegaMapEntry.getValue())
			);
		}

		return new RiskClassSensitivity (
			new RiskMeasureSensitivity (bucketDeltaSensitivityMap),
			new RiskMeasureSensitivity (bucketVegaSensitivityMap),
			new RiskMeasureSensitivity (bucketVegaSensitivityMap)
		);
	}

	private static final RiskClassSensitivity CommoditySensitivity (
		final double notional,
		final int vegaDurationDays)
		throws Exception
	{
		Map<String, Map<String, Double>> bucketDeltaMap = CommodityRiskFactorSensitivityMap (notional);

		Map<String, BucketSensitivity> bucketDeltaSensitivityMap = new HashMap<String, BucketSensitivity>();

		for (Map.Entry<String, Map<String, Double>> bucketDeltaMapEntry : bucketDeltaMap.entrySet())
		{
			bucketDeltaSensitivityMap.put (
				bucketDeltaMapEntry.getKey(),
				new BucketSensitivity (bucketDeltaMapEntry.getValue())
			);
		}

		Map<String, Map<String, Double>> bucketVegaMap = CommodityRiskFactorSensitivityMap (notional);

		Map<String, BucketSensitivity> bucketVegaSensitivityMap = new HashMap<String, BucketSensitivity>();

		for (Map.Entry<String, Map<String, Double>> bucketVegaMapEntry : bucketVegaMap.entrySet())
		{
			bucketVegaSensitivityMap.put (
				bucketVegaMapEntry.getKey(),
				new BucketSensitivity (bucketVegaMapEntry.getValue())
			);
		}

		return new RiskClassSensitivity (
			new RiskMeasureSensitivity (bucketDeltaSensitivityMap),
			new RiskMeasureSensitivity (bucketVegaSensitivityMap),
			new RiskMeasureSensitivity (bucketVegaSensitivityMap)
		);
	}

	private static final RiskClassSensitivity FXSensitivity (
		final String[] currencyArray,
		final double notional)
		throws Exception
	{
		Map<String, Map<String, Double>> bucketDeltaMap = FXCategorySensitivityMap (
			currencyArray,
			notional
		);

		Map<String, BucketSensitivity> bucketDeltaSensitivityMap = new TreeMap<String, BucketSensitivity>();

		for (Map.Entry<String, Map<String, Double>> deltaCategoryMapEntry : bucketDeltaMap.entrySet())
		{
			bucketDeltaSensitivityMap.put (
				deltaCategoryMapEntry.getKey(),
				new BucketSensitivity (deltaCategoryMapEntry.getValue())
			);
		}

		Map<String, BucketSensitivity> bucketVegaSensitivityMap = BucketVegaSensitivityMap (notional);

		return new RiskClassSensitivity (
			new RiskMeasureSensitivity (bucketDeltaSensitivityMap),
			new RiskMeasureSensitivity (bucketVegaSensitivityMap),
			new RiskMeasureSensitivity (bucketVegaSensitivityMap)
		);
	}

	public static final void main (
		final String[] inputArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double notional = 100.;
		int vegaDurationDays = 365;

		String[] currencyArray = {
			"USD",
			"EUR",
			"CNY",
			"INR",
			"JPY"
		};

		List<String> currencyList = new ArrayList<String>();

		for (String currency : currencyArray)
		{
			currencyList.add (currency);
		}

		ProductClassSettings productClassSettings = ProductClassSettings.ISDA_20 (
			currencyList,
			vegaDurationDays
		);

		RiskClassSensitivity equityRiskClassSensitivity = EquitySensitivity (
			notional,
			vegaDurationDays
		);

		RiskClassAggregate equityRiskClassAggregate = equityRiskClassSensitivity.aggregate
			(productClassSettings.equityRiskClassSensitivitySettings());

		RiskClassSensitivity commodityRiskClassSensitivity = CommoditySensitivity (
			notional,
			vegaDurationDays
		);

		RiskClassAggregate commodityRiskClassAggregate = commodityRiskClassSensitivity.aggregate
			(productClassSettings.commodityRiskClassSensitivitySettings());

		RiskClassSensitivity fxRiskClassSensitivity = FXSensitivity (
			currencyArray,
			notional
		);

		RiskClassAggregate fxRiskClassAggregate = fxRiskClassSensitivity.aggregate
			(productClassSettings.fxRiskClassSensitivitySettings());

		System.out.println ("\t|------------------------------------||");

		System.out.println (
			"\t| PRODUCT EQUITY MARGIN    => " +
			FormatUtil.FormatDouble (equityRiskClassAggregate.margin(), 5, 0, 1.) + " ||"
		);

		System.out.println (
			"\t| PRODUCT COMMODITY MARGIN => " +
			FormatUtil.FormatDouble (commodityRiskClassAggregate.margin(), 5, 0, 1.) + " ||"
		);

		System.out.println (
			"\t| PRODUCT FX     MARGIN    => " +
			FormatUtil.FormatDouble (fxRiskClassAggregate.margin(), 5, 0, 1.) + " ||"
		);

		System.out.println ("\t|------------------------------------||");

		System.out.println();

		ProductClassSensitivity productClassSensitivity = new ProductClassSensitivity (
			equityRiskClassSensitivity,
			commodityRiskClassSensitivity,
			fxRiskClassSensitivity,
			null, // irRiskClassSensitivity,
			null, // creditQualifyingRiskClassSensitivity,
			null // creditNonQualifyingRiskClassSensitivity
		);

		ProductClassMargin productClassMargin = productClassSensitivity.estimate (productClassSettings);

		System.out.println ("\t|------------------------------------||");

		System.out.println (
			"\t| PRODUCT EQUITY MARGIN    => " +
			FormatUtil.FormatDouble (
				productClassMargin.equityRiskClassAggregate().margin(), 5, 0, 1.
			) + " ||"
		);

		System.out.println (
			"\t| PRODUCT COMMODITY MARGIN => " +
			FormatUtil.FormatDouble (
				productClassMargin.commodityRiskClassAggregate().margin(), 5, 0, 1.
			) + " ||"
		);

		System.out.println (
			"\t| PRODUCT FX MARGIN        => " +
			FormatUtil.FormatDouble (
				productClassMargin.fxRiskClassAggregate().margin(), 5, 0, 1.
			) + " ||"
		);

		System.out.println ("\t|------------------------------------||");

		System.out.println (
			"\t| PRODUCT TOTAL MARGIN     => " +
			FormatUtil.FormatDouble (
				productClassMargin.total (productClassSettings.labelCorrelation()), 5, 0, 1.
			) + " ||"
		);

		System.out.println ("\t|------------------------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
