
package org.drip.simm.product;

import java.util.Map;
import java.util.TreeMap;

import org.drip.measure.identifier.LabelCorrelation;
import org.drip.simm.foundation.CurvatureEstimator;
import org.drip.simm.foundation.MarginEstimationSettings;
import org.drip.simm.margin.BucketAggregate;
import org.drip.simm.margin.RiskMeasureAggregate;
import org.drip.simm.parameters.BucketSensitivitySettings;
import org.drip.simm.parameters.RiskMeasureSensitivitySettings;

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
 * <i>RiskMeasureSensitivity</i> holds the Risk Class Bucket Sensitivities for a single Risk Measure. The
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product/README.md">ISDA SIMM Risk Factor Sensitivities</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RiskMeasureSensitivity
{
	private Map<String, BucketSensitivity> _bucketSensitivityMap = null;

	private static final double PositionPrincipalComponentCovariance (
		final BucketAggregate bucketAggregate,
		final MarginEstimationSettings marginEstimationSettings)
		throws Exception
	{
		String positionPrincipalComponentScheme =
			marginEstimationSettings.positionPrincipalComponentScheme();

		if (positionPrincipalComponentScheme.equalsIgnoreCase (
			MarginEstimationSettings.POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_FRTB
		))
		{
			return bucketAggregate.positionPrincipalComponentCovarianceFRTB();
		}

		if (positionPrincipalComponentScheme.equalsIgnoreCase (
			MarginEstimationSettings.POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_ISDA
		))
		{
			return bucketAggregate.positionPrincipalComponentCovarianceISDA();
		}

		throw new Exception (
			"RiskMeasureSensitivity::PositionPrincipalComponentCovariance => Invalid Inputs"
		);
	}

	/**
	 * RiskMeasureSensitivity Constructor
	 * 
	 * @param bucketSensitivityMap The Risk Class Bucket Sensitivity Map
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public RiskMeasureSensitivity (
		final Map<String, BucketSensitivity> bucketSensitivityMap)
		throws Exception
	{
		if (null == (_bucketSensitivityMap = bucketSensitivityMap) || 0 == _bucketSensitivityMap.size())
		{
			throw new Exception (
				"RiskMeasureSensitivity Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Risk Class Bucket Sensitivity Map
	 * 
	 * @return The Risk Class Bucket Sensitivity Map
	 */

	public Map<String, BucketSensitivity> bucketSensitivityMap()
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

	public RiskMeasureAggregate linearAggregate (
		final RiskMeasureSensitivitySettings riskMeasureSensitivitySettings,
		final MarginEstimationSettings marginEstimationSettings)
	{
		if (null == _bucketSensitivityMap ||
			null == riskMeasureSensitivitySettings ||
			null == marginEstimationSettings)
		{
			return null;
		}

		double coreSBAVariance = 0.;

		Map<String, BucketAggregate> bucketAggregateMap = new TreeMap<String, BucketAggregate>();

		Map<String, BucketSensitivitySettings> bucketSensitivitySettingsMap =
			riskMeasureSensitivitySettings.bucketSettingsMap();

		LabelCorrelation crossBucketCorrelation = riskMeasureSensitivitySettings.crossBucketCorrelation();

		for (Map.Entry<String, BucketSensitivity> bucketSensitivityEntry : _bucketSensitivityMap.entrySet())
		{
			String bucketIndex = bucketSensitivityEntry.getKey();

			BucketAggregate bucketAggregate = bucketSensitivityEntry.getValue().aggregate (
				bucketSensitivitySettingsMap.get (
					bucketIndex
				)
			);

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
			for (Map.Entry<String, BucketAggregate> bucketAggregateMapOuterEntry :
				bucketAggregateMap.entrySet()
			)
			{
				String outerKey = bucketAggregateMapOuterEntry.getKey();

				BucketAggregate bucketAggregateOuter = bucketAggregateMapOuterEntry.getValue();

				double weightedSensitivityVarianceOuter = bucketAggregateOuter.sensitivityMarginVariance();

				double positionPrincipalComponentCovarianceOuter = PositionPrincipalComponentCovariance (
					bucketAggregateOuter,
					marginEstimationSettings
				);

				for (Map.Entry<String, BucketAggregate> bucketAggregateMapInnerEntry :
					bucketAggregateMap.entrySet()
				)
				{
					String innerKey = bucketAggregateMapInnerEntry.getKey();

					if (!"-1".equalsIgnoreCase (
							outerKey
						) && !"-1".equalsIgnoreCase (
							innerKey
						)
					)
					{
						coreSBAVariance = coreSBAVariance + (
							outerKey.equalsIgnoreCase (
								innerKey
							) ? weightedSensitivityVarianceOuter : crossBucketCorrelation.entry (
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

			return new RiskMeasureAggregate (
				bucketAggregateMap,
				coreSBAVariance,
				bucketAggregateMap.containsKey (
					"-1"
				) ? bucketAggregateMap.get (
					"-1"
				).sensitivityMarginVariance() : 0.
			);
		}
		catch (Exception e)
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

	public RiskMeasureAggregate curvatureAggregate (
		final RiskMeasureSensitivitySettings riskMeasureSensitivitySettings,
		final MarginEstimationSettings marginEstimationSettings)
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

		Map<String, BucketAggregate> bucketAggregateMap = new TreeMap<String, BucketAggregate>();

		Map<String, BucketSensitivitySettings> bucketSensitivitySettingsMap =
			riskMeasureSensitivitySettings.bucketSettingsMap();

		LabelCorrelation crossBucketCorrelation = riskMeasureSensitivitySettings.crossBucketCorrelation();

		CurvatureEstimator curvatureEstimator = marginEstimationSettings.curvatureEstimator();

		boolean isCorrelatorQuadratric = curvatureEstimator.isCorrelatorQuadratric();

		for (Map.Entry<String, BucketSensitivity> bucketSensitivityEntry : _bucketSensitivityMap.entrySet())
		{
			String bucketIndex = bucketSensitivityEntry.getKey();

			BucketSensitivity bucketSensitivity = bucketSensitivityEntry.getValue();

			BucketAggregate bucketAggregate = bucketSensitivity.aggregate (
				bucketSensitivitySettingsMap.get (
					bucketIndex
				)
			);

			if (null == bucketAggregate)
			{
				return null;
			}

			double bucketCumulativeRiskFactorSensitivityMargin =
				bucketAggregate.cumulativeSensitivityMargin();

			if (!"-1".equalsIgnoreCase (
				bucketIndex
			))
			{
				cumulativeRiskFactorSensitivityMarginCore = cumulativeRiskFactorSensitivityMarginCore +
					bucketCumulativeRiskFactorSensitivityMargin;

				cumulativeRiskFactorSensitivityMarginCorePositive =
					cumulativeRiskFactorSensitivityMarginCorePositive +
					Math.max (
						bucketCumulativeRiskFactorSensitivityMargin,
						0.
					);
			}
			else
			{
				cumulativeRiskFactorSensitivityMarginResidual = cumulativeRiskFactorSensitivityMarginResidual
					+ bucketCumulativeRiskFactorSensitivityMargin;

				cumulativeRiskFactorSensitivityMarginResidualPositive =
					cumulativeRiskFactorSensitivityMarginResidualPositive + Math.max (
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
			for (Map.Entry<String, BucketAggregate> bucketAggregateMapOuterEntry :
				bucketAggregateMap.entrySet()
			)
			{
				String outerKey = bucketAggregateMapOuterEntry.getKey();

				BucketAggregate bucketAggregateOuter = bucketAggregateMapOuterEntry.getValue();

				double weightedSensitivityVarianceOuter = bucketAggregateOuter.sensitivityMarginVariance();

				double positionPrincipalComponentCovarianceOuter = PositionPrincipalComponentCovariance (
					bucketAggregateOuter,
					marginEstimationSettings
				);

				for (Map.Entry<String, BucketAggregate> bucketAggregateMapInnerEntry :
					bucketAggregateMap.entrySet()
				)
				{
					String innerKey = bucketAggregateMapInnerEntry.getKey();

					if (!"-1".equalsIgnoreCase (
							outerKey
						) && !"-1".equalsIgnoreCase (
							innerKey
						)
					)
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

							double curvatureCorrelation = isCorrelatorQuadratric
								? correlation * correlation : correlation;

							BucketAggregate bucketAggregateInner = bucketAggregateMapInnerEntry.getValue();

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
			}

			double coreSBAMargin = curvatureEstimator.margin (
				cumulativeRiskFactorSensitivityMarginCore,
				cumulativeRiskFactorSensitivityMarginCorePositive,
				coreSBAVariance
			);

			double residualSBAMargin = !bucketAggregateMap.containsKey (
				"-1"
			) ? 0. : curvatureEstimator.margin (
				cumulativeRiskFactorSensitivityMarginResidual,
				cumulativeRiskFactorSensitivityMarginResidualPositive,
				bucketAggregateMap.get (
					"-1"
				).sensitivityMarginVariance()
			);

			return new RiskMeasureAggregate (
				bucketAggregateMap,
				coreSBAMargin * coreSBAMargin,
				residualSBAMargin * residualSBAMargin
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
