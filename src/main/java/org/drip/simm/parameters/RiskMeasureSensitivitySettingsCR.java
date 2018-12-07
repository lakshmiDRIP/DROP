
package org.drip.simm.parameters;

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
 * <i>RiskMeasureSensitivitySettingsCR</i> holds the Settings that govern the Generation of the ISDA SIMM
 * Bucket Sensitivities across Individual CR Class Risk Measure Buckets. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters">Parameters</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RiskMeasureSensitivitySettingsCR
{
	private org.drip.measure.stochastic.LabelCorrelation _crossBucketCorrelation = null;
	private java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsCR>
		_bucketSensitivitySettingsMap = null;

	/**
	 * Generate SIMM 2.0 Credit Qualifying Delta Sensitivity Settings
	 * 
	 * @return The SIMM 2.0 Credit Qualifying Delta Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRQ_DELTA_20()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsCR>
			bucketSensitivitySettingsMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.simm.parameters.BucketSensitivitySettingsCR>();

		java.util.Set<java.lang.Integer> bucketIndexSet =
			org.drip.simm.credit.CRQSettingsContainer20.BucketSet();

		for (int bucketIndex : bucketIndexSet)
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndex,
				org.drip.simm.parameters.BucketSensitivitySettingsCR.ISDA_CRQ_DELTA_20 (bucketIndex)
			);
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				org.drip.simm.credit.CRQSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate SIMM 2.1 Credit Qualifying Delta Sensitivity Settings
	 * 
	 * @return The SIMM 2.1 Credit Qualifying Delta Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRQ_DELTA_21()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsCR>
			bucketSensitivitySettingsMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.simm.parameters.BucketSensitivitySettingsCR>();

		java.util.Set<java.lang.Integer> bucketIndexSet =
			org.drip.simm.credit.CRQSettingsContainer21.BucketSet();

		for (int bucketIndex : bucketIndexSet)
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndex,
				org.drip.simm.parameters.BucketSensitivitySettingsCR.ISDA_CRQ_DELTA_21 (bucketIndex)
			);
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				org.drip.simm.credit.CRQSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate SIMM 2.0 Credit Qualifying Vega Sensitivity Settings
	 * 
	 * @return The SIMM 2.0 Credit Qualifying Vega Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRQ_VEGA_20()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsCR>
			bucketSensitivitySettingsMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.simm.parameters.BucketSensitivitySettingsCR>();

		java.util.Set<java.lang.Integer> bucketIndexSet =
			org.drip.simm.credit.CRQSettingsContainer20.BucketSet();

		for (int bucketIndex : bucketIndexSet)
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndex,
				org.drip.simm.parameters.BucketVegaSettingsCR.ISDA_CRQ_20 (bucketIndex)
			);
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				org.drip.simm.credit.CRQSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate SIMM 2.1 Credit Qualifying Vega Sensitivity Settings
	 * 
	 * @return The SIMM 2.1 Credit Qualifying Vega Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRQ_VEGA_21()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsCR>
			bucketSensitivitySettingsMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.simm.parameters.BucketSensitivitySettingsCR>();

		java.util.Set<java.lang.Integer> bucketIndexSet =
			org.drip.simm.credit.CRQSettingsContainer21.BucketSet();

		for (int bucketIndex : bucketIndexSet)
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndex,
				org.drip.simm.parameters.BucketVegaSettingsCR.ISDA_CRQ_21 (bucketIndex)
			);
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				org.drip.simm.credit.CRQSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate SIMM 2.0 Credit Qualifying Curvature Sensitivity Settings
	 * 
	 * @return The SIMM 2.0 Credit Qualifying Curvature Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRQ_CURVATURE_20()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsCR>
			bucketSensitivitySettingsMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.simm.parameters.BucketSensitivitySettingsCR>();

		java.util.Set<java.lang.Integer> bucketIndexSet =
			org.drip.simm.credit.CRQSettingsContainer20.BucketSet();

		for (int bucketIndex : bucketIndexSet)
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndex,
				org.drip.simm.parameters.BucketCurvatureSettingsCR.ISDA_CRQ_20 (bucketIndex)
			);
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				org.drip.simm.credit.CRQSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate SIMM 2.1 Credit Qualifying Curvature Sensitivity Settings
	 * 
	 * @return The SIMM 2.1 Credit Qualifying Curvature Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRQ_CURVATURE_21()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsCR>
			bucketSensitivitySettingsMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.simm.parameters.BucketSensitivitySettingsCR>();

		java.util.Set<java.lang.Integer> bucketIndexSet =
			org.drip.simm.credit.CRQSettingsContainer21.BucketSet();

		for (int bucketIndex : bucketIndexSet)
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndex,
				org.drip.simm.parameters.BucketCurvatureSettingsCR.ISDA_CRQ_21 (bucketIndex)
			);
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				org.drip.simm.credit.CRQSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate SIMM 2.0 Non-Credit Qualifying Delta Sensitivity Settings
	 * 
	 * @return The SIMM 2.0 Non-Credit Qualifying Delta Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRNQ_DELTA_20()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsCR>
			bucketSensitivitySettingsMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.simm.parameters.BucketSensitivitySettingsCR>();

		java.util.Set<java.lang.Integer> bucketIndexSet =
			org.drip.simm.credit.CRNQSettingsContainer20.BucketSet();

		int bucketCount = bucketIndexSet.size();

		java.util.List<java.lang.String> bucketLabelList = new java.util.ArrayList<java.lang.String>();

		double[][] crossBucketCorrelation = new double[bucketCount][bucketCount];

		for (int bucketIndexOuter = 0; bucketIndexOuter < bucketCount; ++bucketIndexOuter)
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndexOuter,
				org.drip.simm.parameters.BucketSensitivitySettingsCR.ISDA_CRNQ_DELTA_20 (bucketIndexOuter)
			);

			bucketLabelList.add ("" + bucketIndexOuter);

			for (int bucketIndexInner = 0; bucketIndexInner < bucketCount; ++bucketIndexInner)
			{
				crossBucketCorrelation[bucketIndexInner][bucketIndexOuter] =
					bucketIndexInner == bucketIndexOuter ? 1. :
					org.drip.simm.credit.CRNQBucketCorrelation20.NON_RESIDUAL_TO_NON_RESIDUAL;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					bucketLabelList,
					crossBucketCorrelation
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
	 * Generate SIMM 2.1 Non-Credit Qualifying Delta Sensitivity Settings
	 * 
	 * @return The SIMM 2.1 Non-Credit Qualifying Delta Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRNQ_DELTA_21()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsCR>
			bucketSensitivitySettingsMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.simm.parameters.BucketSensitivitySettingsCR>();

		java.util.Set<java.lang.Integer> bucketIndexSet =
			org.drip.simm.credit.CRNQSettingsContainer21.BucketSet();

		int bucketCount = bucketIndexSet.size();

		java.util.List<java.lang.String> bucketLabelList = new java.util.ArrayList<java.lang.String>();

		double[][] crossBucketCorrelation = new double[bucketCount][bucketCount];

		for (int bucketIndexOuter = 0; bucketIndexOuter < bucketCount; ++bucketIndexOuter)
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndexOuter,
				org.drip.simm.parameters.BucketSensitivitySettingsCR.ISDA_CRNQ_DELTA_21 (bucketIndexOuter)
			);

			bucketLabelList.add ("" + bucketIndexOuter);

			for (int bucketIndexInner = 0; bucketIndexInner < bucketCount; ++bucketIndexInner)
			{
				crossBucketCorrelation[bucketIndexInner][bucketIndexOuter] =
					bucketIndexInner == bucketIndexOuter ? 1. :
					org.drip.simm.credit.CRNQBucketCorrelation21.NON_RESIDUAL_TO_NON_RESIDUAL;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					bucketLabelList,
					crossBucketCorrelation
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
	 * Generate SIMM 2.0 Non-Credit Qualifying Vega Sensitivity Settings
	 * 
	 * @return The SIMM 2.0 Non-Credit Qualifying Vega Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRNQ_VEGA_20()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsCR>
			bucketSensitivitySettingsMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.simm.parameters.BucketSensitivitySettingsCR>();

		java.util.Set<java.lang.Integer> bucketIndexSet =
			org.drip.simm.credit.CRNQSettingsContainer20.BucketSet();

		int bucketCount = bucketIndexSet.size();

		java.util.List<java.lang.String> bucketLabelList = new java.util.ArrayList<java.lang.String>();

		double[][] crossBucketCorrelation = new double[bucketCount][bucketCount];

		for (int bucketIndexOuter = 0; bucketIndexOuter < bucketCount; ++bucketIndexOuter)
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndexOuter,
				org.drip.simm.parameters.BucketVegaSettingsCR.ISDA_CRNQ_20 (bucketIndexOuter)
			);

			bucketLabelList.add ("" + bucketIndexOuter);

			for (int bucketIndexInner = 0; bucketIndexInner < bucketCount; ++bucketIndexInner)
			{
				crossBucketCorrelation[bucketIndexInner][bucketIndexOuter] =
					bucketIndexInner == bucketIndexOuter ? 1. :
					org.drip.simm.credit.CRNQBucketCorrelation20.NON_RESIDUAL_TO_NON_RESIDUAL;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					bucketLabelList,
					crossBucketCorrelation
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
	 * Generate SIMM 2.1 Non-Credit Qualifying Vega Sensitivity Settings
	 * 
	 * @return The SIMM 2.1 Non-Credit Qualifying Vega Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRNQ_VEGA_21()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsCR>
			bucketSensitivitySettingsMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.simm.parameters.BucketSensitivitySettingsCR>();

		java.util.Set<java.lang.Integer> bucketIndexSet =
			org.drip.simm.credit.CRNQSettingsContainer21.BucketSet();

		int bucketCount = bucketIndexSet.size();

		java.util.List<java.lang.String> bucketLabelList = new java.util.ArrayList<java.lang.String>();

		double[][] crossBucketCorrelation = new double[bucketCount][bucketCount];

		for (int bucketIndexOuter = 0; bucketIndexOuter < bucketCount; ++bucketIndexOuter)
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndexOuter,
				org.drip.simm.parameters.BucketVegaSettingsCR.ISDA_CRNQ_21 (bucketIndexOuter)
			);

			bucketLabelList.add ("" + bucketIndexOuter);

			for (int bucketIndexInner = 0; bucketIndexInner < bucketCount; ++bucketIndexInner)
			{
				crossBucketCorrelation[bucketIndexInner][bucketIndexOuter] =
					bucketIndexInner == bucketIndexOuter ? 1. :
					org.drip.simm.credit.CRNQBucketCorrelation21.NON_RESIDUAL_TO_NON_RESIDUAL;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					bucketLabelList,
					crossBucketCorrelation
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
	 * Generate SIMM 2.0 Non-Credit Qualifying Curvature Sensitivity Settings
	 * 
	 * @return The SIMM 2.0 Non-Credit Qualifying Curvature Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRNQ_CURVATURE_20()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsCR>
			bucketSensitivitySettingsMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.simm.parameters.BucketSensitivitySettingsCR>();

		java.util.Set<java.lang.Integer> bucketIndexSet =
			org.drip.simm.credit.CRNQSettingsContainer20.BucketSet();

		int bucketCount = bucketIndexSet.size();

		java.util.List<java.lang.String> bucketLabelList = new java.util.ArrayList<java.lang.String>();

		double[][] crossBucketCorrelation = new double[bucketCount][bucketCount];

		for (int bucketIndexOuter = 0; bucketIndexOuter < bucketCount; ++bucketIndexOuter)
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndexOuter,
				org.drip.simm.parameters.BucketCurvatureSettingsCR.ISDA_CRNQ_20 (bucketIndexOuter)
			);

			bucketLabelList.add ("" + bucketIndexOuter);

			for (int bucketIndexInner = 0; bucketIndexInner < bucketCount; ++bucketIndexInner)
			{
				crossBucketCorrelation[bucketIndexInner][bucketIndexOuter] =
					bucketIndexInner == bucketIndexOuter ? 1. :
					org.drip.simm.credit.CRNQBucketCorrelation20.NON_RESIDUAL_TO_NON_RESIDUAL;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					bucketLabelList,
					crossBucketCorrelation
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
	 * Generate SIMM 2.1 Non-Credit Qualifying Curvature Sensitivity Settings
	 * 
	 * @return The SIMM 2.1 Non-Credit Qualifying Curvature Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRNQ_CURVATURE_21()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsCR>
			bucketSensitivitySettingsMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.simm.parameters.BucketSensitivitySettingsCR>();

		java.util.Set<java.lang.Integer> bucketIndexSet =
			org.drip.simm.credit.CRNQSettingsContainer21.BucketSet();

		int bucketCount = bucketIndexSet.size();

		java.util.List<java.lang.String> bucketLabelList = new java.util.ArrayList<java.lang.String>();

		double[][] crossBucketCorrelation = new double[bucketCount][bucketCount];

		for (int bucketIndexOuter = 0; bucketIndexOuter < bucketCount; ++bucketIndexOuter)
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndexOuter,
				org.drip.simm.parameters.BucketCurvatureSettingsCR.ISDA_CRNQ_21 (bucketIndexOuter)
			);

			bucketLabelList.add ("" + bucketIndexOuter);

			for (int bucketIndexInner = 0; bucketIndexInner < bucketCount; ++bucketIndexInner)
			{
				crossBucketCorrelation[bucketIndexInner][bucketIndexOuter] =
					bucketIndexInner == bucketIndexOuter ? 1. :
					org.drip.simm.credit.CRNQBucketCorrelation21.NON_RESIDUAL_TO_NON_RESIDUAL;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					bucketLabelList,
					crossBucketCorrelation
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
	 * RiskMeasureSensitivitySettingsCR Constructor
	 * 
	 * @param bucketSensitivitySettingsMap Credit Bucket Sensitivity Settings Map
	 * @param crossBucketCorrelation Cross Bucket Correlation
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskMeasureSensitivitySettingsCR (
		final java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsCR>
			bucketSensitivitySettingsMap,
		final org.drip.measure.stochastic.LabelCorrelation crossBucketCorrelation)
		throws java.lang.Exception
	{
		if (null == (_bucketSensitivitySettingsMap = bucketSensitivitySettingsMap) ||
				0 == _bucketSensitivitySettingsMap.size() ||
			null == (_crossBucketCorrelation = crossBucketCorrelation))
		{
			throw new java.lang.Exception ("RiskMeasureSensitivitySettingsCR Constructor => Invalid Inputs");
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
	 * Retrieve the Credit Bucket Sensitivity Settings Map
	 * 
	 * @return The Credit Bucket Sensitivity Settings Map
	 */

	public java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsCR>
		bucketSensitivitySettingsMap()
	{
		return _bucketSensitivitySettingsMap;
	}
}
