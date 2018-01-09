
package org.drip.service.env;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * BuildManager maintains a Log of the Build Records.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BuildManager
{
	private static org.drip.service.env.BuildRecord[] s_aBuildRecord = null;

	/**
	 * Initialize the Build Logs of the Build Manager
	 * 
	 * @return TRUE - The Build Manager Successfully Initialized
	 */

	public static final boolean Init()
	{
		if (null != s_aBuildRecord) return true;

		try {
			s_aBuildRecord = new org.drip.service.env.BuildRecord[] {
				new org.drip.service.env.BuildRecord (
					"3.26.0",
					"1.8.0_112",
					"Mon Jan 08 18:01:41 EST 2017"
				),
				new org.drip.service.env.BuildRecord (
					"3.25.0",
					"1.8.0_112",
					"Mon Dec 31 18:43:34 EST 2017"
				),
				new org.drip.service.env.BuildRecord (
					"3.24.0",
					"1.8.0_112",
					"Mon Dec 25 12:29:26 EST 2017"
				),
				new org.drip.service.env.BuildRecord (
					"3.23.0",
					"1.8.0_112",
					"Fri Dec 22 14:51:17 EST 2017"
				),
				new org.drip.service.env.BuildRecord (
					"3.22.0",
					"1.8.0_112",
					"Mon Dec 18 17:32:03 EST 2017"
				)
			};

			return true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Retrieve the Array of Build Records
	 * 
	 * @return Array of Build Records
	 */

	public static final org.drip.service.env.BuildRecord[] buildRecords()
	{
		return s_aBuildRecord;
	}

	/**
	 * Retrieve the Latest Build Record
	 * 
	 * @return Latest Build Record
	 */

	public static final org.drip.service.env.BuildRecord latestBuildRecord()
	{
		return s_aBuildRecord[0];
	}
}
