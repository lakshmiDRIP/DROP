
package org.drip.sample.simmvariance;

import java.util.HashMap;
import java.util.Map;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.simm.foundation.MarginEstimationSettings;
import org.drip.simm.margin.RiskClassAggregateCR;
import org.drip.simm.margin.RiskMeasureAggregateCR;
import org.drip.simm.parameters.RiskClassSensitivitySettingsCR;
import org.drip.simm.product.BucketSensitivityCR;
import org.drip.simm.product.RiskClassSensitivityCR;
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
 * CRNQMarginComparison illustrates the Comparison of the Credit Non-Qualifying Margin Estimates using
 *  different Schemes for Calculating the Position-Bucket Principal Component Co-variance. The References
 *  are:
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

public class CRNQMarginComparison
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

	private static final void ISDABucketCovarianceMargin (
		final String positionBucketCovarianceScheme,
		final Map<String, BucketSensitivityCR> bucketDeltaSensitivityMap,
		final Map<String, BucketSensitivityCR> bucketVegaSensitivityMap,
		final RiskClassSensitivitySettingsCR riskClassSensitivitySettings,
		final MarginEstimationSettings marginEstimationSettings)
		throws Exception
	{
		RiskClassAggregateCR riskClassAggregate = new RiskClassSensitivityCR (
			new RiskMeasureSensitivityCR (bucketDeltaSensitivityMap),
			new RiskMeasureSensitivityCR (bucketVegaSensitivityMap),
			new RiskMeasureSensitivityCR (bucketVegaSensitivityMap)
		).aggregate (
			riskClassSensitivitySettings,
			marginEstimationSettings
		);

		RiskMeasureAggregateCR deltaRiskMeasureAggregate = riskClassAggregate.deltaMargin();

		RiskMeasureAggregateCR vegaRiskMeasureAggregate = riskClassAggregate.vegaMargin();

		RiskMeasureAggregateCR curvatureRiskMeasureAggregate = riskClassAggregate.curvatureMargin();

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

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double notional = 100.;
		int[] bucketIndexArray = {
			 1,
			 2,
		};
		String[][] bucketComponentGrid = {
			{"01a", "01b", "01c", "01d", "01e", "01f"},
			{"02a", "02b", "02c", "02d", "02e", "02f"},
		};

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
				ComponentRiskFactorTenorSensitivity (
					tenorDeltaSensitivityMap,
					notional,
					componentName
				);

				ComponentRiskFactorTenorSensitivity (
					tenorVegaSensitivityMap,
					notional,
					componentName
				);
			}

			bucketDeltaSensitivityMap.put (
				"" + bucketIndex,
				new BucketSensitivityCR (tenorDeltaSensitivityMap)
			);

			bucketVegaSensitivityMap.put (
				"" + bucketIndex,
				new BucketSensitivityCR (tenorVegaSensitivityMap)
			);
		}

		RiskClassSensitivitySettingsCR riskClassSensitivitySettings =
			RiskClassSensitivitySettingsCR.ISDA_CRNQ_20();

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
