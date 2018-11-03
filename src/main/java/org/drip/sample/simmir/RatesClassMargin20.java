
package org.drip.sample.simmir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.simm.foundation.MarginEstimationSettings;
import org.drip.simm.margin.RiskClassAggregateIR;
import org.drip.simm.margin.RiskMeasureAggregateIR;
import org.drip.simm.parameters.RiskClassSensitivitySettingsIR;
import org.drip.simm.product.BucketSensitivityIR;
import org.drip.simm.product.RiskClassSensitivityIR;
import org.drip.simm.product.RiskFactorTenorSensitivity;
import org.drip.simm.product.RiskMeasureSensitivityIR;

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
 * RatesClassMargin20 illustrates the Computation of the SIMM 2.0 IR Class Margin for a Currency Bucket's IR
 *  Exposure Sensitivities. The References are:
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

public class RatesClassMargin20
{

	private static final RiskFactorTenorSensitivity CurveTenorSensitivityMap (
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

		return new RiskFactorTenorSensitivity (tenorSensitivityMap);
	}

	private static final BucketSensitivityIR CurrencyBucketSensitivity (
		final String currency,
		final double notional)
		throws Exception
	{
		return new BucketSensitivityIR (
			CurveTenorSensitivityMap (notional),
			CurveTenorSensitivityMap (notional),
			CurveTenorSensitivityMap (notional),
			CurveTenorSensitivityMap (notional),
			CurveTenorSensitivityMap (notional),
			CurveTenorSensitivityMap (notional),
			CurveTenorSensitivityMap (notional)
		);
	}

	public static final void main (
		final String[] inputs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String[] currencyArray = {
			"USD",
			"EUR",
			"CNY",
			"INR",
			"JPY"
		};

		double[] notionalArray = {
			100.,
			108.,
			119.,
			 49.,
			 28.
		};

		Map<String, BucketSensitivityIR> bucketDeltaSensitivityMap = new HashMap<String, BucketSensitivityIR>();

		Map<String, BucketSensitivityIR> bucketVegaSensitivityMap = new HashMap<String, BucketSensitivityIR>();

		for (int currencyIndex = 0; currencyIndex < currencyArray.length; ++currencyIndex)
		{
			bucketDeltaSensitivityMap.put (
				currencyArray[currencyIndex],
				CurrencyBucketSensitivity (
					currencyArray[currencyIndex],
					notionalArray[currencyIndex]
				)
			);

			bucketVegaSensitivityMap.put (
				currencyArray[currencyIndex],
				CurrencyBucketSensitivity (
					currencyArray[currencyIndex],
					notionalArray[currencyIndex]
				)
			);
		}

		List<String> currencyList = new ArrayList<String>();

		for (String currency : currencyArray)
		{
			currencyList.add (currency);
		}

		MarginEstimationSettings marginEstimationSettings = MarginEstimationSettings.CornishFischer
			(MarginEstimationSettings.POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_ISDA);

		RiskClassSensitivitySettingsIR riskClassSensitivitySettingsIR =
			RiskClassSensitivitySettingsIR.ISDA_20 (currencyList);

		RiskClassAggregateIR riskClassAggregate = new RiskClassSensitivityIR (
			new RiskMeasureSensitivityIR (bucketDeltaSensitivityMap),
			new RiskMeasureSensitivityIR (bucketVegaSensitivityMap),
			new RiskMeasureSensitivityIR (bucketVegaSensitivityMap)
		).aggregate (
			riskClassSensitivitySettingsIR,
			marginEstimationSettings
		);

		RiskMeasureAggregateIR deltaRiskMeasureAggregate = riskClassAggregate.deltaMargin();

		RiskMeasureAggregateIR vegaRiskMeasureAggregate = riskClassAggregate.vegaMargin();

		RiskMeasureAggregateIR curvatureRiskMeasureAggregate = riskClassAggregate.curvatureMargin();

		System.out.println ("\t|-----------------------------------------------------||");

		System.out.println ("\t|               SBA BASED DELTA MARGIN                ||");

		System.out.println ("\t|-----------------------------------------------------||");

		System.out.println ("\t|                                                     ||");

		System.out.println ("\t|    L -> R:                                          ||");

		System.out.println ("\t|                                                     ||");

		System.out.println ("\t|            - Core Delta SBA Margin                  ||");

		System.out.println ("\t|            - Residual Delta SBA Margin              ||");

		System.out.println ("\t|            - SBA Delta Margin                       ||");

		System.out.println ("\t|-----------------------------------------------------||");

		System.out.println ("\t| DELTA MARGIN COMPONENTS => " +
			FormatUtil.FormatDouble (Math.sqrt (deltaRiskMeasureAggregate.coreSBAVariance()), 5, 0, 1.) +
				" | " +
			FormatUtil.FormatDouble (Math.sqrt (deltaRiskMeasureAggregate.residualSBAVariance()), 5, 0, 1.) +
				" | " +
			FormatUtil.FormatDouble (deltaRiskMeasureAggregate.sba(), 5, 0, 1.) + " ||"
		);

		System.out.println ("\t|-----------------------------------------------------||");

		System.out.println ("\t|-----------------------------------------------------||");

		System.out.println ("\t|               SBA BASED VEGA MARGIN                 ||");

		System.out.println ("\t|-----------------------------------------------------||");

		System.out.println ("\t|                                                     ||");

		System.out.println ("\t|    L -> R:                                          ||");

		System.out.println ("\t|                                                     ||");

		System.out.println ("\t|            - Core Vega SBA Margin                   ||");

		System.out.println ("\t|            - Residual Vega SBA Margin               ||");

		System.out.println ("\t|            - SBA Vega Margin                        ||");

		System.out.println ("\t|-----------------------------------------------------||");

		System.out.println ("\t| VEGA MARGIN COMPONENTS  => " +
			FormatUtil.FormatDouble (Math.sqrt (vegaRiskMeasureAggregate.coreSBAVariance()), 5, 0, 1.) +
				" | " +
			FormatUtil.FormatDouble (Math.sqrt (vegaRiskMeasureAggregate.residualSBAVariance()), 5, 0, 1.) +
				" | " +
			FormatUtil.FormatDouble (vegaRiskMeasureAggregate.sba(), 5, 0, 1.) + " ||"
		);

		System.out.println ("\t|-----------------------------------------------------||");

		System.out.println ("\t|---------------------------------------------------------||");

		System.out.println ("\t|                 SBA BASED CURVATURE MARGIN              ||");

		System.out.println ("\t|---------------------------------------------------------||");

		System.out.println ("\t|                                                         ||");

		System.out.println ("\t|    L -> R:                                              ||");

		System.out.println ("\t|                                                         ||");

		System.out.println ("\t|            - Core Curvature SBA Margin                  ||");

		System.out.println ("\t|            - Residual Curvature SBA Margin              ||");

		System.out.println ("\t|            - SBA Curvature Margin                       ||");

		System.out.println ("\t|---------------------------------------------------------||");

		System.out.println ("\t| CURVATURE MARGIN COMPONENTS => " +
			FormatUtil.FormatDouble (Math.sqrt (curvatureRiskMeasureAggregate.coreSBAVariance()), 5, 0, 1.) +
				" | " +
			FormatUtil.FormatDouble (Math.sqrt (curvatureRiskMeasureAggregate.residualSBAVariance()), 5, 0, 1.) +
				" | " +
			FormatUtil.FormatDouble (curvatureRiskMeasureAggregate.sba(), 5, 0, 1.) + " ||"
		);

		System.out.println ("\t|---------------------------------------------------------||");

		System.out.println();

		System.out.println ("\t|------------------------||");

		System.out.println (
			"\t| TOTAL MARGIN => " +
			FormatUtil.FormatDouble (riskClassAggregate.margin(), 5, 0, 1.) + " ||");

		System.out.println ("\t|------------------------||");

		EnvManager.TerminateEnv();
	}
}
