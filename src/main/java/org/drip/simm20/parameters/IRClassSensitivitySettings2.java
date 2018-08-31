
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
 * IRClassSensitivitySettings holds the Constituent IR Currency Bucket Curve Tenor Settings and the
 *  corresponding Cross-Bucket Correlations. The References are:
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

public class IRClassSensitivitySettings2
{
	private double _crossBucketCorrelation = java.lang.Double.NaN;

	private java.util.Map<java.lang.String, org.drip.simm20.parameters.BucketSensitivitySettingsIR>
		_irBucketSensitivitySettingsMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.simm20.parameters.BucketSensitivitySettingsIR>();

	/**
	 * Construct the ISDA Standard IRClassSensitivitySettings
	 * 
	 * @return The ISDA Standard IRClassSensitivitySettings
	 */

	public static final IRClassSensitivitySettings2 ISDA()
	{
		try
		{
			return new IRClassSensitivitySettings2
				(org.drip.simm20.rates.IRSystemics.CROSS_CURRENCY_CORRELATION);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * IRClassSensitivitySettings Constructor
	 * 
	 * @param crossBucketCorrelation The Cross Currency Bucket Correlation
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public IRClassSensitivitySettings2 (
		final double crossBucketCorrelation)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_crossBucketCorrelation = crossBucketCorrelation) ||
			1. < _crossBucketCorrelation || -1. > crossBucketCorrelation)
		{
			throw new java.lang.Exception ("IRClassSensitivitySettings Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Cross Currency Bucket Correlation
	 * 
	 * @return The Cross Currency Bucket Correlation
	 */

	public double crossBucketCorrelation()
	{
		return _crossBucketCorrelation;
	}

	/**
	 * Add the specified Curve Tenor Settings Instance
	 * 
	 * @param currency The Currency
	 * @param curveTenorSettings The specified Curve Tenor Settings Instance
	 * 
	 * @return TRUE - The specified Curve Tenor Settings Instance successfully added
	 */

	public boolean addCurveTenorSettings (
		final java.lang.String currency,
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR curveTenorSettings)
	{
		if (null == currency || currency.isEmpty() || null == curveTenorSettings)
		{
			return false;
		}

		_irBucketSensitivitySettingsMap.put (
			currency,
			curveTenorSettings
		);

		return true;
	}

	/**
	 * Indicate if the Currency is available in the Map
	 * 
	 * @param currency The Currency
	 * 
	 * @return TRUE - The Currency is available in the Map
	 */

	public boolean containsCurrency (
		final java.lang.String currency)
	{
		return null != currency && !currency.isEmpty() && _irBucketSensitivitySettingsMap.containsKey (currency);
	}

	/**
	 * Retrieve the Curve Tenor Settings for the Currency
	 * 
	 * @param currency The Currency
	 * 
	 * @return The Curve Tenor Settings for the Currency
	 */

	public org.drip.simm20.parameters.BucketSensitivitySettingsIR curveTenorSettings (
		final java.lang.String currency)
	{
		return containsCurrency (currency) ? _irBucketSensitivitySettingsMap.get (currency) : null;
	}

	/**
	 * Retrieve the IR Bucket Sensitivity Settings Map
	 * 
	 * @return The IR Bucket Sensitivity Settings Map
	 */

	public java.util.Map<java.lang.String, org.drip.simm20.parameters.BucketSensitivitySettingsIR>
		irBucketSensitivitySettingsMap()
	{
		return _irBucketSensitivitySettingsMap;
	}
}
