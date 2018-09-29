
package org.drip.simm.credit;

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
 * CRThresholdContainer holds the ISDA SIMM 2.0 Credit Risk Thresholds - the Credit Risk Buckets and the
 *  Delta/Vega Limits defined for the Concentration Thresholds. The References are:
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

public class CRThresholdContainer
{
	private static final java.util.Map<java.lang.Integer, org.drip.simm.common.DeltaVegaThreshold>
		s_CRQThresholdMap = new java.util.TreeMap<java.lang.Integer,
			org.drip.simm.common.DeltaVegaThreshold>();

	private static final java.util.Map<java.lang.Integer, org.drip.simm.common.DeltaVegaThreshold>
		s_CRNQThresholdMap = new java.util.TreeMap<java.lang.Integer,
			org.drip.simm.common.DeltaVegaThreshold>();

	/**
	 * Initialize the Credit Risk Threshold Container
	 * 
	 * @return TRUE - The Credit Risk Threshold Container successfully initialized
	 */

	public static final boolean Init()
	{
		try
		{
			s_CRQThresholdMap.put (
				-1,
				new org.drip.simm.common.DeltaVegaThreshold (
					0.29,
					290.
				)
			);

			s_CRQThresholdMap.put (
				1,
				new org.drip.simm.common.DeltaVegaThreshold (
					0.95,
					290.
				)
			);

			s_CRQThresholdMap.put (
				2,
				new org.drip.simm.common.DeltaVegaThreshold (
					0.29,
					290.
				)
			);

			s_CRQThresholdMap.put (
				3,
				new org.drip.simm.common.DeltaVegaThreshold (
					0.29,
					290.
				)
			);

			s_CRQThresholdMap.put (
				4,
				new org.drip.simm.common.DeltaVegaThreshold (
					0.29,
					290.
				)
			);

			s_CRQThresholdMap.put (
				5,
				new org.drip.simm.common.DeltaVegaThreshold (
					0.29,
					290.
				)
			);

			s_CRQThresholdMap.put (
				6,
				new org.drip.simm.common.DeltaVegaThreshold (
					0.29,
					290.
				)
			);

			s_CRQThresholdMap.put (
				7,
				new org.drip.simm.common.DeltaVegaThreshold (
					0.95,
					290.
				)
			);

			s_CRQThresholdMap.put (
				8,
				new org.drip.simm.common.DeltaVegaThreshold (
					0.29,
					290.
				)
			);

			s_CRQThresholdMap.put (
				9,
				new org.drip.simm.common.DeltaVegaThreshold (
					0.29,
					290.
				)
			);

			s_CRQThresholdMap.put (
				10,
				new org.drip.simm.common.DeltaVegaThreshold (
					0.29,
					290.
				)
			);

			s_CRQThresholdMap.put (
				11,
				new org.drip.simm.common.DeltaVegaThreshold (
					0.29,
					290.
				)
			);

			s_CRQThresholdMap.put (
				12,
				new org.drip.simm.common.DeltaVegaThreshold (
					0.29,
					290.
				)
			);

			s_CRNQThresholdMap.put (
				-1,
				new org.drip.simm.common.DeltaVegaThreshold (
					0.5,
					65.
				)
			);

			s_CRNQThresholdMap.put (
				1,
				new org.drip.simm.common.DeltaVegaThreshold (
					9.5,
					65.
				)
			);

			s_CRNQThresholdMap.put (
				2,
				new org.drip.simm.common.DeltaVegaThreshold (
					0.5,
					65.
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Credit Risk Qualifying Threshold Bucket Set
	 * 
	 * @return The Credit Risk Qualifying Threshold Bucket Set
	 */

	public static final java.util.Set<java.lang.Integer> QualifyingBucketSet()
	{
		return s_CRQThresholdMap.keySet();
	}

	/**
	 * Retrieve the Credit Risk Non-Qualifying Threshold Bucket Set
	 * 
	 * @return The Credit Risk Non-Qualifying Threshold Bucket Set
	 */

	public static final java.util.Set<java.lang.Integer> NonQualifyingBucketSet()
	{
		return s_CRNQThresholdMap.keySet();
	}

	/**
	 * Indicate if the Qualifying Bucket specified by the Number is available
	 * 
	 * @param bucketNumber The Qualifying Bucket Number
	 * 
	 * @return TRUE - The Qualifying Bucket specified by the Number is available
	 */

	public static final boolean ContainsQualifyingBucket (
		final int bucketNumber)
	{
		return s_CRQThresholdMap.containsKey (bucketNumber);
	}

	/**
	 * Indicate if the Non-Qualifying Bucket specified by the Number is available
	 * 
	 * @param bucketNumber The Non-Qualifying Bucket Number
	 * 
	 * @return TRUE - The Non-Qualifying Bucket specified by the Number is available
	 */

	public static final boolean ContainsNonQualifyingBucket (
		final int bucketNumber)
	{
		return s_CRNQThresholdMap.containsKey (bucketNumber);
	}

	/**
	 * Retrieve the Credit Risk Qualifying Threshold Instance identified by the Bucket Number
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The Credit Risk Qualifying Threshold Instance identified by the Bucket Number
	 */

	public static final org.drip.simm.common.DeltaVegaThreshold QualifyingThreshold (
		final int bucketNumber)
	{
		return ContainsQualifyingBucket (bucketNumber) ? s_CRQThresholdMap.get (bucketNumber) : null;
	}

	/**
	 * Retrieve the Credit Risk Non-Qualifying Threshold Instance identified by the Bucket Number
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The Credit Risk Non-Qualifying Threshold Instance identified by the Bucket Number
	 */

	public static final org.drip.simm.common.DeltaVegaThreshold NonQualifyingThreshold (
		final int bucketNumber)
	{
		return ContainsNonQualifyingBucket (bucketNumber) ? s_CRNQThresholdMap.get (bucketNumber) : null;
	}

	/**
	 * Retrieve the Credit Risk Qualifying Threshold Map
	 * 
	 * @return The Credit Risk Qualifying Threshold Map
	 */

	public static final java.util.Map<java.lang.Integer, org.drip.simm.common.DeltaVegaThreshold>
		CreditRiskQualifyingThresholdMap()
	{
		return s_CRQThresholdMap;
	}

	/**
	 * Retrieve the Credit Risk Non-Qualifying Threshold Map
	 * 
	 * @return The Credit Risk Non-Qualifying Threshold Map
	 */

	public static final java.util.Map<java.lang.Integer, org.drip.simm.common.DeltaVegaThreshold>
		CreditRiskNonQualifyingThresholdMap()
	{
		return s_CRNQThresholdMap;
	}
}
