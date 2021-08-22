
package org.drip.sample.simmeq;

import java.util.Map;
import java.util.TreeMap;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.simm.foundation.MarginEstimationSettings;
import org.drip.simm.margin.BucketAggregate;
import org.drip.simm.margin.RiskMeasureAggregate;
import org.drip.simm.parameters.RiskMeasureSensitivitySettings;
import org.drip.simm.product.BucketSensitivity;
import org.drip.simm.product.RiskMeasureSensitivity;

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
 * <i>EquityVegaMargin20</i> illustrates the Computation of the SIMM 2.0 Vega Margin across a Group of Equity
 * 	Bucket Exposure Sensitivities. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simmeq/README.md">ISDA SIMM Equity Estimate Runs</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EquityVegaMargin20
{

	private static final void AddBucketRiskFactorSensitivity (
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

	private static final Map<String, Map<String, Double>> BucketRiskFactorSensitivityMap (
		final double notional)
		throws Exception
	{
		Map<String, Map<String, Double>> bucketRiskFactorSensitivityMap =
			new TreeMap<String, Map<String, Double>>();

		AddBucketRiskFactorSensitivity (
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

		AddBucketRiskFactorSensitivity (
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

		AddBucketRiskFactorSensitivity (
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

		AddBucketRiskFactorSensitivity (
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

		AddBucketRiskFactorSensitivity (
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

		AddBucketRiskFactorSensitivity (
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

		AddBucketRiskFactorSensitivity (
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

		AddBucketRiskFactorSensitivity (
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

		AddBucketRiskFactorSensitivity (
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

		AddBucketRiskFactorSensitivity (
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

		AddBucketRiskFactorSensitivity (
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

		AddBucketRiskFactorSensitivity (
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

		AddBucketRiskFactorSensitivity (
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

	private static final void DisplayBucketRiskFactorSensitivity (
		final Map<String, Map<String, Double>> bucketRiskFactorSensitivityMap)
		throws Exception
	{
		System.out.println ("\t|--------------------------||");

		System.out.println ("\t|     RISK FACTOR VEGA     ||");

		System.out.println ("\t|--------------------------||");

		System.out.println ("\t|  L -> R:                 ||");

		System.out.println ("\t|    - Ticker              ||");

		System.out.println ("\t|    - Bucket              ||");

		System.out.println ("\t|    - Vega                ||");

		System.out.println ("\t|--------------------------||");

		for (Map.Entry<String, Map<String, Double>> bucketSensitivityMapEntry :
			bucketRiskFactorSensitivityMap.entrySet())
		{
			String bucketIndex = bucketSensitivityMapEntry.getKey();

			Map<String, Double> riskFactorSensitivityMap = bucketSensitivityMapEntry.getValue();

			for (Map.Entry<String, Double> riskFactorSensitivityMapEntry :
				riskFactorSensitivityMap.entrySet())
			{
				String currency = riskFactorSensitivityMapEntry.getKey();

				double riskFactorSensitivity = riskFactorSensitivityMapEntry.getValue();

				System.out.println (
					"\t| " +
					currency + " => " +
					FormatUtil.FormatDouble (Integer.parseInt (bucketIndex), 2, 0, 1.) + " | " +
					FormatUtil.FormatDouble (riskFactorSensitivity, 2, 2, 1.) + " ||"
				);
			}
		}

		System.out.println ("\t|--------------------------||");

		System.out.println();
	}

	public static void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double notional = 100.;

		MarginEstimationSettings marginEstimationSettings = MarginEstimationSettings.CornishFischer
			(MarginEstimationSettings.POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_ISDA);

		RiskMeasureSensitivitySettings riskMeasureSensitivitySettings =
			RiskMeasureSensitivitySettings.ISDA_EQ_VEGA_20();

		Map<String, Map<String, Double>> bucketRiskFactorSensitivityMap = BucketRiskFactorSensitivityMap
			(notional);

		DisplayBucketRiskFactorSensitivity (bucketRiskFactorSensitivityMap);

		Map<String, BucketSensitivity> bucketSensitivityMap = new TreeMap<String, BucketSensitivity>();

		System.out.println ("\t|------------------------||");

		System.out.println ("\t|    BUCKET AGGREGATE    ||");

		System.out.println ("\t|------------------------||");

		System.out.println ("\t|  L -> R:               ||");

		System.out.println ("\t|    - Bucket Index      ||");

		System.out.println ("\t|    - Bucket Margin     ||");

		System.out.println ("\t|    - Bucket Vega       ||");

		System.out.println ("\t|------------------------||");

		for (Map.Entry<String, Map<String, Double>> bucketSensitivityMapEntry :
			bucketRiskFactorSensitivityMap.entrySet())
		{
			String bucketIndex = bucketSensitivityMapEntry.getKey();

			BucketSensitivity bucketSensitivity = new BucketSensitivity
				(bucketSensitivityMapEntry.getValue());

			bucketSensitivityMap.put (
				"" + bucketIndex,
				bucketSensitivity
			);

			BucketAggregate bucketAggregate = bucketSensitivity.aggregate
				(riskMeasureSensitivitySettings.bucketSettingsMap().get (bucketIndex));

			System.out.println ("\t| " +
				FormatUtil.FormatDouble (Integer.parseInt (bucketIndex), 2, 0, 1.) + " => " +
				FormatUtil.FormatDouble (Math.sqrt (bucketAggregate.sensitivityMarginVariance()), 5, 0, 1.) + " | " +
				FormatUtil.FormatDouble (bucketAggregate.cumulativeSensitivityMargin(), 5, 0, 1.) + " ||"
			);
		}

		System.out.println ("\t|------------------------||");

		System.out.println();

		RiskMeasureAggregate riskMeasureAggregate =
			new RiskMeasureSensitivity (bucketSensitivityMap).linearAggregate (
				riskMeasureSensitivitySettings,
				marginEstimationSettings
			);

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
			FormatUtil.FormatDouble (Math.sqrt (riskMeasureAggregate.coreSBAVariance()), 5, 0, 1.) +
				" | " +
			FormatUtil.FormatDouble (Math.sqrt (riskMeasureAggregate.residualSBAVariance()), 5, 0, 1.) +
				" | " +
			FormatUtil.FormatDouble (riskMeasureAggregate.sba(), 5, 0, 1.) + " ||"
		);

		System.out.println ("\t|-----------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
