
package org.drip.simm.parameters;

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
 * RiskMeasureSensitivitySettings holds the Settings that govern the Generation of the ISDA SIMM Bucket
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
	private java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
		_bucketSettingsMap = null;

	/**
	 * Construct an ISDA 2.0 Equity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.0 Equity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_DELTA_20()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketDeltaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.equity.EQSettingsContainer20.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketDeltaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketSensitivitySettings.ISDA_EQ_20 (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				org.drip.simm.equity.EQSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 Equity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.1 Equity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_DELTA_21()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketDeltaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.equity.EQSettingsContainer21.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketDeltaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketSensitivitySettings.ISDA_EQ_21 (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				org.drip.simm.equity.EQSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 Equity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.0 Equity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_VEGA_20()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketVegaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.equity.EQSettingsContainer20.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketVegaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketVegaSettings.ISDA_EQ_20 (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				org.drip.simm.equity.EQSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 Equity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.1 Equity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_VEGA_21()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketVegaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.equity.EQSettingsContainer21.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketVegaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketVegaSettings.ISDA_EQ_21 (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				org.drip.simm.equity.EQSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 Equity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.0 Equity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_CURVATURE_20 (
		final int vegaDurationDays)
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketCurvatureSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.equity.EQSettingsContainer20.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketCurvatureSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketCurvatureSettings.ISDA_EQ_20 (
						bucketIndex,
						vegaDurationDays
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				org.drip.simm.equity.EQSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 Equity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.1 Equity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_CURVATURE_21 (
		final int vegaDurationDays)
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketCurvatureSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.equity.EQSettingsContainer21.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketCurvatureSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketCurvatureSettings.ISDA_EQ_21 (
						bucketIndex,
						vegaDurationDays
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				org.drip.simm.equity.EQSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 Commodity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.0 Commodity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_DELTA_20()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketDeltaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.commodity.CTSettingsContainer20.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketDeltaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketSensitivitySettings.ISDA_CT_20 (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				org.drip.simm.commodity.CTSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 Commodity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.1 Commodity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_DELTA_21()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketDeltaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.commodity.CTSettingsContainer20.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketDeltaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketSensitivitySettings.ISDA_CT_21 (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				org.drip.simm.commodity.CTSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 Commodity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.0 Commodity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_VEGA_20()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketVegaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.Integer, org.drip.simm.commodity.CTBucket> bucketMap =
			org.drip.simm.commodity.CTSettingsContainer20.BucketMap();

		java.util.Set<java.lang.Integer> bucketKeySet = bucketMap.keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketVegaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketVegaSettings.ISDA_CT_20 (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				org.drip.simm.commodity.CTSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 Commodity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.1 Commodity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_VEGA_21()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketVegaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.Integer, org.drip.simm.commodity.CTBucket> bucketMap =
			org.drip.simm.commodity.CTSettingsContainer21.BucketMap();

		java.util.Set<java.lang.Integer> bucketKeySet = bucketMap.keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketVegaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketVegaSettings.ISDA_CT_21 (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				org.drip.simm.commodity.CTSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 Commodity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.0 Commodity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_CURVATURE_20 (
		final int vegaDurationDays)
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketCurvatureSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.commodity.CTSettingsContainer20.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketCurvatureSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketCurvatureSettings.ISDA_CT_20 (
						bucketIndex,
						vegaDurationDays
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				org.drip.simm.commodity.CTSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 Commodity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.1 Commodity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_CURVATURE_21 (
		final int vegaDurationDays)
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketCurvatureSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.commodity.CTSettingsContainer21.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketCurvatureSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketCurvatureSettings.ISDA_CT_21 (
						bucketIndex,
						vegaDurationDays
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				org.drip.simm.commodity.CTSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 FX DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.0 FX DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_DELTA_20()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketDeltaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.Integer, java.lang.Double> fxConcentrationCategoryDeltaMap =
			org.drip.simm.fx.FXRiskThresholdContainer20.CategoryDeltaMap();

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
					org.drip.simm.parameters.BucketSensitivitySettings.ISDA_FX_20 (deltaCategoryIndex)
				);

				for (int categoryIndexInner : fxConcentrationCategoryDeltaKey)
				{
					crossBucketCorrelationMatrix[deltaCategoryIndex - 1][categoryIndexInner - 1] =
						deltaCategoryIndex == categoryIndexInner ? 1. :
						org.drip.simm.fx.FXSystemics20.CORRELATION;
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
	 * Construct an ISDA 2.1 FX DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.1 FX DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_DELTA_21()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketDeltaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.Integer, java.lang.Double> fxConcentrationCategoryDeltaMap =
			org.drip.simm.fx.FXRiskThresholdContainer21.CategoryDeltaMap();

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
					org.drip.simm.parameters.BucketSensitivitySettings.ISDA_FX_21 (deltaCategoryIndex)
				);

				for (int categoryIndexInner : fxConcentrationCategoryDeltaKey)
				{
					crossBucketCorrelationMatrix[deltaCategoryIndex - 1][categoryIndexInner - 1] =
						deltaCategoryIndex == categoryIndexInner ? 1. :
						org.drip.simm.fx.FXSystemics21.CORRELATION;
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
	 * Construct an ISDA 2.0 FX VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.0 FX VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_VEGA_20()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketVegaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.String, java.lang.Double> fxConcentrationCategoryVegaMap =
			org.drip.simm.fx.FXRiskThresholdContainer20.CategoryVegaMap();

		java.util.Set<java.lang.String> fxConcentrationCategoryVegaKey =
			fxConcentrationCategoryVegaMap.keySet();

		java.util.List<java.lang.String> vegaCategoryList = new java.util.ArrayList<java.lang.String>();

		int fxConcentrationCategoryVegaCount = fxConcentrationCategoryVegaKey.size();

		int vegaCategoryIndexOuter = 0;
		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryVegaCount][fxConcentrationCategoryVegaCount];

		try
		{
			for (java.lang.String vegaCategoryOuter : fxConcentrationCategoryVegaKey)
			{
				vegaCategoryList.add (vegaCategoryOuter);

				bucketVegaSettingsMap.put (
					vegaCategoryOuter,
					org.drip.simm.parameters.BucketVegaSettings.ISDA_FX_20 (vegaCategoryOuter)
				);

				for (int vegaCategoryIndexInner = 0;
					vegaCategoryIndexInner < fxConcentrationCategoryVegaCount;
					++vegaCategoryIndexInner)
				{
					crossBucketCorrelationMatrix[vegaCategoryIndexOuter][vegaCategoryIndexInner] =
						vegaCategoryIndexOuter == vegaCategoryIndexInner ? 1. :
						org.drip.simm.fx.FXSystemics20.CORRELATION;
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
	 * Construct an ISDA 2.1 FX VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.1 FX VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_VEGA_21()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketVegaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.String, java.lang.Double> fxConcentrationCategoryVegaMap =
			org.drip.simm.fx.FXRiskThresholdContainer21.CategoryVegaMap();

		java.util.Set<java.lang.String> fxConcentrationCategoryVegaKey =
			fxConcentrationCategoryVegaMap.keySet();

		java.util.List<java.lang.String> vegaCategoryList = new java.util.ArrayList<java.lang.String>();

		int fxConcentrationCategoryVegaCount = fxConcentrationCategoryVegaKey.size();

		int vegaCategoryIndexOuter = 0;
		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryVegaCount][fxConcentrationCategoryVegaCount];

		try
		{
			for (java.lang.String vegaCategoryOuter : fxConcentrationCategoryVegaKey)
			{
				vegaCategoryList.add (vegaCategoryOuter);

				bucketVegaSettingsMap.put (
					vegaCategoryOuter,
					org.drip.simm.parameters.BucketVegaSettings.ISDA_FX_21 (vegaCategoryOuter)
				);

				for (int vegaCategoryIndexInner = 0;
					vegaCategoryIndexInner < fxConcentrationCategoryVegaCount;
					++vegaCategoryIndexInner)
				{
					crossBucketCorrelationMatrix[vegaCategoryIndexOuter][vegaCategoryIndexInner] =
						vegaCategoryIndexOuter == vegaCategoryIndexInner ? 1. :
						org.drip.simm.fx.FXSystemics21.CORRELATION;
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
	 * Construct an ISDA 2.0 FX Curvature Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.0 FX Curvature Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_CURVATURE_20 (
		final int vegaDurationDays)
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketCurvatureSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.String, java.lang.Double> fxConcentrationCategoryCurvatureMap =
			org.drip.simm.fx.FXRiskThresholdContainer20.CategoryVegaMap();

		java.util.Set<java.lang.String> fxConcentrationCategoryCurvatureKey =
			fxConcentrationCategoryCurvatureMap.keySet();

		java.util.List<java.lang.String> curvatureCategoryList = new java.util.ArrayList<java.lang.String>();

		int fxConcentrationCategoryCurvatureCount = fxConcentrationCategoryCurvatureKey.size();

		int curvatureCategoryIndexOuter = 0;
		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryCurvatureCount][fxConcentrationCategoryCurvatureCount];

		try
		{
			for (java.lang.String curvatureCategoryOuter : fxConcentrationCategoryCurvatureKey)
			{
				curvatureCategoryList.add (curvatureCategoryOuter);

				bucketCurvatureSettingsMap.put (
					curvatureCategoryOuter,
					org.drip.simm.parameters.BucketCurvatureSettings.ISDA_FX_20 (
						curvatureCategoryOuter,
						vegaDurationDays
					)
				);

				for (int curvatureCategoryIndexInner = 0;
					curvatureCategoryIndexInner < fxConcentrationCategoryCurvatureCount;
					++curvatureCategoryIndexInner)
				{
					crossBucketCorrelationMatrix[curvatureCategoryIndexOuter][curvatureCategoryIndexInner] =
						curvatureCategoryIndexOuter == curvatureCategoryIndexInner ? 1. :
						org.drip.simm.fx.FXSystemics20.CORRELATION;
				}

				++curvatureCategoryIndexOuter;
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					curvatureCategoryList,
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
	 * Construct an ISDA 2.1 FX Curvature Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.1 FX Curvature Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_CURVATURE_21 (
		final int vegaDurationDays)
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketCurvatureSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.String, java.lang.Double> fxConcentrationCategoryCurvatureMap =
			org.drip.simm.fx.FXRiskThresholdContainer21.CategoryVegaMap();

		java.util.Set<java.lang.String> fxConcentrationCategoryCurvatureKey =
			fxConcentrationCategoryCurvatureMap.keySet();

		java.util.List<java.lang.String> curvatureCategoryList = new java.util.ArrayList<java.lang.String>();

		int fxConcentrationCategoryCurvatureCount = fxConcentrationCategoryCurvatureKey.size();

		int curvatureCategoryIndexOuter = 0;
		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryCurvatureCount][fxConcentrationCategoryCurvatureCount];

		try
		{
			for (java.lang.String curvatureCategoryOuter : fxConcentrationCategoryCurvatureKey)
			{
				curvatureCategoryList.add (curvatureCategoryOuter);

				bucketCurvatureSettingsMap.put (
					curvatureCategoryOuter,
					org.drip.simm.parameters.BucketCurvatureSettings.ISDA_FX_21 (
						curvatureCategoryOuter,
						vegaDurationDays
					)
				);

				for (int curvatureCategoryIndexInner = 0;
					curvatureCategoryIndexInner < fxConcentrationCategoryCurvatureCount;
					++curvatureCategoryIndexInner)
				{
					crossBucketCorrelationMatrix[curvatureCategoryIndexOuter][curvatureCategoryIndexInner] =
						curvatureCategoryIndexOuter == curvatureCategoryIndexInner ? 1. :
						org.drip.simm.fx.FXSystemics21.CORRELATION;
				}

				++curvatureCategoryIndexOuter;
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					curvatureCategoryList,
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
		final java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
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

	public java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
		bucketSettingsMap()
	{
		return _bucketSettingsMap;
	}
}
