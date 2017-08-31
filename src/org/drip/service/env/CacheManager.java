
package org.drip.service.env;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * CacheManager implements the DRIP Cache Management Functionality, and contains the Functions to Add,
 *  Delete, Retrieve, and Time out a Key-Value Pair along the lines of memcached.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CacheManager {
	private static final boolean s_bLog = true;
	private static java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.String> s_mapCache =
		null;

	private static final java.util.concurrent.ScheduledExecutorService _ses =
		java.util.concurrent.Executors.newScheduledThreadPool (1);

	/**
	 * Initialize the Cache Manager
	 * 
	 * @return TRUE - The Cache Manager successfully initialized
	 */

	public static final boolean Init()
	{
		s_mapCache = new java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.String>();

		return true;
	}

	/**
	 * The Put Method adds a Key/Value Pair to the In-Memory KV Store
	 * 
	 * @param strKey The Key
	 * @param strValue The Value
	 * @param lSecondsToExpiry The Time to Expiry of the Key/Value Pair
	 * 
	 * @return Return Value from the Underlying HashMap.put
	 */

	public static final java.lang.String Put (
		final java.lang.String strKey,
		final java.lang.String strValue,
		final long lSecondsToExpiry)
	{
		if (null == strKey || strKey.isEmpty() || null == strValue || strValue.isEmpty()) return null;

		if (0 < lSecondsToExpiry) {
			java.lang.Runnable timedTask = new java.lang.Runnable() {
				@Override public void run() {
					if (s_bLog)
						System.out.println ("\t\t[" + new java.util.Date() + "] Removing " + strKey +
							" from Thread " + java.lang.Thread.currentThread());

					if (s_mapCache.contains (strKey)) s_mapCache.remove (strKey);
				}
			};

			_ses.schedule (timedTask, lSecondsToExpiry, java.util.concurrent.TimeUnit.SECONDS);
		}

		return s_mapCache.put (strKey, strValue);
	}

	/**
	 * The Contains Method checks the Presence of the specified Key
	 * 
	 * @param strKey The Key
	 * 
	 * @return Return Value from the Underlying HashMap.contains
	 */

	public static final boolean Contains (
		final java.lang.String strKey)
	{
		if (null == strKey || strKey.isEmpty()) return false;

		return s_mapCache.contains (strKey);
	}

	/**
	 * The Get Method retrieves the Value given the Key
	 * 
	 * @param strKey The Key
	 * 
	 * @return Return Value from the Underlying HashMap.get
	 */

	public static final java.lang.String Get (
		final java.lang.String strKey)
	{
		if (null == strKey || strKey.isEmpty()) return null;

		return s_mapCache.get (strKey);
	}
}
