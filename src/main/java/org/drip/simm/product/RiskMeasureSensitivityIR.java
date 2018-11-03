
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
 * RiskMeasureSensitivityIR holds the Risk Class Bucket Sensitivities for the IR Risk Measure. The References
 *  are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  	Calculations, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488, eSSRN.
 *  
 *  - Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  	Framework for Forecasting .Initial Margin Requirements,
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

public class RiskMeasureSensitivityIR
{
	private java.util.Map<java.lang.String, org.drip.simm.product.BucketSensitivityIR> _bucketSensitivityMap
		= null;

	private static final double PositionPrincipalComponentCovariance (
		final org.drip.simm.margin.BucketAggregateIR bucketAggregate,
		final org.drip.simm.parameters.MarginEstimationSettings marginEstimationSettings)
		throws java.lang.Exception
	{
		java.lang.String positionPrincipalComponentScheme =
			marginEstimationSettings.positionPrincipalComponentScheme();

		if (positionPrincipalComponentScheme.equalsIgnoreCase
			(org.drip.simm.parameters.MarginEstimationSettings.POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_FRTB))
		{
			return bucketAggregate.positionPrincipalComponentCovarianceFRTB();
		}

		if (positionPrincipalComponentScheme.equalsIgnoreCase
			(org.drip.simm.parameters.MarginEstimationSettings.POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_ISDA))
		{
			return bucketAggregate.positionPrincipalComponentCovarianceISDA();
		}

		throw new java.lang.Exception
			("RiskMeasureSensitivityIR::PositionPrincipalComponentCovariance => Invalid Inputs");
	}

	/**
	 * RiskMeasureSensitivityIR Constructor
	 * 
	 * @param bucketSensitivityMap The IR Class Bucket Sensitivity Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskMeasureSensitivityIR (
		final java.util.Map<java.lang.String, org.drip.simm.product.BucketSensitivityIR>
			bucketSensitivityMap)
		throws java.lang.Exception
	{
		if (null == (_bucketSensitivityMap = bucketSensitivityMap) || 0 == _bucketSensitivityMap.size())
		{
			throw new java.lang.Exception ("RiskMeasureSensitivityIR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Risk Class Bucket Sensitivity Map
	 * 
	 * @return The Risk Class Bucket Sensitivity Map
	 */

	public java.util.Map<java.lang.String, org.drip.simm.product.BucketSensitivityIR>
		bucketSensitivityMap()
	{
		return _bucketSensitivityMap;
	}

	/**
	 * Generate the Linear Risk Measure Aggregate
	 * 
	 * @param riskMeasureSensitivitySettings The Risk Measure Sensitivity Settings
	 * @param marginEstimationSettings Margin Estimation Settings
	 * 
	 * @return The Linear Risk Measure Aggregate
	 */

	public org.drip.simm.margin.RiskMeasureAggregateIR linearAggregate (
		final org.drip.simm.parameters.RiskMeasureSensitivitySettingsIR riskMeasureSensitivitySettings,
		final org.drip.simm.parameters.MarginEstimationSettings marginEstimationSettings)
	{
		if (null == riskMeasureSensitivitySettings ||
			null == marginEstimationSettings)
		{
			return null;
		}

		double coreSBAVariance = 0.;

		java.util.Map<java.lang.String, org.drip.simm.margin.BucketAggregateIR> bucketAggregateMap = new
			java.util.TreeMap<java.lang.String, org.drip.simm.margin.BucketAggregateIR>();

		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsIR>
			bucketSensitivitySettingsMap = riskMeasureSensitivitySettings.bucketSensitivitySettingsMap();

		org.drip.measure.stochastic.LabelCorrelation crossBucketCorrelation =
			riskMeasureSensitivitySettings.crossBucketCorrelation();

		for (java.util.Map.Entry<java.lang.String, org.drip.simm.product.BucketSensitivityIR>
			bucketSensitivityMapEntry : _bucketSensitivityMap.entrySet())
		{
			java.lang.String bucketIndex = bucketSensitivityMapEntry.getKey();

			org.drip.simm.product.BucketSensitivityIR bucketSensitivity =
				bucketSensitivityMapEntry.getValue();

			org.drip.simm.margin.BucketAggregateIR bucketAggregate = bucketSensitivity.aggregate
				(bucketSensitivitySettingsMap.get (bucketIndex));

			if (null == bucketAggregate)
			{
				return null;
			}

			bucketAggregateMap.put (
				"" + bucketIndex,
				bucketAggregate
			);
		}

		try
		{
			for (java.util.Map.Entry<java.lang.String, org.drip.simm.margin.BucketAggregateIR>
				bucketAggregateMapOuterEntry : bucketAggregateMap.entrySet())
			{
				java.lang.String outerKey = bucketAggregateMapOuterEntry.getKey();

				org.drip.simm.margin.BucketAggregateIR bucketAggregateOuter =
					bucketAggregateMapOuterEntry.getValue();

				double weightedSensitivityVarianceOuter = bucketAggregateOuter.sensitivityMarginVariance();

				double positionPrincipalComponentCovarianceOuter = PositionPrincipalComponentCovariance (
					bucketAggregateOuter,
					marginEstimationSettings
				);

				for (java.util.Map.Entry<java.lang.String, org.drip.simm.margin.BucketAggregateIR>
					bucketAggregateMapInnerEntry : bucketAggregateMap.entrySet())
				{
					java.lang.String innerKey = bucketAggregateMapInnerEntry.getKey();

					coreSBAVariance = coreSBAVariance + (outerKey.equalsIgnoreCase (innerKey) ?
						weightedSensitivityVarianceOuter : crossBucketCorrelation.entry (
							outerKey,
							innerKey
						) * positionPrincipalComponentCovarianceOuter *
						PositionPrincipalComponentCovariance (
							bucketAggregateMapInnerEntry.getValue(),
							marginEstimationSettings
						)
					);
				}
			}

			return new org.drip.simm.margin.RiskMeasureAggregateIR (
				bucketAggregateMap,
				coreSBAVariance,
				0.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Curvature Risk Measure Aggregate
	 * 
	 * @param riskMeasureSensitivitySettings The Risk Measure Sensitivity Settings
	 * @param marginEstimationSettings Margin Estimation Settings
	 * 
	 * @return The Curvature Risk Measure Aggregate
	 */

	public org.drip.simm.margin.RiskMeasureAggregateIR curvatureAggregate (
		final org.drip.simm.parameters.RiskMeasureSensitivitySettingsIR riskMeasureSensitivitySettings,
		final org.drip.simm.parameters.MarginEstimationSettings marginEstimationSettings)
	{
		if (null == riskMeasureSensitivitySettings || null == marginEstimationSettings)
		{
			return null;
		}

		double coreSBAVariance = 0.;
		double cumulativeRiskFactorSensitivityMarginCore = 0.;
		double cumulativeRiskFactorSensitivityMarginCorePositive = 0.;

		java.util.Map<java.lang.String, org.drip.simm.margin.BucketAggregateIR> bucketAggregateMap = new
			java.util.TreeMap<java.lang.String, org.drip.simm.margin.BucketAggregateIR>();

		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsIR>
			bucketSensitivitySettingsMap = riskMeasureSensitivitySettings.bucketSensitivitySettingsMap();

		org.drip.measure.stochastic.LabelCorrelation crossBucketCorrelation =
			riskMeasureSensitivitySettings.crossBucketCorrelation();

		org.drip.simm.foundation.CurvatureEstimator curvatureEstimator =
			marginEstimationSettings.curvatureEstimator();

		boolean isCorrelatorQuadratric = curvatureEstimator.isCorrelatorQuadratric();

		for (java.util.Map.Entry<java.lang.String, org.drip.simm.product.BucketSensitivityIR>
			bucketSensitivityMapEntry : _bucketSensitivityMap.entrySet())
		{
			java.lang.String bucketIndex = bucketSensitivityMapEntry.getKey();

			org.drip.simm.product.BucketSensitivityIR bucketSensitivity =
				bucketSensitivityMapEntry.getValue();

			org.drip.simm.margin.BucketAggregateIR bucketAggregate = bucketSensitivity.aggregate
				(bucketSensitivitySettingsMap.get (bucketIndex));

			if (null == bucketAggregate)
			{
				return null;
			}

			double bucketCumulativeRiskFactorSensitivityMargin =
				bucketAggregate.cumulativeSensitivityMargin();

			cumulativeRiskFactorSensitivityMarginCore = cumulativeRiskFactorSensitivityMarginCore +
				bucketCumulativeRiskFactorSensitivityMargin;

			cumulativeRiskFactorSensitivityMarginCorePositive =
				cumulativeRiskFactorSensitivityMarginCorePositive +
				java.lang.Math.max (
					bucketCumulativeRiskFactorSensitivityMargin,
					0.
				);

			bucketAggregateMap.put (
				bucketIndex,
				bucketAggregate
			);
		}

		try
		{
			for (java.util.Map.Entry<java.lang.String, org.drip.simm.margin.BucketAggregateIR>
				bucketAggregateMapOuterEntry : bucketAggregateMap.entrySet())
			{
				java.lang.String outerKey = bucketAggregateMapOuterEntry.getKey();

				org.drip.simm.margin.BucketAggregateIR bucketAggregateOuter =
					bucketAggregateMapOuterEntry.getValue();

				double weightedSensitivityVarianceOuter = bucketAggregateOuter.sensitivityMarginVariance();

				double positionPrincipalComponentCovarianceOuter = PositionPrincipalComponentCovariance (
					bucketAggregateOuter,
					marginEstimationSettings
				);

				for (java.util.Map.Entry<java.lang.String, org.drip.simm.margin.BucketAggregateIR>
					bucketAggregateMapInnerEntry : bucketAggregateMap.entrySet())
				{
					java.lang.String innerKey = bucketAggregateMapInnerEntry.getKey();

					if (outerKey.equalsIgnoreCase (innerKey))
					{
						coreSBAVariance = coreSBAVariance + weightedSensitivityVarianceOuter;
					}
					else
					{
						double correlation = crossBucketCorrelation.entry (
							"" + outerKey,
							"" + innerKey
						);

						double curvatureCorrelation = isCorrelatorQuadratric ? correlation * correlation
							: correlation;

						org.drip.simm.margin.BucketAggregateIR bucketAggregateInner =
							bucketAggregateMapInnerEntry.getValue();

						coreSBAVariance = coreSBAVariance + curvatureCorrelation *
							positionPrincipalComponentCovarianceOuter *
							curvatureEstimator.varianceModulator (
								outerKey,
								weightedSensitivityVarianceOuter,
								innerKey,
								bucketAggregateInner.sensitivityMarginVariance()
							) * PositionPrincipalComponentCovariance (
								bucketAggregateInner,
								marginEstimationSettings
							);
					}
				}
			}

			double coreSBAMargin = curvatureEstimator.margin (
				cumulativeRiskFactorSensitivityMarginCore,
				cumulativeRiskFactorSensitivityMarginCorePositive,
				coreSBAVariance
			);

			return new org.drip.simm.margin.RiskMeasureAggregateIR (
				bucketAggregateMap,
				coreSBAMargin * coreSBAMargin,
				0.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
