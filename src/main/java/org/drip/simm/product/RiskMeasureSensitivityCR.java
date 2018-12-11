
package org.drip.simm.product;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>RiskMeasureSensitivityCR</i> holds the Risk Class Bucket Sensitivities for the CR Risk Measure. The
 * References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm">SIMM</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product">Product</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RiskMeasureSensitivityCR
{
	private java.util.Map<java.lang.String, org.drip.simm.product.BucketSensitivityCR> _bucketSensitivityMap
		= null;

	private static final double PositionPrincipalComponentCovariance (
		final org.drip.simm.margin.BucketAggregateCR bucketAggregate,
		final org.drip.simm.foundation.MarginEstimationSettings marginEstimationSettings)
		throws java.lang.Exception
	{
		java.lang.String positionPrincipalComponentScheme =
			marginEstimationSettings.positionPrincipalComponentScheme();

		if (positionPrincipalComponentScheme.equalsIgnoreCase
			(org.drip.simm.foundation.MarginEstimationSettings.POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_FRTB))
		{
			return bucketAggregate.positionPrincipalComponentCovarianceFRTB();
		}

		if (positionPrincipalComponentScheme.equalsIgnoreCase
			(org.drip.simm.foundation.MarginEstimationSettings.POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_ISDA))
		{
			return bucketAggregate.positionPrincipalComponentCovarianceISDA();
		}

		throw new java.lang.Exception
			("RiskMeasureSensitivityCR::PositionPrincipalComponentCovariance => Invalid Inputs");
	}

	/**
	 * RiskMeasureSensitivityCR Constructor
	 * 
	 * @param bucketSensitivityMap The CR Class Bucket Sensitivity Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskMeasureSensitivityCR (
		final java.util.Map<java.lang.String, org.drip.simm.product.BucketSensitivityCR>
			bucketSensitivityMap)
		throws java.lang.Exception
	{
		if (null == (_bucketSensitivityMap = bucketSensitivityMap) || 0 == _bucketSensitivityMap.size())
		{
			throw new java.lang.Exception ("RiskMeasureSensitivityCR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Credit Bucket Sensitivity Map
	 * 
	 * @return The Credit Bucket Sensitivity Map
	 */

	public java.util.Map<java.lang.String, org.drip.simm.product.BucketSensitivityCR> bucketSensitivityMap()
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

	public org.drip.simm.margin.RiskMeasureAggregateCR linearAggregate (
		final org.drip.simm.parameters.RiskMeasureSensitivitySettingsCR riskMeasureSensitivitySettings,
		final org.drip.simm.foundation.MarginEstimationSettings marginEstimationSettings)
	{
		if (null == riskMeasureSensitivitySettings || null == marginEstimationSettings)
		{
			return null;
		}

		double coreSBAVariance = 0.;

		java.util.Map<java.lang.String, org.drip.simm.margin.BucketAggregateCR> bucketAggregateMap = new
			java.util.TreeMap<java.lang.String, org.drip.simm.margin.BucketAggregateCR>();

		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsCR>
			bucketSensitivitySettingsMap = riskMeasureSensitivitySettings.bucketSensitivitySettingsMap();

		org.drip.measure.stochastic.LabelCorrelation crossBucketCorrelation =
			riskMeasureSensitivitySettings.crossBucketCorrelation();

		for (java.util.Map.Entry<java.lang.String, org.drip.simm.product.BucketSensitivityCR>
			bucketSensitivityMapEntry : _bucketSensitivityMap.entrySet())
		{
			java.lang.String bucketIndex = bucketSensitivityMapEntry.getKey();

			org.drip.simm.product.BucketSensitivityCR bucketSensitivity =
				bucketSensitivityMapEntry.getValue();

			org.drip.simm.margin.BucketAggregateCR bucketAggregate = bucketSensitivity.aggregate
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
			for (java.util.Map.Entry<java.lang.String, org.drip.simm.margin.BucketAggregateCR>
				bucketAggregateMapOuterEntry : bucketAggregateMap.entrySet())
			{
				java.lang.String outerKey = bucketAggregateMapOuterEntry.getKey();

				org.drip.simm.margin.BucketAggregateCR bucketAggregateOuter =
					bucketAggregateMapOuterEntry.getValue();

				double weightedSensitivityVarianceOuter = bucketAggregateOuter.sensitivityMarginVariance();

				double positionPrincipalComponentCovarianceOuter = PositionPrincipalComponentCovariance (
					bucketAggregateOuter,
					marginEstimationSettings
				);

				for (java.util.Map.Entry<java.lang.String, org.drip.simm.margin.BucketAggregateCR>
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

			return new org.drip.simm.margin.RiskMeasureAggregateCR (
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

	public org.drip.simm.margin.RiskMeasureAggregateCR curvatureAggregate (
		final org.drip.simm.parameters.RiskMeasureSensitivitySettingsCR riskMeasureSensitivitySettings,
		final org.drip.simm.foundation.MarginEstimationSettings marginEstimationSettings)
	{
		if (null == riskMeasureSensitivitySettings || null == marginEstimationSettings)
		{
			return null;
		}

		double coreSBAVariance = 0.;
		double cumulativeRiskFactorSensitivityMarginCore = 0.;
		double cumulativeRiskFactorSensitivityMarginCorePositive = 0.;

		java.util.Map<java.lang.String, org.drip.simm.margin.BucketAggregateCR> bucketAggregateMap = new
			java.util.TreeMap<java.lang.String, org.drip.simm.margin.BucketAggregateCR>();

		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsCR>
			bucketSensitivitySettingsMap = riskMeasureSensitivitySettings.bucketSensitivitySettingsMap();

		org.drip.measure.stochastic.LabelCorrelation crossBucketCorrelation =
			riskMeasureSensitivitySettings.crossBucketCorrelation();

		org.drip.simm.foundation.CurvatureEstimator curvatureEstimator =
			marginEstimationSettings.curvatureEstimator();

		boolean isCorrelatorQuadratric = curvatureEstimator.isCorrelatorQuadratric();

		for (java.util.Map.Entry<java.lang.String, org.drip.simm.product.BucketSensitivityCR>
			bucketSensitivityMapEntry : _bucketSensitivityMap.entrySet())
		{
			java.lang.String bucketIndex = bucketSensitivityMapEntry.getKey();

			org.drip.simm.product.BucketSensitivityCR bucketSensitivity =
				bucketSensitivityMapEntry.getValue();

			org.drip.simm.margin.BucketAggregateCR bucketAggregate = bucketSensitivity.aggregate
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
			for (java.util.Map.Entry<java.lang.String, org.drip.simm.margin.BucketAggregateCR>
				bucketAggregateMapOuterEntry : bucketAggregateMap.entrySet())
			{
				java.lang.String outerKey = bucketAggregateMapOuterEntry.getKey();

				org.drip.simm.margin.BucketAggregateCR bucketAggregateOuter =
					bucketAggregateMapOuterEntry.getValue();

				double weightedSensitivityVarianceOuter = bucketAggregateOuter.sensitivityMarginVariance();

				double positionPrincipalComponentCovarianceOuter = PositionPrincipalComponentCovariance (
					bucketAggregateOuter,
					marginEstimationSettings
				);

				for (java.util.Map.Entry<java.lang.String, org.drip.simm.margin.BucketAggregateCR>
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

						org.drip.simm.margin.BucketAggregateCR bucketAggregateInner =
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

			return new org.drip.simm.margin.RiskMeasureAggregateCR (
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
