
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
 * RiskMeasureSensitivitySettingsIR holds the Settings that govern the Generation of the ISDA SIMM Bucket
 *  Sensitivities across Individual IR Class Risk Measure Buckets. The References are:
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

public class RiskMeasureSensitivitySettingsIR
{
	private org.drip.measure.stochastic.LabelCorrelation _crossBucketCorrelation = null;
	private java.util.Map<java.lang.String, org.drip.simm20.parameters.BucketSensitivitySettingsIR>
		_bucketSensitivitySettingsMap = null;

	/**
	 * Generate the Standard ISDA DELTA Instance of RiskMeasureSensitivitySettingsIR
	 * 
	 * @param currencyList The Currency List
	 * 
	 * @return The Standard ISDA DELTA Instance of RiskMeasureSensitivitySettingsIR
	 */

	public static final RiskMeasureSensitivitySettingsIR ISDA_DELTA (
		final java.util.List<java.lang.String> currencyList)
	{
		if (null == currencyList)
		{
			return null;
		}

		int currencyListSize = currencyList.size();

		if (0 == currencyListSize)
		{
			return null;
		}

		double[][] crossCurrencyCorrelation = new double[currencyListSize][currencyListSize];

		java.util.Map<java.lang.String, org.drip.simm20.parameters.BucketSensitivitySettingsIR>
			bucketDeltaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm20.parameters.BucketSensitivitySettingsIR>();

		for (int currencyListIndex = 0; currencyListIndex < currencyListSize; ++currencyListIndex)
		{
			java.lang.String currency = currencyList.get (currencyListIndex);

			bucketDeltaSettingsMap.put (
				currency,
				org.drip.simm20.parameters.BucketSensitivitySettingsIR.ISDA_DELTA (currency)
			);

			for (int currencyListInnerIndex = 0;
				currencyListInnerIndex < currencyListSize;
				++currencyListInnerIndex)
			{
				crossCurrencyCorrelation[currencyListIndex][currencyListInnerIndex] =
					currencyListIndex == currencyListInnerIndex ? 1. :
						org.drip.simm20.rates.IRSystemics.CROSS_CURRENCY_CORRELATION;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsIR (
				bucketDeltaSettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					currencyList,
					crossCurrencyCorrelation
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Standard ISDA VEGA Instance of RiskMeasureSensitivitySettingsIR
	 * 
	 * @param currencyList The Currency List
	 * 
	 * @return The Standard ISDA VEGA Instance of RiskMeasureSensitivitySettingsIR
	 */

	public static final RiskMeasureSensitivitySettingsIR ISDA_VEGA (
		final java.util.List<java.lang.String> currencyList)
	{
		if (null == currencyList)
		{
			return null;
		}

		int currencyListSize = currencyList.size();

		if (0 == currencyListSize)
		{
			return null;
		}

		double[][] crossCurrencyCorrelation = new double[currencyListSize][currencyListSize];

		java.util.Map<java.lang.String, org.drip.simm20.parameters.BucketSensitivitySettingsIR>
			bucketVegaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm20.parameters.BucketSensitivitySettingsIR>();

		for (int currencyListIndex = 0; currencyListIndex < currencyListSize; ++currencyListIndex)
		{
			java.lang.String currency = currencyList.get (currencyListIndex);

			bucketVegaSettingsMap.put (
				currency,
				org.drip.simm20.parameters.BucketVegaSettingsIR.ISDA_VEGA (currency)
			);

			for (int currencyListInnerIndex = 0;
				currencyListInnerIndex < currencyListSize;
				++currencyListInnerIndex)
			{
				crossCurrencyCorrelation[currencyListIndex][currencyListInnerIndex] =
					currencyListIndex == currencyListInnerIndex ? 1. :
						org.drip.simm20.rates.IRSystemics.CROSS_CURRENCY_CORRELATION;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsIR (
				bucketVegaSettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					currencyList,
					crossCurrencyCorrelation
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * RiskMeasureSensitivitySettingsIR Constructor
	 * 
	 * @param bucketSensitivitySettingsMap The IR Bucket Sensitivity Settings Map
	 * @param crossBucketCorrelation The Cross Bucket Correlation
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskMeasureSensitivitySettingsIR (
		final java.util.Map<java.lang.String, org.drip.simm20.parameters.BucketSensitivitySettingsIR>
			bucketSensitivitySettingsMap,
		final org.drip.measure.stochastic.LabelCorrelation crossBucketCorrelation)
		throws java.lang.Exception
	{
		if (null == (_bucketSensitivitySettingsMap = bucketSensitivitySettingsMap) ||
				0 == _bucketSensitivitySettingsMap.size() ||
			null == (_crossBucketCorrelation = crossBucketCorrelation))
		{
			throw new java.lang.Exception ("RiskMeasureSensitivitySettingsIR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Cross Bucket Correlation
	 * 
	 * @return The Cross Bucket Correlation
	 */

	public org.drip.measure.stochastic.LabelCorrelation crossBucketCorrelation()
	{
		return _crossBucketCorrelation;
	}

	/**
	 * Retrieve the IR Bucket Sensitivity Settings Map
	 * 
	 * @return The IR Bucket Sensitivity Settings Map
	 */

	public java.util.Map<java.lang.String, org.drip.simm20.parameters.BucketSensitivitySettingsIR>
		bucketSensitivitySettingsMap()
	{
		return _bucketSensitivitySettingsMap;
	}
}
