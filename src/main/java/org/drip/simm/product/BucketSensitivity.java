
package org.drip.simm.product;

import java.util.Map;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.simm.margin.BucketAggregate;
import org.drip.simm.margin.RiskFactorAggregate;
import org.drip.simm.parameters.BucketCurvatureSettings;
import org.drip.simm.parameters.BucketSensitivitySettings;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>BucketSensitivity</i> holds the Risk Factor Sensitivities inside a single Bucket. The References are:
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
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>BucketSensitivity</i> Constructor</li>
 * 		<li>Retrieve the Map of Risk Factor Sensitivities</li>
 * 		<li>Weight and Adjust the Input Sensitivities</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product/README.md">ISDA SIMM Risk Factor Sensitivities</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BucketSensitivity
{
	private Map<String, Double> _riskFactorSensitivityMap = null;

	private BucketAggregate linearAggregate (
		final BucketSensitivitySettings bucketSensitivitySettings)
	{
		double cumulativeRiskFactorSensitivity = 0.;
		double weightedAggregateSensitivityVariance = 0.;

		double memberCorrelation = bucketSensitivitySettings.memberCorrelation();

		double bucketSensitivityRiskWeight = bucketSensitivitySettings.riskWeight();

		double concentrationNormalizer = 1. / bucketSensitivitySettings.concentrationThreshold();

		Map<String, RiskFactorAggregate> augmentedBucketSensitivityMap =
			new CaseInsensitiveHashMap<RiskFactorAggregate>();

		for (Map.Entry<String, Double> riskFactorSensitivityMapEntry : _riskFactorSensitivityMap.entrySet())
		{
			double riskFactorSensitivity = riskFactorSensitivityMapEntry.getValue();

			double concentrationRiskFactor = Math.max (
				1.,
				Math.sqrt (Math.abs (riskFactorSensitivity) * concentrationNormalizer)
			);

			double riskFactorSensitivityMargin = riskFactorSensitivity * bucketSensitivityRiskWeight *
				concentrationRiskFactor;
			cumulativeRiskFactorSensitivity = cumulativeRiskFactorSensitivity + riskFactorSensitivity;

			try {
				augmentedBucketSensitivityMap.put (
					riskFactorSensitivityMapEntry.getKey(),
					new RiskFactorAggregate (riskFactorSensitivityMargin, concentrationRiskFactor)
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		for (Map.Entry<String, RiskFactorAggregate> augmentedBucketSensitivityMapOuterEntry :
			augmentedBucketSensitivityMap.entrySet())
		{
			RiskFactorAggregate augmentedRiskFactorSensitivityOuter =
				augmentedBucketSensitivityMapOuterEntry.getValue();

			double riskFactorSensitivityOuter = augmentedRiskFactorSensitivityOuter.sensitivityMargin();

			double concentrationRiskFactorOuter =
				augmentedRiskFactorSensitivityOuter.concentrationRiskFactor();

			String riskFactorKeyOuter = augmentedBucketSensitivityMapOuterEntry.getKey();

			for (Map.Entry<String, RiskFactorAggregate> augmentedBucketSensitivityMapInnerEntry :
				augmentedBucketSensitivityMap.entrySet())
			{
				RiskFactorAggregate augmentedRiskFactorSensitivityInner =
					augmentedBucketSensitivityMapInnerEntry.getValue();

				double concentrationRiskFactorInner =
					augmentedRiskFactorSensitivityInner.concentrationRiskFactor();

				double riskFactorSensitivityInner = augmentedRiskFactorSensitivityInner.sensitivityMargin();

				double concentrationScaleDown = Math.min (
					concentrationRiskFactorInner,
					concentrationRiskFactorOuter
				) / Math.max (
					concentrationRiskFactorInner,
					concentrationRiskFactorOuter
				);

				weightedAggregateSensitivityVariance = weightedAggregateSensitivityVariance +
					concentrationScaleDown * riskFactorSensitivityOuter * riskFactorSensitivityInner * (
						riskFactorKeyOuter.equalsIgnoreCase (
							augmentedBucketSensitivityMapInnerEntry.getKey()
						) ? 1. : memberCorrelation
					);
			}
		}

		try {
			return new BucketAggregate (
				augmentedBucketSensitivityMap,
				weightedAggregateSensitivityVariance,
				cumulativeRiskFactorSensitivity
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private BucketAggregate curvatureAggregate (
		final BucketSensitivitySettings bucketSensitivitySettings)
	{
		double cumulativeRiskFactorSensitivity = 0.;
		double weightedAggregateSensitivityVariance = 0.;

		double memberCorrelation = bucketSensitivitySettings.memberCorrelation();

		double bucketSensitivityRiskWeight = bucketSensitivitySettings.riskWeight();

		double concentrationNormalizer = 1. / bucketSensitivitySettings.concentrationThreshold();

		Map<String, RiskFactorAggregate> augmentedBucketSensitivityMap =
			new CaseInsensitiveHashMap<RiskFactorAggregate>();

		for (Map.Entry<String, Double> riskFactorSensitivityMapEntry : _riskFactorSensitivityMap.entrySet())
		{
			double riskFactorSensitivity = riskFactorSensitivityMapEntry.getValue();

			double concentrationRiskFactor = Math.max (
				1.,
				Math.sqrt (Math.abs (riskFactorSensitivity) * concentrationNormalizer)
			);

			double riskFactorSensitivityMargin = riskFactorSensitivity * bucketSensitivityRiskWeight *
				concentrationRiskFactor;
			cumulativeRiskFactorSensitivity = cumulativeRiskFactorSensitivity + riskFactorSensitivity;

			try {
				augmentedBucketSensitivityMap.put (
					riskFactorSensitivityMapEntry.getKey(),
					new RiskFactorAggregate (riskFactorSensitivityMargin, concentrationRiskFactor)
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		for (Map.Entry<String, RiskFactorAggregate> augmentedBucketSensitivityMapOuterEntry :
			augmentedBucketSensitivityMap.entrySet())
		{
			RiskFactorAggregate augmentedRiskFactorSensitivityOuter =
				augmentedBucketSensitivityMapOuterEntry.getValue();

			double riskFactorSensitivityOuter = augmentedRiskFactorSensitivityOuter.sensitivityMargin();

			String riskFactorKeyOuter = augmentedBucketSensitivityMapOuterEntry.getKey();

			for (Map.Entry<String, RiskFactorAggregate> augmentedBucketSensitivityMapInnerEntry :
				augmentedBucketSensitivityMap.entrySet())
			{
				weightedAggregateSensitivityVariance = weightedAggregateSensitivityVariance +
					riskFactorSensitivityOuter *
					augmentedBucketSensitivityMapInnerEntry.getValue().sensitivityMargin() * (
						riskFactorKeyOuter.equalsIgnoreCase (
							augmentedBucketSensitivityMapInnerEntry.getKey()
						) ? 1. : memberCorrelation * memberCorrelation
					);
			}
		}

		try {
			return new BucketAggregate (
				augmentedBucketSensitivityMap,
				weightedAggregateSensitivityVariance,
				cumulativeRiskFactorSensitivity
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>BucketSensitivity</i> Constructor
	 * 
	 * @param riskFactorSensitivityMap The Map of Risk Factor Sensitivities
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BucketSensitivity (
		final Map<String, Double> riskFactorSensitivityMap)
		throws Exception
	{
		if (null == (_riskFactorSensitivityMap = riskFactorSensitivityMap) ||
			0 == _riskFactorSensitivityMap.size())
		{
			throw new Exception ("BucketSensitivity Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Map of Risk Factor Sensitivities
	 * 
	 * @return The Map of Risk Factor Sensitivities
	 */

	public Map<String, Double> riskFactorSensitivityMap()
	{
		return _riskFactorSensitivityMap;
	}

	/**
	 * Weight and Adjust the Input Sensitivities
	 * 
	 * @param bucketSensitivitySettings The Bucket Sensitivity Settings
	 * 
	 * @return Map of Weighted and Adjusted Input Sensitivities
	 */

	public BucketAggregate aggregate (
		final BucketSensitivitySettings bucketSensitivitySettings)
	{
		return null == bucketSensitivitySettings ? null :
			bucketSensitivitySettings instanceof BucketCurvatureSettings ?
				curvatureAggregate (bucketSensitivitySettings) : linearAggregate (bucketSensitivitySettings);
	}
}
