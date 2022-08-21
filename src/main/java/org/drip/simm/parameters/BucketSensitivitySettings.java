
package org.drip.simm.parameters;

import java.util.Map;

import org.drip.numerical.common.NumberUtil;
import org.drip.simm.commodity.CTBucket;
import org.drip.simm.commodity.CTRiskThresholdContainer20;
import org.drip.simm.commodity.CTRiskThresholdContainer21;
import org.drip.simm.commodity.CTRiskThresholdContainer24;
import org.drip.simm.commodity.CTSettingsContainer20;
import org.drip.simm.commodity.CTSettingsContainer21;
import org.drip.simm.commodity.CTSettingsContainer24;
import org.drip.simm.equity.EQBucket;
import org.drip.simm.equity.EQRiskThresholdContainer20;
import org.drip.simm.equity.EQRiskThresholdContainer21;
import org.drip.simm.equity.EQRiskThresholdContainer24;
import org.drip.simm.equity.EQSettingsContainer20;
import org.drip.simm.equity.EQSettingsContainer21;
import org.drip.simm.equity.EQSettingsContainer24;
import org.drip.simm.fx.FXRiskThresholdContainer20;
import org.drip.simm.fx.FXRiskThresholdContainer21;
import org.drip.simm.fx.FXRiskThresholdContainer24;
import org.drip.simm.fx.FXSystemics20;
import org.drip.simm.fx.FXSystemics21;
import org.drip.simm.fx.FXSystemics24;

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

public class BucketSensitivitySettings
	extends LiquiditySettings
{
	private double _riskWeight = Double.NaN;
	private double _memberCorrelation = Double.NaN;

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
		EQBucket equityBucket = EQSettingsContainer20.BucketMap().get (
			bucketIndex
		);

		try
		{
			return null == equityBucket ? null : new BucketSensitivitySettings (
				equityBucket.deltaRiskWeight(),
				EQRiskThresholdContainer20.DeltaVegaThresholdMap().get (
					bucketIndex
				).delta(),
				equityBucket.memberCorrelation()
			);
		}
		catch (Exception e)
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
		EQBucket equityBucket = EQSettingsContainer21.BucketMap().get (
			bucketIndex
		);

		try
		{
			return null == equityBucket ? null : new BucketSensitivitySettings (
				equityBucket.deltaRiskWeight(),
				EQRiskThresholdContainer21.DeltaVegaThresholdMap().get (
					bucketIndex
				).delta(),
				equityBucket.memberCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the BucketSensitivitySettings 2.4 Instance for the specified Bucket Index
	 * 
	 * @param bucketIndex The Bucket Index
	 * 
	 * @return The BucketSensitivitySettings 2.4 Instance
	 */

	public static BucketSensitivitySettings ISDA_EQ_24 (
		final int bucketIndex)
	{
		EQBucket equityBucket = EQSettingsContainer24.BucketMap().get (
			bucketIndex
		);

		try
		{
			return null == equityBucket ? null : new BucketSensitivitySettings (
				equityBucket.deltaRiskWeight(),
				EQRiskThresholdContainer24.DeltaVegaThresholdMap().get (
					bucketIndex
				).delta(),
				equityBucket.memberCorrelation()
			);
		}
		catch (Exception e)
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
		CTBucket commodityBucket = CTSettingsContainer20.BucketMap().get (
			bucketIndex
		);

		try
		{
			return null == commodityBucket ? null : new BucketSensitivitySettings (
				commodityBucket.deltaRiskWeight(),
				CTRiskThresholdContainer20.DeltaVegaThresholdMap().get (
					bucketIndex
				).delta(),
				commodityBucket.memberCorrelation()
			);
		}
		catch (Exception e)
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
		CTBucket commodityBucket = CTSettingsContainer21.BucketMap().get (
			bucketIndex
		);

		try
		{
			return null == commodityBucket ? null : new BucketSensitivitySettings (
				commodityBucket.deltaRiskWeight(),
				CTRiskThresholdContainer21.DeltaVegaThresholdMap().get (
					bucketIndex
				).delta(),
				commodityBucket.memberCorrelation()
			);
		}
		catch (Exception e)
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

	public static BucketSensitivitySettings ISDA_CT_24 (
		final int bucketIndex)
	{
		CTBucket commodityBucket = CTSettingsContainer24.BucketMap().get (
			bucketIndex
		);

		try
		{
			return null == commodityBucket ? null : new BucketSensitivitySettings (
				commodityBucket.deltaRiskWeight(),
				CTRiskThresholdContainer24.DeltaVegaThresholdMap().get (
					bucketIndex
				).delta(),
				commodityBucket.memberCorrelation()
			);
		}
		catch (Exception e)
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
		Map<Integer, Double> fxConcentrationCategoryDeltaMap = FXRiskThresholdContainer20.CategoryDeltaMap();

		try
		{
			return fxConcentrationCategoryDeltaMap.containsKey (
				categoryIndex
			) ? new BucketSensitivitySettings (
				FXSystemics20.DELTA_RISK_WEIGHT,
				fxConcentrationCategoryDeltaMap.get (
					categoryIndex
				),
				FXSystemics20.CORRELATION
			) : null;
		}
		catch (Exception e)
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
		Map<Integer, Double> fxConcentrationCategoryDeltaMap = FXRiskThresholdContainer21.CategoryDeltaMap();

		if (!fxConcentrationCategoryDeltaMap.containsKey(categoryIndex))
		{
			return null;
		}

		try
		{
			return fxConcentrationCategoryDeltaMap.containsKey (
				categoryIndex
			) ? new BucketSensitivitySettings (
				FXSystemics21.DELTA_RISK_WEIGHT,
				fxConcentrationCategoryDeltaMap.get (
					categoryIndex
				),
				FXSystemics21.CORRELATION
			) : null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard ISDA 2.4 Instance of FX Delta Settings
	 * 
	 * @param categoryIndex The Category Index
	 * 
	 * @return The Standard ISDA 2.4 Instance of FX Delta Settings
	 */

	public static BucketSensitivitySettings ISDA_FX_24 (
		final int categoryIndex)
	{
		Map<Integer, Double> fxConcentrationCategoryDeltaMap = FXRiskThresholdContainer24.CategoryDeltaMap();

		if (!fxConcentrationCategoryDeltaMap.containsKey(categoryIndex))
		{
			return null;
		}

		try
		{
			return fxConcentrationCategoryDeltaMap.containsKey (
				categoryIndex
			) ? new BucketSensitivitySettings (
				FXSystemics24.REGULAR_REGULAR_DELTA_RISK_WEIGHT,
				fxConcentrationCategoryDeltaMap.get (
					categoryIndex
				),
				FXSystemics24.VOLATILITY_CURVATURE_CORRELATION
			) : null;
		}
		catch (Exception e)
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
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BucketSensitivitySettings (
		final double riskWeight,
		final double concentrationFactor,
		final double memberCorrelation)
		throws Exception
	{
		super (
			concentrationFactor
		);

		if (!NumberUtil.IsValid (
				_riskWeight = riskWeight
			) ||
			!NumberUtil.IsValid (
				_memberCorrelation = memberCorrelation
			) || 1. < _memberCorrelation || -1. > _memberCorrelation)
		{
			throw new Exception (
				"BucketSensitivitySettings Constructor => Invalid Inputs"
			);
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
