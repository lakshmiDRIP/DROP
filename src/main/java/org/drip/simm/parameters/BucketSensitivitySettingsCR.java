
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
 * BucketSensitivitySettingsCR holds the Delta Risk Weights, Concentration Thresholds, and Cross-Tenor
 *  Correlations for each Credit Curve and its Tenor. The References are:
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

public class BucketSensitivitySettingsCR extends org.drip.simm.parameters.LiquiditySettings
{
	private double _sameIssuerSeniorityCorrelation = java.lang.Double.NaN;
	private double _differentIssuerSeniorityCorrelation = java.lang.Double.NaN;
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
	 * @param sameIssuerSeniorityCorrelation Same Issuer/Seniority Correlation
	 * @param differentIssuerSeniorityCorrelation Different Issuer/Seniority Correlation
	 * @param concentrationThreshold The Concentration Threshold
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketSensitivitySettingsCR (
		final java.util.Map<java.lang.String, java.lang.Double> tenorRiskWeight,
		final double sameIssuerSeniorityCorrelation,
		final double differentIssuerSeniorityCorrelation,
		final double concentrationThreshold)
		throws java.lang.Exception
	{
		super (concentrationThreshold);

		if (null == (_tenorRiskWeight = tenorRiskWeight) || 0 == _tenorRiskWeight.size() ||
			!org.drip.quant.common.NumberUtil.IsValid (_sameIssuerSeniorityCorrelation =
				sameIssuerSeniorityCorrelation) ||
				1. <= _sameIssuerSeniorityCorrelation || -1. >= _sameIssuerSeniorityCorrelation ||
			!org.drip.quant.common.NumberUtil.IsValid (_differentIssuerSeniorityCorrelation =
				differentIssuerSeniorityCorrelation) ||
				1. <= _differentIssuerSeniorityCorrelation || -1. >= _differentIssuerSeniorityCorrelation)
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
	 * Retrieve the Same Issuer/Seniority Correlation
	 * 
	 * @return The Same Issuer/Seniority Correlation
	 */

	public double sameIssuerSeniorityCorrelation()
	{
		return _sameIssuerSeniorityCorrelation;
	}

	/**
	 * Retrieve the Different Issuer/Seniority Correlation
	 * 
	 * @return The Different Issuer/Seniority Correlation
	 */

	public double differentIssuerSeniorityCorrelation()
	{
		return _differentIssuerSeniorityCorrelation;
	}
}
