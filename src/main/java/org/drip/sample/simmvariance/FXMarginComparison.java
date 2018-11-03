
package org.drip.sample.simmvariance;

import java.util.Map;
import java.util.TreeMap;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.simm.fx.FXRiskThresholdContainer20;
import org.drip.simm.margin.RiskClassAggregate;
import org.drip.simm.margin.RiskMeasureAggregate;
import org.drip.simm.parameters.MarginEstimationSettings;
import org.drip.simm.parameters.RiskClassSensitivitySettings;
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
 * FXMarginComparison illustrates the Comparison of the FX Margin Estimates using difference Schemes for
 *  Calculating the Position-Bucket Principal Component Co-variance. The References are:
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

public class FXMarginComparison
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

	private static final void AddBucketRiskFactorSensitivity (
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

	private static final Map<String, Map<String, Double>> BucketRiskFactorSensitivityMap (
		final double notional)
		throws Exception
	{
		Map<String, Map<String, Double>> bucketRiskFactorSensitivityMap =
			new TreeMap<String, Map<String, Double>>();

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
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

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
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

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
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

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
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

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
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

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
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

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
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

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
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

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
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

		return bucketRiskFactorSensitivityMap;
	}

	private static final void ISDABucketCovarianceMargin (
		final String positionBucketCovarianceScheme,
		final Map<String, BucketSensitivity> bucketDeltaSensitivityMap,
		final Map<String, BucketSensitivity> bucketVegaSensitivityMap,
		final RiskClassSensitivitySettings riskClassSensitivitySettings,
		final MarginEstimationSettings marginEstimationSettings)
		throws Exception
	{
		RiskClassAggregate riskClassAggregate = new RiskClassSensitivity (
			new RiskMeasureSensitivity (bucketDeltaSensitivityMap),
			new RiskMeasureSensitivity (bucketVegaSensitivityMap),
			new RiskMeasureSensitivity (bucketVegaSensitivityMap)
		).aggregate (
			riskClassSensitivitySettings,
			marginEstimationSettings
		);

		RiskMeasureAggregate deltaRiskMeasureAggregate = riskClassAggregate.deltaMargin();

		RiskMeasureAggregate vegaRiskMeasureAggregate = riskClassAggregate.vegaMargin();

		RiskMeasureAggregate curvatureRiskMeasureAggregate = riskClassAggregate.curvatureMargin();

		System.out.println ("\t|----------------------------------------||");

		System.out.println ("\t|               " + positionBucketCovarianceScheme + " SBA MARGIN          ||");

		System.out.println ("\t|----------------------------------------||");

		System.out.println ("\t|  MEASURE  =>  CORE  | RESIDUAL | TOTAL ||");

		System.out.println ("\t|----------------------------------------||");

		System.out.println ("\t|   DELTA   => " +
			FormatUtil.FormatDouble (Math.sqrt (deltaRiskMeasureAggregate.coreSBAVariance()), 5, 0, 1.) +
				" |  " +
			FormatUtil.FormatDouble (Math.sqrt (deltaRiskMeasureAggregate.residualSBAVariance()), 5, 0, 1.) +
				"  |" +
			FormatUtil.FormatDouble (deltaRiskMeasureAggregate.sba(), 5, 0, 1.) + " ||"
		);

		System.out.println ("\t|   VEGA    => " +
			FormatUtil.FormatDouble (Math.sqrt (vegaRiskMeasureAggregate.coreSBAVariance()), 5, 0, 1.) +
				" |  " +
			FormatUtil.FormatDouble (Math.sqrt (vegaRiskMeasureAggregate.residualSBAVariance()), 5, 0, 1.) +
				"  |" +
			FormatUtil.FormatDouble (vegaRiskMeasureAggregate.sba(), 5, 0, 1.) + " ||"
		);

		System.out.println ("\t| CURVATURE => " +
			FormatUtil.FormatDouble (Math.sqrt (curvatureRiskMeasureAggregate.coreSBAVariance()), 5, 0, 1.) +
				" |  " +
			FormatUtil.FormatDouble (Math.sqrt (curvatureRiskMeasureAggregate.residualSBAVariance()), 5, 0, 1.) +
				"  |" +
			FormatUtil.FormatDouble (curvatureRiskMeasureAggregate.sba(), 5, 0, 1.) + " ||"
		);

		System.out.println ("\t|----------------------------------------||");

		System.out.println();
	}

	public static void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double notional = 100.;
		int vegaDurationDays = 365;

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

		RiskClassSensitivitySettings riskClassSensitivitySettings = RiskClassSensitivitySettings.ISDA_FX_20
			(vegaDurationDays);

		Map<String, Map<String, Double>> bucketDeltaMap = CategorySensitivityMap (
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

		Map<String, Map<String, Double>> bucketVegaMap = BucketRiskFactorSensitivityMap (notional);

		Map<String, BucketSensitivity> bucketVegaSensitivityMap = new TreeMap<String, BucketSensitivity>();

		for (Map.Entry<String, Map<String, Double>> bucketVegaMapEntry : bucketVegaMap.entrySet())
		{
			bucketVegaSensitivityMap.put (
				bucketVegaMapEntry.getKey(),
				new BucketSensitivity (bucketVegaMapEntry.getValue())
			);
		}

		ISDABucketCovarianceMargin (
			MarginEstimationSettings.POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_ISDA,
			bucketDeltaSensitivityMap,
			bucketVegaSensitivityMap,
			riskClassSensitivitySettings,
			MarginEstimationSettings.CornishFischer
				(MarginEstimationSettings.POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_ISDA)
		);

		ISDABucketCovarianceMargin (
			MarginEstimationSettings.POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_FRTB,
			bucketDeltaSensitivityMap,
			bucketVegaSensitivityMap,
			riskClassSensitivitySettings,
			MarginEstimationSettings.CornishFischer
				(MarginEstimationSettings.POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_FRTB)
		);

		EnvManager.TerminateEnv();
	}
}
