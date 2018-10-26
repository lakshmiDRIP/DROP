
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
 * RiskMeasureSensitivity holds the Risk Class Bucket Sensitivities for a single Risk Measure. The References
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

public class RiskMeasureSensitivity
{
	private java.util.Map<java.lang.String, org.drip.simm.product.BucketSensitivity> _bucketSensitivityMap =
		null;

	private static final double PositionPrincipalComponentCovariance (
		final org.drip.simm.margin.BucketAggregate bucketAggregate,
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
			("RiskMeasureSensitivity::PositionPrincipalComponentCovariance => Invalid Inputs");
	}

	/**
	 * RiskMeasureSensitivity Constructor
	 * 
	 * @param bucketSensitivityMap The Risk Class Bucket Sensitivity Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskMeasureSensitivity (
		final java.util.Map<java.lang.String, org.drip.simm.product.BucketSensitivity>
			bucketSensitivityMap)
		throws java.lang.Exception
	{
		if (null == (_bucketSensitivityMap = bucketSensitivityMap) || 0 == _bucketSensitivityMap.size())
		{
			throw new java.lang.Exception ("RiskMeasureSensitivity Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Risk Class Bucket Sensitivity Map
	 * 
	 * @return The Risk Class Bucket Sensitivity Map
	 */

	public java.util.Map<java.lang.String, org.drip.simm.product.BucketSensitivity> bucketSensitivityMap()
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

	public org.drip.simm.margin.RiskMeasureAggregate linearAggregate (
		final org.drip.simm.parameters.RiskMeasureSensitivitySettings riskMeasureSensitivitySettings,
		final org.drip.simm.parameters.MarginEstimationSettings marginEstimationSettings)
	{
		if (null == _bucketSensitivityMap ||
			null == riskMeasureSensitivitySettings ||
			null == marginEstimationSettings)
		{
			return null;
		}

		double coreSBAVariance = 0.;

		java.util.Map<java.lang.String, org.drip.simm.margin.BucketAggregate> bucketAggregateMap = new
			java.util.TreeMap<java.lang.String, org.drip.simm.margin.BucketAggregate>();

		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketSensitivitySettingsMap = riskMeasureSensitivitySettings.bucketSettingsMap();

		org.drip.measure.stochastic.LabelCorrelation crossBucketCorrelation =
			riskMeasureSensitivitySettings.crossBucketCorrelation();

		for (java.util.Map.Entry<java.lang.String, org.drip.simm.product.BucketSensitivity>
			bucketSensitivityEntry : _bucketSensitivityMap.entrySet())
		{
			java.lang.String bucketIndex = bucketSensitivityEntry.getKey();

			org.drip.simm.product.BucketSensitivity bucketSensitivity = bucketSensitivityEntry.getValue();

			org.drip.simm.margin.BucketAggregate bucketAggregate = bucketSensitivity.aggregate
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
			for (java.util.Map.Entry<java.lang.String, org.drip.simm.margin.BucketAggregate>
				bucketAggregateMapOuterEntry : bucketAggregateMap.entrySet())
			{
				java.lang.String outerKey = bucketAggregateMapOuterEntry.getKey();

				org.drip.simm.margin.BucketAggregate bucketAggregateOuter =
					bucketAggregateMapOuterEntry.getValue();

				double weightedSensitivityVarianceOuter = bucketAggregateOuter.sensitivityMarginVariance();

				double positionPrincipalComponentCovarianceOuter = PositionPrincipalComponentCovariance (
					bucketAggregateOuter,
					marginEstimationSettings
				);

				for (java.util.Map.Entry<java.lang.String, org.drip.simm.margin.BucketAggregate>
					bucketAggregateMapInnerEntry : bucketAggregateMap.entrySet())
				{
					java.lang.String innerKey = bucketAggregateMapInnerEntry.getKey();

					if (!"-1".equalsIgnoreCase (outerKey) && !"-1".equalsIgnoreCase (innerKey))
					{
						coreSBAVariance = coreSBAVariance + (outerKey.equalsIgnoreCase (innerKey) ?
							weightedSensitivityVarianceOuter : crossBucketCorrelation.entry (
								"" + outerKey,
								"" + innerKey
							) * positionPrincipalComponentCovarianceOuter *
							PositionPrincipalComponentCovariance (
								bucketAggregateMapInnerEntry.getValue(),
								marginEstimationSettings
							)
						);
					}
				}
			}

			return new org.drip.simm.margin.RiskMeasureAggregate (
				bucketAggregateMap,
				coreSBAVariance,
				bucketAggregateMap.containsKey ("-1") ?
					bucketAggregateMap.get ("-1").sensitivityMarginVariance() : 0.
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

	public org.drip.simm.margin.RiskMeasureAggregate curvatureAggregate (
		final org.drip.simm.parameters.RiskMeasureSensitivitySettings riskMeasureSensitivitySettings,
		final org.drip.simm.parameters.MarginEstimationSettings marginEstimationSettings)
	{
		if (null == _bucketSensitivityMap ||
			null == riskMeasureSensitivitySettings ||
			null == marginEstimationSettings)
		{
			return null;
		}

		double coreSBAVariance = 0.;
		double cumulativeRiskFactorSensitivityMarginCore = 0.;
		double cumulativeRiskFactorSensitivityMarginResidual = 0.;
		double cumulativeRiskFactorSensitivityMarginCorePositive = 0.;
		double cumulativeRiskFactorSensitivityMarginResidualPositive = 0.;

		java.util.Map<java.lang.String, org.drip.simm.margin.BucketAggregate> bucketAggregateMap = new
			java.util.TreeMap<java.lang.String, org.drip.simm.margin.BucketAggregate>();

		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketSensitivitySettingsMap = riskMeasureSensitivitySettings.bucketSettingsMap();

		org.drip.measure.stochastic.LabelCorrelation crossBucketCorrelation =
			riskMeasureSensitivitySettings.crossBucketCorrelation();

		for (java.util.Map.Entry<java.lang.String, org.drip.simm.product.BucketSensitivity>
			bucketSensitivityEntry : _bucketSensitivityMap.entrySet())
		{
			java.lang.String bucketIndex = bucketSensitivityEntry.getKey();

			org.drip.simm.product.BucketSensitivity bucketSensitivity = bucketSensitivityEntry.getValue();

			org.drip.simm.margin.BucketAggregate bucketAggregate = bucketSensitivity.aggregate
				(bucketSensitivitySettingsMap.get (bucketIndex));

			if (null == bucketAggregate)
			{
				return null;
			}

			double bucketCumulativeRiskFactorSensitivityMargin =
				bucketAggregate.cumulativeSensitivityMargin();

			if (!"-1".equalsIgnoreCase (bucketIndex))
			{
				cumulativeRiskFactorSensitivityMarginCore = cumulativeRiskFactorSensitivityMarginCore +
					bucketCumulativeRiskFactorSensitivityMargin;

				cumulativeRiskFactorSensitivityMarginCorePositive =
					cumulativeRiskFactorSensitivityMarginCorePositive +
					java.lang.Math.max (
						bucketCumulativeRiskFactorSensitivityMargin,
						0.
					);
			}
			else
			{
				cumulativeRiskFactorSensitivityMarginResidual = cumulativeRiskFactorSensitivityMarginResidual
					+ bucketCumulativeRiskFactorSensitivityMargin;

				cumulativeRiskFactorSensitivityMarginResidualPositive =
					cumulativeRiskFactorSensitivityMarginResidualPositive +
					java.lang.Math.max (
						bucketCumulativeRiskFactorSensitivityMargin,
						0.
					);
			}

			bucketAggregateMap.put (
				bucketIndex,
				bucketAggregate
			);
		}

		try
		{
			for (java.util.Map.Entry<java.lang.String, org.drip.simm.margin.BucketAggregate>
				bucketAggregateMapOuterEntry : bucketAggregateMap.entrySet())
			{
				java.lang.String outerKey = bucketAggregateMapOuterEntry.getKey();

				org.drip.simm.margin.BucketAggregate bucketAggregateOuter =
					bucketAggregateMapOuterEntry.getValue();

				double weightedSensitivityVarianceOuter = bucketAggregateOuter.sensitivityMarginVariance();

				double positionPrincipalComponentCovarianceOuter = PositionPrincipalComponentCovariance (
					bucketAggregateOuter,
					marginEstimationSettings
				);

				for (java.util.Map.Entry<java.lang.String, org.drip.simm.margin.BucketAggregate>
					bucketAggregateMapInnerEntry : bucketAggregateMap.entrySet())
				{
					java.lang.String innerKey = bucketAggregateMapInnerEntry.getKey();

					if (!"-1".equalsIgnoreCase (outerKey) && !"-1".equalsIgnoreCase (innerKey))
					{
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

							coreSBAVariance = coreSBAVariance + correlation * correlation *
								positionPrincipalComponentCovarianceOuter *
								PositionPrincipalComponentCovariance (
									bucketAggregateMapInnerEntry.getValue(),
									marginEstimationSettings
								);
						}
					}
				}
			}

			double tailVariate = org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.995);

			double lambda = tailVariate * tailVariate - 1.;

			double thetaCore = 0 == cumulativeRiskFactorSensitivityMarginCorePositive ? 0. :
				java.lang.Math.min (
					cumulativeRiskFactorSensitivityMarginCore /
						cumulativeRiskFactorSensitivityMarginCorePositive,
					0.
				);

			double coreSBAMargin = java.lang.Math.max (
				cumulativeRiskFactorSensitivityMarginCore +
					(lambda * (1. + thetaCore) - thetaCore) * java.lang.Math.sqrt (coreSBAVariance),
				0.
			);

			double thetaResidual = 0 == cumulativeRiskFactorSensitivityMarginResidualPositive ? 0. :
				java.lang.Math.min (
					cumulativeRiskFactorSensitivityMarginResidual /
						cumulativeRiskFactorSensitivityMarginResidualPositive,
					0.
				);

			double residualSBAMargin = !bucketAggregateMap.containsKey ("-1") ? 0. : java.lang.Math.max (
				cumulativeRiskFactorSensitivityMarginResidual +
					(lambda * (1. + thetaResidual) - thetaResidual) *
						java.lang.Math.sqrt (bucketAggregateMap.get ("-1").sensitivityMarginVariance()),
				0.
			);

			return new org.drip.simm.margin.RiskMeasureAggregate (
				bucketAggregateMap,
				coreSBAMargin * coreSBAMargin,
				residualSBAMargin * residualSBAMargin
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
