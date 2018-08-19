
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
 * EQSensitivity holds the ISDA SIMM 2.0 Bucket Sensitivities across EQ Risk Factor Buckets. The References
 *  are:
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

public class EQSensitivity
{
	private java.util.Map<java.lang.Integer, java.lang.Double> _bucketMap = null;

	/**
	 * Construct an Instance of EQSensitivity with Zero Sensitivities across all Buckets
	 * 
	 * @return The "Zero/NULL" EQSensitivity
	 */

	public static final EQSensitivity NULL()
	{
		java.util.Map<java.lang.Integer, java.lang.Double> bucketMap = new
			java.util.HashMap<java.lang.Integer, java.lang.Double>();

		bucketMap.put (
			-1,
			0.
		);

		for (int bucketIndex = 1; bucketIndex <= 12; ++bucketIndex)
		{
			bucketMap.put (
				bucketIndex,
				0.
			);
		}

		try
		{
			return new EQSensitivity (bucketMap);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * EQSensitivity Constructor
	 * 
	 * @param bucketMap The Bucket Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EQSensitivity (
		final java.util.Map<java.lang.Integer, java.lang.Double> bucketMap)
		throws java.lang.Exception
	{
		if (null == (_bucketMap = bucketMap) || 0 == _bucketMap.size())
		{
			throw new java.lang.Exception ("EQSensitivity Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Bucket Sensitivity Map
	 * 
	 * @return The Bucket Sensitivity Map
	 */

	public java.util.Map<java.lang.Integer, java.lang.Double> bucketMap()
	{
		return _bucketMap;
	}

	/**
	 * Compute the Delta Margin Based Net Sensitivity
	 * 
	 * @param equitySensitivitySettings The Equity Sensitivity Settings
	 * 
	 * @return The Delta Margin Based Net Sensitivity
	 */

	public org.drip.simm20.product.EQNetSensitivity deltaMargin (
		final org.drip.simm20.product.EQSensitivitySettings equitySensitivitySettings)
	{
		java.util.Map<java.lang.Integer, java.lang.Double> bucketRiskWeight =
			equitySensitivitySettings.bucketRiskWeight();

		java.util.Map<java.lang.Integer, java.lang.Double> bucketDeltaThreshold =
			equitySensitivitySettings.bucketDeltaThreshold();

		java.util.Map<java.lang.Integer, org.drip.simm20.product.EQBucketNetSensitivity>
			bucketNetSensitivityMap = new java.util.HashMap<java.lang.Integer,
				org.drip.simm20.product.EQBucketNetSensitivity>();

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> bucketMapEntry : _bucketMap.entrySet())
		{
			int bucketIndex = bucketMapEntry.getKey();

			double bucketSensitivity = bucketMapEntry.getValue();

			if (!bucketRiskWeight.containsKey (bucketIndex) || !bucketDeltaThreshold.containsKey
				(bucketIndex))
			{
				return null;
			}

			try
			{
				bucketNetSensitivityMap.put (
					bucketIndex,
					new org.drip.simm20.product.EQBucketNetSensitivity (
						bucketSensitivity * bucketRiskWeight.get (bucketIndex),
						 java.lang.Math.max (
							1.,
							java.lang.Math.sqrt (java.lang.Math.abs (bucketSensitivity)) /
								bucketDeltaThreshold.get (bucketIndex)
						)
					)
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return null;
	}
}
