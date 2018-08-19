
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
 * FlatSensitivitySettings holds the Flat Risk Weights and Concentration Thresholds for each Risk Factor. The
 *  References are:
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

public class FlatSensitivitySettings extends org.drip.simm20.parameters.LiquiditySettings
{
	private double _riskWeight = java.lang.Double.NaN;

	/**
	 * Construct the ISDA SIMM 2.0 Standard Equity FlatSensitivitySettings Instance
	 * 
	 * @param bucketNumber The Equity Bucket Number
	 * 
	 * @return The ISDA SIMM 2.0 Standard Equity FlatSensitivitySettings Instance
	 */

	public static final FlatSensitivitySettings EQ (
		final int bucketNumber)
	{
		org.drip.simm20.equity.EQBucket equityBucket = org.drip.simm20.equity.EQSettingsContainer.Bucket
			(bucketNumber);

		org.drip.simm20.common.DeltaVegaThreshold deltaVegaThreshold =
			org.drip.simm20.equity.EQRiskThresholdContainer.Threshold (bucketNumber);

		try
		{
			return null == equityBucket || null == deltaVegaThreshold ? null :
				new FlatSensitivitySettings (
					equityBucket.riskWeight(),
					deltaVegaThreshold.delta()
				);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the ISDA SIMM 2.0 Standard FX FlatSensitivitySettings Instance
	 * 
	 * @param categoryNumber The FX Category Number
	 * 
	 * @return The ISDA SIMM 2.0 Standard FX FlatSensitivitySettings Instance
	 */

	public static final FlatSensitivitySettings FX (
		final int categoryNumber)
	{
		try
		{
			return !org.drip.simm20.fx.FXRiskThresholdContainer.ContainsCategory (categoryNumber) ? null :
				new FlatSensitivitySettings (
					org.drip.simm20.fx.FXSystemics.RISK_WEIGHT,
					org.drip.simm20.fx.FXRiskThresholdContainer.CategoryDeltaThreshold (categoryNumber)
				);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the ISDA SIMM 2.0 Standard Commodity FlatSensitivitySettings Instance
	 * 
	 * @param bucketNumber The Commodity Bucket Number
	 * 
	 * @return The ISDA SIMM 2.0 Standard Commodity FlatSensitivitySettings Instance
	 */

	public static final FlatSensitivitySettings CT (
		final int bucketNumber)
	{
		org.drip.simm20.commodity.CTBucket commodityBucket =
			org.drip.simm20.commodity.CTSettingsContainer.Bucket (bucketNumber);

		org.drip.simm20.common.DeltaVegaThreshold deltaVegaThreshold =
			org.drip.simm20.commodity.CTRiskThresholdContainer.Threshold (bucketNumber);

		try
		{
			return null == commodityBucket || null == deltaVegaThreshold ? null :
				new FlatSensitivitySettings (
					commodityBucket.riskWeight(),
					deltaVegaThreshold.delta()
				);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * FlatSensitivitySettings Constructor
	 * 
	 * @param riskWeight The Risk Weight
	 * @param concentrationThreshold The Concentration Threshold
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs ar4e Invalid
	 */

	public FlatSensitivitySettings (
		final double riskWeight,
		final double concentrationThreshold)
		throws java.lang.Exception
	{
		super (concentrationThreshold);

		if (!org.drip.quant.common.NumberUtil.IsValid (_riskWeight = riskWeight))
		{
			throw new java.lang.Exception ("FlatSensitivitySettings Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Flat Risk Weight
	 * 
	 * @return The Flat Risk Weight
	 */

	public double riskWeight()
	{
		return _riskWeight;
	}
}
