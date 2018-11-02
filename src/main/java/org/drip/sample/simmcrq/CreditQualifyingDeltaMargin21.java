
package org.drip.sample.simmcrq;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.simm.margin.RiskMeasureAggregateCR;
import org.drip.simm.margin.SensitivityAggregateCR;
import org.drip.simm.parameters.MarginEstimationSettings;
import org.drip.simm.parameters.RiskMeasureSensitivitySettingsCR;
import org.drip.simm.product.BucketSensitivityCR;
import org.drip.simm.product.RiskFactorTenorSensitivity;
import org.drip.simm.product.RiskMeasureSensitivityCR;

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
 * CreditQualifyingDeltaMargin21 illustrates the Computation of the CR SIMM 2.1 Delta Margin for a Bucket of
 *  Credit Exposure Sensitivities. The References are:
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

public class CreditQualifyingDeltaMargin21
{

	private static final void AddTenorSensitivity (
		final Map<String, Double> tenorSensitivityMap,
		final double notional,
		final String tenor)
		throws Exception
	{
		if (tenorSensitivityMap.containsKey (tenor))
		{
			tenorSensitivityMap.put (
				tenor,
				tenorSensitivityMap.get (tenor) + notional * (Math.random() - 0.5)
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

	private static final RiskFactorTenorSensitivity CurveTenorSensitivityMap (
		final double notional)
		throws Exception
	{
		Map<String, Double> tenorSensitivityMap = new HashMap<String, Double>();

		AddTenorSensitivity (
			tenorSensitivityMap,
			notional,
			"1Y"
		);

		AddTenorSensitivity (
			tenorSensitivityMap,
			notional,
			"2Y"
		);

		AddTenorSensitivity (
			tenorSensitivityMap,
			notional,
			"3Y"
		);

		AddTenorSensitivity (
			tenorSensitivityMap,
			notional,
			"5Y"
		);

		AddTenorSensitivity (
			tenorSensitivityMap,
			notional,
			"10Y"
		);

		return new RiskFactorTenorSensitivity (tenorSensitivityMap);
	}

	private static final void DisplayComponentTenorSensitivity (
		final String componentName,
		final RiskFactorTenorSensitivity tenorSensitivityMap)
		throws Exception
	{
		System.out.println();

		System.out.println ("\t|--------------||");

		System.out.println ("\t|  " + componentName + " DELTA   ||");

		System.out.println ("\t|--------------||");

		System.out.println ("\t|              ||");

		System.out.println ("\t|  L -> R:     ||");

		System.out.println ("\t|    - Tenor   ||");

		System.out.println ("\t|    - Delta   ||");

		System.out.println ("\t|--------------||");

		for (Map.Entry<String, Double> tenorSensitivityEntry :
			tenorSensitivityMap.sensitivityMap().entrySet())
		{
			System.out.println (
				"\t| " +
				tenorSensitivityEntry.getKey() + " => " +
				FormatUtil.FormatDouble (tenorSensitivityEntry.getValue(), 2, 2, 1.) + " ||"
			);
		}

		System.out.println ("\t|--------------||");

		System.out.println();
	}

	private static final void ComponentRiskFactorTenorSensitivity (
		final Map<String, RiskFactorTenorSensitivity> tenorSensitivityMap,
		final double notional,
		final String componentName)
		throws Exception
	{
		RiskFactorTenorSensitivity ustRiskFactorSensitivity = CurveTenorSensitivityMap (notional);

		tenorSensitivityMap.put (
			componentName,
			ustRiskFactorSensitivity
		);

		DisplayComponentTenorSensitivity (
			componentName,
			ustRiskFactorSensitivity
		);
	}

	private static final void DisplayRiskMeasureAggregate (
		final RiskMeasureAggregateCR riskMeasureAggregateCR)
		throws Exception
	{
		System.out.println ("\t||--------------------------------------------||");

		System.out.println ("\t||   CR RISK CLASS AGGREGATE MARGIN METRICS   ||");

		System.out.println ("\t||--------------------------------------------||");

		System.out.println (
			"\t|| Core Delta SBA Variance     => " +
			FormatUtil.FormatDouble (riskMeasureAggregateCR.coreSBAVariance(), 10, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| Residual Delta SBA Variance => " +
			FormatUtil.FormatDouble (riskMeasureAggregateCR.residualSBAVariance(), 10, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| Delta SBA                   => " +
			FormatUtil.FormatDouble (riskMeasureAggregateCR.sba(), 10, 0, 1.) + " ||"
		);

		System.out.println ("\t||--------------------------------------------||");

		System.out.println();
	}

	private static final void DeltaMarginCovarianceEntry (
		final int bucketIndex,
		final SensitivityAggregateCR crDeltaAggregate)
		throws Exception
	{
		System.out.println ("\t||-------------------------------------||");

		System.out.println (
			"\t|| " + FormatUtil.FormatDouble (bucketIndex, 2, 0, 1.) +
			"  RISK FACTOR MARGIN COVARIANCE  ||"
		);

		System.out.println ("\t||-------------------------------------||");

		System.out.println ("\t||                                     ||");

		System.out.println ("\t||    - L -> R:                        ||");

		System.out.println ("\t||        - Component Pair             ||");

		System.out.println ("\t||        - Covariance                 ||");

		System.out.println ("\t||-------------------------------------||");

		Map<String, Double> componentMarginCovarianceMap = crDeltaAggregate.componentMarginCovarianceMap();

		Set<String> componentPairSet = componentMarginCovarianceMap.keySet();

		for (String componentPair : componentPairSet)
		{
			System.out.println (
				"\t|| " + componentPair + " => " +
				FormatUtil.FormatDouble (componentMarginCovarianceMap.get (componentPair), 9, 0, 1.) +
					"               ||"
			);
		}

		System.out.println ("\t||-------------------------------------||");

		System.out.println();
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double notional = 100.;
		int[] bucketIndexArray = {
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
		String[][] bucketComponentGrid = {
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

		Map<String, BucketSensitivityCR> bucketSensitivityMap = new HashMap<String, BucketSensitivityCR>();

		for (int bucketIndex : bucketIndexArray)
		{
			Map<String, RiskFactorTenorSensitivity> tenorSensitivityMap = new
				CaseInsensitiveHashMap<RiskFactorTenorSensitivity>();

			for (String componentName : bucketComponentGrid[bucketIndex - 1])
			{
				ComponentRiskFactorTenorSensitivity (
					tenorSensitivityMap,
					notional,
					componentName
				);
			}

			bucketSensitivityMap.put (
				"" + bucketIndex,
				new BucketSensitivityCR (tenorSensitivityMap)
			);
		}

		RiskMeasureSensitivityCR riskClassSensitivity = new RiskMeasureSensitivityCR (bucketSensitivityMap);

		MarginEstimationSettings marginEstimationSettings = MarginEstimationSettings.Standard
			(MarginEstimationSettings.POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_ISDA);

		RiskMeasureSensitivitySettingsCR riskMeasureSensitivitySettings =
			RiskMeasureSensitivitySettingsCR.ISDA_CRQ_DELTA_21();

		RiskMeasureAggregateCR riskMeasureAggregate = riskClassSensitivity.linearAggregate (
			riskMeasureSensitivitySettings,
			marginEstimationSettings
		);

		for (int bucketIndex : bucketIndexArray)
		{
			DeltaMarginCovarianceEntry (
				bucketIndex,
				riskMeasureAggregate.bucketAggregateMap().get ("" + bucketIndex).sensitivityAggregate()
			);
		}

		DisplayRiskMeasureAggregate (riskMeasureAggregate);

		EnvManager.TerminateEnv();
	}
}
