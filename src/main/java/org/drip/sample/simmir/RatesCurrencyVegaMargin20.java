
package org.drip.sample.simmir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.simm.margin.SensitivityAggregateIR;
import org.drip.simm.margin.RiskMeasureAggregateIR;
import org.drip.simm.parameters.MarginEstimationSettings;
import org.drip.simm.parameters.RiskMeasureSensitivitySettingsIR;
import org.drip.simm.product.BucketSensitivityIR;
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
 * RatesCurrencyVegaMargin20 illustrates the Computation of the SIMM 2.0 IR Vega Margin for a Currency
 *  Bucket's IR Exposure Sensitivities. The References are:
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

public class RatesCurrencyVegaMargin20
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

	private static final void DisplayBucketSensitivityIR (
		final String currency,
		final BucketSensitivityIR bucketSensitivityIR)
		throws Exception
	{
		Map<String, Double> oisTenorSensitivity = bucketSensitivityIR.oisTenorSensitivity().sensitivityMap();

		Map<String, Double> libor1MTenorSensitivity =
			bucketSensitivityIR.libor1MTenorSensitivity().sensitivityMap();

		Map<String, Double> libor3MTenorSensitivity =
			bucketSensitivityIR.libor3MTenorSensitivity().sensitivityMap();

		Map<String, Double> libor6MTenorSensitivity =
			bucketSensitivityIR.libor6MTenorSensitivity().sensitivityMap();

		Map<String, Double> libor12MTenorSensitivity =
			bucketSensitivityIR.libor12MTenorSensitivity().sensitivityMap();

		Map<String, Double> primeTenorSensitivity =
			bucketSensitivityIR.primeTenorSensitivity().sensitivityMap();

		Map<String, Double> municipalTenorSensitivity =
			bucketSensitivityIR.municipalTenorSensitivity().sensitivityMap();

		System.out.println ("\t||-----------------------------------------------------------------------------------------||");

		System.out.println ("\t||                            " + currency + " INTEREST CURVE TENOR SENSITIVITY                         ||");

		System.out.println ("\t||-----------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                                                                         ||");

		System.out.println ("\t||    L -> R:                                                                              ||");

		System.out.println ("\t||            - Curve Type                                                                 ||");

		System.out.println ("\t||            - OIS Tenor Vega Sensitivity                                                 ||");

		System.out.println ("\t||            - LIBOR1M Tenor Vega Sensitivity                                             ||");

		System.out.println ("\t||            - LIBOR3M Tenor Vega Sensitivity                                             ||");

		System.out.println ("\t||            - LIBOR6M Tenor Vega Sensitivity                                             ||");

		System.out.println ("\t||            - LIBOR12M Tenor Vega Sensitivity                                            ||");

		System.out.println ("\t||            - PRIME Tenor Vega Sensitivity                                               ||");

		System.out.println ("\t||            - MUNICIPAL Tenor Vega Sensitivity                                           ||");

		System.out.println ("\t||-----------------------------------------------------------------------------------------||");

		System.out.println ("\t||    OIS    |  LIBOR1M   |  LIBOR3M   |  LIBOR6M   |  LIBOR12M  |   PRIME    | MUNICIPAL  ||");

		System.out.println ("\t||-----------------------------------------------------------------------------------------||");

		for (String tenor : oisTenorSensitivity.keySet())
		{
			System.out.println (
				"\t||  " +
				FormatUtil.FormatDouble (oisTenorSensitivity.get (tenor), 2, 2, 1.) + "   |   " +
				FormatUtil.FormatDouble (libor1MTenorSensitivity.get (tenor), 2, 2, 1.) + "   |   " +
				FormatUtil.FormatDouble (libor3MTenorSensitivity.get (tenor), 2, 2, 1.) + "   |   " +
				FormatUtil.FormatDouble (libor6MTenorSensitivity.get (tenor), 2, 2, 1.) + "   |   " +
				FormatUtil.FormatDouble (libor12MTenorSensitivity.get (tenor), 2, 2, 1.) + "   |   " +
				FormatUtil.FormatDouble (primeTenorSensitivity.get (tenor), 2, 2, 1.) + "   |   " +
				FormatUtil.FormatDouble (municipalTenorSensitivity.get (tenor), 2, 2, 1.) + "   ||"
			);
		}

		System.out.println ("\t||-----------------------------------------------------------------------------------------||");

		System.out.println();
	}

	private static final void DisplayRiskMeasureAggregate (
		final RiskMeasureAggregateIR riskMeasureAggregateIR)
		throws Exception
	{
		System.out.println ("\t||--------------------------------------------||");

		System.out.println ("\t||   IR RISK CLASS AGGREGATE MARGIN METRICS   ||");

		System.out.println ("\t||--------------------------------------------||");

		System.out.println (
			"\t|| Core Vega SBA Variance      => " +
			FormatUtil.FormatDouble (riskMeasureAggregateIR.coreSBAVariance(), 10, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| Residual Vega SBA Variance  => " +
			FormatUtil.FormatDouble (riskMeasureAggregateIR.residualSBAVariance(), 10, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| Vega SBA                    => " +
			FormatUtil.FormatDouble (riskMeasureAggregateIR.sba(), 10, 0, 1.) + " ||"
		);

		System.out.println ("\t||--------------------------------------------||");

		System.out.println();
	}

	private static final void VegaMarginCovarianceEntry (
		final String currency,
		final SensitivityAggregateIR irSensitivityAggregate)
		throws Exception
	{
		double marginCovariance_OIS_OIS = irSensitivityAggregate.marginCovariance_OIS_OIS();

		double marginCovariance_OIS_LIBOR1M = irSensitivityAggregate.marginCovariance_OIS_LIBOR1M();

		double marginCovariance_OIS_LIBOR3M = irSensitivityAggregate.marginCovariance_OIS_LIBOR3M();

		double marginCovariance_OIS_LIBOR6M = irSensitivityAggregate.marginCovariance_OIS_LIBOR6M();

		double marginCovariance_OIS_LIBOR12M = irSensitivityAggregate.marginCovariance_OIS_LIBOR12M();

		double marginCovariance_OIS_PRIME = irSensitivityAggregate.marginCovariance_OIS_PRIME();

		double marginCovariance_OIS_MUNICIPAL = irSensitivityAggregate.marginCovariance_OIS_MUNICIPAL();

		double marginCovariance_LIBOR1M_LIBOR1M = irSensitivityAggregate.marginCovariance_LIBOR1M_LIBOR1M();

		double marginCovariance_LIBOR1M_LIBOR3M = irSensitivityAggregate.marginCovariance_LIBOR1M_LIBOR3M();

		double marginCovariance_LIBOR1M_LIBOR6M = irSensitivityAggregate.marginCovariance_LIBOR1M_LIBOR6M();

		double marginCovariance_LIBOR1M_LIBOR12M = irSensitivityAggregate.marginCovariance_LIBOR1M_LIBOR12M();

		double marginCovariance_LIBOR1M_PRIME = irSensitivityAggregate.marginCovariance_LIBOR1M_PRIME();

		double marginCovariance_LIBOR1M_MUNICIPAL = irSensitivityAggregate.marginCovariance_LIBOR1M_MUNICIPAL();

		double marginCovariance_LIBOR3M_LIBOR3M = irSensitivityAggregate.marginCovariance_LIBOR3M_LIBOR3M();

		double marginCovariance_LIBOR3M_LIBOR6M = irSensitivityAggregate.marginCovariance_LIBOR3M_LIBOR6M();

		double marginCovariance_LIBOR3M_LIBOR12M = irSensitivityAggregate.marginCovariance_LIBOR3M_LIBOR12M();

		double marginCovariance_LIBOR3M_PRIME = irSensitivityAggregate.marginCovariance_LIBOR3M_PRIME();

		double marginCovariance_LIBOR3M_MUNICIPAL = irSensitivityAggregate.marginCovariance_LIBOR3M_MUNICIPAL();

		double marginCovariance_LIBOR6M_LIBOR6M = irSensitivityAggregate.marginCovariance_LIBOR6M_LIBOR6M();

		double marginCovariance_LIBOR6M_LIBOR12M = irSensitivityAggregate.marginCovariance_LIBOR6M_LIBOR12M();

		double marginCovariance_LIBOR6M_PRIME = irSensitivityAggregate.marginCovariance_LIBOR6M_PRIME();

		double marginCovariance_LIBOR6M_MUNICIPAL = irSensitivityAggregate.marginCovariance_LIBOR6M_MUNICIPAL();

		double marginCovariance_LIBOR12M_LIBOR12M = irSensitivityAggregate.marginCovariance_LIBOR12M_LIBOR12M();

		double marginCovariance_LIBOR12M_PRIME = irSensitivityAggregate.marginCovariance_LIBOR12M_PRIME();

		double marginCovariance_LIBOR12M_MUNICIPAL = irSensitivityAggregate.marginCovariance_LIBOR12M_MUNICIPAL();

		double marginCovariance_PRIME_PRIME = irSensitivityAggregate.marginCovariance_PRIME_PRIME();

		double marginCovariance_PRIME_MUNICIPAL = irSensitivityAggregate.marginCovariance_PRIME_MUNICIPAL();

		double marginCovariance_MUNICIPAL_MUNICIPAL = irSensitivityAggregate.marginCovariance_MUNICIPAL_MUNICIPAL();

		System.out.println ("\t||-------------------------------------||");

		System.out.println ("\t||  " + currency + " RISK FACTOR MARGIN COVARIANCE  ||");

		System.out.println ("\t||-------------------------------------||");

		System.out.println ("\t||                                     ||");

		System.out.println ("\t||    - L -> R:                        ||");

		System.out.println ("\t||        - Curve #1                   ||");

		System.out.println ("\t||        - Curve #2                   ||");

		System.out.println ("\t||        - Covariance                 ||");

		System.out.println ("\t||-------------------------------------||");

		System.out.println (
			"\t|| OIS       - OIS       => " +
			FormatUtil.FormatDouble (marginCovariance_OIS_OIS, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| OIS       - LIBOR1M   => " +
			FormatUtil.FormatDouble (marginCovariance_OIS_LIBOR1M, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| OIS       - LIBOR3M   => " +
			FormatUtil.FormatDouble (marginCovariance_OIS_LIBOR3M, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| OIS       - LIBOR6M   => " +
			FormatUtil.FormatDouble (marginCovariance_OIS_LIBOR6M, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| OIS       - LIBOR12M  => " +
			FormatUtil.FormatDouble (marginCovariance_OIS_LIBOR12M, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| OIS       - PRIME     => " +
			FormatUtil.FormatDouble (marginCovariance_OIS_PRIME, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| OIS       - MUNICIPAL => " +
			FormatUtil.FormatDouble (marginCovariance_OIS_MUNICIPAL, 9, 0, 1.) + " ||"
		);

		System.out.println ("\t||-------------------------------------||");

		System.out.println (
			"\t|| LIBOR1M   - LIBOR1M   => " +
			FormatUtil.FormatDouble (marginCovariance_LIBOR1M_LIBOR1M, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| LIBOR1M   - LIBOR3M   => " +
			FormatUtil.FormatDouble (marginCovariance_LIBOR1M_LIBOR3M, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| LIBOR1M   - LIBOR6M   => " +
			FormatUtil.FormatDouble (marginCovariance_LIBOR1M_LIBOR6M, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| LIBOR1M   - LIBOR12M  => " +
			FormatUtil.FormatDouble (marginCovariance_LIBOR1M_LIBOR12M, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| LIBOR1M   - PRIME     => " +
			FormatUtil.FormatDouble (marginCovariance_LIBOR1M_PRIME, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| LIBOR1M   - MUNICIPAL => " +
			FormatUtil.FormatDouble (marginCovariance_LIBOR1M_MUNICIPAL, 9, 0, 1.) + " ||"
		);

		System.out.println ("\t||-------------------------------------||");

		System.out.println (
			"\t|| LIBOR3M   - LIBOR3M   => " +
			FormatUtil.FormatDouble (marginCovariance_LIBOR3M_LIBOR3M, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| LIBOR3M   - LIBOR6M   => " +
			FormatUtil.FormatDouble (marginCovariance_LIBOR3M_LIBOR6M, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| LIBOR3M   - LIBOR12M  => " +
			FormatUtil.FormatDouble (marginCovariance_LIBOR3M_LIBOR12M, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| LIBOR3M   - PRIME     => " +
			FormatUtil.FormatDouble (marginCovariance_LIBOR3M_PRIME, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| LIBOR3M   - MUNICIPAL => " +
			FormatUtil.FormatDouble (marginCovariance_LIBOR3M_MUNICIPAL, 9, 0, 1.) + " ||"
		);

		System.out.println ("\t||-------------------------------------||");

		System.out.println (
			"\t|| LIBOR6M   - LIBOR6M   => " +
			FormatUtil.FormatDouble (marginCovariance_LIBOR6M_LIBOR6M, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| LIBOR6M   - LIBOR12M  => " +
			FormatUtil.FormatDouble (marginCovariance_LIBOR6M_LIBOR12M, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| LIBOR6M   - PRIME     => " +
			FormatUtil.FormatDouble (marginCovariance_LIBOR6M_PRIME, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| LIBOR6M   - MUNICIPAL => " +
			FormatUtil.FormatDouble (marginCovariance_LIBOR6M_MUNICIPAL, 9, 0, 1.) + " ||"
		);

		System.out.println ("\t||-------------------------------------||");

		System.out.println (
			"\t|| LIBOR12M  - LIBOR12M  => " +
			FormatUtil.FormatDouble (marginCovariance_LIBOR12M_LIBOR12M, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| LIBOR12M  - PRIME     => " +
			FormatUtil.FormatDouble (marginCovariance_LIBOR12M_PRIME, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| LIBOR12M  - MUNICIPAL => " +
			FormatUtil.FormatDouble (marginCovariance_LIBOR12M_MUNICIPAL, 9, 0, 1.) + " ||"
		);

		System.out.println ("\t||-------------------------------------||");

		System.out.println (
			"\t|| PRIME     - PRIME     => " +
			FormatUtil.FormatDouble (marginCovariance_PRIME_PRIME, 9, 0, 1.) + " ||"
		);

		System.out.println (
			"\t|| PRIME     - MUNICIPAL => " +
			FormatUtil.FormatDouble (marginCovariance_PRIME_MUNICIPAL, 9, 0, 1.) + " ||"
		);

		System.out.println ("\t||-------------------------------------||");

		System.out.println (
			"\t|| MUNICIPAL - MUNICIPAL => " +
			FormatUtil.FormatDouble (marginCovariance_MUNICIPAL_MUNICIPAL, 9, 0, 1.) + " ||"
		);

		System.out.println ("\t||-------------------------------------||");

		System.out.println();
	}

	public static final void main (
		final String[] inputArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double notional = 100.;
		String currency = "USD";

		List<String> currencyList = new ArrayList<String>();

		currencyList.add (currency);

		MarginEstimationSettings marginEstimationSettings = MarginEstimationSettings.CornishFischer
			(MarginEstimationSettings.POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_ISDA);

		RiskMeasureSensitivitySettingsIR riskMeasureSensitivitySettingsIR =
			RiskMeasureSensitivitySettingsIR.ISDA_VEGA_20 (currencyList);

		BucketSensitivityIR bucketSensitivityIR = new BucketSensitivityIR (
			CurveTenorSensitivityMap (notional),
			CurveTenorSensitivityMap (notional),
			CurveTenorSensitivityMap (notional),
			CurveTenorSensitivityMap (notional),
			CurveTenorSensitivityMap (notional),
			CurveTenorSensitivityMap (notional),
			CurveTenorSensitivityMap (notional)
		);

		DisplayBucketSensitivityIR (
			currency,
			bucketSensitivityIR
		);

		Map<String, BucketSensitivityIR> bucketSensitivityMap = new HashMap<String, BucketSensitivityIR>();

		bucketSensitivityMap.put (
			currency,
			bucketSensitivityIR
		);

		RiskMeasureSensitivityIR riskClassSensitivityIR = new RiskMeasureSensitivityIR
			(bucketSensitivityMap);

		RiskMeasureAggregateIR riskMeasureAggregateIR = riskClassSensitivityIR.linearAggregate (
			riskMeasureSensitivitySettingsIR,
			marginEstimationSettings
		);

		VegaMarginCovarianceEntry (
			currency,
			riskMeasureAggregateIR.bucketAggregateMap().get (currency).sensitivityAggregate()
		);

		DisplayRiskMeasureAggregate (riskMeasureAggregateIR);

		EnvManager.TerminateEnv();
	}
}
