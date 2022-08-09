
package org.drip.sample.simm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.simm.estimator.ProductClassMargin;
import org.drip.simm.estimator.ProductClassSensitivity;
import org.drip.simm.estimator.ProductClassSettings;
import org.drip.simm.foundation.MarginEstimationSettings;
import org.drip.simm.fx.FXRiskThresholdContainer21;
import org.drip.simm.margin.RiskClassAggregate;
import org.drip.simm.margin.RiskClassAggregateCR;
import org.drip.simm.margin.RiskClassAggregateIR;
import org.drip.simm.product.BucketSensitivity;
import org.drip.simm.product.BucketSensitivityCR;
import org.drip.simm.product.BucketSensitivityIR;
import org.drip.simm.product.RiskClassSensitivity;
import org.drip.simm.product.RiskClassSensitivityCR;
import org.drip.simm.product.RiskClassSensitivityIR;
import org.drip.simm.product.RiskFactorTenorSensitivity;
import org.drip.simm.product.RiskMeasureSensitivity;
import org.drip.simm.product.RiskMeasureSensitivityCR;
import org.drip.simm.product.RiskMeasureSensitivityIR;

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
 * <i>ProductMargin21</i> illustrates the Computation of the ISDA SIMM 2.1 Product Margin for across a Group
 * 	of Risk Factor Exposure Sensitivities. The References are:
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simm/README.md">ISDA Product SIMM Margin Estimation</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ProductMargin21
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
			String categoryIndexKey = "" + FXRiskThresholdContainer21.CurrencyCategory (
				currency
				);

			if (currencySentivityMap.containsKey (
				categoryIndexKey
			))
			{
				Map<String, Double> riskFactorSensitivityMap = currencySentivityMap.get (
					categoryIndexKey
				);

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
					categoryIndexKey,
					riskFactorSensitivityMap
				);
			}
		}

		return currencySentivityMap;
	}

	private static final RiskFactorTenorSensitivity IRCurveTenorSensitivityMap (
		final double notional)
		throws Exception
	{
		Map<String, Double> tenorSensitivityMap = new HashMap<String, Double>();

		tenorSensitivityMap.put (
			"2W",
			notional * (Math.random() - 0.5)
		);

		tenorSensitivityMap.put (
			"1M",
			notional * (Math.random() - 0.5)
		);

		tenorSensitivityMap.put (
			"3M",
			notional * (Math.random() - 0.5)
		);

		tenorSensitivityMap.put (
			"6M",
			notional * (Math.random() - 0.5)
		);

		tenorSensitivityMap.put (
			"1Y",
			notional * (Math.random() - 0.5)
		);

		tenorSensitivityMap.put (
			"2Y",
			notional * (Math.random() - 0.5)
		);

		tenorSensitivityMap.put (
			"3Y",
			notional * (Math.random() - 0.5)
		);

		tenorSensitivityMap.put (
			"5Y",
			notional * (Math.random() - 0.5)
		);

		tenorSensitivityMap.put (
			"10Y",
			notional * (Math.random() - 0.5)
		);

		tenorSensitivityMap.put (
			"15Y",
			notional * (Math.random() - 0.5)
		);

		tenorSensitivityMap.put (
			"20Y",
			notional * (Math.random() - 0.5)
		);

		tenorSensitivityMap.put (
			"30Y",
			notional * (Math.random() - 0.5)
		);

		return new RiskFactorTenorSensitivity (
			tenorSensitivityMap
		);
	}

	private static final void AddCreditTenorSensitivity (
		final Map<String, Double> tenorSensitivityMap,
		final double notional,
		final String tenor)
		throws Exception
	{
		if (tenorSensitivityMap.containsKey (
			tenor
		))
		{
			tenorSensitivityMap.put (
				tenor,
				tenorSensitivityMap.get (
					tenor
				) + notional * (Math.random() - 0.5)
			);
		}
		else
		{
			tenorSensitivityMap.put (
				tenor,
				notional * (Math.random() - 0.5)
			);
		}
	}

	private static final RiskFactorTenorSensitivity CreditCurveTenorSensitivityMap (
		final double notional)
		throws Exception
	{
		Map<String, Double> tenorSensitivityMap = new HashMap<String, Double>();

		AddCreditTenorSensitivity (
			tenorSensitivityMap,
			notional,
			"1Y"
		);

		AddCreditTenorSensitivity (
			tenorSensitivityMap,
			notional,
			"2Y"
		);

		AddCreditTenorSensitivity (
			tenorSensitivityMap,
			notional,
			"3Y"
		);

		AddCreditTenorSensitivity (
			tenorSensitivityMap,
			notional,
			"5Y"
		);

		AddCreditTenorSensitivity (
			tenorSensitivityMap,
			notional,
			"10Y"
		);

		return new RiskFactorTenorSensitivity (
			tenorSensitivityMap
		);
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

	private static final Map<String, BucketSensitivity> BucketFXVegaSensitivityMap (
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
				new BucketSensitivity (
					bucketVegaMapEntry.getValue()
				)
			);
		}

		return bucketVegaSensitivityMap;
	}

	private static final BucketSensitivityIR IRCurrencyBucketSensitivity (
		final String currency,
		final double notional)
		throws Exception
	{
		return new BucketSensitivityIR (
			IRCurveTenorSensitivityMap (
				notional
			),
			IRCurveTenorSensitivityMap (
				notional
			),
			IRCurveTenorSensitivityMap (
				notional
			),
			IRCurveTenorSensitivityMap (
				notional
			),
			IRCurveTenorSensitivityMap (
				notional
			),
			IRCurveTenorSensitivityMap (
				notional
			),
			IRCurveTenorSensitivityMap (
				notional
			)
		);
	}

	private static final void CreditComponentRiskFactorTenorSensitivity (
		final Map<String, RiskFactorTenorSensitivity> tenorSensitivityMap,
		final double notional,
		final String componentName)
		throws Exception
	{
		tenorSensitivityMap.put (
			componentName,
			CreditCurveTenorSensitivityMap (
				notional
			)
		);
	}

	private static final RiskClassSensitivity EquitySensitivity (
		final double notional,
		final int vegaDurationDays)
		throws Exception
	{
		Map<String, BucketSensitivity> bucketDeltaSensitivityMap = new HashMap<String, BucketSensitivity>();

		for (Map.Entry<String, Map<String, Double>> bucketDeltaMapEntry : EquityRiskFactorSensitivityMap (
				notional
			).entrySet()
		)
		{
			bucketDeltaSensitivityMap.put (
				bucketDeltaMapEntry.getKey(),
				new BucketSensitivity (
					bucketDeltaMapEntry.getValue()
				)
			);
		}

		Map<String, BucketSensitivity> bucketVegaSensitivityMap = new HashMap<String, BucketSensitivity>();

		for (Map.Entry<String, Map<String, Double>> bucketVegaMapEntry : EquityRiskFactorSensitivityMap (
				notional
			).entrySet()
		)
		{
			bucketVegaSensitivityMap.put (
				bucketVegaMapEntry.getKey(),
				new BucketSensitivity (
					bucketVegaMapEntry.getValue()
				)
			);
		}

		return new RiskClassSensitivity (
			new RiskMeasureSensitivity (
				bucketDeltaSensitivityMap
			),
			new RiskMeasureSensitivity (
				bucketVegaSensitivityMap
			),
			new RiskMeasureSensitivity (
				bucketVegaSensitivityMap
			)
		);
	}

	private static final RiskClassSensitivity CommoditySensitivity (
		final double notional,
		final int vegaDurationDays)
		throws Exception
	{
		Map<String, BucketSensitivity> bucketDeltaSensitivityMap = new HashMap<String, BucketSensitivity>();

		for (Map.Entry<String, Map<String, Double>> bucketDeltaMapEntry : CommodityRiskFactorSensitivityMap (
				notional
			).entrySet()
		)
		{
			bucketDeltaSensitivityMap.put (
				bucketDeltaMapEntry.getKey(),
				new BucketSensitivity (
					bucketDeltaMapEntry.getValue()
				)
			);
		}

		Map<String, BucketSensitivity> bucketVegaSensitivityMap = new HashMap<String, BucketSensitivity>();

		for (Map.Entry<String, Map<String, Double>> bucketVegaMapEntry : CommodityRiskFactorSensitivityMap (
				notional
			).entrySet()
		)
		{
			bucketVegaSensitivityMap.put (
				bucketVegaMapEntry.getKey(),
				new BucketSensitivity (
					bucketVegaMapEntry.getValue()
				)
			);
		}

		return new RiskClassSensitivity (
			new RiskMeasureSensitivity (
				bucketDeltaSensitivityMap
			),
			new RiskMeasureSensitivity (
				bucketVegaSensitivityMap
			),
			new RiskMeasureSensitivity (
				bucketVegaSensitivityMap
			)
		);
	}

	private static final RiskClassSensitivity FXSensitivity (
		final String[] currencyArray,
		final double notional)
		throws Exception
	{
		Map<String, BucketSensitivity> bucketDeltaSensitivityMap = new TreeMap<String, BucketSensitivity>();

		for (Map.Entry<String, Map<String, Double>> deltaCategoryMapEntry : FXCategorySensitivityMap (
				currencyArray,
				notional
			).entrySet()
		)
		{
			bucketDeltaSensitivityMap.put (
				deltaCategoryMapEntry.getKey(),
				new BucketSensitivity (
					deltaCategoryMapEntry.getValue()
				)
			);
		}

		Map<String, BucketSensitivity> bucketVegaSensitivityMap = BucketFXVegaSensitivityMap (
			notional
		);

		return new RiskClassSensitivity (
			new RiskMeasureSensitivity (
				bucketDeltaSensitivityMap
			),
			new RiskMeasureSensitivity (
				bucketVegaSensitivityMap
			),
			new RiskMeasureSensitivity (
				bucketVegaSensitivityMap
			)
		);
	}

	private static final RiskClassSensitivityIR IRSensitivity (
		final String[] currencyArray,
		final double[] notionalArray)
		throws Exception
	{
		Map<String, BucketSensitivityIR> bucketDeltaSensitivityMap =
			new HashMap<String, BucketSensitivityIR>();

		Map<String, BucketSensitivityIR> bucketVegaSensitivityMap =
			new HashMap<String, BucketSensitivityIR>();

		for (int currencyIndex = 0;
			currencyIndex < currencyArray.length;
			++currencyIndex)
		{
			bucketDeltaSensitivityMap.put (
				currencyArray[currencyIndex],
				IRCurrencyBucketSensitivity (
					currencyArray[currencyIndex],
					notionalArray[currencyIndex]
				)
			);

			bucketVegaSensitivityMap.put (
				currencyArray[currencyIndex],
				IRCurrencyBucketSensitivity (
					currencyArray[currencyIndex],
					notionalArray[currencyIndex]
				)
			);
		}

		return new RiskClassSensitivityIR (
			new RiskMeasureSensitivityIR (
				bucketDeltaSensitivityMap
			),
			new RiskMeasureSensitivityIR (
				bucketVegaSensitivityMap
			),
			new RiskMeasureSensitivityIR (
				bucketVegaSensitivityMap
			)
		);
	}

	private static final RiskClassSensitivityCR CreditSensitivity (
		final String[][] bucketComponentGrid,
		final int[] bucketIndexArray,
		final double notional)
		throws Exception
	{
		Map<String, BucketSensitivityCR> bucketDeltaSensitivityMap =
			new HashMap<String, BucketSensitivityCR>();

		Map<String, BucketSensitivityCR> bucketVegaSensitivityMap =
			new HashMap<String, BucketSensitivityCR>();

		for (int bucketIndex : bucketIndexArray)
		{
			Map<String, RiskFactorTenorSensitivity> tenorDeltaSensitivityMap = new
				CaseInsensitiveHashMap<RiskFactorTenorSensitivity>();

			Map<String, RiskFactorTenorSensitivity> tenorVegaSensitivityMap = new
				CaseInsensitiveHashMap<RiskFactorTenorSensitivity>();

			for (String componentName : bucketComponentGrid[bucketIndex - 1])
			{
				CreditComponentRiskFactorTenorSensitivity (
					tenorDeltaSensitivityMap,
					notional,
					componentName
				);

				CreditComponentRiskFactorTenorSensitivity (
					tenorVegaSensitivityMap,
					notional,
					componentName
				);
			}

			String bucketIndexKey = "" + bucketIndex;

			bucketDeltaSensitivityMap.put (
				bucketIndexKey,
				new BucketSensitivityCR (
					tenorDeltaSensitivityMap
				)
			);

			bucketVegaSensitivityMap.put (
				bucketIndexKey,
				new BucketSensitivityCR (
					tenorVegaSensitivityMap
				)
			);
		}

		return new RiskClassSensitivityCR (
			new RiskMeasureSensitivityCR (
				bucketDeltaSensitivityMap
			),
			new RiskMeasureSensitivityCR (
				bucketVegaSensitivityMap
			),
			new RiskMeasureSensitivityCR (
				bucketVegaSensitivityMap
			)
		);
	}

	public static final void main (
		final String[] inputArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double notional = 100.;
		int vegaDurationDays = 365;
		String[] fxCurrencyArray = {
			"USD",
			"EUR",
			"CNY",
			"INR",
			"JPY"
		};
		String[] irCurrencyArray = {
			"USD",
			"EUR",
			"CNY",
			"INR",
			"JPY"
		};

		double[] irNotionalArray = {
			100.,
			108.,
			119.,
			 49.,
			 28.
		};
		int[] crqBucketIndexArray = {
			 1,
			 2,
			 3,
			 4,
			 5,
			 6,
			 7,
			 8,
			 9,
			10,
			11,
			12,
		};
		String[][] crqBucketComponentGrid = {
			{"01a", "01b", "01c", "01d", "01e", "01f"},
			{"02a", "02b", "02c", "02d", "02e", "02f"},
			{"03a", "03b", "03c", "03d", "03e", "03f"},
			{"04a", "04b", "04c", "04d", "04e", "04f"},
			{"05a", "05b", "05c", "05d", "05e", "05f"},
			{"06a", "06b", "06c", "06d", "06e", "06f"},
			{"07a", "07b", "07c", "07d", "07e", "07f"},
			{"08a", "08b", "08c", "08d", "08e", "08f"},
			{"09a", "09b", "09c", "09d", "09e", "09f"},
			{"10a", "10b", "10c", "10d", "10e", "10f"},
			{"11a", "11b", "11c", "11d", "11e", "11f"},
			{"12a", "12b", "12c", "12d", "12e", "12f"},
		};
		int[] crnqBucketIndexArray = {
			 1,
			 2,
		};
		String[][] crnqBucketComponentGrid = {
			{"01a", "01b", "01c", "01d", "01e", "01f"},
			{"02a", "02b", "02c", "02d", "02e", "02f"},
		};

		List<String> fxCurrencyList = new ArrayList<String>();

		for (String fxCurrency : fxCurrencyArray)
		{
			fxCurrencyList.add (fxCurrency);
		}

		MarginEstimationSettings marginEstimationSettings = MarginEstimationSettings.CornishFischer
			(MarginEstimationSettings.POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_ISDA);

		ProductClassSettings productClassSettings = ProductClassSettings.ISDA_21 (
			fxCurrencyList,
			vegaDurationDays
		);

		RiskClassSensitivity equityRiskClassSensitivity = EquitySensitivity (
			notional,
			vegaDurationDays
		);

		RiskClassAggregate equityRiskClassAggregate = equityRiskClassSensitivity.aggregate (
			productClassSettings.equityRiskClassSensitivitySettings(),
			marginEstimationSettings
		);

		RiskClassSensitivity commodityRiskClassSensitivity = CommoditySensitivity (
			notional,
			vegaDurationDays
		);

		RiskClassAggregate commodityRiskClassAggregate = commodityRiskClassSensitivity.aggregate (
			productClassSettings.commodityRiskClassSensitivitySettings(),
			marginEstimationSettings
		);

		RiskClassSensitivity fxRiskClassSensitivity = FXSensitivity (
			fxCurrencyArray,
			notional
		);

		RiskClassAggregate fxRiskClassAggregate = fxRiskClassSensitivity.aggregate (
			productClassSettings.fxRiskClassSensitivitySettings(),
			marginEstimationSettings
		);

		RiskClassSensitivityIR irRiskClassSensitivity = IRSensitivity (
			irCurrencyArray,
			irNotionalArray
		);

		RiskClassAggregateIR irRiskClassAggregate = irRiskClassSensitivity.aggregate (
			productClassSettings.irRiskClassSensitivitySettings(),
			marginEstimationSettings
		);

		RiskClassSensitivityCR crqRiskClassSensitivity = CreditSensitivity (
			crqBucketComponentGrid,
			crqBucketIndexArray,
			notional
		);

		RiskClassAggregateCR crqRiskClassAggregate = crqRiskClassSensitivity.aggregate (
			productClassSettings.creditQualifyingRiskClassSensitivitySettings(),
			marginEstimationSettings
		);

		RiskClassSensitivityCR crnqRiskClassSensitivity = CreditSensitivity (
			crnqBucketComponentGrid,
			crnqBucketIndexArray,
			notional
		);

		RiskClassAggregateCR crnqRiskClassAggregate = crnqRiskClassSensitivity.aggregate (
			productClassSettings.creditNonQualifyingRiskClassSensitivitySettings(),
			marginEstimationSettings
		);

		System.out.println ("\t|-------------------------------------||");

		System.out.println ("\t|   SIMM RISK CLASS INITIAL MARGIN    ||");

		System.out.println ("\t|-------------------------------------||");

		System.out.println ("\t|                                     ||");

		System.out.println ("\t|  L -> R:                            ||");

		System.out.println ("\t|     - RISK FACTOR        =>  MARGIN ||");

		System.out.println ("\t|-------------------------------------||");

		System.out.println ("\t|-------------------------------------||");

		System.out.println (
			"\t| PRODUCT EQUITY MARGIN    => " +
			FormatUtil.FormatDouble (equityRiskClassAggregate.margin(), 6, 0, 1.) + " ||"
		);

		System.out.println (
			"\t| PRODUCT COMMODITY MARGIN => " +
			FormatUtil.FormatDouble (commodityRiskClassAggregate.margin(), 6, 0, 1.) + " ||"
		);

		System.out.println (
			"\t| PRODUCT FX MARGIN        => " +
			FormatUtil.FormatDouble (fxRiskClassAggregate.margin(), 6, 0, 1.) + " ||"
		);

		System.out.println (
			"\t| PRODUCT IR MARGIN        => " +
			FormatUtil.FormatDouble (irRiskClassAggregate.margin(), 6, 0, 1.) + " ||"
		);

		System.out.println (
			"\t| PRODUCT CRQ MARGIN       => " +
			FormatUtil.FormatDouble (crqRiskClassAggregate.margin(), 6, 0, 1.) + " ||"
		);

		System.out.println (
			"\t| PRODUCT CRNQ MARGIN      => " +
			FormatUtil.FormatDouble (crnqRiskClassAggregate.margin(), 6, 0, 1.) + " ||"
		);

		System.out.println ("\t|-------------------------------------||");

		System.out.println();

		ProductClassSensitivity productClassSensitivity = new ProductClassSensitivity (
			equityRiskClassSensitivity,
			commodityRiskClassSensitivity,
			fxRiskClassSensitivity,
			irRiskClassSensitivity,
			crqRiskClassSensitivity,
			crnqRiskClassSensitivity
		);

		ProductClassMargin productClassMargin = productClassSensitivity.estimate (
			productClassSettings,
			marginEstimationSettings
		);

		System.out.println ("\t|-------------------------------------||");

		System.out.println ("\t|  SIMM PRODUCT CLASS INITIAL MARGIN  ||");

		System.out.println ("\t|-------------------------------------||");

		System.out.println ("\t|                                     ||");

		System.out.println ("\t|  L -> R:                            ||");

		System.out.println ("\t|     - RISK FACTOR        =>  MARGIN ||");

		System.out.println ("\t|-------------------------------------||");

		System.out.println (
			"\t| PRODUCT EQUITY MARGIN    => " +
			FormatUtil.FormatDouble (
				productClassMargin.equityRiskClassAggregate().margin(), 6, 0, 1.
			) + " ||"
		);

		System.out.println (
			"\t| PRODUCT COMMODITY MARGIN => " +
			FormatUtil.FormatDouble (
				productClassMargin.commodityRiskClassAggregate().margin(), 6, 0, 1.
			) + " ||"
		);

		System.out.println (
			"\t| PRODUCT FX MARGIN        => " +
			FormatUtil.FormatDouble (
				productClassMargin.fxRiskClassAggregate().margin(), 6, 0, 1.
			) + " ||"
		);

		System.out.println (
			"\t| PRODUCT IR MARGIN        => " +
			FormatUtil.FormatDouble (
				productClassMargin.irRiskClassAggregate().margin(), 6, 0, 1.
			) + " ||"
		);

		System.out.println (
			"\t| PRODUCT CRQ MARGIN       => " +
			FormatUtil.FormatDouble (
				productClassMargin.creditQualifyingRiskClassAggregate().margin(), 6, 0, 1.
			) + " ||"
		);

		System.out.println (
			"\t| PRODUCT CRNQ MARGIN      => " +
			FormatUtil.FormatDouble (
				productClassMargin.creditNonQualifyingRiskClassAggregate().margin(), 6, 0, 1.
			) + " ||"
		);

		System.out.println ("\t|-------------------------------------||");

		System.out.println (
			"\t| PRODUCT TOTAL MARGIN     => " +
			FormatUtil.FormatDouble (
				productClassMargin.total (productClassSettings.labelCorrelation()), 6, 0, 1.
			) + " ||"
		);

		System.out.println ("\t|-------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
