
package org.drip.sample.simmct;

import java.util.HashMap;
import java.util.Map;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
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
 * CommodityClassMargin20 illustrates the Computation of the ISDA 2.0 Aggregate Margin for across a Group of
 *  Commodity Bucket Exposure Sensitivities. The References are:
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

public class CommodityClassMargin20
{

	private static final void AddBucketRiskFactorSensitivity (
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

	private static final Map<String, Map<String, Double>> BucketRiskFactorSensitivityMap (
		final double notional)
		throws Exception
	{
		Map<String, Map<String, Double>> bucketRiskFactorSensitivityMap =
			new HashMap<String, Map<String, Double>>();

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			1,
			notional,
			"COAL                          "
		);

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			2,
			notional,
			"CRUDE                         "
		);

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			3,
			notional,
			"LIGHT ENDS                    "
		);

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			4,
			notional,
			"MIDDLE DISTILLATES            "
		);

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			5,
			notional,
			"HEAVY DISTILLATES             "
		);

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			6,
			notional,
			"NORTH AMERICAN NATURAL GAS    "
		);

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			7,
			notional,
			"EUROPEAN NATURAL GAS          "
		);

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			8,
			notional,
			"NORTH AMERICAN POWER          "
		);

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			9,
			notional,
			"EUROPEAN POWER                "
		);

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			10,
			notional,
			"FREIGHT                       "
		);

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			11,
			notional,
			"BASE METALS                   "
		);

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			12,
			notional,
			"PRECIOUS METALS               "
		);

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			13,
			notional,
			"GRAINS                        "
		);

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			14,
			notional,
			"SOFTS                         "
		);

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			15,
			notional,
			"LIVESTOCK                     "
		);

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			16,
			notional,
			"OTHER                         "
		);

		AddBucketRiskFactorSensitivity (
			bucketRiskFactorSensitivityMap,
			17,
			notional,
			"INDEXES                       "
		);

		return bucketRiskFactorSensitivityMap;
	}

	public static final void main (
		final String[] inputArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double notional = 100.;
		int vegaDurationDays = 365;

		MarginEstimationSettings marginEstimationSettings = MarginEstimationSettings.CornishFischer
			(MarginEstimationSettings.POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_ISDA);

		RiskClassSensitivitySettings riskClassSensitivitySettings = RiskClassSensitivitySettings.ISDA_CT_20
			(vegaDurationDays);

		Map<String, Map<String, Double>> bucketDeltaMap = BucketRiskFactorSensitivityMap (notional);

		Map<String, BucketSensitivity> bucketDeltaSensitivityMap = new HashMap<String, BucketSensitivity>();

		for (Map.Entry<String, Map<String, Double>> bucketDeltaMapEntry : bucketDeltaMap.entrySet())
		{
			bucketDeltaSensitivityMap.put (
				bucketDeltaMapEntry.getKey(),
				new BucketSensitivity (bucketDeltaMapEntry.getValue())
			);
		}

		Map<String, Map<String, Double>> bucketVegaMap = BucketRiskFactorSensitivityMap (notional);

		Map<String, BucketSensitivity> bucketVegaSensitivityMap = new HashMap<String, BucketSensitivity>();

		for (Map.Entry<String, Map<String, Double>> bucketVegaMapEntry : bucketVegaMap.entrySet())
		{
			bucketVegaSensitivityMap.put (
				bucketVegaMapEntry.getKey(),
				new BucketSensitivity (bucketVegaMapEntry.getValue())
			);
		}

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

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
