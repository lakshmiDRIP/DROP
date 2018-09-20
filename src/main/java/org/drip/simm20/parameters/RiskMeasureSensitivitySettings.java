
package org.drip.simm20.parameters;

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
 * RiskMeasureSensitivitySettings holds the Settings that govern the Generation of the ISDA SIMM 2.0 Bucket
 *  Sensitivities across Individual Risk Measure Buckets. The References are:
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

public class RiskMeasureSensitivitySettings
{
	private org.drip.measure.stochastic.LabelCorrelation _crossBucketCorrelation = null;
	private java.util.Map<java.lang.String, org.drip.simm20.parameters.BucketSensitivitySettings>
		_bucketSettingsMap = null;

	/**
	 * Construct an ISDA Equity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA Equity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_DELTA()
	{
		java.util.Map<java.lang.String, org.drip.simm20.parameters.BucketSensitivitySettings>
			bucketDeltaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm20.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm20.equity.EQSettingsContainer.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketDeltaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm20.parameters.BucketSensitivitySettings.ISDA_EQ (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				org.drip.simm20.equity.EQSettingsContainer.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA Equity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA Equity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_VEGA()
	{
		java.util.Map<java.lang.String, org.drip.simm20.parameters.BucketSensitivitySettings>
			bucketVegaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm20.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm20.equity.EQSettingsContainer.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketVegaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm20.parameters.BucketVegaSettings.ISDA_EQ (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				org.drip.simm20.equity.EQSettingsContainer.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA Commodity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA Commodity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_DELTA()
	{
		java.util.Map<java.lang.String, org.drip.simm20.parameters.BucketSensitivitySettings>
			bucketDeltaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm20.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm20.commodity.CTSettingsContainer.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketDeltaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm20.parameters.BucketSensitivitySettings.ISDA_CT (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				org.drip.simm20.commodity.CTSettingsContainer.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA Commodity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA Commodity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_VEGA()
	{
		java.util.Map<java.lang.String, org.drip.simm20.parameters.BucketSensitivitySettings>
			bucketVegaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm20.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.Integer, org.drip.simm20.commodity.CTBucket> bucketMap =
			org.drip.simm20.commodity.CTSettingsContainer.BucketMap();

		java.util.Set<java.lang.Integer> bucketKeySet = bucketMap.keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketVegaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm20.parameters.BucketVegaSettings.ISDA_CT (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				org.drip.simm20.commodity.CTSettingsContainer.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA FX DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA FX DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_DELTA()
	{
		java.util.Map<java.lang.String, org.drip.simm20.parameters.BucketSensitivitySettings>
			bucketDeltaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm20.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.Integer, java.lang.Double> fxConcentrationCategoryDeltaMap =
			org.drip.simm20.fx.FXRiskThresholdContainer.CategoryDeltaMap();

		java.util.Set<java.lang.Integer> fxConcentrationCategoryDeltaKey =
			fxConcentrationCategoryDeltaMap.keySet();

		java.util.List<java.lang.String> deltaCategoryList = new java.util.ArrayList<java.lang.String>();

		int fxConcentrationCategoryDeltaCount = fxConcentrationCategoryDeltaKey.size();

		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryDeltaCount][fxConcentrationCategoryDeltaCount];

		try
		{
			for (int deltaCategoryIndex : fxConcentrationCategoryDeltaKey)
			{
				deltaCategoryList.add ("" + deltaCategoryIndex);

				bucketDeltaSettingsMap.put (
					"" + deltaCategoryIndex,
					org.drip.simm20.parameters.BucketSensitivitySettings.ISDA_CT (deltaCategoryIndex)
				);

				for (int categoryIndexInner : fxConcentrationCategoryDeltaKey)
				{
					crossBucketCorrelationMatrix[deltaCategoryIndex - 1][categoryIndexInner - 1] =
						deltaCategoryIndex == categoryIndexInner ? 1. :
						org.drip.simm20.fx.FXSystemics.CORRELATION;
				}
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					deltaCategoryList,
					crossBucketCorrelationMatrix
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA FX VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA FX VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_VEGA()
	{
		java.util.Map<java.lang.String, org.drip.simm20.parameters.BucketSensitivitySettings>
			bucketVegaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm20.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.String, java.lang.Double> fxConcentrationCategoryVegaMap =
			org.drip.simm20.fx.FXRiskThresholdContainer.CategoryVegaMap();

		java.util.Set<java.lang.String> fxConcentrationCategoryVegaKey =
			fxConcentrationCategoryVegaMap.keySet();

		java.util.List<java.lang.String> vegaCategoryList = new java.util.ArrayList<java.lang.String>();

		int fxConcentrationCategoryVegaCount = fxConcentrationCategoryVegaKey.size();

		int vegaCategoryIndexOuter = 0;
		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryVegaCount][fxConcentrationCategoryVegaCount];

		try
		{
			double vegaScaler = java.lang.Math.sqrt (365. / 14.) /
				org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99);

			for (java.lang.String vegaCategoryOuter : fxConcentrationCategoryVegaKey)
			{
				vegaCategoryList.add (vegaCategoryOuter);

				bucketVegaSettingsMap.put (
					vegaCategoryOuter,
					new org.drip.simm20.parameters.BucketVegaSettings (
						org.drip.simm20.fx.FXSystemics.VEGA_RISK_WEIGHT *
							org.drip.simm20.fx.FXSystemics.DELTA_RISK_WEIGHT,
						fxConcentrationCategoryVegaMap.get (vegaCategoryOuter),
						org.drip.simm20.fx.FXSystemics.CORRELATION,
						vegaScaler,
						org.drip.simm20.fx.FXSystemics.HISTORICAL_VOLATILITY_RATIO
					)
				);

				for (int vegaCategoryIndexInner = 0;
					vegaCategoryIndexInner < fxConcentrationCategoryVegaCount;
					++vegaCategoryIndexInner)
				{
					crossBucketCorrelationMatrix[vegaCategoryIndexOuter][vegaCategoryIndexInner] =
						vegaCategoryIndexOuter == vegaCategoryIndexInner ? 1. :
						org.drip.simm20.fx.FXSystemics.CORRELATION;
				}

				++vegaCategoryIndexOuter;
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					vegaCategoryList,
					crossBucketCorrelationMatrix
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * RiskMeasureSensitivitySettings Constructor
	 * 
	 * @param bucketSettingsMap The Bucket Sensitivity Settings Map
	 * @param crossBucketCorrelation The Cross Bucket Correlation
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskMeasureSensitivitySettings (
		final java.util.Map<java.lang.String, org.drip.simm20.parameters.BucketSensitivitySettings>
			bucketSettingsMap,
		final org.drip.measure.stochastic.LabelCorrelation crossBucketCorrelation)
		throws java.lang.Exception
	{
		if (null == (_bucketSettingsMap = bucketSettingsMap) || 0 == _bucketSettingsMap.size() ||
			null == (_crossBucketCorrelation = crossBucketCorrelation))
		{
			throw new java.lang.Exception ("RiskMeasureSensitivitySettings Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Cross Bucket Correlation
	 * 
	 * @return The Cross Bucket Correlation
	 */

	public org.drip.measure.stochastic.LabelCorrelation crossBucketCorrelation()
	{
		return _crossBucketCorrelation;
	}

	/**
	 * Retrieve the Bucket Sensitivity Settings Map
	 * 
	 * @return The Bucket Sensitivity Settings Map
	 */

	public java.util.Map<java.lang.String, org.drip.simm20.parameters.BucketSensitivitySettings>
		bucketSettingsMap()
	{
		return _bucketSettingsMap;
	}
}
