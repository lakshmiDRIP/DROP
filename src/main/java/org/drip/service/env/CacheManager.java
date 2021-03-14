
package org.drip.service.env;

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
 * Delete, Retrieve, and Time out a Key-Value Pair along the lines of memcached.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/env/README.md">Library Module Loader Environment Manager</a></li>
 *  </ul>
 * <br><br>
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
		if (null != s_mapCache) return true;

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
