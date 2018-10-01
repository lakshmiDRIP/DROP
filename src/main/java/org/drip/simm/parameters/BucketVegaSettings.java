
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
 * BucketVegaSettings holds the Settings that govern the Generation of the ISDA SIMM Single Bucket Vega
 *  Sensitivities. The References are:
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

public class BucketVegaSettings extends org.drip.simm.parameters.BucketSensitivitySettings
{
	private double _impliedVolatility = java.lang.Double.NaN;
	private double _historicalVolatilityRatio = java.lang.Double.NaN;

	/**
	 * Retrieve the ISDA 2.0 Equity Vega Settings
	 * 
	 * @param bucketIndex The Bucket Index
	 * 
	 * @return The ISDA 2.0 Equity Vega Settings
	 */

	public static BucketVegaSettings ISDA_EQ_20 (
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
			return new BucketVegaSettings (
				equityBucket.vegaRiskWeight() * equityBucket.deltaRiskWeight(),
				org.drip.simm.equity.EQRiskThresholdContainer20.DeltaVegaThresholdMap().get
					(bucketIndex).vega(),
				equityBucket.memberCorrelation(),
				java.lang.Math.sqrt (365. / 14.) /
					org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
				org.drip.simm.equity.EQSystemics20.HISTORICAL_VOLATILITY_RATIO
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard ISDA 2.0 Commodity Vega Settings for the specified Bucket
	 * 
	 * @param bucketIndex The Bucket Index
	 * 
	 * @return The Standard ISDA 2.0 Commodity Vega Settings for the specified Bucket
	 */

	public static BucketVegaSettings ISDA_CT_20 (
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
			return new org.drip.simm.parameters.BucketVegaSettings (
				org.drip.simm.commodity.CTSystemics20.VEGA_RISK_WEIGHT * commodityBucket.deltaRiskWeight(),
				org.drip.simm.commodity.CTRiskThresholdContainer20.DeltaVegaThresholdMap().get
					(bucketIndex).vega(),
				commodityBucket.memberCorrelation(),
				java.lang.Math.sqrt (365. / 14.) /
					org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
				org.drip.simm.commodity.CTSystemics20.HISTORICAL_VOLATILITY_RATIO
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard ISDA 2.1 Commodity Vega Settings for the specified Bucket
	 * 
	 * @param bucketIndex The Bucket Index
	 * 
	 * @return The Standard ISDA 2.1 Commodity Vega Settings for the specified Bucket
	 */

	public static BucketVegaSettings ISDA_CT_21 (
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
			return new org.drip.simm.parameters.BucketVegaSettings (
				org.drip.simm.commodity.CTSystemics21.VEGA_RISK_WEIGHT * commodityBucket.deltaRiskWeight(),
				org.drip.simm.commodity.CTRiskThresholdContainer21.DeltaVegaThresholdMap().get
					(bucketIndex).vega(),
				commodityBucket.memberCorrelation(),
				java.lang.Math.sqrt (365. / 14.) /
					org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
				org.drip.simm.commodity.CTSystemics21.HISTORICAL_VOLATILITY_RATIO
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard ISDA 2.0 Bucket FX Settings
	 * 
	 * @param vegaCategory The Vega Category
	 * 
	 * @return The Standard ISDA 2.0 Bucket FX Settings
	 */

	public static BucketVegaSettings ISDA_FX_20 (
		final java.lang.String vegaCategory)
	{
		java.util.Map<java.lang.String, java.lang.Double> fxConcentrationCategoryVegaMap =
			org.drip.simm.fx.FXRiskThresholdContainer20.CategoryVegaMap();

		try
		{
			return fxConcentrationCategoryVegaMap.containsKey (vegaCategory) ?
				new org.drip.simm.parameters.BucketVegaSettings (
					org.drip.simm.fx.FXSystemics20.VEGA_RISK_WEIGHT *
						org.drip.simm.fx.FXSystemics20.DELTA_RISK_WEIGHT,
					fxConcentrationCategoryVegaMap.get (vegaCategory),
					org.drip.simm.fx.FXSystemics20.CORRELATION,
					java.lang.Math.sqrt (365. / 14.) /
						org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
					org.drip.simm.fx.FXSystemics20.HISTORICAL_VOLATILITY_RATIO
				) : null;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard ISDA 2.1 Bucket FX Settings
	 * 
	 * @param vegaCategory The Vega Category
	 * 
	 * @return The Standard ISDA 2.1 Bucket FX Settings
	 */

	public static BucketVegaSettings ISDA_FX_21 (
		final java.lang.String vegaCategory)
	{
		java.util.Map<java.lang.String, java.lang.Double> fxConcentrationCategoryVegaMap =
			org.drip.simm.fx.FXRiskThresholdContainer21.CategoryVegaMap();

		try
		{
			return fxConcentrationCategoryVegaMap.containsKey (vegaCategory) ?
				new org.drip.simm.parameters.BucketVegaSettings (
					org.drip.simm.fx.FXSystemics21.VEGA_RISK_WEIGHT *
						org.drip.simm.fx.FXSystemics21.DELTA_RISK_WEIGHT,
					fxConcentrationCategoryVegaMap.get (vegaCategory),
					org.drip.simm.fx.FXSystemics21.CORRELATION,
					java.lang.Math.sqrt (365. / 14.) /
						org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
					org.drip.simm.fx.FXSystemics21.HISTORICAL_VOLATILITY_RATIO
				) : null;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BucketVegaSettings Constructor
	 * 
	 * @param riskWeight The Vega Risk Weight
	 * @param concentrationFactor The Concentration Factor
	 * @param memberCorrelation The Member Correlation
	 * @param impliedVolatility The Implied Volatility
	 * @param historicalVolatilityRatio The Historical Volatility Ratio
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketVegaSettings (
		final double riskWeight,
		final double concentrationFactor,
		final double memberCorrelation,
		final double impliedVolatility,
		final double historicalVolatilityRatio)
		throws java.lang.Exception
	{
		super (
			riskWeight,
			concentrationFactor,
			memberCorrelation
		);

		if (!org.drip.quant.common.NumberUtil.IsValid (_impliedVolatility = impliedVolatility) ||
				0. > _impliedVolatility ||
			!org.drip.quant.common.NumberUtil.IsValid (_historicalVolatilityRatio =
				historicalVolatilityRatio) || 0. > _historicalVolatilityRatio)
		{
			throw new java.lang.Exception ("BucketVegaSettings Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Implied Volatility
	 * 
	 * @return The Implied Volatility
	 */

	public double impliedVolatility()
	{
		return _impliedVolatility;
	}

	/**
	 * Retrieve the Historical Volatility Ratio
	 * 
	 * @return The Historical Volatility Ratio
	 */

	public double historicalVolatilityRatio()
	{
		return _historicalVolatilityRatio;
	}

	/**
	 * Retrieve the Raw Vega Risk Weight
	 * 
	 * @return The Raw Vega Risk Weight
	 */

	public double rawRiskWeight()
	{
		return super.riskWeight();
	}

	@Override public double riskWeight()
	{
		return super.riskWeight() * _impliedVolatility;
	}
}
