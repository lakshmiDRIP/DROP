
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
					"3.82.0",
					"1.8.0_112",
					"Sun Nov 11 15:42:23 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.81.0",
					"1.8.0_112",
					"Mon Nov 05 18:22:06 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.80.0",
					"1.8.0_112",
					"Fri Nov 02 21:22:20 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.79.0",
					"1.8.0_112",
					"Fri Oct 26 15:37:12 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.78.0",
					"1.8.0_112",
					"Tue Oct 16 18:01:52 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.77.0",
					"1.8.0_112",
					"Wed Oct 03 23:30:03 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.76.0",
					"1.8.0_112",
					"Sat Sep 29 00:34:42 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.75.0",
					"1.8.0_112",
					"Sun Sep 23 20:49:57 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.74.0",
					"1.8.0_112",
					"Tue Sep 11 23:21:49 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.73.0",
					"1.8.0_112",
					"Mon Sep 03 09:50:58 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.72.0",
					"1.8.0_112",
					"Sat Aug 25 21:49:58 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.71.0",
					"1.8.0_112",
					"Sun Aug 19 17:11:04 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.70.0",
					"1.8.0_112",
					"Mon Aug 13 20:18:11 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.68.0",
					"1.8.0_112",
					"Sat Aug 03 17:46:29 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.67.0",
					"1.8.0_112",
					"Sun Jul 29 22:33:36 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.65.0",
					"1.8.0_112",
					"Wed Jul 18 15:07:54 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.61.0",
					"1.8.0_112",
					"Wed Jul 11 14:57:02 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.56.0",
					"1.8.0_112",
					"Thu Jun 21 17:36:41 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.54.0",
					"1.8.0_112",
					"Fri Jun 15 22:02:20 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.52.0",
					"1.8.0_112",
					"Wed Jun 02 11:42:46 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.45.0",
					"1.8.0_112",
					"Wed May 23 17:25:31 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.44.0",
					"1.8.0_112",
					"Tue May 22 14:55:44 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.43.0",
					"1.8.0_112",
					"Wed Apr 25 07:39:51 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.42.0",
					"1.8.0_112",
					"Sat Apr 21 03:24:56 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.41.0",
					"1.8.0_112",
					"Tue Apr 17 12:01:07 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.40.0",
					"1.8.0_112",
					"Sat Apr 15 14:31:21 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.39.0",
					"1.8.0_112",
					"Sat Apr 07 16:49:44 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.38.0",
					"1.8.0_112",
					"Wed Apr 04 23:31:19 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.37.0",
					"1.8.0_112",
					"Wed Mar 28 19:26:05 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.36.0",
					"1.8.0_112",
					"Fri Mar 09 09:38:17 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.35.0",
					"1.8.0_112",
					"Mon Mar 05 14:23:32 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.34.0",
					"1.8.0_112",
					"Thu Feb 26 23:27:08 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.33.0",
					"1.8.0_112",
					"Thu Feb 22 22:59:49 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.32.0",
					"1.8.0_112",
					"Sat Feb 10 23:20:26 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.31.0",
					"1.8.0_112",
					"Tue Feb 06 01:10:47 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.30.0",
					"1.8.0_112",
					"Mon Jan 29 18:28:41 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.29.0",
					"1.8.0_112",
					"Fri Jan 26 20:46:23 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.28.0",
					"1.8.0_112",
					"Tue Jan 16 22:46:36 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.27.0",
					"1.8.0_112",
					"Sat Jan 13 13:23:56 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.26.0",
					"1.8.0_112",
					"Mon Jan 08 18:01:41 EST 2018"
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
