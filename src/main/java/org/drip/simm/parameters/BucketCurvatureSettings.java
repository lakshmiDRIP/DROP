
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
 * BucketCurvatureSettings holds the ISDA SIMM Curvature Settings for Interest Rates, Qualifying and
 * 	Non-qualifying Credit, Equity, Commodity, and Foreign Exchange. The References are:
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

public class BucketCurvatureSettings extends org.drip.simm.parameters.BucketVegaSettings
{
	private double _tenorScalingFactor = java.lang.Double.NaN;

	/**
	 * Construct the ISDA Standard BucketCurvatureSettings
	 * 
	 * @param riskWeight The Vega Risk Weight
	 * @param memberCorrelation The Member Correlation
	 * @param impliedVolatility The Implied Volatility
	 * @param vegaDurationDays The Bucket Vega Duration in Days
	 * 
	 * @return The ISDA Standard BucketCurvatureSettings
	 */

	public static final BucketCurvatureSettings ISDA (
		final double riskWeight,
		final double memberCorrelation,
		final double impliedVolatility,
		final int vegaDurationDays)
	{
		try
		{
			return new BucketCurvatureSettings (
				riskWeight,
				memberCorrelation,
				impliedVolatility,
				org.drip.function.r1tor1.ISDABucketCurvatureTenorScaler.Standard().evaluate
					(vegaDurationDays)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard ISDA 2.0 EQ Bucket Curvature Settings
	 * 
	 * @param bucketIndex The Bucket Index
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return The Standard ISDA 2.0 EQ Bucket Curvature Settings
	 */

	public static BucketCurvatureSettings ISDA_EQ_20 (
		final int bucketIndex,
		final int vegaDurationDays)
	{
		org.drip.simm.equity.EQBucket equityBucket =
			org.drip.simm.equity.EQSettingsContainer20.BucketMap().get (bucketIndex);

		try
		{
			return null == equityBucket ? null : BucketCurvatureSettings.ISDA (
				equityBucket.vegaRiskWeight() * equityBucket.deltaRiskWeight(),
				equityBucket.memberCorrelation(),
				java.lang.Math.sqrt (365. / 14.) /
					org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
				vegaDurationDays
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard ISDA 2.0 CT Bucket Curvature Settings
	 * 
	 * @param bucketIndex The Bucket Index
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return The Standard ISDA 2.0 CT Bucket Curvature Settings
	 */

	public static BucketCurvatureSettings ISDA_CT_20 (
		final int bucketIndex,
		final int vegaDurationDays)
	{
		org.drip.simm.commodity.CTBucket commodityBucket =
			org.drip.simm.commodity.CTSettingsContainer20.BucketMap().get (bucketIndex);

		try
		{
			return null == commodityBucket ? null : BucketCurvatureSettings.ISDA (
				org.drip.simm.commodity.CTSystemics20.VEGA_RISK_WEIGHT * commodityBucket.deltaRiskWeight(),
				commodityBucket.memberCorrelation(),
				java.lang.Math.sqrt (365. / 14.) /
					org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
				vegaDurationDays
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard ISDA 2.1 CT Bucket Curvature Settings
	 * 
	 * @param bucketIndex The Bucket Index
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return The Standard ISDA 2.1 CT Bucket Curvature Settings
	 */

	public static BucketCurvatureSettings ISDA_CT_21 (
		final int bucketIndex,
		final int vegaDurationDays)
	{
		org.drip.simm.commodity.CTBucket commodityBucket =
			org.drip.simm.commodity.CTSettingsContainer21.BucketMap().get (bucketIndex);

		try
		{
			return null == commodityBucket ? null : BucketCurvatureSettings.ISDA (
				org.drip.simm.commodity.CTSystemics21.VEGA_RISK_WEIGHT * commodityBucket.deltaRiskWeight(),
				commodityBucket.memberCorrelation(),
				java.lang.Math.sqrt (365. / 14.) /
					org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
				vegaDurationDays
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard ISDA 2.0 FX Bucket Curvature Settings
	 * 
	 * @param vegaCategory The Vega Category
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return The Standard ISDA 2.0 FX Bucket Curvature Settings
	 */

	public static BucketCurvatureSettings ISDA_FX_20 (
		final java.lang.String vegaCategory,
		final int vegaDurationDays)
	{
		java.util.Map<java.lang.String, java.lang.Double> fxConcentrationCategoryVegaMap =
			org.drip.simm.fx.FXRiskThresholdContainer20.CategoryVegaMap();

		try {
			return !fxConcentrationCategoryVegaMap.containsKey (vegaCategory) ? null :
				BucketCurvatureSettings.ISDA (
					org.drip.simm.fx.FXSystemics20.VEGA_RISK_WEIGHT *
						org.drip.simm.fx.FXSystemics20.DELTA_RISK_WEIGHT,
					org.drip.simm.fx.FXSystemics20.CORRELATION,
					java.lang.Math.sqrt (365. / 14.) /
						org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
					vegaDurationDays
				);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard ISDA 2.1 FX Bucket Curvature Settings
	 * 
	 * @param vegaCategory The Vega Category
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return The Standard ISDA 2.1 FX Bucket Curvature Settings
	 */

	public static BucketCurvatureSettings ISDA_FX_21 (
		final java.lang.String vegaCategory,
		final int vegaDurationDays)
	{
		java.util.Map<java.lang.String, java.lang.Double> fxConcentrationCategoryVegaMap =
			org.drip.simm.fx.FXRiskThresholdContainer21.CategoryVegaMap();

		try {
			return !fxConcentrationCategoryVegaMap.containsKey (vegaCategory) ? null :
				BucketCurvatureSettings.ISDA (
					org.drip.simm.fx.FXSystemics21.VEGA_RISK_WEIGHT *
						org.drip.simm.fx.FXSystemics21.DELTA_RISK_WEIGHT,
					org.drip.simm.fx.FXSystemics21.CORRELATION,
					java.lang.Math.sqrt (365. / 14.) /
						org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
					vegaDurationDays
				);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BucketCurvatureSettings Constructor
	 * 
	 * @param riskWeight The Vega Risk Weight
	 * @param memberCorrelation The Member Correlation
	 * @param impliedVolatility The Implied Volatility
	 * @param tenorScalingFactor The Tenor Scaling Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketCurvatureSettings (
		final double riskWeight,
		final double memberCorrelation,
		final double impliedVolatility,
		final double tenorScalingFactor)
		throws java.lang.Exception
	{
		super (
			riskWeight,
			1.,
			memberCorrelation,
			impliedVolatility,
			1.
		);

		if (!org.drip.quant.common.NumberUtil.IsValid (_tenorScalingFactor = tenorScalingFactor))
		{
			throw new java.lang.Exception ("BucketCurvatureSettings Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Tenor Scaling Factor
	 * 
	 * @return The Tenor Scaling Factor
	 */

	public double tenorScalingFactor()
	{
		return _tenorScalingFactor;
	}

	/**
	 * Retrieve the Vega Risk Weight
	 * 
	 * @return The Vega Risk Weight
	 */

	public double vegaRiskWeight()
	{
		return super.riskWeight();
	}

	@Override public double riskWeight()
	{
		return super.riskWeight() * _tenorScalingFactor;
	}
}
