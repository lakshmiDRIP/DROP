
package org.drip.sample.simmcrnq;

import java.util.HashMap;
import java.util.Map;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.simm.margin.RiskMeasureAggregateCR;
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
 * CreditNonQualifyingBucketCurvatureMargin21 illustrates the Computation of the SIMM 2.1 CR Curvature Margin
 *  for a Bucket's Non-Qualifying Credit Exposure Sensitivities. The References are:
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

public class CreditNonQualifyingBucketCurvatureMargin21
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

		System.out.println ("\t|  " + componentName + " VEGA    ||");

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
	}

	private static final void DisplayRiskMeasureAggregate (
		final RiskMeasureAggregateCR riskMeasureAggregateCR)
		throws Exception
	{
		System.out.println ("\t||--------------------------------------------||");

		System.out.println ("\t||   CR RISK CLASS AGGREGATE MARGIN METRICS   ||");

		System.out.println ("\t||--------------------------------------------||");

		System.out.println (
			"\t|| Core Vega SBA Variance      => " +
			FormatUtil.FormatDouble (riskMeasureAggregateCR.coreSBAVariance(), 10, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| Residual Vega SBA Variance  => " +
			FormatUtil.FormatDouble (riskMeasureAggregateCR.residualSBAVariance(), 10, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| Vega SBA                    => " +
			FormatUtil.FormatDouble (riskMeasureAggregateCR.sba(), 10, 0, 1.) + " ||"
		);

		System.out.println ("\t||--------------------------------------------||");

		System.out.println();
	}

	public static final void main (
		final String[] inputs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int bucketIndex = 1;
		double notional = 100.;
		String[] componentNameArray = new String[]
		{
			"UST",
			"BND",
			"FRT",
			"ITA",
			"ESP",
			"GLT",
		};

		Map<String, RiskFactorTenorSensitivity> tenorSensitivityMap = new
			CaseInsensitiveHashMap<RiskFactorTenorSensitivity>();

		for (String componentName : componentNameArray)
		{
			ComponentRiskFactorTenorSensitivity (
				tenorSensitivityMap,
				notional,
				componentName
			);
		}

		BucketSensitivityCR bucketSensitivityCR = new BucketSensitivityCR (tenorSensitivityMap);

		DisplayComponentTenorSensitivity (
			"NET",
			bucketSensitivityCR.cumulativeTenorSensitivityMap()
		);

		MarginEstimationSettings marginEstimationSettings = MarginEstimationSettings.CornishFischer
			(MarginEstimationSettings.POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_ISDA);

		RiskMeasureSensitivitySettingsCR riskMeasureSensitivitySettingsCR =
			RiskMeasureSensitivitySettingsCR.ISDA_CRNQ_CURVATURE_21();

		Map<String, BucketSensitivityCR> bucketSensitivityMap = new
			CaseInsensitiveHashMap<BucketSensitivityCR>();

		bucketSensitivityMap.put (
			"" + bucketIndex,
			bucketSensitivityCR
		);

		RiskMeasureSensitivityCR riskMeasureSensitivityCR = new RiskMeasureSensitivityCR
			(bucketSensitivityMap);

		RiskMeasureAggregateCR riskMeasureAggregateCR = riskMeasureSensitivityCR.curvatureAggregate (
			riskMeasureSensitivitySettingsCR,
			marginEstimationSettings
		);

		DisplayRiskMeasureAggregate (riskMeasureAggregateCR);

		EnvManager.TerminateEnv();
	}
}
