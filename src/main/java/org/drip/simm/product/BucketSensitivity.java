
package org.drip.simm.product;

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
	private java.util.Map<java.lang.String, java.lang.Double> _riskFactorSensitivityMap = null;

	private org.drip.simm.margin.BucketAggregate linearAggregate (
		final org.drip.simm.parameters.BucketSensitivitySettings bucketSensitivitySettings)
	{
		double cumulativeRiskFactorSensitivity = 0.;
		double weightedAggregateSensitivityVariance = 0.;

		double memberCorrelation = bucketSensitivitySettings.memberCorrelation();

		double bucketSensitivityRiskWeight = bucketSensitivitySettings.riskWeight();

		double concentrationNormalizer = 1. / bucketSensitivitySettings.concentrationThreshold();

		java.util.Map<java.lang.String, org.drip.simm.margin.RiskFactorAggregate>
			augmentedBucketSensitivityMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.simm.margin.RiskFactorAggregate>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> riskFactorSensitivityMapEntry :
			_riskFactorSensitivityMap.entrySet())
		{
			double riskFactorSensitivity = riskFactorSensitivityMapEntry.getValue();

			double concentrationRiskFactor = java.lang.Math.max (
				1.,
				java.lang.Math.sqrt (java.lang.Math.abs (riskFactorSensitivity) * concentrationNormalizer)
			);

			double riskFactorSensitivityMargin = riskFactorSensitivity * bucketSensitivityRiskWeight *
				concentrationRiskFactor;
			cumulativeRiskFactorSensitivity = cumulativeRiskFactorSensitivity + riskFactorSensitivity;

			try
			{
				augmentedBucketSensitivityMap.put (
					riskFactorSensitivityMapEntry.getKey(),
					new org.drip.simm.margin.RiskFactorAggregate (
						riskFactorSensitivityMargin,
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

		for (java.util.Map.Entry<java.lang.String, org.drip.simm.margin.RiskFactorAggregate>
			augmentedBucketSensitivityMapOuterEntry : augmentedBucketSensitivityMap.entrySet())
		{
			org.drip.simm.margin.RiskFactorAggregate augmentedRiskFactorSensitivityOuter =
				augmentedBucketSensitivityMapOuterEntry.getValue();

			double riskFactorSensitivityOuter = augmentedRiskFactorSensitivityOuter.sensitivityMargin();

			double concentrationRiskFactorOuter =
				augmentedRiskFactorSensitivityOuter.concentrationRiskFactor();

			java.lang.String riskFactorKeyOuter = augmentedBucketSensitivityMapOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, org.drip.simm.margin.RiskFactorAggregate>
				augmentedBucketSensitivityMapInnerEntry : augmentedBucketSensitivityMap.entrySet())
			{
				org.drip.simm.margin.RiskFactorAggregate augmentedRiskFactorSensitivityInner =
					augmentedBucketSensitivityMapInnerEntry.getValue();

				double concentrationRiskFactorInner =
					augmentedRiskFactorSensitivityInner.concentrationRiskFactor();

				double riskFactorSensitivityInner =
					augmentedRiskFactorSensitivityInner.sensitivityMargin();

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
			return new org.drip.simm.margin.BucketAggregate (
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

	private org.drip.simm.margin.BucketAggregate curvatureAggregate (
		final org.drip.simm.parameters.BucketSensitivitySettings bucketSensitivitySettings)
	{
		double cumulativeRiskFactorSensitivity = 0.;
		double weightedAggregateSensitivityVariance = 0.;

		double memberCorrelation = bucketSensitivitySettings.memberCorrelation();

		double bucketSensitivityRiskWeight = bucketSensitivitySettings.riskWeight();

		double concentrationNormalizer = 1. / bucketSensitivitySettings.concentrationThreshold();

		java.util.Map<java.lang.String, org.drip.simm.margin.RiskFactorAggregate>
			augmentedBucketSensitivityMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.simm.margin.RiskFactorAggregate>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> riskFactorSensitivityMapEntry :
			_riskFactorSensitivityMap.entrySet())
		{
			double riskFactorSensitivity = riskFactorSensitivityMapEntry.getValue();

			double concentrationRiskFactor = java.lang.Math.max (
				1.,
				java.lang.Math.sqrt (java.lang.Math.abs (riskFactorSensitivity) * concentrationNormalizer)
			);

			double riskFactorSensitivityMargin = riskFactorSensitivity * bucketSensitivityRiskWeight *
				concentrationRiskFactor;
			cumulativeRiskFactorSensitivity = cumulativeRiskFactorSensitivity + riskFactorSensitivity;

			try
			{
				augmentedBucketSensitivityMap.put (
					riskFactorSensitivityMapEntry.getKey(),
					new org.drip.simm.margin.RiskFactorAggregate (
						riskFactorSensitivityMargin,
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

		for (java.util.Map.Entry<java.lang.String, org.drip.simm.margin.RiskFactorAggregate>
			augmentedBucketSensitivityMapOuterEntry : augmentedBucketSensitivityMap.entrySet())
		{
			org.drip.simm.margin.RiskFactorAggregate augmentedRiskFactorSensitivityOuter =
				augmentedBucketSensitivityMapOuterEntry.getValue();

			double riskFactorSensitivityOuter = augmentedRiskFactorSensitivityOuter.sensitivityMargin();

			java.lang.String riskFactorKeyOuter = augmentedBucketSensitivityMapOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, org.drip.simm.margin.RiskFactorAggregate>
				augmentedBucketSensitivityMapInnerEntry : augmentedBucketSensitivityMap.entrySet())
			{
				org.drip.simm.margin.RiskFactorAggregate augmentedRiskFactorSensitivityInner =
					augmentedBucketSensitivityMapInnerEntry.getValue();

				weightedAggregateSensitivityVariance = weightedAggregateSensitivityVariance +
					riskFactorSensitivityOuter * (riskFactorKeyOuter.equalsIgnoreCase
						(augmentedBucketSensitivityMapInnerEntry.getKey()) ? 1. : memberCorrelation *
							memberCorrelation) * augmentedRiskFactorSensitivityInner.sensitivityMargin();
			}
		}

		try
		{
			return new org.drip.simm.margin.BucketAggregate (
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

	/**
	 * BucketSensitivity Constructor
	 * 
	 * @param riskFactorSensitivityMap The Map of Risk Factor Sensitivities
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketSensitivity (
		final java.util.Map<java.lang.String, java.lang.Double> riskFactorSensitivityMap)
		throws java.lang.Exception
	{
		if (null == (_riskFactorSensitivityMap = riskFactorSensitivityMap) ||
			0 == _riskFactorSensitivityMap.size())
		{
			throw new java.lang.Exception ("BucketSensitivity Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Map of Risk Factor Sensitivities
	 * 
	 * @return The Map of Risk Factor Sensitivities
	 */

	public java.util.Map<java.lang.String, java.lang.Double> riskFactorSensitivityMap()
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

	public org.drip.simm.margin.BucketAggregate aggregate (
		final org.drip.simm.parameters.BucketSensitivitySettings bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		return bucketSensitivitySettings instanceof org.drip.simm.parameters.BucketCurvatureSettings ?
			curvatureAggregate (bucketSensitivitySettings) : linearAggregate (bucketSensitivitySettings);
	}
}
