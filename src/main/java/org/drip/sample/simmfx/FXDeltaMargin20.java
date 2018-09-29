
package org.drip.sample.simmfx;

import java.util.Map;
import java.util.TreeMap;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.simm.fx.FXRiskThresholdContainer20;
import org.drip.simm.margin.BucketAggregate;
import org.drip.simm.margin.RiskMeasureAggregate;
import org.drip.simm.parameters.RiskMeasureSensitivitySettings;
import org.drip.simm.product.BucketSensitivity;
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
 * FXDeltaMargin20 demonstrates the Construction of a Portfolio of FX Delta Sensitivities and their eventual
 *  SIMM 2.0 Margin Computation. The References are:
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

public class FXDeltaMargin20
{

	private static final Map<String, Map<String, Double>> CategorySensitivityMap (
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

	private static final void CategoryRiskFactorSensitivity (
		final Map<String, Map<String, Double>> categorySensitivityMap)
		throws Exception
	{
		System.out.println ("\t|-------------------||");

		System.out.println ("\t| RISK FACTOR DELTA ||");

		System.out.println ("\t|-------------------||");

		System.out.println ("\t|  L -> R:          ||");

		System.out.println ("\t|    - Currency     ||");

		System.out.println ("\t|    - Category     ||");

		System.out.println ("\t|    - Delta        ||");

		System.out.println ("\t|-------------------||");

		for (Map.Entry<String, Map<String, Double>> categorySensitivityMapEntry :
			categorySensitivityMap.entrySet())
		{
			String categoryIndex = categorySensitivityMapEntry.getKey();

			Map<String, Double> riskFactorSensitivityMap = categorySensitivityMapEntry.getValue();

			for (Map.Entry<String, Double> riskFactorSensitivityMapEntry :
				riskFactorSensitivityMap.entrySet())
			{
				String currency = riskFactorSensitivityMapEntry.getKey();

				double riskFactorSensitivity = riskFactorSensitivityMapEntry.getValue();

				System.out.println (
					"\t| " +
					currency + " => " +
					categoryIndex + " | " +
					FormatUtil.FormatDouble(riskFactorSensitivity, 2, 2, 1.) + " ||"
				);
			}
		}

		System.out.println ("\t|-------------------||");

		System.out.println();
	}

	public static final void main (
		final String[] inputs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double notional = 100.;

		String[] currencyArray =
		{
			"USD",
			"EUR",
			"JPY",
			"GBP",
			"AUD",
			"CHF",
			"CAD",
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
			"ZAR",
			"PKR",
			"IDR"
		};

		RiskMeasureSensitivitySettings riskMeasureSensitivitySettings =
			RiskMeasureSensitivitySettings.ISDA_FX_DELTA_20();

		Map<String, Map<String, Double>> categorySensitivityMap = CategorySensitivityMap (
			currencyArray,
			notional
		);

		CategoryRiskFactorSensitivity (categorySensitivityMap);

		Map<String, BucketSensitivity> bucketSensitivityMap = new TreeMap<String, BucketSensitivity>();

		System.out.println ("\t|--------------------||");

		System.out.println ("\t|  BUCKET AGGREGATE  ||");

		System.out.println ("\t|--------------------||");

		System.out.println ("\t|  L -> R:           ||");

		System.out.println ("\t|    - Bucket Index  ||");

		System.out.println ("\t|    - Bucket Margin ||");

		System.out.println ("\t|    - Bucket Delta  ||");

		System.out.println ("\t|--------------------||");

		for (Map.Entry<String, Map<String, Double>> categorySensitivityMapEntry :
			categorySensitivityMap.entrySet())
		{
			String bucketIndex = categorySensitivityMapEntry.getKey();

			BucketSensitivity bucketSensitivity = new BucketSensitivity
				(categorySensitivityMapEntry.getValue());

			bucketSensitivityMap.put (
				"" + bucketIndex,
				bucketSensitivity
			);

			BucketAggregate bucketAggregate = bucketSensitivity.aggregate
				(riskMeasureSensitivitySettings.bucketSettingsMap().get (bucketIndex));

			System.out.println ("\t| " +
				bucketIndex + " => " +
				FormatUtil.FormatDouble (Math.sqrt (bucketAggregate.sensitivityMarginVariance()), 4, 0, 1.) + " | " +
				FormatUtil.FormatDouble (bucketAggregate.cumulativeRiskFactorSensitivityMargin(), 4, 0, 1.) + " ||"
			);
		}

		System.out.println ("\t|--------------------||");

		System.out.println();

		RiskMeasureAggregate riskMeasureAggregate = new
			RiskMeasureSensitivity (bucketSensitivityMap).linearAggregate (riskMeasureSensitivitySettings);

		System.out.println ("\t|--------------------------------------------------||");

		System.out.println ("\t|              SBA BASED DELTA MARGIN              ||");

		System.out.println ("\t|--------------------------------------------------||");

		System.out.println ("\t|                                                  ||");

		System.out.println ("\t|    L -> R:                                       ||");

		System.out.println ("\t|                                                  ||");

		System.out.println ("\t|            - Core Delta SBA Margin               ||");

		System.out.println ("\t|            - Residual Delta SBA Margin           ||");

		System.out.println ("\t|            - SBA Delta Margin                    ||");

		System.out.println ("\t|--------------------------------------------------||");

		System.out.println ("\t| DELTA MARGIN COMPONENTS => " +
			FormatUtil.FormatDouble (Math.sqrt (riskMeasureAggregate.coreSBAVariance()), 4, 0, 1.) +
				" | " +
			FormatUtil.FormatDouble (Math.sqrt (riskMeasureAggregate.residualSBAVariance()), 4, 0, 1.) +
				" | " +
			FormatUtil.FormatDouble (riskMeasureAggregate.sba(), 4, 0, 1.) + " ||"
		);

		System.out.println ("\t|--------------------------------------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
