
package org.drip.simm.product;

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
 * BucketSensitivityCR holds the ISDA SIMM Risk Factor Tenor Bucket Sensitivities across CR Tenor Factors.
 *  The References are:
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

public class BucketSensitivityCR
{
	private org.drip.simm.product.RiskFactorTenorSensitivity _cumulativeTenorSensitivityMap = null;
	private java.util.Map<java.lang.String, org.drip.simm.product.RiskFactorTenorSensitivity>
		_tenorSensitivityMap = null;

	private org.drip.simm.margin.BucketAggregateCR linearAggregate (
		final org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettingsCR)
	{
		org.drip.simm.margin.RiskFactorAggregateCR riskFactorAggregateCR = curveAggregate
			(bucketSensitivitySettingsCR);

		if (null == riskFactorAggregateCR)
		{
			return null;
		}

		try
		{
			return new org.drip.simm.margin.BucketAggregateCR (
				riskFactorAggregateCR,
				riskFactorAggregateCR.linearMarginCovariance (bucketSensitivitySettingsCR),
				riskFactorAggregateCR.cumulativeTenorSensitivityMargin()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private org.drip.simm.margin.BucketAggregateCR curvatureAggregate (
		final org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettingsCR)
	{
		org.drip.simm.margin.RiskFactorAggregateCR riskFactorAggregateCR = curveAggregate
			(bucketSensitivitySettingsCR);

		if (null == riskFactorAggregateCR)
		{
			return null;
		}

		try
		{
			return new org.drip.simm.margin.BucketAggregateCR (
				riskFactorAggregateCR,
				riskFactorAggregateCR.curvatureMarginCovariance (bucketSensitivitySettingsCR),
				riskFactorAggregateCR.cumulativeTenorSensitivityMargin()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BucketSensitivityCR Constructor
	 * 
	 * @param tenorSensitivityMap The Risk Factor Tenor Sensitivity Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketSensitivityCR (
		final java.util.Map<java.lang.String, org.drip.simm.product.RiskFactorTenorSensitivity>
			tenorSensitivityMap)
		throws java.lang.Exception
	{
		if (null == (_tenorSensitivityMap = tenorSensitivityMap) || 0 == _tenorSensitivityMap.size())
		{
			throw new java.lang.Exception ("BucketSensitivityCR Constructor => Invalid Inputs");
		}

		java.util.Map<java.lang.String, java.lang.Double> riskFactorTenorSensitivityMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, org.drip.simm.product.RiskFactorTenorSensitivity>
			tenorSensitivityMapEntry : _tenorSensitivityMap.entrySet())
		{
			java.util.Map<java.lang.String, java.lang.Double> componentRiskFactorTenorSensitivityMap =
				tenorSensitivityMapEntry.getValue().sensitivityMap();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double>
				componentRiskFactorTenorSensitivityMapEntry :
				componentRiskFactorTenorSensitivityMap.entrySet())
			{
				java.lang.String tenor = componentRiskFactorTenorSensitivityMapEntry.getKey();

				if (riskFactorTenorSensitivityMap.containsKey (tenor))
				{
					riskFactorTenorSensitivityMap.put (
						tenor,
						riskFactorTenorSensitivityMap.get (tenor) +
							componentRiskFactorTenorSensitivityMap.get (tenor)
					);
				}
				else
				{
					riskFactorTenorSensitivityMap.put (
						tenor,
						componentRiskFactorTenorSensitivityMap.get (tenor)
					);
				}
			}
		}

		_cumulativeTenorSensitivityMap = new org.drip.simm.product.RiskFactorTenorSensitivity
			(riskFactorTenorSensitivityMap);
	}

	/**
	 * Retrieve the Cumulative Risk Factor Tenor Sensitivity Map
	 * 
	 * @return The Cumulative Risk Factor Tenor Sensitivity Map
	 */

	public org.drip.simm.product.RiskFactorTenorSensitivity cumulativeTenorSensitivityMap()
	{
		return _cumulativeTenorSensitivityMap;
	}

	/**
	 * Retrieve the Risk Factor Tenor Sensitivity Map
	 * 
	 * @return The Risk Factor Tenor Sensitivity Map
	 */

	public java.util.Map<java.lang.String, org.drip.simm.product.RiskFactorTenorSensitivity>
		tenorSensitivityMap()
	{
		return _tenorSensitivityMap;
	}

	/**
	 * Generate the Cumulative Tenor Sensitivity
	 * 
	 * @return The Cumulative Tenor Sensitivity
	 */

	public double cumulativeTenorSensitivity()
	{
		return _cumulativeTenorSensitivityMap.cumulative();
	}

	/**
	 * Compute the Sensitivity Concentration Risk Factor
	 * 
	 * @param sensitivityConcentrationThreshold The Sensitivity Concentration Threshold
	 * 
	 * @return The Sensitivity Concentration Risk Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double sensitivityConcentrationRiskFactor (
		final double sensitivityConcentrationThreshold)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (sensitivityConcentrationThreshold))
		{
			throw new java.lang.Exception
				("BucketSensitivityCR::sensitivityConcentrationRiskFactor => Invalid Inputs");
		}

		return java.lang.Math.max (
			java.lang.Math.sqrt (
				java.lang.Math.max (
					cumulativeTenorSensitivity(),
					0.
				) / sensitivityConcentrationThreshold
			),
			1.
		);
	}

	/**
	 * Generate the Tenor Sensitivity Margin Map
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The Tenor Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> tenorSensitivityMargin (
		final org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> tenorSensitivityMargin =
			_cumulativeTenorSensitivityMap.sensitivityMargin (bucketSensitivitySettings.tenorRiskWeight());

		if (null == tenorSensitivityMargin)
		{
			return tenorSensitivityMargin;
		}

		double sensitivityConcentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			sensitivityConcentrationRiskFactor = sensitivityConcentrationRiskFactor
				(bucketSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> tenorSensitivityMarginEntry :
			tenorSensitivityMargin.entrySet())
		{
			java.lang.String tenor = tenorSensitivityMarginEntry.getKey();

			tenorSensitivityMargin.put (
				tenor,
				tenorSensitivityMargin.get (tenor) * sensitivityConcentrationRiskFactor
			);
		}

		return tenorSensitivityMargin;
	}

	/**
	 * Generate the CR Margin Factor Curve Tenor Aggregate
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The CR Margin Factor Curve Tenor Aggregate
	 */

	public org.drip.simm.margin.RiskFactorAggregateCR curveAggregate (
		final org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> tenorSensitivityMargin =
			_cumulativeTenorSensitivityMap.sensitivityMargin (bucketSensitivitySettings.tenorRiskWeight());

		if (null == tenorSensitivityMargin)
		{
			return null;
		}

		double sensitivityConcentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			sensitivityConcentrationRiskFactor = sensitivityConcentrationRiskFactor
				(bucketSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> tenorSensitivityMarginEntry :
			tenorSensitivityMargin.entrySet())
		{
			java.lang.String tenor = tenorSensitivityMarginEntry.getKey();

			tenorSensitivityMargin.put (
				tenor,
				tenorSensitivityMargin.get (tenor) * sensitivityConcentrationRiskFactor
			);
		}

		try
		{
			return new org.drip.simm.margin.RiskFactorAggregateCR (
				tenorSensitivityMargin,
				sensitivityConcentrationRiskFactor
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Bucket CR Sensitivity Margin Aggregate
	 * 
	 * @param bucketSensitivitySettingsCR The CR Bucket Sensitivity Settings
	 * 
	 * @return The Bucket IR Sensitivity Margin Aggregate
	 */

	public org.drip.simm.margin.BucketAggregateCR aggregate (
		final org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettingsCR)
	{
		if (null == bucketSensitivitySettingsCR)
		{
			return null;
		}

		return bucketSensitivitySettingsCR instanceof org.drip.simm.parameters.BucketCurvatureSettingsCR ?
			curvatureAggregate (bucketSensitivitySettingsCR) : linearAggregate (bucketSensitivitySettingsCR);
	}
}
