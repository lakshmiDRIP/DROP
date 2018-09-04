
package org.drip.simm20.product;

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
 * BucketSensitivityIR holds the ISDA SIMM 2.0 Risk Factor Tenor Bucket Sensitivities across IR Factor Sub
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
	private org.drip.simm20.product.RiskFactorTenorSensitivity _oisTenorDeltaSensitivity = null;
	private org.drip.simm20.product.RiskFactorTenorSensitivity _primeTenorDeltaSensitivity = null;
	private org.drip.simm20.product.RiskFactorTenorSensitivity _libor1MTenorDeltaSensitivity = null;
	private org.drip.simm20.product.RiskFactorTenorSensitivity _libor3MTenorDeltaSensitivity = null;
	private org.drip.simm20.product.RiskFactorTenorSensitivity _libor6MTenorDeltaSensitivity = null;
	private org.drip.simm20.product.RiskFactorTenorSensitivity _libor12MTenorDeltaSensitivity = null;
	private org.drip.simm20.product.RiskFactorTenorSensitivity _municipalTenorDeltaSensitivity = null;

	/**
	 * Generate a Standard Instance of BucketSensitivityIR from the Tenor Delta Sensitivity Maps
	 * 
	 * @param oisTenorDeltaSensitivity OIS Tenor Delta Sensitivity Map
	 * @param libor1MTenorDeltaSensitivity LIBOR1M Tenor Delta Sensitivity Map
	 * @param libor3MTenorDeltaSensitivity LIBOR3M Tenor Delta Sensitivity Map
	 * @param libor6MTenorDeltaSensitivity LIBOR6M Tenor Delta Sensitivity Map
	 * @param libor12MTenorDeltaSensitivity LIBOR 12M Tenor Delta Sensitivity Map
	 * @param primeTenorDeltaSensitivity Prime Tenor Delta Sensitivity Map
	 * @param municipalTenorDeltaSensitivity Municipal Tenor Delta Sensitivity Map
	 * 
	 * @return Standard Instance of IRCurveDeltaSensitivity from the Sensitivity Maps
	 */

	public static final BucketSensitivityIR Standard (
		final java.util.Map<java.lang.String, java.lang.Double> oisTenorDeltaSensitivity,
		final java.util.Map<java.lang.String, java.lang.Double> libor1MTenorDeltaSensitivity,
		final java.util.Map<java.lang.String, java.lang.Double> libor3MTenorDeltaSensitivity,
		final java.util.Map<java.lang.String, java.lang.Double> libor6MTenorDeltaSensitivity,
		final java.util.Map<java.lang.String, java.lang.Double> libor12MTenorDeltaSensitivity,
		final java.util.Map<java.lang.String, java.lang.Double> primeTenorDeltaSensitivity,
		final java.util.Map<java.lang.String, java.lang.Double> municipalTenorDeltaSensitivity)
	{
		try
		{
			return new BucketSensitivityIR (
				new org.drip.simm20.product.RiskFactorTenorSensitivity (oisTenorDeltaSensitivity),
				new org.drip.simm20.product.RiskFactorTenorSensitivity (libor1MTenorDeltaSensitivity),
				new org.drip.simm20.product.RiskFactorTenorSensitivity (libor3MTenorDeltaSensitivity),
				new org.drip.simm20.product.RiskFactorTenorSensitivity (libor6MTenorDeltaSensitivity),
				new org.drip.simm20.product.RiskFactorTenorSensitivity (libor12MTenorDeltaSensitivity),
				new org.drip.simm20.product.RiskFactorTenorSensitivity (primeTenorDeltaSensitivity),
				new org.drip.simm20.product.RiskFactorTenorSensitivity (municipalTenorDeltaSensitivity)
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
	 * @param oisTenorDeltaSensitivity The OIS Risk Factor Tenor Delta Sensitivity
	 * @param libor1MTenorDeltaSensitivity The LIBOR1M Risk Factor Tenor Delta Sensitivity
	 * @param libor3MTenorDeltaSensitivity The LIBOR3M Risk Factor Tenor Delta Sensitivity
	 * @param libor6MTenorDeltaSensitivity The LIBOR6M Risk Factor Tenor Delta Sensitivity
	 * @param libor12MTenorDeltaSensitivity The LIBOR12M Risk Factor Tenor Delta Sensitivity
	 * @param primeTenorDeltaSensitivity The PRIME Risk Factor Tenor Delta Sensitivity
	 * @param municipalTenorDeltaSensitivity The MUNICIPAL Risk Factor Tenor Delta Sensitivity
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketSensitivityIR (
		final org.drip.simm20.product.RiskFactorTenorSensitivity oisTenorDeltaSensitivity,
		final org.drip.simm20.product.RiskFactorTenorSensitivity libor1MTenorDeltaSensitivity,
		final org.drip.simm20.product.RiskFactorTenorSensitivity libor3MTenorDeltaSensitivity,
		final org.drip.simm20.product.RiskFactorTenorSensitivity libor6MTenorDeltaSensitivity,
		final org.drip.simm20.product.RiskFactorTenorSensitivity libor12MTenorDeltaSensitivity,
		final org.drip.simm20.product.RiskFactorTenorSensitivity primeTenorDeltaSensitivity,
		final org.drip.simm20.product.RiskFactorTenorSensitivity municipalTenorDeltaSensitivity)
		throws java.lang.Exception
	{
		if (null == (_oisTenorDeltaSensitivity = oisTenorDeltaSensitivity) ||
			null == (_libor1MTenorDeltaSensitivity = libor1MTenorDeltaSensitivity) ||
			null == (_libor3MTenorDeltaSensitivity = libor3MTenorDeltaSensitivity) ||
			null == (_libor6MTenorDeltaSensitivity = libor6MTenorDeltaSensitivity) ||
			null == (_libor12MTenorDeltaSensitivity = libor12MTenorDeltaSensitivity) ||
			null == (_primeTenorDeltaSensitivity = primeTenorDeltaSensitivity) ||
			null == (_municipalTenorDeltaSensitivity = municipalTenorDeltaSensitivity))
		{
			throw new java.lang.Exception ("BucketSensitivityIR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the OIS Risk Factor Tenor Delta Sensitivity
	 * 
	 * @return The OIS Risk Factor Tenor Delta Sensitivity
	 */

	public org.drip.simm20.product.RiskFactorTenorSensitivity oisTenorDeltaSensitivity()
	{
		return _oisTenorDeltaSensitivity;
	}

	/**
	 * Retrieve the LIBOR1M Risk Factor Tenor Delta Sensitivity
	 * 
	 * @return The LIBOR1M Risk Factor Tenor Delta Sensitivity
	 */

	public org.drip.simm20.product.RiskFactorTenorSensitivity libor1MTenorDeltaSensitivity()
	{
		return _libor1MTenorDeltaSensitivity;
	}

	/**
	 * Retrieve the LIBOR3M Risk Factor Tenor Delta Sensitivity
	 * 
	 * @return The LIBOR3M Risk Factor Tenor Delta Sensitivity
	 */

	public org.drip.simm20.product.RiskFactorTenorSensitivity libor3MTenorDeltaSensitivity()
	{
		return _libor3MTenorDeltaSensitivity;
	}

	/**
	 * Retrieve the LIBOR6M Risk Factor Tenor Delta Sensitivity
	 * 
	 * @return The LIBOR6M Risk Factor Tenor Delta Sensitivity
	 */

	public org.drip.simm20.product.RiskFactorTenorSensitivity libor6MTenorDeltaSensitivity()
	{
		return _libor6MTenorDeltaSensitivity;
	}

	/**
	 * Retrieve the LIBOR12M Risk Factor Tenor Delta Sensitivity
	 * 
	 * @return The LIBOR12M Risk Factor Tenor Delta Sensitivity
	 */

	public org.drip.simm20.product.RiskFactorTenorSensitivity libor12MTenorDeltaSensitivity()
	{
		return _libor12MTenorDeltaSensitivity;
	}

	/**
	 * Retrieve the PRIME Risk Factor Tenor Delta Sensitivity
	 * 
	 * @return The PRIME Risk Factor Tenor Delta Sensitivity
	 */

	public org.drip.simm20.product.RiskFactorTenorSensitivity primeTenorDeltaSensitivity()
	{
		return _primeTenorDeltaSensitivity;
	}

	/**
	 * Retrieve the MUNICIPAL Risk Factor Tenor Delta Sensitivity
	 * 
	 * @return The MUNICIPAL Risk Factor Tenor Delta Sensitivity
	 */

	public org.drip.simm20.product.RiskFactorTenorSensitivity municipalTenorDeltaSensitivity()
	{
		return _municipalTenorDeltaSensitivity;
	}

	/**
	 * Generate the Cumulative Tenor Delta Sensitivity
	 * 
	 * @return The Cumulative Tenor Delta Sensitivity
	 */

	public double cumulativeTenorDeltaSensitivity()
	{
		return _oisTenorDeltaSensitivity.cumulativeDelta() +
			_libor1MTenorDeltaSensitivity.cumulativeDelta() +
			_libor3MTenorDeltaSensitivity.cumulativeDelta() +
			_libor6MTenorDeltaSensitivity.cumulativeDelta() +
			_libor12MTenorDeltaSensitivity.cumulativeDelta() +
			_primeTenorDeltaSensitivity.cumulativeDelta() +
			_municipalTenorDeltaSensitivity.cumulativeDelta();
	}

	/**
	 * Compute the Delta Concentration Risk Factor
	 * 
	 * @param deltaConcentrationThreshold The Delta Concentration Threshold
	 * 
	 * @return The Delta Concentration Risk Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double deltaConcentrationRiskFactor (
		final double deltaConcentrationThreshold)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (deltaConcentrationThreshold))
		{
			throw new java.lang.Exception
				("BucketSensitivityIR::deltaConcentrationRiskFactor => Invalid Inputs");
		}

		return java.lang.Math.max (
			java.lang.Math.sqrt (
				java.lang.Math.max (
					cumulativeTenorDeltaSensitivity(),
					0.
				) / deltaConcentrationThreshold
			),
			1.
		);
	}

	/**
	 * Generate the OIS Tenor Delta Sensitivity Margin Map
	 * 
	 * @param bucketDeltaSensitivitySettings The Bucket Delta Tenor Sensitivity Settings
	 * 
	 * @return The OIS Tenor Delta Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> oisTenorDeltaMargin (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketDeltaSensitivitySettings)
	{
		if (null == bucketDeltaSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> oisTenorDeltaMargin =
			_oisTenorDeltaSensitivity.deltaSensitivityMargin
				(bucketDeltaSensitivitySettings.oisTenorRiskWeight());

		if (null == oisTenorDeltaMargin)
		{
			return oisTenorDeltaMargin;
		}

		double deltaConcentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			deltaConcentrationRiskFactor = deltaConcentrationRiskFactor
				(bucketDeltaSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisTenorDeltaMarginEntry :
			oisTenorDeltaMargin.entrySet())
		{
			java.lang.String tenor = oisTenorDeltaMarginEntry.getKey();

			oisTenorDeltaMargin.put (
				tenor,
				oisTenorDeltaMargin.get (tenor) * deltaConcentrationRiskFactor
			);
		}

		return oisTenorDeltaMargin;
	}

	/**
	 * Generate the LIBOR1M Tenor Delta Sensitivity Margin Map
	 * 
	 * @param bucketDeltaSensitivitySettings The Bucket Delta Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR1M Tenor Delta Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor1MTenorDeltaMargin (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketDeltaSensitivitySettings)
	{
		if (null == bucketDeltaSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> libor1MTenorDeltaMargin =
			_libor1MTenorDeltaSensitivity.deltaSensitivityMargin
				(bucketDeltaSensitivitySettings.libor1MTenorRiskWeight());

		if (null == libor1MTenorDeltaMargin)
		{
			return libor1MTenorDeltaMargin;
		}

		double deltaConcentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			deltaConcentrationRiskFactor = deltaConcentrationRiskFactor
				(bucketDeltaSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MTenorDeltaMarginEntry :
			libor1MTenorDeltaMargin.entrySet())
		{
			java.lang.String tenor = libor1MTenorDeltaMarginEntry.getKey();

			libor1MTenorDeltaMargin.put (
				tenor,
				libor1MTenorDeltaMargin.get (tenor) * deltaConcentrationRiskFactor
			);
		}

		return libor1MTenorDeltaMargin;
	}

	/**
	 * Generate the LIBOR3M Tenor Delta Sensitivity Margin Map
	 * 
	 * @param bucketDeltaSensitivitySettings The Bucket Delta Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR3M Tenor Delta Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor3MTenorDeltaMargin (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketDeltaSensitivitySettings)
	{
		if (null == bucketDeltaSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> libor3MTenorDeltaMargin =
			_libor3MTenorDeltaSensitivity.deltaSensitivityMargin
				(bucketDeltaSensitivitySettings.libor3MTenorRiskWeight());

		if (null == libor3MTenorDeltaMargin)
		{
			return libor3MTenorDeltaMargin;
		}

		double deltaConcentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			deltaConcentrationRiskFactor = deltaConcentrationRiskFactor
				(bucketDeltaSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MTenorDeltaMarginEntry :
			libor3MTenorDeltaMargin.entrySet())
		{
			java.lang.String tenor = libor3MTenorDeltaMarginEntry.getKey();

			libor3MTenorDeltaMargin.put (
				tenor,
				libor3MTenorDeltaMargin.get (tenor) * deltaConcentrationRiskFactor
			);
		}

		return libor3MTenorDeltaMargin;
	}

	/**
	 * Generate the LIBOR6M Tenor Delta Sensitivity Margin Map
	 * 
	 * @param bucketDeltaSensitivitySettings The Bucket Delta Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR6M Tenor Delta Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor6MTenorDeltaMargin (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketDeltaSensitivitySettings)
	{
		if (null == bucketDeltaSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> libor6MTenorDeltaMargin =
			_libor6MTenorDeltaSensitivity.deltaSensitivityMargin
				(bucketDeltaSensitivitySettings.libor6MTenorRiskWeight());

		if (null == libor6MTenorDeltaMargin)
		{
			return libor6MTenorDeltaMargin;
		}

		double deltaConcentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			deltaConcentrationRiskFactor = deltaConcentrationRiskFactor
				(bucketDeltaSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MTenorDeltaMarginEntry :
			libor6MTenorDeltaMargin.entrySet())
		{
			java.lang.String tenor = libor6MTenorDeltaMarginEntry.getKey();

			libor6MTenorDeltaMargin.put (
				tenor,
				libor6MTenorDeltaMargin.get (tenor) * deltaConcentrationRiskFactor
			);
		}

		return libor6MTenorDeltaMargin;
	}

	/**
	 * Generate the LIBOR12M Tenor Delta Sensitivity Margin Map
	 * 
	 * @param bucketDeltaSensitivitySettings The Bucket Delta Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR12M Tenor Delta Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor12MTenorDeltaMargin (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketDeltaSensitivitySettings)
	{
		if (null == bucketDeltaSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> libor12MTenorDeltaMargin =
			_libor12MTenorDeltaSensitivity.deltaSensitivityMargin
				(bucketDeltaSensitivitySettings.libor12MTenorRiskWeight());

		if (null == libor12MTenorDeltaMargin)
		{
			return libor12MTenorDeltaMargin;
		}

		double deltaConcentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			deltaConcentrationRiskFactor = deltaConcentrationRiskFactor
				(bucketDeltaSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MTenorDeltaMarginEntry :
			libor12MTenorDeltaMargin.entrySet())
		{
			java.lang.String tenor = libor12MTenorDeltaMarginEntry.getKey();

			libor12MTenorDeltaMargin.put (
				tenor,
				libor12MTenorDeltaMargin.get (tenor) * deltaConcentrationRiskFactor
			);
		}

		return libor12MTenorDeltaMargin;
	}

	/**
	 * Generate the PRIME Tenor Delta Sensitivity Margin Map
	 * 
	 * @param bucketDeltaSensitivitySettings The Bucket Delta Tenor Sensitivity Settings
	 * 
	 * @return The PRIME Tenor Delta Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> primeTenorDeltaMargin (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketDeltaSensitivitySettings)
	{
		if (null == bucketDeltaSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> primeTenorDeltaMargin =
			_primeTenorDeltaSensitivity.deltaSensitivityMargin
				(bucketDeltaSensitivitySettings.primeTenorRiskWeight());

		if (null == primeTenorDeltaMargin)
		{
			return primeTenorDeltaMargin;
		}

		double deltaConcentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			deltaConcentrationRiskFactor = deltaConcentrationRiskFactor
				(bucketDeltaSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeTenorDeltaMarginEntry :
			primeTenorDeltaMargin.entrySet())
		{
			java.lang.String tenor = primeTenorDeltaMarginEntry.getKey();

			primeTenorDeltaMargin.put (
				tenor,
				primeTenorDeltaMargin.get (tenor) * deltaConcentrationRiskFactor
			);
		}

		return primeTenorDeltaMargin;
	}

	/**
	 * Generate the MUNICIPAL Tenor Delta Sensitivity Margin Map
	 * 
	 * @param bucketDeltaSensitivitySettings The Bucket Delta Tenor Sensitivity Settings
	 * 
	 * @return The MUNICIPAL Tenor Delta Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> municipalTenorDeltaMargin (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketDeltaSensitivitySettings)
	{
		if (null == bucketDeltaSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> municipalTenorDeltaMargin =
			_municipalTenorDeltaSensitivity.deltaSensitivityMargin
				(bucketDeltaSensitivitySettings.municipalTenorRiskWeight());

		if (null == municipalTenorDeltaMargin)
		{
			return municipalTenorDeltaMargin;
		}

		double deltaConcentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			deltaConcentrationRiskFactor = deltaConcentrationRiskFactor
				(bucketDeltaSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalTenorDeltaMarginEntry :
			municipalTenorDeltaMargin.entrySet())
		{
			java.lang.String tenor = municipalTenorDeltaMarginEntry.getKey();

			municipalTenorDeltaMargin.put (
				tenor,
				municipalTenorDeltaMargin.get (tenor) * deltaConcentrationRiskFactor
			);
		}

		return municipalTenorDeltaMargin;
	}

	/**
	 * Generate the IR Margin Factor Aggregate
	 * 
	 * @param bucketDeltaSensitivitySettings The Bucket Delta Tenor Sensitivity Settings
	 * 
	 * @return The IR Margin Factor Aggregate
	 */

	public org.drip.simm20.margin.IRFactorAggregate aggregate (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketDeltaSensitivitySettings)
	{
		if (null == bucketDeltaSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> oisTenorDeltaMargin =
			_oisTenorDeltaSensitivity.deltaSensitivityMargin
				(bucketDeltaSensitivitySettings.oisTenorRiskWeight());

		java.util.Map<java.lang.String, java.lang.Double> libor1MTenorDeltaMargin =
			_libor1MTenorDeltaSensitivity.deltaSensitivityMargin
				(bucketDeltaSensitivitySettings.libor1MTenorRiskWeight());

		java.util.Map<java.lang.String, java.lang.Double> libor3MTenorDeltaMargin =
			_libor3MTenorDeltaSensitivity.deltaSensitivityMargin
				(bucketDeltaSensitivitySettings.libor3MTenorRiskWeight());

		java.util.Map<java.lang.String, java.lang.Double> libor6MTenorDeltaMargin =
			_libor6MTenorDeltaSensitivity.deltaSensitivityMargin
				(bucketDeltaSensitivitySettings.libor6MTenorRiskWeight());

		java.util.Map<java.lang.String, java.lang.Double> libor12MTenorDeltaMargin =
			_libor12MTenorDeltaSensitivity.deltaSensitivityMargin
				(bucketDeltaSensitivitySettings.libor12MTenorRiskWeight());

		java.util.Map<java.lang.String, java.lang.Double> primeTenorDeltaMargin =
			_primeTenorDeltaSensitivity.deltaSensitivityMargin
				(bucketDeltaSensitivitySettings.primeTenorRiskWeight());

		java.util.Map<java.lang.String, java.lang.Double> municipalTenorDeltaMargin =
			_municipalTenorDeltaSensitivity.deltaSensitivityMargin
				(bucketDeltaSensitivitySettings.municipalTenorRiskWeight());

		if (null == oisTenorDeltaMargin ||
			null == libor1MTenorDeltaMargin ||
			null == libor3MTenorDeltaMargin ||
			null == libor6MTenorDeltaMargin ||
			null == libor12MTenorDeltaMargin ||
			null == primeTenorDeltaMargin ||
			null == municipalTenorDeltaMargin)
		{
			return null;
		}

		double deltaConcentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			deltaConcentrationRiskFactor = deltaConcentrationRiskFactor
				(bucketDeltaSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalTenorDeltaMarginEntry :
			municipalTenorDeltaMargin.entrySet())
		{
			java.lang.String tenor = municipalTenorDeltaMarginEntry.getKey();

			oisTenorDeltaMargin.put (
				tenor,
				oisTenorDeltaMargin.get (tenor) * deltaConcentrationRiskFactor
			);

			libor1MTenorDeltaMargin.put (
				tenor,
				libor1MTenorDeltaMargin.get (tenor) * deltaConcentrationRiskFactor
			);

			libor3MTenorDeltaMargin.put (
				tenor,
				libor3MTenorDeltaMargin.get (tenor) * deltaConcentrationRiskFactor
			);

			libor6MTenorDeltaMargin.put (
				tenor,
				libor6MTenorDeltaMargin.get (tenor) * deltaConcentrationRiskFactor
			);

			libor12MTenorDeltaMargin.put (
				tenor,
				libor12MTenorDeltaMargin.get (tenor) * deltaConcentrationRiskFactor
			);

			primeTenorDeltaMargin.put (
				tenor,
				primeTenorDeltaMargin.get (tenor) * deltaConcentrationRiskFactor
			);

			municipalTenorDeltaMargin.put (
				tenor,
				municipalTenorDeltaMargin.get (tenor) * deltaConcentrationRiskFactor
			);
		}

		try
		{
			return new org.drip.simm20.margin.IRFactorAggregate (
				oisTenorDeltaMargin,
				libor1MTenorDeltaMargin,
				libor3MTenorDeltaMargin,
				libor6MTenorDeltaMargin,
				libor12MTenorDeltaMargin,
				primeTenorDeltaMargin,
				municipalTenorDeltaMargin,
				deltaConcentrationRiskFactor
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
