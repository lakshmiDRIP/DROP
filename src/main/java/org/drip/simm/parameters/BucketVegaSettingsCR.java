
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
 * BucketVegaSettingsCR holds the Vega Risk Weights, Concentration Thresholds, and Cross-Tenor Correlations
 *  for each Credit Curve and its Tenor. The References are:
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

public class BucketVegaSettingsCR extends org.drip.simm.parameters.BucketSensitivitySettingsCR
{
	private double _vegaScaler = java.lang.Double.NaN;
	private double _historicalVolatilityRatio = java.lang.Double.NaN;
	private java.util.Map<java.lang.String, java.lang.Double> _tenorDeltaRiskWeight = null;

	/**
	 * Retrieve the ISDA 2.0 Credit Qualifying Bucket Vega Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.0 Credit Qualifying Bucket Vega Settings
	 */

	public static BucketVegaSettingsCR ISDA_CRQ_20 (
		final int bucketNumber)
	{
		org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettingsCR =
			org.drip.simm.parameters.BucketSensitivitySettingsCR.ISDA_CRQ_DELTA_20 (bucketNumber);

		if (null == bucketSensitivitySettingsCR)
		{
			return null;
		}

		try
		{
			return new BucketVegaSettingsCR (
				TenorRiskWeightMap (org.drip.simm.credit.CRQSystemics20.VEGA_RISK_WEIGHT),
				bucketSensitivitySettingsCR.sameIssuerSeniorityCorrelation(),
				bucketSensitivitySettingsCR.differentIssuerSeniorityCorrelation(),
				org.drip.simm.credit.CRThresholdContainer20.QualifyingThreshold (bucketNumber).vega(),
				java.lang.Math.sqrt (365. / 14.) /
					org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
				1.,
				bucketSensitivitySettingsCR.tenorRiskWeight()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the ISDA 2.0 Credit Non-Qualifying Bucket Vega Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.0 Credit Non-Qualifying Bucket Vega Settings
	 */

	public static BucketVegaSettingsCR ISDA_CRNQ_20 (
		final int bucketNumber)
	{
		org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettingsCR =
			org.drip.simm.parameters.BucketSensitivitySettingsCR.ISDA_CRNQ_DELTA_20 (bucketNumber);

		if (null == bucketSensitivitySettingsCR)
		{
			return null;
		}

		try
		{
			return new BucketVegaSettingsCR (
				TenorRiskWeightMap (org.drip.simm.credit.CRNQSystemics20.VEGA_RISK_WEIGHT),
				bucketSensitivitySettingsCR.sameIssuerSeniorityCorrelation(),
				bucketSensitivitySettingsCR.differentIssuerSeniorityCorrelation(),
				org.drip.simm.credit.CRThresholdContainer20.NonQualifyingThreshold (bucketNumber).vega(),
				java.lang.Math.sqrt (365. / 14.) /
					org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
				1.,
				bucketSensitivitySettingsCR.tenorRiskWeight()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the ISDA 2.1 Credit Qualifying Bucket Vega Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.1 Credit Qualifying Bucket Vega Settings
	 */

	public static BucketVegaSettingsCR ISDA_CRQ_21 (
		final int bucketNumber)
	{
		org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettingsCR =
			org.drip.simm.parameters.BucketSensitivitySettingsCR.ISDA_CRQ_DELTA_21 (bucketNumber);

		if (null == bucketSensitivitySettingsCR)
		{
			return null;
		}

		try
		{
			return new BucketVegaSettingsCR (
				TenorRiskWeightMap (org.drip.simm.credit.CRQSystemics21.VEGA_RISK_WEIGHT),
				bucketSensitivitySettingsCR.sameIssuerSeniorityCorrelation(),
				bucketSensitivitySettingsCR.differentIssuerSeniorityCorrelation(),
				org.drip.simm.credit.CRThresholdContainer21.QualifyingThreshold (bucketNumber).vega(),
				java.lang.Math.sqrt (365. / 14.) /
					org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
				1.,
				bucketSensitivitySettingsCR.tenorRiskWeight()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the ISDA 2.1 Credit Non-Qualifying Bucket Vega Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.1 Credit Non-Qualifying Bucket Vega Settings
	 */

	public static BucketVegaSettingsCR ISDA_CRNQ_21 (
		final int bucketNumber)
	{
		org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettingsCR =
			org.drip.simm.parameters.BucketSensitivitySettingsCR.ISDA_CRNQ_DELTA_21 (bucketNumber);

		if (null == bucketSensitivitySettingsCR)
		{
			return null;
		}

		try
		{
			return new BucketVegaSettingsCR (
				TenorRiskWeightMap (org.drip.simm.credit.CRNQSystemics21.VEGA_RISK_WEIGHT),
				bucketSensitivitySettingsCR.sameIssuerSeniorityCorrelation(),
				bucketSensitivitySettingsCR.differentIssuerSeniorityCorrelation(),
				org.drip.simm.credit.CRThresholdContainer21.NonQualifyingThreshold (bucketNumber).vega(),
				java.lang.Math.sqrt (365. / 14.) /
					org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
				1.,
				bucketSensitivitySettingsCR.tenorRiskWeight()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BucketVegaSettingsCR Constructor
	 * 
	 * @param tenorVegaRiskWeight The Tenor Vega Risk Weight Map
	 * @param sameIssuerSeniorityCorrelation Same Issuer/Seniority Correlation
	 * @param differentIssuerSeniorityCorrelation Different Issuer/Seniority Correlation
	 * @param concentrationThreshold The Concentration Threshold
	 * @param vegaScaler The Vega Scaler
	 * @param historicalVolatilityRatio The Historical Volatility Ratio
	 * @param tenorDeltaRiskWeight The Credit Tenor Delta Risk Weight
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketVegaSettingsCR (
		final java.util.Map<java.lang.String, java.lang.Double> tenorVegaRiskWeight,
		final double sameIssuerSeniorityCorrelation,
		final double differentIssuerSeniorityCorrelation,
		final double concentrationThreshold,
		final double vegaScaler,
		final double historicalVolatilityRatio,
		final java.util.Map<java.lang.String, java.lang.Double> tenorDeltaRiskWeight)
		throws java.lang.Exception
	{
		super (
			tenorVegaRiskWeight,
			sameIssuerSeniorityCorrelation,
			differentIssuerSeniorityCorrelation,
			concentrationThreshold
		);

		if (!org.drip.quant.common.NumberUtil.IsValid (_vegaScaler = vegaScaler) ||
			!org.drip.quant.common.NumberUtil.IsValid (_historicalVolatilityRatio =
				historicalVolatilityRatio) ||
			null == (_tenorDeltaRiskWeight = tenorDeltaRiskWeight))
		{
			throw new java.lang.Exception ("BucketVegaSettingsIR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Vega Scaler
	 * 
	 * @return The Vega Scaler
	 */

	public double vegaScaler()
	{
		return _vegaScaler;
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
	 * Retrieve the Tenor Delta Risk Weight
	 * 
	 * @return The Tenor Delta Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> tenorDeltaRiskWeight()
	{
		return _tenorDeltaRiskWeight;
	}

	/**
	 * Retrieve the Tenor Vega Risk Weight
	 * 
	 * @return The Tenor Vega Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> tenorVegaRiskWeight()
	{
		return super.tenorRiskWeight();
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> tenorRiskWeight()
	{
		java.util.Map<java.lang.String, java.lang.Double> tenorVegaRiskWeight = tenorVegaRiskWeight();

		java.util.Map<java.lang.String, java.lang.Double> tenorRiskWeight = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> tenorVegaRiskWeightEntry :
			tenorVegaRiskWeight.entrySet())
		{
			java.lang.String tenor = tenorVegaRiskWeightEntry.getKey();

			if (!tenorVegaRiskWeight.containsKey (tenor))
			{
				return null;
			}

			tenorRiskWeight.put (
				tenor,
				tenorVegaRiskWeightEntry.getValue() * _tenorDeltaRiskWeight.get (tenor) * _vegaScaler *
					_historicalVolatilityRatio
			);
		}

		return tenorRiskWeight;
	}
}
