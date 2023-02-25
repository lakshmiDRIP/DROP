
package org.drip.simm.parameters;

import java.util.Map;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.numerical.common.NumberUtil;
import org.drip.simm.credit.CRBucket;
import org.drip.simm.credit.CRNQBucketCorrelation20;
import org.drip.simm.credit.CRNQBucketCorrelation21;
import org.drip.simm.credit.CRNQBucketCorrelation24;
import org.drip.simm.credit.CRNQSettingsContainer20;
import org.drip.simm.credit.CRNQSettingsContainer21;
import org.drip.simm.credit.CRNQSettingsContainer24;
import org.drip.simm.credit.CRQBucketCorrelation20;
import org.drip.simm.credit.CRQBucketCorrelation21;
import org.drip.simm.credit.CRQBucketCorrelation24;
import org.drip.simm.credit.CRQSettingsContainer20;
import org.drip.simm.credit.CRQSettingsContainer21;
import org.drip.simm.credit.CRQSettingsContainer24;
import org.drip.simm.credit.CRThresholdContainer20;
import org.drip.simm.credit.CRThresholdContainer21;
import org.drip.simm.credit.CRThresholdContainer24;

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
 *  		International Swaps and Derivatives Association (2021): SIMM v2.4 Methodology
 *  			https://www.isda.org/a/CeggE/ISDA-SIMM-v2.4-PUBLIC.pdf
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/README.md">ISDA SIMM Risk Factor Parameters</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BucketSensitivitySettingsCR
	extends LiquiditySettings
{
	private Map<String, Double> _tenorRiskWeight = null;
	private double _extraFamilyCrossTenorCorrelation = Double.NaN;
	private double _intraFamilyCrossTenorCorrelation = Double.NaN;

	protected static final Map<String, Double> TenorRiskWeightMap (
		final double riskWeight)
	{
		Map<String, Double> tenorRiskWeight = new CaseInsensitiveHashMap<Double>();

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
		CRBucket creditBucket = CRQSettingsContainer20.Bucket (
			bucketNumber
		);

		if (null == creditBucket)
		{
			return null;
		}

		try
		{
			return -1 == bucketNumber ? new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (
					creditBucket.riskWeight()
				),
				CRQBucketCorrelation20.SAME_ISSUER_SENIORITY_RESIDUAL,
				CRQBucketCorrelation20.DIFFERENT_ISSUER_SENIORITY_RESIDUAL,
				CRThresholdContainer20.QualifyingThreshold (
					bucketNumber
				).delta()
			) : new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (
					creditBucket.riskWeight()
				),
				CRQBucketCorrelation20.SAME_ISSUER_SENIORITY_NON_RESIDUAL,
				CRQBucketCorrelation20.DIFFERENT_ISSUER_SENIORITY_NON_RESIDUAL,
				CRThresholdContainer20.QualifyingThreshold (
					bucketNumber
				).delta()
			);
		}
		catch (Exception e)
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
		CRBucket creditBucket = CRNQSettingsContainer20.Bucket (
			bucketNumber
		);

		if (null == creditBucket)
		{
			return null;
		}

		try
		{
			return -1 == bucketNumber ? new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (
					creditBucket.riskWeight()
				),
				CRNQBucketCorrelation20.GT_80PC_OVERLAP_RESIDUAL,
				CRNQBucketCorrelation20.LT_80PC_OVERLAP_RESIDUAL,
				CRThresholdContainer20.NonQualifyingThreshold (
					bucketNumber
				).delta()
			) : new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (
					creditBucket.riskWeight()
				),
				CRNQBucketCorrelation20.GT_80PC_OVERLAP_NON_RESIDUAL,
				CRNQBucketCorrelation20.LT_80PC_OVERLAP_NON_RESIDUAL,
				CRThresholdContainer20.NonQualifyingThreshold (
					bucketNumber
				).delta()
			);
		}
		catch (Exception e)
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
		CRBucket creditBucket = CRQSettingsContainer21.Bucket (
			bucketNumber
		);

		if (null == creditBucket)
		{
			return null;
		}

		try
		{
			return -1 == bucketNumber ? new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (
					creditBucket.riskWeight()
				),
				CRQBucketCorrelation21.SAME_ISSUER_SENIORITY_RESIDUAL,
				CRQBucketCorrelation21.DIFFERENT_ISSUER_SENIORITY_RESIDUAL,
				CRThresholdContainer21.QualifyingThreshold (
					bucketNumber
				).delta()
			) : new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (
					creditBucket.riskWeight()
				),
				CRQBucketCorrelation21.SAME_ISSUER_SENIORITY_NON_RESIDUAL,
				CRQBucketCorrelation21.DIFFERENT_ISSUER_SENIORITY_NON_RESIDUAL,
				CRThresholdContainer21.QualifyingThreshold (
					bucketNumber
				).delta()
			);
		}
		catch (Exception e)
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
		CRBucket creditBucket = CRNQSettingsContainer21.Bucket (
			bucketNumber
		);

		if (null == creditBucket)
		{
			return null;
		}

		try
		{
			return -1 == bucketNumber ? new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (
					creditBucket.riskWeight()
				),
				CRNQBucketCorrelation21.GT_80PC_OVERLAP_RESIDUAL,
				CRNQBucketCorrelation21.LT_80PC_OVERLAP_RESIDUAL,
				CRThresholdContainer21.NonQualifyingThreshold (
					bucketNumber
				).delta()
			) : new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (
					creditBucket.riskWeight()
				),
				CRNQBucketCorrelation21.GT_80PC_OVERLAP_NON_RESIDUAL,
				CRNQBucketCorrelation21.LT_80PC_OVERLAP_NON_RESIDUAL,
				CRThresholdContainer21.NonQualifyingThreshold (
					bucketNumber
				).delta()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the ISDA 2.4 Credit Qualifying Bucket Delta Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.4 Credit Qualifying Bucket Delta Settings
	 */

	public static BucketSensitivitySettingsCR ISDA_CRQ_DELTA_24 (
		final int bucketNumber)
	{
		CRBucket creditBucket = CRQSettingsContainer24.Bucket (
			bucketNumber
		);

		if (null == creditBucket)
		{
			return null;
		}

		try
		{
			return -1 == bucketNumber ? new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (
					creditBucket.riskWeight()
				),
				CRQBucketCorrelation24.SAME_ISSUER_SENIORITY_RESIDUAL,
				CRQBucketCorrelation24.DIFFERENT_ISSUER_SENIORITY_RESIDUAL,
				CRThresholdContainer24.QualifyingThreshold (
					bucketNumber
				).delta()
			) : new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (
					creditBucket.riskWeight()
				),
				CRQBucketCorrelation24.SAME_ISSUER_SENIORITY_NON_RESIDUAL,
				CRQBucketCorrelation24.DIFFERENT_ISSUER_SENIORITY_NON_RESIDUAL,
				CRThresholdContainer24.QualifyingThreshold (
					bucketNumber
				).delta()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the ISDA 2.4 Credit Non-Qualifying Bucket Delta Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.4 Credit Non-Qualifying Bucket Delta Settings
	 */

	public static BucketSensitivitySettingsCR ISDA_CRNQ_DELTA_24 (
		final int bucketNumber)
	{
		CRBucket creditBucket = CRNQSettingsContainer24.Bucket (
			bucketNumber
		);

		if (null == creditBucket)
		{
			return null;
		}

		try
		{
			return -1 == bucketNumber ? new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (
					creditBucket.riskWeight()
				),
				CRNQBucketCorrelation24.GT_80PC_OVERLAP_RESIDUAL,
				CRNQBucketCorrelation24.LT_80PC_OVERLAP_RESIDUAL,
				CRThresholdContainer24.NonQualifyingThreshold (
					bucketNumber
				).delta()
			) : new BucketSensitivitySettingsCR (
				TenorRiskWeightMap (
					creditBucket.riskWeight()
				),
				CRNQBucketCorrelation24.GT_80PC_OVERLAP_NON_RESIDUAL,
				CRNQBucketCorrelation24.LT_80PC_OVERLAP_NON_RESIDUAL,
				CRThresholdContainer24.NonQualifyingThreshold (
					bucketNumber
				).delta()
			);
		}
		catch (Exception e)
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
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BucketSensitivitySettingsCR (
		final Map<String, Double> tenorRiskWeight,
		final double intraFamilyCrossTenorCorrelation,
		final double extraFamilyCrossTenorCorrelation,
		final double concentrationThreshold)
		throws Exception
	{
		super (concentrationThreshold);

		if (null == (_tenorRiskWeight = tenorRiskWeight) || 0 == _tenorRiskWeight.size() ||
			!NumberUtil.IsValid (
				_intraFamilyCrossTenorCorrelation = intraFamilyCrossTenorCorrelation
			) || 1. <= _intraFamilyCrossTenorCorrelation || -1. >= _intraFamilyCrossTenorCorrelation ||
			!NumberUtil.IsValid (
				_extraFamilyCrossTenorCorrelation = extraFamilyCrossTenorCorrelation
			) || 1. <= _extraFamilyCrossTenorCorrelation || -1. >= _extraFamilyCrossTenorCorrelation
		)
		{
			throw new Exception (
				"BucketSensitivitySettingsCR Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Tenor Risk Weight Map
	 * 
	 * @return The Tenor Risk Weight Map
	 */

	public Map<String, Double> tenorRiskWeight()
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
