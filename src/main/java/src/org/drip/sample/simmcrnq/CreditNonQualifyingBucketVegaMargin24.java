
package org.drip.sample.simmcrnq;

import java.util.HashMap;
import java.util.Map;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.simm.foundation.MarginEstimationSettings;
import org.drip.simm.margin.RiskMeasureAggregateCR;
import org.drip.simm.parameters.RiskMeasureSensitivitySettingsCR;
import org.drip.simm.product.BucketSensitivityCR;
import org.drip.simm.product.RiskFactorTenorSensitivity;
import org.drip.simm.product.RiskMeasureSensitivityCR;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
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
 * <i>CreditNonQualifyingBucketVegaMargin24</i> illustrates the Computation of the SIMM 2.4 CR Vega Margin
 * 	for a Bucket's Non-Qualifying Credit Exposure Sensitivities. The References are:
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
 *  		International Swaps and Derivatives Association (2021): SIMM v2.4 Methodology
 *  			https://www.isda.org/a/CeggE/ISDA-SIMM-v2.4-PUBLIC.pdf
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simmcrnq/README.md">ISDA SIMM Credit Non-Qualifying Estimates</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CreditNonQualifyingBucketVegaMargin24
{

	private static final void AddTenorSensitivity (
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

		return new RiskFactorTenorSensitivity (
			tenorSensitivityMap
		);
	}

	private static final void DisplayComponentTenorSensitivity (
		final String componentName,
		final RiskFactorTenorSensitivity tenorSensitivityMap)
		throws Exception
	{
		System.out.println();

		System.out.println (
			"\t|--------------||"
		);

		System.out.println (
			"\t|  " + componentName + " VEGA    ||"
		);

		System.out.println (
			"\t|--------------||"
		);

		System.out.println (
			"\t|              ||"
		);

		System.out.println (
			"\t|  L -> R:     ||"
		);

		System.out.println (
			"\t|    - Tenor   ||"
		);

		System.out.println (
			"\t|    - Delta   ||"
		);

		System.out.println ("\t|--------------||");

		for (Map.Entry<String, Double> tenorSensitivityEntry :
			tenorSensitivityMap.sensitivityMap().entrySet())
		{
			System.out.println (
				"\t| " +
				tenorSensitivityEntry.getKey() + " => " +
				FormatUtil.FormatDouble (
					tenorSensitivityEntry.getValue(),
					2,
					2,
					1.
				) + " ||"
			);
		}

		System.out.println (
			"\t|--------------||"
		);

		System.out.println();
	}

	private static final void ComponentRiskFactorTenorSensitivity (
		final Map<String, RiskFactorTenorSensitivity> tenorSensitivityMap,
		final double notional,
		final String componentName)
		throws Exception
	{
		tenorSensitivityMap.put (
			componentName,
			CurveTenorSensitivityMap (
				notional
			)
		);
	}

	private static final void DisplayRiskMeasureAggregate (
		final RiskMeasureAggregateCR riskMeasureAggregateCR)
		throws Exception
	{
		System.out.println (
			"\t||--------------------------------------------||"
		);

		System.out.println (
			"\t||   CR RISK CLASS AGGREGATE MARGIN METRICS   ||"
		);

		System.out.println (
			"\t||--------------------------------------------||"
		);

		System.out.println (
			"\t|| Core Vega SBA Variance      => " +
			FormatUtil.FormatDouble (
				riskMeasureAggregateCR.coreSBAVariance(),
				10,
				0,
				1.
			) + " ||"
		);

		System.out.println (
			"\t|| Residual Vega SBA Variance  => " +
			FormatUtil.FormatDouble (
				riskMeasureAggregateCR.residualSBAVariance(),
				10,
				0,
				1.
			) + " ||"
		);

		System.out.println (
			"\t|| Vega SBA                    => " +
			FormatUtil.FormatDouble (
				riskMeasureAggregateCR.sba(),
				10,
				0,
				1.
			) + " ||"
		);

		System.out.println (
			"\t||--------------------------------------------||"
		);

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
			RiskMeasureSensitivitySettingsCR.ISDA_CRNQ_VEGA_24();

		Map<String, BucketSensitivityCR> bucketSensitivityMap = new
			CaseInsensitiveHashMap<BucketSensitivityCR>();

		bucketSensitivityMap.put (
			"" + bucketIndex,
			bucketSensitivityCR
		);

		RiskMeasureSensitivityCR riskMeasureSensitivityCR = new RiskMeasureSensitivityCR
			(bucketSensitivityMap);

		RiskMeasureAggregateCR riskMeasureAggregateCR = riskMeasureSensitivityCR.linearAggregate (
			riskMeasureSensitivitySettingsCR,
			marginEstimationSettings
		);

		DisplayRiskMeasureAggregate (riskMeasureAggregateCR);

		EnvManager.TerminateEnv();
	}
}
