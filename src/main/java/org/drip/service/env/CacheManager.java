
package org.drip.service.env;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service">Service</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/env">Env</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
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
