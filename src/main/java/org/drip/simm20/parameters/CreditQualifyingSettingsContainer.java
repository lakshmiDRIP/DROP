
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
 * CreditQualifyingSettingsContainer holds the ISDA SIMM 2.0 Credit Qualifying Buckets. The References are:
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

public class CreditQualifyingSettingsContainer
{
	private static org.drip.measure.stochastic.LabelCorrelation s_CrossBucketCorrelation = null;

	private static final java.util.Map<java.lang.Integer, org.drip.simm20.risk.CreditBucket>
		s_BucketMap = new java.util.TreeMap<java.lang.Integer, org.drip.simm20.risk.CreditBucket>();

	private static final boolean SetUpCrossBucketCorrelation()
	{
		java.util.List<java.lang.String> bucketList = new java.util.ArrayList<java.lang.String>();

		bucketList.add ("01");

		bucketList.add ("02");

		bucketList.add ("03");

		bucketList.add ("04");

		bucketList.add ("05");

		bucketList.add ("06");

		bucketList.add ("07");

		bucketList.add ("08");

		bucketList.add ("09");

		bucketList.add ("10");

		bucketList.add ("11");

		bucketList.add ("12");

		try
		{
			s_CrossBucketCorrelation = new org.drip.measure.stochastic.LabelCorrelation (
				bucketList,
				new double[][]
				{
					{1.00, 0.42, 0.39, 0.39, 0.40, 0.38, 0.39, 0.34, 0.37, 0.39, 0.37, 0.31},
					{0.42, 1.00, 0.44, 0.45, 0.47, 0.45, 0.33, 0.40, 0.41, 0.44, 0.43, 0.47},
					{0.39, 0.44, 1.00, 0.43, 0.45, 0.43, 0.32, 0.35, 0.41, 0.42, 0.40, 0.36},
					{0.39, 0.45, 0.43, 1.00, 0.47, 0.44, 0.30, 0.34, 0.39, 0.43, 0.39, 0.36},
					{0.40, 0.47, 0.45, 0.47, 1.00, 0.47, 0.31, 0.35, 0.40, 0.44, 0.42, 0.37},
					{0.38, 0.45, 0.43, 0.44, 0.47, 1.00, 0.30, 0.34, 0.38, 0.40, 0.39, 0.38},
					{0.39, 0.33, 0.32, 0.30, 0.31, 0.30, 1.00, 0.28, 0.31, 0.31, 0.30, 0.26},
					{0.34, 0.40, 0.35, 0.34, 0.35, 0.34, 0.28, 1.00, 0.34, 0.35, 0.33, 0.30},
					{0.37, 0.41, 0.41, 0.39, 0.40, 0.38, 0.31, 0.34, 1.00, 0.40, 0.37, 0.32},
					{0.39, 0.44, 0.42, 0.43, 0.44, 0.40, 0.31, 0.35, 0.40, 1.00, 0.40, 0.35},
					{0.37, 0.43, 0.40, 0.39, 0.42, 0.39, 0.30, 0.33, 0.37, 0.40, 1.00, 0.34},
					{0.31, 0.37, 0.36, 0.36, 0.37, 0.38, 0.26, 0.30, 0.32, 0.35, 0.34, 1.00}
				}
			);

			return true;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Initial the Credit Qualifying Settings
	 * 
	 * @return TRUE - The Credit Qualifying Settings successfully initialized
	 */

	public static final boolean Init()
	{
		try
		{
			s_BucketMap.put (
				-1,
				new org.drip.simm20.risk.CreditBucket (
					-1,
					org.drip.simm20.risk.CreditSystemics.CREDIT_QUALITY_UNSPECIFIED,
					org.drip.simm20.risk.SectorSystemics.RESIDUAL,
					238.
				)
			);

			s_BucketMap.put (
				1,
				new org.drip.simm20.risk.CreditBucket (
					1,
					org.drip.simm20.risk.CreditSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					org.drip.simm20.risk.SectorSystemics.SOVEREIGNS,
					85.
				)
			);

			s_BucketMap.put (
				2,
				new org.drip.simm20.risk.CreditBucket (
					2,
					org.drip.simm20.risk.CreditSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					org.drip.simm20.risk.SectorSystemics.FINANCIALS,
					85.
				)
			);

			s_BucketMap.put (
				3,
				new org.drip.simm20.risk.CreditBucket (
					3,
					org.drip.simm20.risk.CreditSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					org.drip.simm20.risk.SectorSystemics.BASIC_MATERIALS,
					73.
				)
			);

			s_BucketMap.put (
				4,
				new org.drip.simm20.risk.CreditBucket (
					4,
					org.drip.simm20.risk.CreditSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					org.drip.simm20.risk.SectorSystemics.CONSUMER,
					49.
				)
			);

			s_BucketMap.put (
				5,
				new org.drip.simm20.risk.CreditBucket (
					5,
					org.drip.simm20.risk.CreditSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					org.drip.simm20.risk.SectorSystemics.TMT,
					48.
				)
			);

			s_BucketMap.put (
				6,
				new org.drip.simm20.risk.CreditBucket (
					6,
					org.drip.simm20.risk.CreditSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					org.drip.simm20.risk.SectorSystemics.LOCAL_SERVICES,
					43.
				)
			);

			s_BucketMap.put (
				7,
				new org.drip.simm20.risk.CreditBucket (
					7,
					org.drip.simm20.risk.CreditSystemics.CREDIT_QUALITY_HIGH_YIELD,
					org.drip.simm20.risk.SectorSystemics.BASIC_MATERIALS,
					161.
				)
			);

			s_BucketMap.put (
				8,
				new org.drip.simm20.risk.CreditBucket (
					8,
					org.drip.simm20.risk.CreditSystemics.CREDIT_QUALITY_HIGH_YIELD,
					org.drip.simm20.risk.SectorSystemics.FINANCIALS,
					238.
				)
			);

			s_BucketMap.put (
				9,
				new org.drip.simm20.risk.CreditBucket (
					9,
					org.drip.simm20.risk.CreditSystemics.CREDIT_QUALITY_HIGH_YIELD,
					org.drip.simm20.risk.SectorSystemics.BASIC_MATERIALS,
					151.
				)
			);

			s_BucketMap.put (
				10,
				new org.drip.simm20.risk.CreditBucket (
					10,
					org.drip.simm20.risk.CreditSystemics.CREDIT_QUALITY_HIGH_YIELD,
					org.drip.simm20.risk.SectorSystemics.CONSUMER,
					210.
				)
			);

			s_BucketMap.put (
				11,
				new org.drip.simm20.risk.CreditBucket (
					11,
					org.drip.simm20.risk.CreditSystemics.CREDIT_QUALITY_HIGH_YIELD,
					org.drip.simm20.risk.SectorSystemics.TMT,
					141.
				)
			);

			s_BucketMap.put (
				12,
				new org.drip.simm20.risk.CreditBucket (
					12,
					org.drip.simm20.risk.CreditSystemics.CREDIT_QUALITY_HIGH_YIELD,
					org.drip.simm20.risk.SectorSystemics.LOCAL_SERVICES,
					102.
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return SetUpCrossBucketCorrelation();
	}

	/**
	 * Retrieve the Set of Bucket Indexes available
	 * 
	 * @return The Set of Bucket Indexes available
	 */

	public static final java.util.Set<java.lang.Integer> BucketSet()
	{
		return s_BucketMap.keySet();
	}

	/**
	 * Indicate if the Bucket denoted by the Number is available
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return TRUE - The Bucket denoted by the Number is available
	 */

	public static final boolean ContainsBucket (
		final int bucketNumber)
	{
		return s_BucketMap.containsKey (bucketNumber);
	}

	/**
	 * Retrieve the Bucket denoted by the Number
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The Bucket denoted by the Number
	 */

	public static final org.drip.simm20.risk.CreditBucket Bucket (
		final int bucketNumber)
	{
		return ContainsBucket (bucketNumber) ? s_BucketMap.get (bucketNumber) : null;
	}

	/**
	 * Retrieve the Cross Bucket Correlation
	 * 
	 * @return The Cross Bucket Correlation
	 */

	public static final org.drip.measure.stochastic.LabelCorrelation CrossBucketCorrelation()
	{
		return s_CrossBucketCorrelation;
	}

	/**
	 * Retrieve the Bucket Map
	 * 
	 * @return The Bucket Map
	 */

	public static final java.util.Map<java.lang.Integer, org.drip.simm20.risk.CreditBucket> BucketMap()
	{
		return s_BucketMap;
	}
}
