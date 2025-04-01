
package org.drip.service.env;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>CacheManager</i> implements the DRIP Cache Management Functionality, and contains the Functions to Add,
 * 	Delete, Retrieve, and Time out a Key-Value Pair along the lines of mem-cache-d. It provides the following
 * 	Functions:
 * 
 * <ul>
 * 		<li>Initialize the Build Logs of the Build Manager</li>
 * </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/env/README.md">Library Module Loader Environment Manager</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CacheManager
{
	private static final boolean s_bLog = true;

	private static ConcurrentHashMap<String, String> _cacheMap = null;

	private static final ScheduledExecutorService _scheduledExecutorService =
		Executors.newScheduledThreadPool (1);

	/**
	 * Initialize the Cache Manager
	 * 
	 * @return TRUE - The Cache Manager successfully initialized
	 */

	public static final boolean Init()
	{
		if (null != _cacheMap) {
			return true;
		}

		_cacheMap = new ConcurrentHashMap<String, String>();

		return true;
	}

	/**
	 * The Put Method adds a Key/Value Pair to the In-Memory KV Store
	 * 
	 * @param key The Key
	 * @param value The Value
	 * @param secondsToExpiry The Time to Expiry of the Key/Value Pair
	 * 
	 * @return Return Value from the Underlying HashMap.put
	 */

	public static final String Put (
		final String key,
		final String value,
		final long secondsToExpiry)
	{
		if (null == key || key.isEmpty() || null == value || value.isEmpty()) {
			return null;
		}

		if (0L < secondsToExpiry) {
			_scheduledExecutorService.schedule (
				new Runnable()
				{
					@Override public void run()
					{
						if (s_bLog) {
							System.out.println (
								"\t\t[" + new Date() + "] Removing " + key + " from Thread " +
									Thread.currentThread()
							);
						}

						if (_cacheMap.contains (key)) {
							_cacheMap.remove (key);
						}
					}
				},
				secondsToExpiry,
				TimeUnit.SECONDS
			);
		}

		return _cacheMap.put (key, value);
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

		return _cacheMap.contains (strKey);
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

		return _cacheMap.get (strKey);
	}
}
