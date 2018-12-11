
package org.drip.analytics.support;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
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
 * <i>Logger</i> implements level-set logging, backed by either the screen or a file. Logging always includes
 * time stamps, and happens according to the level requested.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Analytics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/support/README.md">Support</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Logger {

	/**
	 * Logger level ERROR
	 */

	public static final int ERROR = 0;

	/**
	 * Logger level WARNING
	 */

	public static final int WARNING = 1;

	/**
	 * Logger level INFO
	 */

	public static final int INFO = 2;

	/**
	 * Logger level DEBUG
	 */

	public static final int DEBUG = 4;

	private static boolean s_bInit = false;
	private static java.io.BufferedWriter _writeLog = null;

	private static final java.lang.String ImprintPreSub (
		final int iLevel,
		final java.lang.String strMsg)
	{
		if (null == strMsg || strMsg.isEmpty()) return "";

		java.lang.StringBuilder sb = new java.lang.StringBuilder();

		sb.append ("[").append ((new java.util.Date()).toString()).append ("|Level:").append (iLevel).append
			("|").append (strMsg).append ("]\n");

		return sb.toString();
	}

	/**
	 * Initialize the logger from a configuration file
	 * 
	 * @param strConfigFile Configuration file containing the logger file location
	 * 
	 * @return boolean indicating whether initialization succeeded
	 */

	public static boolean Init (
		final java.lang.String strConfigFile)
	{
		if (null == strConfigFile || strConfigFile.isEmpty()) return true;

		if (s_bInit) return true;

		try {
			_writeLog = new java.io.BufferedWriter (new java.io.FileWriter
				(org.drip.param.config.ConfigLoader.LoggerLocation (strConfigFile)));

			return s_bInit = true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return s_bInit = false;
	}

	/**
	 * Log a specific message to the level
	 * 
	 * @param iLevel the level of message (ERROR/WARNING/INFO/DEBUG)
	 * @param bHardLog whether the logging is to file/DB (true) or to screen (false)
	 * @param strMsg Message to be logged
	 * 
	 * @return boolean indicating whether logging operation succeeded
	 */

	public static boolean Log (
		final int iLevel,
		final boolean bHardLog,
		final java.lang.String strMsg)
	{
		if (!s_bInit || null == strMsg || strMsg.isEmpty()) return false;

		if (bHardLog) {
			try {
				_writeLog.write (ImprintPreSub (iLevel, strMsg));

				return true;
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}

			return false;
		}

		System.out.println (ImprintPreSub (iLevel, strMsg));

		return true;
	}
}
