
package org.drip.simm20.product;

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
 * BucketSensitivity holds the Risk Factor Sensitivities inside a single Bucket. The References are:
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

public class BucketSensitivity
{
	private java.util.Map<java.lang.String, java.lang.Double> _riskFactorDeltaMap = null;

	/**
	 * BucketSensitivity Constructor
	 * 
	 * @param riskFactorDeltaMap The Map of Risk Factor Deltas
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketSensitivity (
		final java.util.Map<java.lang.String, java.lang.Double> riskFactorDeltaMap)
		throws java.lang.Exception
	{
		if (null == (_riskFactorDeltaMap = riskFactorDeltaMap) || 0 == _riskFactorDeltaMap.size())
		{
			throw new java.lang.Exception ("BucketSensitivity Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Map of Risk Factor Deltas
	 * 
	 * @return The Map of Risk Factor Deltas
	 */

	public java.util.Map<java.lang.String, java.lang.Double> riskFactorDeltaMap()
	{
		return _riskFactorDeltaMap;
	}

	/**
	 * Weight and Adjust the Input Sensitivities
	 * 
	 * @param bucketSensitivitySettings The Bucket Sensitivity Settings
	 * 
	 * @return Map of Weighted and Adjusted Input Sensitivities
	 */

	public org.drip.simm20.margin.BucketAggregate aggregate (
		final org.drip.simm20.parameters.BucketSensitivitySettings bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		double cumulativeRiskFactorSensitivity = 0.;
		double weightedAggregateSensitivityVariance = 0.;

		double memberCorrelation = bucketSensitivitySettings.memberCorrelation();

		double bucketDeltaRiskWeight = bucketSensitivitySettings.deltaRiskWeight();

		double concentrationNormalizer = 1. / bucketSensitivitySettings.concentrationThreshold();

		java.util.Map<java.lang.String, org.drip.simm20.margin.RiskFactorAggregate>
			augmentedBucketSensitivityMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.simm20.margin.RiskFactorAggregate>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> riskFactorDeltaMapEntry :
			_riskFactorDeltaMap.entrySet())
		{
			double riskFactorDelta = riskFactorDeltaMapEntry.getValue();

			double concentrationRiskFactor = java.lang.Math.max (
				1.,
				java.lang.Math.sqrt (java.lang.Math.abs (riskFactorDelta) * concentrationNormalizer)
			);

			double riskFactorSensitivity = riskFactorDelta * bucketDeltaRiskWeight * concentrationRiskFactor;
			cumulativeRiskFactorSensitivity = cumulativeRiskFactorSensitivity + riskFactorSensitivity;

			try
			{
				augmentedBucketSensitivityMap.put (
					riskFactorDeltaMapEntry.getKey(),
					new org.drip.simm20.margin.RiskFactorAggregate (
						riskFactorSensitivity,
						concentrationRiskFactor
					)
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		for (java.util.Map.Entry<java.lang.String, org.drip.simm20.margin.RiskFactorAggregate>
			augmentedBucketSensitivityMapOuterEntry : augmentedBucketSensitivityMap.entrySet())
		{
			org.drip.simm20.margin.RiskFactorAggregate augmentedRiskFactorSensitivityOuter =
				augmentedBucketSensitivityMapOuterEntry.getValue();

			double riskFactorSensitivityOuter = augmentedRiskFactorSensitivityOuter.weightedAndNormalized();

			double concentrationRiskFactorOuter =
				augmentedRiskFactorSensitivityOuter.concentrationRiskFactor();

			java.lang.String riskFactorKeyOuter = augmentedBucketSensitivityMapOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, org.drip.simm20.margin.RiskFactorAggregate>
				augmentedBucketSensitivityMapInnerEntry : augmentedBucketSensitivityMap.entrySet())
			{
				org.drip.simm20.margin.RiskFactorAggregate augmentedRiskFactorSensitivityInner =
					augmentedBucketSensitivityMapInnerEntry.getValue();

				double concentrationRiskFactorInner =
					augmentedRiskFactorSensitivityInner.concentrationRiskFactor();

				double riskFactorSensitivityInner =
					augmentedRiskFactorSensitivityInner.weightedAndNormalized();

				double concentrationScaleDown = java.lang.Math.min (
					concentrationRiskFactorInner,
					concentrationRiskFactorOuter
				) / java.lang.Math.max (
					concentrationRiskFactorInner,
					concentrationRiskFactorOuter
				);

				weightedAggregateSensitivityVariance = weightedAggregateSensitivityVariance +
					concentrationScaleDown * riskFactorSensitivityOuter *
						(riskFactorKeyOuter.equalsIgnoreCase
							(augmentedBucketSensitivityMapInnerEntry.getKey()) ? 1. : memberCorrelation) *
								riskFactorSensitivityInner;
			}
		}

		try
		{
			return new org.drip.simm20.margin.BucketAggregate (
				augmentedBucketSensitivityMap,
				weightedAggregateSensitivityVariance,
				cumulativeRiskFactorSensitivity
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
