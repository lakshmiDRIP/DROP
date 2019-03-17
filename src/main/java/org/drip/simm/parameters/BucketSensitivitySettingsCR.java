
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
 * <i>BucketSensitivitySettingsCR</i> holds the Delta Risk Weights, Concentration Thresholds, and Cross-Tenor
 * Correlations for each Credit Curve and its Tenor. The References are:
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

public class BucketSensitivitySettingsCR extends org.drip.simm.parameters.LiquiditySettings
{
	private double _extraFamilyCrossTenorCorrelation = java.lang.Double.NaN;
	private double _intraFamilyCrossTenorCorrelation = java.lang.Double.NaN;
	private java.util.Map<java.lang.String, java.lang.Double> _tenorRiskWeight = null;

	protected static final java.util.Map<java.lang.String, java.lang.Double> TenorRiskWeightMap (
		final double riskWeight)
	{
		java.util.Map<java.lang.String, java.lang.Double> tenorRiskWeight = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		tenorRiskWeight.put (
			"1Y",
			riskWeight
		);

		tenorRiskWeight.put (
			"2Y",
			riskWeight
		);

		tenorRiskWeight.put (
			"3Y",
			riskWeight
		);

		tenorRiskWeight.put (
			"5Y",
			riskWeight
		);

		tenorRiskWeight.put (
			"10Y",
			riskWeight
		);

		return tenorRiskWeight;
	}

	/**
	 * Retrieve the ISDA 2.0 Credit Qualifying Bucket Delta Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.0 Credit Qualifying Bucket Delta Settings
	 */

	public static BucketSensitivitySettingsCR ISDA_CRQ_DELTA_20 (
		final int bucketNumber)
	{
		org.drip.simm.credit.CRBucket creditBucket = org.drip.simm.credit.CRQSettingsContainer20.Bucket
			(bucketNumber);

		if (null == creditBucket)
		{
			return null;
		}

		try
		{
			return -1 == bucketNumber ? new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (creditBucket.riskWeight()),
				org.drip.simm.credit.CRQBucketCorrelation20.SAME_ISSUER_SENIORITY_RESIDUAL,
				org.drip.simm.credit.CRQBucketCorrelation20.DIFFERENT_ISSUER_SENIORITY_RESIDUAL,
				org.drip.simm.credit.CRThresholdContainer20.QualifyingThreshold (bucketNumber).delta()
			) : new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (creditBucket.riskWeight()),
				org.drip.simm.credit.CRQBucketCorrelation20.SAME_ISSUER_SENIORITY_NON_RESIDUAL,
				org.drip.simm.credit.CRQBucketCorrelation20.DIFFERENT_ISSUER_SENIORITY_NON_RESIDUAL,
				org.drip.simm.credit.CRThresholdContainer20.QualifyingThreshold (bucketNumber).delta()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the ISDA 2.0 Credit Non-Qualifying Bucket Delta Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.0 Credit Non-Qualifying Bucket Delta Settings
	 */

	public static BucketSensitivitySettingsCR ISDA_CRNQ_DELTA_20 (
		final int bucketNumber)
	{
		org.drip.simm.credit.CRBucket creditBucket = org.drip.simm.credit.CRNQSettingsContainer20.Bucket
			(bucketNumber);

		if (null == creditBucket)
		{
			return null;
		}

		try
		{
			return -1 == bucketNumber ? new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (creditBucket.riskWeight()),
				org.drip.simm.credit.CRNQBucketCorrelation20.GT_80PC_OVERLAP_RESIDUAL,
				org.drip.simm.credit.CRNQBucketCorrelation20.LT_80PC_OVERLAP_RESIDUAL,
				org.drip.simm.credit.CRThresholdContainer20.NonQualifyingThreshold (bucketNumber).delta()
			) : new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (creditBucket.riskWeight()),
				org.drip.simm.credit.CRNQBucketCorrelation20.GT_80PC_OVERLAP_NON_RESIDUAL,
				org.drip.simm.credit.CRNQBucketCorrelation20.LT_80PC_OVERLAP_NON_RESIDUAL,
				org.drip.simm.credit.CRThresholdContainer20.NonQualifyingThreshold (bucketNumber).delta()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the ISDA 2.1 Credit Qualifying Bucket Delta Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.1 Credit Qualifying Bucket Delta Settings
	 */

	public static BucketSensitivitySettingsCR ISDA_CRQ_DELTA_21 (
		final int bucketNumber)
	{
		org.drip.simm.credit.CRBucket creditBucket = org.drip.simm.credit.CRQSettingsContainer21.Bucket
			(bucketNumber);

		if (null == creditBucket)
		{
			return null;
		}

		try
		{
			return -1 == bucketNumber ? new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (creditBucket.riskWeight()),
				org.drip.simm.credit.CRQBucketCorrelation21.SAME_ISSUER_SENIORITY_RESIDUAL,
				org.drip.simm.credit.CRQBucketCorrelation21.DIFFERENT_ISSUER_SENIORITY_RESIDUAL,
				org.drip.simm.credit.CRThresholdContainer21.QualifyingThreshold (bucketNumber).delta()
			) : new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (creditBucket.riskWeight()),
				org.drip.simm.credit.CRQBucketCorrelation21.SAME_ISSUER_SENIORITY_NON_RESIDUAL,
				org.drip.simm.credit.CRQBucketCorrelation21.DIFFERENT_ISSUER_SENIORITY_NON_RESIDUAL,
				org.drip.simm.credit.CRThresholdContainer21.QualifyingThreshold (bucketNumber).delta()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the ISDA 2.1 Credit Non-Qualifying Bucket Delta Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.1 Credit Non-Qualifying Bucket Delta Settings
	 */

	public static BucketSensitivitySettingsCR ISDA_CRNQ_DELTA_21 (
		final int bucketNumber)
	{
		org.drip.simm.credit.CRBucket creditBucket = org.drip.simm.credit.CRNQSettingsContainer21.Bucket
			(bucketNumber);

		if (null == creditBucket)
		{
			return null;
		}

		try
		{
			return -1 == bucketNumber ? new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (creditBucket.riskWeight()),
				org.drip.simm.credit.CRNQBucketCorrelation21.GT_80PC_OVERLAP_RESIDUAL,
				org.drip.simm.credit.CRNQBucketCorrelation21.LT_80PC_OVERLAP_RESIDUAL,
				org.drip.simm.credit.CRThresholdContainer21.NonQualifyingThreshold (bucketNumber).delta()
			) : new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (creditBucket.riskWeight()),
				org.drip.simm.credit.CRNQBucketCorrelation21.GT_80PC_OVERLAP_NON_RESIDUAL,
				org.drip.simm.credit.CRNQBucketCorrelation21.LT_80PC_OVERLAP_NON_RESIDUAL,
				org.drip.simm.credit.CRThresholdContainer21.NonQualifyingThreshold (bucketNumber).delta()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BucketSensitivitySettingsCR Constructor
	 * 
	 * @param tenorRiskWeight The Tenor Risk Weight Map
	 * @param intraFamilyCrossTenorCorrelation Intra-Family Cross Tenor Correlation 
	 * @param extraFamilyCrossTenorCorrelation Extra-Family Cross Tenor Correlation
	 * @param concentrationThreshold The Concentration Threshold
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketSensitivitySettingsCR (
		final java.util.Map<java.lang.String, java.lang.Double> tenorRiskWeight,
		final double intraFamilyCrossTenorCorrelation,
		final double extraFamilyCrossTenorCorrelation,
		final double concentrationThreshold)
		throws java.lang.Exception
	{
		super (concentrationThreshold);

		if (null == (_tenorRiskWeight = tenorRiskWeight) || 0 == _tenorRiskWeight.size() ||
			!org.drip.numerical.common.NumberUtil.IsValid (_intraFamilyCrossTenorCorrelation =
				intraFamilyCrossTenorCorrelation) ||
				1. <= _intraFamilyCrossTenorCorrelation || -1. >= _intraFamilyCrossTenorCorrelation ||
			!org.drip.numerical.common.NumberUtil.IsValid (_extraFamilyCrossTenorCorrelation =
				extraFamilyCrossTenorCorrelation) ||
				1. <= _extraFamilyCrossTenorCorrelation || -1. >= _extraFamilyCrossTenorCorrelation)
		{
			throw new java.lang.Exception ("BucketSensitivitySettingsCR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Tenor Risk Weight Map
	 * 
	 * @return The Tenor Risk Weight Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> tenorRiskWeight()
	{
		return _tenorRiskWeight;
	}

	/**
	 * Retrieve the Intra-Family Cross Tenor Correlation
	 * 
	 * @return The Intra-Family Cross Tenor Correlation
	 */

	public double intraFamilyCrossTenorCorrelation()
	{
		return _intraFamilyCrossTenorCorrelation;
	}

	/**
	 * Retrieve the Extra-Family Cross Tenor Correlation
	 * 
	 * @return The Extra-Family Cross Tenor Correlation
	 */

	public double extraFamilyCrossTenorCorrelation()
	{
		return _extraFamilyCrossTenorCorrelation;
	}
}
