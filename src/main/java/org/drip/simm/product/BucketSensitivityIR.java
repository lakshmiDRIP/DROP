
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
 * BucketSensitivityIR holds the ISDA SIMM Risk Factor Tenor Bucket Sensitivities across IR Factor Sub
 *  Curves. USD Exposures enhanced with the USD specific Sub-Curve Factors - PRIME and MUNICIPAL. The
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

public class BucketSensitivityIR
{
	private org.drip.simm.product.RiskFactorTenorSensitivity _oisTenorSensitivity = null;
	private org.drip.simm.product.RiskFactorTenorSensitivity _primeTenorSensitivity = null;
	private org.drip.simm.product.RiskFactorTenorSensitivity _libor1MTenorSensitivity = null;
	private org.drip.simm.product.RiskFactorTenorSensitivity _libor3MTenorSensitivity = null;
	private org.drip.simm.product.RiskFactorTenorSensitivity _libor6MTenorSensitivity = null;
	private org.drip.simm.product.RiskFactorTenorSensitivity _libor12MTenorSensitivity = null;
	private org.drip.simm.product.RiskFactorTenorSensitivity _municipalTenorSensitivity = null;

	private org.drip.simm.margin.BucketAggregateIR linearAggregate (
		final org.drip.simm.parameters.BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		org.drip.simm.margin.RiskFactorAggregateIR riskFactorAggregate = curveAggregate
			(bucketSensitivitySettings);

		if (null == riskFactorAggregate)
		{
			return null;
		}

		org.drip.simm.margin.SensitivityAggregateIR sensitivityAggregate = riskFactorAggregate.linearMargin
			(bucketSensitivitySettings);

		if (null == sensitivityAggregate)
		{
			return null;
		}

		try
		{
			return new org.drip.simm.margin.BucketAggregateIR (
				riskFactorAggregate,
				sensitivityAggregate,
				sensitivityAggregate.cumulativeMarginCovariance(),
				riskFactorAggregate.cumulativeSensitivityMargin()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private org.drip.simm.margin.BucketAggregateIR curvatureAggregate (
		final org.drip.simm.parameters.BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		org.drip.simm.margin.RiskFactorAggregateIR riskFactorAggregate = curveAggregate
			(bucketSensitivitySettings);

		if (null == riskFactorAggregate)
		{
			return null;
		}

		org.drip.simm.margin.SensitivityAggregateIR sensitivityAggregate =
			riskFactorAggregate.curvatureMargin (bucketSensitivitySettings);

		if (null == sensitivityAggregate)
		{
			return null;
		}

		try
		{
			return new org.drip.simm.margin.BucketAggregateIR (
				riskFactorAggregate,
				sensitivityAggregate,
				sensitivityAggregate.cumulativeMarginCovariance(),
				riskFactorAggregate.cumulativeSensitivityMargin()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a Standard Instance of BucketSensitivityIR from the Tenor Sensitivity Maps
	 * 
	 * @param oisTenorSensitivity OIS Tenor Sensitivity Map
	 * @param libor1MTenorSensitivity LIBOR1M Tenor Sensitivity Map
	 * @param libor3MTenorSensitivity LIBOR3M Tenor Sensitivity Map
	 * @param libor6MTenorSensitivity LIBOR6M Tenor Sensitivity Map
	 * @param libor12MTenorSensitivity LIBOR 12M Tenor Sensitivity Map
	 * @param primeTenorSensitivity Prime Tenor Sensitivity Map
	 * @param municipalTenorSensitivity Municipal Tenor Sensitivity Map
	 * 
	 * @return Standard Instance of BucketSensitivityIR from the Tenor Sensitivity Maps
	 */

	public static final BucketSensitivityIR Standard (
		final java.util.Map<java.lang.String, java.lang.Double> oisTenorSensitivity,
		final java.util.Map<java.lang.String, java.lang.Double> libor1MTenorSensitivity,
		final java.util.Map<java.lang.String, java.lang.Double> libor3MTenorSensitivity,
		final java.util.Map<java.lang.String, java.lang.Double> libor6MTenorSensitivity,
		final java.util.Map<java.lang.String, java.lang.Double> libor12MTenorSensitivity,
		final java.util.Map<java.lang.String, java.lang.Double> primeTenorSensitivity,
		final java.util.Map<java.lang.String, java.lang.Double> municipalTenorSensitivity)
	{
		try
		{
			return new BucketSensitivityIR (
				new org.drip.simm.product.RiskFactorTenorSensitivity (oisTenorSensitivity),
				new org.drip.simm.product.RiskFactorTenorSensitivity (libor1MTenorSensitivity),
				new org.drip.simm.product.RiskFactorTenorSensitivity (libor3MTenorSensitivity),
				new org.drip.simm.product.RiskFactorTenorSensitivity (libor6MTenorSensitivity),
				new org.drip.simm.product.RiskFactorTenorSensitivity (libor12MTenorSensitivity),
				new org.drip.simm.product.RiskFactorTenorSensitivity (primeTenorSensitivity),
				new org.drip.simm.product.RiskFactorTenorSensitivity (municipalTenorSensitivity)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BucketSensitivityIR Constructor
	 * 
	 * @param oisTenorSensitivity The OIS Risk Factor Tenor Sensitivity
	 * @param libor1MTenorSensitivity The LIBOR1M Risk Factor Tenor Sensitivity
	 * @param libor3MTenorSensitivity The LIBOR3M Risk Factor Tenor Sensitivity
	 * @param libor6MTenorSensitivity The LIBOR6M Risk Factor Tenor Delta Sensitivity
	 * @param libor12MTenorSensitivity The LIBOR12M Risk Factor Tenor Sensitivity
	 * @param primeTenorSensitivity The PRIME Risk Factor Tenor Sensitivity
	 * @param municipalTenorSensitivity The MUNICIPAL Risk Factor Tenor Sensitivity
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketSensitivityIR (
		final org.drip.simm.product.RiskFactorTenorSensitivity oisTenorSensitivity,
		final org.drip.simm.product.RiskFactorTenorSensitivity libor1MTenorSensitivity,
		final org.drip.simm.product.RiskFactorTenorSensitivity libor3MTenorSensitivity,
		final org.drip.simm.product.RiskFactorTenorSensitivity libor6MTenorSensitivity,
		final org.drip.simm.product.RiskFactorTenorSensitivity libor12MTenorSensitivity,
		final org.drip.simm.product.RiskFactorTenorSensitivity primeTenorSensitivity,
		final org.drip.simm.product.RiskFactorTenorSensitivity municipalTenorSensitivity)
		throws java.lang.Exception
	{
		if (null == (_oisTenorSensitivity = oisTenorSensitivity) ||
			null == (_libor1MTenorSensitivity = libor1MTenorSensitivity) ||
			null == (_libor3MTenorSensitivity = libor3MTenorSensitivity) ||
			null == (_libor6MTenorSensitivity = libor6MTenorSensitivity) ||
			null == (_libor12MTenorSensitivity = libor12MTenorSensitivity) ||
			null == (_primeTenorSensitivity = primeTenorSensitivity) ||
			null == (_municipalTenorSensitivity = municipalTenorSensitivity))
		{
			throw new java.lang.Exception ("BucketSensitivityIR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the OIS Risk Factor Tenor Sensitivity
	 * 
	 * @return The OIS Risk Factor Tenor Sensitivity
	 */

	public org.drip.simm.product.RiskFactorTenorSensitivity oisTenorSensitivity()
	{
		return _oisTenorSensitivity;
	}

	/**
	 * Retrieve the LIBOR1M Risk Factor Tenor Sensitivity
	 * 
	 * @return The LIBOR1M Risk Factor Tenor Sensitivity
	 */

	public org.drip.simm.product.RiskFactorTenorSensitivity libor1MTenorSensitivity()
	{
		return _libor1MTenorSensitivity;
	}

	/**
	 * Retrieve the LIBOR3M Risk Factor Tenor Sensitivity
	 * 
	 * @return The LIBOR3M Risk Factor Tenor Sensitivity
	 */

	public org.drip.simm.product.RiskFactorTenorSensitivity libor3MTenorSensitivity()
	{
		return _libor3MTenorSensitivity;
	}

	/**
	 * Retrieve the LIBOR6M Risk Factor Tenor Sensitivity
	 * 
	 * @return The LIBOR6M Risk Factor Tenor Sensitivity
	 */

	public org.drip.simm.product.RiskFactorTenorSensitivity libor6MTenorSensitivity()
	{
		return _libor6MTenorSensitivity;
	}

	/**
	 * Retrieve the LIBOR12M Risk Factor Tenor Sensitivity
	 * 
	 * @return The LIBOR12M Risk Factor Tenor Sensitivity
	 */

	public org.drip.simm.product.RiskFactorTenorSensitivity libor12MTenorSensitivity()
	{
		return _libor12MTenorSensitivity;
	}

	/**
	 * Retrieve the PRIME Risk Factor Tenor Sensitivity
	 * 
	 * @return The PRIME Risk Factor Tenor Sensitivity
	 */

	public org.drip.simm.product.RiskFactorTenorSensitivity primeTenorSensitivity()
	{
		return _primeTenorSensitivity;
	}

	/**
	 * Retrieve the MUNICIPAL Risk Factor Tenor Sensitivity
	 * 
	 * @return The MUNICIPAL Risk Factor Tenor Sensitivity
	 */

	public org.drip.simm.product.RiskFactorTenorSensitivity municipalTenorSensitivity()
	{
		return _municipalTenorSensitivity;
	}

	/**
	 * Generate the Cumulative Tenor Sensitivity
	 * 
	 * @return The Cumulative Tenor Sensitivity
	 */

	public double cumulativeTenorSensitivity()
	{
		return _oisTenorSensitivity.cumulative() +
			_libor1MTenorSensitivity.cumulative() +
			_libor3MTenorSensitivity.cumulative() +
			_libor6MTenorSensitivity.cumulative() +
			_libor12MTenorSensitivity.cumulative() +
			_primeTenorSensitivity.cumulative() +
			_municipalTenorSensitivity.cumulative();
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
				("BucketSensitivityIR::sensitivityConcentrationRiskFactor => Invalid Inputs");
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
	 * Generate the OIS Tenor Sensitivity Margin Map
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The OIS Tenor Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> oisTenorMargin (
		final org.drip.simm.parameters.BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> oisTenorMargin =
			_oisTenorSensitivity.sensitivityMargin (bucketSensitivitySettings.oisTenorRiskWeight());

		if (null == oisTenorMargin)
		{
			return oisTenorMargin;
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

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisTenorMarginEntry :
			oisTenorMargin.entrySet())
		{
			java.lang.String tenor = oisTenorMarginEntry.getKey();

			oisTenorMargin.put (
				tenor,
				oisTenorMargin.get (tenor) * sensitivityConcentrationRiskFactor
			);
		}

		return oisTenorMargin;
	}

	/**
	 * Generate the LIBOR1M Tenor Sensitivity Margin Map
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR1M Tenor Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor1MTenorMargin (
		final org.drip.simm.parameters.BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> libor1MTenorMargin =
			_libor1MTenorSensitivity.sensitivityMargin (bucketSensitivitySettings.libor1MTenorRiskWeight());

		if (null == libor1MTenorMargin)
		{
			return libor1MTenorMargin;
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

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MTenorMarginEntry :
			libor1MTenorMargin.entrySet())
		{
			java.lang.String tenor = libor1MTenorMarginEntry.getKey();

			libor1MTenorMargin.put (
				tenor,
				libor1MTenorMargin.get (tenor) * sensitivityConcentrationRiskFactor
			);
		}

		return libor1MTenorMargin;
	}

	/**
	 * Generate the LIBOR3M Tenor Sensitivity Margin Map
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR3M Tenor Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor3MTenorMargin (
		final org.drip.simm.parameters.BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> libor3MTenorMargin =
			_libor3MTenorSensitivity.sensitivityMargin (bucketSensitivitySettings.libor3MTenorRiskWeight());

		if (null == libor3MTenorMargin)
		{
			return libor3MTenorMargin;
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

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MTenorMarginEntry :
			libor3MTenorMargin.entrySet())
		{
			java.lang.String tenor = libor3MTenorMarginEntry.getKey();

			libor3MTenorMargin.put (
				tenor,
				libor3MTenorMargin.get (tenor) * sensitivityConcentrationRiskFactor
			);
		}

		return libor3MTenorMargin;
	}

	/**
	 * Generate the LIBOR6M Tenor Sensitivity Margin Map
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR6M Tenor Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor6MTenorMargin (
		final org.drip.simm.parameters.BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> libor6MTenorMargin =
			_libor6MTenorSensitivity.sensitivityMargin (bucketSensitivitySettings.libor6MTenorRiskWeight());

		if (null == libor6MTenorMargin)
		{
			return libor6MTenorMargin;
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

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MTenorMarginEntry :
			libor6MTenorMargin.entrySet())
		{
			java.lang.String tenor = libor6MTenorMarginEntry.getKey();

			libor6MTenorMargin.put (
				tenor,
				libor6MTenorMargin.get (tenor) * sensitivityConcentrationRiskFactor
			);
		}

		return libor6MTenorMargin;
	}

	/**
	 * Generate the LIBOR12M Tenor Sensitivity Margin Map
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR12M Tenor Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor12MTenorMargin (
		final org.drip.simm.parameters.BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> libor12MTenorMargin =
			_libor12MTenorSensitivity.sensitivityMargin
				(bucketSensitivitySettings.libor12MTenorRiskWeight());

		if (null == libor12MTenorMargin)
		{
			return libor12MTenorMargin;
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

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MTenorMarginEntry :
			libor12MTenorMargin.entrySet())
		{
			java.lang.String tenor = libor12MTenorMarginEntry.getKey();

			libor12MTenorMargin.put (
				tenor,
				libor12MTenorMargin.get (tenor) * sensitivityConcentrationRiskFactor
			);
		}

		return libor12MTenorMargin;
	}

	/**
	 * Generate the PRIME Tenor Sensitivity Margin Map
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The PRIME Tenor Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> primeTenorMargin (
		final org.drip.simm.parameters.BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> primeTenorMargin =
			_primeTenorSensitivity.sensitivityMargin (bucketSensitivitySettings.primeTenorRiskWeight());

		if (null == primeTenorMargin)
		{
			return primeTenorMargin;
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

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeTenorMarginEntry :
			primeTenorMargin.entrySet())
		{
			java.lang.String tenor = primeTenorMarginEntry.getKey();

			primeTenorMargin.put (
				tenor,
				primeTenorMargin.get (tenor) * sensitivityConcentrationRiskFactor
			);
		}

		return primeTenorMargin;
	}

	/**
	 * Generate the MUNICIPAL Tenor Sensitivity Margin Map
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The MUNICIPAL Tenor Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> municipalTenorMargin (
		final org.drip.simm.parameters.BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> municipalTenorMargin =
			_municipalTenorSensitivity.sensitivityMargin
				(bucketSensitivitySettings.municipalTenorRiskWeight());

		if (null == municipalTenorMargin)
		{
			return municipalTenorMargin;
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

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalTenorMarginEntry :
			municipalTenorMargin.entrySet())
		{
			java.lang.String tenor = municipalTenorMarginEntry.getKey();

			municipalTenorMargin.put (
				tenor,
				municipalTenorMargin.get (tenor) * sensitivityConcentrationRiskFactor
			);
		}

		return municipalTenorMargin;
	}

	/**
	 * Generate the IR Margin Factor Curve Tenor Aggregate
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The IR Margin Factor Curve Tenor Aggregate
	 */

	public org.drip.simm.margin.RiskFactorAggregateIR curveAggregate (
		final org.drip.simm.parameters.BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> oisTenorMargin =
			_oisTenorSensitivity.sensitivityMargin (bucketSensitivitySettings.oisTenorRiskWeight());

		java.util.Map<java.lang.String, java.lang.Double> libor1MTenorMargin =
			_libor1MTenorSensitivity.sensitivityMargin (bucketSensitivitySettings.libor1MTenorRiskWeight());

		java.util.Map<java.lang.String, java.lang.Double> libor3MTenorMargin =
			_libor3MTenorSensitivity.sensitivityMargin (bucketSensitivitySettings.libor3MTenorRiskWeight());

		java.util.Map<java.lang.String, java.lang.Double> libor6MTenorMargin =
			_libor6MTenorSensitivity.sensitivityMargin (bucketSensitivitySettings.libor6MTenorRiskWeight());

		java.util.Map<java.lang.String, java.lang.Double> libor12MTenorMargin =
			_libor12MTenorSensitivity.sensitivityMargin
				(bucketSensitivitySettings.libor12MTenorRiskWeight());

		java.util.Map<java.lang.String, java.lang.Double> primeTenorMargin =
			_primeTenorSensitivity.sensitivityMargin (bucketSensitivitySettings.primeTenorRiskWeight());

		java.util.Map<java.lang.String, java.lang.Double> municipalTenorMargin =
			_municipalTenorSensitivity.sensitivityMargin
				(bucketSensitivitySettings.municipalTenorRiskWeight());

		if (null == oisTenorMargin ||
			null == libor1MTenorMargin ||
			null == libor3MTenorMargin ||
			null == libor6MTenorMargin ||
			null == libor12MTenorMargin ||
			null == primeTenorMargin ||
			null == municipalTenorMargin)
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

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalTenorMarginEntry :
			municipalTenorMargin.entrySet())
		{
			java.lang.String tenor = municipalTenorMarginEntry.getKey();

			oisTenorMargin.put (
				tenor,
				oisTenorMargin.get (tenor) * sensitivityConcentrationRiskFactor
			);

			libor1MTenorMargin.put (
				tenor,
				libor1MTenorMargin.get (tenor) * sensitivityConcentrationRiskFactor
			);

			libor3MTenorMargin.put (
				tenor,
				libor3MTenorMargin.get (tenor) * sensitivityConcentrationRiskFactor
			);

			libor6MTenorMargin.put (
				tenor,
				libor6MTenorMargin.get (tenor) * sensitivityConcentrationRiskFactor
			);

			libor12MTenorMargin.put (
				tenor,
				libor12MTenorMargin.get (tenor) * sensitivityConcentrationRiskFactor
			);

			primeTenorMargin.put (
				tenor,
				primeTenorMargin.get (tenor) * sensitivityConcentrationRiskFactor
			);

			municipalTenorMargin.put (
				tenor,
				municipalTenorMargin.get (tenor) * sensitivityConcentrationRiskFactor
			);
		}

		try
		{
			return new org.drip.simm.margin.RiskFactorAggregateIR (
				oisTenorMargin,
				libor1MTenorMargin,
				libor3MTenorMargin,
				libor6MTenorMargin,
				libor12MTenorMargin,
				primeTenorMargin,
				municipalTenorMargin,
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
	 * Generate the Bucket IR Sensitivity Margin Aggregate
	 * 
	 * @param bucketSensitivitySettingsIR The IR Bucket Sensitivity Settings
	 * 
	 * @return The Bucket IR Sensitivity Margin Aggregate
	 */

	public org.drip.simm.margin.BucketAggregateIR aggregate (
		final org.drip.simm.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
	{
		if (null == bucketSensitivitySettingsIR)
		{
			return null;
		}

		return bucketSensitivitySettingsIR instanceof org.drip.simm.parameters.BucketCurvatureSettingsIR ?
			curvatureAggregate (bucketSensitivitySettingsIR) : linearAggregate (bucketSensitivitySettingsIR);
	}
}
