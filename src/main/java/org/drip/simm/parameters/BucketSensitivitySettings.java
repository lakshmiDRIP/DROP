
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
 * <i>BucketSensitivitySettings</i> holds the Settings that govern the Generation of the ISDA SIMM Single
 * Bucket Sensitivities. The References are:
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

public class BucketSensitivitySettings extends org.drip.simm.parameters.LiquiditySettings
{
	private double _riskWeight = java.lang.Double.NaN;
	private double _memberCorrelation = java.lang.Double.NaN;

	/**
	 * Construct the BucketSensitivitySettings 2.0 Instance for the specified Bucket Index
	 * 
	 * @param bucketIndex The Bucket Index
	 * 
	 * @return The BucketSensitivitySettings 2.0 Instance
	 */

	public static BucketSensitivitySettings ISDA_EQ_20 (
		final int bucketIndex)
	{
		org.drip.simm.equity.EQBucket equityBucket =
			org.drip.simm.equity.EQSettingsContainer20.BucketMap().get (bucketIndex);

		if (null == equityBucket)
		{
			return null;
		}

		try
		{
			return new BucketSensitivitySettings (
				equityBucket.deltaRiskWeight(),
				org.drip.simm.equity.EQRiskThresholdContainer20.DeltaVegaThresholdMap().get
					(bucketIndex).delta(),
				equityBucket.memberCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the BucketSensitivitySettings 2.1 Instance for the specified Bucket Index
	 * 
	 * @param bucketIndex The Bucket Index
	 * 
	 * @return The BucketSensitivitySettings 2.1 Instance
	 */

	public static BucketSensitivitySettings ISDA_EQ_21 (
		final int bucketIndex)
	{
		org.drip.simm.equity.EQBucket equityBucket =
			org.drip.simm.equity.EQSettingsContainer21.BucketMap().get (bucketIndex);

		if (null == equityBucket)
		{
			return null;
		}

		try
		{
			return new BucketSensitivitySettings (
				equityBucket.deltaRiskWeight(),
				org.drip.simm.equity.EQRiskThresholdContainer20.DeltaVegaThresholdMap().get
					(bucketIndex).delta(),
				equityBucket.memberCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the ISDA 2.0 Standard Commodity Bucket Sensitivity Settings for the specified Index
	 * 
	 * @param bucketIndex The Bucket Index
	 * 
	 * @return The ISDA 2.0 Standard Commodity Bucket Sensitivity Settings for the specified Index
	 */

	public static BucketSensitivitySettings ISDA_CT_20 (
		final int bucketIndex)
	{
		org.drip.simm.commodity.CTBucket commodityBucket =
			org.drip.simm.commodity.CTSettingsContainer20.BucketMap().get (bucketIndex);

		if (null == commodityBucket)
		{
			return null;
		}

		try
		{
			return new BucketSensitivitySettings (
				commodityBucket.deltaRiskWeight(),
				org.drip.simm.commodity.CTRiskThresholdContainer20.DeltaVegaThresholdMap().get
					(bucketIndex).delta(),
				commodityBucket.memberCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the ISDA 2.1 Standard Commodity Bucket Sensitivity Settings for the specified Index
	 * 
	 * @param bucketIndex The Bucket Index
	 * 
	 * @return The ISDA 2.1 Standard Commodity Bucket Sensitivity Settings for the specified Index
	 */

	public static BucketSensitivitySettings ISDA_CT_21 (
		final int bucketIndex)
	{
		org.drip.simm.commodity.CTBucket commodityBucket =
			org.drip.simm.commodity.CTSettingsContainer21.BucketMap().get (bucketIndex);

		if (null == commodityBucket)
		{
			return null;
		}

		try
		{
			return new BucketSensitivitySettings (
				commodityBucket.deltaRiskWeight(),
				org.drip.simm.commodity.CTRiskThresholdContainer21.DeltaVegaThresholdMap().get
					(bucketIndex).delta(),
				commodityBucket.memberCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard ISDA 2.0 Instance of FX Delta Settings
	 * 
	 * @param categoryIndex The Category Index
	 * 
	 * @return The Standard ISDA 2.0 Instance of FX Delta Settings
	 */

	public static BucketSensitivitySettings ISDA_FX_20 (
		final int categoryIndex)
	{
		java.util.Map<java.lang.Integer, java.lang.Double> fxConcentrationCategoryDeltaMap =
			org.drip.simm.fx.FXRiskThresholdContainer20.CategoryDeltaMap();

		if (!fxConcentrationCategoryDeltaMap.containsKey(categoryIndex))
		{
			return null;
		}

		try
		{
			return new org.drip.simm.parameters.BucketSensitivitySettings (
				org.drip.simm.fx.FXSystemics20.DELTA_RISK_WEIGHT,
				fxConcentrationCategoryDeltaMap.get (categoryIndex),
				org.drip.simm.fx.FXSystemics20.CORRELATION
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard ISDA 2.1 Instance of FX Delta Settings
	 * 
	 * @param categoryIndex The Category Index
	 * 
	 * @return The Standard ISDA 2.1 Instance of FX Delta Settings
	 */

	public static BucketSensitivitySettings ISDA_FX_21 (
		final int categoryIndex)
	{
		java.util.Map<java.lang.Integer, java.lang.Double> fxConcentrationCategoryDeltaMap =
			org.drip.simm.fx.FXRiskThresholdContainer21.CategoryDeltaMap();

		if (!fxConcentrationCategoryDeltaMap.containsKey(categoryIndex))
		{
			return null;
		}

		try
		{
			return new org.drip.simm.parameters.BucketSensitivitySettings (
				org.drip.simm.fx.FXSystemics21.DELTA_RISK_WEIGHT,
				fxConcentrationCategoryDeltaMap.get (categoryIndex),
				org.drip.simm.fx.FXSystemics21.CORRELATION
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BucketSensitivitySettings Constructor
	 * 
	 * @param riskWeight The Risk Factor Weight
	 * @param concentrationFactor The Concentration Factor
	 * @param memberCorrelation The Bucket Member Correlation
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketSensitivitySettings (
		final double riskWeight,
		final double concentrationFactor,
		final double memberCorrelation)
		throws java.lang.Exception
	{
		super (concentrationFactor);

		if (!org.drip.quant.common.NumberUtil.IsValid (_riskWeight = riskWeight) ||
			!org.drip.quant.common.NumberUtil.IsValid (_memberCorrelation = memberCorrelation) ||
				1. < _memberCorrelation || -1. > _memberCorrelation)
		{
			throw new java.lang.Exception ("BucketSensitivitySettings Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Bucket Risk Factor Weight
	 * 
	 * @return The Bucket Risk Factor Weight
	 */

	public double riskWeight()
	{
		return _riskWeight;
	}

	/**
	 * Retrieve the Correlation between the Basket Members
	 * 
	 * @return The Correlation between the Basket Members
	 */

	public double memberCorrelation()
	{
		return _memberCorrelation;
	}
}
